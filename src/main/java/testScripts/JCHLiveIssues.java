package testScripts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

public class JCHLiveIssues extends ProjectSpecificFunctions{

	public int c;
	public  ArrayList<String> packing =new ArrayList<String>();
ArrayList<String> pickedLPNS1=new ArrayList<String>();
ArrayList<String> NVLPNRETURN=null;
String last;
String last1;
String last2;


public String poquantity;
	@Test(priority=9,alwaysRun=true)
	public void  live_13034() throws  Exception
	{	parantTest = extent.createTest("Live issue-13034(JCH): Packing slip mismatch");    

		loginAsUser(Username);
		vehicleCreation(site);
		partnvnu="AJNVNU";
		picktype="CUSTLPNMULTI";
		part=new String[]{partnvnu,"AJNVNU1"};
		quantity=new String[]{"2","2"};	
		binPickPart=new String[]{partnvnu,"AJNVNU1"};
		binPickPartquantity=new int[]{2,2};
		writeDataForCSV(picktype,"Newdelpoint","po", part,quantity);
		childTest = parantTest.createNode("PO Order creation for cuslpn picktype");
		clickIDLinkText(DASHBOARD);
		driver.navigate().refresh();
		childTest.info("Vehicle number is :  " + VEHICLENUMBER);
		verify(GATEIN_1stROW);
		enterXpath(SEARCH_BOX, VEHICLENUMBER,"Dock in searchbox");
		VehicleID = xpathGetText(GATEIN_1stROW);
		clickXpath(VEHICLE1,"Vehicle number");
		clickXpath(DOCKIN_BTN,"DOCKIN_BTN");
		uploadFIFO("account1");
		verify(FINISH_UPLOAD_BTN);
		childTest.log(Status.PASS,"The uploaded PO parts  details for cuslpn picktype: ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
		shortWait(2000);
		childTest.pass("Order upload finished for cuslpn picktype");
		assignpod(PodUser);
		podGeneration();
		for(String customerref:CustReference1)
		{
			binnerAssignment(customerref,Binner);
		}
		int i=0;
		for(String customerref:CustReference1)
		{
			binning("MULTI",customerref,Arrays.asList(new String[]{part[i]}), new int[]{Integer.parseInt(quantity[i])});
			i++;
		}
		VLPN=null;
		writeDataForCSV(picktype,"Newdelpoint","so", part,quantity);

		pickgenerationFIFO();
		for(String customerref:CustReference1)
		{
			pickerassign1(customerref);
		}
		int j=0;
		for(String customerref:CustReference1)
		{
			pickedLPNS1.addAll(pickingScreenFS("multi",customerref,Arrays.asList(new String[]{part[j]}), new int[]{Integer.parseInt(quantity[j])}));
			skipConsolidation(customerref);
			VLPN=null;
			j++;
		}
		System.out.println("pickedLPNS1"+pickedLPNS1);
		ArrayList<String> pickedlpn2=new ArrayList<String>();
		pickedlpn2.add(pickedLPNS1.get(0));
CUSLPN=pickedLPNS1.get(0);
binPickPart=new String[]{CUSLPN};

quantity=new String[]{"1"};	

		disp(CustReference1.get(0),"partial",pickedlpn2);
		packing.add(readPDF());
		CUSLPN=pickedLPNS1.get(0);

		disp(CustReference1.get(0),"", pickedlpn2);
		driver.navigate().refresh();
		packing.add(readPDF());
		pickedlpn2.remove(0);
		pickedlpn2.add(pickedLPNS1.get(1));
		CUSLPN=pickedLPNS1.get(1);
		binPickPart=new String[]{CUSLPN};

		disp(CustReference1.get(1),"partial",pickedlpn2);
		packing.add(readPDF());
		CUSLPN=pickedLPNS1.get(1);

		disp(CustReference1.get(1),"", pickedlpn2);
		packing.add(readPDF());
		String label1=packing.get(0)+packing.get(1);
		String label2=packing.get(2)+packing.get(3);
		deleteFile();
		//if(label1.equalsIgnoreCase(OUTBOUND_Dispatch_label(CustReference1.get(0)))){
		clickXpath("//b[text()='Outbound Label']","label");
		Label_Reprint(CustReference1.get(0), "packingslip");
		if(label1.equalsIgnoreCase(readPDF())){	
		childTest.pass("Both the label are same");
		}
		else{
			childTest.fail("Reprint label is not maching");
			System.out.println("Not maching");
			System.out.println(label1);
		}
		logOut();
		System.out.println("cuslpn");
	}
	

	@Test(priority=10,alwaysRun=true)
	public void  live_14438() throws  Exception
	{
		parantTest = extent.createTest("FinolexFlow With API order creation for LIVE ISSUE VD-13739"); 

		partnv="PARTNVFSCUS";

picktype="CUSTLPN";
part=new String[]{partnv,partnv,partnv,partnv,partnv,partnv};
quantity=new String[]{"1","1","1","1","1","1"};
binPickPart=new String[]{partnv};
binPickPartquantity=new int[]{3};
Bin=prop.getProperty("BIN_LABEL");
System.out.println("Jch order binning  started");
loginAsUser(Username);
vehicleCreation(site);

writeDataForCSV(picktype,"Newdelpoint","po", part,quantity);
// writeDataForCSV(picktype,"Newdelpoint","so", part,quantity);
childTest = parantTest.createNode("PO Order creation for fifo Suggested picktype _ JCH");
clickIDLinkText(DASHBOARD);
driver.navigate().refresh();
childTest.info("Vehicle number is :  " + VEHICLENUMBER);
verify(GATEIN_1stROW);
enterXpath(SEARCH_BOX, VEHICLENUMBER,"Dock in searchbox");
VehicleID = xpathGetText(GATEIN_1stROW);
clickXpath(VEHICLE1,"Vehicle number");
clickXpath(DOCKIN_BTN,"DOCKIN_BTN"); 
uploadFIFO("1");

childTest.log(Status.PASS,"The uploaded PO parts details for fifo suggested Picktype JCH: ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
verify(FINISH_UPLOAD_BTN);
clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
shortWait(2000);
childTest.pass("Order upload finished for fifo suggested Picktype _ JCH");

assignpod(PodUser);
podGeneration();

binnerAssignment(CustReference,Binner);
System.out.println(partnv);
Thread.sleep(1000);
binning("",CustReference,Arrays.asList(new String[]{partnv}),binPickPartquantity);
picktype="FIFOSUGGESTED";
part=new String[]{partnv};
quantity=new String[]{"3"};
parantTest = extent.createTest(" Picking and Dispatch for Fifosuggested picktype---JCH");
writeDataForCSV(picktype,"Newdelpoint","so", part,quantity);
pickgenerationFIFO();
pickerassign1(SOCustReference);
ArrayList<String>picked=pickingScreenFS("",SOCustReference,Arrays.asList(binPickPart),binPickPartquantity);
skipConsolidation(SOCustReference);
disp(SOCustReference,"",picked);

parantTest = extent.createTest(" LPN level cancellation for remaining LPNs of Fifosuggested picktype---JCH");
Thread.sleep(1000);
clickIDLinkText("Misc");
Thread.sleep(1000);
clickIDLinkText("Cancellation");
childTest = parantTest.createNode("LPN Level cancellation- At Binner Assignment screen");
childTest.info("LPN level cancellation initiated");
selectByvalue(CANCEL_LEVEL, 3);
//verify("(//*[contains(.,'Search')])[23]","Search");
enterXpath( CANCEL_SOSEARCH, CustReference,"CANCEL_SOSEARCH");
//enterXpath( CANCEL_LPNSEARCH, vlpn.get(1),"CANCEL_LPNSEARCH");
enterXpath( CANCEL_PARTSEARCH, partnv,"CANCEL_PARTSEARCH");
actionEnter();
verify("(//*[contains(.,'INB')])[19]");
clickXpath( CANCEL_PART_CHECKBOX1,"CANCEL_PART_CHECKBOX");
selectByVisibleText(CANCEL_REASON, "Customer Cancellation");
clickXpath( CANCEL_BTN,"CANCEL_BTN");
verify("(//*[contains(.,'Successfully')])[20]","Successfully");
clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
childTest.pass("LPN Level Cancellation has been completed" + "The part Name :   "+partnv);
clickXpath(INBOUND, "Inbound");
clickXpath(BINNING_GRID, "Binning");
Thread.sleep(1000);
enterXpath(SEARCH_BOX, CustReference,"BINNING SEARCH");
verify("//*[contains(text(),'0-0 of 0')]");	
childTest.log(Status.PASS,"Order is removed from Binning screen,Alert Box : ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
logOut();
System.out.println("cancellation live issue");
	}

	
	@Test(priority = 11,alwaysRun=true)
	public void Live_14119() throws IOException, Exception {

		partnv = "NVPARTLIVE";
		picktype = "FIFOSUGGESTED";

		parantTest = extent.createTest("LIVE_14119 : Unable to Do picking-Validation shows LPN not available in Stock");

		loginAsUser(Username);

		part = new String[] { partnv };
		quantity = new String[] { "8" };
		binPickPartquantity = new int[] { 8 };
		binPickPart = new String[] { partnv };
		writeDataForCSV(picktype, "Newdelpoint", "so", part, quantity);
		pickgenerationFIFO();

		pickerassign1(SOCustReference);

		ArrayList<String> picked = pickingScreenFS("", SOCustReference, Arrays.asList(binPickPart),
				binPickPartquantity);
		Thread.sleep(2000);
		clickIDLinkText("Misc");

		clickIDLinkText("Cancellation");
		ArrayList<String> NVLPN_LIVE = new ArrayList<String>();
		NVLPN_LIVE.add(NVLPNpicking.get(0));
		NVLPN_LIVE.add(NVLPNpicking.get(1));
		NVLPN_LIVE.add(NVLPNpicking.get(2));
		NVLPN_LIVE.add(NVLPNpicking.get(3));
		NVLPN_LIVE.add(NVLPNpicking.get(4));
		for (String LPN : NVLPN_LIVE) {
			childTest = parantTest.createNode("LPN Level Cancellation  ");
			childTest.info("LPN which have to cancel : " + LPN);
			cancellation(SOCustReference, "", LPN, 0, 3);
			childTest.pass("LPN level cancellation has been performed successfully");
		}

		vehicleCreation(site);
		quantity = new String[] { "5" };
		binPickPartquantity = new int[] { 5 };
		writeDataForCSV(picktype, "Newdelpoint", "po", part, quantity);
		childTest = parantTest.createNode("PO Order creation");
		clickIDLinkText("Inbound");
		clickIDLinkText(DASHBOARD);
		driver.navigate().refresh();
		childTest.info("Vehicle number is :  " + VEHICLENUMBER);
		verify(GATEIN_1stROW);
		enterXpath(SEARCH_BOX, VEHICLENUMBER, "Dock in searchbox");
		VehicleID = xpathGetText(GATEIN_1stROW);
		clickXpath("//*[@id='DashboardPO_Table']/td[5]/span/img", "Vehicle number");
		clickXpath(DOCKIN_BTN, "DOCKIN_BTN");

		uploadFIFO("1");
		childTest = parantTest.createNode("PO Order creation forLive-13501");
		shortWait(2000);
		verify(FINISH_UPLOAD_BTN);

		clickXpath(FINISH_UPLOAD_BTN, "FINISH_UPLOAD_BTN");
		shortWait(2000);
		childTest.pass("Order upload finished ");
		assignpod(PodUser);
		podGeneration();
		binnerAssignment(CustReference, Binner);
		binning("", CustReference, Arrays.asList(new String[] { partnv }), new int[] { 5 });

		clickIDLinkText(BINNERASSIGNMENT_MENU);
		enterXpath(SEARCH_BOX, SOCustReference, "binnerAssignment SEARCH");
		clickXpath(SEARCH_BTN, "SEARCH_BTN");
		childTest.pass("After postpick cancellation the order is available in Binner assignment.");
		clickXpath(BinnerAssignmentInfo, "PostPickCancellation");

		Thread.sleep(5000);

		WebElement PostPick = driver.findElement(By.xpath("//*[@id='simple-popper']/div/p/div[2]"));
		String PostPick_VON = PostPick.getText().trim();
		System.out.println("After PostPick Cancellation : " + PostPick_VON);
		if (PostPick_VON.contains(VON)) {
			System.out.println("Both VON are matched");
			childTest
					.info("The Sales Order VON is available in binner assignment screen after Post Pick Cancellation.");
			childTest.info(PostPick_VON);

		} else {
			System.out.println("Both VON are different");
		}
		clickXpath("//*[@id='simple-popper']/div/p/div[1]/div/span", "CloseBinnerAssignmentInfo");
		childTest.info("The order available in Binner assignment screen");

		clickXpath("//*[@id='BinnerAssignment_checkbox']", "BINNERASSIGN_CHECK_BOX");

		Thread.sleep(1000);
		clickXpath(ASSIGN_BINNER_BTH, "ASSIGN_BINNER_BTH");

		verify(ASSIGNBINNER);
		enterXpath(PICKERINPUT, Binner, "PICKER INPUT");
		nameClick(Binner);

		resize(1382, 650);

		clickXpath(ASSIGNBINNER, "ASSIGNBINNER");
		Thread.sleep(2000);
		verify(ALERTBOXCANCEL);
		clickXpath(ALERTBOXCANCEL, "ALERTBOXCANCEL");
		childTest.pass("Binner Assigned Successfully." + "The Binner is : " + Binner);

		binning("", SOCustReference, Arrays.asList(new String[] { partnv }), new int[] { 1 });
		driver.navigate().refresh();
		binning("", SOCustReference, Arrays.asList(new String[] { partnv }), new int[] { 1 });
		driver.navigate().refresh();
		binning("", SOCustReference, Arrays.asList(new String[] { partnv }), new int[] { 1 });
		driver.navigate().refresh();
		binning("", SOCustReference, Arrays.asList(new String[] { partnv }), new int[] { 1 });
		driver.navigate().refresh();
		binning("", SOCustReference, Arrays.asList(new String[] { partnv }), new int[] { 1 });
		driver.navigate().refresh();

		writeDataForCSV(picktype, "Newdelpoint", "so", part, quantity);
		pickgenerationFIFO();

		pickerassign1(SOCustReference);

		ArrayList<String> pickedAfterPostPick = pickingScreenFS("", SOCustReference, Arrays.asList(binPickPart),
				binPickPartquantity);
		logOut();

	}
	
	@Test(priority=12,alwaysRun=true)
	public void jch_14100() throws IOException, Exception {

		NVLPNpicking=new  ArrayList<String>();
		Lpn = new HashMap<String ,String>();
		parantTest = extent.createTest("Liveissue-14100(JCH): Unable to Do Binning");    
		partnv="VD-14100";
		picktype="FIFOSUGGESTEDCUSTLPN";

		part=new String[]{partnv,partnv,partnv,partnv,partnv,partnv,partnv,partnv,partnv,partnv};
		quantity=new String[]{"1","1","1","1","1","1","1","1","1","1"};
		binPickPart=new String[]{partnv};
		binPickPartquantity=new int[]{10};
		loginAsUser(Username);
		vehicleCreation(site);

		writeDataForCSV(picktype,"","po", part,quantity);


		childTest = parantTest.createNode("PO Order creation for FifoSuggested with CustLPN picktype _ JCH");
		clickIDLinkText(DASHBOARD);
		driver.navigate().refresh();
		childTest.info("Vehicle number is :  " + VEHICLENUMBER);
		//verify(GATEIN_1stROW);
		enterXpath(SEARCH_BOX, VEHICLENUMBER,"Dock in searchbox");
		fluentWait(GATEIN_1stROW);
		VehicleID = xpathGetText(GATEIN_1stROW);
		
		clickXpath(VEHICLE1,"Vehicle number");
		clickXpath(DOCKIN_BTN,"DOCKIN_BTN");

		uploadFIFO("1");

		childTest.log(Status.PASS,"The uploaded PO parts parts details for FifoSuggested with CustLPN Picktype _ JCH: ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		verify(FINISH_UPLOAD_BTN);
		clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
		shortWait(2000);
		childTest.pass("Order upload finished for FifoSuggested with CustLPN Picktype _ JCH");
		assignpod(PodUser);
		podGeneration();

		binnerAssignment(CustReference,Binner);
	
		binning("",CustReference,Arrays.asList(new String[]{partnv}),  binPickPartquantity);
		childTest.pass("Binning Completed for for FifoSuggested with CustLPN Picktype _ JCH");
		picktype="FIFOSUGGESTEDCUSTLPNAPI";
		part=new String[]{partnv};
		quantity=new String[]{"10"};

		writeDataForCSV(picktype,"intersite","so", part,quantity);
		verify(OUTBOUND);

		clickXpath("//b[text()='Outbound']","OUTBOUND");
		verify(PICKGENERATION);
		clickIDLinkText("Pick Generation");

	
		pickerassign1(SOCustReference);
		ArrayList<String>picked=pickingScreenFS("",SOCustReference,Arrays.asList(binPickPart),binPickPartquantity);

		NVLPNRETURN=new ArrayList<String>(NVLPNpicking);
		last = NVLPNRETURN.get(NVLPNRETURN.size() - 1);
		last1 = NVLPNRETURN.get(NVLPNRETURN.size() - 2);
		last2 = NVLPNRETURN.get(NVLPNRETURN.size() - 3);
		System.out.println("NV lpns are "+last +last1 +last2);
		skipConsolidation(SOCustReference);
		disp(SOCustReference,"",picked);
		Return_PO();
		shortWait(2000);
		logOut();
		CustReference=SOCustReference;
	    site=prop.getProperty("site2");
	    binPickPartquantity=new int[]{7};
		loginAsUser(prop.getProperty("USERNAME1"));
		vehicleCreation(site);
		childTest = parantTest.createNode("Scan PACKING SLIP ");
		clickIDLinkText(DASHBOARD);
		driver.navigate().refresh();
		childTest.info("Vehicle number is :  " + VEHICLENUMBER);
		verify(GATEIN_1stROW);
		enterXpath(SEARCH_BOX, VEHICLENUMBER,"Dock in searchbox");
		fluentWait(VEHICLE1);
		clickXpath(VEHICLE1,"Vehicle number");
		clickXpath(DOCKIN_BTN,"DOCKIN_BTN");
		ScanPackingSlip();
		shortWait(2000);
		poquantity= xpathGetText("//*[@id='DockIn_Quantity']");
		childTest.log(Status.PASS,"The Scanned PO parts  details : ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
		shortWait(2000);
		Binner=prop.getProperty("POD_USER1");
		PodUser=prop.getProperty("POD_USER1");

		childTest.log(Status.PASS,"Scanned succesfully: ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		childTest.pass("packing slip scanned ");
		assignpod(PodUser);
		podGeneration();
		binnerAssignment(CustReference,Binner);
		if(binPickPartquantity.equals(poquantity))
		{
		binning("",CustReference,Arrays.asList(new String[]{partnv}), binPickPartquantity);
		childTest.pass("Intersite Binning Completed for remaining quantity");
		}
		else
		{
			childTest.fail("Quantity mismatch in Binning Screen");
			System.out.println("Quantity showing wrongly in Binning Screen");
		}
		shortWait(2000);
		logOut();

	}
	public void Return_PO() throws Exception{
		partnv="VD-14100";
		picktype="FIFOSUGGESTEDCUSTLPN";

		binPickPart=new String[]{partnv};
		binPickPartquantity=new int[]{3};
		vehicleCreation(site);
		childTest = parantTest.createNode("Return Order creation for fifo Suggested with CustLPN picktype");
		clickXpath(INBOUND, "Inbound");
		clickIDLinkText(DASHBOARD);
		driver.navigate().refresh();
		childTest.info("Vehicle number is :  " + VEHICLENUMBER);
		//verify(GATEIN_1stROW);
		enterXpath(SEARCH_BOX, VEHICLENUMBER,"Dock in searchbox");
		fluentWait(GATEIN_1stROW);
		VehicleID = xpathGetText(GATEIN_1stROW);
		clickXpath(VEHICLE1,"Vehicle number");
		clickXpath(DOCKIN_BTN,"DOCKIN_BTN");
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

		clickXpath(SCAN_PART, "SCAN PART RADIO BTN");
		shortWait(1000);
		
				enterXpath(SCAN_LPN, last,"LPNFIELD");
				shortWait(1000);
				clickXpath(POSCAN_BTN, "SCANBTN");
				shortWait(1000);
				clickXpath(PO_OK, "OKBTN");
				shortWait(1000);
				enterXpath(SCAN_LPN, last1,"LPNFIELD");
				shortWait(1000);
				clickXpath(POSCAN_BTN, "SCANBTN");
				shortWait(1000);
				clickXpath(PO_OK, "OKBTN");
				shortWait(1000);
				enterXpath(SCAN_LPN, last2,"LPNFIELD");
				shortWait(1000);
				clickXpath(POSCAN_BTN, "SCANBTN");
				shortWait(1000);
				clickXpath(PO_OK, "OKBTN");
				shortWait(2000);

	
		clickXpath(PO_SAVE, "SAVEBTN");
		shortWait(1000);
		clickXpath(RETURN_YES, "YES_BTN");
		shortWait(1000);

		Assert.assertTrue(isElementPresent(RETURN_CLOSE_BTN));
		childTest.log(Status.PASS,"Alertbox : ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		clickXpath(RETURN_CLOSE_BTN, "CLOSE_BTN");
		shortWait(1000);
		poquantity= xpathGetText("//*[@id='DockIn_Quantity']");
	
	
		Thread.sleep(2000);
		JavascriptExecutor js1 = (JavascriptExecutor) driver;

		js1.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
		verify(RETURN_COMPLETE_BTN);
		childTest.log(Status.PASS,"The uploaded Return Order details for fifosuggested Picktype: ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		clickXpath(RETURN_COMPLETE_BTN,"COMPLETE_BTN");
		shortWait(2000);

		childTest.pass("Order upload finished for fifo suggested Picktype _ Return order");
	
		assignpod(PodUser);
		podGeneration();
		binnerAssignment(SOCustReference,Binner);
	

		binning("",SOCustReference,Arrays.asList(new String[]{partnv}), binPickPartquantity);

		System.out.println("return order binning  completed");
	
	}

}
