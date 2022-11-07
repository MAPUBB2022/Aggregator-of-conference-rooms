package tests;

import model.BusinessOwner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repo.BusinessOwnerRepository;

import static org.junit.jupiter.api.Assertions.*;

class BusinessOwnerRepositoryTest {

    @BeforeEach
    void setUp() {
        BusinessOwnerRepository.getInstance();
    }

    @Test
    void add() {
        System.out.println("Test add business owner");
        BusinessOwner businessOwner1 = new BusinessOwner("asd", "b", "bowner1", "123");

        assertEquals(BusinessOwnerRepository.getInstance().getSize(), 2);

        BusinessOwnerRepository.getInstance().add(businessOwner1);
        assertEquals(BusinessOwnerRepository.getInstance().getSize(), 3);

    }

    @Test
    void remove() {
        System.out.println("Test remove business owner");

        assertEquals(BusinessOwnerRepository.getInstance().getSize(), 2);
        BusinessOwnerRepository.getInstance().remove("raulstefan002");

        assertEquals(BusinessOwnerRepository.getInstance().getSize(), 1);

        BusinessOwnerRepository.getInstance().remove("bowner1");
        assertEquals(BusinessOwnerRepository.getInstance().getSize(), 1);
    }

    @Test
    void update() {
        System.out.println("Test update business owner");

        BusinessOwner businessOwner1 = new BusinessOwner("asd", "b", "raul1111", "123");

        assertEquals(BusinessOwnerRepository.getInstance().getSize(), 2);
        BusinessOwnerRepository.getInstance().update("raulstefan002", businessOwner1);

        assertNotNull(BusinessOwnerRepository.getInstance().findById("raul1111"));

        assertNull(BusinessOwnerRepository.getInstance().findById("raulstefan002"));

    }

    @Test
    void findById() {
        System.out.println("Test find business owner by username");

        BusinessOwner businessOwner1 = new BusinessOwner("asd", "b", "raul", "123");
        BusinessOwnerRepository.getInstance().add(businessOwner1);

        assertNull(BusinessOwnerRepository.getInstance().findById("raul1111"));
        assertEquals(BusinessOwnerRepository.getInstance().findById("raul"), businessOwner1);

    }
}