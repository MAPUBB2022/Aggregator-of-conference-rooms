package repo;

import interfaces.AdRepositoryInterface;
import model.Ad;

import java.util.ArrayList;
import java.util.List;


public class AdRepository implements AdRepositoryInterface {

    private static AdRepository single_instance = null;
    private final List<Ad> allAds = new ArrayList<>();

   public static AdRepository getInstance() {
       if(single_instance == null) {
           single_instance = new AdRepository();
       }
       return single_instance;
   }


    @Override
    public void add(Ad entity){
        for(Ad ad: this.allAds){
            if(ad.getIdAd().equals(entity.getIdAd())){
                return;
            }
        }
        this.allAds.add(entity);
    }

    @Override
    public void remove(Integer id){
        this.allAds.removeIf(ad -> ad.getIdAd().equals(id));
    }

    @Override
    public void update(Integer id, Ad new_ad) {
        for( Ad ad : this.allAds) {
            if(ad.getIdAd().equals(id)) {
                ad.setIdAd(new_ad.getIdAd());
                ad.setProduct(new_ad.getProduct());
                ad.setImages(new_ad.getImages());
                ad.setCalendar(new_ad.getCalendar());
                break;
            }
        }
    }

    @Override
    public Integer findById(Integer id){
       for(Ad ad: this.allAds) {
           if(ad.getIdAd().equals(id)) {
               return id;
           }
       }
       return null;
    }


}
