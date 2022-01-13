package sla.tacocloud.web;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix = "taco.orders")
@Data
@Validated
public class OrderProps {

  @Min(value = 5, message = "must be between 5 and 25")
  @Max(value = 25, message = "must be between 5 and 25")
  private int pageSize = 20;
}
