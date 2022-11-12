package Controller;

import interfaces.UserControllerInterface;
import model.Ad;
import model.BusinessOwner;
import model.Offer;
import model.Organiser;
import repo.AdRepository;
import repo.OrganiserRepository;
import view.View;

import java.util.ArrayList;

public class BusinessOwnerController implements UserControllerInterface<BusinessOwner, ArrayList<String>> {

    private BusinessOwner businessOwner;
    private final View view;

    public BusinessOwnerController(View view) {
        this.view= view;
    }

    public void setBusinessOwner(BusinessOwner businessOwner) {
        this.businessOwner = businessOwner;
    }

    @Override
    public BusinessOwner createUser(ArrayList<String> credentials) {
        businessOwner = new BusinessOwner(credentials.get(1), credentials.get(2), credentials.get(3), credentials.get(4));
        return businessOwner;
    }

    public Integer showReceivedOffers() {
        //daca lista e goala => ret 1
        if(businessOwner.getReceivedOffers().isEmpty()) {
            view.noOffersReceived();
            return 1;
        }
        for (Offer offer : businessOwner.getReceivedOffers()) {
            view.showOffer(offer);
        }
        return 2;
    }

    public void showAds() {
        for(Ad ad: businessOwner.getAds()) {
            view.showAd(ad);
        }
    }

    public void acceptOffer(Offer offerAccepted, OrganiserRepository organisers) {

        Organiser organiser = organisers.findByOfferId(offerAccepted.getIdOffer());

        if(organiser != null) {
            businessOwner.getAcceptedOffers().add(offerAccepted);
            businessOwner.getAcceptedOffers().remove(offerAccepted);
            organiser.getAcceptedOffers().add(offerAccepted);
            organiser.getSentOffers().remove(offerAccepted);
        }
        else {
            view.somethingWentWrong();
        }
    }

    public Offer findOffer() {
        Integer idOffer = view.chooseOfferToAccept();
        for(Offer offer : businessOwner.getReceivedOffers()) {
            if(offer.getIdOffer() == idOffer)
                return offer;
        }
        return null;
    }

    public void createAd(AdRepository ads) {
        Ad ad = view.createAdView();
        businessOwner.add(ad);
        ads.add(ad);

    }

    public void receivedOffersMenu(OrganiserRepository organisers) {

        int option = showReceivedOffers();

        if(option == 2) {
            boolean answer = view.acceptOfferView();
            if (answer) {
                Offer offer = findOffer();
                if (offer != null) {
                    acceptOffer(offer, organisers);
                } else {
                    view.somethingWentWrong();
                    receivedOffersMenu(organisers);
                }
            }
        }
    }

}
