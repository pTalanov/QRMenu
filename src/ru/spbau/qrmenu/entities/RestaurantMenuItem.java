package ru.spbau.qrmenu.entities;

public class RestaurantMenuItem {

    private String myName;
    private double myCost;

    public String getName() {
        return myName;
    }

    public void setName(String name) {
        this.myName = name;
    }

    public double getCost() {
        return myCost;
    }

    public void setCost(double cost) {
        this.myCost = cost;
    }
}
