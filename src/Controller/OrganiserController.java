package Controller;

import interfaces.UserControllerInterface;
import model.*;
import repo.AdRepository;
import repo.BusinessOwnerRepository;
import repo.OrganiserRepository;
import view.View;

import java.util.ArrayList;
import java.util.Collections;

public class OrganiserController implements UserControllerInterface<Organiser, ArrayList<String>> {

    private Organiser organiser;

    private final View view;

    public OrganiserController(View view) {

        this.view = view;
    }

    public void setOrganiser(Organiser organiser) {

        this.organiser = organiser;
    }

    @Override
    public Organiser createUser(ArrayList<String> credentials) {
        organiser = new Organiser(credentials.get(1), credentials.get(2), credentials.get(3), credentials.get(4));
        return organiser;
    }

    public void showAllAds() {
        for(Ad ad: AdRepository.getInstance().getAllAds()) {
            view.showAd(ad);
        }
    }

    //se seteaza statusul unei oferte la ACCEPTED
    public void acceptOffer(Offer offer) {

        offer.setStatus(Status.ACCEPTED);
    }

    //se seteaza statusul unei oferte la DECLINED
    public void declineOffer(Offer offer) {

        offer.setStatus(Status.DECLINED);
    }

    //se afiseaza dupa status
    public void showNewOffersMenu() {
        if(organiser.getReceivedOffers().isEmpty()) { //daca lista de oferte primite a org e goala
            view.noNewMessages(); //nu exita msj nou
        }
        else {
            for (Offer offer : organiser.getReceivedOffers()) { //pt fiecare oferta din lista de oferte primite a org
                if (offer.getStatus().equals(Status.SENT)) { //daca starea ofertei e SENT
                    view.showOffer(offer); //se afis oferta
                    view.askOfferAccepting(); //apare msj daca vrei sa accepti oferta
                    boolean answer = view.answer();
                    if (answer) { //daca rasp e true
                        acceptOffer(offer); //status ul ofertei devine ACCEPTED
                        view.offerAccepted(); //apare msj cu oferta acceptata
                    } else {
                        declineOffer(offer); //status ul ofertei devine DECLINED
                        view.offerDeclined(); //apare msj cu oferta respinsa
                    }
                }
            }
        }
    }

    public void showSentMessages() {
        if(organiser.getRequestedOffers().isEmpty()) { //daca lista de oferte cerute a org e goala
            view.noSentMessages(); //apare msj ca nu ai trimis inca niciun msj
        }
        else { //daca lista nu e goala
            for (Message message : organiser.getRequestedOffers()) { //pt fiecare msj din lista de oferte cerute a org
                view.showMessage(message); //se afis msj
            }
        }
    }

    public void showReceivedOffers() {
        if(organiser.getReceivedOffers().isEmpty()) {
            view.noSentMessages();
        }
        else {
            for (Offer offer : organiser.getReceivedOffers()) {
                view.showOffer(offer);
            }
        }
    }

    public void sendMessage() {
        Message message = view.createMessageView(); //se creaza msj org catre b.o.
        Integer adId = message.getAd().getIdAd(); //se salveaaza id ul anuntului din anuntul din msj
        BusinessOwner businessOwner = BusinessOwnerRepository.getInstance().findBusinessOwnerByAdId(adId); //se salveaza b.o. coresp id ului din anunt
        businessOwner.getRequestedOffers().add(message); //adaugam in lista de oferte cerute a b.o. msj
        organiser.getRequestedOffers().add(message); //adaugam in lista de oferte cerute a org msj
        message.setStatus(Status.SENT); //setam statusul msj la SENT
    }

    //sorteaza lista de org dupa comparatorul de username a org
    public static ArrayList<Organiser> sort(){
        ArrayList<Organiser> allOrganisers=OrganiserRepository.getInstance().getAllOrganisers();
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
        for (Organiser o : OrganiserRepository.getInstance().getAllOrganisers()) {
            if (o.getReceivedOffers().size() >= NoReceivedOffers) {
                filteredOrganisers.add(o);
            }
        }
        return filteredOrganisers;
    }

    //se afiseaza toate mesajele ale unui anumit org
    public ArrayList<Message> showMessagesByRandomOrg(Organiser o){
        ArrayList<Message> returnedMessages = new ArrayList<>(Collections.emptyList());
        for (Organiser organiser : OrganiserRepository.getInstance().getAllOrganisers()) //cauta in lista de org pe cel dat ca parametru
            if(organiser.getFirstName().equals(o.getFirstName()) && organiser.getLastName().equals(o.getLastName())) { //daca il gaseste
                for(Message m: o.getRequestedOffers()) { //pt fiecare msj a org dat ca param (gasit)
                    view.showMessage(m);
                    returnedMessages.add(m);
                }
            }
        return returnedMessages;
    }
}
