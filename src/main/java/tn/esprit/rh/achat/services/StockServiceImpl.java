package tn.esprit.rh.achat.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.rh.achat.entities.Stock;
import tn.esprit.rh.achat.repositories.StockRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class StockServiceImpl implements IStockService {

	
	private final StockRepository StockRepository;

	@Autowired
    public StockServiceImpl(StockRepository stockRepository) {
        this.StockRepository = stockRepository;
    }


    @Override
	public List<Stock> retrieveAllStocks() {
		// récuperer la date à l'instant t1
		log.info("In method retrieveAllStocks");
		List<Stock> stocks = StockRepository.findAll();
		for (Stock stock : stocks) {
			log.info(" Stock : " + stock);
		}
		log.info("out of method retrieveAllStocks");
		// récuperer la date à l'instant t2
		// temps execution = t2 - t1
		return stocks;
	}

	@Override
	public Stock addStock(Stock s) {
		// récuperer la date à l'instant t1
		log.info("In method addStock");
		return StockRepository.save(s);
		
	}

	@Override
	public void deleteStock(Long stockId) {
		log.info("In method deleteStock");
		StockRepository.deleteById(stockId);

	}

	@Override
	public Stock updateStock(Stock s) {
		log.info("In method updateStock");
		return StockRepository.save(s);
	}

	@Override
	public Stock retrieveStock(Long stockId) {
		long start = System.currentTimeMillis();
		log.info("In method retrieveStock");
		Stock stock = StockRepository.findById(stockId).orElse(null);
		log.info("out of method retrieveStock");
		 long elapsedTime = System.currentTimeMillis() - start;
		log.info("Method execution time: " + elapsedTime + " milliseconds.");

		return stock;
	}

	@Override
	public String retrieveStatusStock() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date now = new Date();
		String msgDate = sdf.format(now);
		StringBuilder finalMessage = new StringBuilder();
		String newLine = System.getProperty("line.separator");
		List<Stock> stocksEnRouge = StockRepository.retrieveStatusStock();

		for (Stock stock : stocksEnRouge) {
			finalMessage.append(newLine)
					.append(msgDate)
					.append(newLine)
					.append(": le stock ")
					.append(stock.getLibelleStock())
					.append(" a une quantité de ")
					.append(stock.getQte())
					.append(" inférieur à la quantité minimale a ne pas dépasser de ")
					.append(stock.getQteMin())
					.append(newLine);
		}

		log.info(finalMessage.toString());
		return finalMessage.toString();
	}



}