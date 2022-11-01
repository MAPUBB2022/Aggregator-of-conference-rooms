package model;

import java.util.ArrayList;
import java.util.Date;

public class Offer {
    private Integer idOffer;

    private Date startingDate;

    private Date endingDate;

    private String description;

    private ArrayList<Ad> adsInOffer;

    public Offer(Integer idOffer, Date startingDate, Date endingDate, String description, ArrayList<Ad> adsInOffer) {
        this.idOffer = idOffer;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.description = description;
        this.adsInOffer = adsInOffer;
    }

    public Integer getIdOffer() {
        return idOffer;
    }

    public void setIdOffer(int idOffer) {
        this.idOffer = idOffer;
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    public Date getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(Date endingDate) {
        this.endingDate = endingDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Ad> getAdsInOffer() {
        return adsInOffer;
    }

    public void setAdsInOffer(ArrayList<Ad> adsInOffer) {
        this.adsInOffer = adsInOffer;
    }
}
