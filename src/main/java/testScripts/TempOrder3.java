package testScripts;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

public class TempOrder3 extends ProjectSpecificFunctions {
	int vsize=0;
	int nvsize=0;
	int nvnusize=0;

	@Test
	public void tempOrder() throws Exception
	{
		parantTest = extent.createTest("Temp sales order with Reject and Accept with part Shortage");    

		loginAsUser(Username);
		partv="TEMPV02".toUpperCase();
		partnv="TEMPNV02".toUpperCase();
		partnvnu="TEMPNVNU02".toUpperCase();
		picktype="FIFO";
		//AcceptReject();
		//loginAsUser(Username);

		holdOrder();
		loginAsUser(Username);
		hold();
	//	loginAsUser(Username);
	//	holdOrder();
	}
	public void AcceptReject() throws Exception{
		NVLPNpicking=new  ArrayList<String>();
		Lpn = new HashMap<String ,String>();
		vehicleCreation(site);

		part=new String[]{partv,partnv,partnvnu};
		quantity=new String[]{"2","2","2"};	
		binPickPart=new String[]{partv,partnv,partnvnu};
		binPickPartquantity=new int[]{2,2,2};
		writeDataForCSV(picktype,"Newdelpoint","po", part,quantity);

	
		childTest = parantTest.createNode("PO Order creation for Temp sales order");
		//clickIDLinkText("Inbound");

		clickIDLinkText(DASHBOARD);
		driver.navigate().refresh();
		childTest.info("Vehicle number is :  " + VEHICLENUMBER);
		verify(GATEIN_1stROW);
		enterXpath(SEARCH_BOX, VEHICLENUMBER,"Dock in searchbox");
		VehicleID = xpathGetText(GATEIN_1stROW);
		clickXpath(VEH,"Vehicle number");
		clickXpath(DOCKIN_BTN,"DOCKIN_BTN");
		uploadFIFO("1");
		verify(FINISH_UPLOAD_BTN);
		childTest.log(Status.PASS,"The uploaded PO parts  details : ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
		shortWait(2000);
		assignpod(PodUser);
		podGeneration();
		binnerAssignment(CustReference,Binner);
		
		//binning1("normal");
		binning("",CustReference,Arrays.asList(new String[]{partv,partnv,partnvnu}), new int[]{2,2,2});


		childTest = parantTest.createNode("Sales Order Generation");
		//temp("SO");
		part=new String[]{partv,partnv,partnvnu};
		quantity=new String[]{""+(Available.get(partv)+1),""+(Available.get(partnv)+1),""+(Available.get(partnvnu)+1)};	

		writeDataForCSV(picktype,"intersite","so", part,quantity);

		//	randomizer("SO");
		soreadData();
		verify(OUTBOUND);
		clickXpath(OUTBOUND,"OUTBOUND");
		verify(OUTBOUND);


		clickIDLinkText("Pick Generation");
		verify(PICKGENERATION);
		partStockCheck(partnv,  "ijk",2);
		partStockCheck(partnvnu,  "mj",2);
		partStockCheck(partv,  "mj",2);



		clickXpath("//span[contains(.,'Create request')]","Create request");
		File file = new File(prop.getProperty("SOPATH"));
		clickXpath(ACCOUNTSELECT);		
		actionEnter();		
		enterXpath(CHOOSEFILE,file.getAbsolutePath());

		clickXpath(UPLOADSO,"UPLOADSO");

		Thread.sleep(3000);
		CommonFunctions.childTest.log(Status.PASS,"File upload status",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build());
		verify(ALERTBOXCANCEL);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");

		tempApprove("Reject Order");


		part=new String[]{partv,partnv,partnvnu};
		quantity=new String[]{""+(Available.get(partv)+1),""+(Available.get(partnv)+1),""+(Available.get(partnvnu)+1)};	

		writeDataForCSV(picktype,"intersite","so", part,quantity);
		//soreadData();

		NVNUQtn=Available.get(partnvnu)+"";
		NvQtn=Available.get(partnv)+"";
		VQTY=Available.get(partv)+"";
		System.out.println("The NVNU QTY :" + NVNUQtn);
		verify(OUTBOUND);
		clickXpath(OUTBOUND,"OUTBOUND");
		verify(OUTBOUND);


		clickIDLinkText("Pick Generation");
		verify(PICKGENERATION);
		partStockCheck(partnv,  "ijk",2);
		partStockCheck(partnvnu,  "mj",2);
		partStockCheck(partv,  "mj",2);



		clickXpath("//span[contains(.,'Create request')]","Create request");
		File file1 = new File(prop.getProperty("SOPATH"));
		clickXpath(ACCOUNTSELECT);
		actionEnter();
		enterXpath(CHOOSEFILE,file1.getAbsolutePath());

		clickXpath(UPLOADSO,"UPLOADSO");


		Thread.sleep(3000);
		CommonFunctions.childTest.log(Status.PASS,"File upload status",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build());
		verify(ALERTBOXCANCEL);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		tempApprove("Accept Order with Part Shortage");
		//clickXpath(ALERTBOXCANCEL);

		/*	writefive("PO");
		writefive("SO");
		 */
		clickXpath("//b[text()='Outbound']","OUTBOUND");
		/*partStockCheck(partnv,  "pickgen",read);
		partStockCheck(partnvnu,  "pickgen",nvnusize);
		partStockCheck(partv,  "pickgen",vsize);

		 */
		verify(PICKGENERATION);
		clickIDLinkText("Pick Generation");
		/*NVNUQtn=nvnusize+"";
		NvQtn=nvsize+"";
		VQTY=vsize+"";
		 */		pickerassign1(SOCustReference);
		 binPickPartquantity=new int[]{Integer.parseInt(VQTY),Integer.parseInt(NvQtn),Integer.parseInt(NVNUQtn)};

		 pickingScreenFS("",SOCustReference,Arrays.asList(binPickPart), binPickPartquantity);

		 //pickingScreenFS();
		 skipConsolidation(SOCustReference);
		 String SOcustreftemp=SOCustReference;
		 quantity=new String[]{""+VQTY,""+NvQtn,""+NVNUQtn};	

		 writeDataForCSV(picktype,"newdelpoint","so", part,quantity);
		 SOCustReference=SOcustreftemp;
		 //dispatch2(SOCustReference);
			clickXpath(DISPATCHGRID,"DISPATCHGRID");

		 bulkDispatch(SOCustReference);
		 enterXpath(DISPATCHSEARCH, SOCustReference,"DISPATCHSEARCH");
			Thread.sleep(1000);
			verify("//*[contains(text(),'No Records Found')]");
		 System.out.println("temp order completed");
		 logOut();
	}
	public void holdOrder() throws Exception
	{

		NVLPNpicking=new  ArrayList<String>(); 
		Lpn = new HashMap<String ,String>();
		parantTest = extent.createTest("Temp sales order with Hold Order");    

		vehicleCreation(site);
		part=new String[]{partv,partnv,partnvnu};
		quantity=new String[]{"2","2","2"};	
		binPickPartquantity=new int[]{2,2,2};
		binPickPart=new String[]{partv,partnv,partnvnu};
		writeDataForCSV(picktype,"endcustomer","po", part,quantity);

	
		childTest = parantTest.createNode("PO Order creation for Temp sales order");
		//clickIDLinkText("Inbound");

		clickIDLinkText(DASHBOARD);
		driver.navigate().refresh();
		childTest.info("Vehicle number is :  " + VEHICLENUMBER);
		verify(GATEIN_1stROW);
		enterXpath(SEARCH_BOX, VEHICLENUMBER,"Dock in searchbox");
		VehicleID = xpathGetText(GATEIN_1stROW);
		clickXpath(VEH,"Vehicle number");
		clickXpath(DOCKIN_BTN,"DOCKIN_BTN");
		uploadFIFO("1");
		verify(FINISH_UPLOAD_BTN);
		childTest.log(Status.PASS,"The uploaded PO parts  details : ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
		shortWait(2000);
		assignpod(PodUser);
		podGeneration();
		binnerAssignment(CustReference,Binner);
		//binning1("normal");
		binning("",CustReference,Arrays.asList(binPickPart),binPickPartquantity);


		childTest = parantTest.createNode("Sales Order Generation");
		//temp("SO");
		part=new String[]{partv,partnv,partnvnu};
		quantity=new String[]{""+(Available.get(partv)+1),""+(Available.get(partnv)+1),""+(Available.get(partnvnu)+1)};	

		writeDataForCSV(picktype,"newdelpoint","so", part,quantity);

		//	randomizer("SO");
		verify(OUTBOUND);
		clickXpath(OUTBOUND,"OUTBOUND");
		verify(OUTBOUND);


		clickIDLinkText("Pick Generation");
		verify(PICKGENERATION);


		clickXpath("//span[contains(.,'Create request')]","Create request");
		File file = new File(prop.getProperty("SOPATH"));
		clickXpath(ACCOUNTSELECT);		
		actionEnter();		
		enterXpath(CHOOSEFILE,file.getAbsolutePath());

		clickXpath(UPLOADSO,"UPLOADSO");

		Thread.sleep(2000);
		CommonFunctions.childTest.log(Status.PASS,"File upload status",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build());
		verify(ALERTBOXCANCEL);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");

		tempApprove("Hold Order");


		part=new String[]{partv,partnv,partnvnu};
		quantity=new String[]{""+2,""+2,""+2};	

		writeDataForCSV(picktype,"Newdelpoint","po", part,quantity);

		int  NVNUt=Available.get(partnvnu)+1;
		int	Nvt=Available.get(partnv)+1;
		int	Vt=Available.get(partv)+1;
		//	temp("SO");
		//randomizer("SO");


		//pickingScreenFS();
		String SOcustreftemp=SOCustReference;
		quantity=new String[]{""+VQTY,""+NvQtn,""+NVNUQtn};	

		//   writeDataForCSV(picktype,"Newdelpoint","so", part,quantity);
		vehicleCreation(site);

		childTest = parantTest.createNode("PO Order creation for Temp sales order");
		clickIDLinkText("Inbound");

		clickIDLinkText(DASHBOARD);
		driver.navigate().refresh();
		childTest.info("Vehicle number is :  " + VEHICLENUMBER);
		verify(GATEIN_1stROW);
		enterXpath(SEARCH_BOX, VEHICLENUMBER,"Dock in searchbox");
		VehicleID = xpathGetText(GATEIN_1stROW);
		clickXpath(VEH,"Vehicle number");
		clickXpath(DOCKIN_BTN,"DOCKIN_BTN");
		uploadFIFO("1");
		verify(FINISH_UPLOAD_BTN);
		childTest.log(Status.PASS,"The uploaded PO parts  details : ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
		shortWait(2000);
		assignpod(PodUser);
		podGeneration();
		binnerAssignment(CustReference,Binner);
		binPickPartquantity=new int[]{(2),(2),(2)};

		binning("",CustReference,Arrays.asList(binPickPart),binPickPartquantity);
		fluentWaitxpath(30,2,OUTBOUND);
		clickXpath(OUTBOUND,"OUTBOUND");
		verify(OUTBOUND);


		clickIDLinkText("Pick Generation");
		pickerassign1(SOCustReference);
		binPickPartquantity=new int[]{Vt,Nvt,NVNUt};

		pickingScreenFS("",SOCustReference,Arrays.asList(binPickPart), binPickPartquantity);

		skipConsolidation(SOCustReference);
		dispatch2(SOCustReference);
		System.out.println("temp order completed");
		logOut();
	}
	public void hold() throws  Exception
	{
		parantTest = extent.createTest("Temp sales order with Hold part with Short qty");    

		NVLPNpicking=new  ArrayList<String>();
		Lpn = new HashMap<String ,String>();

		vehicleCreation(site);
		
		part=new String[]{partv,partnv,partnvnu};
		quantity=new String[]{"2","2","2"};	
		binPickPart=new String[]{partv,partnv,partnvnu};
		binPickPartquantity=new int[]{2,2,2};
		writeDataForCSV(picktype,"endcustomer","po", part,quantity);

		
		childTest = parantTest.createNode("PO Order creation for Temp sales order");
		

		clickIDLinkText(DASHBOARD);
		driver.navigate().refresh();
		childTest.info("Vehicle number is :  " + VEHICLENUMBER);
		verify(GATEIN_1stROW);
		enterXpath(SEARCH_BOX, VEHICLENUMBER,"Dock in searchbox");
		VehicleID = xpathGetText(GATEIN_1stROW);
		clickXpath(VEH,"Vehicle number");
		clickXpath(DOCKIN_BTN,"DOCKIN_BTN");
		uploadFIFO("1");
		verify(FINISH_UPLOAD_BTN);
		childTest.log(Status.PASS,"The uploaded PO parts  details : ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
		shortWait(2000);
		assignpod(PodUser);
		podGeneration();
		binnerAssignment(CustReference,Binner);
		//binning1("normal");
		binning("",CustReference,Arrays.asList(new String[]{partv,partnv,partnvnu}), new int[]{2,2,2});


		childTest = parantTest.createNode("Sales Order Generation");
		//temp("SO");
		part=new String[]{partv,partnv,partnvnu};
		quantity=new String[]{""+(Available.get(partv)+1),""+(Available.get(partnv)+1),""+(Available.get(partnvnu)+1)};	

		writeDataForCSV(picktype,"intersite","so", part,quantity);

		verify(OUTBOUND);
		clickXpath(OUTBOUND,"OUTBOUND");
		verify(OUTBOUND);


		clickIDLinkText("Pick Generation");
		verify(PICKGENERATION);
		partStockCheck(partnv,  "ijk",2);
		partStockCheck(partnvnu,  "mj",2);
		partStockCheck(partv,  "mj",2);



		clickXpath("//span[contains(.,'Create request')]","Create request");
		File file = new File(prop.getProperty("SOPATH"));
		clickXpath(ACCOUNTSELECT);		
		actionEnter();		
		enterXpath(CHOOSEFILE,file.getAbsolutePath());

		clickXpath(UPLOADSO,"UPLOADSO");

		Thread.sleep(3000);
		CommonFunctions.childTest.log(Status.PASS,"File upload status",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build());
		verify(ALERTBOXCANCEL);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");

		tempApprove("Hold Part with Short Qty ");


		part=new String[]{partv,partnv,partnvnu};
		quantity=new String[]{""+(Available.get(partv)+2),""+(Available.get(partnv)+2),""+(Available.get(partnvnu)+2)};	

		writeDataForCSV(picktype,"endcustomer","po", part,quantity);
		//soreadData();

		int  NVNUt=Available.get(partnvnu)+1;
		int	Nvt=Available.get(partnv)+1;
		int	Vt=Available.get(partv)+1;
		
		String SOcustreftemp=SOCustReference;
		quantity=new String[]{""+VQTY,""+NvQtn,""+NVNUQtn};	

		
		vehicleCreation(site);

		childTest = parantTest.createNode("PO Order creation for Temp sales order");
		clickIDLinkText("Inbound");

		clickIDLinkText(DASHBOARD);
		driver.navigate().refresh();
		childTest.info("Vehicle number is :  " + VEHICLENUMBER);
		verify(GATEIN_1stROW);
		enterXpath(SEARCH_BOX, VEHICLENUMBER,"Dock in searchbox");
		VehicleID = xpathGetText(GATEIN_1stROW);
		clickXpath(VEH,"Vehicle number");
		clickXpath(DOCKIN_BTN,"DOCKIN_BTN");
		uploadFIFO("1");
		verify(FINISH_UPLOAD_BTN);
		childTest.log(Status.PASS,"The uploaded PO parts  details : ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
		shortWait(2000);
		assignpod(PodUser);
		podGeneration();
		binnerAssignment(CustReference,Binner);
		//binning1("normal");
		binPickPartquantity=new int[]{(Available.get(partv)+2),(Available.get(partnv)+2),(Available.get(partnvnu)+2)};
		binning("",CustReference,Arrays.asList(new String[]{partv,partnv,partnvnu}), new int[]{(Available.get(partv)+2),(Available.get(partnv)+2),(Available.get(partnvnu)+2)});
		fluentWaitxpath(30,2,OUTBOUND);
		clickXpath(OUTBOUND,"OUTBOUND");
		verify(OUTBOUND);


		clickIDLinkText("Pick Generation");
		clickIDLinkText("Pick Generation");

		enterXpath(MASTERSEARCH, SOCustReference,"MASTERSEARCH");


		clickXpath("//*[@id='PickGen_searchButton']", "search");
		Thread.sleep(1000);
		clickXpath(SELECTPART,"Checkbox");

		childTest.info("The order available in the pick generation screen");

		childTest = parantTest.createNode("picker Assignment");


		clickXpath(PICKERASSIGN,"PICKERASSIGN");

		verify(PICKERSUBMIT);

		enterXpath(PICKERINPUT, Picker,"PICKERINPUT");
		nameClick(Picker);


		clickXpath(PICKERSUBMIT,"PICKERSUBMIT");

		Thread.sleep(2000);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		clickXpath(PICKINGGRID,"PICKINGGRID");
		binPickPartquantity=new int[]{Vt,Nvt,NVNUt};

		ArrayList pickedlpns=pickingScreenFS("",SOCustReference,Arrays.asList(binPickPart), binPickPartquantity);
		
		skipConsolidation(SOCustReference);
		clickXpath(DISPATCHGRID,"DISPATCHGRID");
		quantity=new String[]{""+Vt,""+Nvt,""+NVNUt};	

		disp(SOCustReference, "not", pickedlpns);
		//dispatch2(SOCustReference);
		/*for(int i=0;i<3;i++)
		{
			clickXpath(DISPATCHGRID,"DISPATCHGRID");

		bulkDispatch(SOCustReference);
		}
		enterXpath(DISPATCHSEARCH, SOCustReference,"DISPATCHSEARCH");
		Thread.sleep(1000);
		verify("//*[contains(text(),'No Records Found')]");*/
		System.out.println("temp order completed");
		logOut();
	}
	public void tempApprove(String choose) throws Exception
	{String choose1="Accept Order with Part Shortage";
	String choose2="Hold Part with Short Qty ";
	clickIDLinkText("Misc");
	clickIDLinkText("Approve Request");
	clickXpath("(//*[contains(.,'Temp Sales Order')])[14]", "temp");
	verify(TEMPCHECK);
	enterXpath(TEMPSEARCH, SOCustReference);
	actionEnter();

	clickXpath(TEMPCHECK, "check");
	// select approval option

	if(choose.contains("Hold Part"))
	{
		selectByVisibleValue(TEMPCHOOSE, "RCM12002");

	}
	else
	{
		selectByVisibleText(TEMPCHOOSE, choose);

	}
	clickXpath(TEMPSAVE);
	verify(ALERTBOXCANCEL);
	verify(TEMPSUCCESSMSG,"Successfully");

	clickXpath(ALERTBOXCANCEL);
	}

	public void bulkDispatch(String SOCustReference) throws Exception
	{
	childTest = parantTest.createNode(" Bulk Dispatch ");
	
	verify("(//*[@id='Despatch_REQUESTNO'])[1]");
	enterXpath(DISPATCHSEARCH, SOCustReference,"DISPATCHSEARCH");
	clickXpath(Dispatch1,"Dispatch1");
	Thread.sleep(1000);
	clickXpath(DISPATCHBUTTON,"Dispatch1");
	   childTest.info("The order available in Dispatch Screen");
	        enterXpath(CARRIER, "Testing","CARRIER");
	childTest.info("The Mode of carrier is :" + "Testing");
	enterXpath(CARRIERREF, "SmokeTest","CARRIERREF");
	childTest.info("The carrier reference is :" + "SmokeTest");
	   selectByVisibleText(SCAN_DROPDOWN, "Bulk Dispatch");
	Thread.sleep(2000);
	clickXpath(SELECTALL_BTN, "Select All Btn");
	Thread.sleep(1000);
	clickXpath(BULK_SUBMIT, "Submit Btn");
	Thread.sleep(1000);
	Thread.sleep(1000);
	Thread.sleep(1000);
	Thread.sleep(1000);
	
/*	enterXpath(DISPATCHSEARCH, SOCustReference,"DISPATCHSEARCH");
	Thread.sleep(1000);
	verify("//*[contains(text(),'No Records Found')]");*/
	childTest.pass("The order is Dispatched By Bulk Dispatch");
	}


}
