package com.example.slassignment;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping
@AllArgsConstructor
public class ItemRestController {

    //RESTful Endpoints:
    @GetMapping("/items/RESTful")
    public List<Item> getAllItems(){
        return ItemHandler.getAllItems();
    }
    @GetMapping("/item/RESTful/{itemId}")
    public static Item getItemById(@PathVariable long itemId){
        return ItemHandler.getItemById(itemId);
    }
}
