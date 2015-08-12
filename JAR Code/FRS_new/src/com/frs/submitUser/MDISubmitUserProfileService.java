/**
 * 
 */
package com.frs.submitUser;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;

import com.frs.common.MDIProfileHelper;

/**
 * @author Pavan
 *
 */
@Path("/submitUser")
public class MDISubmitUserProfileService{
	
	private final String RESULT = "result";
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response submitUser(String json) {
		// METHOD IS NOT USED
		boolean result;
		
			MDIProfileHelper mdiProfileHelper = new MDIProfileHelper();
		
			result = mdiProfileHelper.submitUser(json);
		
			// Putting the result into a json
			JSONObject resultJsonObject = new JSONObject();
			resultJsonObject.put(RESULT, result);
		
		return Response.status(201).entity(resultJsonObject.toJSONString()).build();
			
	}
	
}
