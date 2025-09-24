package ecommerce.dinedesign.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import ecommerce.dinedesign.domain.Product;
import ecommerce.dinedesign.repository.ProductRepository;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class MainRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository productRepository;

    @Test
    public void getAllProducts() throws Exception {
        Product productOne = new Product();
        productOne.setId(1L);
        productOne.setManufacturer("Gucci");

        Product productTwo = new Product();
        productTwo.setId(2L);
        productTwo.setManufacturer("Dior");

        when(productRepository.findAll()).thenReturn(Arrays.asList(productOne, productTwo));

        mockMvc.perform(get("/api/v1/rest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(1, 2)))
                .andExpect(jsonPath("$[*].manufacturer", containsInAnyOrder("Gucci", "Dior")));
    }

    @Test
    public void getProduct() throws Exception {
        Product productOne = new Product();
        productOne.setId(1L);
        productOne.setManufacturer("Gucci");

        when(productRepository.getOne(anyLong())).thenReturn(productOne);

        mockMvc.perform(get("/api/v1/rest/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.manufacturer", equalTo("Gucci")));
    }
}