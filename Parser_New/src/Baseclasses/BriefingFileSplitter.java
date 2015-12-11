package Baseclasses;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

/***
 * 
 * Will take a list of Strings and save each of them into a seperate file
 * 
 * @author Alexander Brueckner
 * @version 1.0
 * @since 1.0
 */
public class BriefingFileSplitter {

	private ArrayList<String> chunks;

	/***
	 * 
	 * Constructor, takes an ArrayList
	 * 
	 * @param chunks
	 *            - ArrayList of type String
	 * @Exception IllegalArgumentException
	 * 
	 */

	public BriefingFileSplitter(ArrayList<String> chunks) {
		if (chunks == null || chunks.isEmpty()) {
			throw new IllegalArgumentException("List is null");
		}

		else {
			this.chunks = chunks;
		}

	}

	/**
	 *
	 * generateFiles
	 * 
	 * @param ending
	 *            - desired file ending. Files will be Enumerated and the ending
	 *            is attached. Defaults to .txt incase a null or empty String is
	 *            passed. Must start with a dot and end on a letter or numeric
	 *            character.
	 * @return Number of files saved (int)
	 * @throws IOException
	 **/

	public int generateFiles(String ending) {
		if (this.chunks == null || this.chunks.isEmpty()) {
			System.err.println("List is empty, files cannot be generated");
			return 0;
		}

		String end;
		if (ending == null || ending.equals("")) {

			System.err
					.println("Ending did not match Specifications, defaulting to .txt");
			end = ".txt";
		}
		//matches first character is the only dot in the string
		if (!(ending.matches("(^[.]{1}[a-zA-Z0-9]{1,}$)"))) {
			System.err
					.println("Ending did not match Specifications, defaulting to .txt");
			end = ".txt";
		}
		end = ending;
		int numFiles = 0;
		//One file per list entry
		for (numFiles = 0; numFiles < this.chunks.size(); numFiles++) {

			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(
						"Output\\logentry" + numFiles + end));
				writer.write(chunks.get(numFiles));
				writer.flush();
				writer.close();
			} catch (IOException ioE) {
				ioE.printStackTrace();
				return numFiles;
			}
		}
		return numFiles;
	}

}
