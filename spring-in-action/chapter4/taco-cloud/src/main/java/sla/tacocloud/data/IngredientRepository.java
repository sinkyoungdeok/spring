package sla.tacocloud.data;

import org.springframework.data.jpa.repository.JpaRepository;
import sla.tacocloud.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, String> {

}
