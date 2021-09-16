package backvoteschallenge.entities.order.repositories;

import backvoteschallenge.entities.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
