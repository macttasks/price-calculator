package com.mt.calculator.adapter.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mt.calculator.domain.DiscountPolicy;
import com.mt.calculator.domain.Product;
import com.mt.calculator.domain.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public class PriceCalculationEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void calculatePriceEndpointTest() throws Exception {
        //given
        Product product = new Product(UUID.randomUUID(), new BigDecimal("10"));
        productRepository.save(product);

        CalculationRequest request = new CalculationRequest(product.id(), 10L, DiscountPolicy.COUNT_BASED);

        //expect
        mockMvc.perform(MockMvcRequestBuilders.post("/calculate-price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").exists());
    }

    @Test
    void shouldReturnBadRequestForNullProductId() throws Exception {
        //given
        CalculationRequest request = new CalculationRequest(null, 10L, DiscountPolicy.COUNT_BASED);

        //expect
        mockMvc.perform(MockMvcRequestBuilders.post("/calculate-price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(longs = {-5L})
    @NullSource
    void shouldReturnBadRequestForInvalidQuantity(Long quantity) throws Exception {
        //given
        CalculationRequest request = new CalculationRequest(UUID.randomUUID(), quantity, DiscountPolicy.COUNT_BASED);

        //expect
        mockMvc.perform(MockMvcRequestBuilders.post("/calculate-price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestForInvalidPolicy() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/calculate-price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\":\"" + UUID.randomUUID() + "\",\"quantity\":10,\"policy\":\"INVALID_POLICY\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}