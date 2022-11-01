package interfaces;

import model.BusinessOwner;

public interface BusinessOwnerRepositoryInterface extends ICrudRepositoryInterface<BusinessOwner, String > {

    BusinessOwner findByUsername(String username);
}


