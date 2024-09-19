package hw4;

/**
 * A follower element is one that is associated with another "base" element such
 * as a PlatformElement or LiftElement. Specifically, the follower element's
 * movement is determined by the movement of the base element, when the base
 * move up 10 pixels, the follower moves up 10 pixels. However, the follower may
 * not always be at a fixed location relative to the base. When the horizontal
 * velocity of the follower is set to a non-zero value, the follower will
 * oscillate between the left and right edges of the PlatformElement or
 * LiftElement it is associated with.
 * 
 * @author YOUR NAME HERE
 */
//TODO: This class must directly or indirectly extend AbstractElement
public class FollowerElement {

	/**
	 * Constructs a new FollowerElement. Before being added to a "base" element such
	 * as a PlatformElement or LiftElement, the x and y coordinates are zero. When a
	 * base element is set, the initial x-coordinate becomes the base's
	 * x-coordinate, plus the given offset, and the y-coordinate becomes the base's
	 * y-coordinate, minus this element's height.
	 * 
	 * @param width         element's width
	 * @param height        element's height
	 * @param initialOffset when added to a base, this amount will be added to the
	 *                      bases's x-coordinate to calculate this element's initial
	 *                      x-coordinate
	 */
	public FollowerElement(int width, int height, int initialOffset) {
		// TODO: everything
	}

	// TODO: everything

}
