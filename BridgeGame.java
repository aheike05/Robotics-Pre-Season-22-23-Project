import java.util.Random;

public class BridgeGame {
  private char[] bridge;
  private Random rng;
  private int turns;
  private boolean fallen;
  private int[] moves;

  public BridgeGame() {
    rng = new Random();
    bridge = new char[9];
    moves = new int[2];
  }

  public void init() {
    for (int i = 0; i < bridge.length; i++) {
      bridge[i] = '_';
    }
    bridge[rng.nextInt(9)] = '#';
    turns = 0;
    fallen = false;
    for (int i = 0; i < moves.length; i++) {
      moves[i] = 0;
    }
  }

  public void takeTurn(int move) {
    moves[1] = moves[0];
    moves[0] = Math.abs(move);

    turns++;

    int prevIndex = getPlayerIndex();
    int newIndex = prevIndex + move;
    
    bridge[prevIndex] = '_';

    fallen = newIndex < 0 || newIndex >= 9;
    
    if (!fallen) {
      bridge[newIndex] = '#';
    }
  }

  public boolean isMoveValid(int move) {
    return move > 0 && move <= 3 && !(move == moves[0] && moves[0] == moves[1]);
  }

  private int getPlayerIndex() {
    for (int i = 0; i < 9; i++) {
      if (bridge[i] == '#') {
        return i;
      }
    }
    return -1;
  }
  

  public String toString() {
    String str = "";
    for (int i = 0; i < bridge.length; i++) {
      str += bridge[i];
    }

    return str;
  }

  public int getTurns() {
    return turns;
  }

  public boolean hasFallen() {
    return fallen;
  }
}