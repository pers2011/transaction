package com.example.bank.service.impl;

import com.example.bank.bean.Transaction;
import com.example.bank.common.TransactionTypeEnum;
import com.example.bank.repository.TransactionRepository;
import com.example.bank.service.TransactionService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    @Override
    @RateLimiter(name ="transactionService")
    @CircuitBreaker(name = "transactionService", fallbackMethod = "fallbackCreateTransaction")
    public Transaction createTransaction(Transaction transaction) {
        if (transactionRepository.findById(transaction.getId()).isPresent()) {
            throw new IllegalArgumentException("Transaction with id " + transaction.getId() + " already exists");
        }
        if (!TransactionTypeEnum.isLegalType(transaction.getType())) {
            throw new IllegalArgumentException("Transaction type is illegal");
        }
        return transactionRepository.save(transaction);
    }

    @Override
    public void deleteTransaction(String transId) {
        if (transactionRepository.findById(transId).isEmpty()) {
            throw new IllegalArgumentException("Transaction with id " + transId + " does not exist");
        }
        transactionRepository.deleteById(transId);
    }

    @Override
    @Cacheable(value = "transactions", key = "#transId")
    public Transaction queryTransaction(String transId) {
        Optional<Transaction> transactionOpt = transactionRepository.findById(transId);
        return transactionOpt.orElseGet(Transaction::new);
    }

    @Override
    @CachePut(value = "transactions", key = "#transId")
    public Transaction updateTransaction(String transId, Transaction transaction) {
        if (transactionRepository.findById(transId).isEmpty()) {
            throw new IllegalArgumentException("Transaction with id " + transId + " does not exist");
        }
        if (!TransactionTypeEnum.isLegalType(transaction.getType())) {
            throw new IllegalArgumentException("Transaction type is illegal");
        }
        transaction.setId(transId);
        return transactionRepository.save(transaction);
    }

    @Override
    @RateLimiter(name ="transactionService")
    @CircuitBreaker(name = "transactionService", fallbackMethod = "fallbackListTransactions")
    public Page<Transaction> listTransactions(int page, int pageSize) {
        if (page < 1) {
            page = 1;
        }
        if (pageSize > 10) {
            pageSize = 10;
        }
        List<Transaction> allTransactions = transactionRepository.getPage(page, pageSize);
        int totalSize = transactionRepository.getTotal();
        return new PageImpl<>(allTransactions,
                PageRequest.of(page - 1, pageSize),
                totalSize);
    }

    @Async
    public CompletableFuture<Transaction> createTransactionAsync(Transaction transaction) {
        try {
            Transaction created = createTransaction(transaction);
            return CompletableFuture.completedFuture(created);
        } catch (Exception e) {
            // 异常处理
            return CompletableFuture.failedFuture(e);
        }
    }

    // 熔断降级方法
    private Transaction fallbackCreateTransaction(Transaction transaction, Throwable t) {
        // 返回空置
        return new Transaction();
    }

    private Page<Transaction> fallbackListTransactions(int page, int size, Throwable t) {
        // 返回空分页
        return new PageImpl<>(Collections.emptyList());
    }
}
