package com.example.bank.repository;

import com.example.bank.bean.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository {
    Transaction save(Transaction transaction);

    Optional<Transaction> findById(String id);

    void deleteById(String id);

    List<Transaction> findAll();

    List<Transaction> getPage(int pageNumber, int pageSize);

    int getTotal();
}
