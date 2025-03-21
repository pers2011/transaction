package com.example.bank.controller;

import com.example.bank.bean.Transaction;
import com.example.bank.common.Constants;
import com.example.bank.service.TransactionService;
import com.example.bank.util.ReturnEntity;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transactions")
@AllArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ReturnEntity createTransaction(@RequestBody @Valid Transaction transaction) {
        Transaction transaction1 = transactionService.createTransaction(transaction);
        return ReturnEntity.ok().put(Constants.RESULT, transaction1);
    }

    @DeleteMapping("/{id}")
    public ReturnEntity deleteTransaction(@PathVariable("id") String id) {
        transactionService.deleteTransaction(id);
        return ReturnEntity.ok();
    }

    @PutMapping("/{id}")
    public ReturnEntity updateTransaction(@PathVariable("id") String id, @RequestBody Transaction transaction) {
        return ReturnEntity.ok().put(Constants.RESULT, transactionService.updateTransaction(id, transaction));
    }

    @GetMapping("/{id}")
    public ReturnEntity queryTransactionById(@PathVariable("id") String id) {
        return ReturnEntity.ok().put(Constants.RESULT, transactionService.queryTransaction(id));
    }

    @GetMapping
    public ReturnEntity listTransactions(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        return ReturnEntity.ok().put(Constants.RESULT, transactionService.listTransactions(page, size));
    }

    @PostMapping("/batch-async")
    public CompletableFuture<ReturnEntity> createBatchAsync(@RequestBody List<Transaction> transactions) {
        List<CompletableFuture<Transaction>> futures = transactions.stream()
                .map(transactionService::createTransactionAsync)
                .collect(Collectors.toList());

        // 等待所有异步任务完成并汇总结果
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> {
                    List<Transaction> results = futures.stream()
                            .map(CompletableFuture::join)
                            .collect(Collectors.toList());
                    return ReturnEntity.ok().put(Constants.RESULT, results);
                });
    }
}
