import java.util.Queue;

public class MatchingEngine {
    private TotalTradeBook totalTradeBook;
    private TotalClientBook totalClientBook;
    private TotalOrderBook totalOrderBook;

    public MatchingEngine() {
        totalTradeBook = new TotalTradeBook();
        totalClientBook = new TotalClientBook();
        totalOrderBook = new TotalOrderBook(totalTradeBook, totalClientBook);
    }

    public void registerNewOrders(Queue<Order> newOrders) {
        while (!newOrders.isEmpty()) {
            Order order = newOrders.poll();
            totalOrderBook.registerNewOrder(order);
            totalClientBook.registerNewOrder(order);
        }
    }

    /*
        manage client
     */
    public void registerNewClient(Client client) {
        totalClientBook.registerNewClient(client);
    }

    public void manageClient(ClientManageType clientManageType, int clientId, Instrument instrument,
                             int quantity) {
        totalClientBook.manageClient(clientManageType, clientId, instrument, quantity);
    }

    public void displayClientStock(int clientId) {
        totalClientBook.displayClientStock(clientId);
    }

    public boolean clientExists(int clientId) {
        return totalClientBook.clientExists(clientId);
    }

    public void displayOrdersByInstrument(Instrument instrument) {
        totalOrderBook.displayOrdersByInstrument(instrument);
    }

    public void displayTradesByInstrument(Instrument instrument) {
        totalTradeBook.displayTradesByInstrument(instrument);
    }

    public void cancelOrderById(int orderId) {
        totalOrderBook.cancelOrderById(orderId);
    }


    /*
        getters
     */
    public Order getOrderById(int orderId) {
        return totalOrderBook.getOrderById(orderId);
    }

    public Trade getTradeById(int tradeId) {
        return totalTradeBook.getTradeById(tradeId);
    }

    public int getClientStock(int clientId, Instrument instrument) {
        return totalClientBook.getClientStock(clientId, instrument);
    }

    public int getClientAccountBalance(int clientId) {
        return totalClientBook.getClientAccountBalance(clientId);
    }

    public TotalOrderBook getTotalOrderBook() {
        return totalOrderBook;
    }
}