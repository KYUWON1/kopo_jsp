package com.kopo.web_final.basket.model;

import java.time.LocalDate;

public class Basket {
    private int nbBasket;               // 장바구니 고유 ID
    private String noUser;             // 사용자 ID
    private int qtBasketAmount;        // 장바구니 수량
    private String noRegister;         // 등록자 ID
    private LocalDate daFirstDate;

    public Basket() {
    }

    public Basket(int nbBasket, String noUser, int qtBasketAmount, String noRegister, LocalDate daFirstDate) {
        this.nbBasket = nbBasket;
        this.noUser = noUser;
        this.qtBasketAmount = qtBasketAmount;
        this.noRegister = noRegister;
        this.daFirstDate = daFirstDate;
    }

    public int getNbBasket() {
        return nbBasket;
    }

    public void setNbBasket(int nbBasket) {
        this.nbBasket = nbBasket;
    }

    public String getNoUser() {
        return noUser;
    }

    public void setNoUser(String noUser) {
        this.noUser = noUser;
    }

    public int getQtBasketAmount() {
        return qtBasketAmount;
    }

    public void setQtBasketAmount(int qtBasketAmount) {
        this.qtBasketAmount = qtBasketAmount;
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
