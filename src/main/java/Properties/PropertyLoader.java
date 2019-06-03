package Properties;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class PropertyLoader {
    private static final String CONF_FILE_NAME = "config.properties";
    private static String token;
    private static String MyURL;
    private static String width;
    private static String height;
    private static String dateTo;
    private static String dateFrom;
    private static String resultPackage;
    private static List<String> dashboardIDList;
    private static String sendInterval;
    private static int numberOfThreads;
    public static void init() {
        Properties properties = new Properties();
        String line;
        StringBuilder builder = new StringBuilder();
        String result = "";
        try(BufferedReader reader = new BufferedReader(new FileReader(CONF_FILE_NAME))) {
            while ((line = reader.readLine()) != null) { builder.append(line).append("\n"); }
            result = builder.toString().replaceAll("\\\\", "/");
        }
        catch (IOException e) { e.printStackTrace(); }

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(CONF_FILE_NAME));
            FileInputStream inputStream = new FileInputStream(CONF_FILE_NAME)) {
            writer.write(result);
            writer.flush();
            properties.load(inputStream);
        }
        catch (IOException e) { e.printStackTrace(); }

        token = properties.getProperty("token");
        MyURL = properties.getProperty("URL");
        width = properties.getProperty("width");
        height = properties.getProperty("height");
        dateTo = properties.getProperty("dateTo");
        dateFrom = properties.getProperty("dateFrom");
        resultPackage = properties.getProperty("resultPackage");
        dashboardIDList = splitId(properties.getProperty("dashboardIDList"));
        sendInterval = properties.getProperty("sendInterval");
        numberOfThreads = Integer.parseInt(properties.getProperty("numberOfThreads"));
    }
    public static String getToken() { return token; }
    public static String getURL() { return MyURL; }
    public static String getWidth() { return width; }
    public static String getHeight() { return height; }
    public static String getDateTo() { return dateTo; }
    public static String getDateFrom() { return dateFrom; }
    public static String getSendInterval() { return sendInterval; }
    public static String getResultPackage() { return resultPackage; }
    public static List<String> getDashboardIDList() { return dashboardIDList; }
    public static int getNumberOfThreads() { return numberOfThreads; }

    private static List<String> splitId(String s) {
        List<String> list = new ArrayList<>();
        if (s.contains(",")) {
            list = Arrays.asList(s.split(","));
        }
        else { list.add(s); }
        return list;
    }

}

