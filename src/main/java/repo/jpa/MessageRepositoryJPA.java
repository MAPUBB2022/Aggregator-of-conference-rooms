package repo.jpa;

import interfaces.ChatRepositoryInterface;
import interfaces.ICrudRepositoryInterface;
import model.Message;
import model.Organiser;
import model.Product;
import model.Status;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MessageRepositoryJPA implements ChatRepositoryInterface<Message, Integer> {
    private static MessageRepositoryJPA single_instance = null;

    private final EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
    private final EntityManager manager = factory.createEntityManager();

    public static MessageRepositoryJPA getInstance() {
        if (single_instance == null){
            single_instance = new MessageRepositoryJPA();
        }
        return single_instance;
    }

    @Override
    public void add(Message newMessage) {

        manager.getTransaction().begin();
        Product product = manager.merge(newMessage.getProduct());
        newMessage.setProduct(product);
        manager.persist(newMessage);
        manager.getTransaction().commit();
    }

    @Override
    public void remove(Integer id) {
        Message message = findById(id);
        if(message != null) {
            manager.getTransaction().begin();
            manager.remove(message);
            manager.getTransaction().commit();
        }

    }

    @Override
    public void updateStatus(Message message, Status newStatus) {
        if(message != null) {
            manager.getTransaction().begin();
            manager.merge(message).setStatus(newStatus);
            manager.getTransaction().commit();
            message.setStatus(newStatus);
        }
    }

    @Override
    public Message findById(Integer id) {
        return manager.find(Message.class, id);
    }


}
