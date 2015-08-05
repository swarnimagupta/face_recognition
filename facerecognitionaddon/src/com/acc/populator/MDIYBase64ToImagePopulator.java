/**
 *
 */
package com.acc.populator;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.util.Assert;

import com.acc.data.ImageQualityData;
import com.acc.util.HelperUtil;


/**
 * @author swarnima.gupta
 *
 */
public class MDIYBase64ToImagePopulator implements Populator<String, ImageQualityData>
{

	public static int counter = 1;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hybris.platform.converters.Populator#populate(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void populate(final String source, final ImageQualityData target) throws ConversionException
	{
		Assert.notNull(source, "Parameter source cannot be null.");
		Assert.notNull(target, "Parameter target cannot be null.");
		try
		{
			final byte[] imageByteArray = HelperUtil.decodeImage(source);
			final String directoryPath = HelperUtil.createDirectory(String.valueOf(counter));
			target.setImagePath(directoryPath + counter + ".png");
			final FileOutputStream imageOutFile = new FileOutputStream(directoryPath + counter + ".png");
			imageOutFile.write(imageByteArray);
			imageOutFile.close();
			System.out.println("Image Successfully Manipulated!");
			counter++;
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
