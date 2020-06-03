package com.mega.print.enity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long bucket_id;
    private String page_format;
    private String type_format;
    private String black_percentage_product;
    private String colored_percentage_product;
    private String path_product;
    private String path_PDF_product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBucket_id() {
        return bucket_id;
    }

    public void setBucket_id(Long bucket_id) {
        this.bucket_id = bucket_id;
    }

    public String getPage_format() {
        return page_format;
    }

    public void setPage_format(String page_format) {
        this.page_format = page_format;
    }

    public String getType_format() {
        return type_format;
    }

    public void setType_format(String type_format) {
        this.type_format = type_format;
    }

    public String getBlack_percentage_product() {
        return black_percentage_product;
    }

    public void setBlack_percentage_product(String black_percentage_product) {
        this.black_percentage_product = black_percentage_product;
    }

    public String getColored_percentage_product() {
        return colored_percentage_product;
    }

    public void setColored_percentage_product(String colored_percentage_product) {
        this.colored_percentage_product = colored_percentage_product;
    }

    public String getPath_product() {
        return path_product;
    }

    public void setPath_product(String path_product) {
        this.path_product = path_product;
    }

    public String getPath_PDF_product() {
        return path_PDF_product;
    }

    public void setPath_PDF_product(String path_PDF_product) {
        this.path_PDF_product = path_PDF_product;
    }
}
