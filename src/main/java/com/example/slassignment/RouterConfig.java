package com.example.slassignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class RouterConfig {

    @Autowired
    private ItemHandler itemHandler;

    //Reactive Endpoints:
    @Bean
    public RouterFunction<ServerResponse> getItemsRoute() {
        return route(GET("/items/reactive"),
                request -> ok().contentType(MediaType.TEXT_EVENT_STREAM).body(itemHandler.getItems(), Item.class));
    }
    @Bean
    public RouterFunction<ServerResponse> getItemRoute() {
        return route(GET("/item/reactive/{itemId}"),
                request -> {
                    Long id = Long.valueOf(request.pathVariable("itemId"));
                    return ok().body(itemHandler.getItem(id), Item.class);
                }
        );
    }
}
