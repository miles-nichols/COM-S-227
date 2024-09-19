package hw4;

import java.util.ArrayList;
import api.AbstractElement;

/**
 * A PlatformElement is an element with two distinctive behaviors. First, it can
 * be set up to move horizontally within a fixed set of boundaries. On reaching
 * a boundary, the x-component of its velocity is reversed. Second, it maintains
 * a list of <em>associated</em> elements whose basic motion all occurs relative
 * to the PlatformElement.
 * 
 * @author Miles Nichols
 */
//TODO: This class must directly or indirectly extend AbstractElement
public class PlatformElement extends LiftPlatformElement {

	/**
	 * List of elements associated with this PlatformElement.
	 */
	ArrayList<AbstractElement> associated = new ArrayList<AbstractElement>();

	/**
	 * Constructs a new PlatformElement. Initially the left and right boundaries are
	 * <code>Double.NEGATIVE_INFINITY</code> and
	 * <code>Double.POSITIVE_INFINITY</code>, respectively.
	 * 
	 * @param x      x-coordinate of initial position of upper left corner
	 * @param y      y-coordinate of initial position of upper left corner
	 * @param width  object's width
	 * @param height object's height
	 */
	public PlatformElement(double x, double y, int width, int height) {
		super(x, y, width, height);
	}

	/**
	 * Updates this object's state for a new frame, and additionally calls update on
	 * all its associated elements.
	 * 
	 */
	@Override
	public void update() {
		if (getDeltaX() > 0 && getDeltaX() + getWidth() + getXInt() >= getMax()) {
			setVelocity(getDeltaX() * -1, getDeltaY());
			setPosition(getMax() - getWidth(), getDeltaY() + getYInt());
		} else if (getDeltaX() < 0 && getDeltaX() + getXInt() <= getMin()) {
			setVelocity(getDeltaX() * -1, getDeltaY());
			setPosition(getMin(), getDeltaY() + getYInt());
		} else {
			setPosition(getDeltaX() + getXInt(), getDeltaY() + getYInt());
		}
		super.update();
		setPosition(getXInt() - getDeltaX(), getYInt() - getDeltaY());
	}
}
