package Controller;

import interfaces.UserControllerInterface;
import model.Organiser;

import java.util.ArrayList;

public class OrganiserController implements UserControllerInterface<Organiser, ArrayList<String>> {

    @Override
    public Organiser createUser(ArrayList<String> credentials) {
        Organiser organiser = new Organiser(credentials.get(1), credentials.get(2), credentials.get(3), credentials.get(4));
        return organiser;
    }


}
