package com.kopo.web_final.basket.dto;

public class BasketItemDto {
    private int nbBasketItem;            // 장바구니 항목 ID (PK)
    private int cnBasketItemOrder;       // 항목 순서
    private String noProduct;            // 상품 코드
    private int qtBasketItemPrice;       // 상품 단가
    private int qtBasketItem;            // 수량
    private int qtBasketItemAmount;
    private String idFile;
    private String nmProduct;
    private int qtStock;

    public BasketItemDto() {
    }

    public BasketItemDto(int nbBasketItem, int cnBasketItemOrder, String noProduct, int qtBasketItemPrice, int qtBasketItem, int qtBasketItemAmount, String idFile, String nmProduct, int qtStock) {
        this.nbBasketItem = nbBasketItem;
        this.cnBasketItemOrder = cnBasketItemOrder;
        this.noProduct = noProduct;
        this.qtBasketItemPrice = qtBasketItemPrice;
        this.qtBasketItem = qtBasketItem;
        this.qtBasketItemAmount = qtBasketItemAmount;
        this.idFile = idFile;
        this.nmProduct = nmProduct;
        this.qtStock = qtStock;
    }

    public int getNbBasketItem() {
        return nbBasketItem;
    }

    public void setNbBasketItem(int nbBasketItem) {
        this.nbBasketItem = nbBasketItem;
    }

    public int getCnBasketItemOrder() {
        return cnBasketItemOrder;
    }

    public void setCnBasketItemOrder(int cnBasketItemOrder) {
        this.cnBasketItemOrder = cnBasketItemOrder;
    }

    public String getNoProduct() {
        return noProduct;
    }

    public void setNoProduct(String noProduct) {
        this.noProduct = noProduct;
    }

    public int getQtBasketItemPrice() {
        return qtBasketItemPrice;
    }

    public void setQtBasketItemPrice(int qtBasketItemPrice) {
        this.qtBasketItemPrice = qtBasketItemPrice;
    }

    public int getQtBasketItem() {
        return qtBasketItem;
    }

    public void setQtBasketItem(int qtBasketItem) {
        this.qtBasketItem = qtBasketItem;
    }

    public int getQtBasketItemAmount() {
        return qtBasketItemAmount;
    }

    public void setQtBasketItemAmount(int qtBasketItemAmount) {
        this.qtBasketItemAmount = qtBasketItemAmount;
    }

    public String getIdFile() {
        return idFile;
    }

    public void setIdFile(String idFile) {
        this.idFile = idFile;
    }

    public String getNmProduct() {
        return nmProduct;
    }

    public void setNmProduct(String nmProduct) {
        this.nmProduct = nmProduct;
    }

    public int getQtStock() {
        return qtStock;
    }

    public void setQtStock(int qtStock) {
        this.qtStock = qtStock;
    }
}
