package model;

import interfaces.ICrudRepositoryInterface;

import java.util.ArrayList;

public class Organiser extends User implements ICrudRepositoryInterface<Offer, Integer> {
    private ArrayList<Offer> sentOffers;
    private ArrayList<Offer> acceptedOffers;

    public Organiser(String firstName, String lastName, String username, String password) {
        super(firstName, lastName, username, password);
        this.sentOffers = new ArrayList<>();
        this.acceptedOffers = new ArrayList<>();
    }

    public ArrayList<Offer> getAcceptedOffers() {
        return acceptedOffers;
    }

    public void setAcceptedOffers(ArrayList<Offer> acceptedOffers) {
        this.acceptedOffers = acceptedOffers;
    }

    public ArrayList<Offer> getSentOffers() {
        return sentOffers;
    }

    public void setSentOffers(ArrayList<Offer> sentOffers) {
        this.sentOffers = sentOffers;
    }

    @Override
    public void add(Offer entity){
        for(Offer of: this.sentOffers){
            if(of.getIdOffer().equals(entity.getIdOffer())){
                return;
            }
        }
        this.sentOffers.add(entity);
    }

    @Override
    public void remove(Integer id){
        this.sentOffers.removeIf(of -> of.getIdOffer().equals(id));
    }

    @Override
    public void update(Integer id, Offer new_offer) {

        for( Offer of : this.sentOffers) {
            if(of.getIdOffer().equals(id)) {
                of.setIdOffer(new_offer.getIdOffer());
                of.setStartingDate(new_offer.getStartingDate());
                of.setEndingDate(new_offer.getEndingDate());
                of.setDescription(new_offer.getDescription());
                of.setAdsInOffer(new_offer.getAdsInOffer());
                break;
            }
        }
    }


    public void acceptOffer(){

    }


}
