package com.courseproject.pointofsaleservice.services;

import com.courseproject.pointofsaleservice.models.*;
import com.courseproject.pointofsaleservice.repositories.TransactionRepository;
import com.courseproject.pointofsaleservice.services.utils.InventoryFeignClient;
import com.courseproject.pointofsaleservice.services.utils.LoyaltyFeignClient;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final EmployeeService employeeService;
    private final CustomerService customerService;
    private final RegisterService registerService;
    private final ProductService productService;
    private final InventoryFeignClient inventoryFeignClient;
    private final LoyaltyFeignClient loyaltyFeignClient;

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction getTransactionById(Long id) throws EntityNotFoundException {
        return transactionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Transaction createTransaction(Transaction transaction) {
        Customer c = customerService.getCustomerById(transaction.getCustomer().getId());
        Employee e = employeeService.getEmployeeById(transaction.getEmployee().getId());
        Register r = registerService.findRegisterById(transaction.getRegister().getId());
        c.getTransactions().add(transaction);
        e.getTransactions().add(transaction);
        r.getTransactions().add(transaction);
        transaction.setCustomer(c);
        transaction.setEmployee(e);
        transaction.setRegister(r);
        return transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(Long id, Transaction transaction) throws EntityNotFoundException {
        Transaction oldTransaction = getTransactionById(id);
        Customer c = customerService.getCustomerById(transaction.getCustomer().getId());
        Employee e = employeeService.getEmployeeById(transaction.getEmployee().getId());
        Register r = registerService.findRegisterById(transaction.getRegister().getId());
        if (transaction.getTransactionLineItems() != null)
            oldTransaction.getTransactionLineItems().addAll(transaction.getTransactionLineItems());
        if (transaction.getEmployee() != null)
            oldTransaction.setEmployee(e);
        if (transaction.getCustomer() != null)
            oldTransaction.setCustomer(c);
        if (transaction.getRegister() != null)
            oldTransaction.setRegister(r);
        return transactionRepository.save(oldTransaction);
    }

    public Transaction addTransactionLineItem(Long id, TransactionLineItem transactionLineItem)
            throws EntityNotFoundException {
        if (transactionLineItem == null)
            throw new IllegalArgumentException("TransactionLineItem is null");
        Transaction oldTransaction = getTransactionById(id);
        Product p = productService.getProductById(transactionLineItem.getProduct().getId());
        transactionLineItem.setProduct(p);
        transactionLineItem.setTransaction(oldTransaction);
        p.getTransactionLineItems().add(transactionLineItem);
        oldTransaction.getTransactionLineItems().add(transactionLineItem);
        return transactionRepository.save(oldTransaction);
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    public Transaction completeTransaction(String token, Long id) {
        Transaction transaction = getTransactionById(id);
        if (transaction == null)
            throw new EntityNotFoundException("Transaction not found");

        deductProductQuantity(token, transaction);
        addLoyaltyRewards(token, transaction);

        transaction.setCompletedAt(LocalDateTime.now());
        Transaction savedTransaction = transactionRepository.save(transaction);
        log.info("Transaction completed successfully: {}", savedTransaction);
        return savedTransaction;
    }

    private void deductProductQuantity(String token, Transaction transaction) {
        transaction.getTransactionLineItems().forEach(transactionLineItem -> {
            Double qty = transactionLineItem.getQuantity();
            Long productId = transactionLineItem.getProduct().getId();
            try {
                ResponseEntity<Product> response = inventoryFeignClient.saleProduct(token, productId, qty);
                if (!response.getStatusCode().is2xxSuccessful()) {
                    throw new RuntimeException("Failed to update product quantity for product " + productId);
                }
            } catch (RestClientException e) {
                throw new RuntimeException("Error calling inventory service.", e);
            }
        });
    }

    private void addLoyaltyRewards(String token, Transaction transaction) {
        double pointsToAdd = transaction.getTransactionLineItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        try {
            ResponseEntity<Void> response = loyaltyFeignClient.creditLoyalty(token, transaction.getCustomer().getId(),
                    pointsToAdd);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Failed to create new loyalty customer");
            }
        } catch (RestClientException e) {
            throw new RuntimeException("Error calling loyalty service.", e);
        }
    }

}
