/**
Runs the game multiple times and displays data about the plays
**/
public class Main {

  /**
This will run the game 10 times, recording the maximum turns taken out of all, the minimum, and the average.
It will then print the number of plays done, the minimum, maximum, and the average.
  **/
  public static void main(String[] args) {
    int[] plays = new int[10];
    int sum = 0;
    int min = 1000000;
    int max = 0;
    Game game;
    
    for (int i = 0; i < plays.length; i++) {
      game = new Game();
      int play = game.play();
      sum += play;
      if (play < min) {
        min = play;
      }

      if (play > max) {
        max = play;
      }
    }

    double average = sum / plays.length;

    System.out.println("Number of plays: " + plays.length);
    System.out.println("Minimum turns taken: " + min);
    System.out.println("Maximum turns taken: " + max);
    System.out.println("Average turns taken: " + average);
  }
}