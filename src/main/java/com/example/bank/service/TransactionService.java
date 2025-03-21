package com.example.bank.service;

import com.example.bank.bean.Transaction;
import org.springframework.data.domain.Page;

import java.util.concurrent.CompletableFuture;

public interface TransactionService {
    Transaction createTransaction(Transaction transaction);

    void deleteTransaction(String transId);

    Transaction queryTransaction(String transId);

    Transaction updateTransaction(String transId, Transaction transaction);

    Page<Transaction> listTransactions(int page, int pageSize);

    CompletableFuture<Transaction> createTransactionAsync(Transaction transaction);
}
