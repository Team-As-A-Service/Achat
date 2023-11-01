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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
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
    @Test
    public void testRetrieveStatusStock() {
        // Arrange
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        List<Stock> stocksEnRouge = Arrays.asList(
                new Stock(1L, "Stock1", 5, 10),  // Quantity < Min Quantity
                new Stock(2L, "Stock2", 15, 20), // Quantity >= Min Quantity
                new Stock(3L, "Stock3", 8, 12)   // Quantity < Min Quantity
        );

        when(stockRepository.retrieveStatusStock()).thenReturn(
                stocksEnRouge.stream().filter(stock -> stock.getLibelleStock().equals("Stock1") || stock.getLibelleStock().equals("Stock3"))
                        .collect(Collectors.toList())
        );

        // Act
        String statusMessage = stockService.retrieveStatusStock();

        // Assert
        assertTrue(statusMessage.contains("le stock Stock1 a une quantité de 5 inférieur à la quantité minimale a ne pas dépasser de 10"));
        assertFalse(statusMessage.contains("le stock Stock2 a une quantité de 15 inférieur à la quantité minimale a ne pas dépasser de 20"));
        assertTrue(statusMessage.contains("le stock Stock3 a une quantité de 8 inférieur à la quantité minimale a ne pas dépasser de 12"));

        // Ensure that Stock2 is not in the status message
        assertFalse(statusMessage.contains("le stock Stock2"));

        // You can add more specific assertions as needed
    }




}
