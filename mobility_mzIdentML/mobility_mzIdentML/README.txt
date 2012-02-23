README.txt
**********************

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
java -jar "C:\Work\IonMobility\Code\Jan2011\src\mobility_mzIdentML\mobility_mzIdentML\dist\mobility_mzIdentML.jar" 1 [input.csv] [output.mgf]


2- to convert from CSV to MGF with drift time, with the following example command
java -jar "C:\Work\IonMobility\Code\Jan2011\src\mobility_mzIdentML\mobility_mzIdentML\dist\mobility_mzIdentML.jar" 2 [input.csv] [output.mgf] [filter_ions| ]

If the filter_ions flag is given, only predicted 1+ ions will be exported to the CSV file. Otherwise, leave this parameter blank. To use this parameter, an output file name for the MGF file must be given.

3- to add drift time to mzIdentML from CSV.
java -jar "C:\Work\IonMobility\Code\Jan2011\src\mobility_mzIdentML\mobility_mzIdentML\dist\mobility_mzIdentML.jar" 3 [input.csv] [input.mzid] [output.mzid]

4- to add drift time to mzIdentML from MGF.
java -jar "C:\Work\IonMobility\Code\Jan2011\src\mobility_mzIdentML\mobility_mzIdentML\dist\mobility_mzIdentML.jar" 4 [input.mgf] [input.mzid] [output.mzid]

Here the input.mgf must have been created by the script above (option 2)


