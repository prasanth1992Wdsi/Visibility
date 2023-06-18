package testScripts;

import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class Replenishment extends ProjectSpecificFunctions {
	ArrayList<String> DBLPN=new ArrayList<String>();
	ArrayList<Integer> DBQTY=new ArrayList<Integer>();
	int DBQTY1=0;
	@Test
	public void inbound() throws Exception
	{
		parantTest = extent.createTest("Replenishment inbound testing");    

		loginAsUser(Username);


		vehicleCreation(site);

		partv="REPCUSTV";
		partnv="REPCUSTNV";
		partnvnu="REPCUSTNVNU";
		picktype="CUSTLPN";
		part=new String[]{partv,partnv,partnvnu,partnv,partv};
		quantity=new String[]{"1","1","2","1","1"};	
		binPickPart=new String[]{partv,partnv,partnvnu};
		binPickPartquantity=new int[]{2,2,2};
		writeDataForCSV(picktype,"Newdelpoint","po", part,quantity);

		childTest = parantTest.createNode("PO Order creation Replenishment yes parts");
		//clickIDLinkText("Inbound");

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
		childTest.log(Status.PASS,"The uploaded PO parts  details : ",MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build() );
		clickXpath(FINISH_UPLOAD_BTN,"FINISH_UPLOAD_BTN");
		shortWait(2000);
		
		assignpod(PodUser);
		podGeneration();
		binnerAssignment(CustReference,Binner);
		//binning1("normal");
		Bin=prop.getProperty("bulkbin");
		binning("additionallabel",CustReference,Arrays.asList(binPickPart),binPickPartquantity);
		parantTest = extent.createTest("Bin transfer and outbound testing");

		single_Query(binPickPart[2],prop.getProperty("bulkbin"));
		BinTOBin(Bin, prop.getProperty("picklinebin"));
		Bin_Querry(binPickPart,prop.getProperty("picklinebin"));
		writeDataForCSV(picktype,"Newdelpoint","so", part,quantity);

		pickgenerationFIFO();

		pickerassign1(SOCustReference);
		Bin=prop.getProperty("picklinebin");

		ArrayList<String>picked=pickingScreenFS("",SOCustReference,Arrays.asList(binPickPart),binPickPartquantity);
		disp(SOCustReference,"",cons( SOCustReference,"",picked));
		logOut();

	}
	public void BinTOBin(String sourceBin,String destinationBin) throws Exception
	{
		childTest = parantTest.createNode("Single Bin Transfer");

		clickXpath("//b[contains(text(),'Admin')]", "Admin");
		clickXpath("//b[contains(text(),'Storage')]", "Storage");
		clickXpath("//*[contains(text(),'SGL.Bin Transfer')]", "Single bin transfer");
		//picklinebin
		enterXpath("//input[@name='sourcebin']", destinationBin, "destinationbin");
		clickXpath("//span[contains(text(),'SUBMIT')]", "submit");
		if(VLPN!=null)
		{
			for(String lpn:VLPN){
				verify("(//div[contains(.,'Successfully Scanned')])[7]");
				enterXpath("//input[@class='fieldmodebintobin form-control']", lpn, "LPN");
				clickXpath("//span[contains(text(),'SUBMIT')]", "submit");
				clickXpath("//span[contains(text(),'Proceed')]", "");


			}}
		if(NVLPN!=null)
		{
			for(String lpn:NVLPN){
				verify("(//div[contains(.,'Successfully Scanned')])[7]");

				enterXpath("//input[@class='fieldmodebintobin form-control']", lpn, "LPN");
				clickXpath("//span[contains(text(),'SUBMIT')]", "submit");
				clickXpath("//span[contains(text(),'Proceed')]", "");


			}}
		if(LPNNVNU!=null)
		{
			verify("(//div[contains(.,'Successfully Scanned')])[7]");

			enterXpath("//input[@class='fieldmodebintobin form-control']", LPNNVNU, "LPN");
			clickXpath("//span[contains(text(),'SUBMIT')]", "submit");
			clickXpath("//div[contains(text(),'Enter')]");
			actionSendKeys("//div[contains(text(),'Enter')]", sourceBin);
			clickDownArrow();
			actionEnter();
			enterXpath("//input[@name='lpnQty']", readXL("PO", 2, "QUANTITY"));
			clickXpath("//span[contains(text(),'SUBMIT')]", "submit");
			clickXpath("//span[contains(text(),'Proceed')]", "proceed");
		}
	}
	public void Bin_Querry(String[] partnumber,String Bin) throws Exception{

		childTest = parantTest.createNode("LPN in Destination Bin");
		for(int i=0;i<=binPickPart.length-1;i++){
			if(partnumber[i].equals(partv)||partnumber[i].equals(partnv)||picktype.equalsIgnoreCase("CUSTLPN"))
			{
				String Query="select wmi.lpn,wmi.customerLpn,sum(wmi.quantity-wmi.pickedQuantity-wmi.requestedQuantity) from warehousematinbound wmi inner join partsinorder pio on pio.partsInOrderId=wmi.partsInOrderId inner join tblbin_m bin on wmi.binId=bin.binId inner join  tblpart_m pm on wmi.partId=pm.partId inner join ordersmaster o on pio.orderId=o.orderId where pm.partNumber='"+partnumber[i] +"'"+"and bin.binLocation='"+Bin +"'"+"and o.siteId='"+prop.getProperty("DESTINATION") +"'"+" and o.accountId='"+prop.getProperty("ACCOUNTID")+"'"+" and wmi.statusCodeId='STCM-8' group by wmi.lpn,wmi.customerLpn";
				System.out.println(Query);
				con=null;
				Class.forName("com.mysql.jdbc.Driver");
				con=DriverManager.getConnection(prop.getProperty("DBURL"), prop.getProperty("DBUSERNAME"), prop.getProperty("DBPASSWORD"));
				st=con.createStatement();
				rs=st.executeQuery(Query);
				DBLPN.clear();
				while(rs.next()){
					if(picktype.equalsIgnoreCase("CUSTLPN")){
						DBLPN.add(rs.getString("customerLpn").toUpperCase());
					}
					else{
						DBLPN.add(rs.getString("lpn"));
					}
				}
			}
			else{

				String Query="select wmi.partNumber,sum(wmi.quantity-wmi.pickedQuantity-wmi.requestedQuantity) as totalAvaliableQty from warehousematinbound wmi inner join partsinorder pio on pio.partsInOrderId=wmi.partsInOrderId inner join tblbin_m bin on wmi.binId=bin.binId inner join  tblpart_m pm on wmi.partId=pm.partId inner join ordersmaster o on pio.orderId=o.orderId where pm.partNumber='"+partnumber[i] +"'"+"and bin.binLocation='"+Bin +"'"+"and o.siteId='"+prop.getProperty("DESTINATION") +"'"+" and o.accountId='"+prop.getProperty("ACCOUNTID")+"'"+" and wmi.statusCodeId='STCM-8' group by wmi.partNumber";
				System.out.println(Query);
				con=null;
				Class.forName("com.mysql.jdbc.Driver");
				con=DriverManager.getConnection(prop.getProperty("DBURL"), prop.getProperty("DBUSERNAME"), prop.getProperty("DBPASSWORD"));
				childTest.pass("DB has Connected Successfully");
				st=con.createStatement();
				rs=st.executeQuery(Query);

				while(rs.next()){
					DBLPN.clear();
					DBLPN.add(rs.getString("totalAvaliableQty"));
				}
				System.out.println(DBLPN.get(0));
				DBQTY.clear();
				DBQTY.add((int) Float.parseFloat(DBLPN.get(0)));
			}

			if(partnumber[i].equals(partv)){
				if(DBLPN.containsAll(VLPN)){
					childTest.log(Status.INFO,MarkupHelper.createLabel("LPN : "+ VLPN + "  transfered to Bin :"+Bin,ExtentColor.CYAN));
				}else{
					childTest.fail("LPN : "+ VLPN + "  not available  in Destination Bin :"+Bin);
				}
			}
			else if(partnumber[i].equals(partnv)){
				if(DBLPN.containsAll(NVLPN)){
					childTest.log(Status.INFO,MarkupHelper.createLabel("LPN : "+ NVLPN + "  transfered to Bin :"+Bin,ExtentColor.CYAN));
				}else{
					childTest.fail("LPN : "+ NVLPN + "  not available  in Destination Bin :"+Bin);
				}
			}
			else if(partnumber[i].equals(partnvnu)&& picktype.equalsIgnoreCase("CUSTLPN")){
				if(DBLPN.contains(LPNNVNU)){
					childTest.log(Status.INFO,MarkupHelper.createLabel("LPN : "+ LPNNVNU + "  transfered to Bin :"+Bin,ExtentColor.CYAN));
				}else{
					childTest.fail("LPN : "+ LPNNVNU + "  not available  in Destination Bin :"+Bin);
				}
			}
			else{
				if(DBQTY.get(0).equals(DBQTY1+binPickPartquantity[2])){
					childTest.pass("The NVNU part Quantity "+ DBQTY + " = "+ (DBQTY1+binPickPartquantity[2]));
				}else{
					childTest.fail("The NVNU part Quantity "+ DBQTY + " Not Equal " + DBQTY1+binPickPartquantity[2] );
				}

			}
		}
	}


	public void single_Query(String partnumber , String Bin) throws Exception{
		childTest.log(Status.INFO,MarkupHelper.createLabel("DB validation has been started for : "+partnumber,ExtentColor.ORANGE));

		String Query="select wmi.partNumber,sum(wmi.quantity-wmi.pickedQuantity-wmi.requestedQuantity) as totalAvaliableQty from warehousematinbound wmi inner join partsinorder pio on pio.partsInOrderId=wmi.partsInOrderId inner join tblbin_m bin on wmi.binId=bin.binId inner join  tblpart_m pm on wmi.partId=pm.partId inner join ordersmaster o on pio.orderId=o.orderId where pm.partNumber='"+partnumber +"'"+"and bin.binLocation='"+Bin +"'"+"and o.siteId='"+prop.getProperty("DESTINATION") +"'"+" and o.accountId='"+prop.getProperty("ACCOUNTID")+"'"+" and wmi.statusCodeId='STCM-8' group by wmi.partNumber";
		//System.out.println(Query);
		con=null;
		Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection(prop.getProperty("DBURL"), prop.getProperty("DBUSERNAME"), prop.getProperty("DBPASSWORD"));
		st=con.createStatement();
		rs=st.executeQuery(Query);
		DBLPN.clear();

		while(rs.next()){
			DBLPN.add(rs.getString("totalAvaliableQty"));
		}
		if(DBLPN.size()==0)
		{
			DBLPN.add("0");
		}
		System.out.println(DBLPN.get(0));
		DBQTY.clear();
		DBQTY.add((int) Float.parseFloat(DBLPN.get(0)));
		System.out.println("The Qty :"+ DBQTY);
		DBQTY1= DBQTY.get(0);
		System.out.println("DBQTY1 : "+ DBQTY1);
	}
}

