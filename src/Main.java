import Controller.Server;
import model.*;
import repo.OrganiserRepository;

import java.time.LocalDate;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

//
//       OrganiserRepository.getInstance();
//       Organiser organiser1 = new Organiser("A", "b", "organiser1", "123");
//       Organiser organiser2 = new Organiser("X", "b", "organiser2", "123");
//
//        System.out.println("Add");
//        OrganiserRepository.getInstance().add(organiser1);
//        OrganiserRepository.getInstance().printOrganisers();
//
//        System.out.println("Modify");
//        OrganiserRepository.getInstance().update("organiser1", organiser2);
//        OrganiserRepository.getInstance().printOrganisers();
//
//        System.out.println("Delete");
//        OrganiserRepository.getInstance().remove("organiser2");
//        OrganiserRepository.getInstance().printOrganisers();
//
////        BusinessOwnerRepository.getInstance();
////        BusinessOwner businessOwner1 = new BusinessOwner("a", "b", "bowner1", "123");
////        BusinessOwner businessOwner2 = new BusinessOwner("sa", "dab", "bowner2", "123");
//
////        System.out.println("Add");
////        BusinessOwnerRepository.getInstance().add(businessOwner1);
////        BusinessOwnerRepository.getInstance().printBusinessOwners();
////
////        System.out.println("Modify");
////        BusinessOwnerRepository.getInstance().update("bowner1", businessOwner2);
////        BusinessOwnerRepository.getInstance().printBusinessOwners();
////
////        System.out.println("Delete");
////        BusinessOwnerRepository.getInstance().remove("bowner2");
////        BusinessOwnerRepository.getInstance().printBusinessOwners();



     Server server = new Server();

     server.runProgram();





    }
}