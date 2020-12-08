import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

/**
* Maze represents a maze that can be navigated. The maze
* should indicate its start and end squares, and where the
* walls are. 
*
* Eventually, this class will be able to load a maze from a
* file, and solve the maze.
* The starter code has part of the implementation of load, but
* it does not read and store the information about where the walls of the maze are.
*
*/
public class Maze { 
    //Number of rows in the maze.
    private int numRows;
    
    //Number of columns in the maze.
    private int numColumns;
    
    //Grid coordinates for the starting maze square
    private int startRow;
    private int startColumn;
    
    //Grid coordinates for the final maze square
    private int finishRow;
    private int finishColumn;
    
    //Store all the maze squares and solution squares
    private List<MazeSquare> squares;
    private List<MazeSquare> solutionList = new ArrayList<MazeSquare>();
    
    //Whether or not this maze has been solved or is unsolvable
    boolean solved = false;
    /**
     * Creates an empty maze with no squares.
     */
    public Maze() {
        ; //You can add any code you need to initialize instance 
          //variables you've added.
    } 
    
    /**
     * Loads the maze that is written in the given fileName.
     * Returns true if the file in fileName is formatted correctly
     * (meaning the maze could be loaded) and false if it was formatted
     * incorrectly (meaning the maze could not be loaded). The correct format
     * for a maze file is given in the assignment description. Ways 
     * that you should account for a maze file being incorrectly
     * formatted are: one or more squares has a descriptor that doesn't
     * match  *, 7, _, or | as a descriptor; the number of rows doesn't match
     * what is specified at the beginning of the file; or the number of
     * columns in any row doesn't match what's specified at the beginning
     * of the file; or the start square or the finish square is outside of
     * the maze. You can assume that the file does start with the number of
     * rows and columns.
     * 
     */
    public boolean load(String fileName) { 
      String row;
      squares = new ArrayList<MazeSquare>();

      try {
        File inputFile = new File(fileName);
        Scanner scanner = new Scanner(inputFile);
        numColumns = scanner.nextInt();
        numRows = scanner.nextInt();
        startColumn = scanner.nextInt();
        startRow = scanner.nextInt();
        finishColumn = scanner.nextInt();
        finishRow = scanner.nextInt();
        if (numRows < 1 || numColumns < 1 ||
        !isInRange(startColumn, 0, numColumns + 1) ||
        !isInRange(startRow, 0, numRows + 1) ||
        !isInRange(finishColumn, 0, numColumns + 1) ||
        !isInRange(finishRow, 0, numRows + 1)) {
          return false;
        }
        scanner.nextLine();

        int curCol = 0;
        int curRow = 0;
        MazeSquare tempSquare;
        String square;

        while (scanner.hasNextLine()) {
          
          if (curRow > numRows) {
            return false;
          }
          row = scanner.nextLine();
          if (row.length() != numColumns) {
            return false;
          }
          curCol = 0;
          for (int i = 0; i < row.length(); i++) {
            square = row.substring(i, i + 1);
            //System.out.println("This square is: " + square + i);
            if (curCol > numColumns) {
              return false;
            }
            if (square.equals("7")) {
              tempSquare = new MazeSquare(true, true, curRow, curCol);
            } else if (square.equals("|")) {
              tempSquare = 
              new MazeSquare(false, true, curRow, curCol);
            } else if (square.equals("_")) {
              tempSquare = 
              new MazeSquare(true, false, curRow, curCol);
            } else if (square.equals("*")) {
              tempSquare = 
              new MazeSquare(false, false, curRow, curCol);
            } else {
              return false;
            }
            squares.add(tempSquare);
            curCol++;
          }
          curRow++; 
        }
      } catch (FileNotFoundException e) {
        System.out.println(e);
        System.exit(1);
      }
      return true;
    } 
    
    /**
     * Returns true if number is greater than or equal to lower bound
     * and less than upper bound. 
     * @param number
     * @param lowerBound
     * @param upperBound
     * @return true if lowerBound ≤ number < upperBound
     */
    private static boolean isInRange(int number, int lowerBound, int upperBound) {
        return number < upperBound && number >= lowerBound;
    }
    
