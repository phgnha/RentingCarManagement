package com.rentingcar.model.enums;

public enum InvoiceStatus {
    DA_THANH_TOAN("Đã thanh toán"),
    CHO_THANH_TOAN("Chờ thanh toán"),
    THANH_TOAN_THAT_BAI("Thanh toán thất bại"),
    HOAN_TIEN("Hoàn tiền"),
    DA_HUY("Đã hủy");
    
    private final String value;
    
    InvoiceStatus(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}