package Dashboards;

import java.util.ArrayList;

class Dashboard {
    public String id;
    public String title;
    public ArrayList<Graph> graphs;
    public Dashboard(String id, String title, ArrayList<Graph> graphs) {
        this.id = id;
        this.title = title;
        this.graphs = graphs;
    }
}
