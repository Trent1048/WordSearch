import java.util.Scanner;

public class WordSearch {

    private static char[][] unsolvedBoard;
    private static char[][] solvedBoard;
    private static String words; // words in the word search split up by spaces

    public static void main(String[] args) {
        intro();
        Scanner console = new Scanner(System.in);
        boolean playing = true;

        while(playing) {
            if(console.hasNext()){
                String input = console.next();

                switch(input) {
                    case "g":
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
        }
        System.out.println("Thanks for playing!");
        console.close();
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

    private static void intro() {
        System.out.println("\nWelcome to my word search program!\n" +
                "It will allow you to generate a word search\n" +
                "Please select an option:\n" +
                "\tGenerate a new board (g)\n" +
                "\tPrint out the word search (p)\n" +
                "\tDisplay the solution (s)\n" +
                "\tQuit the program (q)\n");
    }

    private static char getRandomChar() {
        return (char)(97 + (int)(Math.random() * 26));
    }
}