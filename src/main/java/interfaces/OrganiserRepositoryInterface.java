package interfaces;

import model.BusinessOwner;
import model.Organiser;

import java.util.ArrayList;


public interface OrganiserRepositoryInterface extends ICrudRepositoryInterface<Organiser, String > {


    Organiser findByUsernameAndPassword(String username, String password);
}
