package cs3500.hw04;

import cs3500.hw02.Card;
import cs3500.hw02.IFreeCellModel;
import cs3500.hw02.PileType;
import cs3500.hw02.Suit;
import cs3500.hw04.FreeCellModelCreator;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Ethan on 10/14/2016.
 */
public class MultiMoveFreeCellModelTest {
    FreeCellModelCreator factory;
    IFreeCellModel<Card> model;
    List<Card> sortedDeck;
    List<Card> validBuildsDeck;

    // Begin: All tests conducted on the single move class, except testMoveMultipleError
    @Test
    public void testGetDeck() {
        this.reset();
        List<Card> deck = model.getDeck();
        Collections.sort(deck);
        assertEquals(this.sortedDeck, deck);
    }

    @Test
    public void testStartGame() {
        this.reset();
        List<Card> deck = model.getDeck();
        model.startGame(deck, 4, 1, false);
        assertEquals(model.getDeck(), deck);
        String gameState = "F1:\n" +
                "F2:\n" +
                "F3:\n" +
                "F4:\n" +
                "O1:\n" +
                "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                "C3: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
                "C4: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠";
        assertEquals(gameState, model.getGameState());

        this.reset();
        model.startGame(deck, 6, 2, false);
        gameState = "F1:\n" +
                "F2:\n" +
                "F3:\n" +
                "F4:\n" +
                "O1:\n" +
                "O2:\n" +
                "C1: K♥, Q♣, 10♥, 9♣, 7♥, 6♣, 4♥, 3♣, A♥\n" +
                "C2: K♦, Q♠, 10♦, 9♠, 7♦, 6♠, 4♦, 3♠, A♦\n" +
                "C3: K♣, J♥, 10♣, 8♥, 7♣, 5♥, 4♣, 2♥, A♣\n" +
                "C4: K♠, J♦, 10♠, 8♦, 7♠, 5♦, 4♠, 2♦, A♠\n" +
                "C5: Q♥, J♣, 9♥, 8♣, 6♥, 5♣, 3♥, 2♣\n" +
                "C6: Q♦, J♠, 9♦, 8♠, 6♦, 5♠, 3♦, 2♠";
        assertEquals(gameState, model.getGameState());

        //test shuffle/non-shuffle
        this.reset();
        model.startGame(deck, 4, 1, true);
        assertNotEquals(model.getDeck(), deck);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidLargeDeck() {
        this.reset();
        List<Card> deck = model.getDeck();
        deck.add(new Card(Suit.HEARTS, 12));
        model.startGame(deck, 4, 1, false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidSmallDeck() {
        this.reset();
        List<Card> deck = model.getDeck();
        deck.remove(4);
        model.startGame(deck, 4, 1, false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCascades() {
        this.reset();
        model.startGame(model.getDeck(), 3, 1, false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidOpens() {
        this.reset();
        model.startGame(model.getDeck(), 8, 0, false);
    }

    @Test
    public void testMove() {

        //test state before movement
        this.reset();
        model.startGame(model.getDeck(), 4, 2, false);
        String gameState = "F1:\n" +
                "F2:\n" +
                "F3:\n" +
                "F4:\n" +
                "O1:\n" +
                "O2:\n" +
                "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                "C3: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
                "C4: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠";
        assertEquals(gameState, model.getGameState());

        //test move from cascade
        //test move to open
        model.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
        gameState = "F1:\n" +
                "F2:\n" +
                "F3:\n" +
                "F4:\n" +
                "O1: A♥\n" +
                "O2:\n" +
                "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥\n" +
                "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                "C3: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
                "C4: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠";
        assertEquals(gameState, model.getGameState());

        //test move from open
        //test move to empty foundation
        model.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 3);
        gameState = "F1:\n" +
                "F2:\n" +
                "F3:\n" +
                "F4: A♥\n" +
                "O1:\n" +
                "O2:\n" +
                "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥\n" +
                "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                "C3: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
                "C4: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠";
        assertEquals(gameState, model.getGameState());

        //test move to cascade
        model.move(PileType.CASCADE, 2, 12, PileType.CASCADE, 0);
        gameState = "F1:\n" +
                "F2:\n" +
                "F3:\n" +
                "F4: A♥\n" +
                "O1:\n" +
                "O2:\n" +
                "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♣\n" +
                "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                "C3: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣\n" +
                "C4: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠";
        assertEquals(gameState, model.getGameState());

        //test move to filled foundation
        this.reset();
        model.startGame(model.getDeck(), 4, 2, false);

        for (int i = 12; i >= 0; i--) {
            model.move(PileType.CASCADE, 0, i, PileType.FOUNDATION, 0);
        }
        gameState = "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                "F2:\n" +
                "F3:\n" +
                "F4:\n" +
                "O1:\n" +
                "O2:\n" +
                "C1:\n" +
                "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                "C3: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
                "C4: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠";
        assertEquals(gameState, model.getGameState());

        //test move from foundation
        //test move to empty cascade pile
        model.move(PileType.FOUNDATION, 0, 12, PileType.CASCADE, 0);
        gameState = "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥\n" +
                "F2:\n" +
                "F3:\n" +
                "F4:\n" +
                "O1:\n" +
                "O2:\n" +
                "C1: K♥\n" +
                "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                "C3: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
                "C4: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠";
        assertEquals(gameState, model.getGameState());

        this.reset();
        model.startGame(model.getDeck(), 4, 2, false);

        //test move to win the game
        for (int i = 0; i < 4; i++) {
            for (int j = 12; j >= 0; j--) {
                model.move(PileType.CASCADE, i, j, PileType.FOUNDATION, i);
            }
        }
        gameState = "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                "F2: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n" +
                "F3: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n" +
                "F4: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
                "O1:\n" +
                "O2:\n" +
                "C1:\n" +
                "C2:\n" +
                "C3:\n" +
                "C4:";
        assertEquals(gameState, model.getGameState());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMoveOpenError() {
        this.reset();
        model.startGame(model.getDeck(), 4, 2, false);
        model.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);

        //move to filled open pile
        model.move(PileType.CASCADE, 0, 11, PileType.OPEN, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMoveCascadeOutOfOrderError() {
        this.reset();
        model.startGame(model.getDeck(), 4, 2, false);

        //move onto pile out of order but opposite color
        model.move(PileType.CASCADE, 0, 12, PileType.CASCADE, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMoveCascadeSameColorError() {
        this.reset();
        model.startGame(model.getDeck(), 4, 2, false);
        model.move(PileType.CASCADE, 0, 12, PileType.FOUNDATION, 0);

        //move onto pile in order but same color
        model.move(PileType.CASCADE, 1, 12, PileType.CASCADE, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMoveFoundationOutOfSuitError() {
        this.reset();
        model.startGame(model.getDeck(), 4, 2, false);
        model.move(PileType.CASCADE, 0, 12, PileType.FOUNDATION, 0);
        model.move(PileType.CASCADE, 2, 12, PileType.FOUNDATION, 2);

        //move onto pile in order but wrong suit
        model.move(PileType.CASCADE, 0, 11, PileType.FOUNDATION, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMoveFoundationOutOfOrderError() {
        this.reset();
        model.startGame(model.getDeck(), 4, 2, false);
        model.move(PileType.CASCADE, 0, 12, PileType.FOUNDATION, 0);
        model.move(PileType.CASCADE, 0, 11, PileType.OPEN, 0);

        //move onto pile in correct suit but wrong order
        model.move(PileType.CASCADE, 0, 10, PileType.FOUNDATION, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMoveFoundationWrongStartError() {
        this.reset();
        model.startGame(model.getDeck(), 4, 2, false);
        model.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);

        //move card that isn't ace onto empty pile
        model.move(PileType.CASCADE, 0, 11, PileType.FOUNDATION, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMoveIndexOutOfBoundsError() {
        this.reset();
        model.startGame(model.getDeck(), 4, 2, false);

        //move higher index than possible
        model.move(PileType.CASCADE, 0, 13, PileType.OPEN, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMoveIndexNegativeOutOfBoundsError() {
        this.reset();
        model.startGame(model.getDeck(), 4, 2, false);

        //move lower index than possible
        model.move(PileType.CASCADE, 0, -1, PileType.OPEN, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMoveToNullError() {
        this.reset();
        model.startGame(model.getDeck(), 4, 2, false);

        //move to null pile
        model.move(PileType.CASCADE, 0, 12, null, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMoveFromNullError() {
        this.reset();
        model.startGame(model.getDeck(), 4, 2, false);

        //move from null pile
        model.move(null, 0, 12, PileType.OPEN, 0);
    }

    @Test(expected = IllegalStateException.class)
    public void testMoveEndGameError() {

        //test move to win the game
        this.reset();
        model.startGame(model.getDeck(), 4, 2, false);

        for (int i = 0; i < 4; i++) {
            for (int j = 12; j >= 0; j--) {
                model.move(PileType.CASCADE, i, j, PileType.FOUNDATION, i);
            }
        }
        String gameState = "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                "F2: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n" +
                "F3: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n" +
                "F4: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
                "O1:\n" +
                "O2:\n" +
                "C1:\n" +
                "C2:\n" +
                "C3:\n" +
                "C4:";
        assertEquals(gameState, model.getGameState());

        //test move when game won
        model.move(PileType.FOUNDATION, 0, 12, PileType.CASCADE, 1);
    }


    @Test
    public void testIsGameOver() {
        this.reset();
        //test before game start
        assertEquals(false, model.isGameOver());
        //test after game start but before game won
        model.startGame(model.getDeck(), 4, 4, false);
        assertEquals(false, model.isGameOver());
        //test after game won
        for (int i = 0; i < 4; i++) {
            for (int j = 12; j >= 0; j--) {
                model.move(PileType.CASCADE, i, j, PileType.FOUNDATION, i);
            }
        }
        assertEquals(true, model.isGameOver());
    }

    @Test
    public void testGetGameState() {
        this.reset();

        //test empty before game start
        assertEquals("", model.getGameState());

        //test initial conditions
        model.startGame(model.getDeck(), 4, 2, false);
        String gameState = "F1:\n" +
                "F2:\n" +
                "F3:\n" +
                "F4:\n" +
                "O1:\n" +
                "O2:\n" +
                "C1: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n" +
                "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n" +
                "C3: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n" +
                "C4: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠";
        assertEquals(gameState, model.getGameState());

        //test winning conditions
        for (int i = 0; i < 4; i++) {
            for (int j = 12; j >= 0; j--) {
                model.move(PileType.CASCADE, i, j, PileType.FOUNDATION, i);
            }
        }
        gameState = "F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
                "F2: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n" +
                "F3: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n" +
                "F4: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
                "O1:\n" +
                "O2:\n" +
                "C1:\n" +
                "C2:\n" +
                "C3:\n" +
                "C4:";
        assertEquals(gameState, model.getGameState());
    }

    // End: old tests

    @Test
    public void testMoveMultiple() {
        this.reset();

        //test initial conditions
        model.startGame(this.validBuildsDeck, 8, 5, false);
        String gameState = "F1:\n" +
                "F2:\n" +
                "F3:\n" +
                "F4:\n" +
                "O1:\n" +
                "O2:\n" +
                "O3:\n" +
                "O4:\n" +
                "O5:\n" +
                "C1: K♥, Q♣, J♥, 10♣, 9♥, 8♣, 7♥\n" +
                "C2: K♦, Q♠, J♦, 10♠, 9♦, 8♠, 7♦\n" +
                "C3: K♣, Q♥, J♣, 10♥, 9♣, 8♥, 7♣\n" +
                "C4: K♠, Q♦, J♠, 10♦, 9♠, 8♦, 7♠\n" +
                "C5: 6♣, 5♥, 4♣, 3♥, 2♣, A♥\n" +
                "C6: 6♠, 5♦, 4♠, 3♦, 2♠, A♦\n" +
                "C7: 6♥, 5♣, 4♥, 3♣, 2♥, A♣\n" +
                "C8: 6♦, 5♠, 4♦, 3♠, 2♦, A♠";
        assertEquals(gameState, model.getGameState());

        // test move valid build to valid build
        model.move(PileType.CASCADE, 4, 0, PileType.CASCADE, 0);
        gameState = "F1:\n" +
                "F2:\n" +
                "F3:\n" +
                "F4:\n" +
                "O1:\n" +
                "O2:\n" +
                "O3:\n" +
                "O4:\n" +
                "O5:\n" +
                "C1: K♥, Q♣, J♥, 10♣, 9♥, 8♣, 7♥, 6♣, 5♥, 4♣, 3♥, 2♣, A♥\n" +
                "C2: K♦, Q♠, J♦, 10♠, 9♦, 8♠, 7♦\n" +
                "C3: K♣, Q♥, J♣, 10♥, 9♣, 8♥, 7♣\n" +
                "C4: K♠, Q♦, J♠, 10♦, 9♠, 8♦, 7♠\n" +
                "C5:\n" +
                "C6: 6♠, 5♦, 4♠, 3♦, 2♠, A♦\n" +
                "C7: 6♥, 5♣, 4♥, 3♣, 2♥, A♣\n" +
                "C8: 6♦, 5♠, 4♦, 3♠, 2♦, A♠";
        assertEquals(gameState, model.getGameState());

        // test move valid build to empty pile
        model.move(PileType.CASCADE, 5, 3, PileType.CASCADE, 4);
        gameState = "F1:\n" +
                "F2:\n" +
                "F3:\n" +
                "F4:\n" +
                "O1:\n" +
                "O2:\n" +
                "O3:\n" +
                "O4:\n" +
                "O5:\n" +
                "C1: K♥, Q♣, J♥, 10♣, 9♥, 8♣, 7♥, 6♣, 5♥, 4♣, 3♥, 2♣, A♥\n" +
                "C2: K♦, Q♠, J♦, 10♠, 9♦, 8♠, 7♦\n" +
                "C3: K♣, Q♥, J♣, 10♥, 9♣, 8♥, 7♣\n" +
                "C4: K♠, Q♦, J♠, 10♦, 9♠, 8♦, 7♠\n" +
                "C5: 3♦, 2♠, A♦\n" +
                "C6: 6♠, 5♦, 4♠\n" +
                "C7: 6♥, 5♣, 4♥, 3♣, 2♥, A♣\n" +
                "C8: 6♦, 5♠, 4♦, 3♠, 2♦, A♠";
        assertEquals(gameState, model.getGameState());
    }
    //TODO: add exception testing

    /**
     * TODO
     */
    private void reset() {
        this.model = this.factory.create(FreeCellModelCreator.GameType.MULTIMOVE);

        this.sortedDeck = new ArrayList<>();
        this.sortedDeck.add(new Card(Suit.HEARTS, 1));
        this.sortedDeck.add(new Card(Suit.DIAMONDS, 1));
        this.sortedDeck.add(new Card(Suit.CLUBS, 1));
        this.sortedDeck.add(new Card(Suit.SPADES, 1));

        this.sortedDeck.add(new Card(Suit.HEARTS, 2));
        this.sortedDeck.add(new Card(Suit.DIAMONDS, 2));
        this.sortedDeck.add(new Card(Suit.CLUBS, 2));
        this.sortedDeck.add(new Card(Suit.SPADES, 2));

        this.sortedDeck.add(new Card(Suit.HEARTS, 3));
        this.sortedDeck.add(new Card(Suit.DIAMONDS, 3));
        this.sortedDeck.add(new Card(Suit.CLUBS, 3));
        this.sortedDeck.add(new Card(Suit.SPADES, 3));

        this.sortedDeck.add(new Card(Suit.HEARTS, 4));
        this.sortedDeck.add(new Card(Suit.DIAMONDS, 4));
        this.sortedDeck.add(new Card(Suit.CLUBS, 4));
        this.sortedDeck.add(new Card(Suit.SPADES, 4));

        this.sortedDeck.add(new Card(Suit.HEARTS, 5));
        this.sortedDeck.add(new Card(Suit.DIAMONDS, 5));
        this.sortedDeck.add(new Card(Suit.CLUBS, 5));
        this.sortedDeck.add(new Card(Suit.SPADES, 5));

        this.sortedDeck.add(new Card(Suit.HEARTS, 6));
        this.sortedDeck.add(new Card(Suit.DIAMONDS, 6));
        this.sortedDeck.add(new Card(Suit.CLUBS, 6));
        this.sortedDeck.add(new Card(Suit.SPADES, 6));

        this.sortedDeck.add(new Card(Suit.HEARTS, 7));
        this.sortedDeck.add(new Card(Suit.DIAMONDS, 7));
        this.sortedDeck.add(new Card(Suit.CLUBS, 7));
        this.sortedDeck.add(new Card(Suit.SPADES, 7));

        this.sortedDeck.add(new Card(Suit.HEARTS, 8));
        this.sortedDeck.add(new Card(Suit.DIAMONDS, 8));
        this.sortedDeck.add(new Card(Suit.CLUBS, 8));
        this.sortedDeck.add(new Card(Suit.SPADES, 8));

        this.sortedDeck.add(new Card(Suit.HEARTS, 9));
        this.sortedDeck.add(new Card(Suit.DIAMONDS, 9));
        this.sortedDeck.add(new Card(Suit.CLUBS, 9));
        this.sortedDeck.add(new Card(Suit.SPADES, 9));

        this.sortedDeck.add(new Card(Suit.HEARTS, 10));
        this.sortedDeck.add(new Card(Suit.DIAMONDS, 10));
        this.sortedDeck.add(new Card(Suit.CLUBS, 10));
        this.sortedDeck.add(new Card(Suit.SPADES, 10));

        this.sortedDeck.add(new Card(Suit.HEARTS, 11));
        this.sortedDeck.add(new Card(Suit.DIAMONDS, 11));
        this.sortedDeck.add(new Card(Suit.CLUBS, 11));
        this.sortedDeck.add(new Card(Suit.SPADES, 11));

        this.sortedDeck.add(new Card(Suit.HEARTS, 12));
        this.sortedDeck.add(new Card(Suit.DIAMONDS, 12));
        this.sortedDeck.add(new Card(Suit.CLUBS, 12));
        this.sortedDeck.add(new Card(Suit.SPADES, 12));

        this.sortedDeck.add(new Card(Suit.HEARTS, 13));
        this.sortedDeck.add(new Card(Suit.DIAMONDS, 13));
        this.sortedDeck.add(new Card(Suit.CLUBS, 13));
        this.sortedDeck.add(new Card(Suit.SPADES, 13));

        this.validBuildsDeck = new ArrayList<>();
        this.validBuildsDeck.add(new Card(Suit.HEARTS, 13));
        this.validBuildsDeck.add(new Card(Suit.DIAMONDS, 13));
        this.validBuildsDeck.add(new Card(Suit.CLUBS, 13));
        this.validBuildsDeck.add(new Card(Suit.SPADES, 13));

        this.validBuildsDeck.add(new Card(Suit.CLUBS, 6));
        this.validBuildsDeck.add(new Card(Suit.SPADES, 6));
        this.validBuildsDeck.add(new Card(Suit.HEARTS, 6));
        this.validBuildsDeck.add(new Card(Suit.DIAMONDS, 6));

        this.validBuildsDeck.add(new Card(Suit.CLUBS, 12));
        this.validBuildsDeck.add(new Card(Suit.SPADES, 12));
        this.validBuildsDeck.add(new Card(Suit.HEARTS, 12));
        this.validBuildsDeck.add(new Card(Suit.DIAMONDS, 12));

        this.validBuildsDeck.add(new Card(Suit.HEARTS, 5));
        this.validBuildsDeck.add(new Card(Suit.DIAMONDS, 5));
        this.validBuildsDeck.add(new Card(Suit.CLUBS, 5));
        this.validBuildsDeck.add(new Card(Suit.SPADES, 5));

        this.validBuildsDeck.add(new Card(Suit.HEARTS, 11));
        this.validBuildsDeck.add(new Card(Suit.DIAMONDS, 11));
        this.validBuildsDeck.add(new Card(Suit.CLUBS, 11));
        this.validBuildsDeck.add(new Card(Suit.SPADES, 11));

        this.validBuildsDeck.add(new Card(Suit.CLUBS, 4));
        this.validBuildsDeck.add(new Card(Suit.SPADES, 4));
        this.validBuildsDeck.add(new Card(Suit.HEARTS, 4));
        this.validBuildsDeck.add(new Card(Suit.DIAMONDS, 4));

        this.validBuildsDeck.add(new Card(Suit.CLUBS, 10));
        this.validBuildsDeck.add(new Card(Suit.SPADES, 10));
        this.validBuildsDeck.add(new Card(Suit.HEARTS, 10));
        this.validBuildsDeck.add(new Card(Suit.DIAMONDS, 10));

        this.validBuildsDeck.add(new Card(Suit.HEARTS, 3));
        this.validBuildsDeck.add(new Card(Suit.DIAMONDS, 3));
        this.validBuildsDeck.add(new Card(Suit.CLUBS, 3));
        this.validBuildsDeck.add(new Card(Suit.SPADES, 3));

        this.validBuildsDeck.add(new Card(Suit.HEARTS, 9));
        this.validBuildsDeck.add(new Card(Suit.DIAMONDS, 9));
        this.validBuildsDeck.add(new Card(Suit.CLUBS, 9));
        this.validBuildsDeck.add(new Card(Suit.SPADES, 9));

        this.validBuildsDeck.add(new Card(Suit.CLUBS, 2));
        this.validBuildsDeck.add(new Card(Suit.SPADES, 2));
        this.validBuildsDeck.add(new Card(Suit.HEARTS, 2));
        this.validBuildsDeck.add(new Card(Suit.DIAMONDS, 2));

        this.validBuildsDeck.add(new Card(Suit.CLUBS, 8));
        this.validBuildsDeck.add(new Card(Suit.SPADES, 8));
        this.validBuildsDeck.add(new Card(Suit.HEARTS, 8));
        this.validBuildsDeck.add(new Card(Suit.DIAMONDS, 8));

        this.validBuildsDeck.add(new Card(Suit.HEARTS, 1));
        this.validBuildsDeck.add(new Card(Suit.DIAMONDS, 1));
        this.validBuildsDeck.add(new Card(Suit.CLUBS, 1));
        this.validBuildsDeck.add(new Card(Suit.SPADES, 1));

        this.validBuildsDeck.add(new Card(Suit.HEARTS, 7));
        this.validBuildsDeck.add(new Card(Suit.DIAMONDS, 7));
        this.validBuildsDeck.add(new Card(Suit.CLUBS, 7));
        this.validBuildsDeck.add(new Card(Suit.SPADES, 7));
    }

    /**
     * TODO
     */
    public MultiMoveFreeCellModelTest() {
        this.factory = new FreeCellModelCreator();
        this.reset();
    }


}
