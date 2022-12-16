package Controller;

import model.Product;
import repo.jpa.ProductRepositoryJPA;

import java.util.List;

public class ProductController {

    ProductRepositoryJPA productRepositoryJPA;

    ProductController(ProductRepositoryJPA productRepositoryJPA) {
        this.productRepositoryJPA = productRepositoryJPA;
    }

    public Product getProduct(Integer idProduct) {
        return productRepositoryJPA.findById(idProduct);
    }

    public List<Product> getProducts() {
        return productRepositoryJPA.getProducts();
    }

    public void deleteProduct(Integer idProduct){
        productRepositoryJPA.remove(idProduct);
    }

    public void modifyProduct(Integer idProduct, Product newProduct) {
        productRepositoryJPA.update(idProduct, newProduct);
    }

}
