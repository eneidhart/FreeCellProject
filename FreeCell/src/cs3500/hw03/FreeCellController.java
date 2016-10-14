package cs3500.hw03;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import cs3500.hw02.Card;
import cs3500.hw02.IFreeCellModel;
import cs3500.hw02.PileType;

/**
 * Created by Ethan on 10/5/2016.
 */
public class FreeCellController implements IFreeCellController<Card> {

  private final Readable in;
  private final Appendable out;
  private boolean quit;

  @Override
  public void playGame(List<Card> deck, IFreeCellModel<Card> model, int numCascades, int numOpens,
                       boolean shuffle) {
    if (this.in == null || this.out == null) {
      throw new IllegalStateException("Controller was not properly initialized to handle " +
              "input and output.");
    }

    if (model == null || deck == null) {
      throw new IllegalArgumentException("Please use a valid model and deck.");
    }

    try {
      model.startGame(deck, numCascades, numOpens, shuffle);
    } catch (IllegalArgumentException e) {
      this.output("Could not start game.");
      return;
    }

    Scanner scan = new Scanner(this.in);
    String input;

    PileType sourceType;
    int sourceIndex = 0;
    int cardIndex = 0;
    PileType destType;
    int destIndex = 0;
    while (true) {
      this.output(model.getGameState());

      this.quit = false;

      //Get source pile
      while (true) {
        input = scan.next();

        //Get pile type
        sourceType = getPile(input.charAt(0));

        if (this.quit) {
          break;
        } else if (sourceType == null) {
          this.output("\nPlease enter a valid source pile.");
          continue;
        }

        //Get pile index
        try {
          sourceIndex = Integer.parseInt(input.substring(1));
          sourceIndex--;
          break;
        } catch (NumberFormatException e) {
          this.output("\nPlease enter a valid source pile.");
          continue;
        }
      }

      if (this.quit) {
        this.output("\nGame quit prematurely.");
        return;
      }

      //Get card index
      while (true) {
        input = scan.next();

        if (input.toLowerCase().charAt(0) == 'q') {
          this.quit = true;
          break;
        }

        //Get card index
        try {
          cardIndex = Integer.parseInt(input);
          cardIndex--;
          break;
        } catch (NumberFormatException e) {
          this.output("\nPlease enter a valid card index.");
          continue;
        }
      }

      if (this.quit) {
        this.output("\nGame quit prematurely.");
        break;
      }

      //Get dest pile
      while (true) {
        input = scan.next();

        //Get pile type
        destType = getPile(input.charAt(0));

        if (this.quit) {
          break;
        } else if (destType == null) {
          this.output("\nPlease enter a valid destination pile.");
          continue;
        }

        //Get pile index
        try {
          destIndex = Integer.parseInt(input.substring(1));
          destIndex--;
          break;
        } catch (NumberFormatException e) {
          this.output("\nPlease enter a valid destination pile.");
          continue;
        }
      }

      if (quit) {
        this.output("\nGame quit prematurely.");
        return;
      }

      try {
        model.move(sourceType, sourceIndex, cardIndex, destType, destIndex);
      } catch (IllegalArgumentException e) {
        this.output("\nInvalid move. Try again.");
      }
      if (model.isGameOver()) {
        this.output("\n");
        this.output(model.getGameState());
        this.output("\nGame over.");
        return;
      }
      this.output("\n");
    }
  }

  private PileType getPile(char c) {
    PileType pile;
    switch (c) {
      case 'C':
        return PileType.CASCADE;
      case 'F':
        return PileType.FOUNDATION;
      case 'O':
        return PileType.OPEN;
      case 'q':
      case 'Q':
        this.quit = true;
        return null;
      default:
        return null;
    }
  }

  private void output(String out) {
    boolean exception = false;
    try {
      this.out.append(out);
    } catch (IOException e) {
      return;
    }
  }

  /**
   * Constructs a controller with a readable and appendable.
   * @param rd Manages reading input to the controller
   * @param ap Manages printing output from the controller.
   */
  public FreeCellController(Readable rd, Appendable ap) {
    if (rd == null || ap == null) {
      throw new IllegalStateException("Readable and Appendable objects cannot be null.");
    }
    this.in = rd;
    this.out = ap;
    this.quit = false;
  }
}
