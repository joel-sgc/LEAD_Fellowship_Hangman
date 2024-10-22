import java.util.ArrayList;
import java.util.Scanner;

public class Main {
  static final String[] WORDS = {
    "adventure", "balloon", "candle", "dolphin", "eclipse", "fossil", "galaxy", "horizon", "island", "jungle", "kettle", "labyrinth", "mountain", "nebula", "ocean", "pyramid", "quicksand", "rainbow", "satellite", "telescope", "universe", "volcano", "whirlpool", "xylophone", "yacht", "zeppelin", "astronaut", "butterfly", "compass", "desert", "emerald", "feather", "guitar", "hurricane", "illusion", "journey", "kaleidoscope", "lantern", "mirror", "nightfall", "oasis", "painting", "quartz", "riddle", "silhouette", "treasure", "umbrella", "vortex", "windmill", "xenon", "yellow", "zipper", "blizzard", "castle", "dragon", "elephant", "forest", "glacier", "honeycomb", "icicle", "jigsaw", "knight", "lightning", "meadow", "novel", "octopus", "parachute", "quiver", "river", "snowflake", "thunder", "umbrella", "vulture", "whistle", "xenophobia", "yawn", "zigzag", "alchemist", "boulder", "cyclone", "dungeon", "enigma", "firefly", "ghost", "horizon", "ivy", "journey", "keystone", "labyrinth", "moonlight"
  };

  static final String WELCOME_TEXT = 
    "██╗  ██╗ █████╗ ███╗   ██╗ ██████╗     ███╗   ███╗ █████╗ ███╗   ██╗\n" +
    "██║  ██║██╔══██╗████╗  ██║██╔════╝     ████╗ ████║██╔══██╗████╗  ██║\n" +
    "███████║███████║██╔██╗ ██║██║  ███╗    ██╔████╔██║███████║██╔██╗ ██║\n" +
    "██╔══██║██╔══██║██║╚██╗██║██║   ██║    ██║╚██╔╝██║██╔══██║██║╚██╗██║\n" +
    "██║  ██║██║  ██║██║ ╚████║╚██████╔╝    ██║ ╚═╝ ██║██║  ██║██║ ╚████║\n" +
    "╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝ ╚═════╝     ╚═╝     ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝";

  static final String[][] HANGMAN_STAGES = {
    {
      "     ",
      "     ",
      "     ",
      "     ",
      "     ",
      "     ",
    },
    {
      "  O  ",
      "  |  ",
      "  |  ",
      "  |  ",
      "     ",
      "     ",
    },
    {
      "  O  ",
      " /|\\ ",
      "  |  ",
      "  |  ",
      "     ",
      "     ",
    },
    {
      "  O  ",
      " /|\\ ",
      "  |  ",
      " / \\ ",
      "     ",
      "     ",
    }
  };
  
  static String notification;
  static int attemptsLeft = 3;
  static int wordIndex;
  static String wordToGuess;
  static String word = "";
  static ArrayList<Character> guessedCharacters = new ArrayList<>();

  static final Boolean DEBUG_SHOW_ANSWER = true;

  public static void main(String[] args) {
    boolean playAgain = true;
    Scanner input = new Scanner(System.in);

    System.out.println(WELCOME_TEXT);
    System.out.println("\nWelcome to the Word Guessing Game! \n");
    System.out.println("You will be given a random word to guess. You can guess one letter at a time.");
    System.out.println("If you guess the word correctly, you win! You'll get 3 faild attempts, after that, you lose :(\n");
    System.out.print("Press Enter to play! "); input.nextLine();

    while (playAgain) {
      // Resetting all variables
      // Clearing error/information notifications
      // Resetting failed attempts count
      // Selecting a random word to guess
      
      notification = "";
      guessedCharacters.clear();
      attemptsLeft = 3;
      wordIndex = (int) (Math.floor(Math.random() * WORDS.length + 1) - 1);
      wordToGuess = WORDS[wordIndex];

      // Generating 
      word = wordToGuess.replaceAll("([a-z])", "_ ");

      while (attemptsLeft > 0) {
        // Clear the console
        System.out.print("\033\143");

        if (DEBUG_SHOW_ANSWER) {
          System.out.println("Debug answer: " + wordToGuess);
        }
        
        // First we display the hangman state
        if (notification.trim().length() > 0) {
          System.out.println("|============ " + notification + " ============|");
          notification = "";
        }

        System.out.println("Failed attempts left: " + attemptsLeft);
        System.out.println("Guessed Letters: " + guessedCharacters.toString().replaceAll("\\[|\\]", ""));

        for (int i = 0; i < 6; i++) {
          System.out.println(HANGMAN_STAGES[3 - attemptsLeft][i]);
        }

        System.out.println("Word: " + word);

        // Main Game Loop
        System.out.print("Guess a letter: ");
        char guessedLetter = (input.nextLine().trim().toLowerCase() + " ").charAt(0);

        if (guessedCharacters.contains(guessedLetter)) {
          notification = "You already guessed that letter. Try again.";
          continue;
        }

        if (guessedLetter < 'a' || guessedLetter > 'z' || guessedLetter == ' ') {
          notification = "Invalid input. Please guess a letter between a-z.";
          continue;
        }

        guessedCharacters.add(guessedLetter);
        // Check if the guessed letter is in the word
        if (wordToGuess.indexOf(guessedLetter) == -1) {
          attemptsLeft--;

          if (attemptsLeft == 0) {
            printEndText(false);
            break;
          }
        }

        // Check if the player has guessed the word
        word = "";
        for (char letter : wordToGuess.toCharArray()) {
          if (guessedCharacters.contains(letter)) {
            word += (letter + " ");
          } else {
            word += "_ ";
          }
        }

        if (word.replaceAll(" ", "").equals(wordToGuess)) {
          printEndText(true);
          break;
        }
      }

      // Play Again Prompt
      System.out.print("Would you like to play again? (y/n): ");
      playAgain = (input.nextLine().trim().toLowerCase().equals("y"));
    }

    input.close();
  }

  static void printEndText(Boolean win) {
    System.out.print("\033\143");

    System.out.println("Failed attempts left: " + attemptsLeft);
    System.out.println("Guessed Letters: " + guessedCharacters.toString().replaceAll("\\[|\\]", ""));

    for (int i = 0; i < 6; i++) {
      System.out.println(HANGMAN_STAGES[3 - attemptsLeft][i]);
    }

    word = "";
    for (char letter : wordToGuess.toCharArray()) {
      if (guessedCharacters.contains(letter)) {
        word += (letter + " ");
      } else {
        word += "_ ";
      }
    }

    System.out.println("Word: " + word);
    if (win) {
      System.out.println("Congratulations! You've guessed the word: " + wordToGuess);
    } else {
      System.out.println("Sorry, you didn't make it. The word was: " + wordToGuess);
    }

    System.out.println();
  }
}