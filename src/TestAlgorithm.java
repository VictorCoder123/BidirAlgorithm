import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 
 */

/**
 * @author Qishen Zhang
 *
 */
public class TestAlgorithm {
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
		StdOut.println("size of total expanded points: ");
		StdOut.println(bi.getSet1().size()+bi.getSet2().size());
		while(iterator2.hasNext()){
			Point p = g.getPoint(iterator2.next());
			draw.point(p.getX(), p.getY());
		}
		if(bi.getIntersection()!=-1){
			draw.setPenColor(Color.YELLOW); //hightlight intersection point
			draw.setPenRadius(0.025);
			Point p = g.getPoint(bi.getIntersection());
			draw.point(p.getX(), p.getY());
			StdOut.println("Intersection point: ");
			StdOut.println(bi.getIntersection());
		}
		
		StdOut.print("Path: ");     // output and draw path on graph
		StdOut.print(list.toString());
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
		StdOut.print("\nThe num of Iterations: ");
		StdOut.println(bi.numIterations());

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("random weight graph");
		String filename = "RandomGraph.txt";
		WeightedGraph g = new WeightedGraph(filename);
		TestAlgorithm.testBidir(g, 1, 5);
		System.out.println("\n\nGraph with weight related to coordinates");
		g.resetWeight();
		TestAlgorithm.testBidir(g, 1, 5);
		
	}

}
