package com.gregfrady.dyna.utilites.test;

import java.util.List;

public interface CustomObjectA {

	public String getName();
	public void setName(String name);
	
	public int getIntegerValue();
	public void setIntegerValue(int value);
	
	public List<?> getList();
	public void setList(List<?> someList);
	
}
