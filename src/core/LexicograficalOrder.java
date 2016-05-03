package core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LexicograficalOrder {
	private List<String> firstFileStrings;
	private List<String> secondFileStrings;
	
	public static void main(String[] args) throws IOException {	
		// read the file parameters
		String firstInputFile = args[0];
		String secondInputFile = args[1];
		String outputFile = args[2];
		
		//Load the file line Strings
		LexicograficalOrder lexicograficalOrder = new LexicograficalOrder();        
        lexicograficalOrder.firstFileStrings = lexicograficalOrder.readFileStrings(firstInputFile);
        lexicograficalOrder.secondFileStrings = lexicograficalOrder.readFileStrings(secondInputFile);
        
        //Compare the file Strings
        lexicograficalOrder.compareTwoFileStrings(outputFile);
	}	

	/**
	 * Reads the strings of every line in a file
	 * @param inputFile the file path to read from
	 * @return the list of string in the file
	 */
	private List<String> readFileStrings(String inputFile) {
		List<String> listToReturn = new ArrayList<String>(); 
		try(BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
		    for(String line; (line = br.readLine()) != null; ) {
		    	listToReturn.add(line);
		    }
		}
		catch (IOException ioException) {
			System.out.println("IO Exception in readFileStrings: " + ioException.getMessage());
		}
		return listToReturn;
	}
	
	/**
	 * Compare the two files strings and flush 
	 * the common words in another file
	 * @param outputFileString the file path to write the
	 * compare results
	 * @throws IOException
	 */
	private void compareTwoFileStrings(String outputFileString) throws IOException{
		//Sort the file Strings
        Collections.sort(firstFileStrings);
        Collections.sort(secondFileStrings);
        
        int firstFileIndex = 0;
        int secondFileIndex = 0;
        String previousSavedString = "";
        File outputFile = new File(outputFileString);
        try {
        	//Delete file if already exists
        	outputFile.delete();
        	//Create the new file
			outputFile.createNewFile();
		} catch (IOException ioException) {
			System.out.println("IO Exception in file creation: " + ioException.getMessage());
		}
        
        while (firstFileIndex < firstFileStrings.size() - 1
        		|| secondFileIndex < secondFileStrings.size() - 1) {        	
        	int compareResult = firstFileStrings.get(firstFileIndex)
        		.compareTo(secondFileStrings.get(secondFileIndex));
        	
        	if (compareResult < 0) { // first string lexicografically less than second string
        		if (firstFileIndex < firstFileStrings.size() - 1)
        			firstFileIndex ++;
        	}
        	else if (compareResult > 0) { // first string lexicografically greater than second string
        		if (secondFileIndex < secondFileStrings.size() - 1)
        			secondFileIndex ++;
        	}
        	else { // two Strings are equal
        		String stringToSave = firstFileStrings.get(firstFileIndex);
        		if (previousSavedString.compareTo(stringToSave) != 0) {
        			previousSavedString = stringToSave;
        			writeStringToOutputFile(outputFile, stringToSave);
        		}
        		
        		if (firstFileIndex < firstFileStrings.size() - 1)
        			firstFileIndex ++;
        		if (secondFileIndex < secondFileStrings.size() - 1)
        			secondFileIndex ++;
        	}
        }
	}

	/**
	 * Writes a string to a file and changes line
	 * @param outputFile file to write in
	 * @param stringToSave string to write in the file
	 */
	private void writeStringToOutputFile(File outputFile, String stringToSave) {	
		try {
			FileWriter fw = new FileWriter(outputFile.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(stringToSave);
			bw.newLine();
			bw.close();
		} catch (IOException ioException) {
			System.out.println("IO Exception in writeStringToOutputFile: " + ioException.getMessage());
		}	
	}
}
