// Omar Iltaf

import java.util.Scanner;

public class GameOfLife {

  public static void main(String[] args) {
    Grid world = new Grid(1);
    Scanner keyboard = new Scanner(System.in);
    String input;
    boolean playGame = true;

    while (playGame) {
      world.display();
      input = keyboard.nextLine();
      if (input.equals("q") || input.equals("quit")) {
        playGame = false;
      } else {
        world.update();
      }
    }

  }
}
