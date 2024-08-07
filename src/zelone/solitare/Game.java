/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zelone.solitare;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Zelone
 */
public class Game {

    Board board;

    public Game() {
        Setup();
        Chance();
    }

    void Chance() {
        System.out.println("");
        ArrayList<Placement> p = new ArrayList<>();
        for (int i = 0; i < Board.GameSlotNumber; i++) {
            La visibleSlot = board.getVisibleGameSlots(i);
            System.out.println("");
            for (Card card : visibleSlot) {
                System.out.print(card);
            }
            Card lastcard = visibleSlot.get(visibleSlot.size() - 1);
            if (lastcard.getNumberName().equals("A")) {
                p.add(new Placement(Position.Slot, Position.ASlot, 1, i, lastcard.getDeckID()));
                continue;
            }
            for (int j = 0; j < Board.GameSlotNumber; j++) {
                int size = visibleSlot.size();
                if (i != j) {
                    for (Card card : visibleSlot) {
                        if (board.isCompatabletoSlots(j, card)) {
                            p.add(new Placement(Position.Slot, Position.Slot, size, i, j));
                        }
                        size--;
                    }
                }
            }
            for (Card card : visibleSlot) {
                if (board.isCompatabletoASlots(card)) {
                    p.add(new Placement(Position.Slot, Position.ASlot, 1, i, card.getDeckID()));
                }
            }
        }
        System.out.println("\nTried");
        for (Placement placement : p) {
            System.out.println(placement);
        }
    }

    void Setup() {
        Card.useKfirst();
        int[] deck = Card.createDeck();
        for (int i : deck) {
            System.out.println(Card.toString(i));
        }
        board = new Board();

        int pos = 0;
        for (int i = Board.GameSlotNumber - 1; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                if (pos < deck.length) {
                    board.addCardtoSlot(j, new Card(deck[pos++], (j == i)));
                } else {
                    assert false : "Level Design Fault";
                }
            }
        }
        for (int i = pos; i < deck.length; i++) {
            board.addCardstoHand(new Card(deck[i]));
        }
        deck = null;
    }

    public static void main(String[] args) {
        new Game();
    }
}

enum Position {
    ASlot, Slot, Hand
}

class Placement {

    Position pfrom;
    Position pto;
    int size;
    int from;
    int to;

    public Placement(Position pfrom, Position pto, int size, int from, int to) {
        this.pfrom = pfrom;
        this.pto = pto;
        this.size = size;
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return pfrom + ":" + from + "->" + pto + ":" + to + "(" + size + ")";
    }

}

class Board {

    private La gameSlots[];
    private La ASlots[];
    private La hand;
    Card view[];
    int setup;
    int lastHandtoView;
    static final int GameSlotNumber = 7;

    public Board() {
        this(3); // 3 or 1
    }

    /**
     * Setup can be 3 or 1
     */
    public Board(int setup) {
        this.lastHandtoView = 0;
        this.setup = setup;
        gameSlots = new La[GameSlotNumber];
        ASlots = new La[Card.carddeck.length];
        hand = new La();
        view = new Card[this.setup];
        for (int i = 0; i < gameSlots.length; i++) {
            gameSlots[i] = new La();
        }
        for (int i = 0; i < ASlots.length; i++) {
            ASlots[i] = new La();
        }
    }

    public void addCardstoView() {
        int len = hand.size();
        if (lastHandtoView >= len) {
            lastHandtoView = 0;
        }
        for (int i = 0; i < view.length && lastHandtoView > len; i++, lastHandtoView++) {
            view[i] = hand.get(lastHandtoView).setIsFaceUp(true);
        }
    }

    public boolean isCompatabletoSlots(int i, Card card) {
        boolean out = false;
        int len = gameSlots[i].size();
        if (len > 0) {
            Card lastCard = gameSlots[i].get(len - 1);
            if (lastCard.isFaceUp()) {
                if (card.getI() + 1 == lastCard.getI()) {
                    if (card.getColorID() != lastCard.getColorID()) {
                        out = true;
                    }
                }
            } else {
                out = true;
            }
        } else {
            out = true;
        }
        return out;
    }

    public boolean addCardtoSlot(int i, Card card) {
        boolean out = false;
        if (card.isFaceUp()) {
            out = isCompatabletoSlots(i, card);
        }
        if (out) {
            gameSlots[i].add(card);
        }
        return out;
    }

