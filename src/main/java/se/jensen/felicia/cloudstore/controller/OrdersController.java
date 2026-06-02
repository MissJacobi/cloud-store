package se.jensen.felicia.cloudstore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import se.jensen.felicia.cloudstore.dto.OrdersDTO;
import se.jensen.felicia.cloudstore.dto.ProductDTO;
import se.jensen.felicia.cloudstore.service.OrdersService;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {
    private final OrdersService ordersService;

    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @PostMapping
    public ResponseEntity<OrdersDTO> createOrder(@RequestBody List<ProductDTO> items, Authentication authentication){
        OrdersDTO result = ordersService.createOrder(authentication.getName(), items);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<List<OrdersDTO>> getOrdersByUser(@PathVariable String email){
    List<OrdersDTO> orders = ordersService.getOrdersByUserEmail(email);
    return ResponseEntity.ok(orders);
    }
}
