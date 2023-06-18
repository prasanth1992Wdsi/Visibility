package testScripts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.testng.annotations.Test;

public class DispatchWithVehicle extends ProjectSpecificFunctions	 {
	@Test(priority = 0)
	public void DispatchVehicle() throws IOException, Exception {

		parantTest = extent.createTest("Dispatch with Vehicle as Gateintype(loading)");

		loginAsUser(Username);
		partv = "BFIFOV1";
		partnv = "BFIFONV1";
		partnvnu = "BFIFONVNU1";
		GateInType="LOADING";
		picktype = "FIFO";
		part = new String[] {  partnvnu };
		quantity = new String[] {  "2" };
		binPickPartquantity = new int[] { 2 };
		binPickPart = new String[] {  partnvnu };
		vehicleCreation(site);
		writeDataForCSV(picktype, "Newdelpoint", "so", part, quantity);
		pickgenerationFIFO();

		pickerassign1(SOCustReference);

		ArrayList<String> picked = pickingScreenFS("", SOCustReference, Arrays.asList(binPickPart),
				binPickPartquantity);
		skipConsolidation(SOCustReference);

		 disp(SOCustReference,"vehicle",picked);
			//dispatch2(SOCustReference);
	}

}
