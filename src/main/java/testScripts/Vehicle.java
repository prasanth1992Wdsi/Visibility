package testScripts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.testng.annotations.Test;

public class Vehicle {
	@Test
	public void veh() throws Exception{
		
	
	ArrayList<String> cus =new ArrayList<String>();
	cus.add("name1-1");
	cus.add("name2-2");
	HashMap<String, String> NameNUm=new HashMap<String, String>();
	for(String nam:cus)
	{
	String[] name=nam.split("-");
	NameNUm.put(name[0], name[1]);
	}
System.out.println(NameNUm);
	
	}
}
