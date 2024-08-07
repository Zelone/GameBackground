/*

 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zelone.timberborn;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;

/**
 *
 * @author Zelone
 */
public class Main {

    public Main() {
        World world = new World();
        world.init();
        world.input(new MouseInputListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mouseDragged(MouseEvent e) {
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }
        });
        System.out.println("EE");
        world.input(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        world.addItem(
                new Item("Log")
                        .addType(ItemType.Level0) //0
                        .addType(ItemType.Generatable) //0 
        );
        world.addResourse(
                new Resourse("Tree")
                        .addProperty( //2
                                Property.Growable
                                        .setUpgradable(true) //1
                                        .setMaxUpgrades(10) //1
                                        .setMaxTime(4000) //1
                                        .setMinTime(100) //1
                        )
                        .addProperty( //2
                                Property.Generatable
                        )
        );
        world.addRecipes(
                new Recipe("Tree Harvest")
                        .add(new RecipeData()
                                .addType(EntityType.Resourse)
                                .add(world.resourceList.get("Tree"))
                                .setFromOrTo(false)
                        ) //0
                        .add(new RecipeData()
                                .add(world.resourceList.get("Log"))
                                .addType(EntityType.Item)
                                .setFromOrTo(true)
                                .addMaxAmount(10)
                                .addMinAmount(1)
                        ) //0
                        .setUpgradable(true) //1
                        .setMaxUpgrades(10) //1
                        .setMaxTime(400) //1
                        .setMinTime(10) //1
        );
        world.addFactory(
                new Factory("Lumberjack")
                        .addRecipe((Recipe) world.resourceList.get("Tree Harvest")) //0
                        .setUpgradable(true) //1
                        .setMaxUpgrades(10) //1
                        .setMaxTime(40) //1
                        .setMinTime(1) //1
        );

    }

    public static void main(String[] args) {
        new Main();
    }

}
