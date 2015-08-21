/**
 *
 */
package com.acc.service.impl;

import java.util.List;

import com.acc.dao.MDIYCustomerInformationDao;
import com.acc.model.ImageQualityModel;
import com.acc.service.MDIYCustomerInformationService;


/**
 * @author swarnima.gupta
 *
 */
public class MDIYCustomerInformationServiceImpl implements MDIYCustomerInformationService
{
	private MDIYCustomerInformationDao mDIYCustomerInformationDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.acc.service.MDIYCustomerInformationService#getCustomerImages(java.lang.String)
	 */
	@Override
	public ImageQualityModel getCustomerImages(final String customerId)
	{
		return mDIYCustomerInformationDao.getCustomerImages(customerId);
	}

	/**
	 * @param mDIYCustomerInformationDao
	 *           the mDIYCustomerInformationDao to set
	 */
	public void setmDIYCustomerInformationDao(final MDIYCustomerInformationDao mDIYCustomerInformationDao)
	{
		this.mDIYCustomerInformationDao = mDIYCustomerInformationDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.acc.service.MDIYCustomerInformationService#getImageQualities()
	 */
	@Override
	public List<ImageQualityModel> getImageQualities()
	{
		// YTODO Auto-generated method stub
		return mDIYCustomerInformationDao.getImageQualities();
	}
}
