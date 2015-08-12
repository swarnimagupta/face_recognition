/**
 * 
 */
package com.frs.common;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.frs.bean.MDIRequestResponseBean;
import com.frs.bean.MDIUser;

/**
 * @author Pavan
 *
 */
public class MDIProfileUtils implements MDIProfileUtilsInterface , MDIConstants{

	@Override
	public String getImageInBase64(BufferedImage image) {
		String imageInBase64 = null;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		System.out.println("getImageInBase64() Method Starts...");		
		
		try {

			ImageIO.write(image, "jpeg", byteArrayOutputStream);
			byte[] imageInBytes = byteArrayOutputStream.toByteArray();
			imageInBase64 = org.apache.commons.codec.binary.Base64
					.encodeBase64URLSafeString(imageInBytes);

		} catch (IOException e) {
			System.out.println("IO Exception : " + e);
		}

		System.out.println("getImageInBase64() Method Ends... Returning base64 String : "+imageInBase64);
		
		return imageInBase64;
	}

	@Override
	public String getJsonForQuality(MDIRequestResponseBean reqResBean) {

		System.out.println("getJsonForQuality() .. Method Starts...");
		String imageInBase64 = reqResBean.getUser().getImageInBase64();
		
		JSONObject jsonObject = new JSONObject();
		jsonObject = getJson(jsonObject, imageInBase64); 
		
		System.out.println("getJsonForQuality Method Ends.. Returned json : "+jsonObject.toJSONString());
			
		return jsonObject.toJSONString();
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.frs.common.MDIProfileUtilsInterface#getJsonForNewUser(com.frs.bean.MDIRequestResponseBean)
	 * 
	 * This Method is written to obtain 'request' json string for Persist New User flow
	 * Input : RequestResponseBean
	 * Output : jsonString 
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getJsonForNewUser(MDIRequestResponseBean requestResponseBean) {
		
		System.out.println("getJsonForNewUser. Method Starts....");
		
		JSONObject jsonMainObject = new JSONObject();
		JSONObject jsonSubjectObject = new JSONObject();
		JSONObject jsonNameObject = new JSONObject();
		JSONObject jsonIdObject = new JSONObject();
		JSONObject jsonImageObject = new JSONObject();
		
		MDIUser user = requestResponseBean.getUser();
		
		JSONArray jsonBiographicDataArray = new JSONArray();
		JSONArray jsonBiometricDatasArray = new JSONArray();
		
		/* Create JSON Name Object. that is, 
		 {
			"Value": "Ravi",
			"Key": "Name"
		 }
		*/
		jsonNameObject.put(MDIConstants.KEY, MDIConstants.NAME);
		jsonNameObject.put(MDIConstants.VALUE, user.getUserName());
		
		/*
		 * Create Json Id Object
		 */
		jsonIdObject.put(MDIConstants.KEY, MDIConstants.ID);
		jsonIdObject.put(MDIConstants.VALUE, user.getHybrisId());
		
		// Put those into jsonBiographicDataArray.
		jsonBiographicDataArray.add(jsonNameObject);
		jsonBiographicDataArray.add(jsonIdObject);
		
		// Construct Biometric Datas Array;
		jsonImageObject = getJson(jsonImageObject, user.getImageInBase64());
		jsonBiometricDatasArray.add(jsonImageObject);
		
		// Creating json Subject Object
		jsonSubjectObject.put(MDIConstants.BIOGRAPHIC_DATA, jsonBiographicDataArray);
		jsonSubjectObject.put(MDIConstants.BIO_METRIC_DATAS, jsonBiometricDatasArray);
				
		// Creating json Main Object
		jsonMainObject.put(MDIConstants.BIOMETRIC_GROUP_ID_KEY, MDIConstants.BIOMETRIC_GROUP_ID_VALUE);
		jsonMainObject.put(MDIConstants.BIOMETRIC_CLIENT_ID_KEY, MDIConstants.BIOMETRIC_CLIENT_ID_VALUE);
		jsonMainObject.put(MDIConstants.SUBJECT, jsonSubjectObject);
		
		System.out.println("getJsonForNewUser. Method Ends...");
		System.out.println("Json is "+jsonMainObject.toJSONString());
		
		return jsonMainObject.toJSONString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getJson(JSONObject jsonObject, String imageInBase64) {

		System.out.println("getJson() Starts");
		
		JSONObject jsonObj = jsonObject;
		jsonObject.put(MDIConstants.BASE64DATA, imageInBase64);
		jsonObject.put(MDIConstants.MODALITY_KEY, MDIConstants.MODALITY_VALUE);
		jsonObject.put(MDIConstants.IMAGE_TYPE_KEY, MDIConstants.IMAGE_TYPE_VALUE);
		
		System.out.println("getJson() Ends");
		
		return jsonObj;
	}
	

	public MDIRequestResponseBean getBeanFromRequest(HttpServletRequest request) {
	
		// get Json From Request
		
		Scanner scanner;
		MDIRequestResponseBean requestResponseBean = new MDIRequestResponseBean();
		try {
			scanner = new Scanner(request.getInputStream());
			String submitUserJson = scanner.useDelimiter("\\Z").next();
		
			// get Bean from Json
		 
			MDIUser mdiUser = processInputUser(submitUserJson);
			requestResponseBean.setUser(mdiUser);
		
			scanner.close();
		
			} catch (IOException e) {
			
				System.out.println("IO Exception : \n");
				e.printStackTrace();
			
			}
		
			return requestResponseBean;
	}
	
		// The method accepts the inputJson and returns the user
		public MDIUser processInputUser(String inputJson) {
			
			MDIUser mdiUser = new MDIUser();
			
			JSONParser jsonParser = new JSONParser();
			try {
				// Parse Input json and get the user object
				JSONObject jsonMainObject = (JSONObject)jsonParser.parse(inputJson);
				JSONObject jsonUserObject = (JSONObject)jsonMainObject.get(MDIConstants.USER);
				
				
				// From the user object set the appropriate values to User bean
				mdiUser.setBiometricId((String)jsonUserObject.get(MDIConstants.BIOMETRIC_USER_ID));
				mdiUser.setAge(Integer.parseInt(jsonUserObject.get(MDIConstants.CUSTOMER_AGE).toString()));
				mdiUser.setComplexion((String)jsonUserObject.get(MDIConstants.CUSTOMER_COMPLEXION));
				mdiUser.setGender((String)jsonUserObject.get(MDIConstants.CUSTOMER_GENDER));
				
			} catch (ParseException e) {
				System.out.println("Parser Exception. \n");
				e.printStackTrace(); 
			}
			
			return mdiUser;
		}
}