    public La getVisibleGameSlots(int i) {
        La sequence = new La();
        for (Card card : gameSlots[i]) {
            if (card.isFaceUp()) {
                sequence.add(card);
            }
        }
        return sequence;
    }

    public boolean addCardstoHand(Card card) {
        if (!card.isFaceUp()) {
            hand.add(card);
            return true;
        }
        return false;
    }

    public boolean addCardtoASlot(Card card) {
        boolean out = false;
        if (card.isFaceUp()) {
            out = isCompatabletoASlots(card);
        }
        if (out) {
            ASlots[card.getDeckID()].add(card);
        }
        return out;
    }

    public boolean isCompatabletoASlots(Card card) {
        boolean out = false;
        int len = ASlots[card.getDeckID()].size();
        if (len > 0) {
            Card lastCard = ASlots[card.getDeckID()].get(len - 1);
            if (lastCard.isFaceUp()) {
                if (card.getI() == lastCard.getI() + 1) {
                    if (card.getDeckID() != lastCard.getDeckID()) {
                        out = true;
                    }
                }
            } else {
                out = card.getNumberName().equals("A");
            }
        } else {
            out = card.getNumberName().equals("A");
        }
        return out;
    }

}

class La extends ArrayList<Card> {

}

class Card {

    private final int i;
    private boolean isFaceUp;

    public Card(int i, boolean isFaceUp) {
        this.i = i;
        this.isFaceUp = isFaceUp;
    }

    public Card(int i) {
        this(i, false);
    }

    public int getI() {
        return i;
    }

    public boolean isFaceUp() {
        return isFaceUp;
    }

    public Card setIsFaceUp(boolean isFaceUp) {
        this.isFaceUp = isFaceUp;
        return this;
    }

    public int getColorID() {
        return getColorID(i);
    }

    public int getDeckID() {
        return getDeckID(i);
    }

    public int getNumberID() {
        return getNumberID(i);
    }

    public String getColorName() {
        return getColorName(i);
    }

    public String getDeckName() {
        return getDeckName(i);
    }

    public String getNumberName() {
        return getNumberName(i);
    }

    @Override
    public String toString() {
        return Card.toString(i);
    }

    public final static String[] cardcolor = new String[]{"Black", "Red"};
    public final static String[] carddeck = new String[]{"Club", "Diamond", "Pan", "Heart"};
    public static String[] cardnumber = new String[]{"A", "K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2"};

    public static int getDeckSize() {
        return cardnumber.length * carddeck.length;
    }

    public static int[] createDeck() {
        int size = Card.getDeckSize();
        int[] deck = new int[size];
        Random random = new Random();
        ArrayList<Integer> arr = new ArrayList<Integer>();
        for (int i = 1; i <= deck.length; i++) {
            arr.add(i);
        }
        for (int i = 0; i < deck.length; i++) {
            deck[i] = arr.remove(random.nextInt(arr.size()));
        }
        arr = null;

        return deck;
    }

    public static void useKfirst() {
        if (cardnumber[0].equals("A")) {
            String cache = cardnumber[0];
            for (int i = 0; i < cardnumber.length - 1; i++) {
                cardnumber[i] = cardnumber[i + 1];
            }
            cardnumber[cardnumber.length - 1] = cache;
        }
    }

    public static void useAfirst() {
        if (cardnumber[0].equals("K")) {
            String cache = cardnumber[cardnumber.length - 1];
            for (int i = cardnumber.length - 1; i > 0; i--) {
                cardnumber[i] = cardnumber[i - 1];
            }
            cardnumber[0] = cache;
        }
    }

    public static int getColorID(int i) {
        float f = i - 0.0001f;
        return (int) (f / cardnumber.length) % cardcolor.length;
    }

    public static int getDeckID(int i) {
        float f = i - 0.0001f;
        return (int) (f / cardnumber.length);
    }

    public static int getNumberID(int i) {
        float f = i - 0.0001f;
        return (int) (f % cardnumber.length);
    }

    public static String getColorName(int i) {
        return cardcolor[getColorID(i)];
    }

    public static String getDeckName(int i) {
        return carddeck[getDeckID(i)];
    }

    public static String getNumberName(int i) {
        return cardnumber[getNumberID(i)];
    }

    public static String toString(int i) {
        return i + "\t" + getNumberName(i) + "\t" + getDeckName(i) + "\t" + getColorName(i);
    }

}
