package com.courseproject.pointofsaleservice.repositories;

import com.courseproject.pointofsaleservice.models.TransactionLineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionLineItemRepository extends JpaRepository<TransactionLineItem, Long> {
}
