package com.example.slassignment;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemHandler {

    private static final Map<Long, Item> items = new HashMap<>();
    static {
        Item item1 = new Item(1L, "PS5");
        Item item2 = new Item(2L, "Screen");
        Item item3 = new Item(3L, "Chair");
        items.put(1L, item1);
        items.put(2L, item2);
        items.put(3L, item3);
    }

    public static List<Item> getAllItems() {
        return new ArrayList<>(items.values());
    }

    public static Item getItemById(long id) {
        return items.get(id);
    }

    public Flux<Item> getItems() {
        return Flux.just(new Item(1L, "ReactItem1"),
                new Item(2L, "ReactItem2"));
    }

    public Mono<Item> getItem(Long id) {
        return Mono.just(new Item(id, "ReactItem"));
    }

}

