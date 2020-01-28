import java.util.Scanner;

public class WordSearch {

    private static Scanner console;
    private static int boardSize; // the length of the sides of the board
    private static char[][] unsolvedBoard;
    private static char[][] solvedBoard;
    private static String[] words; // array of all words in the word search

    private enum Direction {
        HORIZONTAL,
        VERTICAL,
        DIAGONAL
    }

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

        // make the board a square with the sides equal to the longest word's length
        boardSize = longestWordLen;
        solvedBoard = new char[boardSize][boardSize];
        unsolvedBoard = new char[boardSize][boardSize];

        // fill solved board with placeholder letters
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                solvedBoard[row][col] = '_';
            }
        }

        for(String word : words) {
            // set up randomly if it is backwards and its orientation
            Direction dir = Direction.values()[(int)(Math.random() * 3)];
            boolean backwards = Math.random() < 0.5;

            // reverses the word if backwards
            if(backwards) {
                String reversedWord = "";
                for(int letter = word.length() - 1; letter >= 0; letter--) {
                    reversedWord += word.charAt(letter);
                }
                word = reversedWord;
            }

            // goes through the loop of directions and adds the word to the solved board
            mainBoardLoop(addLetter, word, dir, Math.random(), Math.random());
        }

        // copy the solved board to the unsolved one so they aren't references
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                unsolvedBoard[row][col] = solvedBoard[row][col];
            }
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

    // lambda function to add a letter to the solved board in a given location
    private static QuadFunction<Integer, Integer, String, Integer, Boolean> addLetter = (row, col, word, letter) -> {
        solvedBoard[row][col] = word.charAt(letter);
        return false;
    };

    // goes through the possible directions, loops through the array locations, and applies a function
    private static void mainBoardLoop(QuadFunction<Integer, Integer, String, Integer, Boolean> func,
                                        String word, Direction dir, double random1, double random2) {
        /*
        func: the function that will be run in ever iteration of a possible word's location
        word: the word being compared or inputted or used for whatever
        dir: the direction the word goes
        random1, random2: would be an inputted Math.random() so it will run the same every time
            and not recalculate the randomness
         */

        int letter = 0;
        int startPos = (int)(random1 * (boardSize - word.length()));

        if(dir == Direction.VERTICAL) { // sets a word vertically
            int col = (int)(random2 * boardSize);
            for (int row = startPos; row < (startPos + word.length()); row++, letter++) {
                // the column stays the same but row changes, making the word vertical
                func.apply(row, col, word, letter);
            }
        } else if(dir == Direction.HORIZONTAL) { // sets a word horizontally
            int row = (int)(random2 * boardSize);
            for (int col = startPos; col < (startPos + word.length()); col++, letter++) {
                // the row stays the same but the column changes, making the word horizontal
                func.apply(row, col, word, letter);
            }
        } else { // sets a word diagonally
            int row = (int)(random1 * (boardSize - word.length()));
            int col = (int)(random2 * (boardSize - word.length()));
            for(letter = 0; letter < word.length(); letter++, row++, col++) {
                // both row and column change so it goes diagonally to the right
                func.apply(row, col, word, letter);
            }
        }
    }

    // displays a 2d char array, either the solved or unsolved boards
    private static void display(char[][] wordSearch) {
        for (int row = 0; row < wordSearch.length; row++) {
            for (int col = 0; col < wordSearch[0].length; col++) {
                System.out.print(wordSearch[row][col] + "  ");
            }
            System.out.println();
        }
    }

    // gives a random capital letter
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
}