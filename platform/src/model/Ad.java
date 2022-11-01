package model;

import java.awt.*;
import java.util.ArrayList;
public class Ad {

    private Integer idAd;
    private Product product;
    private ArrayList<Image> images;
    private Calendar calendar;

    public Ad(Integer id,Product product, Calendar calendar) {
        this.idAd = id;
        this.product = product;
        this.images = new ArrayList<>();
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

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

}
