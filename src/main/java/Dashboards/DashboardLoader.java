package Dashboards;

import Properties.PropertyLoader;
import Services.Get;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import net.minidev.json.JSONArray;

import java.util.*;

public class DashboardLoader {
        public static volatile ArrayList<Dashboard> list = new ArrayList<>();
    public static void init() {
        String api = "api/dashboards/uid/";
        String token = PropertyLoader.getToken();
        String MyURL = PropertyLoader.getURL() + "/";
        String sendInterval = PropertyLoader.getSendInterval();
        List<String> dashboardIDList = PropertyLoader.getDashboardIDList();
        ArrayList<Graph> graphs = new ArrayList<>();
        String response;
        LinkedHashMap graphJson;
        boolean isEnd = false;
        int count = 0;
        String dashboardName;
        String dashboardId;
        for(String s : dashboardIDList) {
            String url = MyURL + api + s + "?" + sendInterval;
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + token);
            response = Get.send(url, headers);
            dashboardName = JsonPath.read(response, "$.dashboard.title");
            dashboardId = s;
            while (!isEnd) {
                try {
                    graphJson = JsonPath.read(response, "$.dashboard.panels[" + count + "]");
                    JSONArray id = JsonPath.read(graphJson, "$..id");
                    JSONArray title = JsonPath.read(graphJson, "$..title");
                    JSONArray type = JsonPath.read(graphJson, "$.[?(@.targets)].type");
                    JSONArray transactionName = JsonPath.read(graphJson, "$..scopedVars.transaction.value");
                    if (type.size() != 0) {
                        Graph graph = new Graph(id, title, type, transactionName);
                        graphs.add(graph);
                    }
                    count++;
                }
                catch (PathNotFoundException e) { isEnd = true; }
            }
            Dashboard dashboard = new Dashboard(dashboardId, dashboardName, graphs);
            list.add(dashboard);
        }
    }
}