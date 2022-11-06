package view;

import interfaces.AdRepositoryInterface;
import model.BusinessOwner;
import model.Organiser;
import model.User;
import repo.BusinessOwnerRepository;
import repo.OrganiserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class View {
    public int welcomeView() {
        Scanner input = new Scanner(System.in);

        System.out.println("Welcome!");
        System.out.println("Select oprion: ");
        System.out.println("0. Exit");
        System.out.println("1. Login");
        System.out.println("2. SignUp");
        int option = input.nextInt();

        return option;
    }

    public ArrayList<String> loginView() {
        Scanner input = new Scanner(System.in);
        ArrayList<String> credentials = new ArrayList<>();

        System.out.println("Select:");
        System.out.println("1-event organiser");
        System.out.println("2-business owner");
        String userType = input.nextLine();

        System.out.println("username: ");
        String username = input.nextLine();
        System.out.println("password: ");
        String password = input.nextLine();

        credentials.add(userType);
        credentials.add(username);
        credentials.add(password);
        return credentials;
    }

    public void wrongCredentials() {
        System.out.println("Wrong username or password please try again");
    }

    public ArrayList<String> signupView() {
        Scanner input = new Scanner(System.in);
        System.out.println("Sign up form: ");
        System.out.println("First Name:");
        String firstName = input.nextLine();
        System.out.println("Last Name: ");
        String lastName = input.nextLine();
        boolean ok = true;
        String username = null;
        while(ok) {
            System.out.println("username: ");
            username = input.nextLine();
            if(BusinessOwnerRepository.getInstance().findById(username) != null || OrganiserRepository.getInstance().findById(username) != null) {
                System.out.println("Unavailable username please choose another one");
            }
            else {
                ok = false;
            }
        }
        System.out.println("Password: ");
        String password = input.nextLine();
        String userType = null;
        ok = true;
        while(ok) {
            System.out.println("I am a: ");
            System.out.println("1-event organiser");
            System.out.println("2-business owner");
            userType = input.nextLine();
            if(userType.equals("1") || userType.equals("2")) {
                ok = false;
            }
            else {
                System.out.println("Wrong answer! Please choose 1 or 2");
            }
        }

        ArrayList<String> credentials = new ArrayList<>(Arrays.asList(userType, firstName, lastName, username, password));
        return credentials;

    }

    public void userCreatedSuccessfully() {
        System.out.println("User created Successfully!");
        System.out.println("Please login");
    }

    public void adsView(AdRepositoryInterface ads) {
    }

    public int businessOwnerMenu() {
        Scanner input = new Scanner(System.in);

        System.out.println("Select: ");
        System.out.println("1. Received offers");
        System.out.println("2. Your Ads");
        System.out.println("3. Create ad");
        int option = input.nextInt();

        return option;
    }

//    public void printBusinessOwners(){
//        for(BusinessOwnerRepository businessOwner : BusinessOwnerRepository.getInstance()) {
//            System.out.println("First "+businessOwner.getFirstName());
//            System.out.println("Lastname "+businessOwner.getLastName());
//            System.out.println("Username "+businessOwner.getUsername());
//        }
//    }


}
