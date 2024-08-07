/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zelone.IndustryIdle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author Zelone
 */
public class Tile {

    public HashMap<Integer, Integer[]> items_percent;
    private int x = -1, y = -1, z = -1;
    private static int empty_x = -1, empty_y = -1, empty_z = -1;
    private int ItemMine = -1;
    private boolean isIndexSet = false, isEmpty = false, isMinable = false;

    public Tile setIndex(int x, int y, int z) {

        if (isEmpty) {
            empty_x = x;
            empty_y = y;
            empty_z = z;
        }
        this.x = x;
        this.y = y;
        this.z = z;
        isIndexSet = true;
        return this;

    }

    public static int[] getEmptyIndex() {
        return new int[]{empty_x, empty_y, empty_z};
    }

    public Tile setAsMineable(int iitem) {
        if (Market.getItem(iitem).isMineable()) {
            isMinable = true;
            ItemMine = iitem;
        }
        return this;
    }

    public Tile setAsEmpty() {
        isEmpty = true;
        if (isIndexSet) {
            empty_x = x;
            empty_y = y;
            empty_z = z;
        }
        return this;
    }

    public int[] getIndex() {
        return new int[]{x, y, z};
    }

    public Tile() {
        items_percent = new HashMap<Integer, Integer[]>();
        int[] percent = new int[6];
        int total_items = Market.getTotalItems();
        int n_items_per_percent = 1;//total_items / (percent + 10); //1,2
        ArrayList<Integer> item_list = new ArrayList<>();
        for (int i = 0; i < total_items; ++i) {
            item_list.add(i);
        }
        for (int i = 0; i < 10; i++) {
            item_list.add(Item.getEmptyItemIndex());
        }
        int max_percent = (5 * (percent.length - 1));
        Random random = new Random();
        
        for (int i = 0; i < percent.length - 1; ++i) {
            percent[i] = max_percent - i * 5; // 25,20,15,10,5,0
            Integer[] items = new Integer[n_items_per_percent];
            System.out.print("\n"+percent[i] + ":" );
            for (int j = 0; j < n_items_per_percent; j++) {
                items[j] = item_list.remove(random.nextInt(item_list.size()));
                System.out.print("," + Market.getItemName(items[j]));
            }
            items_percent.put(percent[i], items);
        }
        percent[percent.length - 1] = 0;
        Integer[] item_lisst = new Integer[item_list.size()];
        int i = 0;
        System.out.print("\n"+percent[percent.length - 1]  + ":" );
        for (Integer integer : item_list) {
            item_lisst[i] = integer;
            System.out.print("," + Market.getItemName(item_lisst[i]));
            i++;
        }
        items_percent.put(percent[percent.length - 1], item_lisst);

    }

}
