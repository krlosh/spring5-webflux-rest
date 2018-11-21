package guru.springframework.spring5webfluxrest.bootstrap;

import guru.springframework.spring5webfluxrest.domain.Category;
import guru.springframework.spring5webfluxrest.domain.Vendor;
import guru.springframework.spring5webfluxrest.repositories.CategoryRepository;
import guru.springframework.spring5webfluxrest.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private CategoryRepository categoryRepository;
    private VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if(this.categoryRepository.count().block()==0) {
            System.out.println("LOADING DATA IN MongoDB");

            this.categoryRepository.save(Category.builder().description("Fruits").build()).block();
            this.categoryRepository.save(Category.builder().description("Nuts").build()).block();
            this.categoryRepository.save(Category.builder().description("Breads").build()).block();
            this.categoryRepository.save(Category.builder().description("Meats").build()).block();
            this.categoryRepository.save(Category.builder().description("Egss").build()).block();

            System.out.println("Loaded categories: " + this.categoryRepository.count().block());

            this.vendorRepository.save(Vendor.builder().firstName("Joe").lastName("Buck").build()).block();
            this.vendorRepository.save(Vendor.builder().firstName("Michael").lastName("Weston").build()).block();
            this.vendorRepository.save(Vendor.builder().firstName("Jessie").lastName("Watters").build()).block();
            this.vendorRepository.save(Vendor.builder().firstName("Bill").lastName("Nersie").build()).block();
            this.vendorRepository.save(Vendor.builder().firstName("Jimmy").lastName("Buffet").build()).block();
            System.out.println("Loaded vendors: " + this.vendorRepository.count().block());
        }
    }
}
