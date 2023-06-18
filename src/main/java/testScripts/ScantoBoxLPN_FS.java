package testScripts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

public class ScantoBoxLPN_FS extends ProjectSpecificFunctions {

	@Test
	public void scantobox_FS() throws IOException, Exception {
		parantTest = extent.createTest("Scan to box FifoSuggested picktype(consolidate) Inbound process");    

		loginAsUser(Username);

		NVLPNpicking=new  ArrayList<String>();
		Lpn = new HashMap<String ,String>();
			
		partv="VFS";
		partnv="NVFS";
		partnvnu="FSNVNU";
		picktype="FIFOSUGGESTEDBOX";
		System.out.println("scan to box fifo sugg(consolidate) started");

		vehicleCreation(site);
		 part=new String[]{partv,partnv,partnvnu};
			quantity=new String[]{"1","1","1"};	
			binPickPart=new String[]{partv,partnv,partnvnu};
			binPickPartquantity=new int[]{1,1,1};
		    writeDataForCSV(picktype,"Newdelpoint","po", part,quantity);
		    writeDataForCSV(picktype,"Newdelpoint","SO", part,quantity);
	  
		poreadData();
		vqty();
	    nvnuqty();
	    childTest = parantTest.createNode("PO Order creation");
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
		childTest.log(Status.PASS,"The uploaded PO parts  details: ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
		shortWait(2000);
		childTest.pass("Order upload finished ");
		assignpod(PodUser);
		podGeneration();
		binnerAssignment(CustReference,Binner);
	//	printScanToBoxlabel();
		clickIDLinkText(INBOUND_LABEL);

		Label_Reprint("3", "boxlabel");
		nvnupartBox=VLPN.get(2);
			nvpartBox=VLPN.get(1);
			vpartBox=VLPN.get(0);
		//binning2();
			
			binning("box",CustReference,Arrays.asList(new String[]{partv,partnv,partnvnu}), new int[]{1,1,1});
			binning("binbox",CustReference,Arrays.asList(new String[]{partv,partnv,partnvnu}), new int[]{1,1,1});

		parantTest = extent.createTest("Scan to box FifoSuggested picktype(consolidate) Outbound process");
		pickgenerationFIFO();
		pickerassign1(SOCustReference);
		//pickingScreenFS("",SOCustReference,Arrays.asList(binPickPart),binPickPartquantity);
		//skipConsolidation(customerref);
		 /*cons( "",picked);*/
		ArrayList<String>picked=pickingScreenFS("",SOCustReference,Arrays.asList(binPickPart),binPickPartquantity);

		 disp(SOCustReference,"",cons( SOCustReference,"",picked));
	//	consolidationbox();
	//	dispatch1();
		System.out.println("scan to box fifo sugg(consolidate) completed");
		
		logOut();	
}
}


