package testScripts;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class Outbound_OrderForm extends ProjectSpecificFunctions{


	String[] outPicktype;
	String[] outNVpart;
	String[] outNVNUpart;
	@Test(priority=0)
	
	public void outboundCREATEREQUEST1() throws AWTException, IOException, Exception{
		parantTest = extent.createTest("Outbound_OrderCreation through UI");

		loginAsUser(Username);

		childTest = parantTest.createNode("Outbound_OrderForm_OrderCreation");
		System.out.println("outbound orderform started");
		clickXpath(OUTBOUND);
		clickIDLinkText("Pick Generation");			

		outPicktype = prop.getProperty("OutboundPicktype").split(",");
		//String[] outVpart = prop.getProperty("outVpart").split(",");
		outNVpart = prop.getProperty("outNVpart").split(",");
		outNVNUpart = prop.getProperty("outNVNUpart").split(",");

		CustRefList = new ArrayList<String>();
		for(int i2=0;i2<=2;i2++) {
			int n=15;
			CustRefList.add(CustRefNo= getAlphaNumericString(n));
		}

		String[] cusref = new String[CustRefList.size()];

		CustRefList.toArray(cusref);
		System.out.println(cusref);

		for(int i=0;i<=2;i++){


			partStockCheck(outNVpart[i],  "dd",1);
			partStockCheck(outNVNUpart[i],  "dw",2);


			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
			clickXpath("//span[contains(.,'Create request')]","Create request");
			fluentWaitxpath(3,1,ORDERFORM);
			clickXpath(ORDERFORM,"ORDERFORM");
			//verify("(//*[contains(.,'OTIS INDIA')])[16]");
			shortWait(1000);

			clickXpath("(//*[@id='PickGen_WebAcc'])", "Accselect");
			actionEnter();
			clickXpath(SERVICETYPE,"SERVICETYPE");
			shortWait(1000);
			actionEnter();
			clickXpath(DELIVERYTYPE,"DELIVERYTYPE");
			shortWait(1000);
			clickDownArrow();
			clickDownArrow();
			shortWait(1000);
			actionEnter();
			actionSendKeys(DESTINATIONCODE,prop.getProperty("OutboundDesCode"));
			shortWait(2000);
			clickDownArrow();
			//	shortWait(1000);
			actionEnter();
			enterXpath(CUSTOMERREFERENCE,cusref[i],"CUSTOMERREFERENCE");
			enterXpath(REFERENCE1,prop.getProperty("Outboundref1"),"REFERENCE1");
			enterXpath(REFERENCE2,prop.getProperty("Outboundref2"),"REFERENCE2");



			actionSendKeys(PARTNO,outNVpart[i]);
			shortWait(3000);
			actionEnter();



			fluentWaitxpath(3,3,QUANTITY);
			enterXpath(QUANTITY,"2","QUANTITY");
			//JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,1000)");  
			if(outPicktype[i].equals("Custlpnpart")){

				enterXpath(CUSTOMERLPN,"Nvpart01","CUSTOMERLPN");

			}

			enterXpath(PARTREFERENCE,prop.getProperty("Outboundpartref"),"PARTREFERENCE");
			fluentWaitxpath(3,3,PARTREFERENCE);
			//verify("(//*[contains(.,'OTIS INDIA')])[16]");
			clickXpath(ADD,"ADD");


			childTest.pass(outNVpart[i] + "NVpart Binning Completed Successfully");


			actionSendKeys(PARTNO,outNVNUpart[i]);
			shortWait(3000);
			actionEnter();



			fluentWaitxpath(3,3,QUANTITY);
			enterXpath(QUANTITY,"2","QUANTITY");


			if(outPicktype[i].equals("Custlpnpart")){

				enterXpath(CUSTOMERLPN,"NVNUpart01","CUSTOMERLPN");

			}

			fluentWaitxpath(3,3,PARTREFERENCE);

			enterXpath(PARTREFERENCE,prop.getProperty("Outboundpartref"),"PARTREFERENCE");
			Thread.sleep(2000);

			clickXpath(ADD,"ADD");



			childTest.pass(outNVNUpart[i] + "NVNUpart Binning Completed Successfully");
			fluentWaitxpath(3,3,PROCEED);
			clickXpath(PROCEED,"PROCEED");
			fluentWaitxpath(30,1,CHOOSESITE);
			clickXpath(CHOOSESITE,"CHOOSESITE");
			fluentWaitxpath(30,1,SAVE);
			clickXpath(SAVE,"SAVE");
			verify("(//*[contains(.,'Document Uploaded Successfully')])[17]");
			clickXpath(CLOSE,"CLOSE");
			//verify(CLICKRECORD);
			childTest.pass("Outbound_OrderForm Order Created Successfully." + "Order Customer reference is" +cusref[i] );
			partStockCheck(outNVNUpart[i],  "pickgen",2);

			partStockCheck(outNVpart[i],  "pickgen",2);
			orderStatus(cusref[i], Module.SOCREATION);
		}


	}




	@Test(dependsOnMethods={"outboundCREATEREQUEST1"})
	public void pickerassignment() throws Exception{



		childTest = parantTest.createNode("Picker Assignment");
		childTest.info("The order available in picker assignment screen");
		String[] cusref = new String[CustRefList.size()];
		CustRefList.toArray(cusref);

		for(String customerreference:cusref)
		{
			pickerassign1(customerreference);
		}


	}

	@Test(dependsOnMethods={"pickerassignment"})
	public void AlternativeBin_Picking() throws Exception {


		childTest = parantTest.createNode("Alternative_picking");
		childTest.info("The order available in picking screen");


		String[] cusref = new String[CustRefList.size()];
		CustRefList.toArray(cusref);

		clickIDLinkText("Picking");


		for (int i = 0; i <= 1; i++) {



			enterXpath(PICKINGSEARCH, cusref[0],"PICKINGSEARCH");
			clickXpath(PICKSEARCH, "search");
			String PartType = xpathGetText(PARTTYPE);
			clickXpath(PARTTYPE,"PARTTYPE");
			if (PartType.equals("NVNU")) {


				//verify("(//*[contains(.,'Please')])[16]","Please");
				clickXpath(Navigation,"Info");

				alternativeLPNNVNU = xpathGetText(LPNINFO);
				Thread.sleep(1000);
				clickXpath("/html/body/div[2]/div[2]/p/div[1]/span","Infoclose");
				//System.out.println(alternativeLPNNVNU);
				clickXpath("//*[@id='root']/div/div/main/div/div/div/div[3]/div/div/p/form/div[4]/div/b");
				Thread.sleep(1000);
				clickXpath("//*[@id='simple-menu']/div[2]/ul/li[3]");

				Thread.sleep(2000);
				enterXpath(SERIALNOFIELD1, alternativeLPNNVNU,"SERIALNOFIELD1");

				Thread.sleep(500);
				enterXpath(VQUANTITY1, "2","VQUANTITY1");
				Thread.sleep(500);
				clickXpath(SUBMITBTN,"SUBMITBTN");

				clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");

				partStockCheck(outNVNUpart[0],  "picking",2);

			}
			else {


				//verify("(//*[contains(.,'scan')])[16]","scan");
				clickXpath(Navigation,"Info");

				alternativeNVLPN1 = xpathGetText(LPNINFO);
				//Thread.sleep(1000);
				clickXpath("/html/body/div[2]/div[2]/p/div[1]/span","Infoclose");
				//verify("(//*[contains(.,'scan')])[16]","scan");
				enterXpath(SERIALNOFIELD1, alternativeNVLPN1,"SERIALNOFIELD1");
				//System.out.println("THE 1st LPN" +alternativeNVLPN1);
				Thread.sleep(1000);
				actionClick(SUBMITBTN);
				Thread.sleep(1000);
				//verify("(//*[contains(.,'picked')])[16]","picked");

				clickXpath("//*[@id='root']/div/div/main/div/div/div/div[3]/div/div/p/form/div[4]/div/b");
				Thread.sleep(1000);
				clickXpath("//*[@id='simple-menu']/div[2]/ul/li[3]");
				Thread.sleep(1000);
				clickXpath(Navigation,"Info");


				String serialnumber3=xpathGetText(LPNINFO);

				clickXpath("/html/body/div[2]/div[2]/p/div[1]/span","Infoclose");
				//System.out.println("The LPN before Alternate BIN" +  serialnumber3);
				//Thread.sleep(1000);
				//verify("(//*[contains(.,'Alternate')])[16]","Alternate");
				//actionClick(SUBMITBTN);
				Thread.sleep(1000);

				//clickXpath(SUBMITBTN,"SUBMITBTN");
				//verify("(//*[contains(.,'scan')])[16]","scan");
				clickXpath(Navigation,"Info");

				alternativeNVLPN2 =xpathGetText(LPNINFO);
				//Thread.sleep(800);
				clickXpath("/html/body/div[2]/div[2]/p/div[1]/span","Infoclose");
				Thread.sleep(1000);
				//	verify("(//*[contains(.,'Alternate')])[16]");
				enterXpath(SERIALNOFIELD1, alternativeNVLPN2,"SERIALNOFIELD1");
				//System.out.println("The LPN after Alternate BIN" +  alternativeNVLPN2);
				Thread.sleep(1000);
				actionClick(SUBMITBTN);
				LPNStatus(alternativeNVLPN2,Module.PICKING);
				if(alternativeNVLPN2!=serialnumber3) {

					childTest.info("Reserved LPN's Before alternative Option  :   "+ alternativeNVLPN1+"  ||  "+serialnumber3);
					childTest.info("Picked LPN's Before alternative Option   :   "+ alternativeNVLPN1+"  ||  "+alternativeNVLPN2);
				}


				clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");


				partStockCheck(outNVpart[0],  "picking",1);


			}

		}

		Thread.sleep(3000);
		//verify(PARTTYPE);
		enterXpath(PICKINGSEARCH, cusref[0],"PICKINGSEARCH");
		clickXpath(PICKSEARCH, "search");
		verify(RECORD,"NO");

		childTest.log(Status.PASS, "Alternative pick completed");
		orderStatus(cusref[0], Module.PICKING);

	}

	@Test(dependsOnMethods={"AlternativeBin_Picking"})
	public void Random_pick() throws Exception {
		childTest = parantTest.createNode("Random Picking");
		childTest.info("The order available in picking screen");
		//ArrayList<String> Part=new ArrayList<String>();
		String[] cusref = new String[CustRefList.size()];
		CustRefList.toArray(cusref);

		ArrayList<String> approvalParts=new ArrayList<String>();
		ArrayList<String> custParts=new ArrayList<String>();
		List<WebElement> reqparts=new ArrayList<WebElement>();
		int no=1;
		while(no<=2)
		{


			//clickIDLinkText("Picking");

			clear1(PICKINGSEARCH);
			enterXpath(PICKINGSEARCH, cusref[1],"PICKINGSEARCH");
			clickXpath(PICKSEARCH, "search");

			String PartType = xpathGetText("(//td[@data-label='PART TYPE'])["+no+"]");
			if(no==1)
			{
				reqparts=driver.findElements(By.xpath(REQPARTS));
				for(WebElement a:reqparts)
				{
					custParts.add(a.getText());
				}

			}

			clickXpath("(//td[@data-label='PART TYPE'])["+no+"]","Part");
			no++;
			String ReqQuantity="2";
			if (PartType.equals("NVNU")) {


				//verify("(//*[contains(.,'scan')])[16]","scan");
				enterXpath(SERIALNOFIELD,Bin ,"SERIALNOFIELD");
				clickXpath(SUBMITBTN,"SUBMITBTN");
				clickXpath(INFO,"Info");
				randomNVNULPN=xpathGetText(LPN0);
				fluentWaitxpath(30,5,LPNCLOSE);
				clickXpath(LPNCLOSE,"LPNCLOSE");


				enterXpath(SERIALNOFIELD, randomNVNULPN,"SERIALNOFIELD");

				//clickXpath(SUBMITBTN,"SUBMITBTN");
				Thread.sleep(1000);

				enterXpath(VQUANTITY, ReqQuantity,"VQUANTITY");

				Thread.sleep(1000);
				clickXpath("//*[@id='root']/div/div/main/div/div/div/div[3]/div/div/p/form/div[4]/div/b");
				Thread.sleep(1000);
				clickXpath("//*[@id='simple-menu']/div[2]/ul/li[1]");
				Thread.sleep(1000);
				//AlertHandle();
				clickXpath("//*[@id='root']/div/div/main/div/div/div/div[5]/div[2]/div/div/div/div/button[1]/span[1]");
				Thread.sleep(1000);
				actionClick(ALERT);
				Thread.sleep(2000);
				

			} else if(PartType.equals("NV")){



				//verify("(//*[contains(.,'scan')])[16]","scan");
				clickXpath(INFO,"INFO");

				randomNVLPN1=xpathGetText(LPN0);

				randomNVLPN2=xpathGetText(LPN1);
				fluentWaitxpath(30,5,LPNCLOSE);
				clickXpath(LPNCLOSE,"LPNCLOSE");
				Thread.sleep(1000);
				clickXpath("//*[@id='root']/div/div/main/div/div/div/div[3]/div/div/p/form/div[4]/div/b");
				Thread.sleep(1000);
				clickXpath("//*[@id='simple-menu']/div[2]/ul/li[1]");
				Thread.sleep(1000);
				//AlertHandle();
				clickXpath("//*[@id='root']/div/div/main/div/div/div/div[5]/div[2]/div/div/div/div/button[1]/span[1]");
				actionClick(ALERTBOXCANCEL);
				Thread.sleep(2000);
			}





		}





		clickIDLinkText("Misc");

		clickIDLinkText("Approve Request");

		verify(SEARCH_BOX);
		clickXpath(RAND,"Random");
		verify(SEARCH_BOX);

		enterXpath(RANDSEAR, cusref[1],"Search");

		List<WebElement> parts=driver.findElements(By.xpath(APPROVALPARTS));
		for(WebElement a:parts){
			approvalParts.add(a.getText());
		}


		clickXpath(CHECK1,"Check box");

		selectByVisibleText(APPROVALSELECT,"Approve");
		clickXpath("//button[contains(@id,'buttonSave')]","Save button");
		Thread.sleep(2000);

		clickXpath("//SPAN[@class='close'][text()=' × ']","close");




		int Count=0;
	//	String bin="B1";
		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
		clickXpath(OUTBOUND);
		shortWait(3000);
		clickXpath(PICKINGGRID);
		while(Count<2)
		{

			enterXpath(PICKINGSEARCH, cusref[1],"PICKINGSEARCH");


			clickXpath(PICKSEARCH, "search");
			verify(PARTTYPE);
			String PartType =xpathGetText(PARTTYPE);
			Count++;


			clickXpath(PARTTYPE,"Part");

			String ReqQuantity="2";

			if (PartType.equals("NVNU")) {


				// serialnumber2=driver.findElement(By.xpath("/html/body/div[2]/div[2]/p/div[2]/table/tbody/tr[3]/td[1]")).getText();


				//Thread.sleep(2000);
				//verify("(//*[contains(.,'scan')])[16]","scan");
				enterXpath(SERIALNOFIELD, Bin,"SERIALNOFIELD");
				//Thread.sleep(1000);
				clickXpath(SUBMITBTN,"SUBMITBTN");
				verify("(//*[contains(.,'Successfully')])[16]","Successfully");
			//	Thread.sleep(1000);
				enterXpath(SERIALNOFIELD, randomNVNULPN,"SERIALNOFIELD");
			//	Thread.sleep(1000);
				//clickXpath(SUBMITBTN,"SUBMITBTN");
				//Thread.sleep(1000);
				enterXpath(VQUANTITY, ReqQuantity,"VQUANTITY");
				//Thread.sleep(1000);
				clickXpath(SUBMITBTN,"SUBMITBTN");
				Thread.sleep(1000);
				clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");

				partStockCheck(outNVNUpart[1],  "picking",2);

				//driver.findElement(By.xpath("(//SPAN[@class='close'][text()=' × '])[2]")).click();
				//	Thread.sleep(2000);

			} else if(PartType.equals("NV")) {
				//driver.findElement(By.xpath("(//img[contains(@alt,'o')])[4]")).click();
				//Thread.sleep(2000);


				//verify("(//*[contains(.,'scan')])[16]");
				enterXpath(SERIALNOFIELD, randomNVLPN1,"SERIALNOFIELD");
				//	Thread.sleep(1000);
				clickXpath(SUBMITBTN,"SUBMITBTN");

				//Thread.sleep(1000);
				verify("(//*[contains(.,'successfully')])[16]","successfully");
				enterXpath(SERIALNOFIELD, randomNVLPN2,"SERIALNOFIELD");
				//	Thread.sleep(1000);
				clickXpath(SUBMITBTN,"SUBMITBTN");
				Thread.sleep(3000);
				LPNStatus(randomNVLPN2,Module.PICKING);
				clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
				partStockCheck(outNVpart[1],  "picking",2);

				//Thread.sleep(3000);
				//driver.navigate().refresh();

			}
		}
		enterXpath(PICKINGSEARCH, cusref[1],"PICKINGSEARCH");
		clickXpath(PICKSEARCH, "search");

		verify(RECORD,"NO");

		clear1(PICKINGSEARCH);
		childTest.info("Reserved LPN's After Random Pick    : "+ randomNVNULPN+"   ||   "+randomNVLPN1+randomNVLPN2);

		orderStatus(cusref[1], Module.PICKING);

	}

	@Test(dependsOnMethods={"Random_pick"})
	public void shortpick() throws Exception {
		childTest = parantTest.createNode("Short Pick");
		childTest.info("The order available in picking screen");
		String[] cusref = new String[CustRefList.size()];
		CustRefList.toArray(cusref);



		//clickIDLinkText("Picking");
		//	Thread.sleep(1000);
		//	driver.navigate().refresh();


		enterXpath(PICKINGSEARCH, cusref[2],"PICKINGSEARCH");
		clickXpath(PICKSEARCH, "search");


		// for (int i = 0; i <= 1; i++) {
		shortPartType = xpathGetText(PARTTYPE);
		//System.out.println(shortPartType);
		clickXpath(PARTTYPE,"PARTTYPE");
		//Thread.sleep(2000);
		String ReqQuantity="1";
		if (shortPartType.equals("NVNU")) {

			//verify("(//*[contains(.,'Please')])[16]","Please");
			enterXpath(SERIALNOFIELD, Bin,"SERIALNOFIELD");
			Thread.sleep(1000);
			clickXpath(SUBMITBTN,"SUBMITBTN");
			//verify("(//*[contains(.,'Please')])[16]","Please");
			clickXpath(INFO,"INFO");
			shortNVNULPN = xpathGetText(LPNINFO);
			clickXpath(CANCEL,"CANCEL");
			//System.out.println(shortNVNULPN);
			Thread.sleep(1000);
			enterXpath(SERIALNOFIELD, shortNVNULPN,"SERIALNOFIELD");
			//Thread.sleep(1000);
			//clickXpath(SUBMITBTN,"SUBMITBTN");
			//verify("(//*[contains(.,'successfully')])[16]","successfully");
			enterXpath(VQUANTITY, ReqQuantity,"VQUANTITY");
			//Thread.sleep(2500);
			//Thread.sleep(1000);
			clickXpath(SUBMITBTN,"SUBMITBTN");


			Thread.sleep(2000);

			clickXpath("//*[@id='root']/div/div/main/div/div/div/div[3]/div/div/p/form/div[4]/div/b");
			Thread.sleep(1000);
			clickXpath("//*[@id='simple-menu']/div[2]/ul/li[1]");
			Thread.sleep(1000);
			//AlertHandle();
			clickXpath("//*[@id='root']/div/div/main/div/div/div/div[5]/div[2]/div/div/div/div/button[1]/span[1]");
			actionClick(ALERTBOXCANCEL);
			//verify(ALERTBOXCANCEL);
//			AlertHandle();
//			clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");

			//childTest.log(Status.PASS, MarkupHelper
			// .createLabel(NVNUQtn + "  NVNU Type part picked sucessfully  ", ExtentColor.GREEN));
			//Thread.sleep(1000);
		} else {
			//driver.findElement(By.xpath("(//img[contains(@alt,'o')])[4]")).click();


			//verify("(//*[contains(.,'Please')])[16]","Please");
			clickXpath(INFO,"INFO");

			shortNVLPN1 = xpathGetText(LPNSCAN);
			// serialnumber2=driver.findElement(By.xpath("/html/body/div[2]/div[2]/p/div[2]/table/tbody/tr[3]/td[1]")).getText();

			clickXpath(CANCEL,"CANCEL");
			Thread.sleep(1000);

			enterXpath(SERIALNOFIELD, shortNVLPN1,"SERIALNOFIELD");
			//Thread.sleep(1000);
			clickXpath(SUBMITBTN,"SUBMITBTN");
			LPNStatus(shortNVLPN1,Module.PICKING);
			clickXpath("//*[@id='root']/div/div/main/div/div/div/div[3]/div/div/p/form/div[4]/div/b");
			Thread.sleep(1000);
			clickXpath("//*[@id='simple-menu']/div[2]/ul/li[1]");
			Thread.sleep(1000);
			//AlertHandle();
			clickXpath("//*[@id='root']/div/div/main/div/div/div/div[5]/div[2]/div/div/div/div/button[1]/span[1]");
			//verify("(//*[contains(.,'successfully')])[16]","successfully");
//			selectByVisibleText(DROPDOWN, "Short Pick");
//
//			clickXpath(SUBMITBTN,"SUBMITBTN");
//			Thread.sleep(1000);
//			AlertHandle();


			Thread.sleep(2000);
			clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
			//Thread.sleep(2000);





		}
		// }
		//driver.navigate().refresh();


		//clickIDLinkText("Picking");
		Thread.sleep(1000);
		enterXpath(PICKINGSEARCH, cusref[2],"PICKINGSEARCH");
		clickXpath(PICKSEARCH, "search");
		String PartType = xpathGetText(PARTTYPE);
		clickXpath(PARTTYPE,"PARTTYPE");
		//verify("(//*[contains(.,'Please')])[16]");
		clickXpath("//*[@id='root']/div/div/main/div/div/div/div[3]/div/div/p/form/div[4]/div/b");
		//Thread.sleep(1000);
		clickXpath("//*[@id='simple-menu']/div[2]/ul/li[1]");
		//Thread.sleep(1000);
		//AlertHandle();
		clickXpath("//*[@id='root']/div/div/main/div/div/div/div[5]/div[2]/div/div/div/div/button[1]/span[1]");
		Thread.sleep(2000);

		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");



		clickIDLinkText("Misc");


		clickIDLinkText("Approve Request");


		verify(SEARCH_BOX);
		clickXpath(SHORTPIC,"Short");
		Thread.sleep(2000);
		enterXpath(MISCSEARCH, cusref[2],"MISCSEARCH");
		Thread.sleep(1000);
		actionEnter();
		clickXpath(CHECKBOXALL,"CHECKBOXALL");
		selectByVisibleText(APPROVALSELECT,"Approve");


		clickXpath("//button[contains(@id,'buttonSave')]","buttonSave");
		Thread.sleep(2000);

		clickXpath("//SPAN[@class='close'][text()=' × ']","close button");
		//partStockCheck(outNVNUpart[2],  "picking",0);

		//	partStockCheck(outNVpart[2],  "picking",1);
		if(shortPartType.equals("NVNU"))
		{
			partStockCheck(outNVNUpart[2],  "short",1);
			partStockCheck(outNVpart[2],  "short",1);
		}else
		{
			partStockCheck(outNVNUpart[2],  "short",1);
			partStockCheck(outNVpart[2],  "short",1);
		}

		clickXpath(OUTBOUND,"OUTBOUND");
		shortWait(3000);
		clickXpath(PICKINGGRID,"PICKINGGRID");
		//Thread.sleep(2000);
		enterXpath(PICKINGSEARCH, cusref[2],"PICKINGSEARCH");
		clickXpath(PICKSEARCH, "search");

		childTest.log(Status.PASS, "Shortpick completed");

		orderStatus(cusref[2], Module.PICKING);
	}

	@Test(dependsOnMethods={"shortpick"})
	public void optimization_skip() throws Exception{


		childTest = parantTest.createNode("Order Consolidation");
		String[] cusref = new String[CustRefList.size()];
		CustRefList.toArray(cusref);

skipConsolidation(cusref[0]);
		

		for(int i=0;i<3;i++){

			childTest.log(Status.PASS, "Order Skipped Successfully    :" +  cusref[i]);
			orderStatus(cusref[i], Module.CONSOLIDATION);
		}


	}

	@Test(dependsOnMethods={"optimization_skip"})
	public void dispatch() throws Exception {

		String[] cusref = new String[CustRefList.size()];
		CustRefList.toArray(cusref);


		childTest = parantTest.createNode(" Order Dispatch ");
		clickXpath(DISPATCHGRID,"DISPATCHGRID");
		// clickXpath(DISPATCHGRID);

		verify("(//*[@id='Despatch_REQUESTNO'])[1]");
		Thread.sleep(3000);
		enterXpath(DISPATCHSEARCH, cusref[0],"DISPATCHSEARCH");
		Thread.sleep(1000);
		clickXpath(Dispatch1,"Dispatch1");
		clickXpath(DISPATCHBUTTON,"DISPATCHBTN");
		Thread.sleep(1000);
		//verify("(//*[contains(.,' / ')])[23]","9");

		enterXpath(CARRIER, "Testing","CARRIER");
		childTest.info("The Mode of carrier is :" + "Testing");
		Thread.sleep(100);
		enterXpath(CARRIERREF, "SmokeTest","CARRIERREF");
		childTest.info("The carrier reference is :" + "SmokeTest");
		Thread.sleep(100);

		Thread.sleep(1000);

		enterXpath(SCANLPN,alternativeLPNNVNU,"SCANLPN");
		actionEnter();
		enterXpath("//*[@id=\"Despatch_enterQty1\"]","2","Quantity");
		actionEnter();
Thread.sleep(500);
		enterXpath(SCANLPN,alternativeNVLPN1,"SCANLPN");
		actionEnter();
		Thread.sleep(500);
		enterXpath(SCANLPN,alternativeNVLPN2,"SCANLPN");
		actionEnter();
		Thread.sleep(1000);
		enterXpath(SCANLPN,randomNVNULPN,"SCANLPN");
		actionEnter();
		Thread.sleep(1000);
		enterXpath("//*[@id=\"Despatch_enterQty1\"]", "2","Quantity");
		actionEnter();
Thread.sleep(1000);
		enterXpath(SCANLPN,randomNVLPN1,"SCANLPN");
		actionEnter();
		Thread.sleep(1000);
		enterXpath(SCANLPN,randomNVLPN2,"SCANLPN");
		actionEnter();

		if(shortPartType.equals("NVNU")){
			Thread.sleep(1000);
			enterXpath(SCANLPN,shortNVNULPN,"SCANLPN");
			actionEnter();
			enterXpath("//*[@id=\"Despatch_enterQty1\"]", "1","Quantity");
			actionEnter();

		}

		else{
			Thread.sleep(2000);
			enterXpath(SCANLPN,shortNVLPN1,"SCANLPN");
			actionEnter();


		}

		orderStatus(cusref[0], Module.DISPATCH);
		System.out.println("outbound orderform completed");
logOut();

		childTest.log(Status.PASS,
				MarkupHelper.createLabel(CustReference  +" : "+ "Order dispatched Sucessfully", ExtentColor.GREEN));

		/*driver.switchTo().window(tabs2.get(1));
			shortWait(1500);
			driver.close();
			shortWait(1500);
			driver.switchTo().window(tabs2.get(0));
			shortWait(1500);*/






	}
}