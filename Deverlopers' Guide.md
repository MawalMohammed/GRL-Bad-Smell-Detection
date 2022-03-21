# GSDetector Developers’ Guide

* Objective: 

 	This guide is prepared to help developers extend the functionality of GSDetector by presenting the steps needed to detect the instances of a new bad smell. 

* Prerequisite: 

	Developers are expected to have a good background in programming with Java. They also expected to have a basic understating of Eclipse Plugin development. 

* Recommended:

	Developers are advised to make use of the available resources including the following:

		1.	The paper titled “GSDetector: A tool for automatic bad smell detection in GRL goal models”. This paper introduced the tool and explained several of its aspects including its structure, operation, and model transformation. 
		2.	The comments provided within the code as they explain several implementation details. 
		3.	The available code for reusing and quicker development as several code segments are structural and can be reused easily. 

* Developers’ Guide: 

    To add the functionality of detecting a new bad smell, the following steps provides a rough guide for interested developers (Assume “NBS” is the new bad smell):
    
		1.	In the GRLBadSmellDetectionPreferences class, add the following:
  
			a) A checkbox to enable and disable NBS detection.
			b) A label to label the checkbox with NBS name.
			c) A textbox for threshold setting (if needed). Note: Some bad smells might not need a threshold.
			
		2.	In the GRLBSDetectorConstants class, add the following:

			a)	Two new variables to store the default values of the checkbox and textbox created in the previous step. The default values are used the first time the tool is installed or when the user press the “restore defaults” button in the preferences page.
			b)	Two new variables to store the user defined values of these checkbox and textbox. The user defined values are used when the user define new values. 
			
		3.	In the GRLBadSmellsDetectorEngine class, do the following:
	
			a)	Examine the “transformGRL2ERD ()” function and the associated “ERD_*” variables to see if the data requirements for detecting the instances of NBS are available. If not, extend the “transformGRL2ERD ()” and the associated “ERD_*” variables according to the new requirements. 
			b)	Add a condition to check whether the detection of the NBS is enabled in the preferences page as stored in the “GRLBSDetectorConstants” class. 
			c)	Call the detector associated with the NBS from the “GRLBadSmellDetectors” (see the next step) and pass the needed ERD_*  data variables. Note: Every bad smell has a detector.
			d)	If needed, pass the threshold value associated with the NBS in the preferences page as stored in the in the “GRLBSDetectorConstants” class.
			
		4.	In the GRLBadSmellDetectors class, add a new detector for the NBS. In this detector, do the following: 
	
			a)	Call the detection query associated with the NBS on each targeted object (actor, element, etc.) from the “GRLBadSmellsDetectionQueries” class (see the next step) and pass the needed data to this query. 
			b)	To report the detected instances of the NBS to the problems view in Eclipse workbench, write a new marker and pass to it the needed data.
			
		5.	In the GRLBadSmellsDetectionQueries class, add the following:

			a)	A query (i.e., detection function) to detect instances of NBS (Note: Every bad smell has a query). This query is supposed to return true or false for each object examined. Depending on the new bad smell, most of the coding is expected to happen in this step. In the previous steps, most of the coding is structural and, hence, several segments of the code can be reused with the appropriate modifications. 
