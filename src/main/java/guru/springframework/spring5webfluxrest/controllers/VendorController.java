package guru.springframework.spring5webfluxrest.controllers;

import guru.springframework.spring5webfluxrest.domain.Vendor;
import guru.springframework.spring5webfluxrest.repositories.VendorRepository;
import org.apache.commons.lang3.StringUtils;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class VendorController {

    private VendorRepository vendorRepository;

    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @GetMapping("/api/v1/vendors")
    public Flux<Vendor> list() {
        return this.vendorRepository.findAll();
    }

    @GetMapping("/api/v1/vendors/{id}")
    public Mono<Vendor> getVendorById(@PathVariable String id){
        return this.vendorRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/vendors")
    public Mono<Void> createVendor(@RequestBody Publisher<Vendor> vendorStream){
        return this.vendorRepository.saveAll(vendorStream).then();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/api/v1/vendors/{id}")
    public Mono<Vendor> updateVendor(@PathVariable String id, @RequestBody Vendor vendor ){
        vendor.setId(id);
        return this.vendorRepository.save(vendor);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/api/v1/vendors/{id}")
    public Mono<Vendor> patchVendor(@PathVariable String id, @RequestBody Vendor vendor ){
        Vendor vendorFound = this.vendorRepository.findById(id).block();
        boolean modified = false;
        if(!StringUtils.equals(vendorFound.getFirstName(), vendor.getFirstName())){
            vendorFound.setFirstName(vendor.getFirstName());
            modified = true;
        }
        if(!StringUtils.equals(vendorFound.getLastName(), vendor.getLastName())) {
            vendorFound.setLastName(vendor.getLastName());
            modified = true;
        }
        if(modified) {
            return this.vendorRepository.save(vendor);
        }
        else {
            return Mono.just(vendorFound);
        }
    }
}
