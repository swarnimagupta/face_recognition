import static org.junit.Assert.assertEquals;

import de.hybris.platform.acceleratorservices.setup.SetupSyncJobService;
import de.hybris.platform.addonsupport.controllers.page.AbstractAddOnPageController;
import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.jalo.CatalogManager;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.media.MediaService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.util.Config;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.acc.data.ImageQualityData;
import com.acc.model.ImageQualityModel;
import com.frs.bean.MDIRequestResponseBean;
import com.frs.bean.MDIUser;
import com.frs.common.MDIProfileHelper;
import com.frs.common.MDIProfileUtils;
import com.acc.storefront.controllers.FcControllerConstants;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Iterator;

import javax.servlet.http.HttpServletResponse;

/**
 * 
 */

/**
 * @author swarnima.gupta
 *
 */
@Controller
@Scope("tenant")
@RequestMapping("/my-account")
public class AjaxCMSController extends AbstractAddOnPageController
{
	
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

	private static int counter = 0;
	
	@RequestMapping(value = "/profile/uploadImage", method = RequestMethod.POST)  
	public @ResponseBody String fileUploaded(MultipartHttpServletRequest request, HttpServletResponse response, final Model model)
	{  
		System.out.println("-----------------------------------------------------------------------");
		Iterator<String> itr =  request.getFileNames();
		MultipartFile file = request.getFile(itr.next());
		System.out.println(file.getOriginalFilename() +" uploaded!");
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
			  	DataInputStream dis = null;
   			final MediaModel media = modelService.create(MediaModel.class);
   			try
   			{
   				counter++;
   				media.setCode(counter + "");
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
	  catch (IOException e) 
	  {  
   	   // TODO Auto-generated catch block  
   	   e.printStackTrace();  
	  }  
	  
	  return FcControllerConstants.Views.Fragments.Facerecog.ajaxuploadImage;  
	 }  
}
