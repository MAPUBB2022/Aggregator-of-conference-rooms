package model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "organisers")
public class Organiser extends User {

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private List<Offer> receivedOffers = new ArrayList<>();
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private List<Message> sentMessages = new ArrayList<>();


    public Organiser(String firstName, String lastName, String username, String password) {
        super(firstName, lastName, username, password);
        this.receivedOffers = new ArrayList<>();
    }

    public Organiser() {

    }

    public List<Offer> getReceivedOffers() {
        return receivedOffers;
    }

    public List<Message> getSentMessages() {
        return sentMessages;
    }

    @Override
    public String toString() {
        return "Organiser{ " +
                "username=" + getUsername() +
                ", receivedOffers=" + receivedOffers +
                ", sentMessages=" + sentMessages +
                '}';
    }


}
