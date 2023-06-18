package testScripts;

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

import testScripts.CommonFunctions.Module;

public class BodyShopFlow extends ProjectSpecificFunctions {
	@Test(priority=0)
	public void BodyShopFlow1() throws Exception
	{site=prop.getProperty("SITEIDGRN");
	
	Binner=prop.getProperty("USERNAMEGRN");
	Picker=prop.getProperty("USERNAMEGRN");
	PodUser=prop.getProperty("USERNAMEGRN");
	Username=prop.getProperty("USERNAMEGRN");


		partv="PARTVCUSLPN";
		partnv="PARTNVCUSLPN";
		partnvnu="PARTNVNUCUSLPN";
		picktype="CUSTLPNAPI";
		parantTest = extent.createTest("BodyShopFlow: Inbound testing for Customer LPN(API order creation)");    

	    vehicleCreation(site);

		loginAsUser(Username);

		//site="in00613";
		//site="in18699";

		    //childTest = parantTest.createNode("Vehicle Creation");
		   
		    part=new String[]{partnvnu};
			quantity=new String[]{"3"};
			binPickPart=new String[]{partnvnu};
			binPickPartquantity=new int[]{3};
			String q=quantity[(Arrays.asList(binPickPart)).indexOf(partnvnu)];
	    writeDataForCSV(picktype,"Newdelpoint","po", part,quantity);

			childTest = parantTest.createNode("PO Order creation for cuslpn picktype through API");
			clickIDLinkText(DASHBOARD);
			driver.navigate().refresh();
			childTest.info("Vehicle number is :  " + VEHICLENUMBER);
			verify(GATEIN_1stROW);
			enterXpath(SEARCH_BOX, VEHICLENUMBER,"Dock in searchbox");
			VehicleID = xpathGetText(GATEIN_1stROW);
		    clickXpath(VEHICLE1,"Vehicle number");
	        clickXpath(DOCKIN_BTN,"DOCKIN_BTN");
			uploadFIFO("ASSIGNORDER");
			//verify(FINISH_UPLOAD_BTN);
			shortWait(2000);

			childTest.log(Status.PASS,"The uploaded PO parts  details for cuslpn picktype: ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
			clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
			shortWait(2000);
			assignpod(PodUser);
			GRNProcess(CustReference);
			podGeneration();
			binnerAssignment(CustReference,Binner);
			
			binning("",CustReference,Arrays.asList(binPickPart), binPickPartquantity);

			parantTest = extent.createTest("BodyShopFlow: Outbound testing for Customer LPN(API order creation)");    
		    writeDataForCSV(picktype,"Newdelpoint","so", part,quantity);
			DB(Arrays.asList(binPickPart), binPickPartquantity, "pickgen");

			//pickgenerationFIFO();
		    verify(OUTBOUND);

			clickXpath("//b[text()='Outbound']","OUTBOUND");

			verify(PICKGENERATION);
			clickIDLinkText("Pick Generation");
			pickerassign1(SOCustReference);
			clickXpath("//b[text()='Outbound Label']","label");
			Label_Reprint(SOCustReference.toLowerCase(), "cartorderlevel");
			cart=new ArrayList<String>();
			cart.add(VLPN.get(0));
			cart.add(VLPN.get(0));
			cart.add(VLPN.get(0));
			
			ArrayList<String>picked=pickingScreenFS("cart",SOCustReference,Arrays.asList(binPickPart),binPickPartquantity);
for(int ii=0;ii<Integer.parseInt(quantity[(Arrays.asList(binPickPart)).indexOf(partnvnu)]);ii++)
	{
	picked.add(picked.get(0));
	}disp(SOCustReference,"",cons( SOCustReference,"MULTIBOXNVNU",picked));
	Packingslip_Quantity(readPDF(),SOCustReference);
	deleteFile();
	clickXpath("//b[text()='Outbound Label']","label");
	Label_Reprint(SOCustReference.toLowerCase(), "packingslip");
	Packingslip_Quantity(readPDF(),SOCustReference);

			logOut();

			System.out.println("cuslpn");
		
	}

	
	
					
}
