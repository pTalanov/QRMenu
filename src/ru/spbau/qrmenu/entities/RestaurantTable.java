package ru.spbau.qrmenu.entities;

public class RestaurantTable {

    private String myRestaurantName;
    private String myUrl;
    private int myTableId;

    public String getRestaurantName() {
        return myRestaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.myRestaurantName = restaurantName;
    }

    public String getUrl() {
        return myUrl;
    }

    public void setUrl(String url) {
        this.myUrl = url;
    }

    public int getTableId() {
        return myTableId;
    }

    public void setTableId(int tableId) {
        this.myTableId = tableId;
    }
}
