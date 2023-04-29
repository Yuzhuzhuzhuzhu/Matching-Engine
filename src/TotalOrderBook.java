public class TotalOrderBook {
    private TotalTradeBook totalTradeBook;
    private TotalClientBook totalClientBook;
    private ActiveOrderBook activeOrderBook;
    private InactiveOrderBook inactiveOrderBook;

    public TotalOrderBook(TotalTradeBook totalTradeBook, TotalClientBook totalClientBook) {
        this.totalTradeBook = totalTradeBook;
        this.totalClientBook = totalClientBook;
        activeOrderBook = new ActiveOrderBook(totalTradeBook, totalClientBook);
        inactiveOrderBook = new InactiveOrderBook();
    }

    public void registerNewOrder(Order order) {
        if (order.isValidOrder(totalClientBook)) {
            activeOrderBook.registerNewOrder(order);
        } else {
            inactiveOrderBook.registerNewOrder(order);
        }
    }

    public void displayOrdersByInstrument(Instrument instrument) {
        activeOrderBook.displayOrdersByInstrument(instrument);
    }

    public void cancelOrderById(int orderId) {
        Order order = getOrderById(orderId);
        order.cancelOrder();
        activeOrderBook.removeOrderById(orderId);
        inactiveOrderBook.registerNewOrder(order);
    }


    /*
        getters
     */
    public Order getOrderById(int orderId) {
        if (activeOrderBook.orderExists(orderId)) {
            return activeOrderBook.getOrderById(orderId);
        }
        return inactiveOrderBook.getOrderById(orderId);
    }

    public ActiveOrderBook getActiveOrderBook() {
        return activeOrderBook;
    }

    public InactiveOrderBook getInactiveOrderBook() {
        return inactiveOrderBook;
    }
}
