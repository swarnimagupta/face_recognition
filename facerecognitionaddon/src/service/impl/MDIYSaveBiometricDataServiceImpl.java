/**
 * 
 */
package com.acc.core.service.impl;

import de.hybris.platform.servicelayer.model.ModelService;

import com.acc.core.service.MDIYSaveBiometricDataService;
import com.acc.model.ImageQualityModel;


/**
 * @author jayasree.gogineni
 * 
 */
public class MDIYSaveBiometricDataServiceImpl implements MDIYSaveBiometricDataService
{
	private ModelService modelService;

	/**
	 * @return the modelService
	 */
	public ModelService getModelService()
	{
		return modelService;
	}

	/**
	 * @param modelService
	 *           the modelService to set
	 */
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.acc.core.service.MDIYSaveBiometricDataService#saveBiometricData(java.lang.String)
	 */
	@Override
	public void saveBiometricData(final Integer identityID)
	{
		// YTODO Auto-generated method stub
		final ImageQualityModel imageQualityModel = getModelService().create(ImageQualityModel.class);

		imageQualityModel.setIdentityId(identityID);

		modelService.save(imageQualityModel);

	}
}
