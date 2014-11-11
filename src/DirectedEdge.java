/**
 * 
 */

/**
 * @author Qishen Zhang
 *
 */

public class DirectedEdge { 
    private final Point a; // the direction is a->b
    private final Point b;
    private double weight;

    /**
     * Initializes a directed edge from vertex <tt>v</tt> to vertex <tt>w</tt> with
     * the given <tt>weight</tt>.
     * @param v the tail vertex
     * @param w the head vertex
     * @param weight the weight of the directed edge
     * @throws java.lang.IndexOutOfBoundsException if either <tt>v</tt> or <tt>w</tt>
     *    is a negative integer
     * @throws IllegalArgumentException if <tt>weight</tt> is <tt>NaN</tt>
     */
    public DirectedEdge(Point a, Point b, double weight) {
        if (Double.isNaN(weight)) throw new IllegalArgumentException("Weight is NaN");
        this.a = a;
        this.b = b;
        this.weight = weight;
    }

    /**
     * Returns the tail vertex of the directed edge.
     * @return the tail vertex of the directed edge
     */
    public Point from() {
        return this.a;
    }

    /**
     * Returns the head vertex of the directed edge.
     * @return the head vertex of the directed edge
     */
    public Point to() {
        return this.b;
    }

    /**
     * Returns the weight of the directed edge.
     * @return the weight of the directed edge
     */
    public double weight() {
        return weight;
    }
    
    public void setWeight(double weight){
    	this.weight = weight;
    }
    
    /**
     * reverse the direction of the DirectedEdge
     * @return a reverse directed edge
     */
    public DirectedEdge reverse(){
    	DirectedEdge e = new DirectedEdge(this.b,this.a,this.weight);
    	return e;
    }
    
    /**
     * Returns a string representation of the directed edge.
     * @return a string representation of the directed edge
     */
    public String toString() {
        return  "("+String.format("%5.2f", this.a.getX())+","+String.format("%5.2f", this.a.getY())+" )" 
        +"->" + "("+String.format("%5.2f", this.b.getX())+","+String.format("%5.2f", this.b.getY())+" )" 
        		+ " " + String.format("%5.2f", weight);
    }

    /**
     * Unit tests the <tt>DirectedEdge</tt> data type.
     */
    public static void main(String[] args) {
    	Point p1 = new Point(0.5,0.5,1);
    	Point p2 = new Point(0.4,0.3,2);
        DirectedEdge e = new DirectedEdge(p1,p2, 3.14);
        System.out.println(e);
    }
}
