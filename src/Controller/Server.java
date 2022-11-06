package Controller;

import interfaces.BusinessOwnerRepositoryInterface;
import model.BusinessOwner;
import repo.AdRepository;
import repo.BusinessOwnerRepository;
import repo.OrganiserRepository;
import view.View;

import java.util.ArrayList;

public class Server {
    public Server() {
        this.view = new View();
        this.organisers = OrganiserRepository.getInstance();
        this.businessOwners = BusinessOwnerRepository.getInstance();
        this.ads = AdRepository.getInstance();
        this.organiserController = new OrganiserController();
        this.businessOwnerController = new BusinessOwnerController();
    }

    private final View view;

    private OrganiserRepository organisers;

    private BusinessOwnerRepository businessOwners;

    private AdRepository ads;

    private OrganiserController organiserController;

    private BusinessOwnerController businessOwnerController;


    public void login() {
        ArrayList<String> credentials = view.loginView();
        if(credentials.get(0).equals("1") && organisers.findByUsernameAndPassword(credentials.get(1), credentials.get(2)) != null) {
            view.adsView(ads);
        }
        else if (credentials.get(0).equals("2") && businessOwners.findByUsernameAndPassword(credentials.get(1), credentials.get(2)) != null) {
            view.businessOwnerMenu();
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
