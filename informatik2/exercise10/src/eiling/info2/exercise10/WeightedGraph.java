package eiling.info2.exercise10;

import static java.lang.Math.*;

import java.util.ArrayList;

public class WeightedGraph {

	private int[][] matrix;

	int vert;
	int edge;
	int start;
	int end;
	int[] dist;
	int[] pred;
	ArrayList<Integer> unsettledVert = new ArrayList<Integer>();
	ArrayList<Integer> settledVert = new ArrayList<Integer>();
	ArrayList<Integer> neighbourVert = new ArrayList<Integer>();

	WeightedGraph(int vert, int edge) {
		this.vert = vert;
		this.edge = edge;
		dist = new int[vert];
		pred = new int[vert];
		matrix = generateMatrix(vert, edge);
	}

	void shortestPath() {

		// reset matrix to only distance 1
		for (int i = 0; i < vert; i++) {
			for (int j = 0; j < vert; j++) {
				if (matrix[i][j] != -1) {
					matrix[i][j] = 1;
				}
			}
		}
		cheapestPath();
	}

	// Dijkstra
	void cheapestPath() {

		for (int i = 0; i < vert; i++) {
			dist[i] = Integer.MAX_VALUE;
			pred[i] = -1;
		}

		// set start and end vertices which are connected to the graph and not
		// to themselfs
		for (int i = 0; i < vert; i++) {
			int startCandidate = (int) (random() * vert - 1);
			if (matrix[startCandidate][i] != -1 && startCandidate != i) {
				start = startCandidate;
			}
		}
		
		while (end == start) {
			for (int i = 0; i < vert; i++) {
				int endCandidate = (int) (random() * vert - 1);
				if (matrix[vert - 1 - i][endCandidate] != -1
						&& endCandidate != i && endCandidate != start) {
					end = endCandidate;
				}
			}
		}

		dist[start] = 0;
		unsettledVert.add(start);

		while (!unsettledVert.isEmpty()) {

			int u = getMinimumDist();

			settledVert.add(u);

			for (int i = 0; i < vert; i++) {

				// gather vertices connected to u and vertices not settled
				if (matrix[u][i] != -1 && !settledVert.contains(i)) {
					neighbourVert.add(i);
				}
				if (matrix[i][u] != -1) {
					neighbourVert.add(i);
				}
			}

			for (int i = 0; i < neighbourVert.size(); i++) {

				// getting delta of u and neighbour
				int delta;
				if (matrix[neighbourVert.get(i)][u] == -1) {
					delta = matrix[u][neighbourVert.get(i)];
				} else {
					delta = matrix[neighbourVert.get(i)][u];
				}

				// looking for shorter path and updating dist and pred
				if (dist[neighbourVert.get(i)] > dist[u] + delta) {
					dist[neighbourVert.get(i)] = dist[u] + delta;
					pred[neighbourVert.get(i)] = u;
					unsettledVert.add(neighbourVert.get(i));
				}
			}
			neighbourVert.clear();
		}

		// Print the results
		System.out.println("Start: " + start);
		System.out.println("End: " + end);
		if(dist[end] == Integer.MAX_VALUE) {
			System.out.println("There is no connected path between start and end node.");
		} else {
			System.out.println("The path is " + dist[end] + " units long.");
			// iterate through path
			int nextVert = end;
			while (pred[nextVert] != -1) {
				System.out.print("NODE[" + nextVert + "]");
				int nodeDist = getNodeDist(nextVert, pred[nextVert]);
				System.out.print(" <--" + nodeDist + "--> ");
				nextVert = pred[nextVert];
			}
			System.out.print("NODE[" + nextVert + "]");
		}
	}
	
	int getNodeDist(int node1, int node2) {
		int returnDist;
		if(matrix[node1][node2] != -1) {
			returnDist = matrix[node1][node2];
		} else {
			returnDist = matrix[node2][node1];
		}
		return returnDist;
	}

	int getMinimumDist() {

		int compare = Integer.MAX_VALUE;
		int remove = -1;
		int maxDistVert = -1;

		for (int i = 0; i < unsettledVert.size(); i++) {
			if (compare > dist[unsettledVert.get(i)]) {
				compare = dist[unsettledVert.get(i)];
				maxDistVert = unsettledVert.get(i);
				remove = i;
			}
		}
		unsettledVert.remove(remove);
		return maxDistVert;
	}

	private int[][] generateMatrix(int vert, int edge) {
		int[][] returnMatrix = new int[vert][vert];

		// fill matrix with -0 == no connection
		for (int i = 0; i < vert; i++) {
			for (int j = 0; j < vert; j++) {
				returnMatrix[i][j] = -1;
			}
		}

		// fill matrix with edge times values != -1
		for (int i = 0; i < edge; i++) {
			int row = (int) (random() * vert);
			int col = (int) (random() * vert);

			if (returnMatrix[row][col] == -1) {
				returnMatrix[row][col] = (int) (random() * (80 - 1) + 1);
			} else {
				i--;
			}

		}

		return returnMatrix;
	}

	int getValue(int row, int col) {
		return this.matrix[row][col];
	}

}
