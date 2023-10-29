package tn.esprit.rh.achat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.rh.achat.entities.Stock;
import tn.esprit.rh.achat.repositories.StockRepository;
import tn.esprit.rh.achat.services.StockServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StockTest {

    @Mock
    StockRepository stockRepository;

    @InjectMocks
    StockServiceImpl stockService;

    @Test
    public void createStockTest() {
        Stock stock = new Stock(null, "Test Stock", 100, 10);
        stock.setIdStock(1L);

        stockService.addStock(stock);

        // Verify that the 'save' method of the repository is called with the correct argument
        verify(stockRepository, times(1)).save(stock);
        System.out.println(stock);
        System.out.println("createStockTest -> It's working!");
    }

    @Test
    public void testRetrieveStock() {
        Stock stock = new Stock(null, "Test Stock", 100, 10);
        stock.setIdStock(1L);

        when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));
        Stock retrievedStock = stockService.retrieveStock(1L);
        assertEquals(stock, retrievedStock);
        System.out.println(retrievedStock);
        System.out.println("testRetrieveStock -> It's working!");
    }

    @Test
    public void getAllStocksTest() {
        List<Stock> stockList = new ArrayList<Stock>() {
            {
                add(new Stock(null, "Stock 1", 50, 5));
                add(new Stock(null, "Stock 2", 75, 8));
                add(new Stock(null, "Stock 3", 120, 12));
            }
        };

        when(stockRepository.findAll()).thenReturn(stockList);

        List<Stock> retrievedStockList = stockService.retrieveAllStocks();
        assertEquals(3, retrievedStockList.size());
        System.out.println("getAllStocksTest -> It's working!");
    }

    @Test
    public void testDeleteStock() {
        Stock stock = new Stock(null, "Test Stock", 100, 10);
        stock.setIdStock(1L);

        Mockito.lenient().when(stockRepository.findById(stock.getIdStock())).thenReturn(Optional.of(stock));

        stockService.deleteStock(1L);

        verify(stockRepository).deleteById(stock.getIdStock());
        System.out.println(stock);
        System.out.println("testDeleteStock -> It's working!");
    }

    @Test
    public void testUpdateStock() {
        Stock stock = new Stock(null, "Test Stock", 100, 10);
        stock.setIdStock(1L);
        stock.setQte(120);

        Mockito.lenient().when(stockRepository.findById(stock.getIdStock())).thenReturn(Optional.of(stock));

        stockService.updateStock(stock);

        verify(stockRepository).save(stock);
        System.out.println(stock);
        System.out.println("testUpdateStock -> It's working!");
    }
}
