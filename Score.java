public class Score implements Comparable<Score> {

  private String player;
  private int score;
  
  public Score(String player, int score) {
    this.player = player.toUpperCase();
    this.score = score;
  }

  public int getScore() {
    return score;
  }

  public String getPlayer() {
    return player;
  }

  public int compareTo(Score other) {
    return other.getScore() - score;
  }

  public boolean equals(Score other) {
    return score == other.getScore() && player == other.getPlayer();
  }

  public String toString() {
    return player + " - " + score;
  }
}