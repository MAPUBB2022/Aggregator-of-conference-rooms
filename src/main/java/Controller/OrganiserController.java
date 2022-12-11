package Controller;

import interfaces.UserControllerInterface;
import model.*;
import repo.inMemory.BusinessOwnerInMemoryRepository;
import repo.inMemory.OrganiserInMemoryRepository;
import repo.inMemory.ProductsInMemoryRepository;
import repo.jpa.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OrganiserController implements UserControllerInterface<Organiser, ArrayList<String>> {

    private static OrganiserController single_instance = null;
    String username;
    public static OrganiserController getInstance() {
        if (single_instance == null){
            single_instance = new OrganiserController();
        }
        return single_instance;
    }
    public OrganiserController() {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Organiser getOrganiser() {
        return OrganiserRepositoryJPA.getInstance().findById(username);
    }


    @Override
    public Organiser createUser(ArrayList<String> credentials) {
        return new Organiser(credentials.get(1), credentials.get(2), credentials.get(3), credentials.get(4));
    }

    public List<Product> getProducts() {
        return ProductRepositoryJPA.getInstance().getProducts();
    }

    //se seteaza statusul unei oferte la ACCEPTED
    public void acceptOffer(Offer offer) {
        OfferRepositoryJPA.getInstance().updateStatus(offer, Status.ACCEPTED);
    }

    //se seteaza statusul unei oferte la DECLINED
    public void declineOffer(Offer offer) {
        OfferRepositoryJPA.getInstance().updateStatus(offer, Status.DECLINED);
    }

    public boolean checkNewReceivedOffers() {
        List<Offer> newOffers = getOrganiser().getReceivedOffers().stream().filter(offer -> offer.getStatus().equals(Status.SENT)).toList(); ;
        return newOffers.isEmpty();
    }

    public boolean checkRequestedOffers() {
        return getOrganiser().getSentMessages().isEmpty();
    }


    public void sendMessage(Message message) {
        MessageRepositoryJPA.getInstance().add(message);
        MessageRepositoryJPA.getInstance().updateStatus(message, Status.SENT); //setam statusul msj la SENT
    }

    @Override
    public List<Message> filterByDeclinedMessages(){

        List<Message> messages = OrganiserRepositoryJPA.getInstance().findById(this.username).getSentMessages();

        return messages.stream().filter(message -> message.getStatus().equals(Status.DECLINED)).toList();
    }

    @Override
    public List<Message> filterByAcceptedMessages(){

        List<Message> messages = OrganiserRepositoryJPA.getInstance().findById(this.username).getSentMessages();

        return messages.stream().filter(message -> message.getStatus().equals(Status.ACCEPTED)).toList();
    }
    @Override
    public List<Offer> filterByDeclinedOffers(){

        List<Offer> offers = OrganiserRepositoryJPA.getInstance().findById(this.username).getReceivedOffers();

        return offers.stream().filter(offer -> offer.getStatus().equals(Status.DECLINED)).toList();
    }
    @Override
    public List<Offer> filterByAcceptedOffers(){

        List<Offer> offers = OrganiserRepositoryJPA.getInstance().findById(this.username).getReceivedOffers();

        return offers.stream().filter(offer -> offer.getStatus().equals(Status.ACCEPTED)).toList();
    }

    public List<Offer> sortOffersByPriceAscending() {
        List<Offer> offers = OrganiserRepositoryJPA.getInstance().findById(this.username).getReceivedOffers();

        return offers.stream().sorted(Comparator.comparingInt(Offer::getPrice)).toList();
    }

    public List<Offer> sortOffersByPriceDescending() {
        List<Offer> offers = OrganiserRepositoryJPA.getInstance().findById(this.username).getReceivedOffers();

        return offers.stream().sorted(Comparator.comparingInt(Offer::getPrice).reversed()).toList();
    }

    public List<Offer> filterOffersBySenderUsername(String usernameSender) {
        List<Offer> offers = OrganiserRepositoryJPA.getInstance().findById(this.username).getReceivedOffers();
        return offers.stream().filter(offer -> offer.getSender().getUsername().equals(usernameSender)).toList();
    }


}
