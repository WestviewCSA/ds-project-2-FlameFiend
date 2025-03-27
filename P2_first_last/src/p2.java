import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
public class p2 {
	static int[] dRow = {0, 1, 0, -1};
	static int[] dCol = {1, 0, -1, 0};
	static Map[] grid;
	static LinkedList<Tile> path;
	static int numRows, numCols, numRooms;
	public static void main(String[] args) {
        readMap("test1");
	}
	public static void readMap(String filename) {
		try {
			File file = new File(filename);
			Scanner scanner = new Scanner(file);
			numRows = scanner.nextInt();
			numCols = scanner.nextInt();
			numRooms = scanner.nextInt();
			grid = new Map[numRooms];
			int rowIndex = 0;
			for (int room = 0; room < numRooms; room++) {
				grid[room] = new Map(numRows, numCols);
				while (scanner.hasNextLine() && rowIndex < numRows) {
					String row = scanner.nextLine();
					if (row.length() > 0) {
						for (int i = 0; i < numCols && i < row.length(); i++) {
							char el = row.charAt(i);
							Tile tile = new Tile(rowIndex, i, el);
							grid[room].set(rowIndex, i, tile);
						}
						rowIndex++;
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
	}
}