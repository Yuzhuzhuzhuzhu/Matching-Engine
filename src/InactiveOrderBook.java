import java.util.HashMap;
import java.util.Map;

public class InactiveOrderBook {
    private Map<OrderStatus, BasicOrderBook> orderMap;

    public InactiveOrderBook() {
        orderMap = new HashMap<>();
        for(OrderStatus orderStatus: OrderStatus.values()) {
            if (orderStatus != OrderStatus.Active) {
                orderMap.put(orderStatus, new BasicOrderBook(orderStatus));
            }
        }
    }

    public void registerNewOrder(Order order) {
        orderMap.get(order.getOrderStatus()).registerNewOrder(order);
    }

    public boolean orderExists(int orderId) {
        return orderMap.get(OrderStatus.Cancelled).orderExists(orderId) ||
                orderMap.get(OrderStatus.Executed).orderExists(orderId) ||
                orderMap.get(OrderStatus.Invalid).orderExists(orderId);
    }

    /*
        getter
     */
    public Order getOrderById(int orderId) {
        for(OrderStatus orderStatus: OrderStatus.values()) {
            if (orderStatus != OrderStatus.Active) {
                BasicOrderBook basicOrderBook = orderMap.get(orderStatus);
                if (basicOrderBook.orderExists(orderId)) {
                    return basicOrderBook.getOrderById(orderId);
                }
            }
        }
        return null;
    }

    public BasicOrderBook getBasicOrderBook(OrderStatus orderStatus) {
        return orderMap.get(orderStatus);
    }
}
