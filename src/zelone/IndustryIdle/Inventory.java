/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zelone.IndustryIdle;

/**
 *
 * @author Zelone
 */
class Inventory {

    private int[] inventory;

    public Inventory() {
        inventory = new int[Market.getTotalItems()];
    }

    int getInventory(int iitem) {
        return inventory[iitem];
    }

    void addInventory(int iitem, int quantity) {
        inventory[iitem] += quantity;
    }

    boolean checkInventoryChange(int iitem, int quantity) {
        return inventory[iitem] + quantity > 0;
    }

}
