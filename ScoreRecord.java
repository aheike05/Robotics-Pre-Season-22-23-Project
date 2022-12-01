import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;

public class ScoreRecord {

  private File source;
  private ArrayList<Score> scoreList;
  
  public ScoreRecord(String path) {
    source = new File(path);
    try {
      source.createNewFile();
      
    } catch (Exception e) {
      System.out.println(Main.ESC + Main.FG_RED + "Can't create missing score file!" + Main.ESC + Main.RESET);
    }
    scoreList = new ArrayList<Score>();
  }

  public void readScores() {
    try {
      Scanner scan = new Scanner(source);
      while (scan.hasNextLine()) {
        String scoreString = scan.nextLine();

        String[] pieces = scoreString.split(":");
        
        String player = pieces[0];
        int score = Integer.parseInt(pieces[1]);
        scoreList.add(new Score(player, score));
      }
    } catch (Exception e) {
      System.out.println(Main.ESC + Main.FG_RED + "Can't read from score file!" + Main.ESC + Main.RESET);
    }
  }

  public void addScore(Score score) {
    scoreList.add(score);
  }

  public void writeScores() {
    String scoresString = "";
    for (Score score : scoreList) {
      scoresString += score.getPlayer() + ":" + score.getScore() + "\n";
    }
    
    try {
      FileWriter writer = new FileWriter(source, false);
      writer.write(scoresString);
      writer.close();
    } catch (Exception e) {
      System.out.println(Main.ESC + Main.FG_RED + "Failed to record scores!" + Main.ESC + Main.RESET);
    }
  }

  public String[] getPlayers() {
    ArrayList<String> players = new ArrayList<String>();

    for (Score score : scoreList) {
      if (!players.contains(score.getPlayer())) {
        players.add(score.getPlayer());
      }
    }

    return players.toArray(new String[0]);
  }

  public Score[] getScores(String player) {
    ArrayList<Score> scores = new ArrayList<Score>();

    for (Score score : scoreList) {
      if (score.getPlayer().equals(player.toUpperCase())) {
        scores.add(score);
      }
    }

    return scores.toArray(new Score[0]);
  }

  public Score getHighScore() {
    Score highScore = scoreList.get(0);
    for (Score score : scoreList) {
      if (score.getScore() >= highScore.getScore()) {
        highScore = score;
      }
    }

    return highScore;
  }

  public Score getPlayerHighScore(String player) {
    Score[] scores = getScores(player);
    Score highScore = scores[0];
    for (Score score : scores) {
      if (score.getPlayer().equals(player.toUpperCase())) {
        if (score.getScore() >= highScore.getScore()) {
          highScore = score;
        }
      }
    }

    return highScore;
  }

  public Score[] getScoreBoard() {
    Score[] scoreBoard = scoreList.toArray(new Score[0]);
    Arrays.sort(scoreBoard);

    return scoreBoard;
  }
}