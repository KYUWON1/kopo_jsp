package com.kopo.web_final.domain.order.model;

import java.time.LocalDate;

public class OrderItem {
    private String idOrderItem;             // 주문 상세 ID (PK)
    private String idOrder;                 // 주문 ID (FK)
    private int cnOrderItem;            // 주문 내 항목 순번

    private String noProduct;               // 상품 ID
    private String noUser;                  // 사용자 ID

    private int qtUnitPrice;            // 상품 단가
    private int qtOrderItem;            // 수량
    private int qtOrderItemAmount;      // 항목 총액 (단가 * 수량)
    private int qtOrderItemDeliveryFee; // 항목별 배송비

    private String stPayment;               // 결제 상태 코드

    private String noRegister;              // 등록자 ID
    private LocalDate daFirstDate;               // 등록일시

    public OrderItem() {
    }

    public OrderItem(String idOrderItem, String idOrder, int cnOrderItem, String noProduct, String noUser, int qtUnitPrice, int qtOrderItem, int qtOrderItemAmount, int qtOrderItemDeliveryFee, String stPayment, String noRegister, LocalDate daFirstDate) {
        this.idOrderItem = idOrderItem;
        this.idOrder = idOrder;
        this.cnOrderItem = cnOrderItem;
        this.noProduct = noProduct;
        this.noUser = noUser;
        this.qtUnitPrice = qtUnitPrice;
        this.qtOrderItem = qtOrderItem;
        this.qtOrderItemAmount = qtOrderItemAmount;
        this.qtOrderItemDeliveryFee = qtOrderItemDeliveryFee;
        this.stPayment = stPayment;
        this.noRegister = noRegister;
        this.daFirstDate = daFirstDate;
    }

    public String getIdOrderItem() {
        return idOrderItem;
    }

    public void setIdOrderItem(String idOrderItem) {
        this.idOrderItem = idOrderItem;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public int getCnOrderItem() {
        return cnOrderItem;
    }

    public void setCnOrderItem(int cnOrderItem) {
        this.cnOrderItem = cnOrderItem;
    }

    public String getNoProduct() {
        return noProduct;
    }

    public void setNoProduct(String noProduct) {
        this.noProduct = noProduct;
    }

    public String getNoUser() {
        return noUser;
    }

    public void setNoUser(String noUser) {
        this.noUser = noUser;
    }

    public int getQtUnitPrice() {
        return qtUnitPrice;
    }

    public void setQtUnitPrice(int qtUnitPrice) {
        this.qtUnitPrice = qtUnitPrice;
    }

    public int getQtOrderItem() {
        return qtOrderItem;
    }

    public void setQtOrderItem(int qtOrderItem) {
        this.qtOrderItem = qtOrderItem;
    }

    public int getQtOrderItemAmount() {
        return qtOrderItemAmount;
    }

    public void setQtOrderItemAmount(int qtOrderItemAmount) {
        this.qtOrderItemAmount = qtOrderItemAmount;
    }

    public int getQtOrderItemDeliveryFee() {
        return qtOrderItemDeliveryFee;
    }

    public void setQtOrderItemDeliveryFee(int qtOrderItemDeliveryFee) {
        this.qtOrderItemDeliveryFee = qtOrderItemDeliveryFee;
    }

    public String getStPayment() {
        return stPayment;
    }

    public void setStPayment(String stPayment) {
        this.stPayment = stPayment;
    }

    public String getNoRegister() {
        return noRegister;
    }

    public void setNoRegister(String noRegister) {
        this.noRegister = noRegister;
    }

    public LocalDate getDaFirstDate() {
        return daFirstDate;
    }

    public void setDaFirstDate(LocalDate daFirstDate) {
        this.daFirstDate = daFirstDate;
    }
}
