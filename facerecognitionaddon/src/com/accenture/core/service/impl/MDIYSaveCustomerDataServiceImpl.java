/**
 * 
 */
package com.accenture.core.service.impl;

import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.model.ModelService;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.accenture.core.service.MDIYSaveCustomerDataService;
import com.accenture.model.ImageQualityModel;


/**
 * @author jayasree.gogineni
 * 
 */


public class MDIYSaveCustomerDataServiceImpl implements MDIYSaveCustomerDataService
{
	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(MDIYSaveCustomerDataServiceImpl.class);
	@Autowired
	private ModelService modelService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.accenture.core.Service.MDIYSaveCustomerDataService#saveCustomerData(de.hybris.platform.core.model.user.
	 * CustomerModel)
	 */
	@Override
	public void saveCustomerData(final CustomerModel model)
	{

		final CustomerModel customerModel = new CustomerModel();
		final Collection<ImageQualityModel> ImageQuality = null;
		customerModel.setImageQuality(ImageQuality);
		modelService.save(customerModel);
		// YTODO Auto-generated method stub
		return;
	}
}
