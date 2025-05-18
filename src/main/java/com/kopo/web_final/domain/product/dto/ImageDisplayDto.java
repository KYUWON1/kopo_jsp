package com.kopo.web_final.domain.product.dto;

import java.sql.Blob;

public class ImageDisplayDto {
    private Blob blob;
    private int count;

    public ImageDisplayDto() {
    }

    public ImageDisplayDto(Blob blob, int count) {
        this.blob = blob;
        this.count = count;
    }

    public Blob getBlob() {
        return blob;
    }

    public void setBlob(Blob blob) {
        this.blob = blob;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
