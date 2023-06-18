package testScripts;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

public class Cancellation_Outbound extends ProjectSpecificFunctions{

	int PARTIALNVNU = 1;
	public void sales() throws Exception
	{
		partnv="PARTNVFIFO";
		partnvnu="PARTNVNUFIFO";
		part=new String[]{partnv,partnvnu};
		picktype="FIFOCANCEL2";
		//SOCustReference=readXL("so", 1, "QUANTITY");
		quantity=new String[]{"2","2"};	
		writeDataForCSV(picktype,"Newdelpoint","so", part,quantity);
		NVNUQtn=readXL("so", 1, "QUANTITY");
		NvQtn=readXL("so", 0, "QUANTITY");
		System.out.println(CustReference1);

	}

	@Test(priority=0)
	public void OUT_Cancellation_pickgenerationScreen() throws Throwable {
		parantTest = extent.createTest("Outbound_cancellation pre and post picking");

		loginAsUser(Username);

		childTest = parantTest.createNode("Cancellation at pickgeneration screen" );

		ArrayList<String> so = new ArrayList<String>();
		ArrayList<String> von = new ArrayList<String>();

		clickXpath(OUTBOUND);
		Thread.sleep(3000);
		explicitWaitXpathClick(20,PICKGENERATION);

		//	for (int i = 0; i <= 1; i++) {
		sales();

		partStockCheck(partnvnu,  "bib",2);
		partStockCheck(partnv,  "bing",2);
		//randomizerSOCancel("SO");
		//	readexcel(prop.getProperty("SOPATH"));
		//soreadDataCancel();
		File sofile = new File(prop.getProperty("SOPATH"));
		//shortWait(3000);
		clickXpath(CREATEREQUEST);
		clickXpath(ACCOUNTSELECT);
		actionEnter();
		//verify("(//*[contains(.,'OTIS INDIA')])[16]");
		enterXpath( CHOOSEFILE, sofile.getAbsolutePath());
		//shortWait(2000);
		//System.out.println(SOCustReference);
		//Thread.sleep(2000);
		clickXpath( UPLOADSO);
		Thread.sleep(3000);
		CommonFunctions.childTest.log(Status.PASS,"File upload status",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build());
		//verify(ALERTBOXCANCEL);
		fluentWait(ALERTBOXCANCEL);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		partStockCheck(partnvnu,  "pickgen",4);
		partStockCheck(partnv,  "pickgen",4);
		//Thread.sleep(3000);
		//verify(CLICKRECORD);
		System.out.println(CustReference1);
		for(String SOCustReference1:CustReference1 )
		{
			enterXpath( MASTERSEARCH, SOCustReference1);
			actionEnter();
			verify("(//*[contains(.,'1-1 of 1')])[17]");

			//shortWait(2000);

			String von1 = fluentWait("(//td[@id='PickGen_VON'])[1]").getText();
			childTest.pass("SO Uploaded Successfully.   : " + " Order Customer reference is :" + SOCustReference  + von1+"<a href="+ sofile.getAbsolutePath()+">CSV</a>");

			von.add(von1);
			so.add(SOCustReference1);

			//System.out.println(von1);
			//	driver.navigate().refresh();

			orderStatus(SOCustReference1, Module.SOCREATION);
			clear1(MASTERSEARCH);
		}
		//	}


		//
		clickIDLinkText("Misc");
		clickIDLinkText("Cancellation");


		childTest = parantTest.createNode("Order Level cancellation- At pick generation screen");
		childTest.info("Order level cancellation initiated Cust Ref is  : " + so.get(0));
		verify(CANCEL_LEVEL);
		selectByvalue(CANCEL_LEVEL, 1);

		enterXpath( CANCEL_VONSEARCH, von.get(0),"CANCEL_VONSEARCH");

		enterXpath( CANCEL_SOSEARCH, so.get(0),"CANCEL_SOSEARCH");

		actionEnter();
		//	shortWait(2000);
		childTest.info("The Order is available in order level cancellation screen. Cust Ref is  : " + so.get(0)  +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());
		//Thread.sleep(2000);
		//verify("(//*[contains(.,'OUT')])[19]");

		clickXpath( CANCEL_PART_CHECKBOX);

		selectByVisibleText(CANCEL_REASON, "Customer Cancellation");
		clickXpath( CANCEL_BTN,"Cancel Btn");
		verify("(//*[contains(.,'Successfully')])[20]","Successfully");
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		partStockCheck(partnvnu,  "precancel",2);

		partStockCheck(partnv,  "precancel",2);
		childTest.pass("Order Level Cancellation has been completed for Cust Ref : " + so.get(0));

		clear1(CANCEL_VONSEARCH);
		clear1(CANCEL_SOSEARCH);

		for (int i = 1; i <= 4; i++) {
			selectByvalue(CANCEL_LEVEL, i);
			//shortWait(2000);
			enterXpath( CANCEL_VONSEARCH, von.get(0),"CANCEL_VONSEARCH");
			//	shortWait(2000);
			enterXpath( CANCEL_SOSEARCH, so.get(0),"CANCEL_SOSEARCH");
			//Thread.sleep(2000);
			actionEnter();
			//Thread.sleep(2000);
		}

		childTest.info("Verification complete the cancelled order not listed in the cancellation screen" +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());



		childTest = parantTest.createNode("Quantity Level cancellation- At pick generation screen");
		childTest.info("Quantity level cancellation initiated Cust Ref is  :  " + so.get(1));
		clear1(CANCEL_VONSEARCH);
		clear1(CANCEL_SOSEARCH);
		selectByvalue(CANCEL_LEVEL, 4);
		//	Thread.sleep(2000);
		enterXpath( CANCEL_VONSEARCH, von.get(1),"CANCEL_VONSEARCH");
		//shortWait(2000);
		enterXpath( CANCEL_SOSEARCH, so.get(1),"CANCEL_SOSEARCH");
		//shortWait(2000);
		enterXpath( CANCEL_PARTSEARCH, partnvnu,"CANCEL_PARTSEARCH");
		//shortWait(2000);
		actionEnter();
		//verify("(//*[contains(.,'OUT')])[19]");

		//shortWait(2000);
		childTest.info("The Order is available in Quantity level cancellation screen. Cust Ref is  : " + so.get(1)  +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());
		childTest.info("The Requested Quantity is  :  "+ prop.getProperty("NVNUCANCELQTY")  + "The Cancelled Quantity is " + prop.getProperty("NVNUCANCELQTY"));
		enterXpath( CANCEL_QTY_ENTER, prop.getProperty("NVNUCANCELQTY"));
		//	Thread.sleep(2000);
		selectByVisibleText(CANCEL_REASON, "Customer Cancellation");
		//Thread.sleep(2000);
		clickXpath( CANCEL_BTN,"Cancel Btn");
		verify("(//*[contains(.,'Successfully')])[20]","Successfully");
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		partStockCheck(partnvnu,  "precancel",1);

		childTest.pass("Quantity Level Cancellation has been completed for Cust Ref : " + so.get(1)
		+ " part number is : PARTNVNUFIFO");



		childTest = parantTest.createNode("Part Level cancellation- At pick generation screen");
		childTest.info("Part level cancellation initiated Cust Ref is  :" + so.get(1));
		selectByvalue(CANCEL_LEVEL, 2);
		enterXpath( CANCEL_VONSEARCH, von.get(1));
		enterXpath( CANCEL_SOSEARCH, so.get(1));
		enterXpath( CANCEL_PARTSEARCH, partnv);
		actionEnter();
		childTest.info("The Order Part is available in Part level cancellation screen. Cust Ref is  : " + so.get(1)  +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());
		Thread.sleep(2000);
		String partType = driver.findElement(By.xpath(CANCELNVPART)).getText();

		clickXpath(CANCELNVPARTSELECT,"check box nvnu");



		// clickXpath(CANCEL_PART_CHECKBOX);
		//	Thread.sleep(2000);
		selectByVisibleText(CANCEL_REASON, "Customer Cancellation");
		//	Thread.sleep(2000);
		clickXpath( CANCEL_BTN,"Cancel Btn");
		verify("(//*[contains(.,'Successfully')])[20]","Successfully");
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");

		partStockCheck(partnv,  "precancel",2);
		childTest.pass(
				"Part Level Cancellation has been completed for Cust Ref : " + so.get(1) + " part number is : PARTNVFIFO");


		for (int i = 1; i <= 4; i++) {
			selectByvalue(CANCEL_LEVEL, i);
			//shortWait(2000);
			enterXpath( CANCEL_SOSEARCH, so.get(1));
			//shortWait(2000);
			actionEnter();
		}
		childTest.info("Verification complete the cancelled part not listed in the cancellation screen" +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());


		clickXpath(OUTBOUND);
		explicitWaitXpathClick(20,PICKGENERATION);

		childTest = parantTest.createNode(
				"Picking,Consolidation,Dispatch against Cancellation performed Order. customer ref. " + so.get(1));
		verify(CLICKRECORD);

		enterXpath(MASTERSEARCH, so.get(1));
		clickXpath(PICKGEN_SEARCH, "PICKGEN_SEARCH");
		//verify("(//*[contains(.,'1-1 of 1')])[17]");

		clickXpath(SELECTPART,"Checkbox");

		//Thread.sleep(3000);
		clickXpath(PICKERASSIGN);
		verify(PICKERSUBMIT);

		enterXpath(PICKERINPUT, prop.getProperty("PICKER"),"PICKERINPUT");
		nameClick(prop.getProperty("PICKER"));


		clickXpath(PICKERSUBMIT,"PICKERSUBMIT");
		fluentWait(ALERTBOXCANCEL);
		Thread.sleep(2000);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		//	driver.navigate().refresh();

		childTest.pass("Picker Assigned Successfully." + "Picker is : " + prop.getProperty("PICKER"));
		// PICKING Screen
		orderStatus(so.get(1), Module.PICKASSIGN);


		clickIDLinkText("Picking");
		enterXpath( PICKINGSEARCH, so.get(1));
		//Thread.sleep(1000);
		actionEnter();
		//Thread.sleep(2000);
		String PartType = xpathGetText(PARTTYPE);
		//Thread.sleep(2000);
		clickXpath( PARTTYPE);
		//Thread.sleep(2000);
		if (PartType.equals("NVNU")) {

			enterXpath( VQUANTITY, "1");
			clickXpath( SUBMITBTN);
			shortWait(3000);
			clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		}
		partStockCheck(partnvnu,  "picking",1);
		orderStatus(so.get(1), Module.PICKING);
		childTest.pass("picking has been completed for part number PARTNVNUFIFO.  : " + "The Picked Quantity is  : 1 " );

		// optimization_skip
		clickXpath(CONSOLIDATIONGRID,"CONSOLIDATIONGRID");
		verify(CONSOLIDATION);

		enterXpath(MASTERSEARCH1, so.get(1),"MASTER SEARCH");
		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
		actionEnter();
		Thread.sleep(4000);
		clickXpath(ORDERCLICK);
		childTest.info("The Order is available in consolidation screen. " );
		Thread.sleep(1000);

		clickXpath(CONSOLIDATIONSKIP);
		//switchWindow();
		Thread.sleep(4000);
		clickXpath(CONSOLIDATIONYES);
		Thread.sleep(3000);
		//closeBrowserTab();
		// switchWindow();
		clickXpath(ALERT);

		childTest.pass("Order skipped successfully in consolidation screen. ");

		orderStatus(so.get(1), Module.CONSOLIDATION);
		// Dispatch
		childTest = parantTest.createNode(" Order Dispatch ");
		clickXpath(DISPATCHGRID,"DISPATCHGRID");
		verify("(//*[@id='Despatch_REQUESTNO'])[1]");
		enterXpath(DISPATCHSEARCH, so.get(1),"DISPATCHSEARCH");
		Thread.sleep(3000);
		clickXpath(Dispatch1,"Dispatch1");
		//verify(DISPATCHBUTTON);

		Thread.sleep(2000);
		clickXpath(DISPATCHBUTTON,"Dispatch1");


		//clickXpath(DISPATCHBTN);
		//Thread.sleep(1000);
		childTest.info("The Order is available in Dispatch screen. " + MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());

		verify("(//*[contains(.,' / ')])[23]","1");

		enterXpath(CARRIER, "Testing");
		//Thread.sleep(100);
		enterXpath(CARRIERREF, "SmokeTest");
		//Thread.sleep(100);
		//enterXpath(VEHICLE, "TN38AA1234");
		//Thread.sleep(100);
		enterXpath(SCANLPN, partnvnu);
		//Thread.sleep(2000);
		actionEnter();
		enterXpath("//input[@id='Despatch_enterQty1']", "1");
		actionEnter();
		actionEnter();
		Thread.sleep(2000);
		Thread.sleep(1000);
		childTest.pass("Dispatch has been completed Successfully. ");

		Thread.sleep(3000);
		enterXpath( DISPATCHSEARCH, so.get(1),"DISPATCHSEARCH");
		shortWait(2000);
		actionEnter();
		orderStatus(so.get(1), Module.DISPATCH);
	}
	//@Test(priority=0)
	@Test(dependsOnMethods = { "OUT_Cancellation_pickgenerationScreen" })
	public void OUT_Cancellation_Orderisin_PickerDashboardScreen() throws Exception, Throwable {
		
		// driver.manage().window().maximize();
		ArrayList<String> so = new ArrayList<String>();
		ArrayList<String> von = new ArrayList<String>();
		ArrayList<String> lpn = new ArrayList<String>();
		childTest = parantTest.createNode("Cancellation at Picking Dashboard - PrePicking" );
		//	clickXpath(OUTBOUND);
sales();
		explicitWaitXpathClick(20,PICKGENERATION);
		// pickgeneration();

		//	for (int i = 0; i <= 1; i++) {
		//System.out.println("return succeess");
		partStockCheck(partnvnu,  "drg",2);

		partStockCheck(partnv,  "sef",2);
		//randomizerSOCancel("SO");
		//	ReadExcel(prop.getProperty("SOPATH"));
		//soreadDataCancel();
		NVNUQUTY = Integer.parseInt(NVNUQtn);
		System.out.println(NVNUQUTY);
		PARTIALNVNU = NVNUQUTY / 2;



		explicitWaitXpathClick(20,PICKGENERATION);
		clickXpath( CREATEREQUESTMENU);
		clickXpath(ACCOUNTSELECT);
		actionEnter();
		File file = new File(prop.getProperty("SOPATH"));

		//verify("(//*[contains(.,'OTIS INDIA')])[16]");
		enterXpath(CHOOSEFILE,file.getAbsolutePath(),"CHOOSEFILE");
		//soreadDataCancel();
		// System.out.println("Hai");
		//Thread.sleep(2000);
		clickXpath( UPLOADSO);

		//windowHandle();
		verify(ALERTBOXCANCEL);
		fluentWait(ALERTBOXCANCEL);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		partStockCheck(partnvnu,  "pickgen",2);

		partStockCheck(partnv,  "pickgen",2);
		// pickerassign
		childTest.pass("SO Uploaded Successfully for Order Customer reference is :" + SOCustReference);
		for(String SOCustReference1:CustReference1 )
		{
			orderStatus(SOCustReference1, Module.SOCREATION);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			verify(CLICKRECORD);
			enterXpath( MASTERSEARCH, SOCustReference1);
			//shortWait(2000);
			actionEnter();
			//	Thread.sleep(2000);
			clickXpath( CLICKRECORD);
			//Thread.sleep(2000);
			clickXpath( CLICKBACK);
			//shortWait(2000);
			verify(CLICKRECORD);
			enterXpath( MASTERSEARCH, SOCustReference1);
			//shortWait(2000);
			clickXpath(PICKGENSEARCH, "search");
			//verify("(//*[contains(.,'1-1 of 1')])[17]");
			clickXpath(SELECTPART);
			clickXpath(PICKERASSIGN);
			String von1 = xpathGetText(CANCEL_VONTEXT);
			childTest.pass("SO Uploaded Successfully.   : " + " Order Customer reference is :" + SOCustReference  + von1);				
			so.add(SOCustReference1);
			von.add(von1);

			verify(PICKERSUBMIT);
			enterXpath(PICKERINPUT, prop.getProperty("PICKER"),"PICKERINPUT");
			nameClick(prop.getProperty("PICKER"));


			clickXpath(PICKERSUBMIT,"PICKERSUBMIT");
			Thread.sleep(3000);
			clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
			// driver.navigate().refresh();
			childTest.pass(" picker assigned Successfully for  Customer reference is : " + SOCustReference);
			orderStatus(SOCustReference1, Module.PICKASSIGN);
		}


		childTest = parantTest.createNode("Order Level cancellation- At picking screen");
		childTest.info("Order level cancellation initiated for Cust Ref is  :" + so.get(0));
		//shortWait(2000);
		clickIDLinkText(MISC_MENU);
		//Thread.sleep(2000);
		clickIDLinkText("Cancellation");
		// ------------------------------------------order level
		// cancellation for order1
		//Thread.sleep(2000);
		verify(CANCEL_LEVEL);

		selectByvalue(CANCEL_LEVEL, 1);

		enterXpath( CANCEL_VONSEARCH, von.get(0));
		//shortWait(2000);
		actionEnter();
		//shortWait(2000);
		childTest.info("The Order is available in order level cancellation screen. Cust Ref is  : " + so.get(0)  +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());
		//verify("(//*[contains(.,'OUT')])[19]");
		clickXpath( CANCEL_PART_CHECKBOX);
		//Thread.sleep(2000);
		selectByVisibleText(CANCEL_REASON, "Customer Cancellation");
		//Thread.sleep(2000);
		clickXpath( CANCEL_BTN,"Cancel Btn");
		verify("(//*[contains(.,'Successfully')])[20]","Successfully");
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		partStockCheck(partnvnu,  "precancel",2);

		partStockCheck(partnv,  "precancel",2);
		childTest.pass("Order level cancellation completed for Cust Ref is  :" + so.get(0));
		clear1(CANCEL_VONSEARCH);
		clear1(CANCEL_SOSEARCH);


		// validation

		for (int i = 1; i <= 4; i++) {
			selectByvalue(CANCEL_LEVEL, i);


			enterXpath( CANCEL_SOSEARCH, so.get(0));


		}
		childTest.pass("Verification has been completed for Order level. " +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());

		clickXpath(OUTBOUND);
		clickIDLinkText( "Picking");
		//shortWait(2000);
		enterXpath( PICKINGSEARCH, von.get(0));
		//shortWait(2000);
		enterXpath( PICKINGSOSEARCH, so.get(0));
		//shortWait(2000);
		actionEnter();
		// ------------------------------------------quantity level cancellation
		// for order2
		childTest = parantTest.createNode("Quanitity Level cancellation- At picking screen");
		childTest.info("Quantity level cancellation initiated. Cust Ref is  :" + so.get(1));

		//shortWait(2000);
		clickIDLinkText(MISC_MENU);
		//shortWait(2000);
		clickIDLinkText("Cancellation");
		//shortWait(2000);

		selectByvalue(CANCEL_LEVEL, 4);

		enterXpath( CANCEL_VONSEARCH, von.get(1));
		//	shortWait(2000);
		enterXpath( CANCEL_PARTSEARCH, partnvnu);
		//shortWait(2000);
		actionEnter();
		//verify("(//*[contains(.,'OUT')])[19]");
		childTest.info("The Order is available in Quantity level cancellation screen. Cust Ref is  : " + so.get(1)  +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());
		childTest.info("The Requested Quantity is  :  "+ prop.getProperty("NVNUCANCELQTY")  + "The Cancelled Quantity is " + prop.getProperty("NVNUCANCELQTY"));
		//Thread.sleep(2000);
		enterXpath( CANCEL_QTY_ENTER, prop.getProperty("NVNUCANCELQTY") );
		//Thread.sleep(2000);
		selectByVisibleText(CANCEL_REASON, "Customer Cancellation");
		//Thread.sleep(2000);


		clickXpath( CANCEL_BTN,"Cancel Btn");
		verify("(//*[contains(.,'Successfully')])[20]","Successfully");	
		fluentWait(ALERTBOXCANCEL);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		partStockCheck(partnvnu,  "precancel",PARTIALNVNU);

		childTest.pass(" Quantity level cancellation completed. Cust Ref is  :" + so.get(1));


		// ------------------------------------------part level cancellation for
		// order2
		childTest = parantTest.createNode("Part Level cancellation- At picking screen");
		childTest.info("Part level cancellation initiated. part type are NVNU Cust Ref is  : " + so.get(1));


		selectByvalue(CANCEL_LEVEL, 2);
		// childTest.pass(" Part level cancellation initiated for Customer
		// reference : "+von.get(1));
		//		shortWait(2000);
		enterXpath( CANCEL_VONSEARCH, von.get(1));
		//	shortWait(2000);
		enterXpath( CANCEL_SOSEARCH, so.get(1));
		//shortWait(2000);
		enterXpath( CANCEL_PARTSEARCH, partnv);
		//shortWait(2000);
		actionEnter();
		//verify("(//*[contains(.,'OUT')])[19]");
		childTest.info("The Order Part is available in Part level cancellation screen. Cust Ref is  : " + so.get(1)  +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());
		//Thread.sleep(2000);
		String partType = xpathGetText(CANCELNVPART);

		/*	if (partType.equals(partnvnu)) {

			clickXpath(CANCELLATIONSELECTALL,"Checkbox all");
			shortWait(2000);*/
		clickXpath(CANCELNVPARTSELECT,"check box nvnu");



		selectByVisibleText(CANCEL_REASON, "Customer Cancellation");
		//Thread.sleep(2000);

		clickXpath( CANCEL_BTN,"Cancel Btn");
		//verify("(//*[contains(.,'Successfully')])[20]","Successfully");
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		partStockCheck(partnv,  "precancel",2);

		childTest.pass(" Part level cancellation completed part type is NV and Cust Ref is  : " + so.get(1));


		childTest = parantTest.createNode("Picking,Consolidation,Dispatch against Cancellation performed Order. customer ref. " + so.get(1));
		//Thread.sleep(2000);
		clickXpath(OUTBOUND,"OUTBOUND");
		// clickXpath( OUTBOUNDMENU);
		//Thread.sleep(2000);
		clickIDLinkText("Picking");
		//shortWait(2000);
		enterXpath( PICKINGSEARCH, von.get(1),"PICKINGSEARCH");
		//shortWait(2000);
		enterXpath( PICKINGSOSEARCH, so.get(1),"PICKINGSOSEARCH");
		//shortWait(2000);
		actionEnter();
		//Thread.sleep(2000);
		String PartType =xpathGetText(PARTTYPE);
		//Thread.sleep(2000);

		clickXpath( PARTTYPE,"PARTTYPE");
		//Thread.sleep(2000);
		if (PartType.equals("NVNU")) {
			//verify("(//*[contains(.,'Please')])[16]","Please");
			enterXpath( SERIALNOFIELD, partnvnu,"lpn");
			//Thread.sleep(2000);
			//clickXpath( SUBMITBTN);
			Thread.sleep(1000);
			clear1(VQUANTITY);
			shortWait(2000);
			enterXpath( VQUANTITY,  "1");
			shortWait(2000);
			clickXpath( SUBMITBTN);
			//windowHandle();
			fluentWait(ALERTBOXCANCEL);
			clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
			// driver.navigate().back();
			partStockCheck(partnvnu,  "precancel",PARTIALNVNU);

			childTest.pass("Picker Assigned Successfully." + "Picker is : " + prop.getProperty("PICKER"));
			orderStatus(so.get(1), Module.PICKING);
			//	Thread.sleep(1000);
		}

		// -----Consolidation

		//Thread.sleep(2000);
		clickIDLinkText("Optimization");
		verify(CONSOLIDATION);
		enterXpath( CONSOLIDATIONSEARCH, von.get(1));
		//shortWait(2000);
		actionEnter();

		//	Thread.sleep(2000);
		//try{
		clickXpath( ORDERCLICK,"ORDERCLICK");
		//Thread.sleep(1000);
		childTest.info("The Order is available in consolidation screen. " );
		//	Thread.sleep(1000);

		clickXpath( CONSOLIDATIONSKIP,"CONSOLIDATIONSKIP");
		//switchWindow();
		//Thread.sleep(2000);
		clickXpath( CONSOLIDATIONYES,"CONSOLIDATIONYES");
		Thread.sleep(3000);
		//closeBrowserTab();
		Thread.sleep(3000);

		clickXpath( ALERT);
		childTest.pass("Order skipped successfully in consolidation screen. ");
		orderStatus(so.get(1), Module.CONSOLIDATION);

		// -----Dispatch
		clickXpath(DISPATCHGRID,"DISPATCHGRID");
		verify("(//*[@id='Despatch_REQUESTNO'])[1]");
		enterXpath(DISPATCHSEARCH, so.get(1),"DISPATCHSEARCH");
		Thread.sleep(3000);


		clickXpath(Dispatch1,"Dispatch1");
		clickXpath(DISPATCHBUTTON,"Dispatch1");
		//Thread.sleep(1000);
		childTest.info("The Order is available in Dispatch screen. " + MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());
		verify("(//*[contains(.,' / ')])[23]","1");

		enterXpath( CARRIER, "Testing");
		//shortWait(2000);
		childTest.info("The Mode of carrier is :" + "Testing");
		//shortWait(2000);
		enterXpath( CARRIERREF, "SmokeTest");
		//shortWait(2000);
		childTest.info("The carrier reference is :" + "SmokeTest");
		//shortWait(2000);
		//enterXpath( VEHICLE, "TN38AA1234");
		//shortWait(2000);
		//childTest.info("The Vehicle Number is :" + "TN38AA1234");
		//Thread.sleep(100);

		enterXpath( SCANLPN, partnvnu,"LPN");
		actionEnter();
		clear1("//input[@id='Despatch_enterQty1']");
		//shortWait(2000);
		enterXpath( "//input[@id='Despatch_enterQty1']", "1","qty");
		actionEnter();
		childTest.pass("Dispatch has been completed Successfully.");
		Thread.sleep(2000);
		//closeBrowserTab();
		Thread.sleep(3000);
		enterXpath( DISPATCHSEARCH, so.get(1),"DISPATCHSEARCH");
		shortWait(2000);
		actionEnter();

		orderStatus(so.get(1), Module.DISPATCH);
	}

