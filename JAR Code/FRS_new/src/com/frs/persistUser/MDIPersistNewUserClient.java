/**
 * 
 */
package com.frs.persistUser;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * @author Pavan
 *
 */
public class MDIPersistNewUserClient {

	public String persistNewUser(String inputJson) {

		System.out.println("persistNewUser() method starts..");
		
		String newUserOutputJson = null;
		String persistNewUserUrl = "https://demo.uis.accenture.com/MEVISV2.0/api/Biometric/ENROLL";
		
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource webResource = client.resource(persistNewUserUrl);
		
//		ClientResponse response = webResource.accept("application/json").header("Authorization", "Basic XGNsb3VkdXNlcjpEZW1vc3VpdGUyMDEzIyM=").post(ClientResponse.class, inputJson);
		ClientResponse response = webResource.accept("application/json").header("Authorization", "Basic XGNsb3VkdXNlcjpEZW1vc3VpdGUyMDEzIyM=").method("ENROLL", ClientResponse.class, inputJson);
		
		if(response.getStatus() != 200){
			System.out.println("Unable to Connect to the Server");
		}
		
		newUserOutputJson = response.getEntity(String.class);
		
		System.out.println("persistNewUser() method ends ! Output Json is "+newUserOutputJson);
		
		return newUserOutputJson;
		
	}

	
	
}
