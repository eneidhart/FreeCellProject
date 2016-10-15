package cs3500.hw04;

import cs3500.hw02.FreeCellModel;
import cs3500.hw02.IFreeCellModel;

/**
 * Factory class for creating an IFreeCellModel.
 * Created by Ethan on 10/13/2016.
 */
public class FreeCellModelCreator {

  /**
   * Enumeration specifying which type of game will be played.
   */
  public enum GameType {
    /**
     * Only one card may be moved at a time in this game type.
     */
    SINGLEMOVE,

    /**
     * Multiple cards may be moved at a time in this game type, as long as they form a valid build,
     * form a valid build with the top card of their destination pile, and the number of free open
     * piles and cascade piles allows this sequence of single moves.
     */
    MULTIMOVE
  }

  /**
   * Returns a model for FreeCell.
   * @param type specifies whether the game will be played with single moves only,
   *             or with multi-moves allowed.
   * @return An IFreeCellModel for playing the game.
   */
  public static IFreeCellModel create(GameType type) {
    switch (type) {
      case SINGLEMOVE:
        return new FreeCellModel();
      case MULTIMOVE:
        return new MultiMoveFreeCellModel();
      default:
        throw new IllegalArgumentException("Invalid game type.");
    }
  }
}
