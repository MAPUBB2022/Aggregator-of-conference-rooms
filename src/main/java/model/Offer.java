package model;

import javax.swing.*;

public class Offer extends Message{

    private Integer price;


    public Offer(Ad ad, String startingDate, String endingDate, Integer guests, String description, Integer price) {
        super(ad, startingDate, endingDate, guests, description);
        this.price = price;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
