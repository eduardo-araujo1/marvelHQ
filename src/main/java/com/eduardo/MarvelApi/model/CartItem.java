package com.eduardo.MarvelApi.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class CartItem {
    private Long productId;

    private String productName;

    private String image;

    private BigDecimal price;

    private Integer quantity;
}
