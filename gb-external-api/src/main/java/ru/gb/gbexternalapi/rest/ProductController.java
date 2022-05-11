package ru.gb.gbexternalapi.rest;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.gbapi.product.api.ProductGateway;
import ru.gb.gbapi.product.dto.ProductDto;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductGateway productGateway;

    public ProductController(
            @Qualifier("productIntGateway") ProductGateway productGateway
    ) {
        this.productGateway = productGateway;
    }

    @GetMapping
    List<ProductDto> getProductList() {
        return productGateway.getProductList();
    }

    @GetMapping({"/{productId}"})
    ResponseEntity<ProductDto> getProduct(@PathVariable("productId") Long id) {
        return productGateway.getProduct(id);
    }

    @PostMapping
    ResponseEntity<ProductDto> handlePost(@Validated @RequestBody ProductDto productDto) {
        return productGateway.handlePost(productDto);
    }

    @PutMapping({"/{productId}"})
    ResponseEntity<ProductDto> handleUpdate(@PathVariable("productId") Long id,
                                            @Validated @RequestBody ProductDto productDto) {
        return productGateway.handleUpdate(id, productDto);
    }

    @DeleteMapping({"/{productId}"})
    void deleteById(@PathVariable("productId") Long id) {
        productGateway.deleteById(id);
    }
}
