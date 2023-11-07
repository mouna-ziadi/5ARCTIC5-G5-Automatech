package tn.esprit.devops_project.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.ProductRepository;
import tn.esprit.devops_project.repositories.StockRepository;
import tn.esprit.devops_project.services.Iservices.IProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private StockRepository stockRepository;

    private IProductService productService;

    @Test
    public void testAddProduct() {
        // Create a mock product and stock
        Product product = new Product();
        product.setTitle("Test Product");

        when(stockRepository.findById(anyLong())).thenReturn(Optional.of(new Stock())); // Mocking the stock retrieval
        when(productRepository.save(any(Product.class))).thenReturn(product); // Mocking the save operation

        productService = new ProductServiceImpl(productRepository, stockRepository);

        Product savedProduct = productService.addProduct(product, 1L);

        assertEquals("Test Product", savedProduct.getTitle());
    }

    @Test
    public void testDeleteProduct() {
        // Create a mock product for deletion
        Product product = new Product();
        product.setIdProduct(1L);
        product.setTitle("Test Product");

        // Mock the behavior for retrieving the product by ID
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Mock the behavior for deleting a product
        doNothing().when(productRepository).delete(product);

        productService = new ProductServiceImpl(productRepository, stockRepository);

        // Delete the product and verify
        productService.deleteProduct(1L);

        // Verify that the delete operation was called with the correct product
        Mockito.verify(productRepository, Mockito.times(1)).delete(product);
    }
}







