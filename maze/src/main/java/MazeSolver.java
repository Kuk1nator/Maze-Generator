import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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

    public MazeSolver(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Maze dimensions must be positive integers.");
        }
        this.rows = rows;
        this.cols = cols;
        this.maze = generateMaze();
        this.startRow = 0;
        this.startCol = 0;
        this.endRow = rows - 1;
        this.endCol = cols - 1;
        this.path = new ArrayList<>();

    }

    public char[][] generateMaze() {
        char[][] localMaze = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            Arrays.fill(localMaze[i], '#'); // Initialize with walls
        }

        recursiveBacktracking(0, 0, localMaze); // Start backtracking from top-left corner
        localMaze[startRow][startCol] = 'S';  // Mark start point
        localMaze[endRow][endCol] = 'E';    // Mark end point

        return localMaze;
    }

    private void recursiveBacktracking(int row, int col, char[][] maze) {
        maze[row][col] = ' '; // Mark current cell as path

        List<int[]> directions = new ArrayList<>();
        directions.add(new int[]{0, 2});
        directions.add(new int[]{2, 0});
        directions.add(new int[]{0, -2});
        directions.add(new int[]{-2, 0});
        Collections.shuffle(directions); // Randomize the order of exploration

        for (int[] dir : directions) {
            int dr = dir[0];
            int dc = dir[1];
            int newRow = row + dr;
            int newCol = col + dc;

            if (newRow > 0 && newRow < rows - 1 && newCol > 0 && newCol < cols - 1 && maze[newRow][newCol] == '#') {
                maze[row + dr / 2][col + dc / 2] = ' '; // Carve path between current and next cell
                recursiveBacktracking(newRow, newCol, maze);
            }
        }
    }

    public boolean solveMaze() {
        char[][] visited = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            Arrays.fill(visited[i], '#');
        }
        path.clear();  // Reset path
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


        path.remove(path.size() - 1);  // Backtrack
        return false;
    }

    public void displayMaze(boolean showPath) {
        if (maze == null) {
            System.out.println("Maze not generated yet.");
            return;
        }

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                boolean isOnPath = false;
                if (showPath) {
                    for (int[] coord : path) {
                        if (coord[0] == row && coord[1] == col && maze[row][col] != 'S' && maze[row][col] != 'E') {
                            isOnPath = true;
                            break;
                        }
                    }
                }

                if (isOnPath) {
                    System.out.print(". ");
                } else {
                    System.out.print(maze[row][col] + " ");
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