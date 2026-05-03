package se.jensen.felicia.cloudstore.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.jensen.felicia.cloudstore.model.Orders;
import se.jensen.felicia.cloudstore.model.User;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders,Long> {
    List<Orders> findByUserEmail(String email);

    List<Orders> user(User user);
}
