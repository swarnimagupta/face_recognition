/**
 * 
 */
package com.frs.test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * @author Pavan
 *
 */
public class TestSubmitUserProfile {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String json = "{ \"User\" : { \"Age\" : 26, \"BiometricUserId\" : \"user_id1\", \"Complexion\" : \"Black\", \"Gender\" : \"Male\"} }";
		
		String baseURI = "http://localhost:8080/FRS_new/rest/submitUser";
		try{
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource resource = client.resource(baseURI);
		
		ClientResponse response = resource.type("application/json").post(ClientResponse.class, json);
		
		System.out.println("Webservice Output json : "+response.getEntity(String.class));
		}
		catch(Exception e){
			System.out.println("Stack Trace : \n");
			e.printStackTrace();
		}
		
		
	}

}
