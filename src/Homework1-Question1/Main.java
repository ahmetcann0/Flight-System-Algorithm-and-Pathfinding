//-----------------------------------------------------
// Title: Driver Class
// Author: Ahmet Can Öztürk
// Assignment: 1
// Description: This class is the driver class; it provides the create graph object and takes input 
// and reads a text file. It also calls the important functions convert(), findShortestPath() and addEdges().
//-----------------------------------------------------
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

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
			// System.out.print(veri[0]+ " "); // check
			// System.out.println(veri[1]); // check
			list.addLast(Integer.parseInt(veri[0]));
			list.addLast(Integer.parseInt(veri[1]));
			if (veri.length >= 3) {
				list.addLast(Integer.parseInt(veri[2]));
				list.addLast(Integer.parseInt(veri[3]));
			}

			counter++;
		}

		// We created empty graph which has only vertices according to input
		Graph g = new Graph(list.size() - 2); // size can be change it was counter-2

		int end = list.remove((list.size() - 1)); // take the end vertex (Y-City)
		int start = list.remove(list.size() - 1); // take the start vertex (X-City)

        // I am holding the data in linkedlist, and then I took from it the times and number of vertices and edges value.
		g.setV(list.removeFirst());
		g.setE(list.removeFirst()); 
		g.time = list.removeFirst();
		g.cTime = list.removeFirst();

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
		int result[] = g.findShortestPath(adjacencyMatrix, start - 1, end - 1);

		// Calculate times and then print output.
		System.out.println(result.length);

		for (int i = 0; i < result.length; i++) {
			System.out.print((result[i] + 1) + " ");
		}

	    int totalTime = (((g.time-(g.cTime - g.time)) * (result.length-2)) + g.cTime*(result.length - 1)); // calculated by niloyloy 
		System.out.println("\n" + totalTime);

	}

}
