import java.util.Scanner;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.Random;
import java.util.ArrayList;

class Main {
  private static Scanner scan;
  private static BridgeGame game;
  private static boolean play;
  private static ScoreRecord record;
  private static String player;
  private static String playerReg;
  private static Random rng;
  private static Score score;
  private static ArrayList<String> replay;

  public static final String ESC = "\u001B[";
  public static final String RESET = "0m";
  public static final String FG_RED = "31m";
  public static final String FG_BLACK = "30m";
  public static final String BG_YELLOW = "43m";
  public static final String BG_BLUE = "44m";
  public static final String FG_WHITE = "37m";
  public static final String BOLD = "1m";
  
  public static void main(String[] args) {
    scan = new Scanner(System.in);
    game = new BridgeGame();
    rng = new Random();
    play = true;
    record = new ScoreRecord("scores.txt");
    playerReg = "[A-Z]{3}$";
    
    record.readScores();

    displayHelp("");

    while (play) {
      game.init();

      replay = new ArrayList<String>();
      
      player = "\u0000";
      while (!Pattern.matches(playerReg, player)) {
        if (!player.equals("\u0000")) {
          if (player.equalsIgnoreCase("help")) {
            displayHelp("name");
          } else {
            System.out.println(ESC + FG_RED + "Invalid player name! Type 'help' for help." + ESC + RESET);
          }
        }
        System.out.println("Player name?");
        player = scan.nextLine().toUpperCase();
      }

      while (!game.hasFallen()) {
        replay.add(game.toString());
        int move = getMove();
        if (move != 0) {
          game.takeTurn();
        }
      }

      replay.add(game.toString());

      int turns = game.getTurns();
      
      score = new Score(player, turns);

      record.addScore(score);

      System.out.println("You fell off the bridge, Sploosh!");
      System.out.println("You scored " + turns + "!");

      if (askYesOrNo("Show replay?", "replay")) {
        displayReplay();
      }

      displayScoreBoard();
      
      play = askYesOrNo("Play again?", "again");
    }
    
    record.writeScores();
  }

  private static boolean askYesOrNo(String prompt, String help) {
    String answer = "\u0000";
    
    while (!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no")) {
      if (!answer.equals("\u0000")) {
        if (answer.equalsIgnoreCase("help")) {
          displayHelp(help);
        } else {
          System.out.println(ESC + FG_RED + "Incorrect answer! Type 'help' for help." + ESC + RESET);
        }
      }
      System.out.println(prompt);
      answer = scan.nextLine();
    }
    return answer.equalsIgnoreCase("yes");
  }

  private static int getMove() {
    int move = Integer.MAX_VALUE;
    String input = "\u0000";
    while (!game.isMoveValid(move)) {
      if (move != Integer.MAX_VALUE) {
        System.out.println(ESC + FG_RED + "Invalid move! Type 'help' for help." + ESC + RESET);
      }
      
      System.out.println("What is your move?");
      
      input = scan.nextLine();

      if (input.equalsIgnoreCase("help")) {
        displayHelp("move");
        return 0;
      } else {
        try {
          move = Integer.parseInt(input);
        } catch (Exception e) {
          System.out.println(ESC + FG_RED + "Not a proper number! Type 'help' for help." + ESC + RESET);
        }
      }
    }

    if (rng.nextBoolean()) {
      move = -move;
    }

    return move;
  }

  private static void displayScoreBoard() {
    Score[] board = record.getScoreBoard();
    Score[] topTen;
    
    if (board.length >= 5) {
      topTen = new Score[5];
    } else {
      topTen = new Score[board.length];
    }

    int place = Arrays.binarySearch(board, score);

    for (int i = 0; i < topTen.length; i++) {
      topTen[i] = board[i];
    }

    System.out.println("Score Board:");
      
    for (int i = 0; i < topTen.length; i++) {
      if (topTen[i].getPlayer().equals(player)) {
        System.out.printf("%s%-5s %s\n", ESC + BG_YELLOW + ESC + FG_BLACK + ESC + BOLD, "[" + (i + 1) + "]", topTen[i].toString() + ESC + RESET);
      } else {
        System.out.printf("%-5s %s\n", "[" + (i + 1) + "]", topTen[i].toString());
      }
    }

    if (place >= topTen.length) {
      System.out.println("...");
      System.out.printf("%s%-5s %s\n", ESC + BG_YELLOW + ESC + FG_BLACK + ESC + BOLD, "[" + (place + 1) + "]", score.toString() + ESC + RESET);
    }
  }

  private static void displayReplay() {
    System.out.printf("%6s\n", "Replay:");
    for (String s : replay) {
      System.out.printf("%5s\n", ESC + BG_BLUE + ESC + FG_BLACK + s + ESC + RESET);
    }
  }

  private static void decryptReplay() {
    
  }

  private static void displayHelp(String context) {
    switch (context) {
      case "name":
        System.out.print(ESC + BOLD);
        System.out.println("Your player name will be used to store your scores.");
        System.out.println("The name you choose can only be three letters in length.");
        System.out.println("The chosen name will automatically be uppercase.");
        System.out.print(ESC + RESET);
        break;
      case "move":
        System.out.print(ESC + BOLD);
        System.out.println("Your move will be the distance you travel on the bridge.");
        System.out.println("Your move must be between one and three.");
        System.out.println("The game will randomly choose which direction you move.");
        System.out.println("You can't choose the same move three times in a row.");
        System.out.print(ESC + RESET);
        break;
      case "again":
        System.out.print(ESC + BOLD);
        System.out.println("Answer 'yes' or 'no'.");
        System.out.println("If you answer yes, the game will completely reset and ask for a player name again.");
        System.out.println("If you answer no, the game will record all the scores and exit.");
        System.out.print(ESC + RESET);
        break;
      case "replay":
        System.out.print(ESC + BOLD);
        System.out.println("Answer 'yes' or 'no'.");
        System.out.println("If you answer yes, a visual replay of your moves will be shown.");
        System.out.println("If you answer no, you will not be shown your replay.");
        System.out.print(ESC + RESET);
        break;
      default:
        System.out.print(ESC + BOLD);
        System.out.println("Blindfold Bridge");
        System.out.println("You will be placed randomly on a nine tile wide bridge that you cannot see.");
        System.out.println("You choose how far you move but the game chooses which direction.");
        System.out.println("This is mostly a game of chance, so have fun.");
        System.out.println("Type 'help' at any prompt for help in the current context.");
        System.out.print(ESC + RESET);
    }
  }
}