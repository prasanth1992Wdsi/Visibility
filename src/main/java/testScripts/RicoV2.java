package testScripts;

import java.util.HashMap;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class RicoV2 {
	
	String tocken;
	Response r1;
	@Test(priority=0)
	public void Tocken_generation()
	{

	HashMap da =new HashMap();
	da.put("userName","api1");
	da.put("password","api1");
	System.out.println(da);  
	Response vr= RestAssured
	.given()
	.contentType("application/json")
	.body(da)
	.when()
	.post("https://gatewaytest.tvsscstulip.com:8015/token/authenticate");
	String response=vr.asString();
	System.out.println(response);
	tocken=JsonPath.from(response).get("tocken");
	System.out.println("Tocken - " + tocken);
	}
	@Test(priority=1)
	public void POOrder_Creation()
	{
	String word = "customerReference";
	StringBuilder sb = new StringBuilder();
	Random rand = new Random();

	for (int i = 0; i < word.length(); i++) {
	char c = word.charAt(i);
	int randomInt = rand.nextInt(36);
	char randomChar = Character.forDigit(randomInt, 36);
	sb.append(randomChar);
	}

	String randomWord = sb.toString();
	System.out.println("Random alphanumeric word for " + word + ": " + randomWord);

	JSONObject requestBody = new JSONObject();
	requestBody.put("orderTypesMasterId", "OTYM-1");
	requestBody.put("siteMasterId", "IN22906");
	requestBody.put("sendMail", true);
	requestBody.put("isSave", false);
	requestBody.put("partialSave", true);
	requestBody.put("accountMasterId", "TSD001488");
	requestBody.put("actorType", "AM001");
	requestBody.put("vehicleAssigned", false);
	requestBody.put("isGrnReady", true);

	JSONObject ordersobj = new JSONObject();
	JSONArray jas=new JSONArray();
	ordersobj.put("customerReference",randomWord);
	ordersobj.put("invoiceNumber", "ABC");
	ordersobj.put("reference1", "123");
	ordersobj.put("reference2", "123");
	ordersobj.put("reference3", "123");

	JSONObject lineitem = new JSONObject();
	JSONArray B=new JSONArray();
	lineitem.put("partNumber","NVNUFIFOSS1");
	lineitem.put("stockTypeCode", "GOOD");
	lineitem.put("lpn", null);
	lineitem.put("quantity", "10");
	lineitem.put("manufacturingDate",null);
	lineitem.put("expiryDate", null);
	lineitem.put("partRef", "123");
	lineitem.put("customerBoxLabel",null);
	lineitem.put("batchNumber", null);
	lineitem.put("consignee", null);
	lineitem.put("lineNumber",null);
	B.add(lineitem);
	ordersobj.put("lineItems",B);
	jas.add(ordersobj);
	requestBody.put("orders", jas);
	System.out.println(requestBody.toJSONString());
	      r1 = RestAssured.given()
	.contentType("application/json")
	.header("AppId","008")
	.header("timezone","Asia/Calcutta")
	.header("Authorization","Bearer "+tocken)
	.body(requestBody.toString())
	.post("http://52.66.237.14:8016/api/upload/poOrderCreationV2");
	System.out.println(r1.asString());
	System.out.println("PO Order Created Successfully");

	}
	@Test(priority=2)
	public void SOOrder_Creation()
	{
	Tocken_generation();
	{
	String word = "customerReference";
	        StringBuilder sb = new StringBuilder();
	        Random rand = new Random();

	        for (int i = 0; i < word.length(); i++) {
	                char c = word.charAt(i);
	                int randomInt = rand.nextInt(36);
	                char randomChar = Character.forDigit(randomInt, 36);
	                sb.append(randomChar);
	        }

	        String randomWord = sb.toString();
	        System.out.println("Random alphanumeric word for " + word + ": " + randomWord);

	        JSONObject requestBody = new JSONObject();
	        requestBody.put("orderTypesMasterId", "OTYM-2");
	        requestBody.put("siteMasterId", "IN22906");
	        requestBody.put("sendMail", true);
	        requestBody.put("isSave", false);
	        requestBody.put("partialSave", true);
	        requestBody.put("accountMasterId", "TSD001488");

	        JSONObject ordersobj = new JSONObject();
	        JSONArray jas=new JSONArray();
	        ordersobj.put("customerReference",randomWord);
	        ordersobj.put("reference1", "123");
	        ordersobj.put("reference2", "123");
	        ordersobj.put("reference3", "123");
	        ordersobj.put("serviceTypeCode", "SAME DAY");
	        ordersobj.put("deliveryTypeCode", "NEWDELPOINT");
	        ordersobj.put("addressLine_1", "Workshop Unit");
	        ordersobj.put("addressLine_2", "Station Road");
	        ordersobj.put("addressLine_3", "");
	        ordersobj.put("city", "Locharbriggs");
	        ordersobj.put("state", "DUMFRIES Dumfriesshire");
	        ordersobj.put("country", "GB");
	        ordersobj.put("countryCode", "GB");
	        ordersobj.put("pinCode", "DG1 1SD");
	        ordersobj.put("priority", "P3");
	        ordersobj.put("destinationCode", "TK Print");
	        ordersobj.put("contactName", "Tracey Graham");
	        ordersobj.put("phoneNo", "1387711700");
	       

	        JSONObject lineitem = new JSONObject();
	        JSONArray B=new JSONArray();
	        lineitem.put("partNumber","NVNUFIFOSS1");
	        lineitem.put("stockTypeCode", "GOOD");
	        lineitem.put("lpn", null);
	        lineitem.put("quantity", "10");
	        lineitem.put("manufacturingDate",null);
	        lineitem.put("expiryDate", null);
	        lineitem.put("partRef", "123");
	        lineitem.put("customerBoxLabel",null);
	        lineitem.put("batchNumber", null);
	        lineitem.put("consignee", null);
	        lineitem.put("lineNumber",null);
	        B.add(lineitem);
	        ordersobj.put("lineItems",B);
	        jas.add(ordersobj);
	        requestBody.put("orders", jas);
	        System.out.println(requestBody.toJSONString());
	        r1 = RestAssured.given()
	                        .contentType("application/json")
	                        .header("AppId","008")
	                        .header("timezone","Asia/Calcutta")
	                        .header("Authorization","Bearer "+tocken)
	                        .body(requestBody.toString())
	                        .post("http://52.66.237.14:8016/api/upload/soOrderCreationV2");
	        System.out.println(r1.asString());
	        System.out.println("SO Order Created Successfully");
	}
	}	

}
