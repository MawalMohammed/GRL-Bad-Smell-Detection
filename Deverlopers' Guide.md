# GSDetector Developers’ Guide

* Objective: 

 	This guide is prepared to help developers extend the functionality of GSDetector by presenting the steps needed to add the functionality of the detection of the instances of a new bad smell. 

* Prerequisite: 

	Developers are expected to have a good background in programming with Java. They are also expected to have a basic understating of Eclipse Plugin development. 

* Recommended:

	Developers are advised to make use of the available resources including the following:

		1.	The paper titled “GSDetector: A tool for automatic bad smell detection in GRL goal models”. This paper introduced the tool and explained several of its aspects including its structure, operation, and model transformation. 
		2.	The comments provided within the code as they explain several implementation details. 
		3.	The available code for reusing and quicker development as several code segments are structural and can be reused easily. 

* Developers’ Guide: 

    To add the functionality of detecting a new bad smell, the following steps provide a rough guide for interested developers (Assume “NBS” is the new bad smell):
    
		1.	Update the GUI by updating the GRLBadSmellDetectionPreferences.java, as follows:
  
			a) A checkbox to enable and disable NBS detection  
			b) A label to label the checkbox with NBS name.
			c) A textbox for threshold setting (if needed). Note: Some bad smells might not need a threshold.
			
		2.	Update the data in GRLBSDetectorConstants.java by add the following:

			a)	Two new variables to store the default values of the checkbox and textbox created in the previous step. The default values are used the first time the tool is installed or when the user press the “restore defaults” button in the preferences page.
			b)	Two new variables to store the user defined values of these checkbox and textbox. The user defined values are used when the user define new values. 
			c)	Link these varaibles to the checkbox and textbox created in the previous step.
			
		3.	Update the GRLBadSmellsDetectorEngine.java by  the following:
	
			a)	Examine the “transformGRL2ERD ()” function and the associated “ERD_*” variables to see if the data requirements for detecting the instances of NBS are available. If not, extend the “transformGRL2ERD ()” and the associated “ERD_*” variables according to the new requirements. 
			b)	Add an "if" condition to check whether the detection of the NBS is enabled by examining the value stored in the GRLBSDetectorConstants.java. 
			c)	Add a function call to the detector associated with the NBS by calling the function from the GRLBadSmellDetectors.java (see the next step) and pass the needed ERD_*  data variables. Note: Every bad smell has a detector.
			d)	If needed, pass the threshold value associated with the NBS by retreving it from the data class in GRLBSDetectorConstants. java.
			
		4.	Update the GRLBadSmellDetectors.java by adding a new function (detector) to detect the instances of the NBS. In this function, do the following: 
	
			a)	Add a function call to the detection query associated with the NBS on each targeted object (actor, element, etc.) by calling the query from the GRLBadSmellsDetectionQueries.java (see the next step) and pass the needed data to this query. 
			b)	To report the detected instances of the NBS to the problems view in Eclipse workbench, write a new marker and pass to it the needed data.
			
		5.	Update the GRLBadSmellsDetectionQueries.java by adding a new function (query) to detect instances of NBS (Note: Every bad smell has a query). This function is supposed to return true or false for each examined object. Depending on the new bad smell, most of the coding is expected to happen in this step. In the previous steps, most of the coding is structural and, hence, several segments of the code can be reused with the appropriate modifications. 
