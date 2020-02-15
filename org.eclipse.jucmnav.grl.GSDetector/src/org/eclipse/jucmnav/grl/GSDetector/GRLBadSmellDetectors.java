package org.eclipse.jucmnav.grl.GSDetector;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import seg.jUCMNav.editors.UCMNavMultiPageEditor;

/**
 * Copyright (C) 2020 Mawal Mohammed - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the Eclipse Public License - v 2.0 ,
 */

public class GRLBadSmellDetectors {


UCMNavMultiPageEditor jucmEditor;
private IFile activeFile;
Document jucmDoc;

GRLBadSmellsDetectionQueries detectionQuery ; 


 public GRLBadSmellDetectors(UCMNavMultiPageEditor jmEditor, IFile jmFile, Document jmDoc) {
	 
	jucmEditor=jmEditor;
	activeFile=jmFile;
	jucmDoc=jmDoc;
	
	detectionQuery = new GRLBadSmellsDetectionQueries();

	}
		

//overly populated model BS
public void overlyPopulatedModelDetector(int overlyPopulatedModelThreshold) {
	
	try {
		
			NodeList actorList = jucmDoc.getElementsByTagName("actors");
			NodeList graphList = jucmDoc.getElementsByTagName("specDiagrams");
			Node nNode = graphList.item(0);
			Element eElement = (Element) nNode;
			  if(detectionQuery.overlyPopulatedModelBS(actorList, overlyPopulatedModelThreshold )) {
				  writeMarkers(activeFile, "Instance of the overly populated model bad smell","overlyPopulatedModelBS",jucmEditor.getTitle(),"graph","",eElement.getAttribute("id"));
		}		
	} catch (Exception e) {
		e.printStackTrace();
	}
}			


//overly populated actor BS
public void overlyPopulatedActorDetector(int overlyPopulatedActorThreshold) {
	
	try {
			List<Integer> listOfNodesInActor = new ArrayList<Integer> ();
			
			NodeList actorList = jucmDoc.getElementsByTagName("actors");
			NodeList contRefsList = jucmDoc.getElementsByTagName("contRefs");
			
			for (int x=0; x < actorList.getLength();x++) {
				Node ANode = actorList.item(x);
				Element AElement = (Element) ANode;
				
				String nodesInActor ;
				for (int temp = 0; temp < contRefsList.getLength(); temp++) {
					Node nNode = contRefsList.item(temp);
					Element eElement = (Element) nNode;
					if(AElement.getAttribute("id").equalsIgnoreCase(eElement.getAttribute("contDef")))
					{
						nodesInActor=eElement.getAttribute("nodes");
						if(nodesInActor!=""){
							String[] integerStrings = nodesInActor.split(" "); 
							
							for (int i = 0; i < integerStrings.length; i++){
								listOfNodesInActor.add(Integer.parseInt(integerStrings[i])); 
							}
						}
					}
				}
				//System.out.println("no of elements in actor: " + AElement.getAttribute("name")+ " equals to : "+ listOfNodesInActor.size() );
						if(detectionQuery.overlyPopulatedActorBS (listOfNodesInActor, overlyPopulatedActorThreshold) ) {
							writeMarkers(activeFile, "Instance of the overly populated actor bad smell","overlyPopulatedActorBS",AElement.getAttribute("name"),"actor","",AElement.getAttribute("id"));
						}
						listOfNodesInActor.clear();
				}
			
		} catch (Exception e) {
		e.printStackTrace();
	}
}	

//overly ambitious actor BS
public void overlyAmbitiousActorDetector(int overlyAmbitiousActorThreshold) {

	try {
		
	List<Integer> listOfNodesInActor = new ArrayList<Integer> ();
	
	 NodeList actorList = jucmDoc.getElementsByTagName("actors");
		String actorId;
		String actorName;
		String contRefs;
		
		for (int i=0; i<actorList.getLength();i++) {
			
			listOfNodesInActor.clear();
			
			Node nNode = actorList.item(i);
			Element eElement = (Element) nNode;
			actorId=eElement.getAttribute("id");
			actorName=eElement.getAttribute("name");
			contRefs=eElement.getAttribute("contRefs");
			int[] allContRefsOfActor= new int [0];
			if(contRefs!=""){
				String[] integerStringsOfContRefs = contRefs.split(" "); 
				allContRefsOfActor = new int[integerStringsOfContRefs.length]; 
				for (int j = 0; j < allContRefsOfActor.length; j++){
					allContRefsOfActor[j] = Integer.parseInt(integerStringsOfContRefs[j]); 
				}
			

		for (int cr=0;cr<allContRefsOfActor.length;cr++) {
			
		NodeList contRefsList = jucmDoc.getElementsByTagName("contRefs");
		if (contRefsList.getLength()>0) {
			String nodesInActor ;
			String contId;
			int[] allElementsRefsInAnActorRef=null;
			for (int temp = 0; temp < contRefsList.getLength(); temp++) {
				Node nnNode = contRefsList.item(temp);
				Element eeElement = (Element) nnNode;
				contId=eeElement.getAttribute("id");
				nodesInActor=eeElement.getAttribute("nodes");
				if(allContRefsOfActor [cr] == Integer.parseInt(contId) && nodesInActor!=""){
					String[] integerStringsOfNodesInContRef = nodesInActor.split(" "); 
					allElementsRefsInAnActorRef = new int[integerStringsOfNodesInContRef.length]; 
					for (int ii = 0; ii < allElementsRefsInAnActorRef.length; ii++){
						allElementsRefsInAnActorRef[ii] = Integer.parseInt(integerStringsOfNodesInContRef[ii]); 
					}
				}
			}
		
		for (int n=0;n<allElementsRefsInAnActorRef.length; n++) {
		listOfNodesInActor.add(allElementsRefsInAnActorRef[n]);
		}
		}
		}
		
		List<Integer> listOfElementRefs = new ArrayList<Integer> ();
		
		List<Integer> listOfGoalsAndSoftgoalsRefs = new ArrayList<Integer> ();
	
		for (int n=0;n<listOfNodesInActor.size(); n++) {
			listOfGoalsAndSoftgoalsRefs.add(listOfNodesInActor.get(n)); 
			}
	

		
		 NodeList nodesList = jucmDoc.getElementsByTagName("nodes");
			int [][] nodes= new int [nodesList.getLength()][2];
			for (int ni=0; ni<nodesList.getLength();ni++) {
				Node noNode = nodesList.item(ni);
				Element noElement = (Element) noNode;
				if (noElement.getAttribute("xsi:type").equalsIgnoreCase("grl:IntentionalElementRef")) {
				nodes [ni][0] = Integer.parseInt(noElement.getAttribute("id"));
				nodes [ni][1] = Integer.parseInt(noElement.getAttribute("def"));
				}
			}
		
		
		 NodeList ElementsList = jucmDoc.getElementsByTagName("intElements");
		 int noOfElements=ElementsList.getLength();
		 String [][] allElements=new String [noOfElements][4];
		 for (int temp = 0; temp < noOfElements; temp++) {
			 Node intNode = ElementsList.item(temp);
			 Element intElement = (Element) intNode;
			 allElements[temp][0]= intElement.getAttribute("id");
			 allElements[temp][1]= intElement.getAttribute("type");
			 allElements[temp][2]=intElement.getAttribute("refs");
			 allElements[temp][3]=intElement.getAttribute("linksDest");
			 
			 listOfElementRefs.clear();
			 
			 if( allElements[temp][2]!=""){
					String[] integerStringsOfElementRefs =  allElements[temp][2].split(" "); 
						for (int j = 0; j < integerStringsOfElementRefs.length; j++){
							listOfElementRefs.add(Integer.parseInt(integerStringsOfElementRefs[j])); 
					}
				}
		
							
			 
			 if (!(allElements[temp][1].equalsIgnoreCase("Goal") || allElements[temp][1].equalsIgnoreCase("")))
					 for (int n=0;n<listOfElementRefs.size(); n++) {
						 int index=listOfGoalsAndSoftgoalsRefs.indexOf(listOfElementRefs.get(n));
						 if(index !=-1)
							 listOfGoalsAndSoftgoalsRefs.remove(index);
						}
		 }
		 
		
		 NodeList connsList = jucmDoc.getElementsByTagName("connections");
			int noOfConns=connsList.getLength();
			int noOfLinkRefs=0;
			for (int temp = 0; temp < noOfConns; temp++) {
				Node conNode = connsList.item(temp);
				Element conElement = (Element) conNode;
				if (conElement.getAttribute("xsi:type").equalsIgnoreCase("grl:LinkRef"))
					noOfLinkRefs++;
			}
		 
		 NodeList connectionsList = jucmDoc.getElementsByTagName("connections");
			int [][] allConnections=new int [noOfLinkRefs][3];
			int counterLinkRefs=0;
			for (int temp = 0; temp < connectionsList.getLength(); temp++) {
				Node conNode = connectionsList.item(temp);
				Element conElement = (Element) conNode;
				if (conElement.getAttribute("xsi:type").equalsIgnoreCase("grl:LinkRef")){
					allConnections[counterLinkRefs][0]= Integer.parseInt(conElement.getAttribute("source"));
					allConnections[counterLinkRefs][1]=Integer.parseInt(conElement.getAttribute("target"));
					allConnections[counterLinkRefs][2]=Integer.parseInt(conElement.getAttribute("link"));
					counterLinkRefs++;
				}
			}
					
					if(detectionQuery.overlyAmbitiousActorBS(listOfGoalsAndSoftgoalsRefs,allConnections,listOfNodesInActor, allElements, nodes, overlyAmbitiousActorThreshold)) {
						writeMarkers(activeFile, "Instance of the overly ambitious actor bad smell","overlyAmbitiousActorBS",actorName,"actor","",actorId);
					}
					
				}
			}
	}catch (Exception e) {
		e.printStackTrace();
	}
}	
	

//deep hierarchy bad smell
public void deepHierarchyDetector(int deepHierarchyThreshold) {
	
	try {
			
			NodeList intElemsList = jucmDoc.getElementsByTagName("nodes");
			int noOfnodes=intElemsList.getLength();
			int noOfIntElemRefs=0;
			for (int temp = 0; temp < noOfnodes; temp++) {
				Node nNode = intElemsList.item(temp);
					Element eElement = (Element) nNode;
					if (eElement.getAttribute("xsi:type").equalsIgnoreCase("grl:IntentionalElementRef"))
						noOfIntElemRefs++;
			}
			
			NodeList IntentionalElementRefsList = jucmDoc.getElementsByTagName("nodes");
			int noOfIntentionalElementRefs=noOfIntElemRefs;
			String [][] allIntentionalElementRefs=new String [noOfIntentionalElementRefs][4];
			int counterElementRefs=0;
			for (int temp = 0; temp < IntentionalElementRefsList.getLength(); temp++) {
				Node nNode = IntentionalElementRefsList.item(temp);
				Element eElement = (Element) nNode;
				if (eElement.getAttribute("xsi:type").equalsIgnoreCase("grl:IntentionalElementRef")){
					allIntentionalElementRefs[counterElementRefs][0]= eElement.getAttribute("id");
					allIntentionalElementRefs[counterElementRefs][1]=eElement.getAttribute("contRef");
					counterElementRefs++;

				}
			}
			
			NodeList connsList = jucmDoc.getElementsByTagName("connections");
			int noOfConns=connsList.getLength();
			int noOfLinkRefs=0;
			for (int temp = 0; temp < noOfConns; temp++) {
				Node nNode = connsList.item(temp);
				Element eElement = (Element) nNode;
				if (eElement.getAttribute("xsi:type").equalsIgnoreCase("grl:LinkRef"))
					noOfLinkRefs++;
			}
		
			NodeList connectionsList = jucmDoc.getElementsByTagName("connections");
			int [][] allConnections=new int [noOfLinkRefs][3];
			int counterLinkRefs=0;
			for (int temp = 0; temp < connectionsList.getLength(); temp++) {
				Node nNode = connectionsList.item(temp);
				Element eElement = (Element) nNode;
				if (eElement.getAttribute("xsi:type").equalsIgnoreCase("grl:LinkRef")){
					allConnections[counterLinkRefs][0]= Integer.parseInt(eElement.getAttribute("source"));
					allConnections[counterLinkRefs][1]=Integer.parseInt(eElement.getAttribute("target"));
					allConnections[counterLinkRefs][2]=Integer.parseInt(eElement.getAttribute("link"));
					counterLinkRefs++;
				}
			}
			
			NodeList ElementsList = jucmDoc.getElementsByTagName("intElements");
			int noOfElements=ElementsList.getLength();
			String [][] allElements=new String [noOfElements][3];
			for (int temp = 0; temp < noOfElements; temp++) {
				Node nNode = ElementsList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					allElements[temp][0]= eElement.getAttribute("type");
					allElements[temp][1]=eElement.getAttribute("refs");
				}
			}
			
			List<Integer> listOfNodesInActor = new ArrayList<Integer> ();
			NodeList actorList = jucmDoc.getElementsByTagName("actors");
			NodeList contRefsList = jucmDoc.getElementsByTagName("contRefs");
			
			for (int x=0; x < actorList.getLength();x++) {
				Node ANode = actorList.item(x);
				Element AElement = (Element) ANode;
				int thisActor = Integer.parseInt(AElement.getAttribute("id"));
				List<Integer> listOfContRefsInActor = new ArrayList<Integer> ();
				String contRefsOfActor=AElement.getAttribute("contRefs");
				if(contRefsOfActor!=""){
					String[] integerStrings = contRefsOfActor.split(" "); 
					
					for (int i = 0; i < integerStrings.length; i++){
						listOfContRefsInActor.add(Integer.parseInt(integerStrings[i])); 
					}
				}
				
				String nodesInActor ;
				for (int temp = 0; temp < contRefsList.getLength(); temp++) {
					Node nNode = contRefsList.item(temp);
					Element eElement = (Element) nNode;
					if(AElement.getAttribute("id").equalsIgnoreCase(eElement.getAttribute("contDef")))
					{
						nodesInActor=eElement.getAttribute("nodes");
						if(nodesInActor!=""){
							String[] integerStrings = nodesInActor.split(" "); 
							
							for (int i = 0; i < integerStrings.length; i++){
								listOfNodesInActor.add(Integer.parseInt(integerStrings[i])); 
							}
						}
					}
				}
					
				if(detectionQuery.deepHierarchyBS (listOfNodesInActor, allElements, allConnections, allIntentionalElementRefs,listOfContRefsInActor, thisActor, deepHierarchyThreshold)) {
					writeMarkers(activeFile, "Instance of the deep hierarchical  refinement bad smell","deepHierarchyBS",AElement.getAttribute("name"),"actor","",AElement.getAttribute("id"));
				}
				listOfNodesInActor.clear();
			}
	} catch (Exception e) {
		e.printStackTrace();
	}
}

