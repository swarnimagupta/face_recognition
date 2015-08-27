/**
 *
 */
package com.acc.facade.impl;

import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.acc.dao.StoreLoginDao;
import com.acc.data.ImageData;
import com.acc.facade.MDIYCustomerInformationFacade;
import com.acc.model.ImageQualityModel;
import com.acc.service.MDIYCustomerInformationService;


/**
 * @author swarnima.gupta
 *
 */
public class MDIYCustomerInformationFacadeImpl implements MDIYCustomerInformationFacade
{

	private MDIYCustomerInformationService mDIYCustomerInformationService;
	@Autowired
	private StoreLoginDao storeLoginDao;

	@Resource(name = "mdiYImagesToStringConverter")
	private Converter<ImageQualityModel, String> mdiYImagesToStringConverter;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.acc.facade.MDIYCustomerInformation#getCustomerImages(java.lang.String, com.acc.data.ImageData)
	 */
	@Override
	public void getCustomerImages(final String customerId, final ImageData imageData)
	{
		final CustomerModel model = storeLoginDao.isCustomerFound(customerId);
		final Collection<ImageQualityModel> qualityModels = model.getImageQuality();
		final List<String> imagesList = new ArrayList<String>();
		if (CollectionUtils.isNotEmpty(qualityModels))
		{
			String imagePath = null;
			final Iterator<ImageQualityModel> iterator = qualityModels.iterator();
			while (iterator.hasNext())
			{
				imagePath = iterator.next().getImagePath();
				continue;
			}
			imagesList.add(imagePath);
		}
		imageData.setImagesList(imagesList);
	}

	/**
	 * @param mDIYCustomerInformationService
	 *           the mDIYCustomerInformationService to set
	 */
	public void setmDIYCustomerInformationService(final MDIYCustomerInformationService mDIYCustomerInformationService)
	{
		this.mDIYCustomerInformationService = mDIYCustomerInformationService;
	}

	/**
	 * @param storeLoginDao
	 *           the storeLoginDao to set
	 */
	public void setStoreLoginDao(final StoreLoginDao storeLoginDao)
	{
		this.storeLoginDao = storeLoginDao;
	}


}
