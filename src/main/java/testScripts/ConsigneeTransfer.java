package testScripts;

import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class ConsigneeTransfer extends ProjectSpecificFunctions{
	ArrayList<String> DBLPN=new ArrayList<String>();
	ArrayList<Integer> DBQTY=new ArrayList<Integer>();
	int DBQTY1=0;
	@Test(priority=32,alwaysRun=true)
	public void scan_to_box1() throws  Exception
    {	     parantTest = extent.createTest("Consignee Stock Transfer from general to consignee");   

		loginAsUser(Username);
		vehicleCreation(site);
		account1=true;
		account=prop.getProperty("ACCOUNTID1");
		//Consignee="CATSD000172";
	
		partv="BATCHVHV";
		partnv="BATCHNVLB";
		partnvnu="BATCHNVNUOV";
		Bin="back";
		picktype="CUSTLPNBATCH";
		 part=new String[]{partv,partnv,partnvnu,partnv,partnv};
			quantity=new String[]{"3","1","3","1","1"};
			binPickPart=new String[]{partv,partnv,partnvnu};
			binPickPartquantity=new int[]{3,3,3};

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
		binning("",CustReference,Arrays.asList(binPickPart), binPickPartquantity);
		extent.flush();
		clickIDLinkText("Misc");
		clickIDLinkText("Consignee Stock Change");
		System.out.println(CTR(partv, 3,"V"));
	System.out.println(CTR(partnv, 3,""));
		System.out.println(CTR(partnvnu, 3,"nvnu"));
		
		clickXpath("//b[contains(text(),'Admin')]", "Admin");
		clickXpath("//b[contains(text(),'Storage')]", "Storage");
		clickXpath("//*[contains(text(),'SGL.Bin Transfer')]", "Single bin transfer");
		driver.navigate().refresh();
		Thread.sleep(1000);
		actionClick("//b[contains(text(),'Transfer Request')]");
		BinTOBinConsignee(partnvnu, "c2","nvnu");
		clear1(MASTERSEARCH1);
		BinTOBinConsignee(partnv, "c2","nv");
		clear1(MASTERSEARCH1);
		BinTOBinConsignee(partv, "c2","v");
		Consignee=prop.getProperty("Consignee");
		picktype="BATCH";
Bin="c2";
		writeDataForCSV(picktype,"Newdelpoint","so", part,quantity);

		pickgenerationFIFO();


		pickerassign1(SOCustReference);
		ArrayList<String>picked=pickingScreenFS("",SOCustReference,Arrays.asList(binPickPart),binPickPartquantity);
		skipConsolidation(SOCustReference); 
		disp(SOCustReference,"",picked);


		logOut();
		account1=false;
		Consignee=null;
		account=prop.getProperty("ACCOUNTID");
		Bin=prop.getProperty("BIN_LABEL");



	}
public String CTR(String part,int quantity,String type) throws Exception
{
	clickXpath("(//button[@type='button'])[4]", "createRequest");
	Thread.sleep(2000);

	clickXpath("(//div[contains(.,'Enter')])[13]", "account");
	clickDownArrow();
	actionEnter();
	clickXpath("(//div[contains(.,'Enter')])[13]", "part");

	actionSendKeys("(//div[contains(.,'Enter')])[13]",part);
	Thread.sleep(2000);
	actionEnter();
	

	enterXpath("//*[@id='PickGen_KittingQty']",""+quantity);
	
	clickXpath("(//button[@type='button'])[4]", "search");
	
	clickXpath("//input[@id='radio']","radiobutton");
	if(type.equalsIgnoreCase("V"))
	{	clickXpath("(//button[@type='button'])[4]", "SelectLPN");

		List<WebElement> VLPNSDetails=driver.findElements(By.xpath("(//table)[2]//tbody//tr"));
		int i=0;
for(WebElement a:VLPNSDetails)
{   ++i;
	System.out.println("LPN : "+a.getText());
	if(a.getText().contains(VLPN.get(0)))
	{
		clickXpath("(//table)[2]//tbody//tr["+(i)+"]//input[@id='check']", "checkbox");
	}
	

}
clickXpath("(//button[@type='button'])[7]", "ok");

		System.out.println("LPN : "+VLPNSDetails.size());
		clickXpath("(//button[@type='button'])[6]", "save");

	}
	else
	{
		clickXpath("(//button[@type='button'])[5]", "save");

	}

	verify("(//*[contains(.,'Sucessfully')])[14]","Sucessfully");
	clickXpath(ALERTBOXCANCEL,"ALERTBOXCANCEL");
	return xpathGetText("(//*[@id='row'])[1]//td[1]");
}

public void BinTOBinConsignee(String reqNo,String destinationBin,String type) throws Exception
{
	childTest = parantTest.createNode("Single Bin Transfer- Consignee Transfer: "+reqNo);
verify("(//tr[@id='row']//td[2])[1]");
enterXpath(MASTERSEARCH1, reqNo, "request number");
clickXpath(OUTBOUNDPICKGENERATIONSEARCHBUTTON, "search");
clickXpath("(//tr[@id='row']//td[2])[1]", "first");
if(!type.equalsIgnoreCase("nvnu"))
{
	enterXpath("//input[@name='sourcebin']", destinationBin, "destinationbin");
	clickXpath("//span[contains(text(),'SUBMIT')]", "submit");
	Thread.sleep(2000);
	clickXpath("//img[@alt='info_icon']", "info");
}
ArrayList<String> lpns=new ArrayList<String>();
if(type.equalsIgnoreCase("V"))
{
	lpns.add(xpathGetText("//*[@id='0']/div[1]/div[2]/b"));

	clickXpath("//span[@class='close']", "close");
}else if(type.equalsIgnoreCase("nvnu"))
{
	String nvnulpn=picktype.contains("CUSTLPN")?readXL("PO", 2, "LPN"):partnvnu;
	clear1("//input[@name='sourcebin']");
	enterXpath("//input[@name='sourcebin']", nvnulpn, "nvnu");
	clickXpath("//span[contains(text(),'SUBMIT')]", "submit");
	Thread.sleep(1000);
	clickXpath("//span[contains(text(),'SUBMIT')]", "submit");
	enterXpath("//input[@name='lpnQty']",""+ binPickPartquantity[2],"");
	clickXpath("//span[contains(text(),'SUBMIT')]", "submit");

	enterXpath("//input[@name='sourcebin']", destinationBin, "nvnu");

	clickXpath("//span[contains(text(),'SUBMIT')]", "submit");
}
else
{
	for(int i=0;i<binPickPartquantity[Arrays.asList(binPickPart).indexOf(partnv)];i++)
	{
		lpns.add(xpathGetText("//*[@id="+i+"]/div[1]/div[2]/b"));

	}	clickXpath("//span[@class='close']", "close");
}
	for(String lpn:lpns){
		verify("(//div[contains(.,'Successfully')])[9]");

		enterXpath("//input[@name='sourcebin']", lpn, "LPN");
		clickXpath("//span[contains(text(),'SUBMIT')]", "submit");
		}
	Thread.sleep(1000);
	verify("(//div[contains(.,'Scanned Successfully')])[14]");
	clickXpath("//span[@class='close']", "close");
}



}
