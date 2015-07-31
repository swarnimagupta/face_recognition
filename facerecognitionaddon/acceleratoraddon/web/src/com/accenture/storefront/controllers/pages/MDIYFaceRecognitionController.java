package com.accenture.storefront.controllers.pages;

import com.accenture.FcControllerConstants;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.addonsupport.controllers.page.AbstractAddOnPageController;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



/**
 * 
 */

/**
 * @author swapnil.a.pandey
 * 
 */
@Controller
@Scope("tenant")
@RequestMapping(value ="/facerecognitionpage")
public class MDIYFaceRecognitionController extends AbstractAddOnPageController
{
	// CMS Pages
	private static final String FACE_RECOGNITION_PAGE = "facerecognitionpage";

	@RequestMapping(method = RequestMethod.GET)
	public String profile(final Model model) throws CMSItemNotFoundException
	{
		System.out.println("in MDIYFaceRecognitionController................");

		storeCmsPageInModel(model, getContentPageForLabelOrId(FACE_RECOGNITION_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FACE_RECOGNITION_PAGE));
		//model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs(null));
		System.out.println("in MDIYFaceRecognitionController");
		model.addAttribute("metaRobots", "no-index,no-follow");
		return FcControllerConstants.Views.Pages.Account.FaceRecognitionPage;
	}
}
