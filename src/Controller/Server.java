package Controller;


import interfaces.AdRepositoryInterface;
import interfaces.BusinessOwnerRepositoryInterface;
import interfaces.OrganiserRepositoryInterface;
import view.View;

import java.util.ArrayList;

public class Server {

    private View view;

    private OrganiserRepositoryInterface organisers;

    private BusinessOwnerRepositoryInterface businessOwners;

    private AdRepositoryInterface ads;


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


            }
        }
    }
}
