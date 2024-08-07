/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zelone.FarmKeeper;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Zelone
 */
public class Main {

    Board board;

    public Main() {
        board = new Board();
    }

    public static void main(String[] args) {
        new Main();
    }

}

class Board extends SimpleStart {

    Map<Integer, Tile> tiles;

    public Board() {
        tiles = new HashMap<Integer, Tile>();
        Add(0, null, new Farm());
        this.Add(0, Position.top, new Farm());
        Start();
    }

    public void Add(int attach_to, Position p, Tile t) {
        if (p == null) {
            tiles.put(0, t);
        } else {
            t = tiles.get(attach_to).AttachTile(p, t);
            tiles.put(t.iTile, t);
        }
        t.Start();
    }

    @Override
    void Update() {//runs Update in Tiles
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (Map.Entry<Integer, Tile> tiledata : tiles.entrySet()) {
                    Integer tilekey = tiledata.getKey();
                    Tile tile = tiledata.getValue();
                    tile.Update();
                }
            }
        });
        t.start();
        try {
            t.join(100);
        } catch (Exception e) {
        }
    }

    @Override
    void FixedUpdate() {//runs Fixed update in tiles
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (Map.Entry<Integer, Tile> tiledata : tiles.entrySet()) {
                    Integer tilekey = tiledata.getKey();
                    Tile tile = tiledata.getValue();
                    tile.FixedUpdate();
                }
            }
        });
        t.start();
        try {
            t.join(0);
        } catch (Exception e) {
        }
    }

    @Override
    void Start() {//does not initiate Start in tiles
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Update();
                }
            }
        });
        Thread tt = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    FixedUpdate();
                }
            }
        });
        tt.start();
        t.start();
        try {
            Thread.sleep(1000);
        } catch (Exception ex) {
        }
        t.interrupt();
        tt.interrupt();
        t.stop();
        tt.stop();
        System.err.println("INTRRUPT");
    }
}

class Farm extends Tile {

    int U = 0;

    @Override
    void Update() {
        System.out.println("zelone.FarmKeeper.Farm.Update()" + (U++) + "-" + this);

    }
    int FU = 0;

    @Override
    void FixedUpdate() {
        System.out.println("zelone.FarmKeeper.Farm.FixedUpdate()" + (FU++) + "-" + this);

    }

    @Override
    void Start() {
        System.out.println("zelone.FarmKeeper.Farm.Start()" + this);
    }
}

abstract class Tile extends SimpleStart {

    public int iTile;
    int[] connect;

    public Tile() {
        connect = new int[8];
        iTile = 0;
    }

    @Override
    public String toString() {
        return "" + iTile + ":Tile";
    }

    public Tile AttachTile(Position p, Tile t) {
        t.iTile = iTile + 1;
        switch (p) {
            case top:
                t.connect[0] = t.iTile;
                break;
            case top_right:
                t.connect[1] = t.iTile;
                break;
            case right:
                t.connect[2] = t.iTile;
                break;
            case bottom_right:
                t.connect[3] = t.iTile;
                break;
            case bottom:
                t.connect[4] = t.iTile;
                break;
            case bottom_left:
                t.connect[5] = t.iTile;
                break;
            case left:
                t.connect[6] = t.iTile;
                break;
            case top_left:
                t.connect[7] = t.iTile;
                break;
            default:
        }
        return t;
    }
}

abstract class SimpleStart {

    abstract void Update();

    abstract void FixedUpdate();

    abstract void Start();

}

enum Position {
    top, top_right, right, bottom_right, bottom, bottom_left, left, top_left;
}
