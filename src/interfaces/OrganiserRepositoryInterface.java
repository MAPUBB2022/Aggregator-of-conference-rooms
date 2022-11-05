package interfaces;

import model.Organiser;

import java.util.SplittableRandom;

public interface OrganiserRepositoryInterface extends ICrudRepositoryInterface<Organiser, String > {
    Organiser findByUsernameAndPassword(String username, String password);
}
