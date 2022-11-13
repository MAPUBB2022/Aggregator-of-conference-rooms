package Controller;

import interfaces.UserControllerInterface;
import model.*;
import repo.AdRepository;
import repo.OrganiserRepository;
import view.View;

import java.util.ArrayList;

public class BusinessOwnerController implements UserControllerInterface<BusinessOwner, ArrayList<String>> {

    private BusinessOwner businessOwner;
    private final View view;

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
        for (Ad ad : businessOwner.getAds()) {
            view.showAd(ad);
        }
    }

    public void createAd() {
        Ad ad = view.createAdView();
        businessOwner.getAds().add(ad);
        AdRepository.getInstance().add(ad);

    }

    public void declineMessage(Message message) {
        message.setStatus(Status.DECLINED);
    }

    public void makeOffer(Message message) {
        Offer offer = (Offer) view.createOfferView(message);
        Organiser organiser = OrganiserRepository.getInstance().findByMessageId(message.getIdMessage());
        organiser.getReceivedOffers().add(offer);
        businessOwner.getSentOffers().add(offer);
        offer.setStatus(Status.SENT);
        message.setStatus(Status.ACCEPTED);
    }

    public void allMessagesMenu() {
        if (businessOwner.getRequestedOffers().isEmpty()) {
            view.noMessages();
        }
        for (Message message : businessOwner.getRequestedOffers()) {
            view.showMessage(message);

        }

    }
    public void newMessagesMenu() {
        if (businessOwner.getRequestedOffers().isEmpty()) {
            view.noNewMessages();
        }
        for (Message message : businessOwner.getRequestedOffers()) {
            if(message.getStatus().equals(Status.SENT)) {
                view.showMessage(message);
                view.askOfferMaking();
                boolean answer = view.answer();
                if (answer) {
                    makeOffer(message);
                    view.offerSent();
                } else {
                    declineMessage(message);
                    view.messageDeclined();
                }
            }
        }
    }

    public void showOffers() {
        if(businessOwner.getSentOffers().isEmpty()) {
            view.noOffers();
        }
        else {
            for (Offer offer : businessOwner.getSentOffers()) {
                view.showOffer(offer);
            }
        }
    }

}

