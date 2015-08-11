/**
 * 
 */
package com.frs.imagequality;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

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
}
