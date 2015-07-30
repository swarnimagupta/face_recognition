/**
 * 
 */
package com.accenture.populator;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import java.util.ArrayList;
import java.util.List;

import com.acc.data.ImageQualityData;
import com.accenture.model.ImageQualityModel;


/**
 * @author jayasree.gogineni
 * 
 */
public class ImageQualityPopulator implements Populator<ImageQualityModel, ImageQualityData>
{


	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hybris.platform.converters.Populator#populate(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void populate(final ImageQualityModel source, final ImageQualityData target) throws ConversionException
	{
		if (source != null)
		{
			final List<String> identityIds = new ArrayList<String>();
			identityIds.add(String.valueOf(source.getIdentityId()));
			target.setQualityScore(String.valueOf(source.getQualityscore()));
			target.setIdentificationIds(identityIds);
			target.setImagePath(source.getImagePath());
		}

	}

}
