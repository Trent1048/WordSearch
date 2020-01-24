public class WordSearch {

    private static char[][] unsolvedBoard;
    private static char[][] solvedBoard;
    private static String words; // words in the word search split up by spaces

    public static void main(String[] args) {
        intro();
        unsolvedBoard = new char[][] {{'a','b','c'},{'d','e','f'},{'g','h','i'}};
        display(unsolvedBoard);
        for(int i = 0; i < 20; i++){
            System.out.print(getRandomChar());
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