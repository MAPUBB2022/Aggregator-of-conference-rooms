package repo.jpa;

import interfaces.OrganiserRepositoryInterface;
import model.BusinessOwner;
import model.Organiser;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class OrganiserRepositoryJPA implements OrganiserRepositoryInterface {
    private static OrganiserRepositoryJPA single_instance = null;

    private final EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
    private final EntityManager manager = factory.createEntityManager();

    public static OrganiserRepositoryJPA getInstance() {
        if (single_instance == null){
            single_instance = new OrganiserRepositoryJPA();
        }
        return single_instance;
    }

    @Override
    public void add(Organiser newOrganiser){

        Organiser organiser = findById(newOrganiser.getUsername());
        if(organiser == null) {
            manager.getTransaction().begin();
            manager.persist(newOrganiser);
            manager.getTransaction().commit();
        }

    }

    @Override
    public void remove(String username){
        Organiser organiser = findById(username);
        if(organiser != null) {
            manager.getTransaction().begin();
            manager.remove(organiser);
            manager.getTransaction().commit();
        }

    }

    @Override
    public void update(String username, Organiser newOrganiser){

        Organiser organiser = findById(username);
        if(organiser != null) {
            manager.getTransaction().begin();
            organiser.setFirstName(newOrganiser.getFirstName());
            organiser.setLastName(newOrganiser.getLastName());
            organiser.setPassword(newOrganiser.getPassword());
            manager.getTransaction().commit();
        }
    }

    @Override
    public Organiser findById(String username) {
        return manager.find(Organiser.class, username);
    }

    @Override
    public Organiser findByUsernameAndPassword(String username, String password) {
        Organiser organiser = findById(username);

        if(organiser != null) {
            if(organiser.getPassword().equals(password)) {
                return organiser;
            }
        }
        return null;
    }

//

    public Organiser findOrganiserByMessageId(Integer idMessage) {
        Query query = manager.createNativeQuery("SELECT idOrganiser FROM messages WHERE idMessage = " + idMessage);
        return (Organiser) manager.find(Organiser.class, (String)query.getSingleResult());
    }
}
