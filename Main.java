public class Main {
  public static void main(String[] args) {
    int[] plays = new int[10];
    int sum = 0;
    int min = 1000000;
    int max = 0;
    Game game;

    for (int i = 0; i < plays.length; i++) {
      System.out.println("New game!");
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