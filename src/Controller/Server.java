package Controller;

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
    }

    private final View view;

    private OrganiserRepository organisers;

    private BusinessOwnerRepository businessOwners;

    private AdRepository ads;


//    public boolean validateCredentials(ArrayList<String> credentials) {
//
//        if(credentials.get(0).equals("1")) {
//            return organisers.findByUsernameAndPassword(credentials.get(1), credentials.get(2)) != null;
//        }
//
//        return businessOwners.findByUsernameAndPassword(credentials.get(1), credentials.get(2)) != null;
//    }
    public void runProgram() {
        while(true) {
            int option = view.welcomeView();

            if(option == 1) {
                ArrayList<String> credentials = view.loginView();
                if(credentials.get(0).equals("1")) {
                    if(organisers.findByUsernameAndPassword(credentials.get(1), credentials.get(2)) != null) {
                        view.adsView(ads);
                    }
                    else {
                        view.wrongCredentials();
                        runProgram();
                    }
                } else if (credentials.get(0).equals("2")) {
                    if(businessOwners.findByUsernameAndPassword(credentials.get(1), credentials.get(2)) != null) {
                        view.businessOwnerMenu();
                    }
                    else {
                        view.wrongCredentials();
                        runProgram();
                    }
                }

            } else if (option == 0) {
                break;

            }
        }
    }
}
