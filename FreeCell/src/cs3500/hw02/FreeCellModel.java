package cs3500.hw02;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The FreeCell Game model. Keeps track of all game data.
 */
public class FreeCellModel implements IFreeCellModel<Card> {
  protected Pile[] opens;
  protected Pile[] cascades;
  protected Pile[] foundations;
  protected int foundationCount;
  protected boolean gameStart;

  @Override
  public List<Card> getDeck() {
    List<Card> cards = new ArrayList<Card>();
    for (int value = 13; value > 0; value--) {
      for (Suit suit : Suit.values()) {
        cards.add(new Card(suit, value));
      }
    }
    return cards;
  }

  @Override
  public void startGame(List<Card> deck, int numCascadePiles, int numOpenPiles, boolean shuffle)
          throws IllegalArgumentException {
    if (numCascadePiles < 4 || numOpenPiles < 1) {
      throw new IllegalArgumentException("Invalid number of piles.");
    }

    //check deck validity
    if (deck.size() != 52) {
      throw new IllegalArgumentException("Invalid number of cards in deck.");
    }
    List<Card> testDeck = this.getDeck();
    for (Card c : testDeck) {
      if (!deck.contains(c)) {
        throw new IllegalArgumentException("Deck is missing " + c.toString());
      }
    }

    if (shuffle) {
      Collections.shuffle(deck);
    }

    //init piles
    this.opens = new Pile[numOpenPiles];
    this.cascades = new Pile[numCascadePiles];
    for (int i = 0; i < numOpenPiles; i++) {
      this.opens[i] = new Pile();
    }
    for (int i = 0; i < numCascadePiles; i++) {
      this.cascades[i] = new Pile();
    }

    //deal cards
    for (int i = 0; i < deck.size();) {
      for (int p = 0; p < numCascadePiles && i < deck.size(); p++, i++) {
        this.cascades[p].add(deck.get(i));
      }
    }

    this.gameStart = true;
  }

  /**
   * TODO
   * @param sourcePileNumber
   * @param piles
   * @param cardIndex
   * @return
   * @throws IllegalArgumentException
   */
  protected List<Card> removeCards(int sourcePileNumber, Pile[] piles, int cardIndex)
          throws IllegalArgumentException {
    if (sourcePileNumber >= piles.length || sourcePileNumber < 0) {
      throw new IllegalArgumentException("Invalid source pile index.");
    }
    if (piles[sourcePileNumber].isEmpty()) {
      throw new IllegalArgumentException("Source pile is empty");
    }
    return piles[sourcePileNumber].remove(cardIndex);
  }

  /**
   * TODO
   * @param destPileNumber
   * @param piles
   * @param sourceType
   * @param sourcePileNumber
   * @param cards
   */
  protected void checkDestPile (int  destPileNumber, Pile[] piles, PileType sourceType,
                                int sourcePileNumber, List<Card> cards)
          throws IllegalArgumentException{
    if (destPileNumber >= piles.length || destPileNumber < 0) {
      returnCards(sourceType, sourcePileNumber, cards);
      throw new IllegalArgumentException("Invalid destination pile index.");
    }
  }
  protected void placeCards(int destPileNumber, Pile[] piles, List<Card> cards,
                            boolean placeCondition, PileType sourceType, int sourcePileNumber)
          throws IllegalArgumentException {
    if (placeCondition) {
      piles[destPileNumber].add(cards);
    } else {
      returnCards(sourceType, sourcePileNumber, cards);
      throw new IllegalArgumentException("Invalid move.");
    }
  }

