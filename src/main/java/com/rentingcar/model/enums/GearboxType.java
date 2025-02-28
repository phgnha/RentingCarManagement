package com.rentingcar.model.enums;

public enum GearboxType {
    SO_SAN("Số sàn"),
    SO_TU_DONG("Số tự động"),
    SO_HON_HOP("Số hỗn hợp");
    
    private final String value;
    
    GearboxType(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}