package repo;

import model.*;
import interfaces.BusinessOwnerRepositoryInterface;


import java.util.ArrayList;
import java.util.List;

public class BusinessOwnerRepository implements BusinessOwnerRepositoryInterface{

    private static BusinessOwnerRepository single_instance = null;
    private ArrayList<BusinessOwner> allBusinessOwner = new ArrayList<>();

    public static BusinessOwnerRepository getInstance() {
        if (single_instance == null){
            single_instance = new BusinessOwnerRepository();
            populateBusinessOwners();
        }
        return single_instance;
    }

    public ArrayList<BusinessOwner> getAllBusinessOwner() {
        return allBusinessOwner;
    }

    //static-poti apela metoda fara sa creezi un obj
    public static void populateBusinessOwners(){
        BusinessOwner businessOwner1 = new BusinessOwner("Raul", "Pop", "raulstefan002", "1234");
        BusinessOwner businessOwner2 = new BusinessOwner("Andreea", "Tamas", "andreeatam", "4321");

       //adaugam in memorie businessOwnerii
       BusinessOwnerRepository.getInstance().add(businessOwner1);
       BusinessOwnerRepository.getInstance().add(businessOwner2);

       Hall hall1 =new Hall("Sala 1","Evenimente exclusiviste","Cluj",150);
       Ad ad1=new Ad(hall1, new Calendar());

       DJ dj1 =new DJ("DjAndrei","atmosfera geniala",true,true);
       Ad ad2=new Ad(dj1, new Calendar());

       List<String> sweets = new ArrayList<>();
       sweets.add("prajituri");
       sweets.add("tort");
       sweets.add("Bhutan");
       CandyBar candybar1=new CandyBar("AllDelicious","very good",sweets);
       Ad ad3=new Ad(candybar1, new Calendar());

       //adaugam anunturile "din memorie" la businessOwnerii "din memorie"
       businessOwner1.getAds().add(ad1);
       businessOwner2.getAds().add(ad2);
       businessOwner2.getAds().add(ad3);

       //adaugam in memorie anunturi
       AdRepository.getInstance().add(ad1);
       AdRepository.getInstance().add(ad2);
       AdRepository.getInstance().add(ad3);



//       ArrayList<Ad> adsInOffer = new ArrayList<>();
//       adsInOffer.add(ad1);
//
//       Offer offer = new Offer("12.12.2022", "24.12.2022", "das", adsInOffer);
//
//       businessOwner1.getReceivedOffers().add(offer);

    }

    @Override
    public void add(BusinessOwner newBusinessOwner){
        for(BusinessOwner businessOwner: this.allBusinessOwner){
            if(businessOwner.getUsername().equals(newBusinessOwner.getUsername())){
                return;
            }
        }
        this.allBusinessOwner.add(newBusinessOwner);
    }

    @Override
    public void remove(String username){
        this.allBusinessOwner.removeIf(businessOwner -> businessOwner.getUsername().equals(username));
    }

    @Override
    public void update(String username, BusinessOwner newBusinessOwner){
        for(BusinessOwner businessOwner: this.allBusinessOwner){
            if(businessOwner.getUsername().equals(username)){
              businessOwner.setFirstName(newBusinessOwner.getFirstName());
              businessOwner.setLastName(newBusinessOwner.getLastName());
              businessOwner.setUsername(newBusinessOwner.getUsername());
              businessOwner.setPassword(newBusinessOwner.getPassword());
              break;
            }
        }
    }

    @Override
    public BusinessOwner findById(String username) {
        for(BusinessOwner businessOwner: this.allBusinessOwner){
            if(businessOwner.getUsername().equals(username))
                return businessOwner;
        }
        return null;
    }


    @Override
    public BusinessOwner findByUsernameAndPassword(String username, String password) {
        BusinessOwner businessOwner = findById(username);

        if(businessOwner != null) {
            if(businessOwner.getPassword().equals(password)) {
                return businessOwner;
            }
        }
        return null;
    }

    //ret businessOwner-ul coresp id-ului unui Ad
    public BusinessOwner findBusinessOwnerByAdId(Integer idAd) {
        for(BusinessOwner businessOwner : this.allBusinessOwner) {
            for(Ad ad: businessOwner.getAds()) {
                if(ad.getIdAd().equals(idAd)) {
                    return businessOwner;
                }
            }
        }
        return null;
    }

    public Integer getSize() {

        return this.allBusinessOwner.size();
    }


}

