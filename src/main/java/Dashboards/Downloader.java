package Dashboards;

import Properties.PropertyLoader;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static Properties.PropertyLoader.*;

public class Downloader{

    public static void lol() {
        int numberOfThreads = PropertyLoader.getNumberOfThreads();
        for(Dashboard dash : DashboardLoader.list) {
            File newPackage = new File( getResultPackage() +
                    "/" + dash.title.replaceAll(" ", "_"));
            boolean ignore = newPackage.mkdir();
            ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
            for(Graph graph : dash.graphs) {
                String newURL = getURL() + "/render/d-solo/" + dash.id + "/"
                        + dash.title.replaceAll(" ", "_")
                        + "?orgId=1&from=" + Services.Time.getMilliseconds(getDateFrom())
                        + "&to=" + Services.Time.getMilliseconds(getDateTo()) + "&panelId="
                        + graph.getId() + "&width=" + getWidth()
                        + "&height=" + getHeight() + "&tz=Europe%2FMoscow";
                System.out.println(newURL);

                String path = newPackage.getPath();
                String title = graph.getTitle().replaceAll("/", "_");
                if (graph.getTransaction() != null) {
                    String transaction = graph.getTransaction().replaceAll(" ", "_")
                            .replaceAll("/", "_");
                    title = title.replace("$transaction", transaction);
                    path = path + "/" + transaction;
                    File transactionPackage = new File(path);
                    if (!transactionPackage.exists()) ignore = transactionPackage.mkdir();
                }
                File image = new File(path + "/" + title + "(" + graph.getId() + ").jpeg");
                executor.submit(new GetImage(newURL, image.getPath()));
            }
            executor.shutdown();
        }
    }
}
