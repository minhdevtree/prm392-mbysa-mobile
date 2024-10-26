package vn.edu.fpt.project.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import vn.edu.fpt.project.model.Product;
import vn.edu.fpt.project.repository.ProductRepository;

public class ProductViewModel extends ViewModel {

    private ProductRepository productRepository;

    public ProductViewModel() {
        productRepository = new ProductRepository();
    }

    public LiveData<List<Product>> getAllProducts() {
        return productRepository.getAllProducts();
    }

    public LiveData<List<Product>> getFeaturedProducts() {
        return productRepository.getFeaturedProducts();
    }

    public LiveData<List<Product>> getLastestProducts() {
        return productRepository.getLastestProducts();
    }

    public LiveData<List<Product>> getRelatedProducts(String id) {
        return productRepository.getRelatedProducts(id);
    }

    public LiveData<Product> getProductById(String id) {
        return productRepository.getProductById(id);
    }

    public LiveData<List<Product>> searchProducts(String filter, String keyword) {
        return productRepository.searchProducts(filter, keyword);
    }
}
