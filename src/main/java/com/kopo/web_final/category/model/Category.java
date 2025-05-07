package com.kopo.web_final.category.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class Category {
    private int nbCategory;
    private Integer nbParentCategory;
    private String nmCategory;
    private String nmFullCategory;
    private String nmExplain;
    private int cnLevel;
    private int cnOrder;
    private String ynUse;
    private String ynDelete;
    private String noRegister;
    private LocalDate daFirstDate;

    public Category() {
    }

    public static Category buildCategory(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setNbCategory(rs.getInt("nb_category"));
        category.setNbParentCategory(rs.getInt("nb_parent_category"));
        category.setNmCategory(rs.getString("nm_category"));
        category.setNmFullCategory(rs.getString("nm_full_category"));
        category.setNmExplain(rs.getString("nm_explain"));
        category.setCnLevel(rs.getInt("cn_level"));
        category.setCnOrder(rs.getInt("cn_order"));
        category.setYnUse(rs.getString("yn_use"));
        category.setYnDelete(rs.getString("yn_delete"));
        category.setNoRegister(rs.getString("no_register"));
        category.setDaFirstDate(fromSqlDate(rs.getDate("da_first_date")));

        return category;
    }

    public Category(int nbCategory, Integer nbParentCategory, String nmCategory, String nmFullCategory, String nmExplain, int cnLevel, int cnOrder, String ynUse, String ynDelete, String noRegister, LocalDate daFirstDate) {
        this.nbCategory = nbCategory;
        this.nbParentCategory = nbParentCategory;
        this.nmCategory = nmCategory;
        this.nmFullCategory = nmFullCategory;
        this.nmExplain = nmExplain;
        this.cnLevel = cnLevel;
        this.cnOrder = cnOrder;
        this.ynUse = ynUse;
        this.ynDelete = ynDelete;
        this.noRegister = noRegister;
        this.daFirstDate = daFirstDate;
    }

    public int getNbCategory() {
        return nbCategory;
    }

    public void setNbCategory(int nbCategory) {
        this.nbCategory = nbCategory;
    }

    public Integer getNbParentCategory() {
        return nbParentCategory;
    }

    public void setNbParentCategory(Integer nbParentCategory) {
        this.nbParentCategory = nbParentCategory;
    }

    public String getNmCategory() {
        return nmCategory;
    }

    public void setNmCategory(String nmCategory) {
        this.nmCategory = nmCategory;
    }

    public String getNmFullCategory() {
        return nmFullCategory;
    }

    public void setNmFullCategory(String nmFullCategory) {
        this.nmFullCategory = nmFullCategory;
    }

    public String getNmExplain() {
        return nmExplain;
    }

    public void setNmExplain(String nmExplain) {
        this.nmExplain = nmExplain;
    }

    public int getCnLevel() {
        return cnLevel;
    }

    public void setCnLevel(int cnLevel) {
        this.cnLevel = cnLevel;
    }

    public int getCnOrder() {
        return cnOrder;
    }

    public void setCnOrder(int cnOrder) {
        this.cnOrder = cnOrder;
    }

    public String getYnUse() {
        return ynUse;
    }

    public void setYnUse(String ynUse) {
        this.ynUse = ynUse;
    }

    public String getYnDelete() {
        return ynDelete;
    }

    public void setYnDelete(String ynDelete) {
        this.ynDelete = ynDelete;
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
