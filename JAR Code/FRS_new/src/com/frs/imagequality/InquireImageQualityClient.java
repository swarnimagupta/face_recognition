/**
 * 
 */
package com.frs.imagequality;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.HttpUrlConnectorProvider;

/**
 * @author Pavan
 *
 */


public class InquireImageQualityClient {

	
	public String getQuality(String inputJson){
		
		System.out.println("getQuality() method starts..");
		
		Client client = ClientBuilder.newClient();

		client.property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true);
		//String imageQualityOutputJson = client.target("https://demo.uis.accenture.com/MEVISV2.0/api/Biometric").request().header("Authorization", "Basic XGNsb3VkdXNlcjpEZW1vc3VpdGUyMDEzIyM=").method("quality",Entity.entity(inputJson, MediaType.APPLICATION_JSON), String.class);
		
		String imageQualityOutputJson = client.target(URI.create("https://demo.uis.accenture.com/MEVISV2.0/api/Biometric")).request().header("Authorization", "Basic XGNsb3VkdXNlcjpEZW1vc3VpdGUyMDEzIyM=").method("quality", Entity.entity(inputJson, MediaType.APPLICATION_JSON), String.class);
		
		System.out.println("Response : "+imageQualityOutputJson);
		
		return imageQualityOutputJson;
		
	}
	
	
	/*public String getQuality1(String inputJson){
		
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

				URL targetUrl = new URL("https://demo.uis.accenture.com/MEVISV2.0/api/Biometric/");

				byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
				String authStringEnc = new String(authEncBytes);
				
				HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
				httpConnection.setDoOutput(true);
				//Map map = httpConnection.getHeaderFields();
				httpConnection.setRequestMethod("QUALITY");
				httpConnection.setRequestProperty("Content-Type", "application/json");
				httpConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
				
				OutputStream outputStream = httpConnection.getOutputStream();
				outputStream.write(inputJson.getBytes());
				outputStream.flush();

				if (httpConnection.getResponseCode() != 200) {
					System.out.println("Response Code : "+httpConnection.getResponseCode());
					System.out.println("\nResponse Message : "+httpConnection.getResponseMessage());
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
	}*/
	
}
