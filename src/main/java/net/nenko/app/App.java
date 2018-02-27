package net.nenko.app;

import static java.lang.System.exit;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final String APP_NAME = "net.nenko.app.App - simple command line Java utility - starter template";
    private static final String APP_PROPERTIES = "/app.properties";

    // JDBC part
    private String jdbcUrl;
    private String jdbcUser;
    private String jdbcPass;
    private String jdbcDrvr;
    private JdbcBase jdbc;
    private long index = 0;
    
    public static void main( String[] args )
    {
        String externalPropertiesFile = null;
        print('\n' + APP_NAME + '\n');
        switch(args.length) {
        	case 1:
        		break;
        	case 2:
        		externalPropertiesFile = args[1];
        		break;
        	default:
        		print("ERROR: wrong # of arguments. Must be 1 or 2 arguments.");
        		exit(1);
        }

		App app = new App(externalPropertiesFile);
	    if(args[0].toLowerCase().equals("insert")) {
	        app.insert();
	    } else if(args[0].toLowerCase().equals("select")) {
	        app.select();
	    } else if(args[0].toLowerCase().equals("update")) {
	        app.update();
	    } else {
	        print("ERROR: 1st argument = mode. Must be 'select' or 'udpate' or 'insert'.");
	        exit(1);
	    }
	
	    print("Exiting successfully.");
	    exit(0);
    }

    private App(String propertiesFile) {

    	// Load available properties from internal resource
        try (InputStream in = getClass().getResourceAsStream(APP_PROPERTIES)) {
            Properties properties = new Properties();
            properties.load(in);
            assignProperties(properties);
        } catch(IOException e) {
            print("ERROR: JAR must include configuration data: " + APP_PROPERTIES);
            exit(1);
        }

        // If external file specified, it is read and override properties
    	if(propertiesFile != null) {
    		try (InputStream inputStream = new FileInputStream(propertiesFile)) {
	            Properties properties = new Properties();
	            properties.load(inputStream);
	            assignProperties(properties);		// override properties from resource
    		} catch (FileNotFoundException e) {
            	print("ERROR: properties file '" + propertiesFile + "' not found in the classpath.");
    		} catch (Exception e) {
            	print("ERROR: Exception: " + e);
    		}
    	}

        print("Utility initialized to run with following configuration:"
                + "\n\tdatabase url: " + jdbcUrl
                + "\n\tdatabase user: " + jdbcUser
                + "\n\tdatabase pass: not printed"
                + "\n\tdatabase driver: " + jdbcDrvr);
        jdbc = new JdbcClob(jdbcUrl, jdbcUser, jdbcPass, jdbcDrvr);
    }

    private void insert() {
        long msStart = System.currentTimeMillis();
        print("Inserting ...");
        jdbc.insert(new ObjectToStore(index ++));
        print("Done in :" + (System.currentTimeMillis() - msStart) + " ms.");
    }

    private void select() {
        print("Selecting ...");
        List<ObjectToStore> objs = jdbc.select();
        print("Done. " + objs.size() + " keys found.");
    }

    private void update() {
        print("Updating ...");
        jdbc.update("ae6ef203-c926-48c6-a201-3a873aabbffa", "updated data");
        print("Done");
    }

    private void assignProperties(Properties properties) {
    	String value = properties.getProperty("jdbc-url");
    	if(value != null) {
    		jdbcUrl = value;
    	}
    	value = properties.getProperty("jdbc-user");
    	if(value != null) {
    		jdbcUser = value;
    	}
    	value = properties.getProperty("jdbc-pass");
    	if(value != null) {
    		jdbcPass = value;
    	}
    	value = properties.getProperty("jdbc-drvr");
    	if(value != null) {
    		jdbcDrvr = value;
    	}
    }

    private static void print(String msg) {
        System.out.println(msg);
    }

}
