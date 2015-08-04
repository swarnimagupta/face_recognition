package com.acc.service.impl;

import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.model.ModelService;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.acc.model.ImageQualityModel;
import com.acc.service.MDIYSaveCustomerDataService;


public class MDIYSaveCustomerDataServiceImpl implements MDIYSaveCustomerDataService
{
	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(MDIYSaveCustomerDataServiceImpl.class);
	@Autowired
	private ModelService modelService;

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
