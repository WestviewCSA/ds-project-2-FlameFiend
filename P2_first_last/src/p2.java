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
	public static void queueSolve() {
	    for (int roomIndex = 0; roomIndex < numRooms; roomIndex++) {
	        Queue<Tile> queue = new LinkedList<>();
	        boolean[][] visited = new boolean[numRows][numCols];
	        int[][] distance = new int[numRows][numCols];
	        
	        // Initialize distances and enqueue starting points ('W')
	        for (int row = 0; row < numRows; row++) {
	            for (int col = 0; col < numCols; col++) {
	                distance[row][col] = -1;
	                if (grid[roomIndex].get(row, col).getType() == 'W') {
	                    queue.add(grid[roomIndex].get(row, col));
	                    visited[row][col] = true;
	                    distance[row][col] = 0;
	                }
	            }
	        }
	        
	        // Perform BFS to calculate distances
	        while (!queue.isEmpty()) {
	            Tile currentTile = queue.poll();
	            int currentRow = currentTile.getRow(), currentCol = currentTile.getCol();
	            
	            for (int direction = 0; direction < 4; direction++) {
	                int nextRow = currentRow + dRow[direction], nextCol = currentCol + dCol[direction];
	                if (nextRow >= 0 && nextCol >= 0 && nextRow < numRows && nextCol < numCols && !visited[nextRow][nextCol] && grid[roomIndex].get(nextRow, nextCol).getType() != '@') {
	                    visited[nextRow][nextCol] = true;
	                    distance[nextRow][nextCol] = distance[currentRow][currentCol] + 1;
	                    queue.add(grid[roomIndex].get(nextRow, nextCol));
	                }
	            }
	        }
	        
	        // Find target tile ('$' or '|')
	        int targetRow = -1, targetCol = -1;
	        for (int row = 0; row < numRows; row++) {
	            for (int col = 0; col < numCols; col++) {
	                if ((grid[roomIndex].get(row, col).getType() == '$' || grid[roomIndex].get(row, col).getType() == '|') && visited[row][col]) {
	                    targetRow = row;
	                    targetCol = col;
	                }
	            }
	        }
	        
	        if (targetRow == -1) {
	            System.out.println("The Wolverine Store is closed.");
	            return;
	        }
	        
	        // Reconstruct shortest path
	        path = new LinkedList<>();
	        while (distance[targetRow][targetCol] != 0) {
	            for (int direction = 0; direction < 4; direction++) {
	                int prevRow = targetRow + dRow[direction], prevCol = targetCol + dCol[direction];
	                if (prevRow >= 0 && prevCol >= 0 && prevRow < numRows && prevCol < numCols && visited[prevRow][prevCol] && distance[prevRow][prevCol] == distance[targetRow][targetCol] - 1) {
	                    if (grid[roomIndex].get(prevRow, prevCol).getType() == '.') {
	                        grid[roomIndex].get(prevRow, prevCol).setType('+');
	                        path.addFirst(grid[roomIndex].get(prevRow, prevCol));
	                    }
	                    targetRow = prevRow;
	                    targetCol = prevCol;
	                    break;
	                }
	            }
	        }
	        
	        grid[roomIndex].print();
	    }
	}
}