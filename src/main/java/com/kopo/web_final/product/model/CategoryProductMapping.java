package com.kopo.web_final.product.model;

import java.time.LocalDate;

public class CategoryProductMapping {
    private int nbCategory;
    private String noProduct;
    private int cnOrder;
    private String noRegister;
    private LocalDate  daFirstDate;

    public CategoryProductMapping() {
    }

    public CategoryProductMapping(int nbCategory, String noProduct, int cnOrder, String noRegister, LocalDate  daFirstDate) {
        this.nbCategory = nbCategory;
        this.noProduct = noProduct;
        this.cnOrder = cnOrder;
        this.noRegister = noRegister;
        this.daFirstDate = daFirstDate;
    }

    public int getNbCategory() {
        return nbCategory;
    }

    public void setNbCategory(int nbCategory) {
        this.nbCategory = nbCategory;
    }

    public String getNoProduct() {
        return noProduct;
    }

    public void setNoProduct(String noProduct) {
        this.noProduct = noProduct;
    }

    public int getCnOrder() {
        return cnOrder;
    }

    public void setCnOrder(int cnOrder) {
        this.cnOrder = cnOrder;
    }

    public String getNoRegister() {
        return noRegister;
    }

    public void setNoRegister(String noRegister) {
        this.noRegister = noRegister;
    }

    public LocalDate  getDaFirstDate() {
        return daFirstDate;
    }

    public void setDaFirstDate(LocalDate  daFirstDate) {
        this.daFirstDate = daFirstDate;
    }
}
