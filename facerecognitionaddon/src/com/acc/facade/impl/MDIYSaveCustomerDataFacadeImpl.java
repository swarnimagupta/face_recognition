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
import org.springframework.beans.factory.annotation.Autowired;

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

	@Autowired
	private UserService userService;

	@Resource(name = "imageQualityConverter")
	private Converter<ImageQualityModel, ImageQualityData> imageQualityConverter;

	@Resource(name = "CustomerDataConverter")
	private Converter<CustomerModel, CustomerData> CustomerDataConverter;

	@Autowired
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
	 * @return the imageQualityConverter
	 */
	public Converter<ImageQualityModel, ImageQualityData> getImageQualityConverter()
	{
		return imageQualityConverter;
	}

	/**
	 * @param imageQualityConverter
	 *           the imageQualityConverter to set
	 */
	public void setImageQualityConverter(final Converter<ImageQualityModel, ImageQualityData> imageQualityConverter)
	{
		this.imageQualityConverter = imageQualityConverter;
	}


	/**
	 * @return the saveCustomerDataConverter
	 */
	public Converter<CustomerModel, CustomerData> getSaveCustomerDataConverter()
	{
		return CustomerDataConverter;
	}

	/**
	 * @param saveCustomerDataConverter
	 *           the saveCustomerDataConverter to set
	 */
	@SuppressWarnings("javadoc")
	public void setCustomerDataConverter(final Converter<CustomerModel, CustomerData> CustomerDataConverter)
	{
		this.CustomerDataConverter = CustomerDataConverter;
	}
}
