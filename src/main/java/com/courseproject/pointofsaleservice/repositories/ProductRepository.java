package com.courseproject.pointofsaleservice.repositories;

import com.courseproject.pointofsaleservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
