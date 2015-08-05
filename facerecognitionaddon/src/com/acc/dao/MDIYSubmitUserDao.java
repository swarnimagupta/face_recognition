/**
 *
 */
package com.acc.dao;

import de.hybris.platform.core.model.user.CustomerModel;

/**
 * @author swarnima.gupta
 *
 */
public interface MDIYSubmitUserDao
{
	public CustomerModel doesCustomerExist(String identityId);
}
