import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public final class MazeSolver {

    private int rows;
    private int cols;
    private char[][] maze;
    private List<int[]> path;
    private int startRow;
    private int startCol;
    private int endRow;
    private int endCol;
    private final Random random = new Random();

    public MazeSolver(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Maze dimensions must be positive integers.");
        }
        this.rows = rows;
        this.cols = cols;
        this.maze = generateMaze(); // Generate maze on initialization
        this.path = new ArrayList<>();
    }

    public char[][] generateMaze() {
        maze = new char[rows][cols];

        // Initialize maze with walls
        for (int i = 0; i < rows; i++) {
            Arrays.fill(maze[i], '#');
        }

        // Choose a random starting position along the top edge
        startRow = 1;
        startCol = 1;

        // Choose a random ending position along the bottom edge
        endRow = rows - 2;
        endCol = cols - 2;

        // Keep the start and end within bounds and on odd numbered rows and cols
        startRow = Math.min(Math.max(startRow, 1), rows - 2);
        startCol = Math.min(Math.max(startCol, 1), cols - 2);
        endRow = Math.min(Math.max(endRow, 1), rows - 2);
        endCol = Math.min(Math.max(endCol, 1), cols - 2);
        if (startRow % 2 == 0) startRow++;
        if (startCol % 2 == 0) startCol++;
        if (endRow % 2 == 0) endRow++;
        if (endCol % 2 == 0) endCol++;

        recursiveBacktracking(startRow, startCol, maze);

        // Mark start and end AFTER generating the maze.  This ensures that the S and E are always on a path.
        maze[startRow][startCol] = 'S';
        maze[endRow][endCol] = 'E';

        return maze;
    }

    private void recursiveBacktracking(int row, int col, char[][] maze) {
        maze[row][col] = ' '; // Mark current cell as visited

        List<int[]> directions = new ArrayList<>();
        directions.add(new int[]{0, 2});  // Right
        directions.add(new int[]{2, 0});  // Down
        directions.add(new int[]{0, -2}); // Left
        directions.add(new int[]{-2, 0}); // Up
        Collections.shuffle(directions, random);

        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];

            // Bounds checking is CRITICAL and must ensure that newRow/newCol are WITHIN the maze
            if (newRow > 0 && newRow < rows - 1 && newCol > 0 && newCol < cols - 1 && maze[newRow][newCol] == '#') {
                maze[row + dir[0] / 2][col + dir[1] / 2] = ' '; // Carve path
                recursiveBacktracking(newRow, newCol, maze); // Recurse
            }
        }
    }

    public boolean solveMaze() {
        char[][] visited = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            Arrays.fill(visited[i], '#');
        }
        path.clear();
        return findPath(startRow, startCol, visited);
    }

    private boolean findPath(int row, int col, char[][] visited) {
        if (row == endRow && col == endCol) {
            path.add(new int[]{row, col});
            return true;
        }

        if (row < 0 || row >= rows || col < 0 || col >= cols || maze[row][col] == '#' || visited[row][col] == ' ') {
            return false;
        }

        visited[row][col] = ' ';
        path.add(new int[]{row, col});

        if (findPath(row + 1, col, visited)) return true;
        if (findPath(row - 1, col, visited)) return true;
        if (findPath(row, col + 1, visited)) return true;
        if (findPath(row, col - 1, visited)) return true;

        path.remove(path.size() - 1);
        return false;
    }

    public void displayMaze(boolean showPath) {
        if (maze == null) {
            System.out.println("Maze not generated yet.");
            return;
        }

        for (int row = 0; row < rows; row++) {
            final int currentRow = row; // Make row effectively final
            for (int col = 0; col < cols; col++) {
                final int currentCol = col; // Make col effectively final
                char cell = maze[currentRow][currentCol];
                if (showPath && path.stream().anyMatch(p -> p[0] == currentRow && p[1] == currentCol) && cell != 'S' && cell != 'E') {
                    System.out.print(". ");  // Show path
                } else {
                    System.out.print(cell + " ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the number of rows for the maze (default 10): ");
            String rowsInput = scanner.nextLine();
            int rows = rowsInput.isEmpty() ? 10 : Integer.parseInt(rowsInput);

            System.out.print("Enter the number of columns for the maze (default 10): ");
            String colsInput = scanner.nextLine();
            int cols = colsInput.isEmpty() ? 10 : Integer.parseInt(colsInput);

            MazeSolver solver = new MazeSolver(rows, cols);

            System.out.println("\nGenerated Maze:");
            solver.displayMaze(false);

            if (solver.solveMaze()) {
                System.out.println("\nSolved Maze with Path:");
                solver.displayMaze(true);

                System.out.print("\nPath found: ");
                for (int[] coord : solver.path) {
                    System.out.print("(" + coord[0] + ", " + coord[1] + ") ");
                }
                System.out.println();

            } else {
                System.out.println("\nNo path found.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input: Please enter valid integer values for rows and columns.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
}