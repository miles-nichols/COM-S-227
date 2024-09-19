package hw4;

/**
 * An element with two distinctive behaviors. First, it can be set up to move
 * vertically within a fixed set of boundaries. On reaching a boundary, the
 * y-component of its velocity is reversed. Second, it maintains a list of
 * <em>associated</em> elements whose basic motion all occurs relative to the
 * LiftElement.
 * 
 * @author Miles Nichols
 */
public class LiftElement extends LiftPlatformElement {

	/**
	 * List of elements associated with this element. Associated elements are moved
	 * when this element moves.
	 */
	
	/**
	 * Constructs a new Elevator. Initially the upper and lower boundaries are
	 * <code>Double.NEGATIVE_INFINITY</code> and
	 * <code>Double.POSITIVE_INFINITY</code>, respectively.
	 * 
	 * @param x      x-coordinate of initial position of upper left corner
	 * @param y      y-coordinate of initial position of upper left corner
	 * @param width  element's width
	 * @param height element's height
	 */
	public LiftElement(double x, double y, int width, int height) {
		super(x, y, width, height);
	}

	/**
	 * 
	 * Updates this element's state for a new frame, and additionally calls update
	 * on all its associated elements.
	 */
	@Override
	public void update() {
		if (getDeltaY() > 0 && getDeltaY() + getHeight() + getYInt() >= getMax()) {
			setVelocity(getDeltaX(), getDeltaY() * -1);
			setPosition(getDeltaX() + getXInt(), getMax() - getHeight());
		} else if (getDeltaY() < 0 && getDeltaY() + getYInt() <= getMin()) {
			setVelocity(getDeltaX(), getDeltaY() * -1);
			setPosition(getDeltaX() + getXInt(), getMin());
		} else {
			setPosition(getDeltaX() + getXInt(), getDeltaY() + getYInt());
		}
		super.update();
		setPosition(getXInt() - getDeltaX(), getYInt() - getDeltaY());
	}
}