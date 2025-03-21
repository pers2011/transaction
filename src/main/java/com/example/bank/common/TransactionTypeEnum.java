package com.example.bank.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public enum TransactionTypeEnum {
    CASH("cash"),
    STOCK("stock");

    private final String type;

    public static List<String> getAllTypes() {
        List<String> types = new ArrayList<>();
        for (TransactionTypeEnum typeEnum : values()) {
            types.add(typeEnum.getType());
        }
        return types;
    }

    public static boolean isLegalType(String type) {
        return getAllTypes().contains(type);
    }


}
