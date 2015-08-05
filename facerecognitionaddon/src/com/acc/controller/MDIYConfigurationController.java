/**
 *
 */
package com.acc.controller;

import de.hybris.platform.util.Config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acc.data.ConfigData;


/**
 * @author swarnima.gupta
 *
 */
@Controller
@RequestMapping(value = "/v1/{baseSiteId}")
public class MDIYConfigurationController
{
	@RequestMapping(value = "/imageThreshold", method = RequestMethod.GET)
	@ResponseBody
	public ConfigData submitUser(final HttpServletRequest request)
	{
		final ConfigData configData = new ConfigData();
		configData.setValue(Config.getString("face.recognition.image.threshold", "20"));
		return configData;
	}
}
