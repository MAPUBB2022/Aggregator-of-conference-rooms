package model;

import java.util.ArrayList;
public class Ad {

    private static Integer counter = 1; //counter static -> pt a incrementa ori de cat ori apelam clasa, nu pt a reseta counter ul la apelul de mai multe ori (caz// cu o variab normala)
    private final Integer idAd;
    private Product product;
    private Calendar calendar;

    public Ad(Product product, Calendar calendar) {
        this.idAd = counter++; //pt a avea un id unic/diferit de fiecare data cand creez un obj
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
