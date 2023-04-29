import java.util.HashMap;
import java.util.Map;

public class TotalTradeBook {
    private Map<Instrument, InstrumentTradeBook> instrumentTradeBookMap;
    private int tradeNumber;

    public TotalTradeBook() {
        instrumentTradeBookMap = new HashMap<>();
        for (Instrument instrument: Instrument.values()) {
            InstrumentTradeBook instrumentTradeBook = new InstrumentTradeBook(instrument);
            instrumentTradeBookMap.put(instrument, instrumentTradeBook);
        }
        tradeNumber = 0;
    }

    public void registerNewTrade(Trade trade) {
        instrumentTradeBookMap.get(trade.getInstrument()).registerNewTrade(trade);
        tradeNumber ++;
    }

    public void displayTradesByInstrument(Instrument instrument) {
        instrumentTradeBookMap.get(instrument).displayTrades();
    }

    /*
        getters
     */
    public Trade getTradeById(int tradeId) {
        for (InstrumentTradeBook instrumentTradeBook: instrumentTradeBookMap.values()) {
            if (instrumentTradeBook.tradeExists(tradeId)) {
                return instrumentTradeBook.getTradeById(tradeId);
            }
        }
        return null;
    }

    public int getTradeNumber() {
        return tradeNumber;
    }
}
