
package org.sm.lab.mybooks.server;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AppProps {
    private static final Logger logger = Logger.getLogger(AppProps.class);

// Property names    
    private static final String APP_NAME = "app.name";

    private static final String APP_BACKUP_DIRECTORY_NAME = "app.backupDirectory";
    
    private static final String DB_PERSISTENCE_UNITNAME_NAME = "db.persistenceUnitName";
    private static final String DB_DRIVER_CLASSNAME_NAME = "db.driverClassName";
    private static final String DB_URL_NAME = "db.url";
    private static final String DB_USERNAME_NAME = "db.username";
    private static final String DB_PASSWORD_NAME = "db.password";
    
    private static Properties defaults = new Properties();
    private static Properties properties;
    
    
// Loading default values    
    static {
        defaults.put(APP_NAME, "My Books Application");

        defaults.put(APP_BACKUP_DIRECTORY_NAME, "./db/backup/");

        defaults.put(DB_PERSISTENCE_UNITNAME_NAME, "mybooks");
        defaults.put(DB_DRIVER_CLASSNAME_NAME, "org.hsqldb.jdbcDriver");
        defaults.put(DB_URL_NAME, "jdbc:hsqldb:hsql://localhost:9001/");
        defaults.put(DB_USERNAME_NAME, "sa");
        defaults.put(DB_PASSWORD_NAME, "");

        properties = new Properties(defaults);
    }

    private static File appProps = new File("app.properties");
    private static long lastModified = -1;

    private static Properties getProperties() {
        if (appProps.canRead()) {
            if (lastModified != appProps.lastModified()) {
                logger.debug("Checking for app properties file...");
                synchronized (defaults) {
                    // check again; we may have been waiting for the loading to complete!
                    if (lastModified != appProps.lastModified()) {
                        properties = new Properties(defaults);
                        try {
                            logger.info("Reloading properties");
                            properties.load(new FileInputStream(appProps));
                        } catch (IOException e) {
                            logger.error(e);
                        }
                        lastModified = appProps.lastModified();
                    }
                }
            }
        }

        return properties;
    }

    public static String getAppName() {
        return getProperties().getProperty(APP_NAME);
    }

    public static String getAppBackupDirectoryName() {
        return getProperties().getProperty(APP_BACKUP_DIRECTORY_NAME);
    }
    

    
    public static String getDbPersistenceUnit() {
        
        logger.debug(DB_PERSISTENCE_UNITNAME_NAME + "=" + getProperties().getProperty(DB_PERSISTENCE_UNITNAME_NAME));
        
        return getProperties().getProperty(DB_PERSISTENCE_UNITNAME_NAME);
    }
    
}
