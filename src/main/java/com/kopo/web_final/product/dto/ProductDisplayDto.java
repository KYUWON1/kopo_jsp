package com.kopo.web_final.product.dto;

import com.kopo.web_final.product.model.Product;

public class ProductDisplayDto {
    private Product product;
    private String categoryName;
    private int categoryId;

    public ProductDisplayDto() {
    }

    public ProductDisplayDto(Product product, String categoryName, int categoryId) {
        this.product = product;
        this.categoryName = categoryName;
        this.categoryId = categoryId;
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
}
