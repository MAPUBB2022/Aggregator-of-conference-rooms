package Controller;

import interfaces.UserControllerInterface;
import model.*;
import repo.AdRepository;
import repo.BusinessOwnerRepository;
import view.View;

import java.util.ArrayList;

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
    public void acceptOffer(Offer offer) {
        offer.setStatus(Status.ACCEPTED);
    }

    public void declineOffer(Offer offer) {
        offer.setStatus(Status.DECLINED);
    }

    public void showNewOffersMenu() {
        if(organiser.getReceivedOffers().isEmpty()) {
            view.noNewMessages();
        }
        else {
            for (Offer offer : organiser.getReceivedOffers()) {
                if (offer.getStatus().equals(Status.SENT)) {
                    view.showOffer(offer);
                    view.askOfferAccepting();
                    boolean answer = view.answer();
                    if (answer) {
                        acceptOffer(offer);
                        view.offerAccepted();
                    } else {
                        declineOffer(offer);
                        view.offerDeclined();
                    }
                }
            }
        }
    }



    public void sendMessage() {
        Message message = view.createMessageView();
        Integer adId = message.getAd().getIdAd();
        BusinessOwner businessOwner = BusinessOwnerRepository.getInstance().findByAdId(adId);
        businessOwner.getRequestedOffers().add(message);
        organiser.getRequestedOffers().add(message);
        message.setStatus(Status.SENT);
    }

    public void showSentMessages() {
        if(organiser.getRequestedOffers().isEmpty()) {
            view.noSentMessages();
        }
        else {
            for (Message message : organiser.getRequestedOffers()) {
                view.showMessage(message);
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
}
