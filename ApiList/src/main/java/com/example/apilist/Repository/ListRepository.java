package com.example.apilist.Repository;

import com.example.apilist.Entity.CoinList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ListRepository extends JpaRepository<CoinList, Long> {

    @Query(value = "SELECT * FROM coin_list l WHERE l.user_id = :userid", nativeQuery = true)
    public List<CoinList> findByUser(Long userid);

    @Query(value = "SELECT * FROM coin_list l WHERE l.name = :name AND l.user_id = :userid", nativeQuery = true)
    public Optional<CoinList> compare(String name,Long userid);


}
