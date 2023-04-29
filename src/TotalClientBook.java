import java.util.HashMap;
import java.util.Map;

public class TotalClientBook {
    private Map<Integer, Client> clientMap;

    public TotalClientBook() {
        clientMap = new HashMap<>();
    }

    public void registerNewClient(Client client) {
        clientMap.put(client.getClientId(), client);
    }

    public void registerNewOrder(Order order) {
        clientMap.get(order.getOrderClientId()).registerNewOrder(order);
    }

    public void displayClientStock(int clientId) {
        clientMap.get(clientId).displayClientStock();
    }

    public void manageClient(ClientManageType clientManageType, int clientId, Instrument instrument,
                             int quantity) {
        clientMap.get(clientId).manageClient(clientManageType, instrument, quantity);
    }

    public void makeTrade(Trade trade) {
        clientMap.get(trade.getBuyOrder().getOrderClientId()).makeTrade(trade, trade.getBuyOrder());
        clientMap.get(trade.getSellOrder().getOrderClientId()).makeTrade(trade, trade.getSellOrder());
    }

    public boolean clientExists(int clientId) {
        return clientMap.containsKey(clientId);
    }

    /*
        getters
     */
    public Client getClient(int clientId) {
        return clientMap.get(clientId);
    }

    /*
        used for test only
     */
    public int getClientStock(int clientId, Instrument instrument) {
        return clientMap.get(clientId).getStock(instrument);
    }

    public int getClientAccountBalance(int clientId) {
        return clientMap.get(clientId).getAccountBalance();
    }
}
