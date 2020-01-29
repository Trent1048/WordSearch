import java.util.Scanner;
/*
Trent Bultsma
CS 145
January 28, 2020

Word search generator that allows the user to generate
a new word search by inputting words, display the word
search, and the solution to it.

Some extra cool stuff I have:
  - An enum for storing directions of the words
  - An interface used to help create lambda functions
  - A function that loops through the board in a certain
    way based on the direction and executes an inputted
    function (its at the bottom of this file)
  - try/catch in main() to not break the code when there
    is an invalid input
  - switch/case in mainBoardLoop()
 */

public class WordSearch {

    private static Scanner console;
    private static int boardSize; // the length of the sides of the board
    private static char[][] unsolvedBoard;
    private static char[][] solvedBoard;
    private static String[] words; // array of all words in the word search

    // for describing the direction a word is going
    private enum Direction {
        HORIZONTAL,
        VERTICAL,
        DIAGONAL_RIGHT,
        DIAGONAL_LEFT
    }

    // for the creation of lambda functions
    private interface QuadFunction<A, B, C, D, R> {
        R apply(A a, B b, C c, D d);
    }

    // tells the user their options
    private static void options() {
        System.out.println("\nPlease select an option:\n" +
                "\tGenerate a new board (g)\n" +
                "\tPrint out the word search (p)\n" +
                "\tDisplay the solution (s)\n" +
                "\tQuit the program (q)\n");
    }

    // allows the user to input words which will be added in the search
    private static void wordInput() {
        System.out.print("You will input words to add to the word search\n" +
                "How many do you want to input? ");
        int totalWords = console.nextInt();
        words = new String[totalWords];

        for(int wordNum = 0; wordNum < totalWords; wordNum++) {
            System.out.print("Please input a word: ");
            String word = console.next();
            words[wordNum] = word.toUpperCase();
        }
    }

    // displays a 2d char array, either the solved or unsolved boards
    private static void display(char[][] wordSearch) {
        for (char[] row : wordSearch) {
            for (char letter : row) {
                System.out.print(letter + "  ");
            }
            System.out.println();
        }
    }

    // returns a random capital letter
    private static char getRandomChar() {
        return (char)(65 + (int)(Math.random() * 26));
    }

    public static void main(String[] args) {
        System.out.print("\nWelcome to my word search program!\n" +
                "It will allow you to generate a word search");
        options();
        console = new Scanner(System.in);
        boolean playing = true;

        while(playing) {
            String input = console.next();

            try {
                switch (input) {
                    case "g":
                        wordInput();
                        generate();
                        break;
                    case "p":
                        display(unsolvedBoard);
                        break;
                    case "s":
                        display(solvedBoard);
                        break;
                    case "q":
                        playing = false;
                        break;
                }
            } catch (Exception e) {
                System.out.println("INVALID INPUT");
            }
            options();
        }
        System.out.println("Thanks for playing!");
        console.close();
    }

