/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zelone.minesweeper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static zelone.minesweeper.Main.r;

/**
 *
 * @author Zelone
 */
public class Main {

    public static Random r;

    static {
        r = new Random();
        createRules();
    }
    int size;
    int[][] board;
    boolean[][] boardMask;

    int[] possible = new int[]{0, -1};
    float[] percent = new float[]{80, 20};

    public static List<Rule> rules;

    public Main(int size) {
        this.size = size;
        createBoard();
        setNumbers();
        createMask();

        selectPlace(true);
        printBoard();
        if (checkiflost()) {
            System.out.println("\n\nLOST\n");
        }

    }

    public static void main(String[] args) {
        int i = 9;
        for (int j = 0; j < 10; j++) {
            new Main(i);
        }
    }

    private void createBoard() {
        board = new int[size][size];
        for (int i = 0; i < board.length; i++) {
            board[i] = new int[size];
            for (int j = 0; j < board[i].length; j++) {
                int tot = 100;
                int kk = r.nextInt(tot);
                int possiblei = 0;
                try {
                    do {
                        kk -= (int) (((float) tot) * (percent[possiblei] / 100));
                        possiblei++;
                    } while (kk > 0);
                } catch (Exception e) {
                    possiblei = possible.length - 1;
                }
                board[i][j] = possible[possiblei - 1];
            }
        }

    }

    private void setNumbers() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                int sum = 0;
                if (board[i][j] >= 0) {
                    for (int ii = -1; ii <= 1; ii++) {
                        for (int jj = -1; jj <= 1; jj++) {
                            if (ii == 0 && jj == 0) {
                                continue;
                            }
                            try {
                                if (board[i + ii][j + jj] < 0) {
                                    sum += board[i + ii][j + jj];

                                }
                            } catch (Exception e) {
                            }
                        }
                    }
                    board[i][j] = sum * -1;
                }
            }

        }
    }

    private void printBoard() {
        for (int i = 0; i < board.length; i++) {
            System.out.println();
            for (int j = 0; j < board[i].length; j++) {
                if (boardMask[i][j]) {
                    System.out.print(((board[i][j] >= 0) ? board[i][j] : "*") + " ");
                } else {
                    System.out.print("# ");
                }
            }
        }
    }

    private void createMask() {
        boardMask = new boolean[size][size];
        for (int i = 0; i < boardMask.length; i++) {
            boardMask[i] = new boolean[size];
            for (int j = 0; j < boardMask[i].length; j++) {
                boardMask[i][j] = false;
            }
        }
    }

    private void selectPlace(boolean rand) {

        int[] point = new int[2];
        if (rand) {
            point = new int[]{r.nextInt(size), r.nextInt(size)};
        } else {
            point = runNextRule();
        }
        selectPlace(point[0], point[1]);
    }

    private void selectPlace(int i, int j) {
        if (boardMask[i][j]) {

        } else {
            if (board[i][j] == 0) {
                boardMask[i][j] = true;
//                try {
//                    selectPlace(i+1, j);
//                    selectPlace(i, j+1);
//
//                    selectPlace(i, j-1);
//                    selectPlace(i-1, j);
//
//                } catch (Exception e) {
//                }
                for (int ii = -1; ii <= 1; ii++) {
                    for (int jj = -1; jj <= 1; jj++) {
                        if (ii == 0 && jj == 0) {
                            continue;
                        }
                        try {
                            selectPlace(i + ii, j + jj);
                        } catch (Exception e) {
                        }
                    }
                }
            } else {
                boardMask[i][j] = true;
            }
        }
    }

    private boolean checkiflost() {
        for (int i = 0; i < boardMask.length; i++) {
            for (int j = 0; j < boardMask[i].length; j++) {
                if (boardMask[i][j]) {
                    if (board[i][j] < 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private int[] runNextRule() {
        return rules.get(0).getClearPoint(board);
    }

    private static void createRules() {
        rules = new ArrayList<Rule>();
        rules.add(new Rule());
    }
}

class Rule {

    Rule() {
    }

    int[] getClearPoint(int[][] board) {
        return new int[]{r.nextInt(board.length), r.nextInt(board.length)};
    }
}
