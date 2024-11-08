package codingblackfemales.gettingstarted;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import codingblackfemales.action.Action;
import codingblackfemales.action.CancelChildOrder;
import codingblackfemales.action.CreateChildOrder;
import codingblackfemales.action.NoAction;
import codingblackfemales.algo.AlgoLogic;
import codingblackfemales.sotw.ChildOrder;
import codingblackfemales.sotw.SimpleAlgoState;
import codingblackfemales.sotw.marketdata.BidLevel;
import codingblackfemales.util.Util;
import messages.order.Side;

public class MyAlgoLogic implements AlgoLogic {

    private static boolean cancelOrders = false;

    private static final Logger logger = LoggerFactory.getLogger(MyAlgoLogic.class);

    @Override
    public Action evaluate(SimpleAlgoState state) {
        String orderBookAsString;
        try {
         orderBookAsString = Util.orderBookToString(state);
        } catch (Exception e) {
            return NoAction.NoAction;
        }
        

        logger.info("[MYALGO] The state of the order book is:\n" + orderBookAsString);

        /********
         *
         * Add your logic here....
         *
         */
         // Define the maximum number of bid levels to check
        int maxBidLevels = Math.min(15, state.getBidLevels());
        int maxOrders = 3;
        
        
        // Variable to store the best bid
        BidLevel bestBid = null;

        // Loop through bid levels to find the best one
        for (int i = 0; i < maxBidLevels; i++) {
            BidLevel currentBid = state.getBidAt(i);
             // Add null check to avoid NullPointerException
            

            logger.info("Bid " + i + ": [Price: " + currentBid.price + ", Quantity: " + currentBid.quantity + "]");


            // Check if this is the best bid (highest price)
            if (bestBid == null || currentBid.price > bestBid.price) {
                bestBid = currentBid;
            }
        }
                    

        // Check active orders
        var activeOrders = state.getActiveChildOrders();

        // Handle filled quantities (relevant to backtest)
        long totalFilledQuantity = activeOrders.stream()
                .mapToLong(ChildOrder::getFilledQuantity)
                .sum();

        
       logger.info("[MYALGO] Total active orders so far:" + activeOrders.size());
         
       logger.info("[MYALGO] Total child order so far:" + state.getChildOrders().size());
    
        
        //actual orders
         logger.info("[MYALGO] Total filled quantity so far: " + totalFilledQuantity);

        if (cancelOrders == true && !activeOrders.isEmpty()) {
            ChildOrder firstOrder = activeOrders.get(0);
            logger.info("[MYALGO] Cancelling order: " + firstOrder);
            return new CancelChildOrder(firstOrder); //Cancel existing order

        } else if (cancelOrders == true && activeOrders.isEmpty()){
            return NoAction.NoAction;
        }
        
        // If no active orders, create a new buy order at the best bid price
         if (activeOrders.size() < maxOrders && bestBid != null){
            
            
            // If we reached the maximum number of orders, enable cancellation for the next evaluation
            if (activeOrders.size() + 1 >= maxOrders) {
            cancelOrders = true;
              // Do nothing until orders are canceled
        }
            logger.info("[MYALGO] Placing new buy order for " + bestBid.quantity + " units @ " + bestBid.price);
            return new CreateChildOrder(Side.BUY, bestBid.quantity, bestBid.price);
        }
    return NoAction.NoAction;
    }
}


        
        
        
        
        
        
        
        
        