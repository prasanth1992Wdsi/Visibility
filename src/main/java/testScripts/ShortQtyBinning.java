package testScripts;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class ShortQtyBinning extends ProjectSpecificFunctions {

	String[] part;
	String[] quantity;

	@Test
	public void shortbinning() throws IOException, Exception {
		parantTest = extent.createTest("Inbound_ShortQtyBinning_Testing");    

		loginAsUser(Username);

		NVLPNpicking=new  ArrayList<String>();
		Lpn = new HashMap<String ,String>();
		System.out.println("short binning started");

		vehicleCreation(site);

		ReadExcel(prop.getProperty("POPATH"));
		poreadData();
//clearcells("SO");
		vqty();
		nvnuqty();
		partv="VFS";
		partnv="NVFS";
		partnvnu="FSNVNU";
		picktype="FIFOSUGGESTED";
		 part=new String[]{partv,partnv,partnvnu};
			quantity=new String[]{"5","5","5"};	
	    writeDataForCSV(picktype,"Newdelpoint","po", part,quantity);
	//	shortqty("PO");
	//	shortqty("SO");

		//childTest = parantTest.createNode("Capture Parent Document Details").assignCategory("Smoke");
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

		clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
	
		childTest.pass("Order upload finished");




		assignpod(PodUser);
	
		childTest = parantTest.createNode("POD Generation");

		clickIDLinkText(POD_DASHBOARD);
		verify(ROW1);		
		enterXpath(SEARCH_BOX, VEHICLENUMBER,"SEARCH_BOX");
		clickXpath(ROW1,"Vehicle");

		enterXpath(EXPECTED_QTY, prop.getProperty("EXPECTED_QTY"),"EXPECTED_QTY");

		enterXpath(RECEIVED_QTY, prop.getProperty("RECEIVED_QTY")+1,"RECEIVED_QTY");

		clickXpath(POD_SAVE_BTN,"POD_SAVE_BTN");

		clickXpath(POD_CLOSE_BTN,"POD_CLOSE_BTN");
		clickIDLinkText("Misc");
		clickIDLinkText("Approve Request");
		
ApproveRequest("podapprove",VEHICLENUMBER);
	
		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("window.scrollTo(document.body.scrollHeight, 0)");

		clickIDLinkText("Inbound");
		childTest.pass("Excess POD Approved Successfully");





		binnerAssignment(CustReference,Binner);

	


		childTest = parantTest.createNode("Binning");
		clickIDLinkText(BINNING_MENU);

		for (int i1 = 0; i1 <=2; i1++) {
			verify(PARTTYPE_CELL);
			clear1(SEARCH_BOX);
			enterXpath(SEARCH_BOX, CustReference,"BINNING SEARCH");
			scrollUp();
			String parttype = xpathGetText(PARTTYPE_CELL);

			String partname=xpathGetText(PARTName_CELL);
			if (parttype.equalsIgnoreCase("V")) {
				partStockCheck(partv,  "ui",1);

				clickXpath(INFO_BTN,"INFO_BTN");
				String LPNV = xpathGetText(LPN_CELL_1);
				//System.out.println("LPN:" + LPNV);

				clickXpath(INFO_CLOSEBTN,"INFO_CLOSEBTN");
				Thread.sleep(1000);
				verify("(//*[@id='BinnerDashboard_Scanbutton'])[1]");
				clickXpath(SCAN_BTN,"SCAN_BTN");
				//clickXpath(SCANTOBIN_RADIOBTN,"SCANTOBIN_RADIOBTN");
				clickXpath(SCANTOBIN_SUMBIT,"SCANTOBIN_SUMBIT");

				childTest.pass("Scan To Bin Selected Successfully for V Part Binning");

				enterXpath(BIN_TEXT_BOX, prop.getProperty("BIN_LABEL"),"BIN_TEXT_BOX");
				clickXpath(BINNING_SUBMIT_BTN,"BINNING_SUBMIT_BTN");
				verify("(//*[contains(.,'Successfully')])[14]");

				enterXpath(BIN_TEXT_BOX, LPNV,"LPN");

				clickXpath(BINNING_SUBMIT_BTN,"BINNING_SUBMIT_BTN");

				//int vqtyexcess = Integer.parseInt(VQTY); 
				int i=VPQTY;  
				String s=String.valueOf(i-1);

				verify("(//*[contains(.,'Enter')])[14]","Enter");
				enterXpath(VPART_QTY_TXT,s,"VPART_QTY_TXT");
				actionEnter();


				clickXpath(".//*[contains(text(),'PROCEED')]","Proceed");

				verify("(//*[contains(.,'Warehouse')])[14]","Warehouse");
				System.out.println(xpathGetText("(//*[contains(.,'Warehouse')])[14]"));
				Thread.sleep(1000);
				clickXpath(BINNING_CLOSE_BTN,"BINNING_CLOSE_BTN");
				childTest.pass("Vpart ExcessQty Binning Request Sent To Warehouse Approval");
				clickIDLinkText("Misc");
				clickIDLinkText("Approve Request");
				
				ApproveRequest("shortbinapprove",CustReference);



				
				partStockCheck(partv,  "binning",4);

				childTest.pass("Vpart ShortQty Approval Request Approved Successfully");

				clickIDLinkText("Inbound");

				clickIDLinkText("Binning");
				
			}

			else if (parttype.equalsIgnoreCase("NV")) {
				partStockCheck(partnv,  "jv",5);

			ArrayList<String> NVLPN=new  ArrayList<String>();


			clickXpath(INFO_BTN,"INFO_BTN");


			for(int i=1;i<=Integer.parseInt(readXL("PO", 1, "QUANTITY"));i++)
			{String NVLPN1 = xpathGetText("(//TD[@data-label='LPN'])["+i+"]");
			NVLPN.add(NVLPN1);
			//	System.out.println(NVLPN1);
			}


			clickXpath(INFO_CLOSEBTN,"INFO_CLOSEBTN");
			Thread.sleep(1000);
			verify("(//*[@id='BinnerDashboard_Scanbutton'])[1]");
			clickXpath("(//*[@id='BinnerDashboard_Scanbutton'])[1]","SCAN_BTN");

			//	clickXpath(SCANTOBIN_RADIOBTN,"SCANTOBIN_RADIOBTN");
			clickXpath(SCANTOBIN_SUMBIT,"SCANTOBIN_SUMBIT");
			childTest.pass("NV parts binning started through Scan to Bin option");
			verify("(//*[contains(.,'Please')])[14]","Please");
			actionSendKeys(BIN_TEXT_BOX, prop.getProperty("BIN_LABEL"));
			clickXpath(BINNING_SUBMIT_BTN,"BINNING_SUBMIT_BTN");
			verify("(//*[contains(.,'Successfully')])[14]","Successfully",prop.getProperty("BIN_LABEL"));

			int i=0;
			for(String a:NVLPN)
			{
				++i;
				enterXpath(BIN_TEXT_BOX, a,"BIN_TEXT_BOX");

				clickXpath(BINNING_SUBMIT_BTN,"BINNING_SUBMIT_BTN");
				if(!a.equalsIgnoreCase(NVLPN.get(Integer.parseInt(readXL("PO", 1, "QUANTITY"))-1)))
				{
					verify("(//*[contains(.,'Successfully')])[14]","Successfully",a);
					partStockCheck(partnv,  "binning",1);
					System.out.println("After binning nv Qty : "+quantity);

					verify("(//*[contains(.,'SCANNED')])[14]",""+i+" / 5");

				}


			}
			verify("(//*[contains(.,'Successfully')])[14]","Successfully");
			driver.navigate().refresh();
			//clickXpath(BINNING_CLOSE_BTN,"BINNING_CLOSE_BTN");
			partStockCheck(partnv,  "binning",1);
			//System.out.println("After binning nv Qty : "+quanti);


			childTest.pass("NV parts Binned Successfully");
			} else if (parttype.equalsIgnoreCase("NVNU")) {
				partStockCheck(partnvnu,  "fg",1);

				clickXpath(INFO_BTN,"INFO_BTN");
				String LPNNVNU = xpathGetText(LPN_CELL_1);
				clickXpath(INFO_CLOSEBTN,"INFO_CLOSEBTN");

				Thread.sleep(1000);
				clickXpath(PARTTYPE_CELL,"PARTTYPE_CELL");
				clickXpath(SCANTOBIN_RADIOBTN,"SCANTOBIN_RADIOBTN");
				clickXpath(SCANTOBIN_SUMBIT,"SCANTOBIN_SUMBIT");
				childTest.pass("NVNU parts binning started through Scan to Bin option");
				verify("(//*[contains(.,'Scan')])[18]","Scan");
				enterXpath(BIN_TEXT_BOX, prop.getProperty("BIN_LABEL"),"BIN_TEXT_BOX");
				clickXpath(BINNING_SUBMIT_BTN,"BINNING_SUBMIT_BTN");
				verify("(//*[contains(.,'Successfully')])[14]","Successfully");

				enterXpath(BIN_TEXT_BOX, LPNNVNU,"BIN_TEXT_BOX");
				//clickXpath(BINNING_SUBMIT_BTN,"BINNING_SUBMIT_BTN");



				int j=NVNUVPQTY;  
				String s1=String.valueOf(j-1); 

				enterXpath(NVNUPARTQTY,s1,"NVNUPARTQTY");
             Thread.sleep(2000);
				
				clickXpath(BINNING_SUBMIT_BTN,"BINNING_SUBMIT_BTN");
				driver.navigate().refresh();
				  Thread.sleep(2000);
				clickXpath("//input[@id='BinnerAssignment_Checkbox 0']");
				  Thread.sleep(2000);
				  clickXpath("//button[@tabindex='0']");
				  Thread.sleep(2000);
				clickXpath(".//*[contains(text(),'PROCEED')]","Proceed");
				//verify("(//*[contains(.,'Warehouse')])[14]","Warehouse");
				//System.out.println(xpathGetText("(//*[contains(.,'Warehouse')])[14]"));
				//clickXpath(BINNING_CLOSE_BTN,"BINNING_CLOSE_BTN");
				//driver.navigate().refresh();
				  Thread.sleep(2000);

				clickXpath("//SPAN[@class='close']","Close");

				childTest.pass("NVNUpart ShortQty Binning Request Sent To Warehouse Approval");
				
				clickIDLinkText("Misc");
				clickIDLinkText("Approve Request");
				ApproveRequest("shortbinapprove",CustReference);

			
				partStockCheck(partnvnu,  "binning",4);

				childTest.pass("NVNUpart ExcessQty Approval Request Approved Successfully");

				clickIDLinkText("Inbound");


				clickIDLinkText("Binning");
				

			}
			
		}
		Thread.sleep(1000);
		enterXpath(SEARCH_BOX, CustReference,"BINNING SEARCH");
		verify("//*[contains(text(),'0-0 of 0')]");
		childTest.pass("validation completed");
		System.out.println("short binning completed");
		logOut();
	}			
}










