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
import ru.gb.gbapi.category.api.CategoryGateway;
import ru.gb.gbapi.category.dto.CategoryDto;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {


    private final CategoryGateway categoryGateway;

    public CategoryController(
            @Qualifier("categoryIntGateway") CategoryGateway categoryGateway
    ) {
        this.categoryGateway = categoryGateway;
    }

    @GetMapping
    List<CategoryDto> getCategoryList() {
        return categoryGateway.getCategoryList();
    }

    @GetMapping({"/{categoryId}"})
    ResponseEntity<CategoryDto> getCategory(@PathVariable("categoryId") Long id) {
        return categoryGateway.getCategory(id);
    }

    @PostMapping
    ResponseEntity<CategoryDto> handlePost(@Validated @RequestBody CategoryDto categoryDto) {
        return categoryGateway.handlePost(categoryDto);
    }

    @PutMapping({"/{categoryId}"})
    ResponseEntity<CategoryDto> handleUpdate(@PathVariable("categoryId") Long id,
                                             @Validated @RequestBody CategoryDto categoryDto) {
        return categoryGateway.handleUpdate(id, categoryDto);
    }

    @DeleteMapping({"/{categoryId}"})
    void deleteById(@PathVariable("categoryId") Long id) {
        categoryGateway.deleteById(id);
    }
}
