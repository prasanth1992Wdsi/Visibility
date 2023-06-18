package testScripts;

import java.util.ArrayList;
import java.util.Arrays;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
	
public class RicoFlow extends ProjectSpecificFunctions {

		
		@Test
public void ricoflows() throws Exception
{
	RicoFlow_1();
	RicoFlow_2();
	RicoFlow_3();
}

		public void dispRico(ArrayList<String> cart,String type,ArrayList<String> label) throws Exception
		{
			deleteFile();
			// ArrayList<String> cart
			childTest = parantTest.createNode(" Order Dispatch ");
			clickXpath(DISPATCHGRID,"DISPATCHGRID");
			verify("(//*[@id='Despatch_REQUESTNO'])[1]");
			enterXpath(DISPATCHSEARCH, cart.get(0),"DISPATCHSEARCH");
			actionEnter();
			
			Thread.sleep(2000);
			//clickXpath(DISPATCHBUTTON,"Dispatch1");
			//clickXpath(DISPATCHBTN,"DISPATCHBTN");		
			//verify("(//*[contains(.,' / ')])[23]","3");
			childTest.info("The order available in Dispatch Screen");

			
			childTest.info("The Mode of carrier is :" + "UPS");
			//enterXpath(CARRIERREF, "SmokeTest","CARRIERREF");
			//childTest.info("The carrier reference is :" + "SmokeTest");
			//	enterXpath("//*[@id=\"Despatch_entervehicle\"]/div/div[1]", VEHICLENUMBER,"VEHICLE");


			//childTest.info("The Vehicle Number is :" + "TN38AA1234");


			JavascriptExecutor js = (JavascriptExecutor) driver;

			js.executeScript("window.scrollTo(document.body.scrollHeight, 1000)");
			
			clickXpath(PRINTCARRIER,"Print carrier label");
			Thread.sleep(4000);
			driver.navigate().refresh();
			Thread.sleep(4000);
			clickIDLinkText(MISC_MENU);
			scrollUp();
			Thread.sleep(2000);

			clickXpath(SCANCART,"SCAN CART GRID");
			scrollDown();
			Thread.sleep(3000);
			scanCart(cart);
			scrollUp();
			clickXpath("//b[text()='Outbound']","OUTBOUND");
			
			Thread.sleep(4000);
			clickXpath(DISPATCHGRID,"DISPATCHGRID");
			verify("(//*[@id='Despatch_REQUESTNO'])[1]");
			enterXpath(DISPATCHSEARCH, cart.get(0),"DISPATCHSEARCH");
			actionEnter();
			
			Thread.sleep(2000);
			//clickXpath(DISPATCHBUTTON,"Dispatch1");
			//clickXpath(DISPATCHBTN,"DISPATCHBTN");		
			//verify("(//*[contains(.,' / ')])[23]","3");
			childTest.info("The order available in Dispatch Screen");

			
			childTest.info("The Mode of carrier is :" + "UPS");
			//enterXpath(CARRIERREF, "SmokeTest","CARRIERREF");
			//childTest.info("The carrier reference is :" + "SmokeTest");
			//	enterXpath("//*[@id=\"Despatch_entervehicle\"]/div/div[1]", VEHICLENUMBER,"VEHICLE");


			//childTest.info("The Vehicle Number is :" + "TN38AA1234");


			JavascriptExecutor js1 = (JavascriptExecutor) driver;

			js1.executeScript("window.scrollTo(document.body.scrollHeight, 1000)");
			clickXpath(FINISH,"Finish");
			
			//getStockNumber1(readPDF(), "packingslip");
			//packingslip1 =  VLPN.get(1);
			System.out.println("packing slip is:" +packingslip1);
			childTest.info("packing slip is:" +packingslip1);

			//  deleteFile();
			Thread.sleep(4000);
			orderStatus(SOCustReference, Module.DISPATCH);
			JavascriptExecutor js2 = (JavascriptExecutor) driver;

			js2.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
			enterXpath(DISPATCHSEARCH, SOCustReference,"DISPATCHSEARCH");
			verify("//*[contains(text(),'No Records Found')]");	
			childTest.log(Status.INFO,"Dispatched order not listed in dispatch screen ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );}

//	@Test(priority=29,alwaysRun=true)
		public void RicoFlow_1() throws Exception
		{  site=prop.getProperty("SITEIDRICO");
		account=prop.getProperty("ACCOUNTRICO");
		Binner=prop.getProperty("USERNAMERICO");
		Picker=prop.getProperty("USERNAMERICO");
		PodUser=prop.getProperty("USERNAMERICO");
		Username=prop.getProperty("USERNAMERICO");
		parantTest = extent.createTest("RicoFlow:Scenario 1: Inbound testing for fifo suggested with serial no as yes LPN(API order creation)");    


			
			partnvnu="NVNUSERIAL46";
			picktype="SERIALNUMBERAPI";


			loginAsUser(Username);

			parantTest = extent.createTest("RicoFlow:Scenario 1: Inbound testing for fifo suggested with serial no as yes LPN(API order creation)");    
			//site="in00613";

			    vehicleCreation(site);
			    //childTest = parantTest.createNode("Vehicle Creation");
			   
			    part=new String[]{partnvnu};
				quantity=new String[]{"3"};
				binPickPart=new String[]{partnvnu};
				binPickPartquantity=new int[]{3};
				String q=quantity[(Arrays.asList(binPickPart)).indexOf(partnvnu)];
		    writeDataForCSV(picktype,"Newdelpoint","po", part,quantity);

				childTest = parantTest.createNode("PO Order creation for fifo suggested with serial number yes picktype through API");
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

				childTest.log(Status.PASS,"The uploaded PO parts  details fifo suggested with serial no as yes: ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
				clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
				shortWait(2000);
				assignpod(PodUser);
				//GRNProcess(CustReference);
				podGeneration();
				binnerAssignment(CustReference,Binner);
				//binning1(CustReference);
			//	nvnupartBox=readXL("PO", 0, "CUST_BOX_LABEL");
				//nvpartBox=readXL("PO", 2, "CUST_BOX_LABEL");

				binning("",CustReference,Arrays.asList(binPickPart), binPickPartquantity);

				parantTest = extent.createTest("RicoFlow: Outbound testing for fifo suggested with serial no as yes LPN(API order creation)using Order level cart label");    
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
				
				//pickingScreenFS();
			//	pickingScreenFS("",SOCustReference,Arrays.asList(binPickPart),binPickPartquantity);
				//consolidationcuslpn();
				//skipConsolidation(SOCustReference);
			//	dispatch2(SOCustReference);	
				ArrayList<String>picked=pickingScreenFS("cart",SOCustReference,Arrays.asList(binPickPart),binPickPartquantity);
	for(int ii=0;ii<Integer.parseInt(quantity[(Arrays.asList(binPickPart)).indexOf(partnvnu)]);ii++)
		{
		picked.add(picked.get(0));
		}
	Thread.sleep(3000);
	clickIDLinkText(MISC_MENU);scrollUp();
	Thread.sleep(2000);

	clickXpath(SCANCART,"SCAN CART GRID");
	scrollDown();
	Thread.sleep(3000);
	scanCart(cart);
	
	clickXpath("//b[text()='Outbound']","OUTBOUND");
	//disp(SOCustReference,"MULTIBOXNVNU",picked);
	dispRico(cart,"MULTIBOXNVNU",picked);
	Thread.sleep(3000);
/*
	clickIDLinkText(MISC_MENU);
	scrollUp();
	Thread.sleep(2000);

	clickXpath(SCANCART,"SCAN CART GRID");
	scrollDown();
	Thread.sleep(3000);
	//scanCart(cart);
*/
		
		deleteFile();
	/*	//clickXpath("//b[text()='Outbound']","OUTBOUND");
		clickXpath("//b[text()='Outbound Label']","label");
		Label_Reprint(SOCustReference.toLowerCase(), "packingslip");
	//	Label_Reprint(SOCustReference.toLowerCase(), "carrierlabelorder");
*/		
		

				logOut();
	System.out.println("Rico cart label flow");
			

				
				//logOut();

		}


		public void scanCart(ArrayList<String> cart) throws Exception
	    {
	        childTest = parantTest.createNode("Scan Cart label for Rico process");
	        for(String pickingcart:cart)
	        {
	            enterXpath(SCANCARTLABEL, pickingcart,"Scan cart label");    
	            actionEnter();
		        verify("//*[@id='root']/div/div/main/div/div/div[2]/div[1]/div[2]/div/div/div[2]/div[2]/label",pickingcart);

		        childTest.log(Status.INFO,MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture1()).build());
	        }
	        
	        
	    }
	//	@Test(priority=30,alwaysRun=true)
		public void RicoFlow_2() throws Exception
		{  			parantTest = extent.createTest("RicoFlow:Scenario 2: Inbound testing for fifo suggested with serial no as yes LPN(API order creation)");    

			site=prop.getProperty("SITEIDRICO");
		account=prop.getProperty("ACCOUNTRICO");
		Binner=prop.getProperty("USERNAMERICO");
		Picker=prop.getProperty("USERNAMERICO");
		PodUser=prop.getProperty("USERNAMERICO");
		Username=prop.getProperty("USERNAMERICO");


			
			partnvnu="NVNUSERIAL46";
			picktype="SERIALNUMBERAPI";


			loginAsUser(Username);

			

			    vehicleCreation(site);
			    //childTest = parantTest.createNode("Vehicle Creation");
			   
			    part=new String[]{partnvnu};
				quantity=new String[]{"3"};
				binPickPart=new String[]{partnvnu};
				binPickPartquantity=new int[]{3};
				String q=quantity[(Arrays.asList(binPickPart)).indexOf(partnvnu)];
		    writeDataForCSV(picktype,"Newdelpoint","po", part,quantity);

				childTest = parantTest.createNode("PO Order creation for fifo suggested with serial number yes picktype through API");
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

				childTest.log(Status.PASS,"The uploaded PO parts  details fifo suggested with serial no as yes: ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
				clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
				shortWait(2000);
				assignpod(PodUser);
				//GRNProcess(CustReference);
				podGeneration();
				binnerAssignment(CustReference,Binner);
				//binning1(CustReference);
			//	nvnupartBox=readXL("PO", 0, "CUST_BOX_LABEL");
				//nvpartBox=readXL("PO", 2, "CUST_BOX_LABEL");

				binning("",CustReference,Arrays.asList(binPickPart), binPickPartquantity);

				parantTest = extent.createTest("RicoFlow: Outbound testing for fifo suggested with serial no as yes LPN(API order creation)using Site level cart label");    
			    writeDataForCSV(picktype,"Newdelpoint","so", part,quantity);
			    
				DB(Arrays.asList(binPickPart), binPickPartquantity, "pickgen");

				//pickgenerationFIFO();
			    verify(OUTBOUND);

				clickXpath("//b[text()='Outbound']","OUTBOUND");

				verify(PICKGENERATION);
				clickIDLinkText("Pick Generation");
				pickerassign1(SOCustReference);
				clickXpath("//b[text()='Outbound Label']","label");
				
				cart=new ArrayList<String>();

				Label_Reprint("1", "cartlabel");
				cart.add(VLPN.get(0));
				
				//pickingScreenFS();
			//	pickingScreenFS("",SOCustReference,Arrays.asList(binPickPart),binPickPartquantity);
				//consolidationcuslpn();
				//skipConsolidation(SOCustReference);
			//	dispatch2(SOCustReference);	
				ArrayList<String>picked=pickingScreenFS("cart",SOCustReference,Arrays.asList(binPickPart),binPickPartquantity);
	for(int ii=0;ii<Integer.parseInt(quantity[(Arrays.asList(binPickPart)).indexOf(partnvnu)]);ii++)
		{
		picked.add(picked.get(0));
		}
	Thread.sleep(3000);
	clickIDLinkText(MISC_MENU);scrollUp();
	Thread.sleep(2000);

	clickXpath(SCANCART,"SCAN CART GRID");
	scrollDown();
	Thread.sleep(3000);
	scanCart(cart);
	
	clickXpath("//b[text()='Outbound']","OUTBOUND");
	//disp(SOCustReference,"MULTIBOXNVNU",picked);
	dispRico(cart,"MULTIBOXNVNU",picked);
	Thread.sleep(3000);

		
		deleteFile();
			 parantTest = extent.createTest("RicoFlow: Outbound testing for fifo suggested with serial no as yes LPN(API order creation)Reusing Dispatched Site level cart label");    
		writeDataForCSV(picktype,"Newdelpoint","so", part,quantity);
	    
		DB(Arrays.asList(binPickPart), binPickPartquantity, "pickgen");
verify(PICKGENERATION);
clickIDLinkText("Pick Generation");
pickerassign1(SOCustReference);
ArrayList<String>picked1=pickingScreenFS("cart",SOCustReference,Arrays.asList(binPickPart),binPickPartquantity);
for(int ii=0;ii<Integer.parseInt(quantity[(Arrays.asList(binPickPart)).indexOf(partnvnu)]);ii++)
	{
	picked1.add(picked1.get(0));
	}
Thread.sleep(3000);
clickIDLinkText(MISC_MENU);
scrollUp();
Thread.sleep(2000);

clickXpath(SCANCART,"SCAN CART GRID");
scrollDown();
Thread.sleep(3000);
scanCart(cart);
scrollUp();
clickXpath("//b[text()='Outbound']","OUTBOUND");
//disp(SOCustReference,"MULTIBOXNVNU",picked);
dispRico(cart,"MULTIBOXNVNU",picked);
Thread.sleep(3000);

				logOut();
	System.out.println("Rico cart label flow");
			

				
				//logOut();

		}
	//	@Test(priority=31,alwaysRun=true)
		public void RicoFlow_3() throws Exception
		{ 
			site=prop.getProperty("SITEIDRICO");
		account=prop.getProperty("ACCOUNTRICO");
		Binner=prop.getProperty("USERNAMERICO");
		Picker=prop.getProperty("USERNAMERICO");
		PodUser=prop.getProperty("USERNAMERICO");
		Username=prop.getProperty("USERNAMERICO");
		parantTest = extent.createTest("RicoFlow:Scenario 1: Inbound testing for serial no as yes (API order creation)");    


			
			partnvnu="NVNUSERIAL46";
			picktype="SERIALNUMBERAPI";


			loginAsUser(Username);

			//parantTest = extent.createTest("RicoFlow:Scenario 1: Inbound testing for fifo suggested with serial no as yes LPN(API order creation)");    
			//site="in00613";

			    vehicleCreation(site);
			    //childTest = parantTest.createNode("Vehicle Creation");
			   
			    part=new String[]{partnvnu};
				quantity=new String[]{"3"};
				binPickPart=new String[]{partnvnu};
				binPickPartquantity=new int[]{3};
				String q=quantity[(Arrays.asList(binPickPart)).indexOf(partnvnu)];
		    writeDataForCSV(picktype,"Newdelpoint","po", part,quantity);

				childTest = parantTest.createNode("PO Order creation for serial number yes picktype through API");
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

				childTest.log(Status.PASS,"The uploaded PO parts  details serial no picktype: ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
				clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
				shortWait(2000);
				assignpod(PodUser);
				//GRNProcess(CustReference);
				podGeneration();
				binnerAssignment(CustReference,Binner);
				//binning1(CustReference);
			//	nvnupartBox=readXL("PO", 0, "CUST_BOX_LABEL");
				//nvpartBox=readXL("PO", 2, "CUST_BOX_LABEL");

				binning("",CustReference,Arrays.asList(binPickPart), binPickPartquantity);
	parantTest = extent.createTest("RicoFlow: Outbound testing for  serial no Picktype (API order creation)using Order level cart label");    
	picktype="SERIALNUMBERAPI";

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
				
				//pickingScreenFS();
			//	pickingScreenFS("",SOCustReference,Arrays.asList(binPickPart),binPickPartquantity);
				//consolidationcuslpn();
				//skipConsolidation(SOCustReference);
			//	dispatch2(SOCustReference);	
				ArrayList<String>picked=pickingScreenFS("cart",SOCustReference,Arrays.asList(binPickPart),binPickPartquantity);
	for(int ii=0;ii<Integer.parseInt(quantity[(Arrays.asList(binPickPart)).indexOf(partnvnu)]);ii++)
		{
		picked.add(picked.get(0));
		}
	Thread.sleep(3000);
	//PickedNVLPNS=new ArrayList();
	//PickedNVLPNS.addAll(picked);

	//disp(SOCustReference,"MULTIBOXNVNU",picked);
	dispRico(cart,"MULTIBOXNVNU",picked);
	Thread.sleep(3000);

		deleteFile();
	/*	//clickXpath("//b[text()='Outbound']","OUTBOUND");
		clickXpath("//b[text()='Outbound Label']","label");
		Label_Reprint(SOCustReference.toLowerCase(), "packingslip");
//		Label_Reprint(SOCustReference.toLowerCase(), "carrierlabelorder");
	*/		
//		PickedNVLPNS=new ArrayList();
//	       PickedNVLPNS.addAll(picked);
		Return_PO();

				//logOut();
	System.out.println("Rico Return order flow");
			

				
				logOut();

		}
		public void Return_PO() throws Exception{
			parantTest = extent.createTest("RicoFlow: Return order inbound testing for  serial no picktype");    
			picktype="SERIALNUMBER";
			
			//String[]lpn=new String[]{pickedLPNS,};
			part=new String[]{partnvnu};
			quantity=new String[]{"3"};
			binPickPart=new String[]{partnvnu};
			binPickPartquantity=new int[]{3};
		
			vehicleCreation(site);
			
			//String q=quantity[(Arrays.asList(binPickPart)).indexOf(partnvnu)];
			writeDataForCSV(picktype, "Newdelpoint", "returnorder",part,quantity);

			clickXpath(INBOUND, "Inbound");
			clickIDLinkText(DASHBOARD);
			driver.navigate().refresh();
			childTest.info("Vehicle number is :  " + VEHICLENUMBER);
			//verify(GATEIN_1stROW);
			enterXpath(SEARCH_BOX, VEHICLENUMBER,"Dock in searchbox");
			VehicleID = xpathGetText(GATEIN_1stROW);
			clickXpath(VEHICLE1,"Vehicle number");
			clickXpath(DOCKIN_BTN,"DOCKIN_BTN");
			CustRefList=new ArrayList<String>();
			
		uploadFIFO("RETURNORDER");
			
			assignpod(PodUser);
			podGeneration();
			binnerAssignment(CustReference,Binner);
						
			binning("",CustReference,Arrays.asList(binPickPart), binPickPartquantity);

		}
		
}
