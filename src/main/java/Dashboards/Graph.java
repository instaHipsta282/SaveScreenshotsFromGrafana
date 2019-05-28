package Dashboards;

import net.minidev.json.JSONArray;

class Graph {
    private String id;
    private String title;
    private String transaction;
    private String type;
    public Graph(JSONArray id, JSONArray title, JSONArray type, JSONArray transaction) {
        this.id = id.get(0).toString();
        this.title = title.get(0).toString();
        this.transaction = convertTransaction(transaction);
        this.type = convertType(type);
    }
    private String convertTransaction(JSONArray transaction) {
        String result = null;
        if (transaction.size() != 0) result = transaction.get(0).toString();
        return result;
    }
    private String convertType(JSONArray type) {
        String result = null;
        if (type.size() != 0) result = type.get(0).toString();
        return result;
    }

    @Override
    public String toString() {
        return "Graph{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", transaction='" + transaction + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getTransaction() { return transaction; }
    public void setTitle(String title) { this.title = title; }

}
