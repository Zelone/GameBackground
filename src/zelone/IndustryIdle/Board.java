/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zelone.IndustryIdle;

/**
 *
 * @author Zelone
 */
public class Board {

    private static final int LENGTH, HEIGHT, WIDTH;
    Tile[][][] tiles;
    
    static {
        HEIGHT = 1;
        LENGTH = 10;
        WIDTH = 10;
    }

    public Board() {
        tiles = new Tile[LENGTH][WIDTH][HEIGHT];
        // 0, 0, 0 
        // length , width , height
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                for (int k = 0; k < tiles[i][j].length; k++) {
                    tiles[i][j][k] = new Tile().setIndex(i, j, k);
                }
            }
        }
        
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public static int getLENGTH() {
        return LENGTH;
    }

    public static int getWIDTH() {
        return WIDTH;
    }

}
/*
******************************
*                            *
*                            *
*                            *
*                            *
*                            *
*                            *
*                            *
*                            *
*                            *
*                            *
******************************
 */
