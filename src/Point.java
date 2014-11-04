/**
 * Point class with x-y coordinates and 
 * a universal identifier num
 */

/**
 * @author Qishen Zhang
 *
 */
public class Point {
	private double x;
	private double y;
	private final int num;
	
	/**
	 * Initialize Point class with only number
	 * By default create random coordinates between 0~1
	 * @param num
	 */
	Point(int num){
		this.x = Math.random();
		this.y = Math.random();
		this.num = num;
	}
	
	/**
	 * Initialize Point class with all parameters
	 * @param x
	 * @param y
	 * @param num
	 */
	Point(double x, double y, int num){
		if(x<0 || y<0 || num<0) throw new IllegalArgumentException("Unsupported coordinates");
		this.x = x;
		this.y = y;
		this.num = num;
	}
	
	/**
	 * Return the distance between two points including the invoking point object
	 * @return the distance between two points
	 * @throws IllegalArgumentException if the coordinates are negative
	 */
	public double distance(Point p){
		return Math.sqrt((this.x - p.x)*(this.x - p.x) + (this.y - p.y)*(this.y - p.y));
	}
	
	/**
	 * check if two points are identical
	 * @param p
	 * @return true if identical
	 */
	public boolean equals(Point p){
		if(this.x == p.x && this.y == p.y) return true;
		else return false;
	}
	
	/**
	 * Set new value for x-coordinate
	 * @param x
	 */
	public void setX(double x){
		this.x = x;
	}
	
	/**
	 * Set new value for y-coordinate
	 * @param y
	 */
	public void setY(double y){
		this.y = y;
	}
	
	/**
	 * get the value of x-coordinate
	 * @return value of x-coordinate
	 */
	public double getX(){
		return this.x;
	}
	
	/**
	 * get the value of y-coordinate
	 * @return value of y-coordinate
	 */
	public double getY(){
		return this.y;
	}
	
	/**
	 * get the number of Point
	 * @return the number of Point
	 */
	public int getNum(){
		return this.num;
	}
	
	public String toString(){
		return "("+String.format("%5.2f", this.x)+","+String.format("%5.2f", this.y)+" )" 
				+ " No."+this.num;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Point p = new Point(1.0,2.0,3);
		StdOut.println(p);

	}

}
