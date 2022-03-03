package org.eclipse.jucmnav.grl.GSDetector;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import seg.jUCMNav.editors.UCMNavMultiPageEditor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;

/**
 * Copyright (C) 2022 Mawal Mohammed - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the Eclipse Public License - v 2.0 ,
 */

public class GRLBadSmellsDetectorEngine implements IEditorActionDelegate {

private IWorkbenchPage activePage;
private IEditorPart  activeEditor;
UCMNavMultiPageEditor jucmEditor;
private IFile activeFile;

String fileLoc;
Document GRL;

NodeList actorList;

//ERD Transformation Tables 

/*
* 1) The index of actors is taken as a key to access contRefs and nodes of each 
*    actor in ERD_allContRefsByActor and listOfNodesByActor.
* 2) The index of element is taken as a key to access elementRefs in ERD_listOfIntenElmntRefsByElement
* 3) actors have references (contrefs) that represents the occurrences of that actors in the 
* 	  different graphs within the same model
* 4) Intentional elements have references (IntentionalElementRef) that represents the 
*    occurrences of that element in the different graphs within the same model 
*/

String [][] ERD_actors; 										//id, name 
ArrayList<ArrayList<Integer>> ERD_allContRefsByActor;			//list of contRefs for each actor identified by the index of each actor
ArrayList<ArrayList<Integer>> ERD_listOfIntentElmntRefsByActor;	//list of intentional elements references in each actor identified by the index of each actor
ArrayList<ArrayList<Integer>> ERD_listOfIntentElmntsByActor;	//list of intentional elements in each actor identified by the index of each actor
String [][] ERD_allElements;									//id, type, linkDest, linkSrc, decompositionType, name, xsi:type 
ArrayList<ArrayList<Integer>> ERD_listOfIntenElmntRefsByElement;//list of elementRefs for each element identified by the index of each element
int [][] ERD_allIntentionalElementRefs; 						//id, def, contRef
int [][] ERD_allLinkRefs;										//source, target, link
String [][] ERD_allLinks;										//xsi:type, id, dest


//The transformation logic
public void transformGRL2ERD(Document GRL) {
 	
	try {
		
		NodeList actorList = GRL.getElementsByTagName("actors");
			
		ERD_actors  = new String [actorList.getLength()][2];
			
		ERD_allContRefsByActor =  new ArrayList<>(actorList.getLength());
			
		for(int q=0; q < actorList.getLength(); q++) {
			ERD_allContRefsByActor.add(new ArrayList<Integer>());
		}
			
		ArrayList<ArrayList<Integer>> listOfNodesByActor =  new ArrayList<>(actorList.getLength());
			
		for(int q=0; q < actorList.getLength(); q++) {
			listOfNodesByActor.add(new ArrayList<Integer>());
		}
			
		ERD_listOfIntentElmntsByActor =new ArrayList<>(actorList.getLength());
			
		for(int q=0; q < actorList.getLength(); q++) {
			ERD_listOfIntentElmntsByActor.add(new ArrayList<Integer>());
		}
		
		ERD_listOfIntentElmntRefsByActor =new ArrayList<>(actorList.getLength());
		
		for(int q=0; q < actorList.getLength(); q++) {
			ERD_listOfIntentElmntRefsByActor.add(new ArrayList<Integer>());
		}
		
		NodeList ElementsList = GRL.getElementsByTagName("intElements");
		int noOfElements=ElementsList.getLength();
		
		ERD_listOfIntenElmntRefsByElement =  new ArrayList<>(noOfElements);
	 
		for(int q=0; q < noOfElements; q++) {
			ERD_listOfIntenElmntRefsByElement.add(new ArrayList<Integer>());
		}
			 
			 
		/*
		 * In this for loop
		 * (1) The actors are extracted into ERD_actors
		 * (2) The contRefs of each actor are extracted into ERD_allContRefsByActor
		 * (3) The nodes of each actor are extracted into listOfNodesByActor					
		 */
		for (int i=0; i<actorList.getLength();i++) {
			
			Node aNode = actorList.item(i);
			Element aElement = (Element) aNode;
			ERD_actors[i][0]=aElement.getAttribute("id");
			ERD_actors[i][1]=aElement.getAttribute("name");
			
			String contRef=aElement.getAttribute("contRefs");
			
			if(contRef!=""){
				String[] integerStringsOfContRefs = contRef.split(" "); 
				
				for (int j = 0; j < integerStringsOfContRefs.length; j++){
					ERD_allContRefsByActor.get(i).add(Integer.parseInt(integerStringsOfContRefs[j])); 
				}
				
				
				for (int cr=0;cr<ERD_allContRefsByActor.get(i).size();cr++) {
				
					NodeList contRefsList = GRL.getElementsByTagName("contRefs");
					if (contRefsList.getLength()>0) {
							
						String nodesInActor ;
						String contId;
						
						for (int temp = 0; temp < contRefsList.getLength(); temp++) {
							Node nnNode = contRefsList.item(temp);
							Element eeElement = (Element) nnNode;
							contId=eeElement.getAttribute("id");
							nodesInActor=eeElement.getAttribute("nodes");
							if(ERD_allContRefsByActor.get(i).get(cr) == Integer.parseInt(contId) && nodesInActor!=""){
								String[] integerStringsOfNodesInContRef = nodesInActor.split(" "); 
								for (int ii = 0; ii < integerStringsOfNodesInContRef.length; ii++){
									listOfNodesByActor.get(i).add(Integer.parseInt(integerStringsOfNodesInContRef[ii])); 
								}
							}
						}
					}
				}
			}
		}
			
		/*
		 * In this loop, 
		 * (1) The intentional elements are extracted into ERD_allElements 
		 * (2) The intentional elements refs of each element 
		 * 	   are extracted into ERD_listOfIntenElmntRefsByElement
		 */
		ERD_allElements=new String [noOfElements][7];
		
		for (int temp = 0; temp < noOfElements; temp++) {
			Node intNode = ElementsList.item(temp);
			Element intElement = (Element) intNode; 	// intentional elements
			ERD_allElements[temp][0]= intElement.getAttribute("id");
			ERD_allElements[temp][1]= intElement.getAttribute("type");
			ERD_allElements[temp][2]= intElement.getAttribute("linksDest");
			ERD_allElements[temp][3]= intElement.getAttribute("linksSrc");
			ERD_allElements[temp][4]= intElement.getAttribute("decompositionType");
			ERD_allElements[temp][5]= intElement.getAttribute("name");
			ERD_allElements[temp][6]= intElement.getAttribute("xsi:type");
			
			String elementRef=intElement.getAttribute("refs");
				 
			if( elementRef!=""){
				String[] integerStringsOfElementRefs =  elementRef.split(" "); 
				for (int j = 0; j < integerStringsOfElementRefs.length; j++){
					ERD_listOfIntenElmntRefsByElement.get(temp).add(Integer.parseInt(integerStringsOfElementRefs[j])); 
				}
			}
		}
			 
		//The number of intentional element refs are calculated in the nodes 
		NodeList intElemsList = GRL.getElementsByTagName("nodes");
		int noOfnodes=intElemsList.getLength();
		int noOfIntElemRefs=0;
		for (int temp = 0; temp < noOfnodes; temp++) {
			Node nNode = intElemsList.item(temp);
			Element eElement = (Element) nNode;
			if (eElement.getAttribute("xsi:type").equalsIgnoreCase("grl:IntentionalElementRef"))
				noOfIntElemRefs++;
		}
		
		/*
		 * In this loop, all the intentional elements refs are extracted into  ERD_allIntentionalElementRefs
		 */
		NodeList IntentionalElementRefsList = GRL.getElementsByTagName("nodes");
		ERD_allIntentionalElementRefs=new int [noOfIntElemRefs][3];
		int counterElementRefs=0;
		for (int temp = 0; temp < IntentionalElementRefsList.getLength(); temp++) {
			Node nNode = IntentionalElementRefsList.item(temp);
			Element eElement = (Element) nNode;
			if (eElement.getAttribute("xsi:type").equalsIgnoreCase("grl:IntentionalElementRef")){
				ERD_allIntentionalElementRefs[counterElementRefs][0]= Integer.parseInt(eElement.getAttribute("id"));
				ERD_allIntentionalElementRefs[counterElementRefs][1]= Integer.parseInt(eElement.getAttribute("def"));
				
				if (!eElement.getAttribute("contRef").isEmpty())
					ERD_allIntentionalElementRefs[counterElementRefs][2]= Integer.parseInt(eElement.getAttribute("contRef"));
				else 
					ERD_allIntentionalElementRefs[counterElementRefs][2]=0;
				
				counterElementRefs++;
			}
		}
		
		for (int i=0; i < ERD_actors.length;i++) {
			for (int j=0; j < listOfNodesByActor.get(i).size(); j++) {
				for (int k = 0; k < ERD_listOfIntenElmntRefsByElement.size(); k++){
					if (ERD_listOfIntenElmntRefsByElement.get(k).contains(listOfNodesByActor.get(i).get(j)))
						if (!ERD_listOfIntentElmntsByActor.get(i).contains(Integer.parseInt(ERD_allElements[k][0])))
							ERD_listOfIntentElmntsByActor.get(i).add(Integer.parseInt(ERD_allElements[k][0]));
				}
			}
			
			for (int l=0; l < ERD_listOfIntentElmntsByActor.get(i).size(); l++) {
				for (int m = 0; m < ERD_allIntentionalElementRefs.length; m++) {
					if (ERD_allIntentionalElementRefs[m][1]==ERD_listOfIntentElmntsByActor.get(i).get(l))
						ERD_listOfIntentElmntRefsByActor.get(i).add(ERD_allIntentionalElementRefs[m][0]);
				}
			}
		}
		//The number of link refs are calculated in the connections 
		NodeList connsList = GRL.getElementsByTagName("connections");
		int noOfConns=connsList.getLength();
		int noOfLinkRefs=0;
		for (int temp = 0; temp < noOfConns; temp++) {
			Node conNode = connsList.item(temp);
			Element conElement = (Element) conNode;
			if (conElement.getAttribute("xsi:type").equalsIgnoreCase("grl:LinkRef"))
				noOfLinkRefs++;
		}
		
		/*
		 * In this loop, all the link refs are extracted into ERD_allLinkRefs
		 */
		NodeList connectionsList = GRL.getElementsByTagName("connections");
		ERD_allLinkRefs=new int [noOfLinkRefs][3];
		int counterLinkRefs=0;
		for (int temp = 0; temp < connectionsList.getLength(); temp++) {
			Node conNode = connectionsList.item(temp);
			Element conElement = (Element) conNode;
			if (conElement.getAttribute("xsi:type").equalsIgnoreCase("grl:LinkRef")){
				ERD_allLinkRefs[counterLinkRefs][0]= Integer.parseInt(conElement.getAttribute("source"));
				ERD_allLinkRefs[counterLinkRefs][1]=Integer.parseInt(conElement.getAttribute("target"));
				ERD_allLinkRefs[counterLinkRefs][2]=Integer.parseInt(conElement.getAttribute("link"));
				counterLinkRefs++;
			}
		}
		
		/*
		 * In this loop, all links are extracted into ERD_allLinks
		 */
		NodeList linkList = GRL.getElementsByTagName("links");
		int noOfLinks=linkList.getLength();
		ERD_allLinks=new String [noOfLinks][3];
		for (int temp = 0; temp < noOfLinks; temp++) {
			Node nNode = linkList.item(temp);
			Element eElement = (Element) nNode;
			ERD_allLinks[temp][0]= eElement.getAttribute("xsi:type");
			ERD_allLinks[temp][1]=eElement.getAttribute("id");
			ERD_allLinks[temp][2]=eElement.getAttribute("dest");
		}
		
	}catch (Exception e) {
		e.printStackTrace();
	}
}			

