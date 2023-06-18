package testScripts;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class Backorderscenarios extends ProjectSpecificFunctions	 {

	ArrayList<String> VONS=new  ArrayList<String>();
    HashMap<String, String> partOrder= new HashMap<String, String>();
    ArrayList<String> OrderQuantity;
    HashMap<String,String> disLpns1=new HashMap<String,String>();
	ArrayList<String> order=new ArrayList<String>();
	int availableV;
	int availableNV;
	int availableNVNU;

	String backOrderID="";

	@Test(priority=22,alwaysRun=true)
	public void backorder() throws Exception
	{
		partv="NEWBACKV";
		partnv="NEWBACKNV";
		partnvnu="NEWBACKNVNU";
		Bin="back";
		picktype="FIFO";
		Binner=prop.getProperty("BINNINGUSER_NAME");
		Picker=prop.getProperty("BINNINGUSER_NAME");
		PodUser=prop.getProperty("BINNINGUSER_NAME");
		Username=prop.getProperty("USERNAME");
		backordershortpick();
		prepick();
	}
	public void backordershortpick() throws Exception {
		account1=true;
		account=prop.getProperty("ACCOUNTID1");
		OrderQuantity=new  ArrayList<String>();
		NVLPNpicking=new  ArrayList<String>();
		Lpn = new HashMap<String ,String>();
	
		parantTest = extent.createTest("Back order scenario: order creation after short pick");
		loginAsUser(Username);
		NVLPNpicking=new  ArrayList<String>();
		Lpn = new HashMap<String ,String>();

		vehicleCreation(site);
		part=new String[]{partv,partnv,partnvnu};
		quantity=new String[]{"3","3","3"};	
		binPickPart=new String[]{partv,partnv,partnvnu};
		binPickPartquantity=new int[]{3,3,3};
		writeDataForCSV(picktype,"Newdelpoint","po", part,quantity);


		childTest = parantTest.createNode("PO Order creation for fifo picktype _ Back order");
		clickIDLinkText(DASHBOARD);
		driver.navigate().refresh();
		childTest.info("Vehicle number is :  " + VEHICLENUMBER);
		verify(GATEIN_1stROW);
		enterXpath(SEARCH_BOX, VEHICLENUMBER,"Dock in searchbox");
		VehicleID = xpathGetText(GATEIN_1stROW);
		clickXpath(VEHICLE1,"Vehicle number");
		clickXpath(DOCKIN_BTN,"DOCKIN_BTN");

		uploadFIFO("1");

		childTest.log(Status.PASS,"The uploaded PO parts details for fifo Picktype Back Order: ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		verify(FINISH_UPLOAD_BTN);
		clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
		shortWait(2000);
		childTest.pass("Order upload finished for fifo Picktype _ Back order");
		assignpod(PodUser);
		podGeneration();

		binnerAssignment(CustReference,Binner);
		System.out.println(partv+" "+partnv+" "+partnvnu);
		
		binning("",CustReference,Arrays.asList(new String[]{partv,partnv,partnvnu}),  binPickPartquantity);
		childTest.pass("Binning Completed for fifo Picktype _ Back order");

		childTest = parantTest.createNode("Sales Order Generation");

		partStockCheck(partnv,   "k", 6);
		partStockCheck(partnvnu,   "k", 6);
		partStockCheck(partv,   "k", 6);

		part=new String[]{partv,partnv,partnvnu};
		quantity=new String[]{Available.get(partv)+1+"",Available.get(partnv)+1+"",Available.get(partnvnu)+1+""};	
		writeDataForCSV(picktype,"Newdelpoint","so", part,quantity);
		ReadExcel(prop.getProperty("SOPATH"));

		availableV=Available.get(partv);
		availableNV=Available.get(partnv);
		availableNVNU=Available.get(partnvnu);
		ArrayList<String> VON=new  ArrayList<String>();
	
		verify(OUTBOUND);
		clickXpath(OUTBOUND,"OUTBOUND");
		verify(OUTBOUND);


		clickIDLinkText("Pick Generation");
		verify(PICKGENERATION);
		partStockCheck(partnv,  "ijk",2);
		partStockCheck(partnvnu,  "mj",2);
		partStockCheck(partv,  "mj",2);



		clickXpath("//span[contains(.,'Create request')]","Create request");
		Thread.sleep(1500);
		clickXpath("(//*[@id='PickGen_Custname'])");
		Thread.sleep(1000);
		clickDownArrow();
		actionEnter();
		File file = new File(prop.getProperty("SOPATH"));

		enterXpath(CHOOSEFILE,file.getAbsolutePath());

		clickXpath(UPLOADSO,"UPLOADSO");

		Thread.sleep(3000);
		CommonFunctions.childTest.log(Status.PASS,"File upload status",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build());
		verify(ALERTBOXCANCEL);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		verify(ALERTBOXCANCEL);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		partStockCheck(partnv,  "pickgen",availableNV);
		partStockCheck(partnvnu,  "pickgen",availableNVNU);
		partStockCheck(partv,  "pickgen",availableV);

		Thread.sleep(2000);
		enterXpath(MASTERSEARCH, SOCustReference,"MASTERSEARCH");


		clickXpath("//*[@id='PickGen_searchButton']", "search");
		Thread.sleep(1000);
		CommonFunctions.childTest.log(Status.PASS,"Orders Created in Pick Generation Screen",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build());
		List<WebElement> column=driver.findElements(By.xpath("//*[@id='PickGen_VON']"));
		int colCount=column.size();
		System.out.println(colCount);

		for(int i=1;i<=colCount;i++)
		{

			String colText=xpathGetText("(//*[@id='PickGen_VON'])["+i+"]");
			VONS.add(colText);
			Thread.sleep(1000);
		}
		System.out.println(VONS);
		Thread.sleep(2000);
		clickXpath(SELECTPART,"Checkbox");

		childTest.info("The order available in the pick generation screen");



		clickXpath(PICKERASSIGN,"PICKERASSIGN");

		verify(PICKERSUBMIT);

		enterXpath(PICKERINPUT, Picker,"PICKERINPUT");
		nameClick(Picker);
		resize(1382, 650);

		clickXpath(PICKERSUBMIT,"PICKERSUBMIT");

		Thread.sleep(2000);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		driver.manage().window().maximize();
		driver.navigate().refresh();

		Thread.sleep(2000);



		pickingScreenFS("Shortpick",SOCustReference,Arrays.asList(binPickPart),binPickPartquantity);

		//Thread.sleep(2500);
		clickIDLinkText("Misc");
		clickIDLinkText("Approve Request");
		
		ApproveRequest("shortpickapproveall",SOCustReference);
		
		verify(OUTBOUND);
		clickXpath(OUTBOUND,"OUTBOUND");
	
		verify(OUTBOUND);
		Thread.sleep(1000);
		clickXpath(PICKINGGRID,"PICKINGGRID");

		Thread.sleep(2000);
		enterXpath(PICKINGSEARCH, SOCustReference,"MASTERSEARCH");
		clickXpath(PICKSEARCH, "search");
		Thread.sleep(1000);
		childTest.log(Status.PASS,"Validation in Picking Screen: ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		verify("//*[contains(text(),'No Records Found')]");
		childTest.pass("validation completed");


		VONS=new  ArrayList<String>();
		clickIDLinkText("Pick Generation");
		childTest = parantTest.createNode("Pick Generation");
		enterXpath(MASTERSEARCH, SOCustReference,"MASTERSEARCH");


		clickXpath("//*[@id='PickGen_searchButton']", "search");
		Thread.sleep(1000);
		CommonFunctions.childTest.log(Status.PASS,"New Orders Created in Pick Generation Screen",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build());
		column=driver.findElements(By.xpath("//*[@id='PickGen_VON']"));
		colCount=column.size();
		System.out.println(colCount);

		for(int i=1;i<=colCount;i++)
		{

			String colText=xpathGetText("(//*[@id='PickGen_VON'])["+i+"]");
			VONS.add(colText);
			Thread.sleep(1000);
		}
		System.out.println(VONS);
		Thread.sleep(2000);
		clickXpath(SELECTPART,"Checkbox");

		childTest.info("The order available in the pick generation screen");



		clickXpath(PICKERASSIGN,"PICKERASSIGN");

		verify(PICKERSUBMIT);

		enterXpath(PICKERINPUT, Picker,"PICKERINPUT");
		nameClick(Picker);


		clickXpath(PICKERSUBMIT,"PICKERSUBMIT");

		Thread.sleep(2000);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		driver.navigate().refresh();
		Thread.sleep(2000);
		backOrderPicking("",SOCustReference,Arrays.asList(binPickPart),binPickPartquantity);

		skipConsolidation(SOCustReference);
		backOrderDispatch();
		Thread.sleep(1000);
		logOut();
	}
	//@Test(priority=15,alwaysRun=true)
	public void prepick() throws Exception {
		account1=true;
		account=prop.getProperty("ACCOUNTID1");
		OrderQuantity=new  ArrayList<String>();
		NVLPNpicking=new  ArrayList<String>();
		Lpn = new HashMap<String ,String>();
		
		parantTest = extent.createTest("Back order scenario: order creation after pre pick cancellation");
		loginAsUser(Username);
		NVLPNpicking=new  ArrayList<String>();
		Lpn = new HashMap<String ,String>();

		vehicleCreation(site);
		part=new String[]{partv,partnv,partnvnu};
		quantity=new String[]{"3","3","3"};	
		binPickPart=new String[]{partv,partnv,partnvnu};
		binPickPartquantity=new int[]{3,3,3};
		writeDataForCSV(picktype,"Newdelpoint","po", part,quantity);


		childTest = parantTest.createNode("PO Order creation for fifo picktype _ Back order");
		clickIDLinkText(DASHBOARD);
		driver.navigate().refresh();
		childTest.info("Vehicle number is :  " + VEHICLENUMBER);
		verify(GATEIN_1stROW);
		enterXpath(SEARCH_BOX, VEHICLENUMBER,"Dock in searchbox");
		VehicleID = xpathGetText(GATEIN_1stROW);
		clickXpath(VEHICLE1,"Vehicle number");
		clickXpath(DOCKIN_BTN,"DOCKIN_BTN");

		uploadFIFO("1");

		childTest.log(Status.PASS,"The uploaded PO parts details for fifo Picktype Back Order: ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		verify(FINISH_UPLOAD_BTN);
		clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
		shortWait(2000);
		childTest.pass("Order upload finished for fifo Picktype _ Back order");
		assignpod(PodUser);
		podGeneration();

		binnerAssignment(CustReference,Binner);
		System.out.println(partv+" "+partnv+" "+partnvnu);

		binning("",CustReference,Arrays.asList(new String[]{partv,partnv,partnvnu}),  binPickPartquantity);
		childTest.pass("Binning Completed for fifo Picktype _ Back order");

		childTest = parantTest.createNode("Sales Order Generation");

		partStockCheck(partnv,   "k", 6);
		partStockCheck(partnvnu,   "k", 6);
		partStockCheck(partv,   "k", 6);

		part=new String[]{partv,partnv,partnvnu};
		quantity=new String[]{Available.get(partv)+1+"",Available.get(partnv)+1+"",Available.get(partnvnu)+1+""};	
		writeDataForCSV(picktype,"Newdelpoint","so", part,quantity);
		ReadExcel(prop.getProperty("SOPATH"));

		availableV=Available.get(partv);
		availableNV=Available.get(partnv);
		availableNVNU=Available.get(partnvnu);

		verify(OUTBOUND);
		clickXpath(OUTBOUND,"OUTBOUND");
		verify(OUTBOUND);


		clickIDLinkText("Pick Generation");
		verify(PICKGENERATION);
		partStockCheck(partnv,  "ijk",2);
		partStockCheck(partnvnu,  "mj",2);
		partStockCheck(partv,  "mj",2);



		clickXpath("//span[contains(.,'Create request')]","Create request");
		Thread.sleep(1500);
		clickXpath("(//*[@id='PickGen_Custname'])");
		Thread.sleep(1000);
		clickDownArrow();
		actionEnter();
		File file = new File(prop.getProperty("SOPATH"));

		enterXpath(CHOOSEFILE,file.getAbsolutePath());

		clickXpath(UPLOADSO,"UPLOADSO");

		Thread.sleep(3000);
		CommonFunctions.childTest.log(Status.PASS,"File upload status",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build());
		verify(ALERTBOXCANCEL);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		verify(ALERTBOXCANCEL);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		partStockCheck(partnv,  "pickgen",availableNV);
		partStockCheck(partnvnu,  "pickgen",availableNVNU);
		partStockCheck(partv,  "pickgen",availableV);
		Thread.sleep(2000);
		enterXpath(MASTERSEARCH, SOCustReference,"MASTERSEARCH");


		clickXpath("//*[@id='PickGen_searchButton']", "search");
		Thread.sleep(1000);
		CommonFunctions.childTest.log(Status.PASS,"Orders Created in Pick Generation Screen",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build());
		List<WebElement> column=driver.findElements(By.xpath("//*[@id='PickGen_VON']"));
		int colCount=column.size();
		System.out.println(colCount);

		for(int i=1;i<=colCount;i++)
		{

			String colText=xpathGetText("(//*[@id='PickGen_VON'])["+i+"]");
			VONS.add(colText);
			Thread.sleep(1000);
		}
		System.out.println(VONS);
		Thread.sleep(2000);

		clickIDLinkText("Misc");
		clickIDLinkText("Cancellation");
		cancellation(SOCustReference, "", "",0, 1);
		Thread.sleep(2000);
		for(String SO:VONS)
		{
			clear1(CANCEL_SOSEARCH);
			Thread.sleep(1000);
		enterXpath(CANCEL_SOSEARCH, SO,"CANCEL_SOSEARCH");
		
		clickXpath("(//*[@type='button'])[3]","button");
		Thread.sleep(1000);
		childTest.log(Status.PASS,"Validation in Picking Screen: ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		verify("//*[contains(text(),'No Records Found')]");
		childTest.pass("validation completed");
		Thread.sleep(1000);
		}

		shortWait(2000);
		verify(OUTBOUND);
		clickXpath(OUTBOUND,"OUTBOUND");
		verify(OUTBOUND);
		

		clickIDLinkText("Pick Generation");
		Thread.sleep(1000);
		for(String SO:VONS)
		{
		enterXpath(MASTERSEARCH, SO,"MASTERSEARCH");
		
		clickXpath("//*[@id='PickGen_searchButton']", "search");
		Thread.sleep(1000);
		childTest.log(Status.PASS,"Validation in Picking Screen: ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		verify("//*[contains(text(),'No Records Found')]");
		childTest.pass("validation completed");
		Thread.sleep(1000);
		clickXpath(CLEAR, "clear");
		}
		
		driver.navigate().refresh();
		VONS=new  ArrayList<String>();
		Thread.sleep(2000);
		enterXpath(MASTERSEARCH, SOCustReference,"MASTERSEARCH");


		clickXpath("//*[@id='PickGen_searchButton']", "search");
		Thread.sleep(1000);
		CommonFunctions.childTest.log(Status.PASS,"Orders Created in Pick Generation Screen",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build());
		column=driver.findElements(By.xpath("//*[@id='PickGen_VON']"));
		colCount=column.size();
		System.out.println(colCount);

		for(int i=1;i<=colCount;i++)
		{

			String colText=xpathGetText("(//*[@id='PickGen_VON'])["+i+"]");
			VONS.add(colText);
			Thread.sleep(1000);
		}
		System.out.println(VONS);
		Thread.sleep(2000);
		clickXpath(SELECTPART,"Checkbox");

		childTest.info("The order available in the pick generation screen");


		clickXpath(PICKERASSIGN,"PICKERASSIGN");

		verify(PICKERSUBMIT);

		enterXpath(PICKERINPUT, Picker,"PICKERINPUT");
		nameClick(Picker);


		clickXpath(PICKERSUBMIT,"PICKERSUBMIT");

		Thread.sleep(2000);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		driver.navigate().refresh();


		Thread.sleep(2000);
		backOrderPicking("",SOCustReference,Arrays.asList(binPickPart),binPickPartquantity);

		skipConsolidation(SOCustReference);
		backOrderDispatch();
		logOut();


	}
	public void backOrderPicking(String type,String SOCustReference,List allpart,int []quantity) throws Exception {
		childTest = parantTest.createNode("picking");
		
		Thread.sleep(2000);
		clickXpath(PICKINGGRID,"PICKINGGRID");
		deleteFile();
		for(String SO:VONS)
	{
		//Lpn=null ;
		VLPN=new ArrayList<String>();
		NVLPNpicking=new ArrayList<String>();
		if(!(SO.equalsIgnoreCase(backOrderID)))
		{

			enterXpath(PICKINGSEARCH, SO,"PICKINGSEARCH");
			clickXpath(PICKSEARCH, "search");
			Thread.sleep(2000);
			PartType = xpathGetText(PARTTYPE);
			partName=xpathGetText(PARTNAME);



			//Thread.sleep(4000);			    
			childTest.info("The order part  :"+partName +" :" +"  Part type  "+ PartType +" : part available in picking screen");
			if (PartType.equals("NVNU") ){
				partOrder.put(PartType,xpathGetText("(//*[@id='Picking_Action'])[1]"));

				clickXpath(PARTTYPE,"Select Part    :"+PartType);

				childTest.info("NVNU part picking started");


				clickXpath("//img[@alt='o']");
				CUSLPN = xpathGetText(PICKINGLPN);
				clickXpath(LPNCLOSE,"Infoclose");
				enterXpath(SERIALNOFIELD, CUSLPN,"SERIALNOFIELD");

				//clickXpath(SUBMITBTN,"SUBMITBTN");

				enterXpath(VQUANTITY, "1","NVNUQtn");


				clickXpath(SUBMITBTN,"SUBMITBTN");


				clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
				partStockCheck(partnvnu,  "picking",Integer.parseInt(NVNUQtn));


				childTest.log(Status.PASS,
						MarkupHelper.createLabel(" NVNU Type part picked sucessfully  ", ExtentColor.GREEN));
			}else if ((PartType.equals("NV"))&&(!partName.equals("PARTNVCUSLPN"))){
				partOrder.put(PartType,xpathGetText("(//*[@id='Picking_Action'])[1]"));

				clickXpath(PARTTYPE,"Select Part    :"+PartType);
				childTest.info("NV part picking started");


				clickXpath(LPNINFO1,"INFO");



				String NVLPN1picking = xpathGetText("/html/body/div[2]/div[2]/p/div[2]/table/tbody/tr[2]/td[4]");

				NVLPNpicking.add(NVLPN1picking);

				clickXpath(LPNCLOSE,"Infoclose");

				for(String apicking:NVLPNpicking)
				{
					enterXpath(SERIALNOFIELD, apicking,"LPNFIELD");

					//verify("(//*[contains(.,'Successfully')])[16]");					

					clickXpath(SUBMITBTN,"SUBMITBTN");
					disLpns1.put(PartType,apicking);
				}
				childTest.log(Status.PASS,
						MarkupHelper.createLabel("NV Type part picked sucessfully  :", ExtentColor.GREEN));

				Thread.sleep(2000);
				clickXpath(ALERTBOXCANCEL,"ALERTBOX");
				partStockCheck(partnv,  "picking",1);




			}
		
			else if ((PartType.equals("V"))&&(!partName.equals("PARTVCUSLPN"))){
				
				Lpn = new HashMap<String ,String>();

				partOrder.put(PartType,xpathGetText("(//*[@id='Picking_Action'])[1]"));
				// HashMap<String, String>  Lpn=null;
				clickXpath(PARTTYPE,"Select Part    :"+PartType);
				Thread.sleep(1000);

				clickXpath(LPNINFO1,"INFO");
				Thread.sleep(3000);
				WebElement mytable = driver.findElement(By.xpath("/html/body/div[2]/div[2]/p/div[2]/table"));
				List < WebElement > rows_table = mytable.findElements(By.tagName("tr"));
				int rows_count = rows_table.size();
				System.out.println("V Part row count "+rows_count);

				for (int row = 2; row <= rows_count; row++) {
					LPN=xpathGetText("//*[@id='simple-popper']/div[2]/p/div[2]/table/tbody/tr["+row+"]/td[4]");
					String QUANTITY=xpathGetText("//*[@id='simple-popper']/div[2]/p/div[2]/table/tbody/tr["+row+"]/td[5]");
					Lpn.put(LPN, QUANTITY);

				}
				System.out.println("V part lpns are : "+LPN);
				clickXpath(LPNCLOSE,"Infoclose");
				for(Map.Entry<String, String> Pick_Lpn:Lpn.entrySet()){
					String  VLPN = Pick_Lpn.getKey();
					System.out.println("Entered vpart lpns are" +VLPN);
					String  VQty11 = Pick_Lpn.getValue();
					enterXpath("//*[@id='Picking_serialLPN']", VLPN,"LPN");
					clickXpath("//*[@id='Picking_SubmitButton']","submit");
	
					enterXpath("//*[@id='Picking_enterQty']",VQty11,"Quantity");
					clickXpath("//*[@id='Picking_SubmitButton']","submit");
					Thread.sleep(2000);
					disLpns1.put(PartType,VLPN);
					String nu=xpathGetText("//*[@id='root']/div/div/main/div/div/div/div[3]/div/div/p/form/div[2]/div[2]/span[2]");
					num= nu.split("/");
					num_0 = num[0];
					num1 = num[1].split("\\(");
					num2 = num1[1].split("\\)");
				}
				Thread.sleep(1000);

				Partial_LPNQTY = num2[0];

				if(!Partial_LPNQTY.trim().equals("0") ){

					enterXpath("//*[@id='nooflabels']","1","Label");
					clickXpath("(.//*[@type='button'])[4]","submit");


					getStockNumber1(readPDF(),"LPN");



					enterXpath("//*[@id='Picking_serialLPN']", VLPN.get(0), "partial label LPN");
					enterXpath("//*[@id='Picking_enterQty']",Partial_LPNQTY , "Partial Quantity");
					clickXpath("//*[@id='Picking_SubmitButton']","submit");
					disLpns1.put(PartType,VLPN.get(0));
				}

				clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");

				partStockCheck(partv,  "picking",1);
			}

		}}

	System.out.println(partOrder);
	System.out.println("disp1 lpns:"+disLpns1);


	enterXpath(PICKINGSEARCH, SOCustReference,"PICKINGSEARCH");
	clickXpath(PICKSEARCH, "search");
	Thread.sleep(2000);
	 
	}
	public void backOrderDispatch() throws Exception{
		childTest = parantTest.createNode("BackOrder Dispatch ");
		clickXpath(DISPATCHGRID,"DISPATCHGRID");
		verify(Dispatch1);
		enterXpath(DISPATCHSEARCH, partOrder.get("NVNU"),"DISPATCHSEARCH");

		clickXpath(Dispatch1,"Dispatch1");
		

		Thread.sleep(1000);
		clickXpath(DISPATCHBUTTON,"Dispatch1");
	
		childTest.info("The order available in Dispatch Screen");

		enterXpath(CARRIER, "Testing","CARRIER");
		childTest.info("The Mode of carrier is :" + "Testing");
		Thread.sleep(1000);
		enterXpath(CARRIERREF, "SmokeTest","CARRIERREF");
		childTest.info("The carrier reference is :" + "SmokeTest");
		Thread.sleep(1000);
		enterXpath(SCANLPN, partnvnu,"LPNFIELD");
		actionEnter();

		enterXpath("//*[@id=\"Despatch_enterQty1\"]","1","Quantity");
		actionEnter();

		verify(Dispatch1);
		Thread.sleep(1000);
		enterXpath(DISPATCHSEARCH, partOrder.get("NV"),"DISPATCHSEARCH");

		clickXpath(Dispatch1,"Dispatch1");
	

		Thread.sleep(1000);
		clickXpath(DISPATCHBUTTON,"Dispatch1");
		
		childTest.info("The order available in Dispatch Screen");

		enterXpath(CARRIER, "Testing","CARRIER");
		childTest.info("The Mode of carrier is :" + "Testing");
		Thread.sleep(1000);
		enterXpath(CARRIERREF, "SmokeTest","CARRIERREF");
		childTest.info("The carrier reference is :" + "SmokeTest");
		Thread.sleep(1000);
		enterXpath(SCANLPN, disLpns1.get("NV"),"LPNFIELD");
		actionEnter();
		verify(Dispatch1);
		Thread.sleep(1000);
		enterXpath(DISPATCHSEARCH, partOrder.get("V"),"DISPATCHSEARCH");

		clickXpath(Dispatch1,"Dispatch1");
	

		Thread.sleep(1000);
		clickXpath(DISPATCHBUTTON,"Dispatch1");
		
		childTest.info("The order available in Dispatch Screen");

		enterXpath(CARRIER, "Testing","CARRIER");
		childTest.info("The Mode of carrier is :" + "Testing");
		Thread.sleep(1000);
		enterXpath(CARRIERREF, "SmokeTest","CARRIERREF");
		childTest.info("The carrier reference is :" + "SmokeTest");
		Thread.sleep(1000);
		enterXpath(SCANLPN, disLpns1.get("V"),"LPNFIELD");
		actionEnter();

		Thread.sleep(2000);

		enterXpath(DISPATCHSEARCH, SOCustReference,"DISPATCHSEARCH");

		Thread.sleep(3000);
		childTest.log(Status.PASS,"Validation in Dispatch Screen:",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build());
	}

}
