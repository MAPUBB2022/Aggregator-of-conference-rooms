package Controller;

import interfaces.UserControllerInterface;
import model.*;
import repo.AdRepository;
import repo.BusinessOwnerRepository;
import repo.OrganiserRepository;
import view.View;

import java.util.*;

public class BusinessOwnerController implements UserControllerInterface<BusinessOwner, ArrayList<String>> {

    private BusinessOwner businessOwner;
    private final View view;

    //in constructor folosim cls View pt ca apelam mai multe metode din view si sa nu tot cream un obj view de fiecare data si in fiecare metoda corep
    public BusinessOwnerController(View view) {

        this.view = view;
    }

    public void setBusinessOwner(BusinessOwner businessOwner) {

        this.businessOwner = businessOwner;
    }

    @Override
    public BusinessOwner createUser(ArrayList<String> credentials) {
        businessOwner = new BusinessOwner(credentials.get(1), credentials.get(2), credentials.get(3), credentials.get(4));
        return businessOwner;
    }


    public void showAds() {
        for (Ad ad : businessOwner.getAds()) { //pt fiecare anunt din lista de anunturi a b.o.
            view.showAd(ad); //se afis ad (idAd,prod,calendar)
        }
    }

    public void newMessagesMenu() {
        if (businessOwner.getRequestedOffers().isEmpty()) { //daca lista de oferte cerute a b.o. e goala
            view.noNewMessages(); //nu exista msj nou
        }
        for (Message message : businessOwner.getRequestedOffers()) { //pt fiecare mesaj a org catre b.o. din lista de oferte cerute
            if(message.getStatus().equals(Status.SENT)) { //daca starea msj e de SENT
                view.showMessage(message); //vezi msj
                view.askOfferMaking(); //apare msj daca vrei sa faci o oferta
                boolean answer = view.answer(); //se ret rasp true/false
                if (answer) {  //if(answer!=false) //daca rasp e da
                    makeOffer(message); //se face oferta
                    view.offerSent(); //apare msj de oferta creata cu succes
                } else { //daca rasp e nu
                    declineMessage(message); //se set starea msj la DECLINED
                    view.messageDeclined(); //apare msj de declined
                }
            }
        }
    }

    public void allMessagesMenu() {
        if (businessOwner.getRequestedOffers().isEmpty()) { //daca lista de oferte cerute a b.o. e goala
            view.noMessages(); //nu exista mesaje primite
        }
        for (Message message : businessOwner.getRequestedOffers()) { //pt fiecare mesaj a org catre b.o. din lista de oferte cerute
            view.showMessage(message); //se arata msj de forma.. pe care il trimite org b.o.

        }

    }

    public void showOffers() {
        if(businessOwner.getSentOffers().isEmpty()) { //daca lista de oferte trimise a b.o. e goala
            view.noOffers(); //apare msj ca nu ai ce oferte sa vezi
        }
        else {
            for (Offer offer : businessOwner.getSentOffers()) {
                view.showOffer(offer);
            }
        }
    }

    public void createAd() {
        Ad ad = view.createAdView();
        businessOwner.getAds().add(ad); //ad in lista de anunturi a b.o. noul anunt
        AdRepository.getInstance().add(ad); //add ad ul ca sa fie vzt ca 1 sg instanta alaturi de restul
    }

    // e ok?
    public void deleteAd(){
        Ad deletedAd=view.deleteAdView(); //se ret ad-ul ce trb sters
        this.businessOwner.getAds().removeIf(ad -> ad.getIdAd().equals(deletedAd.getIdAd()));
        AdRepository.getInstance().remove(deletedAd.getIdAd());
    }

    // e ok?
    public void modifyAd() {
        Ad newAd = view.modifyAdView(); //se ret noul ad cu noile valori
        for (Ad ad : this.businessOwner.getAds()) {
            if (ad.getIdAd().equals(newAd.getIdAd())) {
                ad.setProduct(newAd.getProduct());
                ad.setCalendar(newAd.getCalendar());
            }
        }
    }

    public void declineMessage(Message message) {

        message.setStatus(Status.DECLINED);
    }


    public void makeOffer(Message message) {
        Offer offer = (Offer) view.createOfferView(message);
        Organiser organiser = OrganiserRepository.getInstance().findOrganiserByMessageId(message.getIdMessage()); //se gaseste org
        organiser.getReceivedOffers().add(offer); //adaugam oferta in lista de oferte primite a org
        businessOwner.getSentOffers().add(offer); //adaugam oferta in lista de oferte trimise a b.o.
        offer.setStatus(Status.SENT); //setam starea ofertei la SENT
        message.setStatus(Status.ACCEPTED); //setam starea msj la ACCEPTED
    }

    //sorteaza lista de b.o. dupa comparatorul de username a b.o.
    public static ArrayList<BusinessOwner> sort(){
        ArrayList<BusinessOwner> allBusinessOwners=BusinessOwnerRepository.getInstance().getAllBusinessOwner();
        Collections.sort(allBusinessOwners, new NameComparatorBusinessOwner());

        return allBusinessOwners;

    }

    //filtrare dupa mesaje acceptate - e ok dat ca param?
    public ArrayList<Message> filterByAcceptedMessage(){
        ArrayList<Message> filteredMessage=new ArrayList<>(Collections.emptyList());
        for(Message message:businessOwner.getRequestedOffers()){
            if(message.getStatus().equals(Status.SENT)){
                filteredMessage.add(message);
                //trb si afisat?
            }
        }
        return filteredMessage;
    }

    //filtrare dupa mesaje respinse
    public ArrayList<Message> filterByDeclinedMessage(){
        ArrayList<Message> filteredMessage=new ArrayList<>(Collections.emptyList());
        for(Message message:businessOwner.getRequestedOffers()){
            if(message.getStatus().equals(Status.DECLINED)){
                filteredMessage.add(message);
                //trb si afisat?
            }
        }
        return filteredMessage;
    }

    //filtrare b.o. dupa nr total de oferte facute; nr total de oferte a unui b.o. trb sa fie >= un nr oarecare
    public ArrayList<BusinessOwner>filterByNumberOfMadeOffers(int NoOffers) {
        ArrayList<BusinessOwner> filteredBusinessOwners = new ArrayList<>(Collections.emptyList());
        for (BusinessOwner b : BusinessOwnerRepository.getInstance().getAllBusinessOwner()) {
            if (b.getSentOffers().size() >= NoOffers) {
                filteredBusinessOwners.add(b);
            }
        }
        return filteredBusinessOwners;
    }

    //se afiseaza toate ofertele ale unui anumit b.o.
    public ArrayList<Offer> showOffersByRandomBO(BusinessOwner b){
        ArrayList<Offer> returnedOffers = new ArrayList<>(Collections.emptyList());
        for (BusinessOwner businessOwner : BusinessOwnerRepository.getInstance().getAllBusinessOwner()) //cauta in lista de b.o. pe cel dat ca parametru
            if(businessOwner.getFirstName().equals(b.getFirstName()) && businessOwner.getLastName().equals(b.getLastName())) { //daca il gaseste
                for(Offer o: b.getSentOffers()) { //pt fiecare oferta a b.o dat ca param (gasit)
                    view.showOffer(o);
                    returnedOffers.add(o);
                }
            }
        return returnedOffers;
    }
}

