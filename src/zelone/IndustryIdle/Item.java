/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zelone.IndustryIdle;

/**
 *
 * @author Zelone
 */
class Item implements Comparable<String> {

    private int index;
    final String name;
    final float baseprice;
    private boolean isIndexSet = false;
    private boolean isEmpty = false, isMineable = false;
    private static int emptyIitem = -1;

    public static int getEmptyItemIndex() {
        return emptyIitem;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public Item setEmpty() {
        this.isEmpty = true;

        return this;
    }

    public Item(String name, float baseprice) {
        this.name = name;
        this.baseprice = baseprice;
    }

    @Override
    public int compareTo(String o) {
        return name.compareTo(name);
    }

    boolean setIndex(int i) {
        if (!isIndexSet) {
            index = i;
            if (isEmpty) {
                emptyIitem = i;
            }
            isIndexSet = true;
            return true;
        }
        return false;
    }

    public int getIndex() {
        return index;
    }

    public Item setAsMineable() {
        isMineable = true;
        return this;
    }

    boolean isMineable() {
        return isMineable;
    }

}
