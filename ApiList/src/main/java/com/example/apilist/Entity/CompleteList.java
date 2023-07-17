package com.example.apilist.Entity;

import com.example.apilist.Client.ItemServiceClient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class CompleteList {

    private String name;
    private Double totalAmount = 0.0;
    private List<ItemServiceClient.Item> items;

}
