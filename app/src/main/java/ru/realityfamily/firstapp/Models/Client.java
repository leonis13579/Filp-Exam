package ru.realityfamily.firstapp.Models;

import android.os.AsyncTask;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ru.realityfamily.firstapp.MainActivity;

public abstract class Client extends AsyncTask<Void, Client, Void> {
    private String name;
    private int productCount;
    private List<Product> productCart = new ArrayList<>();
    private Automat automat;

    public Client(int id, int productCount, Automat automat) {
        this.name = "Покупатель " + id;
        this.productCount = productCount;
        this.automat = automat;
    }

    public String getClientName() {
        return name;
    }

    public boolean hasProductsChoosen(){
        return productCart.size() == productCount;
    }

    public void chooseProduct() {
        List<Product> products = automat.getProductList();

        Product product = products.get(new Random().nextInt(products.size()));
        if (automat.hasProduct(product)) {
            productCart.add(automat.getProduct(product));
        }
    }

    public Automat getAutomat() {
        return automat;
    }

    public String getBill() {
        String bill = "";
        double billCost = 0;

        for (Product product : productCart) {
            billCost += product.getCost();
            bill += product.getName() + "\t\t\t" + product.getCost() + " у.е.\n";
        }

        bill += "Итоговая стоимость: " + billCost + " у.е.";
        return bill;
    }
}
