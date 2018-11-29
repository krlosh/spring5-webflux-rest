package guru.springframework.spring5webfluxrest.controllers;

import guru.springframework.spring5webfluxrest.domain.Category;
import guru.springframework.spring5webfluxrest.repositories.CategoryRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/api/v1/categories")
    public Flux<Category> getAllCategories(){
        return this.categoryRepository.findAll();
    }

    @GetMapping("/api/v1/categories/{id}")
    public Mono<Category> getCategoryById(@PathVariable String id){
        return this.categoryRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/categories")
    public Mono<Void> createCategory(@RequestBody Publisher<Category> categoryStream) {
        return this.categoryRepository.saveAll(categoryStream).then();
    }
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/api/v1/categories/{id}")
    public Mono<Category> updateCategory(@PathVariable String id, Category category) {
        category.setId(id);
        return this.categoryRepository.save(category);
    }
}
