/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zelone.IndustryIdle;

/**
 *
 * @author Zelone
 */
public class Game {

    public static boolean isRunning = true;

    public Game() {
        setupMarket();
        Bank b = new Bank(); //Personal Bank 
        Market m = new Market(false); //Market that does not change the prices 
        Inventory inv = new Inventory(); //Personal Inventory
        inv.addInventory(0, 50);
        City cityA = new City();  //City that has a market, inventory

        System.out.println(cityA.market.getPrice(0) + ":" + cityA.market.getPrice(1));
        cityA.addInventory(0, 10000);
        for (int i = 0; i < 10; i++) {
            m.sell(0, 50, b);
            cityA.sell(0, 50);
            try {
                Thread.sleep((int) (0.5f * 100));
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(cityA.bank.getCashinHand() + "\t" + cityA.getInventory(0));
            System.out.println(b.getCashinHand() + "\t" + cityA.getInventory(0));
        }
        cityA.buy(1, 100);
        System.out.println(cityA.market.getPrice(0) + ":" + cityA.market.getPrice(1));
        Board board = new Board();
        

    }

    private void setupMarket() {
        try {
            Market.add(new Item("Empty", 0f).setEmpty());
            Market.add(new Item("A", 400f));
            Market.add(new Item("B", 500f));
            Market.add(new Item("C", 300f).setAsMineable());
            Market.add(new Item("D", 750f));
            Market.add(new Item("E", 600f));
            Market.add(new Item("F", 450f).setAsMineable());
            Market.add(new Item("G", 350f));
            Market.add(new Item("H", 150f));
            Market.add(new Item("I", 250f));

        } catch (IllegalAccessException illex) {
            Market.close();
            illex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Game();
        Market.close();
        isRunning = false;
    }

}

