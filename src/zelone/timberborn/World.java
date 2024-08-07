/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zelone.timberborn;

import java.awt.event.KeyListener;
import java.util.EventListener;
import java.util.HashMap;
import javax.swing.event.MouseInputListener;

/**
 *
 * @author Zelone
 */
public class World {

    public HashMap<String, Resourse> resourceList;

    public World() {
        resourceList = new HashMap<>();
    }

    public void init() {
    }

    public void input(EventListener listner) {
        if (listner instanceof MouseInputListener) {
            System.out.println("Mouse");
        }
        if (listner instanceof KeyListener) {
            System.out.println("Keyboard");
        }

    }

    void addResourse(Resourse r) {
        resourceList.put(r.name, r);
    }

    void addItem(Item item) {
        addResourse(item);
    }

    void addFactory(Factory factory) {
        addResourse(factory);
    }

    void addRecipes(Recipe recipe) {
        addResourse(recipe);
    }

}
