import java.util.Scanner;

public class WordSearch {

    private static Scanner console;
    private static char[][] unsolvedBoard;
    private static char[][] solvedBoard;
    private static String words = ""; // words in the word search split up by spaces

    // introduces the program
    private static void intro() {
        System.out.println("\nWelcome to my word search program!\n" +
                "It will allow you to generate a word search\n" +
                "Please select an option:\n" +
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

        for(int wordNum = 0; wordNum < totalWords; wordNum++) {
            System.out.print("Please input a word: ");
            String word = console.next();
            words += word.toUpperCase() + " ";
        }
    }

    // displays a 2d char array, either the solved or unsolved boards
    private static void display(char[][] wordSearch) {
        for (int row = 0; row < wordSearch.length; row++) {
            for (int col = 0; col < wordSearch[0].length; col++) {
                System.out.print(wordSearch[row][col] + "   ");
            }
            System.out.println();
        }
    }

    // gives a random capital letter
    private static char getRandomChar() {
        return (char)(65 + (int)(Math.random() * 26));
    }

    public static void main(String[] args) {
        intro();
        console = new Scanner(System.in);
        boolean playing = true;

        while(playing) {
            String input = console.next();

            switch(input) {
                case "g":
                    wordInput();
                    System.out.println(words);
                    break;
                case "p":
                    break;
                case "s":
                    break;
                case "q":
                    playing = false;
                    break;
            }
        }
        System.out.println("Thanks for playing!");
        console.close();
    }
}