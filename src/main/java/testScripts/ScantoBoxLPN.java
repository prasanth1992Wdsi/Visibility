

package testScripts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

public class ScantoBoxLPN  extends ProjectSpecificFunctions {
	@Test
	public void scantobox_BN() throws IOException, Exception {
		NVLPNpicking=new  ArrayList<String>();
		Lpn = new HashMap<String ,String>();
		partv="VBN";
		partnv="NVBN";
		partnvnu="NVNUBN";
		picktype="BATCHBOX";
		System.out.println("scan to box batch(consolidate) started");
		parantTest = extent.createTest("Scan to box Batch picktype(consolidate) Inbound process");    

		loginAsUser(Username);

	 vehicleCreation(site);
		//clearcells("PO");
		//clearcells("SO");
		part=new String[]{partv,partnv,partnvnu};
		quantity=new String[]{"5","5","5"};	
		binPickPart=new String[]{partv,partnv,partnvnu};
		binPickPartquantity=new int[]{5,5,5};
	    writeDataForCSV(picktype,"Newdelpoint","po", part,quantity);
	    writeDataForCSV(picktype,"Newdelpoint","SO", part,quantity);
		//batchPartbox("PO");
	//	batchPartbox("SO");
		poreadData();
	    vqty();
	    nvnuqty();
	    childTest = parantTest.createNode("PO Order creation for Batch picktype");
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
		childTest.log(Status.PASS,"The PO uploaded parts  details for Batch Picktype: ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
		shortWait(2000);
		childTest.pass("Order upload finished for Batch Picktype");
		assignpod(PodUser);
		podGeneration();
		binnerAssignment(CustReference,Binner);
		//printScanToBoxlabel();
		clickIDLinkText(INBOUND_LABEL);

		Label_Reprint("3", "boxlabel");
		nvnupartBox=VLPN.get(2);
			nvpartBox=VLPN.get(1);
			vpartBox=VLPN.get(0);

		//binning2();
			
			binning("box",CustReference,Arrays.asList(new String[]{partv,partnv,partnvnu}), new int[]{5,5,5});
			binning("binbox",CustReference,Arrays.asList(new String[]{partv,partnv,partnvnu}), new int[]{5,5,5});

		parantTest = extent.createTest("Scan to box Batch picktype(consolidate) Outbound process");
		
		pickgenerationFIFO();
		pickerassign1(SOCustReference);
		/*pickingScreenFS("",SOCustReference,Arrays.asList(binPickPart),binPickPartquantity);
		consolidationbox();
		dispatch1();*/
		ArrayList<String>picked=pickingScreenFS("",SOCustReference,Arrays.asList(binPickPart),binPickPartquantity);

		 disp(SOCustReference,"",cons( SOCustReference,"",picked));

		System.out.println("scan to box batch(consolidate) completed");
		logOut();
}
	
}
