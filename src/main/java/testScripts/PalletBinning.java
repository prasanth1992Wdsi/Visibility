package testScripts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import testScripts.CommonFunctions.Module;

public class PalletBinning extends ProjectSpecificFunctions{


	


	@Test
	public void palletBinning()throws Exception {
		parantTest = extent.createTest("Pallet Binning for Fifosuggested picktype");   

		loginAsUser(Username);
		vehicleCreation(site);
		partv="VFS";
		partnv="NVFS";
		partnvnu="NVNUFS";
		picktype="FIFOSUGGESTED";
		binPickPart=new String[]{partv,partnv,partnvnu};
		binPickPartquantity=new int[]{5,5,5};
		part=new String[]{partv,partnv,partnvnu};
		quantity=new String[]{"5","5","5"};	

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
		clickIDLinkText(INBOUND_LABEL);
		Label_Reprint("3", "palletlabel");
		nvnupartBox=VLPN.get(2);
		nvpartBox=VLPN.get(1);
		vpartBox=VLPN.get(0);

		//System.out.println(nvnupartBox);
		//Binningpallet();
		binning("box",CustReference,Arrays.asList(new String[]{partv,partnv,partnvnu}), new int[]{5,5,5});
		binning("binpallet",CustReference,Arrays.asList(new String[]{partv,partnv,partnvnu}), new int[]{5,5,5});


		parantTest = extent.createTest("Pallet picking for Fifosuggested picktype");
		writeDataForCSV(picktype,"Newdelpoint","so", part,quantity);
		pickgenerationFIFO();
		pickerassign1(SOCustReference);
		ArrayList<String>picked=pickingScreenFS("pallet",SOCustReference,Arrays.asList(binPickPart),binPickPartquantity);
		skipConsolidation(SOCustReference);
		disp(SOCustReference,"",picked);

		logOut();


	}
}
