package com.kopo.web_final.domain.product.dto;

import com.kopo.web_final.domain.product.model.Product;

public class ProductDisplayDto {
    private Product product;
    private String categoryName;
    private int categoryId;
    private String fileId;

    public ProductDisplayDto() {
    }

    public ProductDisplayDto(Product product, String categoryName, int categoryId, String fileId) {
        this.product = product;
        this.categoryName = categoryName;
        this.categoryId = categoryId;
        this.fileId = fileId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}
