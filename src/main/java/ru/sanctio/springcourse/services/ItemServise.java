package ru.sanctio.springcourse.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sanctio.springcourse.models.Item;
import ru.sanctio.springcourse.models.Person;
import ru.sanctio.springcourse.repositories.ItemRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ItemServise {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemServise(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> findByItemName(String itemName) {
        return itemRepository.findByItemName(itemName);
    }

    public List<Item> findByOwner(Person owner) {
        return itemRepository.findByOwner(owner);
    }
}
