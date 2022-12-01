import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.security.MessageDigest;

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
    try (BufferedInputStream read = new BufferedInputStream(new FileInputStream(source))) {
      MessageDigest md5;
      byte[] buf = new byte[read.available()];
      if (buf.length > 16) {
        for (int i = 0; i < buf.length; i++) {
          read.read(buf, 0, buf.length);
        }
        String scoreString = new String(Arrays.copyOfRange(buf, 0, buf.length - 16));
        md5 = MessageDigest.getInstance("MD5");
        md5.update(scoreString.getBytes());
        byte[] expected = md5.digest();
        byte[] given = Arrays.copyOfRange(buf, buf.length - 16, buf.length);

        if (!Arrays.equals(given, expected)) {
          System.out.println(Main.ESC + Main.FG_RED + "You modified the scores, cheater!" + Main.ESC + Main.RESET);
          resetScores(true);
          System.exit(1);
        }
        
        String[] scoreStrings = scoreString.split(":");
        for (String str : scoreStrings) {
          String player = str.substring(0, 3);
          int score = Integer.parseInt(str.substring(3));
        
          scoreList.add(new Score(player, score));
        }
      }
      read.close();
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println(Main.ESC + Main.FG_RED + "Can't read from score file!" + Main.ESC + Main.RESET);
    }
  }

  public void addScore(Score score) {
    scoreList.add(score);
  }

  private void resetScores(boolean cheat) {
    try (FileWriter writer = new FileWriter(source, false)) {
      writer.write("");
    } catch (Exception e) {
      System.out.print(Main.ESC + Main.FG_RED + "Failed to reset scores!");
      if (cheat) {
        System.out.print(" Lucky you, cheater.");
      }

      System.out.println(Main.ESC + Main.RESET);
    }
  }

  public void writeScores() {
    String scoresString = "";
    for (Score score : scoreList) {
      scoresString += score.getPlayer() + score.getScore() + ":";
    }
    try (FileWriter writer = new FileWriter(source, false)) {
      writer.write(scoresString);
      MessageDigest md5 = MessageDigest.getInstance("MD5");
      md5.update(scoresString.getBytes());
      writer.close();
      byte[] hash = md5.digest();
      FileOutputStream byteWriter = new FileOutputStream(source, true);
      byteWriter.write(hash);
      byteWriter.close();
    } catch (Exception e) {
      System.out.println(Main.ESC + Main.FG_RED + "Failed to write scores!" + Main.ESC + Main.RESET);
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