package com.example.apiitem.Service;

import com.example.apiitem.Entity.Item;
import com.example.apiitem.Repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public List<Item> getByList(Long listid) {
        return itemRepository.findByList(listid);
    }

    public Item add(Item item) {
        return itemRepository.save(item);
    }


}
