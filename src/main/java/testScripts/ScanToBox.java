
package testScripts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.testng.annotations.Test;

public class ScanToBox extends ProjectSpecificFunctions{
	@Test
	public void scan_to_box() throws IOException, Exception
	{
		parantTest = extent.createTest("Inbound_ScanToBox_Testing");    

		loginAsUser(Username);

		NVLPNpicking=new  ArrayList<String>();
		Lpn = new HashMap<String ,String>();
		
		System.out.println("scan to box fifo sugg(skip) started");

	vehicleCreation(site);
	partv="VFS";
	partnv="NVFS";
	partnvnu="FSNVNU";
	picktype="FIFOSUGGESTEDBOX";
	binPickPart=new String[]{partv,partnv,partnvnu};
	binPickPartquantity=new int[]{5,5,5};
	 part=new String[]{partv,partnv,partnvnu};
		quantity=new String[]{"5","5","5"};	
    writeDataForCSV(picktype,"Newdelpoint","po", part,quantity);

	ReadExcel(prop.getProperty("POPATH"));
	
	System.out.println("captureParentDetails-----------");
	clickIDLinkText(DASHBOARD);
	driver.navigate().refresh();

	verify(GATEIN_1stROW);
	enterXpath(SEARCH_BOX, VEHICLENUMBER,"SEARCH_BOX");



	childTest.info("Vehicle number is :  " + VEHICLENUMBER);
	VehicleID = xpathGetText(GATEIN_1stROW);
	clickXpath(VEH,"First record");
	childTest = parantTest.createNode("PO Order creation");
			clickXpath(DOCKIN_BTN,"DOCKIN_BTN");
			uploadFIFO("1");
			shortWait(2000);
			clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
			shortWait(2000);
			childTest.pass("Order upload finished");
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

		
			
			parantTest = extent.createTest("Outbound_ScanToBox_Testing");
		    writeDataForCSV(picktype,"Newdelpoint","so", part,quantity);

			
			   pickgenerationFIFO();
				pickerassign1(SOCustReference);
				//pickingScreenBox();
				ArrayList<String>picked=pickingScreenFS("",SOCustReference,Arrays.asList(binPickPart),binPickPartquantity);

				skipConsolidation(SOCustReference);
				disp(SOCustReference, "", picked);
				//dispatchbox();
				System.out.println("scan to box fifo sugg(skip) completed");
				logOut();
}
	
}
