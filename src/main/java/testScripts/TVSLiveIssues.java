package testScripts;

import java.io.IOException;

import org.testng.annotations.Test;

public class TVSLiveIssues extends ProjectSpecificFunctions {
	String partnv2;
	@Test(priority=11,alwaysRun=true)
	public void Live_13493() throws  Exception {
		
			
			partnv="NVLIVEPART";
			partnv2="NVLIVEPART2";
			
			picktype="CUSTLPN";
			parantTest = extent.createTest("LIVE_13493(TVS) :  Unable to do bulk part level binner assigning.)");    


			loginAsUser(Username);
			
			vehicleCreation(site);
			part=new String[]{partnv,partnv2};
				quantity=new String[]{"1","1",};
				binPickPart=new String[]{partnv,partnv2};
				binPickPartquantity=new int[]{1};
				 writeDataForCSV(picktype,"Newdelpoint","po", part,quantity);

				
			    childTest = parantTest.createNode("PO Order creation");
				clickIDLinkText(DASHBOARD);
				driver.navigate().refresh();
				childTest.info("Vehicle number is :  " + VEHICLENUMBER);
				verify(GATEIN_1stROW);
				enterXpath(SEARCH_BOX, VEHICLENUMBER,"Dock in searchbox");
				VehicleID = xpathGetText(GATEIN_1stROW);
			    clickXpath(VEH,"Vehicle number");
		        clickXpath(DOCKIN_BTN,"DOCKIN_BTN");
		     
				uploadFIFO("1");
				childTest = parantTest.createNode("PO Order creation forLive-13501");
				shortWait(2000);
				verify(FINISH_UPLOAD_BTN);
				
				clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
				shortWait(2000);
				childTest.pass("Order upload finished ");
				assignpod(PodUser);
				podGeneration();
				PartLevelBinner(CustReference,Binner);
				logOut();
}
	public void PartLevelBinner(String CustReference,String Binner) throws IOException, Exception {
		scrollUp();

		childTest = parantTest.createNode("Binner Assignment");
		

		clickIDLinkText(BINNERASSIGNMENT_MENU);
		verify(ROW1);
		enterXpath(SEARCH_BOX, CustReference,"binnerAssignment SEARCH");
		clickXpath(SEARCH_BTN,"SEARCH_BTN");
		childTest.info("The order available in Binner assignment screen");
		verify("(//*[contains(.,'1 of 1')])[16]");

		clickXpath(BINNERASSIGN_CHECK_BOX,"BINNERASSIGN_CHECK_BOX");
		
		clickXpath(ASSIGN_PARTLEVEL_BINNER_BTH,"ASSIGN_BINNER_BTH");
		clickXpath("//input[@id='PartLevel_Checkbox']","ASSIGN_BINNER_BTH");
		
		clickXpath("//span[text()='ASSIGN BINNER']","ASSIGN_BINNER_BTH");
		enterXpath(PICKERINPUT, Binner,"PICKER INPUT");
		nameClick(Binner);
		resize(1382, 650);

		clickXpath(ASSIGNBINNER,"ASSIGNBINNER");
		driver.manage().window().maximize();

		Thread.sleep(2000);
		driver.navigate().refresh();
		childTest.pass("Binner Assigned Successfully." + "The Binner is : " + Binner);
		clickXpath(BINNING_GRID, "Binning");
		enterXpath(SEARCH_BOX, CustReference,"BINNING SEARCH");
		//span[text()='1-2 of 2']
	//	verify("//span[text()='1-2 of 2']");
		childTest.pass("Order available in binning screen." +"Customer Reference : " +CustReference);
		


		Thread.sleep(1000);}
	
}
