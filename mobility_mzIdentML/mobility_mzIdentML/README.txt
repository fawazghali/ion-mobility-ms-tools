This directory contains the code for creating MGF files suitable for searching and inserting mobility values in mzIdentML files, following a Mascot search (stages 2 and 4).

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

There are several modes of operation for the the mobility parsers.

Command line menu mode
***********************

If you run the following command, you will get a command line menu of options (assuming a Windows operating system):

java -jar "dist\mobility_mzIdentML.jar"

Under Linux or Mac, the script will be as follows:

java -jar "dist/mobility_mzIdentML.jar"


The menu gives the following options:
1- to convert from CSV to MGF without drift time.
2- to convert from CSV to MGF with drift time.
3- to add drift time to mzIdentML from CSV.
4- to add drift time to mzIdentML from MGF.
5- to exit.


*********************************************************************


Command line mode without menus
*******************************

The same options as above can be accessed directly from the command line, as follows (replace filenames in square brackets with local files):

1- to convert from CSV to MGF without drift time.
java -jar "dist\mobility_mzIdentML.jar" 1 [input.csv] [output.mgf]


2- to convert from CSV to MGF with drift time, with the following example command
java -jar "dist\mobility_mzIdentML.jar" 2 [input.csv] [output.mgf] [filter_ions| ]

If the filter_ions flag is given, only predicted 1+ ions will be exported to the MGF file. Otherwise, leave this parameter blank. To use this parameter, an output file name for the MGF file must be given.

3- to convert from CSV to MGF with drift time (inserting charge assignment), with the following example command
java -jar "dist\mobility_mzIdentML.jar" 2 [input.csv] [output.mgf] [insert_ions| ]

If the insert_ions flag is given, the predicted charge will be exported as a third column of the MGF file after each intensity value. Please note, many search engines may not accept this format

4- to add drift time to mzIdentML from CSV.
java -jar "dist\mobility_mzIdentML.jar" 3 [input.csv] [input.mzid] [output.mzid]

5- to add drift time to mzIdentML from MGF.
java -jar "dist\mobility_mzIdentML.jar" 4 [input.mgf] [input.mzid] [output.mzid]

Here the input.mgf must have been created by the script above (option 2)


