package imconverters;

import java.io.*;
import java.util.Arrays;
import java.util.Vector;
import java.util.Iterator;
import java.util.Enumeration;

public class CsvReader {
	String [] colHeader;
	/*public Vector<String> col1;
	public Vector<String> col2;
	public Vector<String> col3;*/	
	private String inputString;
	//private String outputString;
	public String dbRecord = null;
	public int line;
	//int counter = 0;	//count the line
	
	public String[][] allRecords;
	private static int MAX_RECORDS = 100000;
	private static int MAX_VALUES = 10;
	public CsvReader(String inputFile){
		/*col1 = new Vector();
		col2 = new Vector();
		col3 = new Vector();*/	
		inputString = inputFile;
		allRecords = new String[MAX_RECORDS][MAX_VALUES];
	}
	
	
	/*methods need: readCSV(), to get the tokenized value from each column.
	 * getcol1() and getcol2() to return the value of each column. 
	 * assignIonType() to write the assignment back
	 */
	
	/*
	public void accptIonPrediction() throws IOException{
		System.out.println("Please enter the name of IonPrediction file");
		BufferedReader input = new BufferedReader(new InputStreamReader (System.in));
		inputString = input.readLine();
		
	}
	
	public void accptIMSResult() throws IOException{
		System.out.println("Please enter the name of IMS result file");
		BufferedReader input = new BufferedReader(new InputStreamReader (System.in));
		inputString = input.readLine();
		outputString = inputString + "_result" + ".csv";
	}
	*/
	
	
	public void readCSV() throws IOException{
		
		try{
		
		File f = new File(inputString);
		FileInputStream fin = new FileInputStream(f);
		BufferedReader bin = new BufferedReader(new InputStreamReader(fin));
		
		int counter = 0;
				
		while ((dbRecord = bin.readLine())!= null){	//get the value from the txt file
			//System.out.println("dbRecord: " + dbRecord);						
						
			String [] vals = dbRecord.split(",");	//split the line with signature ",".
			
			int valsLength = vals.length;
			
			if(valsLength >= 1){
			
				
			allRecords[counter] = vals;
		
			counter++;
			
			
				/*for (int i=0; i<vals.length;i++){
					if (i == 0){	
					col1.addElement(vals[i]);
					}
					if (i == 1){
					col2.addElement(vals[i]);
					}
					if (i == 2){
					col3.addElement(vals[i]);
					}
				}*/
			} 
			else{
				System.out.println("Incomplete arrayline: " + dbRecord);
			}
			
		} //System.out.println(counter);
		line = counter;
	
			
		}catch (IOException e){
			System.err.println ("Unable to read from file:" + e);
			System.exit(-1);
		}
	}	
}












