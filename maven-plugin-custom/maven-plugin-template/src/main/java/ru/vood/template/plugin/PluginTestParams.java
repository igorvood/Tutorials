package ru.vood.template.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.junit.Assert;
import ru.vood.template.plugin.generated.from.xsd.ClassFromTablesType;
import ru.vood.template.plugin.generated.from.xsd.PluginTinesType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Mojo(name = "setname", defaultPhase = LifecyclePhase.GENERATE_SOURCES, threadSafe = true)
public class PluginTestParams extends AbstractMojo {

    @Parameter(property = "someDataClass")
    private SomeDataClass someDataClass;

    @Parameter(property = "filename")
    private String filename;

    @Override
    public void execute() {
        System.out.println("------>" + filename);
        System.out.println("------>" + someDataClass);
        Assert.assertNotNull(filename, "filename Should be not null");

        PluginParamsXml pluginParamsXml = null;
        StringBuilder sb = new StringBuilder();
        pluginParamsXml = new PluginParamsXml();
        try {
            Files.lines(Paths.get(filename), StandardCharsets.UTF_8)
                    .forEach(sb::append);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("------>\n" + sb + "\n");
        final PluginTinesType pluginTines = pluginParamsXml.xmlToObj(sb.toString());
        System.out.println("pluginTines------>" + toString(pluginTines));
    }

    private String toString(PluginTinesType pluginTines) {
        List<ClassFromTablesType> lists = pluginTines.getTableClassList().getClassFromTables();
        String s = lists.stream()
                .map(classTablesType -> "class=" + classTablesType.getGeneratingClass() + " TableList=" +
                        classTablesType.getTableList())
                .reduce((o, o2) -> o + "\n" + o2)
                .orElse("");

        return "PluginTines{username='" + pluginTines.getUsername() +
                "', password='" + pluginTines.getPassword() + "', tableClassList=" + s +
                "}";
    }


}
