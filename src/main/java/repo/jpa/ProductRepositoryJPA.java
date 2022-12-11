package repo.jpa;

import interfaces.ProductRepositoryInterface;
import model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class ProductRepositoryJPA implements ProductRepositoryInterface {

    private static ProductRepositoryJPA single_instance = null;

    private final EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
    private final EntityManager manager = factory.createEntityManager();

    public static ProductRepositoryJPA getInstance() {
        if (single_instance == null) {
            single_instance = new ProductRepositoryJPA();
        }
        return single_instance;
    }

    @Override
    public void add(Product newProduct) {
        Product product = findById(newProduct.getId());
        if (product == null) {
            manager.getTransaction().begin();
            manager.persist(newProduct);
            manager.getTransaction().commit();
        }
    }

    @Override
    public void remove(Integer id) {
        Product product = findById(id);
        if (product != null) {
            manager.getTransaction().begin();
            manager.createNativeQuery("DELETE FROM messages WHERE idProduct = " + id);
            manager.createNativeQuery("DELETE FROM offers WHERE idProduct = " + id);
            manager.remove(product);
            manager.getTransaction().commit();
        }

    }

    @Override
    public void update(Integer id, Product newProduct) {
        Product product = findById(id);
        if (product != null) {
            manager.getTransaction().begin();
            if (product.getClass() != newProduct.getClass()) {
                manager.remove(product);
                manager.persist(newProduct);
                manager.getTransaction().commit();
            } else {
                if (product instanceof Hall) {
                    updateHall((Hall) product, (Hall) newProduct);
                } else if (product instanceof CandyBar) {
                    updateCandybar((CandyBar) product, (CandyBar) newProduct);
                } else {
                    updateDj((DJ) product, (DJ) newProduct);
                }
            }
        }
    }

    public void updateHall(Hall hall, Hall newHall) {
        hall.setName(newHall.getName());
        hall.setCapacity(newHall.getCapacity());
        hall.setLocation(newHall.getLocation());
        hall.setDescription(newHall.getDescription());
        manager.getTransaction().commit();
    }

    public void updateCandybar(CandyBar candyBar, CandyBar newCandyBar) {
        candyBar.setName(newCandyBar.getName());
        candyBar.setDescription(newCandyBar.getDescription());
        manager.getTransaction().commit();
    }

    public void updateDj(DJ dj, DJ newDj) {
        dj.setName(newDj.getName());
        dj.setDescription(newDj.getDescription());
        dj.setLights(newDj.getLights());
        dj.setStereo(newDj.getStereo());
        manager.getTransaction().commit();
    }

    @Override
    public Product findById(Integer id) {

        return manager.find(Product.class, id);
    }

    public List<Hall> getHalls() {
        TypedQuery<Hall> query = manager.createQuery("SELECT h FROM Hall h", Hall.class);
        List<Hall> halls = query.getResultList();
        return halls;
    }
    public List<CandyBar> getCandyBars() {
        TypedQuery<CandyBar> query = manager.createQuery("SELECT c FROM CandyBar c", CandyBar.class);
        List<CandyBar> candyBars = query.getResultList();
        return candyBars;
    }
    public List<DJ> getDjs() {
        TypedQuery<DJ> query = manager.createQuery("SELECT d FROM DJ d", DJ.class);
        List<DJ> djs = query.getResultList();
        return djs;
    }

}
