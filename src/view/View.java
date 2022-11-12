package view;

import interfaces.AdRepositoryInterface;
import interfaces.UserControllerInterface;
import model.*;
import model.Calendar;
import repo.AdRepository;
import repo.BusinessOwnerRepository;
import repo.OrganiserRepository;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class View {
    public int welcomeView() {
        Scanner input = new Scanner(System.in); //cls Scanner e folosita pt a lua input-ul user ului

        System.out.println("Welcome!");
        System.out.println("Select option: ");
        System.out.println("0. Exit");
        System.out.println("1. Login");
        System.out.println("2. SignUp");

        int option = input.nextInt(); //nextInt() - citeste o valoare int a user ului

        return option;
    }

    public ArrayList<String> loginView() {
        Scanner input = new Scanner(System.in);
        ArrayList<String> credentials = new ArrayList<>();

        System.out.println("Select:");
        boolean ok = true;
        String userType = "";
        while (ok) {
            System.out.println("1-event organiser");
            System.out.println("2-business owner");
            userType = input.nextLine(); //nextLine() - citeste un string a user-ului
            if(userType.equals("1") || userType.equals("2")) {
                ok = false;
            }
            else {
                wrongNumber();
            }
        }

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

        while(ok) { //while(ok==true)

            System.out.println("username: ");
            username = input.nextLine();

            //in bd avem deja un username existent pt un oarecare utiliz, nu putem avea 2 cu acelasi username
            //deci, se incearca un username pana se gaseste unul diferit de toate

            if(BusinessOwnerRepository.getInstance().findById(username) != null || OrganiserRepository.getInstance().findById(username) != null) {
                System.out.println("Unavailable username, please choose another one");
            }
            else { //gaseste un username nou
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

    public void somethingWentWrong() {
        System.out.println("Something went wrong...");
        System.out.println("Please try again later!");
    }
    public void userCreatedSuccessfully() {
        System.out.println("User created Successfully!");
        System.out.println("Please login");
    }

    public void wrongNumber() {
        System.out.println("Please choose a valid option");
    }

    public void adsView(AdRepositoryInterface ads) {
    }

    public int businessOwnerMenu() {
        Scanner input = new Scanner(System.in);

        System.out.println("Select: ");
        System.out.println("1. Received offers");
        System.out.println("2. Your Ads");
        System.out.println("3. Create ad");
        System.out.println("4. Log out");
        int option = input.nextInt();

        return option;
    }

    public int organiserMenu(){
        Scanner input=new Scanner(System.in);

        System.out.println("Select: ");
        System.out.println("1. Show all ads");
        System.out.println("2. Show accepted offers");
        System.out.println("3. Create an offer");
        System.out.println("4. Log out ");
        int option=input.nextInt();

        return option;

    }

    public void noOffersReceived() {
        System.out.println("You haven't received any offer");
    }

    public void showProduct(Product product){
        System.out.println("Product: " + product.getName());
        System.out.println("Rating: " + product.getRating());
        System.out.println("Description: " + product.getDescription() + "\n");

    }

    public void showCalendar(Calendar calendar){

    }
    public void showAd(Ad ad) {
        System.out.println("Ad Id: " + ad.getIdAd());
        showProduct(ad.getProduct());
        showCalendar(ad.getCalendar());
    }
    public void showOffer(Offer offer) {
        System.out.println("Offer Id: " + offer.getIdOffer());
        System.out.println("Starting Date: " + offer.getStartingDate());
        System.out.println("Ending Date: " + offer.getEndingDate());
        System.out.println("Description: " + offer.getDescription());
        System.out.println("Ad Id: " + offer.getAdInOffer().getIdAd()+"-Product "+offer.getAdInOffer().getProduct().getName());
    }
       public Ad createAdView() {
        Scanner input = new Scanner(System.in);

        System.out.println("Product: ");
        Integer option = selectTypeOfProduct();
        Calendar calendar = new Calendar();
        if(option == 1) {
            Hall hall = createHallView();
            Ad ad = new Ad(hall, calendar);
            return ad;
        }
        if(option == 2) {
            DJ dj = createDJView();
            Ad ad = new Ad(dj, calendar);
            return ad;
        }
        if(option == 3) {
            CandyBar candyBar = createCandyBarView();
            Ad ad = new Ad(candyBar, calendar);
            return ad;
        }
        return null;
    }

    public Hall createHallView() {
        Scanner input = new Scanner(System.in);
        System.out.println("Name: ");
        String name = input.nextLine();
        System.out.println("Description: ");
        String description = input.nextLine();
        System.out.println("Location: ");
        String location = input.nextLine();
        System.out.println("Capacity: ");
        Integer capacity = input.nextInt();

        Hall hall = new Hall(name, description, location, capacity);
        return hall;
    }

    public DJ createDJView() {
        Scanner input = new Scanner(System.in);
        System.out.println("Name: ");
        String name = input.nextLine();
        System.out.println("Description: ");
        String description = input.nextLine();
        // facut teste sa raspunda corect
        boolean lights = false;
        System.out.println("Lights: yes/no");
        String answer = input.nextLine();
        if(answer.equals("yes") || answer.equals("y")) {
            lights = true;
        }

        boolean stereo = false;
        System.out.println("Stereo: yes/no");
        answer = input.nextLine();
        if(answer.equals("yes") || answer.equals("y")) {
            stereo = true;
        }
        DJ dj = new DJ(name, description, lights, stereo);
        return dj;
    }

    public CandyBar createCandyBarView() {
        Scanner input = new Scanner(System.in);
        System.out.println("Name: ");
        String name = input.nextLine();
        System.out.println("Description: ");
        String description = input.nextLine();
        System.out.println("Add sweets: ");
        boolean ok = true;
        ArrayList<String> sweets = new ArrayList<>();

        while (ok) {
            String sweet = input.nextLine();
            if(sweet.equals(" ") || sweet.equals("\n") || sweet.equals("")) {
                ok = false;
            }
            sweets.add(sweet);
        }

        CandyBar candyBar = new CandyBar(name, description, sweets);

        return candyBar;
    }


    public Integer selectTypeOfProduct() {

        boolean ok = true;
        Scanner input = new Scanner(System.in);
        Integer option = null;


        while (ok) {
            System.out.println("Choose the type of the service you offer: ");
            System.out.println("1- Hall renting");
            System.out.println("2- Dj");
            System.out.println("3- Candybar");
            option = input.nextInt();
            if(option == 1) {
                return 1;
            }
            if(option == 2) {
                return 2;
            }
            if (option == 3) {
                return 3;
            }
            wrongNumber();

        }
        somethingWentWrong();
        return null;
    }
    public Offer createOfferView(AdRepository ads) throws ParseException {
        Scanner input = new Scanner(System.in);
        System.out.println("Start Date: ");
        String startDate=input.nextLine();
        System.out.println("End Date: ");
        String endDate=input.nextLine();
        System.out.println("Description: ");
        String description=input.nextLine();
        System.out.println("Add ads: ");
        Integer adInOfferId = input.nextInt();

        //SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

        //String dateInString = "7-Jun-2013"; -> string de forma aceasta vom da ca DI
//        String start = formatter.parse(start_date);
//        Date end=formatter.parse(end_date);

//        boolean ok=true;
//        while (ok) {
//            Integer adId = input.nextInt();
//
//            //ad trebuie convertit la Ad pt ca adsInOffer e o lista de Ad, nu de stringuri
//
//            //var array = ad.split(" ");
//            //String getIdFromString=array[0];
//            //Integer idd=Integer.parseInt(getIdFromString);
//
//            adsInOfferIds.add(adId);
//        }

        Ad adInOffer = ads.findById(adInOfferId);

//        for(Integer adId : adsInOfferIds) {
//            if(ads.findById(adId) != null) {
//                adsInOffer.add(ads.findById(adId));
//            }
//        }

        Offer new_offer=new Offer(startDate,endDate,description,adInOffer);
        return new_offer;

    }

    public boolean acceptOfferView() {
        Scanner input = new Scanner(System.in);
        System.out.println("Do you want to accept an offer? (Yes/No)");
        while(true) {
            String answer = input.nextLine();
            if (answer.equals("yes") || answer.equals("y") || answer.equals("Yes"))
                return true;
            if (answer.equals("no") || answer.equals("n") || answer.equals("No"))
                return false;
            System.out.println("Please select Yes or No");
        }

    }


    public Integer chooseOfferToAccept(){
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the id of the offer you want to accept");
        return input.nextInt();

    }
}
