Question:
In this program, I want to see the behavior of the bidirectional 
shortest path algorithm under certain assumptions of random input.
Therefore, I want you to start by programming the bidirectional
shortest path algorithm as well as Dijkstra's algorithm
(you should not use any code from a book).  I want you also to count the 
number of iterations before a vertex is
in the set reached from both s and t.

In the bidirectional search algorithm, you run Dijkstra's algorithm from both s (growing a set S), and t (growing a set T).  When some vertex is in both S and T, the shortest path from s to t is found by looking at all edges from S to T, and choosing the edge which gives the least total cost of the path (i.e. distance s to x plus weight of x,y plus distance y to t)

You should then run experiments to determine expected number of
iterations if the input is a) a graph with random edge weights. b) a
graph in which vertices are points randomly chosen in the plane,
and edge weight from x to y = (distance from x to y) + a random variable
between 0 and .5 (distance between x and y).
What conclusions do you reach about the expected running time? Present your 
results clearly.

/*************************************************************************/

Clarification: The program runs on TestAlgorithm.java in standard JDK1.8 library.
All of the codes are my own works, except two file Draw.java and DrawListener from third party libraries that are only used to draw the graph and path and are totally irrelevant to the implementation of algorithms. The source of these two GUI libraries is clearly cited in the file.

Point.java: represent a point with x-y coordinates and a global identifier num

DirectedEdge.java: represent a directed edge which contains start point, end point and the weight.

WeightedGraph.java: represent a weighted graph with directed edge.

GraphGenerator.java: Given V and E, randomly generate a graph with random weight and position, then store it to RandomGraph.txt on main directory.

Dijkstra.java: Given a graph and start/end point, compute the shortest path using Dijkstra algorithm.

BidirAlgorithm.java: Given a graph and start/end point, reuse the code in Dijkstra class to compute the shortest path using Bidirectional algorithm.

TestAlgorithm.java: try both Dijkstra and Bidirectional algorithms on two different kinds of graph input and output the result. 4 graph are created to display the result that red line is the path of shortest path, the blue points are the points expanded from s, the green points are points expanded from t, one yellow points is the the intersection point when the two sets have one same point, the black points are the unexplored points.


Result from program: 

/**Random weight graph**/

1.Result in Dijkstra's algorithm
size of total expanded points: 
98
Path: [1, 65, 12, 34, 64, 24, 30, 86, 5]
The num of Iterations: 131

2.Result in Bi-Directional algorithm:
size of total expanded points: 
22
Intersection point: 
64
Path: [1, 65, 12, 34, 64, 24, 30, 86, 5]
The num of Iterations: 68


/**Graph with weight related to coordinates**/

1.Result in Dijkstra's algorithm
size of total expanded points: 
95
Path: [1, 65, 12, 80, 73, 51, 79, 30, 86, 5]
The num of Iterations: 127

2.Result in Bi-Directional algorithm:
size of total expanded points: 
36
Intersection point: 
77
Path: [1, 65, 12, 88, 77, 48, 86, 5]
The num of Iterations: 103



Analysis:
Obviously the Bidirectional algorithm expands less vertices and uses less iterations than Dijkstra’s algorithm.

In both Bidirectional and Dijkstra’s algorithm, the queue operation uses the priority queue to pop out the next vertices with shortest distance from s, as the structure of the priority queue is heap, so it takes O(log N) for every queue operation.

So the worst running time for Dijkstra’s algorithm is O(N*logN), when all vertices are visited before the end point t is reached.
The expected running time for Bidirectional algorithm is O(N^1/2*logN), as the expected number of vertices visited is O(N^1/2), but in worst case the running time is twice of the running time of Dijkstra algorithm when the intersection point is the start point or end point, so the worst running is also O(N*logN).

