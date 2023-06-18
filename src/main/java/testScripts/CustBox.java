package testScripts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import testScripts.CommonFunctions.Module;

public class CustBox extends ProjectSpecificFunctions {
	@Test
	public void  custBoxInb() throws  Exception
	{
	partv="PARTVCUSLPN";
	partnv="PARTNVCUSLPN";
	partnvnu="PARTNVNUCUSLPN";
	picktype="CUSTLPNBOX";

	parantTest = extent.createTest("Inbound testing for Customer LPN with Customer box");    

	loginAsUser(Username);



	    vehicleCreation(site);
	    //childTest = parantTest.createNode("Vehicle Creation");
	   
	    part=new String[]{partv,partnv,partnvnu,partnv,partnv};
		quantity=new String[]{"3","1","3","1","1"};
		binPickPart=new String[]{partv,partnv,partnvnu};
		binPickPartquantity=new int[]{3,3,3};
    writeDataForCSV(picktype,"Newdelpoint","po", part,quantity);

		childTest = parantTest.createNode("PO Order creation for cuslpn picktype");
		clickIDLinkText(DASHBOARD);
		driver.navigate().refresh();
		childTest.info("Vehicle number is :  " + VEHICLENUMBER);
		verify(GATEIN_1stROW);
		enterXpath(SEARCH_BOX, VEHICLENUMBER,"Dock in searchbox");
		VehicleID = xpathGetText(GATEIN_1stROW);
	    clickXpath(VEHICLE1,"Vehicle number");
        clickXpath(DOCKIN_BTN,"DOCKIN_BTN");
		uploadFIFO("1");
		//verify(FINISH_UPLOAD_BTN);
		childTest.log(Status.PASS,"The uploaded PO parts  details for cuslpn picktype: ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
		shortWait(2000);
		childTest.pass("Order upload finished for cuslpn picktype");
		assignpod(PodUser);
		podGeneration();
		binnerAssignment(CustReference,Binner);
		//binning1(CustReference);
		nvnupartBox=readXL("PO", 2, "CUST_BOX_LABEL");
		//nvpartBox=readXL("PO", 2, "CUST_BOX_LABEL");

		binning("binbox",CustReference,Arrays.asList(binPickPart), binPickPartquantity);

		parantTest = extent.createTest("Outbound testing for Customer LPN with Customer box");    
	    writeDataForCSV(picktype,"Newdelpoint","so", part,quantity);

		pickgenerationFIFO();

		pickerassign1(SOCustReference);
		//pickingScreenFS();
	//	pickingScreenFS("",SOCustReference,Arrays.asList(binPickPart),binPickPartquantity);
		//consolidationcuslpn();
		//skipConsolidation(SOCustReference);
	//	dispatch2(SOCustReference);	
		ArrayList<String>picked=pickingScreenFS("",SOCustReference,Arrays.asList(binPickPart),binPickPartquantity);
		 disp(SOCustReference,"",cons( SOCustReference,"",picked));

		logOut();

		System.out.println("cuslpn");
	

		
		//logOut();
}
	
}