    /**
     * Prints the maze with the start and finish squares marked. If
     * there a solution has been found, it will be displayed on the 
     * maze by asterisks.
     */
    public void print() {
        //Indicates whether a given square is in the solution path
        boolean inSolution;

        //We'll print off each row of squares in turn.
        for(int row = 0; row < numRows; row++) {
            
            //Print each of the lines of text in the row
            for(int charInRow = 0; charInRow < 4; charInRow++) {
                //Need to start with the initial left wall.
                if(charInRow == 0) {
                    System.out.print("+");
                } else {
                    System.out.print("|");
                }
                
                for(int col = 0; col < numColumns; col++) {
                    MazeSquare curSquare = this.getMazeSquare(row, col);
                    
                    inSolution = false;
                    if (solutionList.contains(curSquare)) {
                      inSolution = true;
                    }
                    if(charInRow == 0) {
                        //We're in the first row of characters for this square - need to print
                        //top wall if necessary.
                        if(curSquare.hasTopWall()) {
                            System.out.print(getTopWallString());
                        } else {
                            System.out.print(getTopOpenString());
                        }
                    } else if(charInRow == 1 || charInRow == 3) {
                        //These are the interior of the square and are unaffected by
                        //the start/final state.
                        if(curSquare.hasRightWall()) {
                            System.out.print(getRightWallString());
                        } else {
                            System.out.print(getOpenWallString());
                        }
                    } else {
                        //We must be in the second row of characters.
                        //This is the row where start/finish should be displayed if relevant
                        
                        //Check if we're in the start or finish state
                        if(startRow == row && startColumn == col) {
                            System.out.print("  S  ");
                        } else if(finishRow == row && finishColumn == col) {
                            System.out.print("  F  ");
                        } else if (inSolution) {
                            System.out.print("  *  ");
                        } else {
                            System.out.print("     ");
                        }
                        if(curSquare.hasRightWall()) {
                            System.out.print("|");
                        } else {
                            System.out.print(" ");
                        }
                    } 
                }
                
                //Now end the line to start the next
                System.out.print("\n");
            }           
        }
        
        //Finally, we have to print off the bottom of the maze, since that's not explicitly represented
        //by the squares. Printing off the bottom separately means we can think of each row as
        //consisting of four lines of text.
        printFullHorizontalRow(numColumns);
    }
    
    /**
     * Prints the very bottom row of characters for the bottom row of maze squares (which is always walls).
     * numColumns is the number of columns of bottom wall to print.
     */
    private static void printFullHorizontalRow(int numColumns) {
        System.out.print("+");
        for(int row = 0; row < numColumns; row++) {
            //We use getTopWallString() since bottom and top walls are the same.
            System.out.print(getTopWallString());
        }
        System.out.print("\n");
    }
    
    /**
     * Returns a String representing the bottom of a horizontal wall.
     */
    private static String getTopWallString() {
        return "-----+";
    }
    
    /**
     * Returns a String representing the bottom of a square without a
     * horizontal wall.
     */
    private static String getTopOpenString() {
        return "     +";
    }
    
    /**
     * Returns a String representing a left wall (for the interior of the row).
     */
    private static String getRightWallString() {
        return "     |";
    }
    
    /**
     * Returns a String representing no left wall (for the interior of the row).
     */
    private static String getOpenWallString() {
        return "      ";
    }
    
    /**
     * Implement me! This method should return the MazeSquare at the given 
     * row and column. The line "return null" is added only to make the
     * code compile before this method is implemented. Delete that line and
     * replace it with your own code.
     */
    public MazeSquare getMazeSquare(int row, int col) {
        MazeSquare targetSquare = null;
        for (MazeSquare square : squares) {
            if (square.getRow() == row && square.getColumn() == col) {
              targetSquare = square;
              break;
            }
        }
        return targetSquare;    
    }
    
