package ru.gb.gbshopmart.web.rest.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.gb.gbapi.category.dto.CategoryDto;
import ru.gb.gbshopmart.entity.Category;
import ru.gb.gbshopmart.service.CategoryService;
import ru.gb.gbshopmart.web.rest.CategoryController;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
class CategoryControllerMockMvcTest {

    @MockBean
    CategoryService categoryService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void findAllTest() throws Exception {
        List<CategoryDto> categories = new ArrayList<>();

        categories.add(CategoryDto.builder().id(1L).title("Technics").build());
        categories.add(CategoryDto.builder().id(2L).title("Meal").build());

        given(categoryService.findAll()).willReturn(categories);

        mockMvc.perform(get("/api/v1/category"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].title").value("Technics"))
                .andExpect(jsonPath("$.[1].id").value("2"));
    }

    @Test
    void testSaveCategoryTest() throws Exception {

        given(categoryService.save(any())).willReturn(new CategoryDto(3L, "Building"));

        mockMvc.perform(post("/api/v1/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper
                        .writeValueAsString(Category.builder()
                                .title("Building")
                                .build())))
                .andExpect(status().isCreated());
    }
}