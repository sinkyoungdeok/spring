package kd.jwt.tutorial.repository;

import java.util.Optional;
import kd.jwt.tutorial.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  @EntityGraph(attributePaths = "authorities") // @EntityGraph은 쿼리가 수행이 될 때 Lazy조회가 아니고 Eager조회로 authorities정보를 같이 가져온다.
  Optional<User> findOneWithAuthoritiesByUsername(String username);
}