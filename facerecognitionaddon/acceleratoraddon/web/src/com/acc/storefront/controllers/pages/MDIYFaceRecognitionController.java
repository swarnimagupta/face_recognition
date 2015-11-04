package com.acc.storefront.controllers.pages;

import static org.junit.Assert.assertEquals;

import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.jalo.CatalogManager;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.media.MediaService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.util.Config;
import de.hybris.platform.acceleratorservices.setup.SetupSyncJobService;
import de.hybris.platform.addonsupport.controllers.AbstractAddOnController;
import de.hybris.platform.addonsupport.controllers.page.AbstractAddOnPageController;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.glassfish.jersey.client.HttpUrlConnectorProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;  
import org.springframework.web.bind.annotation.ModelAttribute;  

import java.io.DataInputStream;
import java.io.File; 
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream; 
import java.io.IOException;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.acc.model.ImageQualityModel;
import com.acc.storefront.controllers.FcControllerConstants;
import com.acc.util.HelperUtil;
import com.acc.util.WebservicesUtil;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedInputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map.Entry;

import com.acc.data.ImageQualityData;
import com.acc.form.UploadedFile;
import com.frs.common.MDIConfigurationUtils;
import com.frs.common.MDIProfileHelper;
import com.frs.common.MDIProfileUtils;
import com.frs.imagequality.MDIApplicationImageQualityInterface;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import com.frs.bean.MDIRequestResponseBean;
import com.frs.bean.MDIUser;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.Authenticator;
import java.net.InetAddress;
import java.net.PasswordAuthentication;

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
	
	@Autowired
	private MediaService mediaService;

	@Autowired
	private CatalogVersionService catalogVersionService;

	@Autowired
	private SetupSyncJobService setupSyncJobService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String profile(final Model model) throws CMSItemNotFoundException
	{
		System.out.println("in MDIYFaceRecognitionController................");

		//model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs(null));
		System.out.println("in MDIYFaceRecognitionController");
		model.addAttribute("metaRobots", "no-index,no-follow");
		return FcControllerConstants.Views.Pages.Account.FaceRecognitionPage;
	}
	
	@RequestMapping(value="/submitImageURL", method = RequestMethod.GET)  
	public String fileUpload(@RequestParam(value = "url") String imageUrl, final Model model, HttpServletRequest request, HttpServletResponse response) throws MalformedURLException, IOException
	{
		System.out.println("in MDIYFaceRecognitionController................fileUploaded");
		System.out.println("in url................fileUploaded-->"+imageUrl);
		String message = StringUtils.EMPTY;
		if(StringUtils.isNotEmpty(imageUrl))
		{
			imageUrl = imageUrl.replaceAll("SLASH", "/");
			imageUrl = imageUrl.replaceAll("DOT", ".");
			imageUrl = imageUrl.replaceAll("AMPERSAND", "&");
			URL url = new URL(imageUrl);
			InputStream is = url.openStream();
			BufferedInputStream bis = new BufferedInputStream(url.openConnection().getInputStream());
			byte imageData[] = new byte[2048];
			bis.read(imageData);
			// Converting Image byte array into Base64 String
			String imageInBase64 = encodeImage(imageData);
			System.out.println("imageInBase64 : " + imageInBase64);
			imageInBase64 = imageInBase64.replace("data:image/jpeg;base64,", "");
			MDIProfileUtils utils = new MDIProfileUtils();
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
			message = (qualityScore.compareTo(thresholdValue)>=0)?"Image Uploaded Successfully":"Please upload image again!!";
			if(qualityScore.compareTo(thresholdValue)>=0)
			{
				CustomerModel customer = (CustomerModel) userService.getCurrentUser();
				ImageQualityModel imageQuality = modelService.create(ImageQualityModel.class);
				imageQuality.setQualityScore(qualityScore.toString());
				imageQuality.setCustomer(customer);
				final ImageQualityData data = mdiYBase64ToImageConverter.convert(imageInBase64);
         	   DataInputStream dis = null;
        			final MediaModel media = modelService.create(MediaModel.class);
        			try
        			{
        				media.setCode(String.valueOf(Math.random()));
        				final CatalogVersionModel stagedCatalogVersion = catalogVersionService.getCatalogVersion("electronicsContentCatalog",
        						CatalogManager.OFFLINE_VERSION);
        				media.setCatalogVersion(stagedCatalogVersion);
        				modelService.save(media);
        				final FileInputStream fis = new FileInputStream(data.getImagePath());
        				dis = new DataInputStream(fis);
        				mediaService.setDataStreamForMedia(media, dis);
        				// start the synchronization job
        				final PerformResult res = setupSyncJobService.executeCatalogSyncJob("electronicsContentCatalog");
        				final CronJobStatus cronJobStatus = res.getStatus();
        				assertEquals("", CronJobStatus.FINISHED, cronJobStatus);
        				final CronJobResult cronJobResult = res.getResult();
        				assertEquals("", CronJobResult.SUCCESS, cronJobResult);
        			}
        			catch (final FileNotFoundException e)
        			{
        				// YTODO Auto-generated catch block
        				e.printStackTrace();
        			}
        			finally
        			{
        				if (dis != null)
        				{
        					try
        					{
        						dis.close();
        					}
        					catch (final IOException e)
        					{
        						// ignore
        					}
        				}
        			}
        			imageQuality.setImagePath(media.getURL2());
         	  
         	  
         	  //persist in biometrics get identity id
         	  String inputPersistUserJson = utils.getJsonForNewUser(bean);
         	  MDIRequestResponseBean requestResponseBean = helper.newUser(inputPersistUserJson);
         	  imageQuality.setIdentityId(requestResponseBean.getUser().biometricId);
         	  modelService.saveAll(imageQuality);
           }
		}
		return FcControllerConstants.Views.Pages.Account.FaceRecognitionPage;
	}
	
	public static String encodeImage(byte[] imageByteArray) {
	    return Base64.encodeBase64String(imageByteArray);
	}
	
