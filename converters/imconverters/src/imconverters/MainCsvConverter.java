package imconverters;


//import java.util.Enumeration;
//import java.util.Iterator;
//import java.util.Vector;
import java.io.*;

/*This code compares m/z predictions made from IonPrediction with csv files from Driftscope, and report them in two different formats. 
 * 
 * An additional column is added to check whether IonType assignment agrees with manual assignment
 * 
 * 
 *  12-March-2010: code has been modified to only report 1+ and 2+ ions.
 *   
 *  25-March-2010: outPut1 (simplified report) is edited to report additional columns of 3D_Mobility and 3D_Intensity (potentially for R).
 *  
 *  8-April-2010: outPut1 (simplified report) is edited to report ion types and charges in different columns (for MATLAB).
 *  
 *  4-May-2010: outPut1 (simplified report) is edited for R script (ionPlot) in the following ways a) to report ion types and charges
 *  	in a combined single column, i.e. "(1+)b1" for R script.  b)first three rows reporting file names have been disabled
 *  	c) manual assignment columns have been removed to avoid confusion. 
 *  	d) removed the final "number of yes, no" to avoid confusion
*/

public class MainCsvConverter {

    

    public static void main (String args[])throws Exception{			
	
		
        if(args.length == 3){
            String seq = args[0];
            MainCsvConverter conv = new MainCsvConverter();
            conv.formatConversions(seq,args[1],args[2]);
        }
        else{

            System.out.println("Please input command-line arguments: \t\n " +
            "1. Peptide sequence in upper case \t\n " +
            "2. File name / location of 'Driftscope output file' \t\n " +
            "3. m/z tolerance \t\n " +
            "e.g. RNDALPLK data/Sample6.csv 0.05 ");
        }
    }


