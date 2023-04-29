import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        MatchingEngine matchingEngine = new MatchingEngine();

        /*
            Test 1: New Client Register
         */
        System.out.println("Test 1: New Client Register");
        Client btcSeller = new Client(1, "BTC Seller No.1");
        matchingEngine.registerNewClient(btcSeller);
        if (matchingEngine.clientExists(btcSeller.getClientId())) {
            System.out.println("Test 1 passed");
        } else {
            System.out.println("Test 1 failed. Please check again");
        }
        System.out.println();





        /*
            Test 2: Manage Client (deposits/withdraws some money/properties)
         */
        System.out.println("Test 2: Manage Client (deposits/withdraws some money/properties)");
        matchingEngine.manageClient(ClientManageType.DepositMoney, 1, null, 101);
        matchingEngine.manageClient(ClientManageType.WithdrawMoney, 1, null, 1);
        matchingEngine.manageClient(ClientManageType.DepositProperty, 1, Instrument.BTC, 1001);
        matchingEngine.manageClient(ClientManageType.WithdrawProperty, 1, Instrument.BTC, 1);

        /*
            check if the account balance and property's stock are correct
         */
        if (matchingEngine.getClientAccountBalance(1) == 100 &&
                matchingEngine.getClientStock(1, Instrument.BTC) == 1000) {
            System.out.println("Test 2 passed");
        } else {
            System.out.println("Test 2 failed. Please check again");
        }
        System.out.println();





        /*
            Test 3: Invalid Operation of Client Management
         */
        System.out.println("Test 3: Invalid Operation of Client Management");
        matchingEngine.manageClient(ClientManageType.WithdrawMoney, 1, null, 1000);
        matchingEngine.manageClient(ClientManageType.WithdrawProperty, 1, Instrument.BTC, 10000);

        /*
            check if the account balance and property's stock are correct
         */
        if (matchingEngine.getClientAccountBalance(1) == 100 &&
                matchingEngine.getClientStock(1, Instrument.BTC) == 1000) {
            System.out.println("Test 3 passed");
        } else {
            System.out.println("Test 3 failed. Please check again");
        }
        System.out.println();





        /*
            Test 4: Register a Valid Buy Order, an Invalid Buy Order,
                    a Valid Sell Order and an Invalid Sell Order.
         */
        System.out.println("Test 4: Register a Valid Buy Order, an Invalid Buy Order, a Valid Sell Order and an Invalid Sell Order");
        Order ethBuyOrder = new Order(1, Instrument.ETH, OrderType.Buy, 1, 5,
                10);
        Order invalidBuyOrder = new Order(2, Instrument.ETH, OrderType.Buy, 1, 5,
                100);
        Order btcSellOrder1 = new Order(3, Instrument.BTC, OrderType.Sell, 1, 5,
                5);
        Order invalidSellOrder = new Order(4, Instrument.BTC, OrderType.Sell, 1, 5,
                10000);
        Queue<Order> newOrders = new LinkedList<>();
        newOrders.add(ethBuyOrder);
        newOrders.add(invalidBuyOrder);
        newOrders.add(btcSellOrder1);
        newOrders.add(invalidSellOrder);
        matchingEngine.registerNewOrders(newOrders);

        /*
            Test 4.1: Check If the Orders' Status Are Correct
         */
        System.out.println("Test 4.1: Check If the Orders' Status Are Correct");
        if (ethBuyOrder.getOrderStatus() == OrderStatus.Active &&
                invalidBuyOrder.getOrderStatus() == OrderStatus.Invalid &&
                btcSellOrder1.getOrderStatus() == OrderStatus.Active &&
                invalidSellOrder.getOrderStatus() == OrderStatus.Invalid) {
            System.out.println("Test 4.1 passed");
        } else {
            System.out.println("Test 4.1 failed. Please check again");
        }

        /*
            Test 4.2: Check If the Orders Are Stored In the Right Order Book
         */
        System.out.println("Test 4.2: Check If the Orders Are Stored In the Right Order Book");
        if (matchingEngine.getTotalOrderBook().getActiveOrderBook().getInstrumentOrderBook(Instrument.ETH).getBuyBook().orderExists(1) &&
                matchingEngine.getTotalOrderBook().getInactiveOrderBook().getBasicOrderBook(OrderStatus.Invalid).orderExists(2) &&
                matchingEngine.getTotalOrderBook().getActiveOrderBook().getInstrumentOrderBook(Instrument.BTC).getSellBook().orderExists(3) &&
                matchingEngine.getTotalOrderBook().getInactiveOrderBook().getBasicOrderBook(OrderStatus.Invalid).orderExists(4)) {
            System.out.println("Test 4.2 passed");
        } else {
            System.out.println("Test 4.2 failed. Please check again");
        }
        System.out.println();





        /*
            Test 5: Test Matching Algorithm
         */
        System.out.println("Test 5: Test Matching Algorithm");

        /*
            add BTC sell orders
         */
        Order btcSellOrder2 = new Order(5, Instrument.BTC, OrderType.Sell, 1, 5,
                9);

        Order btcSellOrder3 = new Order(6, Instrument.BTC, OrderType.Sell, 1, 5,
                57);
        Order btcSellOrder4 = new Order(7, Instrument.BTC, OrderType.Sell, 1, 5,
                4);
        Order btcSellOrder5 = new Order(8, Instrument.BTC, OrderType.Sell, 1, 5,
                28);
        Order btcSellOrder6 = new Order(9, Instrument.BTC, OrderType.Sell, 1, 5,
                300);
        newOrders.add(btcSellOrder2);
        newOrders.add(btcSellOrder3);
        newOrders.add(btcSellOrder4);
        newOrders.add(btcSellOrder5);
        newOrders.add(btcSellOrder6);

        /*
            add BTC buyer and a buy order
         */
        Client btcBuyer = new Client(2, "BTC Buyer NO.1");
        matchingEngine.registerNewClient(btcBuyer);
        btcBuyer.manageClient(ClientManageType.DepositMoney, null, 4000);

        /*
            test pro - rata step 1: modify btcBuyOrder‘s orderQuantity to 500
            test pro - rata step 2: modify btcBuyOrder's orderQuantity to 50
         */
        Order btcBuyOrder = new Order(10, Instrument.BTC, OrderType.Buy, 2, 6,
                50);
        newOrders.add(btcBuyOrder);
        matchingEngine.registerNewOrders(newOrders);
        System.out.println();





        /*
            Test 6: Retrieve An Order
         */
        System.out.println("Test 6: Retrieve An Order");
        Order order = matchingEngine.getOrderById(1);
        order.displayOrder();
        System.out.println();





        /*
            Test 7: Display All the Active Orders For A Specific Instrument
         */
        System.out.println("Test 7: Display All the Active Orders For A Specific Instrument");
        matchingEngine.displayOrdersByInstrument(Instrument.BTC);
        System.out.println();





        /*
            Test 8: Retrieve A Trade
         */
        System.out.println("Test 8: Retrieve A Trade");
        Trade trade = matchingEngine.getTradeById(1);
        trade.displayTrade();
        System.out.println();





        /*
            Test 9: Display All the Executed Trades For A Specific Instrument
         */
        System.out.println("Test 9: Display All the Executed Trades For A Specific Instrument");
        matchingEngine.displayTradesByInstrument(Instrument.BTC);
        System.out.println();





        /*
            Test 10: Cancel An Order
         */
        System.out.println("Test 10: Cancel An Order");
        matchingEngine.cancelOrderById(1);
        if (matchingEngine.getTotalOrderBook().getInactiveOrderBook().getBasicOrderBook(OrderStatus.Cancelled).
                orderExists(1)) {
            System.out.println("Test 10 passed");
        } else {
            System.out.println("Test 10 failed. Please check again");
        }
    }
}