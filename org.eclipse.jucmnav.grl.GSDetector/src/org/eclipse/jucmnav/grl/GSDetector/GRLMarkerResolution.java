package org.eclipse.jucmnav.grl.GSDetector;

/**
 * Copyright (C) 2022 Mawal Mohammed - All Rights Reserved
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
import grl.GRLspec;
import grl.IntentionalElement;
import seg.jUCMNav.editors.UCMNavMultiPageEditor;
import urn.URNspec;


public class GRLMarkerResolution implements IMarkerResolutionGenerator {
	IWorkbenchPage activePage = org.eclipse.jucmnav.grl.GSDetector.Activator.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage();
	IEditorPart editor;
    UCMNavMultiPageEditor jucmEditor;
    URNspec urnspec;
    static GRLspec graph;
    
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
		
				
		
		 if (marker.getAttribute("sourceType").toString().equals("actor")) {
			List actorList = graph.getActors();
			
			for (int i=0;i<actorList.size();i++) {
				Actor actr=(Actor) actorList.get(i);
				if (actr.getId().toString().equals(marker.getAttribute(IMarker.SOURCE_ID))) {
				
					GRLBSDetectorConstants.oldSourceID=actr.getId();
					GRLBSDetectorConstants.oldSourceType="actor";
					actr.setLineColor(StringConverter.asString(new RGB(GRLBSDetectorConstants.RGB_RED, GRLBSDetectorConstants.RGB_GREEN, GRLBSDetectorConstants.RGB_BLUE )));
				//	System.out.println("color  "+actr.getLineColor());

				}
			}
		}
		else if (marker.getAttribute("sourceType").toString().equals("element")) {
			List intElements = graph.getIntElements();
			
			for (int i=0;i<intElements.size();i++) {
				IntentionalElement iElement=(IntentionalElement) intElements.get(i);
				if (iElement.getId().toString().equals(marker.getAttribute(IMarker.SOURCE_ID))) {
				
					GRLBSDetectorConstants.oldSourceID=iElement.getId();
					GRLBSDetectorConstants.oldSourceType="element";
					iElement.setLineColor(StringConverter.asString(new RGB(GRLBSDetectorConstants.RGB_RED, GRLBSDetectorConstants.RGB_GREEN, GRLBSDetectorConstants.RGB_BLUE )));
				//	System.out.println("color  "+iElement.getLineColor());

				}
			}
		}
			
		}catch(Exception e){
			
		}
			 IMarkerResolution[] mr=new  IMarkerResolution[3];
		
		return mr;
	}
	
public  static void resetColorOfTheLastChoice() {

	if (!GRLBSDetectorConstants.oldSourceType.equals("")) {
		if (GRLBSDetectorConstants.oldSourceType.equalsIgnoreCase("actor")) {
		List actorsList = graph.getActors();
		for (int i=0; i<actorsList.size();i++) {
			Actor actr=(Actor) actorsList.get(i);
			if (actr.getId().equalsIgnoreCase(GRLBSDetectorConstants.oldSourceID))
			actr.setLineColor(StringConverter.asString(new RGB(0, 0, 0)));

		}
	}else if (GRLBSDetectorConstants.oldSourceType.equalsIgnoreCase("element")) {
		List intElements = graph.getIntElements();
		for (int i=0;i<intElements.size();i++) {
			IntentionalElement iElement=(IntentionalElement) intElements.get(i);
			if (iElement.getId().equalsIgnoreCase(GRLBSDetectorConstants.oldSourceID))
				iElement.setLineColor(StringConverter.asString(new RGB(0, 0, 0)));

			}
		}
	}
}



}
