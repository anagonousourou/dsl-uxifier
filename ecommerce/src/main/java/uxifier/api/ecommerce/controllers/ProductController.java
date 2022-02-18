package uxifier.api.ecommerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import uxifier.api.ecommerce.entities.Product;
import uxifier.api.ecommerce.services.ProductService;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public List<Product> fetchProducts(){
        return this.productService.findAll();
    }
}
