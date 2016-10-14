package cs3500.hw02;

import java.util.Objects;

/**
 * Representation of a card using value and suit.
 */
public class Card implements Comparable<Card> {
  private Suit suit;
  private int value;

  /**
   * Get color (red or black) of card.
   * @return Card color as String.
   */
  public String getColor() {
    return this.suit.getColor();
  }

  /**
   * Get the value (1-13) of the card.
   * @return value of card as int.
   */
  public int getValue() {
    return this.value;
  }

  /**
   * Get value as a String where values 2-10 are represented by their numbers, and Jack-Ace are
   * represented by their letters.
   * @return Value of card as String
   */
  public String getValueStr() {
    switch (this.value) {
      case 1:
        return "A";
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
        return "" + this.value;
      case 11:
        return "J";
      case 12:
        return "Q";
      case 13:
        return "K";
      default:
        throw new IllegalStateException("Card value is out of range (1 - 13).");
    }
  }

  /**
   * Return true if cards have same suit, false otherwise.
   * @param that another card
   * @return boolean representing whether or not the cards are the same suit
   */
  public boolean sameSuit(Card that) {
    return this.suit == that.suit;
  }

  /**
   * Return true of cards are opposite colors, false otherwise.
   * @param that another card
   * @return boolean representing whether or not the two cards are opposite colors
   */
  public boolean oppositeColor(Card that) {
    return !this.getColor().equals(that.getColor());
  }

  @Override
  public String toString() {
    return this.getValueStr() + this.suit.getSymbol();
  }

  /**
   * Constructs a card with a suit and value.
   * @param suit The suit (Hearts, Diamonds, Clubs, or Spades) of the card
   * @param value The value (Ace - King) of the card
   */
  public Card(Suit suit, int value) {
    if (suit == null) {
      throw new IllegalArgumentException("Suit must be one of: Hearts, Spades, Clubs, Diamonds.");
    }
    if (value < 1 || value > 13) {
      throw new IllegalArgumentException("Card value must be between 1 and 13, inclusive.");
    }
    this.suit = suit;
    this.value = value;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (! (obj instanceof Card)) {
      return false;
    }

    Card that = (Card) obj;

    return this.value == that.value && this.suit == that.suit;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.value, this.suit);
  }

  @Override
  public int compareTo(Card o) {
    int diff = this.value - o.value;
    if (diff == 0) {
      return this.suit.getSuitValue() - o.suit.getSuitValue();
    }
    return diff;
  }
}
