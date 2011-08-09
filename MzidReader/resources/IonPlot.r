
ionPlot<-function(inputFile,outputFile,title){
	testCSV<-read.csv(inputFile, header = TRUE)
	
	
	i<-( testCSV$Ion_type=="frag: b ion" & testCSV$charge=="1" & testCSV$M_Z > 0 &testCSV$Mobility > 0)   	# filter entries out from one csv column, in this case (1+) b ions from Charge_IonType column
		bMob<-testCSV$Mobility[i]
		bMZ<-testCSV$M_Z[i]																	# create a 2-D array of coordinates to be used on the plot
		
		
	j<-( testCSV$Ion_type=="frag: y ion" & testCSV$charge=="1" & testCSV$M_Z > 200 &testCSV$Mobility > 0)   	#for linear regression exclude low M_Z values
		yMob<-testCSV$Mobility[j]
		yMZ<-testCSV$M_Z[j]												
		
	k<-( testCSV$Ion_type=="frag: y ion - NH3" & testCSV$charge=="1" & testCSV$M_Z > 0 &testCSV$Mobility > 0)   	
		yNH3Mob<-testCSV$Mobility[k]
		yNH3MZ<-testCSV$M_Z[k]

	l<-( testCSV$Ion_type=="frag: b ion - NH3" & testCSV$charge=="1" & testCSV$M_Z > 0 &testCSV$Mobility > 0)   	
		bNH3Mob<-testCSV$Mobility[l]
		bNH3MZ<-testCSV$M_Z[l]

	m<-( testCSV$Ion_type=="frag: a ion" & testCSV$charge=="1" & testCSV$M_Z > 0 &testCSV$Mobility > 0)   	
		aMob<-testCSV$Mobility[m]
		aMZ<-testCSV$M_Z[m]

	n<-( testCSV$Ion_type=="no id"  )   	# no ion type
		allMob<-testCSV$Mobility[n]
		allMZ<-testCSV$M_Z[n]

	o<-( testCSV$charge=="2" &testCSV$Mobility > 0 )   	# charge = 2
		twoMob<-testCSV$Mobility[o]
		twoMZ<-testCSV$M_Z[o]		
	
	
	jpeg(filename = outputFile, width = 15, height = 10,
		 units = "in", pointsize = 15, quality = 75, bg = "white",
		 res = 300, restoreConsole = TRUE) 								# create a jpeg file for plotting

 
	rx<-range(0,allMob)		 
	ry<-range(0,allMZ)														# define the range of axes according to the highest value in the data


	
		
	plot(rx,ry, ylab="M_Z", xlab="Mobility", main=title, type="n") 
		points(allMob,allMZ, col="black", pch=3, lwd=1)
		points(bMob,bMZ, col="blue", pch=20)					
		points(yMob,yMZ, col="green", pch=20)	
		points(yNH3Mob,yNH3MZ, col="red" , pch=20)
		points(bNH3Mob,bNH3MZ, col="grey", pch=20)
		points(aMob,aMZ, col="purple", pch=20)
		points(twoMob,twoMZ, col="black", pch=15)
				
			# fit linear regression for y ions if there are sufficient values
		if(length(yMob) > 1){
			liney <- lm(yMZ ~ yMob)		
			abline(liney, col = "green")
			lm_y<-round(coef(liney),3)
			mtext(bquote("y Ions (green)": y == .(lm_y[2])*x + .(lm_y[1])), adj=1, padj=0, side = 1, line = 2)				
		}
			
		mtext(bquote("all ions (cross), y ions (green), b ions (blue), yNH3 (red)"), adj=1, padj=0, side = 1, line = 3)
		mtext(bquote("bNH3 grey, a ions (purple), 2+ (square)"), adj=1, padj=0, side = 1, line = 4)
		
	
															# the above line is to display equations at the bottom right corner of the plot
	dev.off()												#close plotting device
}