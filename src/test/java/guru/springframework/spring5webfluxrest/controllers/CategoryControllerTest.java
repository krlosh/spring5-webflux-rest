package guru.springframework.spring5webfluxrest.controllers;

import guru.springframework.spring5webfluxrest.domain.Category;
import guru.springframework.spring5webfluxrest.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

public class CategoryControllerTest {

    private CategoryRepository repository;
    private CategoryController controller;
    private WebTestClient webTestClient;

    @Before
    public void setUp() throws Exception {
        this.repository = Mockito.mock(CategoryRepository.class);
        this.controller = new CategoryController(this.repository);
        this.webTestClient = WebTestClient.bindToController(this.controller).build();
    }

    @Test
    public void getAllCategories() {
        BDDMockito.given(this.repository.findAll()).willReturn(Flux.just(Category.builder().description("cat1").build()
                , Category.builder().description("cat2").build()));
        this.webTestClient.get()
                .uri("/api/v1/categories")
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    public void getCategoryById() {
        BDDMockito.given(this.repository.findById("anId")).willReturn(Mono.just(Category.builder().description("Cat").build()));
        this.webTestClient.get()
                .uri("/api/v1/categories/anId")
                .exchange()
                .expectBody(Category.class);
    }

    @Test
    public void createCategory() {
        BDDMockito.given(this.repository.saveAll(any(Publisher.class))).willReturn(Flux.just(Category.builder().description("cat1").build()));

        Mono<Category> catToSave = Mono.just(Category.builder().description("description").build());

        this.webTestClient.post()
                .uri("/api/v1/categories")
                .body(catToSave, Category.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void updateCategory() {
        BDDMockito.given(this.repository.save(any(Category.class))).willReturn(Mono.just(Category.builder().description(
                "cat1").build()));

        Mono<Category> catToSave = Mono.just(Category.builder().description("description").build());

        this.webTestClient.put()
                .uri("/api/v1/categories/aaaaa")
                .body(catToSave, Category.class)
                .exchange()
                .expectStatus()
                .isOk();
    }
}