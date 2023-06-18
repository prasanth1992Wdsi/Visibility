package testScripts;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
//import com.gargoylesoftware.htmlunit.javascript.host.fetch.Response;
//import com.jayway.jsonpath.JsonPath;
import io.restassured.path.json.JsonPath;
//import com.sun.xml.xsom.impl.Ref.ContentType;
import io.restassured.response.Response;

public class ProjectSpecificFunctions extends CommonFunctions {
	public String VEHICLENUMBER;
	public String BinName;
	public String packingslip1;
	public String VON;
	String CustReferen;
	ArrayList<String> NVLPNDB=new  ArrayList<String>();
	public static HSSFWorkbook wb1;
	public boolean account1 = false;
	ArrayList<String> NVLPNpicking=null;
	ArrayList<String> NVLPN11picking;
	ArrayList<String> NVLPN=null;
	ArrayList<String> NVLPN2=null;

	String LPNNVNU=null;

	ArrayList<String> box=new ArrayList<String>();
	public String CON_VON ;
	public  String[] part;
	public   String[] quantity;
	//public  String siteid;
	//public  String accountid;
	public String Bin=null;

	public String binPickPart[];
	public int binPickPartquantity[];
	public String Binner;
	public String PodUser;
	public String Picker;
	public String Username;
	public String result;

	@BeforeSuite
	public void setUp() throws Exception{
		File directory=new File(".//Screenshots");
		File[] files=directory.listFiles();
		for(File file:files)
		{
			file.delete();
		}
		extentreport();

	}

	@AfterSuite
	public void endTest(){

		extent.flush();
		Email_trigger();


	}

	@BeforeClass
	public void before() throws Exception
	{
		readPropertyFile();


		Bin=prop.getProperty("BIN_LABEL");
		site=prop.getProperty("SITEID");
		account=prop.getProperty("ACCOUNTID");
		Binner=prop.getProperty("POD_USER");
		Picker=prop.getProperty("POD_USER");
		PodUser=prop.getProperty("POD_USER");
		Username=prop.getProperty("USERNAME");
		STOCK_TYPE=prop.getProperty("STOCK_TYPE");
		GateInType="UNLOADING";
		Consignee=null;
		SimpleDateFormat dt1 = new SimpleDateFormat("yyyyy-MM-dd");

		date= new Date();
		currentdate=dt1.format(date);
		pdf = null;
		pdffilename = currentdate.toString().replace(" ", "-").replace(":", "-");
		folder = new File(prop.getProperty("FOLDER")+pdffilename);
		folder.mkdirs();
		if(folder.exists()){

			System.out.println("Folder created");
		}

		else{

			System.out.println("Folder not created");
		}


	}
	public void loginAsUser(String Username) throws Exception {
		childTest = parantTest.createNode("Login");

		//System.setProperty("webdriver.chrome.driver", "./chromedriver1.exe");
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();

		options.addArguments("--disable-dev-shm-usage"); 
		options.addArguments("--disable-infobars");// overcome

		options.addArguments("--disable-notifications");
		options.addArguments("start-maximized"); // open Browser in

		options.addArguments("--disable-extensions"); // disabling
		// extensions
		options.addArguments("--disable-gpu"); // applicable to windows
		// os only
		options.addArguments("--no-sandbox"); // Bypass OS security
		// model
		HashMap<String,Object> prefs = new HashMap<String,Object>();
		prefs.put("plugins.always_open_pdf_externally", true);
		prefs.put("download.default_directory", folder.getAbsolutePath());
		options.setExperimentalOption("prefs", prefs);
		System.out.println(prefs);

		driver = new ChromeDriver(options);
		

		System.out.println("The driver launched successfully");


		driver.get(prop.getProperty("URL"));
		enterXpath(USERNAME, Username, "Username");
		enterXpath(PASSWORD, Username, "PASSWORD");
/*
		Thread.sleep(2000);	

		fluentWait(USERNAME).sendKeys(Username);		
		Thread.sleep(3000);
		fluentWait(PASSWORD).sendKeys(Username);		
*/
		explicitWaitXpathClick(SHORTWAIT, LOGIN_BTN);
		Thread.sleep(2000);	
		//driver.manage().window().fullscreen();

		driver.manage().window().maximize();
		System.out.println(driver. manage(). window(). getSize());

		try {	

			Thread.sleep(2000);
			fluentWait(LOGOUTYES).click();;
			System.out.println("User Logged successfully");

		}catch (Exception e) {
			System.out.println("User Logged successfully");
		}
		//			Thread.sleep(3000);	

		Assert.assertEquals(prop.getProperty("LOGINEXPECTEDURL"),driver.getCurrentUrl());
		System.out.println("User logged Successfully");
		driver.navigate().refresh();
	}

	public void logOut() throws Exception {

		// FileUtils.deleteDirectory(folder);

		childTest = parantTest.createNode("Logout");
		clickXpath("//I[@class='fa fa-user']","user");
		clickXpath("//img[@id='logout']","logout");
		extent.flush();
		driver.quit();

		childTest.pass("Application loggedout successfully");			


	}
	//kitting
	public void pickingScreenkit() throws Exception {
		Thread.sleep(3000);

		deleteFile();

		clickXpath(PICKINGGRID,"PICKINGGRID");

		childTest = parantTest.createNode("Picking");



		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("window.scrollTo(document.body.scrollHeight, 0)");


		enterXpath(PICKINGSEARCH,CustReference,"PICKINGSEARCH");
		clickXpath(PICKSEARCH, "search");
		String kitPart="1";
		PartType = xpathGetText(PARTTYPE);
		Thread.sleep(2000);	
		partName=xpathGetText(PARTNAME);
		clickXpath(PARTTYPE,"Select Part    :"+PartType);
		Thread.sleep(2000);			    
		childTest.info("The order part  :"+partName +" :" +"  Part type  "+ PartType +" : part available in picking screen");
		ArrayList<String> PICKLPN1=new  ArrayList<String>();
		clickXpath("//*[@id='root']/div/div/main/div/div/div/div[2]/div/div/p/form/div[3]/button[2]/span[1]");
		Thread.sleep(2000);	
		enterXpath(CARTLABELFIELD,partName ,"CARTLABELFIELD");
		clickXpath(SUBMITBTN,"SUBMITBTN");
		clickXpath("(//img[contains(@alt,'o')])[5]","INFO");
		String PICKLPNNVNU = xpathGetText("(/html/body/div[2]/div[2]/p/div[2]/table/tbody/tr/td[4])[2]");
		/*for(int p=3;p<=4;p++)
		{
			String PICKLPN = xpathGetText("(/html/body/div[2]/div[2]/p/div[2]/table/tbody/tr/td[4])["+p+"]");
			PICKLPN1.add(PICKLPN);
			System.out.println(PICKLPN1);

		}*/
		clickXpath(CLOSEKIT,"Infoclose");
		//clickXpath("//*[@id='root']/div/div/main/div/div/div/div[2]/div/div/p/form/div[3]/button[2]/span[1]");



		childTest.info("Cart Label is scanned");
		enterXpath(SERIALNOFIELD, PICKLPNNVNU,"LPNFIELD");
		//NV			
		actionEnter();
		Thread.sleep(1000);
		enterXpath(SERIALNOFIELD, Bin,"LPNFIELD");
		actionEnter();
		Thread.sleep(1000);
		enterXpath("//*[@id='Picking_enterQty']",kitPart,"Quantity");
		actionEnter();
		int i=0;
		for(String a:PICKLPN1)
		{
			++i;
			Thread.sleep(1000);
			enterXpath(SERIALNOFIELD, a,"LPNFIELD");
			//NV			
			actionEnter();


		}

		enterXpath("//*[@id='Picking_enterQty']",kitPart,"Quantity");
		actionEnter();
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		enterXpath(PICKINGSEARCH, CustReference,"MASTERSEARCH");
		clickXpath(PICKSEARCH, "search");
		childTest.pass("validation completed");

	}
	public void pickingScreenkit1() throws Exception {
		Thread.sleep(3000);

		if(!partv.equals("PARTVCUSLPN"))
		{
			FileUtils.cleanDirectory(folder);


		}
		clickXpath(PICKINGGRID,"PICKINGGRID");

		childTest = parantTest.createNode("Picking");



		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("window.scrollTo(document.body.scrollHeight, 0)");


		enterXpath(PICKINGSEARCH,CustReference,"PICKINGSEARCH");
		clickXpath(PICKSEARCH, "search");
		String kitPart="1";
		PartType = xpathGetText(PARTTYPE);
		Thread.sleep(2000);	
		partName=xpathGetText(PARTNAME);
		clickXpath(PARTTYPE,"Select Part    :"+PartType);
		Thread.sleep(2000);	
		//kit no
		clickXpath("//*[@id='root']/div/div/main/div/div/div/div[2]/div/div/p/form/div[1]/div[2]/div/div/div[2]");
		Thread.sleep(2000);	
		//v order
		clickXpath("//*[@id='root']/div/div/main/div/div/div/div[2]/div/div/p/form/div[2]/div[1]/div/div/div/div[1]/div[1]/b/span/b");

		childTest.info("The order part  :"+partName +" :" +"  Part type  "+ PartType +" : part available in picking screen");
		ArrayList<String> PICKLPN1=new  ArrayList<String>();
		clickXpath(Picking_Info,"INFO");
		Thread.sleep(2000);
		//		WebElement mytable = driver.findElement(By.xpath("/html/body/div[2]/div[2]/p/div[2]/table"));
		//		List < WebElement > rows_table = mytable.findElements(By.tagName("tr"));
		//		int rows_count = rows_table.size();

		//		for (int row = 2; row <= rows_count; row++) {
		LPN=xpathGetText("//*[@id='simple-popper']/div[2]/p/div[2]/table/tbody/tr[2]/td[4]");
		String QUANTITY=xpathGetText("//*[@id='simple-popper']/div[2]/p/div[2]/table/tbody/tr[2]/td[5]");


		clickXpath(LPNCLOSE1,"Infoclose");

		enterXpath(VLPN_ENTER, LPN,"LPN");
		clickXpath(VLPN_SUBMIT,"submit");



		Thread.sleep(1000);

		enterXpath(SERIALNOFIELD, Bin,"LPNFIELD");
		actionEnter();

		Thread.sleep(1000);
		enterXpath("//*[@id='Picking_enterQty']",kitPart,"Quantity");
		actionEnter();
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		enterXpath(PICKINGSEARCH, CustReference,"MASTERSEARCH");
		clickXpath(PICKSEARCH, "search");
		//verify("//*[contains(text(),'No Records Found')]");
		childTest.pass("validation completed");

	}


