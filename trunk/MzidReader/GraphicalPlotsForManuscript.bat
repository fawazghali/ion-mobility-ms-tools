REM This file needs to be completed with all the scripts for creating the manuscript files

300610-CARBONIC_ANHYDRASE_JUNE_2010_02F011348_plusMob.mzid
CarbonicAnhydrase_210610F011350_plusMob.mzid
carbonic_anhydrase_peptideF011349_plusMob.mzid
filter_ions300610-CARBONIC_ANHYDRASE_JUNE_2010_02F011358_plusMob.mzid
filter_ionsCarbonicAnhydrase_210610F011360_plusMob.mzid
filter_ionscarbonic_anhydrase_peptideF011359_plusMob.mzid
filter_ionsGlycogen_phosphorylaseF011361_plusMob.mzid
filter_ionsGlyphos_250510F011357_plusMob.mzid
Glycogen_phosphorylase_210510F011356_plusMob.mzid
Glyphos_250510F011347_plusMob.mzid

java -jar "dist\MzidReader.jar" ..\Data\IMMS_Data\final_mascot_results\300610-CARBONIC_ANHYDRASE_JUNE_2010_02F011348_plusMob.mzid C:/Work/IonMobility/Code/Jan2011/src/Data/IMMS_Data/ion_plots/300610-CARBONIC_ANHYDRASE_JUNE_2010_02 ..\Data\IMMS_data\final_mgf\300610-CARBONIC_ANHYDRASE_JUNE_2010_02combined_ppmShift.mgf 

.mzid" "C:/Work/IonMobility/Code/Jan2011/src/Data/IMMS_Data/mzIdentML/FragmentCSV/060710-ovalbumin_F010412"  C:\Work\IonMobility\Code\Jan2011\src\Data\IMMS_Data\mgf_Files\mascot_searched\060710-ovalbumin_combined.mgf

java -jar "dist\mobility_mzIdentML.jar" 4 ..\..\Data\IMMS_data\final_mgf\300610-CARBONIC_ANHYDRASE_JUNE_2010_02combined_ppmShift.mgf ..\..\Data\IMMS_data\temp_mascot_res\300610-CARBONIC_ANHYDRASE_JUNE_2010_02F011348.mzid ..\..\Data\IMMS_data\temp_mascot_res\300610-CARBONIC_ANHYDRASE_JUNE_2010_02F011348_plusMob.mzid 

java -jar "dist\mobility_mzIdentML.jar" 4 ..\..\Data\IMMS_data\final_mgf\CarbonicAnhydrase_210610combined_ppmShift.mgf ..\..\Data\IMMS_data\temp_mascot_res\CarbonicAnhydrase_210610F011350.mzid ..\..\Data\IMMS_data\temp_mascot_res\CarbonicAnhydrase_210610F011350_plusMob.mzid

java -jar "dist\mobility_mzIdentML.jar" 4 ..\..\Data\IMMS_data\final_mgf\carbonic_anhydrase_peptidecombined_ppmShift.mgf ..\..\Data\IMMS_data\temp_mascot_res\carbonic_anhydrase_peptideF011349.mzid ..\..\Data\IMMS_data\temp_mascot_res\carbonic_anhydrase_peptideF011349_plusMob.mzid

java -jar "dist\mobility_mzIdentML.jar" 4 ..\..\Data\IMMS_data\final_mgf\filter_ions300610-CARBONIC_ANHYDRASE_JUNE_2010_02_1plusOnlycombined_ppmShift.mgf ..\..\Data\IMMS_data\temp_mascot_res\filter_ions300610-CARBONIC_ANHYDRASE_JUNE_2010_02F011358.mzid ..\..\Data\IMMS_data\temp_mascot_res\filter_ions300610-CARBONIC_ANHYDRASE_JUNE_2010_02F011358_plusMob.mzid

java -jar "dist\mobility_mzIdentML.jar" 4 ..\..\Data\IMMS_data\final_mgf\filter_ionsCarbonicAnhydrase_210610_1plusOnlycombined_ppmShift.mgf ..\..\Data\IMMS_data\temp_mascot_res\filter_ionsCarbonicAnhydrase_210610F011360.mzid ..\..\Data\IMMS_data\temp_mascot_res\filter_ionsCarbonicAnhydrase_210610F011360_plusMob.mzid

java -jar "dist\mobility_mzIdentML.jar" 4 ..\..\Data\IMMS_data\final_mgf\filter_ionscarbonic_anhydrase_peptide_1plusOnlycombined_ppmShift.mgf ..\..\Data\IMMS_data\temp_mascot_res\filter_ionscarbonic_anhydrase_peptideF011359.mzid ..\..\Data\IMMS_data\temp_mascot_res\filter_ionscarbonic_anhydrase_peptideF011359_plusMob.mzid

java -jar "dist\mobility_mzIdentML.jar" 4 ..\..\Data\IMMS_data\final_mgf\filter_ionsGlycogen_phosphorylase_210510_1plusOnlycombined_ppmShift.mgf ..\..\Data\IMMS_data\temp_mascot_res\filter_ionsGlycogen_phosphorylaseF011361.mzid ..\..\Data\IMMS_data\temp_mascot_res\filter_ionsGlycogen_phosphorylaseF011361_plusMob.mzid

java -jar "dist\mobility_mzIdentML.jar" 4 ..\..\Data\IMMS_data\final_mgf\filter_ionsGlyphos_250510_1plusOnlycombined_ppmShift.mgf ..\..\Data\IMMS_data\temp_mascot_res\filter_ionsGlyphos_250510F011357.mzid ..\..\Data\IMMS_data\temp_mascot_res\filter_ionsGlyphos_250510F011357_plusMob.mzid

java -jar "dist\mobility_mzIdentML.jar" 4 ..\..\Data\IMMS_data\final_mgf\Glycogen_phosphorylase_210510combined_ppmShift.mgf ..\..\Data\IMMS_data\temp_mascot_res\Glycogen_phosphorylase_210510F011356.mzid ..\..\Data\IMMS_data\temp_mascot_res\Glycogen_phosphorylase_210510F011356_plusMob.mzid

java -jar "dist\mobility_mzIdentML.jar" 4 ..\..\Data\IMMS_data\final_mgf\Glyphos_250510combined_ppmShift.mgf ..\..\Data\IMMS_data\temp_mascot_res\Glyphos_250510F011347.mzid ..\..\Data\IMMS_data\temp_mascot_res\Glyphos_250510F011347_plusMob.mzid

