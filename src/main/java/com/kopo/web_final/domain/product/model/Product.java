package com.kopo.web_final.domain.product.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

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
    private LocalDate daFirstDate;

    public Product() {
    }

    public static Product BuildProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setNoProduct(rs.getString("NO_PRODUCT"));
        product.setNmProduct(rs.getString("NM_PRODUCT"));
        product.setNmDetailExplain(rs.getString("NM_DETAIL_EXPLAIN"));
        product.setIdFile(rs.getString("ID_FILE"));
        product.setDtStartDate(rs.getString("DT_START_DATE"));
        product.setDtEndDate(rs.getString("DT_END_DATE"));
        product.setQtCustomer(rs.getInt("QT_CUSTOMER"));
        product.setQtSalePrice(rs.getInt("QT_SALE_PRICE"));
        product.setQtStock(rs.getInt("QT_STOCK"));
        product.setQtDeliveryFee(rs.getInt("QT_DELIVERY_FEE"));
        product.setNoRegister(rs.getString("NO_REGISTER"));
        product.setDaFirstDate(fromSqlDate(rs.getDate("DA_FIRST_DATE")));
        return product;
    }

    public Product(String noProduct, String nmProduct, String nmDetailExplain, String idFile, String dtStartDate, String dtEndDate, int qtCustomer, int qtSalePrice, int qtStock, int qtDeliveryFee, String noRegister, LocalDate daFirstDate) {
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

    public LocalDate getDaFirstDate() {
        return daFirstDate;
    }

    public void setDaFirstDate(LocalDate daFirstDate) {
        this.daFirstDate = daFirstDate;
    }

    private static LocalDate fromSqlDate(java.sql.Date sqlDate) {
        return sqlDate != null ? sqlDate.toLocalDate() : null;
    }
}