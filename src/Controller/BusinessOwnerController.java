package Controller;

import interfaces.UserControllerInterface;
import model.Ad;
import model.BusinessOwner;
import model.Offer;
import repo.BusinessOwnerRepository;
import view.View;

import java.time.OffsetDateTime;
import java.util.ArrayList;

public class BusinessOwnerController implements UserControllerInterface<BusinessOwner, ArrayList<String>> {

    @Override
    public BusinessOwner createUser(ArrayList<String> credentials) {
        BusinessOwner businessOwner = new BusinessOwner(credentials.get(1), credentials.get(2), credentials.get(3), credentials.get(4));
        return businessOwner;
    }

    public Integer showReceivedOffers(BusinessOwner businessOwner, View view) {
        if(businessOwner.getReceivedOffers().isEmpty()) {
            view.noOffersReceived();
            return 1;
        }
        for (Offer offer : businessOwner.getReceivedOffers()) {
            view.showOffer(offer);
        }
        return 2;
    }

    public void showAds(BusinessOwner businessOwner, View view) {
        for(Ad ad: businessOwner.getAds()) {
            view.showAd(ad);
        }
    }

    public void acceptOffer(Offer offer) {

    }

    public void createAd(BusinessOwner businessOwner, View view) {
        businessOwner.add(view.createAdView());

    }
}
