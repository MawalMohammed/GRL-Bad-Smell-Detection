package org.eclipse.jucmnav.grl.GSDetector;

import java.util.ArrayList;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import seg.jUCMNav.editors.UCMNavMultiPageEditor;

/**
 * Copyright (C) 2022 Mawal Mohammed - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the Eclipse Public License - v 2.0 ,
 */

public class GRLBadSmellDetectors {


UCMNavMultiPageEditor jucmEditor;
private IFile activeFile;
Document GRL; 						// The GRL model - the jucm file containing the model

GRLBadSmellsDetectionQueries detectionQuery ; 

//Initialization of the detection process
public GRLBadSmellDetectors(UCMNavMultiPageEditor jmEditor, IFile jmFile, Document jmDoc) {
	 
jucmEditor=jmEditor;
activeFile=jmFile;
GRL=jmDoc;
	
detectionQuery = new GRLBadSmellsDetectionQueries();
	
}


//Overly Populated Model Bad Smell
//Too many actors in a model

public void overlyPopulatedModelDetector(String ERD_actors[][], int overlyPopulatedModelThreshold) {
	
	try {
			
			NodeList graphList = GRL.getElementsByTagName("specDiagrams");
			Node nNode = graphList.item(0);
			Element eElement = (Element) nNode;
			  if(detectionQuery.overlyPopulatedModelBS(ERD_actors, overlyPopulatedModelThreshold )) {
				  writeMarkers(activeFile, "Instance of the overly populated model bad smell","overlyPopulatedModelBS",jucmEditor.getTitle(),"graph","",eElement.getAttribute("id"));
		}		
	} catch (Exception e) {
		e.printStackTrace();
	}
}			

//Overly Populated Actor Bad Smell
//Too many elements in an actor

public void overlyPopulatedActorDetector(String ERD_actors[][], ArrayList<ArrayList<Integer>> ERD_listOfIntentElmntsByActor,  int overlyPopulatedActorThreshold) {
	
	try {
		
		for (int i=0; i < ERD_actors.length; i++) {

			//System.out.println("no of elements in actor: " + ERD_actors[i][1] + " equals to : "+ ERD_listOfIntentElmntsByActor.get(i).size() );
			if(detectionQuery.overlyPopulatedActorBS (ERD_listOfIntentElmntsByActor.get(i), overlyPopulatedActorThreshold) ) {
				writeMarkers(activeFile, "Instance of the overly populated actor bad smell","overlyPopulatedActorBS",ERD_actors[i][1],"actor","",ERD_actors[i][0]);
			}
		}
	}catch (Exception e) {
		e.printStackTrace();
	}
}	

//Overly Ambitious Actor Bad Smell
//Too many goals and softgoals

public void overlyAmbitiousActorDetector(String ERD_actors[][], int [][] ERD_allLinkRefs, ArrayList<ArrayList<Integer>> ERD_listOfIntentElmntRefsByActor, String [][] ERD_allElements,int [][] ERD_allIntentionalElementRefs, ArrayList<ArrayList<Integer>> ERD_listOfIntenElmntRefsByElement, int overlyAmbitiousActorThreshold) {

	try {
		
	  	for (int i=0; i<ERD_actors.length;i++) {
			
	  		//System.out.println("no of goals and softgoals: " + actors[i][1] + " equals to : "+ listOfGoalsAndSoftgoalsRefsByActor.get(i).size() );
	  		if(detectionQuery.overlyAmbitiousActorBS(ERD_allLinkRefs, ERD_listOfIntentElmntRefsByActor.get(i), ERD_allElements, ERD_allIntentionalElementRefs, ERD_listOfIntenElmntRefsByElement, overlyAmbitiousActorThreshold)) {
	  			writeMarkers(activeFile, "Instance of the overly ambitious actor bad smell","overlyAmbitiousActorBS",ERD_actors[i][1],"actor","",ERD_actors[i][0]);
			}
					
		}
	}catch (Exception e) {
		e.printStackTrace();
	}
}		
//Deep Hierarchy Bad Smell
//Too many refinement levels

public void deepHierarchyDetector(String ERD_actors[][], ArrayList<ArrayList<Integer>> ERD_listOfIntentElmntRefsByActor, int [][] ERD_allLinkRefs,int [][] ERD_allIntentionalElementRefs, ArrayList<ArrayList<Integer>> ERD_allContRefsByActor, int deepHierarchyThreshold) {
	
	try {
			
			for (int i=0; i < ERD_actors.length; i++) {
				
		  		
				if(detectionQuery.deepHierarchyBS (ERD_listOfIntentElmntRefsByActor.get(i), ERD_allLinkRefs, ERD_allIntentionalElementRefs,ERD_allContRefsByActor.get(i), Integer.parseInt(ERD_actors[i][0]), deepHierarchyThreshold)) {
					writeMarkers(activeFile, "Instance of the deep hierarchical  refinement bad smell","deepHierarchyBS",ERD_actors[i][1],"actor","",ERD_actors[i][0]);
				}
			}
	} catch (Exception e) {
		e.printStackTrace();
	}
}
//Overly Operationalized Bad Smell
//Too many operationalizations

public void overlyOperationalizedActorDetector(String ERD_actors[][], int [][] ERD_allLinkRefs, ArrayList<ArrayList<Integer>> ERD_listOfIntentElmntRefsByActor, String [][] ERD_allElements,int [][] ERD_allIntentionalElementRefs, ArrayList<ArrayList<Integer>> ERD_listOfIntenElmntRefsByElement,float overlyOperationalizedActorThreshold) {
	
	try {
				
			for (int i=0; i<ERD_actors.length;i++) {
								
				if(detectionQuery.overlyOperationalizedActorBS(ERD_allLinkRefs,ERD_listOfIntentElmntRefsByActor.get(i), ERD_allElements, ERD_allIntentionalElementRefs,ERD_listOfIntenElmntRefsByElement, overlyOperationalizedActorThreshold )) {
					writeMarkers(activeFile, "Instance of the overly operationalized actor bad smell","overlyOperationalizedActorBS",ERD_actors[i][1],"actor","",ERD_actors[i][0]);
				}
			}
	} catch (Exception e) {
		e.printStackTrace();
	}
}

//Highly Coupled Actor Bad Smell
//Too many dependency links associated with an actor

public void highlyCoupledActorDetector(String ERD_actors[][], ArrayList<ArrayList<Integer>> ERD_listOfIntentElmntRefsByActor, int [][] ERD_allLinkRefs,String [][] ERD_allLinks, int highlyCoupledActorThreshold) {
	
	try {
		
		for (int i=0; i<ERD_actors.length;i++) {
			
				if(detectionQuery.highlyCoupledActorBS (ERD_listOfIntentElmntRefsByActor.get(i),ERD_allLinkRefs, ERD_allLinks,highlyCoupledActorThreshold)) {
					writeMarkers(activeFile, "Instance of the highly coupled actor bad smell","highlyCoupledActorBS",ERD_actors[i][1],"actor","",ERD_actors[i][0]);
				}
			
			}
	} catch (Exception e) {
		e.printStackTrace();
	}
}	
// Highly Proliferated Goal OR SoftGoal Bad Smell
//Too many And-linked children associated with a goal or softgoal 

public void highlyProliferatedGoalORSoftGoalDetector(String [][] ERD_allElements, String [][] ERD_allLinks, int highlyProliferatedGoalORSoftGoalThreshold) {
	
	try {
				
			for (int i=0;i<ERD_allElements.length; i++){
				if( detectionQuery.highlyProliferatedGoalORSoftGoalBS(ERD_allElements[i],ERD_allLinks,highlyProliferatedGoalORSoftGoalThreshold)) {
					writeMarkers(activeFile, "Instance of the highly proliferated goal/softgoal bad smell","highlyProliferatedGoalORSoftGoalBS",ERD_allElements[i][5],"element","",ERD_allElements[i][0]);
				}
			}
	} catch (Exception e) {
		e.printStackTrace();
	}
}

// Highly Proliferated Task Bad smell
//Too many And-linked children associated with a task

public void highlyProliferatedTaskDetector(String [][] ERD_allElements, String [][] ERD_allLinks, int highlyProliferatedTaskThreshold) {
	
	try {
									
			for (int i=0;i<ERD_allElements.length; i++){
				if( detectionQuery.highlyProliferatedTaskBS(ERD_allElements[i],ERD_allLinks,highlyProliferatedTaskThreshold)) {
					writeMarkers(activeFile, "Instance of the highly proliferated task bad smell","highlyProliferatedTaskBS",ERD_allElements[i][5],"element","",ERD_allElements[i][0]);
				}
			}
	} catch (Exception e) {
		e.printStackTrace();
	}
}

//Possibilities Exhauster Bad Smell
//Too many Or-Linked children associated with an element

public void possibilitiesExhausterDetector(String [][] ERD_allElements, String [][] ERD_allLinks, int possibilitiesExhausterThreshold) {
	
	try {
				
			for (int i=0;i<ERD_allElements.length; i++){
				if( detectionQuery.possibilitiesExhausterBS(ERD_allElements[i],ERD_allLinks,possibilitiesExhausterThreshold)) {
					writeMarkers(activeFile, "Instance of the possibilities Exhauster bad smell","possibilitiesExhauster",ERD_allElements[i][5],"element","",ERD_allElements[i][0]);
				}
			}
	} catch (Exception e) {
		e.printStackTrace();
	}
	
}

//Highly Coupled Element Bad Smell
//Too many links associated with an element

public void highlyCoupledElementDetector(String [][] ERD_allElements, int highlyCoupledElementThreshold) {
	
	try {
	 
		  for (int i = 0; i < ERD_allElements.length; i++) {
							 
			 if( detectionQuery.highlyCoupledElementBS(ERD_allElements[i], highlyCoupledElementThreshold)) {
				 writeMarkers(activeFile, "Instance of the highly coupled element bad smell","highlyCoupledElementBS",ERD_allElements[i][5],"element","",ERD_allElements[i][0]);
			 }
		 }
	} catch (Exception e) {
		e.printStackTrace();
	}
	
}

	//Create markers to present the detected instances of bad smells in the problems view of Ecllipse framework

	private void writeMarkers(IResource resource, String message,String badSmell,String location, String sourceType, String sourceName ,String SourceId) {
        try {
            IMarker marker = resource.createMarker("org.eclipse.jucmnav.grl.GSDetector.GRLBadSmellMarker");
           
            marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_WARNING);
            marker.setAttribute(IMarker.MESSAGE, message);
            marker.setAttribute("badSmell", badSmell );
            marker.setAttribute(IMarker.LOCATION, location);
            marker.setAttribute("sourceName", sourceName );
            marker.setAttribute("sourceType", sourceType );
            marker.setAttribute(IMarker.SOURCE_ID, SourceId);
          
          
           } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	
}
