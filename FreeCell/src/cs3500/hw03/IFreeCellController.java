package cs3500.hw03;

import java.util.List;

import cs3500.hw02.IFreeCellModel;

/**
 * This is the interface of the Freecell model. It is parameterized over the
 * card type.
 */
public interface IFreeCellController<K> {

  /**
   * Launches a game.
   * @param deck The deck to play the game with.
   * @param model The model for the game.
   * @param numCascades The number of cascade piles in the game.
   * @param numOpens The number of open piles in the game.
   * @param shuffle Whether or not to shuffle the deck before starting.
   */
  void playGame(List<K> deck, IFreeCellModel<K> model, int numCascades, int numOpens,
                boolean shuffle);
}
