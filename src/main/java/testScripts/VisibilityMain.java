
package testScripts;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class VisibilityMain extends CommonFunctions{

	public static HSSFWorkbook wb;
	public static String classname;
	public static List<XmlClass>  classes;

	public static void main(String[]args)
	{
		TestNG tng = new TestNG();
		XmlSuite suite = new XmlSuite();
		suite.setName("Visibility");
		XmlTest test = new XmlTest(suite);
		test.setName("Long_regression");
		List listeners=new ArrayList();
		listeners.add("testScripts.TestNGlisteners");
		suite.setListeners(listeners);
		classes = new ArrayList<XmlClass>();


		try {
			
			FileInputStream file = new FileInputStream("./Classrunfilelong.xls");
			wb = new HSSFWorkbook(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		HSSFSheet sheet = wb.getSheetAt(0);
		int Rowcount = sheet.getLastRowNum();

		for (int i = 1; i <= Rowcount; i++) {

			if (sheet.getRow(i).getCell(2).toString().equalsIgnoreCase("Yes")) {  
				classname= sheet.getRow(i).getCell(0).toString();
				classes.add(new XmlClass((String) classname));


			}
		}

		System.out.println(classes);
		test.setXmlClasses(classes);

		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		suites.add(suite);

		tng.setXmlSuites(suites);
		tng.run();

	}
}

