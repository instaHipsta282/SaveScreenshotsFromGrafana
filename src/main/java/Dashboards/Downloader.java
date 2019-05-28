package Dashboards;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static Properties.PropertyLoader.*;

public class Downloader{

    public static void lol() {
        for(Dashboard dash : DashboardLoader.list) {
            File newPackage = new File( getResultPackage() + dash.title);
            boolean ignore = newPackage.mkdir();
            ExecutorService executor = Executors.newFixedThreadPool(10);
            for(Graph graph : dash.graphs) {
                String newURL = getURL() + "render/d-solo/" + dash.id + "/"
                        + dash.title.replaceAll(" ", "_")
                        + "?orgId=1&from=" + Services.Time.getMilliseconds(getDateFrom())
                        + "&to=" + Services.Time.getMilliseconds(getDateTo()) + "&panelId="
                        + graph.getId() + "&width=" + getWidth()
                        + "&height=" + getHeight() + "&tz=Europe%2FMoscow";
                System.out.println(newURL);

                if (graph.getTransaction() != null) {
                    graph.setTitle(graph.getTitle().replace("$transaction", graph.getTransaction()));
                    File transactionPackage = new File(newPackage.getPath() + "/" + graph.getTransaction());
                    if(!transactionPackage.exists()) ignore = transactionPackage.mkdir();
                    File image = new File(transactionPackage.getPath() + "\\" + graph.getTitle()
                            + "(" +graph.getId() + ")" + ".jpeg");

                    executor.submit(new GetImage(newURL, image.getPath()));
                }
                else {
                    File image = new File(newPackage.getPath() + "\\"
                            + graph.getTitle() + graph.getId() + ".jpeg");
                    executor.submit(new GetImage(newURL, image.getPath()));
                }
            }
            executor.shutdown();
        }
    }
}
