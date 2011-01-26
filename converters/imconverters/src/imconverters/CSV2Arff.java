package imconverters;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

// this code is to convert DriftScope peak list to arff file and assign manually decided charge state to an additional column.
// For Waters Mix1 sample, the equation is Y = 9.267 * X + 10, so if y <= 9.267 * X +10, then it's a 2+ ion

//15-10-10 Modified to only report m/z and mobility (not intensity and m/z uncal)

//27-10-10 Modified to include a attribute of (m/z)/mobility

public class CSV2Arff {

	String [] colHeader;
	private String inputString;
	
	public String dbRecord = null;
	public int line;
	
	
	public String[][] allRecords;
	private static int MAX_RECORDS = 100000;
	private static int MAX_VALUES = 10;
	
	public CSV2Arff(String inputFile){
		inputString = inputFile;
		allRecords = new String[MAX_RECORDS][MAX_VALUES];
	}
	
	
	
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
	
	public static void main (String args [])throws Exception {

            if(args.length != 2 && args.length != 3){
                System.out.println("Error, incorrect command line usage, exiting");
                System.out.println("Usage: java CSV2Arff [inputfile].csv [outputfile.arff] [arff_header]");
                System.out.println("Mandatory: [inputfile].csv  - please provide the name of the input file to convert, follow the template provided, keeping the column headings");
                System.out.println("Mandatory: outputfile.arff  - please provide the name of the output file to create, with the arff extension");
                System.out.println("Optional: [arff_header] header for the ARFF file, see RELATION http://www.cs.waikato.ac.nz/~ml/weka/arff.html. If the string has a space in, these needs to be in double quotes");


                System.exit(1);
            }

            //String inputMix = args [0]; //for Waters' result sheet(Mix1 Lpool.xls)
            String inputDriftScope = args[0];
            //Double rtTol = Double.parseDouble(args[2]);//to set the width of retention time window
            String outputFile = args[1];

            String relation = "header";

            if(args.length ==3){
                relation = args[2];
            }
            
            //String outputName = args[3];  //Removed by ARJ

            //Removed by ARJ
            //double ax = Double.parseDouble(args[4]);
           // double b = Double.parseDouble(args[5]);

            //CSV2Arff Mix1 = new CSV2Arff(inputMix);
            //Mix1.readCSV();

            CSV2Arff iD1 = new CSV2Arff(inputDriftScope);
            iD1.readCSV();



            try {
                    double m_z = 0;
                    double mobility = 0;

                    //double counter  = 0;
            /*	double rtMix = 0;
                    for (int k=1;k<Mix1.line;k++){

                            try {

                                    rtMix = Double.parseDouble(Mix1.allRecords[k][0]); //retention time from Mix

                            }catch (NumberFormatException nfe){}; */


                                    PrintWriter out1
                                       = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, true)));

/* modified on 15-10-10	

                                    out1.append("@relation" + " " + relation + "\r\n" + "\r\n" + "@attribute" + " " + "3D_m_z" + " " + "real"
                                                    + "\r\n"  + "@attribute" + " " + "3D_Mobility" + " " + "real"
                                                    + "\r\n"  + "@attribute" + " " + "3D_Intensity" + " " + "real"
                                                    + "\r\n"  + "@attribute" + " " + "3D_m_z_uncal" + " " + "real"
                                                    + "\r\n"  + "@attribute" + " " + "Predicted_charge" + " " + "{1,2}"
                                                    + "\r\n"  + "\r\n" + "@data" +  "\r\n");
*/


/* modified on 27-10-10					
* 					out1.append("@relation" + " " + relation + "\r\n" + "\r\n" + "@attribute" + " " + "3D_m_z" + " " + "real"
                                                    + "\r\n"  + "@attribute" + " " + "3D_Mobility" + " " + "real"
                                                    + "\r\n"  + "@attribute" + " " + "Predicted_charge" + " " + "{1,2}"
                                                    + "\r\n"  + "\r\n" + "@data" +  "\r\n");
*/				
                                    out1.append("@relation" + " " + relation + "\r\n" + "\r\n" + "@attribute" + " " + "3D_m_z" + " " + "real"
                                                    + "\r\n"  + "@attribute" + " " + "3D_Mobility" + " " + "real"
                                                    + "\r\n"  + "@attribute" + " " + "mz_Mob" + " " + "real"	// to calculate (m/z)/mobility
                                                    + "\r\n"  + "@attribute" + " " + "Predicted_charge" + " " + "{1,2}"
                                                    + "\r\n"  + "\r\n" + "@data" +  "\r\n");



                                            for (int j=1;j<iD1.line;j++){

                                                    /*double rtDS = Double.parseDouble(iD1.allRecords[j][3]); //retention time from DriftScope


                                                    if (((rtDS-rtTol) <= rtMix)&& (rtMix <= (rtDS+rtTol))){

                                                            //counter++;
                                                            System.out.println(rtMix);*/


                                                            //for (int i = 0; i< iD1.allRecords[j].length; i++){
                                                            /*out1.append(iD1.allRecords[j][2] + "," + iD1.allRecords[j][4] + "," +
                                                                            iD1.allRecords[j][5] + "," + iD1.allRecords[j][6] + "," +
                                                                            iD1.allRecords[j][8] + "\r\n");*/

/* modified on 15-10-10
                                                            out1.append(iD1.allRecords[j][2] + "," + iD1.allRecords[j][4] + "," +
                                                                            iD1.allRecords[j][5] + "," + iD1.allRecords[j][6] + ",");
*/
                                                    m_z = Double.parseDouble(iD1.allRecords[j][2]);
                                                    mobility = Double.parseDouble(iD1.allRecords[j][4]);

                                                            //out1.append(iD1.allRecords[j][2] + "," + iD1.allRecords[j][4] + "," + (m_z/mobility) + ",");
                                                    out1.append(iD1.allRecords[j][2] + "," + iD1.allRecords[j][4] + "," + (m_z/mobility) + "," + iD1.allRecords[j][13] + "\r\n");




                                                            //if (m_z <= (mobility * 9.267 + 10)){  //WatersMix1
                                                            //if (m_z <= (mobility * 10 + 160)){  //BSA100510/SLHT_01
                                                            //if (m_z <= (mobility * 8.182 + 80)){  //Glyphos/DFNVGGYIQAVLDR
                                                             //(m_z <= (mobility * 12 + 40)){  //CarbonicAnhydrase_210610_MVNNGH_01
                                                             
                                                            
                                                            /*
                                                            if(m_z <= (mobility * ax + b)){
                                                                    out1.append("1" + "\r\n");

                                                                    //to insert in here reading from weka file column if this mode used...
                                                            } else out1.append("2" + "\r\n");
                                                            */

                            }out1.close();
                     //System.out.println(counter);

            }catch (java.lang.ArrayIndexOutOfBoundsException a){}

	} 
	
	
}
