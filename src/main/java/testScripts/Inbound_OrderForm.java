

package testScripts;

import java.awt.AWTException;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class Inbound_OrderForm extends ProjectSpecificFunctions{



	String[] Picktype ;
	String[] Vpart ;
	String[] NVpart ;
	String[] NVNUpart ;
	@Test(priority=0)
	public void vehicleCreatioN() throws IOException, Exception {
		parantTest = extent.createTest("Inbound_OrderCreation through UI");

		loginAsUser(Username);
		System.out.println("inbound orderform started");
	//	ReadExcel(prop.getProperty("POPATH"));

		vehicleCreation(site);

		childTest.log(Status.PASS, "Vehicle ID created");



	}
	@Test(dependsOnMethods={"vehicleCreatioN"})
	public void captureParentDetails() throws IOException, Exception {

		childTest = parantTest.createNode("Capture Parent Document Details");
		clickIDLinkText(DASHBOARD);
		driver.navigate().refresh();


		verify(GATEIN_1stROW);
		enterXpath(SEARCH_BOX, VEHICLENUMBER,"SEARCH_BOX");



		childTest.info("Vehicle number is :  " + VEHICLENUMBER);
		VehicleID = xpathGetText(GATEIN_1stROW);
		clickXpath(VEH,"First record");


		clickXpath(DOCKIN_BTN,"DOCKIN_BTN");
		clickXpath(CREATEREQUEST_BTN,"CREATEREQUEST_BTN");


		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		verify(DOC_TYPE);
		actionClick("(//*[@class=' css-1hwfws3'])[1]");
		Thread.sleep(1000);
		if(account1)
		{
			clickDownArrow();
		}
		Thread.sleep(1000);
		actionEnter();

		selectByVisibleText(DOC_TYPE, prop.getProperty("DOC_TYPE"));
		childTest.info("Document type is :  " + prop.getProperty("DOC_TYPE"));
		enterXpath(DOC_NO, prop.getProperty("DOC_NO"),"DOC_NO");
		childTest.info("Document NO is :  " + prop.getProperty("DOC_NO"));
		//verify("(//*[contains(.,'OTIS INDIA')])[16]");


		clickXpath(DOC_SAVE,"DOC_SAVE");


		childTest.pass("Customer Details Saved Successfully");

	}


	@Test(dependsOnMethods={"captureParentDetails"})
	public void inbOrderform() throws Exception {	


		childTest = parantTest.createNode("Inbound_OrderForm_OrderCreation");
		verify(DOCK);
		
		clickXpath(INBOUNDORDERFORM,"INBOUNDORDERFORM");
	
	 Picktype = prop.getProperty("Picktype").split(",");
		 Vpart = prop.getProperty("Vpart").split(",");
		 NVpart = prop.getProperty("NVpart").split(",");
		NVNUpart = prop.getProperty("NVNUpart").split(",");
		CustRefList = new ArrayList<String>();
		for(int i2=0;i2<=3;i2++) {
			int n=15;
			CustRefList.add(CustRefNo= getAlphaNumericString(n));
			//System.out.println(CustRefList);
		}

		String[] cusref = new String[CustRefList.size()];

		CustRefList.toArray(cusref);

		for(int i=0; i<=CustRefList.size()-1; i++){


			//Creating Vpart------------------------------------


			if(driver.findElements(By.xpath(INBOUNDCREATEREQUEST)).size() > 0){
				Thread.sleep(2000);
				JavascriptExecutor js = (JavascriptExecutor) driver;

				js.executeScript("window.scrollTo(document.body.scrollHeight, 0)");


				clickXpath(CREATEREQ,"CREATE REQUEST");
				List cr=driver.findElements(By.xpath(PROGRESS));
				while(cr.size()!=0)
				{
					Thread.sleep(1000);
					cr=driver.findElements(By.xpath(PROGRESS));
				}
				
				actionClick(INBOUNDORDERFORM);


			}
			verify(INBOUNDCUSTOMERREFERENCE);
			clear1(INBOUNDCUSTOMERREFERENCE);
			enterXpath(INBOUNDCUSTOMERREFERENCE,cusref[i],"INBOUNDCUSTOMERREFERENCE");
		


			
			sendChar(INBOUNDPARTNUMBER,Vpart[i],"INBOUNDPARTNUMBER");
			Thread.sleep(3000);
			actionEnter();


			clear1(INBOUNDQUANTITY);
			enterXpath(INBOUNDQUANTITY,"5","INBOUNDQUANTITY");

			if(Picktype[i].equals("Custlpnpart")){

				int n1=5;
				String CustLPN = getAlphaNumericString(n1);

				enterXpath(INBOUNDCUSTOMERLPN,CustLPN,"INBOUNDCUSTOMERLPN");

			}	


			
			if(Picktype[i].equals("Fmfopart")){

				enterXpath(INBOUNDMANUFACTURINGDATE,"01");
				enterXpath(INBOUNDMANUFACTURINGMONTH,"01");
				enterXpath(INBOUNDMANUFACTURINGYEAR,"2021");


			}
		
			clickXpath(INBOUNDADDBUTTON,"INBOUNDADDBUTTON");



			//Creating NVpart------------------------------------



			if(Picktype[i].equals("Custlpnpart")){	

				for(int j=0;j<=5-1;j++){
					fluentWaitxpath(3,3,INBOUNDCUSTOMERREFERENCE);
					clear1(INBOUNDCUSTOMERREFERENCE);
					enterXpath(INBOUNDCUSTOMERREFERENCE,cusref[i],"INBOUNDCUSTOMERREFERENCE");
				
					sendChar(INBOUNDPARTNUMBER,NVpart[i],"INBOUNDPARTNUMBER");
					Thread.sleep(1000);
					actionEnter();

					clear1(INBOUNDQUANTITY);
					enterXpath(INBOUNDQUANTITY,"1","INBOUNDQUANTITY");
					int n2=5;
					String CustLPN = getAlphaNumericString(n2);

					enterXpath(INBOUNDCUSTOMERLPN,CustLPN,"INBOUNDCUSTOMERLPN");
				
					clickXpath(INBOUNDPARTNUMBER,"INBOUNDPARTNUMBER");
			
					actionEnter();

					clickXpath(INBOUNDADDBUTTON,"INBOUNDADDBUTTON");


				}

			}

			else {

				clear1(INBOUNDCUSTOMERREFERENCE);
				enterXpath(INBOUNDCUSTOMERREFERENCE,cusref[i],"INBOUNDCUSTOMERREFERENCE");
								
				clickXpath(INBOUNDPARTNUMBER,"INBOUNDPARTNUMBER");
				sendChar(INBOUNDPARTNUMBER,NVpart[i],"INBOUNDPARTNUMBER");
				Thread.sleep(1500);
				actionEnter();



				clear1(INBOUNDQUANTITY);
				enterXpath(INBOUNDQUANTITY,"5","INBOUNDQUANTITY");
			
				if(Picktype[i].equals("Fmfopart")){


					enterXpath(INBOUNDMANUFACTURINGDATE,"01","INBOUNDMANUFACTURINGDATE");

					enterXpath(INBOUNDMANUFACTURINGMONTH,"01","INBOUNDMANUFACTURINGMONTH");

					enterXpath(INBOUNDMANUFACTURINGYEAR,"2021","INBOUNDMANUFACTURINGYEAR");


				}
			
				clickXpath(INBOUNDPARTNUMBER,"INBOUNDPARTNUMBER");
				Thread.sleep(1500);
				actionEnter();
				clickXpath(INBOUNDADDBUTTON,"INBOUNDADDBUTTON");

			}

			//Creating NVNU parts-------------------------------------------
			clear1(INBOUNDCUSTOMERREFERENCE);
			enterXpath(INBOUNDCUSTOMERREFERENCE,cusref[i],"INBOUNDCUSTOMERREFERENCE");
		

			sendChar(INBOUNDPARTNUMBER,NVNUpart[i],"INBOUNDPARTNUMBER");
			Thread.sleep(1000);
			actionEnter();


			clear1(INBOUNDQUANTITY);
			enterXpath(INBOUNDQUANTITY,"5","INBOUNDQUANTITY");

			if(Picktype[i].equals("Custlpnpart")){

				int n3=5;
				String CustLPN = getAlphaNumericString(n3);
				enterXpath(INBOUNDCUSTOMERLPN,CustLPN,"INBOUNDCUSTOMERLPN");

			}	
			
			if(Picktype[i].equals("Fmfopart")){


				enterXpath(INBOUNDMANUFACTURINGDATE,"01");

				enterXpath(INBOUNDMANUFACTURINGMONTH,"01");

				enterXpath(INBOUNDMANUFACTURINGYEAR,"2021");	


			}

		
			clickXpath(INBOUNDPARTNUMBER,"INBOUNDPARTNUMBER");
			Thread.sleep(1500);
			actionEnter();
			clickXpath(INBOUNDADDBUTTON,"INBOUNDADDBUTTON");




			fluentWaitxpath(3,3,INBOUNDSAVEBUTTON);
			clickXpath(INBOUNDSAVEBUTTON,"INBOUNDSAVEBUTTON");



			if(Picktype[i].equals("Custlpnpart")){

				fluentWaitxpath(30,3,INBOUNDDOCCLOSEBUTTON);
				clickXpath(INBOUNDDOCCLOSEBUTTON,"INBOUNDDOCCLOSEBUTTON");	

			}

			else{


				shortWait(1500);
				fluentWaitxpath(30,3,INBOUNDDOCCLOSEBUTTON);	
				clickXpath(INBOUNDDOCCLOSEBUTTON,"INBOUNDDOCCLOSEBUTTON");





			}

		}
		childTest.pass("FIFO PickType Inbound Order Created Successfully." + "The Order Cust ref is  :" + cusref[0] );
		childTest.pass("CUSTLPN PickType Inbound Order Created Successfully." + "The Order Cust ref is  :" + cusref[1]);
		childTest.pass("FMFO PickType Inbound Order Created Successfully."+ "The Order Cust ref is  :" + cusref[2]);
		childTest.pass("FIFOSUGG PickType Inbound Order Created Successfully." + "The Order Cust ref is  :" + cusref[3]);

		shortWait(1000);
		clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");	
		childTest.pass("Finish Upload Clicked Successfully  :  ", MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture1()).build());




	}

	@Test(dependsOnMethods={"inbOrderform"})
	public void assignPOD() throws IOException, Exception {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
		assignpod(PodUser);
	}

	@Test(dependsOnMethods={"assignPOD"})
	public void podGeneration1() throws IOException, Exception {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
		podGeneration();
	}

	@Test(dependsOnMethods={"podGeneration1"})
	public void binnerAssignment() throws IOException, Exception {


		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
		String[] cusref = new String[CustRefList.size()];
		CustRefList.toArray(cusref);
		for(int k=0; k<=CustRefList.size()-1; k++) {	
			binnerAssignment(cusref[k],Binner);
		}
	}

	@Test(dependsOnMethods={"binnerAssignment"})
	public void binning() throws IOException, Exception {

		childTest = parantTest.createNode("Binning");	
		clickXpath(BINNINGORDERFORM, "Binning");	
		
		String[] cusref = new String[CustRefList.size()];
		CustRefList.toArray(cusref);

		for(int k=0; k<CustRefList.size(); k++) {
		//	VQTY1="5";
		//	NVNUQTY="5";
		//	VQTY="5";
			picktype=Picktype[k];
			binPickPart=new String[]{Vpart[k],NVpart[k],NVNUpart[k]};
			binPickPartquantity= new int[]{5,5,5};
			binning("",cusref[k].trim(),Arrays.asList(new String[]{Vpart[k],NVpart[k],NVNUpart[k]}), new int[]{5,5,5});


		}
		System.out.println("inbound orderform completed");
		logOut();
	}


}