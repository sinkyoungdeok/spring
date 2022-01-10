package sla.tacocloud.web;

import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import sla.tacocloud.Order;
import sla.tacocloud.data.OrderRepository;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {

  private OrderRepository orderRepository;

  public OrderController(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @GetMapping("/current")
  public String orderForm(Model model) {
    model.addAttribute("order", new Order());
    return "orderForm";
  }

  @PostMapping
  public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus) {
    if( errors.hasErrors() ) {
      return "orderForm";
    }

    orderRepository.save(order);
    sessionStatus.setComplete();

    return "redirect:/";
  }

}
