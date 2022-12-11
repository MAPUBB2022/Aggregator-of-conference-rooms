package Controller;

import interfaces.BusinessOwnerRepositoryInterface;
import interfaces.OrganiserRepositoryInterface;
import interfaces.ProductRepositoryInterface;
import model.BusinessOwner;
import model.Organiser;
import model.Product;
import repo.jpa.BusinessOwnerRepositoryJPA;
import repo.jpa.OrganiserRepositoryJPA;
import repo.jpa.ProductRepositoryJPA;

import java.util.ArrayList;

public class Server {
    private final OrganiserRepositoryInterface organisers = OrganiserRepositoryJPA.getInstance();
    private final BusinessOwnerRepositoryInterface businessOwners = BusinessOwnerRepositoryJPA.getInstance();
    private final ProductRepositoryInterface products = ProductRepositoryJPA.getInstance();

    private static Server single_instance = null;

    public static Server getInstance() {
        if (single_instance == null){
            single_instance = new Server();
        }
        return single_instance;
    }

    public Server() {
    }

    public boolean login(ArrayList<String> credentials) {

        if (credentials.get(0).equals("1") && organisers.findByUsernameAndPassword(credentials.get(1), credentials.get(2)) != null) { //daca e org si s-a gasit in bd (are cont)
            return true; //se dechide meniul pt organiser
        }
        if (credentials.get(0).equals("2") && businessOwners.findByUsernameAndPassword(credentials.get(1), credentials.get(2)) != null) { //daca e b.o. si are deja cont
            return true;
        }
       return false;
    }
    public boolean signUp(ArrayList<String> credentials) {
        if(credentials.get(0).equals("1")) { //daca in string ul de credetiale, e organiser
            organisers.add(OrganiserController.getInstance().createUser(credentials)); //se creeaza un user cu acele credentiale => se ret un org si apoi se adauga la acea instanta unica
            return true;
        }
        if (credentials.get(0).equals("2")) { //daca in string ul de credetiale, e b.o
            businessOwners.add(BusinessOwnerController.getInstance().createUser(credentials)); //se creeaza un user cu acele credentiale => se ret un b.o. si apoi se adauga la acea instanta unica
            return true;
        }
        return false;
    }

    public void setBusinessOwnerInController(String username) {
        BusinessOwnerController.getInstance().setUsername(username);
    } public void setOrganiserInController(String username) {
        OrganiserController.getInstance().setUsername(username);
    }

    public Product getProduct(Integer idProduct) {
        return products.findById(idProduct);
    }

    public BusinessOwner getBusinessOwner(String username) {
        return businessOwners.findById(username);
    }

    public Organiser getOrganiser(String username) {
        return organisers.findById(username);
    }
}
