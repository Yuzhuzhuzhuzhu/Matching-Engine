import java.util.HashMap;
import java.util.Map;

public class ActiveOrderBook {
    private Map<Instrument, InstrumentOrderBook> instrumentOrderBookMap;
    private TotalTradeBook totalTradeBook;
    private TotalClientBook totalClientBook;

    public ActiveOrderBook(TotalTradeBook totalTradeBook, TotalClientBook totalClientBook) {
        this.totalTradeBook = totalTradeBook;
        this.totalClientBook = totalClientBook;
        instrumentOrderBookMap = new HashMap<>();
        for (Instrument instrument: Instrument.values()) {
            instrumentOrderBookMap.put(instrument, new InstrumentOrderBook(instrument, totalTradeBook,
                    totalClientBook));
        }
    }

    public void registerNewOrder(Order order) {
        instrumentOrderBookMap.get(order.getOrderInstrument()).registerNewOrder(order);
    }

    public boolean orderExists(int orderId) {
        for (Instrument instrument: Instrument.values()) {
            if (instrumentOrderBookMap.get(instrument).orderExists(orderId)) {
                return true;
            }
        }
        return false;
    }

    public void displayOrdersByInstrument(Instrument instrument) {
        instrumentOrderBookMap.get(instrument).displayOrders();
    }

    public void removeOrderById(int orderId) {
        for (InstrumentOrderBook instrumentOrderBook: instrumentOrderBookMap.values()) {
            if (instrumentOrderBook.orderExists(orderId)) {
                instrumentOrderBook.removeOrderById(orderId);
                return;
            }
        }
    }

    /*
        getters
     */
    public Order getOrderById(int orderId) {
        for (Instrument instrument: Instrument.values()) {
            InstrumentOrderBook instrumentOrderBook = instrumentOrderBookMap.get(instrument);
            if (instrumentOrderBook.orderExists(orderId)) {
                return instrumentOrderBook.getOrderById(orderId);
            }
        }
        return null;
    }

    public InstrumentOrderBook getInstrumentOrderBook(Instrument instrument) {
        return instrumentOrderBookMap.get(instrument);
    }
}
