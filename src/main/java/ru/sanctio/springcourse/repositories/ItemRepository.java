package ru.sanctio.springcourse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sanctio.springcourse.models.Item;
import ru.sanctio.springcourse.models.Person;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    List<Item> findByItemName(String itemName);

    List<Item> findByOwner(Person owner);
}
