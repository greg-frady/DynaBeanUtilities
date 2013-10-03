/**
 * InterfaceInstanceBeanFactory
 * 
 * <P>Factory used for creating InterfaceInstanceBeans given the name of an interface.
 *  
 * @author Greg Frady
 * @date 10.02.2013
 */
package com.gregfrady.dyna.utilites;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;

public class InterfaceInstanceBeanFactory {

	private static String STRING_GET = ".get";
	private static String STRING_END_METHOD = "(";

	// Protect this class from instantiation
	private InterfaceInstanceBeanFactory() {
	}

	/**
	 * Orchestrates the creation of a new instance of InterfaceInstanceBean given the fully qualified class name of an interface. The interface is expected to
	 * have "getter" methods for retrieving attribute names. All other methods will be ignored. This is intended for creating an instance to represent a JavaBean
	 * implementation of an interface where no implementation is available.
	 * 
	 * @param interfaceName the fully qualified class name of an interface to "implement"
	 * @return a new instance of InterfaceInstanceBean representing the "implementation" of the interface given.
	 * @throws Exception if there is an issue reflecting upon the given interface or building the DynaBean representation
	 */
	public static InterfaceInstanceBean createNewInstanceForInterface(String interfaceName) throws Exception {

		// Map to hold attribute names and the type of each attribute represented.
		Map<String, Class<?>> attributeMap = new HashMap<String, Class<?>>();

		// If the class name is empty, we have no idea what interface to implement.
		if ((interfaceName == null) || (interfaceName.trim().isEmpty())) {
			throw new Exception("className was empty/null.");
		}

		// Using reflection, get the Class for the name passed in
		Class<?> cls = Class.forName(interfaceName);

		// Use reflection to get the public methods (since it's an interface, they are all public)
		Method[] methods = cls.getMethods();
		for (Method method : methods) {

			// Store the fully qualified method signature in a temporary variable
			String temp = method.toString();

			// If the method is a "getter" we will process it
			if (temp.contains(STRING_GET)) {

				// Get the text contained between ".get" and "(". For example, if ...getSomeAttribute(); is the method signature, we will sub it to "SomeAttribute"
				temp = method.toString().substring(temp.indexOf(STRING_GET) + STRING_GET.length(), temp.indexOf(STRING_END_METHOD));

				// Put the calculated attribute name into the attribute map with the return type of that method as the value.
				attributeMap.put(temp, method.getReturnType());
			}

		}

		// Return the value built by buildBean(...)
		return buildBean(attributeMap, cls.getName());
	}

	/**
	 * Builds an instance of InterfaceInstanceBean given a map where the key is the attribute name and the value is the attribute type AND the name for the interface
	 * that is will "implement".
	 * 
	 * @param attributeMap a Map where the key is an attribute name and the value is the type that the attribute represents
	 * @param className the fully qualified class name of the interface to "implement"
	 * @return a new instance of InterfaceInstanceBean that represents the "implementation" of the interface given
	 * @throws Exception if there is a problem creating the new instance
	 */
	private static InterfaceInstanceBean buildBean(Map<String, Class<?>> attributeMap, String className) throws Exception {

		// Create a list to hold the DynaProperty instances created for the class
		List<DynaProperty> dynaPropList = new ArrayList<DynaProperty>();

		Iterator<String> iter = attributeMap.keySet().iterator();
		while (iter.hasNext()) {

			// For each entry in the attribute map, create a DynaProperty instance using the attribute name (LOWER CASED) and the type for that entry
			String attributeName = iter.next();
			DynaProperty prop = new DynaProperty(attributeName.toLowerCase(), attributeMap.get(attributeName));

			// Add the new DynaProperty to the dynaPropList
			dynaPropList.add(prop);
		}

		// Create a DynaProperty array using the list created for the DynaProperty instances
		DynaProperty[] dynaPropArray = dynaPropList.toArray(new DynaProperty[0]);

		// Create a new instance of BasicDynaClass using the fully qualified name passed in, the InterfaceInstanceBean class (so the correct DynaBean implementation
		// is created) and the array of DynaProperty instances
		DynaClass clazz = new BasicDynaClass(className, InterfaceInstanceBean.class, dynaPropArray);

		// Return a new instance of the InterfaceInstanceBean as created by the DynaClass
		return (InterfaceInstanceBean) clazz.newInstance();
	}
}
