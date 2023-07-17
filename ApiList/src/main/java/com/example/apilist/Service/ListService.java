package com.example.apilist.Service;

import com.example.apilist.Client.ItemServiceClient;
import com.example.apilist.Entity.CoinList;
import com.example.apilist.Entity.CompleteList;
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

    public List<CompleteList> list(Long userid) {
        var lists = listRepository.findByUser(userid);
        var userList = new ArrayList<CompleteList>();
        for(CoinList i :lists) {
            var newList = getCompleteList(i.getId());
            userList.add(newList);
        }
        return userList;
    }

    public Optional<CoinList> getById(Long id) {
        return listRepository.findById(id);
    }
    
    public CompleteList getCompleteList(Long id) {
        CompleteList list = new CompleteList();
        list.setName(listRepository.findById(id).get().getName());
        list.setTotalAmount(itemServiceClient.getListTotalAmount(id));
        list.setItems(getByList(id));
        return list;
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
        throw new Exception();
    }

    @Retry(name = "retryItem")
    @CircuitBreaker(name = "clientItem", fallbackMethod = "addItemFallback")
    public ItemServiceClient.Item addItem(ItemServiceClient.Item item) {
        return itemServiceClient.add(item);
    }

    public ItemServiceClient.Item addItemFallback(ItemServiceClient.Item item) throws Exception {
        throw new Exception();
    }
    
    @Retry(name = "retryItem")
    @CircuitBreaker(name = "clientItem", fallbackMethod = "getTotalAmountFallback")
    public Double getListTotalAmount(Long id) {
        return itemServiceClient.getListTotalAmount(id);
    }
    
    public Double getTotalAmountFallback(Long id) throws Exception {
        throw new Exception();
    }

}
