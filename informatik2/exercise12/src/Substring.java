import java.util.ArrayList;

public class Substring {
	
	String sourceString = "";
	//Dictionary dict = new Dictionary();
	
	Substring(String stringTemp) {
		sourceString = stringTemp;
	}
	public ArrayList<String> getSubstrings() {
		
		ArrayList<String> returnList = new ArrayList<String>();
		
		for(int i = 0; i < sourceString.length(); i++) {
			for(int j = i + 1; j <= sourceString.length(); j++) {
				if(!returnList.contains(sourceString.substring(i, j)))
				returnList.add(sourceString.substring(i, j));
			}
		}
		return returnList;
	}

}
