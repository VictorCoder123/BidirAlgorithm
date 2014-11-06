/**
 * Given V and E, randomly generate a graph
 * and output it into txt file.
 * 
 * The output format is like:
 * 5 8
 * 0-2 0.3 0.23 0.45
 * ...
 * 2-3 0.4 0.92 0.32
 * 
 * The first two integers represent V and E,
 * then first double number is weight, the other
 * two double number is x-y coordinates of the 
 * corresponding Point.
 */

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
/**
 * @author Qishen Zhang
 *
 */
public class GraphGenerator {
	
	public static void createGraph(int V, int E, String s){
		File file = new File(s);
		try {
			file.createNewFile();
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(String.valueOf(V)+" "+String.valueOf(E)+"\n");
			for(int v=0; v<V; v++){
				double x = Math.random();
				double y = Math.random();
				String point = new String(String.valueOf(x)+" "+String.valueOf(y)+"\n");
				bw.write(point);
			}
			for(int e=0; e<E; e++){
				int v1 = (int) (Math.random()*V);
				int v2 = (int) (Math.random()*V);
				double weight = Math.random();
				String string = new String(String.valueOf(v1)+" "+String.valueOf(v2)+" "
						+String.valueOf(weight)+"\n");
				bw.write(string);
			}
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Successfully write graph to file: "+ s);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GraphGenerator.createGraph(100, 400, "RandomGraph.txt");
	}

}
