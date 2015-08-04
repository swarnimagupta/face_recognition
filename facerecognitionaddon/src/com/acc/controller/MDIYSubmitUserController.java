/**
 *
 */
package com.acc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acc.data.StatusData;
import com.acc.domain.dummy.MDIUser;
import com.acc.facade.MDIYSubmitUserFacade;


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

	//@Autowired
	//private  ApplicationSubmitUserInterface  submitUserProfileService;


	@RequestMapping(value = "/submitUser", method = RequestMethod.POST)
	@ResponseBody
	public StatusData submitUser(final HttpServletRequest request)
	{
		//final MDIUser user = submitUserProfileService.submitUser(request);
		final MDIUser user;
		return mdiYSubmitUserFacade.addUserToQueue(user);

	}
}
