package tests;//package tests;
//
//import Controller.BusinessOwnerController;
//import Controller.OrganiserController;
//import model.*;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import repo.inMemory.BusinessOwnerInMemoryRepository;
//
//import java.lang.reflect.Array;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class BusinessOwnerControllerTest {
//
//    private BusinessOwner businessOwner;
//    private Hall hall = new Hall("name", "description", "location", 1000);
//    private Message message = new Message(hall, "12-12-2022", "13-12-2022", 500, "description");
//    private Offer offer = new Offer(hall, "12-12-2022", "13-12-2022", 500, "description", 1000);
//
//    @BeforeEach
//    void setUp() {
//
//        businessOwner= new BusinessOwner("firstname", "lastname", "businessOwner", "1234");
//        BusinessOwnerController.getInstance().setBusinessOwner(businessOwner);
//
//    }
//
//    @Test
//    void checkNewMessages() {
//
//        System.out.println("CheckNewMessages Test\n");
//        assertTrue(BusinessOwnerController.getInstance().checkNewMessages());
//
//        businessOwner.getReceivedMessages().add(message);
//        assertFalse(BusinessOwnerController.getInstance().checkNewMessages());
//
////        for(Message message1 : businessOwner.getRequestedOffers()) {
////            message1.setStatus(Status.ACCEPTED);
////        }
////        assertTrue(BusinessOwnerController.getInstance().checkNewMessages());
//    }
//
//    @Test
//    void checkSentOffers() {
//
//        System.out.println("CheckSentOffers Test\n");
//
//        assertTrue(BusinessOwnerController.getInstance().checkSentOffers());
//        businessOwner.getSentOffers().add(offer);
//        assertFalse(BusinessOwnerController.getInstance().checkSentOffers());
//    }
//
//    @Test
//    void createProduct() {
//        System.out.println("CreatedAd Test\n");
//
//        assertFalse(businessOwner.getProducts().contains(hall));
//        BusinessOwnerController.getInstance().createProduct(hall);
//        assertTrue(businessOwner.getProducts().contains(hall));
//
//    }
//
//    @Test
//    void deleteProduct() {
//        System.out.println("DeleteAd Test\n");
//
//        BusinessOwnerController.getInstance().createProduct(hall);
//        assertTrue(businessOwner.getProducts().contains(hall));
//        BusinessOwnerController.getInstance().deleteProduct(hall.getId());
//        assertFalse(businessOwner.getProducts().contains(hall));
//
//    }
//
//    @Test
//    void modifyProduct() {
//        System.out.println("Modify Test\n");
//
//        Sweet sweet1 = new Sweet("chocolate");
//        Sweet sweet2 = new Sweet("haribo");
//
//
//        List<Sweet> sweets = new ArrayList<>(Arrays.asList(sweet1, sweet2));
//        CandyBar candyBar = new CandyBar("name", "description", sweets);
//        BusinessOwnerController.getInstance().createProduct(candyBar);
//        assertTrue(businessOwner.getProducts().contains(hall));
//
//        BusinessOwnerController.getInstance().modifyProduct(hall.getId(), candyBar);
////        assertEquals(businessOwner.getAds())
////        assertTrue(businessOwner.getAds().contains(newAd));
//
//    }
//
//    @Test
//    void sort() {
//    }
//
//    @Test
//    void filterByAcceptedMessage() {
//    }
//
//    @Test
//    void filterByDeclinedMessage() {
//    }
//
//    @Test
//    void filterByNumberOfMadeOffers() {
//    }
//
//    @Test
//    void showOffersByRandomBO() {
//    }
//
//    @Test
//    void isBusinessOwnerAd() {
//    }
//}