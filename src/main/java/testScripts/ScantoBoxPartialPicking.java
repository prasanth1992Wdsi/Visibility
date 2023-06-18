package testScripts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

public class ScantoBoxPartialPicking extends ProjectSpecificFunctions {
	@Test
	public void BNscantobox() throws IOException, Exception {
		NVLPNpicking=new  ArrayList<String>();
		Lpn = new HashMap<String ,String>();
		partv="VBN";
		partnv="NVBN";
		partnvnu="NVNUBN";
		picktype="BATCHBOX";
		System.out.println("scan to box batch(skip) started");
		parantTest = extent.createTest("Scan to box Batch picktype(Dispatch) Inbound process");    

		loginAsUser(Username);

		vehicleCreation(site);
		part=new String[]{partv,partnv,partnvnu};
		quantity=new String[]{"5","5","5"};	
		binPickPart=new String[]{partv,partnv,partnvnu};
		binPickPartquantity=new int[]{5,5,5};
	    writeDataForCSV(picktype,"Newdelpoint","po", part,quantity);
	    writeDataForCSV(picktype,"Newdelpoint","SO", part,quantity);  
		
		/*clearcells("PO");
		clearcells("SO");
		
		batchPartbox("PO");
		batchPartbox("SO");*/
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
	    clickXpath(VEH,"Vehicle number");
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
		clickIDLinkText(INBOUND_LABEL);

		Label_Reprint("3", "boxlabel");
		nvnupartBox=VLPN.get(2);
			nvpartBox=VLPN.get(1);
			vpartBox=VLPN.get(0);

			
			binning("box",CustReference,Arrays.asList(new String[]{partv,partnv,partnvnu}), new int[]{5,5,5});
			binning("binbox",CustReference,Arrays.asList(new String[]{partv,partnv,partnvnu}), new int[]{5,5,5});

		Thread.sleep(2000);
		parantTest = extent.createTest("Scan to box Batch picktype(Dispatch) Outbound process");
		pickgenerationFIFO();
		pickerassign1(SOCustReference);
		////pickingScreenFS("",SOCustReference,Arrays.asList(binPickPart),binPickPartquantity);

		ArrayList<String>picked=pickingScreenFS("",SOCustReference,Arrays.asList(binPickPart),binPickPartquantity);
		skipConsolidation(SOCustReference);

		 disp(SOCustReference,"",picked);
		//dispatchbox();
		System.out.println("scan to box batch(skip) completed");
		logOut();

}
	
}


