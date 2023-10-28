package tn.esprit.rh.achat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.rh.achat.entities.Stock;
import tn.esprit.rh.achat.repositories.StockRepository;
import tn.esprit.rh.achat.services.StockServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {StockServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class StockTest {

    @MockBean
    private StockRepository stockRepository;

    @Autowired
    private StockServiceImpl stockService;

    @Test
    void testRetrieveAllStocks() {
    // Create an empty list for testing
    List<Stock> stockList = new ArrayList<>();

    // Mock the behavior of the stockRepository to return the empty list
    when(stockRepository.findAll()).thenReturn(stockList);

    // Test the retrieveAllStocks method
    List<Stock> actualRetrieveAllStocksResult = stockService.retrieveAllStocks();

    // Verify that the method returns the expected empty list
    assertNotNull(actualRetrieveAllStocksResult);
    assertTrue(actualRetrieveAllStocksResult.isEmpty());
    verify(stockRepository).findAll();
}


    @Test
    void testDeleteStock() {
        doNothing().when(stockRepository).deleteById(any(Long.class));
        stockService.deleteStock(123L);
        verify(stockRepository).deleteById(123L);
    }

    // You can similarly write test cases for other methods like addStock, updateStock, retrieveStock, and retrieveStatusStock.
}
