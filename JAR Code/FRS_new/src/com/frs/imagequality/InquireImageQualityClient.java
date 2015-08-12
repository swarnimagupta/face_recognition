/**
 * 
 */
package com.frs.imagequality;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

/**
 * @author Pavan
 *
 */
public class InquireImageQualityClient {

	public String getQuality(String inputJson){
		
		System.out.println("getQuality() method starts..");
		
		String imageQualityOutputJson = null;
		String imageQualityCheckUrl = "https://demo.uis.accenture.com/MEVISV2.0/api/Biometric/Pavan?Type=Image&Base64data=Image&Modality=Face_Face2D";
		
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource webResource = client.resource(imageQualityCheckUrl);
		
//		ClientResponse response = webResource.accept("application/json").header("Authorization", "Basic XGNsb3VkdXNlcjpEZW1vc3VpdGUyMDEzIyM=").post(ClientResponse.class, inputJson);
//		ClientResponse response = webResource.accept("application/json").header("Authorization", "Basic XGNsb3VkdXNlcjpEZW1vc3VpdGUyMDEzIyM=").method("QUALITY", ClientResponse.class, inputJson);
		ClientResponse response = webResource.accept("application/json").header("Authorization", "Basic XGNsb3VkdXNlcjpEZW1vc3VpdGUyMDEzIyM=").get(ClientResponse.class);
		
		if(response.getStatus() != 200){
			System.out.println("Unable to Connect to the Server");
		}
		
		imageQualityOutputJson = response.getEntity(String.class);
		
		System.out.println("getQuality() method ends ! Output Json is "+imageQualityOutputJson);
		
		return imageQualityOutputJson;
	}
	
	
	public String getQuality1(String inputJson){
		
		System.out.println("getQuality1() method starts..");
		
		String imageQualityOutputJson = null;
		String imageQualityCheckUrl = "https://demo.uis.accenture.com/MEVISV2.0/api/Biometric";
		InputStream ipStream = new ByteArrayInputStream( inputJson.getBytes() );		
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource webResource = client.resource(imageQualityCheckUrl);
		client.addFilter(new HTTPBasicAuthFilter("\\clouduser", "Demosuite2013##"));
	
		//ClientResponse response = webResource.accept("application/json").header("Authorization", "Basic XGNsb3VkdXNlcjpEZW1vc3VpdGUyMDEzIyM=").method("GET", ClientResponse.class, inputJson);
		webResource.method("POST", ClientResponse.class).setEntityInputStream(ipStream);
		ClientResponse response = webResource.get(ClientResponse.class);
	
		if(response.getStatus() != 200){
			System.out.println("Error in response : Http response code "+ response.getStatus());
		}
		
		imageQualityOutputJson = response.getEntity(String.class);
		
		System.out.println("getQuality1() method ends ! Output Json is "+imageQualityOutputJson);
		
		return imageQualityOutputJson;
		
	}
	
	public String getQualityWithoutUsingJersey(String inputJson){
		
		System.out.println("getQualityWithoutUsingJersey Starts...");
		
		String outputJson = null;
		String name = "\\cloudUser";
		String password = "Demosuite2013##";
		
		String authString = name+":"+password;
		

			try {

				URL targetUrl = new URL("https://demo.uis.accenture.com/MEVISV2.0/api/Biometric/QUALITY");

				byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
				String authStringEnc = new String(authEncBytes);

				
				
				HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
				httpConnection.setDoOutput(true);
				httpConnection.setRequestMethod("POST");
				httpConnection.setRequestProperty("Content-Type", "application/json");
				httpConnection.addRequestProperty("method", "QUALITY");
				httpConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);

				
				
				OutputStream outputStream = httpConnection.getOutputStream();
				outputStream.write(inputJson.getBytes());
				outputStream.flush();

				if (httpConnection.getResponseCode() != 200) {
					System.out.println("Response Code : "+httpConnection.getResponseMessage());
				}

				BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
						(httpConnection.getInputStream())));

				String output;
				System.out.println("Output from Server:\n");
				while ((output = responseBuffer.readLine()) != null) {
					System.out.println(output);
				}

				httpConnection.disconnect();

			  } catch (MalformedURLException e) {

				e.printStackTrace();

			  } catch (IOException e) {

				e.printStackTrace();

			 }


		
		System.out.println("getQualityWithoutUsingJersey Ends .. Output Json : "+outputJson);
		
		return outputJson;
	}
}
