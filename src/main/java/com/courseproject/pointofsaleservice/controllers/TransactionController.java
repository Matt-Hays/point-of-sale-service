package com.courseproject.pointofsaleservice.controllers;

import com.courseproject.pointofsaleservice.models.Transaction;
import com.courseproject.pointofsaleservice.models.TransactionLineItem;
import com.courseproject.pointofsaleservice.services.TransactionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("v1/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/{id}/item")
    public ResponseEntity<Transaction> addTransactionLineItems(@PathVariable UUID id, @RequestBody @Valid TransactionLineItem transactionLineItem) {
        try {
            Transaction transaction = transactionService.getTransactionById(id);
            transactionLineItem.setTransaction(transaction);
            transaction.getTransactionLineItems().add(transactionLineItem);
            return ResponseEntity.ok(transactionService.updateTransaction(transaction.getId(), transaction));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(transactionService.getTransactionById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Transaction createTransaction(@RequestBody @Valid Transaction transaction) {
        return transactionService.createTransaction(transaction);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable UUID id, @RequestBody @Valid Transaction transaction) {
        try {
            return ResponseEntity.ok(transactionService.updateTransaction(id, transaction));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable UUID id) {
        transactionService.deleteTransaction(id);
    }
}
