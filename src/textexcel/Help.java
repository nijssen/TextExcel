package textexcel;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

class Help {
    private static Properties helpTopics;
    private static boolean isInitialized = false;

    public static void listTopics() throws IOException {
        init();
        
        System.out.println("Help is available for the following commands:");
        
        //Get list of commands I have text for
        for(String name : helpTopics.stringPropertyNames()) {
            System.out.println("\t" + name);
        }
    }

    public static void showHelpFor(String name) throws IOException {
        init();
        
        String helpText = helpTopics.getProperty(name, "No help text for this method is available.");
        System.out.println(helpText);
    }
    
    private static void init() throws IOException {
        if(isInitialized) return; //already done
        
        helpTopics = new Properties();
        helpTopics.load(new FileInputStream("helpmsg.properties")); //load helpmsg.properties
        
        isInitialized = true; //so we don't do it every time
    }

}
