package com.bee.store.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CatalogProductDto {
    private Long id;
    private String title;
    private float price;
    private String image;
    private float fee;
}
