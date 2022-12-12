package repo.jpa;

import interfaces.BusinessOwnerRepositoryInterface;
import model.*;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;


public class BusinessOwnerRepositoryJPA implements BusinessOwnerRepositoryInterface {
    private static BusinessOwnerRepositoryJPA single_instance = null;

    private final EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
    private final EntityManager manager = factory.createEntityManager();

    public static BusinessOwnerRepositoryJPA getInstance() {
        if (single_instance == null){
            single_instance = new BusinessOwnerRepositoryJPA();
        }
        return single_instance;
    }


    @Override
    public void add(BusinessOwner newBusinessOwner){

        BusinessOwner businessOwner = findById(newBusinessOwner.getUsername());
        if(businessOwner == null) {
            manager.getTransaction().begin();
            manager.persist(newBusinessOwner);
            manager.getTransaction().commit();
        }

    }

    @Override
    public void remove(String username){
        BusinessOwner businessOwner = findById(username);
        if(businessOwner != null) {
            manager.getTransaction().begin();
            manager.remove(businessOwner);
            manager.getTransaction().commit();
        }

    }

    @Override
    public void update(String username, BusinessOwner newBusinessOwner){

        BusinessOwner businessOwner = findById(username);
        if(businessOwner != null) {
            manager.getTransaction().begin();
            businessOwner.setFirstName(newBusinessOwner.getFirstName());
            businessOwner.setLastName(newBusinessOwner.getLastName());
            businessOwner.setPassword(newBusinessOwner.getPassword());
            manager.getTransaction().commit();
        }
    }

    public void updateProductsList(BusinessOwner businessOwner, Product product) {
        manager.getTransaction().begin();
        manager.merge(businessOwner).getProducts().add(product);
        manager.getTransaction().commit();
    }


    @Override
    public BusinessOwner findById(String username) {

        return manager.find(BusinessOwner.class, username);
    }


    @Override
    public BusinessOwner findByUsernameAndPassword(String username, String password) {
        BusinessOwner businessOwner = findById(username);

        if(businessOwner != null) {
            if(businessOwner.getPassword().equals(password)) {
                return businessOwner;
            }
        }
        return null;
    }

    //ret businessOwner-ul coresp id-ului unui Ad
    public BusinessOwner findBusinessOwnerByProductId(Integer idProduct) {

        //facut exceptie pentru query
        Query query = manager.createNativeQuery("SELECT idBusinessOwner FROM products WHERE id = " + idProduct);
        return (BusinessOwner) manager.find(BusinessOwner.class, (String)query.getSingleResult());

    }


    public Integer getSize() {

        return  (Integer) (manager.createNativeQuery("SELECT COUNT(products.username) FROM products", Product.class)).getSingleResult();
    }
}
