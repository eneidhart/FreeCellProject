package cs3500.hw02;

import java.util.ArrayList;
import java.util.List;

/**
 * Representation of a pile of cards as an ArrayList of cards.
 */
public class Pile {
  public List<Card> cards;

  /**
   * Tells if the pile is empty or not.
   * @return true if pile is empty, false otherwise.
   */
  public boolean isEmpty() {
    return this.cards.isEmpty();
  }

  /**
   * Grabs top card of pile. If pile is empty, returns null (there are checks for this whenever
   * top() is called).
   * @return the top card of the pile.
   */
  public Card top() {
    try {
      return this.cards.get(this.cards.size() - 1);
    } catch (IndexOutOfBoundsException e) {

      //top is only used if the pile is not empty
      return null;
    }
  }

  /**
   * Removes cards from the pile, starting at the given index and going to the top of the pile.
   * @param index the index at which to start removing cards.
   * @return A list of the cards removed.
   */
  public List<Card> remove(int index) {
    if (index < 0 || index >= this.cards.size()) {
      throw new IllegalArgumentException("Pile index out of bounds.");
    }
    List<Card> removed = new ArrayList<Card>();
    while (index < this.cards.size()) {
      removed.add(this.cards.remove(index));
    }
    return removed;
  }

  /**
   * Adds a list of cards to the top of the pile.
   * @param cards The list of cards to be added.
   */
  public void add(List<Card> cards) {
    this.cards.addAll(cards);
  }

  /**
   * Adds a single card to the top of the pile.
   * @param c The card to be added.
   */
  public void add(Card c) {
    this.cards.add(c);
  }

  /**
   * Constructor which initializes the ArrayList keeping track of the cards.
   */
  public Pile() {
    this.cards = new ArrayList<Card>();
  }
}
