/**  
 * A directed graph with weighted edges, the constructor take a .txt file as input
 * to build graph using adjacency list. self loop is not allowed in the graph.
 * 
 * graph.txt
 * 4 6
 * 0-2 0.5 
 * 0-3 0.6
 * 1-2 0.4 
 * 1-3 0.9
 * 2-1 0.43
 * 3-2 0.33
 * 
 */

import java.util.Iterator;
import java.util.Vector;
import java.util.Locale;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

/**
 * @author Qishen Zhang
 *
 */
public class WeightedGraph {
	private int V;
	private int E;
	private Point[] vList;
	private Vector<DirectedEdge> [] adjLists;
	
	/**
	 * Constructor for weighted graph given # of vertices and edges
	 * Create random values for vertices number and edge weight
	 * @param V
	 * @param E
	 */
	@SuppressWarnings("unchecked")
	WeightedGraph(int V, int E){
		if(V<0 || E<0) throw new IllegalArgumentException("Accept no negative number for vertices and edges");
		this.V = V;
		this.E = E;
		this.vList = new Point[V];
		this.adjLists = (Vector<DirectedEdge>[]) new Vector[V];
		for(int v=0; v<V; v++){
			adjLists[v] = new Vector<DirectedEdge>();
		}
		for(int e=0; e<E; e++){
			int num1 = (int) Math.random()*V;
			int num2 = (int) Math.random()*V;
			if(vList[num1] == null) vList[num1] = new Point(num1);
			if(vList[num2] == null) vList[num2] = new Point(num2);
			double weight = Math.random();
			DirectedEdge edge = new DirectedEdge(vList[num1],vList[num2],weight);
			addEdge(edge);
		}
	}
	
	/**
	 * Constructor of directed graph given a file name
	 * Create the graph according to the file
	 * @param s
	 */
	@SuppressWarnings("unchecked")
	WeightedGraph(String s){
		File file = new File(s);
		int num1,num2;
		double weight;
		try{
			if (file.exists()){
				Scanner scanner = new Scanner(file,"UTF-8");
				scanner.useLocale(Locale.US);
				this.V = scanner.nextInt();
				this.E = scanner.nextInt();
				this.vList = new Point[this.V];
				this.adjLists = (Vector<DirectedEdge>[]) new Vector[V];
				for(int v=0; v<this.V; v++){
					this.adjLists[v] = new Vector<DirectedEdge>();
				}
				if(V<0 || E<0){
					scanner.close();
					throw new IllegalArgumentException("Negative is not allowed!");
				}
				for(int e=0; e<this.E; e++){
					num1 = scanner.nextInt();
					num2 = scanner.nextInt();
					if(num1>= this.V || num2>= this.V){
						scanner.close();
						throw new IllegalArgumentException("# of vertices exceeds bound!");
					}
					weight = scanner.nextDouble();
					if(vList[num1] == null) vList[num1] = new Point(num1);
					if(vList[num2] == null) vList[num2] = new Point(num2);
					DirectedEdge edge = new DirectedEdge(vList[num1],vList[num2],weight); //p->q
					this.addEdge(edge);	
				}
				scanner.close();
			}	
		}
		catch (IOException ioe) {
            System.err.println("Could not open " + file);
        }
	}
	
	public void addEdge(DirectedEdge e){
		this.adjLists[e.from().getNum()].add(e);
	}
	
	/**
	 * return the number of vertices
	 * @return # of vertices
	 */
	public int vNum(){
		return this.V;
	}
	
	/**
	 * return the number of edges
	 * @return # of edges
	 */
	public int eNum(){
		return this.E;
	}
	
	/**
	 * return adjacent edges of a given vertex
	 * @param v
	 * @return iterator of edges
	 */
	public Iterator<DirectedEdge> adjEdges(int v){
		if(v<0||v>=this.V)throw new IllegalArgumentException("Exceed Bound!");
    	return this.adjLists[v].iterator();
    }
	
	public Point getPoint(int v){
		if(v<0||v>=this.V)throw new IllegalArgumentException("Exceed Bound!");
		return vList[v];
	}
	/**
	 * Draw points and lines to represent graph
	 */
	public void display(){
		Draw draw = new Draw("Directed Graph");
		draw.setPenRadius(0.01);
		for(int v=0; v<this.V; v++){
			draw.point(this.vList[v].getX(),this.vList[v].getY());
		}
		draw.setPenRadius(0.002);
		for(int v=0; v<this.V; v++){
			Vector<DirectedEdge> vector = this.adjLists[v];
			for(int i=0; i<vector.size(); i++){
				DirectedEdge e = (DirectedEdge) vector.elementAt(i);
				Point p1 = e.from();
				Point p2 = e.to();
				draw.line(p1.getX(), p1.getY(), p2.getX(), p2.getY());
			}
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WeightedGraph g = new WeightedGraph("graph.txt");
		g.display();

	}

}
