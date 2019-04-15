package ru.vood.maven.plugin.enumFromTable;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "genEnum", defaultPhase = LifecyclePhase.GENERATE_SOURCES, threadSafe = true)
public class EnumGenerator extends AbstractMojo {
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

    }
}
