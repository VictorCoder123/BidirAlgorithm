/**  
 * A directed graph with weighted edges, the constructor take a .txt file as input
 * to build graph using adjacency list. self loop is not allowed in the graph.
 * 
 * graph.txt
 * 4 6
 * 
 * 0.12 0.12 
 * 0.12 0.15
 * 0.12 0.14
 * 0.12 0.13
 * 
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
	WeightedGraph(int V){
		if(V<0) throw new IllegalArgumentException("Exceed Bound!");
		this.V = V;
		this.E = 0;
		this.vList = new Point[V];
		this.adjLists = (Vector<DirectedEdge>[]) new Vector[V];
		for(int v=0; v<V; v++){
			adjLists[v] = new Vector<DirectedEdge>();
			vList[v] = new Point(v);
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
					double x = scanner.nextDouble();
					double y = scanner.nextDouble();
					this.vList[v] = new Point(x,y,v);
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
	
	/**
	 * create a reverse graph
	 * @return a new reverse graph
	 */
	@SuppressWarnings("unchecked")
	public WeightedGraph reverse(){
		WeightedGraph wg = new WeightedGraph(this.V);
		wg.vList = this.vList; //replace point list with original point list
		wg.adjLists = (Vector<DirectedEdge>[]) new Vector[this.V]; //ignore randomly created edges
		for(int v=0; v<this.V; v++){
			wg.adjLists[v] = new Vector<DirectedEdge>();
		}
		for(int v=0; v<this.V; v++){
			Iterator<DirectedEdge> iterator = this.adjLists[v].iterator();
			while(iterator.hasNext()){
				wg.addEdge(iterator.next().reverse()); 
			}
		}
		return wg;
	}
	
	/**
	 * add edge to the graph
	 * @param e
	 */
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
	
	/**
	 * Given a integer, return the corresponding Point
	 * @param v
	 * @return Point object
	 */
	public Point getPoint(int v){
		if(v<0||v>=this.V)throw new IllegalArgumentException("Exceed Bound!");
		return vList[v];
	}
	
	public void resetWeight(){
		for(int v=0; v<this.V; v++){
			for(int i=0; i<this.adjLists[v].size(); i++){
				DirectedEdge edge = this.adjLists[v].elementAt(i);
				Double weight = edge.from().distance(edge.to())*(Math.random()*0.5+1);
				DirectedEdge newEdge = new DirectedEdge(edge.from(),edge.to(),weight);
				this.adjLists[v].set(i, newEdge);
			}
		}
	}
	
	/**
	 * Draw points and lines to represent graph
	 */
	public Draw display(){
		Draw draw = new Draw("Directed Graph");
		draw.setPenRadius(0.01);
		for(int v=0; v<this.V; v++){
			draw.point(this.vList[v].getX(),this.vList[v].getY());
			draw.text(this.vList[v].getX()+0.02,this.vList[v].getY()+0.02, Integer.toString(this.vList[v].getNum()));
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
		return draw;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WeightedGraph g = new WeightedGraph("RandomGraph.txt");
		g.reverse().display();

	}

}
