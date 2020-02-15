package org.eclipse.jucmnav.grl.GSDetector;

/**
 * Copyright (C) 2020 Mawal Mohammed - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the Eclipse Public License - v 2.0 ,
 */

import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator;
import org.eclipse.ui.IWorkbenchPage;


import grl.Actor;
import grl.ElementLink;
import grl.GRLspec;
import grl.IntentionalElement;
import grl.LinkRef;
import seg.jUCMNav.editors.UCMNavMultiPageEditor;
import urn.URNspec;


public class GRLMarkerResolution implements IMarkerResolutionGenerator {
	IWorkbenchPage activePage = org.eclipse.jucmnav.grl.GSDetector.Activator.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage();
	IEditorPart editor;
    UCMNavMultiPageEditor jucmEditor;
    URNspec urnspec;
    GRLspec graph;
    
	@Override
	public IMarkerResolution[] getResolutions(IMarker marker) {
		// TODO Auto-generated method stub
		activePage = org.eclipse.jucmnav.grl.GSDetector.Activator.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage();
		editor = activePage.getActiveEditor();
	    jucmEditor = (UCMNavMultiPageEditor) editor;
	    urnspec = jucmEditor.getModel();
	    graph = urnspec.getGrlspec();
			
		resetColorOfTheLastChoice();
		
			try {
		//System.out.println(marker.getAttribute("sourceType"));
		//System.out.println(marker.getAttribute(IMarker.SOURCE_ID));
		
		if (marker.getAttribute("sourceType").toString().equals("graph")) {
		//	org.eclipse.jucmnav.grl.rog.Activator.getDefault().getWorkbench().getActiveWorkbenchWindow().setActivePage(activePage);
		//	jucmEditor.setFocus();
		}
		
		
		else if (marker.getAttribute("sourceType").toString().equals("actor")) {
			List actorList = graph.getActors();
			
			for (int i=0;i<actorList.size();i++) {
				Actor actr=(Actor) actorList.get(i);
				if (actr.getId().toString().equals(marker.getAttribute(IMarker.SOURCE_ID))) {
				
					GRLRefactoringConstants.oldSourceID=actr.getId();
					GRLRefactoringConstants.oldSourceType="actor";
					actr.setLineColor(StringConverter.asString(new RGB(GRLRefactoringConstants.RGB_RED, GRLRefactoringConstants.RGB_GREEN, GRLRefactoringConstants.RGB_BLUE )));
				//	System.out.println("color  "+actr.getLineColor());

				}
			}
		}
		else if (marker.getAttribute("sourceType").toString().equals("link")) {
			List  linkList = graph.getLinks();
			
			for (int i=0;i<linkList.size();i++) {
				ElementLink lnk= (ElementLink)linkList.get(i);
				if (lnk.getId().toString().equals(marker.getAttribute(IMarker.SOURCE_ID))) {
				
					GRLRefactoringConstants.oldSourceID=lnk.getId();
					GRLRefactoringConstants.oldSourceType="link";
				
					
					
					ElementLink l = (ElementLink) linkList.get(0);
					//new ChangeColorAction((IWorkbenchPart) l, ChangeColorAction.COLOR_CHART[3]);
		
					
					LinkRef ll;
					

				}
			}
		}else if (marker.getAttribute("sourceType").toString().equals("element")) {
			List intElements = graph.getIntElements();
			
			for (int i=0;i<intElements.size();i++) {
				IntentionalElement iElement=(IntentionalElement) intElements.get(i);
				if (iElement.getId().toString().equals(marker.getAttribute(IMarker.SOURCE_ID))) {
				
					GRLRefactoringConstants.oldSourceID=iElement.getId();
					GRLRefactoringConstants.oldSourceType="element";
					iElement.setLineColor(StringConverter.asString(new RGB(GRLRefactoringConstants.RGB_RED, GRLRefactoringConstants.RGB_GREEN, GRLRefactoringConstants.RGB_BLUE )));
				//	System.out.println("color  "+iElement.getLineColor());

				}
			}
		}
			
		}catch(Exception e){
			
		}
			 IMarkerResolution[] mr=new  IMarkerResolution[3];
		
		return mr;
	}
	
public  void resetColorOfTheLastChoice() {

	if (!GRLRefactoringConstants.oldSourceType.equals("")) {
		if (GRLRefactoringConstants.oldSourceType.equalsIgnoreCase("actor")) {
		List actorsList = graph.getActors();
		for (int i=0; i<actorsList.size();i++) {
			Actor actr=(Actor) actorsList.get(i);
			if (actr.getId().equalsIgnoreCase(GRLRefactoringConstants.oldSourceID))
			actr.setLineColor(StringConverter.asString(new RGB(0, 0, 0)));

		}
	}else if (GRLRefactoringConstants.oldSourceType.equalsIgnoreCase("element")) {
		List intElements = graph.getIntElements();
		for (int i=0;i<intElements.size();i++) {
			IntentionalElement iElement=(IntentionalElement) intElements.get(i);
			if (iElement.getId().equalsIgnoreCase(GRLRefactoringConstants.oldSourceID))
				iElement.setLineColor(StringConverter.asString(new RGB(0, 0, 0)));

		}
	}
		
}

}
}