    public void formatConversions(String pepSeq, String driftFile, String mz){
        IonPredictor peptide1 = new IonPredictor();


        String ipFilename = driftFile.substring(0, driftFile.lastIndexOf('.')) + "_ionPredictions.csv";
        String simpleOutFile  = driftFile.substring(0, driftFile.lastIndexOf('.')) + "_simple.csv";
        String fullOutFile = driftFile.substring(0, driftFile.lastIndexOf('.')) + "_full_output.csv";

        try{
            peptide1.predictIons(pepSeq, ipFilename); //call predictIons method


            String inputIon = ipFilename;
            String inputIM = driftFile;
            Double mzTol = Double.parseDouble(mz);
            String outPut1 = simpleOutFile; //simplified report
            String outPut2 = fullOutFile; //full report
                //String outPut3 = args[5]; //summary report for manual assignment

            System.out.println("ion: " + inputIon + " , IM: " + inputIM);


            CsvReader ionP = new CsvReader(inputIon);
            CsvReader imsr = new CsvReader(inputIM);

            ionP.readCSV();
            imsr.readCSV();


            PrintWriter out1  = new PrintWriter(new BufferedWriter(new FileWriter(outPut1, true)));

            out1.append("Charge_IonType" + "," + "Theoretical m/z" + "," + "3D_Mobility" + "," + "experimental m/z (3D_m_z)"
                                        + "," + "3D_Intensity" + "," + "3D_ID" + "," + "mz2-mz1" + "\r\n"); //4-May-2010

            PrintWriter out2  = new PrintWriter(new BufferedWriter(new FileWriter(outPut2, true)));
            out2.append("Theoretical File: " + inputIon + "\r\n" + "Experimental File: " + inputIM + "\r\n" + "m/z tolerance: " + mzTol + "\r\n");

            out2.append("func" + "," + "3D_ID" + "," + "3D_m_z" + "," + "3D_RetTime" + "," + "3D_Mobility" + "," + "3D_Intensity" + "," + "3D_m_z_uncal" + "," + "Ion_type" + "\r\n");



            double counY = 0;
            double counAll = 0;

            for (int k=1;k<imsr.line;k++){
                    //for (int k=1;k<imsr.counter;k++){
                    double mz2 = Double.parseDouble(imsr.allRecords[k][2]);	//returns value of experimental m/z
                    double dt = Double.parseDouble(imsr.allRecords[k][4]); //returns the corresponding drift time (3D_mobility column)

                    int flag = 0;

                    for (int j=0; j<ionP.line;j++) { //all values for mz1, mz2 have been tested and remain correct.

                            double mz1 = Double.parseDouble(ionP.allRecords[j][3]);	//returns value of predicted m/z
                            String cha = ionP.allRecords[j][2].toString(); //this is the charge value assigned by IonPredictor
                            //String typ = ionP.allRecords[j][1].toString(); //this is the type of the Ion
                            //String ionType = ionP.allRecords[j][0] + "_" + ionP.allRecords[j][1] + ionP.allRecords[j][2];
                            String ionType = ionP.allRecords[j][1]; //8-April-2010


                            if (((mz2-mzTol) <= mz1)&& (mz1 <= (mz2+mzTol))){	//print the value back(write a separate method)

                                    counAll++;
                                    out1.append(ionP.allRecords[j][2] + ionType + "," + mz1 + "," + imsr.allRecords[k][4] + "," + mz2 + "," + imsr.allRecords[k][5]
                                                    + "," + imsr.allRecords[k][1] + "," + (mz2-mz1) + "\r\n"); //25-March-2010, 8-April-2010, 4-May-2010-a


                                    String charge = null; //to represent manual assignment of sample 6

                                    //the following 'if' provides an option to only check b and y ions


                                    //if (typ.startsWith("b")|typ.startsWith("y")) {



                                    /*the following block is to report an extra column to result file 1, listing whether a certain peak should
                                    fall into (2+) or (1+) ion regions according to manual separation*/
                                    /* 4-May-2010-c
                                            if ((mz2 > (dt * 10.417 - 41.7))){	//mz2 falls in 2+ region
                                                    out1.append("(2+) Region" + ",");
                                                    charge = "(2+)";

                                            //	if ((cha.equals("(3+)")) | (cha.equals(charge))){    12-March-2010
                                                    if (cha.equals(charge)){
                                                            out1.append("Yes" + "\r\n");
                                                            counY++;
                                                    }else out1.append("No" + "\r\n");


                                            }else
                                            if ((mz2 < (dt * 10.417 - 41.7))){	//mz2 falls in 1+ region
                                                    out1.append("(1+) Region" + ",");
                                                    charge = "(1+)";

                                                    if (cha.equals(charge)){
                                                            out1.append("Yes" + "\r\n");
                                                            counY++;
                                                    }else out1.append("No" + "\r\n");

                                            }
                                    //}


                                    //this ends the manual checking block.
                                    */ //4-May-2010-c

                                    for (int i=0;i<imsr.allRecords[k].length;i++){

                                            out2.print(imsr.allRecords[k][i] + ",");

                                    }	//out2.append(ionP.allRecords[j][0] + "_" + mz1 + "\r\n");
                                    out2.append(ionP.allRecords[j][1]+ "," + ionP.allRecords[j][2] + "\r\n");
                                    flag = 1;
                            }

                            //out1.append("No. of Yes: " + "," + counterY + "\r\n");
                            //out1.append("No. of No: " + "," + counterN + "\r\n");

                    }

                    if (flag == 0){
                            for (int i=0;i<imsr.allRecords[k].length;i++){

                                    out2.print(imsr.allRecords[k][i] + ",");

                            }out2.append("\r\n");
                    }
                }
                //double counN = counAll - counY;
                // out1.append("Number of Yes: " + "," + counY + "\r\n" + "Number of No: " + "," + counN); 4-May-2010-d
                //out3.append((mzTol + "," + counY + "," + counN + "," + counAll + "," + (counY/counAll) + "\r\n"));
                out1.close();
                out2.close();


                //Now call CSV2Arff converter
                callRCommand(simpleOutFile,pepSeq);

            }
            catch(IOException e){
                e.printStackTrace();
            }        
    }


    public void callRCommand(String inputFile, String pepSeq){

        String rFile = "resources/IonPlot.r";
        String outputFile =  inputFile.substring(0, inputFile.lastIndexOf('.')) + "_out.jpg";
        String title = pepSeq;

        try{
            File f = new File(rFile);
            FileInputStream fin = new FileInputStream(f);
            BufferedReader bin = new BufferedReader(new InputStreamReader(fin));

            int counter = 0;

            String rFunction = "";
            String temp;
            while((temp = bin.readLine()) != null){

                rFunction += temp + "\n";

            }

            rFunction += "\n\n ionPlot(\"" + inputFile + "\",\""+outputFile + "\",\"" + pepSeq + "\")\n" ;

            File fout = new File("rIonPlotter.r");
            FileOutputStream out; // declare a file output object
            PrintStream p; // declare a print stream object

            try
            {
                out = new FileOutputStream(fout);
                p = new PrintStream( out );
                p.print (rFunction);

                p.close();
            }
            catch (Exception e)
            {
                    System.err.println ("Error writing to file");
            }




            //System.out.println(rFunction);

            try {
                Runtime rt = Runtime.getRuntime();
                //Process pr = rt.exec("cmd /c dir");
                Process pr = rt.exec("R CMD BATCH rIonPlotter.r");

                BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));

                String line=null;

                while((line=input.readLine()) != null) {
                    System.out.println(line);
                }

                int exitVal = pr.waitFor();
                System.out.println("Exited with error code "+exitVal);

            }
            catch(Exception e) {
                System.out.println(e.toString());
                e.printStackTrace();
            }


        }
        catch(IOException e){
            e.printStackTrace();
        }

    }

}
