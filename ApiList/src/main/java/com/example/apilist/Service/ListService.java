package com.example.apilist.Service;

import com.example.apilist.Client.ItemServiceClient;
import com.example.apilist.Entity.CoinList;
import com.example.apilist.Repository.ListRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.ws.rs.Path;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ListService {
    private final ListRepository listRepository;
    private final ItemServiceClient itemServiceClient;

    public List<CoinList> list(Long userid) {
        return listRepository.findByUser(userid);
    }

    public Optional<CoinList> getById(Long id) {
        return listRepository.findById(id);
    }

    public Boolean add(CoinList coinList) {
        if (listRepository.compare(coinList.getName(),coinList.getUser_id()).isEmpty()) {
            listRepository.save(coinList);
            return true;
        } else {
            return false;
        }
    }

    public void delete(Long id) {
        listRepository.deleteById(id);
    }

    @Retry(name = "retryItem")
    @CircuitBreaker(name = "clientItem", fallbackMethod = "getByListFallback")
    public List<ItemServiceClient.Item> getByList(Long id) {
        return itemServiceClient.getByList(id);
    }

    public List<ItemServiceClient.Item> getByListFallback(Long id, Throwable t) throws Exception {
        throw new Exception("An error occurred while searching the List.");
    }

    @Retry(name = "retryItem")
    @CircuitBreaker(name = "clientItem", fallbackMethod = "addItemFallback")
    public ItemServiceClient.Item addItem(ItemServiceClient.Item item) {
        return itemServiceClient.add(item);
    }

    public ItemServiceClient.Item addItemFallback(ItemServiceClient.Item item) throws Exception {
        throw new Exception("An error occurred while saving the Item.");
    }

}
