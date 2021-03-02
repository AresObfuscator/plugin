package com.arkoisystems.obfuscator;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.RegularFile;
import org.gradle.api.provider.Provider;
import org.gradle.jvm.tasks.Jar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ObfuscatorPlugin implements Plugin<Project>
{
    
    @Override
    public void apply(final Project project) {
       final ObfuscatorExtension extension = project.getExtensions().create("obfuscator", ObfuscatorExtension.class);
       
       final Task obfuscateTask = project.getTasks().create("obfuscate");
       final Jar jarTask = (Jar) project.getTasks().getByName("jar");
       
       obfuscateTask.dependsOn(jarTask);
       obfuscateTask.doLast(task -> {
           final Provider<RegularFile> jarProvider = jarTask.getArchiveFile();
           final RegularFile regularFile = jarProvider.getOrNull();
           if(regularFile == null) {
               System.err.println("Couldn't find the target file to obfuscate it.");
               System.exit(1);
           }
           
           final File inputFile = extension.getInputFile() != null
                   ? extension.getInputFile()
                   : regularFile.getAsFile();
           final File outputFile = extension.getOutputFile() != null
                   ? extension.getOutputFile()
                   : inputFile;
           final List<File> inputLibraries = extension.getLibraries() != null
                   ? extension.getLibraries()
                   : new ArrayList<>(project.getConfigurations().getByName("runtime").resolve());
           
           System.out.println(inputFile);
           System.out.println(outputFile);
           System.out.println(inputLibraries);
       });
    }
    
}
