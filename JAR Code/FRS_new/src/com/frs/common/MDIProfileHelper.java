/**
 * 
 */
package com.frs.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.frs.bean.MDIRequestResponseBean;
import com.frs.bean.MDIUser;
import com.frs.imagequality.InquireImageQualityClient;
import com.frs.imagequality.MDIApplicationImageQualityInterface;
import com.frs.persistUser.MDIApplicationNewUserInterface;
import com.frs.persistUser.MDIPersistNewUserClient;
import com.frs.submitUser.MDIApplicationSubmitUserUtil;
import com.frs.submitUser.MDIApplicationSubmitUserInterface;
import com.frs.submitUser.MDIHybrisClass;
import com.frs.submitUser.MDIHybrisInterface;
import com.frs.submitUser.StatusData;

/**
 * @author Pavan
 *
 */
public class MDIProfileHelper implements MDIApplicationImageQualityInterface, MDIApplicationNewUserInterface, MDIConstants {

	
	@Override
	public MDIRequestResponseBean checkQuality(String inputJson) {

		System.out.println("checkQuality. Method Starts. Input json : "+inputJson);
		
		InquireImageQualityClient client = new InquireImageQualityClient();
		MDIRequestResponseBean requestResponseBean;
		
		String outputJson = client.getQuality(inputJson);
		requestResponseBean = processImage(outputJson);
		
		System.out.println("checkQuality. Method Ends... Bean returned is : "+requestResponseBean);	
		
		return requestResponseBean;
	}

	@Override
	public MDIRequestResponseBean processImage(String inputJson) {
		
		// This method is written to process the json String and populate the beans
		
		System.out.println("processImage Method Starts : Input json is "+inputJson);
		
		JSONParser parser = new JSONParser();
		MDIRequestResponseBean requestResponseBean = new MDIRequestResponseBean();
		Map<String, String> qualityMap = new HashMap<String, String>();
		try {
			// Read the json using json parser and get json Object out of it. 
			JSONObject jsonObject= (JSONObject)parser.parse(inputJson);
			
			// Get the internal json.
			JSONArray jsonArray = (JSONArray) jsonObject.get(MDIConstants.QUALITY_DATAS);
			
			// Iterate the internal array to get the required map
			Iterator iterator = jsonArray.iterator();
			while (iterator.hasNext()) {
				JSONObject tempObject = (JSONObject) iterator.next();
				qualityMap.put(tempObject.get(MDIConstants.QUALITY_ALGORITHM).toString(), tempObject.get(MDIConstants.QUALITY_SCORE).toString());
				System.out.println("QA : "+tempObject.get(MDIConstants.QUALITY_ALGORITHM));
				System.out.println("QS : "+tempObject.get(MDIConstants.QUALITY_SCORE));
			}
		} catch (ParseException e) {
			System.out.println("Parse Exception. Check the json");
			e.printStackTrace();
		}
		
		requestResponseBean.setQualityScoreMap(qualityMap);
		
		System.out.println("processImage() ends.");
		
		return requestResponseBean;
	}

	@Override
	public MDIRequestResponseBean newUser(String inputJson) {
		
		System.out.println("newUser(). Method Starts. Input json : "+inputJson);
		
		MDIPersistNewUserClient client = new MDIPersistNewUserClient();
		MDIRequestResponseBean requestResponseBean = null;
		
		String outputJson = client.persistNewUser(inputJson);
		requestResponseBean = processNewUser(outputJson);
		
		System.out.println("newUser. Method Ends... Bean returned is : "+requestResponseBean);	
		
		return requestResponseBean;
		
	}

	
	/*
	 * This Method is used to parse the json from service 
	 * and fill the objects with new user properties 
	 * 
	 * Input : json string from NewUser Service
	 * Output : request Response BEan with Biometric id populated
	 * 
	 */
	@SuppressWarnings("unchecked")
	public MDIRequestResponseBean processNewUser(String inputJson) {
		
		System.out.println("processNewUser Method Starts... Output json : "+inputJson);
		
		MDIRequestResponseBean requestResponseBean = new MDIRequestResponseBean();
		MDIUser mdiUser = new MDIUser();
		
		Map newUserResultMap = new HashMap();
		JSONParser parser = new JSONParser();
 
		try {
			// Read the json using json parser and get json Object out of it.
			JSONObject jsonObject= (JSONObject)parser.parse(inputJson);
			
			JSONArray jsonIdentitiesArray = (JSONArray) jsonObject.get("Identities");
			JSONObject jsonIdentitiesObject = (JSONObject) jsonIdentitiesArray.get(0);
			
			JSONObject jsonIdentityDetailsObject = (JSONObject) jsonIdentitiesObject.get("IdentityDetails");
			
			JSONArray jsonBiographicDataArray = (JSONArray)jsonIdentityDetailsObject.get("BiographicData");
			
			Iterator iterator = jsonBiographicDataArray.iterator();
			
			while(iterator.hasNext()){
				JSONObject tempObject = (JSONObject) iterator.next();
				newUserResultMap.put(tempObject.get("Key").toString(), tempObject.get("Value").toString());
				System.out.println("Key : "+tempObject.get("Key"));
				System.out.println("Value : "+tempObject.get("Value"));
			}
			
			if(newUserResultMap.containsKey("ID")){
				System.out.println("Biometric ID is obtained. ID is "+newUserResultMap.get("ID"));
				mdiUser.setBiometricId((String)newUserResultMap.get("ID"));
				requestResponseBean.setUser(mdiUser);
			}else{
				System.out.println("Biometric User ID is null");
			}
			
		} catch (ParseException e) {
			System.out.println("Parse Exception. Exception is "+e);
			e.printStackTrace();
		}
		
		System.out.println("processNewUser Method Ends..");
		
		return requestResponseBean;
	}

	/*
	 * METHOD IS NOT USED
	 * 
	 * Method is written to process submit user functionality
	 * 
	 * The method takes the inputJson, converts it into 
	 * User Object and sends it to Hybris for processing  
	 * 
	 */
	
	public boolean submitUser(String inputJson) {

		MDIRequestResponseBean mdiRequestResponseBean = new MDIRequestResponseBean();
		MDIProfileUtils mdiProfileUtils = new MDIProfileUtils();
		MDIApplicationSubmitUserUtil submitUserImpl = new MDIApplicationSubmitUserUtil();
		boolean isProcessed = false;
		
			MDIUser mdiUser = mdiProfileUtils.processInputUser(inputJson);
			mdiRequestResponseBean.setUser(mdiUser);
			submitUserImpl.setRequestResponseBean(mdiRequestResponseBean);
			
		
		return isProcessed;
	}

	
}
