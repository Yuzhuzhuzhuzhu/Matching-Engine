import java.util.*;

public class OrderBook {
    private final OrderType orderType;
    private PriorityQueue<Order> orderPriorityQueue;
    private Map<Integer, Order> idOrderMap;
    private Map<Integer, Integer> priceQuantityMap;
    private Map<Integer, List<Order>> priceOrdersMap;

    public OrderBook(OrderType orderType) {
        this.orderType = orderType;
        if (orderType == OrderType.Buy) {

            /*
                Buy Orders' priority queue
                first priority: higher price
                second priority: earlier arrived
            */
            orderPriorityQueue = new PriorityQueue<>(new Comparator<Order>() {
                @Override
                public int compare(Order o1, Order o2) {
                    int price1 = o1.getOrderPrice(), price2 = o2.getOrderPrice();
                    int id1 = o1.getOrderId(), id2 = o2.getOrderId();
                    if (price1 != price2) {
                        return price2 - price1;
                    }
                    return id1 - id2;
                }
            });
        } else {

            /*
                Sell Orders' priority queue
                first priority: lower price
                second priority: earlier arrived
            */
            orderPriorityQueue = new PriorityQueue<>(new Comparator<Order>() {
                @Override
                public int compare(Order o1, Order o2) {
                    int price1 = o1.getOrderPrice(), price2 = o2.getOrderPrice();
                    int id1 = o1.getOrderId(), id2 = o2.getOrderId();
                    if (price1 != price2) {
                        return price1 - price2;
                    }
                    return id1 - id2;
                }
            });
        }
        idOrderMap = new HashMap<>();
        priceQuantityMap = new HashMap<>();
        priceOrdersMap = new HashMap<>();
    }

    public void registerNewOrder(Order order) {
        int price = order.getOrderPrice();
        orderPriorityQueue.offer(order);
        idOrderMap.put(order.getOrderId(), order);
        priceQuantityMap.put(price, priceQuantityMap.getOrDefault(price, 0) + order.getOrderQuantity());
        List<Order> list = new ArrayList<>();
        if (priceOrdersMap.containsKey(price)) {
            list = priceOrdersMap.get(price);
        }
        list.add(order);
        priceOrdersMap.put(price, list);
    }

    public boolean orderExists(int orderId) {
        return idOrderMap.containsKey(orderId);
    }

    /*
        Update data in Price-Quantity map after trading
     */
    private void setPriceQuantityMap(Order order, int dealQuantity) {
        int price = order.getOrderPrice();
        priceQuantityMap.put(price, priceQuantityMap.get(price) - dealQuantity);
    }

    private void removeOrderFromPriceOrdersMap(Order order) {
        int price = order.getOrderPrice();
        List<Order> list = priceOrdersMap.get(price);
        list.remove(order);
        priceOrdersMap.put(price, list);
    }

    /*
        remove order from priceQuantityMap and priceOrdersMap.
     */
    private void removeOrderFromTwoMaps(Order order) {
        setPriceQuantityMap(order, order.getOrderQuantity());
        removeOrderFromPriceOrdersMap(order);
    }

    public void removeOrderById(int orderId) {
        Order order = getOrderById(orderId);
        orderPriorityQueue.remove(order);
        idOrderMap.remove(orderId);
        removeOrderFromTwoMaps(order);
    }

    public void makeTrade(Order order, int dealQuantity) {
        order.makeTrade(dealQuantity);
        if (order.getOrderStatus() == OrderStatus.Active) {
            setPriceQuantityMap(order, dealQuantity);
        } else {
            orderPriorityQueue.poll();
            removeOrderFromTwoMaps(order);
        }
    }

    public boolean isEmpty() {
        return orderPriorityQueue.isEmpty();
    }

    public void displayOrdersByInstrument() {
        for (Order order: idOrderMap.values()) {
            order.displayOrder();
        }
    }

    /*
        getters
     */
    public Order getOrderById(int orderId) {
        return idOrderMap.get(orderId);
    }

    public Order getFirstOrder() {
        return orderPriorityQueue.peek();
    }

    public int getBestPrice() {
        return getFirstOrder().getOrderPrice();
    }

    public int getQuantityAtBestPrice() {
        return priceQuantityMap.get(getBestPrice());
    }

    public List<Order> getOrdersAtBestPrice() {
        return priceOrdersMap.get(getBestPrice());
    }
}