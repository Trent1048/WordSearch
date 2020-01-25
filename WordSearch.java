import java.util.Arrays;
import java.util.Scanner;

public class WordSearch {

    private static Scanner console;
    private static int boardSize; // the length of the sides of the board
    private static char[][] unsolvedBoard;
    private static char[][] solvedBoard;
    private static String[] words; // array of all words in the word search

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
            addWord(word);
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

    // add a word to the solved board
    private static void addWord(String word) {

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