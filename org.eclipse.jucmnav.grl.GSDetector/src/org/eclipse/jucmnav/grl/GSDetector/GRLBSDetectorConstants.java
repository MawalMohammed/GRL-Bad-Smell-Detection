package org.eclipse.jucmnav.grl.GSDetector;

/**
 * Copyright (C) 2022 Mawal Mohammed - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the Eclipse Public License - v 2.0 ,
 */

public class GRLBSDetectorConstants {
		
	
		public static final String PLUGIN_ID = "org.eclipse.jucmnav.grl.rog"; //$NON-NLS-1$

	    public static final String PREFIX = PLUGIN_ID + "."; //$NON-NLS-1$
	    
	    	     
	    // Bad Smells constants
	    public static final String PRE_overlyPopulatedModelBS = PREFIX + "check_overlyPopulatedModelBS"; //$NON-NLS-1$
	
	    public static final String PRE_overlyPopulatedActorBS = PREFIX + "check_overlyPopulatedActorBS"; //$NON-NLS-1$
	
	    public static final String PRE_overlyAmbitiousActorBS =  PREFIX + "check_overlyAmbitiousActorBS"; //$NON-NLS-1$ 
	    
	    public static final String PRE_deepHierarchyBS = PREFIX + "check_deepHierarchyBS"; //$NON-NLS-1$

	    public static final String PRE_overlyOperationalizedActorBS = PREFIX + "check_overlyOperationalizedActorBS"; //$NON-NLS-1$
	
	    public static final String PRE_highlyCoupledActorBS = PREFIX + "check_highlyCoupledActorBS"; //$NON-NLS-1$
	    
	    public static final String PRE_highlyProliferatedGoalORSoftGoalBS = PREFIX + "check_highlyProliferatedGoalORSoftGoalBS"; //$NON-NLS-1$

	    public static final String PRE_highlyProliferatedTaskBS = PREFIX + "check_highlyProliferatedTaskBS"; //$NON-NLS-1$
	    
	    public static final String PRE_possibilitiesExhausterBS = PREFIX + "check_possibilitiesExhausterBS"; //$NON-NLS-1$
	    
	    public static final String PRE_highlyCoupledElementBS = PREFIX + "check_highlyCoupledElementBS"; //$NON-NLS-1$

	   
	  
	    // Bad Smells constants
	    public static final boolean Default_overlyPopulatedModelBS = true; //$NON-NLS-1$
	    
	    public static final boolean Default_overlyPopulatedActorBS = true; //$NON-NLS-1$

	    public static final boolean Default_overlyAmbitiousActorBS = true; //$NON-NLS-1$
	  
	    public static final boolean Default_deepHierarchyBS = true; //$NON-NLS-1$
	    
		public static final boolean Default_overlyOperationalizedActorBS = true; //$NON-NLS-1$
		   
		public static final boolean Default_highlyCoupledActorBS = true; //$NON-NLS-1$
	
	    public static final boolean Default_highlyProliferatedGoalORSoftGoalBS = true; //$NON-NLS-1$

	    public static final boolean Default_highlyProliferatedTaskBS = true; //$NON-NLS-1$
	    
	    public static final boolean Default_possibilitiesExhausterBS = true; //$NON-NLS-1$

	    public static final boolean Default_highlyCoupledElementBS = true; //$NON-NLS-1$

	    
	  	    
	    // Thresholds constants
	    public static final String PRE_overlyPopulatedModelThreshold = PREFIX + "overlyPopulatedModel";
	    
	    public static final String PRE_overlyPopulatedActorThreshold = PREFIX + "overlyPopulatedActorThreshold"; //$NON-NLS-1$
	    
	    public static final String PRE_overlyAmbitiousActorThreshold = PREFIX + "overlyAmbitiousActorThreshold"; 
	  
	    public static final String PRE_deepHierarchyThreshold = PREFIX + "deepHierarchyThreshold"; 
	    
	    public static final String PRE_overlyOperationalizedActorThreshold = PREFIX + "overlyOperationalizedActorThreshold"; 
	   
	    public static final String PRE_highlyCoupledActorThreshold = PREFIX + "highlyCoupledActorThreshold"; 
	    
	    public static final String PRE_highlyProliferatedGoalORSoftGoalThreshold = PREFIX + "highlyProliferatedGoalORSoftGoalThreshold"; 
	    
	    public static final String PRE_highlyProliferatedTaskThreshold = PREFIX + "highlyProliferatedTaskThreshold"; 
	    
	    public static final String PRE_possibilitiesExhausterThreshold = PREFIX + "possibilitiesExhausterThreshold"; 
	    
	    public static final String PRE_highlyCoupledElementThreshold = PREFIX + "highlyCoupledElementThreshold"; 
	    
	    // Threshold preferences - Default values
	    public static final String Default_overlyPopulatedModelThreshold = "10"; 
	    
	    public static final String Default_overlyPopulatedActorThreshold = "50"; //$NON-NLS-1$
	    
	    public static final String Default_overlyAmbitiousActorThreshold = "20"; //$NON-NLS-1$
	    
	    public static final String Default_deepHierarchyThreshold = "5"; 
	    
	    public static final String Default_overlyOperationalizedActorThreshold = "300"; 

	    public static final String Default_highlyCoupledActorThreshold = "10"; 
	    
	    public static final String Default_highlyProliferatedGoalORSoftGoalThreshold = "5"; 
	    
	    public static final String Default_highlyProliferatedTaskThreshold = "5"; 
	    
	    public static final String Default_possibilitiesExhausterThreshold = "5"; 
	    
	    public static final String Default_highlyCoupledElementThreshold = "10"; 
		   
	
	    //Color 
	    public static final int RGB_BLUE = 255;
	    public static final int RGB_GREEN = 10;
	    public static final int RGB_RED = 10;
	    
	    public static String oldColor = "";
	    public static String oldSourceType = "";
	    public static String oldSourceID = "";
	 
	}
