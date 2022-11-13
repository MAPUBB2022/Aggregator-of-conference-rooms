package model;

import interfaces.ICrudRepositoryInterface;

import java.util.ArrayList;

public class BusinessOwner extends User {
    private ArrayList<Ad> ads;

    private ArrayList<Offer> sentOffers;

    public BusinessOwner(String firstName, String lastName, String username, String password) {
        super(firstName, lastName, username, password);
        this.ads = new ArrayList<>();
        this.sentOffers = new ArrayList<>();
    }

    public ArrayList<Ad> getAds() {
        return ads;
    }

    public ArrayList<Offer> getSentOffers() {
        return sentOffers;
    }

//    public ArrayList<Offer> getReceivedOffers() {
//        return receivedOffers;
//    }
//
//    public void setReceivedOffers(ArrayList<Offer> receivedOffers) {
//        this.receivedOffers = receivedOffers;
//    }
//
//    public ArrayList<Offer> getAcceptedOffers() {
//        return acceptedOffers;
//    }
//
//    public void setAcceptedOffers(ArrayList<Offer> acceptedOffers) {
//        this.acceptedOffers = acceptedOffers;
//    }

//    @Override
//    public void add(Ad newAd){
//        for(Ad ad: this.ads){
//            if(ad.getIdAd().equals(newAd.getIdAd())){
//                return;
//            }
//        }
//        this.ads.add(newAd);
//    }
//
//    //sterge dupa id un anunt din lista de anunturi
//    @Override
//    public void remove(Integer idAd){
//
//        this.ads.removeIf(ad -> ad.getIdAd().equals(idAd));
//    }
//
//
//    @Override
//    public Ad findById(Integer idAd) {
//        for(Ad ad : this.ads) {
//            if(ad.getIdAd().equals(idAd)) {
//                return ad;
//            }
//        }
//            return null;
//    }
//
//    @Override
//    public void update(Integer id, Ad newAd) {
//        for( Ad ad : this.ads) {
//            if(ad.getIdAd().equals(id)) {
//                ad.setProduct(newAd.getProduct());
//                ad.setCalendar(newAd.getCalendar());
//                break;
//            }
//        }
//    }


}
