package testScripts;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import testScripts.CommonFunctions.Module;

public class MultipleCustomerReference extends ProjectSpecificFunctions {


	@Test
	
	public void  nonBinnableBox() throws  Exception
	{

		parantTest = extent.createTest("Inbound testing for Multiple Orders with non- binnable box");    
		loginAsUser(Username);

		
	
		vehicleCreation(site);
		partv="VFS";
		partnv="NVFS";
		partnvnu="FSNVNU";
		
		picktype="FIFOSUGGESTEDMULTI";
		 part=new String[]{partv,partnv,partnvnu};
			quantity=new String[]{"1","1","1"};	
			binPickPart=new String[]{partv,partnv,partnvnu};
			binPickPartquantity=new int[]{1,1,1};
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
		verify(FINISH_UPLOAD_BTN);
		childTest.log(Status.PASS,"The uploaded PO parts  details for cuslpn picktype: ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
		shortWait(2000);
		childTest.pass("Order upload finished for cuslpn picktype");
	

		assignpod(PodUser);
		podGeneration();
		for(String customerref:CustReference1)
		{
			binnerAssignment(customerref,Binner);
		}
		//binning1(CustReference);
		int i=0;
		clickIDLinkText(INBOUND_LABEL);

		Label_Reprint("1", "boxlabelnon");
		nvnupartBox=VLPN.get(0);
			nvpartBox=VLPN.get(0);
			vpartBox=VLPN.get(0);

		for(String customerref:CustReference1)
		{
			binning("box",customerref,Arrays.asList(new String[]{part[i]}), new int[]{Integer.parseInt(quantity[i])});
			binning("binbox",customerref,Arrays.asList(new String[]{part[i]}), new int[]{Integer.parseInt(quantity[i])});

			i++;
		}
		VLPN=null;
		parantTest = extent.createTest("Outbound testing for Multiple Orders");    

	    writeDataForCSV(picktype,"Newdelpoint","so", part,quantity);

		pickgenerationFIFO();
		for(String customerref:CustReference1)
		{
		pickerassign1(customerref);
		}
		//pickingScreenFS();
		int j=0;
		for(String customerref:CustReference1)
		{
		ArrayList<String>picked=pickingScreenFS("",customerref,Arrays.asList(new String[]{part[j]}), new int[]{Integer.parseInt(quantity[j])});
		skipConsolidation(customerref);
		 /*cons( "",picked);*/
		 disp(customerref,"",picked);
		//dispatch2(customerref);	
		VLPN=null;

		j++;
		}
		
		logOut();

		System.out.println("cuslpn");
	

		
		//logOut();
}
}
