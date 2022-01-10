package sla.tacocloud.data;

import sla.tacocloud.Order;

public interface OrderRepository {
  Order save(Order order);
}