	@Override
	public void run(IAction action) {
		
		
		GRLMarkerResolution.resetColorOfTheLastChoice();
	
		IPreferenceStore store =  org.eclipse.jucmnav.grl.GSDetector.Activator.getDefault().getPreferenceStore();
		
		activePage = org.eclipse.jucmnav.grl.GSDetector.Activator.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage();
		activeEditor=activePage.getActiveEditor();
		jucmEditor = (UCMNavMultiPageEditor) activeEditor;
		
		jucmEditor.doSave(null);
	
		
		try {
			if(activeEditor  != null)
				{
				FileEditorInput input = (FileEditorInput) jucmEditor.getEditorInput() ;
				activeFile = input.getFile();
				fileLoc= activeFile.getRawLocation().makeAbsolute().toString();
				}
		
				activeFile.deleteMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
			   } catch (Exception e) {
				   e.printStackTrace();
			   }
		// prepare color settings
		GRLBSDetectorConstants.oldColor="";
		GRLBSDetectorConstants.oldSourceID="";
		GRLBSDetectorConstants.oldSourceType="";
		
		
		// Start bad smells detection based on the preferences 
		try {
			
			File jucmFile = new File(fileLoc);
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			GRL = docBuilder.parse(jucmFile);
			GRL.getDocumentElement().normalize();
		}   catch (Exception e) {
			e.printStackTrace();
		}
		
		transformGRL2ERD(GRL); //Transformation mapping of the GRL model into an ERD model
		
		GRLBadSmellDetectors badSmellsDetector= new GRLBadSmellDetectors(jucmEditor, activeFile, GRL);
		
		// overly populated actor Bad Smell
		if (store.getBoolean(GRLBSDetectorConstants.PRE_overlyPopulatedModelBS)) {   
			badSmellsDetector.overlyPopulatedModelDetector(ERD_actors, Integer.parseInt(store.getString(GRLBSDetectorConstants.PRE_overlyPopulatedModelThreshold)));
		}		

		//overly populated actor Bad Smell
		if (store.getBoolean(GRLBSDetectorConstants.PRE_overlyPopulatedActorBS)) { 
			badSmellsDetector.overlyPopulatedActorDetector(ERD_actors, ERD_listOfIntentElmntsByActor, Integer.parseInt(store.getString(GRLBSDetectorConstants.PRE_overlyPopulatedActorThreshold)));	
		}
		
		//overly ambitious actor Bad Smell
		if (store.getBoolean(GRLBSDetectorConstants.PRE_overlyAmbitiousActorBS)) { 
			badSmellsDetector.overlyAmbitiousActorDetector(ERD_actors, ERD_allLinkRefs, ERD_listOfIntentElmntRefsByActor, ERD_allElements, ERD_allIntentionalElementRefs, ERD_listOfIntenElmntRefsByElement,Integer.parseInt(store.getString(GRLBSDetectorConstants.PRE_overlyAmbitiousActorThreshold)));	
			
		}
		
		//deep hierarchy bad smell
		if (store.getBoolean(GRLBSDetectorConstants.PRE_deepHierarchyBS)) { 
			badSmellsDetector.deepHierarchyDetector(ERD_actors, ERD_listOfIntentElmntRefsByActor, ERD_allLinkRefs, ERD_allIntentionalElementRefs,  ERD_allContRefsByActor, Integer.parseInt(store.getString(GRLBSDetectorConstants.PRE_deepHierarchyThreshold)));
		}
		
		//too MuchOperationalization bad smell
		if (store.getBoolean(GRLBSDetectorConstants.PRE_overlyOperationalizedActorBS)) {   
			badSmellsDetector.overlyOperationalizedActorDetector(ERD_actors, ERD_allLinkRefs, ERD_listOfIntentElmntRefsByActor, ERD_allElements, ERD_allIntentionalElementRefs, ERD_listOfIntenElmntRefsByElement, Float.parseFloat(store.getString(GRLBSDetectorConstants.PRE_overlyOperationalizedActorThreshold)));
		} 
		
		//Highly Coupled Actor
		if (store.getBoolean(GRLBSDetectorConstants.PRE_highlyCoupledActorBS)) { 
			badSmellsDetector.highlyCoupledActorDetector(ERD_actors, ERD_listOfIntentElmntRefsByActor, ERD_allLinkRefs, ERD_allLinks, Integer.parseInt(store.getString(GRLBSDetectorConstants.PRE_highlyCoupledActorThreshold)));
		}
		
		// God Goal OR SoftGoal Bad Smell
		if (store.getBoolean(GRLBSDetectorConstants.PRE_highlyProliferatedGoalORSoftGoalBS)) { 
			badSmellsDetector.highlyProliferatedGoalORSoftGoalDetector(ERD_allElements, ERD_allLinks, Integer.parseInt(store.getString(GRLBSDetectorConstants.PRE_highlyProliferatedGoalORSoftGoalThreshold)));
		}
		
		//god task Bad smell
		if (store.getBoolean(GRLBSDetectorConstants.PRE_highlyProliferatedTaskBS)) { 
			badSmellsDetector.highlyProliferatedTaskDetector( ERD_allElements, ERD_allLinks, Integer.parseInt(store.getString(GRLBSDetectorConstants.PRE_highlyProliferatedTaskThreshold)));
		}
		

		//Listing possibilities bad Smell
		if (store.getBoolean(GRLBSDetectorConstants.PRE_possibilitiesExhausterBS)) { 
			badSmellsDetector.possibilitiesExhausterDetector(ERD_allElements, ERD_allLinks, Integer.parseInt(store.getString(GRLBSDetectorConstants.PRE_possibilitiesExhausterThreshold)));
		}
		
		//	highly coupled Element bad smell
		if (store.getBoolean(GRLBSDetectorConstants.PRE_highlyCoupledElementBS)) { 
			badSmellsDetector.highlyCoupledElementDetector(ERD_allElements, Integer.parseInt(store.getString(GRLBSDetectorConstants.PRE_highlyCoupledElementThreshold)));
		}
		
		try {
		 
			activePage.showView("org.eclipse.ui.views.ProblemView");
		} catch (PartInitException e) {
		
			e.printStackTrace();
			}
		
		jucmEditor.setFocus();      
	
		
		      
	
	}
	
	    
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		// TODO Auto-generated method stub

	}

}
