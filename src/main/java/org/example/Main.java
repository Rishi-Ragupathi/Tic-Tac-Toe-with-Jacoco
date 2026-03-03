package org.example;

import java.util.Random;
import java.util.Scanner;

/**
 * Entry point for the Tic-Tac-Toe game.
 */

public class Main {

  public static Scanner sc;
  public static Random random = new Random();

  /**
   * Starts the program and processes user commands.
   */

  public static void main(String[] args) {

    sc = new Scanner(System.in);

    while (true) {
      System.out.print("Input command: ");
      String input = sc.nextLine();
      String[] parts = input.split(" ");

      if (parts[0].equals("exit")) {
        break;
      }

      if (parts.length != 3
              || !parts[0].equals("start")
              || !isValidPlayer(parts[1])
              || !isValidPlayer(parts[2])) {

        System.out.println("Bad parameters!");
        continue;
      }

      playGame(parts[1], parts[2]);
    }
  }

  public static boolean isValidPlayer(String p) {
    return p.equals("user")
            || p.equals("easy")
            || p.equals("medium")
            || p.equals("hard");
  }

  public static char[][] playGame(String playerX, String playerO) {

    char[][] board = new char[3][3];
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        board[i][j] = ' ';
      }
    }

    printBoard(board);

    char current = 'X';

    while (true) {
      String currentPlayer = (current == 'X') ? playerX : playerO;

      switch (currentPlayer) {
        case "user" -> userMove(board, current);
        case "easy" -> {
          System.out.println("Making move level \"easy\"");
          easyMove(board, current);
        }
        case "medium" -> {
          System.out.println("Making move level \"medium\"");
          mediumMove(board, current);
        }
        default -> {
          System.out.println("Making move level \"hard\"");
          hardMove(board, current);
        }
      }


      printBoard(board);

      String state = getGameState(board);
      if (!state.equals("Game not finished")) {
        System.out.println(state);
        break;
      }

      current = (current == 'X') ? 'O' : 'X';
    }
    return board;
  }

  private static void userMove(char[][] board, char symbol) {
    while (true) {
      System.out.print("Enter the coordinates: ");
      String[] parts = sc.nextLine().split(" ");

      if (parts.length != 2
              || !parts[0].matches("\\d+")
              || !parts[1].matches("\\d+")) {
        System.out.println("You should enter numbers!");
        continue;
      }

      int row = Integer.parseInt(parts[0]) - 1;
      int col = Integer.parseInt(parts[1]) - 1;

      if (row < 0 || row > 2 || col < 0 || col > 2) {
        System.out.println("Coordinates should be from 1 to 3!");
        continue;
      }

      if (board[row][col] != ' ') {
        System.out.println("This cell is occupied! Choose another one!");
        continue;
      }

      board[row][col] = symbol;
      break;
    }
  }

  /**
   * Makes a random move for the AI.
   *
   * @param board  the game board
   * @param symbol the symbol to place (X or O)
   */

  public static void easyMove(char[][] board, char symbol) {
    while (true) {
      int r = random.nextInt(3);
      int c = random.nextInt(3);

      if (board[r][c] == ' ') {
        board[r][c] = symbol;
        break;
      }
    }
  }

  static void mediumMove(char[][] board, char symbol) {

    char opp = (symbol == 'X') ? 'O' : 'X';
    if (tryWinorBlock(board, symbol, symbol)) return;

    if (tryWinorBlock(board, opp, symbol)) return;

    easyMove(board, symbol);
  }

  private static void hardMove(char[][] board, char symbol) {
    int bestScore = Integer.MIN_VALUE;
    int bestRow = -1;
    int bestCol = -1;

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (board[i][j] == ' ') {
          board[i][j] = symbol;

          int score = minimax(board, false, symbol);

          board[i][j] = ' ';

          if (score > bestScore) {
            bestScore = score;
            bestRow = i;
            bestCol = j;
          }
        }
      }
    }

    board[bestRow][bestCol] = symbol;
  }

  private static int minimax(char[][] board, boolean isMaximizing, char aiSymbol) {

    char opponent = (aiSymbol == 'X') ? 'O' : 'X';

    if (checkWins(board, aiSymbol)) return 10;
    if (checkWins(board, opponent)) return -10;
    if (getGameState(board).equals("Draw")) return 0;

    int bestScore;
    if (isMaximizing) {
      bestScore = Integer.MIN_VALUE;

      for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
          if (board[i][j] == ' ') {
            board[i][j] = aiSymbol;
            int score = minimax(board, false, aiSymbol);
            board[i][j] = ' ';
            bestScore = Math.max(score, bestScore);
          }
        }
      }

    } else {
      bestScore = Integer.MAX_VALUE;

      for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
          if (board[i][j] == ' ') {
            board[i][j] = opponent;
            int score = minimax(board, true, aiSymbol);
            board[i][j] = ' ';
            bestScore = Math.min(score, bestScore);
          }
        }
      }

    }
    return bestScore;
  }


  static boolean tryWinorBlock(char[][] board, char checksymbol, char placeSymbol) {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (board[i][j] == ' ') {
          board[i][j] = checksymbol;
          if (checkWins(board, checksymbol)) {
            board[i][j] = placeSymbol;
            return true;
          }
          board[i][j] = ' ';
        }
      }
    }
    return false;
  }

  static void printBoard(char[][] board) {
    System.out.println("---------");
    for (int i = 0; i < 3; i++) {
      System.out.print("| ");
      for (int j = 0; j < 3; j++) {
        System.out.print(board[i][j] + " ");
      }
      System.out.println("|");
    }
    System.out.println("---------");
  }

  public static String getGameState(char[][] board) {
    if (checkWins(board, 'X')) return "X wins";
    if (checkWins(board, 'O')) return "O wins";

    for (char[] row : board) {
      for (char c : row) {
        if (c == ' ') return "Game not finished";
      }
    }
    return "Draw";
  }

  public static boolean checkWins(char[][] board, char p) {
    for (int i = 0; i < 3; i++) {
      if (board[i][0] == p && board[i][1] == p && board[i][2] == p) return true;
      if (board[0][i] == p && board[1][i] == p && board[2][i] == p) return true;
    }
    return (board[0][0] == p && board[1][1] == p && board[2][2] == p) ||
            (board[0][2] == p && board[1][1] == p && board[2][0] == p);
  }
}