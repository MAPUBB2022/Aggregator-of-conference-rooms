package interfaces;

import model.Ad;

public interface AdRepositoryInterface extends ICrudRepositoryInterface<Ad, Integer>{
    Integer findById(Integer id);
}
