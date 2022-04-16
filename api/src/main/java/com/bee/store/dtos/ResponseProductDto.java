package com.bee.store.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseProductDto {
    private long id;
    private String title;
    private float price;
    private String provider;
    private String image;
}
