package testScripts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import testScripts.CommonFunctions.Module;


public class Intersite_BoxBinning extends ProjectSpecificFunctions {
	@Test
	public void scan_to_box() throws IOException, Exception
	{		parantTest = extent.createTest("Intersite: Inbound_ScanToBox_Testing");   

		loginAsUser(Username);
		NVLPNpicking=new  ArrayList<String>();
		Lpn = new HashMap<String ,String>();
		vehicleCreation(site);
		partv="BOXINV";
		partnv="BOXINNV";
		partnvnu="BOXINNVNU";
		picktype="FIFOSUGGESTEDBOX";
		binPickPart=new String[]{partv,partnv,partnvnu};
		binPickPartquantity=new int[]{2,2,2};
		part=new String[]{partv,partnv,partnvnu};
		quantity=new String[]{"2","2","2"};	

		writeDataForCSV(picktype, "Newdelpoint", "po", part, quantity);
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
		binning("box",CustReference,Arrays.asList(binPickPart), binPickPartquantity);
		binning("binbox",CustReference,Arrays.asList(binPickPart), binPickPartquantity);


		parantTest = extent.createTest("Intersite: Outbound_ScanToBox_Testing");
		  writeDataForCSV(picktype,"intersite","so", part,quantity);


		pickgenerationFIFO();
		pickerassign1(SOCustReference);
		ArrayList<String>picked=pickingScreenFS("",SOCustReference,Arrays.asList(binPickPart),binPickPartquantity);

		consolidation( SOCustReference, VON);
disp(SOCustReference, "", picked);
		//dispatchbox();
		
		logOut();
		CustReference=SOCustReference;
	    site=prop.getProperty("site2");
		loginAsUser(prop.getProperty("USERNAME1"));
		vehicleCreation(site);
		childTest = parantTest.createNode("Scan PACKING SLIP ");
		clickIDLinkText(DASHBOARD);
		driver.navigate().refresh();
		childTest.info("Vehicle number is :  " + VEHICLENUMBER);
		verify(GATEIN_1stROW);
		enterXpath(SEARCH_BOX, VEHICLENUMBER,"Dock in searchbox");
		clickXpath(VEHICLE1,"Vehicle number");
		clickXpath(DOCKIN_BTN,"DOCKIN_BTN");
		ScanPackingSlip();
		childTest.log(Status.PASS,"The Scanned PO parts  details : ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
		shortWait(2000);
		Binner=prop.getProperty("POD_USER1");
		PodUser=prop.getProperty("POD_USER1");

		childTest.log(Status.PASS,"Scanned succesfully: ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		childTest.pass("packing slip scanned ");
		assignpod(PodUser);
		podGeneration();
		binnerAssignment(CustReference,Binner);
		binning("binbox",CustReference,Arrays.asList(new String[]{partv,partnv,partnvnu}), new int[]{5,5,5});
		logOut();
		System.out.println("site to site completed");

	}

	public String CON_VON ;
	public void consolidation(String SOCustReference,String VON) throws Exception {
		
		clickXpath(CONSOLIDATIONGRID,"CONSOLIDATIONGRID");
		enterXpath (MASTERSEARCH1,SOCustReference,"MASTERSEARCH");
		clickXpath("(//button[@type='button'])[3]","Search button");
		Thread.sleep(1000);

		
			CON_VON=xpathGetText("//td[@id='Optimization_OrderId']");
			System.out.println(CON_VON);

				if(CON_VON.contains(VON)&&CON_VON.contains("/")){
					System.out.println("pass");
					String address1="1, LUZ CHURCH ROAD, CHENNAI, TAMIL NADU, INDIA, 600004";
					verify("//tr[1][@id='Optimization_Table']/td[6]",address1);
					clickXpath("//*[@id='Optimization_SONumber']/span[2]");
					List <WebElement> ROW_Count= driver.findElements(By.xpath("//*[@id='simple-popper']/div/p/table/tbody/tr"));
					System.out.println("The total row count in the table is "+ ROW_Count.size());
					for(int j=1;j<=ROW_Count.size();j++){
						String CUS_NO = xpathGetText("//*[@id='simple-popper']/div/p/table/tbody/tr["+j+"]/td");
						System.out.println("The Customer no is "+ CUS_NO);
						if(SOCustReference.equalsIgnoreCase(CUS_NO)){
							clickXpath("//*[@id='simple-popper']/div/p/table/tbody/tr["+j+"]/td/button","split");
							Thread.sleep(1000);
							scrollUp();
							clickXpath("//input[@id='Optimization_checkbox0']","ORDERCLICK");
							clickXpath(CONSOLIDATIONSKIP,"CONSOLIDATIONSKIP");
							clickXpath(CONSOLIDATIONYES,"CONSOLIDATIONYES");
							Thread.sleep(2000);

							clickXpath(ALERT,"Close");
							deleteFile();
						}	
					}
				}

				else  /*(VON.equals(CON_VON))*/{
					String address1="1, LUZ CHURCH ROAD, CHENNAI, TAMIL NADU, INDIA, 600004";
					verify("//tr[1][@id='Optimization_Table']/td[6]",address1);
					clickXpath("//input[@id='Optimization_checkbox0']","ORDERCLICK");
					clickXpath(CONSOLIDATIONSKIP,"CONSOLIDATIONSKIP");
					clickXpath(CONSOLIDATIONYES,"CONSOLIDATIONYES");
					Thread.sleep(2000);
					childTest.log(Status.INFO, "Consolidation Skipped:  ",MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture1()).build());
					verify(ALERT);

					clickXpath(ALERT,"Close");
					deleteFile();
				}

		
	}

}



