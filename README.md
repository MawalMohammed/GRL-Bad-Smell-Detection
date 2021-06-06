# GRL-Bad-Smell-Detection
This repository contains an Eclipse plugin tool for detecting GRL bad smells based on heuristics

*Important notes:

    (1) This tool works on GRL models created with jUCMNav tool. 
     Therefore, jUCMNav plugin needs to be installed before installing this plugin
  
    (2) This plugin is developed and tested on Eclipse committers 6-2019

    (3) This plugin is a beta version
     
    (4) Source code is located in :
     
        org.eclipse.jucmnav.grl.GSDetector/src/org/eclipse/jucmnav/grl/GSDetector



*Installation Instructions:

First way:

    Go to Eclipse Help Menu -> Install New Software -> Add
                      
                Name:GSDetector
                Location: http://softwareengineeringresearch.net/GSDetector/

Second way:

    (1) Download GSDetector folder from this repository to your machine

    (2) Expand the downloaded RAR 
                    
    (3) Go to Eclipse Help Menu -> Install New Software -> Add -> Local -> Location of the expanded RAR
                           
                      Name:GSDetector
                      


*Usage Guide
      
      (1) Open the GRL model to be analyized
      
      (2) Go to Window Menu -> Preferences -> jUCMNav -> GRL Bad Smells Detection Preferences 
            Specify preferences as prefered
            If no preferences, click on Restore Defaults to use default values
            Close the Preferences page
     
      (3) Go to the main editor and click on the torch button. This button can be found in:
            (1) The jUCMNav labeled as "GRL Bad Smell Detector"
            (2) In the toolbar
            (3) Dropdown menu associated with jUCMNav in the Navigator

