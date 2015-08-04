/**
 *
 */
package com.acc.facade;

import de.hybris.platform.core.model.user.CustomerModel;

import java.awt.image.BufferedImage;

import com.acc.data.ImageQualityData;


/**
 * @author swarnima.gupta
 *
 */
public interface MDIYSaveCustomerDataFacade
{

	public void saveCustomerData(CustomerModel model);

	/**
	 * @param image
	 * @return
	 */
	@SuppressWarnings("javadoc")
	public ImageQualityData saveCustomerImage(final BufferedImage image);
}
