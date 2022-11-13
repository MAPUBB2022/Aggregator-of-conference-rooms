package model;

import interfaces.ICrudRepositoryInterface;

import java.util.ArrayList;

public class Organiser extends User {


    private ArrayList<Offer> receivedOffers;


    public Organiser(String firstName, String lastName, String username, String password) {
        super(firstName, lastName, username, password);
        this.receivedOffers = new ArrayList<>();
    }

    public ArrayList<Offer> getReceivedOffers() {
        return receivedOffers;
    }


//    @Override
//    public void add(Message newMessage) {
//
//    }
//
//    @Override
//    public void remove(Integer integer) {
//
//    }
//
//    @Override
//    public void update(Integer integer, Message newEntity) {
//
//    }
//
//    @Override
//    public Message findById(Integer integer) {
//        return null;
//    }

//    @Override
//    public void add(Offer newOffer){
//        for(Offer offer: this.sentOffers){
//            if(of.getIdOffer().equals(entity.getIdOffer())){
//                return;
//            }
//        }
//        this.sentOffers.add(entity);
//    }
//
//    //sterge o oferta dupa id din lista de oferte trimise
//    @Override
//    public void remove(Integer id){
//
//        this.sentOffers.removeIf(of -> of.getIdOffer().equals(id));
//    }
//
//    @Override
//    public void update(Integer id, Offer new_offer) {
//
//        for( Offer of : this.sentOffers) {
//            if(of.getIdOffer().equals(id)) {
//                of.setStartingDate(new_offer.getStartingDate());
//                of.setEndingDate(new_offer.getEndingDate());
//                of.setDescription(new_offer.getDescription());
//                of.setAdInOffer(new_offer.getAdInOffer());
//                break;
//            }
//        }
//    }
//
//    @Override
//    public Offer findById(Integer id) {
//        for(Offer offer : this.sentOffers) {
//            if(offer.getIdOffer().equals(id)) {
//                return offer;
//            }
//        }
//        return null;
//    }



}
