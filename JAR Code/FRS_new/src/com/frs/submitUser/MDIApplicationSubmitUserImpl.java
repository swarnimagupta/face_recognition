/**
 * 
 */
package com.frs.submitUser;

import com.frs.bean.MDIRequestResponseBean;

/**
 * @author Pavan
 *
 */
public class MDIApplicationSubmitUserImpl {

	MDIApplicationSubmitUserInterface submitUserInterface;
	
	MDIRequestResponseBean requestResponseBean;
	
	boolean isUserPersisted;
	
	/**
	 * @param submitUserInterface
	 */
	public MDIApplicationSubmitUserImpl(
			MDIApplicationSubmitUserInterface submitUserInterface) {
		
		super();
		this.submitUserInterface = submitUserInterface;
		
		// Indicates if the user is present in the repo
		isUserPersisted = submitUserInterface.submitUserForProcessing(getRequestResponseBean());
		setUserPersisted(isUserPersisted);
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

	
}
