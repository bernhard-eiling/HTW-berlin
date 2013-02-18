import java.util.PriorityQueue;

public class test {

	static Dictionary d7 = new Dictionary("scrabbleWords.txt");
	//static Dictionary d3 = new Dictionary("3letterWords.txt");
	static Dictionary d5 = new Dictionary("5letterWords.txt");
	

	public void testStatistic() {
		d7.print();
	}

	public void testStatistic5() {
		d5.print();
	}


	public void testSearch() {
		System.out.println();
		System.out.println("Test for search 'SHTICKS' : ");
		PriorityQueue<String> pq = d7.search("SHTICKS");
		for (String str : pq) {
			System.out.println(str);
		}
		System.out.println();
	}
	

	public void testPermutation() {
		System.out.println();
		System.out.println("Test for getPermutations 'SHTICKS' : ");
		PriorityQueue<String> pq = d7.getPermutations("SHTICKS");
		if (pq.isEmpty()) {
			System.out.println("no permutations found!");
		} else {
			for (String str : pq) {
				System.out.println(str);
			}
		}
		System.out.println();
	}
	

	public void testPermutation2() {
		System.out.println();
		System.out.println("Test for getPermutations 'IMPRESE' : ");
		PriorityQueue<String> pq = d7.getPermutations("IMPRESE");
		if (pq.isEmpty()) {
			System.out.println("no permutations found!");
		} else {
			for (String str : pq) {
				System.out.println(str);
			}
		}
		System.out.println();
	}
	
	public void testPermutation5letter() {
	System.out.println();
	System.out.println("Test for getPermutations 'ENTER' : ");
	PriorityQueue<String> pq = d5.getPermutations("ENTER");
	if (pq.isEmpty()) {
		System.out.println("no permutations found!");
	} else {
		for (String str : pq) {
			System.out.println(str);
		}
	}
	System.out.println();
}
	
//	public void testPermutation3letter() {
//		System.out.println();
//		System.out.println("Test for getPermutations 'HIF' : ");
//		PriorityQueue<String> pq = d3.getPermutations("HIF");
//		if (pq.isEmpty()) {
//			System.out.println("no permutations found!");
//		} else {
//			for (String str : pq) {
//				System.out.println(str);
//			}
//		}
//		System.out.println();
//	}
	
	public void testGenerateWord() {
		
	}
	
	
}
