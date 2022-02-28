package uxifier.api.ecommerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import uxifier.api.ecommerce.entities.ProductInCart;
import uxifier.api.ecommerce.services.CartService;

@RestController
@CrossOrigin()
public class CartController {

    @Autowired
    private CartService cartService;
    @GetMapping("/carts")

    public List<ProductInCart> fetchProductInCart(){
        return cartService.getContent();
    }
}
