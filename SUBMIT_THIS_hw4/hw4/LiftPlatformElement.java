package hw4;

import java.util.ArrayList;
import api.AbstractElement;

public class LiftPlatformElement extends MovingElement{
	
	/**
	 * ArrayList to store the elements that are associated with this element
	 */
	private ArrayList<AbstractElement> list = new ArrayList<AbstractElement>();
	
	/**
	 * Variable to represent the lower and right bounds of the element
	 */
	private double max;
	
	/**
	 * Variable to represent the upper and left bounds of the element
	 */
	private double min;
	
	/**
	 * Constructs a new LiftPlatformElement with the given x and y coordinates,
	 * width, and height.
	 * 
	 * @param x      x-coordinate of the element
	 * @param y      y-coordinate of the element
	 * @param width  width of the element
	 * @param height height of the element
	 */
	public LiftPlatformElement(double x, double y, int width, int height) {
	
		super(x, y, width, height);
		
	}
	
	/**
	 * Adds an element to the list of elements that are associated with this element
	 * 
	 * @param attatched element to be added to the list
	 */
	public void addAssociated(AttachedElement attatched) {
		list.add(attatched);
		attatched.setBase(this);
	}
	
	/**
	 * Adds an element to the list of elements that are associated with this element
	 * 
	 * @param follower element to be added to the list
	 */
	public void addAssociated(FollowerElement follower) {
		list.add(follower);
		follower.setBounds(getXInt(), getXInt() + getWidth());
		follower.setBase(this);
	}
	
	/**
	 * Removes an element from the list of elements that are associated with this
	 * element
	 * 
	 * @param element element to be removed from the list
	 */
	public void deleteMarkedAssociated() {
		list.clear();
	}
	
	/**
	 * Returns the list of elements that are associated with this element
	 * 
	 * @return list of elements that are associated with this element
	 */
	public ArrayList<AbstractElement> getAssociated(){
		return list;
	}
	
	/**
	 * Sets the bounds of the element
	 * 
	 * @param min left boundary
	 * @param max right boundary
	 */
	public double getMax() {
		return max;
	}
	
	/**
	 * Returns the right boundary of the element
	 * 
	 * @return right boundary
	 */
	public double getMin() {
		return min;
	}
	
	/**
	 * Sets the bounds of the element
	 * 
	 * @param min left boundary
	 * @param max right boundary
	 */
	public void setBounds(double min, double max) {
		this.min = min;
		this.max = max;
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i) instanceof FollowerElement) {
				((FollowerElement)list.get(i)).setBounds(min, max);
			}
		}
	}
	
	/**
	 * Updates each element in the list of associated elements and increments frameCount
	 */
	@Override
	public void update() {
		for(int i = 0; i < list.size(); i++) {
			list.get(i).update();
		}
		super.update();
	}
}