  @Override
  public void move(PileType sourceType, int sourcePileNumber, int cardIndex, PileType destType,
                   int destPileNumber) throws IllegalArgumentException {
    List<Card> cards;

    if (this.isGameOver()) {
      throw new IllegalStateException("Game is over, no more moves allowed.");
    }

    if (sourceType == null) {
      throw new IllegalArgumentException("Source pile cannot be null.");
    }
    switch (sourceType) {
      case OPEN:
        cards = removeCards(sourcePileNumber, this.opens, cardIndex);
        break;
      case CASCADE:
        cards = removeCards(sourcePileNumber, this.cascades, cardIndex);
        break;
      case FOUNDATION:
        cards = removeCards(sourcePileNumber, this.foundations, cardIndex);
        this.foundationCount -= cards.size();
        break;
      default:
        throw new IllegalArgumentException("Invalid source pile type.");
    }

    //should only move 1 card at a time
    if (cards.size() != 1) {
      returnCards(sourceType, sourcePileNumber, cards);
      throw new IllegalArgumentException("Only one card may be moved at a time.");
    }

    Card top;
    boolean condition;
    if (destType == null) {
      throw new IllegalArgumentException("Destination pile cannot be null.");
    }
    switch (destType) {
      case OPEN:
        checkDestPile(destPileNumber, this.opens, sourceType, sourcePileNumber, cards);
        condition = (this.opens[destPileNumber].isEmpty() && cards.size() == 1);
        placeCards(destPileNumber, this.opens, cards, condition, sourceType, sourcePileNumber);
        break;
      case CASCADE:
        checkDestPile(destPileNumber, this.cascades, sourceType, sourcePileNumber, cards);
        top = this.cascades[destPileNumber].top();
        condition = (this.cascades[destPileNumber].isEmpty() ||
                (top.oppositeColor(cards.get(0)) && top.getValue() == cards.get(0).getValue() + 1));
        placeCards(destPileNumber, this.cascades, cards, condition, sourceType, sourcePileNumber);
        break;
      case FOUNDATION:
        checkDestPile(destPileNumber, this.foundations, sourceType, sourcePileNumber, cards);
        top = this.foundations[destPileNumber].top();
        boolean condition1 = (this.foundations[destPileNumber].isEmpty() &&
                cards.get(0).getValue() == 1);
        if (!condition1 && top == null) {
          throw new IllegalArgumentException("Only Aces may be placed in empty foundation piles.");
        }
        condition = (condition1 ||
                (top.sameSuit(cards.get(0)) && top.getValue() + 1 == cards.get(0).getValue()));
        placeCards(destPileNumber, this.foundations, cards, condition,
                sourceType, sourcePileNumber);
        this.foundationCount += cards.size();
        break;
      default:
        returnCards(sourceType, sourcePileNumber, cards);
        throw new IllegalArgumentException("Card must move to valid pile type.");
    }
  }

  @Override
  public boolean isGameOver() {
    return this.foundationCount == 52;
  }

  @Override
  public String getGameState() {
    String gameState = "";

    if (gameStart) {
      for (int i = 0; i < this.foundations.length; i++) {
        gameState += String.format("F%d: ", i + 1);
        for (Card c : this.foundations[i].cards) {
          gameState += c.toString() + ", ";
        }
        if (this.foundations[i].isEmpty()) {
          gameState = gameState.substring(0, gameState.length() - 1);
        } else {
          gameState = gameState.substring(0, gameState.length() - 2);
        }
        gameState += "\n";
      }

      for (int i = 0; i < this.opens.length; i++) {
        gameState += String.format("O%d: ", i + 1);
        for (Card c : this.opens[i].cards) {
          gameState += c.toString() + ", ";
        }
        if (this.opens[i].isEmpty()) {
          gameState = gameState.substring(0, gameState.length() - 1);
        } else {
          gameState = gameState.substring(0, gameState.length() - 2);
        }
        gameState += "\n";
      }

      for (int i = 0; i < this.cascades.length; i++) {
        gameState += String.format("C%d: ", i + 1);
        for (Card c : this.cascades[i].cards) {
          gameState += c.toString() + ", ";
        }
        if (this.cascades[i].isEmpty()) {
          gameState = gameState.substring(0, gameState.length() - 1);
        } else {
          gameState = gameState.substring(0, gameState.length() - 2);
        }
        if (i != this.cascades.length - 1) {
          gameState += "\n";
        }
      }
    }

    return gameState;
  }

  /**
   * Returns a list of cards to their source pile.
   * Called when moving cards to invalid destination pile.
   * @param sourceType The type of source pile the cards came from and are returning to
   * @param sourceIndex The index of the source pile the cards came form and will return to
   * @param cards The cards being returned
   */
  protected void returnCards(PileType sourceType, int sourceIndex, List<Card> cards) {
    switch (sourceType) {
      case OPEN:
        this.opens[sourceIndex].add(cards);
        break;
      case CASCADE:
        this.cascades[sourceIndex].add(cards);
        break;
      case FOUNDATION:
        this.foundations[sourceIndex].add(cards);
        this.foundationCount += cards.size();
        break;
      default:
        throw new IllegalArgumentException("Card must move to valid pile type.");
    }
  }

  /**
   * Constructs a new FreeCellModel. There are always 4 foundation piles, and they are all
   * initialized. The gameStart and foundationCount variables should always start as false and 0.
   */
  public FreeCellModel() {
    this.foundations = new Pile[4];
    for (int i = 0; i < 4; i++) {
      this.foundations[i] = new Pile();
    }
    this.gameStart = false;
    this.foundationCount = 0;
  }

}
