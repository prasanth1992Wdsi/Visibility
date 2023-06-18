package testScripts;

	import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
	import java.util.concurrent.TimeUnit;

	import org.apache.commons.io.FileUtils;
	import org.openqa.selenium.By;
	import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
	import org.testng.annotations.Test;

	import com.aventstack.extentreports.MediaEntityBuilder;
	import com.aventstack.extentreports.Status;

	public class Site_Site extends ProjectSpecificFunctions {
	//	public String CON_VON ;
	
	@Test
			public void fifosugPicktypeInb1() throws IOException, Exception {
		parantTest = extent.createTest("Intersite(JCH flow) :Dispatch process in source site");    

		loginAsUser(Username);

				partnv="PARTNVFSCUS";
				partv="PARTVFSCUS";

				picktype="FIFOSUGGESTEDCUSTLPN";
				System.out.println("site to site started");

			   vehicleCreation(site);
				    
					 part=new String[]{partv,partnv,partnv,partnv,partnv,partnv};
						quantity=new String[]{"5","1","1","1","1","1"};	
					    writeDataForCSV(picktype,"Newdelpoint","po", part,quantity);
					    writeDataForCSV(picktype,"intersite","SO", part,quantity);
					    binPickPart=new String[]{partv,partnv};
						binPickPartquantity=new int[]{5,5};

					childTest = parantTest.createNode("PO Order creation for fifosug picktypes");
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
					childTest.log(Status.PASS,"The uploaded PO parts  details for fifosug Picktypes: ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
					clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
					shortWait(2000);
					childTest.pass("Order upload finished for fifosug Picktype");
					assignpod(PodUser);
					podGeneration();
					binnerAssignment(CustReference,Binner);
					binning("",CustReference,Arrays.asList(binPickPart), binPickPartquantity);
					
					picktype="FIFOSUGGESTEDAPI";
					DB(Arrays.asList(binPickPart), binPickPartquantity, "beforepick");

				    writeDataForCSV(picktype,"intersite","so", part,quantity);

					verify(OUTBOUND);

					clickXpath("//b[text()='Outbound']","OUTBOUND");
					verify(PICKGENERATION);
					clickIDLinkText("Pick Generation");
				pickerassign1(SOCustReference);
				DB(Arrays.asList(binPickPart), binPickPartquantity, "pickgen");

				ArrayList<String>picked=pickingScreenFS("",SOCustReference,Arrays.asList(binPickPart),binPickPartquantity);

	
				consolidation( SOCustReference, VON);
				 disp(SOCustReference,"",picked);

			//	dispatch2(SOCustReference);	

				packingslip1 =  VLPN.get(0);
				VLPN=null;

				logOut();
				site=prop.getProperty("site2");

				loginAsUser(prop.getProperty("USERNAME1"));
				parantTest = extent.createTest("Intersite :Binning in the Destination site");    
				site=prop.getProperty("site2");
				vehicleCreation(site);
				childTest = parantTest.createNode("Scan PACKING SLIP ");
				clickIDLinkText(DASHBOARD);
				driver.navigate().refresh();
				childTest.info("Vehicle number is :  " + VEHICLENUMBER);
				verify(GATEIN_1stROW);
				enterXpath(SEARCH_BOX, VEHICLENUMBER,"Dock in searchbox");
				VehicleID = xpathGetText(GATEIN_1stROW);
			    clickXpath(VEHICLE1,"Vehicle number");
		        clickXpath(DOCKIN_BTN,"DOCKIN_BTN");
		        ScanPackingSlip();
		        childTest.log(Status.PASS,"The Scanned PO parts  details : ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
				clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
				shortWait(2000);
				
				 childTest.log(Status.PASS,"Scanned succesfully: ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
				childTest.pass("packing slip scanned ");
				Binner=prop.getProperty("POD_USER1");
				PodUser=prop.getProperty("POD_USER1");

				assignpod(PodUser);
				podGeneration();
				binnerAssignment(SOCustReference,Binner);
				binning("",SOCustReference,Arrays.asList(binPickPart), binPickPartquantity);

				logOut();
				
				System.out.println("site to site completed");
		        
		}
	public String CON_VON ;
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
					for(int j=1;j<=ROW_Count.size();j++){
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
						}	
					}
				}

				else  /*(VON.equals(CON_VON))*/{
					String address1="1, LUZ CHURCH ROAD, CHENNAI, TAMIL NADU, INDIA, 600004";
					verify("//tr[1][@id='Optimization_Table']/td[6]",address1);
					clickXpath("//input[@id='Optimization_checkbox0']","ORDERCLICK");
					clickXpath(CONSOLIDATIONSKIP,"CONSOLIDATIONSKIP");
					clickXpath(CONSOLIDATIONYES,"CONSOLIDATIONYES");
					Thread.sleep(3000);
					clickXpath(ALERT,"Close");
					deleteFile();
				}

		
	}


	}



