/**
 * find shortest path using Bi-Dijkstra algorithm
 */

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
	private Vector<Integer> set1; //expanded points in the process
	private Vector<Integer> set2;
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
		/*if pq is empty, then it means the rest of vertices are not reachable*/
		while(hasIntersection(d1.getSet(),d2.getSet())==-1 && 
			!d1.getPQ().isEmpty() && !d2.getPQ().isEmpty()){ 
			d1.expand();
			d2.expand();
		}
		this.intersection = hasIntersection(d1.getSet(),d2.getSet());
		this.from1 = d1.getFrom();
		this.from2 = d2.getFrom();
		this.iterations = d1.numIterations()+d2.numIterations();
		this.set1 = d1.getSet();
		this.set2 = d2.getSet();
	}
	
	/**
	 * check if two sets have intersection
	 * @param a
	 * @param b
	 * @return if true the intersected item, otherwise -1.
	 */
	public int hasIntersection(Vector<Integer> a,Vector<Integer> b){
		Iterator<Integer> iterator1 = a.iterator();
		while(iterator1.hasNext()){
			int num1 = iterator1.next();
			Iterator<Integer> iterator2 = b.iterator();
			while(iterator2.hasNext()){
				int num2 = iterator2.next();
				if(num1 == num2){
					return num1;
				}
			}
		}
		return -1;
	}
	
	/**
	 * Combine the two path into one path from s to t
	 * @return LinkedList consisting of the whole path
	 */
	public LinkedList<Integer> path(){
		Stack<Integer> stack1 = new Stack<Integer>();
		Queue<Integer> queue2 = new LinkedList<Integer>(); //Queue is a interface
		LinkedList<Integer> list = new LinkedList<Integer>();
		int pos = this.intersection;
		if(pos != -1){ // -1 -> no intersection and no solution found
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
			while(!stack1.isEmpty()){
				list.add(stack1.pop());
			}
			queue2.poll(); //avoid adding intersection point twice
			while(!queue2.isEmpty()){
				list.add(queue2.poll());
			}
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
	 * return intersection
	 * @return intersection
	 */
	public int getIntersection(){
		return this.intersection;
	}
	
	/**
	 * @return set1 
	 */
	public Vector<Integer> getSet1(){
		return this.set1;
	}
	
	/**
	 * @return set2
	 */
	public Vector<Integer> getSet2(){
		return this.set2;
	}

}
