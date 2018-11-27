package guru.springframework.spring5webfluxrest.controllers;

import guru.springframework.spring5webfluxrest.domain.Vendor;
import guru.springframework.spring5webfluxrest.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;

public class VendorControllerTest {

    private VendorRepository repository;
    private VendorController controller;
    private WebTestClient webTestClient;

    @Before
    public void setUp() throws Exception {
        this.repository = BDDMockito.mock(VendorRepository.class);
        this.controller = new VendorController(this.repository);
        this.webTestClient = WebTestClient.bindToController(this.controller).build();
    }

    @Test
    public void list() {
        BDDMockito.given(this.repository.findAll())
                .willReturn(Flux.just(
                        Vendor.builder().firstName("first 1").lastName("last 1").build(),
                        Vendor.builder().firstName("first 2").lastName("last 2").build()
                    )
                );
        this.webTestClient.get()
                .uri("/api/v1/vendors")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    public void getVendorById() {
        BDDMockito.given(this.repository.findById("someId"))
                .willReturn(Mono.just(
                        Vendor.builder().firstName("first 1").lastName("last 1").build()
                        )
                );
        this.webTestClient.get()
                .uri("/api/v1/vendors/someId")
                .exchange()
                .expectBody(Vendor.class);
    }
}