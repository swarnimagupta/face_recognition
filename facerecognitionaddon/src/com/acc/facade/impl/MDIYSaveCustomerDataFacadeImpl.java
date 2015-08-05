/**
 *
 */
package com.acc.facade.impl;

import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.user.UserService;

import java.awt.image.BufferedImage;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.acc.data.ImageQualityData;
import com.acc.facade.MDIYSaveCustomerDataFacade;
import com.acc.model.ImageQualityModel;
import com.acc.service.MDIYSaveCustomerDataService;
import com.acc.util.HelperUtil;


/**
 * @author swarnima.gupta
 *
 */

public class MDIYSaveCustomerDataFacadeImpl implements MDIYSaveCustomerDataFacade
{

	private static final Logger LOG = Logger.getLogger(MDIYSaveCustomerDataFacadeImpl.class);

	private UserService userService;

	@Resource(name = "imageQualityConverter")
	private Converter<ImageQualityModel, ImageQualityData> imageQualityConverter;

	@Resource(name = "customerDataConverter")
	private Converter<CustomerModel, CustomerData> customerDataConverter;

	private MDIYSaveCustomerDataService mdiySaveCustomerDataService;


	@Override
	public void saveCustomerData(final CustomerModel model)
	{
		// YTODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.acc.core.facade.MDIYSaveCustomerDataFacade#saveCustomerImage(java.awt.image.BufferedImage)
	 */
	@Override
	public ImageQualityData saveCustomerImage(final BufferedImage image)
	{
		final CustomerModel model = (CustomerModel) userService.getUserForUID("uid");
		HelperUtil.createDirectory(model.getUid());
		return null;
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService()
	{
		return userService;
	}

	/**
	 * @param userService
	 *           the userService to set
	 */
	public void setUserService(final UserService userService)
	{
		this.userService = userService;
	}

	/**
	 * @return the mdiySaveCustomerDataService
	 */
	public MDIYSaveCustomerDataService getMdiySaveCustomerDataService()
	{
		return mdiySaveCustomerDataService;
	}

	/**
	 * @param mdiySaveCustomerDataService
	 *           the mdiySaveCustomerDataService to set
	 */
	public void setMdiySaveCustomerDataService(final MDIYSaveCustomerDataService mdiySaveCustomerDataService)
	{
		this.mdiySaveCustomerDataService = mdiySaveCustomerDataService;
	}
}
