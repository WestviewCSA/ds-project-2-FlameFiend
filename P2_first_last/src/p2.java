import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class p2 {

    public static void main(String[] args) {
        System.out.println("p2");
    }	
    private static String queueRoute(Map gameMap) {
        Tile[][] map = gameMap.getMap();
        Tile start = gameMap.getStart();
        Tile goal = gameMap.getGoal();
        if (start == null || goal == null) return "Start or Goal not found!";

        Queue<Tile> queue = new LinkedList<>();
        Map<Tile, Tile> cameFrom = new HashMap<>();
        queue.add(start);
        cameFrom.put(start, null);

        while (!queue.isEmpty()) {
            Tile current = queue.poll();
            if (current == goal) return reconstructPath(cameFrom, goal);

            for (int[] dir : DIRECTIONS) {
                int newRow = current.getRow() + dir[0];
                int newCol = current.getCol() + dir[1];

                if (isValidMove(map, newRow, newCol) && !cameFrom.containsKey(map[newRow][newCol])) {
                    queue.add(map[newRow][newCol]);
                    cameFrom.put(map[newRow][newCol], current);
                }
            }
        }
        return "No Path Found!";
    }
    private static boolean isValidMove(Tile[][] map, int row, int col) {
        return row >= 0 && row < map.length && col >= 0 && col < map[0].length && map[row][col].getType() != '#';
    }

    public static String stackRoute(String filename) {
    	readMap(filename);
    	
    	return"";
    }
    public static String optimalRoute(String filename) {
    	readMap(filename);
    	
    	return"";
    }
}
