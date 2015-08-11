/**
 * 
 */
package com.frs.common;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
	
}
