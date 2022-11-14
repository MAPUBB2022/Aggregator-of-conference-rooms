package Controller;

import repo.AdRepository;
import repo.BusinessOwnerRepository;
import repo.OrganiserRepository;
import view.View;

import java.util.ArrayList;

public class Server {

    private final View view; //se duce in constructorul de la b.o.Controller
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
        ArrayList<String> credentials = view.loginView(); //string de credentiale  format din (tipUser + username + passw)

        if (credentials.get(0).equals("1") && organisers.findByUsernameAndPassword(credentials.get(1), credentials.get(2)) != null) { //daca e org si s-a gasit in bd (are cont)
            this.organiserMenu2(credentials.get(1)); //se dechide meniul pt organiser
        } else if (credentials.get(0).equals("2") && businessOwners.findByUsernameAndPassword(credentials.get(1), credentials.get(2)) != null) { //daca e b.o. si are deja cont
            this.businessOwnerMenu2(credentials.get(1));
        } else {
            view.wrongCredentials(); //apare msj ca username ul sau parola sunt gresite
            login();
        }


    }
    public void signUp() {
        ArrayList<String> credentials = view.signupView(); //ret un string format din datele de la SignUp
        if(credentials.get(0).equals("1")) { //daca in string ul de credetiale, e organiser
            OrganiserRepository.getInstance().add(organiserController.createUser(credentials)); //se creeaza un user cu acele credentiale => se ret un org si apoi se adauga la acea instanta unica
            view.userCreatedSuccessfully();
            login();
        }
        else if (credentials.get(0).equals("2")) { //daca in string ul de credetiale, e b.o
            BusinessOwnerRepository.getInstance().add(businessOwnerController.createUser(credentials)); //se creeaza un user cu acele credentiale => se ret un b.o. si apoi se adauga la acea instanta unica
            view.userCreatedSuccessfully();
            login();
        }
        else {
            view.somethingWentWrong();
        }
    }

    public void businessOwnerMenu2(String username) {
        int option = view.businessOwnerMenu(); //se alege o opt din meniu b.o.
        businessOwnerController.setBusinessOwner(businessOwners.findById(username)); //se cauta b.o dupa id in lista de b.o si se set noul b.o (cel gasit)

        if(option == 1) {
            businessOwnerController.showAds();
            businessOwnerMenu2(username); //se poate face o noua alegere din meniu
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
        else if(option==6){
            businessOwnerController.deleteAd();
            businessOwnerMenu2(username);
        }
        else if(option==7)
        {
            businessOwnerController.modifyAd();
            businessOwnerMenu2(username);
        }
        else if(option == 8) {
            runProgram();
        }
        else {
            view.wrongNumber();
            businessOwnerMenu2(username); //se face o alta alegere din meniu pt ca optiunea nu a fost valida
        }

    }

    public void organiserMenu2(String username) {
        int option = view.organiserMenu(); //se alege o opt din meniu org
        organiserController.setOrganiser(organisers.findById(username)); //se cauta org dupa id in lista de org si se set noul org (cel gasit)

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
            int option = view.welcomeView(); //se alege din exact primul meniu o opt -login/signup/exit
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
