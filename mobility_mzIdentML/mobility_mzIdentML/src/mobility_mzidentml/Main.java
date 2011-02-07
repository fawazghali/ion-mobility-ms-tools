package mobility_mzidentml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import uk.ac.ebi.jmzidml.model.mzidml.AnalysisData;
import uk.ac.ebi.jmzidml.model.mzidml.CvParam;
import uk.ac.ebi.jmzidml.model.mzidml.FragmentArray;
import uk.ac.ebi.jmzidml.model.mzidml.Fragmentation;
import uk.ac.ebi.jmzidml.model.mzidml.FragmentationTable;
import uk.ac.ebi.jmzidml.model.mzidml.IonType;
import uk.ac.ebi.jmzidml.model.mzidml.Measure;
import uk.ac.ebi.jmzidml.model.mzidml.MzIdentML;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationItem;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationList;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationResult;
import uk.ac.ebi.jmzidml.xml.io.MzIdentMLMarshaller;
import uk.ac.ebi.jmzidml.xml.io.MzIdentMLObjectCache;
import uk.ac.ebi.jmzidml.xml.io.MzIdentMLUnmarshaller;
import uk.ac.ebi.jmzidml.xml.jaxb.unmarshaller.cache.AdapterObjectCache;


import java.util.Comparator;



/**
 * Main.java
 *
 * Created on 22-Nov-2010, 13:25:25
 *
 * @author Fawaz Ghali
 */
public class Main {

    //ARJ - added for the linear regression testing
    //static double rsquared = 0;

    static HashMap predictedCharges;    //Will be completed with the predicted charge of every MS2 ion, based on mobility values


    public static void main(String[] args) {
        

        Scanner scanner = new Scanner(System.in);
        String selection = null;
        String csv, mzid, mgf, outputFile = null;      
        


        if (args.length > 1){
        
            if (args[0].equals("1")){
                csv = args[1];
                
                if (csv == null || !csv.endsWith("csv")) {
                    System.out.println("Your input CSV file is either empty or invalid");
                } else {
                    if(args.length>1){
                        outputFile = args[2];
                    }else{
                        outputFile = "result.mgf";
                    }
                    convertFromCSVtoMGF(csv, 0, outputFile);
                }
            }else if (args[0].equals("2")){
                csv = args[1];
                if (csv == null || !csv.endsWith("csv")) {
                    System.out.println("Your input CSV file is either empty or invalid");
                } else {
                    if(args.length>1){
                        outputFile = args[2];
                    }else{
                        outputFile = "result.mgf";
                    }
                    convertFromCSVtoMGF(csv, 1, outputFile);
                }

            }else if (args[0].equals("3")){
                  csv = args[1];
                if (csv == null || !csv.endsWith("csv")) {
                    System.out.println("Your input CSV file is either empty or invalid");
                } else {
                    mzid = args[2];
                    if(args.length>2){
                        outputFile = args[3];
                    }else{
                        outputFile = "result.mzid";
                    }
                    
                    if (mzid == null || !mzid.endsWith("mzid")) {
                        System.out.println("Your input mzid file is either empty or invalid");
                    } else {
                        addDriftTimetoMzIdentML("csv", csv, mzid,outputFile);
                    }
                }


            }else if (args[0].equals("4")){
                 mgf = args[1];
                if (mgf == null || !mgf.endsWith("mgf")) {
                    System.out.println("Your input MGF file is either empty or invalid");
                } else {
                    
                    mzid = args[2];
                    if(args.length>2){
                        outputFile = args[3];
                    }else{
                        outputFile = "result.mzid";
                    }
                    if (mzid == null || !mzid.endsWith("mzid")) {
                        System.out.println("Your input mzid file is either empty or invalid");
                    } else {
                        addDriftTimetoMzIdentML("mgf", mgf, mzid,outputFile);
                    }
                }

            }else {
                System.exit(0);
            }
             System.exit(0);
        }
        while (true) {
            System.out.println("Please choose one of the following options:");
            System.out.println("1- to convert from CSV to MGF without drift time.");
            System.out.println("2- to convert from CSV to MGF with drift time.");
            System.out.println("3- to add drift time to mzIdentML from CSV.");
            System.out.println("4- to add drift time to mzIdentML from MGF.");
            System.out.println("5- to exit.");
            System.out.println("Selection: ");

            selection = scanner.next();

            if (selection.equals("1")) {
                System.out.println("Enter your CSV file: ");
                csv = scanner.next();
                if (csv == null || !csv.endsWith("csv")) {
                    System.out.println("Your input CSV file is either empty or invalid");
                } else {
                    convertFromCSVtoMGF(csv, 0, "result.mgf");
                }

            } else if (selection.equals("2")) {
                System.out.println("Enter your CSV file: ");
                csv = scanner.next();
                if (csv == null || !csv.endsWith("csv")) {
                    System.out.println("Your input CSV file is either empty or invalid");
                } else {
                    convertFromCSVtoMGF(csv, 1, "result.mgf");
                }

            } else if (selection.equals("3")) {
                System.out.println("Enter your CSV file: ");
                csv = scanner.next();
                if (csv == null || !csv.endsWith("csv")) {
                    System.out.println("Your input CSV file is either empty or invalid");
                } else {
                    System.out.println("Enter your mzid file: ");
                    mzid = scanner.next();
                    if (mzid == null || !mzid.endsWith("mzid")) {
                        System.out.println("Your input mzid file is either empty or invalid");
                    } else {
                        addDriftTimetoMzIdentML("csv", csv, mzid, "result.mzid");
                    }
                }


            } else if (selection.equals("4")) {
                System.out.println("Enter your MGF file: ");
                mgf = scanner.next();
                if (mgf == null || !mgf.endsWith("mgf")) {
                    System.out.println("Your input MGF file is either empty or invalid");
                } else {
                    System.out.println("Enter your mzid file: ");
                    mzid = scanner.next();
                    if (mzid == null || !mzid.endsWith("mzid")) {
                        System.out.println("Your input mzid file is either empty or invalid");
                    } else {
                        addDriftTimetoMzIdentML("mgf", mgf, mzid,"result.mzid");
                    }
                }
            } else if (selection.equals("5")) {
                System.out.println("Exit Successful");
                System.exit(0);

            } else {
                System.out.println("Please enter a valid selection.");

            }

        }
    }