//overly operationalized bad smell
public void overlyOperationalizedActorDetector(float overlyOperationalizedActorThreshold) {
	
	try {
		
		List<Integer> listOfNodesInActor = new ArrayList<Integer> ();
		
		 NodeList actorList = jucmDoc.getElementsByTagName("actors");
			String actorId;
			String actorName;
			String contRefs;
			
			for (int i=0; i<actorList.getLength();i++) {
				
				listOfNodesInActor.clear();
				
				Node nNode = actorList.item(i);
				Element eElement = (Element) nNode;
				actorId=eElement.getAttribute("id");
				actorName=eElement.getAttribute("name");
				contRefs=eElement.getAttribute("contRefs");
				int[] allContRefsOfActor= new int [0];
				if(contRefs!=""){
					String[] integerStringsOfContRefs = contRefs.split(" "); 
					allContRefsOfActor = new int[integerStringsOfContRefs.length]; 
					for (int j = 0; j < allContRefsOfActor.length; j++){
						allContRefsOfActor[j] = Integer.parseInt(integerStringsOfContRefs[j]); 
					}
				

			for (int cr=0;cr<allContRefsOfActor.length;cr++) {
				
			NodeList contRefsList = jucmDoc.getElementsByTagName("contRefs");
			if (contRefsList.getLength()>0) {
				String nodesInActor ;
				String contId;
				int[] allElementsRefsInAnActorRef=null;
				for (int temp = 0; temp < contRefsList.getLength(); temp++) {
					Node nnNode = contRefsList.item(temp);
					Element eeElement = (Element) nnNode;
					contId=eeElement.getAttribute("id");
					nodesInActor=eeElement.getAttribute("nodes");
					if(allContRefsOfActor [cr] == Integer.parseInt(contId) && nodesInActor!=""){
						String[] integerStringsOfNodesInContRef = nodesInActor.split(" "); 
						allElementsRefsInAnActorRef = new int[integerStringsOfNodesInContRef.length]; 
						for (int ii = 0; ii < allElementsRefsInAnActorRef.length; ii++){
							allElementsRefsInAnActorRef[ii] = Integer.parseInt(integerStringsOfNodesInContRef[ii]); 
						}
					}
				}
			
			for (int n=0;n<allElementsRefsInAnActorRef.length; n++) {
			listOfNodesInActor.add(allElementsRefsInAnActorRef[n]);
			}
			}
			}
			
			List<Integer> listOfElementRefs = new ArrayList<Integer> ();
			
			List<Integer> listOfGoalsAndSoftgoalsRefs = new ArrayList<Integer> ();
		
			for (int n=0;n<listOfNodesInActor.size(); n++) {
				listOfGoalsAndSoftgoalsRefs.add(listOfNodesInActor.get(n)); 
				}
		

			
			 NodeList nodesList = jucmDoc.getElementsByTagName("nodes");
				int [][] nodes= new int [nodesList.getLength()][2];
				for (int ni=0; ni<nodesList.getLength();ni++) {
					Node noNode = nodesList.item(ni);
					Element noElement = (Element) noNode;
					if (noElement.getAttribute("xsi:type").equalsIgnoreCase("grl:IntentionalElementRef")) {
					nodes [ni][0] = Integer.parseInt(noElement.getAttribute("id"));
					nodes [ni][1] = Integer.parseInt(noElement.getAttribute("def"));
					}
				}
			
			
			 NodeList ElementsList = jucmDoc.getElementsByTagName("intElements");
			 int noOfElements=ElementsList.getLength();
			 String [][] allElements=new String [noOfElements][4];
			 for (int temp = 0; temp < noOfElements; temp++) {
				 Node intNode = ElementsList.item(temp);
				 Element intElement = (Element) intNode;
				 allElements[temp][0]= intElement.getAttribute("id");
				 allElements[temp][1]= intElement.getAttribute("type");
				 allElements[temp][2]=intElement.getAttribute("refs");
				 allElements[temp][3]=intElement.getAttribute("linksDest");
				 
				 listOfElementRefs.clear();
				 
				 if( allElements[temp][2]!=""){
						String[] integerStringsOfElementRefs =  allElements[temp][2].split(" "); 
							for (int j = 0; j < integerStringsOfElementRefs.length; j++){
								listOfElementRefs.add(Integer.parseInt(integerStringsOfElementRefs[j])); 
						}
					}
			
								
				 
				 if (!(allElements[temp][1].equalsIgnoreCase("Goal") || allElements[temp][1].equalsIgnoreCase("")))
						 for (int n=0;n<listOfElementRefs.size(); n++) {
							 int index=listOfGoalsAndSoftgoalsRefs.indexOf(listOfElementRefs.get(n));
							 if(index !=-1)
								 listOfGoalsAndSoftgoalsRefs.remove(index);
							}
			 }
			 
			
			 NodeList connsList = jucmDoc.getElementsByTagName("connections");
				int noOfConns=connsList.getLength();
				int noOfLinkRefs=0;
				for (int temp = 0; temp < noOfConns; temp++) {
					Node conNode = connsList.item(temp);
					Element conElement = (Element) conNode;
					if (conElement.getAttribute("xsi:type").equalsIgnoreCase("grl:LinkRef"))
						noOfLinkRefs++;
				}
			 
			 NodeList connectionsList = jucmDoc.getElementsByTagName("connections");
				int [][] allConnections=new int [noOfLinkRefs][3];
				int counterLinkRefs=0;
				for (int temp = 0; temp < connectionsList.getLength(); temp++) {
					Node conNode = connectionsList.item(temp);
					Element conElement = (Element) conNode;
					if (conElement.getAttribute("xsi:type").equalsIgnoreCase("grl:LinkRef")){
						allConnections[counterLinkRefs][0]= Integer.parseInt(conElement.getAttribute("source"));
						allConnections[counterLinkRefs][1]=Integer.parseInt(conElement.getAttribute("target"));
						allConnections[counterLinkRefs][2]=Integer.parseInt(conElement.getAttribute("link"));
						counterLinkRefs++;
					}
				}
	
				
					
				if(detectionQuery.overlyOperationalizedActorBS(listOfGoalsAndSoftgoalsRefs,allConnections,listOfNodesInActor, allElements, nodes,overlyOperationalizedActorThreshold )) {
					writeMarkers(activeFile, "Instance of the overly operationalized actor bad smell","overlyOperationalizedActorBS",actorName,"actor","",actorId);
				}
			
			}
			}
	} catch (Exception e) {
		e.printStackTrace();
	}
}

