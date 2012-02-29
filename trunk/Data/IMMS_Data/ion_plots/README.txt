This directory contains the output of the ion plotter run on mzIdentML files both where the ion filter has been used and without the ion filter  (stage 5 below). 
We have also exported the Mascot results to the Mascot native CSV format for comparison.

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