    // generates the solved and unsolved boards
    private static void generate() {

        // find the longest word
        int longestWordLen = 0;

        for(String word : words) {
            int wordLen = word.length();
            if(wordLen > longestWordLen) {
                longestWordLen = wordLen;
            }
        }

        // make the board a square with the sides a little more than the longest word
        boardSize = longestWordLen + 3;
        solvedBoard = new char[boardSize][boardSize];
        unsolvedBoard = new char[boardSize][boardSize];

        // fill solved board with placeholder letters
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                solvedBoard[row][col] = '_';
            }
        }

        // puts all the words on the board
        for(String word : words) {
            // loops through a hundred times to try and place a word on the board
            for(int i = 0; i < 100; i++) {
                // set up randomly if it is backwards and its orientation
                Direction dir = Direction.values()[(int) (Math.random() * 4)];
                boolean backwards = Math.random() < 0.5;

                // reverses the word if backwards
                if (backwards) {
                    String reversedWord = "";
                    for (int letter = word.length() - 1; letter >= 0; letter--) {
                        reversedWord += word.charAt(letter);
                    }
                    word = reversedWord;
                }

                // random numbers for calculation of location on the board
                // defined outside of the function that uses them so the
                // same values are used for both finding a space and putting
                // in the letters
                double rand1 = Math.random();
                double rand2 = Math.random();

                // goes through the main loop to make sure the word will fit
                if(mainBoardLoop(checkLetter, word, dir, rand1, rand2)) {
                    // if it fits, goes through the loop of directions and adds the word to the solved board
                    mainBoardLoop(addLetter, word, dir, rand1, rand2);
                    break; // stops looping when the word is set
                }
            }
        }

        // copy the solved board to the unsolved one so they aren't references
        for (int row = 0; row < boardSize; row++) {
            System.arraycopy(solvedBoard[row], 0, unsolvedBoard[row], 0, boardSize);
        }

        // replace the placeholder letters with normal ones for unsolved board
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if(unsolvedBoard[row][col] == '_'){
                    unsolvedBoard[row][col] = getRandomChar();
                }
            }
        }
    }

    // The Inner Workings of generate()

    // lambda function to add a letter to the solved board in a given location
    private static QuadFunction<Integer, Integer, String, Integer, Boolean> addLetter = (row, col, word, letter) -> {
        solvedBoard[row][col] = word.charAt(letter);
        // this isn't important, it just needs to return a boolean so either
        // addLetter or checkLetter can be inputted to the mainBoardLoop
        return false;
    };

    // lambda function that returns if the spot on the solved board is available to be occupied by
    // a letter indicated by its index in a word
    private static QuadFunction<Integer, Integer, String, Integer, Boolean> checkLetter = (row, col, word, letter) ->
            solvedBoard[row][col] == word.charAt(letter) || solvedBoard[row][col] == '_';

    // goes through the possible directions, loops through the array locations, and applies a function
    // if the function is addLetter, it will add a word in the spot defined by dir, random1, and random2
    // if the function is checkLetter, it sees if the given word fits in a certain spot
    private static boolean mainBoardLoop(QuadFunction<Integer, Integer, String, Integer, Boolean> func,
                                        String word, Direction dir, double random1, double random2) {
        /*
        func: the function that will be run in every iteration of a possible word's location
            (either addLetter or checkLetter)
        word: the word being compared or added to the board
        dir: the direction the word goes
        random1, random2: would be an inputted Math.random() so it will run the same every time
            and not recalculate the randomness when it is run again with the same inputs
         */

        int letter = 0;
        // result is the collective returns from every call to func(), if it returns false once,
        // this function will return false, otherwise true.
        boolean result = true;
        int startPos = (int)(random1 * (boardSize - word.length()));

        switch (dir) {
            case VERTICAL: // goes through vertically

                int col = (int)(random2 * boardSize);
                for (int row = startPos; row < (startPos + word.length()); row++, letter++) {
                    // the column stays the same but row changes, going vertically
                    if(!func.apply(row, col, word, letter)) {
                        result = false;
                    }
                }
                break;

            case HORIZONTAL: // goes through horizontally

                int row = (int)(random2 * boardSize);
                for (col = startPos; col < (startPos + word.length()); col++, letter++) {
                    // the row stays the same but the column changes, going horizontally
                    if (!func.apply(row, col, word, letter)) {
                        result = false;
                    }
                }
                break;

            case DIAGONAL_RIGHT: // goes through diagonally to the right

                row = (int)(random1 * (boardSize - word.length()));
                col = (int)(random2 * (boardSize - word.length()));
                for(letter = 0; letter < word.length(); letter++, row++, col++) {
                    // both row and column change positively so it goes diagonally to the right
                    if(!func.apply(row, col, word, letter)) {
                        result = false;
                    }
                }

                break;

            default: // goes diagonally to the left

                row = (int)(word.length() + random1 * (boardSize - word.length()));
                col = (int)(random2 * (boardSize - word.length()));
                for(letter = 0; letter < word.length(); letter++, row--, col++) {
                    // column goes up but row goes down so it goes diagonally to the left
                    if(!func.apply(row, col, word, letter)) {
                        result = false;
                    }
                }
                break;
        }
        return result;
    }
}