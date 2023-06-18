package testScripts;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.opencsv.CSVWriter;

public class BodyShopLiveIssues extends ProjectSpecificFunctions	{

	
	HashMap<String, String> VLPNRETURN=null;
	ArrayList<String> NVLPNRETURN=null;
	String NVNURETURN=null;

	
	@Test(priority=13,alwaysRun=true)
	public void BodyShopFlow1() throws Exception
	{PickedNVLPNS=new ArrayList<String>();
		parantTest = extent.createTest("Live Issue:VD-13996_Unable to upload return Order for BodyShop");    

		site=prop.getProperty("SITEIDGRN");

		Binner=prop.getProperty("USERNAMEGRN");
		Picker=prop.getProperty("USERNAMEGRN");
		PodUser=prop.getProperty("USERNAMEGRN");
		Username=prop.getProperty("USERNAMEGRN");

		partnvnu="RETURNNVNU";
		picktype="CUSTLPNAPI";


		loginAsUser(Username);

		
		vehicleCreation(site);
		//childTest = parantTest.createNode("Vehicle Creation");

		part=new String[]{partnvnu};
		quantity=new String[]{"10"};
		binPickPart=new String[]{partnvnu};
		binPickPartquantity=new int[]{10};
		String q=quantity[(Arrays.asList(binPickPart)).indexOf(partnvnu)];
		writeDataForCSV(picktype,"Newdelpoint","po", part,quantity);

		childTest = parantTest.createNode("PO-API Order creation for CustLPN picktype _ BodyShop");
		clickIDLinkText(DASHBOARD);
		driver.navigate().refresh();
		childTest.info("Vehicle number is :  " + VEHICLENUMBER);
		verify(GATEIN_1stROW);
		enterXpath(SEARCH_BOX, VEHICLENUMBER,"Dock in searchbox");
		VehicleID = xpathGetText(GATEIN_1stROW);
		clickXpath(VEHICLE1,"Vehicle number");
		clickXpath(DOCKIN_BTN,"DOCKIN_BTN");
		uploadFIFO("ASSIGNORDER");
		//verify(FINISH_UPLOAD_BTN);
		shortWait(3000);
		childTest.log(Status.PASS,"The uploaded PO parts details for Custlpn picktype: ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
		shortWait(2000);
		assignpod(PodUser);
		podGeneration();
		GRNProcess(CustReference);

		binnerAssignment(CustReference,Binner);
		//binning1(CustReference);
		//	nvnupartBox=readXL("PO", 0, "CUST_BOX_LABEL");
		//nvpartBox=readXL("PO", 2, "CUST_BOX_LABEL");

		binning("",CustReference,Arrays.asList(binPickPart), binPickPartquantity);
		childTest.pass("Binning Completed for CustLPN Picktype _ BodyShop");
		childTest = parantTest.createNode("SO-API Order creation for CustLPN picktype _ BodyShop");
		writeDataForCSV(picktype,"Newdelpoint","so", part,quantity);

		//pickgenerationFIFO();
		verify(OUTBOUND);

		clickXpath("//b[text()='Outbound']","OUTBOUND");

		verify(PICKGENERATION);
		clickIDLinkText("Pick Generation");
		pickerassign1(SOCustReference);
		clickXpath("//b[text()='Outbound Label']","label");
		Label_Reprint(SOCustReference, "cartorderlevel");
		cart=new ArrayList<String>();
		cart.add(VLPN.get(0));
	
		ArrayList<String>picked=pickingScreenFS("cart",SOCustReference,Arrays.asList(binPickPart),binPickPartquantity);
		PickedNVLPNS.addAll(picked);

		for(int ii=0;ii<Integer.parseInt(quantity[(Arrays.asList(binPickPart)).indexOf(partnvnu)]);ii++)
		{
			picked.add(picked.get(0));
		}
		disp(SOCustReference,"",cons( SOCustReference,"MULTIBOXNVNU",picked));
		Packingslip_Quantity(readPDF(),SOCustReference);
		deleteFile();
		clickXpath("//b[text()='Outbound Label']","label");
		Label_Reprint(SOCustReference.toLowerCase(), "packingslip");
		Packingslip_Quantity(readPDF(),SOCustReference);
		Return_PO();

		logOut();


	}
	@Test(priority=14,alwaysRun=true)
	public void BodyShopFlow2() throws Exception
	{site=prop.getProperty("SITEIDGRN");
	
	Binner=prop.getProperty("USERNAMEGRN");
	Picker=prop.getProperty("USERNAMEGRN");
	PodUser=prop.getProperty("USERNAMEGRN");
	Username=prop.getProperty("USERNAMEGRN");


		partv="PARTVCUSLPN";
		partnv="PARTNVCUSLPN";
		partnvnu="PARTNVNUCUSLPN";
		picktype="CUSTLPNAPI";
		parantTest = extent.createTest("Live issue 13016 (BodyShopFlow -Dispatch Scanned quantity mismatch) ");    


		loginAsUser(Username);

		

		    vehicleCreation(site);
		    //childTest = parantTest.createNode("Vehicle Creation");
		   
		    part=new String[]{partnvnu};
			quantity=new String[]{"10"};
			binPickPart=new String[]{partnvnu};
			binPickPartquantity=new int[]{10};
			String q=quantity[(Arrays.asList(binPickPart)).indexOf(partnvnu)];
	    writeDataForCSV(picktype,"Newdelpoint","po", part,quantity);

			childTest = parantTest.createNode("PO Order creation for cuslpn picktype through API");
			clickIDLinkText(DASHBOARD);
			driver.navigate().refresh();
			childTest.info("Vehicle number is :  " + VEHICLENUMBER);
			verify(GATEIN_1stROW);
			enterXpath(SEARCH_BOX, VEHICLENUMBER,"Dock in searchbox");
			VehicleID = xpathGetText(GATEIN_1stROW);
		    clickXpath(VEHICLE1,"Vehicle number");
	        clickXpath(DOCKIN_BTN,"DOCKIN_BTN");
			uploadFIFO("ASSIGNORDER");
			//verify(FINISH_UPLOAD_BTN);
			shortWait(2000);

			childTest.log(Status.PASS,"The uploaded PO parts  details for cuslpn picktype: ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
			clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
			shortWait(2000);
			assignpod(PodUser);
			GRNProcess(CustReference);
			podGeneration();
			binnerAssignment(CustReference,Binner);
			

			binning("",CustReference,Arrays.asList(binPickPart), binPickPartquantity);

			//parantTest = extent.createTest("Live issue 13016 (BodyShopFlow -Dispatch Scanned quantity mismatch): Outbound testing for Customer LPN(API order creation)");    
		 
			writeDataForCSV(picktype,"Newdelpoint","so", part,quantity);
			DB(Arrays.asList(binPickPart), binPickPartquantity, "pickgen");

			//pickgenerationFIFO();
		    verify(OUTBOUND);

			clickXpath("//b[text()='Outbound']","OUTBOUND");

			verify(PICKGENERATION);
			clickIDLinkText("Pick Generation");
			pickerassign1(SOCustReference);
			clickXpath("//b[text()='Outbound Label']","label");
			Label_Reprint("2", "cartlabel");
			cart=new ArrayList<String>();
			cart.add(VLPN.get(0));
			ArrayList<String>cartons= new ArrayList<String>();
			
		pickingScreenFS("partialcart",SOCustReference,Arrays.asList(binPickPart),new int[]{5});
		clickXpath(CLEAR, "clear all");
		cart=new ArrayList<String>();
		cart.add(VLPN.get(1));

		binPickPartquantity=new int[]{10};

		ArrayList<String>picked=pickingScreenFS("cart",SOCustReference,Arrays.asList(binPickPart),new int[]{5});
		quantity=new String[]{"6"};
		for(int ii=0;ii<Integer.parseInt(quantity[(Arrays.asList(binPickPart)).indexOf(partnvnu)]);ii++)
		{
		picked.add(picked.get(0));
		}
		String[][] lpn=new String[6][1];
		lpn[0]=new String[]{picked.get(0),picked.get(0),picked.get(0)};

		cartons.addAll(cons("partial", SOCustReference,lpn,new String[]{"1","1","4"},1));

	cartons.add(VLPN.get(0));

	cartons.add(VLPN.get(1));

		disp(SOCustReference,"not",cartons);
		verify("(//*[contains(.,'Partial consolidation is done for this cart label, proceed with lpn')])[18]");
		childTest.log(Status.INFO, "Validation for cart label scan :",MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture1()).build());
		logOut();

		

	}
	@Test(priority=15,alwaysRun=true)
	public void BodyShopFlow3() throws Exception
	{site=prop.getProperty("SITEIDGRN");
	
	Binner=prop.getProperty("USERNAMEGRN");
	Picker=prop.getProperty("USERNAMEGRN");
	PodUser=prop.getProperty("USERNAMEGRN");
	Username=prop.getProperty("USERNAMEGRN");


		partv="PARTVCUSLPN";
		partnv="PARTNVCUSLPN";
		partnvnu="PARTNVNUCUSLPN";
		picktype="CUSTLPNAPI";
		parantTest = extent.createTest("Live issue 12992 (BodyShopFlow- PackingSlip mismatch issue)");    


		loginAsUser(Username);

		

		    vehicleCreation(site);
		    //childTest = parantTest.createNode("Vehicle Creation");
		   
		    part=new String[]{partnvnu,partnvnu,partnvnu};
			quantity=new String[]{"3","3","3"};
			binPickPart=new String[]{partnvnu};
			binPickPartquantity=new int[]{9};
			String q=quantity[(Arrays.asList(binPickPart)).indexOf(partnvnu)];
	    writeDataForCSV(picktype,"Newdelpoint","po", part,quantity);

			childTest = parantTest.createNode("PO Order creation for cuslpn picktype through API");
			clickIDLinkText(DASHBOARD);
			driver.navigate().refresh();
			childTest.info("Vehicle number is :  " + VEHICLENUMBER);
			verify(GATEIN_1stROW);
			enterXpath(SEARCH_BOX, VEHICLENUMBER,"Dock in searchbox");
			VehicleID = xpathGetText(GATEIN_1stROW);
		    clickXpath(VEHICLE1,"Vehicle number");
	        clickXpath(DOCKIN_BTN,"DOCKIN_BTN");
			uploadFIFO("ASSIGNORDER");
			//verify(FINISH_UPLOAD_BTN);
			shortWait(2000);

			childTest.log(Status.PASS,"The uploaded PO parts  details for cuslpn picktype: ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
			clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
			shortWait(2000);
			assignpod(PodUser);
			GRNProcess(CustReference);
			podGeneration();
			binnerAssignment(CustReference,Binner);
			

			binning("",CustReference,Arrays.asList(binPickPart), binPickPartquantity);

		//	parantTest = extent.createTest("Live issue 12992 (BodyShopFlow- PackingSlip mismatch issue): Outbound testing for Customer LPN(API order creation)");    
		 
			writeDataForCSV(picktype,"Newdelpoint","so", part,quantity);
			DB(Arrays.asList(binPickPart), binPickPartquantity, "pickgen");

			//pickgenerationFIFO();
		    verify(OUTBOUND);

			clickXpath("//b[text()='Outbound']","OUTBOUND");

			verify(PICKGENERATION);
			clickIDLinkText("Pick Generation");
			pickerassign1(SOCustReference);
		
			
			ArrayList<String>picked=	pickingScreenFS("",SOCustReference,Arrays.asList(binPickPart),new int[]{9});
System.out.println(picked);
			String[][] c=new String[6][1];
c[0]=new String[]{picked.get(0)};
c[1]=new String[]{picked.get(0)};
c[2]=new String[]{picked.get(1)};
c[3]=new String[]{picked.get(1)};
c[4]=new String[]{picked.get(2)};
c[5]=new String[]{picked.get(2)};

ArrayList<String> cartons=cons("", SOCustReference,c,new String[]{"2","1","2","1","2","1"},6);
ArrayList<String> partial1=new ArrayList<String>();
partial1.add(cartons.get(4));
partial1.add(cartons.get(5));
cartons.removeAll(partial1);
disp(SOCustReference,"partial",cartons);
binPickPartquantity=new int[]{6};
String label1=readPDF();
Packingslip_Quantity(label1,SOCustReference);


disp(SOCustReference,"",partial1);
binPickPartquantity=new int[]{3};
String label2=readPDF();

Packingslip_Quantity(readPDF(),SOCustReference);

deleteFile();
clickXpath("//b[text()='Outbound Label']","label");
Label_Reprint(SOCustReference.toLowerCase(), "packingslip");
String reprintlabel=readPDF();
if(reprintlabel.contains(label1)&&reprintlabel.contains(label2))
{
	childTest.log(Status.PASS, "PackingSlip reprinted are validated successfully");
}
else
{
	childTest.log(Status.FAIL, "PackingSlip reprinted and packingslip generated while dispatch are not same");
	childTest.log(Status.INFO, reprintlabel);

}

		logOut();



	}	
public void Return_PO() throws Exception{

		partnvnu="RETURNNVNU";
		picktype="CUSTLPN";
	
		vehicleCreation(site);
		part=new String[]{partnvnu};
		quantity=new String[]{"10"};
		binPickPart=new String[]{partnvnu};
		binPickPartquantity=new int[]{10};
		//String q=quantity[(Arrays.asList(binPickPart)).indexOf(partnvnu)];
		writeDataForCSV(picktype, "Newdelpoint", "returnorder",part,quantity);
		childTest = parantTest.createNode("BodyShop: Return Order Creation via Upload Option for CustLPN Picktype");    
		//childTest = parantTest.createNode("Return Order creation for cuslpn picktype");

		clickXpath(INBOUND, "Inbound");
		clickIDLinkText(DASHBOARD);
		driver.navigate().refresh();
		childTest.info("Vehicle number is :  " + VEHICLENUMBER);
		//verify(GATEIN_1stROW);
		enterXpath(SEARCH_BOX, VEHICLENUMBER,"Dock in searchbox");
		VehicleID = xpathGetText(GATEIN_1stROW);
		clickXpath(VEHICLE1,"Vehicle number");
		clickXpath(DOCKIN_BTN,"DOCKIN_BTN");
		CustRefList=new ArrayList<String>();
		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
		clickXpath(CREATEREQUEST_BTN,"Order_CreateRequest_Button");
		shortWait(2000);
		clickXpath(RETURN_BTN, "Return button");

		List cr=driver.findElements(By.xpath(".//img[@class='buttonProgress']"));
		while(cr.size()!=0)
		{
			Thread.sleep(1000);
			cr=driver.findElements(By.xpath(".//img[@class='buttonProgress']"));
		}

		actionClick("(//*[@class=' css-1hwfws3'])[1]");
		if(account1)
		{
			clickDownArrow();
		}
		Thread.sleep(1000);
		actionEnter();
		File file1 = new File(prop.getProperty("POPATH"));

		//File file1 = new File(prop.getProperty("RETURNORDERPATH"));
		//verify("(//*[contains(.,'OTIS INDIA')])[16]");
		Thread.sleep(2000);

		enterXpath("//*[@id='PO_fileupload']", file1.getAbsolutePath());
		//verify(DOC_TYPE);


		clickXpath(UPLOAD_PO,"UPLOAD ReturnOrder FILE");
		shortWait(3000);



		childTest.log(Status.PASS,"Alertbox : ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		clickXpath("//*[@class='close']", "CLOSE_BTN");
		shortWait(1000);
		JavascriptExecutor js1 = (JavascriptExecutor) driver;

		js1.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
		verify(RETURN_COMPLETE_BTN);
		childTest.log(Status.PASS,"The uploaded Return Order details for fifosuggested Picktype: ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		clickXpath(RETURN_COMPLETE_BTN,"COMPLETE_BTN");
		shortWait(2000);

		childTest.pass("Return Order uploaded Successfully for CustLPN Picktype");

		assignpod(PodUser);
		podGeneration();
		binnerAssignment(CustReference,Binner);
		//System.out.println(partv+" "+partnv+" "+partnvnu);
		//	returnOrderBinning();	
		binning("",CustReference,Arrays.asList(binPickPart), binPickPartquantity);

	//	System.out.println("return order binning  completed");
	}



	

	

}