	//@Test(priority=0)

	@Test(dependsOnMethods = { "OUT_Cancellation_Orderisin_PickerDashboardScreen" })
	public void OUT_Cancellation_DuringPartialpicking() throws Exception {
		
		ArrayList<String> so = new ArrayList<String>();
		ArrayList<String> von = new ArrayList<String>();
		ArrayList<String> lpn = new ArrayList<String>();
		childTest = parantTest.createNode("Cancellation at picking screen" );

		explicitWaitXpathClick(20,PICKGENERATION);
		CustRefList = new ArrayList<String>();

		//	for (int i = 0; i < 2; i++) {
		partStockCheck(partnvnu,  "df",3);
		partStockCheck(partnv,  "db",1);

		//	randomizerSOCancel("SO");
		//	ReadExcel(prop.getProperty("SOPATH"));
		//soreadDataCancel();
		sales();
		CustRefList.add(SOCustReference);
		String[] socusref = new String[SOCustReference.length()];
		CustRefList.toArray(socusref);

		clickXpath(CREATEREQUESTMENU);
		//Thread.sleep(5000);
		File file = new File(prop.getProperty("SOPATH"));
		//verify("(//*[contains(.,'OTIS INDIA')])[16]");
		clickXpath(ACCOUNTSELECT, "account");
		actionEnter();
		enterXpath(CHOOSEFILE,file.getAbsolutePath(),"CHOOSEFILE");

		clickXpath(UPLOADSO,"UPLOADSO");
		verify(ALERTBOXCANCEL);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		partStockCheck(partnvnu,  "pickgen",2);
		partStockCheck(partnv,  "pickgen",2);


		//driver.navigate().refresh();
		for(String SOCustReference1:CustReference1 )
		{
			childTest.pass("SO Uploaded Successfully." + "Order Customer reference is :" + SOCustReference1);

			orderStatus(SOCustReference1, Module.SOCREATION);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			verify(CLICKRECORD);
			enterXpath( MASTERSEARCH, SOCustReference1);
			//shortWait(2000);
			actionEnter();
			//	Thread.sleep(2000);
			clickXpath( CLICKRECORD);
			//Thread.sleep(2000);
			clickXpath( CLICKBACK);
			//shortWait(2000);
			verify(CLICKRECORD);
			enterXpath( MASTERSEARCH, SOCustReference1);
			//shortWait(2000);
			clickXpath(PICKGENSEARCH, "search");
			//verify("(//*[contains(.,'1-1 of 1')])[17]");
			clickXpath(SELECTPART);
			clickXpath(PICKERASSIGN);
			String von1 = xpathGetText(CANCEL_VONTEXT);
			childTest.pass("SO Uploaded Successfully.   : " + " Order Customer reference is :" + SOCustReference  + von1);				
			so.add(SOCustReference1);
			von.add(von1);

			verify(PICKERSUBMIT);
			enterXpath(PICKERINPUT, prop.getProperty("PICKER"),"PICKERINPUT");
			nameClick(prop.getProperty("PICKER"));


			clickXpath(PICKERSUBMIT,"PICKERSUBMIT");
			Thread.sleep(3000);
			clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
			// driver.navigate().refresh();
			childTest.pass(" picker assigned Successfully for  Customer reference is : " + SOCustReference);
			orderStatus(SOCustReference1, Module.PICKASSIGN);
		}

		// Checking order 1 in picking screen

		//String[] socusref = new String[SOCustReference.length()];
		CustRefList=so;
		//clickXpath(PICKINGGRID);
		clickIDLinkText("Picking");
		//Thread.sleep(3000);
		enterXpath(PICKINGSEARCH, so.get(0),"PICKINGSEARCH");
		actionEnter();
		childTest.pass("Checking order1 in picking screen.");

		// Order level
		// cancellation------------------------------------------------------------

		childTest = parantTest.createNode("Order Level cancellation- At picking Screen");
		childTest.info("Order level cancellation initiated. Cust Ref is  :" + socusref[0]);
		clickIDLinkText("Misc");
		clickIDLinkText("Cancellation");
		//clickXpath( CANCELLATIONAT);

		verify(CANCELLATIONAT);
		selectByVisibleText(CANCELLATIONAT, "Order Level");
		fluentWaitxpath(2, 1, CANCEL_SOSEARCH);
		enterXpath(CANCEL_SOSEARCH, so.get(0));
		//shortWait(2000);
		clickXpath(CANCELLATIONSEARCHBUTTON,"search");
		childTest.info("The Order is available in order level cancellation screen. Cust Ref is  : " + socusref[0]  +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());
		//verify("(//*[contains(.,'OUT')])[19]");
		fluentWaitxpath(2, 1, CANCELLATIONSELECTALL);
		clickXpath(CANCELLATIONSELECTALL,"Checkbox all");
		//shortWait(2000);
		selectByVisibleText(CANCELLATIONREASON, "Customer Cancellation");
		//shortWait(2000);
		clickXpath(CANCELLATIONCANCELORDERBUTTON,"Cancel Btn");
		//shortWait(3000);
		fluentWaitxpath(2, 1, CANCELLATIONPOPUPCLOSE);
		clickXpath(CANCELLATIONPOPUPCLOSE,"close");
		partStockCheck(partnvnu,  "precancel",2);
		partStockCheck(partnv,  "precancel",2);

		childTest.pass("Order level cancellation completed for Customer reference is : " + socusref[0]);
		//shortWait(3000);
		//driver.navigate().refresh();


		// Validations for Order level cancellation----------------------------
		clear1(CANCEL_SOSEARCH);
		for (int j = 1; j <= 4; j++) {

			//shortWait(1000);
			selectByvalue(CANCELLATIONAT, j);
			//shortWait(2000);
			enterXpath(CANCEL_SOSEARCH, so.get(0),"CANCEL_SOSEARCH");
			shortWait(2000);
			clickXpath(CANCELLATIONSEARCHBUTTON,"search");
			//shortWait(2000);

		}
		childTest.info("Verification complete the cancelled order not listed in the cancellation screen" +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());

		clickXpath(OUTBOUND,"OUTBOUND");
		clickIDLinkText("Picking");

		enterXpath(PICKINGSEARCH, so.get(0),"PICKINGSEARCH");
		actionEnter();
		childTest.info("Verification complete the cancelled order not listed in the picking screen. " +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());

		// Order 2 in picking screen for part Level
		// cancellation---------------------------
		clear1(PICKINGSEARCH);
		enterXpath(PICKINGSEARCH, so.get(1),"PICKINGSEARCH");
		actionEnter();
		childTest.info("order2 available in picking screen for NV and NVNU Part." +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());
		// Part Level Cancellation(NV
		// Part)-------------------------------------------

		childTest = parantTest.createNode("Part Level cancellation- At picking Screen");
		childTest.info("Part level cancellation initiated Cust Ref is  :" + socusref[1]);

		clickIDLinkText("Misc");
		clickIDLinkText("Cancellation");

		verify( CANCELLATIONAT);

		selectByVisibleText(CANCELLATIONAT, "Part Level");
		fluentWaitxpath(2, 1, CANCEL_SOSEARCH);
		enterXpath(CANCEL_SOSEARCH, so.get(1),"CANCEL_SOSEARCH");
		fluentWaitxpath(2, 1, CANCELLATIONPARTNUMTEXTBOX);
		enterXpath(CANCELLATIONPARTNUMTEXTBOX, partnv,"CANCELLATIONPARTNUMTEXTBOX");
		//shortWait(2000);
		clickXpath(CANCELLATIONSEARCHBUTTON,"search");
		//verify("(//*[contains(.,'OUT')])[19]");
		childTest.info("The Order Part is available in Part level cancellation screen. Cust Ref is  : " + socusref[1]  +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());


		String partType =xpathGetText(CANCELNVPART);

		/*	if (partType.equals(partnvnu)) {

			clickXpath(CANCELLATIONSELECTALL,"Checkbox all");
			shortWait(2000);*/
		clickXpath(CANCELNVPARTSELECT,"check box nvnu");

		//}
		//shortWait(2000);
		selectByVisibleText(CANCELLATIONREASON, "Customer Cancellation");
		//shortWait(2000);
		clickXpath(CANCELLATIONCANCELORDERBUTTON,"Cancel Btn");
		verify("(//*[contains(.,'Successfully')])[20]","Successfully");
		fluentWaitxpath(2, 1, CANCELLATIONPOPUPCLOSE);
		clickXpath(CANCELLATIONPOPUPCLOSE,"close");
		partStockCheck(partnv,  "precancel",2);

		childTest.pass(
				"Part Level cancellation has been completed for Part type is  NV and Cust Ref is  :" + socusref[1]);




		// Validations for part level cancellation----------------------------
		verify(CANCELLATIONAT);

		for (int k = 2; k <= 4; k++) {

			//	shortWait(1000);
			selectByvalue(CANCELLATIONAT, k);
			//shortWait(1000);
			clear1(CANCEL_SOSEARCH);
			clear1(CANCELLATIONPARTNUMTEXTBOX);
			enterXpath(CANCEL_SOSEARCH, so.get(1));

			fluentWaitxpath(2, 1, CANCELLATIONPARTNUMTEXTBOX);

			enterXpath(CANCELLATIONPARTNUMTEXTBOX, partnv);
			//shortWait(2000);
			clickXpath(CANCELLATIONSEARCHBUTTON,"search");
			//shortWait(2000);

		}

		childTest.info("Verification completed the cancelled part not listed in the cancellation screen." +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());
		// Checking order 2 in picking screen


		clickXpath(OUTBOUND,"OUTBOUND");
		clickIDLinkText("Picking");

		enterXpath(PICKINGSEARCH, so.get(1),"PICKINGSEARCH");
		actionEnter();
		//shortWait(3000);

		childTest.info("Verification completed the cancelled part not listed in the picking screen." +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());

		// Order 2 in picking screen for Quantity Level cancellation(NVNU
		// PART)---------------------------
		clear1(PICKINGSEARCH);
		enterXpath(PICKINGSEARCH, so.get(1),"PICKINGSEARCH");
		actionEnter();

		// Quantity Level Cancellation(NVNU Part)
		childTest = parantTest.createNode("Quantity Level cancellation- At picking Screen");
		childTest.info("Quantity level cancellation initiated Part type is  NVNU and Cust Ref is  :" + socusref[1]);
		clickIDLinkText("Misc");
		clickIDLinkText("Cancellation");
		verify(CANCELLATIONAT);

		selectByVisibleText(CANCELLATIONAT, "Quantity Level");
		//shortWait(3000);
		enterXpath(CANCELLATIONPOSOTEXTBOX, so.get(1),"CANCELLATIONPOSOTEXTBOX");
		//shortWait(3000);
		clickXpath(CANCELLATIONSEARCHBUTTON,"search");
		//shortWait(4000);
		//verify("(//*[contains(.,'OUT')])[19]");
		childTest.info("The Order is available in Quantity level cancellation screen. Cust Ref is  : " + socusref[1]  +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());
		childTest.info("The Requested Quantity is  :  "+ prop.getProperty("NVNUCANCELQTY")  + "The Cancelled Quantity is " + prop.getProperty("NVNUCANCELQTY"));
		enterXpath(CANCELLATIONVPARTQUANITYCANCEL, "1");
		//shortWait(3000);
		selectByVisibleText(CANCELLATIONREASON, "Customer Cancellation");
		//shortWait(3000);
		clickXpath(CANCELLATIONCANCELORDERBUTTON,"Cancel Btn");
		//verify("(//*[contains(.,'Successfully')])[20]","Successfully");
		fluentWaitxpath(10, 1, CANCELLATIONPOPUPCLOSE);
		clickXpath(CANCELLATIONPOPUPCLOSE,"close");
		//shortWait(3000);
		partStockCheck(partnvnu,  "precancel",1);

		childTest.pass(
				"Quantity level cancellation has been completed for NVNU part and Cust Ref is  :" + socusref[1]);
		//driver.navigate().refresh();


		// Validations for Quantity level
		// cancellation----------------------------

		for (int j = 1; j <= 4; j++) {

			//shortWait(1000);
			selectByvalue(CANCELLATIONAT, j);
			//shortWait(1000);
			clear1(CANCEL_SOSEARCH);

			enterXpath(CANCEL_SOSEARCH, so.get(1),"CANCEL_SOSEARCH");
			//shortWait(2000);
			clickXpath(CANCELLATIONSEARCHBUTTON,"search");
			//shortWait(2000);

		}
		childTest.info("Verification completed the cancelled Quantity not listed in the cancellation screen" +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());
		// Checking order 2 in picking screen for quantity
		// level-----------------------------
		childTest = parantTest.createNode("Remaining quantity is ready for dispatch");

		clickXpath(OUTBOUND);
		clickIDLinkText("Picking");
		//clickXpath(PICKINGGRID);
		//Thread.sleep(3000);
		enterXpath(PICKINGSEARCH, so.get(1));
		actionEnter();
		childTest = parantTest.createNode(
				"Picking,Consolidation,Dispatch against Cancellation performed Order. customer ref. " + socusref[1]);


		// Remaining Quantity
		// dispatch------------------------------------------------

		//Thread.sleep(3000);
		PartType = xpathGetText(PARTTYPE);
		partName = xpathGetText(PARTNAME);
		clickXpath(PARTTYPE);
		//verify("(//*[contains(.,'Please')])[16]","Please");
		//	enterXpath(SERIALNOFIELD, partnvnu);
		//Thread.sleep(500);
		//clickXpath(SUBMITBTN);
		Thread.sleep(1000);
		enterXpath(VQUANTITY, "1","quantity box");
		//Thread.sleep(2500);
		clickXpath(SUBMITBTN);
		//Thread.sleep(2500);
		//windowHandle();
		//Thread.sleep(1500);
		fluentWait(ALERTBOXCANCEL);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		partStockCheck(partnvnu,  "picking",1);

		//Thread.sleep(2500);
		childTest.pass("Picker Assigned Successfully." + "Picker is : " + prop.getProperty("PICKER"));

		orderStatus(socusref[1], Module.PICKING);
		//clickXpath(CONSOLIDATIONGRID);
		clickIDLinkText("Optimization");
		verify(CONSOLIDATION);
		enterXpath(MASTERSEARCH1, SOCustReference);
		clickXpath(ORDERCLICK,"ORDERCLICK");
		childTest.info("The order available in Consolidation screen");


		clickXpath(CONSOLIDATION,"CONSOLIDATION");

		enterXpath(NOOFBOX, "1","NOOFBOX");
		clickXpath(ADDBOX,"ADDBOX");
		CartLabel = xpathGetText(LABEL);
		clickXpath(SCANBOX);
		clickXpath("//input[@type='checkbox']", "each");

		enterXpath(ENTERLPN, partnvnu,"lpn");
		//Thread.sleep(1000);
		clickXpath(SUBMIT);
		//enterXpath(ENTERLPN, partnvnu);
		//Thread.sleep(1000);
		enterXpath(NUNVQNTY, "1");
		clickXpath(SUBMIT);
		verify("(//*[contains(.,'Succesfully')])[19]");
		enterXpath(WEIGHT, "10","WEIGHT");
		Thread.sleep(1000);

		selectByVisibleText(UOM,"Ton");
		//Thread.sleep(1000);
		clickXpath(COMPLETE);
		//Thread.sleep(1500);
		clickXpath(FINISH);
		childTest.pass(
				"Remaining NVNU part has been consolidated successfully for Customer reference is : " + socusref[1]);
		Thread.sleep(3000);
		orderStatus(so.get(1), Module.CONSOLIDATION);
		//closeBrowserTab();
		Thread.sleep(2000);
		clickXpath(DISPATCHGRID,"DISPATCHBTN");
		verify(DISPATCHGRID);
		enterXpath(DISPATCHSEARCH, so.get(1));
		Thread.sleep(2000);
		//clickXpath(DISPATCHBTN,"DISPATCHBTN");
		clickXpath(Dispatch1,"Dispatch1");
		clickXpath(DISPATCHBUTTON,"Dispatch1");
		childTest.pass("Order skipped successfully in Disptach screen. ");
		//Thread.sleep(1000);
		//verify("(//*[contains(.,' / ')])[23]","1");
		enterXpath(CARRIER, "Testing");

		//Thread.sleep(100);
		enterXpath(CARRIERREF, "SmokeTest");

		//Thread.sleep(100);
		//enterXpath(VEHICLE, "TN38AA1234");

		//Thread.sleep(100);
		enterXpath(SCANLPN, CartLabel);
		actionEnter();
		Thread.sleep(3000);
		childTest.pass(
				"Remaining NVNU part has been Dispatched successfully for Customer reference is : " + socusref[1]);
		//closeBrowserTab();
		Thread.sleep(1000);
		// Do something to open new tabs
		enterXpath( DISPATCHSEARCH, so.get(1),"DISPATCHSEARCH");
		shortWait(2000);
		actionEnter();
		orderStatus(socusref[1], Module.DISPATCH);
	}
	//@Test(priority=0)
	@Test(dependsOnMethods = { "OUT_Cancellation_DuringPartialpicking" })
	public void OUT_Cancellation_atOrderisinConsolidationDashboard() throws Exception {

		//parantTest = extent.createTest("Outbound_cancellation pre and post picking");

		childTest = parantTest.createNode("Cancellation Order in consolidation Dashboard" );
		//	clickXpath(OUTBOUND);
		sales();

		Thread.sleep(2000);
		explicitWaitXpathClick(20,PICKGENERATION);

		//driver.manage().window().maximize();
		ArrayList<String> so = new ArrayList<String>();
		ArrayList<String> von = new ArrayList<String>();
		ArrayList<String> lpn = new ArrayList<String>();
		//CancellationOutbound("SO");
		sales();
		// pickgeneration();
		// shortWait(4000);

		partStockCheck(partnvnu,  "df",3);
		partStockCheck(partnv,  "db",1);

		//	randomizerSOCancel("SO");
		//	ReadExcel(prop.getProperty("SOPATH"));
		//soreadDataCancel();
		sales();
		/*CustRefList.add(SOCustReference);
		String[] socusref = new String[SOCustReference.length()];
		CustRefList.toArray(socusref);
*/
		clickXpath(CREATEREQUESTMENU);
		//Thread.sleep(5000);
		File file = new File(prop.getProperty("SOPATH"));
		//verify("(//*[contains(.,'OTIS INDIA')])[16]");
		clickXpath(ACCOUNTSELECT, "account");
		actionEnter();
		enterXpath(CHOOSEFILE,file.getAbsolutePath(),"CHOOSEFILE");

		clickXpath(UPLOADSO,"UPLOADSO");
		verify(ALERTBOXCANCEL);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		partStockCheck(partnvnu,  "pickgen",2);
		partStockCheck(partnv,  "pickgen",2);


		//driver.navigate().refresh();
		for(String SOCustReference1:CustReference1 )
		{
			childTest.pass("SO Uploaded Successfully." + "Order Customer reference is :" + SOCustReference1);

			orderStatus(SOCustReference1, Module.SOCREATION);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			verify(CLICKRECORD);
			enterXpath( MASTERSEARCH, SOCustReference1);
			//shortWait(2000);
			actionEnter();
			//	Thread.sleep(2000);
			clickXpath( CLICKRECORD);
			//Thread.sleep(2000);
			clickXpath( CLICKBACK);
			//shortWait(2000);
			verify(CLICKRECORD);
			enterXpath( MASTERSEARCH, SOCustReference1);
			//shortWait(2000);
			clickXpath(PICKGENSEARCH, "search");
			//verify("(//*[contains(.,'1-1 of 1')])[17]");
			Thread.sleep(1000);
			clickXpath(SELECTPART,"SELECTPART");
			clickXpath(PICKERASSIGN,"PICKERASSIGN");
			String von1 = xpathGetText(CANCEL_VONTEXT);
			childTest.pass("SO Uploaded Successfully.   : " + " Order Customer reference is :" + SOCustReference  + von1);				
			so.add(SOCustReference1);
			von.add(von1);

			verify(PICKERSUBMIT);
			enterXpath(PICKERINPUT, prop.getProperty("PICKER"),"PICKERINPUT");
			nameClick(prop.getProperty("PICKER"));


			clickXpath(PICKERSUBMIT,"PICKERSUBMIT");
			Thread.sleep(3000);
			clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
			// driver.navigate().refresh();
			childTest.pass(" picker assigned Successfully for  Customer reference is : " + SOCustReference);
			orderStatus(SOCustReference1, Module.PICKASSIGN);
		}

		// picking
		//Thread.sleep(2000);
		clickIDLinkText("Picking");
		//clickXpath( PICKINGGRID);
		for (int i = 0; i <= 1; i++) {
			//try {
			for (int j = 0; j <= 1; j++) {
				//Thread.sleep(3000);
				enterXpath(PICKINGSEARCH, von.get(i),"PICKINGSEARCH");
				//Thread.sleep(3000);
				enterXpath(PICKINGSOSEARCH, so.get(i),"PICKINGSOSEARCH");
				//Thread.sleep(1000);
				actionEnter();
				//Thread.sleep(2000);
				String PartType = xpathGetText(PARTTYPE);
				//Thread.sleep(2000);
				clickXpath( PARTTYPE,"PARTTYPE");
				//Thread.sleep(2000);
				if (PartType.equals("NVNU")) {
					//verify("(//*[contains(.,'Please')])[16]","Please");
					enterXpath( SERIALNOFIELD, partnvnu,"LPN");
					//Thread.sleep(2000);
					//clickXpath( SUBMITBTN,"SUBMITBTN");
					//Thread.sleep(1000);
					clear1(VQUANTITY);
					//shortWait(2000);
					enterXpath( VQUANTITY, NVNUQtn,"qty");
					//Thread.sleep(1500);
					clickXpath( SUBMITBTN,"SUBMITBTN");
					//windowHandle();
					fluentWait(ALERTBOXCANCEL);
					clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
					// driver.navigate().back();
					partStockCheck(partnvnu,  "picking",2);

					//Thread.sleep(1000);
				} else {
					//verify("(//*[contains(.,'Please')])[16]","Please");
					for (int k = 0; k < 2; k++) {

						// driver.findElement(By.xpath("(//img[contains(@alt,'o')])[4]")).click();
						clickXpath( "//img[@alt='o']","info");
						//	windowHandle();

						serialnumber1 = xpathGetText("/html/body/div[2]/div[2]/p/div[2]/table/tbody/tr[2]/td[4]");

						// serialnumber2=driver.findElement(By.xpath("/html/body/div[2]/div[2]/p/div[2]/table/tbody/tr[3]/td[1]")).getText();

						lpn.add(serialnumber1);
						//Thread.sleep(2000);
						clickXpath( LPNCLOSE1,"LPNCLOSE1");
						//shortWait(2000);
						enterXpath( SERIALNOFIELD, serialnumber1,"lpn");
						//Thread.sleep(1000);
						clickXpath( SUBMITBTN,"SUBMITBTN");
					}
					//windowHandle();
					fluentWait(ALERTBOXCANCEL);
					clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
					partStockCheck(partnv,  "picking",2);
					LPNStatus(serialnumber1,Module.PICKING);
				}

			}
			childTest.pass(" Picking  completed for customer reference  : " + so.get(i));
			orderStatus(so.get(i), Module.PICKING);

		}
		// -----------------Cancellation

		//Thread.sleep(2000);
		clickIDLinkText(MISC_MENU);
		//Thread.sleep(2000);
		clickIDLinkText("Cancellation");
		//shortWait(2000);

		///try {
		childTest = parantTest.createNode("Order Level cancellation- At picking Screen");
		childTest.info("Order level cancellation initiated for Cust Ref is  :" + so.get(0));
		verify(CANCEL_LEVEL);
		selectByvalue(CANCEL_LEVEL, 1);
		//shortWait(2000);
		enterXpath( CANCEL_VONSEARCH, von.get(0));

		actionEnter();
		childTest.info("The Order is available in order level cancellation screen. Cust Ref is  : " + so.get(0)  +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());

		//verify("(//*[contains(.,'OUT')])[19]");
		clickXpath( CANCEL_PART_CHECKBOX);

		selectByVisibleText(CANCEL_REASON, "Customer Cancellation");
		//Thread.sleep(2000);

		clickXpath( CANCEL_BTN,"Cancel Btn");
		verify("(//*[contains(.,'Successfully')])[20]","Successfully");
		fluentWait(ALERTBOXCANCEL);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		partStockCheck(partnvnu,  "postcancel",2);
		partStockCheck(partnv,  "postcancel",2);

		childTest.pass(" Order level cancellation completed for Cust Ref is  :" + so.get(0));


		// validation
		for (int i = 1; i <= 4; i++) {
			selectByvalue(CANCEL_LEVEL, i);

			clear1(CANCEL_SOSEARCH);
			enterXpath( CANCEL_SOSEARCH, so.get(0));

			actionEnter();


		}
		childTest.info("Verification complete the cancelled order not listed in the cancellation screen." +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());


		clickXpath(OUTBOUND);
		clickIDLinkText("Optimization");

		verify(CONSOLIDATION);
		enterXpath( CONSOLIDATIONSEARCH, von.get(0));
		//shortWait(2000);

		actionEnter();
		childTest.info("Verification completed the cancelled order not listed in the Consolidation screen." +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());

		// -----------------Cancellation
		childTest = parantTest.createNode("LPN Level cancellation- At picking Screen");
		childTest.info("LPN level cancellation initiated for NV part and Cust Ref is  :" + so.get(1));
		//Thread.sleep(2000);
		clickIDLinkText(MISC_MENU);
		//Thread.sleep(2000);
		clickIDLinkText("Cancellation");
		//Thread.sleep(2000);
		verify(CANCEL_LEVEL);
		selectByvalue(CANCEL_LEVEL, 3);
		// childTest.pass(" LPN level cancellation initiated for Customer
		// reference : "+von.get(1));
		//shortWait(2000);
		enterXpath( CANCEL_VONSEARCH, von.get(1));
		//shortWait(2000);
		enterXpath( CANCEL_LPNSEARCH, lpn.get(2));
		//shortWait(2000);
		// enterXpath( CANCEL_PARTSEARCH, NVpart);
		actionEnter();
		//verify("(//*[contains(.,'OUT')])[19]");
		childTest.info("The Order is available in LPN level cancellation screen. Cust Ref is  : " + so.get(1)  +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());

		//Thread.sleep(2000);
		clickXpath( CANCEL_PART_CHECKBOX);

		selectByVisibleText(CANCEL_REASON, "Customer Cancellation");
		//Thread.sleep(2000);

		clickXpath( CANCEL_BTN,"Cancel Btn");
		verify("(//*[contains(.,'Successfully')])[20]","Successfully");
		fluentWait(ALERTBOXCANCEL);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		partStockCheck(partnv,  "postcancel",1);

		childTest.pass(" LPN level cancellation completed NV part and Cust Ref is  :" + so.get(1));


		childTest = parantTest.createNode("Quantity Level cancellation- At Consolidation Screen");
		childTest.info("Quantity level cancellation initiated for NVNU part and Cust Ref is  :" + so.get(1));
		verify(CANCEL_LEVEL);
		selectByvalue(CANCEL_LEVEL, 4);

		enterXpath( CANCEL_VONSEARCH, von.get(1),"CANCEL_VONSEARCH");
		//shortWait(2000);
		enterXpath( CANCEL_PARTSEARCH, partnvnu,"CANCEL_PARTSEARCH");
		//shortWait(2000);
		actionEnter();
		childTest.info("The Order is available in Quantity level cancellation screen. Cust Ref is  : " + so.get(1)  +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());
		childTest.info("The Requested Quantity is  :  "+ prop.getProperty("NVNUCANCELQTY")  + "The Cancelled Quantity is " + prop.getProperty("NVNUCANCELQTY"));

		//verify("(//*[contains(.,'OUT')])[19]");
		enterXpath( CANCEL_QTY_ENTER, "" + PARTIALNVNU,"qty");
		//Thread.sleep(2000);

		selectByVisibleText(CANCEL_REASON, "Customer Cancellation");
		//Thread.sleep(2000);

		clickXpath( CANCEL_BTN,"Cancel Btn");
		verify("(//*[contains(.,'Successfully')])[20]","Successfully");
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		partStockCheck(partnvnu,  "postcancel",1);

		childTest.pass(" Quantity level cancellation completed for NVNU part and Cust Ref is  :" + so.get(1));


		// ------------------------------------------part level cancellation for
		// order2
		childTest = parantTest.createNode("Part Level cancellation- At Consolidation Screen");
		childTest.info("Part level cancellation initiated for NV part and Cust Ref is  :" + so.get(1));


		verify(CANCEL_LEVEL);
		selectByvalue(CANCEL_LEVEL, 2);

		enterXpath( CANCEL_VONSEARCH, von.get(1),"CANCEL_VONSEARCH");

		enterXpath( CANCEL_PARTSEARCH, partnv,"CANCEL_PARTSEARCH");
		//shortWait(2000);
		actionEnter();

		childTest.info("The Order Part is available in Part level cancellation screen. Cust Ref is  : " + so.get(1)  +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());

		//Thread.sleep(2000);


		String partType = xpathGetText(CANCELNVPART);

		/*	if (partType.equals(partnvnu)) {

			clickXpath(CANCELLATIONSELECTALL,"Checkbox all");
			shortWait(2000);*/
		clickXpath(CANCELNVPARTSELECT,"check box nvnu");



		selectByVisibleText(CANCEL_REASON, "Customer Cancellation");
		//Thread.sleep(2000);

		clickXpath( CANCEL_BTN,"Cancel Btn");
		verify("(//*[contains(.,'Successfully')])[20]","Successfully");
		fluentWait(ALERTBOXCANCEL);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		partStockCheck(partnv,  "postcancel",1);

		childTest.pass(" Part level cancellation completed for NV part and Cust Ref is  :" + so.get(1));


		// validation for part level--------------------------------------

		selectByvalue(CANCEL_LEVEL, 2);
		clear1(CANCEL_VONSEARCH);
		//shortWait(2000);
		enterXpath( CANCEL_VONSEARCH, von.get(1),"CANCEL_VONSEARCH");
		//shortWait(2000);
		// enterXpath(CANCEL_LPNSEARCH, lpn.get(1));
		enterXpath( CANCEL_PARTSEARCH, partnv,"CANCEL_PARTSEARCH");
		//shortWait(2000);
		actionEnter();
		selectByvalue(CANCEL_LEVEL, 3);

		// enterXpath(CANCEL_VONSEARCH, von.get(1));
		//shortWait(2000);
		enterXpath( CANCEL_LPNSEARCH, lpn.get(2),"CANCEL_LPNSEARCH");
		//shortWait(2000);
		enterXpath( CANCEL_PARTSEARCH, partnv,"CANCEL_PARTSEARCH");
		//shortWait(2000);
		actionEnter();
		selectByvalue(CANCEL_LEVEL, 4);
		//shortWait(2000);
		enterXpath( CANCEL_VONSEARCH, von.get(1),"CANCEL_VONSEARCH");
		//shortWait(2000);
		// enterXpath(CANCEL_LPNSEARCH, lpn.get(2));
		enterXpath( CANCEL_PARTSEARCH, partnvnu,"CANCEL_PARTSEARCH");
		//shortWait(2000);
		actionEnter();

		childTest.info("Verification complete the cancelled part not listed in the cancellation screen." +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());

		childTest = parantTest
				.createNode("Dispatch against Cancellation performed Order. customer ref. " + so.get(1));


		clickXpath(OUTBOUND,"OUTBOUND");
		// clickXpath( OUTBOUNDMENU);
		//Thread.sleep(2000);
		clickIDLinkText("Optimization");
		//clickXpath( CONSOLIDATIONGRID);
		//Thread.sleep(2000);
		verify(CONSOLIDATION);
		enterXpath( CONSOLIDATIONSEARCH, von.get(1),"CONSOLIDATIONSEARCH");
		//Thread.sleep(2000);
		actionEnter();
		childTest.info("The Order is available in consolidation screen. " );
		//Thread.sleep(2000);
		clickXpath( ORDERCLICK,"ORDERCLICK");
		//Thread.sleep(1000);
		clickXpath( CONSOLIDATIONSKIP,"CONSOLIDATIONSKIP");

		clickXpath( CONSOLIDATIONYES,"CONSOLIDATIONYES");
		Thread.sleep(3000);
		//closeBrowserTab();
		shortWait(3000);
		clickXpath(ALERT,"close");
		childTest.pass(
				" Consolidation completed for remaining NVNU Quantity for customer reference is : " + so.get(1));
		childTest.pass("Order skipped successfully in consolidation screen. ");

		orderStatus(so.get(1), Module.CONSOLIDATION);

		clickXpath( DISPATCHGRID,"DISPATCHGRID");
		Thread.sleep(3000);
		//verify(DISPATCHBTN);
		enterXpath( DISPATCHSEARCH, von.get(1),"DISPATCHSEARCH");
		Thread.sleep(3000);



		//Thread.sleep(2000);
		//clickXpath( DISPATCHBTN,"DISPATCHBTN");
		clickXpath(Dispatch1,"Dispatch1");
		clickXpath(DISPATCHBUTTON,"Dispatch1");

		verify("(//*[contains(.,' / ')])[23]","1");
		enterXpath( CARRIER, "Testing","CARRIER");
		//shortWait(2000);
		childTest.info("The Mode of carrier is :" + "Testing");
		//shortWait(2000);
		enterXpath( CARRIERREF, "SmokeTest","CARRIERREF");
		//shortWait(2000);
		childTest.info("The carrier reference is :" + "SmokeTest");
		//shortWait(2000);

		enterXpath( SCANLPN, partnvnu,"lpn");
		actionEnter();
		clear1("//input[@id='Despatch_enterQty1']");
		//shortWait(2000);
		enterXpath( "//input[@id='Despatch_enterQty1']", "1","qty");
		actionEnter();
		//System.out.println(lpn.get(1));
		//	System.out.println(lpn + "\n" + von);
		Thread.sleep(3000);
		//closeBrowserTab();
		childTest.pass(" Dispatch completed for remaining NVNU Quantity for customer reference is : " + so.get(1));

		shortWait(3000);
		enterXpath(DISPATCHSEARCH, von.get(1),"DISPATCHSEARCH");
		shortWait(2000);
		actionEnter();
		orderStatus(so.get(1), Module.DISPATCH);
	}

