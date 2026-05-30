package se.jensen.felicia.cloudstore.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import se.jensen.felicia.cloudstore.dto.ProductDTO;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {
    private final String API_URL = "https://fakestoreapi.com/products";

    public List<ProductDTO> getAllProducts(){
        RestTemplate restTemplate = new RestTemplate();

        ProductDTO[] products = restTemplate.getForObject(API_URL, ProductDTO[].class);
        return Arrays.asList(products);
    }

    public ProductDTO getProductById(Long id){
    String url = "https://fakestoreapi.com/products" + id;
    RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, ProductDTO.class);
    }
}
