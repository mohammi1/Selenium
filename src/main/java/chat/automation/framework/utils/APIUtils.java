package chat.automation.framework.utils;
import com.aventstack.extentreports.Status;

import chat.automation.framework.helper.ActionsHelper;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
public class APIUtils extends ActionsHelper{
	public static Response endPointResponse;
	public static RequestSpecification request;
	public static Response postEndPointResponse;
	public static RequestSpecification createAuthRequest(String AuthURL, String apiKey, String jsonBody) {
		//RequestSpecification request = null;
		try {
			
			RestAssured.baseURI = AuthURL;
	        request = RestAssured.given();
	        request.header("ApiKey", apiKey);
	        request.header("Content-Type", "application/json");
	        request.body(jsonBody);
			
		}catch(Exception e){
			Reporter.ReportEvent(Status.FAIL, getClassMethodName(),	"Error Description:'"+e.toString() +"'");
		}
		
        return request;
    }
	
	
	public static String getToken(String AuthURL, String apiKey, String jsonBody) {
		String token = null;
		try {
			RequestSpecification request=APIUtils.createAuthRequest(AuthURL,apiKey,jsonBody);
			Response response = request.post();
			token = response.jsonPath().getString("accessToken");
			Environment.setProperty("Token", token);
			
			
		}catch(Exception e){
			Environment.setProperty("Token", token);
			Reporter.ReportEvent(Status.FAIL, getClassMethodName(),	"Error Description:'"+e.toString() +"'");
		}
		
		return token;
		
	}
	
	 public static Response getResponse(String requestType, String endpointURL, String headerDetails, String token) {
		 
		 try {
			RequestSpecification getRequest = RestAssured.given();
	    	getRequest.header("Authorization", "Bearer " + token);
	    	getRequest.header("email",headerDetails);
	    	getRequest.header("Content-Type", "application/json");    
	        switch(requestType.toUpperCase()){
	        	case "GET":
	        		Response getResponse = getRequest.get(endpointURL);
	        		endPointResponse = getResponse.then().extract().response();
	        		System.setProperty("RequestType", "GET");
	        		break;
	        	case "PUT":
	        		Response putResponse = getRequest.put(endpointURL);
	        		endPointResponse = putResponse.then().extract().response();
	        		System.setProperty("RequestType", "PUT");
	        		break;
	        	case "POST":
	        		Response postResponse = getRequest.post(endpointURL);
	        		endPointResponse = postResponse.then().extract().response();
	        		System.setProperty("RequestType", "POST");
	        		break;
	        	case "DELETE":
	        		Response deleteResponse = getRequest.delete(endpointURL);
	        		endPointResponse = deleteResponse.then().extract().response();
	        		System.setProperty("RequestType", "DELETE");
	        		break;
	        }
	        
		 }catch(Exception e){
				Reporter.ReportEvent(Status.FAIL, getClassMethodName(),	"Verify Response Type:'"+ System.getProperty("RequestType") +"', Error Description:'"+e.toString() +"'");
		  }
		 
		return endPointResponse;
	 }
	 
 public static Response getResponse(String requestType, String endpointURL, String headerDetails, String token, String jsonBody) {
		 
		 try {
			RequestSpecification getRequest = RestAssured.given();
	    	getRequest.header("Authorization", "Bearer " + token);
	    	getRequest.header("email",headerDetails);
	    	getRequest.header("Content-Type", "application/json");
	    	switch(requestType.toUpperCase()){
        	case "GET":
        		Response getResponse = getRequest.get(endpointURL);
        		endPointResponse = getResponse.then().extract().response();
        		System.setProperty("RequestType", "GET");
        		break;
        	case "PUT":
        		getRequest.body(jsonBody);
        		Response putResponse = getRequest.put(endpointURL);
        		endPointResponse = putResponse.then().extract().response();
        		System.setProperty("RequestType", "PUT");
        		break;
        	case "POST":
        		getRequest.body(jsonBody);
        		Response postResponse = getRequest.post(endpointURL);
        		endPointResponse = postResponse.then().extract().response();
        		System.setProperty("RequestType", "POST");
        		break;
        	case "DELETE":
        		getRequest.body(jsonBody);
        		Response deleteResponse = getRequest.delete(endpointURL);
        		endPointResponse = deleteResponse.then().extract().response();
        		System.setProperty("RequestType", "DELETE");
        		break;
        }
		 }catch(Exception e){
				Reporter.ReportEvent(Status.FAIL, getClassMethodName(),	"Verify Response Type:'"+ System.getProperty("RequestType") +"', Error Description:'"+e.toString() +"'");
		  }
		 
		return endPointResponse;
	 }
	 
