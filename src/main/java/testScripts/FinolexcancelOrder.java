package testScripts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import testScripts.CommonFunctions.Module;

public class FinolexcancelOrder extends ProjectSpecificFunctions {
	
	String[] part;
	String[] quantity;
	@Test(priority=0)
	public void finolexOrder_Inbound() throws IOException, Exception {
		NVLPNpicking=new  ArrayList<String>();
		Lpn = new HashMap<String ,String>();
		
		partnv="PARTNVFSCUS";
		picktype="CUSTLPNBOXAPI";
	part=new String[]{partnv,partnv,partnv,partnv,partnv};
	quantity=new String[]{"1","1","1","1","1"};
	binPickPart=new String[]{partnv};
	binPickPartquantity=new int[]{5};
	Bin=prop.getProperty("BIN_LABEL");
	site=prop.getProperty("SITEFinolex");
	account=prop.getProperty("ACCOUNTFinolex");
	Binner=prop.getProperty("POD_FINOLEX");
	Picker=prop.getProperty("POD_FINOLEX");
	PodUser=prop.getProperty("POD_FINOLEX");
	Username=prop.getProperty("USERNAMEFinolex");
	parantTest = extent.createTest("FinolexFlow With API order creation"); 

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
		binning("binbox",CustReference,Arrays.asList(new String[]{partnv}),binPickPartquantity);
	
		site=prop.getProperty("SITEFinolex");
		account=prop.getProperty("ACCOUNTFinolex");
	
		picktype="FIFOSUGGESTEDCUSTLPNBOXAPI";
		part=new String[]{partnv};
		quantity=new String[]{"5"};
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
		Label_Reprint(SOCustReference, "cartorderlevel");
	//	Label_Reprint("1", "cartlabel");
		cart=new ArrayList<String>();
		cart.add(VLPN.get(0));
		
		pickingScreenFS("cart",SOCustReference,Arrays.asList(binPickPart), binPickPartquantity);
		//pickingScreenFS("",SOCustReference,Arrays.asList(new String[]{partnv}), new int[]{5});
		
		dispatchFinolex();
		binPickPartquantity=new int[]{2};

		Packingslip_Quantity(readPDF(),SOCustReference);
		deleteFile();
		clickXpath("//b[text()='Outbound Label']","label");
		Label_Reprint(SOCustReference.toLowerCase(), "packingslip");
		Packingslip_Quantity(readPDF(),SOCustReference);

		childTest.pass("Dispatch Completed for fifo suggested Picktype _ Partial Dispatch");
		Thread.sleep(1000);
		clickIDLinkText("Inbound");
		binnerAssignment(SOCustReference,Binner);
		binning("",SOCustReference,Arrays.asList(new String[]{partnv}), new int[]{3});
		childTest.pass("ReBinning Completed for fifo suggested Picktype Cancelled LPNS _ Finolex");
		Thread.sleep(1000);
		clear1(SEARCH_BOX);
		enterXpath(SEARCH_BOX, SOCustReference,"SEARCH_BOX");
		verify("(//*[contains(.,'0 of 0')])[16]");
	
		childTest.log(Status.PASS,"Validation Completed in Binning Screen : ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		
		childTest.pass("Verification completed for INBOUND BINNING");
		//orderStatus(CustReference,prop.getProperty("SITEIDFINOLEX"), prop.getProperty("ACCOUNTIDFINOLEX"),Module.BINNING);
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
}
