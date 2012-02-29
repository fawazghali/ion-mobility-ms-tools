#java -jar "C:/Work/IonMobility/Code/Jan2011/src/mobility_mzIdentML/mobility_mzIdentML/dist/mobility_mzIdentML.jar" 2 "C:/Work/IonMobility/Code/Jan2011/src/Data/IMMS_Data/mgf_Files/inputs/060710-ovalbumin-combined.csv" "C:/Work/IonMobility/Code/Jan2011/src/Data/IMMS_Data/mgf_Files/outputs/060710-ovalbumin-combined.mgf"

#java -jar "C:/Work/IonMobility/Code/Jan2011/src/mobility_mzIdentML/mobility_mzIdentML/dist/mobility_mzIdentML.jar" 2 "C:/Work/IonMobility/Code/Jan2011/src/Data/IMMS_Data/mgf_Files/inputs/300610-CARBONIC_ANHYDRASE_JUNE_2010_02-combined.csv" "C:/Work/IonMobility/Code/Jan2011/src/Data/IMMS_Data/mgf_Files/outputs/300610-CARBONIC_ANHYDRASE_JUNE_2010_02-combined.mgf"

#java -jar "C:/Work/IonMobility/Code/Jan2011/src/mobility_mzIdentML/mobility_mzIdentML/dist/mobility_mzIdentML.jar" 2 "C:/Work/IonMobility/Code/Jan2011/src/Data/IMMS_Data/mgf_Files/inputs/bgal_28_may-combined.csv" "C:/Work/IonMobility/Code/Jan2011/src/Data/IMMS_Data/mgf_Files/outputs/bgal_28_may-combined.mgf"


## Perl script to set complete pipeline going for all files in a directory
## hard code the file directory and the command


use strict;
my $file_dir = @ARGV[0];
my $prefix = @ARGV[1];
my $filter_ions = @ARGV[2];

my $comm = "java -jar \"dist/mobility_mzIdentML.jar\" 2 "; 


opendir(DIR, $file_dir);

while (my $file = readdir(DIR)) {
	
	if(-f $file_dir . $file){
	
		if($file =~ m/.csv/){		

	
			my $local_cmd = $comm . "\"". $file_dir . $file . "\" ";
			
			$file =~ s/.csv/.mgf/;	#change file extension
			$local_cmd .= "\"".$file_dir . $file."\"  $filter_ions ";
			
			print "$local_cmd\n";
			
			#system($local_cmd);			
			
		}
	}
}

chdir($file_dir);
#system("del *.txt");
my $new_cmd = "for %f in (*.mgf) do type %f >> ".$filter_ions.$prefix."combined.txt";
print "cd $file_dir\n";
print "$new_cmd\n";
system($new_cmd);

$file_dir = substr($file_dir,rindex($file_dir,"\\")+1);

system("del *.mgf");
print "del *.mgf\n";

#$new_cmd = "move combined.txt ".$filter_ions."_".$prefix."_combined.mgf";
#print "$new_cmd\n";
#system($new_cmd);

