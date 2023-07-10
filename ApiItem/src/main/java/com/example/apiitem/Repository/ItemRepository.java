package com.example.apiitem.Repository;

import com.example.apiitem.Entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(value = "SELECT * FROM item i WHERE i.list_id = :listid", nativeQuery = true)
    public List<Item> findByList(Long listid);

}
