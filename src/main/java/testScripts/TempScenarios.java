package testScripts;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class TempScenarios extends ProjectSpecificFunctions{


	@Test
	public void tempOrder() throws Exception{
		parantTest = extent.createTest("Temp sales order creation after cancellation");    
		partv="TEMPV03".toUpperCase();
		partnv="TEMPNV03".toUpperCase();
		partnvnu="TEMPNVNU03".toUpperCase();
		picktype="FIFOSUGGESTED";
		loginAsUser(Username);
		holdOrder_Scenario("Cancellation");
		parantTest = extent.createTest("Temp sales order creation after Short Pick");
		loginAsUser(Username);
		holdOrder_Scenario("Short");

	}
	
	public void holdOrder_Scenario(String type) throws Exception
	{
		NVLPNpicking=new  ArrayList<String>(); 
		Lpn = new HashMap<String ,String>();  
		vehicleCreation(site);
		part=new String[]{partv,partnv,partnvnu};
		quantity=new String[]{"2","2","2"};	
		binPickPartquantity=new int[]{2,2,2};
		binPickPart=new String[]{partv,partnv,partnvnu};
		writeDataForCSV(picktype,"endcustomer","po", part,quantity);
		childTest = parantTest.createNode("PO Order creation for Temp sales order");
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
		childTest.log(Status.PASS,"The uploaded PO parts  details : ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
		shortWait(2000);
		assignpod(PodUser);
		podGeneration();
		binnerAssignment(CustReference,Binner);
		binning("",CustReference,Arrays.asList(binPickPart),binPickPartquantity);
		//Normal SO
		part=new String[]{partv,partnv,partnvnu};
		quantity=new String[]{""+(Available.get(partv)-1),""+(Available.get(partnv)-1),""+(Available.get(partnvnu)-1)};	
		writeDataForCSV(picktype,"newdelpoint","so", part,quantity);
		String SOCustReference1= SOCustReference; 
		pickgenerationFIFO();
		//temp("SO");
		quantity= new String[]{""+(binPickPartquantity[0]),""+(binPickPartquantity[1]),""+(binPickPartquantity[2])};
		writeDataForCSV(picktype,"newdelpoint","so", part,quantity);
		clickXpath(CREATEREQUEST,"Create request");
		//clickXpath("//span[contains(.,'Create request')]","Create request");
		File file = new File(prop.getProperty("SOPATH"));
		clickXpath(ACCOUNTSELECT);		
		actionEnter();		
		enterXpath(CHOOSEFILE,file.getAbsolutePath());
		clickXpath(UPLOADSO,"UPLOADSO");
		Thread.sleep(2000);
		CommonFunctions.childTest.log(Status.PASS,"File upload status",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build());
		verify(ALERTBOXCANCEL);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		//
		clickIDLinkText("Misc");
		clickIDLinkText("Approve Request");
		
			
		ApproveRequest("Hold Order",SOCustReference);
		if(type.equalsIgnoreCase("Cancellation")){
			clickIDLinkText("Cancellation");
			childTest = parantTest.createNode("Order Level cancellation- At pick generation screen");
			childTest.info("Order level cancellation initiated Cust Ref is  : " + SOCustReference1);
			verify(CANCEL_LEVEL);
			selectByvalue(CANCEL_LEVEL, 1);
			enterXpath( CANCEL_SOSEARCH, SOCustReference1,"CANCEL_SOSEARCH");
			actionEnter();
			childTest.info("The Order is available in order level cancellation screen. Cust Ref is  : " + SOCustReference1  +  MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());
			clickXpath( CANCEL_PART_CHECKBOX);
			selectByVisibleText(CANCEL_REASON, "Customer Cancellation");
			clickXpath( CANCEL_BTN,"Cancel Btn");
			verify("(//*[contains(.,'Successfully')])[20]","Successfully");
			clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
			//partStockCheck(partnvnu,  "precancel",2);
			//partStockCheck(partnv,  "precancel",2);
			childTest.pass("Order Level Cancellation has been completed for Cust Ref : " + SOCustReference1);
		}
		else{
			fluentWaitxpath(30,2,OUTBOUND);
			clickXpath(OUTBOUND,"OUTBOUND");
			verify(OUTBOUND);
			clickIDLinkText("Pick Generation");
			pickerassign1(SOCustReference1);
			pickingScreenFS("Short",SOCustReference1,Arrays.asList(binPickPart), binPickPartquantity);	
			clickIDLinkText("Misc");
			clickIDLinkText("Approve Request");
			
				
			ApproveRequest("shortpickapproveall",SOCustReference1);
		}
		fluentWaitxpath(30,2,OUTBOUND);
		clickXpath(OUTBOUND,"OUTBOUND");
		verify(OUTBOUND);
		clickIDLinkText("Pick Generation");
		pickerassign1(SOCustReference);
		pickingScreenFS("",SOCustReference,Arrays.asList(binPickPart), binPickPartquantity);
		skipConsolidation(SOCustReference);
		dispatch2(SOCustReference);
		System.out.println("temp order completed");
		logOut();
	}
	public ArrayList<String> Picking_Shortet(String type,String SOCustReference,List allpart,int []quantity) throws Exception{
		ArrayList<String> pickedLPNS=new ArrayList<String>();
		int count=0;
		deleteFile();
		clickXpath(PICKINGGRID,"PICKINGGRID");
		childTest = parantTest.createNode("Picking");
		ArrayList<String> failedList=new ArrayList<String>();
		ArrayList<String> passedList=new ArrayList<String>();
		count=allpart.size()+1;
		for(int outer=0;outer<allpart.size();outer++)
		{ driver.navigate().refresh();
		Thread.sleep(2000);
		enterXpath(PICKINGSEARCH, SOCustReference,"PICKINGSEARCH");
		clickXpath(PICKSEARCH, "search");
		try{
			int i;
			check:for ( i = 1; i <=count; i++) {
				partName = xpathGetText("(//td[contains(@id,'Picking_PartNumber')])["+i+"]");
				System.out.println("checking  ; "+partName+count);
				if ((!(failedList.contains(partName))))
				{
					break check;
				}
			}
			PartType=xpathGetText("(//td[@data-label='PART TYPE'])["+i+"]");
			int partquantity=quantity[allpart.indexOf(partName)];
			childTest.log(Status.INFO, "The order "+SOCustReference+" for the part "+partName+" available in picking screen",MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture1()).build());
			//NVNU
			if ((!failedList.contains(partName))&&(PartType.equals("NVNU")) ){
				HashMap<String,String>	Lpnn=new HashMap<String,String>();
				clickXpath("(//td[@data-label='PART TYPE'])["+i+"]");

				if(type.contains("Short")){
					clickXpath("//*[@id='root']/div/div/main/div/div/div/div[3]/div/div/p/form/div[4]/div/b","Option");
					clickXpath("//*[@id='simple-menu']/div[2]/ul/li","Short Pick");
					clickXpath("//*[@id='root']/div/div/main/div/div/div/div[5]/div[2]/div/div/div/div/button[1]","YES button");
					failedList.add(partName);
					childTest.pass("Short pick approval sent to the manager for part : " + partName );
					driver.navigate().refresh();
					clickXpath(PICKINGGRID,"PICKINGGRID");
				}
				else{
					if(type.contains("cart"))
					{
						clickXpath(SCAN_CART_LABEL_BTN);
						enterXpath(CART_LABEL_XPATH, cart.get(outer),"GENERATED_CART_LABEL");
						actionEnter();
						Thread.sleep(1000);
					}
					if(picktype.contains("BATCH")||picktype.contains("FMFO")||picktype.contains("FIFOSUGGESTED")||picktype.contains("FEFO")||picktype.contains("SERIAL")||picktype.contains("CUSTLPN"))
					{
						enterXpath(SERIALNOFIELD,Bin,"SERIALNOFIELD");
						clickXpath(SUBMITBTN,"SUBMITBTN");
					}
					childTest.info("BIN is scanned");
					childTest.info("NVNU part picking started");
					String temp="";
					clickXpath("//img[@alt='o']");
					Thread.sleep(2000);
					WebElement mytable = driver.findElement(By.xpath("/html/body/div[2]/div[2]/p/div[2]/table"));
					List < WebElement > rows_table = mytable.findElements(By.tagName("tr"));
					int rows_count = rows_table.size();
					System.out.println("rows_count : "+rows_count);
					CUSLPN=picktype.contains("BOX")?nvnupartBox:xpathGetText(PICKINGLPN);
					for (int row = 2; row <= rows_count; row++) {
						CUSLPN=picktype.startsWith("CUSTLPNBOX")? xpathGetText(PICKINGBOXLPN):xpathGetText(PICKINGLPN);
						LPN=xpathGetText("/html/body/div[2]/div[2]/p/div[2]/table/tbody/tr["+row+"]/td[4]");
						String QUANTITY=xpathGetText("/html/body/div[2]/div[2]/p/div[2]/table/tbody/tr["+row+"]/td[5]");
						Lpnn.put(LPN, QUANTITY);
						System.out.println(Lpnn);
					}
					//CUSLPN=picktype.startsWith("CUSTLPNBOX")? xpathGetText(PICKINGBOXLPN):xpathGetText(PICKINGLPN);
					temp=xpathGetText(PICKINGLPN);
					clickXpath(LPNCLOSE1,"Infoclose");
					if(picktype.startsWith("CUSTLPN"))
					{for(Map.Entry<String, String> NLPN:Lpnn.entrySet()){
						String  NVNULPN = NLPN.getKey();
						String  NVNUQTY = type.contains("partial")?""+partquantity:NLPN.getValue();
						NVNULPN=picktype.startsWith("CUSTLPNBOX")? CUSLPN:NVNULPN;
						enterXpath(SERIALNOFIELD, NVNULPN,"SERIALNOFIELD");
						if(!picktype.contains("BOX"))
						{
							enterXpath(VQUANTITY,""+NVNUQTY,"NVNUQtn");
							clickXpath(SUBMITBTN,"SUBMITBTN");
							Thread.sleep(1000);
						}
						pickedLPNS.add(NVNULPN);
					}
					//	CUSLPN=picktype.startsWith("CUSTLPNBOX")?temp:CUSLPN;
					}
					//clickXpath(SUBMITBTN,"SUBMITBTN");

					//clickXpath(SUBMITBTN,"SUBMITBTN");
					if(picktype.contains("BOX"))
					{enterXpath(SERIALNOFIELD, nvnupartBox,"SERIALNOFIELD");
					CUSLPN=nvnupartBox;
					clickXpath("//input[@id='pickAsBox']", "pickAsBox");
					}
					else
					{ if(type.equalsIgnoreCase("pallet"))

					{
						enterXpath(SERIALNOFIELD, nvnupartBox,"SERIALNOFIELD");
					}
					if(!(picktype.contains("CUSTLPN")))
					{
						enterXpath(VQUANTITY,""+partquantity,"NVNUQtn");
					}
					}
					//enterXpath(VQUANTITY, csv.readXL("SO",1,"QUANTITY"),"NVNUQtn");

					if((!(picktype.contains("CUSTLPN")))||(picktype.contains("BOX")))
					{
						clickXpath(SUBMITBTN,"SUBMITBTN");
						pickedLPNS.add(CUSLPN);
					}
					if(picktype.contains("SERIAL"))
					{for(int s1=0;s1<partquantity;s1++)
					{
						Thread.sleep(1000);
						enterXpath(SERIAL_ENTER,"1","SERIAL NUMBER");
						actionEnter();
						Thread.sleep(2000);
					}
					}
					//LPNStatus(CUSLPN,Module.PICKING);
					if(!(type.contains("partial"))){
						childTest.log(Status.PASS,"Picking Completed for NVNU part,Alert Box : ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
						clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
						childTest.log(Status.PASS,
								MarkupHelper.createLabel(" NVNU Type part picked sucessfully  ", ExtentColor.GREEN));
					}
					else
					{
						clickXpath("//*[@id='Picking_BackButton2']");
						childTest.log(Status.PASS,"partial Picking for NVNU part");
					}
					//partstockCheck(partnvnu,  "picking",Integer.parseInt(NVNUQtn));
					passedList.add(partName);
					count-=passedList.size();
				}}
			//NV				
			else if ((!failedList.contains(partName))&&(PartType.equals("NV"))){
				NVLPNpicking=new  ArrayList<String>();
				clickXpath("(//td[@data-label='PART TYPE'])["+i+"]");
				childTest.info("NV part picking started");
				if(type.contains("Short")){
					clickXpath("//*[@id='root']/div/div/main/div/div/div/div[3]/div/div/p/form/div[4]/div/b","Option");
					clickXpath("//*[@id='simple-menu']/div[2]/ul/li","Short Pick");
					clickXpath("//*[@id='root']/div/div/main/div/div/div/div[5]/div[2]/div/div/div/div/button[1]","YES button");
					failedList.add(partName);
					childTest.pass("Short pick approval sent to the manager for part : " + partName );
					driver.navigate().refresh();
					clickXpath(PICKINGGRID,"PICKINGGRID");
				}
				else{
					if(type.contains("cart"))
					{
						clickXpath(SCAN_CART_LABEL_BTN);
						enterXpath(CART_LABEL_XPATH, cart.get(outer),"GENERATED_CART_LABEL");
						actionEnter();
					}
					String PICKINGQTY = xpathGetText(PICKINGVQTN);
					String []picknvqtn1=PICKINGQTY.split("/");
					String []picknvqtn2=picknvqtn1[1].split("\\(");
					String []picknvqtn3=picknvqtn2[1].split("\\(");
					clickXpath(Picking_Info,"INFO");
					//	for(int i1=2;i1<=Integer.parseInt(picknvqtn2[0].trim())+1;i1++)
					for(int i1=2;i1<=partquantity+1;i1++)
					{
						String NVLPN1picking = xpathGetText("/html/body/div[2]/div[2]/p/div[2]/table/tbody/tr["+i1+"]/td[4]");
						NVLPNpicking.add(NVLPN1picking);
					}
					clickXpath(LPNCLOSE1,"Infoclose");
					//if(serialnumber1==PICKINGLPN)
					//{
					if(!picktype.contains("BOX")||(picktype.contains("BOX")&&picktype.contains("CUSTLPN")))
					{
						for(String apicking:NVLPNpicking)
						{Thread.sleep(500);
						enterXpath(SERIALNOFIELD, apicking,"LPNFIELD");
						pickedLPNS.add(apicking);
						clickXpath(SUBMITBTN,"SUBMITBTN");
						if(picktype.equalsIgnoreCase("serialnumber"))
						{WebElement m= driver.findElement(By.xpath ("(//*[local-name()='svg']/*[local-name()='path'])[2]"));
						// Action class to move and click element
						Actions a1 = new Actions(driver);
						Thread.sleep(2000);
						a1.moveToElement(m).click();
						a1.build().perform();
						}
						//verify("(//*[contains(.,'Successfully')])[16]");
						LPNStatus(apicking,Module.PICKING);
						}
					}
					if(picktype.contains("BOX")&&(!picktype.contains("CUSTLPN")))
					{
						Thread.sleep(500);
						enterXpath(SERIALNOFIELD, nvpartBox,"LPNFIELD");
						clickXpath(SUBMITBTN,"SUBMITBTN");
						pickedLPNS.add(nvpartBox);
					}
					childTest.log(Status.PASS,
							MarkupHelper.createLabel("NV Type part picked sucessfully  :", ExtentColor.GREEN));
					//	Thread.sleep(2000);
					childTest.log(Status.PASS,"Picking Completed for NV part,Alert Box : ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
					clickXpath(ALERTBOXCANCEL,"ALERTBOX");
					passedList.add(partName);
					count-=passedList.size();
				}}
			//V				
			else if ((!failedList.contains(partName))&&(PartType.equals("V"))){
				Lpn = new HashMap<String ,String>();
				String VLPNL="";
				//else if ((!failedList.contains(PartType))&&(PartType.equals("V"))&&(!partName.equals("PARTVCUSLPN"))){
				clickXpath("(//td[@data-label='PART TYPE'])["+i+"]");
				if(type.contains("Short")){
					clickXpath("//*[@id='root']/div/div/main/div/div/div/div[3]/div/div/p/form/div[4]/div/b","Option");
					clickXpath("//*[@id='simple-menu']/div[2]/ul/li","Short Pick");
					clickXpath("//*[@id='root']/div/div/main/div/div/div/div[5]/div[2]/div/div/div/div/button[1]","YES button");
					failedList.add(partName);
					childTest.pass("Short pick approval sent to the manager for part : " + partName );
					driver.navigate().refresh();
					clickXpath(PICKINGGRID,"PICKINGGRID");
				}
				else{

					if(type.contains("cart"))
					{
						clickXpath(SCAN_CART_LABEL_BTN);
						enterXpath(CART_LABEL_XPATH, cart.get(outer),"GENERATED_CART_LABEL");
						actionEnter();
					}
					clickXpath(Picking_Info,"INFO");
					Thread.sleep(2000);
					WebElement mytable = driver.findElement(By.xpath("/html/body/div[2]/div[2]/p/div[2]/table"));
					List < WebElement > rows_table = mytable.findElements(By.tagName("tr"));
					int rows_count = rows_table.size();
					for (int row = 2; row <= rows_count; row++) {
						LPN=xpathGetText("//*[@id='simple-popper']/div[2]/p/div[2]/table/tbody/tr["+row+"]/td[4]");
						String QUANTITY=xpathGetText("//*[@id='simple-popper']/div[2]/p/div[2]/table/tbody/tr["+row+"]/td[5]");
						Lpn.put(LPN, QUANTITY);
					}
					clickXpath(LPNCLOSE1,"Infoclose");
					if(!picktype.contains("BOX")||(picktype.contains("BOX")&&picktype.contains("CUSTLPN")))
					{						String temp=null;
					int  pickedQuantity=0;
					int  pickedQuantitytemp=0;
					for(Map.Entry<String, String> Pick_Lpn:Lpn.entrySet()){
						String  VLPN = Pick_Lpn.getKey();
						String  VQty11 = Pick_Lpn.getValue();
						enterXpath(VLPN_ENTER, VLPN,"LPN");
						clickXpath(VLPN_SUBMIT,"submit");
						enterXpath(VLPN_QNTY,VQty11,"Quantity");
						clickXpath(VLPN_SUBMIT,"submit");
						Thread.sleep(2000);
						VLPNL=VLPN;
						String nu=xpathGetText(VPICKED_QNTY);
						num= nu.split("/");
						num_0 = num[0];
						num1 = num[1].split("\\(");
						num2 = num1[1].split("\\)");
						childTest.info(""+num1[1]);
						pickedQuantity+=Integer.parseInt(num[0].trim());
						pickedQuantitytemp+=Integer.parseInt(VQty11.trim());
						if(pickedQuantity==pickedQuantitytemp)
						{
							pickedLPNS.add(VLPN);

						}else
						{
							pickedQuantity=0;
							pickedQuantitytemp=0;
						}
					}}
					if(picktype.equalsIgnoreCase("serialnumber"))
					{for(int s1=0;s1<partquantity;s1++)
					{
						Thread.sleep(1000);
						enterXpath(SERIAL_ENTER,"1","SERIAL NUMBER");
						actionEnter();
						Thread.sleep(2000);
					}
					}
					if(picktype.contains("BOX")&&(!picktype.contains("CUSTLPN")))
					{
						Thread.sleep(500);
						enterXpath(SERIALNOFIELD, vpartBox,"LPNFIELD");
						clickXpath(SUBMITBTN,"SUBMITBTN");
						clickXpath("(//button[@type='submit'])[2]", "YES");
						pickedLPNS.add(vpartBox);
					}
					if(!picktype.contains("BOX")||(picktype.contains("BOX")&&picktype.contains("CUSTLPN")))
					{
						Partial_LPNQTY = num2[0];
						if(!Partial_LPNQTY.trim().equals("0") ){
							enterXpath(NOOFLABELS,"1","Label");
							clickXpath("(.//*[@type='button'])[4]","submit");
							getStockNumber1(readPDF(),"LPN");
							//	pdfDocument.close();
							//	fis.close();
							pickedLPNS.add(VLPN.get(0));
							enterXpath(VLPN_ENTER, VLPN.get(0), "partial label LPN");
							enterXpath(VLPN_QNTY,Partial_LPNQTY , "Partial Quantity");
							clickXpath(VLPN_SUBMIT,"submit");
							LPNStatus(VLPN.get(0),Module.PICKING);

						}else{	                        pickedLPNS.add(VLPNL);

						}
					}
					childTest.log(Status.PASS,"Picking Completed for V part,Alert Box : ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
					clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
					////partstockCheck(partv,  "picking",Integer.parseInt(NvQtn));
					passedList.add(partName);
					count-=passedList.size();	}
				deleteFile();
			}}	catch (Exception e)
		{
				failedList.add(partName);
				System.out.println(partName);
				e.printStackTrace();
				System.out.println(e.fillInStackTrace());
				childTest.log(Status.FAIL,"picking failed for part type : "+partName,MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build());
				childTest.info(e.fillInStackTrace());
				driver.navigate().refresh();
				clickXpath(PICKINGGRID,"PICKINGGRID");
				continue;
		}}
		if(!(type.contains("partial")||(type.contains("Short"))))
		{
			enterXpath(PICKINGSEARCH, SOCustReference,"MASTERSEARCH");
			clickXpath(PICKSEARCH, "search");
			childTest.log(Status.PASS,"Validation in Picking Screen: ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
			verify("//*[contains(text(),'No Records Found')]");
			childTest.pass("validation completed");
			DB(allpart, quantity, "picking");
			orderStatus(SOCustReference, Module.PICKING);
		}
		else{	
			enterXpath(PICKINGSEARCH, SOCustReference,"MASTERSEARCH");
			clickXpath(PICKSEARCH, "search");
			childTest.log(Status.PASS,"Validation in Picking Screen: ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
			if(!(type.contains("Short"))){
				DB(allpart, new int[]{0,0,0}, "picking");
				orderStatus(SOCustReference, Module.PARTIALPICKING);
			}
		}
		return pickedLPNS;


	}

}
