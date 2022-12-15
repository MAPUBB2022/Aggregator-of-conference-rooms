package repo.jpa;

import interfaces.ChatRepositoryInterface;
import model.*;

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
        newOffer.setProduct( manager.merge(newOffer.getProduct()));
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
            BusinessOwner sender = BusinessOwnerRepositoryJPA.getInstance().findById(offer.getSender().getUsername());
            Organiser receiver = OrganiserRepositoryJPA.getInstance().findById(offer.getReceiver().getUsername());
            offer.setSender(sender);
            offer.setReceiver(receiver);
            manager.getTransaction().commit();

        }

    }

    @Override
    public Offer findById(Integer id) {
        return manager.find(Offer.class, id);
    }


}
