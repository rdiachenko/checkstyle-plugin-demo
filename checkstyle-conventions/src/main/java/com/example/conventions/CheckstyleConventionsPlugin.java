package com.example.conventions;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.quality.Checkstyle;
import org.gradle.api.plugins.quality.CheckstyleExtension;
import org.gradle.api.tasks.TaskProvider;

/**
 * Applies Checkstyle to the project and points it at the config files
 * bundled inside this plugin's jar (src/main/resources/checkstyle/).
 * The consuming project needs no config/checkstyle directory.
 */
public class CheckstyleConventionsPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getPluginManager().apply("checkstyle");

        // Copies checkstyle.xml and suppressions.xml from the plugin classpath
        // into build/checkstyle-config so that ${config_loc} works as usual.
        TaskProvider<ExtractCheckstyleConfig> extract = project.getTasks().register(
                "extractCheckstyleConfig", ExtractCheckstyleConfig.class, task ->
                        task.getOutputDir().set(project.getLayout().getBuildDirectory().dir("checkstyle-config")));

        CheckstyleExtension checkstyle = project.getExtensions().getByType(CheckstyleExtension.class);
        checkstyle.setToolVersion("14.3.0");
        checkstyle.getConfigDirectory().set(extract.flatMap(ExtractCheckstyleConfig::getOutputDir));
        checkstyle.setConfig(project.getResources().getText()
                .fromFile(checkstyle.getConfigDirectory().file("checkstyle.xml")));

        project.getTasks().withType(Checkstyle.class).configureEach(task -> task.dependsOn(extract));
    }
}
