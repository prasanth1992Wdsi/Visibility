
package testScripts;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.common.function.type4.Parser;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

public class PartialConsolidation extends ProjectSpecificFunctions {
	public String colText;
	public String parttype;
	public String LPN;
	public String  QNTY;
	public String NVNUqnty="3";
	public String dispatchnvnuqnty="2";
	public String dispatchVQNTY="5";
	ArrayList<String> LPNlist=new  ArrayList<String>();
	ArrayList<String> QNTYlist=new  ArrayList<String>();
	ArrayList<String> parttypelist=new  ArrayList<String>();
	public void partialconsolidation1() throws Exception {

		childTest = parantTest.createNode("Partial Consolidation");
		ArrayList <String>lpns=new ArrayList<String>();
		clickXpath(CONSOLIDATIONGRID,"CONSOLIDATIONGRID");
		verify(CONSOLIDATION);
		enterXpath(MASTERSEARCH1, SOCustReference,"PICKING SEARCH");
		Thread.sleep(1000);
		clickXpath(OPTISEARCH,"OPTIMIZATIONSEARCH");
		Thread.sleep(1000);
		Thread.sleep(1000);
		verify("//*[contains(text(),'1-1 of 1')]");
		clickXpath(ORDERCLICK,"ORDERCLICK");
		childTest.info("The order available in Consolidation screen");
		clickXpath(CONSOLIDATION,"CONSOLIDATION");
		Thread.sleep(2000);
		enterXpath(NOOFBOX, "1","NOOFBOX");
		clickXpath(ADDBOX,"ADDBOX");
		CartLabel = xpathGetText(LABEL);
		Thread.sleep(2000);
		clickXpath(SCANBOX,"SCANBOX");
		enterXpath(ENTERLPN, CUSLPN,"ENTERLPN");

		clickXpath(SUBMIT,"SUBMIT");
	
		enterXpath(NUNVQNTY,NVNUqnty,"NUNVQNTY");
		clickXpath(SUBMIT,"SUBMIT");
		childTest.pass("The NVNU Lpn with partial Quantity are Consolidated Successfully");

		
		int size = NVLPNpicking.size();
		String res[] = NVLPNpicking.toArray(new String[size]);
		for(int i=0;i<3;i++)
		{
			
			Thread.sleep(2000);
			enterXpath(ENTERLPN,res[i] ,"LPNFIELD");
			Thread.sleep(3000);

			clickXpath(SUBMIT,"SUBMIT");
		}
		childTest.pass("The NV Lpns are partially Consolidated");


		enterXpath(WEIGHT, "10","WEIGHT");
		Select select1 = new Select(driver.findElement(By.xpath(UOM)));
		select1.selectByVisibleText("Ton");
		clickXpath(COMPLETE,"COMPLETE");
		Thread.sleep(1000);

		clickXpath(FINISH,"FINISH");
		Thread.sleep(1000);
		List<WebElement> rows=driver.findElements(By.xpath("/html/body/div[2]/div[2]/div/div[2]/table/tbody/tr"));
		int rowCount=rows.size();
		System.out.println(rowCount);
		childTest.info("The UnConsolidated RowCount is Stored Successfully");
				
		List<WebElement> column=driver.findElements(By.xpath("/html/body/div[2]/div[2]/div/div[2]/table/thead/th"));
		int colCount=column.size();
		System.out.println(colCount);
		childTest.info("The UnConsolidated ColumnCount is Stored Successfully");
		
		ArrayList<String> colname=new  ArrayList<String>();
		for(int i=1;i<=colCount;i++)
		{
			
		colText=xpathGetText("/html/body/div[2]/div[2]/div/div[2]/table/thead/th["+i+"]");
		colname.add(colText);
		System.out.println(colname);
	
		if(colText.equalsIgnoreCase("LPN")){
			
			
			for(int k=1;k<=rowCount;k++)
			{
				Thread.sleep(2000);
				LPN = xpathGetText("/html/body/div[2]/div[2]/div/div[2]/table/tbody/tr["+k+"]/td[1]/b");
				LPNlist.add(LPN);
				
			}
			System.out.println(LPNlist);
			childTest.info("The UnConsolidated LPNs are Stored Successfully");
			
			}
		}
		Thread.sleep(2000);
		clickXpath("//*[@id='buttonSave'][1]");
	
		Thread.sleep(2000);
		childTest.pass("The order is Partially Consolidated for NV and NVNU Part");
		
		childTest.info("The carton label is :" + CartLabel);
		enterXpath(MASTERSEARCH1, SOCustReference,"PICKING SEARCH");
		clickXpath(OPTISEARCH,"OPTIMIZATIONSEARCH");
		Thread.sleep(1000);
		Thread.sleep(1000);
		verify("//*[contains(text(),'No Records Found')]");
		childTest.pass("The order is removed from Consolidation screen after Consolidation Completed ");
		System.out.println("Consolidation completed partially");
		childTest.pass("validation completed");

	}

