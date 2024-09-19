package hw4;

import api.AbstractElement;

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
 * @author Miles Nichols
 */
//TODO: This class must directly or indirectly extend AbstractElement
public class FollowerElement extends MovingElement {

	/**
	 * The amount to add to the base's x-coordinate to calculate this element's
	 * x-coordinate.
	 */
	private int initialOffset;

	/**
	 * The left boundary of the follower element.
	 */
	private double min;

	/**
	 * The right boundary of the follower element.
	 */
	private double max;

	/**
	 * The base element of this follower element.
	 */
	private AbstractElement base;

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
		super(0, 0, width, height);
		this.initialOffset = initialOffset;
	}

	/**
	 * Sets the base element for this follower element. The initial x-coordinate
	 * becomes the base's x-coordinate, plus the given offset, and the y-coordinate
	 * becomes the base's y-coordinate, minus this element's height.
	 * 
	 * @param b base element
	 */
	public void setBase(AbstractElement b) {
		base = b;
		setPosition(initialOffset + b.getXInt(), b.getYInt() - getHeight());
	}

	/**
	 * Gets the base element for this follower element.
	 * 
	 * @return base element
	 */
	public double getMax() {
		return max;
	}

	/**
	 * Gets the base element for this follower element.
	 * 
	 * @return base element
	 */
	public double getMin() {
		return min;
	}

	/**
	 * Sets the bounds for this follower element.
	 * 
	 * @param min the left boundary of the follower element
	 * @param max the right boundary of the follower element
	 */
	public void setBounds(double min, double max) {
		this.min = min;
		this.max = max;
	}

	/**
	 * Updates this element's position to move horizontally according to its
	 * velocity (possibly zero) relative to the parent object, and remain "resting"
	 * on the parent object (provided that a parent has been set).
	 */
	public void update() {
		super.update();
		setPosition(getXInt() - getDeltaX(), getYInt() - getDeltaY());
		setBounds(base.getXInt(), base.getXInt() + base.getWidth());
		if (getXInt() + getDeltaX() + getWidth() + ((LiftPlatformElement) base).getDeltaX() >= max) {
			setPosition(max - getWidth(), base.getYReal() - getHeight());
			setVelocity(getDeltaX() * -1, getDeltaY());
		} else if (getXInt() + getDeltaX() - initialOffset + ((LiftPlatformElement) base).getDeltaX() <= min) {
			setPosition(min + initialOffset, base.getYReal() - getHeight());
			setVelocity(getDeltaX() * -1, getDeltaY());
		} else {
			setPosition(getXInt() + getDeltaX() + ((LiftPlatformElement) base).getDeltaX(),
					base.getYReal() - getHeight());
		}
	}
}
