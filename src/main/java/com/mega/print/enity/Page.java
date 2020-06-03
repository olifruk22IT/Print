package com.mega.print.enity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String black_percentage_page;
    private String colored_percentage_page;
    private int sum_page;
    private Long product_id;

}
