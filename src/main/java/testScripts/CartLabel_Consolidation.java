package testScripts;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
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

public class CartLabel_Consolidation extends ProjectSpecificFunctions {
	
	
	public String  VON;
	public int i;
	public String NVCartLabel;
	
	ArrayList<String> Outbound_cartLabel=new  ArrayList<String>();
	
	
		


	
	
	@Test(priority=0)
	public void fifoPicktypeInb() throws IOException, Exception {
		parantTest = extent.createTest("cartlabel consolidation started"); 

		loginAsUser(Username);

		partv="PARTVFIFO";
		partnv="PARTNVFIFO";
		partnvnu="PARTNVNUFIFO";
		picktype="FIFO";
		System.out.println("cartlabel consolidation started");
		vehicleCreation(site);
		 part=new String[]{partv,partnv,partnvnu};
			quantity=new String[]{"5","5","5"};
			binPickPart=new String[]{partv,partnv,partnvnu};
			binPickPartquantity=new int[]{5,5,5};
	    writeDataForCSV(picktype,"Newdelpoint","po", part,quantity);
	    writeDataForCSV(picktype,"Newdelpoint","so", part,quantity);

		vqty();
		nvnuqty();
		childTest = parantTest.createNode("PO Order creation for fifo picktype");
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
		childTest.log(Status.PASS,"The uploaded PO parts  details for fifo Picktype: ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
		shortWait(2000);
		childTest.pass("Order upload finished for fifo Picktype");
		assignpod(PodUser);
		podGeneration();
		binnerAssignment(CustReference,Binner);
		System.out.println(partv+" "+partnv+" "+partnvnu);
		binning("",CustReference,Arrays.asList(new String[]{partv,partnv,partnvnu}), new int[]{5,5,5});

	
		//parantTest = extent.createTest("ScantoCart picking with fifo picktype for Outbound process");
		
		pickgenerationFIFO();
		pickerassign1(SOCustReference);
		clickXpath("//b[text()='Outbound Label']","label");

		Label_Reprint("3", "cartlabel");
		cart=new ArrayList<String>();
		cart.add(VLPN.get(0));
		cart.add(VLPN.get(1));
		cart.add(VLPN.get(2));
		pickingScreenFS("cart",SOCustReference,Arrays.asList(binPickPart), binPickPartquantity);
		//ArrayList<String> cartons=cons("", SOCustReference,c,new String[]{"2","1","2","1","2","1"},6);
		disp(SOCustReference,"",cons(SOCustReference, "", cart));

	
		System.out.println("cartlabel consolidation completed");
		logOut();

	}

	

	
}
