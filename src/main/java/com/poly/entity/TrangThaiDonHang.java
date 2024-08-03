package com.poly.entity;

public enum TrangThaiDonHang {

	HUY_DON("1"), 
	DANG_XU_LY("2"), 
	HOAN_TAT("3"),
	CHUA_THANH_TOAN("4"),
	DA_THANH_TOAN("5");

	private final String code;

	TrangThaiDonHang(String code) {
            this.code = code;
        }

	public String getCode() {
		return code;
	}

}
