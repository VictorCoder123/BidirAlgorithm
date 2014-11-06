import java.awt.Color;
import java.util.Vector;
import java.util.Stack;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Iterator;
/**
 * @author Qishen Zhang
 *
 */
public class Dijkstra {
	private int s; //num of start point
	private int t; //num of end point
	private WeightedGraph g;
	private double [] dist; // the current shortest distance from vertex to source
	private int [] from;  // the previous vertex of certain vertex on the shortest path
	private boolean [] visited; //true if the vertice is already visited
	private Vector<Integer> treeSet; //store the expanded vertices, which don't need to be relaxed anymore
	private PriorityQueue<Integer> pq; //store the frontier of vertices
	private int iterations;
	private int smallest; //end point of the smallest edge or start point
	
	/**
	 * Comparator for DirectedEdge, compare the distance from 
	 * source to end point of the edge
	 * @author Qishen Zhang
	 */
	public class EdgeComparator implements Comparator<Integer>{
		public int compare(Integer num1, Integer num2){
			/*compare the dist[] of end point of two edges*/
			if(dist[num1] > dist[num2]) return 1;
			else if(dist[num1] < dist[num2]) return -1;
			else return 0;
		}
	}
	
	/**
	 * Constructor of Dijkstra's algorithm, set t larger than V
	 * if need to all shortest path from s.
	 * @param g
	 * @param s
	 * @param t
	 */
	Dijkstra(WeightedGraph g, int s, int t){
		iterations = 0;
		int V = g.vNum();
		if(s<0||s>=V) throw new IllegalArgumentException("Exceed Bound!");
		this.s = s;
		this.t = t;
		this.g = g;
		smallest = Integer.MAX_VALUE; //end point of the smallest edge or start point
		visited = new boolean[V];
		dist = new double[V];
		from = new int[V];
		treeSet = new Vector<Integer>();
		
		for(int v=0; v<V; v++){
			visited[v] = false;
			dist[v] = Double.POSITIVE_INFINITY;
			from[v] = V; // v is not reachable if it still equals to V at last
		}
		dist[s] = 0.0;
		visited[s] = true;
		from[s] = s;
		Comparator<Integer> comparator = new EdgeComparator();
		pq = new PriorityQueue<Integer>(comparator);
		pq.add(s);
	}
	
	public void expand(){
		smallest = pq.poll(); //end point of the smallest edge
		treeSet.add(smallest);
		Iterator<DirectedEdge> iterator = g.adjEdges(smallest);
		/*add all adjacent edges of smallest vertex to pq*/
		while(iterator.hasNext()){
			DirectedEdge e = iterator.next(); // update dist[] in every expansion
			if(dist[e.from().getNum()]+e.weight() < dist[e.to().getNum()]){
				dist[e.to().getNum()] = dist[e.from().getNum()]+e.weight();
				from[e.to().getNum()] = e.from().getNum(); //keep track of previous vertex
				iterations++;
			}
			/*update all dist but only add edges with unvisited end point */
			if (!visited[e.to().getNum()]){
				pq.add(e.to().getNum());
				visited[e.to().getNum()] = true;
			}
		}	
	}
	
	/**
	 * compute with the Dijkstra algorithm until reach t or cannot 
	 * reach the rest of vertices.
	 */
	public void compute(){
		// continue until the frontier of vertices is empty
		while(!pq.isEmpty() && smallest!=t){ //stop when destination point is going to be expanded
			expand();
		}
	}
	
	/**
	 * return the shortest path
	 * @return a queue of shortest path
	 */
	public Stack<Integer> path(){
		int V = visited.length;
		Stack<Integer> stack = new Stack<Integer>();
		Stack<Integer> stack1 = new Stack<Integer>();
		int pos = t;
		if(from[pos] == V) return stack; // no path found
		stack.push(pos);
		while(pos != this.s){
			pos = from[pos];
			stack.push(pos);
		}
		StdOut.println(stack.size());
		while(!stack.isEmpty()){
			stack1.push(stack.pop());
		}
		return stack1;
	}
	
	/**
	 * # of iterations in algorithms
	 * @return # of iterations in algorithms
	 */
	public int numIterations(){
		return this.iterations;
	}
	
	/**
	 * return the pq that contains frontier
	 * @return priority queue of frontier points
	 */
	public PriorityQueue<Integer> getPQ(){
		return this.pq;
	}
	
	/**
	 * return the points set
	 * @return the tree Set 
	 */
	public Vector<Integer> getSet(){
		return this.treeSet;
	}
	
	/**
	 * return the list which keep track of the path
	 * @return int list.
	 */
	public int[] getFrom(){
		return this.from;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WeightedGraph g = new WeightedGraph("RandomGraph.txt");
		//WeightedGraph g = new WeightedGraph(125, 400);
		Draw draw = g.display();
		Dijkstra d = new Dijkstra(g,1,5);
		d.compute();
		Stack<Integer> list = d.path();
		draw.setPenColor(Color.BLUE);
		draw.setPenRadius(0.015);
		Iterator<Integer> iterator = d.treeSet.iterator(); // draw expanded points
		StdOut.println("size of expanded points: ");
		StdOut.println(d.treeSet.size());
		while(iterator.hasNext()){
			Point p = g.getPoint(iterator.next());
			draw.point(p.getX(), p.getY());
		}
		StdOut.print("Path: ");     // output and draw path on graph
		StdOut.print(list.toString());
		draw.setPenRadius(0.005);
		draw.setPenColor(Color.RED);
		if (!list.isEmpty()){  // output queue if path exists
			int p = list.pop();
			while(!list.isEmpty()){
				int q = list.pop();
				draw.line(g.getPoint(p).getX(), g.getPoint(p).getY(), 
						  g.getPoint(q).getX(), g.getPoint(q).getY());
				p = q;
			}
		}
		StdOut.print("\nThe num of Iterations: ");
		StdOut.println(d.numIterations());
	}

}
