# ProteoIMMS Toolkit #

The research group at the University of Liverpool, led by Dr Andy Jones is developing software tools for handling ion mobility mass spectrometry data, used in proteomics.

All software tools and example data sets are hosted here.

## Summary of Tools ##


IMMS data can be exported from IM enabled instruments in CSV format, capturing data on M/Z, intensity and mobility (drift time), as well as normalised values. Datasets and tools concern MS2 data only at this stage.

1. Java converter:
> - from CSV to MGF
> - insertion of IM data into PSI mzIdentML standard

2. MzidReader:
> - Reads mzIdentML files (for example exported from Mascot) where identified fragment ions have been assigned mobility values, suitable for import into R.

3. R converter and plotter:
> - Load CSV data into R and plot identified ions with linear regressions.

## SYSTEM REQUIREMENTS ##

The software has only been extensively tested on Windows platforms, but all code is written in Java and R and thus should be functional on other operating systems.

Java - To run the software, you will need to have Java installed (tested on Java version 6 onwards).

R - You will also need to have R installed (tested on versions 2.8 onwards) and make sure that the environment variables are set so that R can be run from the command line. Java makes a call via "R CMD BATCH" - check that this is possible on your system other the MzidReader will have an error.

The software can be downloaded from http://code.google.com/p/ion-mobility-ms-tools/downloads/list and there are installation instructions on the Wiki:
http://code.google.com/p/ion-mobility-ms-tools/w/list


## Publications ##

Xia, D., Ghali, F., Gaskell, S. J., O'Cualain, R., Sims, P. F. G., and Jones, A. R. (2012) Software for analysing ion mobility mass spectrometry data to improve peptide identification. PROTEOMICS 12, 1912-1916.

Please cite this article if you use the toolkit in your work.

## Pipeline Structure ##
![http://ion-mobility-ms-tools.googlecode.com/files/Pipeline.png](http://ion-mobility-ms-tools.googlecode.com/files/Pipeline.png)


## Example results ##

B and y ions can be separated by differential IM values for some peptides but not others:

![http://ion-mobility-ms-tools.googlecode.com/files/SeparatingBandYions.png](http://ion-mobility-ms-tools.googlecode.com/files/SeparatingBandYions.png)

The toolkit contains a module for removing higher charge state ions from MS2 spectra, based on their drift time values for use in database searches (before and after running the tool):

![http://ion-mobility-ms-tools.googlecode.com/files/RemovingHigherChargeIons.png](http://ion-mobility-ms-tools.googlecode.com/files/RemovingHigherChargeIons.png)


