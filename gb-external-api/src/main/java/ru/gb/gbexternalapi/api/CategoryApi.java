package ru.gb.gbexternalapi.api;

import org.springframework.http.ResponseEntity;
import ru.gb.gbapi.category.dto.CategoryDto;

import java.util.List;

public interface CategoryApi {

    List<CategoryDto> getCategoriesList();

    ResponseEntity<?> getCategory(Long id);

    ResponseEntity<?> handlePost(CategoryDto categoryDto);

    ResponseEntity<?> handleUpdate(Long id, CategoryDto categoryDto);

    void deleteById();
}
