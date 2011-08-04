/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mzidreader;


import java.io.*;
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
import uk.ac.ebi.jmzidml.model.mzidml.Peptide;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationItem;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationList;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationResult;
import uk.ac.ebi.jmzidml.xml.io.MzIdentMLMarshaller;
import uk.ac.ebi.jmzidml.xml.io.MzIdentMLObjectCache;
import uk.ac.ebi.jmzidml.xml.io.MzIdentMLUnmarshaller;
import uk.ac.ebi.jmzidml.xml.jaxb.unmarshaller.cache.AdapterObjectCache;

/**
 *
 * @author jonesar
 */
public class Main {

    static final int RANK_CUTOFF = 2;
    static final String HEADER = "Peptide,E-value,Ion_type,charge,M_Z,Intensity,Mobility,Rank,PassMascotThreshold\n";
    static HashMap allspecHashMap = new HashMap();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

            

            if(args.length >=2){
                String mzid = args[0];
                String prefix = args[1];

                String inputMGF  = null;
                if(args[2]!=null){
                    inputMGF = args[2];
                    parseDriftTimesFromMGF(inputMGF);
                }


                MzIdentMLUnmarshaller unmarshaller = new MzIdentMLUnmarshaller(new File(mzid));
                MzIdentML mzIdentML_whole = unmarshaller.unmarshal(MzIdentML.class);
                AnalysisData analysisData = mzIdentML_whole.getDataCollection().getAnalysisData();
                List<SpectrumIdentificationList> spectrumIdentificationList = analysisData.getSpectrumIdentificationList();


                try{

                    //int specCounter = 0;

                    if (spectrumIdentificationList != null && !spectrumIdentificationList.isEmpty()) {

                        for (SpectrumIdentificationList sIdentList : spectrumIdentificationList) {
                            for (SpectrumIdentificationResult spectrumIdentResult : sIdentList.getSpectrumIdentificationResult()) {

                                String specTitle = "";
                                for(CvParam cvParam : spectrumIdentResult.getCvParam()){
                                    if(cvParam.getName().indexOf("spectrum title") != -1){
                                        specTitle = cvParam.getValue();
                                    }
                                }                                


                                for (SpectrumIdentificationItem spectrumIdentItem : spectrumIdentResult.getSpectrumIdentificationItem()) {

                                    if(spectrumIdentItem.getRank() <= RANK_CUTOFF){

                                        Fragmentation fragmentation = spectrumIdentItem.getFragmentation();
                                        Peptide pep = spectrumIdentItem.getPeptide();
                                        String pepSeq = pep.getPeptideSequence();
                                        String specID = spectrumIdentItem.getId();         

                                        String evalue = "";

                                        for(CvParam cvParam : spectrumIdentItem.getCvParam()){
                                            if(cvParam.getName().indexOf("expectation") != -1){
                                                evalue = cvParam.getValue();
                                            }
                                        }

                                        Double ev_double = Double.parseDouble(evalue);
                                        if(ev_double < 1){


                                            String csvText = HEADER;

                                            for (IonType ionType : fragmentation.getIonType()) {

                                                int charge = ionType.getCharge();
                                                CvParam ionCV = ionType.getCvParam();
                                                String ion = ionCV.getName();

                                                List m_mz, m_intensity, m_mobility;
                                                String mobility;
                                                m_mz = ionType.getFragmentArray().get(0).getValues();
                                                m_intensity = ionType.getFragmentArray().get(1).getValues();
                                                m_mobility = ionType.getFragmentArray().get(3).getValues();

                                                //NOTE: This is likely not the recommoned way of using Lists, but quick hack to get this working
                                                int MAX_ARRAY = 100;
                                                Float[] mzArray = new Float[MAX_ARRAY];
                                                int mzCount  = 0;
                                                for(Float mzValues : ionType.getFragmentArray().get(0).getValues()){
                                                    mzArray[mzCount] = mzValues;
                                                    mzCount++;
                                                    //System.out.println("mzValues:" + mzValues);
                                                }

                                                Float[] intArray = new Float[MAX_ARRAY];
                                                int intCount  = 0;
                                                for(Float intValues : ionType.getFragmentArray().get(1).getValues()){
                                                    intArray[intCount] = intValues;
                                                    intCount++;
                                                }

                                                Float[] mobArray = new Float[MAX_ARRAY];
                                                int mobCount  = 0;
                                                for(Float mobValues : ionType.getFragmentArray().get(3).getValues()){
                                                    mobArray[mobCount] = mobValues;
                                                    mobCount++;

                                                    //System.out.println("mobValues:" + mobValues);
                                                }


                                                for(int i = 0; i<mzCount; i++){
                                                    csvText += pepSeq + "," + evalue + "," + ion + "," + charge + "," + mzArray[i] + "," + intArray[i] + "," + mobArray[i] + ","+ spectrumIdentItem.getRank()+ ","+ spectrumIdentItem.isPassThreshold() + "\n";

                                                }
                                             }

                                            if(allspecHashMap != null){

                                                //String massToCharge = ""+spectrumIdentItem.getExperimentalMassToCharge();
                                                //massToCharge = massToCharge.substring(0,massToCharge.indexOf(".") + 3);
                                                String[] allValues = null;
                                                if(!specTitle.equals("")){
                                                    allValues = (String[])allspecHashMap.get(specTitle);
                                                }
                                                else{
                                                    System.out.println("Error: No spectrum title retrieved");
                                                    System.exit(1);
                                                }


                                                for(int i=0;i<allValues.length;i++){

                                                    if(allValues[i]!=null){
                                                        csvText += pepSeq + "," + evalue + ",no id,0," + allValues[i] + ","+ spectrumIdentItem.getRank()+ ","+ spectrumIdentItem.isPassThreshold() + "\n";
                                                    }
                                                    else{
                                                        i=allValues.length;
                                                    }
                                                }
                                            }
                                            String filename = prefix + "_" + pepSeq + "_" + specID +  ".csv";
                                            BufferedWriter out = new BufferedWriter(new FileWriter((filename)));
                                            out.write(csvText);
                                            csvText = "";
                                            out.close();

                                            callRCommand(filename,pepSeq);
                                        }


                                    }
                                }
                            }
                        }
                    }


                }
                catch(Exception e){
                        e.printStackTrace();
                }
            }
            else{

                System.out.println("Incorrect usage, give 1) input ([file].mzid) 2) the prefix for your output files 3) input MGF file that was search (including drift time) (optional) containing all ions as command line arguments");
            }
    }


    public static void callRCommand(String inputFile, String pepSeq){

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


    public static void parseDriftTimesFromMGF(String inputFile){


        try {
            int spectrumIdentItemCount = 0;

            String inputMobilityFile = inputFile;
            BufferedReader buffer = new BufferedReader(new FileReader(inputMobilityFile));
            String stringLine;
            String[] tokens;

            String[] mobility_tokens = null;

            String[] allValues = null;
            String title;
            int peakCounter = 0;

            while ((stringLine = buffer.readLine()) != null) {
           //    System.out.print("1");
               //System.out.println("stringLine1:" + stringLine);
                if (stringLine.startsWith("BEGIN")
                        || stringLine.startsWith("CHARGE")
                        || stringLine.startsWith("END")
                        || stringLine.startsWith("PEPMASS")
                        ) {
                    continue;
                }
               /*
                else if (stringLine.startsWith("PEPMASS")){
                    stringLine = stringLine.substring(stringLine.indexOf("PEPMASS=")+8);
                    currentPepMass = stringLine.trim();
                    currentPepMass = currentPepMass.substring(0,currentPepMass.indexOf(".") + 3);          //format to 2 dp
                    //System.out.println("pep:" + currentPepMass);
                    allValues = new String[10000];
                    valueCounter = 0;
                    allspecHashMap.put(currentPepMass,allValues);
                }
                */
                else if (stringLine.startsWith("TITLE")) {
                        allValues = new String[10000];
                        peakCounter = 0;
                        
                        title = stringLine.substring(6);
                        allspecHashMap.put(title,allValues);

                        stringLine = stringLine.substring(stringLine.indexOf("DRIFT_TIME=")+11);
                        stringLine = stringLine.trim();
                        mobility_tokens = stringLine.split(" ");
                        

                }
                else {
                    
                    tokens = stringLine.split(" ");
                    
                   // System.out.println("2" + allValues[valueCounter]);
                   if(tokens.length>=2){
                        allValues[peakCounter] = tokens[0] + "," + tokens[1] + ","+ mobility_tokens[peakCounter];
                        peakCounter++;
                   }

                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }


    }

}
