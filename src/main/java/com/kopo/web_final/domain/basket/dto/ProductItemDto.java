package com.kopo.web_final.domain.basket.dto;

public class ProductItemDto {
    private int noProduct;            // 상품 코드
    private String nmProduct;
    private int qtCustomer;
    private int qtDeliveryFee;
    private String idFile;
    private int quantity;

    public ProductItemDto() {

    }

    public ProductItemDto(int noProduct, String nmProduct, int qtCustomer, int qtDeliveryFee, String idFile) {
        this.noProduct = noProduct;
        this.nmProduct = nmProduct;
        this.qtCustomer = qtCustomer;
        this.qtDeliveryFee = qtDeliveryFee;
        this.idFile = idFile;
    }

    public int getNoProduct() {
        return noProduct;
    }

    public void setNoProduct(int noProduct) {
        this.noProduct = noProduct;
    }

    public String getNmProduct() {
        return nmProduct;
    }

    public void setNmProduct(String nmProduct) {
        this.nmProduct = nmProduct;
    }

    public int getQtCustomer() {
        return qtCustomer;
    }

    public void setQtCustomer(int qtCustomer) {
        this.qtCustomer = qtCustomer;
    }

    public int getQtDeliveryFee() {
        return qtDeliveryFee;
    }

    public void setQtDeliveryFee(int qtDeliveryFee) {
        this.qtDeliveryFee = qtDeliveryFee;
    }

    public String getIdFile() {
        return idFile;
    }

    public void setIdFile(String idFile) {
        this.idFile = idFile;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
