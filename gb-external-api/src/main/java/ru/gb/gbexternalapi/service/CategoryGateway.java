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
import ru.gb.gbapi.category.dto.CategoryDto;
import ru.gb.gbexternalapi.api.CategoryApi;

import java.util.List;

@FeignClient(url = "localhost:8081/api/v1/category", value = "categoryGateway")
public interface CategoryGateway extends CategoryApi {

    @GetMapping
    List<CategoryDto> getCategoryList();

    @GetMapping("/{categoryId}")
    ResponseEntity<?> getCategory(Long categoryId);

    @PostMapping
    ResponseEntity<?> handlePost(@Validated @RequestBody CategoryDto categoryDto);

    @PutMapping("/{categoryId}")
    ResponseEntity<?> handleUpdate(@PathVariable Long categoryId,
                                   @Validated @RequestBody CategoryDto categoryDto);

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteById(@PathVariable Long categoryId);
}
