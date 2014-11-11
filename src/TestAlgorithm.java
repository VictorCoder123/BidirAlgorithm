/**
 * Test class for Dijkstra and Bi-Directional Algorithms
 */

import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

/**
 * @author Qishen Zhang
 *
 */
public class TestAlgorithm {
	
	/**
	 * Test Dijkstra's algorithm and display the result
	 * the red line is the path of shortest path, the blue points
	 * are the points expanded from s.
	 * @param g
	 * @param s
	 * @param t
	 */
	public static void testDijkstra(WeightedGraph g, int s, int t){
		Draw draw = g.display();
		Dijkstra d = new Dijkstra(g,1,5);
		d.compute();
		Stack<Integer> list = d.path();
		draw.setPenColor(Color.BLUE);
		draw.setPenRadius(0.015);
		Iterator<Integer> iterator = d.getSet().iterator(); // draw expanded points
		System.out.println("size of total expanded points: ");
		System.out.println(d.getSet().size());
		while(iterator.hasNext()){
			Point p = g.getPoint(iterator.next());
			draw.point(p.getX(), p.getY());
		}
		System.out.print("Path: ");     // output and draw path on graph
		System.out.print(list.toString());
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
		System.out.print("\nThe num of Iterations: ");
		System.out.println(d.numIterations());
	}
	
	/**
	 * Test Bidirectional algorithms and display the result
	 * the red line is the path of shortest path, the blue points
	 * are the points expanded from s, the green points are points 
	 * expanded from t, one yellow points is the the intersection 
	 * point when the two sets have one same point.
	 * @param g
	 * @param s
	 * @param t
	 */
	public static void testBidir(WeightedGraph g, int s, int t){
		Draw draw = g.display();
		BidirAlgorithm bi = new BidirAlgorithm(g,s,t);
		LinkedList<Integer> list = bi.path();
		draw.setPenColor(Color.BLUE);  
		draw.setPenRadius(0.015);
		Iterator<Integer> iterator1 = bi.getSet1().iterator(); // draw expanded points from s
		while(iterator1.hasNext()){
			Point p = g.getPoint(iterator1.next());
			draw.point(p.getX(), p.getY());
		}
		
		draw.setPenColor(Color.GREEN);
		draw.setPenRadius(0.015);
		Iterator<Integer> iterator2 = bi.getSet2().iterator(); // draw expanded points from t
		System.out.println("size of total expanded points: ");
		System.out.println(bi.getSet1().size()+bi.getSet2().size());
		while(iterator2.hasNext()){
			Point p = g.getPoint(iterator2.next());
			draw.point(p.getX(), p.getY());
		}
		if(bi.getIntersection()!=-1){
			draw.setPenColor(Color.YELLOW); //hightlight intersection point
			draw.setPenRadius(0.025);
			Point p = g.getPoint(bi.getIntersection());
			draw.point(p.getX(), p.getY());
			System.out.println("Intersection point: ");
			System.out.println(bi.getIntersection());
		}
		
		System.out.print("Path: ");     // output and draw path on graph
		System.out.print(list.toString());
		draw.setPenRadius(0.005);
		draw.setPenColor(Color.RED);
		if (!list.isEmpty()){  // output queue if path exists
			int p = list.removeFirst();
			while(!list.isEmpty()){
				int q = list.removeFirst();
				draw.line(g.getPoint(p).getX(), g.getPoint(p).getY(), 
						  g.getPoint(q).getX(), g.getPoint(q).getY());
				p = q;
			}
		}
		System.out.print("\nThe num of Iterations: ");
		System.out.println(bi.numIterations());

	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("/**Random weight graph**/");
		String filename = "RandomGraph.txt";
		WeightedGraph g = new WeightedGraph(filename);
		
		System.out.println("\n1.Result in Dijkstra's algorithm");
		TestAlgorithm.testDijkstra(g, 1, 5);
		System.out.println("\n2.Result in Bi-Directional algorithm:");
		TestAlgorithm.testBidir(g, 1, 5);
		
		System.out.println("\n\n/**Graph with weight related to coordinates**/");
		g.resetWeight();  //reset new weight to make it related to coordinates.
		System.out.println("\n1.Result in Dijkstra's algorithm");
		TestAlgorithm.testDijkstra(g, 1, 5);
		System.out.println("\n2.Result in Bi-Directional algorithm:");
		TestAlgorithm.testBidir(g, 1, 5);
		
	}

}
