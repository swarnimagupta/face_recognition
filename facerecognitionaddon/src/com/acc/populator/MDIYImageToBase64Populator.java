/**
 *
 */
package com.acc.populator;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.util.Assert;

import com.acc.model.ImageQualityModel;
import com.acc.util.HelperUtil;


/**
 * @author swarnima.gupta
 *
 */
public class MDIYImageToBase64Populator implements Populator<ImageQualityModel, String>
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hybris.platform.converters.Populator#populate(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void populate(final ImageQualityModel source, String target) throws ConversionException
	{
		Assert.notNull(source, "Parameter source cannot be null.");
		Assert.notNull(target, "Parameter target cannot be null.");
		if (null != source)
		{
			final File file = new File(source.getImagePath());
			try
			{
				final FileInputStream imageInFile = new FileInputStream(file);
				final byte imageData[] = new byte[(int) file.length()];
				imageInFile.read(imageData);
				target = HelperUtil.encodeImage(imageData);
				imageInFile.close();
			}
			catch (final FileNotFoundException e)
			{
				System.out.println("Image not found" + e);
			}
			catch (final IOException ioe)
			{
				System.out.println("Exception while reading the Image " + ioe);
			}
		}
	}

}
