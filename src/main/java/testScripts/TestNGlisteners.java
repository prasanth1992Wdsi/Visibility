package testScripts;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.IAlterSuiteListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.internal.BaseClassFinder;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.relevantcodes.extentreports.LogStatus;
import com.sun.mail.imap.Utility;




public class TestNGlisteners  extends CommonFunctions implements ITestListener  {

	
	
	public static ThreadLocal<ExtentTest> testReport = new ThreadLocal<ExtentTest>();	
		public void onTestStart(ITestResult result) {
			// TODO Auto-generated method stub
			//testReport.set(parantTest);
		}
		

	public void onTestSuccess(ITestResult result) {
		CommonFunctions.success++;
		CommonFunctions.extent.flush();

		// TODO Auto-generated method stub
		
	}

	public void onTestFailure(ITestResult result) {
		CommonFunctions.failed.add("Failed class  : "+result.getTestClass().getName()+"  method :  "+result.getMethod().getMethodName());
		CommonFunctions.fail++;

		try {
			//childTest.pass("failed",MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture1()).build());
			CommonFunctions.childTest.log(Status.FAIL,"The Script failed at the action of "+ CommonFunctions.click);
			CommonFunctions.childTest.log(Status.FAIL,"The failed  method is : "+result.getTestClass().getName()+result.getMethod().getMethodName(),MediaEntityBuilder.createScreenCaptureFromBase64String( CommonFunctions.Screencapture1()).build());
			CommonFunctions.childTest.info(result.getThrowable().fillInStackTrace().toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CommonFunctions.extent.flush();
		CommonFunctions.driver.quit();

		
	
		
		
	}
	
	
	public void onTestSkipped(ITestResult result) {
		CommonFunctions.skipped.add("Skipped class : "+result.getTestClass().getName()+"  method :  "+result.getMethod().getMethodName());
		CommonFunctions.skip++;	

		
		CommonFunctions.childTest.generateLog(Status.SKIP, "Test skipped class : "+result.getTestClass().getName()+" method : "+result.getMethod().getMethodName()).assignCategory("Skipped Functionalities");
		//CommonFunctions.childTest.log(Status.SKIP, result.getThrowable());
		CommonFunctions.childTest.generateLog(Status.SKIP, result.getMethod().getDescription());
		
		//CommonFunctions.extent.flush();
		//CommonFunctions.driver.quit();
		
		}

	
		
	

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		CommonFunctions.success++;
		
		
	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub

		
	}

	public void onFinish(ITestContext context) {
	
//			
		CommonFunctions.extent.flush();
		CommonFunctions.success1=CommonFunctions.success;
		CommonFunctions.fail1=CommonFunctions.fail;
		CommonFunctions.skip1=	CommonFunctions.skip;
//		try {
//			Desktop.getDesktop().browse(new File("Visibility.html").toURI());
//			//Desktop.getDesktop().open(new File("visibility.html"));
//		} catch (IOException e) {
//			System.out.println("failed");
//			e.printStackTrace();
//		}
		

	}
	

}
