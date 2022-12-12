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
            product.setStatusProduct(StatusProduct.INACTIVE);
            manager.getTransaction().commit();
            product.setStatusProduct(StatusProduct.INACTIVE);
        }

    }

    @Override
    public void update(Integer id, Product newProduct) {
        Product product = findById(id);
        if (product != null) {
            manager.getTransaction().begin();
            if (product.getClass() != newProduct.getClass()) {
                product.setStatusProduct(StatusProduct.INACTIVE);
                add(newProduct); // update la baza de date
                product.setStatusProduct(StatusProduct.INACTIVE);
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

    public List<Product> getProducts() {
        TypedQuery<Product> query = manager.createQuery("SELECT p FROM Product p WHERE p.statusProduct = :status", Product.class);
        query.setParameter("status", StatusProduct.ACTIVE);
        List<Product> products = query.getResultList();
        return products;
    }


}
