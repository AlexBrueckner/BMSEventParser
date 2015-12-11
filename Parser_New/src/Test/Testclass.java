package Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import Baseclasses.BriefingFileReader;
import Baseclasses.BriefingFileSplitter;

public class Testclass {

	public static void main(String[] args) {
		BriefingFileReader bfR = new BriefingFileReader("debrief.txt");
		bfR.parse();
		ArrayList<String> list = new ArrayList<String>(bfR.getChunks());
		bfR = null;
		System.out.print(list.get(0));
		System.out.print(list.get(1));
		BriefingFileSplitter bfS = new BriefingFileSplitter(list);
		bfS.generateFiles(".log");

		// for (int i = 0; i < list.size(); i++) {
		// System.out.println(list.get(i));
		// }
	}

}
