package projectOne.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectOne.order.entity.Order;


public interface OrderRepository extends JpaRepository<Order, Long> {
}
