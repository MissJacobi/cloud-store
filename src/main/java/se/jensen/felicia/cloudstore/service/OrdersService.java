package se.jensen.felicia.cloudstore.service;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import se.jensen.felicia.cloudstore.dto.OrdersDTO;
import se.jensen.felicia.cloudstore.dto.ProductDTO;
import se.jensen.felicia.cloudstore.model.OrderItem;
import se.jensen.felicia.cloudstore.model.User;
import se.jensen.felicia.cloudstore.repository.OrdersRepository;
import se.jensen.felicia.cloudstore.repository.UserRepository;
import se.jensen.felicia.cloudstore.model.Orders;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final UserRepository userRepository;

    public OrdersService(OrdersRepository ordersRepository, UserRepository userRepository) {
        this.ordersRepository = ordersRepository;
        this.userRepository = userRepository;
    }

    @Transactional //Hela orden sparas eller inget alls
    public OrdersDTO createOrder(String email,@NotNull List<ProductDTO> items){
        //1. Hämta användaren från databasen
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        //2. Skapa Order-Huvudet
        Orders orders = new Orders();
        orders.setUser(user);
        orders.setOrderDate(LocalDateTime.now());
        double totalAmount = 0;

        //3. Skapa OrderItems för varje produkt
        for(ProductDTO item : items){
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(item.id());
            orderItem.setProductName(item.title());
            orderItem.setPrice(item.price());
            orderItem.setQuantity(item.quantity());
            orderItem.setOrder(orders);

            orders.getItems().add(orderItem);
            totalAmount += (item.price() * item.quantity());
        }
        orders.setTotalAmount(totalAmount);

        //3.5 Lägger till lojalitetspoäng
        int pointesEarned = (int) totalAmount;
        int currentPoints = user.getTotalPoints();
        user.setTotalPoints(currentPoints + pointesEarned);

        userRepository.save(user);

        //4. Spara modellen i  databasen
        Orders savedOrder = ordersRepository.save(orders);

        return convertToDTO(savedOrder);
    }

    private OrdersDTO convertToDTO(Orders order){
        return new OrdersDTO(
                order.getId(),
                order.getOrderDate(),
                order.getTotalAmount(),
                null
                );
    }


    public List<OrdersDTO> getOrdersByUserEmail(String email) {
        //1. Hämta alla ordrar från repot baserat på användarens mejl
        //2. Mappa varje 'Orders'(model) till 'OrdersDTO'
        return ordersRepository.findByUserEmail(email)
                .stream()
                .map(order -> new OrdersDTO(
                        order.getId(),
                        order.getOrderDate(),
                        order.getTotalAmount(),
                        null
                ))
                .toList();
    }
}
