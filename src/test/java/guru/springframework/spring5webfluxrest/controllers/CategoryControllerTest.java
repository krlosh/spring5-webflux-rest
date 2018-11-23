package guru.springframework.spring5webfluxrest.controllers;

import guru.springframework.spring5webfluxrest.domain.Category;
import guru.springframework.spring5webfluxrest.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;

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
}