package tacos.messaging;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tacos.Order;

@Service
public class RabbitOrderMessagingService
       implements OrderMessagingService {
  
  private RabbitTemplate rabbit;
  
  @Autowired
  public RabbitOrderMessagingService(RabbitTemplate rabbit) {
    this.rabbit = rabbit;
  }

  @Override
  public void sendOrder(Order order) {
    rabbit.convertAndSend("tacocloud.order.queue", order,
        message -> {
          MessageProperties props = message.getMessageProperties();
          props.setHeader("X_ORDER_SOURCE", "WEB");
          return message;
        });
  }
  
}
