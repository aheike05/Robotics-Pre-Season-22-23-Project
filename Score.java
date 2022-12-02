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

  public String toString() {
    return String.format("%s - %03d", player, score);
  }
}