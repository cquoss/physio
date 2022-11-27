package de.quoss.physio.publicprescriptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    
    private static final Properties PROPERTIES = new Properties();
    
    static {
        try {
            PROPERTIES.load(Main.class.getClassLoader().getResourceAsStream(Main.class.getName() + ".properties"));
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
    }
    
    private void run(final String[] args) {
        if (args.length != 1) {
            throw new PublicPrescriptionsException(usage());
        }
        if ("check-users".equals(args[0])) {
            checkUsers();
        } else {
            throw new PublicPrescriptionsException("Unknown function: " + args[0]);
        }
    }
    
    private void checkUsers() {
        final String methodName = "checkUsers()";
        LOGGER.trace("{} start", methodName);
        // start in dir provided
        final String startDir = getProperty("startDir", null);
        final File[] files = new File(startDir).listFiles();
        final Set<String> scanDirs;
        if (files == null) {
            scanDirs = new HashSet<>();
        } else {
            scanDirs = Stream.of(files).filter(file -> file.isDirectory() && file.getName().startsWith("Scan_")).map(File::getName).collect(Collectors.toSet());
        }
        LOGGER.debug("{} [scan-dirs={}]", methodName, scanDirs);
        // iterate over files in scan dirs
        for (String dir : scanDirs) {
            final File f = new File (startDir, dir);
            final File[] files2 = f.listFiles();
            final Set<String> pdfFiles;
            if (files2 == null) {
                pdfFiles = new HashSet<>();
            } else {
                pdfFiles = Stream.of(files2).filter(file -> file.isFile() && file.getName().endsWith(".pdf")).map(File::getName).collect(Collectors.toSet());
            }
            LOGGER.debug("{} [pdf-files={}]", methodName, pdfFiles);
        }
        LOGGER.trace("{} end", methodName);
    }
    
    private String getProperty(final String key, final String defaultValue) {
        final String fullKey = getClass().getName() + "." + key;
        String result = System.getProperty(fullKey);
        if (result == null) {
            result = PROPERTIES.getProperty(fullKey);
        }
        if (result == null) {
            if (defaultValue == null) {
                throw new PublicPrescriptionsException("Mandatory property not provided: " + fullKey);
            } else {
                result = defaultValue;
            }
        }
        return result;
    }
    
    private String usage() {
        return String.format("USAGE: java %s function", getClass().getName());
    }
    
    public static void main(final String[] args) {
        new Main().run(args);
    }
    
}
