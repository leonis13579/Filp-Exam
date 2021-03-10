package ru.realityfamily.firstapp.Models;

public class Product implements Comparable<Product> {
    private String name;
    private double cost;

    public Product(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public int compareTo(Product o) {
        return name.compareTo(o.getName());
    }
}
