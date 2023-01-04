package view;


import Controller.Server;
import model.*;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class View {

    private final Server server;
    public View(EntityManager manager) {

        this.server = new Server(manager);
    }


    public void runProgram() throws InvalidDataException {
        while(true) {
            int option = welcomeView(); //se alege din exact primul meniu o opt -login/signup/exit
            if(option == 1) {
                loginMenu();
            } else if (option == 2) {
                signUpMenu();
            } else if (option == 0) {
                break;
            } else {
                //wrongNumber();
                throw new InvalidDataException("The number you typed is invalid!");
            }
        }
    }

    public void signUpMenu() throws InvalidDataException {
        List<String> credentials = signupView(); //ret un string format din datele de la SignUp
        boolean flag = server.signUp(credentials);
        if(flag) {
            userCreatedSuccessfully();
            loginMenu();
        }
        else{
            //somethingWentWrong();
            throw new InvalidDataException("Something went wrong...! Please try again later!");
        }
    }

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
    public List<String> loginView() throws InvalidDataException {
        Scanner input = new Scanner(System.in);
        List<String> credentials = new ArrayList<>();

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
                //wrongNumber();
                throw new InvalidDataException("The number you typed is invalid!");
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

    public void loginMenu() throws InvalidDataException {
        List<String> credentials = loginView(); //string de credentiale  format din (tipUser + username + passw)
        boolean isUser = server.login(credentials);
        if(isUser) {
            if(credentials.get(0).equals("1"))
                organiserMenu(credentials.get(1));
            else if(credentials.get(0).equals("2"))
                businessOwnerMenu(credentials.get(1));
        }
        else {
            //wrongCredentials();
            loginMenu();
            throw new InvalidDataException("Wrong username or password please try again");
        }
    }



    public void businessOwnerMenu(String username) throws InvalidDataException {
        int option = businessOwnerView(); //se alege o opt din meniu b.o.
        server.setBusinessOwnerInController(username);
        server.getBusinessOwnerController().getBusinessOwner();
        if(option == 1) {
           showProducts(server.getBusinessOwnerController().getBusinessOwnerProducts());
            businessOwnerMenu(username);
        }
        else if(option == 2) {
            newMessagesMenu();
            businessOwnerMenu(username);
        }
        else if(option == 3) {
            showReceivedMessagesSubmenu();
            businessOwnerMenu(username);
        }
        else if(option == 4) {
            showSentOffersSubmenu();
            businessOwnerMenu(username);
        }
        else if(option == 5) {
            createProductMenu();
            businessOwnerMenu(username);
        }
        else if(option==6){
            deleteProductMenu();
            businessOwnerMenu(username);
        }
        else if(option==7) {
            modifyProductMenu();
            businessOwnerMenu(username);
        }
        else if(option == 8) {
            return;
        }
        else {
            businessOwnerMenu(username);
            //wrongNumber(); //se face o alta alegere din meniu pt ca optiunea nu a fost valida
            throw new InvalidDataException("The number you typed is invalid!");
        }
    }

    public void newMessagesMenu() throws InvalidDataException {
        if (server.getBusinessOwnerController().checkNewMessages()) { //daca lista de oferte cerute a b.o. e goala
            //noNewMessages(); //nu exista msj nou
            throw new InvalidDataException("Nothing new...Check again later!");
        }
        List<Message> messages = new CopyOnWriteArrayList<Message>(server.getBusinessOwnerController().getBusinessOwner().getReceivedMessages());
        for (Message message : messages) { //pt fiecare mesaj a org catre b.o. din lista de oferte cerute
            if(message.getStatus().equals(Status.SENT)) { //daca starea msj e de SENT
                showMessage(message); //vezi msj
                askOfferMaking(); //apare msj daca vrei sa faci o oferta
                boolean answer = answer(); //se ret rasp true/false
                if (answer) {
                    makeOfferMenu(message); //se face oferta
                    offerSent(); //apare msj de oferta creata cu succes
                } else { //daca rasp e nu
                    server.getBusinessOwnerController().declineMessage(message); //se set starea msj la DECLINED
                    //messageDeclined(); //apare msj de declined
                    throw new InvalidDataException("Message declined!\n");
                }
            }
        }
    }

    public void makeOfferMenu(Message message) {
        Offer offer = createOfferView(message);
        server.getBusinessOwnerController().makeOffer(offer, message);
    }

    public List<Message> checkForReceivedMessages() throws InvalidDataException {
        if (server.getBusinessOwnerController().checkReceivedMessages()) { //daca lista de oferte cerute a b.o. e goala
            //noMessages();
            throw new InvalidDataException("Nothing new...Check again later!");
            //return null;
        }
        return server.getBusinessOwnerController().getBusinessOwner().getReceivedMessages();
    }

    public Integer showReceivedMessagesView() {
        Scanner input = new Scanner(System.in);

        System.out.println("Select option: ");
        System.out.println("1. Show accepted messages");
        System.out.println("2. Show declined messages");
        System.out.println("3. Show messages by sender");
        System.out.println("4. Exit");


        Integer option = input.nextInt();

        return option;

    }

    public void showReceivedMessagesSubmenu() throws InvalidDataException {
        List<Message> messages= checkForReceivedMessages();
        if(messages != null) {
            showMessages(messages);
            boolean ok = true;
            while (ok) {
                Integer option = showReceivedMessagesView();
                if(option == 1) {
                    showMessages(server.getBusinessOwnerController().filterByAcceptedMessages());
                }
                else if(option == 2) {
                    showMessages(server.getBusinessOwnerController().filterByDeclinedMessages());
                }
                else if(option == 3) {
                    String username = getSenderUsername();
                    showMessages(server.getBusinessOwnerController().filterMessagesBySenderUsername(username));
                }
                else if(option == 4) {
                    ok = false;
                }
                else {
                    //wrongNumber();
                    throw new InvalidDataException("The number you typed is invalid!");
                }
            }
        }

    }

    public List<Offer> checkForSentOffers() throws InvalidDataException {
        if(server.getBusinessOwnerController().checkSentOffers()) { //daca lista de oferte trimise a b.o. e goala
            //noOffers(); //apare msj ca nu ai ce oferte sa vezi
            throw new InvalidDataException("You haven't made any offer yet\n");
            //return null;
        }
        return server.getBusinessOwnerController().getBusinessOwner().getSentOffers();
    }

    public Integer showSentOffersView() {
        Scanner input = new Scanner(System.in);

        System.out.println("Select option: ");
        System.out.println("1. Show accepted offers");
        System.out.println("2. Show declined offers");
        System.out.println("3. Exit");


        Integer option = input.nextInt();

        return option;
    }
    public void showSentOffersSubmenu() throws InvalidDataException {
        List<Offer> offers= checkForSentOffers();
        if(offers != null) {
            showOffers(offers);
            boolean ok = true;
            while (ok) {
                Integer option = showSentOffersView();
                if(option == 1) {
                    showOffers(server.getBusinessOwnerController().filterByAcceptedOffers());
                }
                else if(option == 2) {
                    showOffers(server.getBusinessOwnerController().filterByAcceptedOffers());
                }
                else if(option == 3) {
                    ok = false;
                }
                else {
                    //wrongNumber();
                    throw new InvalidDataException("The number you typed is invalid!");
                }
            }
        }
    }

    public void createProductMenu() throws InvalidDataException {
        Product createdProduct = createProductView();
        server.getBusinessOwnerController().createProduct(createdProduct);
    }

    public void deleteProductMenu() throws InvalidDataException{
        Integer idProduct = getBusinessOwnerProductId();
        server.getBusinessOwnerController().removeProduct(idProduct);
        server.getProductController().deleteProduct(idProduct);
    }

    public void modifyProductMenu() throws InvalidDataException {
        Integer idProduct = getBusinessOwnerProductId();
        System.out.println("You can modify just products of the same type.");
        System.out.println("If you want to modify the type please delete the product and create one new.");
        System.out.println("Do you want to change the type of the product? (yes/ no)");
        boolean flag = answer();
        if(flag) {
                System.out.println("Select the id of the product you want to delete");
                deleteProductMenu();
                createProductMenu();
        }else {

            Product oldProduct = server.getProductController().getProduct(idProduct);
            if(oldProduct instanceof Hall) {
                Hall hall = createHallView();
                server.getProductController().modifyHall((Hall) oldProduct, hall);
            }
            else if(oldProduct instanceof DJ) {
                DJ dj = createDJView();
                server.getProductController().modifyDj((DJ) oldProduct, dj);
            }
            else if(oldProduct instanceof CandyBar) {
                CandyBar candyBar = createCandyBarView();
                server.getProductController().modifyCandyBar((CandyBar) oldProduct, candyBar);
            }
            else {
                //somethingWentWrong();
                throw new InvalidDataException("Something went wrong...! Please try again later!");
            }

        }

    }

    public void organiserMenu(String username) throws InvalidDataException {
        int option = organiserView(); //se alege o opt din meniu org
        server.setOrganiserInController(username);
        server.getOrganiserController().getOrganiser();
        if(option == 1){
            showProducts(server.getProductController().getProducts());
            organiserMenu(username);
        }
        else if(option == 2) {
           showNewOffersMenu();
           organiserMenu(username);
        }
        else if(option == 3) {
            showSentMessagesSubmenu();
            organiserMenu(username);
        }
        else if(option == 4) {
            showReceivedOffersSubmenu();
            organiserMenu(username);
        }
        else if(option == 5){
            sendMessageMenu();
            messageSent();
            organiserMenu(username);
        }
        else if(option == 6) {
            return;
        }
        else {
            organiserMenu(username);
            //wrongNumber(); //se face o alta alegere din meniu pt ca optiunea nu a fost valida
            throw new InvalidDataException("The number you typed is invalid!");
        }
    }


    public void showNewOffersMenu() throws InvalidDataException {
        if(server.getOrganiserController().checkNewReceivedOffers()) { //daca lista de oferte primite a org e goala
            noNewMessages(); //nu exita msj nou
        }
        else {
            List<Offer> offers = new CopyOnWriteArrayList<Offer>(server.getOrganiserController().getOrganiser().getReceivedOffers());

            for (Offer offer : offers) {
                if (offer.getStatus().equals(Status.SENT)) { //daca starea ofertei e SENT
                    showOffer(offer); //se afis oferta
                    askOfferAccepting(); //apare msj daca vrei sa accepti oferta
                    boolean answer = answer();
                    if (answer) { //daca rasp e true
                        server.getOrganiserController().acceptOffer(offer); //status ul ofertei devine ACCEPTED
                        offerAccepted(); //apare msj cu oferta acceptata
                    } else {
                        server.getOrganiserController().declineOffer(offer); //status ul ofertei devine DECLINED
                        //offerDeclined(); //apare msj cu oferta respinsa
                        throw new InvalidDataException("Offer declined!\n");
                    }
                }
            }
        }
    }

    public Integer showSentMessagesView() {
        Scanner input = new Scanner(System.in);

        System.out.println("Select option: ");
        System.out.println("1. Show accepted messages");
        System.out.println("2. Show declined messages");
        System.out.println("3. Exit");


        Integer option = input.nextInt();

        return option;

    }


    public void showSentMessagesSubmenu() throws InvalidDataException {
        List<Message> messages= checkForSentMessages();
        if(messages != null) {
            showMessages(messages);
            boolean ok = true;
            while (ok) {
                Integer option = showSentMessagesView();
                if(option == 1) {
                    showMessages(server.getOrganiserController().filterByAcceptedMessages());
                }
                else if(option == 2) {
                    showMessages(server.getOrganiserController().filterByDeclinedMessages());
                }
                else if(option == 3) {
                    ok = false;
                }
                else {
                    //wrongNumber();
                    throw new InvalidDataException("The number you typed is invalid!");
                }
            }
        }

    }

    public List<Message> checkForSentMessages() throws InvalidDataException {
        if(server.getOrganiserController().checkSentMessages()) { //daca lista de oferte cerute a org e goala
            //noSentMessages(); //apare msj ca nu ai trimis inca niciun msj
            throw new InvalidDataException("You haven't sent any messages yet\n");
            //return null;
        }
        return server.getOrganiserController().getOrganiser().getSentMessages();
    }

    public void showMessages(List<Message> messages) {
        for (Message message : messages) {
            showMessage(message);
        }
    }



    public List<Offer> checkForReceivedOffer() throws InvalidDataException {
        if(server.getOrganiserController().checkReceivedOffers()) {
            throw new InvalidDataException("You haven't sent any messages yet\n");
            //return null;
        }
        return server.getOrganiserController().getOrganiser().getReceivedOffers();
    }

    public void showOffers(List<Offer> offers) {
        for (Offer offer : offers) {
            showOffer(offer);
        }

    }

    public Integer showReceivedOffersView() {

        Scanner input = new Scanner(System.in);

        System.out.println("Select option: ");
        System.out.println("1. Show accepted offers");
        System.out.println("2. Show declined offers");
        System.out.println("3. Show offers by price ascending");
        System.out.println("4. Show offers by price descending");
        System.out.println("5. Show offers by sender");
        System.out.println("6. Exit");


        Integer option = input.nextInt();

        return option;
    }

    public String getSenderUsername() {
        Scanner input = new Scanner(System.in);
        System.out.println("Please select the name of the sender");
        String username = input.nextLine();
        return username;
    }

    public void showReceivedOffersSubmenu() throws InvalidDataException {
        List<Offer> offers= checkForReceivedOffer();
        if(offers != null) {
            showOffers(offers);
            boolean ok = true;
            while (ok) {
                Integer option = showReceivedOffersView();
                if(option == 1) {
                    showOffers(server.getOrganiserController().filterByAcceptedOffers());
                }
                else if(option == 2) {
                    showOffers(server.getOrganiserController().filterByDeclinedOffers());
                }
                else if(option == 3) {
                    showOffers(server.getOrganiserController().sortOffersByPriceAscending());
                }
                else if(option == 4) {
                    showOffers(server.getOrganiserController().sortOffersByPriceDescending());
                }
                else if(option == 5) {

                    String username = getSenderUsername();
                    showOffers(server.getOrganiserController().filterOffersBySenderUsername(username));
                }
                else if(option == 6) {
                    ok = false;
                }
                else {
                    //wrongNumber();
                    throw new InvalidDataException("The number you typed is invalid!");
                }
            }
        }
    }

    public void sendMessageMenu() throws InvalidDataException {
        Message message = createMessageView(); //se creaza msj org catre b.o.
        server.getOrganiserController().sendMessage(message);
    }

    public void wrongCredentials() {
        System.out.println("Wrong username or password please try again");
    }

    //metoda ce ret un string de credentiale obtinute din signUp (cu validarile necesare de username daca acesta a mai fost folosit sau nu)
    public List<String> signupView() throws InvalidDataException {
        Scanner input = new Scanner(System.in);
        System.out.println("Sign up form: ");
        System.out.println("First Name:");
        String firstName = input.nextLine();
        System.out.println("Last Name: ");
        String lastName = input.nextLine();

        boolean ok = true;

        String username = null;

        while(ok) { //while(ok==true)
            //in bd avem deja un username existent pt un oarecare utiliz, nu putem avea 2 cu acelasi username
            //deci, se incearca un username pana se gaseste unul diferit de toate

            System.out.println("username: ");
            username = input.nextLine();

            if(server.getBusinessOwner(username) != null || server.getOrganiser(username) != null) {
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
                //wrongNumber();
                throw new InvalidDataException("The number you typed is invalid!");
            }
        }

        List<String> credentials = new ArrayList<>(Arrays.asList(userType, firstName, lastName, username, password));
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


    public int businessOwnerView() {
        Scanner input = new Scanner(System.in);

        System.out.println("Select: ");
        System.out.println("1. Show your Products");
        System.out.println("2. Show new Messages");
        System.out.println("3. Show all Messages");
        System.out.println("4. Show all Offers");
        System.out.println("5. Create product");
        System.out.println("6. Delete product");
        System.out.println("7. Modify product");
        System.out.println("8 Log out");
        int option = input.nextInt();

        return option;
    }

    public int organiserView(){
        Scanner input=new Scanner(System.in);

        System.out.println("Select: ");
        System.out.println("1. Show all products");
        System.out.println("2. Show new offers"); // dupa status
        System.out.println("3. Show sent messages");
        System.out.println("4. Show all offers");
        System.out.println("5. Send message to ask for an offer");
        System.out.println("6. Log out ");
        int option=input.nextInt();

        return option;

    }

    public void showCandyBar(CandyBar candyBar){
        System.out.println("Id:" + candyBar.getId());
        System.out.println("Product: " + candyBar.getName());
        System.out.println("Rating: " + candyBar.getRating());
        System.out.println("Sweets: " + Arrays.asList(candyBar.getSweets()));
        System.out.println("Description: " + candyBar.getDescription() + "\n");

    }


    public void showProducts(List<Product> products) throws InvalidDataException {
        if(products.isEmpty()) {
            System.out.println("There are no products listed\n");
        }
        else {
            for(Product product : products) {
                if (product instanceof Hall) {
                    System.out.println((Hall) product);
                } else if (product instanceof DJ) {
                    System.out.println((DJ) product);
                } else if (product instanceof CandyBar) {
                    showCandyBar((CandyBar) product);
                } else {
                    //somethingWentWrong();
                    throw new InvalidDataException("Something went wrong...! Please try again later!");
                }
            }
        }
    }
    public void showOffer(Offer offer) {
        System.out.println("Offer Id: " + offer.getId());
        System.out.println("Status: " + offer.getStatus());
        System.out.println("Price: " + offer.getPrice());
        System.out.println("Description: " + offer.getDescription());
        System.out.println("Product Id: " + offer.getProduct().getId()+"-Product "+offer.getProduct().getName()+"\n");
    }

    //metoda org pe care o trimite b.o.
    public void showMessage(Message message) {
        System.out.println("Product Id: " + message.getProduct().getId()+"-Product "+message.getProduct().getName());
        System.out.println("Status: " + message.getStatus());
        System.out.println("Starting Date: " + message.getStartingDate());
        System.out.println("Ending Date: " + message.getEndingDate());
        System.out.println("Guests' number: " + message.getGuests());
        System.out.println("Description: " + message.getDescription()+"\n");

    }

    public Integer selectTypeOfProduct() throws InvalidDataException {

        boolean ok = true;
        Scanner input = new Scanner(System.in);
        Integer option = null;


        while (ok) {
            System.out.println("Choose the type of the service you create: ");
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
            //wrongNumber();
            throw new InvalidDataException("The number you typed is invalid!");

        }
        //somethingWentWrong();
        throw new InvalidDataException("Something went wrong...! Please try again later!");
        //return null;
    }
    public Product createProductView() throws InvalidDataException {
        System.out.println("Enter the values of the product you offer: ");

        Integer option = selectTypeOfProduct();
        if(option == 1) {
            return createHallView();
        }
        if(option == 2) {
            return createDJView();
        }
        if(option == 3) {
            return createCandyBarView();
        }
        return null;
    }

    public Product getProductView() throws InvalidDataException {

        Scanner input = new Scanner(System.in);
        System.out.println("Please insert the product Id: ");

        int idProduct = input.nextInt();
        Product product = server.getProductController().getProduct(idProduct);

        while (product == null || product.getStatusProduct().equals(StatusProduct.INACTIVE)) {
            System.out.println("Please insert a valid Id: ");
            showProducts(server.getProductController().getProducts());
            idProduct = input.nextInt();
            product = server.getProductController().getProduct(idProduct);
        }
        return product;
    }

    public Integer getBusinessOwnerProductId() throws InvalidDataException {

        Scanner input = new Scanner(System.in);
        System.out.println("Please insert the product Id: ");

        int idProduct = input.nextInt();

        while (!server.getBusinessOwnerController().isBusinessOwnerProduct(idProduct)) {
            System.out.println("Please insert a valid Id: ");
            showProducts(server.getBusinessOwnerController().getBusinessOwnerProducts());
            idProduct = input.nextInt();
        }
        return idProduct;
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

        return new Hall(name, description, location, capacity);
    }



    public DJ createDJView() {
        Scanner input = new Scanner(System.in);
        System.out.println("Name: ");
        String name = input.nextLine();
        System.out.println("Description: ");
        String description = input.nextLine();

        // facut teste sa raspunda corect
        boolean lights = answer();
        boolean stereo = answer();

        return new DJ(name, description, lights, stereo);
    }

    public CandyBar createCandyBarView() {
        Scanner input = new Scanner(System.in);
        System.out.println("Name: ");
        String name = input.nextLine();
        System.out.println("Description: ");
        String description = input.nextLine();
        System.out.println("Add sweets: ");
        boolean ok = true;
        List<Sweet> sweets = new ArrayList<>();

        String sweetString = input.nextLine();

        while (ok) {
            if(sweetString.equals(" ") || sweetString.equals("\n") || sweetString.equals("")) {
                ok = false;
            }
            Sweet sweet = new Sweet(sweetString);
            sweets.add(sweet);
            sweetString = input.nextLine();
        }
        return new CandyBar(name, description, sweets);
    }

    public Message createMessageView() throws InvalidDataException {
        Scanner input = new Scanner(System.in);

        System.out.println("Start Date: ");
        String startDate = input.nextLine();
        System.out.println("End Date: ");
        String endDate=input.nextLine();

        System.out.println("Description: ");
        String description=input.nextLine();

        Product productInMessage = getProductView();


        Integer guests = null;
        while (true) {
            System.out.println("Number of guests: ");
            guests = input.nextInt();
            if (productInMessage instanceof Hall) { //daca prod din anuntul din msj e o instanta a salii, caci doar la sala ai nr de invitati
                if(guests <= (((Hall) productInMessage).getCapacity())) { //daca incap invitatii in sala
                    break;
                }
                else {
                    System.out.println("Too many guest for the Hall");
                    System.out.println("Maximum capacity is "+ ((Hall) productInMessage).getCapacity());
                    System.out.println("Please enter a smaller value if you want an offer!");
                }
            }
            else { //daca prod din anuntul din msj NU e o instanta a salii
                break;
            }
        }


        return new Message(productInMessage, server.getOrganiserController().getOrganiser(),  server.getBusinessOwnerByProductId(productInMessage.getId()), startDate, endDate, guests, description);

    }

    //oferta de creare e un msj cu un pret si o descriere
    public Offer createOfferView(Message message) {
        Scanner input = new Scanner(System.in);

        System.out.println("Description: ");
        String description = input.nextLine();

        System.out.println("Price: ");
        Integer price = input.nextInt();

        return new Offer(server.getBusinessOwnerController().getBusinessOwner(), server.getOrganiserByMessageId(message.getIdMessage()), message.getProduct(), price, description);

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
