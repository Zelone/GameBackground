/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zelone.IndustryIdle;

/**
 *
 * @author Zelone
 */
class City {

    Market market;
    Bank bank;
    Inventory inventory;

    public City() {
        market = new Market();
        bank = new Bank();
        inventory = new Inventory();
    }

    private boolean inventoryChange(int iitem, int quantity, boolean to_buy_or_sell) {
        boolean transaction = false;
        quantity = ((to_buy_or_sell) ? 1 : -1) * quantity;
        if (inventory.checkInventoryChange(iitem, quantity)) {
            transaction = market.buy(iitem, quantity, bank);
            if (transaction) {
                inventory.addInventory(iitem, quantity);
            }
        }
        return transaction;
    }

    boolean buy(int iitem, int quantity) {
        return inventoryChange(iitem, quantity, true);
    }

    boolean sell(int iitem, int quantity) {
        return inventoryChange(iitem, quantity, false);
    }

    public int getInventory(int iitem) {
        return inventory.getInventory(iitem);
    }

    boolean addInventory(int iitem, int quantity) {
        if (inventory.checkInventoryChange(iitem, quantity)) {
            inventory.addInventory(iitem, quantity);
            return true;
        }
        return false;
    }

}
