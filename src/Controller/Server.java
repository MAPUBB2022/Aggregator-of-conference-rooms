package Controller;

import repo.AdRepository;
import repo.BusinessOwnerRepository;
import repo.OrganiserRepository;
import view.View;

import java.util.ArrayList;

public class Server {

    private final View view;
    private OrganiserRepository organisers;
    private BusinessOwnerRepository businessOwners;
    private OrganiserController organiserController;
    private BusinessOwnerController businessOwnerController;

    public Server() {
        this.view = new View();
        this.organisers = OrganiserRepository.getInstance();
        this.businessOwners = BusinessOwnerRepository.getInstance();
        this.organiserController = new OrganiserController(this.view);
        this.businessOwnerController = new BusinessOwnerController(this.view);
    }

    public void login() {
        ArrayList<String> credentials = view.loginView();

        if (credentials.get(0).equals("1") && organisers.findByUsernameAndPassword(credentials.get(1), credentials.get(2)) != null) {
            this.organiserMenu2(credentials.get(1));
        } else if (credentials.get(0).equals("2") && businessOwners.findByUsernameAndPassword(credentials.get(1), credentials.get(2)) != null) {
            this.businessOwnerMenu2(credentials.get(1));
        } else {
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
        businessOwnerController.setBusinessOwner(businessOwners.findById(username));

        if(option == 1) {
            businessOwnerController.showAds();
            businessOwnerMenu2(username);
        }
        else if(option == 2) {
            businessOwnerController.newMessagesMenu();
            businessOwnerMenu2(username);
        }
        else if(option == 3) {
            businessOwnerController.allMessagesMenu();
            businessOwnerMenu2(username);
        }
        else if(option == 4) {
            businessOwnerController.showOffers();
            businessOwnerMenu2(username);
        }
        else if(option == 5) {
            businessOwnerController.createAd();
            businessOwnerMenu2(username);
        }
        else if(option == 6) {
            runProgram();
        }
        else {
            view.wrongNumber();
            businessOwnerMenu2(username); //se face o alta alegere din meniu pt ca optiunea nu a fost valida
        }

    }

    public void organiserMenu2(String username) {
        int option = view.organiserMenu();
        organiserController.setOrganiser(organisers.findById(username));

        if(option == 1){
            organiserController.showAllAds();
            organiserMenu2(username);
        }
        else if(option == 2) {
            organiserController.showNewOffersMenu();
            organiserMenu2(username);
        }
        else if(option == 3) {
            organiserController.showSentMessages();
            organiserMenu2(username);
        }
        else if(option == 4) {
            organiserController.showReceivedOffers();
            organiserMenu2(username);
        }
        else if(option == 5){
            organiserController.sendMessage();
            view.messageSent();
            organiserMenu2(username);
        }
        else if(option == 6) {
            runProgram();
        }
        else {
            view.wrongNumber();
            organiserMenu2(username); //se face o alta alegere din meniu pt ca optiunea nu a fost valida
        }
    }
    public void runProgram(){
        while(true) {
            int option = view.welcomeView();
            if(option == 1) {
               login();
            } else if (option == 2) {
               signUp();
            } else if (option == 0) {
                break;
            } else {
                view.wrongNumber();
            }
        }
    }
}
