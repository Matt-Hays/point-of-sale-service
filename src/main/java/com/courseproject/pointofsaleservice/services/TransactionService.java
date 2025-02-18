package com.courseproject.pointofsaleservice.services;

import com.courseproject.pointofsaleservice.models.Transaction;
import com.courseproject.pointofsaleservice.repositories.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction getTransactionById(UUID id) throws EntityNotFoundException {
        return transactionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(UUID id, Transaction transaction) throws EntityNotFoundException {
        Transaction oldTransaction = getTransactionById(id);
        if (transaction.getTransactionLineItems() != null)
            oldTransaction.getTransactionLineItems().addAll(transaction.getTransactionLineItems());
        if (transaction.getEmployee() != null) oldTransaction.setEmployee(transaction.getEmployee());
        if (transaction.getCustomer() != null) oldTransaction.setCustomer(transaction.getCustomer());
        if (transaction.getRegister() != null) oldTransaction.setRegister(transaction.getRegister());
        return transactionRepository.save(oldTransaction);
    }

    public void deleteTransaction(UUID id) {
        transactionRepository.deleteById(id);
    }
}