	public void pickingScreendekit() throws Exception {
		Thread.sleep(3000);

		//		if(!partv.equals("PARTVCUSLPN"))
		//		{
		//			FileUtils.cleanDirectory(folder);
		//
		//
		//		}
		deleteFile();
		clickXpath(PICKINGGRID,"PICKINGGRID");

		childTest = parantTest.createNode("Picking");



		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("window.scrollTo(document.body.scrollHeight, 0)");


		enterXpath(PICKINGSEARCH,CustReferen,"PICKINGSEARCH");
		clickXpath(PICKSEARCH, "search");
		String kitPart="1";
		PartType = xpathGetText(PARTTYPE);
		Thread.sleep(2000);	
		partName=xpathGetText(PARTNAME);
		clickXpath(PARTTYPE,"Select Part    :"+PartType);
		Thread.sleep(2000);	


		childTest.info("The order part  :"+partName +" :" +"  Part type  "+ PartType +" : part available in picking screen");


		//Nvnu part

		clickXpath("(//img[contains(@alt,'o')])[5]","INFO");
		String PICKLPNNVNU = xpathGetText("(/html/body/div[2]/div[2]/p/div[2]/table/tbody/tr/td[4])[2]");
		clickXpath(CLOSEKIT,"Infoclose");
		enterXpath(SERIALNOFIELD, Bin,"LPNFIELD");
		actionEnter();

		//enterXpath(SERIALNOFIELD, PICKLPNNVNU,"LPNFIELD");

		//actionEnter();
		enterXpath("//*[@id='Picking_enterQty']",kitPart,"Quantity");
		actionEnter();

		Thread.sleep(2000);

		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		enterXpath(PICKINGSEARCH, CustReferen,"MASTERSEARCH");
		clickXpath(PICKSEARCH, "search");
		//verify("//*[contains(text(),'No Records Found')]");
		childTest.pass("validation completed");

	}




	public void podGeneration() throws IOException, Exception {
		childTest = parantTest.createNode("POD Generation");

		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("window.scrollTo(document.body.scrollHeight, 0)");

		clickIDLinkText(POD_DASHBOARD);

		verify("(//*[contains(.,'DKIN')])[15]");
		enterXpath(SEARCH_BOX, VEHICLENUMBER,"VEHICLE SEARCH");
		actionEnter();
		clickXpath(ROW1,"ROW1");
		childTest.info("The order available in POD user dashboard");

		//	Assert.assertTrue(isElementPrese(POD_SAVE_BTN));
		enterXpath(EXPECTED_QTY, prop.getProperty("EXPECTED_QTY"),"EXPECTED QTY");
		enterXpath(RECEIVED_QTY, prop.getProperty("RECEIVED_QTY"),"RECEIVED QTY");
		clickXpath(POD_SAVE_BTN,"POD_SAVE_BTN");		
		clickXpath(POD_CLOSE_BTN,"POD_CLOSE_BTN");
		shortWait(2000);
		childTest.pass("Partial POD Generated Successfully");
		clickIDLinkText(POD_DASHBOARD);
		enterXpath(SEARCH_BOX, VEHICLENUMBER,"VEHICLE SEARCH");
		//verify("(//*[contains(.,'0 of 0')])[16]");
		childTest.pass("Partial POD Generated Successfully and also validated");


	}

	public void assignpod(String PodUser) throws IOException, Exception {


		childTest = parantTest.createNode("Assign POD");
		scrollUp();
		//clickIDLinkText(ASSIGNPOD_MENU);
		clickXpath(POD_OPTION, "POD_OPTION");
		verify(ASSIGNPODSELECT);
		enterXpath(ASSIGNPODSEARCH_BOX, VEHICLENUMBER,"ASSIGN POD SEARCH");
		clickXpath(ASSIGNPODSELECT,"ASSIGN_POD_SELECT");
		childTest.info("The order available in Assign POD screen");

		clickXpath(ASSIGN_POD_USER,"ASSIGN_POD_USER");
		verify(ASSIGN_USER_POD_BTN);
		Thread.sleep(1000);

		enterXpath(ASSIGNPODSEARCH, PodUser,"ASSIGN POD SEARCH");
		nameClick(PodUser);
		//clickXpath(PICKERNAME,"PICKER NAME");
		//	explicitWaitClick(20,ASSIGN_USER_POD_BTN);
		resize(1382, 650);

		clickXpath(ASSIGN_USER_POD_BTN,"ASSIGN_USER_POD_BTN");

		clickXpath(POD_ASIGN_SUCES_CLOSE_BTN,"POD_ASIGN_SUCES_CLOSE_BTN");
		childTest.pass("POD User Has Been Assigned Successfully." + "The assigned User is :"
				+ PodUser);
		enterXpath(ASSIGNPODSEARCH_BOX, VEHICLENUMBER,"ASSIGN POD SEARCH");
		verify("(//*[contains(.,'0 of 0')])[16]");
		//resize(1382, 754);

		driver.manage().window().maximize();

		childTest.pass("Validation in assign pod is completed ");

	}
	public void binnerAssignment(String CustReference,String Binner) throws IOException, Exception {
		scrollUp();

		childTest = parantTest.createNode("Binner Assignment");
		//verify(BINNER_ASSIGNMENT_MEU);
actionClick("//b[contains(.,'Binner Assignment')]");
		clickIDLinkText(BINNERASSIGNMENT_MENU);
		verify(ROW1);
		enterXpath(SEARCH_BOX, CustReference,"binnerAssignment SEARCH");
		clickXpath(SEARCH_BTN,"SEARCH_BTN");
		childTest.info("The order available in Binner assignment screen");
		verify("(//*[contains(.,'1 of 1')])[16]");

		clickXpath(BINNERASSIGN_CHECK_BOX,"BINNERASSIGN_CHECK_BOX");

		Thread.sleep(1000);
		clickXpath(ASSIGN_BINNER_BTH,"ASSIGN_BINNER_BTH");

		verify(ASSIGNBINNER);
		enterXpath(PICKERINPUT, Binner,"PICKER INPUT");
		nameClick(Binner);
		//nameClick("Barath0206");
		resize(1382, 650);

		//clickXpath(PICKERNAME,"PICKER NAME");
		clickXpath(ASSIGNBINNER,"ASSIGNBINNER");
		Thread.sleep(2000);
		verify(ALERTBOXCANCEL);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		childTest.pass("Binner Assigned Successfully." + "The Binner is : " + Binner);
		

		driver.manage().window().maximize();

	}






	public void pickerassign1(String SOCustReference) throws Exception {


		childTest = parantTest.createNode("picker Assignment");



		verify(CLICKRECORD);
		enterXpath(MASTERSEARCH, SOCustReference,"MASTERSEARCH");
		clickXpath(PICKGEN_SEARCH, "PICKGEN_SEARCH");
Thread.sleep(1000);
		//	verify("(//*[contains(.,'1-1 of 1')])[17]");
		VON = xpathGetText(Picking_Von);
		clickXpath(SELECTPART,"Checkbox");

		childTest.info("The order available in the pick generation screen");



		clickXpath(PICKERASSIGN,"PICKERASSIGN");

		verify(PICKERSUBMIT);

		enterXpath(PICKERINPUT, Picker,"PICKERINPUT");
		nameClick(Picker);
		resize(1382, 650);

		//nameClick("Barath0206");

		//resize(1382, 754);

		clickXpath(PICKERSUBMIT,"PICKERSUBMIT");
		Thread.sleep(2000);
		
		childTest.log(Status.INFO, "Picker is assigned:  ",MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture1()).build());
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		driver.manage().window().maximize();

