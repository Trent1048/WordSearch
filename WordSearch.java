import java.util.ArrayList;

public class WordSearch {

    private static char[][] unsolvedSearch;
    private static char[][] solvedSearch;
    private static ArrayList<String> words; // words in the word search

    public static void main(String[] args) {
        unsolvedSearch = new char[][] {{'a','b','c'},{'d','e','f'},{'g','h','i'}};
        display(unsolvedSearch);
    }

    private static void display(char[][] wordSearch) {
        for (int row = 0; row < wordSearch.length; row++) {
            for (int col = 0; col < wordSearch[0].length; col++) {
                System.out.print(wordSearch[row][col] + " ");
            }
            System.out.println();
        }
    }
}