    private static void convertFromCSVtoMGF(String csv, int mobility, String outputFile) {
        try {
            String stringLine = null;
            String[] tokens;

            double[][] mz_mob = new double[10000][2];
            int counter = 0;

            BufferedReader buffer = new BufferedReader(new FileReader(csv));
            try {
                stringLine = buffer.readLine();
            } catch (IOException ex) {
                System.out.println(ex.toString());
            }
            tokens = stringLine.split(",");
            int m_z_index = 2, mobility_index = 4, intensity_index = 5, parent_index = 11, charge_index = 12;
            for (int i = 0; i < tokens.length; i++) {
                if (tokens[i].equals("3D_m_z")) {
                    m_z_index = i;
                }
                if (tokens[i].equals("3D_Mobility")) {
                    mobility_index = i;
                }
                if (tokens[i].equals("3D_Intensity")) {
                    intensity_index = i;
                }
                if (tokens[i].equals("Parent_Ion_Mass")) {
                    parent_index = i;
                }
                if (tokens[i].equals("Charge")) {
                    charge_index = i;
                }

            }
            List<String> m_z_list = new ArrayList();
            List<String> intensity_list = new ArrayList();
            List<String> mobility_list = new ArrayList();
            List<String> parent_list = new ArrayList();
            List<String> charge_list = new ArrayList();

            while ((stringLine = buffer.readLine()) != null) {
                tokens = stringLine.split(",");

                //System.out.println("mz:" + tokens[m_z_index] + " mob: " + tokens[mobility_index]);

                mz_mob[counter][0] = Double.parseDouble(tokens[m_z_index]);
                mz_mob[counter][1] = Double.parseDouble(tokens[mobility_index]);
                counter++;

                //if(filterIons(tokens[m_z_index],tokens[mobility_index])){

                m_z_list.add(tokens[m_z_index]);
                intensity_list.add(tokens[intensity_index]);
                mobility_list.add(tokens[mobility_index]);
                parent_list.add(tokens[parent_index]);
                charge_list.add(tokens[charge_index]);
                //}
            }


            Comparator<double[]> compMZMob = new MZMobComparator();

            java.util.Arrays.sort(mz_mob,compMZMob);    //Sort by Mz/Mob - the fact that these are sorted is not used yet
            //double coef[] = linear_equation(mz_mob, 1, counter);
            //print_equation(coef);

            predictedCharges = new HashMap();

            
            
            /*
            for(int j=0; j < 100; j++){  //Keep running until no more improvement  to keep improving value, usually no more improvements after 3 or so
                double rsqBefore = linear_equation(mz_mob, 1, counter);
                System.out.println("r-squared before changing charge: " + rsqBefore );
                for(int i =0; i<counter;i++){
                    double rsquared = linear_equation(mz_mob, 1, counter);    //This changes the global variable r squared
                    //System.out.println("MZ: " +mz_mob[i][0]+ " mob: " + mz_mob[i][1] + " mz/mob:" + "" + (mz_mob[i][0]/mz_mob[i][1]) + " r-squared:" + rsquared);
                    mz_mob[i][1] = mz_mob[i][1] *2; //Try doubling the Mobility value, in case this is a 2+ ion
                    double rsquared2 = linear_equation(mz_mob, 1, counter);    //This changes the global variable r squared
                    //System.out.println("MZ: " +mz_mob[i][0]+ " mob: " + mz_mob[i][1] + " mz/mob:" + "" + (mz_mob[i][0]/mz_mob[i][1]) + " r-squared:" + rsquared);

                    if(rsquared2 < rsquared){
                        mz_mob[i][1] = mz_mob[i][1] /2; //Return value since there is no improvement
                        predictedCharges.put(mz_mob[i][0], "1");
                    }
                    else{
                        predictedCharges.put(mz_mob[i][0], "2");
                    }
                }
                double rsqAfter = linear_equation(mz_mob, 1, counter);
                System.out.println("r-squared after changing charge: " + rsqAfter );

                if(rsqBefore == rsqAfter){
                    j = 100000;
                }
                
            }
            */

            double rsqBefore = linear_equation(mz_mob, 1, counter);
            System.out.println("r-squared before changing charge: " + rsqBefore );

            double maxRsq = linear_equation(mz_mob, 1, counter);    //Starting value of r-squared
            int maxPos = 0;                                         //This is set when the max r-squared is reset

            for(int i =0; i<counter;i++){

                double[][] tempMzMob = new double[counter][2];

                //deep copy into new array
                for (int y = 0; y < counter; y++){
                    for (int x = 0; x < 2; x++){
                            tempMzMob[y][x] = mz_mob[y][x];
                    }
                }


                System.out.println("max r-squared:" + maxRsq);
                
                for(int j =i; j<counter;j++){
                     tempMzMob[j][1] = tempMzMob[j][1] *2; //Try doubling the Mobility value, in case this is a 2+ ion

                }
                double rsquaredNew = linear_equation(tempMzMob, 1, counter);    //This changes the global variable r squared
                System.out.println("New r-squared:" + rsquaredNew);

                if(rsquaredNew > maxRsq){
                        maxPos = i;
                        maxRsq = rsquaredNew;
                }
                 
            }

            System.out.println("MaxPos: " + maxPos);

            double rsqAfter = linear_equation(mz_mob, 1, counter);
            System.out.println("Max rsq: " + maxRsq );
            

            StringBuilder builder_all = new StringBuilder();
            StringBuilder builder_mobility = new StringBuilder();
            StringBuilder builder_m_z_intensity = new StringBuilder();
            StringBuilder builder_params = new StringBuilder();
            int count_parent = 1;

            for (int i = 0; i < parent_list.size(); i++) {
                if (i == 0) {
                    builder_all.append("BEGIN IONS");
                    builder_all.append("\r\n");
                    builder_all.append("TITLE=Spectrum ");
                    builder_all.append(count_parent);

                    builder_all.append(" DRIFT_TIME=");
                    builder_all.append(mobility_list.get(i));
                    builder_all.append(" ");

                    //builder_params.append("\r\n");
                    builder_params.append("PEPMASS=");
                    builder_params.append(parent_list.get(i));
                    builder_params.append("\r\n");
                    builder_params.append("CHARGE=");
                    builder_params.append(charge_list.get(i));
                    builder_params.append("\r\n");

                    builder_m_z_intensity.append(m_z_list.get(i));
                    builder_m_z_intensity.append(" ");
                    builder_m_z_intensity.append(intensity_list.get(i));
                    builder_m_z_intensity.append("\r\n");

                   


                } else if (i == parent_list.size() - 1) {
                    if (mobility == 1) {
                        builder_all.append(builder_mobility);
                        builder_all.append("\r\n");
                    }
                    builder_all.append(builder_params);
                    builder_all.append(builder_m_z_intensity);
                    builder_all.append("END IONS");
                    builder_all.append("\r\n");
                } else {
                    builder_m_z_intensity.append(m_z_list.get(i));
                    builder_m_z_intensity.append(" ");
                    builder_m_z_intensity.append(intensity_list.get(i));
                    builder_m_z_intensity.append("\r\n");

                    builder_mobility.append(mobility_list.get(i));
                    builder_mobility.append(" ");

                    if (!parent_list.get(i).equals(parent_list.get(i + 1))) {
                        count_parent = count_parent + 1;
                        if (mobility == 1) {
                            builder_all.append(builder_mobility);
                            builder_all.append("\r\n");

                        }
                        builder_all.append(builder_params);
                        builder_params = new StringBuilder();
                        builder_all.append(builder_m_z_intensity);
                        builder_all.append("END IONS");
                        builder_all.append("\r\n");
                        builder_all.append("BEGIN IONS");
                        builder_all.append("\r\n");
                        builder_all.append("TITLE=Spectrum ");
                        builder_all.append(count_parent);

                        builder_all.append(" DRIFT_TIME=");
                        //builder_all.append(mobility_list.get(i));
                        builder_all.append(" ");


                        //builder_params.append("\r\n");
                        builder_params.append("PEPMASS=");
                        builder_params.append(parent_list.get(i + 1));
                        builder_params.append("\r\n");
                        builder_params.append("CHARGE=");
                        builder_params.append(charge_list.get(i + 1));
                        builder_params.append("\r\n");
                        builder_m_z_intensity= new StringBuilder();
                        builder_mobility= new StringBuilder();
                        //builder_mobility.append("DRIFT_TIME=");

                    }
                }

            }
            String fileName = "result.mgf";
            if(outputFile != "" && outputFile != null){
                fileName = outputFile;
            }

            BufferedWriter out = new BufferedWriter(new FileWriter((fileName)));
            out.write(builder_all.toString());
            out.close();
            System.out.println("Check the output file: " + fileName);


            String chargeFile = fileName.substring(0,fileName.indexOf(".")) + "_charges.csv";
            BufferedWriter out2 = new BufferedWriter(new FileWriter(chargeFile));
            out2.write("mz,mob,charge\n");
            for(int i =0; i<counter;i++){

                if(predictedCharges.get(mz_mob[i][0]).equals("2")){
                    out2.write((mz_mob[i][0])+ "," + (mz_mob[i][1]/2) + "," + "" + predictedCharges.get(mz_mob[i][0])+"\n");  //convert these mz values back to original
                }
                else{
                    out2.write(mz_mob[i][0]+ "," + mz_mob[i][1] + "," + "" + predictedCharges.get(mz_mob[i][0])+"\n");
                }

            }
            out2.close();
            System.out.println("Check the output file for predicted charges: " + chargeFile);


        } catch (FileNotFoundException ex) {
            System.out.println(ex.toString());
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

    private static void addDriftTimetoMzIdentML(String type, String inputFile, String mzid, String outputFile) {



        try {
            
            int spectrumIdentItemCount = 0;
            HashMap hashMap = new HashMap();
            String inputMobilityFile = inputFile;
            BufferedReader buffer = new BufferedReader(new FileReader(inputMobilityFile));
            String stringLine;
            String[] tokens;


            if (type.equals("csv")) {
                stringLine = buffer.readLine();
                tokens = stringLine.split(",");
                int m_z_index = 2, mobility_index = 4;
                for (int i = 0; i < tokens.length; i++) {
                    if (tokens[i].equals("3D_m_z")) {
                        m_z_index = i;
                    }
                    if (tokens[i].equals("3D_Mobility")) {
                        mobility_index = i;
                    }
                }
                while ((stringLine = buffer.readLine()) != null) {
                    tokens = stringLine.split(",");
                    hashMap.put(tokens[m_z_index], tokens[mobility_index]);
                }
            } else if (type.equals("mgf")) {
                int count_index = 0;
                String[] mobility_tokens = null;

                
                while ((stringLine = buffer.readLine()) != null) {
               //    System.out.print("1");
               //    System.out.println("stringLine:" + stringLine);
                    if (stringLine.startsWith("BEGIN")
                            || stringLine.startsWith("PEPMASS")
                            || stringLine.startsWith("CHARGE")
                            || stringLine.startsWith("END")) {
                 //       System.out.print("2");
                        continue;
                    } else if (stringLine.startsWith("TITLE")) {
                    //    System.out.print("4");
                    //    System.out.println("stringLine:" + stringLine);
                        //stringLine = stringLine.substring(11);
                        stringLine = stringLine.substring(stringLine.indexOf("DRIFT_TIME=")+11);
                        stringLine = stringLine.trim();
                        mobility_tokens = stringLine.split(" ");
                        count_index = 0;
                    } else {
                    //    System.out.print("3");
                        tokens = stringLine.split(" ");
                        hashMap.put(tokens[0], mobility_tokens[count_index]);
                        count_index = count_index + 1;
                    }
                }
                   
                /*
                 while ((stringLine = buffer.readLine()) != null) {
                    if (stringLine.startsWith("BEGIN")
                            || stringLine.startsWith("TITLE")
                            || stringLine.startsWith("PEPMASS")
                            || stringLine.startsWith("CHARGE")
                            || stringLine.startsWith("END")) {
                        break;
                    } else if (stringLine.startsWith("DRIFT_TIME=")) {
                        stringLine = stringLine.substring(11);
                        mobility_tokens = stringLine.split(" ");
                    } else {
                        tokens = stringLine.split(" ");
                        hashMap.put(tokens[0], mobility_tokens[count_index]);
                        count_index = count_index + 1;
                    }
                }
*/


            }





            MzIdentMLUnmarshaller unmarshaller = new MzIdentMLUnmarshaller(new File(mzid));
            MzIdentML mzIdentML_whole = unmarshaller.unmarshal(MzIdentML.class);
            AnalysisData analysisData = mzIdentML_whole.getDataCollection().getAnalysisData();
            List<SpectrumIdentificationList> spectrumIdentificationList = analysisData.getSpectrumIdentificationList();

            if (spectrumIdentificationList != null && !spectrumIdentificationList.isEmpty()) {
                FragmentationTable ft = spectrumIdentificationList.get(0).getFragmentationTable();
                Measure e = new Measure();
                e.setId("m_mobility");
                CvParam cvParam = new CvParam();
                cvParam.setAccession("MS:TODO");
                cvParam.setCvRef("PSI-MS");
                cvParam.setName("product ion mobility");
                e.getCvParam().add(cvParam);
                ft.getMeasure().add(e);
                for (SpectrumIdentificationList sIdentList : spectrumIdentificationList) {
                    for (SpectrumIdentificationResult spectrumIdentResult : sIdentList.getSpectrumIdentificationResult()) {
                        for (SpectrumIdentificationItem spectrumIdentItem : spectrumIdentResult.getSpectrumIdentificationItem()) {
                            spectrumIdentItemCount = spectrumIdentItemCount + 1;
                            Fragmentation fragmentation = spectrumIdentItem.getFragmentation();
                            for (IonType ionType : fragmentation.getIonType()) {
                                List m_mz, m_intensity;
                                String mobility;
                                m_mz = ionType.getFragmentArray().get(0).getValues();
                                m_intensity = ionType.getFragmentArray().get(1).getValues();
                                List<Float> m_mobility = new ArrayList();
                                if (m_mz != null && !m_mz.isEmpty()) {
                                    for (int j = 0; j < m_mz.size(); j++) {
                                        mobility = (String) hashMap.get(m_mz.get(j).toString());
                                        if (mobility != null) {
                                            m_mobility.add(Float.valueOf(mobility));
            
                                        } else {
                                           m_mobility.add(Float.valueOf("0"));

                                        }
                                    }
                                }
                                if (m_mobility.size() > 0) {
                                    FragmentArray fragmentArray = new FragmentArray();
                                    Measure measure = new Measure();
                                    measure.setId("m_mobility");
                                    fragmentArray.setMeasure(measure);
                                    ionType.getFragmentArray().add(fragmentArray);
                                    for (Float m_mobility_s : m_mobility) {
                                        ionType.getFragmentArray().get(3).getValues().add(m_mobility_s);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            MzIdentMLMarshaller m = new MzIdentMLMarshaller();
            String fileName = "result.mzid";

            if(outputFile != "" && outputFile != null){
                fileName = outputFile;
            }
            m.marshall(mzIdentML_whole, new FileOutputStream(fileName));
            System.out.println("Check the output file: " + fileName);

        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

      


    //http://www.angelfire.com/tx4/cus/regress/java.html
       // Apply least squares to raw data to determine the coefficients for
   // an n-order equation: y = a0*X^0 + a1*X^1 + ... + an*X^n.
   // Returns the coefficients for the solved equation, given a number
   // of y and x data points. The rawData input is given in the form of
   // {{y0, x0}, {y1, x1},...,{yn, xn}}.   The coefficients returned by
   // the regression are {a0, a1,...,an} which corresponds to
   // {X^0, X^1,...,X^n}. The number of coefficients returned is the
   // requested equation order (norder) plus 1.
   static double linear_equation(double rawData[][], int norder, int numValues) {
      double a[][] = new double[norder+1][norder+1];
      double b[] = new double[norder+1];
      double term[] = new double[norder+1];
      double ysquare = 0;
      double rsquared;

      // step through each raw data entries
      for (int i = 0; i < numValues; i++) {

         // sum the y values
         b[0] += rawData[i][0];
         ysquare += rawData[i][0] * rawData[i][0];

         // sum the x power values
         double xpower = 1;
         for (int j = 0; j < norder+1; j++) {
            term[j] = xpower;
            a[0][j] += xpower;
            xpower = xpower * rawData[i][1];
         }

         // now set up the rest of rows in the matrix - multiplying each row by each term
         for (int j = 1; j < norder+1; j++) {
            b[j] += rawData[i][0] * term[j];
            for (int k = 0; k < b.length; k++) {
               a[j][k] += term[j] * term[k];
            }
         }
      }

      // solve for the coefficients
      double coef[] = gauss(a, b);

      // calculate the r-squared statistic
      double ss = 0;
      double yaverage = b[0] / numValues;
      for (int i = 0; i < norder+1; i++) {
         double xaverage = a[0][i] / numValues;
         ss += coef[i] * (b[i] - (numValues * xaverage * yaverage));
      }
      rsquared = ss / (ysquare - (numValues * yaverage * yaverage));

      // solve the simultaneous equations via gauss
      return rsquared;
   }

      // it's been so long since I wrote this, that I don't recall the math
   // logic behind it. IIRC, it's just a standard gaussian technique for
   // solving simultaneous equations of the form: |A| = |B| * |C| where we
   // know the values of |A| and |B|, and we are solving for the coefficients
   // in |C|
   static double[] gauss(double ax[][], double bx[]) {
      double a[][] = new double[ax.length][ax[0].length];
      double b[] = new double[bx.length];
      double pivot;
      double mult;
      double top;
      int n = b.length;
      double coef[] = new double[n];

      // copy over the array values - inplace solution changes values
      for (int i = 0; i < ax.length; i++) {
         for (int j = 0; j < ax[i].length; j++) {
            a[i][j] = ax[i][j];
         }
         b[i] = bx[i];
      }

      for (int j = 0; j < (n-1); j++) {
         pivot = a[j][j];
         for (int i = j+1; i < n; i++) {
            mult = a[i][j] / pivot;
            for (int k = j+1; k < n; k++) {
               a[i][k] = a[i][k] - mult * a[j][k];
            }
            b[i] = b[i] - mult * b[j];
         }
      }

      coef[n-1] = b[n-1] / a[n-1][n-1];
      for (int i = n-2; i >= 0; i--) {
         top = b[i];
         for (int k = i+1; k < n; k ++) {
            top = top - a[i][k] * coef[k];
         }
         coef[i] = top / a[i][i];
      }
      return coef;
   }

   /*
   // simple routine to print the equation for inspection
   static void print_equation(double coef[]) {
      for (int i = 0; i < coef.length; i++) {
         if (i == 0) {
            System.out.print("Y = ");
         } else {
            System.out.print(" + ");
         }
         System.out.print(coef[i] + "*X^" + i);
      }
      System.out.println("      [r^2 = " + rsquared + "]");
   }
*/
    
   static class MZMobComparator implements Comparator<double[]> {

        // Comparator interface requires defining compare method.
        public int compare(double[] o1, double[] o2) {

            if(o1[0]/o1[1]> o2[0]/o2[1])return 1;               // 1 for ascending order, -1 for descending order
                 else if(o1[0]/o1[1]< o2[0]/o2[1]) return -1;   // 1 for descending order, -1 for ascending order
                 else return 0; 
        }

    }

}
