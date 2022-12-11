package Controller;

import interfaces.UserControllerInterface;
import model.*;
import repo.inMemory.BusinessOwnerInMemoryRepository;
import repo.inMemory.OrganiserInMemoryRepository;
import repo.inMemory.ProductsInMemoryRepository;
import repo.jpa.*;

import java.util.ArrayList;
import java.util.Collections;
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


    public List<Hall> getHalls() {
        return ProductRepositoryJPA.getInstance().getHalls();
    }
    public List<CandyBar> getCandyBars() {
        return ProductRepositoryJPA.getInstance().getCandyBars();
    }
    public List<DJ> getDjs() {
        return ProductRepositoryJPA.getInstance().getDjs();
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

    //sorteaza lista de org dupa comparatorul de username a org
//    public static ArrayList<Organiser> sort(){
//        ArrayList<Organiser> allOrganisers= OrganiserInMemoryRepository.getInstance().getAllOrganisers();
//        Collections.sort(allOrganisers, new NameComparatorOrganiser());
//
//        return allOrganisers;
//
//    }

    //filtrare dupa oferte acceptate - cu param, dar se poate si fara param (vezi in b.o.controller)
//    public static List<Message> FilterByAcceptedOffer(Organiser organiser){
//        List<Message> filteredOffer=new ArrayList<>(Collections.emptyList());
//        for (Offer offer : organiser.getReceivedOffers()) {
//            if (offer.getStatus().equals(Status.SENT)) {
//                filteredOffer.add(offer);
//                //trb si afisat?
//            }
//        }
//        return filteredOffer;
//    }
//
//    //filtrare dupa oferte respinse
//    public static ArrayList<Message> FilterByDeclinedOffer(Organiser organiser){
//        ArrayList<Message> filteredOffer=new ArrayList<>(Collections.emptyList());
//        for (Offer offer : organiser.getReceivedOffers()) {
//            if (offer.getStatus().equals(Status.DECLINED)) {
//                filteredOffer.add(offer);
//                //trb si afisat?
//            }
//        }
//        return filteredOffer;
//    }

    //filtrare org dupa nr total de oferte primite; nr total de oferte primite a unui org trb sa fie >= un nr oarecare
    public ArrayList<Organiser>filterByNumberOfSentMessages(int NoReceivedOffers) {
        ArrayList<Organiser> filteredOrganisers = new ArrayList<>(Collections.emptyList());
        for (Organiser o : OrganiserInMemoryRepository.getInstance().getAllOrganisers()) {
            if (o.getReceivedOffers().size() >= NoReceivedOffers) {
                filteredOrganisers.add(o);
            }
        }
        return filteredOrganisers;
    }

    //se afiseaza toate mesajele ale unui anumit org
    public ArrayList<Message> showMessagesByRandomOrg(Organiser o){
        ArrayList<Message> returnedMessages = new ArrayList<>(Collections.emptyList());
        for (Organiser organiser : OrganiserInMemoryRepository.getInstance().getAllOrganisers()) //cauta in lista de org pe cel dat ca parametru
            if(organiser.getFirstName().equals(o.getFirstName()) && organiser.getLastName().equals(o.getLastName())) { //daca il gaseste
                for(Message m: o.getSentMessages()) { //pt fiecare msj a org dat ca param (gasit)
                   // view.showMessage(m);
                    returnedMessages.add(m);
                }
            }
        return returnedMessages;
    }
}
