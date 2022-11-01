package model;

import interfaces.ICrudRepositoryInterface;

import java.util.ArrayList;

public class BusinessOwner extends User implements ICrudRepositoryInterface<Ad, Integer> {
    private ArrayList<Ad> ads;
    private ArrayList<Offer> receivedOffers;
    private ArrayList<Offer> acceptedOffers;

    public BusinessOwner(String firstName, String lastName, String username, String password) {
        super(firstName, lastName, username, password);
        this.ads = new ArrayList<>();
        this.receivedOffers = new ArrayList<>();
        this.acceptedOffers = new ArrayList<>();
    }

    public ArrayList<Ad> getAds() {
        return ads;
    }

    public void setAds(ArrayList<Ad> ads) {
        this.ads = ads;
    }

    public ArrayList<Offer> getReceivedOffers() {
        return receivedOffers;
    }

    public void setReceivedOffers(ArrayList<Offer> receivedOffers) {
        this.receivedOffers = receivedOffers;
    }

    public ArrayList<Offer> getAcceptedOffers() {
        return acceptedOffers;
    }

    public void setAcceptedOffers(ArrayList<Offer> acceptedOffers) {
        this.acceptedOffers = acceptedOffers;
    }

    @Override
    public void add(Ad entity){
        for(Ad ad: this.ads){
            if(ad.getIdAd().equals(entity.getIdAd())){
                return;
            }
        }
        this.ads.add(entity);
    }

    @Override
    public void remove(Integer id){
        this.ads.removeIf(ad -> ad.getIdAd().equals(id));
    }

    @Override
    public void update(Integer id, Ad new_ad) {
        for( Ad ad : this.ads) {
            if(ad.getIdAd().equals(id)) {
                ad.setIdAd(new_ad.getIdAd());
                ad.setProduct(new_ad.getProduct());
                ad.setImages(new_ad.getImages());
                ad.setCalendar(new_ad.getCalendar());
                break;
            }
        }
    }



    public void acceptOffer(){

    }


}
