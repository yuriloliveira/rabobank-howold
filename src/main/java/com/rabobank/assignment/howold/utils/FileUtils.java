package com.rabobank.assignment.howold.utils;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class FileUtils {
    public static String getResourcesFileContent(String resourcesFilename, ClassLoader classLoader) throws IOException {
        InputStream is = classLoader.getResourceAsStream(resourcesFilename);
        return new String(is.readAllBytes(), StandardCharsets.UTF_8);
    }

    public static String getResourcesFileContent(String resourcesFilename) throws IOException {
        ClassLoader classLoader = FileUtils.class.getClassLoader();
        return getResourcesFileContent(resourcesFilename, classLoader);
    }
}
