package hw3;

import java.util.ArrayList;

import static api.Direction.*;

import api.Cell;
import api.Direction;
import api.Exit;
import api.ScoreUpdateListener;
import api.ShowDialogListener;
import api.Wall;
import api.BodySegment;

/**
 * @author Miles Nichols
 * 
 * Class that models a game.
 */
public class LizardGame {
	private ShowDialogListener dialogListener;
	private ScoreUpdateListener scoreListener;
	private int width = 0;
	private int height = 0;
	private Cell[][] grid;
	private ArrayList<Lizard> lizards = new ArrayList<Lizard>();
	private ArrayList<Wall> walls = new ArrayList<Wall>();
	private ArrayList<Exit> exits = new ArrayList<Exit>();

	/**
	 * Constructs a new LizardGame object with given grid dimensions.
	 * 
	 * @param width  number of columns
	 * @param height number of rows
	 */
	public LizardGame(int width, int height) {
		this.width = width;
		this.height = height;
		grid = new Cell[width][height];
		for (int row = 0; row < height; row++) { // Iterate over all of the grid
			for (int col = 0; col < width; col++) {
				grid[col][row] = new Cell(col, row);
			}
		}

	}

	/**
	 * Get the grid's width.
	 * 
	 * @return width of the grid
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Get the grid's height.
	 * 
	 * @return height of the grid
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Adds a wall to the grid.
	 * <p>
	 * Specifically, this method calls placeWall on the Cell object associated with
	 * the wall (see the Wall class for how to get the cell associated with the
	 * wall). This class assumes a cell has already been set on the wall before
	 * being called.
	 * 
	 * @param wall to add
	 */
	public void addWall(Wall wall) {
		Cell cell = wall.getCell(); // Get the cell associated with the wall
		cell.placeWall(wall); // Place the wall on the cell
	}

	/**
	 * Adds an exit to the grid.
	 * <p>
	 * Specifically, this method calls placeExit on the Cell object associated with
	 * the exit (see the Exit class for how to get the cell associated with the
	 * exit). This class assumes a cell has already been set on the exit before
	 * being called.
	 * 
	 * @param exit to add
	 */
	public void addExit(Exit exit) {
		Cell cell = exit.getCell(); // Get the cell associated with the exit
		cell.placeExit(exit); // Place the exit on the cell
	}

	/**
	 * Gets a list of all lizards on the grid. Does not include lizards that have
	 * exited.
	 * 
	 * @return lizards list of lizards
	 */
	public ArrayList<Lizard> getLizards() {
		return lizards;
	}

	/**
	 * Adds the given lizard to the grid.
	 * <p>
	 * The scoreListener to should be updated with the number of lizards.
	 * 
	 * @param lizard to add
	 */
	public void addLizard(Lizard lizard) {
		if (lizard != null) {
			lizards.add(lizard); // Add the lizard to the list
			scoreListener.updateScore(lizards.size()); // Update the score listener with the number of lizards
			for (int i = 0; i < lizard.getSegments().size(); i++) { // Iterate over all segments of the lizard
				BodySegment segment = lizard.getSegments().get(i); // Get the current segment
				segment.getCell().placeLizard(lizard); // Place the lizard on the cell of the segment
			}
		}
	}

	/**
	 * Removes the given lizard from the grid. Be aware that each cell object knows
	 * about a lizard that is placed on top of it. It is expected that this method
	 * updates all cells that the lizard used to be on, so that they now have no
	 * lizard placed on them.
	 * <p>
	 * The scoreListener to should be updated with the number of lizards using
	 * updateScore().
	 * 
	 * @param lizard to remove
	 */
	public void removeLizard(Lizard lizard) {
		lizards.remove(lizard);
		for (int row = 0; row < getHeight(); row++) {
			for (int col = 0; col < getWidth(); col++) {
				Cell cell = getCell(col, row);
				if (cell.getLizard() == lizard) {
					cell.placeLizard(null);
				}
			}
		}

		if (scoreListener != null) {
			scoreListener.updateScore(lizards.size());
		}
		return;
	}

	/**
	 * Gets the cell for the given column and row.
	 * <p>
	 * If the column or row are outside of the boundaries of the grid the method
	 * returns null.
	 * 
	 * @param col column of the cell
	 * @param row of the cell
	 * @return the cell or null
	 */
	public Cell getCell(int col, int row) {
		if (col < 0 || col >= width || row < 0 || row >= height) {
			return null;
		}
		return grid[col][row];

	}

