package view;

import interfaces.AdRepositoryInterface;
import model.Ad;
import model.BusinessOwner;
import repo.AdRepository;
import repo.BusinessOwnerRepository;

import java.util.ArrayList;
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
