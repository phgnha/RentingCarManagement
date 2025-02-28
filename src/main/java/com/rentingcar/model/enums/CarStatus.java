package com.rentingcar.model.enums;

public enum CarStatus {
    SAN_SANG("Sẵn sàng"),
    DANG_CHO_THUE("Đang cho thuê"),
    DANG_BAO_TRI("Đang bảo trì"),
    KHONG_KHA_DUNG("Không khả dụng");
    
    private final String value;
    
    CarStatus(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}