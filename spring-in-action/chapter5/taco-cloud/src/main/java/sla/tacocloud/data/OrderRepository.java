package sla.tacocloud.data;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sla.tacocloud.Order;
import sla.tacocloud.User;

public interface OrderRepository extends JpaRepository<Order, Long> {
  List<Order> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
}
