package repo.jpa;

import interfaces.ChatRepositoryInterface;
import model.Offer;
import model.Product;
import model.Status;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class OfferRepositoryJPA implements ChatRepositoryInterface<Offer, Integer> {
    private static OfferRepositoryJPA single_instance = null;

    private final EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
    private final EntityManager manager = factory.createEntityManager();

    public static OfferRepositoryJPA getInstance() {
        if (single_instance == null){
            single_instance = new OfferRepositoryJPA();
        }
        return single_instance;
    }

    @Override
    public void add(Offer newOffer) {
        manager.getTransaction().begin();
        Product product = manager.merge(newOffer.getProduct());
        newOffer.setProduct(product);
        manager.persist(newOffer);
        manager.getTransaction().commit();

    }

    @Override
    public void remove(Integer id) {
        Offer offer = findById(id);
        if(offer != null) {
            manager.getTransaction().begin();
            manager.remove(offer);
            manager.getTransaction().commit();
        }

    }

    @Override
    public void updateStatus(Offer offer, Status newStatus) {
        if(offer != null) {
            manager.getTransaction().begin();
            manager.merge(offer).setStatus(newStatus);
            manager.getTransaction().commit();
            offer.setStatus(newStatus);
        }

    }

    @Override
    public Offer findById(Integer id) {
        return manager.find(Offer.class, id);
    }


}
