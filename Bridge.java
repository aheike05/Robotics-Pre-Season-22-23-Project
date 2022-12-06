import java.util.Random;

public class Bridge {
  private char[] bridge;

  public Bridge(int len) {
    bridge = new char[len];

    for (int i = 0; i < len; i++) {
      bridge[i] = '_';
    }

    Random rng = new Random();

    bridge[rng.nextInt(len)] = '#';
  }

  private int getPosition() {
    for (int i = 0; i < bridge.length; i++) {
      if (bridge[i] == '#') {
        return i;
      }
    }
    return -1;
  }

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

  public String toString() {
    return new String(bridge);
  }
}