import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasicOrderBook {
    private OrderStatus orderStatus;
    private List<Order> orderList;
    private Map<Integer, Order> orderMap;

    public BasicOrderBook(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        orderList = new ArrayList<>();
        orderMap = new HashMap<>();
    }

    public void registerNewOrder(Order order) {
        orderList.add(order);
        orderMap.put(order.getOrderId(), order);
    }

    public boolean orderExists(int orderId) {
        return orderMap.containsKey(orderId);
    }

    public Order getOrderById(int orderId) {
        return orderMap.get(orderId);
    }
}
