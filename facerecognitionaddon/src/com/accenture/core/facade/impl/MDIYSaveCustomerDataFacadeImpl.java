/**
 * 
 */
package com.accenture.core.facade.impl;

import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.user.UserService;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import com.acc.data.ImageQualityData;
import com.accenture.core.facade.MDIYSaveCustomerDataFacade;
import com.accenture.core.service.MDIYSaveCustomerDataService;
import com.accenture.model.ImageQualityModel;


/**
 * @author jayasree.gogineni
 * 
 */
public class MDIYSaveCustomerDataFacadeImpl implements MDIYSaveCustomerDataFacade
{
	@Autowired
	private UserService userService;

	@Resource(name = "imageQualityConverter")
	private Converter<ImageQualityModel, ImageQualityData> imageQualityConverter;

	@Resource(name = "CustomerDataConverter")
	private Converter<CustomerModel, CustomerData> CustomerDataConverter;

	@Autowired
	private MDIYSaveCustomerDataService mdiySaveCustomerDataService;


	@Override
	public void saveCustomerData(final CustomerModel model)
	{
		// YTODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.accenture.core.facade.MDIYSaveCustomerDataFacade#saveCustomerImage(java.awt.image.BufferedImage)
	 */
	@Override
	public ImageQualityData saveCustomerImage(final BufferedImage image)
	{
		final CustomerModel model = (CustomerModel) userService.getUserForUID("uid");
		final String location = "C:\\hybris-5.1.0\\hybris\\data\\";
		final String dateTime = new DateTime().toString("dd-MM-yy HH:mm:ss");
		final File g = new File(location + "\\image\\");
		if (!g.exists())
		{
			if (g.mkdir())
			{
				System.out.println("image Directory is created!" + dateTime.toString());
			}
			else
			{
				System.out.println("Failed to create directory!" + dateTime.toString());

			}
		}

		else
		{
			final String output = dateTime.toString().replace(":", "-"); // Replace colons for compatibility with the Mac HFS+ file system.


			final File f = new File(g + "\\" + output);

			System.out.println("datetime Directory is created!" + output + g.toString());
			f.mkdir();
			System.out.println("datetime Directory is created after f!" + f.toString());
			if (f.exists())
			{
				final File e = new File(f + "\\" + model.getUid());
				e.mkdir();
				System.out.println("customerID folder is created after f!" + f.toString());

			}
		}

		// YTODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the imageQualityConverter
	 */
	public Converter<ImageQualityModel, ImageQualityData> getImageQualityConverter()
	{
		return imageQualityConverter;
	}

	/**
	 * @param imageQualityConverter
	 *           the imageQualityConverter to set
	 */
	public void setImageQualityConverter(final Converter<ImageQualityModel, ImageQualityData> imageQualityConverter)
	{
		this.imageQualityConverter = imageQualityConverter;
	}


	/**
	 * @return the saveCustomerDataConverter
	 */
	public Converter<CustomerModel, CustomerData> getSaveCustomerDataConverter()
	{
		return CustomerDataConverter;
	}

	/**
	 * @param saveCustomerDataConverter
	 *           the saveCustomerDataConverter to set
	 */
	@SuppressWarnings("javadoc")
	public void setCustomerDataConverter(final Converter<CustomerModel, CustomerData> CustomerDataConverter)
	{
		this.CustomerDataConverter = CustomerDataConverter;
	}

}
