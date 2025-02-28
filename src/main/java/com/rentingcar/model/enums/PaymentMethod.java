package com.rentingcar.model.enums;

public enum PaymentMethod {
    TIEN_MAT("Tiền mặt"),
    THE_TIN_DUNG("Thẻ tín dụng"),
    CHUYEN_KHOAN("Chuyển khoản"),
    VI_DIEN_TU("Ví điện tử");
    
    private final String value;
    
    PaymentMethod(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}