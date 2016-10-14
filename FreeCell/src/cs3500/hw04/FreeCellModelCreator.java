package cs3500.hw04;

import cs3500.hw02.FreeCellModel;
import cs3500.hw02.IFreeCellModel;

/**
 * Created by Ethan on 10/13/2016.
 */
public class FreeCellModelCreator {

  /**
   * TODO
   */
  public enum GameType {
    SINGLEMOVE, MULTIMOVE
  }

  /**
   * TODO
   * @param type
   * @return
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
