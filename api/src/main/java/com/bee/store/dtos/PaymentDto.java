package com.bee.store.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
public class PaymentDto {

    @NotEmpty
    private String accountNumber;
    List<CartItemDto> items;
}