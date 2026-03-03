package example;

import org.example.Main;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayGameTest {

  @DisplayName("User VS User")
  @Test
  void startUserVsUser() {

    String input =
            """
                    1 1
                    1 2
                    2 2
                    3 1
                    3 3
                    """;

    Main.sc = new Scanner(input);

    ByteArrayOutputStream output = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(output));

    try {
      Main.playGame("user", "user");
    } finally {
      System.setOut(originalOut);
    }

    String result = output.toString();

    assertTrue(result.contains("X wins"));
  }

  @DisplayName("User VS Easy")
  @Test
  void startUserVsEasy() {
    String input = """
            1 1
            1 2
            1 3
            2 1
            2 2
            2 3
            3 1
            3 2
            3 3
            """;
    ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outputStream));

    Main.sc = new Scanner(System.in);
    Main.random = new Random();

    char[][] finalBoard = Main.playGame("user", "easy");

    System.setOut(originalOut);

    if (Main.checkWins(finalBoard, 'X')) {
      assertEquals("X wins", Main.getGameState(finalBoard));
    } else if (Main.checkWins(finalBoard, 'O')) {
      assertEquals("O wins", Main.getGameState(finalBoard));
    } else {
      assertEquals("Draw", Main.getGameState(finalBoard));
    }
  }

  @DisplayName("User VS Hard")
  @Test
  void startUserVsHard() {
    String input = """
            1 1
            1 2
            1 3
            2 1
            2 2
            2 3
            3 1
            3 2
            3 3
            """;
    ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outputStream));

    Main.sc = new Scanner(System.in);
    Main.random = new Random();

    char[][] finalBoard = Main.playGame("user", "hard");

    System.setOut(originalOut);

    if (Main.checkWins(finalBoard, 'X')) {
      assertEquals("X wins", Main.getGameState(finalBoard));
    } else if (Main.checkWins(finalBoard, 'O')) {
      assertEquals("O wins", Main.getGameState(finalBoard));
    } else {
      assertEquals("Draw", Main.getGameState(finalBoard));
    }
  }

  @DisplayName("Easy vs Hard")
  @Test
  void startEasyVsHard() {
    Main.random = new Random();

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outputStream));

    char[][] finalBoard = Main.playGame("easy", "hard");

    System.setOut(originalOut);

    if (Main.checkWins(finalBoard, 'X')) {
      assertEquals("X wins", Main.getGameState(finalBoard));
    } else if (Main.checkWins(finalBoard, 'O')) {
      assertEquals("O wins", Main.getGameState(finalBoard));
    } else {
      assertEquals("Draw", Main.getGameState(finalBoard));
    }
  }

  @DisplayName("Medium vs Easy")
  @Test
  void startMediumVsEasy() {
    Main.random = new Random();

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outputStream));

    char[][] finalBoard = Main.playGame("medium", "easy");

    System.setOut(originalOut);

    if (Main.checkWins(finalBoard, 'X')) {
      assertEquals("X wins", Main.getGameState(finalBoard));
    } else if (Main.checkWins(finalBoard, 'O')) {
      assertEquals("O wins", Main.getGameState(finalBoard));
    } else {
      assertEquals("Draw", Main.getGameState(finalBoard));
    }
  }



}
