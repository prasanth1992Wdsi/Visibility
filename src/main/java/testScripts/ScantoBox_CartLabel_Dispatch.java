package testScripts;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.google.common.base.Verify;

public class ScantoBox_CartLabel_Dispatch extends ProjectSpecificFunctions{
	public String CARTLABEL_QNTY="1"; 
	public ArrayList<String> Outbound_cartLabel=new  ArrayList<String>();
	public String  VON;
	public int i;
	public String NVCartLabel;
	public void cartLabel_dispatch() throws Exception {

		childTest = parantTest.createNode("CartLabel Dispatch");
		clickXpath(DISPATCHGRID,"DISPATCHGRID");
		verify(DISPATCH_REQNO);
		enterXpath(DISPATCHSEARCH, SOCustReference,"DISPATCHSEARCH");
		verify("//*[contains(text(),'1-1 of 1')]");
		clickXpath(Dispatch1,"Dispatch1");
		

		Thread.sleep(3000);
		clickXpath(DISPATCHBUTTON,"Dispatch1");
			
		childTest.info("The order available in Dispatch Screen");

		enterXpath(CARRIER, "ScantoCart","CARRIER");
		childTest.info("The Mode of carrier is :" + "ScantoCart");
		enterXpath(CARRIERREF, "SmokeTest","CARRIERREF");
		childTest.info("The carrier reference is :" + "SmokeTest");
	

		enterXpath(SCANLPN, cart.get(0),"SCANLPN");

		actionEnter();
		childTest.log(Status.INFO, "Cart label Scanned :  ",MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture1()).build());
driver.navigate().refresh();
		Thread.sleep(1000);
		Thread.sleep(1000);
		Thread.sleep(1000);
		verify(DISPATCHSEARCH);
		enterXpath(DISPATCHSEARCH, SOCustReference,"DISPATCHSEARCH");
//		childTest.log(Status.FAIL,"ORDER REMAINS IN DISPATCH SCREEN  ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		verify("//*[contains(text(),'No Records Found')]");
		//childTest.log(Status.FAIL,"ORDER REMAINS IN DISPATCH SCREEN  ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		childTest.pass("The order is Dispatched successfully by CartLabel");
		System.out.println("Dispatch completed sucessfully ");
		System.out.println("*********************************************************");
		

	}

	
		
	


@Test
	public void scan_to_box_cart_picking() throws IOException, Exception
	{
	parantTest = extent.createTest("Inbound_ScanToBox_Cart_Dispatch_Testing");    

	loginAsUser(Username);

		
		partnv="PARTNVCUSLPN";
		
		picktype="CUSTLPN";
	
		System.out.println("live Inbound_ScanToBox_Cart_Dispatch_Testing");

	vehicleCreation(site);
	//cuslpnPartBOX("PO");
	//cuslpnPartBOX("SO");
	part=new String[]{partnv,partnv,partnv};
	quantity=new String[]{"1","1","1"};	
	binPickPart=new String[]{partnv};
	binPickPartquantity=new int[]{3};
    writeDataForCSV(picktype,"Newdelpoint","po", part,quantity);
    
    writeDataForCSV(picktype,"Newdelpoint","SO", part,quantity);  
	clickIDLinkText(DASHBOARD);
	driver.navigate().refresh();

	verify(GATEIN_1stROW);
	enterXpath(SEARCH_BOX, VEHICLENUMBER,"SEARCH_BOX");



	childTest.info("Vehicle number is :  " + VEHICLENUMBER);
	childTest.log(Status.INFO, "Vehicle number :  ",MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture1()).build());
	VehicleID = xpathGetText(GATEIN_1stROW);
	clickXpath(VEH,"First record");
	childTest = parantTest.createNode("PO Order creation");
			clickXpath(DOCKIN_BTN,"DOCKIN_BTN");
			uploadFIFO("1");

			//uploadBOX(1);
			shortWait(2000);
			clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
			shortWait(2000);
			childTest.pass("Order upload finished");
			assignpod(PodUser);
			podGeneration();
			binnerAssignment(CustReference,Binner);
		
			clickIDLinkText(INBOUND_LABEL);

			Label_Reprint("3", "boxlabel");
				nvpartBox=VLPN.get(1);
		
				binning("box",CustReference,Arrays.asList(binPickPart),binPickPartquantity);
				binning("binbox",CustReference,Arrays.asList(binPickPart),binPickPartquantity);

			pickgenerationFIFO();
			pickerassign1(SOCustReference);
			clickXpath("//b[text()='Outbound Label']","label");
			
				Label_Reprint("1", "cartlabel");
				cart=VLPN;
				
				//pickingScreenFS("cart",SOCustReference,Arrays.asList(binPickPart), binPickPartquantity);
				ArrayList<String>picked=pickingScreenFS("cart",SOCustReference,Arrays.asList(binPickPart),binPickPartquantity);
				 disp(SOCustReference,"",cons( SOCustReference,"",picked));
			//cartLabel_Picking();
			//skipConsolidation(SOCustReference);
		//	cartLabel_dispatch();
			System.out.println("live cart label box dispatch completed");
			logOut();
}
}
