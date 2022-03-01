package uxifier.api.ecommerce.services;

import java.util.List;

import org.springframework.stereotype.Service;

import uxifier.api.ecommerce.entities.ProductInCart;

@Service
public class CartService {
    public List<ProductInCart> getContent(){
        return List.of(
            new ProductInCart("Chaussure", 200, "https://exclusifparis.com/10200-thickbox_default/brosnam.jpg", 2),
            new ProductInCart("Chemise", 300, "https://exclusifparis.com/10200-thickbox_default/brosnam.jpg", 2)

            );

    }
}
