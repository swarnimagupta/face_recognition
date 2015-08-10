/**
 *
 */
package com.acc.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acc.data.ImageData;
import com.acc.facade.MDIYCustomerInformationFacade;
import com.acc.util.WebservicesUtil;


/**
 * @author swarnima.gupta
 *
 */
@Controller
@RequestMapping(value = "/v1/{baseSiteId}/customerImages")
public class MDIYCustomerImagesController
{
	private static final Logger LOG = Logger.getLogger(MDIYCustomerImagesController.class);
	private static final String CUSTOMERID = "customerId";

	@Autowired
	private MDIYCustomerInformationFacade mDIYCustomerInformationFacade;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ImageData getCustomerImages(final HttpServletRequest request) throws UnsupportedEncodingException, IOException,
			ParseException
	{
		LOG.info("::::::: in getCustomerImages POST request method :::::::");
		final WebservicesUtil webservicesUtil = new WebservicesUtil();
		final StringBuffer sbuf = webservicesUtil.getJsonBodyString(request);
		LOG.info("::::::: json object string is :::::::" + sbuf);

		final ImageData imageData = new ImageData();
		if (StringUtils.isNotEmpty(sbuf.toString()))
		{
			final JSONParser parser = new JSONParser();
			final JSONObject obj = (JSONObject) parser.parse(sbuf.toString());
			final String customerId = String.valueOf(obj.get(CUSTOMERID));
			mDIYCustomerInformationFacade.getCustomerImages(customerId, imageData);
		}
		return imageData;
	}
}