/**
 * 
 */
package com.frs.submitUser;

import javax.servlet.http.HttpServletRequest;

import com.frs.bean.MDIRequestResponseBean;
import com.frs.common.MDIProfileHelper;
import com.frs.common.MDIProfileUtils;

/**
 * @author Pavan
 *
 */
public class MDIApplicationSubmitUserUtil {

	private MDIApplicationSubmitUserInterface submitUserInterface;
	
	private MDIRequestResponseBean requestResponseBean;
	
	boolean isUserPersisted;
	
	
	public MDIApplicationSubmitUserUtil() {
		
	
	}
	
	/*
	 *  This method is written to be used by Biometrics Controller
	 *  
	 *  Gets the request and extracts bean out of it.
	 *  
	 */
	public StatusData submitUser(HttpServletRequest request){
		
		StatusData statusData = new StatusData();
		MDIProfileUtils profileUtilObject = new MDIProfileUtils();
		
		// Gets the populated request response bean from the input request 
		MDIRequestResponseBean requestResponseBean = profileUtilObject.getBeanFromRequest(request);
		
		if(requestResponseBean.getUser().getBiometricId()==null || requestResponseBean.getUser().getBiometricId()==""){
			// If the biometric id is empty , he is an unregistered user.
			// Route him to notifyAgentForNewUser() 
			statusData = submitUserInterface.notifyAgentForNewUser(requestResponseBean);
			
		}
		else {
			
			// Biometric id is present, so he is a registered user.
			// Route him to notifyAgentForRegisteredUser() 
			statusData = submitUserInterface.notifyAgentForRegisteredUser(requestResponseBean);
			
		}
		
		
		return statusData;
	}
	


	/**
	 * @return the isUserPersisted
	 */
	public boolean isUserPersisted() {
		return isUserPersisted;
	}

	/**
	 * @param isUserPersisted the isUserPersisted to set
	 */
	public void setUserPersisted(boolean isUserPersisted) {
		this.isUserPersisted = isUserPersisted;
	}

	/**
	 * @return the requestResponseBean
	 */
	public MDIRequestResponseBean getRequestResponseBean() {
		return requestResponseBean;
	}

	/**
	 * @param requestResponseBean the requestResponseBean to set
	 */
	public void setRequestResponseBean(MDIRequestResponseBean requestResponseBean) {
		this.requestResponseBean = requestResponseBean;
	}


	/**
	 * @return the submitUserInterface
	 */
	public MDIApplicationSubmitUserInterface getSubmitUserInterface() {
		return submitUserInterface;
	}


	/**
	 * @param submitUserInterface the submitUserInterface to set
	 */
	public void setSubmitUserInterface(
			MDIApplicationSubmitUserInterface submitUserInterface) {
		this.submitUserInterface = submitUserInterface;
	}

	
}
