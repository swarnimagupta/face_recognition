/**
 *
 */
package com.acc.service.impl;

import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.model.ModelService;

import com.acc.dao.MDIYSubmitUserDao;
import com.acc.model.CSRCustomerDetailsModel;
import com.acc.service.MDIYSubmitUserService;


/**
 * @author swarnima.gupta
 *
 */
public class MDIYSubmitUserServiceImpl implements MDIYSubmitUserService
{

	private MDIYSubmitUserDao mdiYSubmitUserDao;

	private ModelService modelService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.acc.service.MDIYSubmitUserService#doesCustomerExist(java.lang.String)
	 */
	@Override
	public CustomerModel doesCustomerExist(final String identityId)
	{
		// YTODO Auto-generated method stub
		return mdiYSubmitUserDao.doesCustomerExist(identityId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.acc.service.MDIYSubmitUserService#addUserToQueue(MDIYCSRCustomerModel)
	 */
	@Override
	public void addUserToQueue(final CSRCustomerDetailsModel model)
	{
		modelService.save(model);
	}

	/**
	 * @param mdiYSubmitUserDao
	 *           the mdiYSubmitUserDao to set
	 */
	public void setMdiYSubmitUserDao(final MDIYSubmitUserDao mdiYSubmitUserDao)
	{
		this.mdiYSubmitUserDao = mdiYSubmitUserDao;
	}

	/**
	 * @param modelService
	 *           the modelService to set
	 */
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

}
