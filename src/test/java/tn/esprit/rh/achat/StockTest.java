package tn.esprit.rh.achat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import tn.esprit.rh.achat.entities.Stock;
import tn.esprit.rh.achat.repositories.StockRepository;
import tn.esprit.rh.achat.services.StockServiceImpl;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StockTest {

    @InjectMocks
    private StockServiceImpl stockService;

    @Mock
    private StockRepository stockRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRetrieveAllStocks() {
        // Arrange
        List<Stock> stockList = Arrays.asList(
                new Stock(1L, "Stock1", 10, 5),
                new Stock(2L, "Stock2", 20, 8)
        );
        when(stockRepository.findAll()).thenReturn(stockList);

        // Act
        List<Stock> result = stockService.retrieveAllStocks();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Stock1", result.get(0).getLibelleStock());
        assertEquals(20, result.get(1).getQte());
    }

    @Test
    public void testAddStock() {
        // Arrange
        Stock stockToAdd = new Stock(3L, "Stock3", 15, 7);
        when(stockRepository.save(any(Stock.class))).thenReturn(stockToAdd);

        // Act
        Stock addedStock = stockService.addStock(stockToAdd);

        // Assert
        assertEquals("Stock3", addedStock.getLibelleStock());
        assertEquals(15, addedStock.getQte());
    }

    @Test
    public void testDeleteStock() {
        // Arrange
        Long stockIdToDelete = 1L;

        // Act
        stockService.deleteStock(stockIdToDelete);

        // Assert
        verify(stockRepository).deleteById(stockIdToDelete);
    }

    @Test
    public void testUpdateStock() {
        // Arrange
        Stock stockToUpdate = new Stock(4L, "Stock4", 12, 6);
        when(stockRepository.save(any(Stock.class))).thenReturn(stockToUpdate);

        // Act
        Stock updatedStock = stockService.updateStock(stockToUpdate);

        // Assert
        assertEquals("Stock4", updatedStock.getLibelleStock());
        assertEquals(12, updatedStock.getQte());
    }

    @Test
    public void testRetrieveStock() {
        // Arrange
        Long stockIdToRetrieve = 2L;
        Stock stock = new Stock(stockIdToRetrieve, "Stock2", 20, 8);
        when(stockRepository.findById(stockIdToRetrieve)).thenReturn(java.util.Optional.of(stock));

        // Act
        Stock retrievedStock = stockService.retrieveStock(stockIdToRetrieve);

        // Assert
        assertEquals("Stock2", retrievedStock.getLibelleStock());
        assertEquals(20, retrievedStock.getQte());
    }
}
