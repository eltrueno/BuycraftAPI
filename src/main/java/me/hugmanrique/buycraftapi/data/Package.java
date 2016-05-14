package me.hugmanrique.buycraftapi.data;

/**
 * Created by HugmanriqueMC. All Rights Reserved
 * The copy of this file may not be copied in any form without
 * the prior written permission of Hugo Manrique.
 *
 * @author Hugmanrique
 *         Spigot. Created the 14/05/2016.
 **/
public class Package {
    private int id;
    private int order;
    private String name;
    private double price;

    private boolean inSale;
    private double discount;

    public Package(int id, int order, String name, double price, boolean inSale, Double discount) {
        this.id = id;
        this.order = order;
        this.name = name;
        this.price = price;
        this.inSale = inSale;
        if (inSale){
            this.discount = discount;
        }
    }

    public int getId() {
        return id;
    }

    public int getOrder() {
        return order;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public boolean isInSale() {
        return inSale;
    }

    public double getDiscount() {
        return discount;
    }
}
