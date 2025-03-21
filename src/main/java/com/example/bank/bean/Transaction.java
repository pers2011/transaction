package com.example.bank.bean;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Transaction implements Serializable {
    private static final long serialVersionUID = 2669293150219020249L;

    // 为方便测试 长度限制10位
    @Pattern(regexp = "^[a-zA-Z0-9]{10}$")
    private String id;

    @Positive(message = "Amount must be positive")
    private BigDecimal amount;


    @NotBlank(message = "Type cannot be empty")
    private String type;

    private String description;

    private LocalDateTime dateTime = LocalDateTime.now();
}
