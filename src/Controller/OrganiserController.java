package Controller;

import interfaces.UserControllerInterface;
import model.Ad;
import model.BusinessOwner;
import model.Offer;
import model.Organiser;
import repo.AdRepository;
import repo.BusinessOwnerRepository;
import view.View;

import java.text.ParseException;
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

    public void createOffer(AdRepository ads, BusinessOwnerRepository businessOwners) throws ParseException {
        Offer offer = view.createOfferView(ads);
        organiser.getSentOffers().add(offer);

        Integer idAd = offer.getAdInOffer().getIdAd();
        BusinessOwner businessOwner = businessOwners.findByAdId(idAd);
        businessOwner.getReceivedOffers().add(offer);
            }

    public void sendOffer(BusinessOwner businessOwner, Offer offer){
        businessOwner.getReceivedOffers().add(offer);
    }

    public void showAllAds(AdRepository ads) {
        for(Ad ad: ads.getAllAds()) {
            view.showAd(ad);
        }
    }


    public void showAcceptedOffers() {
        if(organiser.getAcceptedOffers().isEmpty()) {
            System.out.println("You don't have offers accepted");
        }
        else {
            for (Offer offer : organiser.getAcceptedOffers()) {
                view.showOffer(offer);
            }
        }
    }
}
