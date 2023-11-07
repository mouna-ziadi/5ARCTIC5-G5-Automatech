package tn.esprit.devops_project.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.repositories.ProductRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
public class ProductRepositoryIntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testSaveAndRetrieveProduct() {
        // Create a Product entity
        Product product = new Product();
        product.setTitle("Test Product");
        product.setPrice(10.0f);
        product.setQuantity(5);

        // Save the product to the H2 database
        Product savedProduct = productRepository.save(product);

        // Retrieve the product from the H2 database by its ID
        Product retrievedProduct = productRepository.findById(savedProduct.getIdProduct()).orElse(null);

        // Verify that the retrieved product matches the saved product
        assertEquals("Test Product", retrievedProduct.getTitle());
        assertEquals(10.0f, retrievedProduct.getPrice(), 0.001);
        assertEquals(5, retrievedProduct.getQuantity());
    }
}
