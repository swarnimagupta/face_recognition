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

import com.acc.data.StatusData;
import com.acc.facade.MDIYSaveCustomerDataFacade;
import com.acc.util.WebservicesUtil;


/**
 * @author swarnima.gupta
 *
 */
@Controller
@RequestMapping(value = "/v1/{baseSiteId}")
public class MDIYImageUploadByMobileController
{

	/**
	 *
	 */
	private static final String QUALITY_SCORE = "qualityScore";
	private static final Logger LOG = Logger.getLogger(MDIYImageUploadByMobileController.class);
	private static final String CUSTOMERID = "customerId";
	private static final String IMAGEINBASE64 = "imageInBase64";

	@Autowired
	private MDIYSaveCustomerDataFacade mDIYSaveCustomerDataFacade;

	@RequestMapping(value = "/uploadCustomerImage", method = RequestMethod.POST)
	@ResponseBody
	public StatusData uploadCustomerImage(final HttpServletRequest request) throws UnsupportedEncodingException, IOException,
			ParseException
	{
		LOG.info("::::::: in uploadCustomerImage POST request method :::::::");
		final WebservicesUtil webservicesUtil = new WebservicesUtil();
		final StringBuffer sbuf = webservicesUtil.getJsonBodyString(request);
		LOG.info("::::::: json object string is :::::::" + sbuf);

		if (StringUtils.isNotEmpty(sbuf.toString()))
		{
			final JSONParser parser = new JSONParser();
			final JSONObject obj = (JSONObject) parser.parse(sbuf.toString());
			final String customerId = String.valueOf(obj.get(CUSTOMERID));
			final String imageInBase64 = String.valueOf(obj.get(IMAGEINBASE64));
			final String qualityScore = String.valueOf(obj.get(QUALITY_SCORE));
			return mDIYSaveCustomerDataFacade.saveCustomerImage(imageInBase64, customerId, qualityScore);
		}
		return new StatusData();
	}

}
