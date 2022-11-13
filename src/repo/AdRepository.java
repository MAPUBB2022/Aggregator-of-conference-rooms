package repo;

import interfaces.AdRepositoryInterface;
import model.Ad;

import java.util.ArrayList;
import java.util.List;


public class AdRepository implements AdRepositoryInterface {

    private static AdRepository single_instance = null;
    private List<Ad> allAds = new ArrayList<>();

   public static AdRepository getInstance() {
       if(single_instance == null) {
           single_instance = new AdRepository();
       }
       return single_instance;
   }

    public List<Ad> getAllAds() {
        return allAds;
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

    //sterge un anunt dupa id din lista de toate anunturile
    @Override
    public void remove(Integer id){

       this.allAds.removeIf(ad -> ad.getIdAd().equals(id));
    }

    @Override
    public void update(Integer id, Ad newAd) {
        for( Ad ad : this.allAds) {
            if(ad.getIdAd().equals(id)) {
                ad.setProduct(newAd.getProduct());
                ad.setCalendar(newAd.getCalendar());
                break;
            }
        }
    }

    @Override
    public Ad findById(Integer id){
       for(Ad ad: this.allAds) {
           if(ad.getIdAd().equals(id)) {
               return ad;
           }
       }
       return null;
    }



}
