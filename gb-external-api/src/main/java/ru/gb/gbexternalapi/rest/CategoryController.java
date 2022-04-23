package ru.gb.gbexternalapi.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.gbapi.category.api.CategoryGateway;
import ru.gb.gbapi.category.dto.CategoryDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryGateway categoryGateway;

    @GetMapping
    public List<CategoryDto> getCategoryList() {
        return categoryGateway.getCategoryList();
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getCategory(@PathVariable Long categoryId) {
        return categoryGateway.getCategory(categoryId);
    }

    @PostMapping
    public ResponseEntity<?> handlePost(@Validated @RequestBody CategoryDto categoryDto) {
        return categoryGateway.handlePost(categoryDto);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<?> handleUpdate(@PathVariable Long categoryId,
                                          @Validated @RequestBody CategoryDto categoryDto) {
        return categoryGateway.handleUpdate(categoryId, categoryDto);
    }

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long categoryId) {
        categoryGateway.deleteById(categoryId);
    }
}