	 public static void verifyStatusCode(Response endPointResponse, int expectedStatusCode) {
		 try {
			 if(endPointResponse.statusCode()==expectedStatusCode) {
				 Reporter.ReportEvent(Status.PASS, "Verify the Status code of Request Type:'"+ System.getProperty("RequestType") +"'","Successfully verified the Status code, Expected Status code:'"+ expectedStatusCode + "', Actual Status Code:'"+ endPointResponse.statusCode() +"'");
		     }else {
		        	Reporter.ReportEvent(Status.FAIL, "Verify the Status code of Request Type:'"+ System.getProperty("RequestType") +"'","Verification failed, Expected Status code:'"+ expectedStatusCode + "', Actual Status Code:'"+ endPointResponse.statusCode() +"', Status Line:'"+endPointResponse.getStatusLine() +"' displayed");
		     }
		 }catch(Exception e){
			 Reporter.ReportEvent(Status.FAIL, getClassMethodName(),"Request Type:'" + System.getProperty("RequestType")+", Error Description:'"+e.toString() +"'");
		 }
	 }
	 
	 public static void verifyResponseBody(Response endPointResponse, String jsonPath, Object expectedValue) {
		 String strActualValue=null;
		 String strexpectedValue=null;
		 try {
			 ResponseBody<?> responsebody=endPointResponse.getBody();
		     JsonPath jsonPathView=responsebody.jsonPath();
		     Object actualvalue= jsonPathView.get(jsonPath);
		     if(actualvalue!=null) { 	
		    	 String dType=actualvalue.getClass().getName();
		    	 switch(dType){
		    	 	case "java.lang.Integer":
		        		strActualValue = convertToString(actualvalue);
		        		break;
		        	case "java.lang.String":
		        		strActualValue = convertToString(actualvalue);
		        		break;
		        	case "java.lang.Double":
		        		strActualValue = convertToString(actualvalue);
		        		break;
		        	case "java.lang.Boolean":
		        		strActualValue=convertToString(actualvalue);
		        		break;
		        	case "java.lang.Float":
		        		strActualValue=convertToString(actualvalue);
		        	}
		        }
		        
		        if(expectedValue!=null) { 	
		        	String dataType=expectedValue.getClass().getName();
		        	switch(dataType){
		        	case "java.lang.Integer":
		        		strexpectedValue = convertToString(expectedValue);
		        		break;
		        	case "java.lang.String":
		        		strexpectedValue = convertToString(expectedValue);
		        		break;
		        	case "java.lang.Double":
		        		strexpectedValue = convertToString(expectedValue);
		        		break;
		        	case "java.lang.Boolean":
		        		strexpectedValue = convertToString(expectedValue);
		        		break;
		        	case "java.lang.Float":
		        		strexpectedValue = convertToString(expectedValue);
		        	}
		        }
		        
		        if((expectedValue!=null) && (actualvalue!=null)) {
		        	ExpectedConditions.assertEquals(strActualValue.trim(), strexpectedValue.trim());
		        }else
		        if((expectedValue==null) && (actualvalue==null)) {
		        	Object objActualvalue= jsonPathView.get(jsonPath);
		        	String strActValue=(String) objActualvalue;
		        	ExpectedConditions.assertNull(strActValue);
		        	
		        }else
		        if(((expectedValue==null) && (actualvalue!=null)) || ((expectedValue!=null) && (actualvalue==null))) {
		        	ExpectedConditions.assertEquals(strActualValue, strexpectedValue);
		        }
			 
		 }catch(Exception e){
			 Reporter.ReportEvent(Status.FAIL, getClassMethodName(),"Error Description:'"+e.toString() +"'");
		 }
		 
	 }
		 
