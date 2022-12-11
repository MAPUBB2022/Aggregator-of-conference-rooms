package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "businessOwners")
public class BusinessOwner extends User {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "idBusinessOwner")
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private List<Offer> sentOffers = new ArrayList<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private List<Message> receivedMessages = new ArrayList<>();

    public BusinessOwner(String firstName, String lastName, String username, String password) {
        super(firstName, lastName, username, password);
    }

    public BusinessOwner() {

    }

    @Override
    public String toString() {
        return "BusinessOwner{ " +
                "username=" + getUsername() +
                ", products=" + products +
                ", sentOffers=" + sentOffers +
                ", receivedMessages=" + receivedMessages +
                '}';
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Offer> getSentOffers() {
        return sentOffers;
    }

    public List<Message> getReceivedMessages() {
        return receivedMessages;
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
