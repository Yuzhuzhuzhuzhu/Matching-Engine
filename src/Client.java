import java.util.HashMap;
import java.util.Map;

public class Client {
    private final int clientId;
    private String clientName;
    private int accountBalance;
    private Map<Instrument, Integer> instrumentStockMap;
    private ClientOrderBook clientOrderBook;
    private TotalTradeBook totalTradeBook;

    public Client(int clientId, String clientName) {
        this.clientId = clientId;
        this.clientName = clientName;
        accountBalance = 0;
        instrumentStockMap = new HashMap<>();
        clientOrderBook = new ClientOrderBook();
        totalTradeBook = new TotalTradeBook();
    }

    public void displayClientStock() {
        System.out.print("Client No." + clientId + "Stock | ");
        for (Instrument instrument: Instrument.values()) {
            System.out.print(instrument + ": " + instrumentStockMap.get(instrument));
            if (instrument == Instrument.USDT) {
                System.out.println();
            } else {
                System.out.print(" | ");
            }
        }
    }

    public void manageClient(ClientManageType clientManageType, Instrument instrument, int quantity) {
        if (clientManageType == ClientManageType.DepositMoney) {
            depositMoney(quantity);
        } else if (clientManageType == ClientManageType.WithdrawMoney) {
            withdrawMoney(quantity);
        } else if (clientManageType == ClientManageType.DepositProperty) {
            depositProperty(instrument, quantity);
        } else {
            withdrawProperty(instrument, quantity);
        }
    }

    /*
        deposit and withdraw money
     */
    public void depositMoney(int totalAmount) {
        accountBalance += totalAmount;
    }

    public void withdrawMoney(int totalAmount) {
        if (accountBalance < totalAmount) {
            System.out.println("Failed to withdraw money. Your account balance is not enough.");
            return;
        }
        accountBalance -= totalAmount;
    }

    /*
        deposit and withdraw property
     */
    public void depositProperty(Instrument instrument, int quantity) {
        int stock = 0;
        if (instrumentStockMap.containsKey(instrument)) {
            stock = instrumentStockMap.get(instrument);
        }
        instrumentStockMap.put(instrument, stock + quantity);
    }

    public void withdrawProperty(Instrument instrument, int quantity) {
        if (!instrumentStockMap.containsKey(instrument) || instrumentStockMap.get(instrument) < quantity) {
            System.out.println("Failed to withdraw " + instrument + ". Your stock is not enough." + clientId);
            return;
        }
        instrumentStockMap.put(instrument, instrumentStockMap.get(instrument) - quantity);
    }

    public void registerNewOrder(Order order) {
        clientOrderBook.registerNewOrder(order);
    }

    public void makeTrade(Trade trade, Order tradeOrder) {
        Instrument instrument = tradeOrder.getOrderInstrument();
        int tradeQuantity = trade.getTradeQuantity(), tradeTotalAmount = trade.getTotalAmount();
        if (tradeOrder.getOrderType() == OrderType.Buy) {
            withdrawMoney(tradeTotalAmount);
            depositProperty(instrument, tradeQuantity);
        } else {
            depositMoney(tradeTotalAmount);
            withdrawProperty(instrument, tradeQuantity);
        }
        totalTradeBook.registerNewTrade(trade);
    }

    public boolean orderExists(int orderId) {
        return clientOrderBook.orderExists(orderId);
    }

    /*
        getters
     */
    public Order getTradeOrder(Trade trade) {
        if (orderExists(trade.getBuyOrder().getOrderId())) {
            System.out.println(trade.getBuyOrder().getOrderId());
            return trade.getBuyOrder();
        }
        System.out.println(trade.getSellOrder().getOrderId());
        return trade.getSellOrder();
    }

    public int getStock(Instrument instrument) {
        return instrumentStockMap.get(instrument);
    }
    public int getClientId() {
        return clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public int getAccountBalance() {
        return accountBalance;
    }
}
