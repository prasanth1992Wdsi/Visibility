package testScripts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.codehaus.groovy.reflection.ClassLoaderForClassArtifacts;
import org.hamcrest.core.IsInstanceOf;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class BackOrder3 extends ProjectSpecificFunctions {
	ArrayList<String> order=new ArrayList<String>();
	int availableV;
	int availableNV;
	int availableNVNU;

	String VONfirst="";
	@Test(priority=20,alwaysRun=true)
	public void backordercreation() throws Exception {
		parantTest = extent.createTest("Outbound_Smoke_Testing for back order");
		backorder("normal");
		
	}
	@Test(priority=21,alwaysRun=true)
	public void backordercreation_BOX() throws Exception {
		parantTest = extent.createTest("Outbound_Smoke_Testing for back order(Box binning)");
		backorder("boxbin");
	}
	
	public void backorder(String type) throws Exception {
		

		loginAsUser(Username);
	
		account1=true;
		account=prop.getProperty("ACCOUNTID1");
		NVLPNpicking=new  ArrayList<String>();
		Lpn = new HashMap<String ,String>();
		if(type.contains("normal"))
		{
		partv="BACKV1";
		partnv="BACKNV1";
		partnvnu="BACKNVNU1";
		picktype="FIFO";
		Bin="back";
		}
		else if(type.contains("boxbin"))
		{
			partv="BACKV4";
			partnv="BACKNV4";
			partnvnu="BACKNVNU4";
		Bin="back";
		picktype="FIFOSUGBOX";
		}
		HashMap<String,String> disLpns1=new HashMap<String,String>();
		ArrayList<String> disLpns2=new ArrayList<String>();

		childTest = parantTest.createNode("Sales Order Generation");
		//back("SO");
		partStockCheck(partnv,   "k", 6);
		partStockCheck(partnvnu,   "k", 6);
		partStockCheck(partv,   "k", 6);

		part=new String[]{partv,partnv,partnvnu};
		quantity=new String[]{Available.get(partv)+1+"",Available.get(partnv)+1+"",Available.get(partnvnu)+1+""};	
		writeDataForCSV(picktype,"Newdelpoint","so", part,quantity);
	//	ReadExcel(prop.getProperty("SOPATH"));
		//	randomizer("SO");
		availableV=Available.get(partv);
		availableNV=Available.get(partnv);
		availableNVNU=Available.get(partnvnu);

	//	soreadData();
		verify(OUTBOUND);
		clickXpath(OUTBOUND,"OUTBOUND");
		verify(OUTBOUND);


		clickIDLinkText("Pick Generation");
		verify(PICKGENERATION);
		partStockCheck(partnv,  "ijk",2);
		partStockCheck(partnvnu,  "mj",2);
		partStockCheck(partv,  "mj",2);



		clickXpath("//span[contains(.,'Create request')]","Create request");
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
		//back("PO");
		
		 check(partv);
	int v=backOrderCount;
		check(partnv);
		int nv=backOrderCount;
		check(partnvnu);
		int nvnu=backOrderCount;
		
		
		
		
		quantity=new String[]{v+"",nv+"",nvnu+""};	
		
		
		writeDataForCSV(picktype,"Newdelpoint","po", part,quantity);
		
		int availablesize=3;
		if(backAvailable)
		{
			verify(CLICKRECORD);

			enterXpath(MASTERSEARCH, SOCustReference,"MASTERSEARCH");

			actionEnter();
			Thread.sleep(1000);
			VONfirst = xpathGetText(Picking_Von);

			clickXpath(CLICKRECORD, "order");
			Thread.sleep(2000);
			verify("(.//*[contains(.,'SO Records')])[6]");
			List<WebElement> a=driver.findElements(By.xpath("//tr"));
			availablesize=a.size()-1;
			System.out.println("available part : "+(a.size()-1));

		}
		vehicleCreation(site);
		//childTest = parantTest.createNode("Vehicle Creation");

		//			nvQTY1("SO");
		//			nvQTY5("PO");


		childTest = parantTest.createNode("PO Order creation for FIFO picktypes");
		clickIDLinkText("Inbound");


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
		childTest.log(Status.PASS,"The uploaded PO parts  details for BATCH Picktypes: ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
		shortWait(2000);
		childTest.pass("Order upload finished for batch Picktype");
		assignpod(PodUser);
		podGeneration();
		binnerAssignment(CustReference,Binner);
		// binningBACK("normal");
		binPickPart=part;
		binPickPartquantity=new int[]{v,nv,nvnu};
		if(type.contains("normal"))
		{
		binning("",CustReference,Arrays.asList(new String[]{partv,partnv,partnvnu}), binPickPartquantity);
		}
		else if(type.contains("boxbin"))
		{
			clickIDLinkText(INBOUND_LABEL);
			Label_Reprint("3", "boxlabel");
			nvnupartBox=VLPN.get(2);
				nvpartBox=VLPN.get(1);
				vpartBox=VLPN.get(0);

			binning("box",CustReference,Arrays.asList(new String[]{partv,partnv,partnvnu}), binPickPartquantity);
			binning("binbox",CustReference,Arrays.asList(new String[]{partv,partnv,partnvnu}), binPickPartquantity);
			
		}	

		


		childTest.info("Vehicle number is :  " + VEHICLENUMBER);
		childTest.pass("Verification completed for INBOUND BINNING");


		HashMap<String, String> partOrder= new HashMap<String, String>();


		verify(OUTBOUND);
		clickXpath(OUTBOUND,"OUTBOUND");
		verify(OUTBOUND);
		clickIDLinkText("Pick Generation");

		enterXpath(MASTERSEARCH, SOCustReference,"MASTERSEARCH");


		clickXpath("//*[@id='PickGen_searchButton']", "search");
		Thread.sleep(1000);
		clickXpath(SELECTPART,"Checkbox");

		childTest.info("The order available in the pick generation screen");

		childTest = parantTest.createNode("picker Assignment");


		clickXpath(PICKERASSIGN,"PICKERASSIGN");

		verify(PICKERSUBMIT);

		enterXpath(PICKERINPUT, prop.getProperty("PICKER"),"PICKERINPUT");
		nameClick(prop.getProperty("PICKER"));


		clickXpath(PICKERSUBMIT,"PICKERSUBMIT");

		Thread.sleep(2000);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		clickXpath(PICKINGGRID,"PICKINGGRID");
		VLPN=new ArrayList<String>();
		NVLPNpicking=new ArrayList<String>();
		binPickPartquantity=new int[]{v,nv,nvnu};
		int index=0;
		int a=0;
		for(int qty: binPickPartquantity)
		{
			 if(qty<1)
			 {
				 List<String> te=Arrays.asList(binPickPart);
					te.remove(index);
			binPickPart=(String[]) te.toArray();	
						 
			 }
			index++;
		}
		
		ArrayList<String>picked = null;
		if(backAvailable)
		{
			picked=pickingScreenFS("",VONfirst,Arrays.asList(binPickPart),binPickPartquantity);
			clickXpath(CLEAR, "clear");
			/*
			for(int i=0;i<availablesize;i++)		
			{
				enterXpath(PICKINGSEARCH, VONfirst,"PICKINGSEARCH");
				Thread.sleep(2000);
				clickXpath(PICKSEARCH, "search");
				PartType = xpathGetText(PARTTYPE);
				partName=xpathGetText(PARTNAME);
				Thread.sleep(1000);

				clickXpath(PARTTYPE,"Select Part    :"+PartType);
				//Thread.sleep(4000);			    
				childTest.info("The order part  :"+partName +" :" +"  Part type  "+ PartType +" : part available in picking screen");
				if (PartType.equals("NVNU") ){
					
					childTest.info("NVNU part picking started");


					clickXpath("//img[@alt='o']");
					CUSLPN = xpathGetText(PICKINGLPN);
					clickXpath(LPNCLOSE,"Infoclose");
					enterXpath(SERIALNOFIELD, CUSLPN,"SERIALNOFIELD");

					//clickXpath(SUBMITBTN,"SUBMITBTN");

					enterXpath(VQUANTITY, availableNVNU+"","NVNUQtn");


					clickXpath(SUBMITBTN,"SUBMITBTN");


					clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
					//	partStockCheck(partnvnu,  "picking",Integer.parseInt(NVNUQtn));


					childTest.log(Status.PASS,
							MarkupHelper.createLabel(" NVNU Type part picked sucessfully  ", ExtentColor.GREEN));
				}else if ((PartType.equals("NV"))&&(!partName.equals("PARTNVCUSLPN"))){
					childTest.info("NV part picking started");


					clickXpath(LPNINFO1,"INFO");


					for(int i1=2;i1<=Integer.parseInt(availableNV+"")+1;i1++)
					{
						//clickXpath(LPNINFO1,"INFO");
						String NVLPN1picking = xpathGetText("/html/body/div[2]/div[2]/p/div[2]/table/tbody/tr["+i1+"]/td[4]");
						;
						NVLPNpicking.add(NVLPN1picking);
						disLpns2.add(NVLPN1picking);

					}
					clickXpath(LPNCLOSE,"Infoclose");

					for(String apicking:NVLPNpicking)
					{
						enterXpath(SERIALNOFIELD, apicking,"LPNFIELD");

						//verify("(//*[contains(.,'Successfully')])[16]");					

						clickXpath(SUBMITBTN,"SUBMITBTN");
					}
					childTest.log(Status.PASS,
							MarkupHelper.createLabel("NV Type part picked sucessfully  :", ExtentColor.GREEN));

					Thread.sleep(2000);
					clickXpath(ALERTBOXCANCEL,"ALERTBOX");
					//	partStockCheck(partnv,  "picking",Integer.parseInt(NvQtn));




				}
				else if ((PartType.equals("V"))&&(!partName.equals("PARTVCUSLPN"))){
					deleteFile();
					//	clickXpath(PARTTYPE,"Select Part    :"+PartType);
					Thread.sleep(1000);

					clickXpath(LPNINFO1,"INFO");
					Thread.sleep(3000);
					WebElement mytable = driver.findElement(By.xpath("/html/body/div[2]/div[2]/p/div[2]/table"));
					List < WebElement > rows_table = mytable.findElements(By.tagName("tr"));
					int rows_count = rows_table.size();
					Lpn=new HashMap<String, String>();
					for (int row = 2; row <= rows_count; row++) {
						LPN=xpathGetText("//*[@id='simple-popper']/div[2]/p/div[2]/table/tbody/tr["+row+"]/td[4]");
						String QUANTITY=xpathGetText("//*[@id='simple-popper']/div[2]/p/div[2]/table/tbody/tr["+row+"]/td[5]");
						Lpn.put(LPN, QUANTITY);
						disLpns2.add(LPN);

					}

					clickXpath(LPNCLOSE,"Infoclose");
					for(Map.Entry<String, String> Pick_Lpn:Lpn.entrySet()){
						String  VLPN = Pick_Lpn.getKey();
						String  VQty11 = Pick_Lpn.getValue();
						System.out.println(VLPN+"  "+VQty11);
						enterXpath("//*[@id='Picking_serialLPN']", VLPN,"LPN");
						clickXpath("//*[@id='Picking_SubmitButton']","submit");

						enterXpath("//*[@id='Picking_enterQty']",VQty11,"Quantity");
						clickXpath("//*[@id='Picking_SubmitButton']","submit");
						Thread.sleep(2000);
						String nu=xpathGetText("//*[@id='root']/div/div/main/div/div/div/div[3]/div/div/p/form/div[2]/div[2]/span[2]");
						num= nu.split("/");
						num_0 = num[0];
						num1 = num[1].split("\\(");
						num2 = num1[1].split("\\)");
					}


					Partial_LPNQTY = num2[0];

					if(!Partial_LPNQTY.trim().equals("0") ){

						enterXpath("//*[@id='nooflabels']","1","Label");
						clickXpath("(.//*[@type='button'])[4]","submit");

						Thread.sleep(10000);

						File[] listOfFiles = folder.listFiles();
						for (int L = 0; L < listOfFiles.length; L++) 
						{
							System.out.println("Loop Checking");
							if (listOfFiles[L].isFile()) 
							{
								pdf = listOfFiles[L].getName(); 
							} 
						}
						String filepath = prop.getProperty("FOLDER") + pdffilename + "//" + pdf;
						File file2 = new File(filepath);
						FileInputStream fis = new FileInputStream(file2); 
						PDDocument pdfDocument = PDDocument.load(fis);
						PDFTextStripper pdfTextStripper = new PDFTextStripper();
						String docText = pdfTextStripper.getText(pdfDocument);

						getStockNumber1(docText,"lpn");
						pdfDocument.close();
						fis.close();

						disLpns2.add(VLPN.get(0));
						enterXpath("//*[@id='Picking_serialLPN']", VLPN.get(0), "partial label LPN");
						enterXpath("//*[@id='Picking_enterQty']",Partial_LPNQTY , "Partial Quantity");
						clickXpath("//*[@id='Picking_SubmitButton']","submit");

					}

					clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");

					//partStockCheck(partv,  "picking",Integer.parseInt(NvQtn));
				}

			}*/}
		VLPN=new ArrayList<String>();
		NVLPNpicking=new ArrayList<String>();
		deleteFile();
		for(int i=0;i<3;i++)		
		{

			enterXpath(PICKINGSEARCH, SOCustReference,"PICKINGSEARCH");
			clickXpath(PICKSEARCH, "search");
			Thread.sleep(1000);
			PartType = xpathGetText(PARTTYPE);
			partName=xpathGetText(PARTNAME);



			//Thread.sleep(4000);			    
			childTest.info("The order part  :"+partName +" :" +"  Part type  "+ PartType +" : part available in picking screen");
			if (PartType.equals("NVNU") ){
				partOrder.put(PartType,xpathGetText("(//*[@id='Picking_Action'])[1]"));
				clickXpath(PARTTYPE,"Select Part    :"+PartType);

				childTest.info("NVNU part picking started");
				if(type.contains("normal"))
				{

				clickXpath("//img[@alt='o']");
				CUSLPN = xpathGetText(PICKINGLPN);
				clickXpath(LPNCLOSE,"Infoclose");
				
				enterXpath(SERIALNOFIELD, CUSLPN,"SERIALNOFIELD");

				//clickXpath(SUBMITBTN,"SUBMITBTN");

				enterXpath(VQUANTITY, "1","NVNUQtn");


				clickXpath(SUBMITBTN,"SUBMITBTN");
				}
				else if(type.contains("boxbin"))
				{ Thread.sleep(500);
					enterXpath(SERIALNOFIELD,Bin,"SERIALNOFIELD");
					clickXpath(SUBMITBTN,"SUBMITBTN");
					Thread.sleep(500);
					enterXpath(SERIALNOFIELD, nvnupartBox,"SERIALNOFIELD");
					Thread.sleep(500);
						clickXpath("//input[@id='pickAsBox']", "pickAsBox");
						Thread.sleep(500);
						clickXpath(SUBMITBTN,"SUBMITBTN");
				}

				clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
				partStockCheck(partnvnu,  "picking",1);


				childTest.log(Status.PASS,
						MarkupHelper.createLabel(" NVNU Type part picked sucessfully  ", ExtentColor.GREEN));
			}else if ((PartType.equals("NV"))&&(!partName.equals("PARTNVCUSLPN"))){
				partOrder.put(PartType,xpathGetText("(//*[@id='Picking_Action'])[1]"));
				clickXpath(PARTTYPE,"Select Part    :"+PartType);
				childTest.info("NV part picking started");

if(type.contains("normal"))
{
				clickXpath(LPNINFO1,"INFO");



				String NVLPN1picking = xpathGetText("/html/body/div[2]/div[2]/p/div[2]/table/tbody/tr[2]/td[4]");

				NVLPNpicking.add(NVLPN1picking);
				disLpns1.put(PartType,NVLPN1picking);
				clickXpath(LPNCLOSE,"Infoclose");

				for(String apicking:NVLPNpicking)
				{
					enterXpath(SERIALNOFIELD, apicking,"LPNFIELD");

					//verify("(//*[contains(.,'Successfully')])[16]");					

					clickXpath(SUBMITBTN,"SUBMITBTN");
				}
				childTest.log(Status.PASS,
						MarkupHelper.createLabel("NV Type part picked sucessfully  :", ExtentColor.GREEN));

}
else if(type.contains("boxbin"))
{
	Thread.sleep(500);
	enterXpath(SERIALNOFIELD, nvpartBox,"LPNFIELD");
	clickXpath(SUBMITBTN,"SUBMITBTN");
	
}
				Thread.sleep(2000);
				clickXpath(ALERTBOXCANCEL,"ALERTBOX");
				partStockCheck(partnv,  "picking",1);




			}
			else if ((PartType.equals("V"))&&(!partName.equals("PARTVCUSLPN"))){

				partOrder.put(PartType,xpathGetText("(//*[@id='Picking_Action'])[1]"));
				clickXpath(PARTTYPE,"Select Part    :"+PartType);
				Thread.sleep(1000);
if(type.contains("normal"))
{
				clickXpath(LPNINFO1,"INFO");
				Thread.sleep(3000);
				WebElement mytable = driver.findElement(By.xpath("/html/body/div[2]/div[2]/p/div[2]/table"));
				List < WebElement > rows_table = mytable.findElements(By.tagName("tr"));
				int rows_count = rows_table.size();
				Lpn.clear();
				for (int row = 2; row <= rows_count; row++) {
					LPN=xpathGetText("//*[@id='simple-popper']/div[2]/p/div[2]/table/tbody/tr["+row+"]/td[4]");
					String QUANTITY=xpathGetText("//*[@id='simple-popper']/div[2]/p/div[2]/table/tbody/tr["+row+"]/td[5]");
					Lpn.put(LPN, QUANTITY);
					disLpns1.put(PartType,LPN);
				}

				clickXpath(LPNCLOSE,"Infoclose");
				for(Map.Entry<String, String> Pick_Lpn:Lpn.entrySet()){
					String  VLPN = Pick_Lpn.getKey();
					System.out.println(VLPN);
					Thread.sleep(500);
					String  VQty11 = Pick_Lpn.getValue();
					Thread.sleep(500);
					enterXpath("//*[@id='Picking_serialLPN']", VLPN,"LPN");
					Thread.sleep(500);
					clickXpath("//*[@id='Picking_SubmitButton']","submit");
					Thread.sleep(500);
					enterXpath("//*[@id='Picking_enterQty']",VQty11,"Quantity");
					Thread.sleep(500);
					clickXpath("//*[@id='Picking_SubmitButton']","submit");
					Thread.sleep(2000);
					String nu=xpathGetText("//*[@id='root']/div/div/main/div/div/div/div[3]/div/div/p/form/div[2]/div[2]/span[2]");
					num= nu.split("/");
					num_0 = num[0];
					num1 = num[1].split("\\(");
					num2 = num1[1].split("\\)");
				}


				Partial_LPNQTY = num2[0];

				if(!Partial_LPNQTY.trim().equals("0") ){

					enterXpath("//*[@id='nooflabels']","1","Label");
					clickXpath("(.//*[@type='button'])[4]","submit");

					Thread.sleep(10000);

					File[] listOfFiles = folder.listFiles();
					for (int L = 0; L < listOfFiles.length; L++) 
					{
						System.out.println("Loop Checking");
						if (listOfFiles[L].isFile()) 
						{
							pdf = listOfFiles[L].getName(); 
						} 
					}
					String filepath = prop.getProperty("FOLDER") + pdffilename + "//" + pdf;
					File file2 = new File(filepath);
					FileInputStream fis = new FileInputStream(file2); 
					PDDocument pdfDocument = PDDocument.load(fis);
					PDFTextStripper pdfTextStripper = new PDFTextStripper();
					String docText = pdfTextStripper.getText(pdfDocument);

					getStockNumber1(docText,"LPN");
					pdfDocument.close();
					fis.close();
					disLpns1.put(PartType,VLPN.get(0));

					enterXpath("//*[@id='Picking_serialLPN']", VLPN.get(0), "partial label LPN");
					enterXpath("//*[@id='Picking_enterQty']",Partial_LPNQTY , "Partial Quantity");
					clickXpath("//*[@id='Picking_SubmitButton']","submit");

				}
}
			else if(type.contains("boxbin"))
			{
				Thread.sleep(500);
				enterXpath(SERIALNOFIELD, vpartBox,"LPNFIELD");
				clickXpath(SUBMITBTN,"SUBMITBTN");
				clickXpath("(//button[@type='submit'])[2]", "YES");
			}
				clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");

				partStockCheck(partv,  "picking",1);
			}

			
		
		//clear1(PICKINGSEARCH);
		}

		skipConsolidation(SOCustReference);
		ArrayList<String> pickedlpn2=new ArrayList<String>();
if(type.contains("normal"))
{
		pickedlpn2.add(partnvnu);
		CUSLPN=partnvnu;
		binPickPart=new String[]{CUSLPN};
		quantity=new String[]{"1"};	
		disp(partOrder.get("NVNU"),"",pickedlpn2);
	pickedlpn2=new ArrayList<String>();
	driver.navigate().refresh();

		pickedlpn2.add(disLpns1.get("NV"));
		disp(partOrder.get("NV"),"",pickedlpn2);
		pickedlpn2=new ArrayList<String>();
		driver.navigate().refresh();

		pickedlpn2.add(disLpns1.get("V"));
		disp(partOrder.get("V"),"",pickedlpn2);
		driver.navigate().refresh();
}
else if(type.contains("boxbin"))
{
	
		childTest = parantTest.createNode(" Bulk Dispatch ");
		clickXpath(DISPATCHGRID,"DISPATCHGRID");
		verify("(//*[@id='Despatch_REQUESTNO'])[1]");
		for(int r=0;r<=2;r++)
		{
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
		
		}
		enterXpath(DISPATCHSEARCH, SOCustReference,"DISPATCHSEARCH");
		Thread.sleep(1000);
		
		
		
		verify("//*[contains(text(),'No Records Found')]");
		childTest.pass("The order is Dispatched By Bulk Dispatch");
	
}

/*		childTest = parantTest.createNode(" Order Dispatch ");
		clickXpath(DISPATCHGRID,"DISPATCHGRID");
		verify(Dispatch1);
		enterXpath(DISPATCHSEARCH, partOrder.get("NVNU"),"DISPATCHSEARCH");

		clickXpath(Dispatch1,"Dispatch1");
		//verify(DISPATCHBUTTON);

		Thread.sleep(1000);
		clickXpath(DISPATCHBUTTON,"Dispatch1");
		//clickXpath(DISPATCHBTN,"DISPATCHBTN");		
		//verify("(//*[contains(.,' / ')])[23]","3");
		childTest.info("The order available in Dispatch Screen");

		enterXpath(CARRIER, "Testing","CARRIER");
		childTest.info("The Mode of carrier is :" + "Testing");
		enterXpath(CARRIERREF, "SmokeTest","CARRIERREF");
		childTest.info("The carrier reference is :" + "SmokeTest");
		enterXpath(SCANLPN, partnvnu,"LPNFIELD");
		actionEnter();

		enterXpath("//*[@id=\"Despatch_enterQty1\"]","1","Quantity");
		actionEnter();*/
	/*	verify(Dispatch1);

		enterXpath(DISPATCHSEARCH, partOrder.get("NV"),"DISPATCHSEARCH");

		clickXpath(Dispatch1,"Dispatch1");
		//verify(DISPATCHBUTTON);

		Thread.sleep(1000);
		clickXpath(DISPATCHBUTTON,"Dispatch1");
		//clickXpath(DISPATCHBTN,"DISPATCHBTN");		
		//verify("(//*[contains(.,' / ')])[23]","3");
		childTest.info("The order available in Dispatch Screen");

		enterXpath(CARRIER, "Testing","CARRIER");
		childTest.info("The Mode of carrier is :" + "Testing");
		enterXpath(CARRIERREF, "SmokeTest","CARRIERREF");
		childTest.info("The carrier reference is :" + "SmokeTest");
		enterXpath(SCANLPN, disLpns1.get("NV"),"LPNFIELD");
		actionEnter();
		verify(Dispatch1);

		enterXpath(DISPATCHSEARCH, partOrder.get("V"),"DISPATCHSEARCH");

		clickXpath(Dispatch1,"Dispatch1");
		//verify(DISPATCHBUTTON);

		Thread.sleep(1000);
		clickXpath(DISPATCHBUTTON,"Dispatch1");
		//clickXpath(DISPATCHBTN,"DISPATCHBTN");		
		//verify("(//*[contains(.,' / ')])[23]","3");
		childTest.info("The order available in Dispatch Screen");

		enterXpath(CARRIER, "Testing","CARRIER");
		childTest.info("The Mode of carrier is :" + "Testing");
		enterXpath(CARRIERREF, "SmokeTest","CARRIERREF");
		childTest.info("The carrier reference is :" + "SmokeTest");
		enterXpath(SCANLPN, disLpns1.get("V"),"LPNFIELD");
		actionEnter();*/
		//verify(Dispatch1);

	//	enterXpath(DISPATCHSEARCH, SOCustReference,"DISPATCHSEARCH");
		if(backAvailable)
		{		CUSLPN=partnvnu;//nvnu-1
		quantity=new String[]{""+(nvnu-1)};	

			disp(SOCustReference,"",picked);
/*
			clickXpath(Dispatch1,"Dispatch1");
			//verify(DISPATCHBUTTON);

			Thread.sleep(1000);
			clickXpath(DISPATCHBUTTON,"Dispatch1");
			//clickXpath(DISPATCHBTN,"DISPATCHBTN");		
			//verify("(//*[contains(.,' / ')])[23]","3");
			childTest.info("The order available in Dispatch Screen");

			enterXpath(CARRIER, "Testing","CARRIER");
			childTest.info("The Mode of carrier is :" + "Testing");
			enterXpath(CARRIERREF, "SmokeTest","CARRIERREF");
			childTest.info("The carrier reference is :" + "SmokeTest");
			for(String lpn:disLpns2)
			{
				enterXpath(SCANLPN,lpn,"LPNFIELD");
				actionEnter();
				Thread.sleep(1000);
			}
			enterXpath(SCANLPN, partnvnu,"LPNFIELD");
			actionEnter();

			enterXpath("//*[@id=\"Despatch_enterQty1\"]",availableNVNU+"","Quantity");
			actionEnter();


		*/}
		
		System.out.println("back order");
		logOut();
	}


	public void check(String part) throws Exception
	{
		readPropertyFile();
		con=null;
		Class.forName("com.mysql.jdbc.Driver");
		//con=DriverManager.getConnection("jdbc:mysql://13.126.173.6:3306/12032020VSB?useSSL=false", "root", "sqladmin");
		//con=DriverManager.getConnection("jdbc:mysql://52.66.237.14:3306/sp11prod?useSSL=false", "root", "c7GdWQzjdeTEqvwf");
		con=DriverManager.getConnection(prop.getProperty("DBURL"), prop.getProperty("DBUSERNAME"), prop.getProperty("DBPASSWORD"));
		st=con.createStatement();
		String s="SELECT o.orderSerialNumber,o.customerReference,part.partNumber,p.backOrderDate,p.backOrderQty,p.quantity FROM"+
				" partsinorder p LEFT JOIN  ordersmaster o ON o.orderId = p.orderId left join tblpart_m part on part.partId=p.partId "+
				"Where o.siteId = '"+prop.getProperty("SITEID")+"' AND o.accountId = '"+prop.getProperty("ACCOUNTID1")+"' AND p.backOrderQty != 0 AND part.partNumber='"+part+"' ORDER BY p.backOrderDate desc  ;";
		rs=st.executeQuery(s);
		data=new HashMap<String,String>();

		int next=0;
		backOrderCount=0;
		availableQtyCount=0;
		while (rs.next()) {

			if(next==0)
			{	if((int)Float.parseFloat(rs.getString("quantity"))==0)
			{
				System.out.println("quantity  "+rs.getString("quantity"));
				backAvailable=false;
				System.out.println(backAvailable);
			}
			}
			System.out.println("order  "+rs.getString("orderSerialNumber"));
			System.out.println("order  "+rs.getString("quantity"));
			System.out.println("order  "+rs.getString("backOrderQty"));

			availableQtyCount+=(int)Float.parseFloat(rs.getString("quantity"));
			backOrderCount+=(int)Float.parseFloat(rs.getString("backOrderQty"));
			next++;

		}

		System.out.println("backOrderCount : "+backOrderCount);
		System.out.println("availableQtyCount : "+availableQtyCount);
		con.close();


	}
}
