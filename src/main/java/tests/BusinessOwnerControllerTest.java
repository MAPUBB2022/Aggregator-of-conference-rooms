package tests;

import Controller.BusinessOwnerController;
import Controller.OrganiserController;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repo.inMemory.BusinessOwnerInMemoryRepository;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BusinessOwnerControllerTest {

    private BusinessOwner businessOwner;
    private Calendar calendar= new Calendar();
    private Hall hall = new Hall("name", "description", "location", 1000);
    private Ad ad = new Ad(hall, calendar);
    private Message message = new Message(ad, "12-12-2022", "13-12-2022", 500, "description");
    private Offer offer = new Offer(ad, "12-12-2022", "13-12-2022", 500, "description", 1000);

    @BeforeEach
    void setUp() {

        businessOwner= new BusinessOwner("firstname", "lastname", "businessOwner", "1234");
        BusinessOwnerController.getInstance().setBusinessOwner(businessOwner);

    }

    @Test
    void checkNewMessages() {

        System.out.println("CheckNewMessages Test\n");
        assertTrue(BusinessOwnerController.getInstance().checkNewMessages());

        businessOwner.getRequestedOffers().add(message);
        assertFalse(BusinessOwnerController.getInstance().checkNewMessages());

//        for(Message message1 : businessOwner.getRequestedOffers()) {
//            message1.setStatus(Status.ACCEPTED);
//        }
//        assertTrue(BusinessOwnerController.getInstance().checkNewMessages());
    }

    @Test
    void checkSentOffers() {

        System.out.println("CheckSentOffers Test\n");

        assertTrue(BusinessOwnerController.getInstance().checkSentOffers());
        businessOwner.getSentOffers().add(offer);
        assertFalse(BusinessOwnerController.getInstance().checkSentOffers());
    }

    @Test
    void createAd() {
        System.out.println("CreatedAd Test\n");

        assertFalse(businessOwner.getAds().contains(ad));
        BusinessOwnerController.getInstance().createAd(ad);
        assertTrue(businessOwner.getAds().contains(ad));

    }

    @Test
    void deleteAd() {
        System.out.println("DeleteAd Test\n");

        BusinessOwnerController.getInstance().createAd(ad);
        assertTrue(businessOwner.getAds().contains(ad));
        BusinessOwnerController.getInstance().deleteAd(ad.getIdAd());
        assertFalse(businessOwner.getAds().contains(ad));

    }

    @Test
    void modifyAd() {
        System.out.println("Modify Test\n");

        Calendar newCalendar= new Calendar();
        List<String> sweets = new ArrayList<>(Arrays.asList("chocolate", "haribo"));
        CandyBar candyBar = new CandyBar("name", "description", sweets);
        Ad newAd = new Ad(candyBar, newCalendar);
        BusinessOwnerController.getInstance().createAd(ad);
        assertTrue(businessOwner.getAds().contains(ad));

        BusinessOwnerController.getInstance().modifyAd(ad.getIdAd(), newAd);
//        assertEquals(businessOwner.getAds())
//        assertTrue(businessOwner.getAds().contains(newAd));

    }

    @Test
    void sort() {
    }

    @Test
    void filterByAcceptedMessage() {
    }

    @Test
    void filterByDeclinedMessage() {
    }

    @Test
    void filterByNumberOfMadeOffers() {
    }

    @Test
    void showOffersByRandomBO() {
    }

    @Test
    void isBusinessOwnerAd() {
    }
}