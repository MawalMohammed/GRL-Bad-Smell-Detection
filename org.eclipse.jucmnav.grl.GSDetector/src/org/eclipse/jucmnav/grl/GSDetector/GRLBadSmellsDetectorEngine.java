package org.eclipse.jucmnav.grl.GSDetector;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import seg.jUCMNav.editors.UCMNavMultiPageEditor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;

/**
 * Copyright (C) 2020 Mawal Mohammed - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the Eclipse Public License - v 2.0 ,
 */

public class GRLBadSmellsDetectorEngine implements IEditorActionDelegate {

private IWorkbenchPage activePage;
private IEditorPart  activeEditor;
UCMNavMultiPageEditor jucmEditor;
private IFile activeFile;

String fileLoc;
Document jucmDoc;

NodeList actorList;

	@Override
	public void run(IAction action) {
		
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
		GRLRefactoringConstants.oldColor="";
		GRLRefactoringConstants.oldSourceID="";
		GRLRefactoringConstants.oldSourceType="";
		
		// Start bad smells detection based on the preferences 
		try {
			
			File jucmFile = new File(fileLoc);
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			jucmDoc = docBuilder.parse(jucmFile);
			jucmDoc.getDocumentElement().normalize();
		}   catch (Exception e) {
			e.printStackTrace();
		}
		
		GRLBadSmellDetectors badSmellsDetector= new GRLBadSmellDetectors(jucmEditor, activeFile, jucmDoc);
		
		// overly populated actor Bad Smell
		if (store.getBoolean(GRLRefactoringConstants.PRE_overlyPopulatedModelBS)) {   
			badSmellsDetector.overlyPopulatedModelDetector(Integer.parseInt(store.getString(GRLRefactoringConstants.PRE_overlyPopulatedModelThreshold)));
		}		

		//overly populated actor Bad Smell
		if (store.getBoolean(GRLRefactoringConstants.PRE_overlyPopulatedActorBS)) { 
			badSmellsDetector.overlyPopulatedActorDetector(Integer.parseInt(store.getString(GRLRefactoringConstants.PRE_overlyPopulatedActorThreshold)));	
		}
		
		//overly ambitious actor Bad Smell
		if (store.getBoolean(GRLRefactoringConstants.PRE_overlyAmbitiousActorBS)) { 
			badSmellsDetector.overlyAmbitiousActorDetector(Integer.parseInt(store.getString(GRLRefactoringConstants.PRE_overlyAmbitiousActorThreshold)));	
			
		}
		
		//deep hierarchy bad smell
		if (store.getBoolean(GRLRefactoringConstants.PRE_deepHierarchyBS)) { 
			badSmellsDetector.deepHierarchyDetector(Integer.parseInt(store.getString(GRLRefactoringConstants.PRE_deepHierarchyThreshold)));
		}
		
		//too MuchOperationalization bad smell
		if (store.getBoolean(GRLRefactoringConstants.PRE_overlyOperationalizedActorBS)) {   
			badSmellsDetector.overlyOperationalizedActorDetector(Float.parseFloat(store.getString(GRLRefactoringConstants.PRE_overlyOperationalizedActorThreshold)));
		} 
		
		//Highly Coupled Actor
		if (store.getBoolean(GRLRefactoringConstants.PRE_highlyCoupledActorBS)) { 
			badSmellsDetector.highlyCoupledActorDetector(Integer.parseInt(store.getString(GRLRefactoringConstants.PRE_highlyCoupledActorThreshold)));
		}
		
		// God Goal OR SoftGoal Bad Smell
		if (store.getBoolean(GRLRefactoringConstants.PRE_highlyProliferatedGoalORSoftGoalBS)) { 
			badSmellsDetector.highlyProliferatedGoalORSoftGoalDetector(Integer.parseInt(store.getString(GRLRefactoringConstants.PRE_highlyProliferatedGoalORSoftGoalThreshold)));
		}
		
		//god task Bad smell
		if (store.getBoolean(GRLRefactoringConstants.PRE_highlyProliferatedTaskBS)) { 
			badSmellsDetector.highlyProliferatedTaskDetector(Integer.parseInt(store.getString(GRLRefactoringConstants.PRE_highlyProliferatedTaskThreshold)));
		}
		

		//Listing possibilities bad Smell
		if (store.getBoolean(GRLRefactoringConstants.PRE_possibilitiesExhausterBS)) { 
			badSmellsDetector.possibilitiesExhausterDetector(Integer.parseInt(store.getString(GRLRefactoringConstants.PRE_possibilitiesExhausterThreshold)));
		}
		
		//	highly coupled Element bad smell
		if (store.getBoolean(GRLRefactoringConstants.PRE_highlyCoupledElementBS)) { 
			badSmellsDetector.highlyCoupledElementDetector(Integer.parseInt(store.getString(GRLRefactoringConstants.PRE_highlyCoupledElementThreshold)));
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