//	@RequestMapping(method = RequestMethod.POST)  
//	public String fileUploaded(@RequestParam(value = "file", required = false) MultipartFile file, final Model model)
//	{  
//		System.out.println("-----------------------------------------------------------------------");
//		model.addAttribute("metaRobots", "no-index,no-follow");
//		InputStream inputStream = null;  
//		OutputStream outputStream = null;  
//	  
//	  String fileName = file.getOriginalFilename(); 
//	  try 
//	  {  
//		  inputStream = file.getInputStream();  
//		  BufferedImage bufferedImage = ImageIO.read(inputStream);
//		  MDIProfileUtils utils = new MDIProfileUtils();
//		  String imageInBase64 = utils.getImageInBase64(bufferedImage);
//		  MDIRequestResponseBean bean = new MDIRequestResponseBean();
//		  MDIUser user = new MDIUser();
//		  user.setImageInBase64(imageInBase64);
//		  bean.setUser(user);
//		  String inputJson = utils.getJsonForQuality(bean);
//		  MDIProfileHelper helper = new MDIProfileHelper();
//		  MDIRequestResponseBean output = helper.checkQuality(inputJson);
//		  Map<String, String> scoreMap = output.getQualityScoreMap();
//		  BigDecimal qualityScore = new BigDecimal(scoreMap.get("Default.Cognitec850"));
//		  BigDecimal thresholdValue = new BigDecimal(Config.getString(FACE_RECOGNITION_IMAGE_THRESHOLD, "20"));
//		  model.addAttribute("message", (qualityScore.compareTo(thresholdValue)>=0)?"Image Uploaded Successfully":"Please upload image again!!");
//		  if(qualityScore.compareTo(thresholdValue)>=0)
//		  {
//			  CustomerModel customer = (CustomerModel) userService.getCurrentUser();
//			  ImageQualityModel imageQuality = modelService.create(ImageQualityModel.class);
//			  imageQuality.setQualityScore(qualityScore.toString());
//			  imageQuality.setCustomer(customer);
//			  final ImageQualityData data = mdiYBase64ToImageConverter.convert(imageInBase64);
//			  imageQuality.setImagePath(data.getImagePath());
//			  //persist in biometrics get identity id
//			  String inputPersistUserJson = utils.getJsonForNewUser(bean);
//			  MDIRequestResponseBean requestResponseBean = helper.newUser(inputPersistUserJson);
//			  imageQuality.setIdentityId(requestResponseBean.getUser().biometricId);
//			  modelService.saveAll(imageQuality);
//		  }
//	  } 
//	  catch (IOException e) 
//	  {  
//   	   // TODO Auto-generated catch block  
//   	   e.printStackTrace();  
//	  }  
//	  
//	  return FcControllerConstants.Views.Pages.Account.FaceRecognitionPage;  
//	 }
	
	public static class CustomAuthenticator extends Authenticator 
	{
        // Called when password authorization is needed
        protected PasswordAuthentication getPasswordAuthentication() 
        {
            // Get information about the request
            String prompt = getRequestingPrompt();
            String hostname = getRequestingHost();
            InetAddress ipaddr = getRequestingSite();
            int port = getRequestingPort();
            String username = "mdiappemail@gmail.com";
            String password = "mdiappemail";
            // Return the information (a data holder that is used by Authenticator)
            return new PasswordAuthentication(username, password.toCharArray());
        }
    }
	
}