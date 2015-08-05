/**
 *
 */
package com.acc.facade.impl;

import de.hybris.platform.servicelayer.dto.converter.Converter;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

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
		final Collection<ImageQualityModel> imagesList = mDIYCustomerInformationService.getCustomerImages(customerId);
		final List<String> images = imageData.getImagesList();
		for (final ImageQualityModel image : imagesList)
		{
			images.add(mdiYImagesToStringConverter.convert(image));
		}
		imageData.setImagesList(images);
	}

	/**
	 * @param mDIYCustomerInformationService
	 *           the mDIYCustomerInformationService to set
	 */
	public void setmDIYCustomerInformationService(final MDIYCustomerInformationService mDIYCustomerInformationService)
	{
		this.mDIYCustomerInformationService = mDIYCustomerInformationService;
	}
}
