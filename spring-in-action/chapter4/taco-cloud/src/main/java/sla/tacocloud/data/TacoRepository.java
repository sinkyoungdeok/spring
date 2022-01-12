package sla.tacocloud.data;

import org.springframework.data.jpa.repository.JpaRepository;
import sla.tacocloud.Taco;

public interface TacoRepository extends JpaRepository<Taco, Long> {

}
