This directory contains the MGF files that were searched in Mascot (stages 1 and 2).

We observed that on certain runs there was a systematic shift in the m/z values so a ppm shift has been made on all peaks prior to searching.

*******************************

Files have been processed as follows in the complete pipeline:

1. Driftscope exported CSV converted to MGF (plus mobility)
	a) Files with the prefix filter_ions had the filter_ions mode switched on to remove candidate 2+ ions
2. The MGF file has been searched using Mascot.
3. We have exported mzIdentML results from Mascot.
4. We have used the mobility_mzIdentML code to insert mobility values in mzIdentML.
5. We have used the MzidReader to extract identified fragment products from mzIdentML and inserted into R for ion plotting

*******************************


