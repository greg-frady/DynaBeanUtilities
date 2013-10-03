/**
 * InterfaceInstanceBean
 * 
 * <P>An extension of BasicDynaBean that is used to represent an "implementation" of an interface. The interface is expected to have "getter" methods to use
 * for gathering the attribute names and types. Any other methods will be ignored, as this should be used ONLY TO REPRESENT INTERFACE AS A JAVA BEAN.  
 * 
 * Every method in this class simply calls a String.toLowerCase() on the name of the attribute passed in and calls the super method using this new name.
 *  
 * @author Greg Frady
 * @date 10.02.2013
 */

package com.gregfrady.dyna.utilites;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.DynaClass;

public class InterfaceInstanceBean extends BasicDynaBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8697644358359681498L;

	public String getImplementedInterface() {
		return this.getDynaClass().getName();
	}

	public InterfaceInstanceBean(DynaClass dynaClass) {
		super(dynaClass);
	}

	@Override
	public boolean contains(String name, String key) {
		return super.contains(name.toLowerCase(), key);
	}

	@Override
	public Object get(String name) {
		return super.get(name.toLowerCase());
	}

	@Override
	public Object get(String name, int index) {
		return super.get(name.toLowerCase(), index);
	}

	@Override
	public Object get(String name, String key) {
		return super.get(name.toLowerCase(), key);
	}

	@Override
	public DynaClass getDynaClass() {
		return super.getDynaClass();
	}

	@Override
	public void remove(String name, String key) {
		super.remove(name.toLowerCase(), key);
	}

	@Override
	public void set(String name, Object value) {
		super.set(name.toLowerCase(), value);
	}

	@Override
	public void set(String name, int index, Object value) {
		super.set(name.toLowerCase(), index, value);
	}

	@Override
	public void set(String name, String key, Object value) {
		super.set(name.toLowerCase(), key, value);
	}
}
