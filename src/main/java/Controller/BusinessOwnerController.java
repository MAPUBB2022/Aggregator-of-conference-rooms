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

    //sorteaza lista de b.o. dupa comparatorul de username a b.o.
//    public static ArrayList<BusinessOwner> sort(){
//        ArrayList<BusinessOwner> allBusinessOwners= BusinessOwnerInMemoryRepository.getInstance().getAllBusinessOwner();
//        Collections.sort(allBusinessOwners, new NameComparatorBusinessOwner());
//
//        return allBusinessOwners;
//
//    }
//
//    //filtrare dupa mesaje acceptate - e ok dat ca param?
//    public ArrayList<Message> filterByAcceptedMessage(){
//        ArrayList<Message> filteredMessage=new ArrayList<>(Collections.emptyList());
//        for(Message message:businessOwner.getReceivedMessages()){
//            if(message.getStatus().equals(Status.SENT)){
//                filteredMessage.add(message);
//                //trb si afisat?
//            }
//        }
//        return filteredMessage;
//    }
//
//    //filtrare dupa mesaje respinse
//    public ArrayList<Message> filterByDeclinedMessage(){
//        ArrayList<Message> filteredMessage=new ArrayList<>(Collections.emptyList());
//        for(Message message:businessOwner.getReceivedMessages()){
//            if(message.getStatus().equals(Status.DECLINED)){
//                filteredMessage.add(message);
//                //trb si afisat?
//            }
//        }
//        return filteredMessage;
//    }
//
//    //filtrare b.o. dupa nr total de oferte facute; nr total de oferte a unui b.o. trb sa fie >= un nr oarecare
//    public ArrayList<BusinessOwner>filterByNumberOfMadeOffers(int NoOffers) {
//        ArrayList<BusinessOwner> filteredBusinessOwners = new ArrayList<>(Collections.emptyList());
//        for (BusinessOwner b : BusinessOwnerInMemoryRepository.getInstance().getAllBusinessOwner()) {
//            if (b.getSentOffers().size() >= NoOffers) {
//                filteredBusinessOwners.add(b);
//            }
//        }
//        return filteredBusinessOwners;
//    }
//
//    //se afiseaza toate ofertele ale unui anumit b.o.
//    public ArrayList<Offer> showOffersByRandomBO(BusinessOwner b){
//        ArrayList<Offer> returnedOffers = new ArrayList<>(Collections.emptyList());
//        for (BusinessOwner businessOwner : BusinessOwnerInMemoryRepository.getInstance().getAllBusinessOwner()) //cauta in lista de b.o. pe cel dat ca parametru
//            if(businessOwner.getFirstName().equals(b.getFirstName()) && businessOwner.getLastName().equals(b.getLastName())) { //daca il gaseste
//                for(Offer o: b.getSentOffers()) { //pt fiecare oferta a b.o dat ca param (gasit)
//                    //view.showOffer(o);
//                    returnedOffers.add(o);
//                }
//            }
//        return returnedOffers;
//    }

    public boolean isBusinessOwnerProduct(Integer idProduct) {
        for(Product product : getBusinessOwner().getProducts()) {
            if(product.getId() == idProduct) {
                return true;
            }
        }
        return false;
    }
}

