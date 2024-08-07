/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zelone.quacks_of_quedlinburg;

import java.util.ArrayList;

/**
 *
 * @author Zelone
 */
public class MainClass {

    public MainClass() {

        /**
         * Steps
         *
         * Loop:
         *
         * Get Potion count rewards[1:null,2:unlock ginger,3: unlock slime
         * ghost,4:null,5:null,6:add 1 wolf's bane,7:null,8:null,9:null,10:[5
         * money:1 score,2 ruby:1 score,every ingredient bought(null- for now to
         * be calculated)+ endLoop]].
         *
         * Calculate Rat extension for non first players
         * [after:[1,4,7,10,12,2*(n_1-20_+6)]].
         *
         * Choose a Card for a new game changing rule.
         *
         * Loop:
         *
         * Choose a item from Bag Put in Cauldron.
         *
         * Check the Bursting of cauldron.
         *
         * Will you continue? Y/N.
         *
         * Loop till not continue/ burst.
         *
         * Check if highest scored then throw D6 die [1 ,2 ,move start ,take 1
         * pumpkin ,1 ,take 1 ruby].
         *
         * Check if number of moths are more than your neighbor then [1:move
         * start,2:move start+ 1 ruby].
         *
         * Check if last or second last is spider then [take 1 ruby].
         *
         * Check if number slime ghost [1:1 score,2:1 score + 1 ruby,3...: 2
         * score + move start].
         *
         * Check if there is a ruby in the next visible tile[true: take 1
         * ruby,false: null].
         *
         * Check if there is a score in the next visible tile[false: null,true:
         * +{n}Score(as written on the board)].
         *
         * Check the amount of money you have in the next visible tile buy
         * ingredient {Ghost:[1:5 money,2:10 money,4:19 money],pumpkin:1:3
         * money,mushroom:[1:6 money,2:10 money,4:16 money],ginger:[1:8
         * money,2:12 money,4:18 money],spider:[1:4 money,2:8 money,4:14
         * money],moth:1:10 money,slime ghost:1:9 money}.
         *
         * If you want to spend 2 rubies to get [move start , refill reinforce
         * cauldron potion].
         *
         *
         */
    }

    public static void main(String[] args) {
        new MainClass();
    }
}

class Level {

    public static final int totallevel = 53;
    public static final int[] scorePlacememt = new int[]{5, 9, 13, 17, 21, 25, 28, 31, 34, 37, 40, 43, 47, 50, 52, 53};
    private static int lastscorearrayi = 0;
    public static final int[] rubyPlacemet = new int[]{5, 9, 13, 16, 20, 24, 28, 34, 36, 40, 42, 46, 50, 52};
    private static int lastrubyarrayi = 0;

    private Level(int count) {
        money = -1;
        setMoney(count);
        score = 0;
        setScore(count);
        isRuby = false;
        setisRuby(count);
        // Optimized for this if change check optimizers too
        if (count < totallevel) {
            nextLevel = new Level(count + 1);
        }
    }

    private static boolean isInitializing() {
        return !(lastrubyarrayi >= rubyPlacemet.length - 1 || lastrubyarrayi == 0 || lastscorearrayi == 0 || lastscorearrayi >= scorePlacememt.length - 1);
    }

    private void setisRuby(int count) {

        /*
        //without optimization
        for (int rp : rubyPlacemet) {
        if (count == rp) {
        isRuby = true;
        break;
        }
        }
         */
        // Optimized to be for continous each run CANNOT RUN MULTIPLE ITERATION AT THE SAME TIME
        if (count == 0) {
            lastrubyarrayi = 0;
        }
        if (count == rubyPlacemet[lastrubyarrayi]) {
            lastrubyarrayi++;
            isRuby = true;
        }
    }

    private void setScore(int count) {
        /*
        //without optimization
        for (int i = 0; i < scorePlacememt.length; i++) {
            if (count <= scorePlacememt[i]) {
                score = i;
                break;
            }
        }
         */
        //Optimized to be for continous each run CANNOT RUN MULTIPLE ITERATION AT THE SAME TIME
        if (count == 0) {
            lastscorearrayi = 0;
        }
        if (count <= scorePlacememt[lastscorearrayi]) {
            score = lastscorearrayi;
        }
        if (count == scorePlacememt[lastscorearrayi]) {
            lastscorearrayi++;
        }
    }

    private void setMoney(int count) {
        if (count <= 15) {
            money = count;
        } else if (count == totallevel) {
            money = 35;
        } else if (count > 35 || count < 0) {
            money = -1;
        } else {
            float ymoney = (((float) (count - 15)) / 2) + 15;
            money = (int) ymoney;
        }
    }

    public static Level generatePreset() {
        while (isInitializing()) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Level l = new Level(0);
        return l;
    }
    int score;
    int money;
    boolean isRuby;
    Level nextLevel;
}

class Board {

    private Level startlevels, currentlevel;
    private int rat = 0;
    private boolean reinforce_cauldron_potion = true;

    public Board() {
        startlevels = Level.generatePreset();
        resetBoard();
    }

    /**
     * Undo not possible
     */
    public void movestart() {
        startlevels = startlevels.nextLevel;
        resetBoard();
    }

    /**
     * Rat is reset to 0 after 1st pull from randomizer
     *
     * Setting Rat will reset the game
     */
    public void setRat(int rat) {
        resetBoard();
        this.rat = rat;
    }

    public void addIngredient(Ingredient item) {
        while (this.rat > 0) {
            this.rat--;
            currentlevel = currentlevel.nextLevel;
        }
        item.isUsed(this);
        //int pass = item.
        
    }

    public boolean isReinforce_cauldron_potion_used() {
        return !reinforce_cauldron_potion;
    }

    public boolean buyReinforce_cauldron_potion() {
        if (this.reinforce_cauldron_potion) {
            return false;
        } else {
            this.reinforce_cauldron_potion = true;
            return true;
        }
    }

    private void resetBoard() {
        currentlevel = startlevels;
    }

}

class Player {

    Board b;

}

class ScoreBoard {

}

class Game {

    ScoreBoard scoreboard;
    ArrayList<Player> players;

    public Game() {
        scoreboard = new ScoreBoard();
        players = new ArrayList<Player>();
    }

}

enum IngredientType {
    Ghost(5, 10, 19), Pumpkin(3), Mushroom(6, 10, 16), Ginger(8, 12, 18), Spider(4, 8, 14), Moth(10), Slime_Ghost(9);

    int[] money;
    int[] level = new int[]{1, 2, 4};

    private IngredientType(int... money) {
        this.money = money;
    }

}

class Ingredient {

    public int level, money;
    public IngredientType type;

    public Ingredient(int itype, IngredientType type) {
        if (itype >= 0 && itype < type.money.length) {
            this.level = type.level[itype];
            this.money = type.money[itype];
            this.type = type;
        } else {
            throw new ArrayIndexOutOfBoundsException("Ingredient index " + itype + " not valid for Ingredient Type " + type.toString());
        }
    }
//////////////////////////////////////ERROR////////////////////////////
    private Ingredient(IngredientType type, int level) {

        this.level = level;
        //this.money = type.money[itype];
        this.type = type;
    }

    private void test(int... level_money) {
        int[] l = new int[level_money.length / 2], m = new int[level_money.length / 2];
        int j = 0;
        for (int i = 0;
                i < level_money.length;
                i
                += 2) {
            l[j] = level_money[i];
            m[j] = level_money[i + 1];
            j++;
        }
        //level = l;
        //money = m;
    }

    void isUsed(Board b) {
        switch (type) {
            case Ghost:

                break;

        }
    }

    void isPulled(Player p) {
    }
}
