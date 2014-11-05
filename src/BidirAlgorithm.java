/**
 * find shortest path using Bi-Dijkstra algorithm
 */

import java.awt.Color;
import java.util.Vector;
import java.util.Queue;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Iterator;

/**
 * @author Qishen Zhang
 *
 */
public class BidirAlgorithm {
	private int s;
	private int t;
	private int intersection;
	private int iterations;
	private int[] from1;
	private int[] from2;
	/**
	 * Constructor of Bi-Dijkstra's algorithm, set t larger than V
	 * if need to all shortest path from s.
	 * @param g
	 * @param s
	 * @param t
	 */
	BidirAlgorithm(WeightedGraph g1, int s, int t){
		this.s = s;
		this.t = t;
		WeightedGraph g2 = g1.reverse();
		Dijkstra d1 = new Dijkstra(g1,s,t);
		Dijkstra d2 = new Dijkstra(g2,t,s);
		while(hasIntersection(d1.getSet(),d2.getSet())==-1){
			d1.expand();
			d2.expand();
		}
		this.intersection = hasIntersection(d1.getSet(),d2.getSet());
		this.from1 = d1.getFrom();
		this.from2 = d2.getFrom();
		this.iterations = d1.numIterations()+d2.numIterations();
	}
	
	/**
	 * check if two sets have intersection
	 * @param a
	 * @param b
	 * @return if true the intersected item, otherwise -1.
	 */
	public int hasIntersection(Vector<Integer> a,Vector<Integer> b){
		Iterator<Integer> iterator1 = a.iterator();
		Iterator<Integer> iterator2 = b.iterator();
		while(iterator1.hasNext()){
			int num1 = iterator1.next();
			while(iterator2.hasNext()){
				int num2 = iterator2.next();
				if(num1 == num2){
					return num1;
				}
			}
		}
		return -1;
	}
	
	public LinkedList<Integer> path(){
		Stack<Integer> stack1 = new Stack<Integer>();
		Queue<Integer> queue2 = new LinkedList<Integer>(); //Queue is a interface
		int pos = this.intersection;
		queue2.add(pos);   //queue inter -> t
		while(pos != this.t){
			pos = from2[pos];
			queue2.add(pos);
		}
		pos = this.intersection;
		stack1.add(pos);   //stack s -> inter
		while(pos != this.s){
			pos = from1[pos];
			stack1.add(pos);
		}
		LinkedList<Integer> list = new LinkedList<Integer>();
		while(!stack1.isEmpty()){
			list.add(stack1.pop());
		}
		queue2.poll(); //avoid adding intersection point twice
		while(!queue2.isEmpty()){
			list.add(queue2.poll());
		}
		return list;
	}
	
	/**
	 * # of iterations in algorithms, the sum of the two search
	 * @return # of iterations in algorithms
	 */
	public int numIterations(){
		return this.iterations;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//WeightedGraph g = new WeightedGraph("graph.txt");
		WeightedGraph g = new WeightedGraph(25, 200);
		Draw draw = g.display();
		BidirAlgorithm bi = new BidirAlgorithm(g,0,4);
		LinkedList<Integer> list = bi.path();
		draw.setPenColor(Color.BLUE);
		draw.setPenRadius(0.015);
		/*Iterator<Integer> iterator = d.treeSet.iterator(); // draw expanded points
		StdOut.println("size of expanded points: ");
		StdOut.println(d.treeSet.size());
		while(iterator.hasNext()){
			Point p = g.getPoint(iterator.next());
			draw.point(p.getX(), p.getY());
		}*/
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

}
