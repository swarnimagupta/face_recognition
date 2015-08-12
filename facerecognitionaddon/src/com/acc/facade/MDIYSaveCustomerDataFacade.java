/**
 *
 */
package com.acc.facade;

import de.hybris.platform.core.model.user.CustomerModel;

import com.acc.data.StatusData;


/**
 * @author swarnima.gupta
 *
 */
public interface MDIYSaveCustomerDataFacade
{

	public void saveCustomerData(CustomerModel model);

	/**
	 * @param imageInBase64
	 * @param customerId
	 * @return StatusData
	 */
	public StatusData saveCustomerImage(final String imageInBase64, final String customerId);
}