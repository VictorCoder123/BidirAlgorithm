/**
 *find shortest path using Dijkstra's algorithm 
 */

import java.awt.Color;
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
	private double [] dist; // the current shortest distance from vertex to source
	private int [] from;  // the previous vertex of certain vertex on the shortest path
	private boolean [] visited; //true if the vertice is already visited
	private PriorityQueue<Point> pq; //store the frontier of vertices
	
	/**
	 * Comparator for DirectedEdge, compare the distance from 
	 * source to end point of the edge
	 * @author Qishen Zhang
	 */
	public class EdgeComparator implements Comparator<Point>{
		public int compare(Point a, Point b){
			int num1 = a.getNum();
			int num2 = b.getNum();
			/*compare the dist[] of end point of two edges*/
			if(dist[num1] > dist[num2]) return 1;
			else if(dist[num1] < dist[num2]) return -1;
			else return 0;
		}
	}
	
	Dijkstra(WeightedGraph g, int s){
		int V = g.vNum();
		if(s<0||s>=V) throw new IllegalArgumentException("Exceed Bound!");
		this.s = s;
		int smallest = s; //end point of the smallest edge or start point
		visited = new boolean[V];
		dist = new double[V];
		from = new int[V];
		
		for(int v=0; v<V; v++){
			visited[v] = false;
			dist[v] = Double.POSITIVE_INFINITY;
			from[v] = V; // v is not reachable if it still equals to V at last
		}
		dist[s] = 0.0;
		visited[s] = true;
		from[s] = s;
		Comparator<Point> comparator = new EdgeComparator();
		pq = new PriorityQueue<Point>(comparator);
		pq.add(g.getPoint(s));
		// continue until the frontier of vertices is empty
		while(!pq.isEmpty()){
			Iterator<DirectedEdge> iterator = g.adjEdges(smallest);
			/*add all adjacent edges of smallest vertex to pq*/
			while(iterator.hasNext()){
				DirectedEdge e = iterator.next(); // update dist[] in every expansion
				if(dist[e.from().getNum()]+e.weight() < dist[e.to().getNum()]){
					dist[e.to().getNum()] = dist[e.from().getNum()]+e.weight();
					from[e.to().getNum()] = e.from().getNum(); //keep track of previous vertex
				}
				/*update all dist but only add edges with unvisited end point */
				if (!visited[e.to().getNum()]){
					pq.add(e.to());
					visited[e.to().getNum()] = true;
				}
			}
			smallest = pq.poll().getNum(); //end point of the smallest edge
		}
	}
	
	public boolean allVisited(){
		for(int v=0; v<visited.length; v++){
			if(visited[v] == false) return false;
		}
		return true;
	}
	
	public Stack<Integer> path(int t){
		int V = visited.length;
		if(t<0||t>=V) throw new IllegalArgumentException("Exceed Bound!");
		Stack<Integer> stack = new Stack<Integer>();
		int pos = t;
		if(from[pos] == V) return stack;
		stack.push(pos);
		while(pos != s){
			pos = from[pos];
			stack.push(pos);
		}
		return stack;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WeightedGraph g = new WeightedGraph("graph.txt");
		//g.display();
		Dijkstra d = new Dijkstra(g,0);
		Stack<Integer> stack = d.path(6);
		StdOut.print(stack);
		Draw draw = g.display();
		draw.setPenColor(Color.RED);
		int p = stack.pop();
		while(!stack.isEmpty()){
			int q = stack.pop();
			draw.line(g.getPoint(p).getX(), g.getPoint(p).getY(), 
					  g.getPoint(q).getX(), g.getPoint(q).getY());
			p = q;
		}
	}

}
