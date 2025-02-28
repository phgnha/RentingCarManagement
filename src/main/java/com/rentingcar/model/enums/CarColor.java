package com.rentingcar.model.enums;

public enum CarColor {
    DO("Đỏ"),
    XANH("Xanh"),
    DEN("Đen"),
    TRANG("Trắng"),
    BAC("Bạc"),
    XAM("Xám"),
    NAU("Nâu"),
    VANG_CAT("Vàng cát"),
    XANH_DEN("Xanh đen"),
    TRANG_NGOC_TRAI("Trắng ngọc trai");
    
    private final String value;
    
    CarColor(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}