	  /*public static void validateDelete(String endpoint, String headerEmailValue, String jsonbody ) {
			RequestSpecification request = RestAssured.given();
	    	request.header("Authorization", "Bearer " + token);
	    	request.header("email",headerEmailValue);
	    	request.header("Content-Type", "application/json"); 	
		   	request.body(jsonbody);
	        Response getRequestResponse = request.delete(endpoint);
	        Response getResponse = getRequestResponse.then().extract().response();
	        System.out.println(getResponse.statusCode());
			Assert.assertEquals(getResponse.statusCode(), 200); 
		   }*/	  
	/* public static Response postResponse(String endpointURL, String headerDetails, String token, String jsonBody) {
		 try {
			request = RestAssured.given();	
	    	request.header("Authorization", "Bearer " + token);
	    	request.header("email",headerDetails);
	    	request.header("Content-Type", "application/json"); 	
		   	request.body(jsonBody);
	        Response response = request.post(endpointURL);
	        postEndPointResponse = response.then().extract().response();
		 }catch(Exception e){
			 Reporter.ReportEvent(Status.FAIL, getClassMethodName(),"Error Description:'"+e.toString() +"'");
		 }
			
	        return postEndPointResponse;
	  }
	 
	 public static void verifyPOSTStatusCode(Response endPointResponse, int expectedStatusCode) {
		 try {
			 if(endPointResponse.statusCode()==expectedStatusCode) {
				 Reporter.ReportEvent(Status.PASS, "Verify the Status code for the POST API","Successfully verified the Status code : '200' ");
		     }else {
		        	Reporter.ReportEvent(Status.FAIL, "Verify the Status code for the POST API","Verification failed, Status code : '"+ endPointResponse.statusCode() +"', Status Line:'"+endPointResponse.getStatusLine() +"' displayed");
		        	
		     }
		 }catch(Exception e){
			 Reporter.ReportEvent(Status.FAIL, getClassMethodName(),"Error Description:'"+e.toString() +"'");
		 }
	 }
	 
	 public static void verifyPOSTResponseBody(Response postEndPointResponse, String jsonPath, Object expectedValue) {
		 String strActualValue=null;
		 String strexpectedValue=null;
		 try {
			 ResponseBody<?> responsebody=postEndPointResponse.getBody();
		     JsonPath jsonPathView=responsebody.jsonPath();
		     Object actualvalue= jsonPathView.get(jsonPath);
		     if(actualvalue!=null) { 	
		    	 String dType=actualvalue.getClass().getName();
		    	 switch(dType){
		    	 	case "java.lang.Integer":
		        		strActualValue = convertToString(actualvalue);
		        		break;
		        	case "java.lang.String":
		        		strActualValue = convertToString(actualvalue);
		        		break;
		        	case "java.lang.Double":
		        		strActualValue = convertToString(actualvalue);
		        		break;
		        	case "java.lang.Boolean":
		        		strActualValue=convertToString(actualvalue);
		        		break;
		        	case "java.lang.Float":
		        		strActualValue=convertToString(actualvalue);
		        	}
		        }
		        
		        if(expectedValue!=null) { 	
		        	String dataType=expectedValue.getClass().getName();
		        	switch(dataType){
		        	case "java.lang.Integer":
		        		strexpectedValue = convertToString(expectedValue);
		        		break;
		        	case "java.lang.String":
		        		strexpectedValue = convertToString(expectedValue);
		        		break;
		        	case "java.lang.Double":
		        		strexpectedValue = convertToString(expectedValue);
		        		break;
		        	case "java.lang.Boolean":
		        		strexpectedValue = convertToString(expectedValue);
		        		break;
		        	case "java.lang.Float":
		        		strexpectedValue = convertToString(expectedValue);
		        	}
		        }
		        
		        if((expectedValue!=null) && (actualvalue!=null)) {
		        	ExpectedConditions.assertEquals(strActualValue.trim(), strexpectedValue.trim());
		        }else
		        if((expectedValue==null) && (actualvalue==null)) {
		        	Object objActualvalue= jsonPathView.get(jsonPath);
		        	String strActValue=(String) objActualvalue;
		        	ExpectedConditions.assertNull(strActValue);
		        	
		        }else
		        if(((expectedValue==null) && (actualvalue!=null)) || ((expectedValue!=null) && (actualvalue==null))) {
		        	ExpectedConditions.assertEquals(strActualValue, strexpectedValue);
		        }
			 
		 }catch(Exception e){
			 Reporter.ReportEvent(Status.FAIL, getClassMethodName(),"Error Description:'"+e.toString() +"'");
		 }
		 
	 }
	  /*public static void validatePut(String endpoint, String headerEmailValue, String jsonbody ) {
			RequestSpecification request = RestAssured.given();	
	    	request.header("Authorization", "Bearer " + token);
	    	request.header("email",headerEmailValue);
	    	request.header("Content-Type", "application/json"); 	
		   	request.body(jsonbody);
	        Response getRequestResponse = request.put(endpoint);
	        Response getResponse = getRequestResponse.then().extract().response();
	        System.out.println(getResponse.statusCode());
			Assert.assertEquals(getResponse.statusCode(), 200); 
	   }*/
	
	 private static String getClassMethodName(){
			String className,classSimpleName,methodName,classMethodName;
			className = Thread.currentThread().getStackTrace()[1].getClassName();
			classSimpleName = className.replace(".", "/").split("/")[className.replace(".", "/").split("/").length-1];
			methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			classMethodName =  classSimpleName + ":" + methodName ;
			return classMethodName; 
		}
   
}
