package model;

import java.util.ArrayList;
public class Ad {

    private static Integer counter = 1;
    private final Integer idAd;
    private Product product;
    private Calendar calendar;

    public Ad(Product product, Calendar calendar) {
        this.idAd = counter++;
        this.product = product;
        this.calendar = calendar;
    }

    public Integer getIdAd() {
        return idAd;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

}
