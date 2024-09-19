package hw3;

//import static COMS227_HW3_S24_skeleton2.COMS227_HW3_S24_skeleton.src.api.Direction.*;

import java.util.ArrayList;

import api.BodySegment;
import api.Cell;
import api.Direction;

/**
 * Represents a Lizard as a collection of body segments.
 */
public class Lizard {

	private ArrayList<BodySegment> segments; // list of segments

	/**
	 * Constructs a Lizard object.
	 */
	public Lizard() {
		segments = new ArrayList<>(); // creating the array list
	}

	/**
	 * Sets the segments of the lizard. Segments should be ordered from tail to
	 * head.
	 * 
	 * @param segments list of segments ordered from tail to head
	 */
	public void setSegments(ArrayList<BodySegment> segments) {
		this.segments = segments; // setting segment list
	}

	/**
	 * Gets the segments of the lizard. Segments are ordered from tail to head.
	 * 
	 * @return a list of segments ordered from tail to head
	 */
	public ArrayList<BodySegment> getSegments() {
		return segments; // getting segment list
	}

	/**
	 * Gets the head segment of the lizard. Returns null if the segments have not
	 * been initialized or there are no segments.
	 * 
	 * @return the head segment
	 */
	public BodySegment getHeadSegment() {
		if (segments != null) {
			return segments.get(segments.size() - 1); // returning the last segment of the list
		} else {
			return null;
		}
	}

	/**
	 * Gets the tail segment of the lizard. Returns null if the segments have not
	 * been initialized or there are no segments.
	 * 
	 * @return the tail segment
	 */
	public BodySegment getTailSegment() {
		if (segments != null) {
			return segments.get(0); // returning the last segment of the list
		} else {
			return null;
		}
	}

	/**
	 * Gets the segment that is located at a given cell or null if there is no
	 * segment at that cell.
	 * 
	 * @param cell to look for lizard
	 * @return the segment that is on the cell or null if there is none
	 */
	public BodySegment getSegmentAt(Cell cell) {
		for (int i = 0; i < segments.size(); i++) { // until as big as size of segments list
			BodySegment segment = segments.get(i); // segment is the "ith" element of the segments list
			if (cell != null && segment.getCell().equals(cell)) { // if there's a cell returns the segment
				return segment;
			}
		}
		return null;
	}

	/**
	 * Get the segment that is in front of (closer to the head segment than) the
	 * given segment. Returns null if there is no segment ahead.
	 * 
	 * @param segment the starting segment
	 * @return the segment in front of the given segment or null
	 */
	public BodySegment getSegmentAhead(BodySegment segment) {
		int index = segments.indexOf(segment); // Find the index of the given segment
		if (index >= 0 && index < segments.size() - 1) {
			return segments.get(index + 1); // Return the segment ahead of the given segment
		}
		return null;
	}

	/**
	 * Get the segment that is behind (closer to the tail segment than) the given
	 * segment. Returns null if there is not segment behind.
	 * 
	 * @param segment the starting segment
	 * @return the segment behind of the given segment or null
	 */
	public BodySegment getSegmentBehind(BodySegment segment) {
		int index = segments.indexOf(segment); // Find the index of the given segment

		// If the segment is found and it's not the tail segment
		if (index != -1 && index > 0) {
			return segments.get(index - 1); // Return the segment behind the given segment
		}

		return null; // Return null if there is no segment behind or if the segment is the tail
						// segment
	}

	/**
	 * Gets the direction from the perspective of the given segment point to the
	 * segment ahead (in front of) of it. Returns null if there is no segment ahead
	 * of the given segment.
	 * 
	 * @param segment the starting segment
	 * @return the direction to the segment ahead of the given segment or null
	 */

	public Direction getDirectionToSegmentAhead(BodySegment segment) {
		BodySegment aheadSegment = getSegmentAhead(segment); // getting the segment ahead of the current segment
		if (aheadSegment != null) {
			Cell currentCell = segment.getCell();
			Cell aheadCell = aheadSegment.getCell();

			// Calculate the direction based on cell coordinates
			if (aheadCell.getRow() < currentCell.getRow()) {
				return Direction.UP;
			} else if (aheadCell.getRow() > currentCell.getRow()) {
				return Direction.DOWN;
			} else if (aheadCell.getCol() < currentCell.getCol()) {
				return Direction.LEFT;
			} else if (aheadCell.getCol() > currentCell.getCol()) {
				return Direction.RIGHT;
			}
		}
		return null;
	}

	/**
	 * Gets the direction from the perspective of the given segment point to the
	 * segment behind it. Returns null if there is no segment behind of the given
	 * segment.
	 * 
	 * @param segment the starting segment
	 * @return the direction to the segment behind of the given segment or null
	 */
	public Direction getDirectionToSegmentBehind(BodySegment segment) {
		BodySegment behindSegment = getSegmentBehind(segment); // getting the segment ahead of the current segment

		if (behindSegment != null) {
			Cell currentCell = segment.getCell();
			Cell behindCell = behindSegment.getCell();

			// Calculate the direction based on cell coordinates
			if (behindCell.getRow() < currentCell.getRow()) {
				return Direction.UP;
			} else if (behindCell.getRow() > currentCell.getRow()) {
				return Direction.DOWN;
			} else if (behindCell.getCol() < currentCell.getCol()) {
				return Direction.LEFT;
			} else if (behindCell.getCol() > currentCell.getCol()) {
				return Direction.RIGHT;
			}
		}
		return null;
	}

	/**
	 * Gets the direction in which the head segment is pointing. This is the
	 * direction formed by going from the segment behind the head segment to the
	 * head segment. A lizard that does not have more than one segment has no
	 * defined head direction and returns null.
	 * 
	 * @return the direction in which the head segment is pointing or null
	 */
	public Direction getHeadDirection() {
		if (segments.size() < 2) {
			return null;
		}
		return getDirectionToSegmentAhead(segments.get(segments.size() - 2));
	}

	/**
	 * Gets the direction in which the tail segment is pointing. This is the
	 * direction formed by going from the segment ahead of the tail segment to the
	 * tail segment. A lizard that does not have more than one segment has no
	 * defined tail direction and returns null.
	 * 
	 * @return the direction in which the tail segment is pointing or null
	 */
	public Direction getTailDirection() {
		if (segments.size() < 2) {
			return null;
		}
		return getDirectionToSegmentBehind(segments.get(1));
	}

	@Override
	public String toString() {
		String result = "";
		for (BodySegment seg : getSegments()) {
			result += seg + " ";
		}
		return result;
	}
}
