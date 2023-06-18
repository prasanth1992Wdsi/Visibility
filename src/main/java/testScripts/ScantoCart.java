
package testScripts;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class ScantoCart extends ProjectSpecificFunctions {
	public String CARTLABEL_QNTY="3"; 
	public ArrayList<String> Outbound_cartLabel=new  ArrayList<String>();
	public String  VON;
	public int i;
	public String NVCartLabel;

	
	
	public void cartLabel_consolidation1() throws Exception {

		childTest = parantTest.createNode("CartLabel Consolidation ");
		ArrayList <String>lpns=new ArrayList<String>();
		clickXpath(CONSOLIDATIONGRID,"CONSOLIDATIONGRID");
		verify(CONSOLIDATION);
		enterXpath(MASTERSEARCH1, SOCustReference,"PICKING SEARCH");
		Thread.sleep(1000);
		clickXpath(OPTISEARCH,"OPTIMIZATIONSEARCH");
		Thread.sleep(1000);
		Thread.sleep(1000);
		verify("//*[contains(text(),'1-1 of 1')]");
		clickXpath(ORDERCLICK,"ORDERCLICK");

		childTest.info("The order available in Consolidation screen");


		clickXpath(CONSOLIDATION,"CONSOLIDATION");
		Thread.sleep(2000);
		enterXpath(NOOFBOX, "1","NOOFBOX");
		clickXpath(ADDBOX,"ADDBOX");
		CartLabel = xpathGetText(LABEL);
		Thread.sleep(2000);
		clickXpath(SCANBOX,"SCANBOX");
		enterXpath(ENTERLPN, CUSLPN,"ENTERLPN");
		clickXpath(SUBMIT,"SUBMIT");


		enterXpath(NUNVQNTY, readXL("SO", 2, "QUANTITY"),"NUNVQNTY");
		clickXpath(SUBMIT,"SUBMIT");
		childTest.pass("The NVNU part Lpns Scanned successfully by breaking CartLabel");
		if(Lpn!=null)
		{
			for(Map.Entry<String, String> Pick_Lpn:Lpn.entrySet()){
				String  VLPN = Pick_Lpn.getKey();
				System.out.println(VLPN);
				Thread.sleep(1000);
				enterXpath(ENTERLPN, VLPN ,"ENTERLPN");
				clickXpath(SUBMIT,"SUBMIT");
				System.out.println("LPN 1 ");
			}
		}
		if(VLPN!=null){
			Thread.sleep(1000);
			enterXpath(ENTERLPN, VLPN.get(0) ,"Enter Partial Quantity");
			System.out.println(VLPN.get(0));
			clickXpath(SUBMIT,"SUBMIT");
		}
		Thread.sleep(1000);
		childTest.pass("The V part Lpns Scanned successfully by breaking CartLabel");
		Thread.sleep(1000);
		enterXpath(WEIGHT, "10","WEIGHT");
		Select select1 = new Select(driver.findElement(By.xpath(UOM)));
		Thread.sleep(1000);
		select1.selectByVisibleText("Ton");
		Thread.sleep(1000);
		clickXpath(COMPLETE,"COMPLETE");
		Thread.sleep(1000);

		clickXpath(FINISH,"FINISH");

		Thread.sleep(2000);

		clickXpath("//*[@id='buttonSave'][1]");

		Thread.sleep(2000);
		childTest.pass("The order Consolidated successfully for V and NVNU part");
		childTest.info("The carton label is :" + CartLabel);
		enterXpath(MASTERSEARCH1, SOCustReference,"PICKING SEARCH");
		actionEnter();
		verify("//*[contains(text(),'No Records Found')]");
		System.out.println("consolidation completed successfully");
		childTest.pass("validation completed");

	}
	public void cartLabel_dispatch() throws Exception {

		childTest = parantTest.createNode("CartLabel Dispatch ");
		clickXpath(DISPATCHGRID,"DISPATCHGRID");
		verify(DISPATCH_REQNO);
		enterXpath(DISPATCHSEARCH, SOCustReference,"DISPATCHSEARCH");
		verify("//*[contains(text(),'1-1 of 1')]");
		clickXpath(Dispatch1,"Dispatch1");


		Thread.sleep(1000);
		clickXpath(DISPATCHBUTTON,"Dispatch1");

		childTest.info("The order available in Dispatch Screen");

		enterXpath(CARRIER, "ScantoCart","CARRIER");
		childTest.info("The Mode of carrier is :" + "ScantoCart");
		enterXpath(CARRIERREF, "SmokeTest","CARRIERREF");
		childTest.info("The carrier reference is :" + "SmokeTest");


		enterXpath(SCANLPN, CartLabel,"SCANLPN");

		actionEnter();


		for(String apicking:NVLPNpicking)
		{Thread.sleep(500);
		enterXpath(SCANLPN, apicking,"NVSCANLPN");
		Thread.sleep(500);
		actionEnter();
		}
		childTest.pass("The NV part Lpns Scanned successfully by breaking CartLabel");

		Thread.sleep(2000);
		Thread.sleep(2000);
		enterXpath(DISPATCHSEARCH, SOCustReference,"DISPATCHSEARCH");
		verify("//*[contains(text(),'No Records Found')]");
		childTest.pass("The order is Dispatched successfully by breaking CartLabel");
		System.out.println("Dispatch completed sucessfully ");
		System.out.println("*********************************************************");


	}
	@Test
	public void fifoPicktypeInb() throws IOException, Exception {
		parantTest = extent.createTest("ScanToCart picking for FIFO picktype");    

		loginAsUser(Username);

		partv="PARTVFIFO";
		partnv="PARTNVFIFO";
		partnvnu="PARTNVNUFIFO";
		picktype="FIFO";
		/*NVLPNpicking=new  ArrayList<String>();
		Lpn = new HashMap<String ,String>();*/

		vehicleCreation(site);



		System.out.println("scan to cart(consolidate) started");
		part=new String[]{partv,partnv,partnvnu};
		quantity=new String[]{"5","5","5"};	
		binPickPart=new String[]{partv,partnv,partnvnu};
		binPickPartquantity=new int[]{5,5,5};
		writeDataForCSV(picktype,"Newdelpoint","po", part,quantity);

		quantity=new String[]{"2","2","2"};	


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
		clickXpath(VEHICLE1,"Vehicle number");
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
		System.out.println(partv+" "+partnv+" "+partnvnu);
		//	binning1();	
		binning("",CustReference,Arrays.asList(new String[]{partv,partnv,partnvnu}), new int[]{5,5,5});
		binPickPartquantity=new int[]{2,2,2};

		parantTest = extent.createTest("Outbound process for cart label order level");

		for(int i=0;i<=1;i++)
		{
			writeDataForCSV(picktype,"Newdelpoint","SO", part,quantity);
			if(i==1)
			{
				parantTest = extent.createTest("Outbound process for cart label site level");

				verify(OUTBOUND);

				clickXpath("//b[text()='Outbound']","OUTBOUND");

			}
			pickgenerationFIFO();
			
			pickerassign1(SOCustReference);
			clickXpath("//b[text()='Outbound Label']","label");
			if(i==0)
			{
				Label_Reprint(SOCustReference.toLowerCase(), "cartorderlevel");
				cart=new ArrayList<String>();
			}
			else
			{
				cart=new ArrayList<String>();

				Label_Reprint("1", "cartlabel");
			


			}
			cart.add(VLPN.get(0));
			cart.add(VLPN.get(0));
			cart.add(VLPN.get(0));
			System.out.println("cart  :"+cart);
			//pickingScreenFS("cart",SOCustReference,Arrays.asList(new String[]{partv,partnv,partnvnu}), new int[]{2,2,2});
			ArrayList<String>picked=pickingScreenFS("cart",SOCustReference,Arrays.asList(binPickPart),binPickPartquantity);
			 disp(SOCustReference,"",cons( SOCustReference,"",picked));

		//	cartLabel_consolidation1();
		//	cartLabel_dispatch();
		}
		System.out.println("scan to cart(consolidate) completed");
		logOut();

	}
	

}
