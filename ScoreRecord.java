import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.HashSet;
import java.io.File;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.security.MessageDigest;

public class ScoreRecord {

  private File source;
  private HashSet<Score> scoreList;
  
  public ScoreRecord(String path) {
    source = new File(path);
    try {
      source.createNewFile();
    } catch (Exception e) {
      System.out.println(Main.ESC + Main.FG_RED + "Can't create missing score file!" + Main.ESC + Main.RESET);
    }
    scoreList = new HashSet<Score>();
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
        
        String[] split1 = scoreString.split(";");
        String[] players = split1[0].split("\\.");
        String[] scores = split1[1].split(":");
        for (String str : scores) {
          String[] split2 = str.split("\\.");
          int playerIndex = Integer.parseInt(split2[0]);
          String player = players[playerIndex];
          int score = Integer.parseInt(split2[1]);
        
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
    try (BufferedOutputStream write = new BufferedOutputStream(new FileOutputStream(source))) {
      write.write(new byte[0], 0, 0);
    } catch (Exception e) {
      System.out.print(Main.ESC + Main.FG_RED + "Failed to reset scores!");
      if (cheat) {
        System.out.print(" Lucky you, cheater.");
      }

      System.out.println(Main.ESC + Main.RESET);
    }
  }

  public void writeScores() {
    String str = "";
    String[] players = getPlayers();
    for (String player : players) {
      str += player + ".";
    }
    str += ";";
    for (Score score : scoreList) {
      int idx;
      for (idx = 0; idx < players.length && !players[idx].equals(score.getPlayer()); idx++);
      str += idx + "." + score.getScore() + ":";
    }
    try (BufferedOutputStream write = new BufferedOutputStream(new FileOutputStream(source))) {
      write.write(str.getBytes(), 0, str.length());
      MessageDigest md5 = MessageDigest.getInstance("MD5");
      md5.update(str.getBytes());
      byte[] hash = md5.digest();
      write.write(hash, 0, hash.length);
      write.close();
    } catch (Exception e) {
      System.out.println(Main.ESC + Main.FG_RED + "Failed to write scores!" + Main.ESC + Main.RESET);
    }
  }

  public String[] getPlayers() {
    HashSet<String> players = new HashSet<String>();

    for (Score score : scoreList) {
      if (!players.contains(score.getPlayer())) {
        players.add(score.getPlayer());
      }
    }

    return players.toArray(new String[0]);
  }

  public Score[] getScores(String player) {
    HashSet<Score> scores = new HashSet<Score>();

    for (Score score : scoreList) {
      if (score.getPlayer().equals(player.toUpperCase())) {
        scores.add(score);
      }
    }

    return scores.toArray(new Score[0]);
  }

  public Score getHighScore() {
    Score highScore = new Score("NUL", 0);
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