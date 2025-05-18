package com.kopo.web_final.domain.order.dto;

import java.time.LocalDate;

public class GetOrderItemDto {
    private int noProduct;
    private int qtUnitPrice;
    private int qtOrderItem;
    private int qtOrderItemAmount;
    private int qtOrderItemDelivery;
    private String nmProduct;
    private String idFile;

    // 관리자용 데이터
    private String stPayment;               // 결제 상태 코드

    private String noRegister;              // 등록자 ID
    private LocalDate daFirstDate;               // 등록일시


    private String idOrderItem;             // 주문 상세 ID (PK)
    private String idOrder;                 // 주문 ID (FK)
    private int cnOrderItem;            // 주문 내 항목 순번


    public GetOrderItemDto() {
    }

    public GetOrderItemDto(int noProduct,int qtUnitPrice, int qtOrderItem, int qtOrderItemAmount, int qtOrderItemDelivery, String nmProduct, String idFile) {
        this.noProduct = noProduct;
        this.qtUnitPrice = qtUnitPrice;
        this.qtOrderItem = qtOrderItem;
        this.qtOrderItemAmount = qtOrderItemAmount;
        this.qtOrderItemDelivery = qtOrderItemDelivery;
        this.nmProduct = nmProduct;
        this.idFile = idFile;
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

    public int getQtOrderItemDelivery() {
        return qtOrderItemDelivery;
    }

    public void setQtOrderItemDelivery(int qtOrderItemDelivery) {
        this.qtOrderItemDelivery = qtOrderItemDelivery;
    }

    public String getNmProduct() {
        return nmProduct;
    }

    public void setNmProduct(String nmProduct) {
        this.nmProduct = nmProduct;
    }

    public String getIdFile() {
        return idFile;
    }

    public void setIdFile(String idFile) {
        this.idFile = idFile;
    }

    public int getNoProduct() {
        return noProduct;
    }

    public void setNoProduct(int noProduct) {
        this.noProduct = noProduct;
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
}
