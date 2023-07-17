package com.example.apilist.Client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    
    @GetMapping("/getTotalAmount/{id}")
    Double getListTotalAmount(@PathVariable Long id);

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties({"list_id","user_id"})
    public class Item {
        private String name;
        private Double amount;
        private Long list_id;
        private Long user_id;
    }

}