	//@Test(priority=0)
	@Test(dependsOnMethods = { "OUT_Cancellation_atOrderisinConsolidationDashboard" })	
	public void OUT_Cancellation_DuringPartialconsolidation() throws Exception {
	//	loginAsUser(Username);
		CustRefList=new ArrayList<String>();
		//parantTest = extent.createTest("Outbound_cancellation pre and post picking");
		childTest = parantTest.createNode("Cancellation during partial consolidation" );

	//	clickXpath(OUTBOUND);
		explicitWaitXpathClick(20,PICKGENERATION);
		//driver.manage().window().maximize();
		ArrayList<String> so = new ArrayList<String>();
		ArrayList<String> von = new ArrayList<String>();
		ArrayList<String> lpn = new ArrayList<String>();
		//parantTest = extent.createTest("inbound");   
		sales();

		// pickgeneration();
		//
		partStockCheck(partnvnu,  "df",3);
		partStockCheck(partnv,  "db",1);

		//	randomizerSOCancel("SO");
		//	ReadExcel(prop.getProperty("SOPATH"));
		//soreadDataCancel();
		CustRefList.add(SOCustReference);
		String[] socusref = new String[SOCustReference.length()];
		CustRefList.toArray(socusref);

		clickXpath(CREATEREQUESTMENU);
		//Thread.sleep(5000);
		File file = new File(prop.getProperty("SOPATH"));
		//verify("(//*[contains(.,'OTIS INDIA')])[16]");
		clickXpath(ACCOUNTSELECT, "account");
		actionEnter();
		enterXpath(CHOOSEFILE,file.getAbsolutePath(),"CHOOSEFILE");

		clickXpath(UPLOADSO,"UPLOADSO");
		verify(ALERTBOXCANCEL);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		partStockCheck(partnvnu,  "pickgen",2);
		partStockCheck(partnv,  "pickgen",2);


		//driver.navigate().refresh();
		for(String SOCustReference1:CustReference1 )
		{
			childTest.pass("SO Uploaded Successfully." + "Order Customer reference is :" + SOCustReference1);

			orderStatus(SOCustReference1, Module.SOCREATION);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			verify(CLICKRECORD);
			enterXpath( MASTERSEARCH, SOCustReference1);
			//shortWait(2000);
			actionEnter();
			//	Thread.sleep(2000);
			clickXpath( CLICKRECORD);
			//Thread.sleep(2000);
			clickXpath( CLICKBACK);
			//shortWait(2000);
			verify(CLICKRECORD);
			enterXpath( MASTERSEARCH, SOCustReference1);
			//shortWait(2000);
			clickXpath(PICKGENSEARCH, "search");
			//verify("(//*[contains(.,'1-1 of 1')])[17]");
			clickXpath(SELECTPART);
			clickXpath(PICKERASSIGN);
			String von1 = xpathGetText(CANCEL_VONTEXT);
			childTest.pass("SO Uploaded Successfully.   : " + " Order Customer reference is :" + SOCustReference  + von1);				
			so.add(SOCustReference1);
			von.add(von1);

			verify(PICKERSUBMIT);
			enterXpath(PICKERINPUT, prop.getProperty("PICKER"),"PICKERINPUT");
			nameClick(prop.getProperty("PICKER"));


			clickXpath(PICKERSUBMIT,"PICKERSUBMIT");
			Thread.sleep(3000);
			clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
			// driver.navigate().refresh();
			childTest.pass(" picker assigned Successfully for  Customer reference is : " + SOCustReference);
			orderStatus(SOCustReference1, Module.PICKASSIGN);
		}

		// picking

		clickIDLinkText("Picking");

		for (int i = 0; i <= 1; i++) {
			for (int j = 0; j <= 1; j++) {
				//shortWait(2000);
				verify(PARTTYPE);
				enterXpath( PICKINGSEARCH, von.get(i),"PICKINGSEARCH");
				//shortWait(2000);
				enterXpath( PICKINGSOSEARCH, so.get(i),"PICKINGSOSEARCH");
				//Thread.sleep(1000);
				actionEnter();
				//Thread.sleep(2000);
				String PartType = xpathGetText(PARTTYPE);
				//	Thread.sleep(2000);
				clickXpath( PARTTYPE);
				//verify("(//*[contains(.,'scan')])[16]","scan");
				//Thread.sleep(2000);
				if (PartType.equals("NVNU")) {
					//Thread.sleep(2000);
					enterXpath( SERIALNOFIELD, partnvnu,"lpn");
					//	Thread.sleep(2000);
					//clickXpath( SUBMITBTN,"SUBMITBTN");
					//Thread.sleep(1000);
					clear1(VQUANTITY);
					//Thread.sleep(2000);
					enterXpath( VQUANTITY, NVNUQtn,"qty");
					//Thread.sleep(1500);
					clickXpath( SUBMITBTN,"SUBMITBTN");
					//windowHandle();
					fluentWait(ALERTBOXCANCEL);
					clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
					partStockCheck(partnvnu,  "picking",2);

				} else {
					for (int k = 0; k < 2; k++) {

						Thread.sleep(1000);
						clickXpath( "//img[@alt='o']");
						//windowHandle();

						serialnumber1 = xpathGetText("/html/body/div[2]/div[2]/p/div[2]/table/tbody/tr[2]/td[4]");

						// serialnumber2=driver.findElement(By.xpath("/html/body/div[2]/div[2]/p/div[2]/table/tbody/tr[3]/td[1]")).getText();

						lpn.add(serialnumber1);
						//Thread.sleep(2000);
						clickXpath( LPNCLOSE1,"LPNCLOSE1");
						//shortWait(2000);
						enterXpath( SERIALNOFIELD, serialnumber1,"lpn");
						//Thread.sleep(1000);
						clickXpath( SUBMITBTN,"SUBMITBTN");
					}
					//windowHandle();
					fluentWait(ALERTBOXCANCEL);
					clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
					partStockCheck(partnv,  "picking",2);
					LPNStatus(serialnumber1,Module.PICKING);
				}

			}
			childTest.pass(" Picking  completed for Customer reference is : " + so.get(i));
			orderStatus(so.get(i), Module.PICKING);
		}

		// Consolidation


		clickIDLinkText("Optimization");
		//shortWait(2000);
		verify(CONSOLIDATION);
		enterXpath( CONSOLIDATIONSEARCH, von.get(1),"CONSOLIDATIONSEARCH");

		clickXpath(OPTISEARCH,"OPTIMIZATIONSEARCH");
		Thread.sleep(1000);
		//Thread.sleep(3000);
		clickXpath( ORDERCLICK,"ORDERCLICK");
		childTest.info("The Order is available in consolidation screen. " + MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());
		//shortWait(2000);
		clickXpath( CONSOLIDATION,"CONSOLIDATION");
		//shortWait(2000);
		enterXpath( NOOFBOX, "1","NOOFBOX");
		Thread.sleep(2000);
		clickXpath( ADDBOX,"ADDBOX");
		//shortWait(2000);
		clickXpath( SCANBOX,"SCANBOX");
		//Thread.sleep(1000);
		clickXpath("//input[@type='checkbox']", "each");

		enterXpath( CONSOLIDATEENTERLPN, partnvnu,"lpn");
		//shortWait(2000);
		clickXpath( SUBMITBTN1,"SUBMITBTN1");
		clear1(NUNVQNTY);
		//	shortWait(2000);
		enterXpath( NUNVQNTY, NVNUQtn,"lpn");
		clickXpath( SUBMITBTN1,"SUBMITBTN1");
		//verify("(//*[contains(.,'Succesfully')])[19]","Succesfully");
		//shortWait(2000);
		enterXpath( WEIGHT, "10","WEIGHT");
		//shortWait(2000);
		selectByVisibleText(UOM, "Ton");
		//shortWait(2000);
		clickXpath( COMPLETE,"COMPLETE");
		childTest.pass(" Partially Consolidation completed for NVNU part and Customer reference : " + so.get(1));


		// cancellation
		Thread.sleep(2000);
		clickIDLinkText(MISC_MENU);
		//Thread.sleep(2000);
		clickIDLinkText("Cancellation");
		//Thread.sleep(2000);

		childTest = parantTest.createNode("Order Level cancellation- At Consolidation Screen");
		childTest.info("Order level cancellation initiated for NV part and Order num is  :" + von.get(0));

		verify(CANCEL_LEVEL);
		selectByvalue(CANCEL_LEVEL, 1);

		enterXpath( CANCEL_VONSEARCH, von.get(0));

		actionEnter();
		childTest.info("The Order is available in order level cancellation screen. Cust Ref is  : " + so.get(0)  +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());
		//verify("(//*[contains(.,'OUT')])[19]");
		clickXpath( CANCEL_PART_CHECKBOX);
		//shortWait(2000);
		selectByVisibleText(CANCEL_REASON, "Customer Cancellation");
		//Thread.sleep(2000);

		clickXpath( CANCEL_BTN,"Cancel Btn");
		verify("(//*[contains(.,'Successfully')])[20]","Successfully");
		fluentWait(ALERTBOXCANCEL);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		partStockCheck(partnvnu,  "postcancel",2);
		partStockCheck(partnv,  "postcancel",2);

		childTest.pass(" Order level cancellation completed for customer reference is : " +von.get(0));


		clear1(CANCEL_SOSEARCH);
		for (int i = 1; i <= 4; i++) {
			selectByvalue(CANCEL_LEVEL, i);

			clear1(CANCEL_SOSEARCH);
			enterXpath( CANCEL_SOSEARCH, so.get(0),"CANCEL_SOSEARCH");
			//	shortWait(2000);
			// enterXpath(CANCEL_PARTSEARCH,);
			actionEnter();


		}

		childTest.info("Verification complete the cancelled order not listed in the cancellation screen" +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());

		Thread.sleep(1000);
		//clickXpath(OUTBOUND);
		clickXpath(OUTBOUND,"OUTBOUND");
		clickIDLinkText("Optimization");
		verify(CONSOLIDATION);
		enterXpath( CONSOLIDATIONSEARCH, von.get(0),"CONSOLIDATIONSEARCH");

		actionEnter();


		childTest.info("Verification complete the cancelled order not listed in the picking screen" +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());

		// cancellation
		//Thread.sleep(2000);
		clickIDLinkText(MISC_MENU);
		//Thread.sleep(2000);
		clickIDLinkText("Cancellation");
		//Thread.sleep(2000);
		childTest = parantTest.createNode("LPN Level cancellation- At Consolidation Screen");
		childTest.info("LPN level cancellation initiated for NV part and Cust Ref is  :" + so.get(1));

		verify(CANCEL_LEVEL);
		selectByvalue(CANCEL_LEVEL, 3);

		enterXpath( CANCEL_VONSEARCH, von.get(1),"CANCEL_VONSEARCH");
		//shortWait(2000);
		enterXpath( CANCEL_LPNSEARCH, lpn.get(2),"CANCEL_LPNSEARCH");
		//shortWait(2000);
		// enterXpath( CANCEL_PARTSEARCH, NVpart);
		actionEnter();

		childTest.info("Verification complete the cancelled LPN not listed in the cancellation screen" +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());

		//verify("(//*[contains(.,'OUT')])[19]");
		clickXpath( CANCEL_PART_CHECKBOX,"CANCEL_PART_CHECKBOX");
		//shortWait(2000);
		selectByVisibleText(CANCEL_REASON, "Customer Cancellation");
		//Thread.sleep(2000);

		clickXpath( CANCEL_BTN,"Cancel Btn");
		verify("(//*[contains(.,'Successfully')])[20]","Successfully");
		fluentWait(ALERTBOXCANCEL);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		//shortWait(2000);
		partStockCheck(partnv,  "postcancel",1);

		childTest.pass(" LPN Level Cancellation has been completed for Cust Ref : " + so.get(1));


		childTest = parantTest.createNode("Quantity Level cancellation- At Consolidation Screen");
		childTest.info("Quantity level cancellation initiated for NVNU part and Cust Ref is  :" + so.get(1));

		verify(CANCEL_LEVEL);
		selectByvalue(CANCEL_LEVEL, 4);

		enterXpath( CANCEL_VONSEARCH, von.get(1),"CANCEL_VONSEARCH");
		//shortWait(2000);
		enterXpath( CANCEL_PARTSEARCH, partnvnu,"CANCEL_PARTSEARCH");
		//shortWait(2000);
		actionEnter();
		//verify("(//*[contains(.,'OUT')])[19]");
		childTest.info("The Order is available in Quantity level cancellation screen. Cust Ref is  : " + so.get(1)  +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());
		childTest.info("The Requested Quantity is  :  "+ prop.getProperty("NVNUCANCELQTY")  + "The Cancelled Quantity is " + prop.getProperty("NVNUCANCELQTY"));

		//shortWait(2000);
		enterXpath( CANCEL_QTY_ENTER, "" + PARTIALNVNU,"qty");
		//Thread.sleep(2000);

		selectByVisibleText(CANCEL_REASON, "Customer Cancellation");
		//Thread.sleep(2000);

		clickXpath( CANCEL_BTN,"Cancel Btn");
		verify("(//*[contains(.,'Successfully')])[20]","Successfully");
		fluentWait(ALERTBOXCANCEL);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		partStockCheck(partnvnu,  "postcancel",1);

		childTest.pass(" Quantity level cancellation completed for NVNU part and Cust Ref is  :" + so.get(1));


		// ------------------------------------------part level cancellation for
		// order2
		childTest = parantTest.createNode("Part Level cancellation- At Consolidation Screen");
		childTest.info("Part level cancellation initiated for NV part and Cust Ref is  :" + so.get(1));

		verify(CANCEL_LEVEL);
		selectByvalue(CANCEL_LEVEL, 2);
		// childTest.pass(" Part level cancellation initiated for Customer
		// reference : "+von.get(1));
		//shortWait(2000);
		enterXpath( CANCEL_VONSEARCH, von.get(1),"CANCEL_VONSEARCH");
		//shortWait(2000);
		// enterXpath(CANCEL_LPNSEARCH, lpn.get(1));
		enterXpath( CANCEL_PARTSEARCH, partnv,"CANCEL_PARTSEARCH");
		//shortWait(2000);
		actionEnter();
		//shortWait(2000);
		childTest.info("The Order Part is available in Part level cancellation screen. Cust Ref is  : " + so.get(1)  +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());

		//Thread.sleep(2000);
		String partType = xpathGetText(CANCELNVPART);

		/*	if (partType.equals(partnvnu)) {

				clickXpath(CANCELLATIONSELECTALL,"Checkbox all");
				shortWait(2000);*/
		clickXpath(CANCELNVPARTSELECT,"check box nvnu");


		selectByVisibleText(CANCEL_REASON, "Customer Cancellation");
		//Thread.sleep(2000);

		clickXpath( CANCEL_BTN,"Cancel Btn");
		//verify("(//*[contains(.,'Successfully')])[20]","Successfully");
		fluentWait(ALERTBOXCANCEL);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		partStockCheck(partnv,  "postcancel",1);

		childTest.pass(" Part level cancellation completed.");



		// validation for part level-------------------------------

		selectByvalue(CANCEL_LEVEL, 2);
		clear1(CANCEL_VONSEARCH);
		//shortWait(2000);
		enterXpath( CANCEL_VONSEARCH, von.get(1));
		//shortWait(2000);
		// enterXpath(CANCEL_LPNSEARCH, lpn.get(1));
		enterXpath( CANCEL_PARTSEARCH, partnv);
		//shortWait(2000);
		actionEnter();
		selectByvalue(CANCEL_LEVEL, 3);

		// enterXpath(CANCEL_VONSEARCH, von.get(1));
		//shortWait(2000);
		enterXpath( CANCEL_LPNSEARCH, lpn.get(2));
		//shortWait(2000);
		enterXpath( CANCEL_PARTSEARCH, partnv);
		//shortWait(2000);
		actionEnter();
		selectByvalue(CANCEL_LEVEL, 4);
		//shortWait(2000);
		enterXpath( CANCEL_VONSEARCH, von.get(1));
		//shortWait(2000);
		// enterXpath(CANCEL_LPNSEARCH, lpn.get(2));
		enterXpath( CANCEL_PARTSEARCH, partnvnu);
		//shortWait(2000);
		actionEnter();

		childTest.info(" Verifications completed for part,LPn,Quanity level." +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());
		// consolidation
		childTest = parantTest.createNode("Dispatch against Cancellation performed Order. customer ref. " + so.get(1));
		//shortWait(2000);
		clickXpath(OUTBOUND);
		//Thread.sleep(1000);
		String pkg = "";
		clickIDLinkText("Optimization");
		//clickXpath( CONSOLIDATIONGRID);
		//shortWait(2000);
		verify(CONSOLIDATION);
		enterXpath( CONSOLIDATIONSEARCH, von.get(1));
		//shortWait(2000);


		actionEnter();
		childTest.info("The Order is available in consolidation screen. " + MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());
		Thread.sleep(3000);
		/*
		 * clickXpath( ORDERCLICK); clickXpath(
		 * CONSOLIDATION);
		 */

		clickXpath( ORDERCLICK);
		//shortWait(2000);
		clickXpath( CONSOLIDATION);
		//	shortWait(2000);

		pkg = xpathGetText("//td[@id='Consolidate_PackageRefId']");
		//System.out.println(pkg);
		clickXpath( FINISH);

		childTest.pass(
				" Consolidation completed for remaining NVNU Quantity for customer reference is :" + so.get(1));
		//switchWindow();
		//	 clickXpath( "(//button[@id='buttonSave'])[1]");
		//shortWait(4000);
		//closeBrowserTab();
		Thread.sleep(3000);

		orderStatus(so.get(1), Module.CONSOLIDATION);


		clickXpath( DISPATCHGRID,"DISPATCHGRID");
		shortWait(2000);
		//verify(DISPATCHBTN);
		enterXpath( DISPATCHSEARCH, von.get(1),"DISPATCHSEARCH");
		Thread.sleep(3000);
		//actionEnter();
		childTest.info("The Order is available in Dispatch screen. " + MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());
		//shortWait(2000);
		//clickXpath( DISPATCHBTN,"DISPATCHBTN");
		clickXpath(Dispatch1,"Dispatch1");
		clickXpath(DISPATCHBUTTON,"Dispatch1");
		Thread.sleep(1000);
		//verify("(//*[contains(.,' / ')])[23]","1");
		enterXpath( CARRIER, "Testing","CARRIER");
		//shortWait(2000);
		childTest.info("The Mode of carrier is :" + "Testing");
		//shortWait(2000);
		enterXpath( CARRIERREF, "OUTCANCEL","CARRIERREF");
		//shortWait(2000);
		childTest.info("The carrier reference is :" + "OUTCANCEL");
		//shortWait(2000);

		enterXpath( SCANLPN, pkg,"lpn");
		//Thread.sleep(2000);
		actionEnter();
		childTest.pass(" Dispatch completed for remaining NVNU Quantity for customer reference is :" + so.get(1));
		Thread.sleep(3000);
		//closeBrowserTab();
		shortWait(3000);

		enterXpath( DISPATCHSEARCH, so.get(1),"DISPATCHSEARCH");

		actionEnter();
		orderStatus(so.get(1), Module.DISPATCH);
	}
	//	@Test(priority=0)
	@Test(dependsOnMethods = { "OUT_Cancellation_DuringPartialconsolidation" })
	public void OUT_Cancellation_atOrderisinDispatchDashboard() throws Exception {

		//parantTest = extent.createTest("Outbound_cancellation pre and post picking");
		childTest = parantTest.createNode("Cancellation at order is in dispatch screen" );
		ArrayList<String> so = new ArrayList<String>();
		ArrayList<String> lpn = new ArrayList<String>();
		ArrayList<String> von = new ArrayList<String>();	
sales();
		Thread.sleep(2000);
		//clickXpath(OUTBOUND);
		explicitWaitXpathClick(20,PICKGENERATION);


		partStockCheck(partnvnu,  "df",3);
		partStockCheck(partnv,  "db",1);

		//	randomizerSOCancel("SO");
		//	ReadExcel(prop.getProperty("SOPATH"));
		//soreadDataCancel();
		sales();
		CustRefList.add(SOCustReference);
		String[] socusref = new String[SOCustReference.length()];
		CustRefList.toArray(socusref);

		clickXpath(CREATEREQUESTMENU);
		//Thread.sleep(5000);
		File file = new File(prop.getProperty("SOPATH"));
		//verify("(//*[contains(.,'OTIS INDIA')])[16]");
		clickXpath(ACCOUNTSELECT, "account");
		actionEnter();
		enterXpath(CHOOSEFILE,file.getAbsolutePath(),"CHOOSEFILE");

		clickXpath(UPLOADSO,"UPLOADSO");
		verify(ALERTBOXCANCEL);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		partStockCheck(partnvnu,  "pickgen",2);
		partStockCheck(partnv,  "pickgen",2);


		//driver.navigate().refresh();
		for(String SOCustReference1:CustReference1 )
		{
			childTest.pass("SO Uploaded Successfully." + "Order Customer reference is :" + SOCustReference1);

			orderStatus(SOCustReference1, Module.SOCREATION);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			verify(CLICKRECORD);
			enterXpath( MASTERSEARCH, SOCustReference1);
			//shortWait(2000);
			actionEnter();
			//	Thread.sleep(2000);
			clickXpath( CLICKRECORD);
			//Thread.sleep(2000);
			clickXpath( CLICKBACK);
			//shortWait(2000);
			verify(CLICKRECORD);
			enterXpath( MASTERSEARCH, SOCustReference1);
			//shortWait(2000);
			clickXpath(PICKGENSEARCH, "search");
			//verify("(//*[contains(.,'1-1 of 1')])[17]");
			clickXpath(SELECTPART);
			clickXpath(PICKERASSIGN);
			String von1 = xpathGetText(CANCEL_VONTEXT);
			childTest.pass("SO Uploaded Successfully.   : " + " Order Customer reference is :" + SOCustReference  + von1);				
			so.add(SOCustReference1);
			von.add(von1);

			verify(PICKERSUBMIT);
			enterXpath(PICKERINPUT, prop.getProperty("PICKER"),"PICKERINPUT");
			nameClick(prop.getProperty("PICKER"));


			clickXpath(PICKERSUBMIT,"PICKERSUBMIT");
			Thread.sleep(3000);
			clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
			// driver.navigate().refresh();
			childTest.pass(" picker assigned Successfully for  Customer reference is : " + SOCustReference);
			orderStatus(SOCustReference1, Module.PICKASSIGN);
		}
		// picking
		clickIDLinkText("Picking");
		//clickXpath( PICKINGGRID);
		for (int i = 0; i <= 1; i++) {
			for (int j = 0; j <= 1; j++) {
				//shortWait(2000);
				verify(PARTTYPE);
				enterXpath( PICKINGSEARCH, so.get(i));
				//Thread.sleep(1000);
				actionEnter();
				//Thread.sleep(2000);
				String PartType = xpathGetText(PARTTYPE);
				//shortWait(2000);
				clickXpath( PARTTYPE);
				//	Thread.sleep(2000);
				if (PartType.equals("NVNU")) {
					//verify("(//*[contains(.,'scan')])[16]","scan");
					//enterXpath( SERIALNOFIELD, partnvnu);
					//shortWait(2000);
					//clickXpath( SUBMITBTN);
					//Thread.sleep(1000);
					clear1(VQUANTITY);
					//shortWait(2000);
					enterXpath( VQUANTITY, "2");
					//Thread.sleep(1500);
					clickXpath( SUBMITBTN);
					//shortWait(2000);
					//windowHandle();
					Thread.sleep(1000);
					clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
					partStockCheck(partnvnu,  "picking",2);
					// driver.navigate().back();
					//Thread.sleep(1000);
				} else {

					// driver.findElement(By.xpath("(//img[contains(@alt,'o')])[4]")).click();
					//verify("(//*[contains(.,'scan')])[16]","scan");
					Thread.sleep(1000);
					clickXpath( LPNINFOC);

					serialnumber1 = xpathGetText("/html/body/div[2]/div[2]/p/div[2]/table/tbody/tr[2]/td[4]");

					// serialnumber2=driver.findElement(By.xpath("/html/body/div[2]/div[2]/p/div[2]/table/tbody/tr[3]/td[1]")).getText();

					lpn.add(serialnumber1);
					//shortWait(2000);
					clickXpath( LPNCLOSE1);
					//shortWait(2000);
					enterXpath( SERIALNOFIELD, serialnumber1,"lpn");
					//Thread.sleep(1000);
					clickXpath( SUBMITBTN,"SUBMITBTN");
					Thread.sleep(1000);
					// Second LPN
					clickXpath( LPNINFOC);

					serialnumber2 = xpathGetText("/html/body/div[2]/div[2]/p/div[2]/table/tbody/tr[2]/td[4]");

					lpn.add(serialnumber2);
					//shortWait(2000);
					clickXpath( LPNCLOSE1);
					//shortWait(2000);
					enterXpath( SERIALNOFIELD, serialnumber2);
					//Thread.sleep(1000);
					clickXpath( SUBMITBTN);
					fluentWait(ALERTBOXCANCEL);
					clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
					//shortWait(1000);
					partStockCheck(partnv,  "picking",2);

					childTest.pass(" Picking  completed for Customer reference is : " + so.get(i));
					LPNStatus(serialnumber1,Module.PICKING);
					LPNStatus(serialnumber2,Module.PICKING);
				}



			}
			orderStatus(so.get(i), Module.PICKING);
		}

		// System.out.println(lpn);

		// Consolidation
		// ---------------------------------------------------------------------------


		for (int i = 0; i < 2; i++) {
			clickIDLinkText("Optimization");
			driver.navigate().refresh();
			Thread.sleep(2000);
			enterXpath( CONSOLIDATIONSEARCH, so.get(i));
			clickXpath(OPTISEARCH,"OPTIMIZATIONSEARCH");
			Thread.sleep(1000);			
			clickXpath(ORDERCLICK,"ORDERCLICK");
			//Thread.sleep(3000);
			clickXpath(CONSOLIDATIONSKIP,"CONSOLIDATIONSKIP");
			///switchWindow();
			clickXpath(CONSOLIDATIONYES,"CONSOLIDATIONYES");
			Thread.sleep(5000);
			//closeBrowserTab();
			Thread.sleep(3000);
			clickXpath(ALERT,"close");


			childTest.pass("Consolidation has been completed for SO." + "customer referance are" + so.get(i));
			orderStatus(so.get(i), Module.CONSOLIDATION);
		}

		// Dispatch Scearch
		// ----------------------------------------------------------------
		//shortWait(2000);
		clickXpath( DISPATCHGRID,"DISPATCHGRID");
		Thread.sleep(3000);
		//verify(DISPATCHBTN);
		enterXpath(DISPATCHSEARCH, so.get(0),"DISPATCHSEARCH");
		Thread.sleep(3000);
		//actionEnter();
		childTest.pass("Order 1 has been checked in Dispatch screen.");
		// ODER
		// CANCELLATION---------------------------------------------------------------------------------------------------------

		clickIDLinkText("Misc");
		//Thread.sleep(2000);
		clickIDLinkText("Cancellation");
		//Thread.sleep(2000);
		childTest = parantTest.createNode("Order Level cancellation- At Dispatch Screen");
		childTest.info("Order level cancellation initiated for Cust Ref is  :" + so.get(0));
		verify(CANCEL_LEVEL);
		selectByvalue(CANCEL_LEVEL, 1);
		//Thread.sleep(2000);
		enterXpath( CANCEL_VONSEARCH, von.get(0),"CANCEL_VONSEARCH");
		//shortWait(2000);
		enterXpath( CANCEL_SOSEARCH, so.get(0),"CANCEL_SOSEARCH");
		//shortWait(2000);
		actionEnter();
		childTest.info("The Order is available in order level cancellation screen. Cust Ref is  : " + so.get(0)  +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());

		//Thread.sleep(2000);
		clickXpath(CANCEL_PART_CHECKBOX,"CANCEL_PART_CHECKBOX");
		//Thread.sleep(2000);
		selectByVisibleText(CANCEL_REASON, "Customer Cancellation");
		//shortWait(2000);
		clickXpath( CANCEL_BTN,"Cancel Btn");
		verify("(//*[contains(.,'Successfully')])[20]","Successfully");
		fluentWait(ALERTBOXCANCEL);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		//Thread.sleep(2000);
		partStockCheck(partnvnu,  "postcancel",2);
		partStockCheck(partnv,  "postcancel",2);

		childTest.pass(" Order level cancellation completed for customer reference is : " +von.get(0));


		// Validation for oder level Cancellation
		// ------------------------------------------------------------------
		clear1(CANCEL_SOSEARCH);
		clear1(CANCEL_VONSEARCH);


		for (int i = 1; i <= 4; i++) {
			selectByvalue(CANCEL_LEVEL, i);
			//shortWait(2000);
			clear1(CANCEL_SOSEARCH);
			enterXpath( CANCEL_SOSEARCH, so.get(0),"CANCEL_SOSEARCH");
			//shortWait(2000);
			actionEnter();
			//Thread.sleep(2000);
		}

		childTest.info("Verification complete the cancelled order not listed in the cancellation screen"+ MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());
		//shortWait(2000);
		clickXpath(OUTBOUND,"OUTBOUND");
		//Thread.sleep(2000);
		clickXpath( DISPATCHGRID,"DISPATCHGRID");
		//verify(DISPATCHBTN);
		enterXpath(DISPATCHSEARCH, so.get(0),"DISPATCHSEARCH");
		Thread.sleep(3000);
		childTest.info("Verification complete the cancelled order not listed in the Dispatch screen" + MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());
		Thread.sleep(1000);
		clickIDLinkText("Misc");
		//Thread.sleep(2000);
		clickIDLinkText( "Cancellation");
		//Thread.sleep(2000);

		// QUT
		// CANCELLATION---------------------------------------------------------------------------------

		childTest = parantTest.createNode("Quantity Level cancellation- At Dispatch Screen");
		childTest.info("Quantity level cancellation initiated for NVNU part and Cust Ref is  :" + so.get(1));
		verify(CANCEL_LEVEL);
		selectByvalue(CANCEL_LEVEL, 4);
		//Thread.sleep(2000);
		enterXpath( CANCEL_VONSEARCH, von.get(1),"CANCEL_VONSEARCH");

		enterXpath( CANCEL_SOSEARCH, so.get(1),"CANCEL_SOSEARCH");

		enterXpath( CANCEL_PARTSEARCH, partnvnu,"CANCEL_PARTSEARCH");
		actionEnter();
		childTest.info("The Order is available in Quantity level cancellation screen. Cust Ref is  : " + so.get(1)  +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());
		childTest.info("The Requested Quantity is  :  "+ prop.getProperty("NVNUCANCELQTY")  + "The Cancelled Quantity is " + prop.getProperty("NVNUCANCELQTY"));


		enterXpath( CANCEL_QTY_ENTER, "2","qty");

		selectByVisibleText(CANCEL_REASON, "Customer Cancellation");
		//Thread.sleep(2000);
		clickXpath( CANCEL_BTN,"Cancel Btn");
		verify("(//*[contains(.,'Successfully')])[20]","Successfully");
		fluentWait(ALERTBOXCANCEL);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		partStockCheck(partnvnu,  "postcancel",2);

		//Thread.sleep(2000);

		childTest.pass(" Quantity level cancellation completed for NVNU part and Cust Ref is  :" + so.get(1));



		// Validation for QUT level Cancellation
		// ------------------------------------------------------------------
		for (int i = 2; i <= 4; i++) {
			selectByvalue(CANCEL_LEVEL, i);
			//shortWait(2000);
			clear1(CANCEL_SOSEARCH);
			enterXpath( CANCEL_SOSEARCH, so.get(0),"CANCEL_SOSEARCH");
			shortWait(2000);
			clear1(CANCEL_SOSEARCH);
			enterXpath( CANCEL_SOSEARCH, so.get(1),"CANCEL_SOSEARCH");
			//shortWait(2000);
			clear1(CANCEL_PARTSEARCH);
			enterXpath( CANCEL_PARTSEARCH, "PARTNVNUFIFO ","CANCEL_PARTSEARCH");
			//shortWait(2000);
			actionEnter();
			//Thread.sleep(2000);
		}

		childTest.pass("Validation has been completed for Quantity level." +   MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());

		// LPN
		// CANCELLATION----------------------------------------------------------------------------
		childTest = parantTest.createNode("LPN Level cancellation- At Dispatch Screen");
		childTest.info("LPN level cancellation initiated for NV part and  LPN is  :" + lpn.get(2));

		selectByvalue(CANCEL_LEVEL, 3);
		//Thread.sleep(2000);
		enterXpath( LPNSCEARCH, lpn.get(2),"LPNSCEARCH");
		//shortWait(2000);
		enterXpath( CANCEL_VONSEARCH, von.get(1),"CANCEL_VONSEARCH");
		//shortWait(2000);
		enterXpath( CANCEL_SOSEARCH, so.get(1),"CANCEL_SOSEARCH");
		//shortWait(2000);
		enterXpath( CANCEL_PARTSEARCH, partnv,"CANCEL_PARTSEARCH");
		//shortWait(2000);
		actionEnter();
		childTest.info("Verification complete the cancelled LPN not listed in the cancellation screen" +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());

		clickXpath(CANCELLATIONSELECTALL,"Checkbox all");
		//shortWait(2000);
		selectByVisibleText(CANCEL_REASON, "Customer Cancellation");
		//Thread.sleep(2000);
		clickXpath( CANCEL_BTN,"Cancel Btn");
		verify("(//*[contains(.,'Successfully')])[20]","Successfully");
		fluentWait(ALERTBOXCANCEL);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		partStockCheck(partnv,  "postcancel",1);

		childTest.pass(" LPN Level Cancellation has been completed for Cust Ref : " + so.get(1));



		// Validation for LPN level Cancellation
		// ------------------------------------------------------------------

		selectByvalue(CANCEL_LEVEL, 2);
		//Thread.sleep(2000);
		selectByvalue(CANCEL_LEVEL, 3);
		//Thread.sleep(2000);
		enterXpath( LPNSCEARCH, lpn.get(2),"LPNSCEARCH");
		//shortWait(2000);
		enterXpath( CANCEL_VONSEARCH, von.get(1),"CANCEL_VONSEARCH");
		//shortWait(2000);
		enterXpath( CANCEL_SOSEARCH, so.get(1),"CANCEL_SOSEARCH");
		//shortWait(2000);
		enterXpath( CANCEL_PARTSEARCH, partnv,"CANCEL_PARTSEARCH");
		//shortWait(2000);
		actionEnter();

		childTest.info(" Verifications completed for LPn level." +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());


		// PART
		// CANCELLATION--------------------------------------------------------------------------------------------------
		childTest = parantTest.createNode("Part Level cancellation- At Dispatch Screen");
		childTest.info("Part level cancellation initiated for NV part and Cust Ref is  :" + so.get(1));

		selectByvalue(CANCEL_LEVEL, 2);
		//Thread.sleep(2000);
		enterXpath( CANCEL_VONSEARCH, von.get(1),"CANCEL_VONSEARCH");
		//shortWait(2000);
		enterXpath( CANCEL_SOSEARCH, so.get(1),"CANCEL_SOSEARCH");
		//	shortWait(2000);
		enterXpath( CANCEL_PARTSEARCH, partnv,"CANCEL_PARTSEARCH");
		//shortWait(2000);
		actionEnter();

		childTest.info("The Order is available in Part level cancellation screen. Cust Ref is  : " + so.get(1)  +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());

		clickXpath(CANCELLATIONSELECTALL,"Checkbox all");
		//Thread.sleep(2000);
		selectByVisibleText(CANCEL_REASON, "Customer Cancellation");
		//Thread.sleep(2000);
		clickXpath( CANCEL_BTN,"Cancel Btn");
		verify("(//*[contains(.,'Successfully')])[20]","Successfully");
		fluentWait(ALERTBOXCANCEL);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		partStockCheck(partnv,  "postcancel",1);

		childTest.pass(" Part level cancellation completed for customer reference is : " +so.get(1));



		// Validation for PART level Cancellation
		// ------------------------------------------------------------------
		for (int i = 2; i <= 4; i++) {
			selectByvalue(CANCEL_LEVEL, i);
			//shortWait(2000);
			clear1(CANCEL_SOSEARCH);
			enterXpath( CANCEL_SOSEARCH, so.get(0));
			clear1(CANCEL_SOSEARCH);
			enterXpath( CANCEL_SOSEARCH, so.get(1));
			clear1(CANCEL_PARTSEARCH);
			enterXpath( CANCEL_PARTSEARCH, partnv);
			actionEnter();
		}

		childTest.info("Verification complete the cancelled part not listed in the cancellation screen" +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());


		// To check in Dispatch Screen------------------------------------------
		clickXpath(OUTBOUND);
		//Thread.sleep(2000);
		clickXpath( DISPATCHGRID);
		enterXpath(DISPATCHSEARCH, so.get(1));
		Thread.sleep(3000);
		childTest.info("Verification complete the cancelled part not listed in the Dispatch screen" +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());

		orderStatus(so.get(1), Module.DISPATCH);

		//childTest.pass("Finally Order 2 has been checked in Dispatch screen.");

	}

