// Title: Driver Class
// Author: Ahmet Can Öztürk
// Assignment: 1
// Description: This class is the driver class; it provides the create graph object and takes input 
// and reads a text file. It also calls the important functions convert(), findShortestPath(), addEdges() and removeEdge().
//-----------------------------------------------------
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;

public class Main {

	public static void main(String[] args) throws IOException {

		// Reading inputs by scanner
		Scanner scanner = new Scanner(System.in);
		StringBuilder inputBuilder = new StringBuilder();
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (line.isEmpty()) {
				break; // stop reading if input is empty
			}
			inputBuilder.append(line).append("\n");
		}

		// Writes the input received with the scanner via bufferedwriter to a txt file
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("input.txt"))) {
			writer.write(inputBuilder.toString());
		} catch (IOException e) {
			System.err.println("Error writing to file: " + e.getMessage());
		}

		// We read the input.txt file by BufferedReader and then I keep the information
		// in the linked list.
		BufferedReader inFile = new BufferedReader(new FileReader("input.txt"));

		String[] veri;
		String nextLine;
		int counter = 0;
		LinkedList<Integer> list = new LinkedList<>();

		while ((nextLine = inFile.readLine()) != null) {
			veri = nextLine.trim().split("\\s+");
			list.addLast(Integer.parseInt(veri[0]));
			list.addLast(Integer.parseInt(veri[1]));

			counter++;
		}

		// We created empty graph which has only vertices according to input
		Graph g = new Graph(list.size() - 2); // size can be change it was counter-2

		int end = list.remove((list.size() - 1)); // take the end vertex (Y-City)
		int start = list.remove(list.size() - 1); // take the start vertex (X-City)
		
		//System.out.println(start);
		//System.out.println(end);
		
		g.setV(list.removeFirst());
		g.setE(list.removeFirst()); // First I am holding the datas in linkedlist and then I took from it the times
									// and nof vertices and edges value.
		

		// New array to hold U-V city directions then put into array from list.
		int arr[] = new int[(counter - 2) * 2];
		for (int i = 0; i < list.size(); i++) {
			arr[i] = list.get(i);
		}

		// I took integers one by one index so in order to add edges I getting them a
		// couple (U-V) thanks by mod(2)
		for (int i = 0; i < arr.length; i++) {
			if (i % 2 == 0) {
				g.addEdge(arr[i] - 1, arr[i + 1] - 1); // I am taking data from array and adding edges on my graph
														// according to input
			}

		}

		// Creating adjacency matrix of my graph
		

		int adjacencyMatrix[][] = g.convert(g.getAdj(), g.getV());


		// Call the method that has the algorithm for determining the shortest path
		// between two vertices.
		int result[] = g.findShortestPath(adjacencyMatrix, start - 1, end-1);

		
		for (int e : result) {
        //    System.out.print(e+1 + " ");  THE first path prints
    }
		
	   	// I am taking start node and second node on the first path
		// Then I removed the edge between these nodes. 
		// Again when I called the same function its dont came back same route
		// Unlike follow other path so these 2 path complete the circle which includes end node
	
		g.removeEdge(start-1,result[1]);
		
		adjacencyMatrix = g.convert(g.getAdj(), g.getV());
		

		int result2[] = g.findShortestPath(adjacencyMatrix,end-1, start-1);
		
		/*for (int e : result2) {
            System.out.print(e+1 + " "); Prints THE SECOND path to check
        }*/
			Set<Integer> set = new HashSet<>();				// Use hash set remove duplicates elements

		for (int i = 0; i < result.length; i++) {
		    set.add(result[i]+1);
		}
		for (int i = 0; i < result2.length; i++) {
		    set.add(result2[i]+1);
		}

	    // Create a new array from the set
	    int[] arrWithoutDuplicates = new int[set.size()];
		int index = 0;
		for (int element : set) {
		    arrWithoutDuplicates[index++] = element;
		}
		
        Arrays.sort(arrWithoutDuplicates); // Sort to array 
        
		for (int e : arrWithoutDuplicates) {    // Prints output
            System.out.print(e + " ");
		}

	}

}
