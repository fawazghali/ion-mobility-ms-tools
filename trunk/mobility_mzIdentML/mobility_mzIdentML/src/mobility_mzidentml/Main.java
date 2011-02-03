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

/**
 * Main.java
 *
 * Created on 22-Nov-2010, 13:25:25
 *
 * @author Fawaz Ghali
 */
public class Main {

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

                if(filterIons(tokens[m_z_index],tokens[mobility_index])){

                    m_z_list.add(tokens[m_z_index]);
                    intensity_list.add(tokens[intensity_index]);
                    mobility_list.add(tokens[mobility_index]);
                    parent_list.add(tokens[parent_index]);
                    charge_list.add(tokens[charge_index]);
                }
            }

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

    /*
     * A method to calculate collision cross section for ions and if exclude them from the MGF file if they fall outside set limits
     *
     */
    public static Boolean filterIons(String mz, String mob){
        Boolean withinRange = true;

        Double mzDouble = Double.parseDouble(mz);
        Double mobDouble = Double.parseDouble(mob);
        Double ccs = mzDouble / mobDouble;

        if(mzDouble < 10 * mobDouble + 180){

            //System.out.println("Acceptable:" + mzDouble + " " + mobDouble);
        }
        else{

            //System.out.println("Unacceptable:" + mzDouble + " " + mobDouble);
        }

        //Approx separation line is y = 10x + 180




        return withinRange;
    }

}
