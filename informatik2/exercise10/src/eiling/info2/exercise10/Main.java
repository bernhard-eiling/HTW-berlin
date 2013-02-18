package eiling.info2.exercise10;

public class Main {

	
	public static void main(String[] args) {
		
		
		
		
		// test WeightedGraph
		
		WeightedGraph graph = new WeightedGraph(30,30);
		
		/*
		int counter = 0;
		
		for(int i = 0; i < 20; i++) {
			for(int j = 0; j < 20; j++) {
				if(graph.getValue(i, j) != 0) {
					counter++;
				}
			}
		}
		System.out.println(counter);
		*/
		
		graph.cheapestPath();
		//graph.shortestPath();
		
	}

}
