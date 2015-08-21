package com.acc.storefront.controllers.pages;

import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.util.Config;
import de.hybris.platform.addonsupport.controllers.AbstractAddOnController;
import de.hybris.platform.addonsupport.controllers.page.AbstractAddOnPageController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;  
import org.springframework.web.bind.annotation.ModelAttribute;  

import java.io.File; 
import java.io.FileOutputStream; 
import java.io.IOException;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.acc.model.ImageQualityModel;
import com.acc.storefront.controllers.FcControllerConstants;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Map.Entry;

import com.acc.data.ImageQualityData;
import com.acc.form.UploadedFile;
import com.frs.common.MDIConfigurationUtils;
import com.frs.common.MDIProfileHelper;
import com.frs.common.MDIProfileUtils;
import com.frs.imagequality.MDIApplicationImageQualityInterface;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import com.frs.bean.MDIRequestResponseBean;
import com.frs.bean.MDIUser;

import java.awt.image.BufferedImage;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 */

/**
 * @author swarnima.gupta
 * 
 */
@Controller
@Scope("tenant")
@RequestMapping(value = "/facerecognitionpage")
public class MDIYFaceRecognitionController
{
	// CMS Pages
	private static final String FACE_RECOGNITION_PAGE = "facerecognitionpage";
	private static final String FACE_RECOGNITION_IMAGE_THRESHOLD = "face.recognition.image.threshold";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelService modelService;
	
	@Resource(name = "mdiYBase64ToImageConverter")
	private Converter<String, ImageQualityData> mdiYBase64ToImageConverter;
	
	@RequestMapping(method = RequestMethod.GET)
	public String profile(final Model model)
	{
		System.out.println("in MDIYFaceRecognitionController................");

		//model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs(null));
		System.out.println("in MDIYFaceRecognitionController");
		model.addAttribute("metaRobots", "no-index,no-follow");
		return FcControllerConstants.Views.Pages.Account.FaceRecognitionPage;
	}
	
	@RequestMapping(method = RequestMethod.POST)  
	public String fileUploaded(@RequestParam(value = "file", required = false) MultipartFile file, final Model model)
	{  
		System.out.println("-----------------------------------------------------------------------");
		model.addAttribute("metaRobots", "no-index,no-follow");
		InputStream inputStream = null;  
		OutputStream outputStream = null;  
	  
	  String fileName = file.getOriginalFilename(); 
	  try 
	  {  
		  inputStream = file.getInputStream();  
		  BufferedImage bufferedImage = ImageIO.read(inputStream);
		  MDIProfileUtils utils = new MDIProfileUtils();
		  String imageInBase64 = utils.getImageInBase64(bufferedImage);
		  MDIRequestResponseBean bean = new MDIRequestResponseBean();
		  MDIUser user = new MDIUser();
		  user.setImageInBase64(imageInBase64);
		  bean.setUser(user);
		  String inputJson = utils.getJsonForQuality(bean);
		  MDIProfileHelper helper = new MDIProfileHelper();
		  MDIRequestResponseBean output = helper.checkQuality(inputJson);
		  Map<String, String> scoreMap = output.getQualityScoreMap();
		  BigDecimal qualityScore = new BigDecimal(scoreMap.get("Default.Cognitec850"));
		  BigDecimal thresholdValue = new BigDecimal(Config.getString(FACE_RECOGNITION_IMAGE_THRESHOLD, "20"));
		  model.addAttribute("message", (qualityScore.compareTo(thresholdValue)>=0)?"Image Uploaded Successfully":"Please upload image again!!");
		  if(qualityScore.compareTo(thresholdValue)>=0)
		  {
			  CustomerModel customer = (CustomerModel) userService.getCurrentUser();
			  ImageQualityModel imageQuality = modelService.create(ImageQualityModel.class);
			  imageQuality.setQualityScore(qualityScore.toString());
			  imageQuality.setCustomer(customer);
			  final ImageQualityData data = mdiYBase64ToImageConverter.convert(imageInBase64);
			  imageQuality.setImagePath(data.getImagePath());
			  //persist in biometrics get identity id
			  String inputPersistUserJson = utils.getJsonForNewUser(bean);
			  MDIRequestResponseBean requestResponseBean = helper.newUser(inputPersistUserJson);
			  imageQuality.setIdentityId(requestResponseBean.getUser().biometricId);
			  modelService.saveAll(imageQuality);
		  }
	  } 
	  catch (IOException e) 
	  {  
   	   // TODO Auto-generated catch block  
   	   e.printStackTrace();  
	  }  
	  
	  return FcControllerConstants.Views.Pages.Account.FaceRecognitionPage;  
	 }  
}