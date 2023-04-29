public class ClientOrderBook {
    private BasicOrderBook activeOrderBook;
    private InactiveOrderBook inactiveOrderBook;

    public ClientOrderBook() {
        activeOrderBook = new BasicOrderBook(OrderStatus.Active);
        inactiveOrderBook = new InactiveOrderBook();
    }

    public void registerNewOrder(Order order) {
        if (order.getOrderStatus() == OrderStatus.Active) {
            activeOrderBook.registerNewOrder(order);
        } else {
            inactiveOrderBook.registerNewOrder(order);
        }
    }

    public boolean orderExists(int orderId) {
        return activeOrderBook.orderExists(orderId) || inactiveOrderBook.orderExists(orderId);
    }
}
