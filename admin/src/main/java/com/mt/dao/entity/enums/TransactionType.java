package com.mt.dao.entity.enums;

public enum TransactionType {
    DEPOSIT("deposit"),
    WITHDRAW("withdraw"),
    TRANSFER("transfer");

    private final String value;

    TransactionType(String value) {
        this.value = value;
    }

    // 根据字符串值获取对应的枚举实例
    public static TransactionType fromValue(String value) {
        for (TransactionType type : TransactionType.values()) {
            if (type.getValue().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown transaction type: " + value);
    }

    public String getValue() {
        return value;
    }
}