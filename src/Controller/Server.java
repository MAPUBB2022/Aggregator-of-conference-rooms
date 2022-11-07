package Controller;

import model.BusinessOwner;
import model.Offer;
import model.Organiser;
import repo.AdRepository;
import repo.BusinessOwnerRepository;
import repo.OrganiserRepository;
import view.View;

import java.util.ArrayList;

public class Server {

    private final View view;
    private OrganiserRepository organisers;
    private BusinessOwnerRepository businessOwners;
    private AdRepository ads;
    private OrganiserController organiserController;
    private BusinessOwnerController businessOwnerController;

    public Server() {
        this.view = new View();
        this.organisers = OrganiserRepository.getInstance();
        this.businessOwners = BusinessOwnerRepository.getInstance();
        this.ads = AdRepository.getInstance();
        this.organiserController = new OrganiserController();
        this.businessOwnerController = new BusinessOwnerController();
    }

    public void login() {
        ArrayList<String> credentials = view.loginView();
        if(credentials.get(0).equals("1") && organisers.findByUsernameAndPassword(credentials.get(1), credentials.get(2)) != null) {
            view.adsView(ads);
        }
        else if (credentials.get(0).equals("2") && businessOwners.findByUsernameAndPassword(credentials.get(1), credentials.get(2)) != null) {
            this.businessOwnerMenu2(credentials.get(1));
        }
        else {
            view.wrongCredentials();
            login();
        }

    }

    public void signUp() {
        ArrayList<String> credentials = view.signupView();
        if(credentials.get(0).equals("1")) {
            OrganiserRepository.getInstance().add(organiserController.createUser(credentials));
            view.userCreatedSuccessfully();
            login();
        }
        else if (credentials.get(0).equals("2")) {
            BusinessOwnerRepository.getInstance().add(businessOwnerController.createUser(credentials));
            view.userCreatedSuccessfully();
            login();
        }
        else {
            view.somethingWentWrong();
        }
    }

    public void businessOwnerMenu2(String username) {
        int option = view.businessOwnerMenu();
        BusinessOwner businessOwner = businessOwners.findById(username);

        if(option == 1) {
            int subOption = businessOwnerController.showReceivedOffers(businessOwner, view);
            //daca lista e goala
            if(subOption == 1) {
                businessOwnerMenu2(username); //se face o alta alegere din meniul lui business owner
            }

        }
        else if(option == 2) {
            businessOwnerController.showAds(businessOwner, view);
        }
        else if(option == 3) {
            businessOwnerController.createAd(businessOwner, view);
        }
        else if(option == 4) {
            runProgram();
        }
        else {
            view.wrongNumber();
            businessOwnerMenu2(username); //se face o alta alegere din meniu pt ca optiunea nu a fost valida
        }

    }

    public void organiserMenu2(String username) {
        int option = view.organiserMenu();
        Organiser organiser=organisers.findById(username);

        if(option==1){
            organiserController.createOffer(organiser,view);
        }
        else if(option==2) {
            BusinessOwner businessOwner=view.showBusinessOwner(); //trb implementata in view
            Offer offer=view.createOfferView();
            organiserController.sendOffer(businessOwner,offer);
        }
        else if(option==3) {

            //cine e businessOwner-ul din fct? sau poate nu sunt ok parametrii de la showALlAds
            organiserController.showAllAds(businessOwners,view);
        }
        else if(option==4){
            runProgram();
        }else {
            view.wrongNumber();
            organiserMenu2(username); //se face o alta alegere din meniu pt ca optiunea nu a fost valida
        }
    }
    public void runProgram() {
        while(true) {
            int option = view.welcomeView();
            if(option == 1) {
               login();
            } else if (option == 2) {
               signUp();
            } else if (option == 0) {
                break;
            }
        }
    }
}
