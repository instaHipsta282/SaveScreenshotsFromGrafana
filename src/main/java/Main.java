import Dashboards.DashboardLoader;
import Dashboards.Downloader;
import Properties.PropertyLoader;

class Main {
    public static void main(String[] args) {
        PropertyLoader.init();
        DashboardLoader.init();
        for (int i = 0; i < DashboardLoader.list.size(); i++) {
            Downloader.lol();
        }
    }
}
