package com.polaris.rest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/livy")
public class LivyController {

	@RequestMapping(method=RequestMethod.POST, value="batches", 
    		consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, 
    		produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public Batch submitBatchRequest(@RequestBody Request request) throws Exception {
    	String url = "https://polaris-spark.azurehdinsight.net/livy/batches";
	    LivySubmitRequest data = new LivySubmitRequest();
	    data.setFile("wasbs://polaris-spark-training@polaris598.blob.core.windows.net/polarissparkapp_2.10-1.0.jar");
	    data.setClassName("FakeReviewDetector");
	    String requestId = "1234";
	    data.setArgs(new String[]{String.valueOf(request.getLocationId()), String.valueOf(request.getBusinessId()), requestId});
		String resp = post(url, data);
    	return toBatch(resp);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="batches/{batchId}", 
    		produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public Batch getBatchRequest(@PathVariable int batchId) throws Exception {
		String url="https://polaris-spark.azurehdinsight.net/livy/batches/" + batchId;
		String resp = get(url);
    	return toBatch(resp);
	}

	private Batch toBatch(String resp) throws IOException, JsonParseException, JsonMappingException {
		ObjectMapper mapper = new ObjectMapper();
		Batch batch = mapper.readValue(resp, Batch.class);
		return batch;
	}
    
    private String post(String url, LivySubmitRequest data) throws Exception{
    	try {
    	    URL obj = new URL(url);
    	    HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
    	    con.setRequestProperty("Content-Type", "application/json");
    	    con.setDoOutput(true);
    	    con.setRequestMethod("PUT");
    	    String userpass = "admin" + ":" + "Polaris@CS598";
    	    String encoding = new String(Base64.encodeBase64(userpass.getBytes()));
    	    con.setRequestProperty("Authorization", "Basic " + encoding);
    	    con.setRequestProperty("Content-Type", "application/json");
    	    con.setRequestProperty("Accept", "application/json");
    	    
    	    ObjectMapper mapper = new ObjectMapper();
    	    String dataString = mapper.writeValueAsString(data);

    	    con.setDoOutput(true);
    	    DataOutputStream wr = new DataOutputStream(con.getOutputStream());
    	    wr.writeBytes(dataString);
    		wr.flush();
    		wr.close();

    		int responseCode = con.getResponseCode();
    		System.out.println("\nSending 'POST' request to URL : " + url);
    		System.out.println("Post data : " + dataString);
    		System.out.println("Response Code : " + responseCode);

    		BufferedReader in = new BufferedReader(
    		        new InputStreamReader(con.getInputStream()));
    		String inputLine;
    		StringBuffer response = new StringBuffer();

    		while ((inputLine = in.readLine()) != null) {
    			response.append(inputLine);
    		}
    		in.close();

    		return response.toString();
    	} catch (Exception e) {
    	    throw new Exception("Error sending post request to trigger Spark job.", e);
    	}
    }
    
    private String get(String url) throws Exception {

		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
	    String userpass = "admin" + ":" + "Polaris@CS598";
	    String encoding = new String(Base64.encodeBase64(userpass.getBytes()));
	    con.setRequestProperty("Authorization", "Basic " + encoding);
	    con.setRequestProperty("Accept", "application/json");
		con.setRequestMethod("GET");
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return response.toString();

	}
    
}
