package ru.gb.gbexternalapi.api;

import org.springframework.http.ResponseEntity;
import ru.gb.gbapi.product.dto.ProductDto;

import java.util.List;

public interface ProductApi {

    List<ProductDto> getProductList();

    ResponseEntity<?> getProduct(Long id);

    ResponseEntity<?> handlePost(ProductDto productDto);

    ResponseEntity<?> handleUpdate(Long id, ProductDto productDto);

    void deleteById(Long id);
}