	public void partial_dispatch() throws Exception {

		childTest = parantTest.createNode("Dispatch Order with Multiple Carrier & Carrier Ref");
		clickXpath(DISPATCHGRID,"DISPATCHGRID");
		Thread.sleep(2000);
		verify("(//*[@id='Despatch_REQUESTNO'])[1]");

		enterXpath(DISPATCHSEARCH, SOCustReference,"DISPATCHSEARCH");
		verify("//*[contains(text(),'1-1 of 1')]");
		clickXpath(Dispatch1,"Dispatch1");
		

		Thread.sleep(4000);
		clickXpath(DISPATCHBUTTON,"Dispatch1");
	
		childTest.info("The order available in Dispatch Screen");

		enterXpath(CARRIER, "Partial Consolidation and Dispatch","CARRIER");
		childTest.info("The Mode of carrier is :" + "Partial Consolidation and Dispatch");
		enterXpath(CARRIERREF, "SmokeTest","CARRIERREF");
		childTest.info("The carrier reference is :" + "SmokeTest");
	

		enterXpath(SCANLPN, CartLabel,"SCANLPN");

		actionEnter();
	
		Thread.sleep(2000);
		clickXpath("//*[@id='Despatch_finish']");
		childTest.pass("The order is Dispatched with 1st Carrier");
		Thread.sleep(3000);
		clickXpath(DISPATCHGRID,"DISPATCHGRID");
		enterXpath(DISPATCHSEARCH, SOCustReference,"DISPATCHSEARCH");
		clickXpath(Dispatch1,"Dispatch1");
		

		Thread.sleep(1000);
		clickXpath(DISPATCHBUTTON,"Dispatch1");
	
		childTest.info("The order available in Dispatch Screen");

		enterXpath(CARRIER, "Partial Consolidation and Dispatch 2","CARRIER");
		childTest.info("The Mode of carrier is :" + "Partial Consolidation and Dispatch 2");
		enterXpath(CARRIERREF, "SmokeTest for 2nd dispatch","CARRIERREF");
		
		childTest.info("The carrier reference is :" + "SmokeTest for 2nd dispatch");
	
		
		int size = LPNlist.size();
		String res[] = LPNlist.toArray(new String[size]);
		for(int i=1;i<=size-1;i++)
		{
			
			Thread.sleep(2000);
			enterXpath(SCANLPN, res[i],"SCANLPN");
			actionEnter();
			Thread.sleep(2000);
		}
		enterXpath(SCANLPN,CUSLPN,"SCANLPN");
		actionEnter();
		Thread.sleep(3000);
		enterXpath("//*[@id='Despatch_enterQty1']", dispatchnvnuqnty,"DISPATCH NVNU QNTY");
		actionEnter();
		childTest.pass("The order is Dispatched with 2nd Carrier");
		/*Thread.sleep(2000);
		clickXpath("//*[@id='Despatch_finish']");*/
		Thread.sleep(3000);
		enterXpath(DISPATCHSEARCH, SOCustReference,"DISPATCHSEARCH");
		//verify("//span[contains(text(),'1-1 of 1')]");
		verify("//*[contains(text(),'No Records Found')]");
		childTest.pass("The order is removed from Dispatch screen after Dispatch Completed");
		System.out.println("Dispatch completed sucessfully ");
		System.out.println("*********************************************************");
		
		

	}
	@Test
	public void fifoPicktypeInb() throws IOException, Exception {
		NVLPNpicking=new  ArrayList<String>();
		Lpn = new HashMap<String ,String>();
    	 partv="PARTVFIFO";
    	 partnv="PARTNVFIFO";
    	 partnvnu="PARTNVNUFIFO";
    	 picktype="FIFO";
  		parantTest = extent.createTest("Partial consolidation for FIFO Picktype"); 

    	 loginAsUser(Username);

    	 System.out.println("partial consolidation started");
		    vehicleCreation(site);
		  
		//	clearcells("PO");
		//	clearcells("SO");
			 part=new String[]{partv,partnv,partnvnu};
				quantity=new String[]{"5","5","5"};	
				binPickPart=new String[]{partv,partnv,partnvnu};
				binPickPartquantity=new int[]{5,5,5};
		    writeDataForCSV(picktype,"Newdelpoint","po", part,quantity);
		    writeDataForCSV(picktype,"Newdelpoint","so", part,quantity);

		//	fifoPart("PO");
		//	fifoPart("SO");
			 poreadData();
			    vqty();
			    nvnuqty();
			childTest = parantTest.createNode("PO Order creation for fifo picktype");
			clickIDLinkText(DASHBOARD);
			driver.navigate().refresh();
			childTest.info("Vehicle number is :  " + VEHICLENUMBER);
			verify(GATEIN_1stROW);
			enterXpath(SEARCH_BOX, VEHICLENUMBER,"Dock in searchbox");
			VehicleID = xpathGetText(GATEIN_1stROW);
		    clickXpath(VEHICLE1,"Vehicle number");
	        clickXpath(DOCKIN_BTN,"DOCKIN_BTN");
	        
			uploadFIFO("1");
		     verify(FINISH_UPLOAD_BTN);
			childTest.log(Status.PASS,"The uploaded PO parts  details for fifo Picktype: ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
			clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
			shortWait(2000);
			childTest.pass("Order upload finished for fifo Picktype");
			assignpod(PodUser);
			podGeneration();
			binnerAssignment(CustReference,Binner);
			System.out.println(partv+" "+partnv+" "+partnvnu);
			//binning1();		
			binning("",CustReference,Arrays.asList(binPickPart),binPickPartquantity);

		

	  //	parantTest = extent.createTest("Partial consolidation for Outbound process");
		   pickgenerationFIFO();
		   pickerassign1(SOCustReference);
		//	pickingScreenFS();
			//pickingScreenFS("",SOCustReference,Arrays.asList(binPickPart), binPickPartquantity);
			ArrayList<String>picked=pickingScreenFS("",SOCustReference,Arrays.asList(binPickPart),binPickPartquantity);
			ArrayList<String>partialLPNs=new 	ArrayList<String>();
			ArrayList<String>partialLPNs2=new 	ArrayList<String>();

			for(int i=0;i<picked.size();i++)
			{
				if(i<picked.size()/2)
			{
				partialLPNs.add(picked.get(i));
			}
			else 
			{
				partialLPNs2.add(picked.get(i));
	
			}
			}
System.out.println("partialLPNs "+partialLPNs);
System.out.println("partialLPNs2 "+partialLPNs2);

			ArrayList<String> carton=cons(SOCustReference, "partialmultibox",partialLPNs2);
			for(String lpn1:carton)
			{
				partialLPNs.add(lpn1);
			}
			 disp(SOCustReference,"",partialLPNs);

		//	partialconsolidation1();
		//	partial_dispatch();
			System.out.println("partial consolidation completed");
			logOut();
		   
	   }

}
