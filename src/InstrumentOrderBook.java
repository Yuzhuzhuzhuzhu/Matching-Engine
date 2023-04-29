import java.util.List;

public class InstrumentOrderBook {
    private final Instrument instrument;
    private OrderBook buyBook;
    private OrderBook sellBook;
    private TotalTradeBook totalTradeBook;
    private TotalClientBook totalClientBook;

    public InstrumentOrderBook(Instrument instrument, TotalTradeBook totalTradeBook, TotalClientBook totalClientBook) {
        this.instrument = instrument;
        buyBook = new OrderBook(OrderType.Buy);
        sellBook = new OrderBook(OrderType.Sell);
        this.totalTradeBook = totalTradeBook;
        this.totalClientBook = totalClientBook;
    }

    public void registerNewOrder(Order order) {
        if (order.getOrderType() == OrderType.Buy) {
            buyBook.registerNewOrder(order);
        } else {
            sellBook.registerNewOrder(order);
        }
        matchOrders(order);
    }

    public boolean orderExists(int orderId) {
        return buyBook.orderExists(orderId) || sellBook.orderExists(orderId);
    }

    private void matchOrders(Order order) {
        proRataMatch(order);
        fifoMatch();
    }

    private void proRataMatch(Order order) {
        OrderBook orderBook = getOppositeOrderBook(order);

        /*
            Step 1: Each loop stands for a round of trades.
                    Every order from the order book in a particular round of trade has the same price.
         */
        while (tradeIsValid(orderBook, order) && order.getOrderQuantity() >= orderBook.getQuantityAtBestPrice()) {
            int orderBookDealPrice = orderBook.getBestPrice();

            /*
                Each loop stands for a trade in this round.
             */
            while (!orderBook.isEmpty() && orderBook.getBestPrice() == orderBookDealPrice) {
                Order dealOrder = orderBook.getFirstOrder();
                int dealQuantity = dealOrder.getOrderQuantity();
                makeTrade(order, dealOrder, dealQuantity, getDealPrice(order, dealOrder));
            }
        }

        /*
            Step 2: Only one round of trade
                    Every order from the order book in this step has the same price.
         */
        if (tradeIsValid(orderBook, order)) {
            int quantityAtBestPrice = orderBook.getQuantityAtBestPrice();
            List<Order> dealOrders = orderBook.getOrdersAtBestPrice();
            int orderQuantity = order.getOrderQuantity();
            for (Order dealOrder: dealOrders) {
                int dealQuantity = dealOrder.getOrderQuantity() * orderQuantity / quantityAtBestPrice;
                if (dealQuantity > 0) {
                    makeTrade(order, dealOrder, dealQuantity, getDealPrice(order, dealOrder));
                }
            }
        }
    }

    private void fifoMatch() {
        while (!buyBook.isEmpty() && !sellBook.isEmpty() &&
                buyBook.getBestPrice() >= sellBook.getBestPrice()) {
            Order buyOrder = buyBook.getFirstOrder(), sellOrder = sellBook.getFirstOrder();
            int dealQuantity = Math.min(buyOrder.getOrderQuantity(), sellOrder.getOrderQuantity());
            int dealPrice = sellOrder.getOrderPrice();
            makeTrade(buyOrder, sellOrder, dealQuantity, dealPrice);
        }
    }

    private void makeTrade(Order order1, Order order2, int dealQuantity, int dealPrice) {
        Order buyOrder = getBuyOrder(order1, order2), sellOrder = getSellOrder(order1, order2);
        buyBook.makeTrade(buyOrder, dealQuantity);
        sellBook.makeTrade(sellOrder, dealQuantity);
        Trade trade = new Trade(totalTradeBook.getTradeNumber() + 1, buyOrder, sellOrder, dealQuantity,
                dealPrice);
        totalTradeBook.registerNewTrade(trade);
        totalClientBook.makeTrade(trade);
    }

    private boolean tradeIsValid(OrderBook orderBook, Order order) {
        if (orderBook.isEmpty()) {
            return false;
        }
        if (order.getOrderStatus() != OrderStatus.Active) {
            return false;
        }
        return getBuyPrice(orderBook, order) >= getSellPrice(orderBook, order);
    }

    public void displayOrders() {
        buyBook.displayOrdersByInstrument();
        sellBook.displayOrdersByInstrument();
    }

    public void removeOrderById(int orderId) {
        if (buyBook.orderExists(orderId)) {
            buyBook.removeOrderById(orderId);
        } else {
            sellBook.removeOrderById(orderId);
        }
    }


    /*
        getters
     */
    public Order getOrderById(int orderId) {
        if (buyBook.orderExists(orderId)) {
            return buyBook.getOrderById(orderId);
        }
        return sellBook.getOrderById(orderId);
    }

    private int getDealPrice(Order order1, Order order2) {
        if (order1.getOrderType() == OrderType.Sell) {
            return order1.getOrderPrice();
        }
        return order2.getOrderPrice();
    }

    private OrderBook getOppositeOrderBook(Order order) {
        if (order.getOrderType() == OrderType.Buy) {
            return sellBook;
        }
        return buyBook;
    }

    private Order getBuyOrder(Order order1, Order order2) {
        if (order1.getOrderType() == OrderType.Buy) {
            return order1;
        }
        return order2;
    }

    private Order getSellOrder(Order order1, Order order2) {
        if (order1.getOrderType() == OrderType.Sell) {
            return order1;
        }
        return order2;
    }

    private int getBuyPrice(OrderBook orderBook, Order order) {
        if (order.getOrderType() == OrderType.Buy) {
            return order.getOrderPrice();
        }
        return orderBook.getBestPrice();
    }

    private int getSellPrice(OrderBook orderBook, Order order) {
        if (order.getOrderType() == OrderType.Sell) {
            return order.getOrderPrice();
        }
        return orderBook.getBestPrice();
    }

    public OrderBook getBuyBook() {
        return buyBook;
    }

    public OrderBook getSellBook() {
        return sellBook;
    }
}