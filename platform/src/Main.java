import model.*;
import repo.BusinessOwnerRepository;
import repo.OrganiserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Main {
    public static void main(String[] args) {


       OrganiserRepository.getInstance();
       Organiser organiser1 = new Organiser("A", "b", "organiser1", "123");
       Organiser organiser2 = new Organiser("X", "b", "organiser2", "123");

        System.out.println("Add");
        OrganiserRepository.getInstance().add(organiser1);
        OrganiserRepository.getInstance().printOrganisers();

        System.out.println("Modify");
        OrganiserRepository.getInstance().update("organiser1", organiser2);
        OrganiserRepository.getInstance().printOrganisers();

        System.out.println("Delete");
        OrganiserRepository.getInstance().remove("organiser2");
        OrganiserRepository.getInstance().printOrganisers();

//        BusinessOwnerRepository.getInstance();
//        BusinessOwner businessOwner1 = new BusinessOwner("a", "b", "bowner1", "123");
//        BusinessOwner businessOwner2 = new BusinessOwner("sa", "dab", "bowner2", "123");
//
//        System.out.println("Add");
//        BusinessOwnerRepository.getInstance().add(businessOwner1);
//        BusinessOwnerRepository.getInstance().printBusinessOwners();

//        System.out.println("Modify");
//        BusinessOwnerRepository.getInstance().update("bowner1", businessOwner2);
//        BusinessOwnerRepository.getInstance().printBusinessOwners();
//
//        System.out.println("Delete");
//        BusinessOwnerRepository.getInstance().remove("bowner2");
//        BusinessOwnerRepository.getInstance().printBusinessOwners();

        LocalDate date1=LocalDate.of(2022,12,31);
        LocalDate date2=LocalDate.of(2022,10,27);
        LocalDate date3=LocalDate.of(2022,06,18);
        LocalDate date4=LocalDate.of(2022,01,01);

        Hall hall = new Hall("Sala X", 5, "Sala de evenimente", "Cluj-Napoca", 100);

        ArrayList<LocalDate>free_dates=new ArrayList<>();
        free_dates.add(date1);
        free_dates.add(date2);
        free_dates.add(date3);
        free_dates.add(date4);
        ArrayList<LocalDate>occupied_dates=new ArrayList<>();


        Calendar calendar1=new Calendar(occupied_dates,free_dates);
        Ad ad=new Ad(1,hall,calendar1);






    }
}