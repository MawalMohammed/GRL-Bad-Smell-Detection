package org.eclipse.jucmnav.grl.GSDetector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Copyright (C) 2022 Mawal Mohammed - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the Eclipse Public License - v 2.0 ,
 */

public class GRLBadSmellsDetectionQueries {


//overlyPopulatedModelBS

/*
 * This function calculates the number of actors in a model,
 * then compares this number with a pre-specified threshold
 */
boolean overlyPopulatedModelBS(String ERD_actors[][], int overlyPopulatedModelThreshold){
	
	if(ERD_actors.length>overlyPopulatedModelThreshold)
		return true;
	else return false;
}

//overlyPopulatedActorBS

/*
 * This function calculates the number of elements in an actor,
 * then, compares this number with a pre-specified threshold
 */
boolean overlyPopulatedActorBS(List <Integer> listOfIntentElmntsInActor, int overlyPopulatedActorThreshold){
	
	if (listOfIntentElmntsInActor.size()> overlyPopulatedActorThreshold)
		return true;
	else return false;
	
}

//overlyAmbitiousActorBS

/*
 * This function calculates the number of low level goals and softgoal in an actor, 
 * then, compares this number with a pre-specified threshold
 */
boolean overlyAmbitiousActorBS(int [][] ERD_allLinkRefs,List<Integer> listOfIntentElmntRefsInActor, String [][] allElements,int [][] ERD_allIntentionalElementRefs, ArrayList<ArrayList<Integer>> ERD_listOfIntenElmntRefsByElement,int overlyAmbitiousActorThreshold){
	
	List<Integer> listOfGoalsAndSoftgoalsRefs = new ArrayList<Integer> ();
	listOfGoalsAndSoftgoalsRefs.addAll(listOfIntentElmntRefsInActor);
	
	//In this loop, the references of goal and softgoal elements in the model are retrieved 
	//softgoal are treated as default. Therefore, an empty string (i.e., "") is used in allElements[1].equalsIgnoreCase("")
	for (int temp=0; temp<allElements.length; temp++) {
		if (!(allElements[temp][1].equalsIgnoreCase("Goal") || allElements[temp][1].equalsIgnoreCase("")))
			 for (int n=0;n<ERD_listOfIntenElmntRefsByElement.get(temp).size(); n++) {
				 int index=listOfGoalsAndSoftgoalsRefs.indexOf(ERD_listOfIntenElmntRefsByElement.get(temp).get(n));
				 if(index !=-1)
					 listOfGoalsAndSoftgoalsRefs.remove(index);
				}
	}
	
	List<Integer> listOfLowLevelGoalsAndSoftgoalsRefs = new ArrayList<Integer> ();
	
	for (int n=0;n<listOfGoalsAndSoftgoalsRefs.size(); n++) {
		listOfLowLevelGoalsAndSoftgoalsRefs.add(listOfGoalsAndSoftgoalsRefs.get(n)); 
		}
	
	List<Integer> listOfSources = new ArrayList<Integer> ();
	
	/*
	 * In this loop, the number of sources of each goal and softgoal in the addressed goal is retrieved,
	 * Based on that, goals and softgoals that do not have goals or softgoals in their sources are kept in the list
	 */
	 for (int n=0;n<listOfGoalsAndSoftgoalsRefs.size(); n++) {
		 int target=listOfGoalsAndSoftgoalsRefs.get(n);
		 listOfSources.clear();
		 for (int m=0;m<ERD_allLinkRefs.length; m++) {
			
			if (target == ERD_allLinkRefs[m][1] && listOfIntentElmntRefsInActor.contains(ERD_allLinkRefs[m][0]))
				listOfSources.add(ERD_allLinkRefs[m][0]);
				}
		 

		 if (!listOfSources.isEmpty()) {
			 if (!isAllsourcesNotGoalsORSoftgoals(listOfSources,ERD_allIntentionalElementRefs,allElements) ) {
				 int indexTarget=listOfLowLevelGoalsAndSoftgoalsRefs.indexOf(target);	
				 listOfLowLevelGoalsAndSoftgoalsRefs.remove(indexTarget);
				 }
		 }
	 }
	 
	 List<Integer> listOfLowLevelGoalsAndSoftgoals= new ArrayList<Integer> ();
	 
	 for (int e=0;e<allElements.length;e++) {
		 if (ERD_listOfIntenElmntRefsByElement.get(e).size()>0)
		 if(isAllRefsOfAnElementsLowLevels(ERD_listOfIntenElmntRefsByElement.get(e),listOfLowLevelGoalsAndSoftgoalsRefs))
			 listOfLowLevelGoalsAndSoftgoals.add(Integer.parseInt(allElements[e][0]));
	 }

	//System.out.println("no of low level goals and softgoals: " + listOfLowLevelGoalsAndSoftgoals.size());

	 
	if (listOfLowLevelGoalsAndSoftgoals.size()>overlyAmbitiousActorThreshold)
	{

		 return true;
	}
	else return false;
}

//deepHierarchyBS

/*
 * This function calculates the number of refinement levels in an actor, 
 * then, compares this number with a pre-specified threshold
 */
boolean deepHierarchyBS( List <Integer> listOfIntentElmntRefsInActor, int [][] ERD_allLinkRefs,int [][] allIntentionalElementRefs, List <Integer> listOfContRefsInActor,int thisActor, int deepHierarchyThreshold ){
	
	//System.out.println(" list of int elements: "+listOfIntentElmntRefsInActor);

	int noOfHighLevelintElementsInAnActor = 0;
	
	//In this loop, the number of the root elements in an actor are calculated
	for (int i = 0; i < listOfIntentElmntRefsInActor.size(); i++){
		if(!isItSource(listOfIntentElmntRefsInActor.get(i),ERD_allLinkRefs))
			noOfHighLevelintElementsInAnActor++;
		else if (isItSource(listOfIntentElmntRefsInActor.get(i),ERD_allLinkRefs) && isTragetsLocationsOutsideThisActor(listOfIntentElmntRefsInActor.get(i),ERD_allLinkRefs,listOfIntentElmntRefsInActor ) )
			noOfHighLevelintElementsInAnActor++;
	}
	
	int [] highLevelIntElementsInActor=new int [noOfHighLevelintElementsInAnActor];
	int y=0;
	
	//	//In this loop, the root elements in an actor are identified
	for (int i = 0; i < listOfIntentElmntRefsInActor.size(); i++){
		if(!isItSource(listOfIntentElmntRefsInActor.get(i),ERD_allLinkRefs)){
			highLevelIntElementsInActor[y]=listOfIntentElmntRefsInActor.get(i);
			y++;}
		else if (isItSource(listOfIntentElmntRefsInActor.get(i),ERD_allLinkRefs) && isTragetsLocationsOutsideThisActor(listOfIntentElmntRefsInActor.get(i),ERD_allLinkRefs,listOfIntentElmntRefsInActor )){
			highLevelIntElementsInActor[y]=listOfIntentElmntRefsInActor.get(i);
			y++;}
	}
	
	boolean [] selectedElements = new boolean [listOfIntentElmntRefsInActor.size()]; 
	Arrays.fill(selectedElements, Boolean.FALSE);
	
	int depth=1;
	int [] targets = new int [highLevelIntElementsInActor.length];
	
	for (int i=0; i<highLevelIntElementsInActor.length;i++){
		targets[i]=highLevelIntElementsInActor[i];
	}
	
	//In this loop, the elements of an actor are traversed level by level to find the depth of the refinement tree 
	while ( targets.length>0){

		int noOfsources=0;
		for (int i=0; i<targets.length;i++){
			for (int j=0; j<ERD_allLinkRefs.length;j++){
				if (ERD_allLinkRefs[j][1]==targets[i] && containsElement(targets,ERD_allLinkRefs[j][1]) && !chosen(ERD_allLinkRefs[j][1], listOfIntentElmntRefsInActor, selectedElements)  && isItInSameActor(ERD_allLinkRefs[j][0], thisActor, listOfContRefsInActor,allIntentionalElementRefs) )  {
					noOfsources++;
				}
			}
		}

		int [] sources=new int [noOfsources];
		int counterSources=0;
		for (int i=0; i<targets.length;i++){
			for (int j=0; j<ERD_allLinkRefs.length;j++){
				if (ERD_allLinkRefs[j][1]==targets[i] && containsElement(targets,ERD_allLinkRefs[j][1]) && !chosen(ERD_allLinkRefs[j][1], listOfIntentElmntRefsInActor, selectedElements) && isItInSameActor(ERD_allLinkRefs[j][0], thisActor, listOfContRefsInActor,allIntentionalElementRefs)){
					sources[counterSources]=ERD_allLinkRefs[j][0];
					counterSources++;
				}
			}
		}
		for (int i = 0; i < listOfIntentElmntRefsInActor.size(); i++){
			for(int j=0; j<targets.length;j++){
			if (listOfIntentElmntRefsInActor.get(i)== targets[j])
				selectedElements[i]=Boolean.TRUE;
			}
		}
		
		targets=new int [sources.length];
		
		for (int i=0; i<sources.length;i++){
			targets[i]=sources[i];
		}
	
		if(targets.length>0){
		depth++;
		}
	}
 
	//System.out.println("depth of this actor is: "+ depth);
	
	if (depth> deepHierarchyThreshold)
	{
		
		return true;
	}
	else return false;
}

//overlyOperationalizedActorBS

/*
 * This function calculates the number of operationalization elements (tasks or resources) in an actor
 * and divides it by the number of low level goals and softgoal in that actor. 
 * then, compares the result of the division with a pre-specified threshold
 */
boolean overlyOperationalizedActorBS(int [][] ERD_allLinkRefs,List<Integer> listOfIntentElmntsInActor, String [][] ERD_allElements,int [][] ERD_allIntentionalElementRefs, ArrayList<ArrayList<Integer>> ERD_listOfIntenElmntRefsByElement, float overlyOperationalizedActorThreshold ){
	
	List<Integer> listOfGoalsAndSoftgoalsRefs = new ArrayList<Integer> ();
	listOfGoalsAndSoftgoalsRefs.addAll(listOfIntentElmntsInActor);
	
	//In this loop, the references of goal and softgoal elements in the model are retrieved 
	//softgoal are treated as default. Therefore, an empty string (i.e., "") is used in allElements[1].equalsIgnoreCase("")
	for (int temp=0; temp<ERD_allElements.length; temp++) {
		if (!(ERD_allElements[temp][1].equalsIgnoreCase("Goal") || ERD_allElements[temp][1].equalsIgnoreCase("")))
			 for (int n=0; n < ERD_listOfIntenElmntRefsByElement.get(temp).size(); n++) {
				 int index=listOfGoalsAndSoftgoalsRefs.indexOf(ERD_listOfIntenElmntRefsByElement.get(temp).get(n));
				 if(index !=-1)
					 listOfGoalsAndSoftgoalsRefs.remove(index);
			}
	}
	
	 
	List<Integer> listOfLowLevelGoalsAndSoftgoalsRefs = new ArrayList<Integer> ();
	
	for (int n=0;n<listOfGoalsAndSoftgoalsRefs.size(); n++) {
		listOfLowLevelGoalsAndSoftgoalsRefs.add(listOfGoalsAndSoftgoalsRefs.get(n)); 
	}
	
	List<Integer> listOfSources = new ArrayList<Integer> ();
	
	/*
	 * In this loop, the number of sources of each goal and softgoal in the addressed goal is retrieved,
	 * Based on that, goals and softgoals that do not have goals or softgoals in their sources are kept in the list
	 */
	 for (int n=0;n<listOfGoalsAndSoftgoalsRefs.size(); n++) {
		 int target=listOfGoalsAndSoftgoalsRefs.get(n);
		 listOfSources.clear();
		 for (int m=0;m<ERD_allLinkRefs.length; m++) {
			
			if (target == ERD_allLinkRefs[m][1] && listOfIntentElmntsInActor.contains(ERD_allLinkRefs[m][0]))
				listOfSources.add(ERD_allLinkRefs[m][0]);
		 }
		 

		 if (!listOfSources.isEmpty()) {
			 if (!isAllsourcesNotGoalsORSoftgoals(listOfSources,ERD_allIntentionalElementRefs,ERD_allElements) ) {
				 int indexTarget=listOfLowLevelGoalsAndSoftgoalsRefs.indexOf(target);	
				 listOfLowLevelGoalsAndSoftgoalsRefs.remove(indexTarget);
			 }
		 }
	 }
	 
	 List<Integer> listOfLowLevelGoalsAndSoftgoals= new ArrayList<Integer> ();
	 
	 //In this loop, the low level goal and softgoal elements are retrieved based on their references 	 
	 for (int e=0;e<ERD_allElements.length;e++) {
		 if (ERD_listOfIntenElmntRefsByElement.get(e).size()>0)
		 if(isAllRefsOfAnElementsLowLevels(ERD_listOfIntenElmntRefsByElement.get(e),listOfLowLevelGoalsAndSoftgoalsRefs))
			 listOfLowLevelGoalsAndSoftgoals.add(Integer.parseInt(ERD_allElements[e][0]));
	 }

	
	int noOfTasksAndResources =0;
	
	//In this loop, the number of tasks and resources is calculated
	for (int i = 0; i < listOfIntentElmntsInActor.size(); i++){
		for (int j=0; j< ERD_allElements.length; j++){
			if (testingIfNodeIsReferedByRefs(listOfIntentElmntsInActor.get(i),ERD_listOfIntenElmntRefsByElement.get(j)) && (ERD_allElements[j][1].equals("Ressource") || ERD_allElements[j][1].equals("Task")) )
				noOfTasksAndResources++;
		}
	}
	//System.out.println("operationalization ratio: "+ (100*((float) (noOfTasksAndResources))/((float) (listOfLowLevelGoalsAndSoftgoals.size()) )));
	
	if (100*((float) (noOfTasksAndResources))/((float) (listOfLowLevelGoalsAndSoftgoals.size()) )> overlyOperationalizedActorThreshold){
		return true;
	}
	else return false;
			
}

//highlyCoupledActorBS

/*
 * This function calculates the number of dependency links associated with an actor,
 * then, compares it with a pre-specified threshold
 */

boolean highlyCoupledActorBS(List <Integer> listOfIntentElmntRefsInActor,int [][] ERD_allLinkRefs,String [][] ERD_allLinks,int highlyCoupledActorThreshold){
	
	int [] allDependencies;
	int noOfDependency=0;
	for (int i=0;i<ERD_allLinks.length;i++){
		if(ERD_allLinks[i][0].equalsIgnoreCase("grl:Dependency"))
			noOfDependency++;
	}
	
	allDependencies = new int [noOfDependency];
	int counter=0;
	for (int i=0;i<ERD_allLinks.length;i++){
		if(ERD_allLinks[i][0].equalsIgnoreCase("grl:Dependency")){
			allDependencies[counter]=Integer.parseInt(ERD_allLinks[i][1]);
			counter++;
		}
	}
	int actorCoupling=0;
	for (int i=0; i<listOfIntentElmntRefsInActor.size(); i++ )
		for (int j=0;j<ERD_allLinkRefs.length;j++)
			if ((listOfIntentElmntRefsInActor.get(i) == ERD_allLinkRefs[j][0] || listOfIntentElmntRefsInActor.get(i) == ERD_allLinkRefs[j][1]) && containsLink(ERD_allLinkRefs[j][2],allDependencies))
				actorCoupling++;
	
	//System.out.println("actorCoupling: "+actorCoupling);
	if (actorCoupling>highlyCoupledActorThreshold)
		return true;
	else return false;
		
}
		
//highlyProliferatedGoalORSoftGoalBS

/*
 * This function calculates the number of children (sources) of a goal or softgoal that is decomposed using And,
 * then, compares this number with a pre-specified threshold.
 * note: softgoal is treated as default. Therefore, an empty string (i.e., "") is used in allElements[1].equalsIgnoreCase("").
 * note: And is default, therefore, an empty string is used (i.e., "") in allElements[4].equalsIgnoreCase("").
 */
boolean highlyProliferatedGoalORSoftGoalBS(String [] ERD_allElements, String [][] ERD_allLinks,int highlyProliferatedGoalORSoftGoalThreshold){
	
	int noOfSources=0;
	if (ERD_allElements[1].equalsIgnoreCase("Goal") || ERD_allElements[1].equalsIgnoreCase("") ){
		for (int j=0;j<ERD_allLinks.length;j++){
			if ((ERD_allLinks[j][0].equalsIgnoreCase("grl:Decomposition") || ERD_allLinks[j][0].equalsIgnoreCase("grl:Contribution")) &&  ERD_allElements[0].equalsIgnoreCase(ERD_allLinks[j][2]) && ERD_allElements[4].equalsIgnoreCase("")  ){
				noOfSources++;
			}
		}
	}
	
	if (noOfSources> highlyProliferatedGoalORSoftGoalThreshold)
		return true;
	else return false;
	
}

//highlyProliferatedTaskBS

/*
 * This function calculates the number of children (sources) of a task that is decomposed using And,
 * then, compares this number with a pre-specified threshold.
 * note: And is default, therefore, an empty string is used (i.e., "") in allElements[4].equalsIgnoreCase("").
 */
boolean highlyProliferatedTaskBS(String [] ERD_allElements, String [][] ERD_allLinks,int highlyProliferatedTaskThreshold){
	
	int noOfSources=0;
	if (ERD_allElements[1].equalsIgnoreCase("Task") ){
		for (int j=0;j<ERD_allLinks.length;j++){
			if ((ERD_allLinks[j][0].equalsIgnoreCase("grl:Decomposition") || ERD_allLinks[j][0].equalsIgnoreCase("grl:Contribution")) && ERD_allElements[0].equalsIgnoreCase(ERD_allLinks[j][2]) && ERD_allElements[4].equalsIgnoreCase("")){
				noOfSources++;
			}
		}
	}
	
	if (noOfSources> highlyProliferatedTaskThreshold)
		return true;
	else return false;
	
	}

//	possibilitiesExhausterBS

/*
 * This function calculates the number of children of an elements that is decomposed using Or or Xor,
 * then, compares this number with a pre-specified threshold.
 */
boolean possibilitiesExhausterBS(String [] ERD_allElements, String [][] ERD_allLinks, int possibilitiesExhausterThreshold){
	
	int noOfDests=0;
	if (ERD_allElements[1].equalsIgnoreCase("Goal") || ERD_allElements[1].equalsIgnoreCase("") || ERD_allElements[1].equalsIgnoreCase("Task") || ERD_allElements[1].equalsIgnoreCase("Ressource")   ){
		for (int j=0;j<ERD_allLinks.length;j++){
			if (ERD_allLinks[j][0].equalsIgnoreCase("grl:Decomposition")  &&  ERD_allElements[0].equalsIgnoreCase(ERD_allLinks[j][2]) && (ERD_allElements[4].equalsIgnoreCase("Or") || ERD_allElements[4].equalsIgnoreCase("Xor") ) ){
				noOfDests++;
			}
		}
	}
	
	if (noOfDests>possibilitiesExhausterThreshold)
		return true;
	else return false;
}
	
//	highlyCoupledElementBS

/*
 * This function calculates the number of source and destination elements of each element,
 * then, compares this number with a pre-specified threshold.
 */
boolean highlyCoupledElementBS(String [] ERD_allElements, int highlyCoupledElementThreshold){
		
	String linksSrc=ERD_allElements[3];
	int x=0;
	int y=0;
	
	if(linksSrc!=""){
		String[] srcStrings = linksSrc.split(" "); 
			x=srcStrings.length;
	}
	
	String linksDest=ERD_allElements[2];
	if(linksDest!=""){
		String[] destStrings = linksDest.split(" "); 
		y=destStrings.length;
	}
		
	int noOfLinksOfAnElement=x+y;
	

	
	if (noOfLinksOfAnElement>highlyCoupledElementThreshold && !ERD_allElements[6].equalsIgnoreCase("fm:Feature"))
	{
		//System.out.println("Element coupling: "+ noOfLinksOfAnElement);
		return true;
	}
	else return false;
		
	}

//Supporting Functions. 
//These functions are used in some of the developed bad smells queries 
boolean containsElement(int [] list, int element){
	 
	for (int i=0; i<list.length;i++)
		if (list [i]==element)
			return true;
	return false;
}

boolean isItSource(int x, int [][] ERD_allLinkRefs)	{

for (int i=0;i<ERD_allLinkRefs.length;i++){
	if(ERD_allLinkRefs[i][0]==x)
		
		return Boolean.TRUE;
}

return Boolean.FALSE;
}

boolean isTragetsLocationsOutsideThisActor(int x,int [][] ERD_allLinkRefs, List <Integer> listOfIntentElmntRefsInActor){

	int noOfTargets=0;
	for (int i=0;i<ERD_allLinkRefs.length;i++){
		if(ERD_allLinkRefs[i][0]==x)
			noOfTargets++;
	}
	
	int [] allTargets= new int [noOfTargets];
	int y=0;
	
	for (int i=0;i<ERD_allLinkRefs.length;i++){
	if(ERD_allLinkRefs[i][0]==x){
		allTargets[y]=ERD_allLinkRefs[i][1];
		y++;
		}
	}

	for (int i=0;i<allTargets.length;i++){
		for (int j=0;j<listOfIntentElmntRefsInActor.size();j++){	
			if (allTargets[i]== listOfIntentElmntRefsInActor.get(j))
				return Boolean.FALSE;
		}
	}

	return Boolean.TRUE;
}

boolean testingIfNodeIsReferedByRefs(int node, List<Integer> listOfIntenElmntRefsOfElement) {

if(listOfIntenElmntRefsOfElement.size()>0){
	
	for (int i = 0; i < listOfIntenElmntRefsOfElement.size(); i++){
		if(listOfIntenElmntRefsOfElement.get(i)==node)
			return true;
		}
	}
	return false;
}

boolean containsLink(int lnk, int [] allDependencies){

	for (int i=0; i<allDependencies.length; i++){
		if(allDependencies[i] == lnk)
			return Boolean.TRUE;
	}

	return Boolean.FALSE;
}

boolean chosen (int element, List <Integer> listOfIntentElmntRefsInActor, boolean [] selectedElements){

	for (int i = 0; i < listOfIntentElmntRefsInActor.size(); i++){
		if (element==listOfIntentElmntRefsInActor.get(i))
			return selectedElements[i];
	}
	
	return Boolean.FALSE;
}

boolean isItInSameActor(int element, int thisActor,List <Integer> allContRefsInActor,int [][] ERD_allIntentionalElementRefs){

	for (int i=0; i<ERD_allIntentionalElementRefs.length;i++){

		if (ERD_allIntentionalElementRefs[i][0]== element && ERD_allIntentionalElementRefs[i][2] != 0)
			return allContRefsInActor.contains(ERD_allIntentionalElementRefs[i][2]);
	}
	return false;
}

boolean isAllsourcesNotGoalsORSoftgoals(List <Integer> listOfSources , int [][] ERD_allIntentionalElementRefs, String [][] ERD_allElements) {
	int  nodeDef=-1;
	for (int i=0; i<listOfSources.size();i++ ) {
		for (int j=0;j<ERD_allIntentionalElementRefs.length;j++) {
			if (listOfSources.get(i)==ERD_allIntentionalElementRefs[j][0]) {
				nodeDef=ERD_allIntentionalElementRefs[j][1];
				break;
			}
		}
		for (int k=0; k<ERD_allElements.length;k++) {
			if (nodeDef == Integer.parseInt(ERD_allElements[k][0])) {
				if ((ERD_allElements[k][1].equalsIgnoreCase("Goal") || ERD_allElements[k][1].equalsIgnoreCase(""))) {
				 return false;
				}
			}
		}
	}

return true;
}

boolean isAllRefsOfAnElementsLowLevels(List <Integer> listOfIntenElmntRefsByElement, List <Integer> listOfLowLevelGoalsAndSoftgoalsRefs ) {


	for (int j = 0; j < listOfIntenElmntRefsByElement.size(); j++){
		if (! listOfLowLevelGoalsAndSoftgoalsRefs.contains(listOfIntenElmntRefsByElement.get(j)))
			return false;
	}

	return true;
}


}
			

