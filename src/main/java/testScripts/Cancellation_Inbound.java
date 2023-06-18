package testScripts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

public class Cancellation_Inbound extends ProjectSpecificFunctions{

	
	int PARTIALNVNU = 0;

	@Test(priority = 0)
	public void INB_Cancellation_DockinScreen() throws IOException, Exception {

		parantTest = extent.createTest("Inbound_cancellations");    

		loginAsUser(Username);
		partv="BFIFOV1";
		partnv="BFIFONV1";
		partnvnu="BFIFONVNU1";


		picktype="FIFOCANCEL2";
		part=new String[]{partv,partnv,partnvnu};
		quantity=new String[]{"5","5","5"};	
		// Vehicle Creation -----------------------------------------
		System.out.println("Inbound_cancellation started");

		vehicleCreation(site);
		shortWait(2000);
		vqty();
		nvnuqty();


		// Dockin Screen --------------------------

		childTest = parantTest.createNode("Cancellation - At Dockin screen" );
		clickIDLinkText(DASHBOARD);
		driver.navigate().refresh();

		verify(GATEIN_1stROW);
		enterXpath(SEARCH_BOX, VEHICLENUMBER,"SEARCH_BOX");

		actionEnter();

		childTest.info("Vehicle number is :  " + VEHICLENUMBER);
		VehicleID = xpathGetText(GATEIN_1stROW);
		clickXpath(VEH,"Vehicle");

		childTest = parantTest.createNode("PO Order creation");
		clickXpath(DOCKIN_BTN,"DOCKIN_BTN");
		//upload(2);
		String[] socusref = new String[2];
		writeDataForCSV(picktype,"Newdelpoint","po", part,quantity);

		uploadFIFO("1");
		socusref[0]=CustReference1.get(0);
		socusref[1]=CustReference1.get(1);




		// Finish Upload ------------------------

		shortWait(2000);
		clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
		childTest.pass("Order upload finished");




		childTest = parantTest.createNode("Order level cancellation -  At Dock in screen");
		Thread.sleep(2000);
		clickIDLinkText("Misc");

		clickIDLinkText("Cancellation");
		cancellation(socusref[0], "", "",0 , 1);
		/*childTest.info("Order Level cancellation initiated");
		//clickXpath( "(//b[contains(.,'Cancellation')])[1]","Cancellation");
		selectByvalue(CANCEL_LEVEL, 1);

		childTest.info("The order available in the Cancellation dashboard   :",
				MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());
		verify("(//*[contains(.,'Search')])[23]","Search");
		//clear1(CANCEL_SOSEARCH);
		clickXpath(CLEAR, "CLEAR");
		enterXpath( CANCEL_SOSEARCH, socusref[0],"CANCEL_SOSEARCH");
		actionEnter();
		//Thread.sleep(2000);
		verify("(//*[contains(.,'INB')])[19]");
		clickXpath( CANCEL_PART_CHECKBOX,"CANCEL_PART_CHECKBOX");
		//Thread.sleep(2000);



		selectByVisibleText(CANCEL_REASON, "Customer Cancellation");
		//	Thread.sleep(2000);
		clickXpath( CANCEL_BTN,"CANCEL_BTN");
		verify("(//*[contains(.,'Successfully')])[20]","Successfully");
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		childTest.pass("Order Level Cancellation has been completed" + "  Order cust ref is :   " + socusref[0]);
*/
		for (int i = 1; i <= 4; i++) {
			//shortWait(3000);
			selectByvalue(CANCEL_LEVEL, i);
			enterXpath( CANCEL_SOSEARCH, socusref[0],"CANCEL_SOSEARCH");
			actionEnter();

		}
		childTest.info("Verification complete the cancelled order not listed in the cancellation screen");
		//driver.navigate().refresh();

		//Thread.sleep(3000);

		childTest = parantTest.createNode("Quantity Level cancellation- At dock screen");
		childTest.info("Quantity level cancellation initiated" + "Part No is  :" + partnvnu + "Cust Ref is  :"
				+ socusref[1]);
cancellation(socusref[1], partnvnu, null,3 , 4);
		/*selectByvalue(CANCEL_LEVEL, 4);
		clear1(CANCEL_SOSEARCH);
		//verify("(//*[contains(.,'Search')])[23]","Search");

		enterXpath( CANCEL_SOSEARCH, socusref[1],"CANCEL_SOSEARCH");
		enterXpath( CANCEL_PARTSEARCH, partnvnu,"CANCEL_PARTSEARCH");
		actionEnter();
		enterXpath( CANCEL_QTY_ENTER, "3","CANCEL_QTY_ENTER");
		//Thread.sleep(2000);

		selectByVisibleText(CANCEL_REASON, "Customer Cancellation");
		//Thread.sleep(2000);

		clickXpath( CANCEL_BTN,"CANCEL_BTN");
		//Thread.sleep(3000);
		//verify("(//*[contains(.,'Successfully')])[20]","Successfully");
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
*/
		childTest.pass("Quantity Level Cancellation has been completed");


		childTest = parantTest.createNode("Part Level cancellation- At dock screen");
		childTest.info("part level cancellation initiated");
		cancellation(socusref[1], partnv, "", 0, 2);
		cancellation(socusref[1], partv, "", 0, 2);

		for (int i = 1; i < 3; i++) {/*

			if (i == 1) {
				selectByvalue(CANCEL_LEVEL, 2);
				verify("(//*[contains(.,'Search')])[23]","Search");

				enterXpath( CANCEL_SOSEARCH, socusref[1],"CANCEL_SOSEARCH");
				enterXpath( CANCEL_PARTSEARCH, partnv,"CANCEL_PARTSEARCH");
				actionEnter();
				String partType = xpathGetText(CANCELNVPART);

				if (partType.equals(partnv)) {

					clickXpath(CANCELLATIONSELECTALL,"Checkbox all");
					//clickXpath(CANCELNVNUPARTSELECT,"check box nvnu");
					verify("(//*[contains(.,'INB')])[19]");

				}

				Thread.sleep(2000);

			} else {
				//driver.navigate().refresh();
				//Thread.sleep(3000);
				selectByvalue(CANCEL_LEVEL, 2);
				clear1(CANCEL_SOSEARCH);
				//verify("(//*[contains(.,'Search')])[23]","Search");
				clear1(CANCEL_PARTSEARCH);

				enterXpath( CANCEL_SOSEARCH, socusref[1],"CANCEL_SOSEARCH");
				enterXpath( CANCEL_PARTSEARCH, partv,"CANCEL_PARTSEARCH");
				actionEnter();
				//Thread.sleep(2000);
				verify("(//*[contains(.,'INB')])[19]");

				clickXpath( CANCEL_PART_CHECKBOX,"CANCEL_PART_CHECKBOX");
			}

			//Thread.sleep(2000);
			selectByVisibleText(CANCEL_REASON, "Customer Cancellation");
			//Thread.sleep(3000);
			clickXpath( CANCEL_BTN,"Cancel Btn");
			verify("(//*[contains(.,'Successfully')])[20]","Successfully");
			clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
			childTest.pass("Part Level Cancellation has been completed");
		*/}
		for (int i = 1; i <= 4; i++) {
			//shortWait(3000);
			selectByvalue(CANCEL_LEVEL, i);
			enterXpath( CANCEL_SOSEARCH, socusref[1],"CANCEL_SOSEARCH");
			actionEnter();
			shortWait(4000);

		}

		childTest.pass("Validations are done for Order level, part level, Quantity level.");


		// Assign
		// POD---------------------------------------------------------------
		//clickXpath(INBOUND_MENU,"Inbound");
		scrollUp();
		shortWait(2000);
		clickIDLinkText("Inbound");
		assignpod(PodUser);


		//shortWait(3000);


		childTest.pass("Assign POD has been completed for remaining Quantity for binning.");


		// POD Dashboad--------------------------------------------------------
		podGeneration();


		childTest.pass("POD assigned for remaining Quantity for binning.");



		// Binner Assignment-----------------------------------------

		binnerAssignment(socusref[1],Binner);


		childTest.pass("Binner assigned for remaining Quantity for binning.");



		// Binning----------------------------------------
		childTest = parantTest.createNode("Binning");
		clickIDLinkText(BINNING_MENU);
		binning("", socusref[1], Arrays.asList(new String[]{partnvnu}), new int[]{2});	

		/*	partStockCheck(partnvnu,  "eg",1);

		verify(PARTTYPE_CELL);
		enterXpath(SEARCH_BOX, socusref[1],"SEARCH_BOX");
		clickXpath(INFO_BTN,"Info");
		shortWait(3000);
		String LPNNVNU = driver.findElement(By.xpath(LPN_CELL_1)).getText();
		shortWait(1000);
		clickXpath(INFO_CLOSEBTN,"INFO_CLOSEBTN");
		shortWait(1000);

		clickXpath(PARTTYPE_CELL,"PARTTYPE_CELL");
		//clickXpath(SCANTOBIN_RADIOBTN);
		clickXpath(SCANTOBIN_SUMBIT,"SCANTOBIN_SUMBIT");
		verify("(//*[contains(.,'SCAN')])[18]");
		enterXpath(BIN_TEXT_BOX, prop.getProperty("BIN_LABEL"),"BIN_TEXT_BOX");
		clickXpath(BINNING_SUBMIT_BTN,"Submit");
		//verify("(//*[contains(.,'Scan')])[18]","Scan");
		enterXpath(BIN_TEXT_BOX, LPNNVNU,"BIN_TEXT_BOX");
		//clickXpath(BINNING_SUBMIT_BTN,"Submit");
		enterXpath(NVNUPARTQTY, "2","NVNUPARTQTY");
		clickXpath(BINNING_SUBMIT_BTN,"Submit");
		//verify("(//*[contains(.,'Successfully')])[14]","Successfully");
		partStockCheck(partnvnu,  "binning",2);*/

		//clickXpath(BINNING_CLOSE_BTN,"BINNING_CLOSE_BTN");
		//wait = new WebDriverWait(driver, 20);
		//wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(fluentWait(PARTTYPE_CELL))));
		//verify(PARTTYPE_CELL);
		childTest.pass("Binning has been completed for Remaining quantity.");

		System.out.println("method 1");

	}

