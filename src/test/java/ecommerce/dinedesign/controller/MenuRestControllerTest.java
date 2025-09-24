package ecommerce.dinedesign.controller;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ecommerce.dinedesign.domain.Product;
import ecommerce.dinedesign.dto.ProductSearchFilterDto;
import ecommerce.dinedesign.repository.ProductRepository;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class MenuRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ProductRepository productRepository;

    @Test
    void findByProductCategory() throws Exception {
        // Given
        ProductSearchFilterDto filterDto = new ProductSearchFilterDto();
        filterDto.setProductCategory("food");

        Product product = new Product();
        product.setManufacturer("FreshFarms");
        product.setProductCategory("food");
        product.setProductTitle("Apple");

        when(productRepository.findByProductCategoryOrderByPriceDesc("food"))
                .thenReturn(Arrays.asList(product));

        // When + Then
        mockMvc.perform(post("/api/v1/rest/menu/category")
                .contentType("application/json")
                .content(mapper.writeValueAsString(filterDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[*].manufacturer", contains("FreshFarms")))
                .andExpect(jsonPath("$[*].productCategory", contains("food")));
    }

    @Test
    void findByManufacturer() throws Exception {
        // Given
        ProductSearchFilterDto filterDto = new ProductSearchFilterDto();
        filterDto.setManufacturer("Gucci");

        Product product = new Product();
        product.setManufacturer("Gucci");
        product.setProductCategory("furniture");
        product.setProductTitle("Chair");

        when(productRepository.findByManufacturerOrderByPriceDesc("Gucci"))
                .thenReturn(Arrays.asList(product));

        // When + Then
        mockMvc.perform(post("/api/v1/rest/menu/Manufacturer")
                .contentType("application/json")
                .content(mapper.writeValueAsString(filterDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[*].manufacturer", contains("Gucci")))
                .andExpect(jsonPath("$[*].productCategory", contains("furniture")));
    }
}