//Highly Coupled Actor
public void highlyCoupledActorDetector(int highlyCoupledActorThreshold) {
	
	try {
		
			NodeList linkList = jucmDoc.getElementsByTagName("links");
			int noOfLinks=linkList.getLength();
			String [][] allLinks=new String [noOfLinks][2];
			for (int temp = 0; temp < noOfLinks; temp++) {
				Node nNode = linkList.item(temp);
				Element eElement = (Element) nNode;
				allLinks[temp][0]= eElement.getAttribute("xsi:type");
				allLinks[temp][1]=eElement.getAttribute("id");
			}
			
			int noOfDependency=0;
			for (int i=0;i<allLinks.length;i++){
				if(allLinks[i][0].equalsIgnoreCase("grl:Dependency"))
					noOfDependency++;
			}
			
			int [] allDependencies = new int [noOfDependency];
			int counter=0;
			for (int i=0;i<allLinks.length;i++){
				if(allLinks[i][0].equalsIgnoreCase("grl:Dependency")){
					allDependencies[counter]=Integer.parseInt(allLinks[i][1]);
					counter++;
				}
			}
			
			NodeList connsList = jucmDoc.getElementsByTagName("connections");
			int noOfConns=connsList.getLength();
			int noOfLinkRefs=0;
			for (int temp = 0; temp < noOfConns; temp++) {
				Node nNode = connsList.item(temp);
					Element eElement = (Element) nNode;
					if (eElement.getAttribute("xsi:type").equalsIgnoreCase("grl:LinkRef"))
						noOfLinkRefs++;
			}
			
			NodeList connectionsList = jucmDoc.getElementsByTagName("connections");
			int noOfConnections=noOfLinkRefs;
			int [][] allConnections=new int [noOfConnections][3];
			for (int temp = 0; temp < noOfConnections; temp++) {
				Node nNode = connectionsList.item(temp);
				Element eElement = (Element) nNode;
				if (eElement.getAttribute("xsi:type").equalsIgnoreCase("grl:LinkRef")){
					allConnections[temp][0]= Integer.parseInt(eElement.getAttribute("source"));
					allConnections[temp][1]=Integer.parseInt(eElement.getAttribute("target"));
					allConnections[temp][2]=Integer.parseInt(eElement.getAttribute("link"));
				}
			}
			
			List<Integer> listOfNodesInActor = new ArrayList<Integer> ();
			NodeList actorList = jucmDoc.getElementsByTagName("actors");
			NodeList contRefsList = jucmDoc.getElementsByTagName("contRefs");
			
			for (int x=0; x < actorList.getLength();x++) {
				Node ANode = actorList.item(x);
				Element AElement = (Element) ANode;
				
				String nodesInActor ;
				for (int temp = 0; temp < contRefsList.getLength(); temp++) {
					Node nNode = contRefsList.item(temp);
					Element eElement = (Element) nNode;
					if(AElement.getAttribute("id").equalsIgnoreCase(eElement.getAttribute("contDef")))
					{
						nodesInActor=eElement.getAttribute("nodes");
						if(nodesInActor!=""){
							String[] integerStrings = nodesInActor.split(" "); 
							
							for (int i = 0; i < integerStrings.length; i++){
								listOfNodesInActor.add(Integer.parseInt(integerStrings[i])); 
							}
						}
					}
				}
				if(detectionQuery.highlyCoupledActorBS (listOfNodesInActor,allConnections, allDependencies,highlyCoupledActorThreshold)) {
					writeMarkers(activeFile, "Instance of the highly coupled actor bad smell","highlyCoupledActorBS",AElement.getAttribute("name"),"actor","",AElement.getAttribute("id"));
				}
				listOfNodesInActor.clear();
			}
	} catch (Exception e) {
		e.printStackTrace();
	}
}
	
