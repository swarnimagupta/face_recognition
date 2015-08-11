/**
 * 
 */
package com.frs.bean;

import java.util.Map;

/**
 * @author Pavan
 *
 */
public class MDIRequestResponseBean {

	
	// This is the User Bean which holds all the User Details
	MDIUser user; 
	
	// This map contains quality Score Details of a User Image
	Map<String, String> qualityScoreMap;

	/**
	 * @return the user
	 */
	public MDIUser getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(MDIUser user) {
		this.user = user;
	}

	/**
	 * @return the qualityScoreMap
	 */
	public Map<String, String> getQualityScoreMap() {
		return qualityScoreMap;
	}

	/**
	 * @param qualityScoreMap the qualityScoreMap to set
	 */
	public void setQualityScoreMap(Map<String, String> qualityScoreMap) {
		this.qualityScoreMap = qualityScoreMap;
	}
	
}
