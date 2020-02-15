package org.eclipse.jucmnav.grl.GSDetector;


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
 * Copyright (C) 2020 Mawal Mohammed - All Rights Reserved
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
     
        
        overlyPopulatedModelBS.setSelection(GRLRefactoringConstants.Default_overlyPopulatedModelBS);
        overlyPopulatedActorBS.setSelection(GRLRefactoringConstants.Default_overlyPopulatedActorBS);
        overlyAmbitiousActorBS.setSelection(GRLRefactoringConstants.Default_overlyAmbitiousActorBS);
        deepHierarchyBS.setSelection(GRLRefactoringConstants.Default_deepHierarchyBS);
        overlyOperationalizedActorBS.setSelection(GRLRefactoringConstants.Default_overlyOperationalizedActorBS);
        highlyCoupledActorBS.setSelection(GRLRefactoringConstants.Default_highlyCoupledActorBS);
        highlyProliferatedGoalORSoftGoalBS.setSelection(GRLRefactoringConstants.Default_highlyProliferatedGoalORSoftGoalBS);
        highlyProliferatedTaskBS.setSelection(GRLRefactoringConstants.Default_highlyProliferatedTaskBS);
        possibilitiesExhausterBS.setSelection(GRLRefactoringConstants.Default_possibilitiesExhausterBS);
        highlyCoupledElementBS.setSelection(GRLRefactoringConstants.Default_highlyCoupledElementBS);
        
        overlyPopulatedModelThreshold.setText(GRLRefactoringConstants.Default_overlyPopulatedModelThreshold);
        overlyPopulatedActorThreshold.setText(GRLRefactoringConstants.Default_overlyPopulatedActorThreshold);
        overlyAmbitiousActorThreshold.setText(GRLRefactoringConstants.Default_overlyAmbitiousActorThreshold);
        deepHierarchyThreshold.setText(GRLRefactoringConstants.Default_deepHierarchyThreshold);
        overlyOperationalizedActorThreshold.setText(GRLRefactoringConstants.Default_overlyOperationalizedActorThreshold);
        highlyCoupledActorThreshold.setText(GRLRefactoringConstants.Default_highlyCoupledActorThreshold);
        highlyProliferatedGoalORSoftGoalThreshold.setText(GRLRefactoringConstants.Default_highlyProliferatedGoalORSoftGoalThreshold);
        highlyProliferatedTaskThreshold.setText(GRLRefactoringConstants.Default_highlyProliferatedTaskThreshold);
        possibilitiesExhausterThreshold.setText(GRLRefactoringConstants.Default_possibilitiesExhausterThreshold);
        highlyCoupledElementThreshold.setText(GRLRefactoringConstants.Default_highlyCoupledElementThreshold);
       
      }

    /**
     * Initializes states of the controls from the preference store.
     */
    private void initializeValues() {
    	
    	IPreferenceStore store =  org.eclipse.jucmnav.grl.GSDetector.Activator.getDefault().getPreferenceStore();
    	
        overlyPopulatedModelBS.setSelection(store.getBoolean(GRLRefactoringConstants.PRE_overlyPopulatedModelBS));
        overlyPopulatedActorBS.setSelection(store.getBoolean(GRLRefactoringConstants.PRE_overlyPopulatedActorBS));
        overlyAmbitiousActorBS.setSelection(store.getBoolean(GRLRefactoringConstants.PRE_overlyAmbitiousActorBS));
        deepHierarchyBS.setSelection(store.getBoolean(GRLRefactoringConstants.PRE_deepHierarchyBS));
        overlyOperationalizedActorBS.setSelection(store.getBoolean(GRLRefactoringConstants.PRE_overlyOperationalizedActorBS));
        highlyCoupledActorBS.setSelection(store.getBoolean(GRLRefactoringConstants.PRE_highlyCoupledActorBS));
        highlyProliferatedGoalORSoftGoalBS.setSelection(store.getBoolean(GRLRefactoringConstants.PRE_highlyProliferatedGoalORSoftGoalBS));
        highlyProliferatedTaskBS.setSelection(store.getBoolean(GRLRefactoringConstants.PRE_highlyProliferatedTaskBS));
        possibilitiesExhausterBS.setSelection(store.getBoolean(GRLRefactoringConstants.PRE_possibilitiesExhausterBS));
        highlyCoupledElementBS.setSelection(store.getBoolean(GRLRefactoringConstants.PRE_highlyCoupledElementBS));
        
        overlyPopulatedModelThreshold.setText(store.getString(GRLRefactoringConstants.PRE_overlyPopulatedModelThreshold));
        overlyPopulatedActorThreshold.setText(store.getString(GRLRefactoringConstants.PRE_overlyPopulatedActorThreshold));
        overlyAmbitiousActorThreshold.setText(store.getString(GRLRefactoringConstants.PRE_overlyAmbitiousActorThreshold));
        deepHierarchyThreshold.setText(store.getString(GRLRefactoringConstants.PRE_deepHierarchyThreshold));
        overlyOperationalizedActorThreshold.setText(store.getString(GRLRefactoringConstants.PRE_overlyOperationalizedActorThreshold));
        highlyCoupledActorThreshold.setText(store.getString(GRLRefactoringConstants.PRE_highlyCoupledActorThreshold));
        highlyProliferatedGoalORSoftGoalThreshold.setText(store.getString(GRLRefactoringConstants.PRE_highlyProliferatedGoalORSoftGoalThreshold));
        highlyProliferatedTaskThreshold.setText(store.getString(GRLRefactoringConstants.PRE_highlyProliferatedTaskThreshold));
        possibilitiesExhausterThreshold.setText(store.getString(GRLRefactoringConstants.PRE_possibilitiesExhausterThreshold));
        highlyCoupledElementThreshold.setText(store.getString(GRLRefactoringConstants.PRE_highlyCoupledElementThreshold));
        
       // textField.setText(store.getString(GRLRefactoringConstants.PRE_TEXT));
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
        store.setValue(GRLRefactoringConstants.PRE_overlyPopulatedModelBS, overlyPopulatedModelBS.getSelection());
        store.setValue(GRLRefactoringConstants.PRE_overlyPopulatedActorBS, overlyPopulatedActorBS.getSelection());
        store.setValue(GRLRefactoringConstants.PRE_overlyAmbitiousActorBS, overlyAmbitiousActorBS.getSelection());
        store.setValue(GRLRefactoringConstants.PRE_deepHierarchyBS, deepHierarchyBS.getSelection());
        store.setValue(GRLRefactoringConstants.PRE_overlyOperationalizedActorBS, overlyOperationalizedActorBS.getSelection());
        store.setValue(GRLRefactoringConstants.PRE_highlyCoupledActorBS, highlyCoupledActorBS.getSelection());
        store.setValue(GRLRefactoringConstants.PRE_highlyProliferatedGoalORSoftGoalBS, highlyProliferatedGoalORSoftGoalBS.getSelection());
        store.setValue(GRLRefactoringConstants.PRE_highlyProliferatedTaskBS, highlyProliferatedTaskBS.getSelection());
        store.setValue(GRLRefactoringConstants.PRE_possibilitiesExhausterBS, possibilitiesExhausterBS.getSelection());
        store.setValue(GRLRefactoringConstants.PRE_highlyCoupledElementBS, highlyCoupledElementBS.getSelection());
        
        store.setValue(GRLRefactoringConstants.PRE_overlyPopulatedModelThreshold, overlyPopulatedModelThreshold.getText());
        store.setValue(GRLRefactoringConstants.PRE_overlyPopulatedActorThreshold, overlyPopulatedActorThreshold.getText());
        store.setValue(GRLRefactoringConstants.PRE_overlyAmbitiousActorThreshold, overlyAmbitiousActorThreshold.getText());
        store.setValue(GRLRefactoringConstants.PRE_deepHierarchyThreshold, deepHierarchyThreshold.getText());
        store.setValue(GRLRefactoringConstants.PRE_overlyOperationalizedActorThreshold, overlyOperationalizedActorThreshold.getText());
        store.setValue(GRLRefactoringConstants.PRE_highlyCoupledActorThreshold, highlyCoupledActorThreshold.getText());
        store.setValue(GRLRefactoringConstants.PRE_highlyProliferatedGoalORSoftGoalThreshold, highlyProliferatedGoalORSoftGoalThreshold.getText());
        store.setValue(GRLRefactoringConstants.PRE_highlyProliferatedTaskThreshold, highlyProliferatedTaskThreshold.getText());
        store.setValue(GRLRefactoringConstants.PRE_possibilitiesExhausterThreshold, possibilitiesExhausterThreshold.getText());
        store.setValue(GRLRefactoringConstants.PRE_highlyCoupledElementThreshold, highlyCoupledElementThreshold.getText());
       
      //  store.setValue(GRLRefactoringConstants.PRE_TEXT, textField.getText());
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