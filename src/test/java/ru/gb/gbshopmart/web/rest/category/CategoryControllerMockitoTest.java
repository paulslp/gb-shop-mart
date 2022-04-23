package ru.gb.gbshopmart.web.rest.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.gb.gbapi.category.dto.CategoryDto;
import ru.gb.gbshopmart.service.CategoryService;
import ru.gb.gbshopmart.web.rest.CategoryController;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// BDD (Behavior Driven Development)
@ExtendWith(MockitoExtension.class)
class CategoryControllerMockitoTest {

    @Mock
    CategoryService categoryService;

    @InjectMocks
    CategoryController categoryController;

    List<CategoryDto> categories = new ArrayList<>();

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        categories.add(CategoryDto.builder().id(1L).title("Technics").build());
        categories.add(CategoryDto.builder().id(2L).title("Meal").build());

        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    void mockMvcGetCategoryListTest() throws Exception {

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
                .content("{\"title\": \"Building\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void getCategoryListTest() {
        // given
        given(categoryService.findAll()).willReturn(categories);

        // when
        List<CategoryDto> categoryList = categoryController.getCategoryList();

        // then
        then(categoryService).should().findAll();

        assertAll(
                () -> assertEquals(2, categoryList.size(), "Size must be equals 2"),
                () -> assertEquals("Technics", categoryList.get(0).getTitle())
        );
    }
}