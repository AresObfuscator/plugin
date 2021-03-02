package com.arkoisystems.obfuscator;

import lombok.Getter;
import lombok.Setter;
import org.gradle.api.tasks.Optional;

import java.io.File;
import java.util.List;

public class ObfuscatorExtension
{
    
    @Getter
    @Setter
    @Optional
    private File inputFile;
    
    @Getter
    @Setter
    @Optional
    private File outputFile;
    
    @Getter
    @Setter
    @Optional
    private List<File> libraries;

}
