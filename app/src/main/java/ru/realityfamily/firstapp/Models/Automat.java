package ru.realityfamily.firstapp.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class Automat {
    public enum AutomatStatusType {
        came,
        choosing,
        paying,
        leaving,
        adding
    }


    private int id;
    private Map<Product, Integer> products = new TreeMap<>();
    private AutomatStatusType status;

    public Automat(int id) {
        this.id = id;

        List<Product> products = new ArrayList<>();
        products.add(new Product("Snikers", 10));
        products.add(new Product("Snikers", 10));
        products.add(new Product("Snikers", 10));
        products.add(new Product("Snikers", 10));

        products.add(new Product("Twiks", 30));
        products.add(new Product("Twiks", 30));
        products.add(new Product("Twiks", 30));
        products.add(new Product("Twiks", 30));
        products.add(new Product("Twiks", 30));

        products.add(new Product("Mars", 20));
        products.add(new Product("Mars", 20));
        products.add(new Product("Mars", 20));
        products.add(new Product("Mars", 20));
        addingProducts(products);
    }

    public int getId() {
        return id;
    }

    synchronized void addingProducts(List<Product> inProducts) {
        for (Product product : inProducts) {
            int count = products.getOrDefault(product, 0);
            products.put(product, count + 1);
        }
    }

    public List<Product> getProductList() {
        return new ArrayList<>(products.keySet());
    }

    public boolean hasProduct(Product product) {
        return products.get(product) > 0;
    }

    public Product getProduct(Product product) {
        products.put(product, products.get(product) - 1);
        return product;
    }

    public void setStatus(AutomatStatusType status) {
        this.status = status;
    }

    public String getStatus() {
        switch (status) {
            case came:
                return "Клиент подошел";
            case choosing:
                return "Кдиент выбирает";
            case paying:
                return "Клиент оплачивает";
            case leaving:
                return "Клиент уходит";
            case adding:
                return "Пополняется";
            default:
                return "";
        }
    }
}
