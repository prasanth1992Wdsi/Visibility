package testScripts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.testng.annotations.Test;

public class HZLLiveIssues extends ProjectSpecificFunctions{
	@Test(priority=10,alwaysRun=true)
	public void Live_13501() throws IOException, Exception {
		

			partv="VPARTLIVE";
			partnv="NVPARTLIVE";
			partnvnu="NVNUPARTLIVE";
			picktype="FIFOSUGGESTED";

			parantTest = extent.createTest("LIVE_13501 : HZL STO status is PICKED in VSB orderd dashboard , but data not moved to OPTIMIZATION)");    

			loginAsUser(Username);
			
			//site="IN01055";
			VONS=new ArrayList<String>();
			vehicleCreation(site);
			part=new String[]{partnv,partv,partnvnu};
				quantity=new String[]{"5","5","5"};
				binPickPart=new String[]{partnv,partv,partnvnu};
				binPickPartquantity=new int[]{5,5,5};
		    writeDataForCSV(picktype,"Newdelpoint","po", part,quantity);

				
				
				picktype="FIFOSUGGESTEDCANCEL2";
				part=new String[]{partnvnu};
				quantity=new String[]{"1"};
				binPickPart=new String[]{partnvnu};
				binPickPartquantity=new int[]{1};
				writeDataForCSV(picktype,"Endcustomer","so", part,quantity);
				pickgenerationFIFO();
				for(String customerref:CustReference1)
				{
				pickerassign1(customerref);
VONS.add(VON);
				}
				//pickingScreenFS();
				
				for(String customerref:CustReference1)
				{
				ArrayList<String>picked=pickingScreenFS("",customerref,Arrays.asList(part),binPickPartquantity);
				clickXpath(CLEAR, "clear");
				}
				int i=0;
				for(String customerreflive:CustReference1) {
					consolidation(customerreflive, VONS.get(i++));
					driver.navigate().refresh();
				}
				for(String customerref:CustReference1) {
				childTest = parantTest.createNode(" Order Dispatch ");
				clickXpath(DISPATCHGRID,"DISPATCHGRID");
				verify("(//*[@id='Despatch_REQUESTNO'])[1]");
				enterXpath(DISPATCHSEARCH, SOCustReference,"DISPATCHSEARCH");
				childTest.info("The order available in Dispatch Screen");
				}
	}
				

}
