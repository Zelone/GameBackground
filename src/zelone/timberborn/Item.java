/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zelone.timberborn;

import java.util.ArrayList;

/**
 *
 * @author Zelone
 */
public class Item extends Resourse {

    ArrayList<ItemType> types;

    public Item(String name) {
        super(name);
        types = new ArrayList<>();
    }

    Item addType(ItemType itemType) {
        types.add(itemType);
        return this;
    }

}
