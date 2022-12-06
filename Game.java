import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
  private ArrayList<String> replay;
  private Bridge bridge;
  private Scanner scan;

  public Game() {
    replay = new ArrayList<String>();
    bridge = new Bridge(9);
    scan = new Scanner(System.in);
  }

  public int play() {
    int turns = 0;

    boolean onBridge = true;

    Random rng = new Random();

    while (onBridge) {
      int dist = 0;

      for (int i = 0; dist <= 0 || dist > 3; i++) {
        if (i > 0) {
          System.out.println("Invalid distance! Must be between 1 and 3, try again.");
        }
        
        System.out.println("Distance to move?");
        
        dist = scan.nextInt();
      }

      if (rng.nextBoolean()) {
        dist = -dist;
      }

      replay.add(bridge.toString());

      onBridge = bridge.move(dist);
      
      turns++;
    }

    replay.add(bridge.toString());

    System.out.println("You surived " + turns + " turn(s)!");
    System.out.println("Here is your replay.");
    
    displayReplay();

    return turns;
  }

  private void displayReplay() {
    for (int i = 0; i < replay.size(); i++) {
      System.out.println(replay.get(i));
    }
  }
}