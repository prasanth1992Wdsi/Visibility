package testScripts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class PostPickCancellation extends ProjectSpecificFunctions	 {

	public ArrayList<String> CustReference1;
	@Test
public void postcancel() throws Exception
{OrderLevel() ;
QuantityLevel();
PartLevel();
LPNLevel();
}
	//@Test(priority = 24,alwaysRun=true)
	public void OrderLevel() throws IOException, Exception {

		parantTest = extent.createTest("Outbound_cancellations : Order Level");

		loginAsUser(Username);
		partv = "BFIFOV1";
		partnv = "BFIFONV1";
		partnvnu = "BFIFONVNU1";

		picktype = "FIFO";
		part = new String[] { partv, partnv, partnvnu };
		quantity = new String[] { "2", "2", "2" };
		binPickPartquantity = new int[] { 2, 2, 2 };
		binPickPart = new String[] { partnv, partv, partnvnu };
		
		writeDataForCSV(picktype, "Newdelpoint", "so", part, quantity);
		pickgenerationFIFO();

		pickerassign1(SOCustReference);

		ArrayList<String> picked = pickingScreenFS("", SOCustReference, Arrays.asList(binPickPart),
				binPickPartquantity);
		Thread.sleep(2000);
		clickIDLinkText("Misc");

		clickIDLinkText("Cancellation");
		cancellation(SOCustReference, "", null, 0, 1);
		for (int i = 1; i <= 4; i++) {

			selectByvalue(CANCEL_LEVEL, i);
			enterXpath(CANCEL_SOSEARCH, SOCustReference, "CANCEL_SOSEARCH");
			actionEnter();

		}
		childTest.info("Verification complete the cancelled order not listed in the cancellation screen");
		PostPickCancellationValidation();
	}
	//@Test(priority = 25,alwaysRun=true)
	public void QuantityLevel() throws IOException, Exception {
		parantTest = extent.createTest("Outbound_cancellations : Quantity Level");
		
		partnvnu = "BFIFONVNU1";

		picktype = "FIFO";
		part = new String[] {partnvnu };
		quantity = new String[] {"2" };
		binPickPartquantity = new int[] { 2 };
		binPickPart = new String[] {partnvnu };

		writeDataForCSV(picktype, "Newdelpoint", "so", part, quantity);
		pickgenerationFIFO();

		pickerassign1(SOCustReference);

		ArrayList<String> picked = pickingScreenFS("", SOCustReference, Arrays.asList(binPickPart),
				binPickPartquantity);
		Thread.sleep(2000);
		clickIDLinkText("Misc");

		clickIDLinkText("Cancellation");
		cancellation(SOCustReference, partnvnu, null,2 , 4);
		childTest.info("Quantity level cancellation has been performed successfully");
		PostPickCancellationValidation();
		
	}
//	@Test(priority = 26,alwaysRun=true)
	public void PartLevel() throws IOException, Exception {
		parantTest = extent.createTest("Outbound_cancellations : Part Level");

		
		partv = "BFIFOV1";

		picktype = "FIFO";
		part = new String[] {partv };
		quantity = new String[] {"2" };
		binPickPartquantity = new int[] { 2 };
		binPickPart = new String[] {partv };

		writeDataForCSV(picktype, "Newdelpoint", "so", part, quantity);
		pickgenerationFIFO();

		pickerassign1(SOCustReference);

		ArrayList<String> picked = pickingScreenFS("", SOCustReference, Arrays.asList(binPickPart),
				binPickPartquantity);
		Thread.sleep(2000);
		clickIDLinkText("Misc");

		clickIDLinkText("Cancellation");
		cancellation(SOCustReference, partv, "", 0, 2);
		childTest.info("Part level cancellation has been performed successfully");
		PostPickCancellationValidation();
		
	}
	//@Test(priority = 27,alwaysRun=true)
	public void LPNLevel() throws IOException, Exception {
		parantTest = extent.createTest("Outbound_cancellations : LPN Level");
		
		partnv = "BFIFONV1";

		picktype = "FIFO";
		part = new String[] {partnv };
		quantity = new String[] {"2" };
		binPickPartquantity = new int[] { 2 };
		binPickPart = new String[] {partnv };

		writeDataForCSV(picktype, "Newdelpoint", "so", part, quantity);
		pickgenerationFIFO();

		pickerassign1(SOCustReference);

		ArrayList<String> picked = pickingScreenFS("", SOCustReference, Arrays.asList(binPickPart),
				binPickPartquantity);
		Thread.sleep(2000);
		clickIDLinkText("Misc");

		clickIDLinkText("Cancellation");
		String LPN1=NVLPNpicking.get(0);
		String LPN2=NVLPNpicking.get(1);
		cancellation(SOCustReference, "", LPN1, 0, 3);
		cancellation(SOCustReference, "", LPN2, 0, 3);
		childTest.info("LPN level cancellation has been performed successfully");
		PostPickCancellationValidation();
		logOut();
		
	}



	public void PostPickCancellationValidation() throws Exception {
		clickXpath(INBOUND, "Inbound");
		clickIDLinkText(BINNERASSIGNMENT_MENU);
		enterXpath(SEARCH_BOX, SOCustReference, "binnerAssignment SEARCH");
		clickXpath(SEARCH_BTN, "SEARCH_BTN");
		childTest.pass("After postpick cancellation the order is available in Binner assignment.");
		clickXpath(BinnerAssignmentInfo, "BinnerAssignmentInfo");

		Thread.sleep(5000);

		WebElement PostPick = driver.findElement(By.xpath("//*[@id='simple-popper']/div/p/div[2]"));
		String PostPick_VON = PostPick.getText().trim();
		System.out.println("After PostPick Cancellation : " + PostPick_VON);
		if (PostPick_VON.contains(VON)) {
			System.out.println("Both VON are matched");
			childTest
					.info("The Sales Order VON is available in binner assignment screen after Post Pick Cancellation.");
			childTest.info(PostPick_VON);

		} else {
			System.out.println("Both VON are different");
		}

		
	}
}
