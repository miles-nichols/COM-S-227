package hw4;

/**
 * Moving element in which the vertical velocity is adjusted each frame by a
 * gravitational constant to simulate gravity. The element can be set to
 * "grounded", meaning gravity will no longer influence its velocity.
 * 
 * @author Miles Nichols
 */
//TODO: This class must directly or indirectly extend AbstractElement
public class FlyingElement extends MovingElement {

	/**
	 * True if this element is currently grounded, meaning gravity does not
	 * influence its velocity.
	 */
	private boolean grounded = true;
	
	/**
	 * The gravitational constant that influences this element's velocity.
	 */
	private double gravity = 0.0;

	/**
	 * Constructs a new FlyingElement. By default it should be grounded, meaning
	 * gravity does not influence its velocity.
	 * 
	 * @param x      x-coordinate of upper left corner
	 * @param y      y-coordinate of upper left corner
	 * @param width  element's width
	 * @param height element's height
	 */
	public FlyingElement(double x, double y, int width, int height) {
		super(x, y, width, height);
	}

	/**
	 * Updates this element's state for a new frame. If this element is not
	 * grounded, the y-component of its velocity is adjusted by the gravitational
	 * constant.
	 */
	@Override
	public void update() {
		super.update();
		if (!grounded) {
			setVelocity(getDeltaX(), getDeltaY() + gravity);
		}
	}

	/**
	 * Returns the gravitational constant that influences this element's velocity.
	 * 
	 * @return gravitational constant
	 */
	public boolean isGrounded() {
		return grounded;
	}

	/**
	 * Returns true if this element is currently grounded, meaning gravity does not
	 * influence its velocity.
	 * 
	 * @return true if this element is grounded
	 */
	public void setGravity(double grav) {
		gravity = grav;
	}

	/**
	 * Sets the gravitational constant that influences this element's velocity.
	 * 
	 * @param grounded true if this element should be grounded
	 */
	public void setGrounded(boolean ground) {
		grounded = ground;
	}
}
