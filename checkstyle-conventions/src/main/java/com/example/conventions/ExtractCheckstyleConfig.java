package com.example.conventions;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.gradle.api.DefaultTask;
import org.gradle.api.UncheckedIOException;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;

/** Writes the Checkstyle config files bundled in the plugin jar to a directory. */
public abstract class ExtractCheckstyleConfig extends DefaultTask {

    private static final List<String> FILES = List.of("checkstyle.xml", "suppressions.xml");

    @Input
    public List<String> getFiles() {
        return FILES;
    }

    @OutputDirectory
    public abstract DirectoryProperty getOutputDir();

    @TaskAction
    public void extract() {
        Path dir = getOutputDir().get().getAsFile().toPath();
        for (String name : FILES) {
            try (InputStream in = getClass().getResourceAsStream("/checkstyle/" + name)) {
                if (in == null) {
                    throw new IllegalStateException("Missing resource /checkstyle/" + name);
                }
                Files.createDirectories(dir);
                Files.copy(in, dir.resolve(name), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }
}
