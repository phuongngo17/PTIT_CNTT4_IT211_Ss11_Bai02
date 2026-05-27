package com.example.bai02.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductService.ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private ProductService.Product product;

    @BeforeEach
    void setUp() {
        product = new ProductService.Product("P01", 10);
    }

    // Happy Path - thêm tồn kho
    @Test
    void shouldAddStockSuccessfully() {

        when(productRepository.findById("P01"))
                .thenReturn(Optional.of(product));

        int result = productService.updateStock("P01", 5);

        assertThat(result).isEqualTo(15);

        verify(productRepository).save(product);
    }

    // Happy Path - trừ tồn kho
    @Test
    void shouldSubtractStockSuccessfully() {

        when(productRepository.findById("P01"))
                .thenReturn(Optional.of(product));

        int result = productService.updateStock("P01", -3);

        assertThat(result).isEqualTo(7);

        verify(productRepository).save(product);
    }

    // Unhappy Path - tồn kho âm
    @Test
    void shouldThrowExceptionWhenStockNegative() {

        when(productRepository.findById("P01"))
                .thenReturn(Optional.of(product));

        assertThatThrownBy(() ->
                productService.updateStock("P01", -20))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Resulting stock would be negative");

        verify(productRepository, never()).save(any());
    }

    // Unhappy Path - không tìm thấy sản phẩm
    @Test
    void shouldThrowExceptionWhenProductNotFound() {

        when(productRepository.findById("P99"))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                productService.updateStock("P99", 5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Product not found with ID: P99");

        verify(productRepository, never()).save(any());
    }

    // Kiểm tra save được gọi
    @Test
    void shouldCallSaveAfterUpdatingStock() {

        when(productRepository.findById("P01"))
                .thenReturn(Optional.of(product));

        productService.updateStock("P01", 2);

        verify(productRepository, times(1))
                .save(product);
    }
}