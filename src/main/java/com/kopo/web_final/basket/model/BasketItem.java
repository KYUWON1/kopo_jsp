package com.kopo.web_final.basket.model;

import java.time.LocalDate;

public class BasketItem {
    private int nbBasketItem;            // 장바구니 항목 ID (PK)
    private int nbBasket;                // 장바구니 ID (FK)
    private int cnBasketItemOrder;       // 항목 순서
    private String noProduct;            // 상품 코드
    private String noUser;               // 사용자 ID
    private int qtBasketItemPrice;       // 상품 단가
    private int qtBasketItem;            // 수량
    private int qtBasketItemAmount;      // 총 금액
    private String noRegister;           // 등록자
    private LocalDate daFirstDate;       // 최초 등록일

    public BasketItem() {
    }

    public BasketItem(int nbBasketItem, int nbBasket, int cnBasketItemOrder, String noProduct, String noUser, int qtBasketItemPrice, int qtBasketItem, int qtBasketItemAmount, String noRegister, LocalDate daFirstDate) {
        this.nbBasketItem = nbBasketItem;
        this.nbBasket = nbBasket;
        this.cnBasketItemOrder = cnBasketItemOrder;
        this.noProduct = noProduct;
        this.noUser = noUser;
        this.qtBasketItemPrice = qtBasketItemPrice;
        this.qtBasketItem = qtBasketItem;
        this.qtBasketItemAmount = qtBasketItemAmount;
        this.noRegister = noRegister;
        this.daFirstDate = daFirstDate;
    }

    public int getNbBasketItem() {
        return nbBasketItem;
    }

    public void setNbBasketItem(int nbBasketItem) {
        this.nbBasketItem = nbBasketItem;
    }

    public int getNbBasket() {
        return nbBasket;
    }

    public void setNbBasket(int nbBasket) {
        this.nbBasket = nbBasket;
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

    public String getNoUser() {
        return noUser;
    }

    public void setNoUser(String noUser) {
        this.noUser = noUser;
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
