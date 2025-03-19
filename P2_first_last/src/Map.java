import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Map {
    private Tile[][] map;
    private int numRows, numCols, numRooms;

    public Map(String filename) {
        readMap(filename);
    }

    private void readMap(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            numRows = scanner.nextInt();
            numCols = scanner.nextInt();
            numRooms = scanner.nextInt();
            scanner.nextLine(); // Move to next line

            map = new Tile[numRows][numCols];

            for (int row = 0; row < numRows; row++) {
                String line = scanner.nextLine();
                for (int col = 0; col < Math.min(numCols, line.length()); col++) {
                    map[row][col] = new Tile(row, col, line.charAt(col));
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        }
    }

    public Tile[][] getMap() {
        return map;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public int getNumRooms() {
        return numRooms;
    }
}