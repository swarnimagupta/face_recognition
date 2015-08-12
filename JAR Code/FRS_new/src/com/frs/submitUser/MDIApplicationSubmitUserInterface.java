/**
 * 
 */
package com.frs.submitUser;

import com.frs.bean.MDIRequestResponseBean;

/**
 * @author Pavan
 *
 */
public interface MDIApplicationSubmitUserInterface {

	public StatusData notifyAgentForRegisteredUser(MDIRequestResponseBean mdiRequestResponseBean);
	public StatusData notifyAgentForNewUser(MDIRequestResponseBean mdiRequestResponseBean);
	
}
