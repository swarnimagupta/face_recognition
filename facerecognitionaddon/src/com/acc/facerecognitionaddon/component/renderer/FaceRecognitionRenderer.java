/**
 * 
 */
package com.acc.facerecognitionaddon.component.renderer;

import de.hybris.platform.addonsupport.renderer.impl.DefaultAddOnCMSComponentRenderer;
import de.hybris.platform.cms2lib.model.components.FaceRecognitionComponentModel;

import java.util.Map;

import javax.servlet.jsp.PageContext;


/**
 * @author swapnil.a.pandey
 * 
 */
public class FaceRecognitionRenderer<C extends FaceRecognitionComponentModel> extends DefaultAddOnCMSComponentRenderer<C>
{
	@Override
	protected Map<String, Object> getVariablesToExpose(final PageContext pageContext, final C component)
	{

		//your component code
		return null;
	}
}
