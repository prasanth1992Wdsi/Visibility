package testScripts;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.model.ScreenCapture;
import com.aventstack.extentreports.observer.ExtentObserver;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.sun.tools.xjc.reader.xmlschema.ErrorReporter;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.internal.util.IOUtils;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


public class CommonFunctions extends objectRepositary.ObjectRepository {
	public String VON;
	public String VON1;
	public String VON2;
	public String CustReferenceV;
	public String CustReferenceNV;
	public String CustReferenceNVNU;
	String BINNINGQTY="";
	String[] qty;
	public static String partnvnu1;
	public static String partv1;
	public static String partnv1;
	public String Reference_2;
	public String Reference_3;
	public static int success=0;
	public static File file;
	public static int fail=0;
	public static int skip=0;
	public static int success1=0;
	public static int fail1=0;
	public static int skip1=0;
	public String VehicleID;
	public int NVquantity5;
	public String NVquantity0;
	public String NVquantity1;
	public String NVquantity2;
	public String NVquantity3;
	public String NVquantity4;
	public String nvpartBox;
	public String nvnupartBox;
	public String vpartBox;

	public String VLPN1;
	public int qtn1;
	public int qtn2;
	public int qtn3;
	public int qtn4;
	public int qtn5;
	public String LPNV2;
	public String LPNV;
	public String LPNVb;
	public String LPNV1;
	public String LPNNVNU;
	public static String partv;
	public static String partnv;
	public static String partnvnu;
	public static String CustomerrefSO;
	public static String param;
	public static String paramWarehouse;
	public String VCUSLPN1;
	public String VCUSLPN2;
	public String NVCUSLPN3;
	public String NVQTY;
	public String NVCUSLPN4;
	public String NVCUSLPN5;
	public String NVCUSLPN6;
	public String Vquantity;
	public String Vquantity1;

	public String picktype;
	public String NVquantity;
	public String NVNUquantity;
	public	static ResultSet rs;
	public	static Statement st;
	public static Connection con;
	public static int pickedQuantity;
	public static int lockedQuantity;
	public static int quantity;
	public static String partnumber;
	public static int available;
	public static  WebDriver driver;
	public static String Customerref;
	public FluentWait<WebDriver> fluentwait;
	public WebDriverWait wait;;
	public static Properties prop1;
	public Properties prop;
	public static String CartLabel;
	static File fis = null;
	static jxl.Sheet sheet1 = null;
	public static HashMap<String, String> map1;
	static Workbook workbook = null;
	public String CustReference;
	public ArrayList<String> CustReference1;
	public ArrayList<String> VONS;


	public String SOCustReference;
	public String CustRefNo;
	public String CustLPN;
	public String NvQtn;
	public String NVNUQtn;
	public String NVLPN;
	public String NVNULPN=null;
	public String BOX;
	public String BOX1;
	public String NVCUSLPN1;
	public String NVCUSLPN2;
	public String NVNUCUSLPN1;
	public int NVQUANTITY;
	public String VQTY;
	public String VQTY1;
	public String VQTY2;
	public int VPQTY;
	public String NVNUQTY;
	public int NVNUQUTY;
	public int NVNUVPQTY;	
	public String alternativeLPNNVNU;
	public String alternativeNVLPN1;
	public String serialnumber3;
	public String alternativeNVLPN2;
	public String randomNVNULPN;
	public String randomNVLPN1;
	public String randomNVLPN2;
	public String shortNVNULPN;
	public String shortNVLPN1;
	public String serialnumber1;
	public String serialnumber2;
	public String VCUSLPN7;
	public String NVNUpart;
	public String NVpart;
	public String CUSLPN=null;
	public String NVNULPNFMFO;
	public String FMFOserialnumber1;
	public String FMFOserialnumber2;
	public String NVNULPNFS;
	public String FSserialnumber1;
	public String FSserialnumber2;
	public String grnclassName;

public String SoType=null;
	public static ArrayList<String> skipped=new ArrayList<String>();
	public static ArrayList<String> failed=new ArrayList<String>();

	public Robot robot ;
	public String label;
	public String shortPartType;
	Object HtmlReport;
	public static ExtentReports extent;
	public static ExtentTest parantTest;
	public static ExtentTest childTest;
	public ExtentTest logger;
	public Actions builder;
	public ArrayList<String> CustRefList;
	public String[] myArray;
	public String PartType;
	public String partName;
	public static String click;
	public static HashMap<String,Integer> locked =new HashMap<String,Integer>();
	public static HashMap<String,Integer> Picked =new HashMap<String,Integer>();
	public static HashMap<String,Integer> Available =new HashMap<String,Integer>();
	public static HashMap<String,Integer> Quantity =new HashMap<String,Integer>();
	public static HashMap<String,String> data;
	public String Consignee;


	public ArrayList<String > PickedNVLPNS;

	public String V;
	public String Vlpn;
	public int QTY1;
	public String celtext;
	public String[] num;
	public String[] num1;
	public String[] num2;
	public String num_0;
	public String Partial_LPNQTY;
	public String LPN;
	public String pdf;
	public String currentdate;
	public Date date;
	public String pdffilename;
	public File folder ;
	public  ArrayList<String> stockNumber = new ArrayList<String>();
	public  ArrayList<String> stockNumberkit = new ArrayList<String>();
	public HashMap<String, String>  Lpn=null ;
	public int backOrderCount=0;
	public int availableQtyCount=0;
	public boolean backAvailable=true;

	public static int columnSize=0;

