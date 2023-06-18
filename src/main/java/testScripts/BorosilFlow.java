package testScripts;

import java.util.ArrayList;
import java.util.Arrays;

import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class BorosilFlow extends ProjectSpecificFunctions {

	@Test
	public void BodyShopFlow1() throws Exception
	{site=prop.getProperty("SITEIDGRN");
	
	Binner=prop.getProperty("USERNAMEGRN");
	Picker=prop.getProperty("USERNAMEGRN");
	PodUser=prop.getProperty("USERNAMEGRN");
	Username=prop.getProperty("USERNAMEGRN");

		partv="BATCHCUSTLPNV";
		
		picktype="BOXBATCHCUSTLPNAPI";
		parantTest = extent.createTest("BorosilFlow: Inbound testing for Customer LPN(API order creation)");    


		loginAsUser(Username);

		//site="in00613";
		//site="in18699";

		    vehicleCreation(site);
		    //childTest = parantTest.createNode("Vehicle Creation");
		   
		    part=new String[]{partv,partv};
			quantity=new String[]{"2","2"};
			binPickPart=new String[]{partv};
			binPickPartquantity=new int[]{4};
		//	String q=quantity[(Arrays.asList(binPickPart)).indexOf(partnvnu)];
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
			childTest.pass("Order upload finished for cuslpn picktype");
			assignpod(PodUser);
			GRNProcess(CustReference);
			podGeneration();
			binnerAssignment(CustReference,Binner);
			
			clickIDLinkText(INBOUND_LABEL);

			Label_Reprint("1", "boxlabel");
		
				vpartBox=VLPN.get(0);
			binning("boxadditionallabel",CustReference,Arrays.asList(binPickPart), binPickPartquantity);
			binning("binbox",CustReference,Arrays.asList(binPickPart), binPickPartquantity);

			parantTest = extent.createTest("BorosilFlow: Outbound testing for Customer LPN(API order creation)");    
		//	DB(Arrays.asList(binPickPart), binPickPartquantity, "prepick");
			picktype="BOXBATCH";

			writeDataForCSV(picktype,"Newdelpoint","so", part,quantity);

			pickgenerationFIFO();
		    /*verify(OUTBOUND);

			clickXpath("//b[text()='Outbound']","OUTBOUND");
*/
		/*	verify(PICKGENERATION);
			DB(Arrays.asList(binPickPart), binPickPartquantity, "pickgen");*/

		//	clickIDLinkText("Pick Generation");
			pickerassign1(SOCustReference);
			clickXpath("//b[text()='Outbound Label']","label");
			Label_Reprint(SOCustReference.toLowerCase(), "cartorderlevel");
			cart=new ArrayList<String>();
			cart.add(VLPN.get(0));
			
			//pickingScreenFS();
		//	pickingScreenFS("",SOCustReference,Arrays.asList(binPickPart),binPickPartquantity);
			//consolidationcuslpn();
			//skipConsolidation(SOCustReference);
		//dispatch2(SOCustReference);	
			ArrayList<String>picked=pickingScreenFS("cart",SOCustReference,Arrays.asList(binPickPart),binPickPartquantity);
			skipConsolidation(SOCustReference);
			disp(SOCustReference,"",picked);
			Packingslip_Quantity(readPDF(),SOCustReference);
			deleteFile();
			clickXpath("//b[text()='Outbound Label']","label");
			Label_Reprint(SOCustReference.toLowerCase(), "packingslip");
			Packingslip_Quantity(readPDF(),SOCustReference);

			logOut();

			System.out.println("cuslpn");
		//logOut();

	}


}
