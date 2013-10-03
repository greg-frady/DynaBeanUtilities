package com.gregfrady.dyna.utilites.test;

import java.util.ArrayList;

import com.gregfrady.dyna.utilites.InterfaceInstanceBean;
import com.gregfrady.dyna.utilites.InterfaceInstanceBeanFactory;


public class TestDriver {

	public static void main(String[] args) throws Exception {
		// Create a new InterfaceInstanceBean 
		InterfaceInstanceBean interfaceInstance = InterfaceInstanceBeanFactory.createNewInstanceForInterface("com.gregfrady.dyna.utilites.test.CustomObjectA");
		
		// Set some attributes
		interfaceInstance.set("NaMe", "some name");
		interfaceInstance.set("lIST", new ArrayList<String>());
		interfaceInstance.set("integerValue", 9);
		
		// Print out the values returned
		System.out.println("Values for instance of [ " + interfaceInstance.getImplementedInterface() + " ]:");
		System.out.println("name: " + interfaceInstance.get("name"));
		System.out.println("integerValue: " + interfaceInstance.get("integerVALUE"));
		System.out.println("list = null?: " + (interfaceInstance.get("list") == null));
	}
}
