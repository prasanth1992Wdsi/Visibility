package testScripts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

public class ReturnOrder extends ProjectSpecificFunctions{
	
	public void Return_PO() throws Exception{
		

		partv="PARTVRO";
		partnv="PARTNVRO";
		partnvnu="PARTNVNURO";


		picktype="FIFOSUGGESTED";
		
		vehicleCreation(site);
		childTest = parantTest.createNode("Return Order creation for fifo Suggested picktype");
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
		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
		clickXpath(CREATEREQUEST_BTN,"Order_CreateRequest_Button");
		shortWait(20000);
		clickXpath(RETURN_BTN, "Return button");
		
		List cr=driver.findElements(By.xpath(".//img[@class='buttonProgress']"));
		while(cr.size()!=0)
		{
			Thread.sleep(1000);
			cr=driver.findElements(By.xpath(".//img[@class='buttonProgress']"));
		}

//		if(not.equals("0-0 of 0")){
//			Thread.sleep(2000);
//			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//			verify(DOC_TYPE);
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
		for(String a1picking:NVLPNRETURN)
		{
			enterXpath(SCAN_LPN, a1picking,"LPNFIELD");
			shortWait(1000);
			clickXpath(POSCAN_BTN, "SCANBTN");
			shortWait(1000);
			clickXpath(PO_OK, "OKBTN");
		}
		for(Map.Entry<String, String> Pick_Lpn:VLPNRETURN.entrySet()){

			enterXpath(SCAN_LPN, Pick_Lpn.getKey(),"LPNFIELD");	
			shortWait(1000);
			clickXpath(POSCAN_BTN, "SCANBTN");
			shortWait(1000);
			clickXpath(PO_OK, "OKBTN");
		}
//		if(!stockNumber.isEmpty())
//		{
//			enterXpath(SCAN_LPN,stockNumber.get(0) ,"LPNFIELD");	
//			shortWait(1000);
//			clickXpath(POSCAN_BTN, "SCANBTN");
//			shortWait(1000);
//			clickXpath(PO_OK, "OKBTN");
//		}
		enterXpath(SCAN_LPN, NVNURETURN,"LPNFIELD");
		shortWait(1000);
		clickXpath(POSCAN_BTN, "SCANBTN");
		shortWait(3000);
		actionSendKeys(PO_STOCKTYPE, "GOOD");
		shortWait(3000);
		actionEnter();
		shortWait(1000);
		enterXpath(RETURN_QUANTITY_BOX, "5");
        Thread.sleep(1000);
        enterXpath(RETURN_CUST_REF, SOCustReference);
        shortWait(1000);
        enterXpath("//input[@id='PO_reference1']", readXL("SO", 2, "REFERENCE_1"));
        shortWait(1000);

        enterXpath(RETURN_REF_2, readXL("SO", 2, "REFERENCE_2"));
        shortWait(1000);
        enterXpath(RETURN_REF_3, readXL("SO", 2, "REFERENCE_3"));
        shortWait(1000);
		clickXpath(PO_OK, "OKBTN");
		shortWait(1000);
		clickXpath(PO_SAVE, "SAVEBTN");
		shortWait(1000);
		clickXpath(RETURN_YES, "YES_BTN");
		shortWait(1000);
		//clickXpath("//*[@id='root']/div/div/main/div/div/div/div[4]/div/div[4]/div/div/div/div/div/div/span");
		Assert.assertTrue(isElementPresent(RETURN_CLOSE_BTN));
		childTest.log(Status.PASS,"Alertbox : ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		clickXpath(RETURN_CLOSE_BTN, "CLOSE_BTN");
		shortWait(1000);
		JavascriptExecutor js1 = (JavascriptExecutor) driver;

		js1.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
		verify(RETURN_COMPLETE_BTN);
		childTest.log(Status.PASS,"The uploaded Return Order details for fifosuggested Picktype: ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		clickXpath(RETURN_COMPLETE_BTN,"COMPLETE_BTN");
		shortWait(2000);
		
		childTest.pass("Order upload finished for fifo suggested Picktype _ Return order");
		
		assignpod(PodUser);
		podGeneration();
		binnerAssignment(SOCustReference,Binner);
		System.out.println(partv+" "+partnv+" "+partnvnu);
	//	returnOrderBinning();	
		binning("",SOCustReference,Arrays.asList(new String[]{partv,partnv,partnvnu}), new int[]{5,5,5});

		System.out.println("return order binning  completed");
		logOut();
	}
	HashMap<String, String> VLPNRETURN=null;
	ArrayList<String> NVLPNRETURN=null;
	String NVNURETURN=null;
	
	
	@Test
	public void returnOrder_Inbound() throws IOException, Exception {
		parantTest = extent.createTest("Return Order :Dispatch for Fifosugg Picktype");    

		loginAsUser(Username);
		NVLPNpicking=new  ArrayList<String>();
		Lpn = new HashMap<String ,String>();
		partv="PARTVRO";
		partnv="PARTNVRO";
		partnvnu="PARTNVNURO";


		picktype="FIFOSUGGESTED";

		System.out.println("return order binning  started");
		vehicleCreation(site);
		 part=new String[]{partv,partnv,partnvnu};
			quantity=new String[]{"5","5","5"};	
			binPickPart=new String[]{partv,partnv,partnvnu};
			binPickPartquantity=new int[]{5,5,5};
	    writeDataForCSV(picktype,"Newdelpoint","po", part,quantity);
	/*	clearcells("PO");
		clearcells("SO");		
		fifosugPart_R0("PO");
		fifosugPart_R0("SO");*/
		poreadData();
		vqty();
		nvnuqty();
		childTest = parantTest.createNode("PO Order creation for fifo Suggested picktype _ Return order");
		clickIDLinkText(DASHBOARD);
		driver.navigate().refresh();
		childTest.info("Vehicle number is :  " + VEHICLENUMBER);
		verify(GATEIN_1stROW);
		enterXpath(SEARCH_BOX, VEHICLENUMBER,"Dock in searchbox");
		VehicleID = xpathGetText(GATEIN_1stROW);
		clickXpath(VEHICLE1,"Vehicle number");
		clickXpath(DOCKIN_BTN,"DOCKIN_BTN");

		uploadFIFO("1");

		childTest.log(Status.PASS,"The uploaded PO parts details for fifo suggested Picktype Return Order: ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		verify(FINISH_UPLOAD_BTN);
		clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
		shortWait(2000);
		childTest.pass("Order upload finished for fifo suggested Picktype _ Return order");
		assignpod(PodUser);
		podGeneration();
		
		binnerAssignment(CustReference,Binner);
		System.out.println(partv+" "+partnv+" "+partnvnu);
		//binning1();	
		binning("",CustReference,Arrays.asList(new String[]{partv,partnv,partnvnu}), new int[]{5,5,5});
		childTest.pass("Binning Completed for fifo suggested Picktype _ Return order");
	    writeDataForCSV(picktype,"Newdelpoint","so", part,quantity);

		pickgenerationFIFO();
		pickerassign1(SOCustReference);
	//	pickingScreenFS("",SOCustReference,Arrays.asList(new String[]{partv,partnv,partnvnu}), new int[]{5,5,5});

		//pickingScreenFS();
		//skipConsolidation(SOCustReference);
	 
	 
	 ArrayList<String>picked=pickingScreenFS("",SOCustReference,Arrays.asList(binPickPart),binPickPartquantity);
	 VLPNRETURN=new HashMap<String,String>(Lpn);
	 NVLPNRETURN=new ArrayList<String>(NVLPNpicking);
	 NVNURETURN=CUSLPN;
	 
	 skipConsolidation(SOCustReference);

	 disp(SOCustReference,"",picked);
		//dispatch2(SOCustReference);
		
		//clickIDLinkText("Outbound Label");
		//Label_Reprint(VON, "packingslipbyorderid");
		//Label_Reprint(VON, "dispatchlabelbyorderid");
		childTest.pass("Dispatch Completed for fifo suggested Picktype _ Return order");
		Return_PO();

	}

}
