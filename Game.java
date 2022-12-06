import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
This is where the game logic actually happens.
**/
public class Game {
  private ArrayList<String> replay;
  private Bridge bridge;
  private Scanner scan;

  /**
Do any setting of variables, like creating a new bridge, scanner, or storage for replay.
  **/
  public Game() {
    replay = new ArrayList<String>();
    bridge = new Bridge(9);
    scan = new Scanner(System.in);
  }

  /**
Play an entire game, at the end print the turns survived, then print the replay.
At the end return the number of turns survived.
  **/
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

  /**
Print the replay.
  **/
  private void displayReplay() {
    for (int i = 0; i < replay.size(); i++) {
      System.out.println(replay.get(i));
    }
  }
}