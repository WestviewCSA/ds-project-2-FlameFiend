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
		public static void main(String[] args) {
		    if (args.length == 0) {
		        System.out.println("Usage: java p2 <filename>");
		        return;
		    }

		    String filename = args[args.length - 1]; // get filename from command-line argument
		    System.out.println("Testing with map file: " + filename);
		    readMap(filename);

		    for (int i = 1; i <= 5; i++) {
		        filename = "test" + i + ".txt";
		        System.out.println("Testing with map file: " + filename);
		        readMap(filename);

		        // Measure execution time for queue-based solution
		        double startTime = System.nanoTime();
		        queueSolve();
		        double endTime = System.nanoTime();
		        System.out.printf("Queue-based Solution Time: %.6f ms%n", (endTime - startTime) / 1_000_000.0);

		        // Measure execution time for stack-based solution
		        startTime = System.nanoTime();
		        stackSolve();
		        endTime = System.nanoTime();
		        System.out.printf("Stack-based Solution Time: %.6f ms%n", (endTime - startTime) / 1_000_000.0);

		        // Measure execution time for optimal solution
		        startTime = System.nanoTime();
		        optimalSolve();
		        endTime = System.nanoTime();
		        System.out.printf("Optimal Solution Time: %.6f ms%n", (endTime - startTime) / 1_000_000.0);

		        System.out.println("\n--------------------------------------------\n");
		    }
		}
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
	        Tile[][] parent = new Tile[numRows][numCols];  //track the path
	        
	        //enqueue starting point
	        for (int row = 0; row < numRows; row++) {
	            for (int col = 0; col < numCols; col++) {
	                if (grid[roomIndex].get(row, col).getType() == 'W') {
	                    queue.add(grid[roomIndex].get(row, col));
	                    visited[row][col] = true;
	                    parent[row][col] = null; //no parent for start
	                }
	            }
	        }

	        //find % or |
	        while (!queue.isEmpty()) {
	        	if (!queue.isEmpty()) {
	        	    Tile currentTile = queue.remove();
	        	}
	            int currentRow = currentTile.getRow();
	            int currentCol = currentTile.getCol();

	            //check target reached
	            if (grid[roomIndex].get(currentRow, currentCol).getType() == '$' || 
	                grid[roomIndex].get(currentRow, currentCol).getType() == '|') {
	                
	                //reconstruct path
	                LinkedList<Tile> path = new LinkedList<>();
	                Tile targetTile = currentTile;
	                
	                while (targetTile != null) {
	                    path.addFirst(targetTile); //reverse list
	                    targetTile = parent[targetTile.getRow()][targetTile.getCol()];
	                }
	                
	                //mark the path
	                for (Tile t : path) {
	                    if (t.getType() == '.') {
	                        t.setType('+'); 
	                    }
	                }
	                
	                System.out.println("Path found:");
	                grid[roomIndex].print();  //Print the grid with the path marked
	                return;
	            }

	            //4 directions
	            for (int direction = 0; direction < 4; direction++) {
	                int nextRow = currentRow + dRow[direction];
	                int nextCol = currentCol + dCol[direction];

	                if (nextRow >= 0 && nextCol >= 0 && nextRow < numRows && nextCol < numCols &&
	                    !visited[nextRow][nextCol] && grid[roomIndex].get(nextRow, nextCol).getType() != '@') {
	                    visited[nextRow][nextCol] = true;
	                    parent[nextRow][nextCol] = currentTile;  //set the parent
	                    queue.add(grid[roomIndex].get(nextRow, nextCol));
	                }
	            }
	        }

	        //if the target was not found
	        System.out.println("Target not found.");
	    }
	}
	public static void stackSolve() {
	    for (int roomIndex = 0; roomIndex < numRooms; roomIndex++) {
	        Stack<Tile> stack = new Stack<>();
	        boolean[][] visited = new boolean[numRows][numCols];
	        int[][] distance = new int[numRows][numCols];
	        
	        for (int row = 0; row < numRows; row++) {
	            for (int col = 0; col < numCols; col++) {
	                distance[row][col] = -1;
	                if (grid[roomIndex].get(row, col).getType() == 'W') {
	                    stack.push(grid[roomIndex].get(row, col));
	                    visited[row][col] = true;
	                    distance[row][col] = 0;
	                }
	            }
	        }
	        
	        while (!stack.isEmpty()) {
	            Tile currentTile = stack.pop();
	            int currentRow = currentTile.getRow(), currentCol = currentTile.getCol();
	            
	            for (int direction = 0; direction < 4; direction++) {
	                int nextRow = currentRow + dRow[direction], nextCol = currentCol + dCol[direction];
	                if (nextRow >= 0 && nextCol >= 0 && nextRow < numRows && nextCol < numCols && !visited[nextRow][nextCol] && grid[roomIndex].get(nextRow, nextCol).getType() != '@') {
	                    visited[nextRow][nextCol] = true;
	                    distance[nextRow][nextCol] = distance[currentRow][currentCol] + 1;
	                    stack.push(grid[roomIndex].get(nextRow, nextCol));
	                }
	            }
	        }
	        
	        grid[roomIndex].print();
	    }
	}
	public static void optimalSolve() {
	    Queue<int[]> queue = new LinkedList<>();
	    boolean[][] visited = new boolean[numRows][numCols];
	    int[][] parentRow = new int[numRows][numCols];
	    int[][] parentCol = new int[numRows][numCols]; 

	    // Find the starting point 'W' and enqueue it
	    for (int roomIndex = 0; roomIndex < numRooms; roomIndex++) {
	        for (int row = 0; row < numRows; row++) {
	            for (int col = 0; col < numCols; col++) {
	                if (grid[roomIndex].get(row, col).getType() == 'W') {
	                    queue.add(new int[]{row, col});
	                    visited[row][col] = true;
	                    parentRow[row][col] = -1; // start no parent
	                    parentCol[row][col] = -1; // start no parent
	                }
	            }
	        }
	    }
	    
	    while (!queue.isEmpty()) {
	    	if (!queue.isEmpty()) {
	    	    int[] current = queue.remove();
	    	}
	        int currentRow = current[0];
	        int currentCol = current[1];

	        // check if $ is reached
	        if (grid[roomIndex].get(currentRow, currentCol).getType() == '$' || 
	            grid[roomIndex].get(currentRow, currentCol).getType() == '|') {
	            //remake path
	            LinkedList<int[]> path = new LinkedList<>();
	            int targetRow = currentRow;
	            int targetCol = currentCol;

	            while (targetRow != -1 && targetCol != -1) {
	                path.addFirst(new int[]{targetRow, targetCol});
	                int tempRow = parentRow[targetRow][targetCol];
	                int tempCol = parentCol[targetRow][targetCol];
	                targetRow = tempRow;
	                targetCol = tempCol;
	            }
	            for (int[] pos : path) {
	                int row = pos[0];
	                int col = pos[1];
	                if (grid[roomIndex].get(row, col).getType() == '.') {
	                    grid[roomIndex].get(row, col).setType('+');  //mark +'s
	                }
	            }

	            System.out.println("Path found:");
	            grid[roomIndex].print(); //print grid
	            return;
	        }

	        //check all directions
	        for (int direction = 0; direction < 4; direction++) {
	            int nextRow = currentRow + dRow[direction];
	            int nextCol = currentCol + dCol[direction];

	            //next tile is possible
	            if (nextRow >= 0 && nextCol >= 0 && nextRow < numRows && nextCol < numCols &&
	                !visited[nextRow][nextCol] && (grid[roomIndex].get(nextRow, nextCol).getType() != '@')) {

	                //check for | or .
	                if (grid[roomIndex].get(nextRow, nextCol).getType() == '|' || 
	                    grid[roomIndex].get(nextRow, nextCol).getType() == '.') {
	                    visited[nextRow][nextCol] = true;
	                    parentRow[nextRow][nextCol] = currentRow;  // parent row
	                    parentCol[nextRow][nextCol] = currentCol;  //parent col
	                    queue.add(new int[]{nextRow, nextCol});
	                }
	            }
	        }
	    }

	    //target not found
	    System.out.println("Target not found.");
	}
}