package repo;

import model.Ad;
import model.BusinessOwner;
import interfaces.BusinessOwnerRepositoryInterface;
import model.Organiser;


import java.util.ArrayList;
import java.util.List;

public class BusinessOwnerRepository implements BusinessOwnerRepositoryInterface{

    private static BusinessOwnerRepository single_instance = null;
    private final List<BusinessOwner> allBusinessOwner = new ArrayList<>();

    public static BusinessOwnerRepository getInstance() {
        if (single_instance == null){
            single_instance = new BusinessOwnerRepository();
            populateBusinessOwners();
        }
        return single_instance;
    }

    public static void populateBusinessOwners(){
        BusinessOwner businessOwner1 = new BusinessOwner("Raul", "Pop", "raulstefan002", "1234");
        BusinessOwner businessOwner2 = new BusinessOwner("Andreea", "Tamas", "andreeatam", "4321");

       BusinessOwnerRepository.getInstance().add(businessOwner1);
       BusinessOwnerRepository.getInstance().add(businessOwner2);

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
    public BusinessOwner findByUsername(String s){
        for(BusinessOwner b: this.allBusinessOwner){
            if(b.getUsername().equals(s))
                return b;
        }
        return null;
    }

    public void printBusinessOwners(){
        for(BusinessOwner b : this.allBusinessOwner) {
            System.out.println("First "+b.getFirstName());
            System.out.println("Lastname "+b.getLastName());
            System.out.println("Username "+b.getUsername());
        }
    }

    public Integer getSize() {
        return this.allBusinessOwner.size();
    }


}

