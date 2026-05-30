package se.jensen.felicia.cloudstore.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.jensen.felicia.cloudstore.dto.ProductDTO;
import se.jensen.felicia.cloudstore.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String listProducts(Model model){
        List<ProductDTO> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/{id}")
    public String productDetails(@PathVariable Long id, Model model){
        ProductDTO product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product-details";
    }
}