		childTest.log(Status.PASS,
				MarkupHelper.createLabel(
						"Picker assigned succesfully." + "The Assigned Picker is  : " + prop.getProperty("PICKER"),ExtentColor.GREEN));

	}


	public void consolidation1() throws Exception {

		childTest = parantTest.createNode(" Order Consolidation ");
		clickXpath(CONSOLIDATIONGRID,"CONSOLIDATIONGRID");
		verify(CONSOLIDATION);
		enterXpath(MASTERSEARCH, SOCustReference,"PICKING SEARCH");
		clickXpath(ORDERCLICK,"ORDERCLICK");
		childTest.info("The order available in Consolidation screen");


		clickXpath(CONSOLIDATION,"CONSOLIDATION");
		Thread.sleep(2000);
		enterXpath(NOOFBOX, "1","NOOFBOX");
		clickXpath(ADDBOX,"ADDBOX");
		CartLabel = xpathGetText(LABEL);
		Thread.sleep(2000);
		clickXpath(SCANBOX,"SCANBOX");
		enterXpath(ENTERLPN, partnvnu,"ENTERLPN");

		clickXpath(SUBMIT,"SUBMIT");

		//verify("(//*[contains(.,'Please')])[19]");
		enterXpath(NUNVQNTY, NVNUQtn,"NUNVQNTY");
		clickXpath(SUBMIT,"SUBMIT");
		childTest.info("The NVNU Lpns Consolidated successfully");

		for(Map.Entry<String, String> Pick_Lpn:Lpn.entrySet()){
			String  VLPN = Pick_Lpn.getKey();
			enterXpath(ENTERLPN, VLPN ,"ENTERLPN");
			clickXpath(SUBMIT,"SUBMIT");
		}
		if(!Partial_LPNQTY.trim().equals("0")){
			enterXpath(ENTERLPN, stockNumber.get(0) ,"Enter Partial Quantity");
			clickXpath(SUBMIT,"SUBMIT");
		}

		//enterXpath(ENTERLPN, serialnumber1,"ENTERLPN");

		for(String apicking:NVLPNpicking)
		{
			enterXpath(ENTERLPN, apicking,"LPNFIELD");

			//verify("(//*[contains(.,'Successfully')])[16]");


			clickXpath(SUBMIT,"SUBMIT");
		}


		enterXpath(WEIGHT, "10","WEIGHT");
		Select select1 = new Select(driver.findElement(By.xpath(UOM)));
		select1.selectByVisibleText("Ton");
		clickXpath(COMPLETE,"COMPLETE");
		Thread.sleep(2000);

		clickXpath(FINISH,"FINISH");





		childTest.info("The order Consolidated successfully");

		childTest.info("The carton label is :" + CartLabel);
		enterXpath(MASTERSEARCH1, SOCustReference,"PICKING SEARCH");
		verify("//*[contains(text(),'No Records Found')]");
		childTest.pass("validation completed");

	}
	public void skipConsolidation(String SOCustReference) throws Exception
	{
		childTest = parantTest.createNode("Consolidation");

		clickXpath(CONSOLIDATIONGRID,"CONSOLIDATIONGRID");
		//verify(CONSOLIDATION);



		enterXpath (MASTERSEARCH1, SOCustReference,"MASTERSEARCH");
		clickXpath(OPTISEARCH,"OPTIMIZATIONSEARCH");
		Thread.sleep(1000);
		clickXpath(ORDERCLICK,"ORDERCLICK");
		Thread.sleep(1000);
		clickXpath(CONSOLIDATIONSKIP,"CONSOLIDATIONSKIP");

		Thread.sleep(1000);
		clickXpath(CONSOLIDATIONYES,"CONSOLIDATIONYES");
		Thread.sleep(2000);
		childTest.log(Status.INFO, "Consolidation Skipped:  ",MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture1()).build());
		verify(ALERT);

		clickXpath(ALERT,"Close");
	}

	public void dispatch2(String SOCustReference) throws Exception {
		deleteFile();

		childTest = parantTest.createNode(" Order Dispatch ");
		clickXpath(DISPATCHGRID,"DISPATCHGRID");
		verify("(//*[@id='Despatch_REQUESTNO'])[1]");
		enterXpath(DISPATCHSEARCH, SOCustReference,"DISPATCHSEARCH");
		clickXpath(Dispatch1,"Dispatch1");
		//verify(DISPATCHBUTTON);

		Thread.sleep(2000);
		clickXpath(DISPATCHBUTTON,"Dispatch1");
		//clickXpath(DISPATCHBTN,"DISPATCHBTN");		
		//verify("(//*[contains(.,' / ')])[23]","3");
		childTest.info("The order available in Dispatch Screen");

		enterXpath(CARRIER, "Testing","CARRIER");
		childTest.info("The Mode of carrier is :" + "Testing");
		enterXpath(CARRIERREF, "SmokeTest","CARRIERREF");
		childTest.info("The carrier reference is :" + "SmokeTest");
		//	enterXpath("//*[@id=\"Despatch_entervehicle\"]/div/div[1]", VEHICLENUMBER,"VEHICLE");


		//childTest.info("The Vehicle Number is :" + "TN38AA1234");

		///enterXpath(SCANLPN, CartLabel,"SCANLPN");
		if(NVLPNpicking!=null)
		{
			for(String apicking:NVLPNpicking)
			{Thread.sleep(1000);
			enterXpath(SCANLPN, apicking,"LPNFIELD");

			//verify("(//*[contains(.,'Successfully')])[16]");

			actionEnter();

			}
			NVLPNpicking=null;
		}
		if(Lpn!=null)
		{
			for(Map.Entry<String, String> Pick_Lpn:Lpn.entrySet()){
				Thread.sleep(500);
				enterXpath(SCANLPN, Pick_Lpn.getKey(),"LPNFIELD");	
				actionEnter();
			}
			Lpn=null;}
		System.out.println(VLPN);

		if(VLPN!=null)
		{Thread.sleep(500);
		enterXpath(SCANLPN,VLPN.get(0) ,"LPNFIELD");	
		actionEnter();	
		VLPN=null;
		}
		Thread.sleep(500);
		if(CUSLPN!=null)
		{
			enterXpath(SCANLPN, CUSLPN,"LPNFIELD");
			actionEnter();
			if(!picktype.contains("BOX"))
			{
				enterXpath("//*[@id=\"Despatch_enterQty1\"]",readXL("so",2, "QUANTITY"),"Quantity");
				actionEnter();
				CUSLPN=null;
			}
		}
		
		getStockNumber1(readPDF(), "packingslip");
		packingslip1 =  VLPN.get(0);
		System.out.println("packing slip is:" +packingslip1);

		//  deleteFile();



		Thread.sleep(4000);
		enterXpath(DISPATCHSEARCH, SOCustReference,"DISPATCHSEARCH");
		verify("//*[contains(text(),'No Records Found')]");


	}

	public void dispatchbox() throws Exception {

		childTest = parantTest.createNode(" Order Dispatch ");
		clickXpath(DISPATCHGRID,"DISPATCHGRID");
		verify("(//*[@id='Despatch_REQUESTNO'])[1]");
		enterXpath(DISPATCHSEARCH, SOCustReference,"DISPATCHSEARCH");
		clickXpath(Dispatch1,"Dispatchmenu");
		//verify(DISPATCHBUTTON);

		Thread.sleep(2000);
		clickXpath(DISPATCHBUTTON,"Dispatchbutton");
		//clickXpath(DISPATCHBTN,"DISPATCHBTN");		
		//verify("(//*[contains(.,' / ')])[23]","3");
		childTest.info("The order available in Dispatch Screen");

		enterXpath(CARRIER, "Testing","CARRIER");
		childTest.info("The Mode of carrier is :" + "Testing");
		enterXpath(CARRIERREF, "SmokeTest","CARRIERREF");
		childTest.info("The carrier reference is :" + "SmokeTest");
		//	enterXpath("//*[@id=\"Despatch_entervehicle\"]/div/div[1]", VEHICLENUMBER,"VEHICLE");


		//childTest.info("The Vehicle Number is :" + "TN38AA1234");

		///enterXpath(SCANLPN, CartLabel,"SCANLPN");

		enterXpath(SCANLPN, nvpartBox,"LPNFIELD");

		//verify("(//*[contains(.,'Successfully')])[16]");

		actionEnter();

		Thread.sleep(500);
		for(Map.Entry<String, String> Pick_Lpn:Lpn.entrySet()){
			Thread.sleep(500);
			enterXpath(SCANLPN, Pick_Lpn.getKey(),"LPNFIELD");	
			actionEnter();
		}
		if(VLPN!=null)
		{Thread.sleep(500);
		enterXpath(SCANLPN,VLPN.get(0) ,"LPNFIELD");	
		actionEnter();	
		}
		Thread.sleep(500);
		enterXpath(SCANLPN, nvnupartBox,"LPNFIELD");
		actionEnter();



		/*		File[] listOfFiles = folder.listFiles();

		for (int L = 0; L < listOfFiles.length; L++) 
		{
			System.out.println("Loop Checking");
			if (listOfFiles[L].isFile()) 
			{
				pdf = listOfFiles[L].getName(); 

			} 

		}
		System.out.println(pdf);*/
		/*	File file = new File(prop.getProperty("FOLDER")+pdffilename + "//" + pdf);
		FileInputStream fis = new FileInputStream(file); 
		PDDocument pdfDocument = PDDocument.load(fis);
		PDFTextStripper pdfTextStripper = new PDFTextStripper();
		String docText = pdfTextStripper.getText(pdfDocument);*/
		getStockNumber1(readPDF(), "packingslip");
		packingslip1 =  VLPN.get(0);
		System.out.println("packing slip is:" +packingslip1);

		//  deleteFile();



		Thread.sleep(4000);
		enterXpath(DISPATCHSEARCH, SOCustReference,"DISPATCHSEARCH");
		verify("//*[contains(text(),'No Records Found')]");

	}
	public String readPDF() throws  Exception
	{
		Thread.sleep(5000);

		File[] listOfFiles = folder.listFiles();

		for (int L = 0; L < listOfFiles.length; L++) 
		{
			System.out.println("Loop Checking");
			if (listOfFiles[L].isFile()) 
			{
				pdf = listOfFiles[L].getName(); 

			} 

		}
		System.out.println(pdf);
		String filepath = folder+ "//" + pdf;
		File file = new File(filepath);
		FileInputStream fis = new FileInputStream(file); 
		PDDocument pdfDocument = PDDocument.load(fis);
		PDFTextStripper pdfTextStripper = new PDFTextStripper();
		String doctext=pdfTextStripper.getText(pdfDocument);
		pdfDocument.close();
		fis.close();
		return  doctext;

	}



	public void vehicleCreation(String site) throws IOException, Exception {


		//parantTest = extent.createTest(test);    



		//parantTest = extent.createTest(test); 
		childTest = parantTest.createNode("Vehicle Creation");
		childTest.log(Status.PASS, "Vehicle creation initiated");
		VEHICLENUMBER = "V1"+getAlphaNumericString(5);
		
		JSONObject reqbodyb=new JSONObject();
		reqbodyb.put("siteMasterId", site);
		reqbodyb.put("workFlowTenantId", prop.getProperty("WORKFLOWID"));
		reqbodyb.put("vehNumber", VEHICLENUMBER);
		reqbodyb.put("vehicleInTime", "2021-06-29T07:15:41.068Z");
		
		reqbodyb.put("gateInType", GateInType);
		
		
		Response r1=null;

		

		 r1 = RestAssured.given()
					.contentType("application/json")
					.header("AppId","002")
					.header("timezone","Asia/Calcutta")
					.body(reqbodyb.toString())
					.post(prop.getProperty("VEHICLEAPI"));
		 System.out.println(r1.asString());



	}


	public void Email_trigger() {
		String to = prop.getProperty("MailID");

		// Sender's email ID needs to be mentioned
		String from = prop.getProperty("FromMailID");

		final String username = prop.getProperty("MailUserName");// change
		// accordingly
		final String password = prop.getProperty("MailPassword");// change
		// accordingly

		// Assuming you are sending email through relay.jangosmtp.net

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		// Get the Session object.
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			// Set Subject: header field
			message.setSubject("Visibility Automation report");

			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Now set the actual message
			messageBodyPart.setText("Kindly Find the Visibility automation test Report");

			// Create a multipar message
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			messageBodyPart = new MimeBodyPart();
			String filename = prop.getProperty("filename");
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);

			// Send the complete message parts
			message.setContent(multipart);

			// Send message
			Transport.send(message);

			System.out.println("Message sent successfully....");

		} catch (MessagingException e) {
			System.out.println("The Mail send functionality not working");
		}

	}



	public void uploadFIFO(String type) throws  Exception
	{

		CustRefList=new ArrayList<String>();


	//	String not=xpathGetText("(//*[contains(.,'of')])[17]");
		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
		clickXpath(CREATEREQUEST_BTN,"Order_CreateRequest_Button");
		List cr=driver.findElements(By.xpath(".//img[@class='buttonProgress']"));
		System.out.println(cr);
		while1:while(cr.size()!=0)
		{
			//System.out.println(cr.size());
			//System.out.println("waiting");
			//Thread.sleep(2000);
			try{
				cr=driver.findElements(By.xpath(".//img[@class='buttonProgress']"));
			}catch(Exception e){

				break while1;
			}


		}

	//	if(not.equals("0-0 of 0")){
			Thread.sleep(2000);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			verify(DOC_TYPE);
			actionClick("(//*[@class=' css-1hwfws3'])[1]");
			if(account1)
			{
				clickDownArrow();
			}
			Thread.sleep(2000);
			actionEnter();
			selectByVisibleText(DOC_TYPE, prop.getProperty("DOC_TYPE"));
			childTest.info("Document type is :  " + prop.getProperty("DOC_TYPE"));
			enterXpath(DOC_NO, prop.getProperty("DOC_NO"),"DOC_NO");
			clickXpath(DOC_SAVE,"DOC_SAVE");
			childTest.pass("Customer Details Saved Successfully");
		

		File file1 = new File(prop.getProperty("POPATH"));
		//verify("(//*[contains(.,'OTIS INDIA')])[16]");
		Thread.sleep(4000);
		if((!type.contains("ASSIGNORDER"))&&(!type.contains("RETURNORDER")))
		{
			enterXpath(PO_CHOOSE_FILE, file1.getAbsolutePath());
			verify(DOC_TYPE);


			clickXpath(UPLOAD_PO,"UPLOAD_PO");
			shortWait(3000);
		}else if(type.contains("ASSIGNORDER"))
		{
			clickXpath(Assign_Order,"Assign_Order");
			shortWait(2000);

			enterXpath(Assign_Order_Scearch_field, CustReference,"Assign_Order_Scearch_field");
			scrollDown();
			verify(ASSIGNORDERAPI,"1");
			childTest.log(Status.PASS," : The created order successfully listed in assign user screen",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
			clickXpath(Assign_Order_Check_Box,"Assign_Order_Check_Box");
			clickXpath(DOC_SAVE,"DOC_SAVE");

		}
		else if(type.contains("RETURNORDER"))
		{
			shortWait(2000);
			JavascriptExecutor js1 = (JavascriptExecutor) driver;

			js1.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
			childTest = parantTest.createNode("Finolex: Return Order Creation via Upload Option for Fifosuggested with CustLPN Picktype");    
			
			clickXpath(RETURN_BTN, "Return button");

			List cr1=driver.findElements(By.xpath(".//img[@class='buttonProgress']"));
			while(cr1.size()!=0)
			{
				Thread.sleep(1000);
				cr1=driver.findElements(By.xpath(".//img[@class='buttonProgress']"));
			}

			actionClick("(//*[@class=' css-1hwfws3'])[1]");
			if(account1)
			{
				clickDownArrow();
			}
			Thread.sleep(1000);
			actionEnter();

			File file2 = new File(System.getProperty("user.dir")+"//Automation_PO.csv");
			//verify("(//*[contains(.,'OTIS INDIA')])[16]");
			Thread.sleep(2000);

			enterXpath("//*[@id='PO_fileupload']", file2.getAbsolutePath());
			//verify(DOC_TYPE);


			clickXpath(UPLOAD_PO,"UPLOAD ReturnOrder FILE");
			shortWait(3000);



			childTest.log(Status.PASS,"Alertbox : ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
			
		}
		//clickXpath(UPLOAD_PO_AGAIN,"UPLOAD_PO_AGAIN");
		//clickXpath("//*[@id='root']/div/div/main/div/div/div/div[3]/div/div[3]/div/div/div/div/div/div/span");
		//closeBrowserTab();
		verify(INBOUNDDOCCLOSEBUTTON);
		childTest.log(Status.PASS,"Alertbox : ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		clickXpath(INBOUNDDOCCLOSEBUTTON,"  INBOUND_DOC_CLOSE_BUTTON  ");
		childTest.pass("PO Uploaded Successfully." + "Order Customer reference is :" +CustReference );

		if(type.contains("RETURNORDER"))
		{
			JavascriptExecutor js1 = (JavascriptExecutor) driver;

			js1.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
			verify(RETURN_COMPLETE_BTN);
			childTest.log(Status.PASS,"The uploaded Return Order details : ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
			clickXpath(RETURN_COMPLETE_BTN,"COMPLETE_BTN");
			shortWait(2000);

			childTest.pass("Return Order uploaded Successfully");
	shortWait(2000);
		}



	}

	public void dispatch1() throws Exception {

		childTest = parantTest.createNode(" Order Dispatch ");
		clickXpath(DISPATCHGRID,"DISPATCHGRID");
		verify("(//*[@id='Despatch_REQUESTNO'])[1]");
		enterXpath(DISPATCHSEARCH, SOCustReference,"DISPATCHSEARCH");
		Thread.sleep(3000);
		clickXpath(Dispatch1,"Dispatch1");
		//verify(DISPATCHBUTTON);
		Thread.sleep(3000);
		Thread.sleep(2000);
		clickXpath(DISPATCHBUTTON,"Dispatch1");
		//clickXpath(DISPATCHBTN,"DISPATCHBTN");		
		//verify("(//*[contains(.,' / ')])[23]","3");
		childTest.info("The order available in Dispatch Screen");

		enterXpath(CARRIER, "Testing","CARRIER");
		childTest.info("The Mode of carrier is :" + "Testing");
		enterXpath(CARRIERREF, "SmokeTest","CARRIERREF");
		childTest.info("The carrier reference is :" + "SmokeTest");
		//enterXpath(VEHICLE, "TN38AA1234","VEHICLE");
		//childTest.info("The Vehicle Number is :" + "TN38AA1234");

		enterXpath(SCANLPN, CartLabel,"SCANLPN");

		actionEnter();
		//		childTest.log(Status.PASS,
		//				MarkupHelper.createLabel(CustReference +" : "+ "Order dispatched Sucessfully", ExtentColor.GREEN));
		Thread.sleep(2000);
		//closeBrowserTab();
		deleteFile();
		Thread.sleep(2000);
		Thread.sleep(2000);
		Thread.sleep(2000);
		enterXpath(DISPATCHSEARCH, SOCustReference,"DISPATCHSEARCH");
		verify("//*[contains(text(),'No Records Found')]");
		System.out.println("Dispatch completed sucessfully ");
		System.out.println("*********************************************************");
		childTest.pass("validation completed");

	}


	public void consolidationbox() throws Exception {

		childTest = parantTest.createNode(" Order Consolidation ");
		ArrayList <String>lpns=new ArrayList<String>();
		clickXpath(CONSOLIDATIONGRID,"CONSOLIDATIONGRID");
		verify(CONSOLIDATION);
		enterXpath(MASTERSEARCH1, SOCustReference,"PICKING SEARCH");
		Thread.sleep(1000);
		clickXpath(OPTISEARCH,"OPTIMIZATIONSEARCH");
		Thread.sleep(1000);
		Thread.sleep(1000);
		clickXpath(ORDERCLICK,"ORDERCLICK");
		childTest.info("The order available in Consolidation screen");


		clickXpath(CONSOLIDATION,"CONSOLIDATION");
		Thread.sleep(2000);
		enterXpath(NOOFBOX, "1","NOOFBOX");
		clickXpath(ADDBOX,"ADDBOX");
		CartLabel = xpathGetText(LABEL);
		Thread.sleep(2000);
		clickXpath(SCANBOX,"SCANBOX");
		enterXpath(ENTERLPN, nvnupartBox,"ENTERLPN");

		clickXpath(SUBMIT,"SUBMIT");
		childTest.info("The NVNU Part Box Label Consolidated successfully");

		Thread.sleep(1000);
		
		enterXpath(ENTERLPN, nvpartBox,"ENTERLPN");

		clickXpath(SUBMIT,"SUBMIT");

		childTest.info("The NV Part Box Label Consolidated successfully");
		Thread.sleep(1000);

		enterXpath(ENTERLPN, vpartBox,"ENTERLPN");

		clickXpath(SUBMIT,"SUBMIT");


		enterXpath(WEIGHT, "10","WEIGHT");
		Select select1 = new Select(driver.findElement(By.xpath(UOM)));
		select1.selectByVisibleText("Ton");
		clickXpath(COMPLETE,"COMPLETE");
		Thread.sleep(2000);

		clickXpath(FINISH,"FINISH");
		Thread.sleep(2000);


		childTest.info("The order Consolidated successfully");
		enterXpath(MASTERSEARCH1, SOCustReference,"PICKING SEARCH");
		actionEnter();
		verify("//*[contains(text(),'No Records Found')]");
		System.out.println("consolidation completed successfully");
		childTest.pass("validation completed");

	}
	public void dispatchBoxandLpn() throws Exception {

		childTest = parantTest.createNode(" Order Dispatch ");
		clickXpath(DISPATCHGRID,"DISPATCHGRID");
		enterXpath(DISPATCHSEARCH, SOCustReference,"DISPATCHSEARCH");
		Thread.sleep(3000);
		Thread.sleep(2000);
		clickXpath(Dispatch1,"Dispatch1");

		Thread.sleep(3000);

		Thread.sleep(2000);
		clickXpath(DISPATCHBUTTON,"Dispatch1");


		enterXpath(CARRIER, "Testing","CARRIER");
		childTest.info("The Mode of carrier is :" + "Testing");
		enterXpath(CARRIERREF, "SmokeTest","CARRIERREF");
		childTest.info("The carrier reference is :" + "SmokeTest");
		//	enterXpath(ENTERLPN, CUSLPN,"ENTERLPN");
		enterXpath(SCANLPN, BOX,"LPNFIELD");
		actionEnter();
		enterXpath(SCANLPN, CUSLPN,"LPNFIELD");
		actionEnter();
		childTest.info("The NVNU part LPN dispatched successfully");
		enterXpath("//*[@id=\"Despatch_enterQty1\"]",prop.getProperty("BOXLABEL_NVNU_QTY1"),"Quantity");
		actionEnter();


		childTest.info("The NVNU part Box Label dispatched successfully");

		for (String b: box) {
			enterXpath(SCANLPN, b,"ENTERLPN");
			actionEnter();
		}
		childTest.info("The NV part LPN dispatched successfully");
		enterXpath(SCANLPN, BOX1,"ENTERLPN");
		actionEnter();
		childTest.info("The NV part Box Label dispatched successfully");

		for(Map.Entry<String, String> Pick_Lpn:Lpn.entrySet()){
			String  VLPN = Pick_Lpn.getKey();
			System.out.println(VLPN);
			enterXpath(SCANLPN, VLPN ,"ENTERLPN");
			Thread.sleep(2000);
			actionEnter();
			childTest.info("The V part LPN dispatched successfully");
			Thread.sleep(3000);
			enterXpath(DISPATCHSEARCH, SOCustReference,"DISPATCHSEARCH");
			verify("//*[contains(text(),'No Records Found')]");


			System.out.println("Dispatch completed sucessfully ");
			System.out.println("*********************************************************");
			clickIDLinkText("Inbound");
			clickIDLinkText(DASHBOARD);
			enterXpath(SEARCH_BOX, VehicleID);
			Thread.sleep(2000);
			childTest.pass("validation completed");

		}}

	public void pickgenerationFIFO() throws Exception {

		childTest = parantTest.createNode("Sales Order Generation for "+ picktype  + " PICKTYPE");
	
		verify(OUTBOUND);

		clickXpath("//b[text()='Outbound']","OUTBOUND");

		verify(PICKGENERATION);
		clickIDLinkText("Pick Generation");
		DB(Arrays.asList(binPickPart), binPickPartquantity, "beforepick");



		scrollUp();
		clickXpath("//span[contains(.,'Create request')]","Create request");
		clickXpath("(//*[@id='PickGen_Custname'])");
		if(account1)
		{
			clickDownArrow();
		}
		actionEnter();
		File file = new File(prop.getProperty("SOPATH"));
		//verify("(//*[contains(.,'OTIS INDIA')])[16]");
		Thread.sleep(2000);
		enterXpath(CHOOSEFILE,file.getAbsolutePath());
		Thread.sleep(3000);
		clickXpath(UPLOADSO,"UPLOADSO");
		Thread.sleep(3000);
		CommonFunctions.childTest.log(Status.PASS,"File upload status",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build());
		verify(ALERTBOXCANCEL);
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		if(SoType!=null)
		{
			if(SoType.equalsIgnoreCase("back"))
			{
				clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");

			}
		}
		DB(Arrays.asList(binPickPart), binPickPartquantity, "pickgen");


		verify(CLICKRECORD);

		enterXpath(MASTERSEARCH, SOCustReference,"MASTERSEARCH");
		clickXpath(PICKGENSEARCH, "search");

		//actionEnter();
		VON = xpathGetText(Picking_Von);
		Thread.sleep(2000);
		clickXpath(CLICKRECORD,"CLICKRECORD");
		CommonFunctions.childTest.log(Status.PASS,"Sales Order Uploaded succesfully for "+picktype+" Picktype. "+ "  Order customer reference is : " + SOCustReference,MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build());
		clickXpath(CLICKBACK,"CLICKBACK");



	}

	public void ScanPackingSlip() throws IOException, Exception {
		String not=xpathGetText("(//*[contains(.,'of')])[17]");
		JavascriptExecutor js = (JavascriptExecutor) driver;			
		js.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
		clickXpath(CREATEREQUEST_BTN,"Order_CreateRequest_Button");
		List cr=driver.findElements(By.xpath(PO_Upload));
		while(cr.size()!=0)
		{
			Thread.sleep(2000);
			cr=driver.findElements(By.xpath(PO_Upload));
		}
		if(not.equals("0-0 of 0")){
			Thread.sleep(2000);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			verify(DOC_TYPE);
			actionClick("(//*[@class=' css-1hwfws3'])[1]");
			Thread.sleep(2000);
			if(account1)
			{
				clickDownArrow();
			}
			Thread.sleep(2000);
			actionEnter();
			selectByVisibleText(DOC_TYPE, prop.getProperty("DOC_TYPE"));
			childTest.info("Document type is :  " + prop.getProperty("DOC_TYPE"));
			enterXpath(DOC_NO, prop.getProperty("DOC_NO"),"DOC_NO");
			childTest.info("Document Number  is :  " + prop.getProperty("DOC_NO"));
			clickXpath(DOC_SAVE,"DOC_SAVE");
			childTest.pass("Customer Details Saved Successfully");
		}	
		clickXpath(SCAN_PACKING_SLIP,"SCAN_PACKING_SLIP");
		enterXpath(PO_PACKING_SLIP,packingslip1,"Dock in searchbox");
		clickXpath(PO_PACKINGSLIPSCAN,"PO_PACKING_SLIP");
		childTest.pass("PO Uploaded Successfully." + "Order Customer reference is :" +CustReference );
		childTest.pass("Label Generated Successfully");

		verify(INBOUNDDOCCLOSEBUTTON);
		childTest.log(Status.PASS,"Alertbox : ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		clickXpath(INBOUNDDOCCLOSEBUTTON,"  INBOUND_DOC_CLOSE_BUTTON  ");

	}

	public void Label_Reprint(String OrderId,String type) throws Exception{
		childTest = parantTest.createNode("Label print for "+type);
		deleteFile();
		switch(type.toLowerCase().trim())
		{
		case "orderid":
			selectByVisibleText(Inbound_Label_dropdown, "SEARCH BY ORDER ID");
			break;
		case "customerreference":
			selectByVisibleText(Inbound_Label_dropdown, "SEARCH BY CUSTOMER REFERENCE");
			break;	
		case "lpn":
			selectByVisibleText(Inbound_Label_dropdown, "SEARCH BY LABEL BARCODE");
			break;
		case "boxlabel":
			selectByVisibleText(Inbound_Label_dropdown, "PRINT BOX LABEL");
			enterXpath("//input[@id='InboundLabel_quantityvalue']", OrderId);
			break;
		case "boxlabelnon":
			selectByVisibleText(Inbound_Label_dropdown, "PRINT BOX LABEL");
			enterXpath("//input[@id='InboundLabel_quantityvalue']", OrderId);
			clickXpath("//input[@type='checkbox']", "nonbinnable");
			break;
		case "boxlabelreprint":
			selectByVisibleText(Inbound_Label_dropdown, "PRINT BOX LABEL");
			clickXpath(Box_Label_Reprint,"Box_Label_Reprint");
			break;
		case "dispatchlabel":
			selectByVisibleText(OUTBOUND_LABEL_CHOOSE, "SEARCH BY SO NO/CUST REF");
			break;
		case "packingslip":
			selectByVisibleText(OUTBOUND_LABEL_CHOOSE, "SEARCH BY SO NO/CUST REF");
			break;
		case "dispatchlabelbyorderid":
			selectByVisibleText(OUTBOUND_LABEL_CHOOSE, "SEARCH BY VON");
			break;
		case "packingslipbyorderid":
			selectByVisibleText(OUTBOUND_LABEL_CHOOSE, "SEARCH BY VON");
			break;
		case "cartlabel":
			selectByVisibleText(OUTBOUND_LABEL_CHOOSE, "PRINT CART LABEL");
			enterXpath(CART_LABEL_QNTY,OrderId);
			break;
		case "palletlabel":
			selectByVisibleText(Inbound_Label_dropdown, "PRINT PALLET LABEL");
			enterXpath("//input[@id='noflabels']", OrderId);
			break;
		case "carrierlabelorder":
            selectByVisibleText(Outbound_Label_Choose2,"Carrier Label");
            clickXpath(Outbound_label_print,"Outbound_label_print");

            break;

		default:
			selectByVisibleText(OUTBOUND_LABEL_CHOOSE, "SEARCH BY SO NO/CUST REF");
			break;
		}
		if(!type.equalsIgnoreCase("cartlabel")&&!type.contains("boxlabel")&&!type.contains("palletlabel"))
		{
			clear1(Cust_Label_Reprint_Scearch);
			enterXpath(Cust_Label_Reprint_Scearch,OrderId,"Cust_Label_Reprint_Scearch");
			actionEnter();
		}
		switch(type)
		{
		case "packingslip":
			selectByVisibleText(Outbound_Label_Choose2,"Packing Slip");
			clickXpath(Outbound_label_print,"Outbound_label_print");
			break;
		case "dispatchlabel":
			selectByVisibleText(Outbound_Label_Choose2,"Dispatch label");
			clickXpath(Outbound_label_print,"Outbound_label_print");
			break;
		case "packingslipbyorderid":
			selectByVisibleText(Outbound_Label_Choose2,"Packing Slip");
			clickXpath(Outbound_label_print,"Outbound_label_print");
			break;
		case "dispatchlabelbyorderid":
			selectByVisibleText(Outbound_Label_Choose2,"Dispatch label");
			clickXpath(Outbound_label_print,"Outbound_label_print");
			break;
		case "boxlabelnon":
			clickXpath("//button[@id='InboundLabel_buttonSave']","print");
			break;
		case "boxlabel":
			clickXpath("//button[@id='InboundLabel_buttonSave']","print");
			break;
		case "boxlabelreprint":
			clickXpath(Box_Label_Print,"print");
			break;
		case "cartorderlevel":
			selectByVisibleText(Outbound_Label_Choose2,"Cart Label");
			clickXpath(Outbound_label_print,"Outbound_label_print");
			enterXpath("//input[@id='quantityvalue']", "1", "quantity");
			clickXpath(CART_LABEL_PRINT_BTN, "print");
			break;
		case "cartlabel":
			clickXpath(CART_LABEL_PRINT_BTN, "print");
			break;
		case "palletlabel":
			clickXpath("//*[text()='PRINT']","print");
			break;

		default:

			clickXpath(Customer_Label_Print1,"Customer_Label_Print1");
			break;	
		}
		Thread.sleep(5000);
		
		getStockNumber1(readPDF(),type);

		childTest.log(Status.INFO, "Printed Labels : "+type.toUpperCase());
		childTest.log(Status.INFO, MarkupHelper.createUnorderedList(VLPN));
	}
	public  void getStockNumber1(String docText,String type)
	{
		VLPN=new ArrayList<String>();

		String temp1="";
		String temp2="";
		int i =0;
		int length=0;
		switch(type)
		{

		case "boxlabel":
			temp1="BL23";
			length=15;
			break;
		case "boxlabelnon":
			temp1="BL23";
			length=15;
			break;
		case "packingslip":
			temp1="CONS";
			length=12;
			break;
		case "packingslipbyorderid":
			temp1="CONS";
			length=12;
			break;
		case "cartorderlevel":
			temp1="PK2";
			length=20;
			break;
		case "cartlabel":
			temp1="CL";
			length=12;
			break;
		case "palletlabel":
			temp1="PL23";
			length=15;
			break;

		default:
			temp1="LP23";
			length=13;
			break;
		}
		while(true)
		{
			if(docText.indexOf(temp1,i)!=-1)
			{  
				for(int j =docText.indexOf(temp1,i);j<docText.indexOf(temp1,i)+length;j++)
				{
					temp2+=docText.charAt(j);
				}
				VLPN.add(temp2);
				System.out.println("printed   :"+VLPN);
				temp2="";
				i=docText.indexOf(temp1,i)+length;
			}
			else
			{

				break;
			}  }

	}
	public  void Packingslip_Quantity(String docText,String SOCustReference){
		childTest.log(Status.INFO,MarkupHelper.createLabel("Packing slip validation for : "+partnumber,ExtentColor.ORANGE));

		System.out.println(docText);
		ArrayList<Integer> total=new ArrayList<Integer>();
		int TotalQuantity = 0;
		for(int i=0;i<=binPickPart.length;i++){
			if(i==binPickPart.length){
				int index= docText.lastIndexOf(SOCustReference);
				String Qty=docText.substring(index+14, index+17);
				TotalQuantity=Integer.parseInt(Qty.trim());
				System.out.println("Total ="+ TotalQuantity );
			}else{
				System.out.println(binPickPart[i]);
				int index= docText.lastIndexOf(SOCustReference);
				String Qty=docText.substring(index+11, index+13);
				int Quantity=Integer.parseInt(Qty.trim());
				System.out.println("part qty: "+Quantity);
				total.add(Quantity);
			}
		}
		int sum=0;
		
		for(int quantity:total){
			sum +=quantity;
		}
		int sum1=0;
		for (int totalquantity:binPickPartquantity){
			sum1 += totalquantity;
		}
		System.out.println(sum);
		if(sum==TotalQuantity && sum1==sum){
			childTest.log(Status.PASS, MarkupHelper.createLabel("Expected Quantity : "+ TotalQuantity + " actual Quantity : "+sum,ExtentColor.TEAL));
		}else{
			childTest.log(Status.WARNING, "The Expected Quantity : "+ TotalQuantity + "is Not-Equal to the actual Quantity "+sum);
		}
	}

	public ArrayList<String> pickingScreenFS(String type,String SOCustReference,List allpart,int []quantity) throws Exception {
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
						clickXpath(SHORTPIC,"Short Pick");
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
					/*if(picktype.startsWith("CUSTLPN"))
					{*/for(Map.Entry<String, String> NLPN:Lpnn.entrySet()){
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

					//}
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
					/*if(!(picktype.contains("CUSTLPN")))
					{
					enterXpath(VQUANTITY,""+partquantity,"NVNUQtn");
					}*/
					}
					//enterXpath(VQUANTITY, csv.readXL("SO",1,"QUANTITY"),"NVNUQtn");

if((picktype.contains("BOX")))
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
				;
					//partstockCheck(partnvnu,  "picking",Integer.parseInt(NVNUQtn));


				
					passedList.add(partName);
					count-=passedList.size();}}
				//NV				
				else if ((!failedList.contains(partName))&&(PartType.equals("NV"))){
					NVLPNpicking=new  ArrayList<String>();

					clickXpath("(//td[@data-label='PART TYPE'])["+i+"]");

					childTest.info("NV part picking started");
					if(type.contains("Short")){
						clickXpath("//*[@id='root']/div/div/main/div/div/div/div[3]/div/div/p/form/div[4]/div/b","Option");
						clickXpath(SHORTPIC,"Short Pick");
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
						clickXpath(SHORTPIC,"Short Pick");
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
	 	
	 		DB(allpart, new int[]{0,0,0}, "picking");
	 		orderStatus(SOCustReference, Module.PARTIALPICKING);
	 		}
		return pickedLPNS;
	}

	public  void binning(String type,String CustReference,List allpart,int []quantity) throws Exception
	{	childTest = parantTest.createNode("Binning "+type+" for order : "+CustReference);

	DB(allpart, quantity, "beforebin");
	String VBIN =Bin;
	String NVBIN=Bin;
	String NVNUBIN=Bin;
	if(type.startsWith("box"))
	{
		VBIN =vpartBox;
		NVBIN=nvpartBox;
		NVNUBIN=nvnupartBox;
	}
	clickXpath(BINNING_GRID, "Binning");

	ArrayList<String> failedList=new ArrayList<String>();
	ArrayList<String> passedList=new ArrayList<String>();

	int part =1;
	int count=allpart.size()+1;
	String parttype="";
	String partname="";
	String BINNINGQTY="";
	String[] qty;
	System.out.println("partnumbercount   "+allpart.size());
	for(int outer=0;outer<allpart.size();outer++)
	{			

		verify(PARTTYPE_CELL);
		clear1(SEARCH_BOX);
		enterXpath(SEARCH_BOX, CustReference,"BINNING SEARCH");
		scrollUp();
		Thread.sleep(1000);

		OrderId = xpathGetText(ORDER);
		Thread.sleep(1000);
		try{
			int i1;
			System.out.println("failedList "+failedList);

			check:for ( i1 = 1; i1 <=count; i1++) {

				partname = xpathGetText("(//td[@id='BinnerDashboard_PartNumber'])[" + i1 + "]");
				if (failedList.contains(partname))
				{
					System.out.println(failedList.contains(partname));
				}
				else
				{
					System.out.println("break at"+partname);
					break check;


				}
			}
			Thread.sleep(1000);

			parttype=xpathGetText("(//td[@id='BinnerDashboard_PartType'])["+i1+"]");
			Thread.sleep(1000);


			int partquantity=quantity[allpart.indexOf(partname)];
			System.out.println("partquantity "+partquantity);
			BINNINGQTY = xpathGetText(BINNINGVQTN);
			qty=BINNINGQTY.split("/");
			childTest.info("The order" + partname+":"+parttype +" : part available in Binning screen");
			// 
			if ((!failedList.contains(partname))&&(!passedList.contains(partname))&&(parttype.equalsIgnoreCase("V"))) {
				VLPN=new  ArrayList<String>();

				//partStockCheck(partname, site, account,"kln",partquantity);
				clickXpath( "(//img[@alt='infoicon'])[" + i1 + "]","Info");
				if(type.contains("additionallabel"))
				{ 
					for(int i=1;i<=2;i++)
					{
						String LPNV = xpathGetText("(//TD[@id='BinnerDashboard_Lpn'])["+i+"]");
						VLPN.add(LPNV);
					}
				}
				else
				{
					if(type.contains("binbox"))
					{
						String LPNV = xpathGetText("(//TD[@id='BinnerDashboard_BoxLabel'])[1]");

						VLPN.add(LPNV);

					}
					else if(type.contains("binpallet"))
					{
						String LPNV = xpathGetText("(//TD[@id='BinnerDashboard_BoxLabel'])[2]");

						VLPN.add(LPNV);

					}
					else
					{
						String LPNV = xpathGetText(LPN_CELL_1);
						VLPN.add(LPNV);
					}}
				clickXpath(INFO_CLOSEBTN,"INFO_CLOSEBTN");
				Thread.sleep(1000);
				//verify(SCANBTN);
				clickXpath(SCAN_BTN,"SCAN_BTN");
				//throw new Exception("my own empire");
				if(type.startsWith("box"))
				{
					clickXpath(SCANTOBOX_RADIOBTN,"SCANTOBOX_RADIOBTN");
				}
				clickXpath(SCANTOBIN_SUMBIT,"SCANTOBIN_SUMBIT");
				childTest.pass("V parts"
						+ " binning started through Scan to Bin option");
				verify(BINPLEASE);

				enterXpath(BIN_TEXT_BOX,VBIN,"BIN_TEXT_BOX");
				clickXpath(BINNING_SUBMIT_BTN,"BINNING_SUBMIT_BTN");
				verify(BINSUCCESS,"Successfully");
				int qty1= partquantity/2;
				int qty2=partquantity-qty1;
				enterXpath(BIN_TEXT_BOX, VLPN.get(0),"LPN");

				clickXpath(BINNING_SUBMIT_BTN,"BINNING_SUBMIT_BTN");

				if(type.contains("additionallabel"))
				{					verify(BINNINGENTER,"Enter");

				enterXpath(VPART_QTY_TXT,qty1+"","VPART_QTY");
				actionEnter();
				verify(BINSUCCESS,"Successfully");
				LPNStatus(VLPN.get(0),Module.BINNING);

				enterXpath(BIN_TEXT_BOX, VLPN.get(1),"LPN");

				clickXpath(BINNING_SUBMIT_BTN,"BINNING_SUBMIT_BTN");
				enterXpath(VPART_QTY_TXT,qty2+"","VPART_QTY");
				actionEnter();
				verify(BINSUCCESS,"Successfully");
				LPNStatus(VLPN.get(1),Module.BINNING);

				}
				else if(!(type.equalsIgnoreCase("binpallet")||type.equalsIgnoreCase("binbox")))
				{					verify(BINNINGENTER,"Enter");

				enterXpath(VPART_QTY_TXT, partquantity+"","VPART_QTY");

				actionEnter();
				verify(BINSUCCESS,"Successfully");
				LPNStatus(VLPN.get(0),Module.BINNING);

				}

				childTest.log(Status.PASS,"Binning Completed for V part,Alert Box : ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
				clickXpath(BINNING_CLOSE_BTN,"BINNING_CLOSE_BTN");

				//	partStockCheck(partname,  "binning",partquantity);

				childTest.pass("Vpart Binned Successfully");

			


			}


			else if ((!failedList.contains(partname))&&(!passedList.contains(partname))&&(parttype.equalsIgnoreCase("NV"))&&(!parttype.equalsIgnoreCase("NVNU"))&&(!parttype.equalsIgnoreCase("V"))) {
				//partDynamic.put("NV", false);

				NVLPN=new  ArrayList<String>();


				//partStockCheck(partname,  "kln",partquantity);


				clickXpath( "(//img[@alt='infoicon'])[" + i1 + "]","Info");

				String NVLPN1 ="";
				int temp=1;
				if((type.contains("binpallet")||type.contains("binbox"))&&(!picktype.contains("CUSTLPNBOX")))
				{
					partquantity=1;
				}
				for(int i=1;i<=partquantity;i++)
				{
					if(type.contains("binbox"))
					{
						NVLPN1 = xpathGetText("(//TD[@id='BinnerDashboard_BoxLabel'])["+temp+"]");

						temp+=2;

					}
					else if(type.contains("binpallet"))
					{
						NVLPN1 = xpathGetText("(//TD[@id='BinnerDashboard_BoxLabel'])[2]");

					}
					else
					{
						NVLPN1 = xpathGetText("(//TD[@id='BinnerDashboard_Lpn'])["+i+"]");
					
					}
					NVLPN.add(NVLPN1);
				}


				clickXpath(INFO_CLOSEBTN,"INFO_CLOSEBTN");
				verify(SCANBTN);
				clickXpath(SCANBTN,"SCAN_BTN");
				if(type.equalsIgnoreCase("box"))
				{
					clickXpath(SCANTOBOX_RADIOBTN,"SCANTOBOX_RADIOBTN");
				}

				clickXpath(SCANTOBIN_SUMBIT,"SCANTOBIN_SUMBIT");

				verify(BINPLEASE,"Please");
				enterXpath(BIN_TEXT_BOX, NVBIN);
				clickXpath(BINNING_SUBMIT_BTN,"BINNING_SUBMIT_BTN");
				verify(BINSUCCESS,"Successfully");

				int i=0;
				for(String a:NVLPN)
				{


					verify(BINSUCCESS,"Successfully");

					enterXpath(BIN_TEXT_BOX, a,"BIN_TEXT_BOX");

					clickXpath(BINNING_SUBMIT_BTN,"BINNING_SUBMIT_BTN");
					i++;
					if(picktype.equalsIgnoreCase("SERIALNUMBER"))
					{
						WebElement m= driver.findElement(By.xpath ("(//*[name()='svg'][contains(@class, ' arrow-icon_bin_long')])"));
						// Action class to move and click element
						Actions a1 = new Actions(driver);

						Thread.sleep(2000);
						a1.moveToElement(m).click();

						a1.build().perform();
					}

				}
				if(!(type.contains("binbox")||type.equalsIgnoreCase("binpallet")))
				{
					verify(BINSUCCESS,"Successfully");
				}
				childTest.log(Status.PASS,"Binning Completed for NV part,Alert Box : ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
				driver.navigate().refresh();
				//scrollUp();
				//clickXpath(BINNING_CLOSE_BTN,"BINNING_CLOSE_BTN");
				////partstockCheck(partnv,  "binning",1);
				
				childTest.pass("NV parts Binned Successfully");



			}	
			else if ((!failedList.contains(partname))&&(!passedList.contains(partname))&&(parttype.equalsIgnoreCase("NVNU"))&&(!parttype.equalsIgnoreCase("V"))) {
				Lpn = new HashMap<String ,String>();


				//partStockCheck(partname,  "ii",partquantity);

				clickXpath( "(//img[@alt='infoicon'])[" + i1 + "]","Info");
				//String LPNNVNU = xpathGetText(LPN_CELL_1);
				WebElement mytable = driver.findElement(By.xpath("/html/body/div[2]/div[2]/p/div[2]/table"));
				List < WebElement > rows_table = mytable.findElements(By.tagName("tr"));
				int rows_count = rows_table.size();
				if(type.contains("binbox"))
				{
					LPNNVNU = xpathGetText("(//TD[@id='BinnerDashboard_BoxLabel'])[1]");
					Lpn.put(LPNNVNU, ""+partquantity);

				}
				else if(type.contains("binpallet"))
				{
					LPNNVNU = xpathGetText("(//TD[@id='BinnerDashboard_BoxLabel'])[2]");
					Lpn.put(LPNNVNU, ""+partquantity);

				}
				else
				{
					for (int row = 1; row <= rows_count; row++) {
						LPN=xpathGetText("(//TD[@data-label='LPN'])["+row+"]");
						String QUANTITY=xpathGetText("(//TD[@id='BinnerDashboard_Qty'])["+row+"]");
						Lpn.put(LPN, QUANTITY);
						System.out.println(Lpn);

					}
					LPNNVNU = xpathGetText("(//TD[@data-label='LPN'])[1]");

				}
				clickXpath(INFO_CLOSEBTN,"INFO_CLOSEBTN");
				Thread.sleep(1000);				
				clickXpath("(.//td[contains(@class,'tablerow')][5])["+i1+"]","PARTTYPE_CELL");
				if(type.equalsIgnoreCase("box"))
				{
					clickXpath(SCANTOBOX_RADIOBTN,"SCANTOBOX_RADIOBTN");
				}					Thread.sleep(2000);
				clickXpath(SCANTOBIN_SUMBIT,"SCANTOBIN_SUMBIT");
				childTest.pass("NVNU parts binning started through Scan to Bin option");
				//verify(BINPLEASE,"Please");
				enterXpath(BIN_TEXT_BOX,NVNUBIN,"BIN_TEXT_BOX");
				clickXpath(BINNING_SUBMIT_BTN,"BINNING_SUBMIT_BTN");
				for(Map.Entry<String, String> NVNULPNS:Lpn.entrySet()){
					String  NVNUVLPN = NVNULPNS.getKey();
					String  NVNUQUANTITY = NVNULPNS.getValue();
					verify(BINSUCCESS,"Successfully");

				enterXpath(BIN_TEXT_BOX, NVNUVLPN,"BIN_TEXT_BOX");
				if(type.equalsIgnoreCase("box"))
				{
					clickXpath(BINNING_SUBMIT_BTN,"BINNING_SUBMIT_BTN");

				}
				//clickXpath(BINNING_SUBMIT_BTN,"BINNING_SUBMIT_BTN");
				//verify(BINNINGENTER);
				//	LPNStatus(LPNNVNU,Module.BINNING);
			//	enterXpath(NVNUPARTQTY,partquantity+"","NVNUPARTQTY");
				enterXpath(NVNUPARTQTY,NVNUQUANTITY+"","NVNUPARTQTY");
				clickXpath(BINNING_SUBMIT_BTN,"BINNING_SUBMIT_BTN");
			//	verify("(//*[contains(.,'Successfully')])[14]");
				}
				//	verify(BINSUCCESS,"Successfully");
				childTest.log(Status.PASS,"Binning Completed for NVNU part,Alert Box : ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
				Thread.sleep(2000);


				//partStockCheck(partname,  "binning",partquantity);

				childTest.pass("NVNU parts Binned Successfully");
				

				//wait = new WebDriverWait(driver, 20);
				//wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(fluentWait(PARTTYPE_CELL))));
			}

			if(!type.equalsIgnoreCase("box"))
			{	

				passedList.add(partname);
				count-=1;
			}
			else
			{
				count++;
				failedList.add(partname);


			}

		}
		catch (Exception e)
		{				System.out.println(e);

		count++;
		failedList.add(partname);
		System.out.println(partname);
		e.printStackTrace();
		childTest.log(Status.FAIL,"Binning failed for part type : "+parttype,MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build());
		childTest.info(e.fillInStackTrace());

		driver.navigate().refresh();
		clickXpath(BINNING_GRID, "Binning");

		continue;


		}
	}	Thread.sleep(2000);
	clear1(SEARCH_BOX);

	enterXpath(SEARCH_BOX, CustReference,"SEARCH_BOX");
	childTest.log(Status.PASS,"Validation Completed in Binning Screen : ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
	clear1(SEARCH_BOX);
	childTest.pass("Verification completed for INBOUND BINNING");
	if(!type.startsWith("box"))
	{

		DB(allpart, quantity, "binning");
		orderStatus(CustReference, Module.BINNING);

	}
	}

	public  void gotoBinningScan() throws Exception
	{
		clickIDLinkText(DASHBOARD);

		clickXpath(BINNING_GRID, "Binning");
		verify(PARTTYPE_CELL);


		clickXpath(SCAN_BTN,"SCAN_BTN");
		clickXpath(SCANTOBIN_SUMBIT,"SCANTOBIN_SUMBIT");
		verify(BINPLEASE);

		enterXpath(BIN_TEXT_BOX,Bin,"BIN_TEXT_BOX");
		clickXpath(BINNING_SUBMIT_BTN,"BINNING_SUBMIT_BTN");
		verify(BINSUCCESS,"Successfully");
	}
	public  void gotoPickingScan() throws Exception
	{
		clickIDLinkText(DASHBOARD);

		clickXpath(BINNING_GRID, "Binning");
		verify(PARTTYPE_CELL);


		clickXpath(SCAN_BTN,"SCAN_BTN");
		clickXpath(SCANTOBIN_SUMBIT,"SCANTOBIN_SUMBIT");
		verify(BINPLEASE);

		enterXpath(BIN_TEXT_BOX,Bin,"BIN_TEXT_BOX");
		clickXpath(BINNING_SUBMIT_BTN,"BINNING_SUBMIT_BTN");
		verify(BINSUCCESS,"Successfully");
	}
	public void ApproveRequest(String choose,String CustReference) throws Exception
	{
		if(choose.contains("Hold")||choose.contains("Accept")||choose.contains("Reject"))
		{
			clickXpath(Temp_Sales_Order, "temp");
		}else if(choose.contains("shortbin")||choose.contains("excess"))
		{
			clickXpath(Binning_Short_excess,"Binning short/excess");
		}else if(choose.contains("pod"))
		{
			clickXpath(POD_Discrepency,"POD Discrepency");
		}else if(choose.contains("shortpick"))
		{
			clickXpath(SHORTPIC,"Short");
		}else if(choose.contains("random"))
		{
			clickXpath(RAND,"Random");
		}	
		else if (choose.contains("Bin"))
		{
			clickXpath(Bin_Transfer_Approval,"Bin Transfer Approval");
		}
		verify(TEMPCHECK);

		enterXpath(TEMPSEARCH, CustReference);
		actionEnter();

		String check=choose.endsWith("all")?CHECKBOXALL:TEMPCHECK;
		clickXpath(check, "check");
		// select approval option
		if(choose.contains("Hold Part"))
		{
			selectByVisibleValue(TEMPCHOOSE, "RCM12002");
		}
		else if(choose.contains("Hold Order")||choose.contains("Accept")||choose.contains("Reject"))
		{
			selectByVisibleText(TEMPCHOOSE, choose);
		}
		else if(choose.contains("approve")){
			selectByVisibleText(APPROVALSELECT,"Approve");
		}
		else if(choose.contains("decline")){
			selectByVisibleText(APPROVALSELECT,"Decline");
		}
		else if(choose.contains("approve")){
			selectByVisibleText(APPROVALSELECT,"Approve");
		}
		clickXpath(TEMPSAVE,"buttonSave");
		verify(ALERTBOXCANCEL);
		verify(TEMPSUCCESSMSG,"Successfully");

		clickXpath(ALERTBOXCANCEL);
		Thread.sleep(1000);
//		enterXpath("//*[@id='standard-search']",SOCustReference);
//		actionEnter();
//		Thread.sleep(1000);
//		childTest.log(Status.PASS,"Validation in Approval Screen: ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
//		verify("//*[contains(text(),'No Records Found')]");
//		childTest.pass("validation completed");

	}
	public ArrayList<String> cons(String SOCustReference,String type,ArrayList<String> LPNs) throws Exception
	{

		ArrayList<String> carton=new ArrayList<String>();
		childTest = parantTest.createNode(" Order Consolidation ");
		ArrayList <String>lpns=new ArrayList<String>();
		clickXpath(CONSOLIDATIONGRID,"CONSOLIDATIONGRID");
		verify(CONSOLIDATION);
		enterXpath(MASTERSEARCH1, SOCustReference,"PICKING SEARCH");
		Thread.sleep(1000);
		clickXpath(OPTISEARCH,"OPTIMIZATIONSEARCH");
		Thread.sleep(1000);
		Thread.sleep(1000);
		clickXpath(ORDERCLICK,"ORDERCLICK");
		childTest.info("The order available in Consolidation screen");


		clickXpath(CONSOLIDATION,"CONSOLIDATION");
		int count=type.contains("MULTIBOXNVNU")?Integer.parseInt(quantity[(Arrays.asList(binPickPart)).indexOf(partnvnu)]):LPNs.size();

		Thread.sleep(2000);
		String NoOfBoxes=type.contains("MULTIBOX")?""+count:"1";
		enterXpath(NOOFBOX, NoOfBoxes,"NOOFBOX");
		clickXpath(ADDBOX,"ADDBOX");
		int orderquantity=0;
		int i=1;
		
		for(int j=0;j<count;j++)
		{			

			if(type.contains("MULTIBOX")||i==1){
				String label="(//td[@id='Consolidate_PackageRefId'])["+i+"]";
				CartLabel = xpathGetText(label);
				carton.add(CartLabel);
				Thread.sleep(1000);
				String scanbox="(//button[@id='Consolidate_ScanBox'])["+i+"]";
				clickXpath(scanbox,"SCANBOX");
				Thread.sleep(1000);
				clickXpath("//input[@type='checkbox']", "each");
				}
			
			
			enterXpath(ENTERLPN, LPNs.get(j),"ENTERLPN");
			clickXpath(SUBMIT,"SUBMIT");
			orderquantity++;
             
			if((LPNs.get(j).equalsIgnoreCase(CUSLPN)||LPNs.get(j).equalsIgnoreCase(partnvnu)||type.contains("MULTIBOXNVNU"))&&(!picktype.contains("BOX")))
			{String NVNUQTY=type.contains("MULTIBOXNVNU")?"1":quantity[(Arrays.asList(binPickPart)).indexOf(partnvnu)];
			enterXpath(NUNVQNTY,NVNUQTY,"NVNUQNTY");
			//Thread.sleep(1000);

			clickXpath(SUBMIT,"SUBMIT");
			}

			Thread.sleep(1000);
			if(type.contains("MULTIBOX")||(i==LPNs.size()))
			{
				enterXpath(WEIGHT, "10","WEIGHT");
				Thread.sleep(1000);
				Select select1 = new Select(driver.findElement(By.xpath(UOM)));
				Thread.sleep(1000);
				select1.selectByVisibleText("TON");
				Thread.sleep(1000);
				clickXpath(COMPLETE,"COMPLETE");
				Thread.sleep(2000);
			}
			i++;}



		/*if(Arrays.asList(binPickPart).contains(partnvnu)&&((!type.contains("MULTIBOXNVNU"))&&(!type.contains("partial"))&&!picktype.startsWith("CUSTLPN")))
		{
			orderquantity=orderquantity+(Integer.parseInt(quantity[(Arrays.asList(binPickPart)).indexOf(partnvnu)])-1);
			verify("//b[contains(.,'PENDING SCAN VS TOTAL PARTS')]", "0 / "+orderquantity);

		}*/
		//System.out.println(xpathGetText("//b[contains(.,'PENDING SCAN VS TOTAL PARTS')]")+"  "+orderquantity);
		childTest.log(Status.INFO,"The LPNs are scanned to carton ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );

		clickXpath(FINISH,"FINISH");
		Thread.sleep(2000);
		if(type.contains("partial"))
		{
			clickXpath("//*[@id='buttonSave'][1]");

		}

		childTest.info("The order Consolidated successfully");
		/*enterXpath(MASTERSEARCH1, SOCustReference,"PICKING SEARCH");
		Thread.sleep(1000);
		clickXpath(OPTISEARCH,"OPTIMIZATIONSEARCH");
		verify("//*[contains(text(),'No Records Found')]");
		*/
		System.out.println("consolidation completed successfully");
		childTest.pass("validation completed");
		orderStatus(SOCustReference, Module.CONSOLIDATION);

		return carton;

	}
	public ArrayList<String> cons(String type,String SOCustReference,String[][] Lpns,String[]quantity,int boxcount) throws Exception
	{

		ArrayList<String> carton=new ArrayList<String>();
		childTest = parantTest.createNode(" Order Consolidation ");
		ArrayList <String>lpns=new ArrayList<String>();
		clickXpath(CONSOLIDATIONGRID,"CONSOLIDATIONGRID");
		verify(CONSOLIDATION);
		enterXpath(MASTERSEARCH1, SOCustReference,"PICKING SEARCH");
		Thread.sleep(1000);
		clickXpath(OPTISEARCH,"OPTIMIZATIONSEARCH");
		Thread.sleep(1000);
		Thread.sleep(1000);
		clickXpath(ORDERCLICK,"ORDERCLICK");
		childTest.info("The order available in Consolidation screen");


		clickXpath(CONSOLIDATION,"CONSOLIDATION");

		Thread.sleep(2000);
		enterXpath(NOOFBOX, ""+boxcount,"NOOFBOX");
		clickXpath(ADDBOX,"ADDBOX");
		int orderquantity=0;
		int i=1;
		int lpncount=0;
		for(int j=0;j<boxcount;j++)
		{			

				String label="(//td[@id='Consolidate_PackageRefId'])["+(j+1)+"]";
				CartLabel = xpathGetText(label);
				carton.add(CartLabel);
				Thread.sleep(1000);
				String scanbox="(//button[@id='Consolidate_ScanBox'])["+(j+1)+"]";
				clickXpath(scanbox,"SCANBOX");
				Thread.sleep(1000);
				
				clickXpath("//input[@type='checkbox']", "each");
				
			
			for(String lpnlist:Lpns[j])
			{
				Thread.sleep(1000);
			enterXpath(ENTERLPN, lpnlist,"ENTERLPN");
			clickXpath(SUBMIT,"SUBMIT");
			if((!quantity[lpncount].equalsIgnoreCase(""))||quantity!=null)
			{			enterXpath(NUNVQNTY,quantity[lpncount],"NVNUQNTY");

			clickXpath(SUBMIT,"SUBMIT");

			}
			lpncount++;

			}
			orderquantity++;
             
			

		
				enterXpath(WEIGHT, "10","WEIGHT");
				Select select1 = new Select(driver.findElement(By.xpath(UOM)));
				select1.selectByVisibleText("Ton");
				clickXpath(COMPLETE,"COMPLETE");
				Thread.sleep(2000);
			}



	
		childTest.log(Status.INFO,"The LPNs are scanned to carton ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );

		clickXpath(FINISH,"FINISH");
		Thread.sleep(2000);
		if(type.contains("partial"))
		{
			clickXpath("//*[@id='buttonSave'][1]");

		}

		childTest.info("The order Consolidated successfully");
		
		System.out.println("consolidation completed successfully");
		childTest.pass("validation completed");
		orderStatus(SOCustReference, Module.CONSOLIDATION);

		return carton;

	}

	public void disp(String SOCustReference ,String type,ArrayList<String> label) throws Exception
	{
		deleteFile();

		childTest = parantTest.createNode(" Order Dispatch ");
scrollUp();
clickXpath(DISPATCHGRID,"DISPATCHGRID");
		verify("(//*[@id='Despatch_REQUESTNO'])[1]");
		enterXpath(DISPATCHSEARCH, SOCustReference,"DISPATCHSEARCH");
		clickXpath(Dispatch1,"options");
		//verify(DISPATCHBUTTON);

		Thread.sleep(2000);
		clickXpath(DISPATCHBUTTON,"Dispatch1");
		//clickXpath(DISPATCHBTN,"DISPATCHBTN");		
		//verify("(//*[contains(.,' / ')])[23]","3");
		childTest.info("The order available in Dispatch Screen");
		Thread.sleep(2000);
		enterXpath(CARRIER, "Testing","CARRIER");
		childTest.info("The Mode of carrier is :" + "Testing");
		Thread.sleep(500);
		enterXpath(CARRIERREF, "SmokeTest","CARRIERREF");
		if(type.equalsIgnoreCase("vehicle"))
		{
		actionSendKeys(VEHICLE, VEHICLENUMBER);
		}

		childTest.info("The carrier reference is :" + "SmokeTest");
		//	enterXpath("//*[@id=\"Despatch_entervehicle\"]/div/div[1]", VEHICLENUMBER,"VEHICLE");


		//childTest.info("The Vehicle Number is :" + "TN38AA1234");


		for(String lpn:label)
		{Thread.sleep(1000);
		enterXpath(SCANLPN, ""+lpn,"LPNFIELD");
		actionEnter();
		if(CUSLPN!=null){
		if((!(picktype.contains("BOX")))&&(lpn.equalsIgnoreCase(CUSLPN.trim())))
		{
			enterXpath("//*[@id=\"Despatch_enterQty1\"]",quantity[(Arrays.asList(binPickPart)).indexOf(partnvnu)],"Quantity");
			actionEnter();
			CUSLPN=null;
		}
		}
		}
		if((!type.contains("partial"))&&(!type.contains("not")))
		{
		getStockNumber1(readPDF(), "packingslip");
		packingslip1 =  VLPN.get(0);
		System.out.println("packing slip is:" +packingslip1);
		childTest.info("packing slip is:" +packingslip1);

		//  deleteFile();
		Thread.sleep(4000);
		orderStatus(SOCustReference, Module.DISPATCH);

		enterXpath(DISPATCHSEARCH, SOCustReference,"DISPATCHSEARCH");
		verify("//*[contains(text(),'No Records Found')]");	
		childTest.log(Status.INFO,"Dispatched order not listed in dispatch screen ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		}
		else if(type.contains("partial"))
		{
			clickXpath("(//*[@type='button'])[4]", "finish");
		}
		else 
		{
			
		}
	}

	public void GRNProcess(String CustReference) throws Exception
	{
		childTest = parantTest.createNode("GRN");

		clickIDLinkText("GRN");

		enterXpath(SEARCH_BOX, CustReference,"binnerAssignment SEARCH");

		clickXpath("//button[@id='Vpart_submit']","GRNSEARCH");
		clickXpath("//img[@class='Icon_Edit_Table']","options");
		clickXpath("//button[@id='Despatch_createTransport']","process");
		clickXpath(".//*[contains(text(),'YES')]","YES");
	}
	public void cancellation(String CustReference,String part,String LPN,int quantity,int level) throws Exception
	{
		childTest = parantTest.createNode("Cancellation level "+level);

		selectByvalue(CANCEL_LEVEL, level);
		//verify("(//*[contains(.,'Search')])[17]");
		clickXpath(CLEAR, "CLEAR");
		if(level!=3)
		{
		enterXpath( CANCEL_SOSEARCH, CustReference,"CANCEL_SOSEARCH");
		}
		//verify("(//*[contains(.,'INB')])[19]");
		if(level==2)
		{
			enterXpath( CANCEL_PARTSEARCH, part,"CANCEL_PARTSEARCH");
			}
		else if(level==3)
		{
		enterXpath( CANCEL_LPNSEARCH,LPN,"CANCEL_LPNSEARCH");
		}
		else if(level==4)
		{
			enterXpath( CANCEL_PARTSEARCH, part,"CANCEL_PARTSEARCH");

		}
		clickXpath("(//*[@type='button'])[3]","button");

		if(level!=4)
		{
			clickXpath( CANCEL_PART_CHECKBOX,"CANCEL_PART_CHECKBOX");
			}
		else{
			enterXpath( CANCEL_QTY_ENTER, ""+quantity,"CANCEL_QTY_ENTER");

		}
		selectByVisibleText(CANCEL_REASON, "Customer Cancellation");
		clickXpath( CANCEL_BTN,"CANCEL_BTN");
		verify("(//*[contains(.,'Successfully')])[20]","Successfully");
		clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
		
	}
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
					Outer:for(int j=1;j<=ROW_Count.size();j++){
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
							break Outer;

						}	
					}
				}

				else  {
					String address1="1, LUZ CHURCH ROAD, CHENNAI, TAMIL NADU, INDIA, 600004";
					verify("//tr[1][@id='Optimization_Table']/td[6]",address1);
					clickXpath("//input[@id='Optimization_checkbox0']","ORDERCLICK");
					clickXpath(CONSOLIDATIONSKIP,"CONSOLIDATIONSKIP");
					clickXpath(CONSOLIDATIONYES,"CONSOLIDATIONYES");
					Thread.sleep(2000);

					clickXpath(ALERT,"Close");
					deleteFile();
				}

		
	}

	
}


