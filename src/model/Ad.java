package model;

import java.util.ArrayList;
public class Ad {

    private Integer idAd;
    private Product product;
    private Calendar calendar;

    public Ad(Integer id,Product product, Calendar calendar) {
        this.idAd = id;
        this.product = product;
        this.calendar = calendar;
    }

    public Integer getIdAd() {
        return idAd;
    }

    public void setIdAd(Integer idAd) {
        this.idAd = idAd;
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
