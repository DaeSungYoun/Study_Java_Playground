package com.ydskingdom.batch.testjob3.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Product {

    @Id
    private Long id;
    private String name;
    private int price;
    private String type;
}
