package org.eclipse.jucmnav.grl.GSDetector;


import javax.swing.JOptionPane;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * Copyright (C) 2022 Mawal Mohammed - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the Eclipse Public License - v 2.0 ,
 */


public class GRLBadSmellDetectionPreferences extends PreferencePage implements
        IWorkbenchPreferencePage, SelectionListener, ModifyListener {
   


    
	private Button  overlyPopulatedModelBS;
	private Button  overlyPopulatedActorBS;
	private Button  overlyAmbitiousActorBS;
    private Button  highlyCoupledActorBS;
    private Button  highlyProliferatedGoalORSoftGoalBS;
    private Button  highlyProliferatedTaskBS;
    private Button  possibilitiesExhausterBS;
    private Button  deepHierarchyBS;
    private Button  highlyCoupledElementBS;
    private Button  overlyOperationalizedActorBS;
    
    private Text  overlyPopulatedModelThreshold;
    private Text  overlyPopulatedActorThreshold;
    private Text  overlyAmbitiousActorThreshold;
    private Text  highlyCoupledActorThreshold;
    private Text  highlyProliferatedGoalORSoftGoalThreshold;
    private Text  highlyProliferatedTaskThreshold;
    private Text  possibilitiesExhausterThreshold;
    private Text  deepHierarchyThreshold;
    private Text  highlyCoupledElementThreshold;
    private Text  overlyOperationalizedActorThreshold;
   
    /**
     * Creates an new checkbox instance and sets the default
     * layout data.
     *
     * @param group  the composite in which to create the checkbox
     * @param label  the string to set into the checkbox
     * @return the new checkbox
     */
    private Button createCheckBox(Composite group, String label) {
        Button button = new Button(group, SWT.CHECK | SWT.LEFT);
        button.setText(label);
        button.addSelectionListener(this);
        GridData data = new GridData();
        button.setLayoutData(data);
        return button;
    }

    /**
     * Creates composite control and sets the default layout data.
     *
     * @param parent  the parent of the new composite
     * @param numColumns  the number of columns for the new composite
     * @return the newly-created coposite
     */
    private Composite createComposite(Composite parent, int numColumns) {
        Composite composite = new Composite(parent, SWT.NULL);

        //GridLayout
        GridLayout layout = new GridLayout();
        layout.numColumns = numColumns;
        composite.setLayout(layout);

        //GridData
        GridData data = new GridData();
        data.verticalAlignment = GridData.FILL;
        data.horizontalAlignment = GridData.FILL;
        composite.setLayoutData(data);
        return composite;
    }

    /** (non-Javadoc)
     * Method declared on PreferencePage
     */
    protected Control createContents(Composite parent) {
    
        //composite_tab2 << parent
        Composite composite_tab2 = createComposite(parent, 2);
        createLabel(composite_tab2, "Bad Smells:                                             : "); //$NON-NLS-1$
        createLabel(composite_tab2, "Thresholds (If any): ");
        //
        tabForward(composite_tab2);
        //composite_checkBox << composite_tab2
        Composite composite_checkBox = createComposite(parent, 2);
        
        
        overlyPopulatedModelBS=createCheckBox(composite_checkBox,"Overly Populated Model");
        overlyPopulatedModelThreshold = createTextField(composite_checkBox);
        
        overlyPopulatedActorBS=createCheckBox(composite_checkBox,"Overly Populated Actor");
   	  	overlyPopulatedActorThreshold = createTextField(composite_checkBox);

        overlyAmbitiousActorBS=createCheckBox(composite_checkBox,"Overly Ambitious Actor");
    	overlyAmbitiousActorThreshold = createTextField(composite_checkBox);
       
        deepHierarchyBS=createCheckBox(composite_checkBox,"Deep Heirarchical Refinement");
        deepHierarchyThreshold = createTextField(composite_checkBox);

        overlyOperationalizedActorBS=createCheckBox(composite_checkBox,"Overly Operationalized Actor");
        overlyOperationalizedActorThreshold = createTextField(composite_checkBox);

        highlyCoupledActorBS=createCheckBox(composite_checkBox,"Highly Coupled Actor");
        highlyCoupledActorThreshold = createTextField(composite_checkBox);

        highlyProliferatedGoalORSoftGoalBS=createCheckBox(composite_checkBox,"Highly Proliferated Goal/SoftGoal");
        highlyProliferatedGoalORSoftGoalThreshold = createTextField(composite_checkBox);

        highlyProliferatedTaskBS=createCheckBox(composite_checkBox,"Highly Proliferated Task");
        highlyProliferatedTaskThreshold = createTextField(composite_checkBox);

        possibilitiesExhausterBS=createCheckBox(composite_checkBox,"Possibilities Exhauster");
        possibilitiesExhausterThreshold = createTextField(composite_checkBox);

        highlyCoupledElementBS=createCheckBox(composite_checkBox,"Highly Coupled Element");
        highlyCoupledElementThreshold = createTextField(composite_checkBox);

       
       initializeValues();

        //font = null;
        return new Composite(parent, SWT.NULL);
    }

   
    private Label createLabel(Composite parent, String text) {
        Label label = new Label(parent, SWT.LEFT);
        label.setText(text);
        GridData data = new GridData();
        data.horizontalSpan = 1;
        data.horizontalAlignment = GridData.FILL;
        label.setLayoutData(data);
        return label;
    }

    private Text createTextField(Composite parent) {
        Text text = new Text(parent, SWT.SINGLE | SWT.BORDER);
        text.addModifyListener(this);
     
        GridData data = new GridData();
        data.horizontalAlignment = GridData.FILL;
        data.grabExcessHorizontalSpace = true;
        data.verticalAlignment = GridData.CENTER;
        data.grabExcessVerticalSpace = false;
    
        text.setLayoutData(data);
        
        return text;
    }


  
    protected IPreferenceStore doGetPreferenceStore() {
        return org.eclipse.jucmnav.grl.GSDetector.Activator.getDefault().getPreferenceStore();
    }

 
    public void init(IWorkbench workbench) {
        // do nothing
    }

    /**
     * Initializes states of the controls using default values
     * in the preference store.
     */
    private void initializeDefaults() {
     
        
        overlyPopulatedModelBS.setSelection(GRLBSDetectorConstants.Default_overlyPopulatedModelBS);
        overlyPopulatedActorBS.setSelection(GRLBSDetectorConstants.Default_overlyPopulatedActorBS);
        overlyAmbitiousActorBS.setSelection(GRLBSDetectorConstants.Default_overlyAmbitiousActorBS);
        deepHierarchyBS.setSelection(GRLBSDetectorConstants.Default_deepHierarchyBS);
        overlyOperationalizedActorBS.setSelection(GRLBSDetectorConstants.Default_overlyOperationalizedActorBS);
        highlyCoupledActorBS.setSelection(GRLBSDetectorConstants.Default_highlyCoupledActorBS);
        highlyProliferatedGoalORSoftGoalBS.setSelection(GRLBSDetectorConstants.Default_highlyProliferatedGoalORSoftGoalBS);
        highlyProliferatedTaskBS.setSelection(GRLBSDetectorConstants.Default_highlyProliferatedTaskBS);
        possibilitiesExhausterBS.setSelection(GRLBSDetectorConstants.Default_possibilitiesExhausterBS);
        highlyCoupledElementBS.setSelection(GRLBSDetectorConstants.Default_highlyCoupledElementBS);
        
        overlyPopulatedModelThreshold.setText(GRLBSDetectorConstants.Default_overlyPopulatedModelThreshold);
        overlyPopulatedActorThreshold.setText(GRLBSDetectorConstants.Default_overlyPopulatedActorThreshold);
        overlyAmbitiousActorThreshold.setText(GRLBSDetectorConstants.Default_overlyAmbitiousActorThreshold);
        deepHierarchyThreshold.setText(GRLBSDetectorConstants.Default_deepHierarchyThreshold);
        overlyOperationalizedActorThreshold.setText(GRLBSDetectorConstants.Default_overlyOperationalizedActorThreshold);
        highlyCoupledActorThreshold.setText(GRLBSDetectorConstants.Default_highlyCoupledActorThreshold);
        highlyProliferatedGoalORSoftGoalThreshold.setText(GRLBSDetectorConstants.Default_highlyProliferatedGoalORSoftGoalThreshold);
        highlyProliferatedTaskThreshold.setText(GRLBSDetectorConstants.Default_highlyProliferatedTaskThreshold);
        possibilitiesExhausterThreshold.setText(GRLBSDetectorConstants.Default_possibilitiesExhausterThreshold);
        highlyCoupledElementThreshold.setText(GRLBSDetectorConstants.Default_highlyCoupledElementThreshold);
       
      }

    /**
     * Initializes states of the controls from the preference store.
     */
    private void initializeValues() {
    	
    	IPreferenceStore store =  org.eclipse.jucmnav.grl.GSDetector.Activator.getDefault().getPreferenceStore();
    	
        overlyPopulatedModelBS.setSelection(store.getBoolean(GRLBSDetectorConstants.PRE_overlyPopulatedModelBS));
        overlyPopulatedActorBS.setSelection(store.getBoolean(GRLBSDetectorConstants.PRE_overlyPopulatedActorBS));
        overlyAmbitiousActorBS.setSelection(store.getBoolean(GRLBSDetectorConstants.PRE_overlyAmbitiousActorBS));
        deepHierarchyBS.setSelection(store.getBoolean(GRLBSDetectorConstants.PRE_deepHierarchyBS));
        overlyOperationalizedActorBS.setSelection(store.getBoolean(GRLBSDetectorConstants.PRE_overlyOperationalizedActorBS));
        highlyCoupledActorBS.setSelection(store.getBoolean(GRLBSDetectorConstants.PRE_highlyCoupledActorBS));
        highlyProliferatedGoalORSoftGoalBS.setSelection(store.getBoolean(GRLBSDetectorConstants.PRE_highlyProliferatedGoalORSoftGoalBS));
        highlyProliferatedTaskBS.setSelection(store.getBoolean(GRLBSDetectorConstants.PRE_highlyProliferatedTaskBS));
        possibilitiesExhausterBS.setSelection(store.getBoolean(GRLBSDetectorConstants.PRE_possibilitiesExhausterBS));
        highlyCoupledElementBS.setSelection(store.getBoolean(GRLBSDetectorConstants.PRE_highlyCoupledElementBS));
        
        overlyPopulatedModelThreshold.setText(store.getString(GRLBSDetectorConstants.PRE_overlyPopulatedModelThreshold));
        overlyPopulatedActorThreshold.setText(store.getString(GRLBSDetectorConstants.PRE_overlyPopulatedActorThreshold));
        overlyAmbitiousActorThreshold.setText(store.getString(GRLBSDetectorConstants.PRE_overlyAmbitiousActorThreshold));
        deepHierarchyThreshold.setText(store.getString(GRLBSDetectorConstants.PRE_deepHierarchyThreshold));
        overlyOperationalizedActorThreshold.setText(store.getString(GRLBSDetectorConstants.PRE_overlyOperationalizedActorThreshold));
        highlyCoupledActorThreshold.setText(store.getString(GRLBSDetectorConstants.PRE_highlyCoupledActorThreshold));
        highlyProliferatedGoalORSoftGoalThreshold.setText(store.getString(GRLBSDetectorConstants.PRE_highlyProliferatedGoalORSoftGoalThreshold));
        highlyProliferatedTaskThreshold.setText(store.getString(GRLBSDetectorConstants.PRE_highlyProliferatedTaskThreshold));
        possibilitiesExhausterThreshold.setText(store.getString(GRLBSDetectorConstants.PRE_possibilitiesExhausterThreshold));
        highlyCoupledElementThreshold.setText(store.getString(GRLBSDetectorConstants.PRE_highlyCoupledElementThreshold));
        
       // textField.setText(store.getString(GRLBSDetectorConstants.PRE_TEXT));
    }

    /** (non-Javadoc)
     * Method declared on ModifyListener
     */
    public void modifyText(ModifyEvent event) {
        //Do nothing on a modification in this example
    }

    /* (non-Javadoc)
     * Method declared on PreferencePage
     */
    protected void performDefaults() {
        super.performDefaults();
        initializeDefaults();
    }

    /* (non-Javadoc)
     * Method declared on PreferencePage
     */
    public boolean performOk() {
        storeValues();
        org.eclipse.jucmnav.grl.GSDetector.Activator.getDefault().savePluginPreferences();
        return true;
    }

    /**
     * Stores the values of the controls back to the preference store.
     */
    private void storeValues() {
    	
        IPreferenceStore store = getPreferenceStore();
        
        store.setValue(GRLBSDetectorConstants.PRE_overlyPopulatedModelBS, overlyPopulatedModelBS.getSelection());
        store.setValue(GRLBSDetectorConstants.PRE_overlyPopulatedActorBS, overlyPopulatedActorBS.getSelection());
        store.setValue(GRLBSDetectorConstants.PRE_overlyAmbitiousActorBS, overlyAmbitiousActorBS.getSelection());
        store.setValue(GRLBSDetectorConstants.PRE_deepHierarchyBS, deepHierarchyBS.getSelection());
        store.setValue(GRLBSDetectorConstants.PRE_overlyOperationalizedActorBS, overlyOperationalizedActorBS.getSelection());
        store.setValue(GRLBSDetectorConstants.PRE_highlyCoupledActorBS, highlyCoupledActorBS.getSelection());
        store.setValue(GRLBSDetectorConstants.PRE_highlyProliferatedGoalORSoftGoalBS, highlyProliferatedGoalORSoftGoalBS.getSelection());
        store.setValue(GRLBSDetectorConstants.PRE_highlyProliferatedTaskBS, highlyProliferatedTaskBS.getSelection());
        store.setValue(GRLBSDetectorConstants.PRE_possibilitiesExhausterBS, possibilitiesExhausterBS.getSelection());
        store.setValue(GRLBSDetectorConstants.PRE_highlyCoupledElementBS, highlyCoupledElementBS.getSelection());

        //The int type in Java any whole number from -2147483648 to 2147483647
        try {
        	int thresholdValue = Integer.parseInt(overlyPopulatedModelThreshold.getText()); //throws NumberFormatException if not int
        	if(thresholdValue > 2147483647 || thresholdValue < 0)
        		throw new NumberFormatException(); //throws NumberFormatException if not in the intended range
        	store.setValue(GRLBSDetectorConstants.PRE_overlyPopulatedModelThreshold, thresholdValue);
        	 }catch(NumberFormatException e){
        		 JOptionPane.showConfirmDialog(null, "Please enter an integer number between 0 and 2147483647 as an Overly Populated Model Threshold", "Error", JOptionPane.CANCEL_OPTION);
        	 }
        
        try {
        	int thresholdValue = Integer.parseInt(overlyPopulatedActorThreshold.getText());
        	if(thresholdValue > 2147483647 || thresholdValue < 0)
        		throw new NumberFormatException();       		
        	store.setValue(GRLBSDetectorConstants.PRE_overlyPopulatedActorThreshold, thresholdValue);
        	}catch(NumberFormatException e){
        		JOptionPane.showConfirmDialog(null, "Please enter an integer number between 0 and 2147483647 as an Overly Populated actor Threshold", "Error", JOptionPane.CANCEL_OPTION);
        	}
        
        try {
        	int thresholdValue = Integer.parseInt(overlyAmbitiousActorThreshold.getText());
        	if(thresholdValue > 2147483647 || thresholdValue < 0)
        		throw new NumberFormatException();
        	store.setValue(GRLBSDetectorConstants.PRE_overlyAmbitiousActorThreshold, thresholdValue);
        }catch(NumberFormatException e){
          		 JOptionPane.showConfirmDialog(null, "Please enter an integer number between 0 and 2147483647 as an Overly Ambitious Actor Threshold", "Error", JOptionPane.CANCEL_OPTION);
          	 }
           
        try {
        	int thresholdValue = Integer.parseInt(deepHierarchyThreshold.getText());
        	if(thresholdValue > 2147483647 || thresholdValue < 0)
        		throw new NumberFormatException();
        	store.setValue(GRLBSDetectorConstants.PRE_deepHierarchyThreshold, thresholdValue);
        }catch(NumberFormatException e){
          		 JOptionPane.showConfirmDialog(null, "Please enter an integer number between 0 and 2147483647 as a Deep Heirarchy Threshold", "Error", JOptionPane.CANCEL_OPTION);
        }
           
        try {
        	float thresholdValue = Float.parseFloat(overlyOperationalizedActorThreshold.getText());
        	if(thresholdValue > Math.pow(2, 127) || thresholdValue < 0)
        		throw new NumberFormatException();
        	store.setValue(GRLBSDetectorConstants.PRE_overlyOperationalizedActorThreshold, thresholdValue);
        }catch(NumberFormatException e){
        	JOptionPane.showConfirmDialog(null, "Please enter a float number between 0 and " + Math.pow(2, 127) + "as an Overly Operationalized Actor Threshold", "Error", JOptionPane.CANCEL_OPTION);
        }
           
        try {
        	int thresholdValue = Integer.parseInt(highlyCoupledActorThreshold.getText());
        	if(thresholdValue > 2147483647 || thresholdValue < 0)
        		throw new NumberFormatException();
        	store.setValue(GRLBSDetectorConstants.PRE_highlyCoupledActorThreshold, thresholdValue);
        }catch(NumberFormatException e){
        	JOptionPane.showConfirmDialog(null, "Please enter an integer number between 0 and 2147483647 as Highly Coupled Actor Threshold", "Error", JOptionPane.CANCEL_OPTION);
        }
           
        try {
        	int thresholdValue = Integer.parseInt(highlyProliferatedGoalORSoftGoalThreshold.getText());
        	if(thresholdValue> 2147483647 || thresholdValue < 0)
        		throw new NumberFormatException();
        	store.setValue(GRLBSDetectorConstants.PRE_highlyProliferatedGoalORSoftGoalThreshold, thresholdValue);
        }catch(NumberFormatException e){
        	JOptionPane.showConfirmDialog(null, "Please enter an integer number between 0 and 2147483647 as a Highly Proliferated Goal or SoftGoal Threshold", "Error", JOptionPane.CANCEL_OPTION);
        }
           
        try {
        	int thresholdValue = Integer.parseInt(highlyProliferatedTaskThreshold.getText());
        	if(thresholdValue> 2147483647 || thresholdValue < 0)
        		throw new NumberFormatException();
        	store.setValue(GRLBSDetectorConstants.PRE_highlyProliferatedTaskThreshold, thresholdValue);
        }catch(NumberFormatException e){
        	JOptionPane.showConfirmDialog(null, "Please enter an integer number between 0 and 2147483647 as a Highly Proliferated Task Threshold", "Error", JOptionPane.CANCEL_OPTION);
        }
           
        try {
        	int thresholdValue = Integer.parseInt(possibilitiesExhausterThreshold.getText());
        	if(thresholdValue> 2147483647 || thresholdValue < 0)
        		throw new NumberFormatException();
        	store.setValue(GRLBSDetectorConstants.PRE_possibilitiesExhausterThreshold, thresholdValue);
        }catch(NumberFormatException e){
        	JOptionPane.showConfirmDialog(null, "Please enter an integer number between 0 and 2147483647 as Possibilities Exhauster Threshold", "Error", JOptionPane.CANCEL_OPTION);
        }
           
        try {
        	int thresholdValue = Integer.parseInt(highlyCoupledElementThreshold.getText());
        	if(thresholdValue> 2147483647 || thresholdValue < 0)
        		throw new NumberFormatException();
             store.setValue(GRLBSDetectorConstants.PRE_highlyCoupledElementThreshold, thresholdValue);
         	 }catch(NumberFormatException e){
         		 JOptionPane.showConfirmDialog(null, "Please enter an integer number between 0 and 2147483647 as Highly Coupled Element Threshold", "Error", JOptionPane.CANCEL_OPTION);
         	 }
                
        
       
      //  store.setValue(GRLBSDetectorConstants.PRE_TEXT, textField.getText());
    }

    /**
     * Creates a tab of one horizontal spans.
     *
     * @param parent  the parent in which the tab should be created
     */
    private void tabForward(Composite parent) {
        Label vfiller = new Label(parent, SWT.LEFT);
        GridData gridData = new GridData();
        gridData = new GridData();
        gridData.horizontalAlignment = GridData.BEGINNING;
        gridData.grabExcessHorizontalSpace = false;
        gridData.verticalAlignment = GridData.CENTER;
        gridData.grabExcessVerticalSpace = false;
        vfiller.setLayoutData(gridData);
    }

    /** (non-Javadoc)
     * Method declared on SelectionListener
     */
    public void widgetDefaultSelected(SelectionEvent event) {
        //Handle a default selection. Do nothing in this example
    }

    /** (non-Javadoc)
     * Method declared on SelectionListener
     */
    public void widgetSelected(SelectionEvent event) {
        //Do nothing on selection in this example;
    }
}