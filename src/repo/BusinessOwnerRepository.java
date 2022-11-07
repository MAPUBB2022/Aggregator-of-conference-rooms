package repo;

import model.*;
import interfaces.BusinessOwnerRepositoryInterface;


import java.util.ArrayList;
import java.util.List;

public class BusinessOwnerRepository implements BusinessOwnerRepositoryInterface{

    private static BusinessOwnerRepository single_instance = null;
    private List<BusinessOwner> allBusinessOwner = new ArrayList<>();

    public static BusinessOwnerRepository getInstance() {
        if (single_instance == null){
            single_instance = new BusinessOwnerRepository();
            populateBusinessOwners();
        }
        return single_instance;
    }

    //static-poti apela metoda fara sa creezi un obj
    public static void populateBusinessOwners(){
        BusinessOwner businessOwner1 = new BusinessOwner("Raul", "Pop", "raulstefan002", "1234");
        BusinessOwner businessOwner2 = new BusinessOwner("Andreea", "Tamas", "andreeatam", "4321");

       BusinessOwnerRepository.getInstance().add(businessOwner1);
       BusinessOwnerRepository.getInstance().add(businessOwner2);

       Hall hall1 =new Hall("Sala 1","Evenimente exclusiviste","Cluj",150);
       Ad ad1=new Ad(1,hall1,new Calendar());

       DJ dj1 =new DJ("DjAndrei","atmosfera geniala",true,true);
       Ad ad2=new Ad(2,dj1,new Calendar());

       List<String> sweets = new ArrayList<>();
       sweets.add("prajituri");
       sweets.add("tort");
       sweets.add("Bhutan");
       CandyBar candybar1=new CandyBar("AllDelicious","very good",sweets);
       Ad ad3=new Ad(3,candybar1,new Calendar());

       businessOwner1.add(ad1);
       businessOwner2.add(ad2);
       businessOwner2.add(ad3);

    }

    @Override
    public void add(BusinessOwner entity){
        for(BusinessOwner b: this.allBusinessOwner){
            if(b.getUsername().equals(entity.getUsername())){
                return;
            }
        }
        this.allBusinessOwner.add(entity);
    }

    @Override
    public void remove(String username){
        this.allBusinessOwner.removeIf(businessOwner -> businessOwner.getUsername().equals(username));
    }

    @Override
    public void update(String username, BusinessOwner new_businessOwner){
        for(BusinessOwner b: this.allBusinessOwner){
            if(b.getUsername().equals(username)){
              this.allBusinessOwner.remove(b);
              this.allBusinessOwner.add(new_businessOwner);
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

    public Integer getSize() {

        return this.allBusinessOwner.size();
    }


}

