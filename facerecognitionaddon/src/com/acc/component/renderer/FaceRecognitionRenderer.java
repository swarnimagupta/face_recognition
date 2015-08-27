/**
 *
 */
package com.acc.component.renderer;

import de.hybris.platform.addonsupport.renderer.impl.DefaultAddOnCMSComponentRenderer;
import de.hybris.platform.cms2lib.model.components.FaceRecognitionComponentModel;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;


/**
 * @author swarnima.gupta
 *
 */
public class FaceRecognitionRenderer<C extends FaceRecognitionComponentModel> extends DefaultAddOnCMSComponentRenderer<C>
{
	private static final Logger LOG = Logger.getLogger(FaceRecognitionRenderer.class);

	@Override
	protected Map<String, Object> getVariablesToExpose(final PageContext pageContext, final C component)
	{
		LOG.info("::::::::::::::: Inside FaceRecognitionRenderer getVariablesToExpose :::::::::::::::::");
		final Map<String, Object> variables = new HashMap<String, Object>();
		return variables;
	}
}
