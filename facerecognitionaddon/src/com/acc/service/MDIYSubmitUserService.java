/**
 *
 */
package com.acc.service;

import de.hybris.platform.core.model.user.CustomerModel;

import com.acc.model.CSRCustomerDetailsModel;


/**
 * @author swarnima.gupta
 *
 */
public interface MDIYSubmitUserService
{
	public void addUserToQueue(CSRCustomerDetailsModel model);

	public CustomerModel doesCustomerExist(String identityId);

}
