/**
 *
 */
package com.acc.facade.impl;

import static org.junit.Assert.assertEquals;

import de.hybris.platform.acceleratorservices.setup.SetupSyncJobService;
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

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.acc.dao.StoreLoginDao;
import com.acc.data.ImageQualityData;
import com.acc.data.StatusData;
import com.acc.facade.MDIYSaveCustomerDataFacade;
import com.acc.model.ImageQualityModel;
import com.acc.service.MDIYSaveCustomerDataService;


/**
 * @author swarnima.gupta
 *
 */

public class MDIYSaveCustomerDataFacadeImpl implements MDIYSaveCustomerDataFacade
{

	private static final Logger LOG = Logger.getLogger(MDIYSaveCustomerDataFacadeImpl.class);

	private UserService userService;

	@Autowired
	private StoreLoginDao storeLoginDao;

	@Resource(name = "mdiYBase64ToImageConverter")
	private Converter<String, ImageQualityData> mdiYBase64ToImageConverter;

	@Autowired
	private MDIYSaveCustomerDataService mdiySaveCustomerDataService;

	@Autowired
	private ModelService modelService;

	private static final String FACE_RECOGNITION_IMAGE_THRESHOLD = "face.recognition.image.threshold";

	@Autowired
	private MediaService mediaService;

	@Autowired
	private CatalogVersionService catalogVersionService;

	@Autowired
	private SetupSyncJobService setupSyncJobService;

	private static int counter = 0;


	@Override
	public void saveCustomerData(final CustomerModel model)
	{
		// YTODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.acc.facade.MDIYSaveCustomerDataFacade#saveCustomerImage(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public StatusData saveCustomerImage(final String imageInBase64, final String customerId, final String qualityScore)
	{
		final CustomerModel model = storeLoginDao.isCustomerFound(customerId);
		//		final MDIRequestResponseBean bean = new MDIRequestResponseBean();
		//		final MDIUser user = new MDIUser();
		//		LOG.info("imageInBase64" + imageInBase64);
		//		LOG.info("customerId" + customerId);
		//		user.setImageInBase64(imageInBase64);
		//		user.setHybrisId(customerId);
		//		bean.setUser(user);
		//		final MDIProfileUtils util = new MDIProfileUtils();
		//		final String inputJson = util.getJsonForNewUser(bean);
		//		LOG.info("inputJson" + inputJson);
		//		final MDIProfileHelper helper = new MDIProfileHelper();
		//		final MDIRequestResponseBean outputBean = helper.newUser(inputJson);
		//		final MDIUser outputUser = outputBean.getUser();
		//		LOG.info("outputBean.getQualityScoreMap().get(Default.Cognitec850)"
		//				+ outputBean.getQualityScoreMap().get("Default.Cognitec850"));

		////////////////////////////////////////////////////////////////////////////////////////////////////////
		//		final MDIProfileUtils utils = new MDIProfileUtils();
		//		final MDIRequestResponseBean bean = new MDIRequestResponseBean();
		//		final MDIUser user = new MDIUser();
		//		user.setImageInBase64(imageInBase64);
		//		bean.setUser(user);
		//		final String inputJson = utils.getJsonForQuality(bean);
		//		final MDIProfileHelper helper = new MDIProfileHelper();
		//		final MDIRequestResponseBean output = helper.checkQuality(inputJson);
		//		final Map<String, String> scoreMap = output.getQualityScoreMap();
		//		final BigDecimal qualityScore = new BigDecimal(scoreMap.get("Default.Cognitec850"));
		//		final BigDecimal thresholdValue = new BigDecimal(Config.getString(FACE_RECOGNITION_IMAGE_THRESHOLD, "20"));
		//		if (qualityScore.compareTo(thresholdValue) >= 0)
		//		{
		//////////////////////////////////////////////////////////////////////////////////////////////////////
		final ImageQualityModel imageQuality = modelService.create(ImageQualityModel.class);
		imageQuality.setQualityScore(qualityScore);
		imageQuality.setCustomer(model);
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
		//////////////////////////////////////////////////////////////
		//persist in biometrics get identity id
		//			final String inputPersistUserJson = utils.getJsonForNewUser(bean);
		//			final MDIRequestResponseBean requestResponseBean = helper.newUser(inputPersistUserJson);
		//			imageQuality.setIdentityId(requestResponseBean.getUser().biometricId);
		////////////////////////////////////////////////////////////////////////////////////////////
		modelService.saveAll(imageQuality);
		//		}
		/////////////////////////////////////////////
		//		final ImageQualityData data = mdiYBase64ToImageConverter.convert(imageInBase64);
		//		final ImageQualityModel qualityModel = modelService.create(ImageQualityModel.class);
		//		qualityModel.setImagePath(data.getImagePath());
		//		LOG.info("data.getImagePath()" + data.getImagePath());
		//		qualityModel.setCustomer(model);
		//		//LOG.info("outputUser.getBiometricId()" + outputUser.getBiometricId());
		//		//qualityModel.setIdentityId(Integer.valueOf(outputUser.getBiometricId()));
		//		//qualityModel.setQualityScore(outputBean.getQualityScoreMap().get("Default.Cognitec850"));
		//		modelService.save(qualityModel);
		final StatusData status = new StatusData();
		status.setStatus("success");
		return status;
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService()
	{
		return userService;
	}

	/**
	 * @param userService
	 *           the userService to set
	 */
	public void setUserService(final UserService userService)
	{
		this.userService = userService;
	}

	/**
	 * @return the mdiySaveCustomerDataService
	 */
	public MDIYSaveCustomerDataService getMdiySaveCustomerDataService()
	{
		return mdiySaveCustomerDataService;
	}

	/**
	 * @param mdiySaveCustomerDataService
	 *           the mdiySaveCustomerDataService to set
	 */
	public void setMdiySaveCustomerDataService(final MDIYSaveCustomerDataService mdiySaveCustomerDataService)
	{
		this.mdiySaveCustomerDataService = mdiySaveCustomerDataService;
	}

	/**
	 * @param modelService
	 *           the modelService to set
	 */
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	/**
	 * @param storeLoginDao
	 *           the storeLoginDao to set
	 */
	public void setStoreLoginDao(final StoreLoginDao storeLoginDao)
	{
		this.storeLoginDao = storeLoginDao;
	}

	/**
	 * @param mediaService
	 *           the mediaService to set
	 */
	public void setMediaService(final MediaService mediaService)
	{
		this.mediaService = mediaService;
	}

	/**
	 * @param catalogVersionService
	 *           the catalogVersionService to set
	 */
	public void setCatalogVersionService(final CatalogVersionService catalogVersionService)
	{
		this.catalogVersionService = catalogVersionService;
	}

	/**
	 * @param setupSyncJobService
	 *           the setupSyncJobService to set
	 */
	public void setSetupSyncJobService(final SetupSyncJobService setupSyncJobService)
	{
		this.setupSyncJobService = setupSyncJobService;
	}


}
