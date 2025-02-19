package com.courseproject.pointofsaleservice.services;

import com.courseproject.pointofsaleservice.models.*;
import com.courseproject.pointofsaleservice.repositories.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final EmployeeService employeeService;
    private final CustomerService customerService;
    private final RegisterService registerService;
    private final ProductService productService;

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
        if (transaction.getEmployee() != null) oldTransaction.setEmployee(e);
        if (transaction.getCustomer() != null) oldTransaction.setCustomer(c);
        if (transaction.getRegister() != null) oldTransaction.setRegister(r);
        return transactionRepository.save(oldTransaction);
    }

    public Transaction addTransactionLineItem(Long id, TransactionLineItem transactionLineItem) throws EntityNotFoundException {
        if (transactionLineItem == null) throw new IllegalArgumentException("TransactionLineItem is null");
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
}
