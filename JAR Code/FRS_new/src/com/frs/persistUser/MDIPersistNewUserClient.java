/**
 * 
 */
package com.frs.persistUser;

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
public class MDIPersistNewUserClient {

	public String persistNewUser(String inputJson) {

		System.out.println("persistNewUser() method starts..");
		
		Client client = ClientBuilder.newClient();

		client.property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true);
		//String imageQualityOutputJson = client.target("https://demo.uis.accenture.com/MEVISV2.0/api/Biometric").request().header("Authorization", "Basic XGNsb3VkdXNlcjpEZW1vc3VpdGUyMDEzIyM=").method("quality",Entity.entity(inputJson, MediaType.APPLICATION_JSON), String.class);
		
		String imageQualityOutputJson = client.target(URI.create("https://demo.uis.accenture.com/RestProxyHybris/api/identity")).request().header("Authorization", "Basic XGNsb3VkdXNlcjpEZW1vc3VpdGUyMDEzIyM=").method("enroll", Entity.entity(inputJson, MediaType.APPLICATION_JSON), String.class);
		
		System.out.println("Response : "+imageQualityOutputJson);
		
		return imageQualityOutputJson;
		
	}

	
	
}
