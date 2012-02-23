These directories contains the CSV files that were exported from DriftScope following peak identification (prior to stage 1).  

The native exports are exactly as produced by DriftScope. The modified versions have extra columns we have added, notably the parent ion m/z and charge, which is required for the database search. This is not produced natively from DriftScope, and we have reported this issue to Waters for modification in future versions. At present, the parent ion m/z and charge need to be deduced by cross-referencing to ProteinLynx software, using retention time for matching. Our data sets were for direct infusion of single peptides so there RT values for each peptide is zero.

*******************************

Files have been processed as follows in the complete pipeline:

1. Driftscope exported CSV converted to MGF (plus mobility)
	a) Files with the prefix filter_ions had the filter_ions mode switched on to remove candidate 2+ ions
2. The MGF file has been searched using Mascot.
3. We have exported mzIdentML results from Mascot.
4. We have used the mobility_mzIdentML code to insert mobility values in mzIdentML.
5. We have used the MzidReader to extract identified fragment products from mzIdentML and inserted into R for ion plotting

*******************************


