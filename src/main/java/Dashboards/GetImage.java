package Dashboards;

import Properties.PropertyLoader;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

class GetImage implements Runnable{
    private String newURL;
    private String image;
    public GetImage(String newURL, String image) {
        this.newURL = newURL;
        this.image = image;
    }
    private static void download(String newURL, String image) {
        HttpURLConnection con = null;
        String token = PropertyLoader.getToken();
        try {
            URL url = new URL(newURL);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "Bearer " + token);
        }
        catch (IOException e) { e.printStackTrace(); }
        finally {
            if (con != null) con.disconnect();
        }
        if (con != null) {
            try (BufferedInputStream inputStream = new BufferedInputStream(con.getInputStream());
                 FileOutputStream outputStream = new FileOutputStream(image)) {
                byte[] buffer = new byte[1024];
                int count;
                while ((count = inputStream.read(buffer, 0, 1024)) != -1) {
                    outputStream.write(buffer, 0, count);
                }
            }
            catch (IOException e) { e.printStackTrace(); }
        }
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " start");
        download(newURL, image);
        System.out.println(Thread.currentThread().getName() + " end");
    }
}
