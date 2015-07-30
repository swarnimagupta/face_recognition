/**
 * 
 */
package com.accenture.storefront.controllers.pages;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acc.data.ImageQualityData;
import com.accenture.core.facade.MDIYSaveCustomerDataFacade;



/**
 * @author jayasree.gogineni
 * 
 */

@Controller
@RequestMapping(value = "/v1/{baseSiteId}/CustomerDetails" + "")
public class MDIYImageQualityCheckController
{
	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(MDIYImageQualityCheckController.class);
	@Autowired
	private MDIYSaveCustomerDataFacade mdiySaveCustomerDataFacade;

	@RequestMapping(value = "SaveCustomerDetails", method = RequestMethod.GET)
	@ResponseBody
	public ImageQualityData SaveCustomerDetails()
	{
		ImageQualityData imagedata = new ImageQualityData();
		BufferedImage image = null;
		try
		{
			image = ImageIO.read(new File("strawberry.jpg"));
			imagedata = getMdiySaveCustomerDataFacade().saveCustomerImage(image);
			return imagedata;
		}
		catch (final IOException e)
		{
			e.printStackTrace();

		}
		return imagedata;
	}

	/**
	 * @return the mdiySaveCustomerDataFacade
	 */
	public MDIYSaveCustomerDataFacade getMdiySaveCustomerDataFacade()
	{
		return mdiySaveCustomerDataFacade;
	}


}