public class Order {

    /*
        Id, instrument, order's type (buy / sell) and clientId couldn't be modified if the order is generated.
        isActive, price and quantity could be modified.
     */
    private final int orderId;
    private OrderStatus orderStatus;
    private final Instrument orderInstrument;
    private final OrderType orderType;
    private final int orderClientId;
    private int orderPrice;
    private int orderQuantity;

    public Order(int orderId, Instrument orderInstrument, OrderType orderType, int orderClientId,
                 int orderPrice, int orderQuantity) {
        this.orderId = orderId;
        this.orderInstrument = orderInstrument;
        this.orderType = orderType;
        this.orderClientId = orderClientId;
        this.orderPrice = orderPrice;
        this.orderQuantity = orderQuantity;
        orderStatus = OrderStatus.Active;
        disPlayNewOrder();
    }

    public boolean isValidOrder(TotalClientBook totalClientBook) {

        /*
            check if the client exists.
         */
        if (!totalClientBook.clientExists(orderClientId)) {
            orderStatus = OrderStatus.Invalid;
            System.out.println("This order is invalid. The client hasn't registered. | Order ID: " + orderId);
            return false;
        }

        /*
            check if this order is valid for the client
         */
        Client client = totalClientBook.getClient(orderClientId);
        if (orderType == OrderType.Buy) {

            /*
                check if the balance is enough to pay for the buy order.
             */
            if (client.getAccountBalance() < orderQuantity * orderPrice) {
                orderStatus = OrderStatus.Invalid;
                System.out.println("This order is invalid. Your account balance is not enough to pay for the order. | Client No."
                        + orderClientId + " | Client Name: " + client.getClientName() + " | Order No." + orderId);
                return false;
            }
        } else {

            /*
                check if the stock is enough
             */
            if (client.getStock(orderInstrument) < orderQuantity) {
                orderStatus = OrderStatus.Invalid;
                System.out.println("This order is invalid. Your stock is not enough to sell for the order. | Client No."
                        + orderClientId + " | Client Name: " + client.getClientName() + " | Order No." + orderId);
                return false;
            }
        }
        return true;
    }

    private void disPlayNewOrder() {
        System.out.print("New Order | ");
        displayOrder();
    }

    public void displayOrder() {
        System.out.println("Order No." + orderId + " | " +
                //orderStatus + " Order" + " | " +
                "Instrument: " + orderInstrument + " | " +
                orderType + " Order" + " | " +
                "Client ID: " + orderClientId + " | " +
                "Price: " + orderPrice + " | " +
                "Quantity: " + orderQuantity
        );
    }

    public void makeTrade(int dealQuantity) {
        orderQuantity -= dealQuantity;
        if (orderQuantity == 0) {
            orderStatus = OrderStatus.Executed;
        }
    }

    public void cancelOrder() {
        orderStatus = OrderStatus.Cancelled;
    }

    /*
        getters
     */
    public int getOrderId() {
        return orderId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public Instrument getOrderInstrument() {
        return orderInstrument;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public int getOrderClientId() {
        return orderClientId;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    /*
        setters
     */
    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }
}