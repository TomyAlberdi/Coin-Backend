package com.example.apiitem.Controller;

import com.example.apiitem.Entity.Item;
import com.example.apiitem.Service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RestControllerAdvice
@Validated
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/{id}/list")
    List<Item> getByList(@PathVariable Long id) {
        return itemService.getByList(id);
    }

    @PostMapping("/add")
    Item add(@RequestBody Item item) {
        return itemService.add(item);
    }

}
