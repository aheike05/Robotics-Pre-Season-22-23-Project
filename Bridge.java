import java.util.Random;

/**
Stores the bridge and the character position on the bridge, includes methods for interracting with the bridge.
**/
public class Bridge {
  private char[] bridge;

  /**
Creates a new bridge with the given length.
Fills the bridge and places the character randomly on the bridge.
  **/
  public Bridge(int len) {
    bridge = new char[len];

    for (int i = 0; i < len; i++) {
      bridge[i] = '_';
    }

    Random rng = new Random();

    bridge[rng.nextInt(len)] = '#';
  }

  /**
Gets the index in the array that holds the character, if the character is not in the array, return -1.
  **/
  private int getPosition() {
    for (int i = 0; i < bridge.length; i++) {
      if (bridge[i] == '#') {
        return i;
      }
    }
    return -1;
  }

  /**
Moves the player by the specified amount.
If the player moves off the bridge, return false, else return true.
  **/
  public boolean move(int len) {
    int pos = getPosition();
    int newPos = pos + len;

    bridge[pos] = '_';
    
    if (newPos < 0 || newPos >= bridge.length) {
      return false;
    }

    bridge[newPos] = '#';

    return true;
  }
  /**
Return the bridge as a string.
  **/
  public String toString() {
    return new String(bridge);
  }
}