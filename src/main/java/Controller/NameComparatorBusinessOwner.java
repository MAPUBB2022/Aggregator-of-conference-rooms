package Controller;

import model.BusinessOwner;

import java.util.Comparator;

public class NameComparatorBusinessOwner implements Comparator<BusinessOwner> {

    //compareByUsername
    @Override
    public int compare(BusinessOwner businessOwner1, BusinessOwner businessOwner2){
        return businessOwner1.getUsername().compareTo(businessOwner2.getUsername());
    }
}
