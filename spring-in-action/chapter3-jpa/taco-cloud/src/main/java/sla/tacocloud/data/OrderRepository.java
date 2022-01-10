package sla.tacocloud.data;

import org.springframework.data.jpa.repository.JpaRepository;
import sla.tacocloud.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
