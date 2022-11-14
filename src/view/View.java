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

    //met ce returneaza un string de credentiale format din DI (tipUser + username + passw)
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

        //adaugam in string ul de credentials, tip user(b.o/org) + username + passw
        credentials.add(userType);
        credentials.add(username);
        credentials.add(password);
        return credentials;
    }

    public void wrongCredentials() {
        System.out.println("Wrong username or password please try again");
    }

    //metoda ce ret un string de credentiale obtinute din signUp (cu validarile necesare de username daca acesta a mai fost folosit sau nu)
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
            else { //gaseste un username nou, adica il accepta pe cel introdus
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
                wrongNumber();
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


    public int businessOwnerMenu() {
        Scanner input = new Scanner(System.in);

        System.out.println("Select: ");
        System.out.println("1. Show your Ads");
        System.out.println("2. Show new Messages");
        System.out.println("3. Show all Messages");
        System.out.println("4. Show all Offers");
        System.out.println("5. Create ad");
        System.out.println("6. Delete ad");
        System.out.println("7. Modify ad");
        System.out.println("8 Log out");
        int option = input.nextInt();

        return option;
    }

    public int organiserMenu(){
        Scanner input=new Scanner(System.in);

        System.out.println("Select: ");
        System.out.println("1. Show all ads");
        System.out.println("2. Show new offers"); // dupa status
        System.out.println("3. Show sent messages");
        System.out.println("4. Show all offers");
        System.out.println("5. Send message to ask for an offer");
        System.out.println("6. Log out ");
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
        System.out.println("Offer Id: " + offer.getIdMessage());
        System.out.println("Status: " + offer.getStatus());
        System.out.println("Price: " + offer.getPrice());
        System.out.println("Starting Date: " + offer.getStartingDate());
        System.out.println("Ending Date: " + offer.getEndingDate());
        System.out.println("Description: " + offer.getDescription());
        System.out.println("Ad Id: " + offer.getAd().getIdAd()+"-Product "+offer.getAd().getProduct().getName()+"\n");
    }

    //metoda org pe care o trimite b.o.
    public void showMessage(Message message) {
        System.out.println("Ad Id: " + message.getAd().getIdAd()+"-Product "+message.getAd().getProduct().getName());
        System.out.println("Status: " + message.getStatus());
        System.out.println("Starting Date: " + message.getStartingDate());
        System.out.println("Ending Date: " + message.getEndingDate());
        System.out.println("Guests' number: " + message.getGuests());
        System.out.println("Description: " + message.getDescription()+"\n");

    }

    public Integer selectTypeOfProduct() {

        boolean ok = true;
        Scanner input = new Scanner(System.in);
        Integer option = null;


        while (ok) {
            System.out.println("Choose the type of the service you offer, modify or delete: ");
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
    public Ad createAdView() {
        Scanner input = new Scanner(System.in);

        System.out.println("Product: ");

        //se ret tipul de produs pe care il ofera businessOwner ul
        System.out.println("Enter the values of the product you offer: ");
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

    public Ad deleteAdView(){
        Scanner input = new Scanner(System.in);

        System.out.println("Product: ");

        //se ret tipul de produs pe care il sterge businessOwner ul
        Integer option = selectTypeOfProduct();
        Calendar calendar = new Calendar();
        System.out.println("Enter the new values of the product you delete: ");
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

    public Ad modifyAdView(){
        Scanner input = new Scanner(System.in);

        System.out.println("Product: ");

        //se ret tipul de produs pe care il modifica businessOwner ul
        Integer option = selectTypeOfProduct();
        Calendar calendar = new Calendar();
        System.out.println("Enter the new values of the product you modify: ");
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

    public Message createMessageView() {
        Scanner input = new Scanner(System.in);
        Ad adInMessage = null;

        System.out.println("Start Date: ");
        String startDate = input.nextLine();
        System.out.println("End Date: ");
        String endDate=input.nextLine();

        System.out.println("Description: ");
        String description=input.nextLine();

        while (true) {
            System.out.println("AdId: ");
            Integer adInOfferId = input.nextInt();
            adInMessage = AdRepository.getInstance().findById(adInOfferId); //se ad in adInMessage ad ul cautat dupa id dat ca DI
            if (adInMessage != null) { //daca s-a gasit id-ul se opreste bucla
                break;
            } else {
                System.out.println("Please enter a valid Id!"); //daca se da un id gresit se tot repeta pana gaseste unul existent
            }
        }

        Integer guests = null;
        while (true) {
            System.out.println("Number of guests: ");
            guests = input.nextInt();
            if (adInMessage.getProduct() instanceof Hall) { //daca prod din anuntul din msj e o instanta a salii, caci doar la sala ai nr de invitati
                if(guests <= ((Hall) adInMessage.getProduct()).getCapacity()) { //daca incap invitatii in sala
                    break;
                }
                else {
                    System.out.println("Too many guest for the Hall");
                    System.out.println("Maximum capacity is "+ ((Hall) adInMessage.getProduct()).getCapacity());
                    System.out.println("Please enter a smaller value if you want an offer!");
                }
            }
            else { //daca prod din anuntul din msj NU e o instanta a salii
                break;
            }
        }

        Message newMessage=new Message(adInMessage, startDate, endDate, guests, description);

        return newMessage;

    }

    //oferta de creare e un msj cu un pret si o descriere
    public Offer createOfferView(Message message) {
        Scanner input = new Scanner(System.in);

        System.out.println("Description: ");
        String description = input.nextLine();

        System.out.println("Price: ");
        Integer price = input.nextInt();

        Offer newOffer = new Offer(message.getAd(), message.getStartingDate(), message.getEndingDate(), message.getGuests(), description, price);

        return newOffer;
    }

    //metoda pt organiser de a accepta oferta (dupa id) a businessowner ului facuta de b.o.
    public Integer chooseOfferToAccept(){
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the id of the offer you want to accept");
        return input.nextInt();

    }

    public void noNewMessages() {
        System.out.println("Nothing new...");
        System.out.println("Check again later\n");
    }


    public void askOfferMaking() {
        System.out.println("Do you want to make an offer? (Yes/No)");
    }

    public void askOfferAccepting() {
        System.out.println("Do you want to accept an offer? (Yes/No)");
    }

    public boolean answer() {
        Scanner input = new Scanner(System.in);
        while(true) {
            String answer = input.nextLine();
            if (answer.equals("yes") || answer.equals("y") || answer.equals("Yes"))
                return true;
            if (answer.equals("no") || answer.equals("n") || answer.equals("No"))
                return false;
            System.out.println("Please select Yes or No");
        }
    }

    public void noMessages() {
        System.out.println("You don't have any messages");
        System.out.println("Check again later\n");
    }

    public void noOffers() {
        System.out.println("You haven't made any offer yet\n");
    }

    public void noSentMessages() {
        System.out.println("You haven't sent any messages yet\n");
    }
    public void messageSent() {
        System.out.println("Message sent successfully!\n");
    }

    public void offerSent() {
        System.out.println("Offer sent successfully!\n");
    }

    public void messageDeclined() {
        System.out.println("Message declined!\n");
    }
    public void offerDeclined() {
        System.out.println("Offer declined!\n");
    }

    public void offerAccepted() {
        System.out.println("Offer accepted!\n");
    }
}
