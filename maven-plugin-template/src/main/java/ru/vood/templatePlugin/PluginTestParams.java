package ru.vood.templatePlugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.junit.Assert;
import ru.vood.templatePlugin.generated.fromXSD.ClassFromTablesType;
import ru.vood.templatePlugin.generated.fromXSD.PluginTines;

import javax.xml.bind.JAXBException;
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
        try {
            pluginParamsXml = new PluginParamsXml();
            Files.lines(Paths.get(filename), StandardCharsets.UTF_8).forEach(sb::append);
            System.out.println("------>\n" + sb + "\n");
            final PluginTines pluginTines = pluginParamsXml.xmlToObj(sb.toString());
            System.out.println("pluginTines------>" + toString(pluginTines));
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }

    }

    public String toString(PluginTines pluginTines) {
        List<ClassFromTablesType> lists = pluginTines.getTableClassList().getClassFromTables();
        String s = lists.stream()
                .map(classTablesType -> "class=" + classTablesType.getGeneratingClass() + " TableList=" + classTablesType.getTableList())
                .reduce((o, o2) -> o + "\n" + o2)
                .orElse("");

        return "PluginTines{" +
                "username='" + pluginTines.getUsername() + '\'' +
                ", password='" + pluginTines.getPassword() + '\'' +
                ", tableClassList=" + s +
                '}';
    }


}
