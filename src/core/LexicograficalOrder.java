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

	

	private List<String> readFileStrings(String inputFile) {
		//System.out.println("inputFile: " + inputFile);
		List<String> listToReturn = new ArrayList<String>(); 
		try(BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
		    for(String line; (line = br.readLine()) != null; ) {
		    	//System.out.println("line: " + line);
		    	listToReturn.add(line);
		    }
		}
		catch (IOException ioException) {
			System.out.println("IO Exception in readFileStrings: " + ioException.getMessage());
		}
		return listToReturn;
	}
	
	private void compareTwoFileStrings(String outputFileString) {
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        while (firstFileIndex < firstFileStrings.size() - 1
        		|| secondFileIndex < secondFileStrings.size() - 1) {
        	//System.out.println("firstFileIndex: " + firstFileIndex);
        	//System.out.println("firstFileStrings.get(firstFileIndex): " + firstFileStrings.get(firstFileIndex));
        	//System.out.println("secondFileIndex: " + secondFileIndex);
        	//System.out.println("secondFileStrings.get(secondFileIndex)): " + secondFileStrings.get(secondFileIndex));
        	
        	int compareResult = firstFileStrings.get(firstFileIndex)
        		.compareTo(secondFileStrings.get(secondFileIndex));
        	//System.out.println("compareResult: " + compareResult);
        	
        	if (compareResult < 0) { // first string lexicografically less than second string
        		if (firstFileIndex < firstFileStrings.size() - 1)
        			firstFileIndex ++;
        		//System.out.println("compareResult < 0 FIRST: " + firstFileIndex);
        	}
        	else if (compareResult > 0) { // first string lexicografically greater than second string
        		if (secondFileIndex < secondFileStrings.size() - 1)
        			secondFileIndex ++;
        		//System.out.println("compareResult > 0 SECOND: " + secondFileIndex);
        	}
        	else { // two Strings equals
        		String stringToSave = firstFileStrings.get(firstFileIndex);
        		if (previousSavedString.compareTo(stringToSave) != 0) {
        			previousSavedString = stringToSave;
        			//System.out.println("previousSavedString: " + previousSavedString);
        			writeStringToOutputFile(outputFile, stringToSave);
        		}
        		
        		if (firstFileIndex < firstFileStrings.size() - 1)
        			firstFileIndex ++;
        		if (secondFileIndex < secondFileStrings.size() - 1)
        			secondFileIndex ++;
        		//System.out.println("compareResult = 0 FIRST: " + firstFileIndex + 
        			//	", SECOND: " + secondFileIndex);
        	}
        }
	}

	private void writeStringToOutputFile(File outputFile, String stringToSave) {	
		try {
			/*// if file doesn't exists, then create it
			if (!outputFile.exists()) {
				outputFile.createNewFile();
			}*/

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
