package com.example.bank.controller;

import com.example.bank.repository.TransactionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class WebController {
    private final TransactionRepository repository;

    public WebController(TransactionRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public RedirectView  index(Model model) {
        return new RedirectView("index.html");
    }
}
