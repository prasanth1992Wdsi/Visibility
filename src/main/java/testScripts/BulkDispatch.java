package testScripts;

import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

public class BulkDispatch extends ProjectSpecificFunctions {

	public void bulkDispatch() throws Exception
	{
		childTest = parantTest.createNode(" Bulk Dispatch ");
		clickXpath(DISPATCHGRID,"DISPATCHGRID");
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
		enterXpath(DISPATCHSEARCH, SOCustReference,"DISPATCHSEARCH");
		Thread.sleep(1000);
		verify("//*[contains(text(),'No Records Found')]");
		childTest.pass("The order is Dispatched By Bulk Dispatch");
	}
		
	public void bulkScanDispatch() throws Exception
	{
		childTest = parantTest.createNode(" Bulk and Scan Dispatch ");
		clickXpath(DISPATCHGRID,"DISPATCHGRID");
		verify("(//*[@id='Despatch_REQUESTNO'])[1]");
		enterXpath(DISPATCHSEARCH, SOCustReference,"DISPATCHSEARCH");
		clickXpath(Dispatch1,"Dispatch1");
        Thread.sleep(1000);
		clickXpath(DISPATCHBUTTON,"Dispatch1");
        childTest.info("The order available in Dispatch Screen");
        enterXpath(CARRIER, "Carrier1","CARRIER");
		childTest.info("The Mode of carrier is :" + "Carrier1");
		enterXpath(CARRIERREF, "CarrierRef1","CARRIERREF");
		childTest.info("The carrier reference is :" + "CarrierRef1");

		int size = NVLPNpicking.size(); 
		String res1[] = NVLPNpicking.toArray(new String[size]);
		for(int i=0;i<3;i++)
		{

			Thread.sleep(2000);
			enterXpath(SCANLPN,res1[i] ,"LPNFIELD");
			actionEnter();
		}
		for(Map.Entry<String, String> Pick_Lpn:Lpn.entrySet()){
			Thread.sleep(500);
			enterXpath(SCANLPN, Pick_Lpn.getKey(),"LPNFIELD");	
			actionEnter();
		}
		if(!stockNumber.isEmpty())
		{Thread.sleep(500);
			enterXpath(SCANLPN,stockNumber.get(0) ,"LPNFIELD");	
			actionEnter();	
		}
		
		enterXpath(SCANLPN, CUSLPN,"LPNFIELD");
		actionEnter();
		enterXpath("//*[@id=\"Despatch_enterQty1\"]",prop.getProperty("NVNUqnty"),"NUNVQNTY");
		actionEnter();
		Thread.sleep(3000);
		clickXpath(DISPATCH_FINISH);
		childTest.pass("The order is Partially Dispatched By Scan");
		Thread.sleep(1000);
		Thread.sleep(1000);
		Thread.sleep(1000);
		Thread.sleep(1000);
		clickXpath(DISPATCHGRID,"DISPATCHGRID");
		Thread.sleep(1000);
		enterXpath(DISPATCHSEARCH, SOCustReference,"DISPATCHSEARCH");
		clickXpath(Dispatch1,"Dispatch1");


		Thread.sleep(1000);
		clickXpath(DISPATCHBUTTON,"Dispatch1");

		childTest.info("The order available in Dispatch Screen");
		Thread.sleep(2000);
		enterXpath(CARRIER,"Carrier2","CARRIER");
		childTest.info("The Mode of carrier is :" + "Carrier2");
		Thread.sleep(1000);
		enterXpath(CARRIERREF,"CarrierRef2","CARRIERREF");

		childTest.info("The carrier reference is :" + "CarrierRef2");

		selectByVisibleText(SCAN_DROPDOWN, "Bulk Dispatch");
		Thread.sleep(2000);
		clickXpath(SELECTALL_BTN, "Select All Btn");
		Thread.sleep(1000);
		clickXpath(BULK_SUBMIT, "Submit Btn");


		Thread.sleep(3000);
		enterXpath(DISPATCHSEARCH, SOCustReference,"DISPATCHSEARCH");
		verify("//*[contains(text(),'No Records Found')]");
		childTest.pass("The order is Partially Dispatched By Bulk Dispatch");

	}

	
	public void inbound() throws IOException, Exception {
		partv="VFS";
		partnv="NVFS";
		partnvnu="NVNUFS";
		picktype="FIFOSUGGESTED";
		site=prop.getProperty("SITEBULKDISPATCH");
		account=prop.getProperty("ACCOUNTBULKDISPATCH");
		Binner=prop.getProperty("USERNAMEBULKDISPATCH");
		Picker=prop.getProperty("USERNAMEBULKDISPATCH");
		PodUser=prop.getProperty("USERNAMEBULKDISPATCH");
		Username=prop.getProperty("USERNAMEBULKDISPATCH");
		
		binPickPart=new String[]{partv,partnv,partnvnu};
		binPickPartquantity=new int[]{2,2,2};
		childTest = parantTest.createNode("PO Order creation for fifosuggested picktype");
		vehicleCreation(site);

		 part=new String[]{partv,partnv,partnvnu};
			quantity=new String[]{"5","5","5"};	
	    writeDataForCSV(picktype,"Newdelpoint","po", part,quantity);
	
    writeDataForCSV(picktype,"Newdelpoint","so", part,quantity);
		
		clickIDLinkText(DASHBOARD);
		driver.navigate().refresh();
		childTest.info("Vehicle number is :  " + VEHICLENUMBER);
		verify(GATEIN_1stROW);
		enterXpath(SEARCH_BOX, VEHICLENUMBER,"Dock in searchbox");
		VehicleID = xpathGetText(GATEIN_1stROW);
		clickXpath(VEHICLE1,"Vehicle number");
		clickXpath(DOCKIN_BTN,"DOCKIN_BTN");

		uploadFIFO("1");
		verify(FINISH_UPLOAD_BTN);
		childTest.log(Status.PASS,"The uploaded PO parts  details for fifosuggested Picktype: ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
		shortWait(2000);
		childTest.pass("Order upload finished for fifosuggested Picktype");
		assignpod(PodUser);
		podGeneration();
		binnerAssignment(CustReference,Binner);
		System.out.println(partv+" "+partnv+" "+partnvnu);
		binning("",CustReference,Arrays.asList(new String[]{partv,partnv,partnvnu}), new int[]{5,5,5});

	}
	public void inbound1() throws IOException, Exception {
		
		parantTest=extent.createTest("Scan and Bulk Dispatch for FIFOSUGGESTED Picktype");
		partv="VFS";
		partnv="NVFS";
		partnvnu="NVNUFS";
		picktype="FIFOSUGGESTED";
		site=prop.getProperty("SITEBULKDISPATCH");
		account=prop.getProperty("ACCOUNTBULKDISPATCH");
		Binner=prop.getProperty("USERNAMEBULKDISPATCH");
		Picker=prop.getProperty("USERNAMEBULKDISPATCH");
		PodUser=prop.getProperty("USERNAMEBULKDISPATCH");
		Username=prop.getProperty("USERNAMEBULKDISPATCH");
		vehicleCreation(site);

		
		part=new String[]{partv,partnv,partnvnu};
		quantity=new String[]{"5","5","5"};	
    writeDataForCSV(picktype,"Newdelpoint","po", part,quantity);
    writeDataForCSV(picktype,"Newdelpoint","so", part,quantity);

		
		childTest = parantTest.createNode("PO Order creation for fifosuggested picktype");
		clickIDLinkText(DASHBOARD);
		driver.navigate().refresh();
		childTest.info("Vehicle number is :  " + VEHICLENUMBER);
		verify(GATEIN_1stROW);
		enterXpath(SEARCH_BOX, VEHICLENUMBER,"Dock in searchbox");
		VehicleID = xpathGetText(GATEIN_1stROW);
		clickXpath(VEHICLE1,"Vehicle number");
		clickXpath(DOCKIN_BTN,"DOCKIN_BTN");

		uploadFIFO("1");
		verify(FINISH_UPLOAD_BTN);
		childTest.log(Status.PASS,"The uploaded PO parts details for fifosuggested Picktype: ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
		shortWait(2000);
		childTest.pass("Order upload finished for fifosuggested Picktype");
		assignpod(PodUser);
		podGeneration();
		binnerAssignment(CustReference,Binner);
		System.out.println(partv+" "+partnv+" "+partnvnu);
		binning("",CustReference,Arrays.asList(new String[]{partv,partnv,partnvnu}), new int[]{5,5,5});

	}
	
	@Test
	public void outbound() throws Exception{
		partv="VFS";
		partnv="NVFS";
		partnvnu="NVNUFS";
		picktype="FIFOSUGGESTED";
		
		site=prop.getProperty("SITEBULKDISPATCH");
		account=prop.getProperty("ACCOUNTBULKDISPATCH");
		Binner=prop.getProperty("USERNAMEBULKDISPATCH");
		Picker=prop.getProperty("USERNAMEBULKDISPATCH");
		PodUser=prop.getProperty("USERNAMEBULKDISPATCH");
		Username=prop.getProperty("USERNAMEBULKDISPATCH");
		parantTest=extent.createTest("PO Order creation for fifosuggested picktype");

		loginAsUser(Username);

         inbound();
		pickgenerationFIFO();
		pickerassign1(SOCustReference);
		pickingScreenFS("",SOCustReference,Arrays.asList(new String[]{partv,partnv,partnvnu}), new int[]{5,5,5});
		skipConsolidation(SOCustReference);
        bulkDispatch();
        //logOut();
	}

	
//	@Test(dependsOnMethods="outbound")
	
	public void outbound1() throws Exception{
		
		partv="VFS";
		partnv="NVFS";
		partnvnu="NVNUFS";
		picktype="FIFOSUGGESTED";
		clickXpath(INBOUND);
		inbound1();
		pickgenerationFIFO();
		pickerassign1(SOCustReference);
		pickingScreenFS("",SOCustReference,Arrays.asList(new String[]{partv,partnv,partnvnu}), new int[]{5,5,5});
		skipConsolidation(SOCustReference);
		logOut();
		System.out.println("BULK DISPATCH");
	}
}
