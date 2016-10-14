package cs3500.hw02;

/**
 * Enumeration representing the suit of a card-- Hearts, Clubs, Diamonds, or Spades.
 * It can provide a String for the color of the suit, a String for the symbol of the suit, and an
 * int which stores the value of the suit (for sorting purposes only).
 */
public enum Suit {

  /**
   * The Hearts suit. It is red, represented by ♥, and is sorted lower than other suits.
   */
  HEARTS("RED", "♥", 0),

  /**
   * The Diamonds suit. It is red, represented by ♦, and is sorted higher than Hearts only.
   */
  DIAMONDS("RED", "♦", 1),

  /**
   * The Clubs suit. It is black, represented by ♣, and is sorted lower than Spades only.
   */
  CLUBS("BLACK", "♣", 2),

  /**
   * The Spades suit. It is black, represented by ♠, and is sorted highest of all suits.
   */
  SPADES("BLACK", "♠", 3);

  private final String color;
  private final String symbol;
  private final int suitValue;

  /**
   * Gets the color of the suit.
   * @return the color as a String.
   */
  public String getColor() {
    return color;
  }

  /**
   * Gets the symbol of the suit.
   * @return the symbol as a String.
   */
  public String getSymbol() {
    return symbol;
  }

  /**
   * Gets the value of the suit. Used to sort two cards of equal value and differing suits only.
   * @return the value of the suit as an int.
   */
  public int getSuitValue() {
    return suitValue;
  }

  /**
   * Constructor for the suit, initializes suit data.
   * @param color the color of the suit.
   * @param symbol the symbol of the suit.
   * @param suitValue the value of the suit.
   */
  Suit(String color, String symbol, int suitValue) {
    this.color = color;
    this.symbol = symbol;
    this.suitValue = suitValue;
  }
}
