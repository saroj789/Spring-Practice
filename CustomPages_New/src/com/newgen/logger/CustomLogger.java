package com.newgen.logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;

import com.newgen.bean.Constants;

public final class CustomLogger {

    private static org.apache.log4j.Logger errorLogger = null;
    private static org.apache.log4j.Logger consoleLogger = null;
    private static org.apache.log4j.Logger transactionLogger = null;
    private static org.apache.log4j.Logger xmlLogger = null;
    private static String logPath = null;
    
    private static boolean initComplete = true;
    		
    static {
        logPath ="CustomPages";
        try {
            File file = null;
            file = new File(logPath);
            if (!file.exists()) {
                file.mkdir();
            }
            loadConfiguration(System.getProperty("user.dir")+File.separator+logPath +File.separator+"Customlog4j.properties");
            initComplete = true;
        } catch (Exception ex) {
            initComplete = false;
        }
        errorLogger = org.apache.log4j.Logger.getLogger("errorLogger");
        consoleLogger = org.apache.log4j.Logger.getLogger("consoleLogger");
        xmlLogger = org.apache.log4j.Logger.getLogger("XMLLogger");
        transactionLogger = org.apache.log4j.Logger.getLogger("transactionLogger");
    }

    public static boolean getInitializationStatus(){
        return initComplete;
    }

   
    private CustomLogger() {
    }


    
    private static void loadConfiguration(String propPath) {
    	System.out.println("propPath..."+propPath);
        Properties props = new Properties();
        PropertyConfigurator propertyConfigurator = null;
        propertyConfigurator = new PropertyConfigurator();
        try(FileInputStream fIn = new FileInputStream(propPath)) { 
            props.load(fIn);
            props = loadProperties(props);
        } catch (IOException ioe) {
            props = loadProperties();
        } 
        propertyConfigurator.doConfigure(props, LogManager.getLoggerRepository());
    }

    
    private static Properties loadProperties(Properties prop) {
        Properties props = prop;
        StringTokenizer tokenizer = null;
        @SuppressWarnings("rawtypes")
		Enumeration keys = null;
        String key = "";
        String value = "";
        String token = "";

        for (keys = props.keys(); keys.hasMoreElements();) {
            key = ((String) keys.nextElement()).trim();
            if (key.startsWith("log4j.logger.")) {
                value = props.getProperty(key);
                tokenizer = new StringTokenizer(value, ",");
                while (tokenizer.hasMoreTokens()) {
                    token = tokenizer.nextToken();
                    key = "log4j.appender." + token.trim() + ".File";
                    value = props.getProperty(key);
                    if (value != null) {
                        props.setProperty(key, logPath + File.separator + value);
                    }
                }
            }
        }
        return props;
    }

    
    private static Properties loadProperties() {
        Properties prop = new Properties();
        /* Error.log */
        prop.setProperty("log4j.logger.errorLogger", "debug, E");
        prop.setProperty("log4j.additivity.errorLogger", Constants.FALSESTRING);
        prop.setProperty("log4j.appender.E", Constants.ROLLINGAPPPROP);
        prop.setProperty("log4j.appender.E.File", logPath + File.separator + "Error.log");
        prop.setProperty("log4j.appender.E.MaxBackupIndex", "10");
        prop.setProperty("log4j.appender.E.MaxFileSize", "5MB");
        prop.setProperty("log4j.appender.E.layout", Constants.PATTERNLAYOUTPROP);
        prop.setProperty("log4j.appender.E.layout.ConversionPattern", Constants.CONVERSIONPATTERN);

        /* Console.log */
        prop.setProperty("log4j.logger.consoleLogger", "debug, C");
        prop.setProperty("log4j.additivity.consoleLogger", Constants.FALSESTRING);
        prop.setProperty("log4j.appender.C", Constants.ROLLINGAPPPROP);
        prop.setProperty("log4j.appender.C.File", logPath + File.separator + "Console.log");
        prop.setProperty("log4j.appender.C.MaxBackupIndex", "10");
        prop.setProperty("log4j.appender.C.MaxFileSize", "5MB");
        prop.setProperty("log4j.appender.C.layout", Constants.PATTERNLAYOUTPROP);
        prop.setProperty("log4j.appender.C.layout.ConversionPattern", Constants.CONVERSIONPATTERN);

        /* Transaction.log */
        prop.setProperty("log4j.logger.transactionLogger", "debug, T");
        prop.setProperty("log4j.additivity.transactionLogger", Constants.FALSESTRING);
        prop.setProperty("log4j.appender.T", Constants.ROLLINGAPPPROP);
        prop.setProperty("log4j.appender.T.File", logPath + File.separator + "Transaction.log");
        prop.setProperty("log4j.appender.T.MaxBackupIndex", "10");
        prop.setProperty("log4j.appender.T.MaxFileSize", "5MB");
        prop.setProperty("log4j.appender.T.layout", Constants.PATTERNLAYOUTPROP);
        prop.setProperty("log4j.appender.T.layout.ConversionPattern", "[%d{dd MMM yyyy HH:mm:ss,SSS} : %-5p] - %m%n");

        /* XML.log */
        prop.setProperty("log4j.logger.XMLLogger", "debug, X");
        prop.setProperty("log4j.additivity.XMLLogger", Constants.FALSESTRING);
        prop.setProperty("log4j.appender.X", Constants.ROLLINGAPPPROP);
        prop.setProperty("log4j.appender.X.File", logPath + File.separator + "XML.log");
        prop.setProperty("log4j.appender.X.MaxBackupIndex", "10");
        prop.setProperty("log4j.appender.X.MaxFileSize", "5MB");
        prop.setProperty("log4j.appender.X.layout", Constants.PATTERNLAYOUTPROP);
        prop.setProperty("log4j.appender.X.layout.ConversionPattern", Constants.CONVERSIONPATTERN);
        return prop;
    }

