package ru.spbau.qrmenu.entities;

public class RestaurantTable {

    private String myRestaurantName;
    private String myUrl;
    private int myTableId;

    public RestaurantTable(String restaurantName, String url, int tableId) {
        this.myRestaurantName = restaurantName;
        this.myUrl = url;
        this.myTableId = tableId;
    }

    public String getRestaurantName() {
        return myRestaurantName;
    }

    public String getUrl() {
        return myUrl;
    }

    public int getTableId() {
        return myTableId;
    }

}
