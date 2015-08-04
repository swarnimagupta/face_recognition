/**
 *
 */
package com.acc.facade;

import com.acc.data.StatusData;
import com.acc.domain.dummy.MDIUser;


/**
 * @author swarnima.gupta
 *
 */
public interface MDIYSubmitUserFacade
{

	public StatusData addUserToQueue(MDIUser user);
}
