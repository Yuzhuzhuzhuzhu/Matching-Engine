import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InstrumentTradeBook {
    private final Instrument instrument;
    private Map<Integer, Trade> tradeMap;

    public InstrumentTradeBook(Instrument instrument) {
        this.instrument = instrument;
        tradeMap = new HashMap<>();
    }

    public void registerNewTrade(Trade trade) {
        tradeMap.put(trade.getTradeId(), trade);
    }

    public boolean tradeExists(int tradeId) {
        return tradeMap.containsKey(tradeId);
    }

    public void displayTrades() {
        for (Trade trade: tradeMap.values()) {
            trade.displayTrade();
        }
    }


    /*
        getters
     */
    public Trade getTradeById(int tradeId) {
        return tradeMap.get(tradeId);
    }
}