# Maze Solver

This Java project generates and solves mazes using recursive algorithms.

## Overview

The program takes the dimensions of a maze (rows and columns) as input and generates a maze using a recursive backtracking algorithm. It then attempts to solve the generated maze using a recursive depth-first search (DFS) algorithm.  Finally, it displays the generated maze, optionally showing the solution path.

## Features

*   **Maze Generation:** Generates random mazes using recursive backtracking. The algorithm ensures that the generated mazes are solvable.
*   **Maze Solving:** Solves the generated mazes using a recursive depth-first search (DFS) algorithm.
*   **Input:**  Takes the number of rows and columns as input from the user.  Provides default values (10x10) if the user doesn't provide any input.
*   **Output:** Displays the generated maze and the solution path (if found) in the console.
*   **Error Handling:** Handles invalid input, such as non-positive maze dimensions or non-numeric input.
*   **Recursive Algorithms:**  The project demonstrates the use of recursion for both maze generation and solving.
*   **GitHub Version Control:** The project includes a history of at least 10 commits, demonstrating the development process.

## Algorithms

### Maze Generation: Recursive Backtracking

1.  Start with a grid filled with walls.
2.  Choose a random starting cell.
3.  Mark the current cell as visited.
4.  While there are unvisited neighbor cells:
    *   Choose one of the unvisited neighbor cells.
    *   Remove the wall between the current cell and the chosen neighbor.
    *   Mark the neighbor cell as visited.
    *   Recurse to the neighbor cell.

### Maze Solving: Depth-First Search (DFS)

1.  Start at the starting cell.
2.  Mark the current cell as visited.
3.  If the current cell is the exit cell, return success.
4.  For each unvisited neighbor cell:
    *   If the neighbor cell is a valid cell (not a wall and within the maze boundaries):
        *   Recurse to the neighbor cell.
        *   If the recursive call returns success, return success.
5.  Backtrack: Unmark the current cell as part of the path and return failure.

## Getting Started

### Prerequisites

*   Java Development Kit (JDK) 8 or higher
*   A text editor or Integrated Development Environment (IDE) for writing and compiling Java code (e.g., VS Code, IntelliJ IDEA, Eclipse).

### Compilation and Execution

1.  **Clone the repository:**

    ```bash
    git clone [repository URL]
    ```

2.  **Navigate to the project directory:**

    ```bash
    cd MazeSolver
    ```

3.  **Compile the Java code:**

    ```bash
    javac MazeSolver.java
    ```

4.  **Run the program:**

    ```bash
    java MazeSolver
    ```

    Or, if using an IDE, open the project in the IDE and use the IDE's build and run commands.

5.  **Input:** The program will prompt you to enter the number of rows and columns for the maze. You can either enter the values or press Enter to use the default values (10 rows and 10 columns).

### Development Journey: From Simple to Solvable

Initially, the maze generation algorithm had some issues, leading to very simple mazes or, even worse, unsolvable ones. The screenshots below illustrate this:

#### Initial Unsolvable Maze

![Initial Unsolvable Maze](image1.png)

As you can see, the generated maze was essentially a straight line of walls, preventing any path from the start to the end.

#### Another Early Issue: Trivial Mazes

![Another Early Issue: Trivial Mazes](image2.png)

In some cases, the generated mazes were very simple and didn't really qualify as mazes!

#### Resolving the Issue: A Working Maze!

After debugging and refining the recursive backtracking algorithm, the maze generation now produces complex and solvable mazes:

![Working Maze with Solution](image3.png)
![Another Working Maze with Solution](image4.png)
![Another Working Maze with Solution zoomed](image5.png)

The program now correctly identifies and displays the solution path, demonstrating the successful implementation of both the maze generation and solving algorithms.

### Example Output
