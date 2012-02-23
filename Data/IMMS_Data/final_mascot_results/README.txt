This directory contains the output of the Mascot search in mzIdentML format plus the insertion of drift time values into mzIdentML files (stages 3 and 4 below). 
The CSV files were required as the source of the mobility data.  

There are two versions for each peptide-spectrum match (PSM), with and without the filter_ions mode switched on. As such, comparing the same PSM shows the effectiveness of the filter_ions module.

*******************************

Files have been processed as follows in the complete pipeline:

1. Driftscope exported CSV converted to MGF (plus mobility)
	a) Files with the prefix filter_ions had the filter_ions mode switched on to remove candidate 2+ ions
2. The MGF file has been searched using Mascot.
3. We have exported mzIdentML results from Mascot.
4. We have used the mobility_mzIdentML code to insert mobility values in mzIdentML.
5. We have used the MzidReader to extract identified fragment products from mzIdentML and inserted into R for ion plotting

*******************************


