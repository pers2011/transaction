package com.example.bank.repository.impl;

import com.example.bank.bean.Transaction;
import com.example.bank.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TransactionRepositoryImpl implements TransactionRepository {
    private final Map<String, Transaction> transactionMap = new ConcurrentHashMap<>();

    @Override
    public Transaction save(Transaction transaction) {
        transactionMap.put(transaction.getId(), transaction);
        return transaction;
    }

    @Override
    public Optional<Transaction> findById(String id) {
        return Optional.ofNullable(transactionMap.get(id));
    }

    @Override
    public void deleteById(String id) {
        transactionMap.remove(id);
    }

    @Override
    public List<Transaction> findAll() {
        return new ArrayList<>(transactionMap.values());
    }

    @Override
    public List<Transaction> getPage(int pageNumber, int pageSize) {
        int startIndex = (pageNumber - 1) * pageSize;
        int endIndex = startIndex + pageSize;

        List<Transaction> pageData = new ArrayList<>();
        int index = 0;
        for (Map.Entry<String, Transaction> entry : transactionMap.entrySet()) {
            if (index >= startIndex && index < endIndex) {
                pageData.add(entry.getValue());
            }
            index++;
        }
        return pageData;
    }

    @Override
    public int getTotal() {
        return transactionMap.size();
    }
}
