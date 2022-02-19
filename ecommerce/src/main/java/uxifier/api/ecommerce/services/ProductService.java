package uxifier.api.ecommerce.services;

import java.util.List;

import org.springframework.stereotype.Service;

import uxifier.api.ecommerce.entities.Product;

@Service
public class ProductService {
    

    public List<Product> findAll(){
        return List.of(
            new Product("Sac en cuir", 300.5, "https://www.eram.fr/media/catalog/product/cache/1/image/1800x/040ec09b1e35df139433887a97daa66f/W/W/sac-a-main-camel-WWWERAM_10693840618_1.jpg")
        );
    }
}
