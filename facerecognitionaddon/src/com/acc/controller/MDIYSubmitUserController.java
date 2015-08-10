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
import com.acc.domain.dummy.MDIUser;
import com.acc.facade.MDIYSubmitUserFacade;
import com.acc.util.WebservicesUtil;


/**
 * @author swarnima.gupta
 *
 */
@Controller
@RequestMapping(value = "/v1/{baseSiteId}")
public class MDIYSubmitUserController
{
	@Autowired
	private MDIYSubmitUserFacade mdiYSubmitUserFacade;

	private static final Logger LOG = Logger.getLogger(MDIYSubmitUserController.class);

	//@Autowired
	//private  ApplicationSubmitUserInterface  submitUserProfileService;


	@RequestMapping(value = "/submitUser", method = RequestMethod.POST)
	@ResponseBody
	public StatusData submitUser(final HttpServletRequest request) throws UnsupportedEncodingException, IOException,
			ParseException
	{
		//final MDIUser user = submitUserProfileService.submitUser(request);
		LOG.info("::::::: in getCustomerImages POST request method :::::::");
		final WebservicesUtil webservicesUtil = new WebservicesUtil();
		final StringBuffer sbuf = webservicesUtil.getJsonBodyString(request);
		LOG.info("::::::: json object string is :::::::" + sbuf);
		final MDIUser user = new MDIUser();
		if (StringUtils.isNotEmpty(sbuf.toString()))
		{
			final JSONParser parser = new JSONParser();
			final JSONObject obj = (JSONObject) parser.parse(sbuf.toString());
			user.setUserName(String.valueOf(obj.get("userName")));
			user.setBiometricId(String.valueOf(obj.get("biometricId")));
			user.setImageInBase64(String.valueOf(obj.get("imageInBase64")));
			user.setImagePath(String.valueOf(obj.get("imagePath")));
		}

		return mdiYSubmitUserFacade.addUserToQueue(user);
	}
}
