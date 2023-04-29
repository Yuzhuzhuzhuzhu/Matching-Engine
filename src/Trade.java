public class Trade {
    private final int tradeId;
    private final Order buyOrder;
    private final Order sellOrder;
    private final int tradeQuantity;
    private final int tradePrice;

    private final Instrument instrument;
    private final int totalAmount;

    public Trade(int tradeId, Order buyOrder, Order sellOrder, int tradeQuantity, int tradePrice) {
        this.tradeId = tradeId;
        this.buyOrder = buyOrder;
        this.sellOrder = sellOrder;
        this.tradeQuantity = tradeQuantity;
        this.tradePrice = tradePrice;
        instrument = buyOrder.getOrderInstrument();
        totalAmount = tradeQuantity * tradePrice;
        System.out.print("New ");
        displayTrade();
    }

    public void displayTrade() {
        System.out.println("Trade No." + tradeId +
                " | Buyer ID: " + buyOrder.getOrderClientId() +
                " | Seller ID: " + sellOrder.getOrderClientId() +
                " | Buy Order ID: " + buyOrder.getOrderId() +
                " | Sell Order ID: " + sellOrder.getOrderId() +
                " | Instrument: " + instrument +
                " | Quantity: " + tradeQuantity +
                " | Price: " + tradePrice +
                " | Total Amount: " + totalAmount);
    }

    /*
        getters
     */
    public int getTradeId() {
        return tradeId;
    }

    public Order getBuyOrder() {
        return buyOrder;
    }

    public Order getSellOrder() {
        return sellOrder;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public int getTradeQuantity() {
        return tradeQuantity;
    }

    public int getTotalAmount() {
        return totalAmount;
    }
}