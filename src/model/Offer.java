package model;

import java.util.ArrayList;
import java.util.Date;

public class Offer {
    private final Integer idOffer;
    private static Integer counter = 1;

    private String startingDate;

    private String endingDate;

    private String description;

    private Ad adInOffer;

    public Offer(String startingDate, String  endingDate, String description, Ad adInOffer) {
        this.idOffer = counter++;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.description = description;
        this.adInOffer = adInOffer;
    }

    public Integer getIdOffer() {
        return idOffer;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    public String getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(String endingDate) {
        this.endingDate = endingDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Ad getAdInOffer() {
        return adInOffer;
    }

    public void setAdInOffer(Ad adInOffer) {
        this.adInOffer = adInOffer;
    }
}
