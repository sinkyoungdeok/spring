package sla.tacocloud.data;

import org.springframework.data.jpa.repository.JpaRepository;
import sla.tacocloud.User;

public interface UserRepository extends JpaRepository<User, Long> {
  User findByUsername(String username);
}