	@Test(dependsOnMethods = { "OUT_Cancellation_atOrderisinDispatchDashboard" })
	public void OUT_Cancellation_DuringPartialDispatch() throws Exception {

		childTest = parantTest.createNode("Cancellation during partial Dispatch" );

		ArrayList<String> so = new ArrayList<String>();
		ArrayList<String> lpn = new ArrayList<String>();
		ArrayList<String> von = new ArrayList<String>();
		//clickXpath(OUTBOUND);
		explicitWaitXpathClick(20,PICKGENERATION);
		partStockCheck(partnvnu,  "df",3);
		partStockCheck(partnv,  "db",1);

		//	randomizerSOCancel("SO");
		//	ReadExcel(prop.getProperty("SOPATH"));
		//soreadDataCancel();
		sales();
		CustRefList.add(SOCustReference);
		String[] socusref = new String[SOCustReference.length()];
		CustRefList.toArray(socusref);

		clickXpath(CREATEREQUESTMENU);
		//Thread.sleep(5000);
		File file = new File(prop.getProperty("SOPATH"));
		//verify("(//*[contains(.,'OTIS INDIA')])[16]");
		clickXpath(ACCOUNTSELECT, "account");
		actionEnter();
		enterXpath(CHOOSEFILE,file.getAbsolutePath(),"CHOOSEFILE");

		clickXpath(UPLOADSO,"UPLOADSO");
		verify(ALERTBOXCANCEL);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		partStockCheck(partnvnu,  "pickgen",2);
		partStockCheck(partnv,  "pickgen",2);


		//driver.navigate().refresh();
		for(String SOCustReference1:CustReference1 )
		{
			childTest.pass("SO Uploaded Successfully." + "Order Customer reference is :" + SOCustReference1);

			orderStatus(SOCustReference1, Module.SOCREATION);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			verify(CLICKRECORD);
			enterXpath( MASTERSEARCH, SOCustReference1);
			//shortWait(2000);
			actionEnter();
			//	Thread.sleep(2000);
			clickXpath( CLICKRECORD);
			//Thread.sleep(2000);
			clickXpath( CLICKBACK);
			//shortWait(2000);
			verify(CLICKRECORD);
			enterXpath( MASTERSEARCH, SOCustReference1);
			//shortWait(2000);
			clickXpath(PICKGENSEARCH, "search");
			//verify("(//*[contains(.,'1-1 of 1')])[17]");
			clickXpath(SELECTPART);
			clickXpath(PICKERASSIGN);
			String von1 = xpathGetText(CANCEL_VONTEXT);
			childTest.pass("SO Uploaded Successfully.   : " + " Order Customer reference is :" + SOCustReference  + von1);				
			so.add(SOCustReference1);
			von.add(von1);

			verify(PICKERSUBMIT);
			enterXpath(PICKERINPUT, prop.getProperty("PICKER"),"PICKERINPUT");
			nameClick(prop.getProperty("PICKER"));


			clickXpath(PICKERSUBMIT,"PICKERSUBMIT");
			Thread.sleep(3000);
			clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
			// driver.navigate().refresh();
			childTest.pass(" picker assigned Successfully for  Customer reference is : " + SOCustReference);
			orderStatus(SOCustReference1, Module.PICKASSIGN);
		}

		// picking

		clickIDLinkText("Picking");
		for (int i = 0; i <= 1; i++) {
			for (int j = 0; j <= 1; j++) {
				verify(PARTTYPE);
				enterXpath( PICKINGSEARCH, so.get(i),"PICKINGSEARCH");
				actionEnter();
				String PartType = xpathGetText(PARTTYPE);
				//shortWait(2000);
				clickXpath( PARTTYPE,"PARTTYPE");
				if (PartType.equals("NVNU")) {
					verify("(//*[contains(.,'Scan LPN ')])[18]","Scan");
					//	enterXpath( SERIALNOFIELD, partnvnu,"lpn");
					//clickXpath( SUBMITBTN,"SUBMITBTN");
					clear1(VQUANTITY);
					enterXpath( VQUANTITY, "2");
					clickXpath( SUBMITBTN,"SUBMITBTN");

					fluentWait(ALERTBOXCANCEL);
					clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
					// driver.navigate().back();
					partStockCheck(partnvnu,  "picking",2);

					Thread.sleep(1000);
				} else {
					verify("(//*[contains(.,'Scan')])[16]","Scan");
					// driver.findElement(By.xpath("(//img[contains(@alt,'o')])[4]")).click();
					shortWait(1000);
					clickXpath( LPNINFOC,"LPNINFOC");


					serialnumber1 = xpathGetText("/html/body/div[2]/div[2]/p/div[2]/table/tbody/tr[2]/td[4]");

					// serialnumber2=driver.findElement(By.xpath("/html/body/div[2]/div[2]/p/div[2]/table/tbody/tr[3]/td[1]")).getText();
					//shortWait(2000);
					lpn.add(serialnumber1);
					clickXpath( LPNCLOSE1,"LPNCLOSE1");
					enterXpath( SERIALNOFIELD, serialnumber1,"lpn");
					clickXpath( SUBMITBTN,"SUBMITBTN");

					Thread.sleep(1000);

					// Second LPN
					clickXpath( LPNINFOC,"LPNINFOC");

					serialnumber2 = xpathGetText("/html/body/div[2]/div[2]/p/div[2]/table/tbody/tr[2]/td[4]");


					lpn.add(serialnumber2);
					clickXpath( LPNCLOSE1,"LPNCLOSE1");
					//shortWait(2000);
					enterXpath( SERIALNOFIELD, serialnumber2);
					//Thread.sleep(1000);
					clickXpath( SUBMITBTN,"SUBMITBTN");
					fluentWait(ALERTBOXCANCEL);
					clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
					partStockCheck(partnv,  "picking",2);
					LPNStatus(serialnumber1,Module.PICKING);
					LPNStatus(serialnumber2,Module.PICKING);
				}

				childTest.pass(	"Picking has been completed successfully." + "Order Customer reference is :" + so.get(i));

			}
			orderStatus(so.get(i), Module.PICKING);
		}

		// Consolidation
		// ---------------------------------------------------------------------------

		// System.out.println(lpn);
		clickIDLinkText("Optimization");
		for (int i = 0; i < 2; i++) {

			//verify(CONSOLIDATION);
			Thread.sleep(2000);
			clear1(CONSOLIDATIONSEARCH);
			Thread.sleep(1000);
			enterXpath(CONSOLIDATIONSEARCH, so.get(i),"CONSOLIDATIONSEARCH");

			clickXpath(OPTISEARCH,"OPTIMIZATIONSEARCH");
			Thread.sleep(1000);
			clickXpath(ORDERCLICK,"ORDERCLICK");
			//		Thread.sleep(3000);
			clickXpath(CONSOLIDATIONSKIP,"CONSOLIDATIONSKIP");
			clickXpath(CONSOLIDATIONYES,"CONSOLIDATIONYES");
			Thread.sleep(3000);
			//closeBrowserTab();
			Thread.sleep(3000);

			clickXpath(ALERT);
			//Thread.sleep(3000);

			childTest.pass(
					"Consolidation has been skipped successfully." + "Order Customer reference is :" + so.get(i));
			orderStatus(so.get(i), Module.CONSOLIDATION);
		}

		// Dispatch Scearch
		// ----------------------------------------------------------------

		clickXpath( DISPATCHGRID,"DISPATCHGRID");
		Thread.sleep(2000);
		//verify(DISPATCHBTN);
		enterXpath(DISPATCHSEARCH, so.get(0),"DISPATCHSEARCH");
		Thread.sleep(3000);
		//actionEnter();

		// ODER
		// CANCELLATION---------------------------------------------------------------------------------------------------------

		childTest = parantTest.createNode("Order Level cancellation- At Dispatch Screen");
		childTest.info(
				"Order level cancellation initiated" + "Order No is  :" + von.get(0) + "Cust Ref is  :" + so.get(0));

		clickIDLinkText("Misc");

		clickIDLinkText("Cancellation");
		verify(CANCEL_LEVEL);
		selectByvalue(CANCEL_LEVEL, 1);
		enterXpath( CANCEL_VONSEARCH, von.get(0));
		enterXpath( CANCEL_SOSEARCH, so.get(0));
		actionEnter();
		childTest.info("The Order is available in order level cancellation screen. Cust Ref is  : " + so.get(0)  +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());
		clickXpath(CANCEL_PART_CHECKBOX);
		selectByVisibleText(CANCEL_REASON, "Customer Cancellation");
		clickXpath( CANCEL_BTN,"Cancel Btn");
		verify("(//*[contains(.,'Successfully')])[20]","Successfully");
		fluentWait(ALERTBOXCANCEL);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		partStockCheck(partnv,  "postcancel",2);
		partStockCheck(partnvnu,  "postcancel",2);

		childTest.pass(" Order level cancellation completed for customer reference is : " +von.get(0));


		// Validation for oder level Cancellation
		// ------------------------------------------------------------------
		clear1(CANCEL_VONSEARCH);
		clear1(CANCEL_SOSEARCH);

		for (int i = 1; i <= 4; i++) {
			selectByvalue(CANCEL_LEVEL, i);
			clear1(CANCEL_SOSEARCH);
			enterXpath( CANCEL_SOSEARCH, so.get(0));

			actionEnter();

		}

		childTest.pass("Verfications has been completed for Order Level. " + MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());
		clickXpath(OUTBOUND);
		clickXpath( DISPATCHGRID);

		enterXpath(DISPATCHSEARCH, so.get(0));
		Thread.sleep(3000);
		//	actionEnter();
		childTest.info("Checking order 1 in Dispatch screen");
		clickIDLinkText("Misc");
		clickIDLinkText("Cancellation");

		//Thread.sleep(2000);

		childTest = parantTest.createNode("Quantity Level cancellation- At Dispatch screen");
		childTest.info(
				"Quantity level cancellation initiated" + "Part No is  :" + partnvnu + "Cust Ref is  :" + so.get(1));

		// QUT
		// CANCELLATION---------------------------------------------------------------------------------
		verify(CANCEL_LEVEL);
		selectByvalue(CANCEL_LEVEL, 4);
		//Thread.sleep(2000);
		enterXpath( CANCEL_VONSEARCH, von.get(1));
		//shortWait(2000);
		enterXpath( CANCEL_SOSEARCH, so.get(1));
		//shortWait(2000);
		enterXpath( CANCEL_PARTSEARCH, partnvnu);
		//shortWait(2000);
		actionEnter();
		childTest.info("The Order is available in Quantity level cancellation screen. Cust Ref is  : " + so.get(1)  +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());
		childTest.info("The Requested Quantity is  :  "+ prop.getProperty("NVNUCANCELQTY")  + "The Cancelled Quantity is " + prop.getProperty("NVNUCANCELQTY"));
		//shortWait(2000);
		enterXpath( CANCEL_QTY_ENTER, prop.getProperty("NVNUCANCELQTY"));
		//Thread.sleep(2000);
		selectByVisibleText(CANCEL_REASON, "Customer Cancellation");
		//Thread.sleep(2000);
		clickXpath( CANCEL_BTN,"Cancel Btn");
		verify("(//*[contains(.,'Successfully')])[20]","Successfully");
		fluentWait(ALERTBOXCANCEL);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		//Thread.sleep(2000);
		partStockCheck(partnvnu,  "postcancel",1);

		childTest.pass(" Quantity level cancellation completed for NVNU part and Cust Ref is  :" + so.get(1));


		for (int i = 2; i <= 4; i++) {
			selectByvalue(CANCEL_LEVEL, i);

			clear1(CANCEL_SOSEARCH);
			enterXpath( CANCEL_SOSEARCH, so.get(1),"CANCEL_SOSEARCH");
			clear1(CANCEL_PARTSEARCH);
			enterXpath( CANCEL_PARTSEARCH, partnvnu,"CANCEL_PARTSEARCH");

			actionEnter();

		}

		childTest.pass("Verifications has been completed for Quantity level. " +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());

		// LPN
		// CANCELLATION----------------------------------------------------------------------------


		childTest = parantTest.createNode("LPN Level cancellation- At Dispatch screen");
		childTest.info(
				"LPN level cancellation initiated" + "LPN No is  :" + lpn.get(2) + "Cust Ref is  :" + so.get(1));

		selectByvalue(CANCEL_LEVEL, 3);

		enterXpath( LPNSCEARCH, lpn.get(2),"LPNSCEARCH");

		enterXpath( CANCEL_VONSEARCH, von.get(1),"CANCEL_VONSEARCH");

		enterXpath( CANCEL_SOSEARCH, so.get(1),"CANCEL_SOSEARCH");

		enterXpath( CANCEL_PARTSEARCH, partnv,"CANCEL_PARTSEARCH");

		actionEnter();

		clickXpath(CANCELLATIONSELECTALL,"Checkbox all");

		selectByVisibleText(CANCEL_REASON, "Customer Cancellation");
		clickXpath( CANCEL_BTN,"Cancel Btn");
		verify("(//*[contains(.,'Successfully')])[20]","Successfully");
		fluentWait(ALERTBOXCANCEL);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		partStockCheck(partnv,  "postcancel",1);


		childTest.pass("LPN Level cancellation has been completed" + lpn.get(2) + "has been cancelled");

		// Validation for LPN level Cancellation
		// ------------------------------------------------------------------

		selectByvalue(CANCEL_LEVEL, 2);
		//Thread.sleep(2000);
		selectByvalue(CANCEL_LEVEL, 3);
		//Thread.sleep(2000);
		//shortWait(2000);
		enterXpath( LPNSCEARCH, lpn.get(2),"LPNSCEARCH");
		//shortWait(2000);
		enterXpath( CANCEL_VONSEARCH, von.get(1),"CANCEL_VONSEARCH");
		//shortWait(2000);
		enterXpath( CANCEL_SOSEARCH, so.get(1),"CANCEL_SOSEARCH");
		//shortWait(2000);
		enterXpath( CANCEL_PARTSEARCH, partnv,"CANCEL_PARTSEARCH");
		//shortWait(2000);
		actionEnter();

		childTest.pass("Verifications has been completed for LPN Level. " +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());

		// PART
		// CANCELLATION--------------------------------------------------------------------------------------------------


		childTest = parantTest.createNode("Part Level cancellation- At Disptach screen");
		childTest.info(
				"part level cancellation initiated" + "Part No is  :" + partnv + "Cust Ref is  :" + so.get(1));
		selectByvalue(CANCEL_LEVEL, 2);
		//Thread.sleep(2000);
		enterXpath( CANCEL_VONSEARCH, von.get(1),"CANCEL_VONSEARCH");
		//shortWait(2000);
		enterXpath( CANCEL_SOSEARCH, so.get(1),"CANCEL_SOSEARCH");
		//shortWait(2000);
		enterXpath( CANCEL_PARTSEARCH, partnv,"CANCEL_PARTSEARCH");
		//shortWait(2000);
		actionEnter();
		//Thread.sleep(2000);
		String partType = xpathGetText(CANCELNVPART);


		/*	if (partType.equals(partnvnu)) {

				clickXpath(CANCELLATIONSELECTALL,"Checkbox all");
				shortWait(2000);*/
		clickXpath(CANCELNVPARTSELECT,"check box nvnu");

		//Thread.sleep(2000);
		selectByVisibleText(CANCEL_REASON, "Customer Cancellation");
		//Thread.sleep(2000);
		clickXpath( CANCEL_BTN,"Cancel Btn");
		verify("(//*[contains(.,'Successfully')])[20]","Successfully");
		fluentWait(ALERTBOXCANCEL);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		partStockCheck(partnv,  "postcancel",1);

		childTest.pass("Part Level cancellation has been completed." + " Part No is  :" + partnv);

		// Validation for PART level Cancellation
		// ------------------------------------------------------------------
		clear1(CANCEL_SOSEARCH);
		for (int i = 2; i <= 4; i++) {
			selectByvalue(CANCEL_LEVEL, i);
			clear1(CANCEL_SOSEARCH);
			enterXpath( CANCEL_SOSEARCH, so.get(1),"Cancel so search");
			clear1(CANCEL_PARTSEARCH);
			enterXpath( CANCEL_PARTSEARCH, partnv,"CANCEL PART SEARCH");
			//shortWait(2000);
			actionEnter();
			//Thread.sleep(2000);
		}

		childTest.pass("Verifications has been completed for Part level. "+ MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());

		// DISPATCH------------------------------------------------------------------------------------------------------------

		childTest = parantTest.createNode("Dispatch against Cancellation performed Order. customer ref. " + so.get(1));
		childTest.info("Dispatch against Cancellation performed Order. customer ref. " + so.get(1));

		clickXpath(OUTBOUND,"OUTBOUND");

		clickXpath( DISPATCHGRID,"DISPATCHGRID");
		Thread.sleep(3000);
		//verify(DISPATCHBTN);
		enterXpath(DISPATCHSEARCH, so.get(1),"DISPATCHSEARCH");
		Thread.sleep(3000);
		//actionEnter();
		//clickXpath(DISPATCHBTN,"DISPATCHBTN");
		clickXpath(Dispatch1,"Dispatch1");
		clickXpath(DISPATCHBUTTON,"Dispatch1");
		verify("(//*[contains(.,' / ')])[23]","1");
		enterXpath(CARRIER, "Testing","CARRIER");
		enterXpath(CARRIERREF, "SmokeTest","CARRIERREF");
		//enterXpath(VEHICLE, "TN38AA1234","VEHICLE");
		enterXpath(SCANLPN, partnvnu,"lpn");
		actionEnter();

		enterXpath("//input[@id='Despatch_enterQty1']", "1","qty");
		actionEnter();
		//clickXpath(FINESH);
		Thread.sleep(3000);

		childTest.pass("Part quanity has been dispatched successfully");
		enterXpath( DISPATCHSEARCH, so.get(1),"DISPATCHSEARCH");
		shortWait(2000);
		actionEnter();
		orderStatus(so.get(1), Module.DISPATCH);
		// Second window
		// close----------------------------------------------------------------------

		//closeBrowserTab();
		shortWait(1500);
		logOut();


	}



}
