package ru.spbau.qrmenu.entities;

public class RestaurantMenuItem {

    private final String myName;
    private final double myCost;

    public RestaurantMenuItem(String name, double cost) {
        this.myName = name;
        this.myCost = cost;
    }

    public String getName() {
        return myName;
    }

    public double getCost() {
        return myCost;
    }

}
