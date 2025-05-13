package com.kopo.web_final.order.dto;

public class GetOrderItemDto {
    private int noProduct;
    private int qtUnitPrice;
    private int qtOrderItem;
    private int qtOrderItemAmount;
    private int qtOrderItemDelivery;
    private String nmProduct;
    private String idFile;

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
}
