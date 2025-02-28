package com.rentingcar.model.enums;

public enum CarCategory {
    XE_4_CHO("Xe 4 chỗ"),
    XE_7_CHO("Xe 7 chỗ"),
    XE_16_CHO("Xe 16 chỗ"),
    XE_29_CHO("Xe 29 chỗ"),
    XE_BAN_TAI("Xe bán tải"),
    XE_SANG("Xe sang"),
    XE_THE_THAO("Xe thể thao"),
    XE_DU_LICH("Xe du lịch");
    
    private final String value;
    
    CarCategory(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}