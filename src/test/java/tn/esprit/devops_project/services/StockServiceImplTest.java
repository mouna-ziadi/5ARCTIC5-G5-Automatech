package tn.esprit.devops_project.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.StockRepository;
import tn.esprit.devops_project.services.Iservices.IStockService;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class StockServiceImplTest {

    @Autowired
    private IStockService iStockService ;
    @MockBean
    private StockRepository stockRepository;

    @BeforeEach
    public void setUp() {

        Stock expectedStock = new Stock(2L, "abc"); // Créez un objet Stock avec l'ID 2
        Mockito.when(stockRepository.findById(2L)).thenReturn(Optional.of(expectedStock));
    }
    @Test
    public void testRetrieveStock() {
        Long stockId = 2L;
        Stock stockById = iStockService.retrieveStock(2L);
        assertEquals(stockId,stockById.getIdStock());

    }



}
  
