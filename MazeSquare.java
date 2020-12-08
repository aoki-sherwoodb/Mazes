/**
* MazeSquare represents a single square within a Maze.
* @author Anna Rafferty
*/ 
public class MazeSquare {
    //Wall variables
    private boolean hasTopWall = false;
    private boolean hasRightWall = false;
		
    //Location of this square in a larger maze.
    private int row;
    private int col;

    //Indicates whether this square has been visited when solving 
    private boolean visited;

		/**
    * Initializes a new MazeSquare with @param top indicating whether
    * it has a top wall, @param right indicating whether it has a 
    * right wall, @param row designating the row it is in, and 
    * @param col designating what column it is in.
    */
		public MazeSquare(boolean top, boolean right, int row, int col) {
      hasTopWall = top;
      hasRightWall = right;
      this.row = row;
      this.col = col;
    }
    /**
     * Returns true if this square has a top wall.
     */
    public boolean hasTopWall() {
        return hasTopWall;
    }
		
    /**
     * Returns true if this square has a right wall.
     */
    public boolean hasRightWall() {
        return hasRightWall;
    }
		
    /**
     * Returns the row this square is in.
     */
    public int getRow() {
        return row;
    }
		
    /**
     * Returns the column this square is in.
     */
    public int getColumn() {
        return col;
    }
    
    /**
    * Returns true if this square has already been visited in
    * the process of finding a solution
    */
    public boolean isVisited() {
      return visited;
    }

    /**
    * Marks this square as having been visited.
    */
    public void visit() {
      visited = true;
    }

    /**
    * Marks this square as not yet having been visited.
    */
    public void unvisit() {
      visited = false;
    }

    /**
    * Returns true if this square is identical to 
    * @param targetSquare
    */
    public boolean equals(MazeSquare targetSquare) {
      if (this.row == targetSquare.getRow() &&
      this.col == targetSquare.getColumn()) {
        return true;
      } else {
        return false;
      }
    }

    
} 