// highly Proliferated Goal OR SoftGoal Bad Smell

public void highlyProliferatedGoalORSoftGoalDetector(int highlyProliferatedGoalORSoftGoalThreshold) {
	
	try {
	
			NodeList ElementsList = jucmDoc.getElementsByTagName("intElements");
			int noOfElements=ElementsList.getLength();
			String [][] allElements=new String [noOfElements][4];
			for (int temp = 0; temp < noOfElements; temp++) {
				Node nNode = ElementsList.item(temp);
				Element eElement = (Element) nNode;
				allElements[temp][0]= eElement.getAttribute("type");
				allElements[temp][1]= eElement.getAttribute("id");
				allElements[temp][2]= eElement.getAttribute("decompositionType");
				allElements[temp][3]= eElement.getAttribute("name");
			}
			
			NodeList linkList = jucmDoc.getElementsByTagName("links");
			int noOfLinks=linkList.getLength();
			String [][] allLinks=new String [noOfLinks][3];
			for (int temp = 0; temp < noOfLinks; temp++) {
				Node nNode = linkList.item(temp);
				Element eElement = (Element) nNode;
				allLinks[temp][0]= eElement.getAttribute("xsi:type");
				allLinks[temp][1]=eElement.getAttribute("id");
				allLinks[temp][2]=eElement.getAttribute("dest");
			}
			
			for (int i=0;i<allElements.length; i++){
				if( detectionQuery.highlyProliferatedGoalORSoftGoalBS(allElements[i],allLinks,highlyProliferatedGoalORSoftGoalThreshold)) {
					writeMarkers(activeFile, "Instance of the highly proliferated goal/softgoal bad smell","highlyProliferatedGoalORSoftGoalBS",allElements[i][3],"element","",allElements[i][1]);
				}
			}
	} catch (Exception e) {
		e.printStackTrace();
	}
}
		