	/**
	 * Gets the cell that is adjacent to (one over from) the given column and row,
	 * when moving in the given direction. For example (1, 4, UP) returns the cell
	 * at (1, 3).
	 * <p>
	 * If the adjacent cell is outside of the boundaries of the grid, the method
	 * returns null.
	 * 
	 * @param col the given column
	 * @param row the given row
	 * @param dir the direction from the given column and row to the adjacent cell
	 * @return the adjacent cell or null
	 */
	public Cell getAdjacentCell(int col, int row, Direction dir) {

		// Update coordinates based on the direction
		if (dir == UP) {
			return getCell(col, row - 1);
		} else if (dir == DOWN) {
			return getCell(col, row + 1);
		} else if (dir == Direction.LEFT) {
			return getCell(col - 1, row);
		} else if (dir == Direction.RIGHT) {
			return getCell(col + 1, row);
		} else {
			return null;
		}
	}

	/**
	 * Resets the grid. After calling this method the game should have a grid of
	 * size width x height containing all empty cells. Empty means cells with no
	 * walls, exits, etc.
	 * <p>
	 * All lizards should also be removed from the grid.
	 * 
	 * @param width  number of columns of the resized grid
	 * @param height number of rows of the resized grid
	 */
	public void resetGrid(int width, int height) {
		walls.clear();
		exits.clear();
		lizards.clear();
		this.width = width;
		this.height = height;
		this.grid = new Cell[width][height]; // Create a new grid with the given dimensions
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				grid[i][j] = new Cell(i, j);
			}
		}
	}

	/**
	 * Returns true if a given cell location (col, row) is available for a lizard to
	 * move into. Specifically the cell cannot contain a wall or a lizard. Any other
	 * type of cell, including an exit is available.
	 * 
	 * @param row of the cell being tested
	 * @param col of the cell being tested
	 * @return true if the cell is available, false otherwise
	 */
	public boolean isAvailable(int col, int row) {
		Cell cell = getCell(col, row); // Get the cell at the given row and column
		if (cell.getLizard() == null && cell.getWall() == null) { // Check if the cell is empty
			return true;
		}
		return false;
	}

	/**
	 * Move the lizard specified by its body segment at the given position (col,
	 * row) one cell in the given direction. The entire body of the lizard must move
	 * in a snake like fashion, in other words, each body segment pushes and pulls
	 * the segments it is connected to forward or backward in the path of the
	 * lizard's body. The given direction may result in the lizard moving its body
	 * either forward or backward by one cell.
	 * <p>
	 * The segments of a lizard's body are linked together and movement must always
	 * be "in-line" with the body. It is allowed to implement movement by either
	 * shifting every body segment one cell over or by creating a new head or tail
	 * segment and removing an existing head or tail segment to achieve the same
	 * effect of movement in the forward or backward direction.
	 * <p>
	 * If any segment of the lizard moves over an exit cell, the lizard should be
	 * removed from the grid.
	 * <p>
	 * If there are no lizards left on the grid the player has won the puzzle the
	 * the dialog listener should be used to display (see showDialog) the message
	 * "You win!".
	 * <p>
	 * It is possible that the given direction is not in-line with the body of the
	 * lizard (as described above), in that case this method should do nothing.
	 * <p>
	 * It is possible that the given column and row are outside the bounds of the
	 * grid, in that case this method should do nothing.
	 * <p>
	 * It is possible that there is no lizard at the given column and row, in that
	 * case this method should do nothing.
	 * <p>
	 * It is possible that the lizard is blocked and cannot move in the requested
	 * direction, in that case this method should do nothing.
	 * <p>
	 * <b>Developer's note: You may have noticed that there are a lot of details
	 * that need to be considered when implement this method method. It is highly
	 * recommend to explore how you can use the public API methods of this class,
	 * Grid and Lizard (hint: there are many helpful methods in those classes that
	 * will simplify your logic here) and also create your own private helper
	 * methods. Break the problem into smaller parts are work on each part
	 * individually.</b>
	 * 
	 * @param col the given column of a selected segment
	 * @param row the given row of a selected segment
	 * @param dir the given direction to move the selected segment
	 */
	public void move(int col, int row, Direction dir) {
		Cell selectedCell = getCell(col, row);
		if (selectedCell.getLizard() != null) {// checks to see if the cell has a lizard
			Lizard lizOnCell = selectedCell.getLizard();
			BodySegment segmentSelected = lizOnCell.getSegmentAt(selectedCell);
			BodySegment headSeg = lizOnCell.getHeadSegment();
			BodySegment tailSeg = lizOnCell.getTailSegment();

			ArrayList<BodySegment> segments = lizOnCell.getSegments(); // Get the segments of the lizard

			Cell headCell = headSeg.getCell();// The cell that is the head of the lizard
			int headCellCol = headCell.getCol(); // The column and row of the head cell
			int headCellRow = headCell.getRow();

			Cell headAdjencentCell = getAdjacentCell(headCellCol, headCellRow, dir);
			if (headAdjencentCell == null) { // checks to see if the desired segment to move to exists
				return;
			}
			int headAdjencentCellCol = headAdjencentCell.getCol();
			int headAdjencentCellRow = headAdjencentCell.getRow();
			Cell tailCell = tailSeg.getCell(); // The cell that is the tail of the lizard
			int tailCellCol = tailCell.getCol();// The column and row of the tail cell
			int tailCellRow = tailCell.getRow();
			boolean ableToMoveFoward = isAvailable(headAdjencentCellCol, headAdjencentCellRow);

			// The cell adjacent to the tail(the snake's tail will enter this cell if it is
			// moved backward)
			Cell tailAdjencentCell = getAdjacentCell(tailCellCol, tailCellRow, (dir));
			// checks to see if the desired segment to move to exists
			if (tailAdjencentCell == null) {
				return;
			}
			int tailAdjencentCellCol = tailAdjencentCell.getCol();// The column and row of the tail adjacent cell
			int tailAdjencentCellRow = tailAdjencentCell.getRow();
			boolean ableToMoveBack = isAvailable(tailAdjencentCellCol, tailAdjencentCellRow);
			Direction DirToSegBehind = lizOnCell.getDirectionToSegmentBehind(segmentSelected);
			Direction DirectionToSegmentAhead = lizOnCell.getDirectionToSegmentAhead(segmentSelected);
			if (segmentSelected == headSeg) {// checks to see if the segment selected is the head
				DirectionToSegmentAhead = dir;
			}
			if (segmentSelected == tailSeg) { // checks to see if the segment selected is the tail
				DirToSegBehind = dir;
			}
			if (dir == DirectionToSegmentAhead) {// checks to see if the direction is in line with the body
				if (ableToMoveFoward) { // checks to see if the cell is available
					BodySegment tail = lizOnCell.getTailSegment();// The tail segment
					tail.getCell().removeLizard();// removes the lizard from the tail cell
					int i;// The index of the segment
					for (i = 0; i < segments.size() - 1; i++) {
						Cell cell = segments.get(i + 1).getCell();
						segments.get(i).setCell(cell);
					}
					BodySegment head = segments.get(i);// The head segment
					head.setCell(headAdjencentCell);
					Exit exit = headAdjencentCell.getExit();
					if (exit != null) {
						removeLizard(lizOnCell);
						if (lizards.size() == 0) {
							dialogListener.showDialog("You Win!");// displays a message if the player wins
						}
					}
				}
			}
			if (dir == DirToSegBehind) {// checks to see if the direction is in line with the body
				if (ableToMoveBack) {
					int i;
					BodySegment head = lizOnCell.getHeadSegment();
					head.getCell().removeLizard();
					for (i = segments.size() - 1; i > 0; i--) {
						Cell cell = segments.get(i - 1).getCell();
						segments.get(i).setCell(cell);
					}
					BodySegment tail = segments.get(i);
					tail.setCell(tailAdjencentCell);
					Exit exit = tailAdjencentCell.getExit();
					if (exit != null) {
						removeLizard(lizOnCell);
						if (lizards.size() == 0) {
							dialogListener.showDialog("You Win!");
						}
					}
				}
			}
		}
	}

	/**
	 * Sets callback listeners for game events.
	 * 
	 * @param dialogListener listener for creating a user dialog
	 * @param scoreListener  listener for updating the player's score
	 */
	public void setListeners(ShowDialogListener dialogListener, ScoreUpdateListener scoreListener) {
		this.dialogListener = dialogListener; // Assign the dialog listener directly
		this.scoreListener = scoreListener; // Assign the score listener if it's not null
	}

	/**
	 * Load the game from the given file path
	 * 
	 * @param filePath location of file to load
	 */
	public void load(String filePath) {
		GameFileUtil.load(filePath, this);
	}

	@Override
	public String toString() {
		String str = "---------- GRID ----------\n";
		str += "Dimensions:\n";
		str += getWidth() + " " + getHeight() + "\n";
		str += "Layout:\n";
		for (int y = 0; y < getHeight(); y++) {
			if (y > 0) {
				str += "\n";
			}
			for (int x = 0; x < getWidth(); x++) {
				str += getCell(x, y);
			}
		}
		str += "\nLizards:\n";
		for (Lizard l : getLizards()) {
			str += l;
		}
		str += "\n--------------------------\n";
		return str;
	}
}
