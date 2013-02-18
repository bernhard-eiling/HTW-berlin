import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Dictionary {

	ArrayList<PriorityQueue<String>> scrabbleWords;
	private final int LENGTH = 8000;

	public Dictionary(String source) {
		super();
		scrabbleWords = new ArrayList<PriorityQueue<String>>();
		for (int i = 0; i < LENGTH; i++) {
			scrabbleWords.add(new PriorityQueue<String>());
		}
		generateScrabbleList(source);
	}

	// generate scrabbleList from a file
	private void generateScrabbleList(String source) {
		try {
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(source);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			String tmp = "";
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				tmp += strLine;
			}
			String[] words = tmp.split(" ");
			for (String s : words) {
				// Print the content on the console

				int index = (int) generateHashValue(s);
				scrabbleWords.get(index).offer(s);

			}
			// Close the input stream
			in.close();
		} catch (Exception e) {// Catch exception if any
			e.printStackTrace();
		}
	}

	private long generateHashValue(String strLine) {
		long result = 0;
		String s = normalizeWord(strLine);
		char[] chars = s.toCharArray();
		int l = chars.length;
		for (int i = 0; i < l; i++) {
			result += ((int) chars[i] - 64) * Math.pow(27, i);
		}
		return result % scrabbleWords.size();
	}

	public ArrayList<PriorityQueue<String>> getScrabbleWords() {
		return scrabbleWords;
	}

	public void print() {
		int index = 0;
		int collisions = 0;
		int maxCollision = 0;
		for (PriorityQueue<String> pq : scrabbleWords) {

			collisions += pq.size() - 1;
			System.out.print(index + "/" + pq.size() + " : ");
			for (String str : pq) {
				System.out.print("[" + str + "]");
			}
			if (pq.size() > maxCollision) {
				maxCollision = pq.size();
			}
			System.out.println();
			index++;
		}
		System.out.println();
		System.out.println("collisions: " + collisions);
		System.out.println("max collision: " + maxCollision);
		System.out.println();
	}

	public PriorityQueue<String> search(String word) {
		long index = generateHashValue(word);
		return scrabbleWords.get((int) index);
	}

	public PriorityQueue<String> getPermutations(String word) {
		PriorityQueue<String> result = new PriorityQueue<String>();
		PriorityQueue<String> tmp = search(word);
		for (String s : tmp) {
			if (isPermutation(word, s)) {
				result.offer(s);
			}
		}
		return result;
	}
	
	public String normalizeWord(String word) {
		String result = "";
		
		PriorityQueue<String> pq = new PriorityQueue<String>();
		 
		for (char c : word.toCharArray()) {
			pq.offer("" + c);
		}
		for (String s : pq) {
			result += s;
		}
		
		return result;
	}

	public boolean isPermutation(String string1, String string2) {
		boolean result = false;

		String norm1 = normalizeWord(string1);
		String norm2 = normalizeWord(string2);
		
		result = norm1.equals(norm2);

		return result;
	}

}
