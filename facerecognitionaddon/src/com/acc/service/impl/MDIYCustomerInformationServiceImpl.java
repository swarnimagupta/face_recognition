/**
 *
 */
package com.acc.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.acc.dao.MDIYCustomerInformationDao;
import com.acc.model.ImageQualityModel;
import com.acc.service.MDIYCustomerInformationService;


/**
 * @author swarnima.gupta
 *
 */
public class MDIYCustomerInformationServiceImpl implements MDIYCustomerInformationService
{
	@Autowired
	private MDIYCustomerInformationDao mDIYCustomerInformationDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.acc.service.MDIYCustomerInformationService#getCustomerImages(java.lang.String)
	 */
	@Override
	public Collection<ImageQualityModel> getCustomerImages(final String customerId)
	{
		return mDIYCustomerInformationDao.getCustomerImages(customerId);
	}

}
