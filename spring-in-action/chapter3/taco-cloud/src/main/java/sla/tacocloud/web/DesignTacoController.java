package sla.tacocloud.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sla.tacocloud.Ingredient;
import sla.tacocloud.Ingredient.Type;
import sla.tacocloud.Order;
import sla.tacocloud.Taco;
import sla.tacocloud.data.IngredientRepository;
import sla.tacocloud.data.TacoRepository;

@Slf4j
@Controller
@RequestMapping("/design")
public class DesignTacoController {

  private final IngredientRepository ingredientRepository;
  private final TacoRepository tacoRepository;

  @Autowired
  public DesignTacoController(IngredientRepository ingredientRepository,
      TacoRepository tacoRepository) {
    this.ingredientRepository = ingredientRepository;
    this.tacoRepository = tacoRepository;
  }

  @GetMapping
  public String showDesignForm(Model model) {
    List<Ingredient> ingredients = new ArrayList<>();
    ingredientRepository.findAll().forEach(i -> ingredients.add(i));

    Type[] types = Ingredient.Type.values();
    for (Type type : types) {
      model.addAttribute(type.toString().toLowerCase(),
          filterByType(ingredients, type));
    }

    model.addAttribute("taco", new Taco());

    return "design";
  }

  private List<Ingredient> filterByType(
      List<Ingredient> ingredients, Type type) {
    return ingredients
        .stream()
        .filter(x -> x.getType().equals(type))
        .collect(Collectors.toList());
  }

  @ModelAttribute(name = "order")
  public Order order() {
    return new Order();
  }

  @ModelAttribute(name = "taco")
  public Taco taco() {
    return new Taco();
  }

  @PostMapping
  public String processDesign(@Valid Taco design, Errors errors, @ModelAttribute Order order) {
    if (errors.hasErrors()) {
      return "design";
    }

    Taco saved = tacoRepository.save(design);
    order.addDesign(saved);

    log.info("Processing design: " + design);
    return "redirect:/orders/current";
  }

}
