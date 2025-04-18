package com.courseproject.pointofsaleservice.services;

import com.courseproject.pointofsaleservice.models.TransactionLineItem;
import com.courseproject.pointofsaleservice.repositories.TransactionLineItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.lang.Long;

@Service
@AllArgsConstructor
public class TransactionLineItemService {
    private final TransactionLineItemRepository transactionLineItemRepository;

    public List<TransactionLineItem> getAllTransactionLineItems() {
        return transactionLineItemRepository.findAll();
    }

    public TransactionLineItem getTransactionLineItemById(Long id) throws EntityNotFoundException {
        return transactionLineItemRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public TransactionLineItem createTransactionLineItem(TransactionLineItem transactionLineItem) {
        return transactionLineItemRepository.save(transactionLineItem);
    }

    public TransactionLineItem updateTransactionLineItem(Long id, TransactionLineItem transactionLineItem) throws EntityNotFoundException {
        TransactionLineItem oldTransactionLineItem = getTransactionLineItemById(id);
        if (transactionLineItem.getQuantity() != null && transactionLineItem.getQuantity() >= 0)
            oldTransactionLineItem.setQuantity(transactionLineItem.getQuantity());
        return transactionLineItemRepository.save(oldTransactionLineItem);
    }

    public void deleteTransactionLineItem(Long id) {
        transactionLineItemRepository.deleteById(id);
    }
}
