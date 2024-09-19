package hw4;

/**
 * An element that does not move. Instead, it is intended to appear on the
 * screen for a fixed number of frames.
 * 
 * @author Miles Nichols
 */
//TODO: This class must directly or indirectly extend AbstractElement
public class VanishingElement extends SimpleElement {
	
	/**
	 * Number of frames until this element marks itself for deletion.
	 */
	private int frameCount;
	
	/**
	 * Constructs a new VanishingElement.
	 * 
	 * @param x           x-coordinate of upper left corner
	 * @param y           y-coordinate of upper left corner
	 * @param width       element's width
	 * @param height      element's height
	 * @param initialLife the number of frames until this element marks itself for
	 *                    deletion
	 */
	public VanishingElement(double x, double y, int width, int height, int initialLife) {
		super(x, y, width, height);
		frameCount = initialLife;
	}
	
	/**
     * Decrements the frame count. If the frame count reaches zero, marks this
     * element for deletion.
     */
	@Override
	public void update() {
		super.update();
		frameCount--;
		if (frameCount == 0) {
			markForDeletion();
		}
	}
}
