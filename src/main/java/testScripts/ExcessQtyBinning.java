
package testScripts;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class ExcessQtyBinning extends ProjectSpecificFunctions{


	@Test
	public void excessbinning() throws IOException, Exception {
		parantTest = extent.createTest("Inbound_Excess_Testing");

		loginAsUser(Username);

		System.out.println("excess binning started");
		NVLPNpicking=new  ArrayList<String>();
		Lpn = new HashMap<String ,String>();
		vehicleCreation(site);
		partv="EXCESSVTYPE";
		partnv="EXCESSNVTYPE";
		partnvnu="EXCESSNVNUTYPE";
		picktype="FIFO";
		 part=new String[]{partv,partnv,partnvnu};
			quantity=new String[]{"5","5","5"};	
			binPickPart=new String[]{partv,partnv,partnvnu};
			binPickPartquantity=new int[]{5,5,5};
	    writeDataForCSV(picktype,"Newdelpoint","po", part,quantity);
		//shortWait(4000);
		poreadData();
		vqty();
		nvnuqty();
		//nvQTY1("SO");
		//excessqty("PO");
	//	excessqty("SO");
	
		System.out.println("captureParentDetails-----------");
		driver.navigate().refresh();

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
		shortWait(2000);
		childTest.pass("Order upload finished");


	
		assignpod(PodUser);

	

		childTest = parantTest.createNode("POD Generation");

		clickIDLinkText(POD_DASHBOARD);

		verify(ROW1);		
		enterXpath(SEARCH_BOX, VEHICLENUMBER,"SEARCH_BOX");

		clickXpath(ROW1,"Vehicle Record");


		enterXpath(EXPECTED_QTY, prop.getProperty("EXPECTED_QTY"),"EXPECTED_QTY");



		enterXpath(RECEIVED_QTY, prop.getProperty("RECEIVED_QTY"),"RECEIVED_QTY");



		clickXpath("//INPUT[@id='damageCheck']","Damagecheck");

		enterXpath(DAMAGED_QTY,prop.getProperty("DAMAGE_QTY"),"DAMAGED_QTY");



		clickXpath(POD_SAVE_BTN,"POD_SAVE_BTN");

		clickXpath(POD_CLOSE_BTN,"POD_CLOSE_BTN");


		childTest.pass("Conditional POD Generated Successfully for received quantity : "+prop.getProperty("RECEIVED_QTY")+" expected quantity : "+prop.getProperty("DAMAGE_QTY"));
		clickIDLinkText("Misc");
		clickIDLinkText("Approve Request");
		
			
		ApproveRequest("podapprove",VEHICLENUMBER);



		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("window.scrollTo(document.body.scrollHeight, 0)");


		clickIDLinkText("Inbound");
		childTest.pass("Conditional POD Approved Successfully");




	
		binnerAssignment(CustReference,Binner);


	

		childTest = parantTest.createNode("Binning");
		clickXpath("//b[text()='Binning']", "Binning");
		for (int i1 = 0; i1 <=2; i1++) {
			verify(PARTTYPE_CELL);
			/*wait = new WebDriverWait(driver, 20);
				wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(fluentWait(PARTTYPE_CELL))));
			 */
			enterXpath(SEARCH_BOX, CustReference,"BINNING SEARCH");
					
			String parttype = xpathGetText(PARTTYPE_CELL);

			String partname=xpathGetText(PARTName_CELL);
			Thread.sleep(2000);

			if (parttype.equalsIgnoreCase("V")) {

				partStockCheck(partv,  "gr",5);


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

				enterXpath(BIN_TEXT_BOX, LPNV,"LPN field");

				clickXpath(BINNING_SUBMIT_BTN,"BINNING_SUBMIT_BTN");

				//int vqtyexcess = Integer.parseInt(VQTY); 
				int i=VPQTY;  
				String s=String.valueOf(i+1);
				verify("(//*[contains(.,'Enter')])[14]");
				enterXpath(VPART_QTY_TXT,s,"VPART_QTY_TXT");
				actionEnter();


				clickXpath(".//*[contains(text(),'PROCEED')]","Proceed");

				verify("(//*[contains(.,'Warehouse')])[14]","Warehouse");
				//System.out.println(xpathGetText("(//*[contains(.,'Warehouse')])[14]"));
				clickXpath(BINNING_CLOSE_BTN,"BINNING_CLOSE_BTN");
				childTest.pass("Vpart ExcessQty Binning Request Sent To Warehouse Approval");
				clickIDLinkText("Misc");
				clickIDLinkText("Approve Request");
				
				ApproveRequest("excessapprove",CustReference);



			
				childTest.pass("Vpart ExcessQty Approval Request Approved Successfully");
				JavascriptExecutor js1 = (JavascriptExecutor) driver;

				js1.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
                Thread.sleep(1000);
				clickIDLinkText("Inbound");

				clickIDLinkText("Binning");
				
				partStockCheck(partv,  "binning",Integer.parseInt(s));

			}

			else if (parttype.equalsIgnoreCase("NV")) {
				 NVLPN=new  ArrayList<String>();
				partStockCheck(partnv,  "fb",5);


				clickXpath(INFO_BTN,"INFO_BTN");


				for(int i=1;i<=Integer.parseInt(readXL("PO", 1, "QUANTITY"));i++)
				{String NVLPN1 = xpathGetText("(//TD[@data-label='LPN'])["+i+"]");
				NVLPN.add(NVLPN1);
				System.out.println(NVLPN1);
				}


				clickXpath(INFO_CLOSEBTN,"INFO_CLOSEBTN");
				Thread.sleep(1000);
				verify("(//*[@id='BinnerDashboard_Scanbutton'])[1]");
				clickXpath("(//*[@id='BinnerDashboard_Scanbutton'])[1]","SCAN_BTN");

				//	clickXpath(SCANTOBIN_RADIOBTN,"SCANTOBIN_RADIOBTN");
				clickXpath(SCANTOBIN_SUMBIT,"SCANTOBIN_SUMBIT");
				childTest.pass("NV parts binning started through Scan to Bin option");
				verify("(//*[contains(.,'Please')])[14]","Please");
				enterXpath(BIN_TEXT_BOX, prop.getProperty("BIN_LABEL"),"BIN_TEXT_BOX");
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
						verify("(//*[contains(.,'Successfully')])[14]","Successfully");
						partStockCheck(partnv,  "binning",1);
						System.out.println("After binning nv Qty : "+quantity);

						//verify("(//*[contains(.,'SCANNED')])[14]",""+i+" / "+NVLPN.size());

					}


				}
				verify("(//*[contains(.,'Successfully')])[14]","Successfully");
				driver.navigate().refresh();
				partStockCheck(partnv,  "binning",1);
				childTest.pass("NV parts Binned Successfully");


			} else if (parttype.equalsIgnoreCase("NVNU")) {
				partStockCheck(partnvnu,  "eg",5);

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

				String s1=String.valueOf(j+1); 
				enterXpath(NVNUPARTQTY, s1,"NVNUPARTQTY");
				clickXpath(BINNING_SUBMIT_BTN,"BINNING_SUBMIT_BTN");



				clickXpath(".//*[contains(text(),'PROCEED')]","PROCEED");
				//verify("(//*[contains(.,'Warehouse')])[14]","Warehouse");
				System.out.println(xpathGetText("(//*[contains(.,'Warehouse')])[14]"));
				Thread.sleep(2000);
				//actionClick(BINNING_CLOSE_BTN);
				driver.navigate().refresh();
				childTest.pass("NVNUpart ExcessQty Binning Request Sent To Warehouse Approval");
				Thread.sleep(2000);
				clickIDLinkText("Misc");
				clickIDLinkText("Approve Request");
				
				 ApproveRequest("excessapprove",CustReference);
	
				partStockCheck(partnvnu,  "binning",Integer.parseInt(s1));

				childTest.pass("NVNUpart ExcessQty Approval Request Approved Successfully");
				JavascriptExecutor js1 = (JavascriptExecutor) driver;

				js1.executeScript("window.scrollTo(document.body.scrollHeight, 0)");

				clickIDLinkText("Inbound");


				clickIDLinkText("Binning");

				
			}
		}
		Thread.sleep(1000);
		enterXpath(SEARCH_BOX, CustReference,"BINNING SEARCH");
		verify("//*[contains(text(),'0-0 of 0')]");
		childTest.pass("validation completed");

		logOut();
	}}










