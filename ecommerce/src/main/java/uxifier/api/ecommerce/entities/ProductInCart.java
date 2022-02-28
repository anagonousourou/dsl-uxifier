package uxifier.api.ecommerce.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductInCart {
        
    private String name;
    private double price;
    private String url;
    private int quantity;
}
