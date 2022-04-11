package ru.gb.gbshopmart.web.dto.mapper;

import org.mapstruct.Mapper;
import ru.gb.gbapi.category.dto.CategoryDto;
import ru.gb.gbshopmart.entity.Category;

import java.util.Set;


@Mapper
public interface CategoryMapper {

    Category toCategory(CategoryDto categoryDto);

    CategoryDto toCategoryDto(Category category);

    Set<CategoryDto> toCategoryDtos(Set<Category> categories);

    default String getCategory(CategoryDto category) {
        return category.getTitle();
    }

    Set<String> toCategoryDtoTitles(Set<CategoryDto> categoryDtos);


}
