package com.example.apilist.Client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "ApiItem")
@LoadBalancerClient(name = "ApiItem")
public interface ItemServiceClient {

    @GetMapping("/{id}/list")
    List<Item> getByList(@PathVariable Long id);

    @PostMapping("/add")
    Item add(@RequestBody Item item);

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public class Item {
        private String name;
        private Double amount;
        private Long list_id;
        private Long user_id;
    }

}
