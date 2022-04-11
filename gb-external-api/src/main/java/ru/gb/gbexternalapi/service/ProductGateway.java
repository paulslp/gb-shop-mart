package ru.gb.gbexternalapi.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.gb.gbapi.product.dto.ProductDto;
import ru.gb.gbexternalapi.api.ProductApi;

import java.util.List;

@FeignClient(url = "localhost:8081/api/v1/product", value = "productGateway")
public interface ProductGateway extends ProductApi {

    @GetMapping
    List<ProductDto> getProductList();

    @GetMapping("/{productId}")
    ResponseEntity<?> getProduct(@PathVariable("productId") Long id);

    @PostMapping
    ResponseEntity<?> handlePost(@Validated @RequestBody ProductDto productDto);

    @PutMapping("/{productId}")
    ResponseEntity<?> handleUpdate(@PathVariable("productId") Long id,
                                   @Validated @RequestBody ProductDto productDto);

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteById(@PathVariable("productId") Long id);
}