    /**
    * Computes and returns a solution to this maze. If there are multiple
    * solutions, only one is returned, and getSolution() makes no guarantees about
    * which one. However, the returned solution will not include visits to dead
    * ends or any backtracks, even if backtracking occurs during the solution
    * process. 
    *
    * @return a stack of MazeSquare objects containing the sequence of squares
    * visited to go from the start square (bottom of the stack) to the finish
    * square (top of the stack). If there is no solution, an empty stack is
    * returned.
    */
    public Stack<MazeSquare> getSolution() {
      boolean solved = false;
      
      for (MazeSquare square : squares) {
        square.unvisit();
      }
      Stack<MazeSquare> solution = new MysteryStackImplementation<MazeSquare>();
      MazeSquare start = this.getMazeSquare(startRow, startColumn);
      start.visit();
      solution.push(start);
      
      //Initializing variables to store the current location
      MazeSquare curSquare = start;
      int curRow = startRow;
      int curColumn = startColumn;

      //Initializing variables to determine where to go next
      MazeSquare nextSquare = null;
      boolean canGoRight;
      boolean canGoLeft;
      boolean canGoUp;
      boolean canGoDown;

      while (!solved) {
        
        canGoRight = false;
        canGoLeft = false;
        canGoUp = false;
        canGoDown = false;

        if (solution.isEmpty()) {
          System.out.println("This maze cannot be solved!");
          break;
        } else if (solution.peek().equals(this.getMazeSquare(finishRow, finishColumn))) {
          break;
        }

        curSquare = solution.peek();
        curRow = curSquare.getRow();
        curColumn = curSquare.getColumn();

        //Determining possible directions to go
        if (curColumn < numColumns - 1 &&
        !curSquare.hasRightWall() &&
        !this.getMazeSquare(curRow, curColumn + 1).isVisited()) {
          canGoRight = true;
        } 
        if (curColumn > 0 &&
        !this.getMazeSquare(curRow, curColumn - 1).hasRightWall() &&
        !this.getMazeSquare(curRow, curColumn - 1).isVisited()) {
          canGoLeft = true;
        }
        if (curRow > 0 &&
        !curSquare.hasTopWall() &&
        !this.getMazeSquare(curRow - 1, curColumn).isVisited()) {
          canGoUp = true;
        }
        if (curRow < numRows - 1 &&
        !this.getMazeSquare(curRow + 1, curColumn).hasTopWall() &&
        !this.getMazeSquare(curRow + 1, curColumn).isVisited()) {
          canGoDown = true;
        }
        
        //Moving to the next square or backtracking
        if (!canGoRight && !canGoLeft && !canGoUp && !canGoDown) {    
          solution.pop();
          continue;
        } else if (canGoRight) {
          nextSquare = this.getMazeSquare(curRow, curColumn + 1);
        } else if (canGoLeft) {
          nextSquare = this.getMazeSquare(curRow, curColumn - 1);
        } else if (canGoUp) {
          nextSquare = this.getMazeSquare(curRow - 1, curColumn);
        } else if (canGoDown) {
          nextSquare = this.getMazeSquare(curRow + 1, curColumn);
        }
        nextSquare.visit();
        solution.push(nextSquare);
      }
      solved = true;
      //Creating an easy-access list to display the solution later
      Stack<MazeSquare> tempStack = solution;
      while (!tempStack.isEmpty()) {
        solutionList.add(tempStack.pop());
      }
      return solution;
    }
      

    /**
     * If there is only one
     * command line argument, it loads the maze and prints it
     * with no solution. If there are two command line arguments
     * and the second one is --solve,
     * it should load the maze, solve it, and print the maze
     * with the solution marked. No other command lines are valid.
     */ 
    public static void main(String[] args) { 
      Maze maze = new Maze();
      try {
        if (maze.load(args[0])) {
          if (args.length == 1) {
            maze.print();
          } else if (args.length == 2 && args[1].equals("--solve")) {
            maze.getSolution();
            maze.print();
          } else {
            System.out.println("USAGE: the first commandline argument should be the file containing the maze.");
            System.out.println("The optional second argument is '--solve' if you would like to see a solution to the maze.");
            System.exit(0);
          }
        } else {
            System.out.println("The maze file you entered was formatted incorrectly. Please try again.");
            System.exit(0);
        }
      } catch (IndexOutOfBoundsException e) {
        System.out.println("USAGE: the first commandline argument should be the file containing the maze.");
        System.out.println("The optional second argument is '--solve' if you would like to see a solution to the maze.");
        System.exit(0);
      }
    } 
}