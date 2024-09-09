//-----------------------------------------------------
// Title: Graph Class
// Author: Ahmet Can Öztürk
// Assignment: 1
// Description: A class representing a graph's implementation and methods. Additionally, it includes the findShortestPath method implementation.
//-----------------------------------------------------
import java.util.*;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Graph {
	private int V;
	private static final int NO_PARENT = -1;
	private int E;
	private Bag<Integer>[] adj;
	public int time;
	public int cTime;

	static int INF = Integer.MAX_VALUE;

    // get Number of vertices
	public int getV() {
		return V;
	}
	// get Number of edges
	public int E() {
		return E;
	}
    
	public void setV(int v) {
		V = v;
	}
    
	public Bag<Integer>[] getAdj() {
		return adj;
	}

	public void setAdj(Bag<Integer>[] adj) {
		this.adj = adj;
	}

	public void setE(int e) {
		E = e;
	}
    
    //  Default constructer which creates empty graph with number of v Vertices.
	public Graph(int V) {
		this.V = V;
		this.E = 0;
		adj = (Bag<Integer>[]) new Bag[V];
		for (int v = 0; v < V; v++)
			adj[v] = new Bag<Integer>();
	}

	public void addEdge(int v, int w) {
	//--------------------------------------------------------
    //Summary: Assigns a new edge between two vertices in the graph.
    //Precondition: v and w are valid indices within the graph.
    //Postcondition: The graph now contains an edge between vertices v and w.
    //--------------------------------------------------------
		adj[v].add(w); // I added -1 to vertices
		adj[w].add(v);
		E++;
	}

	public Iterable<Integer> adj(int v) {
		return adj[v];
	}
    
    // This method provides for checking the course of events, so it doesn't affect code working.
    // Showing edges connections
	/*public String toString() 
	{
		String s = V + " vertices, " + E / 2 + " edges\n"; // I divide E to 2 because its not directed graph
		for (int v = 0; v < V; v++) {
			s += (v + 1) + ": "; // I added v + 1
			for (int w : this.adj(v))
				s += (w + 1) + " "; // I added w + 1 to show start 1 like in input instead of start 0
			s += "\n";
		}
		return s;
	}*/
    
    // This method provides for checking the course of events, so it doesn't affect code working.
    // gives degrees of the vertex
    /*
	public static int degree(Graph G, int v) 
	{
		int degree = 0;
		for (int w : G.adj(v))
			degree++;
		return degree;
	}*/

	public static int[][] convert(Bag<Integer> adj[], int V) {
    //--------------------------------------------------------
    // Summary: Converts an adjacency list representation of a graph to an adjacency matrix representation
    // Precondition: adj is an array of Bag objects representing the adjacency list of a graph, where adj[i] is a Bag object containing the vertices adjacent to vertex i. V is an integer representing the number of vertices in the graph.
    // Postcondition: Returns a 2D integer array representing the adjacency matrix of the graph.
    //--------------------------------------------------------
		int[][] matrix = new int[V][V];

		for (int i = 0; i < V; i++) {
			for (int j : adj[i])
				matrix[i][j] = 1;
		}
		return matrix;
	}

    // This method provides for checking the course of events, so it doesn't affect code working.
    // It provides to print adj matrix
	/*. public static void printMatrix(int[][] adj, int V) {
		for (int i = 0; i < V; i++) {
			for (int j = 0; j < V; j++) {
				System.out.print(adj[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}*/


	public static int[] findShortestPath(int[][] graph, int start, int end) {
    //--------------------------------------------------------
    // Summary: Finds the shortest path between two nodes in a graph
    // Precondition: graph is a two-dimensional array representing the graph, where graph[i][j] represents the weight of the edge between nodes i and j. start and end are integers representing the start and end nodes, respectively.
    // Postcondition: Returns an integer array representing the shortest path from start to end.
    //--------------------------------------------------------
        int n = graph.length;
        
        int[] distances = new int[n];  // distances from start vertex to each vertex
        boolean[] visited = new boolean[n];  // flag for whether each vertex has been visited
        int[] predecessors = new int[n];  // predecessor for each vertex on the shortest path
        
        // initialize distances to infinity and predecessors to -1
        Arrays.fill(distances, INF);
        Arrays.fill(predecessors, -1);
        
        distances[start] = 0;  // distance to starting vertex is 0
        
        // repeat until all vertices have been visited or end vertex is reached
        while (!visited[end]) {
            // find the unvisited vertex with the smallest tentative distance
            int minDist = INF;
            int curr = -1;
            for (int i = 0; i < n; i++) {
                if (!visited[i] && distances[i] < minDist) {
                    minDist = distances[i];
                    curr = i;
                }
            }
            
            // if all remaining vertices are inaccessible, no path exists
            if (curr == -1) {
                return null;
            }
            
            visited[curr] = true;  // mark current vertex as visited
            
            // update distances for all neighbors of the current vertex
            for (int neighbor = 0; neighbor < n; neighbor++) {
                int weight = graph[curr][neighbor];
                if (weight != 0 && !visited[neighbor]) {
                    int dist = distances[curr] + weight;
                    if (dist < distances[neighbor]) {
                        distances[neighbor] = dist;
                        predecessors[neighbor] = curr;
                    }
                }
            }
        }
        
        // reconstruct the shortest path using the predecessor array
        List<Integer> path = new ArrayList<>();
        int curr = end;
        while (curr != -1) {
            path.add(curr);
            curr = predecessors[curr];
        }
        Collections.reverse(path);
        
        // convert the path list to an array and return it
        int[] result = new int[path.size()];
        for (int i = 0; i < path.size(); i++) {
            result[i] = path.get(i);
        }
        return result;
    }
}
