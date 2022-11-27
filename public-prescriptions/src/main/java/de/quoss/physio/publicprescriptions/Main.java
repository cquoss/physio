package de.quoss.physio.publicprescriptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

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
        
    }
    
    private String usage() {
        return String.format("USAGE: ");
    }
    
    public static void main(final String[] args) {
        new Main().run(args);
    }
    
}
