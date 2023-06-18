package testScripts;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import testScripts.CommonFunctions.Module;

public class FinolexLiveIssues extends ProjectSpecificFunctions {

	

	public  String partnv2;
    public  String partnv3;
    public  String partnv4;
    public  String partnv5;
    public  String partnv6;
    public  String partnv7;

   String   NVQTN4;
   String	NVQTN5;
   String	NVQTN6;
   String	NVQTN7;

	@Test(priority=4,alwaysRun=true)
	public void finolexscenario_13739() throws IOException, Exception {
		parantTest = extent.createTest("FinolexFlow With API order creation for LIVE ISSUE VD-13739"); 

				partnv="PARTNVFSCUS";

		picktype="CUSTLPNBOXAPI";
		part=new String[]{partnv,partnv,partnv,partnv,partnv,partnv};
		quantity=new String[]{"1","1","1","1","1","1"};
		binPickPart=new String[]{partnv};
		binPickPartquantity=new int[]{6};
		Bin=prop.getProperty("BIN_LABEL");
		site=prop.getProperty("SITEFinolex");
		account=prop.getProperty("ACCOUNTFinolex");
		Binner=prop.getProperty("POD_FINOLEX");
		Picker=prop.getProperty("POD_FINOLEX");
		PodUser=prop.getProperty("POD_FINOLEX");
		Username=prop.getProperty("USERNAMEFinolex");
		System.out.println("Finolex order binning  started");
		loginAsUser(Username);
		vehicleCreation(site);

		writeDataForCSV(picktype,"Newdelpoint","po", part,quantity);
		// writeDataForCSV(picktype,"Newdelpoint","so", part,quantity);
		childTest = parantTest.createNode("PO Order creation for fifo Suggested picktype _ Parallel Dispatch");
		clickIDLinkText(DASHBOARD);
		driver.navigate().refresh();
		childTest.info("Vehicle number is :  " + VEHICLENUMBER);
		verify(GATEIN_1stROW);
		enterXpath(SEARCH_BOX, VEHICLENUMBER,"Dock in searchbox");
		VehicleID = xpathGetText(GATEIN_1stROW);
		clickXpath(VEHICLE1,"Vehicle number");
		clickXpath(DOCKIN_BTN,"DOCKIN_BTN");

		uploadFIFO("ASSIGNORDER");

		childTest.log(Status.PASS,"The uploaded PO parts details for fifo suggested Picktype Return Order: ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		verify(FINISH_UPLOAD_BTN);
		clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
		shortWait(2000);
		childTest.pass("Order upload finished for fifo suggested Picktype _ Finolex");

		assignpod(PodUser);
		podGeneration();
		GRNProcess(CustReference);

		binnerAssignment(CustReference,Binner);
		System.out.println(partnv);
		Thread.sleep(1000);
		binning("binbox",CustReference,Arrays.asList(new String[]{partnv}),binPickPartquantity);
	
		picktype="FIFOSUGGESTEDCUSTLPNBOXAPI";
		part=new String[]{partnv};
		quantity=new String[]{"6"};
		childTest.pass("Verification completed for INBOUND BINNING");
		//orderStatus(CustReference,site, account,Module.BINNING);
		childTest.pass("Binning Completed for fifo suggested Picktype _ Finolex");
		writeDataForCSV(picktype,"Newdelpoint","so", part,quantity);
		verify(OUTBOUND);

		clickXpath("//b[text()='Outbound']","OUTBOUND");
		verify(PICKGENERATION);
		clickIDLinkText("Pick Generation");
		DB(Arrays.asList(binPickPart), binPickPartquantity, "beforepick");

		//pickgenerationFIFO();

		pickerassign1(SOCustReference);
		clickXpath("//b[text()='Outbound Label']","label");
		Label_Reprint(SOCustReference.toLowerCase(), "cartorderlevel");
		//	Label_Reprint("1", "cartlabel");
		cart=new ArrayList<String>();
		cart.add(VLPN.get(0));
		//short pick


		pickingScreenFS("cart",SOCustReference,Arrays.asList(binPickPart), binPickPartquantity);
		//pickingScreenFS("",SOCustReference,Arrays.asList(new String[]{partnv}), new int[]{5});

		dispatchFinolex();

		logOut();
		System.out.println("Finolex live issue -13739 completed");

	}
	
	public ArrayList<String> pickingScreenshort(String type,String SOCustReference,List allpart,int []quantity) throws Exception {

		ArrayList<String> pickedLPNS=new ArrayList<String>();

		//NV				
		deleteFile();


		clickXpath(PICKINGGRID,"PICKINGGRID");

		childTest = parantTest.createNode("Picking");
		NVLPNpicking=new  ArrayList<String>();

		clickXpath("(//td[@data-label='PART TYPE'])");

		childTest.info("NV part picking started");
		if(type.contains("cart"))
		{
			clickXpath(SCAN_CART_LABEL_BTN);
			enterXpath(CART_LABEL_XPATH, cart.get(0),"GENERATED_CART_LABEL");
			actionEnter();
		}
		String PICKINGQTY = xpathGetText(PICKINGVQTN);
		String []picknvqtn1=PICKINGQTY.split("/");
		String []picknvqtn2=picknvqtn1[1].split("\\(");
		String []picknvqtn3=picknvqtn2[1].split("\\(");
		clickXpath(Picking_Info,"INFO");
		//	for(int i1=2;i1<=Integer.parseInt(picknvqtn2[0].trim())+1;i1++)


		for(int i1=2;i1<=3;i1++)
		{

			String NVLPN1picking = xpathGetText("/html/body/div[2]/div[2]/p/div[2]/table/tbody/tr["+i1+"]/td[4]");
			NVLPNpicking.add(NVLPN1picking);
		}
		clickXpath(LPNCLOSE1,"Infoclose");
		//if(serialnumber1==PICKINGLPN)
		//{
		if(!picktype.contains("BOX")||(picktype.contains("BOX")&&picktype.contains("CUSTLPN")))
		{
			for(String apicking:NVLPNpicking)
			{Thread.sleep(500);
			enterXpath(SERIALNOFIELD, apicking,"LPNFIELD");
			pickedLPNS.add(apicking);

			clickXpath(SUBMITBTN,"SUBMITBTN");

			if(picktype.equalsIgnoreCase("serialnumber"))
			{WebElement m= driver.findElement(By.xpath ("(//*[local-name()='svg']/*[local-name()='path'])[2]"));
			// Action class to move and click element
			Actions a1 = new Actions(driver);

			Thread.sleep(2000);
			a1.moveToElement(m).click();

			a1.build().perform();


			}

			//verify("(//*[contains(.,'Successfully')])[16]");


			LPNStatus(apicking,Module.PICKING);


			}
		}
		if(picktype.contains("BOX")&&(!picktype.contains("CUSTLPN")))
		{
			Thread.sleep(500);
			enterXpath(SERIALNOFIELD, nvpartBox,"LPNFIELD");
			clickXpath(SUBMITBTN,"SUBMITBTN");
			pickedLPNS.add(nvpartBox);


		}

		childTest.log(Status.PASS,
				MarkupHelper.createLabel("NV Type part picked sucessfully  :", ExtentColor.GREEN));

		//	Thread.sleep(2000);
		childTest.log(Status.PASS,"Picking Completed for NV part,Alert Box : ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		clickXpath(ALERTBOXCANCEL,"ALERTBOX");
		driver.navigate().refresh();
		clickXpath(PICKINGGRID,"PICKINGGRID");
		enterXpath(PICKINGSEARCH, SOCustReference,"MASTERSEARCH");
		clickXpath(PICKSEARCH, "search");
		//short pick

		//short pick
		clickXpath("(//td[@data-label='PART TYPE'])");

		childTest.info("NV part picking started");
		if(type.contains("cart"))
		{
			clickXpath(SCAN_CART_LABEL_BTN);
			enterXpath(CART_LABEL_XPATH, cart.get(0),"GENERATED_CART_LABEL");
			actionEnter();
		}
		clickXpath("//*[@id='root']/div/div/main/div/div/div/div[3]/div/div/p/form/div[4]/div/b");
		Thread.sleep(1000);
		clickXpath("//*[@id='simple-menu']/div[2]/ul/li[1]");
		Thread.sleep(1000);
		//AlertHandle();
		clickXpath("//*[@id='root']/div/div/main/div/div/div/div[5]/div[2]/div/div/div/div/button[1]/span[1]");
		actionClick(ALERTBOXCANCEL);


		Thread.sleep(1000);
		clickIDLinkText("Misc");
		Thread.sleep(1000);

		clickIDLinkText("Approve Request");
		Thread.sleep(1000);

		verify(SEARCH_BOX);
		clickXpath(SHORTPIC,"Short");
		Thread.sleep(2000);
		enterXpath(MISCSEARCH, SOCustReference,"MISCSEARCH");
		Thread.sleep(1000);
		actionEnter();
		clickXpath(CHECKBOXALL,"CHECKBOXALL");
		selectByVisibleText(APPROVALSELECT,"Approve");


		clickXpath("//button[contains(@id,'buttonSave')]","buttonSave");
		Thread.sleep(2000);

		clickXpath("//SPAN[@class='close'][text()=' × ']","close button");
		Thread.sleep(2000);

		clickIDLinkText("Cancellation");


		childTest = parantTest.createNode("Order Level cancellation- After picking is completed");
		childTest.info("Order level cancellation initiated Cust Ref is  : " + SOCustReference);
		verify(CANCEL_LEVEL);
		selectByvalue(CANCEL_LEVEL, 1);



		enterXpath( CANCEL_SOSEARCH, SOCustReference,"CANCEL_SOSEARCH");

		actionEnter();
		//	shortWait(2000);
		childTest.info("The Order is available in order level cancellation screen. Cust Ref is  : " + SOCustReference  +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());
		//Thread.sleep(2000);
		verify(OUTBOUND);

		clickXpath("//b[text()='Outbound']","OUTBOUND");
		childTest = parantTest.createNode(" Order Dispatch ");
		clickXpath(DISPATCHGRID,"DISPATCHGRID");
		verify("(//*[@id='Despatch_REQUESTNO'])[1]");
		enterXpath(DISPATCHSEARCH, SOCustReference,"DISPATCHSEARCH");
		clickXpath(Dispatch1,"Dispatch1");
		//verify(DISPATCHBUTTON);

		Thread.sleep(2000);
		clickXpath(DISPATCHBUTTON,"Dispatch1");
		//clickXpath(DISPATCHBTN,"DISPATCHBTN");
		//verify("(//*[contains(.,' / ')])[23]","3");
		childTest.info("The order available in Dispatch Screen");

		enterXpath(CARRIER, "Testing","CARRIER");
		childTest.info("The Mode of carrier is :" + "Testing");
		enterXpath(CARRIERREF, "SmokeTest","CARRIERREF");
		childTest.info("The carrier reference is :" + "SmokeTest");
		// enterXpath("//*[@id=\"Despatch_entervehicle\"]/div/div[1]", VEHICLENUMBER,"VEHICLE");


		//childTest.info("The Vehicle Number is :" + "TN38AA1234");

		///enterXpath(SCANLPN, CartLabel,"SCANLPN");


		Thread.sleep(500);
		String []NVLPNpickingFino = new String[NVLPNpicking.size()];
		NVLPNpicking.toArray(NVLPNpickingFino);
		enterXpath(SCANLPN,NVLPNpickingFino[0] ,"LPNFIELD");

		//verify("(//*[contains(.,'Successfully')])[16]");

		actionEnter();
		Thread.sleep(1000);
		enterXpath(SCANLPN, NVLPNpickingFino[1],"LPNFIELD");

		//verify("(//*[contains(.,'Successfully')])[16]");

		actionEnter();

		Thread.sleep(1000);
		//clickXpath(Partial_Finish,"partialDispatch");

		Thread.sleep(1000);
		DB(allpart, quantity, "picking");
		orderStatus(SOCustReference, Module.PICKING);
		return pickedLPNS;

	}
	public ArrayList<String> pickingScreenshort1(String type,String SOCustReference,List allpart,int []quantity) throws Exception {

		ArrayList<String> pickedLPNS=new ArrayList<String>();

		//NV				
		deleteFile();


		clickXpath(PICKINGGRID,"PICKINGGRID");

		childTest = parantTest.createNode("Picking");
		NVLPNpicking=new  ArrayList<String>();
		for(int i1=1;i1<=4;i1++){
			driver.navigate().refresh();
			clickXpath("(//td[@data-label='PART TYPE'])");

			childTest.info("NV part picking started");
			if(type.contains("cart"))
			{
				clickXpath(SCAN_CART_LABEL_BTN);
				enterXpath(CART_LABEL_XPATH, cart.get(0),"GENERATED_CART_LABEL");
				actionEnter();
			}
			String PICKINGQTY = xpathGetText(PICKINGVQTN);
			String []picknvqtn1=PICKINGQTY.split("/");
			String []picknvqtn2=picknvqtn1[1].split("\\(");
			String []picknvqtn3=picknvqtn2[1].split("\\(");
			clickXpath(Picking_Info,"INFO");
			//	for(int i1=2;i1<=Integer.parseInt(picknvqtn2[0].trim())+1;i1++)
			NVLPNpicking.clear();

			for(int i2=2;i2<=3;i2++)
			{

				String NVLPN1picking = xpathGetText("/html/body/div[2]/div[2]/p/div[2]/table/tbody/tr["+i2+"]/td[4]");
				NVLPNpicking.add(NVLPN1picking);
			}
			clickXpath(LPNCLOSE1,"Infoclose");
			//if(serialnumber1==PICKINGLPN)
			//{
			if(!picktype.contains("BOX")||(picktype.contains("BOX")&&picktype.contains("CUSTLPN")))
			{
				for(String apicking:NVLPNpicking)
				{Thread.sleep(500);
				enterXpath(SERIALNOFIELD, apicking,"LPNFIELD");
				pickedLPNS.add(apicking);

				clickXpath(SUBMITBTN,"SUBMITBTN");

				if(picktype.equalsIgnoreCase("serialnumber"))
				{WebElement m= driver.findElement(By.xpath ("(//*[local-name()='svg']/*[local-name()='path'])[2]"));
				// Action class to move and click element
				Actions a1 = new Actions(driver);

				Thread.sleep(2000);
				a1.moveToElement(m).click();

				a1.build().perform();


				}

				//verify("(//*[contains(.,'Successfully')])[16]");


				LPNStatus(apicking,Module.PICKING);


				}
			}

			if(picktype.contains("BOX")&&(!picktype.contains("CUSTLPN")))
			{
				Thread.sleep(500);
				enterXpath(SERIALNOFIELD, nvpartBox,"LPNFIELD");
				clickXpath(SUBMITBTN,"SUBMITBTN");
				pickedLPNS.add(nvpartBox);


			}
		}
		childTest.log(Status.PASS,
				MarkupHelper.createLabel("NV Type part picked sucessfully  :", ExtentColor.GREEN));

		//	Thread.sleep(2000);
		childTest.log(Status.PASS,"Picking Completed for NV part,Alert Box : ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		clickXpath(ALERTBOXCANCEL,"ALERTBOX");
		driver.navigate().refresh();
		clickXpath(PICKINGGRID,"PICKINGGRID");

		for(int i3=1;i3<=4;i3++)
		{
			enterXpath(PICKINGSEARCH, SOCustReference,"MASTERSEARCH");
			clickXpath(PICKSEARCH, "search");
			//short pick

			//short pick
			clickXpath("(//td[@data-label='PART TYPE'])");

			childTest.info("NV part picking started");
			if(type.contains("cart"))
			{
				clickXpath(SCAN_CART_LABEL_BTN);
				enterXpath(CART_LABEL_XPATH, cart.get(0),"GENERATED_CART_LABEL");
				actionEnter();
			}
			clickXpath("//*[@id='root']/div/div/main/div/div/div/div[3]/div/div/p/form/div[4]/div/b");
			Thread.sleep(1000);
			clickXpath("//*[@id='simple-menu']/div[2]/ul/li[1]");
			Thread.sleep(1000);
			//AlertHandle();
			clickXpath("//*[@id='root']/div/div/main/div/div/div/div[5]/div[2]/div/div/div/div/button[1]/span[1]");
			actionClick(ALERTBOXCANCEL);


			Thread.sleep(1000);
			clickIDLinkText("Misc");
			clickIDLinkText("Approve Request");
			
			ApproveRequest("shortpickapprove",SOCustReference);
			/*clickIDLinkText("Misc");
			Thread.sleep(1000);

			clickIDLinkText("Approve Request");
			Thread.sleep(1000);

			verify(SEARCH_BOX);
			clickXpath(SHORTPIC,"Short");
			Thread.sleep(2000);
			enterXpath(MISCSEARCH, SOCustReference,"MISCSEARCH");
			Thread.sleep(1000);
			actionEnter();
			clickXpath(CHECKBOXALL,"CHECKBOXALL");
			selectByVisibleText(APPROVALSELECT,"Approve");


			clickXpath("//button[contains(@id,'buttonSave')]","buttonSave");
			Thread.sleep(2000);

			clickXpath("//SPAN[@class='close'][text()=' × ']","close button");
			Thread.sleep(2000);*/
			verify(OUTBOUND);

			clickXpath("//b[text()='Outbound']","OUTBOUND");
			clickXpath(PICKINGGRID,"PICKINGGRID");

		}
		clickIDLinkText("Misc");
		Thread.sleep(1000);
		clickIDLinkText("Cancellation");


		childTest = parantTest.createNode("Order Level cancellation- After picking completed");
		childTest.info("Order level cancellation initiated Cust Ref is  : " + SOCustReference);
		verify(CANCEL_LEVEL);
		selectByvalue(CANCEL_LEVEL, 1);



		enterXpath( CANCEL_SOSEARCH, SOCustReference,"CANCEL_SOSEARCH");

		actionEnter();
		//	shortWait(2000);
		childTest.info("The Order is available in order level cancellation screen. Cust Ref is  : " + SOCustReference  +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());
		//Thread.sleep(2000);
		verify(OUTBOUND);

		clickXpath("//b[text()='Outbound']","OUTBOUND");
		childTest = parantTest.createNode(" Order Dispatch ");
		clickXpath(DISPATCHGRID,"DISPATCHGRID");
		
		return pickedLPNS;

						
	}

	@Test(priority=5,alwaysRun=true)
	public void finolexscenario_13438() throws IOException, Exception {
		
		partnv="PARTNVFSCUS";
		partnv1="PARTNVFSCUS1";

		picktype="CUSTLPNBOXAPI";
		part=new String[]{partnv,partnv,partnv1,partnv1};
		quantity=new String[]{"1","1","1","1"};
		binPickPart=new String[]{partnv,partnv1};
		binPickPartquantity=new int[]{2,2};
		Bin=prop.getProperty("BIN_LABEL");
		site=prop.getProperty("SITEFinolex");
		account=prop.getProperty("ACCOUNTFinolex");
		Binner=prop.getProperty("POD_FINOLEX");
		Picker=prop.getProperty("POD_FINOLEX");
		PodUser=prop.getProperty("POD_FINOLEX");
		Username=prop.getProperty("USERNAMEFinolex");
		parantTest = extent.createTest("FinolexFlow With API order creation for LIVE ISSUE VD-13438"); 
		System.out.println("Finolex order binning  started");
		loginAsUser(Username);
		vehicleCreation(site);

		writeDataForCSV(picktype,"Newdelpoint","po", part,quantity);
		// writeDataForCSV(picktype,"Newdelpoint","so", part,quantity);
		childTest = parantTest.createNode("PO Order creation for fifo Suggested picktype _ Parallel Dispatch");
		clickIDLinkText(DASHBOARD);
		driver.navigate().refresh();
		childTest.info("Vehicle number is :  " + VEHICLENUMBER);
		//verify(GATEIN_1stROW);
		enterXpath(SEARCH_BOX, VEHICLENUMBER,"Dock in searchbox");
		VehicleID = xpathGetText(GATEIN_1stROW);
		clickXpath(VEHICLE1,"Vehicle number");
		clickXpath(DOCKIN_BTN,"DOCKIN_BTN");

		uploadFIFO("ASSIGNORDER");

		childTest.log(Status.PASS,"The uploaded PO parts details for fifo suggested Picktype Return Order: ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		verify(FINISH_UPLOAD_BTN);
		clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
		shortWait(2000);
		childTest.pass("Order upload finished for fifo suggested Picktype _ Finolex");

		assignpod(PodUser);
		podGeneration();
		GRNProcess(CustReference);

		binnerAssignment(CustReference,Binner);

		System.out.println(partnv);
		Thread.sleep(1000);
		binning("binbox",CustReference,Arrays.asList(new String[]{partnv,partnv1}),binPickPartquantity);
		
		picktype="FIFOSUGGESTEDCUSTLPNBOXAPI";
		part=new String[]{partnv,partnv1};
		quantity=new String[]{"2","2"};
		binPickPart=new String[]{partnv};
		binPickPartquantity=new int[]{2};
		childTest.pass("Verification completed for INBOUND BINNING");
		//orderStatus(CustReference,site, account,Module.BINNING);
		childTest.pass("Binning Completed for fifo suggested Picktype _ Finolex");
		writeDataForCSV(picktype,"Newdelpoint","so", part,quantity);
		verify(OUTBOUND);

		clickXpath("//b[text()='Outbound']","OUTBOUND");
		verify(PICKGENERATION);
		clickIDLinkText("Pick Generation");
		DB(Arrays.asList(binPickPart), binPickPartquantity, "beforepick");

		//pickgenerationFIFO();

		pickerassign1(SOCustReference);
		clickXpath("//b[text()='Outbound Label']","label");
		Label_Reprint(SOCustReference.toLowerCase(), "cartorderlevel");
		//	Label_Reprint("1", "cartlabel");
		cart=new ArrayList<String>();
		cart.add(VLPN.get(0));
	pickingScreenshort("cart",SOCustReference,Arrays.asList(binPickPart), binPickPartquantity);



		logOut();

	}
	@Test(priority=6,alwaysRun=true)
	public void finolexOrder_13018() throws IOException, Exception {
		NVLPNpicking=new  ArrayList<String>();
		Lpn = new HashMap<String ,String>();

		partnv="PARTNVFSCUS";
		picktype="CUSTLPNBOXAPI";


		part=new String[]{partnv,partnv,partnv,partnv};
		quantity=new String[]{"1","1","1","1"};
		binPickPart=new String[]{partnv};
		binPickPartquantity=new int[]{4};
		Bin=prop.getProperty("BIN_LABEL");
		site=prop.getProperty("SITEFinolex");
		account=prop.getProperty("ACCOUNTFinolex");
		Binner=prop.getProperty("POD_FINOLEX");
		Picker=prop.getProperty("POD_FINOLEX");
		PodUser=prop.getProperty("POD_FINOLEX");
		Username=prop.getProperty("USERNAMEFinolex");

		parantTest = extent.createTest("Live Issue: VD-13018_Unable to Re-Bin the Postpick cancelled LPNs for Finolex"); 
		//site="IN18698";
		//System.out.println("Finolex order binning  started");
		loginAsUser(Username);
		vehicleCreation(site);

		writeDataForCSV(picktype,"","po", part,quantity);

		childTest = parantTest.createNode("PO-API Order creation for FifoSuggested with CustLPN picktype _ Finolex");
		clickIDLinkText(DASHBOARD);
		driver.navigate().refresh();
		childTest.info("Vehicle number is :  " + VEHICLENUMBER);
		//verify(GATEIN_1stROW);
		enterXpath(SEARCH_BOX, VEHICLENUMBER,"Dock in searchbox");
		VehicleID = xpathGetText(GATEIN_1stROW);
		clickXpath(VEHICLE1,"Vehicle number");
		clickXpath(DOCKIN_BTN,"DOCKIN_BTN");

		uploadFIFO("ASSIGNORDER");

		childTest.log(Status.PASS,"The Uploaded PO parts details for FifoSuggested with CustLPN Picktype _ Finolex: ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		verify(FINISH_UPLOAD_BTN);
		clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
		shortWait(2000);
		childTest.pass("Order upload finished for FifoSuggested with CustLPN Picktype _ Finolex");

		assignpod(PodUser);
		podGeneration();
		GRNProcess(CustReference);

		binnerAssignment(CustReference,Binner);
		System.out.println(partnv);
		Thread.sleep(1000);
		binning("binbox",CustReference,Arrays.asList(new String[]{partnv}),binPickPartquantity);
	//	site="IN18698";

		picktype="FIFOSUGGESTEDCUSTLPNBOXAPI";
		part=new String[]{partnv};
		quantity=new String[]{"4"};
		childTest.pass("Verification completed for INBOUND BINNING");
		//orderStatus(CustReference,site, account,Module.BINNING);
		childTest.pass("Binning Completed for Fifosuggested with CustLPN Picktype _ Finolex");
		childTest = parantTest.createNode("SO-API Order creation for FifoSuggested with CustLPN picktype _ Finolex");

		writeDataForCSV(picktype,"Newdelpoint","so", part,quantity);
		verify(OUTBOUND);

		clickXpath("//b[text()='Outbound']","OUTBOUND");
		verify(PICKGENERATION);
		clickIDLinkText("Pick Generation");
		DB(Arrays.asList(binPickPart), binPickPartquantity, "beforepick");

		//pickgenerationFIFO();

		pickerassign1(SOCustReference);
		clickXpath("//b[text()='Outbound Label']","label");
		Label_Reprint(SOCustReference.toLowerCase(), "cartorderlevel");

		cart=new ArrayList<String>();
		cart.add(VLPN.get(0));

		PickedNVLPNS=pickingScreenFS("cart",SOCustReference,Arrays.asList(binPickPart), binPickPartquantity);
		String cancelNVlpn=PickedNVLPNS.get(0);
		System.out.println("NVLPN: "+cancelNVlpn);
		//pickingScreenFS("",SOCustReference,Arrays.asList(new String[]{partnv}), new int[]{5});
		Thread.sleep(2000);
		childTest = parantTest.createNode("LPN Level Cancellation for FifoSuggested with CustLPN picktype in TAB1_ Finolex");
		clickIDLinkText("Misc");
		clickIDLinkText("Cancellation");
		
		//cancellation(SOCustReference, "", cancelNVlpn,0 , 3);
//clear1("");
		verify(CANCELLATIONAT);
		String Cancellationurl=driver.getCurrentUrl();
		selectByVisibleText(CANCELLATIONAT, "LPN Level");
		fluentWaitxpath(2, 1, CANCEL_SOSEARCH);
		enterXpath("//*[@placeholder='LPN/Customer LPN']",cancelNVlpn);
		shortWait(1000);
		clickXpath(CANCELLATIONSEARCHBUTTON,"search");
		childTest.info("The Order is available in LPN level cancellation screen. Cancelled LPN is  : " +cancelNVlpn  +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());

		fluentWaitxpath(2, 1, CANCELLATIONSELECTALL);
		clickXpath(CANCELLATIONSELECTALL,"Checkbox all");
		shortWait(2000);
		selectByVisibleText(CANCELLATIONREASON, "Customer Cancellation");
		childTest = parantTest.createNode("LPN Level Cancellation for FifoSuggested with CustLPN picktype in TAB2_ Finolex");
		String parentTab=driver.getWindowHandle();
		System.out.println(parentTab);
		shortWait(3000);
		((JavascriptExecutor)driver).executeScript("window.open();");

	  
	
		shortWait(5000);
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		System.out.println("Tabs"+tabs.size()+ "and"+tabs);
		shortWait(2000);
		driver.switchTo().window(tabs.get(1));
		shortWait(2000);
	
		driver.get(Cancellationurl);
		shortWait(2000);
		verify(CANCELLATIONAT);
		selectByVisibleText(CANCELLATIONAT, "LPN Level");
		fluentWaitxpath(2, 1, CANCEL_SOSEARCH);
		enterXpath("//*[@placeholder='LPN/Customer LPN']",cancelNVlpn);
		shortWait(1000);
		clickXpath(CANCELLATIONSEARCHBUTTON,"search");
		childTest.info("The Order is available in LPN level cancellation screen. Cancelled LPN is  : " +cancelNVlpn  +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());

		fluentWaitxpath(2, 1, CANCELLATIONSELECTALL);
		clickXpath(CANCELLATIONSELECTALL,"Checkbox all");
		shortWait(2000);
		selectByVisibleText(CANCELLATIONREASON, "Data Entry Error");
		shortWait(2000);

		clickXpath(CANCELLATIONCANCELORDERBUTTON,"Cancel Btn");
		//shortWait(3000);
		fluentWaitxpath(2, 1, CANCELLATIONPOPUPCLOSE);
		clickXpath(CANCELLATIONPOPUPCLOSE,"close");

		driver.close();
		Thread.sleep(1000);
		childTest.pass("LPN level cancellation completed for Customer reference is : " + SOCustReference);
		driver.switchTo().window(tabs.get(0));
	   Thread.sleep(1000);


		clickXpath(CANCELLATIONCANCELORDERBUTTON,"Cancel Btn");
		//shortWait(3000);
		fluentWaitxpath(2, 1, CANCELLATIONPOPUPCLOSE);
		clickXpath(CANCELLATIONPOPUPCLOSE,"close");
		shortWait(2000);
		clickIDLinkText("Inbound");
		binnerAssignment(SOCustReference,Binner);
		binning("",SOCustReference,Arrays.asList(new String[]{partnv}), new int[]{1});
		childTest.pass("ReBinning Completed for Fifosuggested with CustLPN Picktype Cancelled LPNS _ Finolex");
		Thread.sleep(1000);
		clear1(SEARCH_BOX);
		enterXpath(SEARCH_BOX, SOCustReference,"SEARCH_BOX");
		verify("(//*[contains(.,'0 of 0')])[16]");

		childTest.log(Status.PASS,"Validation Completed in Binning Screen : ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );

		childTest.pass("Verification completed for INBOUND BINNING");
		//orderStatus(CustReference,prop.getProperty("SITEIDFINOLEX"), prop.getProperty("ACCOUNTIDFINOLEX"),Module.BINNING);
		logOut();

	
}

	@Test(priority=7,alwaysRun=true)
	public void finolexscenario_13460() throws IOException, Exception {
	
		partnv="PARTNVFSCUS";
		partnv1="PARTNVFSCUS1";
		partnv2="PARTNVFSCUS2";
		partnv3="PARTNVFSCUS3";
		partnv4="PARTNVFSCUS4";
		partnv5="PARTNVFSCUS5";
		partnv6="PARTNVFSCUS6";
		partnv7="PARTNVFSCUS7";
		picktype="CUSTLPNBOXAPI";
		
		part=new String[]{partnv,partnv,partnv1,partnv1,partnv2,partnv2,partnv3,partnv3,partnv4,partnv4,partnv5,partnv5,partnv6,partnv6,partnv7,partnv7};
		quantity=new String[]{"1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1"};
		binPickPart=new String[]{partnv,partnv1,partnv2,partnv3,partnv4,partnv5,partnv6,partnv7};
		binPickPartquantity=new int[]{2,2,2,2,2,2,2,2};
		Bin=prop.getProperty("BIN_LABEL");
		site=prop.getProperty("SITEFinolex");
		account=prop.getProperty("ACCOUNTFinolex");
		Binner=prop.getProperty("POD_FINOLEX");
		Picker=prop.getProperty("POD_FINOLEX");
		PodUser=prop.getProperty("POD_FINOLEX");
		Username=prop.getProperty("USERNAMEFinolex");
		parantTest = extent.createTest("FinolexFlow With API order creation for LIVE ISSUE VD-13460"); 
		System.out.println("Finolex order binning  started");
		loginAsUser(Username);
		vehicleCreation(site);

		writeDataForCSV(picktype,"Newdelpoint","po", part,quantity);
		// writeDataForCSV(picktype,"Newdelpoint","so", part,quantity);
		childTest = parantTest.createNode("PO Order creation for fifo Suggested picktype _ Parallel Dispatch");
		clickIDLinkText(DASHBOARD);
		driver.navigate().refresh();
		childTest.info("Vehicle number is :  " + VEHICLENUMBER);
		//verify(GATEIN_1stROW);
		enterXpath(SEARCH_BOX, VEHICLENUMBER,"Dock in searchbox");
		VehicleID = xpathGetText(GATEIN_1stROW);
		clickXpath(VEHICLE1,"Vehicle number");
		clickXpath(DOCKIN_BTN,"DOCKIN_BTN");

		uploadFIFO("ASSIGNORDER");

		childTest.log(Status.PASS,"The uploaded PO parts details for fifo suggested Picktype Return Order: ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		verify(FINISH_UPLOAD_BTN);
		clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
		shortWait(2000);
		childTest.pass("Order upload finished for fifo suggested Picktype _ Finolex");

		assignpod(PodUser);
		podGeneration();
		GRNProcess(CustReference);

		binnerAssignment(CustReference,Binner);
		System.out.println(partnv);
		Thread.sleep(1000);
		binning("binbox",CustReference,Arrays.asList(binPickPart),binPickPartquantity);
		
		picktype="FIFOSUGGESTEDCUSTLPNBOXAPI";
		part=new String[]{partnv,partnv1,partnv2,partnv3,partnv4,partnv5,partnv6,partnv7};
		quantity=new String[]{"2","2","2","2","2","2","2","2"};
		binPickPart=new String[]{partnv,partnv1,partnv2,partnv3,partnv4,partnv5,partnv6,partnv7};
		binPickPartquantity=new int[]{2,2,2,2,2,2,2,2};
		childTest.pass("Verification completed for INBOUND BINNING");
		//orderStatus(CustReference,site, account,Module.BINNING);
		childTest.pass("Binning Completed for fifo suggested Picktype _ Finolex");
		quantity=new String[]{"2","2","2","2","2",""+(Available.get(partnv5)+1),""+(Available.get(partnv6)+1),""+(Available.get(partnv7)+1)};	

		writeDataForCSV(picktype,"Newdelpoint","so", part,quantity);

		NVQTN4=Available.get(partnv4)+"";
		NVQTN5=Available.get(partnv5)+"";
		NVQTN6=Available.get(partnv6)+"";
		NVQTN7=Available.get(partnv7)+"";

		verify(OUTBOUND);
		clickXpath(OUTBOUND,"OUTBOUND");

		Thread.sleep(2000);
		clickIDLinkText("Misc");
		clickIDLinkText("Approve Request");
		
		ApproveRequest("Accept Order with Part Shortage",SOCustReference);
	//	tempApprove("Accept Order with Part Shortage");
		Thread.sleep(2000);
		clickXpath(OUTBOUND,"OUTBOUND");
		verify(PICKGENERATION);
		clickIDLinkText("Pick Generation");
			pickerassign1(SOCustReference);

		 binPickPartquantity=new int[]{2,2,2,2,Integer.parseInt(NVQTN4),Integer.parseInt(NVQTN5),Integer.parseInt(NVQTN6),Integer.parseInt(NVQTN7)};
		 clickXpath("//b[text()='Outbound Label']","label");
		 Label_Reprint(SOCustReference.toLowerCase(), "cartorderlevel");
		 //	Label_Reprint("1", "cartlabel");
		 cart=new ArrayList<String>();
		 cart.add(VLPN.get(0));
		 pickingScreenshort1("cart",SOCustReference,Arrays.asList(binPickPart), binPickPartquantity);

		 //pickingScreenFS();

		 String SOcustreftemp=SOCustReference;
		 // quantity=new String[]{""+VQTY,""+NvQtn,""+NVNUQtn};
		 SOCustReference=SOcustreftemp;
		 

		 bulkDispatch(SOCustReference);
		
		 DB(Arrays.asList(binPickPart), binPickPartquantity, "beforepick");

		 //pickgenerationFIFO();

		 logOut();
		 System.out.println("Finolex live issue -13460 completed");

	}
	@Test(priority=8,alwaysRun=true)
	public void finolexOrder_13453() throws IOException, Exception {
		
		partnv="PARTNVFSCUS";
		picktype="CUSTLPNBOXAPI";
		
		
		part=new String[]{partnv,partnv,partnv};
		quantity=new String[]{"1","1","1"};
		binPickPart=new String[]{partnv};
		binPickPartquantity=new int[]{3};
		Bin=prop.getProperty("BIN_LABEL");
		site=prop.getProperty("SITEFinolex");
		account=prop.getProperty("ACCOUNTFinolex");
		Binner=prop.getProperty("USERNAMEFinolex");
		Picker=prop.getProperty("USERNAMEFinolex");
		PodUser=prop.getProperty("USERNAMEFinolex");
		Username=prop.getProperty("USERNAMEFinolex");
		parantTest = extent.createTest("Live Issue:VD-13453_Unable to do Return order for Finolex"); 
		//System.out.println("Finolex order binning  started");
		loginAsUser(Username);
		vehicleCreation(site);

		writeDataForCSV(picktype,"Newdelpoint","po", part,quantity);

		childTest = parantTest.createNode("PO-API Order creation for FifoSuggested with CustLPN picktype _ Finolex");
		clickIDLinkText(DASHBOARD);
		driver.navigate().refresh();
		childTest.info("Vehicle number is :  " + VEHICLENUMBER);
		
		enterXpath(SEARCH_BOX, VEHICLENUMBER,"Dock in searchbox");
		VehicleID = xpathGetText(GATEIN_1stROW);
		clickXpath(VEHICLE1,"Vehicle number");
		clickXpath(DOCKIN_BTN,"DOCKIN_BTN");

		uploadFIFO("ASSIGNORDER");

		childTest.log(Status.PASS,"The uploaded PO parts details for FifoSuggested with CustLPN Picktype: ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		verify(FINISH_UPLOAD_BTN);
		clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
		shortWait(2000);
		childTest.pass("Order upload finished for FifoSuggested with CustLPN Picktype _ Finolex");

		assignpod(PodUser);
		podGeneration();
		GRNProcess(CustReference);

		binnerAssignment(CustReference,Binner);
		System.out.println(partnv);
		Thread.sleep(1000);
		binning("binbox",CustReference,Arrays.asList(new String[]{partnv}),binPickPartquantity);
		childTest.pass("Binning Completed for FifoSuggested with CustLPN Picktype _ Finolex");
		childTest.pass("Verification completed for INBOUND BINNING");
		
		picktype="FIFOSUGGESTEDCUSTLPNBOXAPI";
		part=new String[]{partnv};
		quantity=new String[]{"3"};
	
		//orderStatus(CustReference,site, account,Module.BINNING);
		childTest = parantTest.createNode("SO-API Order creation for FifoSuggested with CustLPN picktype _ Finolex");
		writeDataForCSV(picktype,"Newdelpoint","so", part,quantity);
		verify(OUTBOUND);

		clickXpath("//b[text()='Outbound']","OUTBOUND");
		verify(PICKGENERATION);
		clickIDLinkText("Pick Generation");
		DB(Arrays.asList(binPickPart), binPickPartquantity, "beforepick");

	

		pickerassign1(SOCustReference);
		clickXpath("//b[text()='Outbound Label']","label");
		Label_Reprint(SOCustReference, "cartorderlevel");
	
		cart=new ArrayList<String>();
		cart.add(VLPN.get(0));
					
		pickingScreenFS("cart",SOCustReference,Arrays.asList(binPickPart), binPickPartquantity);
		//pickingScreenFS("",SOCustReference,Arrays.asList(new String[]{partnv}), new int[]{5});
		Thread.sleep(2000);
		childTest = parantTest.createNode("Order Level Cancellation for FifoSuggested with CustLPN picktype_ Finolex");
		clickIDLinkText("Misc");
		clickIDLinkText("Cancellation");
		//clickXpath( CANCELLATIONAT);

		verify(CANCELLATIONAT);
		selectByVisibleText(CANCELLATIONAT, "Order Level");
		fluentWaitxpath(2, 1, CANCEL_SOSEARCH);
		enterXpath(CANCEL_SOSEARCH,SOCustReference);
		shortWait(1000);
		clickXpath(CANCELLATIONSEARCHBUTTON,"search");
		childTest.info("The Order is available in order level cancellation screen. Cust Ref is  : " +SOCustReference  +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());
	
		fluentWaitxpath(2, 1, CANCELLATIONSELECTALL);
		clickXpath(CANCELLATIONSELECTALL,"Checkbox all");
		shortWait(2000);
		selectByVisibleText(CANCELLATIONREASON, "Customer Cancellation");
		//shortWait(2000);
		clickXpath(CANCELLATIONCANCELORDERBUTTON,"Cancel Btn");
		//shortWait(3000);
		fluentWaitxpath(2, 1, CANCELLATIONPOPUPCLOSE);
		clickXpath(CANCELLATIONPOPUPCLOSE,"close");
	

		childTest.pass("Order level cancellation completed for Customer reference is : " + SOCustReference);
		Thread.sleep(1000);
		clickIDLinkText("Inbound");
		binnerAssignment(SOCustReference,Binner);
		binning("",SOCustReference,Arrays.asList(binPickPart), binPickPartquantity);
		childTest.pass("ReBinning Completed for Fifosuggested with CustLPN Picktype Cancelled LPNS _ Finolex");
		Thread.sleep(1000);
		
		picktype="FIFOSUGGESTEDCUSTLPNBOXAPI";
		part=new String[]{partnv};
		quantity=new String[]{"3"};
	
		//orderStatus(CustReference,site, account,Module.BINNING);
		childTest = parantTest.createNode("SO-API Order creation for FifoSuggested with CustLPN picktype _ Finolex");
		writeDataForCSV(picktype,"Newdelpoint","so", part,quantity);
		verify(OUTBOUND);

		clickXpath("//b[text()='Outbound']","OUTBOUND");
		verify(PICKGENERATION);
		clickIDLinkText("Pick Generation");
		DB(Arrays.asList(binPickPart), binPickPartquantity, "pickgen");

	

		pickerassign1(SOCustReference);
		clickXpath("//b[text()='Outbound Label']","label");
		Label_Reprint(SOCustReference.toLowerCase(), "cartorderlevel");
	
		cart=new ArrayList<String>();
		cart.add(VLPN.get(0));
		 ArrayList<String>picked=pickingScreenFS("cart",SOCustReference,Arrays.asList(binPickPart), binPickPartquantity);
		 PickedNVLPNS=new ArrayList();
		 PickedNVLPNS.addAll(picked);
		disp(SOCustReference,"",picked);
		
		childTest.pass("Dispatch Completed for finolex");
		Return_PO();
		logOut();

	}

	public void dispatchFinolex() throws Exception {

		childTest = parantTest.createNode(" Order Dispatch ");
		clickXpath(DISPATCHGRID,"DISPATCHGRID");
		verify("(//*[@id='Despatch_REQUESTNO'])[1]");
		enterXpath(DISPATCHSEARCH, SOCustReference,"DISPATCHSEARCH");
		clickXpath(Dispatch1,"Dispatch1");
		//verify(DISPATCHBUTTON);

		Thread.sleep(2000);
		clickXpath(DISPATCHBUTTON,"Dispatch1");
		//clickXpath(DISPATCHBTN,"DISPATCHBTN");
		//verify("(//*[contains(.,' / ')])[23]","3");
		childTest.info("The order available in Dispatch Screen");

		enterXpath(CARRIER, "Testing","CARRIER");
		childTest.info("The Mode of carrier is :" + "Testing");
		enterXpath(CARRIERREF, "SmokeTest","CARRIERREF");
		childTest.info("The carrier reference is :" + "SmokeTest");
		// enterXpath("//*[@id=\"Despatch_entervehicle\"]/div/div[1]", VEHICLENUMBER,"VEHICLE");


		//childTest.info("The Vehicle Number is :" + "TN38AA1234");

		///enterXpath(SCANLPN, CartLabel,"SCANLPN");


		Thread.sleep(500);
		String []NVLPNpickingFino = new String[NVLPNpicking.size()];
		NVLPNpicking.toArray(NVLPNpickingFino);
		enterXpath(SCANLPN,NVLPNpickingFino[1] ,"LPNFIELD");

		//verify("(//*[contains(.,'Successfully')])[16]");

		actionEnter();
		Thread.sleep(1000);
		enterXpath(SCANLPN, NVLPNpickingFino[2],"LPNFIELD");

		//verify("(//*[contains(.,'Successfully')])[16]");

		actionEnter();

		Thread.sleep(1000);
		enterXpath(SCANLPN, NVLPNpickingFino[2],"LPNFIELD");

		//verify("(//*[contains(.,'Successfully')])[16]");

		actionEnter();
		Thread.sleep(1000);
		clickXpath(Partial_Finish,"partialDispatch");

		Thread.sleep(1000);
		verify("(//*[@id='Despatch_REQUESTNO'])[1]");
		enterXpath(DISPATCHSEARCH, SOCustReference,"DISPATCHSEARCH");
		clickXpath(Dispatch1,"Dispatch1");
		//verify(DISPATCHBUTTON);

		Thread.sleep(1000);
		clickXpath(Cancel_Button,"cancelorder Button");
		Thread.sleep(1000);
		clickXpath(Cancel_Button_Yes,"cancel popup");
		Thread.sleep(1000);
		clickXpath(Cancel_Button_close,"cancel close ");


		Thread.sleep(1000);
		clear1(DISPATCHSEARCH);
		enterXpath(DISPATCHSEARCH, SOCustReference,"DISPATCHSEARCH");
		verify("//*[contains(text(),'No Records Found')]");


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

	public void Return_PO() throws Exception{

		picktype="FIFOSUGGESTEDCUSTLPN";
		
		//String[]lpn=new String[]{pickedLPNS,};
		part=new String[]{partnv,partnv,partnv};
		quantity=new String[]{"1","1","1"};
		binPickPart=new String[]{partnv};
		binPickPartquantity=new int[]{3};
	
		vehicleCreation(site);
		
		//String q=quantity[(Arrays.asList(binPickPart)).indexOf(partnvnu)];
		writeDataForCSV(picktype, "Newdelpoint", "returnorder",part,quantity);

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
		childTest = parantTest.createNode("Finolex: Return Order Creation via Upload Option for Fifosuggested with CustLPN Picktype");    
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

		File file1 = new File(System.getProperty("user.dir")+"//Automation_PO.csv");
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
		childTest.log(Status.PASS,"The uploaded Return Order details for Fifosuggested with CustLPN Picktype: ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		clickXpath(RETURN_COMPLETE_BTN,"COMPLETE_BTN");
		shortWait(2000);

		childTest.pass("Return Order uploaded Successfully for Fifosuggested with CustLPN Picktype");
		Thread.sleep(2000);
		childTest=parantTest.createNode("Inbound Order Level Cancellation after Return Order Upload");
		clickIDLinkText("Misc");
		clickIDLinkText("Cancellation");
		//clickXpath( CANCELLATIONAT);

		verify(CANCELLATIONAT);
		selectByVisibleText(CANCELLATIONAT, "Order Level");
		fluentWaitxpath(2, 1, CANCEL_SOSEARCH);
		enterXpath(CANCEL_SOSEARCH,CustReference);
		shortWait(1000);
		clickXpath(CANCELLATIONSEARCHBUTTON,"search");
		childTest.info("The Order is available in order level cancellation screen. Cust Ref is  : " +SOCustReference  +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());
	
		fluentWaitxpath(2, 1, CANCELLATIONSELECTALL);
		clickXpath(CANCELLATIONSELECTALL,"Checkbox all");
		shortWait(2000);
		selectByVisibleText(CANCELLATIONREASON, "Customer Cancellation");
		//shortWait(2000);
		clickXpath(CANCELLATIONCANCELORDERBUTTON,"Cancel Btn");
		//shortWait(3000);
		fluentWaitxpath(2, 1, CANCELLATIONPOPUPCLOSE);
		clickXpath(CANCELLATIONPOPUPCLOSE,"close");
	

		childTest.pass("Order level cancellation completed for Customer reference is : " + SOCustReference);
		Thread.sleep(1000);

		vehicleCreation(site);
		childTest = parantTest.createNode("Finolex: Return Order creation via Scan Option for FifoSuggested with CustLPN spicktype");
		clickXpath(INBOUND, "Inbound");
		clickIDLinkText(DASHBOARD);
		driver.navigate().refresh();
		childTest.info("Vehicle number is :  " + VEHICLENUMBER);
		//verify(GATEIN_1stROW);
		enterXpath(SEARCH_BOX, VEHICLENUMBER,"Dock in searchbox");
		VehicleID = xpathGetText(GATEIN_1stROW);
		clickXpath(VEHICLE1,"Vehicle number");
		clickXpath(DOCKIN_BTN,"DOCKIN_BTN");
		String not=xpathGetText("(//*[contains(.,'of')])[17]");
		 js = (JavascriptExecutor) driver;

		js.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
		clickXpath(CREATEREQUEST_BTN,"Order_CreateRequest_Button");
		shortWait(2000);
		clickXpath(RETURN_BTN, "Return button");
		
		 cr=driver.findElements(By.xpath(".//img[@class='buttonProgress']"));
		while(cr.size()!=0)
		{
			Thread.sleep(1000);
			cr=driver.findElements(By.xpath(".//img[@class='buttonProgress']"));
		}

//		
			actionClick("(//*[@class=' css-1hwfws3'])[1]");
			if(account1)
			{
				clickDownArrow();
			}
			Thread.sleep(1000);
			actionEnter();
		//}
		clickXpath(SCAN_PART, "SCAN PART RADIO BTN");
		shortWait(1000);
		for(String a1picking:PickedNVLPNS)
		{
			enterXpath(SCAN_LPN, a1picking,"LPNFIELD");
			shortWait(1000);
			clickXpath(POSCAN_BTN, "SCANBTN");
			shortWait(1000);
			clickXpath(PO_OK, "OKBTN");
		}
		shortWait(1000);
		clickXpath(PO_SAVE, "SAVEBTN");
		shortWait(1000);
		clickXpath(RETURN_YES, "YES_BTN");
		shortWait(3000);
		//clickXpath("//*[@id='root']/div/div/main/div/div/div/div[4]/div/div[4]/div/div/div/div/div/div/span");
		//Assert.assertTrue(isElementPresent(RETURN_CLOSE_BTN));
		childTest.log(Status.PASS,"Alertbox : ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		clickXpath("//*[@class='close']", "CLOSE_BTN");
		shortWait(1000);
		 js1 = (JavascriptExecutor) driver;

		js1.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
		verify(RETURN_COMPLETE_BTN);
		childTest.log(Status.PASS,"The uploaded Return Order details for Fifosuggested with CustLPN Picktype: ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		clickXpath(RETURN_COMPLETE_BTN,"COMPLETE_BTN");
		shortWait(2000);
		
		childTest.pass("Order upload finished for Fifosuggested with CustLPN Picktype _ Return order");
		
		assignpod(PodUser);
		podGeneration();
		binnerAssignment(CustReference,Binner);
					
		binning("",CustReference,Arrays.asList(binPickPart), binPickPartquantity);

	//System.out.println("return order binning  completed");
	}
}
