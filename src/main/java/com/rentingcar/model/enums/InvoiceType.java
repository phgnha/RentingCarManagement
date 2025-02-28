package com.rentingcar.model.enums;

public enum InvoiceType {
    PHI_THUE_XE("Phí thuê xe"),
    TIEN_COC("Tiền cọc"),
    PHI_TRE_HAN("Phí trễ hạn"),
    PHI_DICH_VU("Phí dịch vụ"),
    PHI_BAO_HIEM("Phí bảo hiểm"),
    PHI_PHAT("Phí phạt");
    
    private final String value;
    
    InvoiceType(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}