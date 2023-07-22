package ca.cmpt276.project.classes.helper;

public class Position {
	private int coordinateX;
	private int coordinateY;
	
	public Position(){
		coordinateX = 0;
		coordinateY = 0;
	}
	public Position(int x, int y){
		coordinateX = x;
		coordinateY = y;
	}
	
	/**
     * A method that sets value for the x coordinate,
     * @param x the value being set for the x coordinate
     */
	public void setX(int x) {
		coordinateX = x;
	}
	
	/**
     * A method that sets value for the y coordinate,
     * @param x the value being set for the y coordinate
     */
	public void setY(int y) {
		coordinateY = y;
	}
	
	/**
     * A method that returns the x coordinate,
     * @return the x coordinate
     */
	public int getX() {
		return coordinateX;
	}
	
	/**
     * A method that returns the y coordinate,
     * @return the y coordinate
     */
	public int getY() {
		return coordinateY;
	}
	
	/**
     * A method that returns whether the position is out of bound in accordance with a maximum index,
     * @param maxIndex the maximum value of both x and y for the comparison
     * @return true if the position is out of bound; otherwise, false
     */
	public boolean isOutOfBound(int maxIndex) {
        return coordinateX < 0 || coordinateY < 0 || coordinateX > maxIndex || coordinateY > maxIndex;
    }
	
	/**
     * A method that returns a string informing the x coordinate and y coordinate in text,
     * @return a string showing the coordinates of this position
     */
	public String toString() {
		Integer x = getX();
		Integer y = getY();
		return x + "x" + y + "y";
	}
}