// highly Proliferated Task Bad smell
public void highlyProliferatedTaskDetector(int highlyProliferatedTaskThreshold) {
	
	try {
	
			NodeList ElementsList = jucmDoc.getElementsByTagName("intElements");
			int noOfElements=ElementsList.getLength();
			String [][] allElements=new String [noOfElements][4];
			for (int temp = 0; temp < noOfElements; temp++) {
				Node nNode = ElementsList.item(temp);
				Element eElement = (Element) nNode;
				allElements[temp][0]= eElement.getAttribute("type");
				allElements[temp][1]= eElement.getAttribute("id");
				allElements[temp][2]= eElement.getAttribute("decompositionType");
				allElements[temp][3]= eElement.getAttribute("name");
			}
		
			NodeList linkList = jucmDoc.getElementsByTagName("links");
			int noOfLinks=linkList.getLength();
			String [][] allLinks=new String [noOfLinks][3];
			for (int temp = 0; temp < noOfLinks; temp++) {
				Node nNode = linkList.item(temp);
				Element eElement = (Element) nNode;
				allLinks[temp][0]= eElement.getAttribute("xsi:type");
				allLinks[temp][1]=eElement.getAttribute("id");
				allLinks[temp][2]=eElement.getAttribute("dest");
			}
					
			for (int i=0;i<allElements.length; i++){
				if( detectionQuery.highlyProliferatedTaskBS(allElements[i],allLinks,highlyProliferatedTaskThreshold)) {
					writeMarkers(activeFile, "Instance of the highly proliferated task bad smell","highlyProliferatedTaskBS",allElements[i][3],"element","",allElements[i][1]);
				}
			}
	} catch (Exception e) {
		e.printStackTrace();
	}
}

