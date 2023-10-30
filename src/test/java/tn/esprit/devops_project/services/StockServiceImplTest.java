package tn.esprit.devops_project.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.ProductRepository;
import tn.esprit.devops_project.repositories.StockRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class StockServiceImplTest {


    private StockServiceImpl stockService;
    private StockRepository stockRepository;
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        stockRepository = mock(StockRepository.class);
        productRepository = mock(ProductRepository.class);
        stockService = new StockServiceImpl(stockRepository, productRepository); // Utilisez la variable de classe
    }

    @Test
    public void testAddStock() {
        Stock stock = new Stock();
        when(stockRepository.save(stock)).thenReturn(stock);

        Stock result = stockService.addStock(stock);

        assertEquals(stock, result);
    }

    @Test
    public void testRetrieveStock() {
        Long stockId = 1L;
        Stock stock = new Stock();
        when(stockRepository.findById(stockId)).thenReturn(java.util.Optional.of(stock));
        //Optional.of(stock)

        Stock result = stockService.retrieveStock(stockId);

        assertEquals(stock, result);
    }

    @Test
    public void testRetrieveStockNotFound() {
        Long stockId = 1L;
        when(stockRepository.findById(stockId)).thenReturn(Optional.empty());

        assertThrows(NullPointerException.class, () -> stockService.retrieveStock(stockId));
    }

    @Test
    public void testRetrieveAllStock() {
        List<Stock> stockList = new ArrayList<>();
        when(stockRepository.findAll()).thenReturn(stockList);

        List<Stock> result = stockService.retrieveAllStock();

        assertEquals(stockList, result);
    }

    @Test
    public void testFindStockByIdProduct() {
        Long productId = 1L;
        Product product = new Product();
        Stock stock = new Stock();
        product.setStock(stock);

        when(productRepository.findById(productId)).thenReturn(java.util.Optional.of(product));

        Stock result = stockService.findStockByIdProduct(productId);

        assertEquals(stock, result);
    }

    @Test
    public void testFindStockByIdProductProductNotFound() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(java.util.Optional.empty());

        assertThrows(NullPointerException.class, () -> stockService.findStockByIdProduct(productId));
    }
}
  
