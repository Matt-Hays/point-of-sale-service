package com.courseproject.pointofsaleservice.services;

import com.courseproject.pointofsaleservice.models.Product;
import com.courseproject.pointofsaleservice.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.logging.log4j.util.ProcessIdUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.lang.Long;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    private final RedisTemplate<Long, Product> redisTemplate;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) throws EntityNotFoundException {
        Product product = redisTemplate.opsForValue().get(id);
        if (product == null) {
            log.info("Product {} not found in Redis", id);
            product = productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
            redisTemplate.opsForValue().set(id, product);
        }
        return product;
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) throws EntityNotFoundException {
        Product oldProduct = getProductById(id);
        if (oldProduct == null)
            return null;
        if (product.getSku() != null)
            oldProduct.setSku(product.getSku());
        if (product.getDescription() != null)
            oldProduct.setDescription(product.getDescription());
        return productRepository.save(oldProduct);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
