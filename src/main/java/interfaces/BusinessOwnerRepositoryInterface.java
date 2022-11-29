package interfaces;

import model.BusinessOwner;

import java.util.ArrayList;

public interface BusinessOwnerRepositoryInterface extends ICrudRepositoryInterface<BusinessOwner, String> {

    BusinessOwner findByUsernameAndPassword(String username, String password);

}


