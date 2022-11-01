package interfaces;

import model.Organiser;

public interface OrganiserRepositoryInterface extends ICrudRepositoryInterface<Organiser, String > {
    Organiser findByUsername(String username);
}
