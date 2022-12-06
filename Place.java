public class Place {
  private int score;
  private String name;

  public Place(String name) {
    this.name = name;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public int getScore() {
    return score;
  }

  public String getName() {
    return name;
  }

  public String toString() {
    return name + " - " + score;
  }
}