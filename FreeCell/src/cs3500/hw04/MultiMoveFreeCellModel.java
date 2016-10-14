package cs3500.hw04;

import java.util.List;

import cs3500.hw02.Card;
import cs3500.hw02.FreeCellModel;
import cs3500.hw02.Pile;
import cs3500.hw02.PileType;

/**
 * Created by Ethan on 10/13/2016.
 */
public class MultiMoveFreeCellModel extends FreeCellModel {

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

    int numFreeOpens = 0;
    int numFreeCascades = 0;

    for (Pile p : this.opens) {
      if (p.isEmpty()) {
        numFreeOpens++;
      }
    }

    for (Pile p : this.cascades) {
      if (p.isEmpty()) {
        numFreeCascades++;
      }
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

    double maxCards = (numFreeOpens + 1) * Math.pow(2, numFreeCascades);

    if (cards.size() > maxCards) {
      throw new IllegalArgumentException("Could not move that many cards at once.");
    }

    Card lastCard = null;
    if (cards.size() > 1) {
      for (Card c : cards) {
        if (lastCard != null &&
                (!c.oppositeColor(lastCard) || c.getValue() != lastCard.getValue() - 1)) {
          throw new IllegalArgumentException("Cards moved must form a valid build.");
        }
        lastCard = c;
      }
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
        condition = ((condition1 ||
                (top.sameSuit(cards.get(0)) && top.getValue() + 1 == cards.get(0).getValue())) &&
        cards.size() == 1);
        placeCards(destPileNumber, this.foundations, cards, condition,
                sourceType, sourcePileNumber);
        this.foundationCount += cards.size();
        break;
      default:
        returnCards(sourceType, sourcePileNumber, cards);
        throw new IllegalArgumentException("Card must move to valid pile type.");
    }
  }
}
