/**
 * 
 */
package com.frs.common;

import java.awt.image.BufferedImage;

import org.json.simple.JSONObject;

import com.frs.bean.MDIRequestResponseBean;
import com.frs.bean.MDIUser;

/**
 * @author Pavan
 *
 */
public interface MDIProfileUtilsInterface {

	public String getImageInBase64(BufferedImage image);
	
	public JSONObject getJson(JSONObject jsonObject , String imageInBase64);
	
	public String getJsonForQuality(MDIRequestResponseBean reqResBean);
	
	public String getJsonForNewUser(MDIRequestResponseBean reqResBean);
	
}
