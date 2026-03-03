package example;

import org.example.Main;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;


class MainTest {
  static PrintStream originalOut;
  private char[][] emptyBoard() {
    char[][] board = new char[3][3];
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        board[i][j] = ' ';
      }
    }
    return board;
  }

  @BeforeAll
  static void startApp(){
    originalOut = System.out;
    System.out.println("Starting tests .....");
  }

  @DisplayName("Placing X position")
  @Test
  void placingXTest() {
    char[][] board = emptyBoard();

    Main.easyMove(board, 'X');

    boolean placed = false;
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (board[i][j] == 'X') {
          placed = true;
          break;
        }
      }
    }
    assertTrue(placed);
  }

  @DisplayName("Valid Player")
  @Test
  void isValidPlayer() {
    assertTrue(Main.isValidPlayer("user"));
    assertTrue(Main.isValidPlayer("easy"));
    assertTrue(Main.isValidPlayer("medium"));
    assertTrue(Main.isValidPlayer("hard"));
    assertFalse(Main.isValidPlayer("invalid"));
  }

  @DisplayName("Exit Tic-Tac-Toe")
  @Test
  void exitMoveTest() {
    String input = "exit\n";
    System.setIn(new ByteArrayInputStream(input.getBytes()));

    ByteArrayOutputStream output = new ByteArrayOutputStream();
    System.setOut(new PrintStream(output));

    Main.main(new String[]{});

    assertTrue(output.toString().contains("Input command:"));
  }

  @AfterAll
  static void endingApp(){
    System.setOut(originalOut);
    System.out.println("Ending tests.....");
  }

}
