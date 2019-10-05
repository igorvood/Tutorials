package ru.vood.test.db;

import freemarker.cache.NullCacheStorage;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.Version;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

public class DataSourceTestUtil {

    public static final String SQL_SCRIPT_FILE_EXTENSION = ".sql";
    public static final String SQL_SCRIPT_SETUP_STD_SUFFIX = "setup" + SQL_SCRIPT_FILE_EXTENSION;
    public static final String SQL_SCRIPT_TEARDOWN_STD_SUFFIX =
            "teardown" + SQL_SCRIPT_FILE_EXTENSION;
    public static final String STD_SUPPORT_PACKAGE_NAME = "test$support";
    public static final String STD_SUPPORT_PACKAGE_FILE_SUFFIX = STD_SUPPORT_PACKAGE_NAME + ".pck";
    protected static final Configuration configuration;
    private static final Logger logger = LoggerFactory.getLogger(DataSourceTestUtil.class);

    static {
        configuration = new DefaultConfiguration(new Version(2, 3, 0));
        configuration.setCacheStorage(new NullCacheStorage());
        configuration.setTemplateLoader(new ResourceTemplateLoader());
        configuration.setDefaultEncoding("UTF8");
        configuration.setNumberFormat("computer");
        configuration.setDateFormat("yyyy-MM-dd");
        configuration.setDateTimeFormat("yyyy-MM-dd HH:mm:ss");
        configuration.setSharedVariable("static", null); // это не работает без статического контекста
    }

    private DataSourceTestUtil() {
    }

    public static void applyScript(String[] script, ScriptPartExecutor scriptPartExecutor) {
        for (String part : script) {
            applyScriptPart(part, scriptPartExecutor);
        }
    }

    public static void applyScriptPart(String part, ScriptPartExecutor scriptPartExecutor) {
        Assert.assertNotNull("Parameter scriptPartExecutor can't be null", scriptPartExecutor);
        Assert.assertNotNull("Parameter part can't be null", scriptPartExecutor);
        try {
            scriptPartExecutor.execute(part);
        } catch (Exception e) {
            logger.error("Error while execution statement: ");
            logger.error(part);
            throw e;
        }
    }

    public static boolean isTestResourceExists(Class<?> class4Resource, String fileName) {
        return class4Resource.getResource(fileName) != null;
    }

    public static String[] readUtf8SqlScript(
            Class<?> class4Resource, String fileName, boolean emptyReturnWhileNoResources
    ) {

        URL url = class4Resource.getResource(fileName);
        if (url == null) {
            if (emptyReturnWhileNoResources) {
                return new String[]{};
            } else {
                throw new RuntimeException(String.format(
                        "Unable to find the resource based on class %s and relative path %s",
                        class4Resource.getName(), fileName)
                );

            }
        }

        String scrSrc;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), UTF_8))) {
            if (fileName.endsWith(".ftl")) {
                scrSrc = processTemplate("@/" + class4Resource.getPackage().getName().replace(".", "/") + "/" + fileName);
            } else {
                scrSrc = reader.lines().collect(Collectors.joining("\n"));
            }
        } catch (IOException e) {
            throw new RuntimeException("кошмар!", e);
        }
        logger.trace("========================================");
        logger.trace(scrSrc);
        logger.trace("========================================");
        if (scrSrc.length() > 0 && '\uFEFF' == scrSrc.charAt(0)) { // убирём BOM, если он там есть
            scrSrc = scrSrc.substring(1);
        }

        return sliceTestScript(scrSrc);
    }

    public static String[] sliceTestScript(String testScript) {
        List<String> ret = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        for (StringTokenizer st = new StringTokenizer(testScript, "\n", false); st.hasMoreTokens(); ) {
            String token = st.nextToken();
            if ("/".equals(token.trim())) {
                if (sb.length() > 0) {
                    ret.add(sb.toString());
                    sb.setLength(0);
                }
            } else {
                sb.append(token).append("\n");
            }
        }
        if (sb.length() > 0) {
            String s = sb.toString().trim();
            if (!s.isEmpty()) {
                ret.add(sb.toString());
            }
        }

        return ret.toArray(new String[0]);
    }

    public static String[] readUtf8SqlScript(Class<?> class4Resource, String fileName) {
        return readUtf8SqlScript(class4Resource, fileName, false);
    }

    public static void injectField(Object bean, String filedName, Object val) {
        Field field;
        try {
            field = findField(bean.getClass(), filedName);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("the bean=" + bean + " does not have the field=" + filedName,
                    e);
        }
        field.setAccessible(true);

        try {
            field.set(bean, val);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("cannot access to field=" + filedName, e);
        }
    }

    public static void applyScriptFromUtf8ScriptFile(Class<?> clazz, String fileName,
                                                     boolean emptyReturnWhileNoResources,
                                                     ScriptPartExecutor scriptPartExecutor) {
        String[] script = readUtf8SqlScript(clazz, fileName, emptyReturnWhileNoResources);
        if (script.length > 0) {
            logger.debug("script file applied: " + fileName);
        }
        applyScript(script, scriptPartExecutor);
    }

    protected static void applyScriptFromUtf8ScriptFile(Class<?> clazz, String fileName) {
        applyScriptFromUtf8ScriptFile(clazz, fileName, false, null);
    }

    public static void applyStdResourceSqlScript(Class<?> clazz, String scrSuffix, ScriptPartExecutor scriptPartExecutor) {
        String scrName = clazz.getSimpleName() + "." + scrSuffix;
        applyScriptFromUtf8ScriptFile(clazz, scrName, false, scriptPartExecutor);
    }

    private static Field findField(Class<?> beanClass, String filedName) throws NoSuchFieldException {
        try {
            return beanClass.getDeclaredField(filedName);
        } catch (NoSuchFieldException ex) {
            Class<?> sc = beanClass.getSuperclass();
            if (sc == null) {
                throw ex;
            }
            return findField(sc, filedName);
        }
    }

    private static String processTemplate(String templateName, Object... templateArgs) {
        Template template;
        try {
            template = configuration.getTemplate(templateName);
        } catch (Exception e) {
            throw
                    new RuntimeException(
                            String.format("unable to get template name=%s", templateName), e
                    );
        }

        return processTemplate(configuration, template, templateArgs);
    }

    private static String processTemplate(final Configuration preparedConfiguration, final Template template, Object[] templateArgs) {

        final StringWriter dest = new StringWriter();

        final SimpleHash root = new SimpleHash(preparedConfiguration.getObjectWrapper());
        root.put("template_args", templateArgs);

        try {
            template.process(root, dest);
        } catch (Exception e) {
            throw
                    new RuntimeException(
                            String.format("unable to process template name=%s", template.getName()), e
                    );
        }

        String res = dest.toString(); // fixme можно было бы и поэффективнее

        return
                res.length() > 0 && '\uFEFF' == res.charAt(0) ? // убирём BOM, если он там есть
                        res.substring(1) : res;
    }


}