//possibilities Exhauster bad Smell
public void possibilitiesExhausterDetector(int possibilitiesExhausterThreshold) {
	
	try {
			
			NodeList ElementsList = jucmDoc.getElementsByTagName("intElements");
			int noOfElements=ElementsList.getLength();
			String [][] allElements=new String [noOfElements][4];
			for (int temp = 0; temp < noOfElements; temp++) {
				Node nNode = ElementsList.item(temp);
				Element eElement = (Element) nNode;
				allElements[temp][0]= eElement.getAttribute("type");
				allElements[temp][1]= eElement.getAttribute("id");
				allElements[temp][2]= eElement.getAttribute("decompositionType");
				allElements[temp][3]= eElement.getAttribute("name");
			}
		
			NodeList linkList = jucmDoc.getElementsByTagName("links");
			int noOfLinks=linkList.getLength();
			String [][] allLinks=new String [noOfLinks][3];
			for (int temp = 0; temp < noOfLinks; temp++) {
				Node nNode = linkList.item(temp);
				Element eElement = (Element) nNode;
				allLinks[temp][0]= eElement.getAttribute("xsi:type");
				allLinks[temp][1]=eElement.getAttribute("id");
				allLinks[temp][2]=eElement.getAttribute("dest");
			}
			
			for (int i=0;i<allElements.length; i++){
				if( detectionQuery.possibilitiesExhausterBS(allElements[i],allLinks,possibilitiesExhausterThreshold)) {
					writeMarkers(activeFile, "Instance of the possibilities Exhauster bad smell","possibilitiesExhauster",allElements[i][3],"element","",allElements[i][1]);
				}
			}
	} catch (Exception e) {
		e.printStackTrace();
	}
}


//	highly coupled Element bad smell
public void highlyCoupledElementDetector(int highlyCoupledElementThreshold) {
	
	try {
	 
		 NodeList ElementsList = jucmDoc.getElementsByTagName("intElements");
		 int noOfElements=ElementsList.getLength();
		 String [][] allElements=new String [noOfElements][5];
		 for (int i = 0; i < noOfElements; i++) {
			 Node nNode = ElementsList.item(i);
			 Element eElement = (Element) nNode;
			 allElements[i][0]=eElement.getAttribute("refs");
			 allElements[i][1]=eElement.getAttribute("linksSrc");
			 allElements[i][2]=eElement.getAttribute("linksDest");
			 allElements[i][3]=eElement.getAttribute("id");
			 allElements[i][4]=eElement.getAttribute("name");
				 
			 if( detectionQuery.highlyCoupledElementBS(allElements[i],highlyCoupledElementThreshold) && !eElement.getAttribute("xsi:type").equalsIgnoreCase("fm:Feature")) {
				 writeMarkers(activeFile, "Instance of the highly coupled element bad smell","highlyCoupledElementBS",allElements[i][4],"element","",allElements[i][3]);
			 }
		 }
	} catch (Exception e) {
		e.printStackTrace();
	}
}

 
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
