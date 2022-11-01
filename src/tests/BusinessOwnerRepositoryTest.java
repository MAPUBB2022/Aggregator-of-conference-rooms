package tests;

import model.BusinessOwner;
import org.junit.jupiter.api.BeforeEach;
import repo.BusinessOwnerRepository;

import static org.junit.jupiter.api.Assertions.*;

class BusinessOwnerRepositoryTest {

    @BeforeEach
    void setUp() {
        BusinessOwnerRepository.getInstance();
    }

    @org.junit.jupiter.api.Test
    void add() {
        System.out.println("Test add business owner");
        BusinessOwner businessOwner1 = new BusinessOwner("asd", "b", "bowner1", "123");

        assertEquals(BusinessOwnerRepository.getInstance().getSize(), 1);
        BusinessOwnerRepository.getInstance().add(businessOwner1);

        assertEquals(BusinessOwnerRepository.getInstance().getSize(), 2);
        BusinessOwnerRepository.getInstance().add(businessOwner1);
        assertEquals(BusinessOwnerRepository.getInstance().getSize(), 2);
    }

    @org.junit.jupiter.api.Test
    void remove() {
        System.out.println("Test remove business owner");

        assertEquals(BusinessOwnerRepository.getInstance().getSize(), 2);
        BusinessOwnerRepository.getInstance().remove("raulstefan002");

        assertEquals(BusinessOwnerRepository.getInstance().getSize(), 1);
        BusinessOwnerRepository.getInstance().remove("bowner1");
        assertEquals(BusinessOwnerRepository.getInstance().getSize(), 1);
    }

    @org.junit.jupiter.api.Test
    void update() {
        System.out.println("Test update business owner");

        BusinessOwner businessOwner1 = new BusinessOwner("asd", "b", "raul1111", "123");

        assertEquals(BusinessOwnerRepository.getInstance().getSize(), 1);
        BusinessOwnerRepository.getInstance().update("raulstefan002", businessOwner1);

        assertNull(BusinessOwnerRepository.getInstance().findByUsername("raul1111"));

        BusinessOwnerRepository.getInstance().update("raulstefan002", businessOwner1);
        assertNull(BusinessOwnerRepository.getInstance().findByUsername("raulstefan002"));

    }

    @org.junit.jupiter.api.Test
    void findByUsername() {
        System.out.println("Test find business owner by username");

        BusinessOwner businessOwner1 = new BusinessOwner("asd", "b", "raul", "123");
        BusinessOwnerRepository.getInstance().add(businessOwner1);

        assertNull(BusinessOwnerRepository.getInstance().findByUsername("raul1111"));
        assertEquals(BusinessOwnerRepository.getInstance().findByUsername("raul"), businessOwner1);

    }
}