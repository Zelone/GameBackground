/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zelone.IndustryIdle;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.function.Predicate;

/**
 *
 * @author Zelone
 */
class Market {

    private final static float maxBalancer = 4f;
    private final static float deaultBalancer = 1f;
    private final static float deltaBalancer = 0.00002f;
    private final static float minBalancer = 0.3f;
    private static Item[] items;
    private static ArrayList<Item> createitems;

    static {
        createitems = new ArrayList<Item>();
        System.out.println("Initialized Items");
    }

    static int add(Item item) throws IllegalAccessException {
        if (items == null) {
            createitems.add(item);
            return createitems.indexOf(item);
        }
        throw new IllegalAccessException("Market created cannot add items");
    }

    public static int getTotalItems() {
        if (items == null) {
            return createitems.size();
        }
        return items.length;
    }

    public static int getItemID(String itemName) {
        int i = 0;
        for (Item item : items) {
            i++;
            if (item.compareTo(itemName) == 0) {
                break;
            }
        }
        return i;
    }

    public static String getItemName(int iitem) {
        return items[iitem].name;
    }

    private static void emptyCreatItemsList() {
        createitems.removeIf(new Predicate<Item>() {
            @Override
            public boolean test(Item t) {
                return true;
            }
        });
    }

    static void close() {
        emptyCreatItemsList();
        if (items != null) {
            for (int i = 0; i < items.length; i++) {
                items[i] = null;
            }
            items = null;
        }
        System.out.println("All Items Removed Successfully");
    }

    public static boolean isMarketSetupComplete() {
        return items != null;
    }

    /*
    
     */
    private float[] priceBalancers;
    private boolean BalancePrices;
    private float[] stockChangesLatest;
    private float[] stockChanges;

    private long timeForStock;

    public static Item getItem(int iitem) {
        if (items == null) {
            return createitems.get(iitem);
        }
        return items[iitem];
    }

    public Market(boolean BalancePric) {
        this();
        BalancePrices = BalancePric;
    }

    public Market() {
        if (items == null) {
            items = new Item[createitems.size()];
            for (int i = 0; i < createitems.size(); i++) {
                items[i] = createitems.get(i);
                items[i].setIndex(i);
            }
            emptyCreatItemsList();
        }
        priceBalancers = new float[items.length];
        stockChanges = new float[items.length];
        stockChangesLatest = new float[items.length];
        for (int i = 0; i < items.length; i++) {
            priceBalancers[i] = deaultBalancer;
            stockChanges[i] = 0f;
            stockChangesLatest[i] = 0f;
        }
        BalancePrices = true;
    }

    public float getPrice(int iitem) {
        return items[iitem].baseprice * priceBalancers[iitem];
    }

    private float balanceBalancer(float blancer) {
        if (blancer > maxBalancer) {
            blancer = maxBalancer;
        }
        if (blancer < minBalancer) {
            blancer = minBalancer;
        }
        return blancer;
    }

    private boolean stockChange(int iitem, int quantity, Bank bank, boolean to_buy_or_sell) {
        float priceBalancer = priceBalancers[iitem];
        if (bank.attemptAmount(((to_buy_or_sell) ? -1 : 1) * items[iitem].baseprice * priceBalancer * quantity)) {
            if (BalancePrices) {
                float balancer = ((to_buy_or_sell) ? 1 : -1) * deltaBalancer * quantity;
                priceBalancers[iitem] = balanceBalancer(priceBalancer + balancer);
                long timeNow = new GregorianCalendar().getTime().getTime();
                stockChangesLatest[iitem] += balancer;
                if (timeNow - timeForStock > 100) {
                    stockChanges = stockChangesLatest;
                    for (int i = 0; i < stockChangesLatest.length; i++) {
                        stockChangesLatest[i] = 0f;
                    }
                    timeForStock = timeNow;
                }
            }
            return true;
        }
        return false;
    }

    public boolean buy(int iitem, int quantity, Bank bank) {
        return stockChange(iitem, quantity, bank, true);
    }

    public boolean sell(int iitem, int quantity, Bank bank) {
        return stockChange(iitem, quantity, bank, false);
    }

}