	public String Bin_No = "BN00035648";
	public int NVNUtoatlqty;
	public String NVNUQTY1 = "5";
	public String OrderId ;
	public  ArrayList<String> BoxNumber = new ArrayList<String>();
	public  ArrayList<String> BINNumber = new ArrayList<String>();
	public  ArrayList<String> packingslip = new ArrayList<String>();
	public  ArrayList<String> NVNUqty11 = new ArrayList<String>();
	public  ArrayList<String> RELPN = new ArrayList<String>();
	public  ArrayList<String> VLPN =null;
	public ArrayList<String> cart=null;
	public String site=null;
	public String account=null;
	public String STOCK_TYPE;
	public String GateInType;


	
	public   String readXL(String poRso,int line,String header) throws Exception {
		String inputfile=poRso.equalsIgnoreCase("PO")?System.getProperty("user.dir")+"//Automation_PO.csv":System.getProperty("user.dir")+"//upload-so.csv";
		ArrayList<HashMap<String,String>> alist = new ArrayList<HashMap<String,String>>();
		CSVReader reader = new CSVReader(new FileReader(inputfile));

		List<String[]> csvBody = reader.readAll();
		int rowSize=countRows(inputfile);
		columnSize=csvBody.get(0).length;
		for(int rows=1;rows<rowSize;rows++) {
			HashMap<String,String> hlist=new HashMap<String, String>();
			for(int colms=0;colms<columnSize;colms++) {
				hlist.put(csvBody.get(0)[colms], csvBody.get(rows)[colms]);
			}
			alist.add(hlist);
		}
		reader.close();
		return alist.get(line).get(header);
	}
	public  int countRows(String inputfile) {



		int lines = 0;

		try (LineNumberReader lnr = new LineNumberReader(new FileReader(inputfile))) {

			while (lnr.readLine() != null) ;

			lines = lnr.getLineNumber();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return lines;

	}
	public  void  writeDataForCSV(String picktype,String deliverytype,String porSo,String[] part,String[]quantity) throws Exception
	{readPropertyFile();

	String inputfile=porSo.equalsIgnoreCase("PO")?System.getProperty("user.dir")+"//Automation_PO.csv":System.getProperty("user.dir")+"//upload-so.csv";
	if(porSo.contains("returnorder")){
		
		 inputfile=System.getProperty("user.dir")+"//Automation_PO.csv";
		 }
	String DESTINATION=prop.getProperty("DESTINATION");
	//String	PART_NUMBER[]={"part"};
	String STOCK_TYPE=prop.getProperty("STOCK_TYPE");
	String LPN="";
	String CUST_BOX_LABEL="";
	String SERIAL_NUMBER="";
	String BATCH_NUMBER="";
	String MANUFACTURING_DATE="";
	String EXPIRY_DATE="";
	String QUANTITY="";
	String	REFERENCE_1=prop.getProperty("REFERENCE_1");
	String	REFERENCE_2=prop.getProperty("REFERENCE_2");
	String  REFERENCE_3=prop.getProperty("REFERENCE_3");
	String PART_REF=prop.getProperty("PART_REF");
	String SERVICE_TYPE=prop.getProperty("SERVICE_TYPE");
	String DELIVERY_TYPE=deliverytype;
	String DESTINATION_CODE=deliverytype.equalsIgnoreCase("intersite")?prop.getProperty("site2"):"sp42";
	String ENGINEER_ID=deliverytype.equalsIgnoreCase("Newdelpoint")?prop.getProperty("ENGINEER_ID"):"";
	String ADDRESS_LINE_1=deliverytype.equalsIgnoreCase("Newdelpoint")?prop.getProperty("ADDRESS_LINE_1"):"";
	String ADDRESS_LINE_2="";
	String ADDRESS_LINE_3="";
	String CITY=deliverytype.equalsIgnoreCase("Newdelpoint")?prop.getProperty("CITY").trim():"";
	String STATE=deliverytype.equalsIgnoreCase("Newdelpoint")?prop.getProperty("STATE").trim():"";
	String COUNTRY=deliverytype.equalsIgnoreCase("Newdelpoint")?prop.getProperty("COUNTRY").trim():"";
	String PINCODE=deliverytype.equalsIgnoreCase("Newdelpoint")?prop.getProperty("PINCODE").trim():"";
	String CONTACT_NAME=deliverytype.equalsIgnoreCase("Newdelpoint")?prop.getProperty("CONTACT_NAME").trim():"";
    String PHONE_NUMBER=deliverytype.equalsIgnoreCase("Newdelpoint")?prop.getProperty("PHONE_NUMBER").trim():"";
	MANUFACTURING_DATE=picktype.contains("FMFO")?"22-02-2022":"";
	SERIAL_NUMBER=picktype.equalsIgnoreCase("SERIALNUMBER")?"S1":"";
	EXPIRY_DATE=picktype.contains("FEFO")?"22-02-2023":"";
	BATCH_NUMBER=picktype.contains("BATCH")?"B1":"";
	String LPN1=picktype.contains("CUSTLPN")? getAlphaNumericString(4).trim():"";
	String box1=picktype.contains("CUSTLPNBOX")? getAlphaNumericString(4).trim():"";
	CustReference1=new ArrayList<String>();
	String JSON="";
	List<String> all=Arrays.asList(part);
	System.out.println(all);


	String CustReference2="";


	try {

		// create CSVWriter with ',' as separator
		CSVWriter writer = new CSVWriter(new FileWriter(inputfile), ',',
				CSVWriter.NO_QUOTE_CHARACTER,
				CSVWriter.DEFAULT_ESCAPE_CHARACTER,
				CSVWriter.DEFAULT_LINE_END);

		// Header array

		String []poHeader={"DESTINATION"	,"CONSIGNEE","PART_NUMBER",	"STOCK_TYPE"	,"CUSTOMER_REFERENCE",	"LPN",	"CUST_BOX_LABEL"	,"SERIAL_NUMBER"	,"BATCH_NUMBER",	"MANUFACTURING_DATE",	"EXPIRY_DATE",	"QUANTITY",	"REFERENCE1",	"REFERENCE2",	"REFERENCE3",	"PART_REF",	"INVOICE_NUMBER"	,"VERSION_NUMBER",	"SOURCE_ID",	"SOURCE_TYPE"};
		String []soHeader={"CUSTOMER_REFERENCE","PART_NUMBER"	,"STOCK_TYPE",	"QUANTITY",	"LPN",	"SERIAL_NUMBER",	"BATCH_NUMBER",	"REFERENCE_1",	"REFERENCE_2",	"REFERENCE_3",	"PART_REF","PRIORITY",	"SERVICE_TYPE",	"DELIVERY_TYPE",	"DESTINATION_CODE","CONSIGNEE",	"ENGINEER_ID",	"ADDRESS_LINE_1",	"ADDRESS_LINE_2"	,"ADDRESS_LINE_3"	,"CITY"	,"STATE"	,"COUNTRY",	"PINCODE" };
		String []returnHeader={"DESTINATION","PART_NUMBER",	"STOCK_TYPE","CUSTOMER_REFERENCE","LPN","SERIAL_NUMBER"	,"BATCH_NUMBER",	"MANUFACTURING_DATE",	"EXPIRY_DATE",	"QUANTITY",	"REFERENCE1",	"REFERENCE2",	"REFERENCE3",	"PART_REF",	"INVOICE_NUMBER"	,"VERSION_NUMBER",	"SOURCE_ID",	"SOURCE_TYPE","RETURN_REASON"};
		List<String[]> data = new ArrayList<String[]>();
		if(porSo.equalsIgnoreCase("PO"))
		{	

			CustReference=CustReference2;
			//addheader
			
			
			data.add(poHeader);
			
			//adddatalines to the list
			int count=picktype.contains("CANCEL")?Integer.parseInt(""+picktype.charAt(picktype.length()-1)):1;
			for(int j=0;j<count;j++)
			{
				if(!picktype.contains("MULTI"))
				{
			CustReference="AP_"+getAlphaNumericString(8).trim();
			CustReference1.add(CustReference);
				}
				for(int i=0;i<part.length;i++)
			{//Add PO data with same format
				if(picktype.contains("MULTI"))
				{
					CustReference="AP_"+getAlphaNumericString(8).trim();
					CustReference1.add(CustReference);

				}
				LPN=picktype.contains("CUSTLPN")?LPN1+i:"";
				CUST_BOX_LABEL=picktype.contains("CUSTLPNBOX")?box1+i:"";

if(picktype.contains("API"))
{

	Consignee=Consignee==null?"":Consignee;
	
 JSON+=""+"{\r\n" + "\"destination\": \""+site+"\",\r\n" + 
			"\"partNumber\": \""+part[i]+"\",\r\n" + 
			"\"stockTypeCode\": \"GOOD\",\r\n" + 
			"\"customerReference\": \""+CustReference+"\",\r\n"+ 
			"\"consignee\": \""+Consignee+"\",\r\n"+ 
			"\"quantity\": \""+quantity[i]+"\",\r\n" + 
			"\"lpn\": \""+LPN+"\",\r\n" + 
			"\"customerBoxLabel\": \""+CUST_BOX_LABEL+"\",\r\n" + 
			"\"invoiceNumber\": \"INV001\",\r\n" + 
			"\"serialNumber\": \""+SERIAL_NUMBER+"\",\r\n" + 
			"\"batchNumber\": \""+BATCH_NUMBER+"\",\r\n" + 
			"\"manufacturingDate\": \""+MANUFACTURING_DATE+"\",\r\n" + 
			"\"expiryDate\": \""+EXPIRY_DATE+"\",\r\n" + 
			"\"reference1\": null,\r\n" + 
			"\"reference2\": \"DEMO\",\r\n" + 
			"\"reference3\": \"DEMO\",\r\n" + 
			"\"returnReason\": null,\r\n" + 
			"\"partRef\": \"P8979\"\r\n" + 
			"\r\n" + 
			"}\r\n" + "";
 if(!(i==part.length-1))
 {
	 JSON+=","; 
 }
	data.add(new String[] {site,Consignee,part[i],STOCK_TYPE,CustReference,LPN,CUST_BOX_LABEL,SERIAL_NUMBER,BATCH_NUMBER,MANUFACTURING_DATE,EXPIRY_DATE,quantity[i],REFERENCE_1,REFERENCE_2,REFERENCE_3,PART_REF,"","","","" });

	

}else{
				data.add(new String[] {site,Consignee,part[i],STOCK_TYPE,CustReference,LPN,CUST_BOX_LABEL,SERIAL_NUMBER,BATCH_NUMBER,MANUFACTURING_DATE,EXPIRY_DATE,quantity[i],REFERENCE_1,REFERENCE_2,REFERENCE_3,PART_REF,"","","","" });
}
				//childTest.log(Status.INFO, MarkupHelper.createJsonCodeBlock(new String[] {prop.getProperty("SITEID"),part[i],STOCK_TYPE,CustReference,LPN,CUST_BOX_LABEL,SERIAL_NUMBER,BATCH_NUMBER,MANUFACTURING_DATE,EXPIRY_DATE,quantity[i],REFERENCE_1,REFERENCE_2,REFERENCE_3,PART_REF,"","","","" }));
			}
			}
			if(picktype.contains("API"))
			{
				param="{\r\n" + 
						"\"orderTypesMasterId\":\"OTYM-1\",\r\n" + 
						"\"siteMasterId\":\""+site+"\",\r\n" + 
						"\"sendMail\":true,\r\n" + 
						"\"isSave\":true,\r\n" + 
						"\"partialSave\":true,\r\n" +
						"\"accountMasterId\":\""+account+"\",\r\n" + 
						"\"actorType\":\"AM001\",\r\n" + 
						"\"vehicleAssigned\": false,\r\n" + 
						"\"replen\":[\r\n"+ JSON +
						"]\r\n" + 
						"}\r\n" + 
						"";
				Tocken_generation("PO");

			}
			}else if(porSo.equalsIgnoreCase("returnorder"))
			{

				CustReference=SOCustReference;
			

				data.add(returnHeader);

				//adddatalines to the list
				int count=picktype.contains("CANCEL")?Integer.parseInt(""+picktype.charAt(picktype.length()-1)):1;
				for(int j=0;j<count;j++)
				{
					
				//	System.out.println("ILOOP"+part.length);
					for(int i=0;i<part.length;i++)
					{//Add PO data with same format
						if(picktype.contains("CUSTLPN"))
						{
							//CustReference="AP_"+getAlphaNumericString(8).trim();
							//CustReference1.add(CustReference);
						/*	for(String apicking:PickedNVLPNS)
							{*/
							LPN=picktype.contains("CUSTLPN")?PickedNVLPNS.get(i):"";
							
							data.add(new String[] {site,part[i],STOCK_TYPE,CustReference,LPN,SERIAL_NUMBER,BATCH_NUMBER,MANUFACTURING_DATE,EXPIRY_DATE,quantity[i],REFERENCE_1,REFERENCE_2,REFERENCE_3,PART_REF,"","","","" });
						//}
							
						}
					
						else if(!picktype.contains("CUSTLPN"))
						
						{
							 data.add(new String[] {site,part[i],STOCK_TYPE,CustReference,"",SERIAL_NUMBER,BATCH_NUMBER,MANUFACTURING_DATE,EXPIRY_DATE,quantity[i],REFERENCE_1,REFERENCE_2,REFERENCE_3,PART_REF,"","","","" });
						}
					
					}
					
					}}
		else
		{	
			
			data.add(soHeader);
			
			//childTest.log(Status.INFO, MarkupHelper.createUnorderedList(Arrays.asList(soHeader)));
			int count=picktype.contains("CANCEL")?Integer.parseInt(""+picktype.charAt(picktype.length()-1)):1;

			//adddatalines to the list
			for(int j=0;j<count;j++)
			{if(!picktype.contains("MULTI"))
			{
		CustReference2="AP_"+getAlphaNumericString(8).trim();
		CustReference1.add(CustReference2);
		SOCustReference=CustReference2;

			}
				for(int i=0;i<part.length;i++)
				{//ADD SO data with same format
					if(picktype.contains("MULTI"))
					{
						CustReference2="AP_"+getAlphaNumericString(8).trim();
						CustReference1.add(CustReference2);
						SOCustReference=CustReference2;


					}
					LPN=picktype.startsWith("CUSTLPN")?readXL("PO",i, "LPN"):"";
					//System.out.println(part[i]+STOCK_TYPE+quantity[i]+readXL("PO",i, "LPN")+CustReference1);
					if(picktype.contains("API"))
					{
						 JSON+=""+	"	{\r\n" + 
									"	\"customerReference\": \""+CustReference2+"\",\r\n" + 
									"	\"partNo\": \""+part[i]+"\",\r\n" + 
									"	\"stockTypeCode\": \"good\",\r\n" + 
									"	\"quantity\": \""+quantity[i]+"\",\r\n" +
									"	\"lpn\": \""+LPN+"\",\r\n" + 
									"	\"serialNo\": \""+SERIAL_NUMBER+"\",\r\n" + 
									"	\"batchNo\": \""+BATCH_NUMBER+"\",\r\n" + 
									"	\"manufacturingDate\": \"\",\r\n" + 
									"	\"expiryDate\": \"\",\r\n" + 
									"	\"reference1\": \"10\",\r\n" + 
									"	\"reference2\": \"\",\r\n" + 
									"	\"reference3\": \"\",\r\n" + 
									"\"consignee\": \""+Consignee+"\",\r\n"+ 
									"	\"partRef\": null,\r\n" + 
									"	\"serviceTypeCode\": \"SAME DAY\",\r\n" + 
									"	\"deliveryTypeCode\": \""+DELIVERY_TYPE+"\",\r\n" + 
									"	\"destinationCode\": \""+DESTINATION_CODE+"\",\r\n" + 
									"	\"addressLine_1\": \""+ADDRESS_LINE_1+"\",\r\n" + 
									"	\"addressLine_2\": \""+ADDRESS_LINE_2+"\",\r\n" + 
									"	\"addressLine_3\": \""+ADDRESS_LINE_3+"\",\r\n" + 
									"	\"city\": \""+CITY+"\",\r\n" + 
									"	\"state\": \""+STATE+"\",\r\n" + 
									"	\"country\": \""+COUNTRY+"\",\r\n" + 
									"    \"pinCode\": \""+PINCODE+"\",\r\n" + 
                                    "   \"contactName\": \""+CONTACT_NAME+"\",\r\n" + 
                                    "   \"phoneNo\": \""+PHONE_NUMBER+"\"\r\n" +
									"	}\r\n" + "";
						 if(!(i==part.length-1))
						 {
							 JSON+=","; 
						 }
							
							data.add(new String[] {CustReference2,	part[i],STOCK_TYPE,quantity[i],LPN,	SERIAL_NUMBER,	BATCH_NUMBER,	REFERENCE_1,	REFERENCE_2,	REFERENCE_3,	PART_REF	,"",SERVICE_TYPE	,DELIVERY_TYPE	,DESTINATION_CODE,Consignee,	ENGINEER_ID,	ADDRESS_LINE_1,	ADDRESS_LINE_2,	ADDRESS_LINE_3,	CITY,	STATE,	COUNTRY,	PINCODE});
	
					}else
					{
					data.add(new String[] {CustReference2,	part[i],STOCK_TYPE,quantity[i],LPN,	SERIAL_NUMBER,	BATCH_NUMBER,	REFERENCE_1,	REFERENCE_2,	REFERENCE_3,	PART_REF	,"",SERVICE_TYPE	,DELIVERY_TYPE	,DESTINATION_CODE,Consignee,	ENGINEER_ID,	ADDRESS_LINE_1,	ADDRESS_LINE_2,	ADDRESS_LINE_3,	CITY,	STATE,	COUNTRY,	PINCODE});
					}//childTest.log(Status.INFO, MarkupHelper.createJsonCodeBlock(new String[] {CustReference,	part[i],STOCK_TYPE,quantity[i],LPN,	SERIAL_NUMBER,	BATCH_NUMBER,	REFERENCE_1,	REFERENCE_2,	REFERENCE_3,	PART_REF	,SERVICE_TYPE	,DELIVERY_TYPE	,DESTINATION_CODE,	ENGINEER_ID,	ADDRESS_LINE_1,	ADDRESS_LINE_2,	ADDRESS_LINE_3,	CITY,	STATE,	COUNTRY,	PINCODE}));

				}


			}
			if(picktype.contains("API"))
			{
				param="	{\r\n" + 
						"	\"siteMasterId\": \""+site+"\",\r\n" + 
						"	\"accountMasterId\": \""+account+"\",\r\n" + 
						"	\"orderTypesMasterId\": \"OTYM-2\",\r\n" + 
						"	\"salesOrderInput\": [\r\n" + JSON +
				"]\r\n" + 
				"}\r\n" + 
				"";
				Tocken_generation("SO");

			}}
		writer.writeAll(data);

		// closing writer connection
		writer.close();
	}
	catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

	public void Tocken_generation(String porSo) throws Exception
	{
		//randomizerapi(porSo);
		System.out.println(Customerref);
		HashMap da =new HashMap();
		da.put("userName",prop.getProperty("USERNAME"));
		da.put("password",prop.getProperty("PASSWORD"));
		System.out.println(da);   	
		Response vr= RestAssured
				.given()
				.contentType("application/json")
				.body(da)
				.when()
				.post(prop.getProperty("ORDERAPI")+"token/authenticate");
		System.out.println(param);
		String response=vr.asString();
		System.out.println(response);
		String tocken=JsonPath.from(response).get("tocken");
		System.out.println("Tocken - " + tocken);
		System.out.println(param);
		Response r1=null;
if(porSo.equalsIgnoreCase("PO"))
{

		 r1 = RestAssured.given().log().all().config(RestAssured.config()
				.encoderConfig(EncoderConfig.encoderConfig()
						.encodeContentTypeAs("x-www-form-urlencoded",
								ContentType.URLENC)))
				.contentType("x-www-form-urlencoded")
				.header("Content-Type","application/x-www-form-urlencoded")
				.header("AppId","002")
				.header("timezone","Asia/Calcutta")
				.header("Authorization","Bearer "+tocken)
				.formParam("paramWarehouse",param)
				//.post(prop.getProperty("ORDERAPI")+"api/import/importOrder");
				.post(prop.getProperty("ORDERAPI")+"api/visibility/api/import/importOrder");
}else
{
	 r1 = RestAssured.given().log().all().config(RestAssured.config()
			.encoderConfig(EncoderConfig.encoderConfig()
					.encodeContentTypeAs("x-www-form-urlencoded",
							ContentType.URLENC)))
			.contentType("x-www-form-urlencoded")
			.header("Content-Type","application/x-www-form-urlencoded")
			.header("AppId","002")
			.header("timezone","Asia/Calcutta")
			.header("Authorization","Bearer "+tocken)
			.formParam("paramWarehouse",param)
			.post(prop.getProperty("ORDERAPI")+"api/visibility/api/upload/Orders");
}
		String response1=r1.asString();
		System.out.println("Response: "+ response1);
		int index =response1.indexOf("importStatus");
		String importstatus = response1.substring(index+14,index+18);
		System.out.println(importstatus);
		/*if(importstatus.equalsIgnoreCase("true")){
			System.out.println("Inbound Order Created");
			childTest.pass("Inbound order Created through Api,CustREference is  : "+ Customerref );
		}
		else{
			childTest.fail("Inbound order not  Created through Api");
			//Assert.fail("Inbound order not  Created through Api"); 
		}*/
	}


	public void soreadDataCancel() throws Exception{
		List<CSVRecord> data = readexcel(prop.getProperty("SOPATH"));
		SOCustReference = data.get(0).get("CUSTOMER_REFERENCE");
		Reference_2=data.get(0).get("REFERENCE_2");
		Reference_3=data.get(0).get("REFERENCE_3");
	}


	enum Module{
		TEMPORDERCREATED,REJECTORDER,TRIGGEREDORDER,HOLD,HOLDPART,CANCEL,INBOUNDORDERCREATION,POD,BINNING,PARTIALPICKING,PARTIALBINNING,SOCREATION,PICKASSIGN,PICKING,CONSOLIDATION,DISPATCH
	}


	public void LPNStatus(String lpn,Module status) throws Exception
	{

		String Query="select lpn,customerLpn,statusCodeDesc,partNumber, customerReference from warehousematinbound wmi inner join tblstatuscode_m st ON st.statusCodeId = wmi.statusCodeId inner join partsinorder pio on pio.partsInOrderId=wmi.partsInOrderId inner join ordersmaster om on om.orderId=pio.orderId WHERE lpn='"+lpn+"'";
		con=null;
		Class.forName("com.mysql.jdbc.Driver");
		//con=DriverManager.getConnection("jdbc:mysql://13.126.173.6:3306/12032020VSB?useSSL=false", "root", "sqladmin");
		//con=DriverManager.getConnection("jdbc:mysql://52.66.237.14:3306/sp11prod?useSSL=false", "root", "c7GdWQzjdeTEqvwf");
		con=DriverManager.getConnection(prop.getProperty("DBURL"), prop.getProperty("DBUSERNAME"), prop.getProperty("DBPASSWORD"));
		st=con.createStatement();
		rs=st.executeQuery(Query);
		data=new HashMap<String,String>();
		ArrayList<String> LPN=new ArrayList<String>();
		ArrayList<String> statuscode=new ArrayList<String>();

		if (rs.next()) {
			LPN.add(rs.getString("lpn"));
			statuscode.add(rs.getString("statusCodeDesc"));
			data.put(rs.getString("lpn"), rs.getString("statusCodeDesc"));


		}
		System.out.println(LPN);
		System.out.println(statuscode);
		childTest.log(Status.INFO,
				MarkupHelper.createLabel(lpn+" : "+data.get(lpn), ExtentColor.INDIGO));
		System.out.println(data.get(lpn));

		con.close();

	}
	public void DB(List<String> binPickPart1,int []binPickPartquantity,String type) throws Exception
	{int i=0;
	for(String part:binPickPart1)
	{
		partStockCheck(part,  type,binPickPartquantity[i]);

		i++;
	}}

	public void orderStatus(String customerReference,Module status) throws Exception
	{

		String Query="select om.accountId,om.siteId,om.customerReference,om.statusCodeId ,tsm.statusCodeDesc from ordersmaster om inner join tblstatuscode_m tsm on tsm.statusCodeId=om.statusCodeId where customerReference='"+customerReference+"' and siteId='"+site+"' and accountId='"+account+"'";
		con=null;
		Class.forName("com.mysql.jdbc.Driver");
		//con=DriverManager.getConnection("jdbc:mysql://13.126.173.6:3306/12032020VSB?useSSL=false", "root", "sqladmin");
		//	con=DriverManager.getConnection("jdbc:mysql://52.66.237.14:3306/sp11prod?useSSL=false", "root", "c7GdWQzjdeTEqvwf");
		con=DriverManager.getConnection(prop.getProperty("DBURL"), prop.getProperty("DBUSERNAME"), prop.getProperty("DBPASSWORD"));
		st=con.createStatement();
		rs=st.executeQuery(Query);
		data=new HashMap<String,String>();
		ArrayList<String> LPN=new ArrayList<String>();
		ArrayList<String> statuscode=new ArrayList<String>();

		if (rs.next()) {
			LPN.add(rs.getString("customerReference"));
			
			statuscode.add(rs.getString("statusCodeId"));
			data.put(rs.getString("customerReference"), rs.getString("statusCodeId"));

		}
		else
		{
			LPN.add(customerReference);
			statuscode.add("null");
			data.put(customerReference,"null");
			System.out.println("null for order");
		}
		System.out.println(LPN);
		System.out.println(statuscode);
		//childTest.log(Status.INFO,
			//	MarkupHelper.createLabel(customerReference+" : "+data.get(customerReference), ExtentColor.INDIGO));

		System.out.println(data.get(customerReference));
		switch(status){
		case INBOUNDORDERCREATION :
			//STCM4
			if(data.get(customerReference).equalsIgnoreCase("STCM4"))

			//if(data.get(customerReference).equalsIgnoreCase("Inbound initiated"))
			{
				System.out.println("pass");
				childTest.log(Status.INFO,
						MarkupHelper.createLabel(customerReference+" : "+data.get(customerReference)+" "+rs.getString("statusCodeDesc"), ExtentColor.INDIGO));
			}
			else
			{
				childTest.log(Status.WARNING,MarkupHelper.createLabel(customerReference+" : "+data.get(customerReference), ExtentColor.INDIGO));
			}
			break;
		case POD :
			//STCM-3
			if(data.get(customerReference).equalsIgnoreCase("STCM-3"))
			{
				System.out.println("pass");
				childTest.log(Status.INFO,
						MarkupHelper.createLabel(customerReference+" : "+data.get(customerReference), ExtentColor.INDIGO));
			}
			else
			{
				childTest.log(Status.WARNING,MarkupHelper.createLabel(customerReference+" : "+data.get(customerReference), ExtentColor.INDIGO));
			}
			break;
		case PARTIALBINNING :
			//STCM-5
			if(data.get(customerReference).equalsIgnoreCase("STCM-5"))
			//if(data.get(customerReference).equalsIgnoreCase("BINST"))
			{
				System.out.println("pass");
				childTest.log(Status.INFO,
						MarkupHelper.createLabel(customerReference+" : "+data.get(customerReference), ExtentColor.INDIGO));
			}
			else
			{
				childTest.log(Status.WARNING,MarkupHelper.createLabel(customerReference+" : "+data.get(customerReference), ExtentColor.INDIGO));
			}
			break;
		case BINNING :
			//STCM-8
			if(data.get(customerReference).equalsIgnoreCase("STCM-8"))
			//if(data.get(customerReference).equalsIgnoreCase("BINCOMP"))
			{
				System.out.println("pass");
				childTest.log(Status.INFO,
						MarkupHelper.createLabel(customerReference+" : "+data.get(customerReference)+" "+rs.getString("statusCodeDesc"), ExtentColor.INDIGO));
			}
			else
			{
				childTest.log(Status.WARNING,MarkupHelper.createLabel(customerReference+" : "+data.get(customerReference), ExtentColor.INDIGO));
			}
			break;
		case CANCEL :
			if(data.get(customerReference).equalsIgnoreCase("CANCEL"))
			{
				System.out.println("pass");
				childTest.log(Status.INFO,
						MarkupHelper.createLabel(customerReference+" : "+data.get(customerReference), ExtentColor.INDIGO));
			}
			else
			{
				childTest.log(Status.WARNING,MarkupHelper.createLabel(customerReference+" : "+data.get(customerReference), ExtentColor.INDIGO));
			}
			break;
		case SOCREATION :
			//STCM-14
			if(data.get(customerReference).equalsIgnoreCase("STCM-14"))

			//if(data.get(customerReference).equalsIgnoreCase("Pick Req"))
			{
				System.out.println("pass");
				childTest.log(Status.INFO,
						MarkupHelper.createLabel(customerReference+" : "+data.get(customerReference), ExtentColor.INDIGO));
			}
			else
			{
				childTest.log(Status.WARNING,MarkupHelper.createLabel(customerReference+" : "+data.get(customerReference), ExtentColor.INDIGO));
			}
			break;
		case HOLD :
			if(data.get(customerReference).equalsIgnoreCase("STCM12005"))
			{
				System.out.println("pass");
				childTest.log(Status.INFO,
						MarkupHelper.createLabel(customerReference+" : "+data.get(customerReference), ExtentColor.INDIGO));
			}
			else
			{
				childTest.log(Status.WARNING,MarkupHelper.createLabel(customerReference+" : "+data.get(customerReference), ExtentColor.INDIGO));
			}
			break;
		case HOLDPART :
			if(data.get(customerReference).equalsIgnoreCase("STCM12002")||data.get(customerReference).equalsIgnoreCase("Hold Temp Short Quantity Order"))
			{
				System.out.println("pass");
				childTest.log(Status.INFO,
						MarkupHelper.createLabel(customerReference+" : "+data.get(customerReference), ExtentColor.INDIGO));
			}
			else
			{
				childTest.log(Status.WARNING,MarkupHelper.createLabel(customerReference+" : "+data.get(customerReference), ExtentColor.INDIGO));
			}
			break;
		case TRIGGEREDORDER :
			if(data.get(customerReference).equalsIgnoreCase("Random Pick Awaiting"))
			{
				System.out.println("pass");
				childTest.log(Status.INFO,
						MarkupHelper.createLabel(customerReference+" : "+data.get(customerReference), ExtentColor.INDIGO));
			}
			else
			{
				childTest.log(Status.WARNING,MarkupHelper.createLabel(customerReference+" : "+data.get(customerReference), ExtentColor.INDIGO));
			}
			break;
		case REJECTORDER :
			if(data.get(customerReference).equalsIgnoreCase("STCM-13"))
			{
				System.out.println("pass");
				childTest.log(Status.INFO,
						MarkupHelper.createLabel(customerReference+" : "+data.get(customerReference), ExtentColor.INDIGO));
			}
			else
			{
				childTest.log(Status.WARNING,MarkupHelper.createLabel(customerReference+" : "+data.get(customerReference), ExtentColor.INDIGO));
			}
			break;
		case TEMPORDERCREATED :
			//STCM8
			if(data.get(customerReference).equalsIgnoreCase("STCM8"))
			{
				System.out.println("pass");
				childTest.log(Status.INFO,
						MarkupHelper.createLabel(customerReference+" : "+data.get(customerReference), ExtentColor.INDIGO));
			}
			else
			{
				childTest.log(Status.WARNING,MarkupHelper.createLabel(customerReference+" : "+data.get(customerReference), ExtentColor.INDIGO));
			}
			break;
		case PICKASSIGN :
			//STCM-14
			if(data.get(customerReference).equalsIgnoreCase("STCM-14"))

			//if(data.get(customerReference).equalsIgnoreCase("PCKASG"))
			{
				System.out.println("pass");
				childTest.log(Status.INFO,
						MarkupHelper.createLabel(customerReference+" : "+data.get(customerReference), ExtentColor.INDIGO));
			}
			else
			{
				childTest.log(Status.WARNING,MarkupHelper.createLabel(customerReference+" : "+data.get(customerReference), ExtentColor.INDIGO));
			}
			break;
		case PICKING:
			//STCM-16//STCM10699
			if(data.get(customerReference).equalsIgnoreCase("STCM-16")||data.get(customerReference).equalsIgnoreCase("STCM10699"))

			//if(data.get(customerReference).equalsIgnoreCase("PCKCMP"))
			{
				System.out.println("pass");
				childTest.log(Status.INFO,
						MarkupHelper.createLabel(customerReference+" : "+data.get(customerReference), ExtentColor.INDIGO));
			}
			else
			{
				childTest.log(Status.WARNING,MarkupHelper.createLabel(customerReference+" : "+data.get(customerReference), ExtentColor.INDIGO));
			}
			break;
		case CONSOLIDATION:
			//STCM10700 -consolidated
			
			if(data.get(customerReference).equalsIgnoreCase("STCM9")||data.get(customerReference).equalsIgnoreCase("STCM10700"))
			{
				System.out.println("pass");
				childTest.log(Status.INFO,
						MarkupHelper.createLabel(customerReference+" : "+data.get(customerReference), ExtentColor.INDIGO));
			}
			else
			{
				childTest.log(Status.WARNING,MarkupHelper.createLabel(customerReference+" : "+data.get(customerReference), ExtentColor.INDIGO));
			}
			break;
		case DISPATCH:
			//STCM2002
			if(data.get(customerReference).equalsIgnoreCase("STCM2002"))

			//if(data.get(customerReference).equalsIgnoreCase("Despatch Completed"))
			{
				System.out.println("pass");
				childTest.log(Status.INFO,
						MarkupHelper.createLabel(customerReference+" : "+data.get(customerReference), ExtentColor.INDIGO));
			}
			else
			{
				childTest.log(Status.WARNING,
						MarkupHelper.createLabel(customerReference+" : "+data.get(customerReference), ExtentColor.INDIGO));
			}
			break;

		default:


		}
		con.close();

	}








	public  void partStockCheck(String part, String status,int QTY) throws SQLException, InterruptedException, ClassNotFoundException
	{

		try {

			con=null;
			Class.forName("com.mysql.jdbc.Driver");
			//con=DriverManager.getConnection("jdbc:mysql://13.126.173.6:3306/12032020VSB?useSSL=false", "root", "sqladmin");
			//con=DriverManager.getConnection("jdbc:mysql://52.66.237.14:3306/sp11prod?useSSL=false", "root", "c7GdWQzjdeTEqvwf");
			con=DriverManager.getConnection(prop.getProperty("DBURL"), prop.getProperty("DBUSERNAME"), prop.getProperty("DBPASSWORD"));
			st=con.createStatement();
			//String S="SELECT  b.partNumber, COALESCE(sb.binnedQuantity, 0) as quantity, COALESCE(sl.lockedQuantity, 0) as lockedQuantity, COALESCE(sc.pickedQuantity, 0) as pickedQuantity, COALESCE(sb.binnedQuantity, 0) - (COALESCE(sl.lockedQuantity, 0) + COALESCE(sc.pickedQuantity, 0)) AS available FROM stockavailability a LEFT JOIN  stockavailability_binning sb ON sb.stockAvailabilityId = a.stockAvailabilityId LEFT JOIN stockavailability_locking sl ON sl.stockAvailabilityId = a.stockAvailabilityId LEFT JOIN stockavailability_picking sc ON sc.stockAvailabilityId = a.stockAvailabilityId LEFT JOIN tblpart_m b ON b.partId = a.partId LEFT JOIN tblstocktypes_m c ON c.stockTypeId = a.stockTypeId LEFT JOIN tblsite_m d ON d.siteId = a.siteId LEFT JOIN tblaccount_m e ON e.accountId = a.accountId WHERE b.partNumber = '"+part+"' AND c.stockTypeCode = 'GOOD' AND d.siteId = '"+site+"' AND e.accountId = '"+account+"'";
			String S1="SELECT  consigneeAccountCode,b.partNumber, COALESCE(sb.binnedQuantity, 0) as quantity, COALESCE(sl.lockedQuantity, 0) as lockedQuantity, COALESCE(sc.pickedQuantity, 0) as pickedQuantity, COALESCE(sb.binnedQuantity, 0) - (COALESCE(sl.lockedQuantity, 0) + COALESCE(sc.pickedQuantity, 0)) AS available FROM stockavailability a LEFT JOIN  stockavailability_binning sb ON sb.stockAvailabilityId = a.stockAvailabilityId LEFT JOIN stockavailability_locking sl ON sl.stockAvailabilityId = a.stockAvailabilityId LEFT JOIN stockavailability_picking sc ON sc.stockAvailabilityId = a.stockAvailabilityId LEFT JOIN tblpart_m b ON b.partId = a.partId LEFT JOIN tblstocktypes_m c ON c.stockTypeId = a.stockTypeId LEFT JOIN tblsite_m d ON d.siteId = a.siteId LEFT JOIN tblaccount_m e ON e.accountId = a.accountId left join tblconsigneeaccount_m ca on ca.consigneeaccountid=a.consigneeaccountid WHERE b.partNumber = '"+part+"' AND c.stockTypeCode = '"+STOCK_TYPE+"' AND d.siteId = '"+site+"' AND e.accountId = '"+account+"' and consigneeAccountCode ='"+Consignee+"'";

			String S=Consignee==null?S1.replace("consigneeAccountCode ='"+Consignee+"'", "consigneeAccountCode is null"):S1;
		
			
			rs=st.executeQuery(S);
			data=new HashMap<String,String>();
			if(rs.next()) {
				data.put("PartNumber",rs.getString("partNumber"));
				data.put("Quantity",rs.getString("quantity"));
				data.put("LockedQuantity",rs.getString("lockedQuantity"));
				data.put("PickedQuantity",rs.getString("pickedQuantity"));
				data.put("Available",rs.getString("available"));

			}
			else
			{

				data.put("PartNumber",""+part.toUpperCase());
				data.put("Quantity","0.0000");
				data.put("LockedQuantity","0.0000");
				data.put("PickedQuantity","0.0000");
				data.put("Available","0.0000");


			}
			available=(int) Float.parseFloat(data.get("Available"));
			lockedQuantity=(int) Float.parseFloat(data.get("LockedQuantity"));
			pickedQuantity=(int) Float.parseFloat(data.get("PickedQuantity"));
			quantity=(int) Float.parseFloat(data.get("Quantity"));
			partnumber=data.get("PartNumber");


			childTest.log(Status.INFO,MarkupHelper.createLabel("DB validation has been started for : "+partnumber,ExtentColor.ORANGE));

			for(Map.Entry<String, String> entry:data.entrySet())
			{
				childTest.log(Status.INFO,MarkupHelper.createLabel( entry.getKey()+"  "+entry.getValue(),ExtentColor.BROWN));
			}
			if(status.equals("binning") )
			{
			int expected=Available.get(partnumber)+QTY;
			int expected1=Quantity.get(partnumber)+QTY;
			if(expected == available && available >=0 && Available.get(partnumber) >=0)
			{childTest.log(Status.INFO,
					MarkupHelper.createLabel("After "+status+" available quantity successfully updated as "+Available.get(partnumber)+" to "+available+" for part "+partnumber , ExtentColor.TEAL));

			childTest.log(Status.PASS, "Expected and actual are matched");

			}
			else
			{
				childTest.log(Status.WARNING, "After "+status+" The expected quantity is not matched with actual quantity Expected quantity : "+expected+" The actual DB displayed quantity" + available);


			}
			if(expected1 == quantity && quantity >=0 && Quantity.get(partnumber) >=0)
			{childTest.log(Status.INFO,
					MarkupHelper.createLabel("After "+status+" "+" quantity" +" successfully updated as "+Quantity.get(partnumber)+" to "+quantity+" for part "+partnumber , ExtentColor.TEAL));
			childTest.log(Status.PASS, "After "+status+"Expected and actual are matched");


			}
			else
			{
				childTest.log(Status.WARNING, "After "+status+"The expected quantity is not matched with actual quantity" +"Expected quantity : "+expected1+"The actual DB displayed quantity" + quantity);

			}

			}
			else if(status.equals("pickgen"))
			{int expected1=locked.get(partnumber)+QTY;
			int expected2=Available.get(partnumber)-QTY;
			if(expected1 == lockedQuantity && lockedQuantity >=0 && locked.get(partnumber)>=0)
			{childTest.log(Status.INFO,
					MarkupHelper.createLabel("After "+status+" "+"locked quantity" +" successfully updated as "+locked.get(partnumber)+" to "+lockedQuantity+" for part "+partnumber , ExtentColor.TEAL));


			}
			else
			{
				childTest.log(Status.WARNING, "After "+status+" The expected locked quantity is not matched with actual quantity" +"Expected quantity : "+expected1+" The actual DB locked quantity" + lockedQuantity);



			}
			if(expected2 == available && available >=0 && Available.get(partnumber) >=0)
			{childTest.log(Status.INFO,
					MarkupHelper.createLabel("After "+status+" "+" available quantity" +" successfully updated as "+Available.get(partnumber)+" to "+available+" for part "+partnumber , ExtentColor.TEAL));


			}
			else
			{
				childTest.log(Status.WARNING, "After "+status+" The expected quantity is not matched with actual quantity Expected quantity : "+expected2+" The actual DB available quantity" + available);


			}
			}
			else if(status.equals("picking"))
			{
				int expected1=Picked.get(partnumber)+QTY;
				int expected2=locked.get(partnumber)-QTY;
				if(expected1 == pickedQuantity && pickedQuantity >=0 && Picked.get(partnumber)>=0)
				{childTest.log(Status.INFO,
						MarkupHelper.createLabel("After "+status+" "+"picked quantity" +" successfully updated as "+Picked.get(partnumber)+" to "+pickedQuantity+" for part "+partnumber , ExtentColor.TEAL));


				}
				else
				{
					childTest.log(Status.WARNING, "After "+status+" The expected quantity is not matched with actual quantity" +"Expected quantity : "+expected1+" The actual DB picked quantity" + pickedQuantity);



				}
				if(expected2 == lockedQuantity && lockedQuantity >=0 && locked.get(partnumber) >=0)
				{childTest.log(Status.INFO,
						MarkupHelper.createLabel("After "+status+" "+" lockedQuantity" +" successfully updated as "+locked.get(partnumber)+" to "+lockedQuantity+" for part "+partnumber , ExtentColor.TEAL));


				}
				else
				{
					childTest.log(Status.WARNING, "After "+status+" The expected quantity is not matched with actual quantity Expected quantity : "+expected2+" The actual DB locked quantity" + lockedQuantity);



				}
			}
			else if(status.equals("picking"))
			{
				int expected1=Picked.get(partnumber)+QTY;
				int expected2=locked.get(partnumber)-QTY;
				if(expected1 == pickedQuantity && pickedQuantity >=0 && Picked.get(partnumber)>=0)
				{childTest.log(Status.INFO,
						MarkupHelper.createLabel("After "+status+" "+"picked quantity" +" successfully updated as "+Picked.get(partnumber)+" to "+pickedQuantity+" for part "+partnumber , ExtentColor.TEAL));


				}
				else
				{
					childTest.log(Status.WARNING, "After "+status+" The expected quantity is not matched with actual quantity Expected quantity : "+expected1+" The actual DB picked quantity" + pickedQuantity);



				}
				if(expected2 == lockedQuantity && lockedQuantity >=0 && locked.get(partnumber) >=0)
				{childTest.log(Status.INFO,
						MarkupHelper.createLabel("After "+status+" "+" lockedQuantity" +" successfully updated as "+locked.get(partnumber)+" to "+lockedQuantity+" for part "+partnumber , ExtentColor.TEAL));


				}
				else
				{
					childTest.log(Status.WARNING, "After "+status+" The expected quantity is not matched with actual quantity Expected quantity : "+expected2+" The actual DB locked quantity" + lockedQuantity);


				}
			}
			else if(status.equals("partialpick"))
			{
				int expected1=Picked.get(partnumber)+QTY;
				int expected2=locked.get(partnumber)-2;
				if(expected1 == pickedQuantity && pickedQuantity >=0 && Picked.get(partnumber) >=0)
				{childTest.log(Status.INFO,
						MarkupHelper.createLabel("After "+status+" "+"picked quantity" +" successfully updated as "+Picked.get(partnumber)+" to "+pickedQuantity+" for part "+partnumber , ExtentColor.TEAL));


				}
				else
				{
					childTest.log(Status.WARNING, "After "+status+" The expected quantity is not matched with actual quantity Expected quantity : "+expected1+" The actual DB picked quantity" + pickedQuantity);


				}
				if(expected2 == lockedQuantity && lockedQuantity >=0 && locked.get(partnumber) >=0)
				{childTest.log(Status.INFO,
						MarkupHelper.createLabel("After "+status+" "+" lockedQuantity" +" successfully updated as "+locked.get(partnumber)+" to "+lockedQuantity+" for part "+partnumber , ExtentColor.TEAL));


				}
				else
				{
					childTest.log(Status.WARNING, "After "+status+" The expected quantity is not matched with actual quantity Expected quantity : "+expected2+" The actual DB locked quantity" + lockedQuantity);


				}
			}
			else if(status.equals("short"))
			{
				int expected1=Available.get(partnumber)+QTY;
				int expected2=locked.get(partnumber)-QTY;
				if(expected1 == available && available >=0 && Available.get(partnumber) >=0)
				{childTest.log(Status.INFO,
						MarkupHelper.createLabel("After "+status+" "+"Available quantity" +" successfully updated as "+Available.get(partnumber)+" to "+available+" for part "+partnumber , ExtentColor.TEAL));


				}
				else
				{
					childTest.log(Status.WARNING, "After "+status+" The expected quantity is not matched with actual quantity Expected quantity : "+expected1+" The actual DB available quantity" + available);


				}
				if(expected2 == lockedQuantity && lockedQuantity >=0 && locked.get(partnumber) >=0)
				{childTest.log(Status.INFO,
						MarkupHelper.createLabel("After "+status+" "+" lockedQuantity" +" successfully updated as "+locked.get(partnumber)+" to "+lockedQuantity+" for part "+partnumber , ExtentColor.TEAL));


				}
				else
				{
					childTest.log(Status.WARNING, "After "+status+" The expected quantity is not matched with actual quantity Expected quantity : "+expected2+" The actual DB locked quantity" + lockedQuantity);



				}
			}
			else if(status.equals("precancel"))
			{
				int expected1=Available.get(partnumber)+QTY;
				int expected2=locked.get(partnumber)-QTY;
				if(expected1 == available && available >=0 && Available.get(partnumber) >=0)
				{childTest.log(Status.INFO,
						MarkupHelper.createLabel("After "+status+" "+"available quantity" +" successfully updated as "+Available.get(partnumber)+" to "+available+" for part "+partnumber , ExtentColor.TEAL));


				}
				else
				{
					childTest.log(Status.WARNING, "After "+status+" The expected quantity is not matched with actual quantity Expected quantity : "+expected1+" The actual DB available quantity" + available);


				}
				if(expected2 == lockedQuantity && lockedQuantity >=0 && locked.get(partnumber) >=0)
				{childTest.log(Status.INFO,
						MarkupHelper.createLabel("After "+status+" "+" lockedQuantity" +" successfully updated as "+locked.get(partnumber)+" to "+lockedQuantity+" for part "+partnumber , ExtentColor.TEAL));


				}
				else
				{
					childTest.log(Status.WARNING, "After "+status+" The expected quantity is not matched with actual quantity Expected quantity : "+expected2+" The actual DB locked quantity" + lockedQuantity);


				}
			}

			else if(status.equals("postcancel"))
			{
				int expected1=Picked.get(partnumber)-QTY;
				// int expected2=locked.get(partnumber)-QTY;
				if(expected1 == pickedQuantity && pickedQuantity >=0 && Picked.get(partnumber)>=0)
				{childTest.log(Status.INFO,
						MarkupHelper.createLabel("After "+status+" "+"picked quantity" +" successfully updated as "+Picked.get(partnumber)+" to "+pickedQuantity+" for part "+partnumber , ExtentColor.TEAL));


				}
				else
				{
					childTest.log(Status.WARNING, "After "+status+" The expected quantity is not matched with actual quantity Expected quantity : "+expected1+" The actual DB picked quantity" + pickedQuantity);


				}
			}
			locked.put(partnumber, lockedQuantity);
			Picked.put(partnumber, pickedQuantity);
			Available.put(partnumber, available);
			Quantity.put(partnumber, quantity);
			con.close();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
	}

	public boolean isElementPresent(String xpath) throws Exception{
		try{
			Thread.sleep(2000);
			fluentWait(xpath);
			return true;
			//driver.findElement(By.xpath(xpath));
		}
		catch(Exception e)
		{	
			//childTest.log(Status.FAIL, " Element not present  "+xpath,MediaEntityBuilder.createScreenCaptureFromBase64String( Screencapture(driver)).build());
			//childTest.info(e);
			extent.flush();
			//	 
			return false;
		}
	}
	public void verify(String xpath,String check) throws  Exception 
	{
		click=check+" : The Keyword not present in the action response";

		if(isElementPresent(xpath))
		{
			if(xpathGetText(xpath).contains(check))
			{

			}
			else
			{
				throw new Exception(xpath+" not found ");
			}
		}
		else
		{
			throw new Exception(xpath+" not found");
		}

	}
	public void verify(String xpath1) throws  Exception 
	{Thread.sleep(1000);
	//click=xpath1+" not present";
	//Assert.assertTrue(isElementPresent(xpath1));
	if(isElementPresent(xpath1))
	{

	}
	else
	{
		throw new Exception(xpath1+" not found");
	}


	}
	public void clickXpath(String xpath) throws InterruptedException {


		Thread.sleep(1000);
		click=xpath;
		WebElement element = fluentWait(xpath);
		element.click();
		childTest.log(Status.INFO, "Clicking on  "+xpath);


	}
	public void verify(String xpath,String check,String check1) throws  Exception 
	{
		//Thread.sleep(2000);
		click=check+" : The Keyword not present in the action response";
		Assert.assertTrue(isElementPresent(xpath));
		Assert.assertTrue(xpathGetText(xpath).contains(check));
		Assert.assertTrue(xpathGetText(xpath).toLowerCase().contains(check1.toLowerCase()));
	}
	public void clickXpath(String xpath,String value) throws Exception {
		Thread.sleep(500);
		click=value;
		WebElement element = fluentWait(xpath);
		//Thread.sleep(500);

		element.click();
		childTest.log(Status.INFO, "Clicking on Element "+value);
	}

	public void poreadData() throws Exception {
		List<CSVRecord> data = readexcel(prop.getProperty("POPATH"));
		CustReference = data.get(0).get("CUSTOMER_REFERENCE").trim();
		Vquantity=data.get(0).get("QUANTITY").trim();

		NVquantity=data.get(1).get("QUANTITY").trim();

		NVNUquantity=data.get(2).get("QUANTITY").trim();


	}
	public void soreadData() throws Exception{
		List<CSVRecord> data = readexcel(prop.getProperty("SOPATH"));
		SOCustReference = data.get(0).get("CUSTOMER_REFERENCE");
		Reference_2=data.get(0).get("REFERENCE_2");
		Reference_3=data.get(0).get("REFERENCE_3");
	}
	public void poreadDataBOX() throws Exception {
		List<CSVRecord> data = readexcel(prop.getProperty("POPATHBOX"));
		CustReference = data.get(0).get("CUSTOMER_REFERENCE").trim();
		List<CSVRecord> data1 = readexcel(prop.getProperty("POPATHBOX"));
		NVCUSLPN1 = data1.get(0).get("LPN");
		NVCUSLPN2 = data1.get(1).get("LPN");
		NVCUSLPN3=data1.get(2).get("LPN");
		NVQTY = data.get(2).get("QUANTITY");

	}



	public void vqty() throws Exception {
		List<CSVRecord> data = readexcel(prop.getProperty("POPATH"));
		//VQTY = data.get(0).get("QUANTITY");
		VQTY = data.get(2).get("QUANTITY");
		VPQTY = Integer.parseInt(VQTY);

	}
	public void vqtycuslpn() throws Exception {
		List<CSVRecord> data = readexcel(prop.getProperty("POPATHCUSLPN"));
		VQTY1 = data.get(0).get("QUANTITY");
		VQTY2 = data.get(7).get("QUANTITY");
		//VPQTY = Integer.parseInt(VQTY);
	}
	public void nvnuqty() throws Exception {
		List<CSVRecord> data = readexcel(prop.getProperty("POPATH"));
		NVNUQTY = data.get(2).get("QUANTITY");
		NVNUVPQTY = Integer.parseInt(NVNUQTY);
	}

	public void switchParentWindow() {
		String winHandleBefore = driver.getWindowHandle();
		//for (String winHandle : driver.getWindowHandles()) {		
		driver.switchTo().window(winHandleBefore);			

	}
	public void javainput(String path , String data){

		WebElement userNameTxt = driver.findElement(By.xpath(path)); 
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;  
		// set the text
		jsExecutor.executeScript("arguments[0].value='testuser'", userNameTxt);
	}
	public void clickXpath1(String xpath) {
		WebElement element = fluentWait(xpath);
		element.click();
	}

	public void soreadData1() throws Exception{
		List<CSVRecord> data = readexcel(prop.getProperty("SOPATH"));
		SOCustReference = data.get(0).get("CUSTOMER_REFERENCE");
		NVNUpart =data.get(1).get(1);
		NVpart=data.get(0).get(1);

	}

	public void closeBrowserTab() throws  Exception{		
		shortWait(5000);
		ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
		shortWait(1500);
		driver.switchTo().window(tabs2.get(1));
		shortWait(1500);
		childTest.log(Status.PASS,"Child window craeted",MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());
		driver.close();
		shortWait(1500);
		driver.switchTo().window(tabs2.get(0));
		shortWait(1500);


		childTest.log(Status.INFO, "Closed child window ");
		extent.flush();
		//	 


	}






	public void clear1(String path) throws InterruptedException
	{
		Thread.sleep(3000);
		WebElement b=fluentWait(path);
		b.sendKeys(Keys.CONTROL + "a");
		b.sendKeys(Keys.DELETE);
	}
	public void resize(int a,int b) throws Exception
	{
		
     	  Dimension e = new Dimension(a, b);
       	//Resize current window to the set dimension
       	   driver.manage().window().setSize(e);
	}
	public void clickDownArrow() {

		try {

			builder = new Actions(driver);
			builder.sendKeys(new CharSequence[] {
					Keys.ARROW_DOWN}).build().perform();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}

	}
	public void enterXpath(String xpath, String value1,String locator) throws Exception {

		//Thread.sleep(2000);
		String value =new String(value1.trim());
		click=value;
		WebElement element = fluentWait(xpath);

		element.clear();

		element.sendKeys(value.trim());
		childTest.log(Status.INFO, "Entered :  "+value+" in the   "+locator+"  field: ");

	}
	public void enterXpath(String xpath, String value) throws  Exception {

		click=value+" not entered";
		fluentWait(xpath).clear();
		Thread.sleep(1000);
		fluentWait(xpath).sendKeys(value.trim());
		CommonFunctions.childTest.log(Status.INFO, "Typing  : "+value);

	}


	public void sendChar(String xpath, String value,String locator) throws InterruptedException
	{	
		click=value;
		WebElement element = driver.findElement(By.xpath(xpath));
		element.clear();

		for (int i = 0; i < value.length(); i++){
			char c = value.charAt(i);
			String s = new StringBuilder().append(c).toString();
			Thread.sleep(1000);
			element.sendKeys(s);
		}       
		childTest.log(Status.INFO, "Entered :  "+value+" in the   "+locator+"  field: ");
	}	

	public void actionEnter() throws Exception{

		Actions builder = new Actions(driver);        
		builder.sendKeys(Keys.ENTER).perform();
		childTest.log(Status.INFO, "Entered Manually :  "/*,MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture1()).build()*/);
	}
	public void doubleclick(String target){

		Actions builder = new Actions(driver);        
		builder.doubleClick(fluentWait(target));

	}

	public void actionClear(){

		Actions builder = new Actions(driver);        
		builder.sendKeys(Keys.DELETE).perform();

	}



	public void fluentWaitxpath(int seconds , int poolseconds , final String xpath){
		fluentwait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(seconds)).pollingEvery(Duration.ofSeconds(poolseconds)).ignoring(NoSuchElementException.class);
		fluentwait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver){
				return driver.findElement(By.xpath(xpath));
			}
		});
	}

	public void enterXpathSO(String xpath,Object object){
		WebElement element = fluentWait(xpath);
		element.clear();
		element.sendKeys((CharSequence[]) object);

	}

	public void enterXpath1(String xpath, String value) {
		WebElement element = fluentWait(xpath);

		element.sendKeys(value);
	}

	public void shortWait(int waittime) {
		try {
			Thread.sleep(waittime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	public void actionSendKeys(String xpath , String sendkey){

		try {

			builder = new Actions(driver);
			WebElement element = driver.findElement(By.xpath(xpath));
			builder.moveToElement(element).click().sendKeys(sendkey).perform();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}

	}



	public void mediumWait(int waittime) {
		try {
			Thread.sleep(waittime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void longWait(int waittime) {
		try {
			Thread.sleep(waittime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public WebElement fluentWait(final String xpath) {

		long TIMEUNIT = Long.parseLong(prop.getProperty("TIMECOUNT"));
		long POOLCOUNT = Long.parseLong(prop.getProperty("POOLINGTIME"));
		fluentwait = new FluentWait(driver).withTimeout(Duration.ofSeconds(TIMEUNIT))
				.pollingEvery(Duration.ofSeconds(POOLCOUNT)).ignoring(NoSuchElementException.class);
		return fluentwait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(By.xpath(xpath));
			}
		});
	}

	public void explicitWaitClick(int timeinseconds, String xpath) {
		wait = new WebDriverWait(driver, timeinseconds);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
	}


	public void explicitWaitXpathClick(int timeinseconds, String xpath) {
		wait = new WebDriverWait(driver, timeinseconds);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
	}

	public void explicitWaitSendKeysXpath(int timeinseconds, String xpath, String value) {
		wait = new WebDriverWait(driver, timeinseconds);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))).sendKeys(value);
	}

	public void explicitWaitSendKeys(int timeinseconds, String id, String value) {
		wait = new WebDriverWait(driver, timeinseconds);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(id))).sendKeys(value);
	}

	public void explicitWaitClick2(int timeinseconds, String id) {
		wait = new WebDriverWait(driver, timeinseconds);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(id))).click();
	}

	public void implicitWait(int timeout) {
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
	}

	public void switchWindow() {
		for (String childwindow : driver.getWindowHandles()) {
			driver.switchTo().window(childwindow);
		}
	}

	public void robotEnter() throws AWTException {
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);
	}
	public void deleteFile()
	{
		File[] files=folder.listFiles();
		try
		{
			for(File file:files)
			{
				System.out.println(file.getName());
				file.delete();
			}}
		catch(Exception e)
		{
			//System.out.println(e);
		}	
	}
	public void selectByVisibleText(String xpath, String text) throws  Exception {
		try{
			Thread.sleep(2000);
			click=text+" not selected";
			Select selectvisibletext = new Select(driver.findElement(By.xpath(xpath)));
			selectvisibletext.selectByVisibleText(text);
			childTest.log(Status.INFO, " Selected  : "+text);
		}catch(Exception e)
		{
			childTest.log(Status.FAIL, " Unable to select : "+text+" element in the DOM ");
			extent.flush();

		}
	}

	public void selectByVisibleValue(String xpath, String value) throws  Exception {

		Thread.sleep(2000);
		try{

			Select selectvisibletext = new Select(driver.findElement(By.xpath(xpath)));
			selectvisibletext.selectByValue(value);
		}catch(Exception e)
		{
			childTest.log(Status.FAIL, " Unable to select  "+value+" ",MediaEntityBuilder.createScreenCaptureFromBase64String(Screencapture(driver)).build());
			extent.flush();

		}
	}

	public void selectByvalue(String xpath, int i) throws InterruptedException {
		Thread.sleep(2000);
		Select selectvisibletext = new Select(driver.findElement(By.xpath(xpath)));
		selectvisibletext.selectByIndex(i);
	}

	public void lightWait(int time) {
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}

	public void nameClick(String name) throws  Exception {
		try{
			driver.findElement(By.name(name.trim())).click();
		}catch(Exception e)
		{
			childTest.log(Status.FAIL, " Unable to click  "+name+"  in the DOM ");
			extent.flush();

		}
	}

	public void idClick(String id) {
		driver.findElement(By.id(id)).click();
	}

	public void xpathClick(String xpath) {
		driver.findElement(By.xpath(xpath)).click();
	}

	public void cssClick(String cssValue) {
		driver.findElement(By.cssSelector(cssValue)).click();

	}

	public void cssVisibleText(String cssValue, String text) {
		Select selectvisibletext = new Select(driver.findElement(By.cssSelector(cssValue)));
		selectvisibletext.selectByVisibleText(text);
	}

	public void readPropertyFile() throws IOException {
		if (prop == null) {
			prop = new Properties();
			try {
				FileInputStream fis = new FileInputStream(".//Config.properties.mine");
				prop.load(fis);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}



	public void extentreport(){

		extent = new ExtentReports();
		ExtentSparkReporter spark = new ExtentSparkReporter("./Visibility.html");
		extent.attachReporter(spark);
		spark.config().setTheme(Theme.DARK);
		spark.config().setDocumentTitle("Visibility");

	}

	public void basicConfig() {
		BasicConfigurator.configure();
	}
	public void AlertHandle() {
		driver.switchTo().alert().accept();
	}

	public void assertEquals(String expected) throws IOException {
		String expectedurl = prop.getProperty(expected);
		String actualurl = driver.getCurrentUrl();
		// System.out.println(expectedurl);
		// System.out.println(actualurl);
		Assert.assertEquals(actualurl, expectedurl);
	}

	public ArrayList<HashMap<String, String>> readXL(String FILEPATH, String SHEETNAME)
			throws IOException, BiffException {

		ArrayList<HashMap<String, String>> alist = new ArrayList<HashMap<String, String>>();
		FileInputStream fis1 = new FileInputStream(prop.getProperty(FILEPATH));
		Workbook wb = Workbook.getWorkbook(new File(prop.getProperty(FILEPATH)));
		Sheet sh = wb.getSheet(prop.getProperty(SHEETNAME));
		for (int rows = 1; rows < sh.getRows(); rows++) {
			HashMap<String, String> hlist = new HashMap<String, String>();
			for (int colms = 0; colms < sh.getColumns(); colms++) {
				hlist.put(sh.getCell(colms, 0).getContents().trim(), sh.getCell(colms, rows).getContents().trim());
			}
			alist.add(hlist);
		}
		return alist;
	}


	public void clickIDLinkText(String link) throws Exception
	{
		Thread.sleep(1500);
		fluentlink(link).click();
		childTest.log(Status.INFO, " Clicking on : "+link);
	}
	public WebElement fluentlink(final String linkText) throws Exception {
		long TIMEUNIT = Long.parseLong(prop.getProperty("TIMECOUNT"));
		long POOLCOUNT = Long.parseLong(prop.getProperty("POOLINGTIME"));
		fluentwait = new FluentWait(driver).withTimeout(Duration.ofSeconds(TIMEUNIT))
				.pollingEvery(Duration.ofSeconds(POOLCOUNT)).ignoring(NoSuchElementException.class);
		return fluentwait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(By.linkText(linkText));
			}
		});

	}

	public String xpathGetText(String xpath) throws  Exception {
		//		try{
		String eletext=null;
		//Thread.sleep(2000);
		eletext=fluentWait(xpath).getText();
		if(eletext.equals("")) {
			eletext=fluentWait(xpath).getAttribute("innerText");
			System.out.println("inner "+eletext);
			if(eletext.equals(""))
			{
				eletext=fluentWait(xpath).getAttribute("textContent");	
				System.out.println("inner1 "+eletext);

			}

		}
		childTest.log(Status.INFO, " Readed value from UI : "+eletext);
		return eletext;
	}
	public void actionClearXpath(String xpath){

		Actions builder = new Actions(driver);
		WebElement element = driver.findElement(By.xpath(xpath));
		builder.moveToElement(element).sendKeys(Keys.DELETE).perform();



	}


	public static  String Screencapture(WebDriver driver) throws Exception{


		TakesScreenshot newScreen = (TakesScreenshot) driver;
		String scnShot = newScreen.getScreenshotAs(OutputType.BASE64);
		return "data:image/jpg;base64, " + scnShot ;

	}
	public static  String Screencapture1() throws Exception{
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File Dest = new File(System.getProperty("user.dir")+"/Screenshots/test" + System.currentTimeMillis() + ".png");
		String errflpath = Dest.getAbsolutePath();
		FileUtils.copyFile(scrFile, Dest);
		byte[] img=IOUtils.toByteArray(new FileInputStream(errflpath));
		return Base64.getEncoder().encodeToString(img);

	}



	public String capture(WebDriver driver) throws Exception {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File Dest = new File(System.getProperty("user.dir")+"/Screenshots/test" + System.currentTimeMillis() + ".png");
		String errflpath = Dest.getAbsolutePath();
		FileUtils.copyFile(scrFile, Dest);
		Thread.sleep(2000);
		return errflpath;

	}
	public void windowHandle() throws  Exception {

		for (String handle : driver.getWindowHandles()) {

			driver.switchTo().window(handle);
		}

	}





	public String getAlphaNumericString(int n) {

		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
		//+ "!@#$%^&*()_-+=:;<>,.{}[]| ?";

		StringBuilder sb = new StringBuilder(n);

		for (int i = 0; i < n; i++) {

			int index = (int) (AlphaNumericString.length() * Math.random());

			sb.append(AlphaNumericString.charAt(index));
		}

		return sb.toString();
	}


	public void ReadExcel(String path) throws Exception {

		List<CSVRecord> data = readexcel(path);
		CustReference = data.get(0).get("CUSTOMER_REFERENCE");
		NvQtn = data.get(0).get("QUANTITY");
		NVNUQtn = data.get(1).get("QUANTITY");
		NVLPN = data.get(0).get("LPN");
		NVNULPN = data.get(1).get("PART_NUMBER");
		//NVQUANTITY = Integer.parseInt(NvQtn);
	}


	public List<CSVRecord> readexcel(String path) throws Exception {

		FileInputStream targetStream = new FileInputStream(path);
		CSVParser parser = CSVFormat.EXCEL.withHeader().parse(new InputStreamReader(targetStream));
		List<CSVRecord> listCsvRecord = parser.getRecords();
		Map<String, Integer> inputFileHeader = parser.getHeaderMap();
		parser.close();
		return listCsvRecord;
	}

	public void actionClick(String xpath){

		//		try {
		builder = new Actions(driver);	
		WebElement element = fluentWait(xpath);
		builder.moveToElement(element).click(element).build().perform();

		//		} catch (Exception e) {
		//			// TODO Auto-generated catch block
		//			System.out.println(e);
		//		}		

	}

	public void actionLinkTextClick(String linkText){

		try {

			builder = new Actions(driver);	
			WebElement element = driver.findElement(By.linkText(linkText));
			builder.moveToElement(element).click().perform();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}		

	}






	


	public void scrollUp(){

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,-250)", "");

	}


	public void scrollDown(){

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,350)", "");

	}




}
