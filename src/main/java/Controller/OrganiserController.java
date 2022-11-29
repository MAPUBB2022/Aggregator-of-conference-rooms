package Controller;

import interfaces.UserControllerInterface;
import model.*;
import repo.inMemory.AdInMemoryRepository;
import repo.inMemory.BusinessOwnerInMemoryRepository;
import repo.inMemory.OrganiserInMemoryRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrganiserController implements UserControllerInterface<Organiser, ArrayList<String>> {

    private static OrganiserController single_instance = null;
    private Organiser organiser;
    public static OrganiserController getInstance() {
        if (single_instance == null){
            single_instance = new OrganiserController();
        }
        return single_instance;
    }
    public OrganiserController() {
    }

    public Organiser getOrganiser() {
        return organiser;
    }


    public void setOrganiser(Organiser organiser) {
        this.organiser = organiser;
    }

    @Override
    public Organiser createUser(ArrayList<String> credentials) {
        organiser = new Organiser(credentials.get(1), credentials.get(2), credentials.get(3), credentials.get(4));
        return organiser;
    }

    public List<Ad> getAds() {
        return AdInMemoryRepository.getInstance().getAllAds();
    }

    //se seteaza statusul unei oferte la ACCEPTED
    public void acceptOffer(Offer offer) {

        offer.setStatus(Status.ACCEPTED);
    }

    //se seteaza statusul unei oferte la DECLINED
    public void declineOffer(Offer offer) {

        offer.setStatus(Status.DECLINED);
    }

    public boolean checkNewReceivedOffers() {
        return organiser.getReceivedOffers().isEmpty();
    }

    public boolean checkRequestedOffers() {
        return organiser.getRequestedOffers().isEmpty();
    }


    public void sendMessage(Message message) {
        Integer adId = message.getAd().getIdAd(); //se salveaaza id ul anuntului din anuntul din msj
        BusinessOwner businessOwner = BusinessOwnerInMemoryRepository.getInstance().findBusinessOwnerByAdId(adId); //se salveaza b.o. coresp id ului din anunt
        businessOwner.getRequestedOffers().add(message); //adaugam in lista de oferte cerute a b.o. msj
        organiser.getRequestedOffers().add(message); //adaugam in lista de oferte cerute a org msj
        message.setStatus(Status.SENT); //setam statusul msj la SENT
    }

    //sorteaza lista de org dupa comparatorul de username a org
    public static ArrayList<Organiser> sort(){
        ArrayList<Organiser> allOrganisers= OrganiserInMemoryRepository.getInstance().getAllOrganisers();
        Collections.sort(allOrganisers, new NameComparatorOrganiser());

        return allOrganisers;

    }

    //filtrare dupa oferte acceptate - cu param, dar se poate si fara param (vezi in b.o.controller)
    public static ArrayList<Message> FilterByAcceptedOffer(Organiser organiser){
        ArrayList<Message> filteredOffer=new ArrayList<>(Collections.emptyList());
        for (Offer offer : organiser.getReceivedOffers()) {
            if (offer.getStatus().equals(Status.SENT)) {
                filteredOffer.add(offer);
                //trb si afisat?
            }
        }
        return filteredOffer;
    }

    //filtrare dupa oferte respinse
    public static ArrayList<Message> FilterByDeclinedOffer(Organiser organiser){
        ArrayList<Message> filteredOffer=new ArrayList<>(Collections.emptyList());
        for (Offer offer : organiser.getReceivedOffers()) {
            if (offer.getStatus().equals(Status.DECLINED)) {
                filteredOffer.add(offer);
                //trb si afisat?
            }
        }
        return filteredOffer;
    }

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
                for(Message m: o.getRequestedOffers()) { //pt fiecare msj a org dat ca param (gasit)
                   // view.showMessage(m);
                    returnedMessages.add(m);
                }
            }
        return returnedMessages;
    }
}
