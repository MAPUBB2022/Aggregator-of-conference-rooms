package repo.jpa;

import interfaces.ChatRepositoryInterface;
import interfaces.ICrudRepositoryInterface;
import model.*;

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
        Organiser sender = manager.merge(newMessage.getSender());
        BusinessOwner receiver = manager.merge(newMessage.getReceiver());
        newMessage.setSender(sender);
        newMessage.setReceiver(receiver);
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
            Organiser sender = OrganiserRepositoryJPA.getInstance().findById(message.getSender().getUsername());
            BusinessOwner receiver = BusinessOwnerRepositoryJPA.getInstance().findById(message.getReceiver().getUsername());
            message.setSender(sender);
            message.setReceiver(receiver);
            manager.merge(message).setStatus(newStatus);

            manager.getTransaction().commit();
        }
    }

    @Override
    public Message findById(Integer id) {
        //manager.refresh(Message.class);

        Message message =  manager.find(Message.class, id);
        //manager.refresh(message);
        return message;
    }


}
