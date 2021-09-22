package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.entity.Item;

public interface ItemRepository extends JpaRepository<Item, String> {

}
