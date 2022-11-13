package model;

public class Message {

    private Ad ad;

    private final Integer idMessage;
    private static Integer counter = 1;
    private String description;
    private String startingDate;
    private String endingDate;
    private Integer guests;

    private Status status;

    public Message(Ad ad, String startingDate, String endingDate, Integer guests, String description) {
        this.idMessage = counter++;
        this.ad = ad;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.guests = guests;
        this.description = description;
        this.status = null;
    }

    public Integer getIdMessage() {
        return idMessage;
    }

    public Ad getAd() {
        return ad;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getGuests() {
        return guests;
    }

    public void setGuests(Integer guests) {
        this.guests = guests;
    }

}
