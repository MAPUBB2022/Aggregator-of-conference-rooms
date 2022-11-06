package Controller;

import interfaces.UserControllerInterface;
import model.BusinessOwner;

import java.util.ArrayList;

public class BusinessOwnerController implements UserControllerInterface<BusinessOwner, ArrayList<String>> {

    @Override
    public BusinessOwner createUser(ArrayList<String> credentials) {
        BusinessOwner businessOwner = new BusinessOwner(credentials.get(1), credentials.get(2), credentials.get(3), credentials.get(4));
        return businessOwner;
    }
}