	//@Test(priority = 0)
	@Test(dependsOnMethods = { "INB_Cancellation_DuringBinning" })

	//@Test(dependsOnMethods = { "INB_Cancellation_DockinScreen" })	
	public void INB_Cancellation_AfterBinnerAssignment() throws Exception {
		System.out.println("method 2 start");
		//loginAsUser(Username);
		partv="PARTVFIFO";
		partnv="PARTNVFIFO";
		partnvnu="PARTNVNUFIFO";

		picktype="FIFOCANCEL2";
		part=new String[]{partv,partnv,partnvnu};
		quantity=new String[]{"5","5","5"};	
		int NVNUQTY = 0;
		int PARTIALNVNU = 0;
		int PARTIALNVNU1 = 0;

		ArrayList<String> vlpn = new ArrayList<String>();

		// Vehicle Creation -----------------------------------------
		//	parantTest = extent.createTest("Cancellation_AfterBinnerAssignment"); 
		vehicleCreation(site);
		childTest = parantTest.createNode("Cancellation_BinnerAssignment" );
		//po();
		vqty();
		nvnuqty();
		//nvQTY2("SO");

		// Dockin Screen --------------------------

		childTest = parantTest.createNode("Cancellation - At Dockin screen" );
		Thread.sleep(2000);
		clickIDLinkText(DASHBOARD);
		driver.navigate().refresh();

		verify(GATEIN_1stROW);
		enterXpath(SEARCH_BOX, VEHICLENUMBER,"SEARCH_BOX");



		childTest.info("Vehicle number is :  " + VEHICLENUMBER);
		VehicleID = xpathGetText(GATEIN_1stROW);
		clickXpath(VEH,"First record");
		childTest = parantTest.createNode("PO Order creation");
		childTest.pass("Vehicle ID created");
		clickXpath(DOCKIN_BTN,"DOCKIN_BTN");
		//upload(2);
		String[] socusref = new String[2];
		writeDataForCSV(picktype,"Newdelpoint","po", part,quantity);

		uploadFIFO("1");
		socusref[0]=CustReference1.get(0);

		socusref[1]=CustReference1.get(1);



		NVNUQTY = Integer.parseInt(readXL("PO", 2, "QUANTITY"));
		PARTIALNVNU = NVNUQTY / 2;
		PARTIALNVNU1 = NVNUQTY - PARTIALNVNU;
		// Assign POD---------------------------
		assignpod(PodUser);


		//String[] socusref = new String[CustReference.length()];
		//CustRefList.toArray(socusref);
		for (int j = 0; j <= 1; j++) {
			binnerAssignment(socusref[j],Binner);
		}
		childTest = parantTest.createNode("Binning");
		clickIDLinkText(BINNING_MENU);

		for (int i = 0; i <= 1; i++) {

			verify(PARTTYPE_CELL);
			clear1(SEARCH_BOX);

			enterXpath(SEARCH_BOX, socusref[i],"SEARCH_BOX");
			shortWait(3000);

			for (int i1 = 1; i1 <= 3; i1++) {

				String parttype = xpathGetText("(//td[@id='BinnerDashboard_PartType'])[" + i1 + "]");
				shortWait(2000);
				if (parttype.equalsIgnoreCase("V")) {

					shortWait(2000);
					clickXpath( "(//img[@alt='infoicon'])[" + i1 + "]","Info");
					shortWait(2000);
					String LPNV = xpathGetText("(//td[@data-label='LPN'])[1]");
					shortWait(2000);
					vlpn.add(LPNV);
					shortWait(2000);
					clickXpath( "//SPAN[@class='close']","Close");
				} else {

				}
			}
		}

		childTest = parantTest.createNode("Order level cancellation initiated- At binner Assignment screen.");
		Thread.sleep(2000);
		//	clickXpath( "(//b[contains(.,'Misc')])[1]","Misc");
		clickIDLinkText("Misc");
		//Thread.sleep(2000);
		clickIDLinkText("Cancellation");

		//	clickXpath( "(//b[contains(.,'Cancellation')])[1]","SEARCH_BOX");
		// clickIDLinkText(MISC_MENU);
		// clickIDLinkText("Cancellation");

		selectByvalue(CANCEL_LEVEL, 1);
		//verify("(//*[contains(.,'Search')])[23]","Search");
		enterXpath( CANCEL_SOSEARCH, socusref[0],"CANCEL_SOSEARCH");
		// enterXpath(CANCEL_PARTSEARCH,);
		actionEnter();
		verify("(//*[contains(.,'INB')])[19]");
		clickXpath( CANCEL_PART_CHECKBOX,"CANCEL_PART_CHECKBOX");

		//Thread.sleep(2000);
		// childTest.info("Order level cancellation initiated " + " Order
		// cust ref :" + socusref[0]);
		selectByVisibleText(CANCEL_REASON, "Customer Cancellation");
		clickXpath(CANCEL_BTN,"CANCEL_BTN");
		verify("(//*[contains(.,'Successfully')])[20]","Successfully");
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		childTest.pass("Order Level Cancellation has been completed" + "  Order cust ref is :   " + socusref[0]);


		// validation

		for (int i = 1; i <= 4; i++) {
			selectByvalue(CANCEL_LEVEL, i);

			if (i == 3) {
				enterXpath( CANCEL_LPNSEARCH, vlpn.get(0),"CANCEL_LPNSEARCH");
			} else {

				clear1(CANCEL_SOSEARCH);
				enterXpath( CANCEL_SOSEARCH, socusref[0],"CANCEL_SOSEARCH");
			}
			// enterXpath(CANCEL_PARTSEARCH,);
			actionEnter();
			String no = xpathGetText(NORECORD);
			//System.out.println(no);

			// enterXpath("//input[@id='cancelQty_0']", PARTIALNVNU);
			// clickXpath(CANCEL_PART_CHECKBOX);

		}
		childTest.info("Verification complete the cancelled order not listed in the cancellation screen");

		//Thread.sleep(3000);
		clickXpath(INBOUND_MENU,"Inbound");
		//Thread.sleep(2000);

		clickIDLinkText(BINNING_MENU);
		//Thread.sleep(3000);
		verify(PARTTYPE);
		enterXpath( SEARCH_BOX, socusref[0],"SEARCH_BOX");
		//Thread.sleep(2000);
		clickXpath( "//img[@alt='Search_logo']","Search_logo");

		//Thread.sleep(2000);
		clickIDLinkText(BINNERASSIGNMENT_MENU);
		/*
		 * WebElement ab=fluentWait(SEARCH_BOX); a.sendKeys(Keys.CONTROL + "a");
		 * a.sendKeys(Keys.DELETE);
		 */
		//shortWait(2000);
		verify(ROW1);
		enterXpath( SEARCH_BOX, socusref[0],"SEARCH_BOX");
		//actionEnter();
		//Thread.sleep(2000);
		clickXpath( SEARCH_BTN,"SEARCH_BTN");
		//String no1 = xpathGetText(NORECORD);
		//	System.out.println(no1);
		clickIDLinkText(DASHBOARD);
		enterXpath(SEARCH_BOX,VEHICLENUMBER,"Search");
		//Thread.sleep(2000);
		//clickXpath( "(//td[contains(@id,'vehNumber')])[1]");
		//shortWait(3000);
		childTest = parantTest.createNode("Quantity Level cancellation- At Binner Assignment screen");
		childTest.info("Quantity level cancellation initiated");
		clickIDLinkText("Misc");
		clickIDLinkText("Cancellation");

		// childTest.info(" Quantity level cancellation completed " +" Order
		// Cust ref : " + socusref[1]);
		selectByvalue(CANCEL_LEVEL, 4);
		//verify("(//*[contains(.,'Search')])[23]","Search");
		enterXpath( CANCEL_SOSEARCH, socusref[1],"CANCEL_SOSEARCH");
		enterXpath( CANCEL_PARTSEARCH, partnvnu,"CANCEL_PARTSEARCH");
		actionEnter();
		verify("(//*[contains(.,'INB')])[19]");
		enterXpath( CANCEL_QTY_ENTER, "" + PARTIALNVNU,"CANCEL_QTY_ENTER");
		//Thread.sleep(2000);

		selectByVisibleText(CANCEL_REASON, "Customer Cancellation");
		//Thread.sleep(2000);

		clickXpath(CANCEL_BTN,"CANCEL_BTN");
		verify("(//*[contains(.,'Successfully')])[20]","Successfully");
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		childTest.pass("Order Level Cancellation has been completed" + "  Order cust ref is :   " + socusref[1]);


		for (int i = 1; i <= 2; i++) {

			if (i == 1) {
				childTest = parantTest.createNode("LPN Level cancellation- At Binner Assignemnt screen");
				childTest.info("LPN level cancellation initiated");
				selectByvalue(CANCEL_LEVEL, 3);
				//verify("(//*[contains(.,'Search')])[23]","Search");
				enterXpath( CANCEL_SOSEARCH, socusref[1],"CANCEL_SOSEARCH");
				//enterXpath( CANCEL_LPNSEARCH, vlpn.get(1),"CANCEL_LPNSEARCH");
				enterXpath( CANCEL_PARTSEARCH, partv,"CANCEL_PARTSEARCH");
				actionEnter();
				verify("(//*[contains(.,'INB')])[19]");
				clickXpath( CANCEL_PART_CHECKBOX,"CANCEL_PART_CHECKBOX");
				childTest.pass("LPN Level Cancellation has been completed" + "The part Name :   "+partv
						+ " The LPN is :  " + vlpn.get(1));

			} else if (i == 2) {

				childTest = parantTest.createNode("Part Level cancellation- At Binner Assignemnt screen");
				childTest.info("Part level cancellation initiated");
				selectByvalue(CANCEL_LEVEL, 2);
				//verify("(//*[contains(.,'Search')])[23]","Search");
				enterXpath( CANCEL_SOSEARCH, socusref[1],"CANCEL_SOSEARCH");
				enterXpath( CANCEL_PARTSEARCH, partnv,"CANCEL_PARTSEARCH");
				actionEnter();
				verify("(//*[contains(.,'INB')])[19]");
				String partType = xpathGetText(CANCELNVPART);

				if (partType.equals(partnv)) {

					clickXpath(CANCELLATIONSELECTALL,"Checkbox all");
					shortWait(2000);
					//clickXpath(CANCELNVNUPARTSELECT,"check box nvnu");

				}
				childTest.pass("Part Level Cancellation has been completed" + "  The part Name :  " +partnv
						+ " The Cust ref is :  " + socusref[1]);
			}

			//Thread.sleep(2000);
			selectByVisibleText(CANCEL_REASON, "Customer Cancellation");
			clickXpath(CANCEL_BTN,"CANCEL_BTN");
			verify("(//*[contains(.,'Successfully')])[20]","Successfully");
			clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");

		}
		// validation
		selectByvalue(CANCEL_LEVEL, 2);
		clear1(CANCEL_SOSEARCH);
		//shortWait(2000);
		enterXpath( CANCEL_SOSEARCH, socusref[1],"CANCEL_SOSEARCH");
		//shortWait(3000);
		// enterXpath(CANCEL_LPNSEARCH, lpn.get(1));
		enterXpath( CANCEL_PARTSEARCH, partnv,"CANCEL_PARTSEARCH");
		//shortWait(2000);
		actionEnter();
		String no = xpathGetText(NORECORD);
		//System.out.println(no);
		childTest.info("Verification complete the cancelled part not listed in the cancellation screen");

		selectByvalue(CANCEL_LEVEL, 3);
		// enterXpath(CANCEL_VONSEARCH, von.get(1));
		//verify("(//*[contains(.,'Search')])[23]","Search");
		enterXpath( CANCEL_LPNSEARCH, vlpn.get(1),"CANCEL_LPNSEARCH");
		enterXpath( CANCEL_PARTSEARCH, partv,"CANCEL_PARTSEARCH");
		actionEnter();
		no = xpathGetText(NORECORD);
		//System.out.println(no);
		childTest.info("Verification complete the cancelled LPN not listed in the cancellation screen");

		selectByvalue(CANCEL_LEVEL, 4);
		//shortWait(2000);
		enterXpath( CANCEL_SOSEARCH, socusref[1],"CANCEL_SOSEARCH");
		//shortWait(2000);
		// enterXpath(CANCEL_LPNSEARCH, lpn.get(2));
		enterXpath( CANCEL_PARTSEARCH, partnvnu,"CANCEL_PARTSEARCH");
		//	shortWait(2000);
		actionEnter();
		childTest.info("Verification complete the cancelled Quantity not listed in the cancellation screen");

		//Thread.sleep(2000);
		clickIDLinkText("Inbound");
		//Thread.sleep(2000);
		clickIDLinkText(DASHBOARD);
		// enterXpath(SEARCH_BOX, VehicleID);
		verify(GATEIN_1stROW);
		enterXpath(SEARCH_BOX, VEHICLENUMBER,"SEARCH_BOX");



		childTest.info("Vehicle number is :  " + VEHICLENUMBER);

		clickXpath(VEH,"First record");
		lightWait(3000);
		clickXpath(DOCKIN_BTN);
		//driver.findElement(By.xpath(SEARCH_BOX)).sendKeys(VehicleID);		
		childTest.pass("Verification completed - The cancelled items not available in Dockin screen");
		Thread.sleep(3000);
		clickXpath( FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
		Thread.sleep(4000);
		podGeneration();		
		clickIDLinkText(BINNING_MENU);
		//verify(PARTYPE);
		binning("", socusref[1], Arrays.asList(new String[]{partnvnu}), new int[]{PARTIALNVNU1});	

		/*clear1(SEARCH_BOX);
		//shortWait(2000);
		enterXpath( SEARCH_BOX, socusref[1],"SEARCH_BOX");
		//shortWait(2000);
		actionEnter();
		partStockCheck(partnvnu,  "ef",2);

		//String parttype1 = xpathGetText("(//td[contains(@class,'tablerow')][4])[1]");

		//shortWait(2000);
		clickXpath( "(//img[@alt='infoicon'])[1]","Info");
		//shortWait(5000);
		String LPNVNU = xpathGetText("(//td[@data-label='LPN'])[1]");
		// vlpn.add(LPNV);
		//shortWait(2000);
		//System.out.println("LPN:" + LPNVNU);
		clickXpath( "//SPAN[@class='close']","close");
		Thread.sleep(1000);
		clickXpath( PARTTYPE_CELL,"PARTTYPE_CELL");
		//shortWait(1000);
		//clickXpath( SCANTOBIN_RADIOBTN);
		//shortWait(1000);
		clickXpath( SCANTOBIN_SUMBIT,"SCANTOBIN_SUMBIT");
		childTest.pass("Scan To Bin Selected Successfully for NVNU Part Binning");
		verify("(//*[contains(.,'Scan')])[18]","Scan");
		enterXpath( BIN_TEXT_BOX, prop.getProperty("BIN_LABEL"),"BIN_TEXT_BOX");

		clickXpath( BINNING_SUBMIT_BTN,"BINNING_SUBMIT_BTN");
		verify("(//*[contains(.,'Successfully')])[14]","Successfully");

		enterXpath( BIN_TEXT_BOX, LPNVNU,"BIN_TEXT_BOX");
		//clickXpath( BINNING_SUBMIT_BTN,"BINNING_SUBMIT_BTN");

		// robotEnterQuantity(NVNUPARTQTY);
		clear1(NVNUPARTQTY);
		enterXpath( NVNUPARTQTY, "" + PARTIALNVNU1,"NVNUPARTQTY");
		clickXpath( BINNING_SUBMIT_BTN,"BINNING_SUBMIT_BTN");
		partStockCheck(partnvnu,  "binning",PARTIALNVNU1);

		childTest.pass("Binning has been completed for remaining Quantity of NVNU part");
		//verify("(//*[contains(.,'Successfully')])[14]","Successfully");

		//verify("(//td[contains(@class,'tablerow')][4])[2]");

		Thread.sleep(2000);
		clear1(SEARCH_BOX);
		enterXpath(SEARCH_BOX, socusref[1],"SEARCH_BOX");
		actionEnter();
		 */		System.out.println("method 2");


	}

	//@Test(priority = 0)
	@Test(dependsOnMethods = { "INB_Cancellation_DockinScreen" })	
	//@Test(dependsOnMethods = { "INB_Cancellation_AfterBinnerAssignment" })
	public void INB_Cancellation_DuringBinning() throws IOException, Exception {
		System.out.println("method 3 start");
		//f
		partv="PARTVFIFO";
		partnv="PARTNVFIFO";
		partnvnu="PARTNVNUFIFO";

		picktype="FIFOCANCEL2";
		part=new String[]{partv,partnv,partnvnu};
		quantity=new String[]{"5","5","5"};	
		//shortWait(5000);

		// Vehicle Creation -----------------------------------------
		//	parantTest=extent.createTest("Cancellation_DuringBinning");

		vehicleCreation(site);
		childTest = parantTest.createNode("Cancellation_BinningScreen" );
		//shortWait(2000);
		vqty();
		nvnuqty();
		// Dockin Screen --------------------------
		driver.navigate().refresh();

		clickIDLinkText(DASHBOARD);
		//shortWait(4000);

		verify(GATEIN_1stROW);
		enterXpath(SEARCH_BOX, VEHICLENUMBER,"SEARCH_BOX");



		childTest.info("Vehicle number is :  " + VEHICLENUMBER);
		VehicleID = xpathGetText(GATEIN_1stROW);
		clickXpath(VEH,"First record");
		childTest = parantTest.createNode("PO Order creation");

		clickXpath(DOCKIN_BTN,"DOCKIN_BTN");
		//	upload(2);
		String[] socusref = new String[2];
		writeDataForCSV(picktype,"Newdelpoint","po", part,quantity);

		uploadFIFO("1");
		socusref[0]=CustReference1.get(0);

		socusref[1]=CustReference1.get(1);



		// Finish Upload ------------------------


		shortWait(2000);
		clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
		childTest.pass("Order upload finished");


		//		}

		// Assign POD---------------------------
		assignpod(PodUser);

		// POD Generation----------------------------
		podGeneration();

		// Binner Assignment---------------------------------

		//CustRefList.toArray(socusref);
		System.out.println(socusref.length);
		for (String socusref1:socusref) {
			binnerAssignment(socusref1,Binner);
		}


		// Binning screen for Order 1--------------------------
		childTest = parantTest.createNode("Binning");
		clickIDLinkText(BINNING_MENU);
		//shortWait(3000);
		verify(PARTTYPE);
		enterXpath(SEARCH_BOX, socusref[0],"Binning search");
		//shortWait(3000);

		// Cancellation Order level for Order 1---------------------


		childTest = parantTest.createNode("Order level cancellation initiated - At Binning Screen");
		childTest.info("Order level cancellation initiated" + "Cust Ref is  :" + socusref[0]);
		//shortWait(3000);
		//clickXpath(MISCBUTTON);
		clickIDLinkText("Misc");
		//clickXpath(CANCELLATIONBUTTON);
		clickIDLinkText("Cancellation");

		//shortWait(3000);
		// clickXpath(CANCELLATIONAT);
		selectByVisibleText(CANCELLATIONAT, "Order Level");
		fluentWaitxpath(5, 1, CANCELLATIONPOSOTEXTBOX);

		enterXpath(CANCELLATIONPOSOTEXTBOX, socusref[0],"Cancellation So search box");
		clickXpath(CANCELLATIONSEARCHBUTTON,"search");
		fluentWaitxpath(2, 1, CANCELLATIONSELECTALL);
		clickXpath(CANCELLATIONSELECTALL,"Checkbox all");

		selectByVisibleText(CANCELLATIONREASON, "Customer Cancellation");
		clickXpath(CANCELLATIONCANCELORDERBUTTON,"Cancel Btn");
		//shortWait(3000);
		verify("(//*[contains(.,'Successfully')])[20]","Successfully");
		fluentWaitxpath(2, 1, CANCELLATIONPOPUPCLOSE);
		clickXpath(CANCELLATIONPOPUPCLOSE,"close");
		//shortWait(2000);
		childTest.pass("Order Level Cancellation has been completed");


		selectByVisibleText(CANCELLATIONAT, "Part Level");
		//shortWait(1000);
		enterXpath(CANCELLATIONPOSOTEXTBOX, socusref[0]);
		clickXpath(CANCELLATIONSEARCHBUTTON,"search");
		//shortWait(2000);
		selectByVisibleText(CANCELLATIONAT, "LPN Level");
		//shortWait(1000);
		enterXpath(CANCELLATIONPOSOTEXTBOX, socusref[0]);
		clickXpath(CANCELLATIONSEARCHBUTTON,"search");
		//shortWait(2000);
		selectByVisibleText(CANCELLATIONAT, "Quantity Level");
		//shortWait(1000);
		enterXpath(CANCELLATIONPOSOTEXTBOX, socusref[0]);
		clickXpath(CANCELLATIONSEARCHBUTTON,"search");
		//shortWait(2000);
		selectByVisibleText(CANCELLATIONAT, "Order Level");
		//shortWait(1000);
		enterXpath(CANCELLATIONPOSOTEXTBOX, socusref[0]);
		clickXpath(CANCELLATIONSEARCHBUTTON,"search");
		//shortWait(4000);
		//clickXpath(INBOUND_MENU,"Inbound");
		clickIDLinkText("Inbound");
		//shortWait(4000);
		clickIDLinkText(BINNING_MENU);
		//shortWait(2000);
		//clickXpath(SEARCH_BOX);
		//	verify(PARTTYPE);
		enterXpath(SEARCH_BOX, socusref[0],"SEARCH_BOX");
		//shortWait(3000);
		childTest.pass("Verification completed for Order Level");
		// Binning screen for Order 2 for Part Level--------------------------

		//driver.navigate().refresh();
		//	shortWait(3000);


		//	shortWait(3000);

		// Cancellation for part level for order 2----------------------------

		childTest = parantTest.createNode("Part level cancellation initiated - At Binning Screen");
		childTest.info("Part level cancellation initiated" + "Cust Ref is  :" + socusref[1]);
		clickIDLinkText("Misc");
		clickIDLinkText("Cancellation");

		selectByVisibleText(CANCELLATIONAT, "Part Level");
		//shortWait(3000);
		enterXpath(CANCELLATIONPOSOTEXTBOX, socusref[1],"CANCELLATIONPOSOTEXTBOX");
		//shortWait(3000);
		enterXpath(CANCELLATIONPARTNUMTEXTBOX, partnvnu,"CANCELLATION PART NUM TEXTBOX");
		//shortWait(3000);
		actionEnter();
		//clickXpath(CANCELLATIONSEARCHBUTTON,"search");
		verify("(//*[contains(.,'INB')])[19]");
		clickXpath(CANCELLATIONSELECTALL,"Checkbox all");
		//shortWait(3000);
		selectByVisibleText(CANCELLATIONREASON, "Customer Cancellation");
		//	shortWait(3000);
		clickXpath(CANCELLATIONCANCELORDERBUTTON,"Cancel Btn");
		//shortWait(3000);
		verify("(//*[contains(.,'Successfully')])[20]","Successfully");
		fluentWaitxpath(2, 1, CANCELLATIONPOPUPCLOSE);
		clickXpath(CANCELLATIONPOPUPCLOSE,"close");
		childTest.pass("Verification completed for part level. part type = NVNU");


		//shortWait(3000);
		selectByVisibleText(CANCELLATIONAT, "LPN Level");
		//shortWait(1000);
		enterXpath(CANCELLATIONPOSOTEXTBOX, socusref[1]);
		fluentWaitxpath(2, 1, CANCELLATIONPARTNUMTEXTBOX);
		enterXpath(CANCELLATIONPARTNUMTEXTBOX, partnvnu,"CANCELLATION PARTNUM TEXTBOX");
		//shortWait(2000);
		clickXpath(CANCELLATIONSEARCHBUTTON,"search");
		//shortWait(2000);
		selectByVisibleText(CANCELLATIONAT, "Quantity Level");
		//shortWait(1000);
		enterXpath(CANCELLATIONPOSOTEXTBOX, socusref[1]);
		fluentWaitxpath(2, 1, CANCELLATIONPARTNUMTEXTBOX);
		enterXpath(CANCELLATIONPARTNUMTEXTBOX, partnvnu);
		//shortWait(2000);
		clickXpath(CANCELLATIONSEARCHBUTTON,"search");
		//	shortWait(2000);
		selectByVisibleText(CANCELLATIONAT, "Part Level");
		//	shortWait(1000);
		enterXpath(CANCELLATIONPOSOTEXTBOX, socusref[1]);
		//	shortWait(2000);
		enterXpath(CANCELLATIONPARTNUMTEXTBOX, partnvnu);
		//shortWait(2000);
		clickXpath(CANCELLATIONSEARCHBUTTON,"search");
		//	shortWait(2000);
		//clickXpath(INBOUND_MENU,"Inbound");
		clickIDLinkText("Inbound");
		//shortWait(3000);
		clickIDLinkText(BINNING_MENU);

		childTest.pass("Verification completed for Part level");
		// Binning screen for Order 2 for LPN Level--------------------------


		verify(PARTTYPE);
		clear1(SEARCH_BOX);
		enterXpath(SEARCH_BOX, socusref[1]);
		//shortWait(3000);
		clickXpath(BINNINGNVINFO);
		shortWait(3000);
		List<WebElement> lPN = driver.findElements(By.xpath(INBOUNDBINNINGNVLPN));
		String[] NVLPN = new String[Integer.parseInt(readXL("PO", 1, "QUANTITY"))];
		//System.out.println(lPN.size());
		/*for (int j = 0; j < lPN.size(); j++) {
			NVLPN[j] = (lPN.get(j)).getText();
			System.out.println(NVLPN[j]);
		}
		 */int j=0;
		 for(int i=1;i<=Integer.parseInt(readXL("PO", 1, "QUANTITY"));i++)
		 {

			 String NVLPN1 = xpathGetText("(//td[@id='BinnerDashboard_Lpn']//b)["+i+"]");

			 NVLPN[j] = NVLPN1;
			 System.out.println(NVLPN[j]);

			 j++;

		 }

		 clickXpath(INBOUNDBINNINGLPNCLOSE);

		 // Cancellation for LPN level for order 2----------------------------

		 childTest = parantTest.createNode("LPN level cancellation initiated - At Binning Screen");
		 childTest.info("LPN level cancellation initiated" + "Cust Ref is  :" + socusref[1]);
		 shortWait(2000);

		 clickIDLinkText("Misc");
		 clickIDLinkText("Cancellation");


		 for (String name : NVLPN) {


			 selectByVisibleText(CANCELLATIONAT, "LPN Level");
			 //shortWait(3000);
			 clear1(CANCELLATIONLPNTEXTBOX);

			 enterXpath(CANCELLATIONLPNTEXTBOX, name,"CANCELLATIONLPNTEXTBOX");
			 Thread.sleep(1000);
			 actionClick(CANCELLATIONSEARCHBUTTON);
			 /*(CANCELLATIONSEARCHBUTTON,"search");
			  */			verify("(//*[contains(.,'INB')])[19]");
			  verify("(//*[contains(.,'of')])[19]","1");
			  fluentWaitxpath(2, 1, CANCELLATIONSELECTALL);
			  clickXpath(CANCELLATIONSELECTALL,"Checkbox all");
			  //shortWait(3000);
			  selectByVisibleText(CANCELLATIONREASON, "Customer Cancellation");
			  //shortWait(3000);
			  clickXpath(CANCELLATIONCANCELORDERBUTTON,"Cancel Btn");
			  //shortWait(3000);
			  fluentWaitxpath(2, 1, CANCELLATIONPOPUPCLOSE);
			  clickXpath(CANCELLATIONPOPUPCLOSE,"close");
			  childTest.pass("LPN Level Cancellation has been completed" + "LPN Number is " + name);

		 }


		 //shortWait(3000);
		 clickXpath(INBOUND_MENU,"Inbound");
		 //shortWait(3000);
		 clickIDLinkText(BINNING_MENU);
		 //shortWait(2000);
		 //clickXpath(SEARCH_BOX);
		 //shortWait(2000);
		 //	verify(PARTTYPE_CELL);
		 enterXpath(SEARCH_BOX, socusref[1],"SEARCH_BOX");
		 //shortWait(3000);
		 childTest.pass("Verification completed for LPN level");

		 // Cancellation for Quantity level for order
		 // 2----------------------------
		 childTest = parantTest.createNode("Quantity level cancellation initiated - At Binning Screen");
		 childTest.info("Quantity level cancellation initiated" + "Cust Ref is  :" + socusref[1]);
		 shortWait(2000);
		 clickIDLinkText("Misc");
		 shortWait(2000);
		 clickIDLinkText("Cancellation");
		 // clickXpath(CANCELLATIONAT);
		 shortWait(3000);
		 selectByVisibleText(CANCELLATIONAT, "Quantity Level");
		 //shortWait(3000);
		 enterXpath(CANCELLATIONPOSOTEXTBOX, socusref[1],"CANCELLATIONPOSOTEXTBOX");
		 //shortWait(3000);
		 clickXpath(CANCELLATIONSEARCHBUTTON,"search");
		 verify("(//*[contains(.,'INB')])[19]");
		 enterXpath(CANCELLATIONVPARTQUANITYCANCEL, "3");
		 //shortWait(3000);
		 selectByVisibleText(CANCELLATIONREASON, "Customer Cancellation");
		 //shortWait(3000);
		 clickXpath(CANCELLATIONCANCELORDERBUTTON,"Cancel");
		 //shortWait(3000);
		 verify("(//*[contains(.,'Successfully')])[20]","Successfully");
		 fluentWaitxpath(2, 1, CANCELLATIONPOPUPCLOSE);
		 clickXpath(CANCELLATIONPOPUPCLOSE,"close");
		 childTest.pass("Quantity Level Cancellation has been completed");

		 //	shortWait(3000);
		 selectByVisibleText(CANCELLATIONAT, "Order Level");
		 //shortWait(1000);
		 enterXpath(CANCELLATIONPOSOTEXTBOX, socusref[1]);
		 //shortWait(2000);
		 clickXpath(CANCELLATIONSEARCHBUTTON,"search");
		 //shortWait(2000);
		 //	clickXpath(CANCELLATIONSEARCHBUTTON,"search");
		 //	shortWait(2000);
		 selectByVisibleText(CANCELLATIONAT, "LPN Level");
		 //shortWait(1000);
		 enterXpath(CANCELLATIONPOSOTEXTBOX, socusref[1],"CANCELLATIONPOSOTEXTBOX");
		 //shortWait(2000);
		 clickXpath(CANCELLATIONSEARCHBUTTON,"search");
		 //shortWait(2000);
		 clickIDLinkText("Inbound");
		 //clickXpath(INBOUND_MENU,"Inbound");
		 //shortWait(3000);
		 clickIDLinkText(BINNING_MENU);

		 childTest.pass("Verification completed for Quanity Level");
		 // Binning screen for Order 2 Quantity Level--------------------------

		 binning("", socusref[1], Arrays.asList(new String[]{partv}), new int[]{2});	

		 childTest.pass("Binning has been completed for remaining quantity");
		 System.out.println("cancellation inbound completed");
		 // logOut();


	}


}
