package com.example.apilist.Controller;

import com.example.apilist.Client.ItemServiceClient;
import com.example.apilist.Entity.CoinList;
import com.example.apilist.Service.ListService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RestControllerAdvice
@Validated
@RequestMapping("/list")
public class ListController {

    private final ListService listService;

    private final ItemServiceClient itemServiceClient;

    @GetMapping("/{userid}")
    public ResponseEntity<List<CoinList>> list(@PathVariable Long userid) {
        return ResponseEntity.ok()
                .body(listService.list(userid));
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody CoinList coinList) {
        if (listService.add(coinList)) {
            return ResponseEntity.ok().body(coinList);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("List with name: " + coinList.getName() + " already exists.");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<CoinList> list = listService.getById(id);
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("List with ID: " + id + " not found.");
        } else {
            listService.delete(id);
            return ResponseEntity.ok().body("List with id " + id + " deleted.");
        }
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<?> getItems(@PathVariable Long id) {
        Optional<CoinList> list = listService.getById(id);
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("List with ID: " + id + " not found.");
        } else {
            return ResponseEntity.ok().body(itemServiceClient.getByList(id));
        }
    }

    @PostMapping("/{id}/add")
    public ResponseEntity<?> addItem(@PathVariable Long id, @RequestBody ItemServiceClient.Item item) {
        Optional<CoinList> list = listService.getById(id);
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("List with ID: " + id + " not found.");
        } else {
            return ResponseEntity.ok().body(itemServiceClient.add(item));
        }
    }

}
