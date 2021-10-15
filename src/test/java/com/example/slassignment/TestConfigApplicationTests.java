package com.example.slassignment;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class TestConfigApplicationTests {
    @Autowired
    private RouterConfig config;

    @MockBean
    private ItemHandler handler;

    //Reactive Tests
    @Test
    public void getItemsCorrectlyReturnsItems() {
        WebTestClient testClient = WebTestClient
                .bindToRouterFunction(config.getItemsRoute())
                .build();

        List<Item> students = List.of(
                new Item(1L, "ReactItem1Test"),
                new Item(2L, "ReactItem2Test")
        );
        when(handler.getItems()).thenReturn(Flux.fromIterable(students));

        testClient.get().uri("/items/reactive")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(Item.class)
                .isEqualTo(students);
    }

    @Test
    public void getItemCorrectlyReturnsItemById() {
        WebTestClient testClient = WebTestClient
                .bindToRouterFunction(config.getItemRoute())
                .build();

        Long id = 12345L;
        Item item = new Item(id, "ReactItemSingle");
        when(handler.getItem(id)).thenReturn(Mono.just(item));

        testClient.get().uri("/item/reactive/" + id)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Item.class)
                .isEqualTo(item);

        verify(handler).getItem(id);
    }

    //Rest Tests
    @Autowired
    private WebTestClient webClient;

    @Test
    public void getItemCorrectlyReturnsItemByIdRest() {
        this.webClient.get().uri("/item/RESTful/1").exchange().expectStatus().isOk()
                .expectBody(String.class).isEqualTo("{\"id\":1,\"name\":\"PS5\"}");
    }

    @Test
    public void getItemsCorrectlyReturnsItemsRest() {
        webClient.get()
                .uri("/items/RESTful/")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response ->
                        Assertions.assertThat(response.getResponseBody()).isNotNull());
    }
}

