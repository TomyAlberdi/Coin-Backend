package com.example.apilist.Controller;

import com.example.apilist.Client.ItemServiceClient;
import com.example.apilist.Entity.CoinList;
import com.example.apilist.Event.DeleteListEventProducer;
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
    private final DeleteListEventProducer deleteListEventProducer;

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
            deleteListEventProducer.publishDeleteListEvent(new DeleteListEventProducer.Data(id));
            return ResponseEntity.ok().body("List with id " + id + " deleted.");
        }
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<?> getItems(@PathVariable Long id) {
        Optional<CoinList> list = listService.getById(id);
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("List with ID: " + id + " not found.");
        } else {
            try {
                return ResponseEntity.ok().body(listService.getByList(id));
            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Item Service Unavailable.");
            }
        }
    }

    @PostMapping("/addItem")
    public ResponseEntity<?> addItem(@RequestBody ItemServiceClient.Item item) {
        Optional<CoinList> list = listService.getById(item.getList_id());
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("List with ID: " + item.getList_id() + " not found.");
        } else {
            try {
                return ResponseEntity.ok().body(listService.addItem(item));
            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Item Service Unavailable.");
            }
        }
    }

}
