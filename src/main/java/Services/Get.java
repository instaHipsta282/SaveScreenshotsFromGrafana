package Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class Get {
    private static HttpURLConnection con;

    public static String send(String url, Map<String, String> headers) {
        String response = null;
        try {
            URL myURL = new URL(url);
            con = (HttpURLConnection) myURL.openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> entry : headers.entrySet()) {
                con.setRequestProperty(entry.getKey(), entry.getValue());
            }
            response = getResponse(con);
        }
        catch (IOException e) { e.printStackTrace(); }
        finally { con.disconnect(); }
        return response;
    }
    private static String getResponse(HttpURLConnection con) {
        StringBuilder content = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        }
        catch (IOException e) { e.printStackTrace(); }
//        System.out.println(content.toString());
        return content.toString();
    }
}
