package tacos.restclient;

import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.UriComponentsBuilder;
import tacos.Ingredient;
import tacos.Taco;

@Service
@Slf4j
@RequiredArgsConstructor
public class TacoCloudClient {

  private final RestTemplate restTemplate;
  private final Traverson traverson;

  public Ingredient getIngredientById(String ingredientId) {
    return restTemplate.getForObject("http://localhost:8080/ingredients/{id}",
        Ingredient.class, ingredientId);
  }

  public Ingredient getIngredientById2(String ingredientId) {
    Map<String, String> urlVariables = new HashMap<>();
    urlVariables.put("id", ingredientId);
    return restTemplate.getForObject("http://localhost:8080/ingredients/{id}",
        Ingredient.class, urlVariables);
  }

  public Ingredient getIngredientById3(String ingredientId) {
    Map<String, String> urlVariables = new HashMap<>();
    urlVariables.put("id", ingredientId);
    URI url = UriComponentsBuilder
        .fromHttpUrl("http://localhost:8080/ingredients/{id}")
        .build(urlVariables);
    return restTemplate.getForObject(url, Ingredient.class);
  }

  public Ingredient getIngredientById4(String ingredientId) {
    ResponseEntity<Ingredient> responseEntity =
        restTemplate.getForEntity("http://localhost:8080/ingredients/{id}",
            Ingredient.class, ingredientId);
    log.info("Fetched time: " +
        responseEntity.getHeaders().getDate());

    return responseEntity.getBody();
  }

  public void updateIngredient(Ingredient ingredient) {
    restTemplate.put("http://localhost:8080/ingredients/{id}",
        ingredient,
        ingredient.getId());
  }

  public void deleteIngredient(Ingredient ingredient) {
    restTemplate.delete("http://localhost:8080/ingredients/{id}",
        ingredient.getId());
  }

  public Ingredient createIngredient(Ingredient ingredient) {
    return restTemplate.postForObject("http://localhost:8080/ingredients",
        ingredient,
        Ingredient.class);
  }

  public URI createIngredient2(Ingredient ingredient) {
    return restTemplate.postForLocation("http://localhost:8080/ingredients", ingredient);
  }

  public Ingredient createIngredient3(Ingredient ingredient) {
    ResponseEntity<Ingredient> responseEntity =
        restTemplate.postForEntity("http://localhost:8080/ingredients",
            ingredient,
            Ingredient.class);

    log.info("New resource created at " +
        responseEntity.getHeaders().getLocation());

    return responseEntity.getBody();
  }

  public Iterable<Ingredient> getAllIngredeitnsWithTraverson() {
    ParameterizedTypeReference<Resources<Ingredient>> ingredientType =
        new ParameterizedTypeReference<Resources<Ingredient>>() {};

    Resources<Ingredient> ingredientRes =
        traverson
            .follow("ingredients")
            .toObject(ingredientType);

    Collection<Ingredient> ingredients = ingredientRes.getContent();

    return ingredients;
  }

  public Ingredient addIngredient(Ingredient ingredient) {
    String ingredientUrl = traverson
        .follow("ingredients")
        .asLink()
        .getHref();
    return restTemplate.postForObject(ingredientUrl,
        ingredient,
        Ingredient.class);
  }

  public Iterable<Taco> getRecentTacosWithTraverson() {
    ParameterizedTypeReference<Resources<Taco>> tacoType =
        new ParameterizedTypeReference<Resources<Taco>>() {};

    Resources<Taco> tacoRes =
        traverson
            .follow("tacos")
            .follow("recents")
            .toObject(tacoType);

    return tacoRes.getContent();
  }
}
