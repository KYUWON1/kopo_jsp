package com.kopo.web_final.domain.basket.dto;

public class BasketDto {
    private int nbBasket;               // 장바구니 고유 ID
    private String noUser;             // 사용자 ID
    private int qtBasketAmount;        // 장바구니 수량

    public BasketDto() {
    }

    public BasketDto(int nbBasket, String noUser, int qtBasketAmount) {
        this.nbBasket = nbBasket;
        this.noUser = noUser;
        this.qtBasketAmount = qtBasketAmount;
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
}
