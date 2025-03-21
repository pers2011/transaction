package com.example.bank.controller;

import com.example.bank.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TransactionService service;


    @Test
    public void createTransaction() throws Exception {
        mockMvc.perform(post("/api/transactions")
                        .contentType("application/json")
                        .content("{\"id\" : \"1234567891\",\"amount\": 12,\"type\": \"cash\",\"description\": \"test\"}"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("\"msg\":\"success\"")));
    }

    @Test
    void deleteTransaction() throws Exception {
        mockMvc.perform(post("/api/transactions")
                .contentType("application/json")
                .content("{\"id\" : \"1234567891\",\"amount\": 12,\"type\": \"cash\",\"description\": \"test\"}"));
        mockMvc.perform(delete("/api/transactions/1234567891")
                        .contentType("application/json"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("\"msg\":\"success\"")));
    }

    @Test
    void updateTransaction() throws Exception {
        mockMvc.perform(post("/api/transactions")
                .contentType("application/json")
                .content("{\"id\" : \"1234567891\",\"amount\": 12,\"type\": \"cash\",\"description\": \"test\"}"));
        mockMvc.perform(put("/api/transactions/1234567891")
                        .contentType("application/json")
                        .content("{\"amount\": 123,\"type\": \"cash\"}"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("\"amount\":123")));
    }

    @Test
    void queryTransactionById() throws Exception {
        mockMvc.perform(post("/api/transactions")
                .contentType("application/json")
                .content("{\"id\" : \"1234567891\",\"amount\": 12,\"type\": \"cash\",\"description\": \"test\"}"));
        mockMvc.perform(get("/api/transactions/1234567891")
                .contentType("application/json"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("\"amount\":12")));
    }


    @Test
    void listTransactions() throws Exception {
        mockMvc.perform(post("/api/transactions")
                .contentType("application/json")
                .content("{\"id\" : \"1234567890\",\"amount\": 12,\"type\": \"cash\"}"));
        mockMvc.perform(post("/api/transactions")
                .contentType("application/json")
                .content("{\"id\" : \"1234567891\",\"amount\": 12,\"type\": \"cash\"}"));
        mockMvc.perform(get("/api/transactions?page=1&size=10")
                        .contentType("application/json"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("\"numberOfElements\":2")));

    }
}