package ru.realityfamily.firstapp.Models;

import java.util.ArrayList;

public class Service extends Thread {
    Automat automat;

    public Service(Automat automat) {
        this.automat = automat;
    }

    @Override
    public void run() {
        synchronized (automat){
            automat.addingProducts(new ArrayList<>());
        }
    }
}
