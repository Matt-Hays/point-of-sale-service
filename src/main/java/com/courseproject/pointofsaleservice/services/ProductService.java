package com.courseproject.pointofsaleservice.services;

import com.courseproject.pointofsaleservice.models.Product;
import com.courseproject.pointofsaleservice.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.lang.Long;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) throws EntityNotFoundException {
        return productRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) throws EntityNotFoundException {
        Product oldProduct = getProductById(id);
        if (oldProduct == null) return null;
        if (product.getSku() != null) oldProduct.setSku(product.getSku());
        if (product.getDescription() != null) oldProduct.setDescription(product.getDescription());
        return productRepository.save(oldProduct);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
