package com.kopo.web_final.product.model;

import java.sql.Date;

public class Product {
    private String noProduct;
    private String nmProduct;
    private String nmDetailExplain;
    private String idFile;
    private String dtStartDate;
    private String dtEndDate;
    private int qtCustomer;
    private int qtSalePrice;
    private int qtStock;
    private int qtDeliveryFee;
    private String noRegister;
    private Date daFirstDate;

    public Product() {
    }

    public Product(String noProduct, String nmProduct, String nmDetailExplain, String idFile, String dtStartDate, String dtEndDate, int qtCustomer, int qtSalePrice, int qtStock, int qtDeliveryFee, String noRegister, Date daFirstDate) {
        this.noProduct = noProduct;
        this.nmProduct = nmProduct;
        this.nmDetailExplain = nmDetailExplain;
        this.idFile = idFile;
        this.dtStartDate = dtStartDate;
        this.dtEndDate = dtEndDate;
        this.qtCustomer = qtCustomer;
        this.qtSalePrice = qtSalePrice;
        this.qtStock = qtStock;
        this.qtDeliveryFee = qtDeliveryFee;
        this.noRegister = noRegister;
        this.daFirstDate = daFirstDate;
    }

    public String getNoProduct() {
        return noProduct;
    }

    public void setNoProduct(String noProduct) {
        this.noProduct = noProduct;
    }

    public String getNmProduct() {
        return nmProduct;
    }

    public void setNmProduct(String nmProduct) {
        this.nmProduct = nmProduct;
    }

    public String getNmDetailExplain() {
        return nmDetailExplain;
    }

    public void setNmDetailExplain(String nmDetailExplain) {
        this.nmDetailExplain = nmDetailExplain;
    }

    public String getIdFile() {
        return idFile;
    }

    public void setIdFile(String idFile) {
        this.idFile = idFile;
    }

    public String getDtStartDate() {
        return dtStartDate;
    }

    public void setDtStartDate(String dtStartDate) {
        this.dtStartDate = dtStartDate;
    }

    public String getDtEndDate() {
        return dtEndDate;
    }

    public void setDtEndDate(String dtEndDate) {
        this.dtEndDate = dtEndDate;
    }

    public int getQtCustomer() {
        return qtCustomer;
    }

    public void setQtCustomer(int qtCustomer) {
        this.qtCustomer = qtCustomer;
    }

    public int getQtSalePrice() {
        return qtSalePrice;
    }

    public void setQtSalePrice(int qtSalePrice) {
        this.qtSalePrice = qtSalePrice;
    }

    public int getQtStock() {
        return qtStock;
    }

    public void setQtStock(int qtStock) {
        this.qtStock = qtStock;
    }

    public int getQtDeliveryFee() {
        return qtDeliveryFee;
    }

    public void setQtDeliveryFee(int qtDeliveryFee) {
        this.qtDeliveryFee = qtDeliveryFee;
    }

    public String getNoRegister() {
        return noRegister;
    }

    public void setNoRegister(String noRegister) {
        this.noRegister = noRegister;
    }

    public Date getDaFirstDate() {
        return daFirstDate;
    }

    public void setDaFirstDate(Date daFirstDate) {
        this.daFirstDate = daFirstDate;
    }
}
