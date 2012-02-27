This directory contains the code for parsing mzIdentML files after mobility values have been inserted (stage 5).

*******************************

Files have been processed as follows in the complete pipeline:

1. Driftscope exported CSV converted to MGF (plus mobility)
	a) Files with the prefix filter_ions had the filter_ions mode switched on to remove candidate 2+ ions
2. The MGF file has been searched using Mascot.
3. We have exported mzIdentML results from Mascot.
4. We have used the mobility_mzIdentML code to insert mobility values in mzIdentML.
5. We have used the MzidReader to extract identified fragment products from mzIdentML and inserted into R for ion plotting

*******************************





INSTRUCTIONS FOR USE
***********************

On a windows operating system, the following command will parse the mzIdentML file, apply the second argument as a filename prefix in R and use the MGF file supplied to retrieve all other ions that were searched to complete the ion plot:

java -jar "dist\MzidReader.jar" "..\Data\IMMS_Data\temp_mascot_res\300610-CARBONIC_ANHYDRASE_JUNE_2010_02F011348_plusMob.mzid" "300610-CARBONIC_ANHYDRASE_JUNE_2010_02F011348_temp"  ..\Data\IMMS_Data\final_mgf\300610-CARBONIC_ANHYDRASE_JUNE_2010_02combined_ppmShift.mgf

The three arguments are mandatory:
java -jar "dist\MzidReader.jar" [INPUT_MZID_FILE] [DIR_FILE_PREFIX] [INPUT_MGF_FILE]

The DIR_FILE_PREFIX allows R to write results to a specific location with a specific file prefix, however, if you select a particular directory e.g. results directory, you need to ensure that a results directory already exists and has permission for file writing:

java -jar "dist\MzidReader.jar" "..\Data\IMMS_Data\final_mascot_results\300610-CARBONIC_ANHYDRASE_JUNE_2010_02F011348_plusMob.mzid" "results300610-CARBONIC_ANHYDRASE_JUNE_2010_02F011348_temp"  ..\Data\IMMS_Data\final_mgf\300610-CARBONIC_ANHYDRASE_JUNE_2010_02combined_ppmShift.mgf


