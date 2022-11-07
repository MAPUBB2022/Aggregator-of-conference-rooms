package Controller;

import interfaces.UserControllerInterface;
import model.Ad;
import model.BusinessOwner;
import model.Offer;
import model.Organiser;
import view.View;

import java.util.ArrayList;

public class OrganiserController implements UserControllerInterface<Organiser, ArrayList<String>> {

    @Override
    public Organiser createUser(ArrayList<String> credentials) {
        Organiser organiser = new Organiser(credentials.get(1), credentials.get(2), credentials.get(3), credentials.get(4));
        return organiser;
    }

    public void createOffer(Organiser organiser, View view)  {
        organiser.add(view.createOfferView());
    }

    public void sendOffer(BusinessOwner businessOwner, Offer offer){
        businessOwner.getReceivedOffers().add(offer);
    }
    public void showAllAds(BusinessOwner businessOwner, View view) {
        for(Ad ad: businessOwner.getAds()) {
            view.showAd(ad);
        }
    }


}