    public static void printErr(Object message) {
        errorLogger.debug(message);
    }

    public static void printErr(Throwable message) {
        printErr("", message);
    }

    public static void printErr(Object message, Throwable t) {
        errorLogger.debug(message, t);
    }

    
    public static void printErr(Level level, Object message, Throwable t) {
        errorLogger.log(level, message, t);
    }

    
    public static void printOut(Object message) {
        consoleLogger.debug(message);
    }

    
    public static void printOut(Level level, Object message) {
        consoleLogger.log(level, message);
    }

    
    public static void writeLog(char type, StringBuilder message) {
        writeLog(type, message.toString());
    }

    public static void writeXML(String message){
        writeLog('X', message);
    }

    public static void writeTxn(String message){
        writeLog('T', message);
    }

    public static void writeTxn(String api, long duration, String processInstanceId){
        writeTxn("API : " + api + " : " + duration + " PId : " + processInstanceId);
    }

    public static void writeLog(char type, String message) {
        switch (type) {
            case 't':
            case 'T':
                CustomLogger.transactionLogger.debug(message);
                break;
            case 'x':
            case 'X':
                CustomLogger.xmlLogger.debug(message);
                break;
            default:
        }
    }

   
    public static boolean isLogEnabled(char logType, Level level) {
        switch (logType) {
            case 't':
            case 'T':
                return transactionLogger.isEnabledFor(level);

            case 'x':
            case 'X':
                return xmlLogger.isEnabledFor(level);

            case 'c':
            case 'C':
                return consoleLogger.isEnabledFor(level);

            case 'e':
            case 'E':
                return errorLogger.isEnabledFor(level);

            default:
                return false;
        }
    }

    
    public static void setLogLevel(char logType, String level) {
        switch (logType) {
            case 't':
            case 'T':
                transactionLogger.setLevel(Level.toLevel(level, Level.DEBUG));
                printOut(Level.toLevel(level, Level.WARN), "Changed transaction Logs level to " + Level.toLevel(level, Level.DEBUG));
                break;

            case 'x':
            case 'X':
                xmlLogger.setLevel(Level.toLevel(level, Level.DEBUG));
                printOut(Level.toLevel(level, Level.WARN), "Changed XML Logs level to " + Level.toLevel(level, Level.DEBUG));
                break;

            case 'c':
            case 'C':
                consoleLogger.setLevel(Level.toLevel(level, Level.INFO));
                printOut(Level.toLevel(level, Level.WARN), "Changed console Logs level to " + Level.toLevel(level, Level.INFO));
                break;

            case 'e':
            case 'E':
                errorLogger.setLevel(Level.toLevel(level, Level.DEBUG));
                printOut(Level.toLevel(level, Level.WARN), "Changed error Logs level to " + Level.toLevel(level, Level.INFO));
                break;
            default:
        }
    }

    public static String getLogLevel(char logType) {
        switch (logType) {
            case 't':
            case 'T':
                return transactionLogger.getLevel().toString();

            case 'x':
            case 'X':
                return xmlLogger.getLevel().toString();

            case 'c':
            case 'C':
                return consoleLogger.getLevel().toString();

            case 'e':
            case 'E':
                return errorLogger.getLevel().toString();

            default:
                return null;
        }
    }
}
