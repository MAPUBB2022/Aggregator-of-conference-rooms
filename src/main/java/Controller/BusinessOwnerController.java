package Controller;

import interfaces.UserControllerInterface;
import model.*;
import repo.inMemory.ProductsInMemoryRepository;
import repo.jpa.BusinessOwnerRepositoryJPA;
import repo.jpa.MessageRepositoryJPA;
import repo.jpa.OfferRepositoryJPA;
import repo.jpa.ProductRepositoryJPA;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BusinessOwnerController implements UserControllerInterface<BusinessOwner, ArrayList<String>> {
    private static BusinessOwnerController single_instance = null;
    private String username;

    public static BusinessOwnerController getInstance() {
        if (single_instance == null){
            single_instance = new BusinessOwnerController();
        }
        return single_instance;
    }

    public BusinessOwnerController() {
    }

    public BusinessOwner getBusinessOwner() {
        return BusinessOwnerRepositoryJPA.getInstance().findById(username);
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public List<Product> getBusinessOwnerProducts() {
        List<Product> products = BusinessOwnerRepositoryJPA.getInstance().findById(this.username).getProducts();
        return products.stream().filter(product -> product.getStatusProduct().equals(StatusProduct.ACTIVE)).toList();
    }

    @Override
    public BusinessOwner createUser(ArrayList<String> credentials) {
        return new BusinessOwner(credentials.get(1), credentials.get(2), credentials.get(3), credentials.get(4));

    }

    public boolean checkNewMessages() {
        List<Message> newMessages = getBusinessOwner().getReceivedMessages().stream().filter(message -> message.getStatus().equals(Status.SENT)).toList(); ;
        return newMessages.isEmpty();
    }
    public boolean checkSentOffers() {
        return getBusinessOwner().getSentOffers().isEmpty();
    }

    public void createProduct(Product newProduct) {
        BusinessOwnerRepositoryJPA.getInstance().updateProductsList(getBusinessOwner(), newProduct);
    }

    public void deleteProduct(Integer idProduct){
        ProductRepositoryJPA.getInstance().remove(idProduct);
    }

    public void modifyProduct(Integer idProduct, Product newProduct) {
        ProductRepositoryJPA.getInstance().update(idProduct, newProduct);
    }

    public void declineMessage(Message message) {

        MessageRepositoryJPA.getInstance().updateStatus(message, Status.DECLINED);
    }


    public void makeOffer(Offer offer, Message message) {
        OfferRepositoryJPA.getInstance().add(offer);
        OfferRepositoryJPA.getInstance().updateStatus(offer, Status.SENT);
        MessageRepositoryJPA.getInstance().updateStatus(message, Status.ACCEPTED);
    }

    @Override
    public List<Message> filterByDeclinedMessages(){

        List<Message> messages = BusinessOwnerRepositoryJPA.getInstance().findById(this.username).getReceivedMessages();

        return messages.stream().filter(message -> message.getStatus().equals(Status.DECLINED)).toList();
    }

    @Override
    public List<Message> filterByAcceptedMessages(){

        List<Message> messages = BusinessOwnerRepositoryJPA.getInstance().findById(this.username).getReceivedMessages();

        return messages.stream().filter(message -> message.getStatus().equals(Status.ACCEPTED)).toList();
    }
    @Override
    public List<Offer> filterByDeclinedOffers(){

        List<Offer> offers = BusinessOwnerRepositoryJPA.getInstance().findById(this.username).getSentOffers();

        return offers.stream().filter(offer -> offer.getStatus().equals(Status.DECLINED)).toList();
    }
    @Override
    public List<Offer> filterByAcceptedOffers(){

        List<Offer> offers = BusinessOwnerRepositoryJPA.getInstance().findById(this.username).getSentOffers();

        return offers.stream().filter(offer -> offer.getStatus().equals(Status.ACCEPTED)).toList();
    }

    public List<Message> filterMessagesBySenderUsername(String senderUsername) {
        List<Message> messages = BusinessOwnerRepositoryJPA.getInstance().findById(this.username).getReceivedMessages();

        return messages.stream().filter(message -> message.getSender().getUsername().equals(senderUsername)).toList();
    }

    public boolean isBusinessOwnerProduct(Integer idProduct) {
        for(Product product : getBusinessOwner().getProducts()) {
            if(product.getId() == idProduct) {
                return true;
            }
        }
        return false;
    }
}

