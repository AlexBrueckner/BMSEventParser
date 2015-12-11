package Baseclasses;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Takes a file or filename and reads the contents into a List of Strings. Will
 * delete obsolete whitespaces and generally clean up the format for ease of
 * reading and especially parsing further.
 * 
 * @author Alexander Brueckner
 * @version 1.0
 * @since 1.0
 * 
 * */
public class BriefingFileReader {

	private BufferedReader reader;
	private File file;
	private ArrayList<String> chunks;

	/**
	 * Constructor with String argument Constructs a File based on the specified
	 * path and creates a reader for use
	 * 
	 * @Exception IllegalArgumentException, IOException
	 * @param filename
	 *            String representation of Filename.
	 */

	public BriefingFileReader(String filename) {
		if (filename == null || filename.length() == 0) {
			throw new IllegalArgumentException("Invalid filename specified");
		} else { // this.chunks = new ArrayList<String>();
			this.file = new File(filename);
		}
	}

	/***
	 * Parses the file and chunkates contents into one chunk per mission entry
	 * 
	 * @return Amount of mission chunks parsed successfully, -1 if error occured
	 */
	public ArrayList<String> parse() {

		String holder = "";
		String current = "";
		try {
			this.reader = new BufferedReader(new FileReader(this.file));
			while ((current = this.reader.readLine()) != null) {
				// Filters out gunshots at identical timestamps, reduces load
				// and increases readability
				if (current.equals("")) {
					holder += cleanUp(current).trim();
				} else {

					if (current.contains("LOADOUT")) {
						holder += "##########\n";
					}

					holder += cleanUp(current).trim() + "\n";

				}
			}

			// Kill the Stream
			closeStream();
			// Regex to remove the 3 whitespaces before each chunk and the
			// newline in front of the first loadout
			// which was induced due to the removal of "WEAPON DATA" (hashtags)
			holder = holder.replaceAll(
					"((\n){3}|(([-]{13})\n(WEAPON DATA\n)))", "");

			this.chunks = new ArrayList<String>(Arrays.asList(holder
					.split("(-){56}")));

			// The File will always start with 3 Whitespaces and the separator
			// (56 dashes)
			// This would cause an empty String to sit on top of the list -> BE
			// GONE!
			this.chunks.remove(0);
			return this.chunks;
		}

		catch (IOException ioE) {
			System.err.println("Error: " + ioE.getMessage());
			return null;
		}

	}

	private void closeStream() {
		if (this.reader == null)
			return;

		try {
			this.reader.close();
		} catch (IOException ioE) {
			System.err.println("Error: " + ioE.getMessage());
		}
	}

	public List<String> getChunks() {
		return this.chunks;
	}

	private String addSpacer(String s) {
		return s += "\n-------------\n";
	}

	private String cleanUp(String s) {

		if (s.contains("@72")) {
			s = s.replaceAll("@72", " ");
			s = s.replaceAll("(  hit)", " hit");
		}

		if (s.contains("Other Player Kills")) {
			s = addSpacer(s);
		}

		if (s.contains("Contry:")) {
			s = addSpacer(s);
		}

		return s;
	}

}
