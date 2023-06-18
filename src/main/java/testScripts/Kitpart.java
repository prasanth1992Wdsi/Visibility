package testScripts;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class Kitpart extends ProjectSpecificFunctions{
	int n = 15;
	@Test(priority=0)
	public void kitpart_scenario1() throws IOException, Exception
	{
		parantTest = extent.createTest("Kitting"); 
		loginAsUser(Username);
		partv="CHILDV";
		partnvnu="CHILDNVNU_44";
//		partv1="NEWKITPARENT4";
//		partnv1="NEWKITPARENT2";
		partnvnu1="NEWKITPARENT44";
		picktype ="Fifosuggested";
		part=new String[]{partnvnu};
		quantity=new String[]{"5"};
		site=prop.getProperty("SITEID");
		vehicleCreation(site);
		//ReadExcel1(prop.getProperty("POPATH"));
		
    writeDataForCSV(picktype,"Newdelpoint","po", part,quantity);
    
		childTest = parantTest.createNode("PO Order creation for kit part scenario");
		//clickIDLinkText("Inbound");

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
		childTest.log(Status.PASS,"The uploaded PO parts  details  ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
		shortWait(2000);
		childTest.pass("Order upload finished ");
		
		assignpod(PodUser);
		podGeneration();
		binnerAssignment(CustReference,Binner);
		binning("",CustReference,Arrays.asList(new String[]{partnvnu}), new int[]{5});
	
		childTest = parantTest.createNode("Kit PART Generation(KIT AS YES)");
		partv="CHILDV";
		partnvnu="CHILDNVNU_44";

		partnvnu1="NEWKITPARENT44";
				//randomizer("SO");
		soreadData();
		verify(OUTBOUND);
		clickXpath(OUTBOUND,"OUTBOUND");
		verify(OUTBOUND);
		
		
		
        clickIDLinkText("Pick Generation");
		verify(PICKGENERATION);
		

		clickXpath("//span[contains(.,'Create request')]","Create request");

		clickXpath("//label[@id='PickGen_Kitting']","Kitting");
		Thread.sleep(2000);
		actionClick("(//*[@class=' css-1kvcxg-control'])[2]");
		Thread.sleep(2000);
		actionEnter();
		Thread.sleep(2000);
		actionClick("(//*[@class=' css-1kvcxg-control'])[3]");
		Thread.sleep(2000);
		WebElement txt=driver.findElement(By.xpath("(//*[@class=' css-1kvcxg-control'])[3]"));
		Thread.sleep(2000);
		Actions builder = new Actions(driver);
		Thread.sleep(2000);
		Action seriesOfActions = builder
			.moveToElement(txt)
			.click()
			.sendKeys(txt,partnvnu1)
			.build();
			
		seriesOfActions.perform() ;
		Thread.sleep(1000);
		actionEnter();
		Thread.sleep(2000);

		clear1("//input[@id='PickGen_KittingCustRef']");
		
		
		enterXpath("//input[@id='PickGen_KittingCustRef']",CustReference);//cusref[i]);
		Thread.sleep(2000);
		enterXpath("//input[@id='PickGen_KittingQty']","1");
		Thread.sleep(2000);
		clickXpath("//button[@id='PickGen_KittingAdd']");
		Thread.sleep(2000);
		
		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("window.scrollTo(document.body.scrollHeight, 400)");

		Thread.sleep(3000);
		Thread.sleep(4000);
		
		clickXpath("//*[@id='root']/div/div/main/div/div/div[2]/div[3]/div[1]/button/span[1]");
		//*[@id="root"]/div/div/main/div/div/div[2]/div[3]/div[1]/button/span[1]
		CommonFunctions.childTest.log(Status.PASS,"File upload status",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build());
		verify(ALERTBOXCANCEL);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		pickerassign1(CustReference);
	pickingScreenkit();
	clickIDLinkText("Inbound");
	binnerAssignment(CustReference,Binner);
	binning("",CustReference,Arrays.asList(new String[]{partnvnu1}), new int[]{1});
	//binningrekit();
	deleteFile();
		//logOut();
	
	}
	@Test(dependsOnMethods="kitpart_scenario1")
	//@Test(priority=0)
	public void kitpart_scenario2() throws IOException, Exception
	{
		//loginAsAdmin();
		partv="CHILDV";
		partnvnu="CHILDNVNU_44";
//		partv1="NEWKITPARENT4";
//		partnv1="NEWKITPARENT2";
		partnvnu1="NEWKITPARENT45";
		part=new String[]{partnvnu};
		quantity=new String[]{"5"};
		site=prop.getProperty("SITEID");
		picktype ="Fifosuggested";
		vehicleCreation(site);
		//ReadExcel1(prop.getProperty("POPATH"));
		
    writeDataForCSV(picktype,"Newdelpoint","po", part,quantity);

		childTest = parantTest.createNode("PO Order creation for kit part scenario");
		//clickIDLinkText("Inbound");

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
		childTest.log(Status.PASS,"The uploaded PO parts  details  ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
		shortWait(2000);
		childTest.pass("Order upload finished ");
	//POD =prop.getProperty("POD_USER");
		assignpod(PodUser);
		podGeneration();
		binnerAssignment(CustReference,Binner);
		binning("",CustReference,Arrays.asList(new String[]{partnvnu}), new int[]{5});
	
	
		childTest = parantTest.createNode("KIT Part creation(KIT AS NO)");
		//Kitpart1("SO");
		//randomizer("SO");
		//soreadData();
		verify(OUTBOUND);
		clickXpath(OUTBOUND,"OUTBOUND");
		verify(OUTBOUND);
		
		
		
        clickIDLinkText("Pick Generation");
		verify(PICKGENERATION);
		

		clickXpath("//span[contains(.,'Create request')]","Create request");

		clickXpath("//label[@id='PickGen_Kitting']","Kitting");
		Thread.sleep(2000);
		actionClick("(//*[@class=' css-1kvcxg-control'])[2]");
		Thread.sleep(2000);
		actionEnter();
		Thread.sleep(2000);
		actionClick("(//*[@class=' css-1kvcxg-control'])[3]");
		Thread.sleep(2000);
		WebElement txt=driver.findElement(By.xpath("(//*[@class=' css-1kvcxg-control'])[3]"));
		Thread.sleep(2000);
		Actions builder = new Actions(driver);
		Thread.sleep(2000);
		Action seriesOfActions = builder
			.moveToElement(txt)
			.click()
			.sendKeys(txt,partnvnu1)
			.build();
			
		seriesOfActions.perform() ;
		Thread.sleep(1000);
		actionEnter();
		Thread.sleep(2000);

		clear1("//input[@id='PickGen_KittingCustRef']");
		
		
		enterXpath("//input[@id='PickGen_KittingCustRef']",CustReference);//cusref[i]);
		Thread.sleep(2000);
		enterXpath("//input[@id='PickGen_KittingQty']","1");
		Thread.sleep(2000);
		clickXpath("//button[@id='PickGen_KittingAdd']");
		Thread.sleep(2000);
	Thread.sleep(2000);
		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("window.scrollTo(document.body.scrollHeight, 400)");

		Thread.sleep(3000);
		Thread.sleep(4000);
		
		clickXpath("//*[@id='root']/div/div/main/div/div/div[2]/div[3]/div[1]/button/span[1]");
		//*[@id="root"]/div/div/main/div/div/div[2]/div[3]/div[1]/button/span[1]
		CommonFunctions.childTest.log(Status.PASS,"File upload status",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build());
		verify(ALERTBOXCANCEL);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		pickerassign1(CustReference);
		
	pickingScreenkit1();
	childTest = parantTest.createNode("Kitting");
	clickIDLinkText("Misc");
	Thread.sleep(1000);
	clickIDLinkText("Kitting");
	Thread.sleep(1000);
	enterXpath(MISCSEARCH, CustReference);
	Thread.sleep(1000);
	actionEnter();
	Thread.sleep(1000);
	clickXpath("//*[@id='row']");
	Thread.sleep(1000);
	enterXpath("//*[@id='cartLabel']", partnvnu1);
	Thread.sleep(1000);
	actionEnter();
	clickXpath(Picking_Info,"INFO");
	
	Thread.sleep(2000);
//	WebElement mytable = driver.findElement(By.xpath("/html/body/div[2]/div[2]/p/div[2]/table"));
//	List < WebElement > rows_table = mytable.findElements(By.tagName("tr"));
//	int rows_count = rows_table.size();
//
//	for (int row = 2; row <= rows_count; row++) {
	String LPN=xpathGetText("  /html/body/div[2]/div[2]/p/div[2]/table/tbody/tr[2]/td[3]");
		String QUANTITY=xpathGetText("//*[@id='simple-popper']/div[2]/p/div[2]/table/tbody/tr[2]/td[4]");
//		Lpn.put(LPN, QUANTITY);
//
//	}

	clickXpath(LPNCLOSE1,"Infoclose");
//	for(Map.Entry<String, String> Pick_Lpn:Lpn.entrySet()){
//		String  VLPN = Pick_Lpn.getKey();
//		System.out.println(VLPN);
//		String  VQty11 = Pick_Lpn.getValue();
//		Thread.sleep(2000);
//		enterXpath(VLPN_ENTER, VLPN,"LPN");
//		enterXpath(VLPN_QNTY,VQty11,"Quantity");
//		Thread.sleep(2000);
//		clickXpath(VLPN_SUBMIT,"submit");
//	}
	
	enterXpath(VLPN_ENTER, LPN,"LPN");
	clickXpath(VLPN_SUBMIT,"submit");
	enterXpath(VLPN_QNTY,QUANTITY,"Quantity");
	Thread.sleep(2000);
	clickXpath(VLPN_SUBMIT,"submit");
	childTest.log(Status.PASS,
			MarkupHelper.createLabel("Kit part picked sucessfully  :", ExtentColor.GREEN));

	Thread.sleep(2000);
	driver.navigate().refresh();
	childTest.log(Status.PASS,"Picking Completed for Kitpart(kit as NO),Alert Box : ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
	//clickXpath(ALERTBOXCANCEL,"ALERTBOX");
	clickIDLinkText("Inbound");
	binnerAssignment(CustReference,Binner);
	binning("",CustReference,Arrays.asList(new String[]{partnvnu1}), new int[]{1});
	//binningrekit();
	deleteFile();
	
	}
	@Test(dependsOnMethods="kitpart_scenario2")
	//@Test(priority=0)
	public void kitpart_scenario3() throws IOException, Exception
	{
				//Kitpart1("SO");
		partv="CHILDV";
		partnv="CHILDNV";
		partnvnu="CHILDNVNU_44";
//		partv1="NEWKITPARENT4";
//		partnv1="NEWKITPARENT2";
		partnvnu1="NEWKITPARENT45";
		picktype ="Fifosuggested";
		parantTest = extent.createTest("DEKITTING");
		childTest = parantTest.createNode("Dekitting process");
		verify(OUTBOUND);
		clickXpath(OUTBOUND,"OUTBOUND");
		//verify(OUTBOUND);
		
		
		
        clickIDLinkText("Pick Generation");
		verify(PICKGENERATION);
		

		clickXpath("//span[contains(.,'Create request')]","Create request");

		clickXpath("//label[@id='PickGen_Kitting']","Kitting");
		Thread.sleep(2000);
		clickXpath("//*[@id='dekitting']");
		Thread.sleep(2000);
		actionClick("(//*[@class=' css-1kvcxg-control'])[2]");
		Thread.sleep(2000);
		actionEnter();
		Thread.sleep(2000);
		actionClick("(//*[@class=' css-1kvcxg-control'])[3]");
		Thread.sleep(2000);
		WebElement txt=driver.findElement(By.xpath("(//*[@class=' css-1kvcxg-control'])[3]"));
		Thread.sleep(2000);
		Actions builder = new Actions(driver);
		Thread.sleep(2000);
		Action seriesOfActions = builder
			.moveToElement(txt)
			.click()
			.sendKeys(txt,partnvnu1)
			.build();
			
		seriesOfActions.perform() ;
		Thread.sleep(1000);
		actionEnter();
		Thread.sleep(2000);

		clear1("//input[@id='PickGen_KittingCustRef']");
		CustReferen = getAlphaNumericString(n).trim();
		
		enterXpath("//input[@id='PickGen_KittingCustRef']",CustReferen);//cusref[i]);
		Thread.sleep(2000);
		enterXpath("//input[@id='PickGen_KittingQty']","1");
		Thread.sleep(2000);
		clickXpath("//button[@id='PickGen_KittingAdd']");
		Thread.sleep(2000);
	Thread.sleep(2000);
		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("window.scrollTo(document.body.scrollHeight, 400)");

		Thread.sleep(1000);
		Thread.sleep(1000);
		Thread.sleep(1000);
		Thread.sleep(1000);
		
		clickXpath("//*[@id='root']/div/div/main/div/div/div[2]/div[3]/div[1]/button/span[1]");
		//*[@id="root"]/div/div/main/div/div/div[2]/div[3]/div[1]/button/span[1]
		CommonFunctions.childTest.log(Status.PASS,"File upload status",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build());
		verify(ALERTBOXCANCEL);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		pickerassign1(CustReferen);
		
	pickingScreendekit();
	childTest = parantTest.createNode("Dekitting");
	clickIDLinkText("Misc");
	Thread.sleep(1000);
	clickIDLinkText("Dekitting");
	Thread.sleep(1000);
	enterXpath(MISCSEARCH, CustReferen);
	Thread.sleep(1000);
	actionEnter();
	Thread.sleep(1000);
	clickXpath("//*[@id='row']");
	Thread.sleep(1000);
	enterXpath("//*[@id='Despatch_ScanCarton/LPN']", partnvnu1);
	Thread.sleep(1000);
	actionEnter();

	childTest.log(Status.PASS,
			MarkupHelper.createLabel("DEKit part picked sucessfully  :", ExtentColor.GREEN));

	Thread.sleep(2000);
	//driver.navigate().refresh();
	childTest.log(Status.PASS,"Dekit process completed,Alert Box : ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
	clickXpath(ALERTBOXCANCEL,"ALERTBOX");
	clickIDLinkText("Inbound");
	binnerAssignment(CustReferen,Binner);
	binning("",CustReferen,Arrays.asList(new String[]{partnvnu}), new int[]{1});

	
deleteFile();
logOut();
	}
}