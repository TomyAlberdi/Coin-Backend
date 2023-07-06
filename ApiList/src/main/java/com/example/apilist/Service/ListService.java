package com.example.apilist.Service;

import com.example.apilist.Entity.CoinList;
import com.example.apilist.Repository.ListRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ListService {

    private final ListRepository listRepository;

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

}
