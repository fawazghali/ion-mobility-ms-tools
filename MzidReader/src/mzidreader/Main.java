/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mzidreader;

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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

            if(args.length == 2){
                String mzid = args[0];
                String output = args[1];

                MzIdentMLUnmarshaller unmarshaller = new MzIdentMLUnmarshaller(new File(mzid));
                MzIdentML mzIdentML_whole = unmarshaller.unmarshal(MzIdentML.class);
                AnalysisData analysisData = mzIdentML_whole.getDataCollection().getAnalysisData();
                List<SpectrumIdentificationList> spectrumIdentificationList = analysisData.getSpectrumIdentificationList();


                try{
                    BufferedWriter out = new BufferedWriter(new FileWriter((output)));
                    String csvText = "Peptide,E-value,Ion type,charge,M/Z,Intensity,Mobility,Rank,PassMascotThreshold\n";

                    if (spectrumIdentificationList != null && !spectrumIdentificationList.isEmpty()) {

                        for (SpectrumIdentificationList sIdentList : spectrumIdentificationList) {
                            for (SpectrumIdentificationResult spectrumIdentResult : sIdentList.getSpectrumIdentificationResult()) {
                                for (SpectrumIdentificationItem spectrumIdentItem : spectrumIdentResult.getSpectrumIdentificationItem()) {

                                    Fragmentation fragmentation = spectrumIdentItem.getFragmentation();
                                    Peptide pep = spectrumIdentItem.getPeptide();
                                    String pepSeq = pep.getPeptideSequence();
                                    
                                    

                                    String evalue = "";

                                    for(CvParam cvParam : spectrumIdentItem.getCvParam()){
                                        if(cvParam.getName().indexOf("expectation") != -1){
                                            evalue = cvParam.getValue();
                                        }
                                    }


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

                                            //System.out.print("line:" + csvText);

                                     }
                                    
                                        

                                }
                            }
                        }
                    }

                    out.write(csvText);


                }
                catch(Exception e){
                        e.printStackTrace();
                }
            }
            else{

                System.out.println("Incorrect usage, give input ([file].mzid) and output ([file].csv file names as command line arguments");
            }
    }

}
