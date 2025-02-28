package com.rentingcar.model.enums;

public enum ContractStatus {
    CHO_DUYET("Chờ duyệt"),
    DANG_THUC_HIEN("Đang thực hiện"),
    HOAN_THANH("Hoàn thành"),
    DA_HUY("Đã hủy"),
    VI_PHAM("Vi phạm");
    
    private final String value;
    
    ContractStatus(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}