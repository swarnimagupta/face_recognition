/**
 *
 */
package com.acc.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.frs.submitUser.MDIApplicationSubmitUserUtil;
import com.frs.submitUser.StatusData;


/**
 * @author swarnima.gupta
 *
 */
@Controller
@RequestMapping(value = "/v1/{baseSiteId}")
public class MDIYSubmitUserController
{

	private static final Logger LOG = Logger.getLogger(MDIYSubmitUserController.class);

	@Autowired
	private MDIApplicationSubmitUserUtil mDIApplicationSubmitUserUtil;


	@RequestMapping(value = "/submitUser", method = RequestMethod.POST)
	@ResponseBody
	public StatusData submitUser(final HttpServletRequest request)
	{
		return mDIApplicationSubmitUserUtil.submitUser(request);
	}
}
