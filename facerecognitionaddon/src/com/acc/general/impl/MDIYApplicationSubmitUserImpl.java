/**
 *
 */
package com.acc.general.impl;

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

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.acc.data.ImageQualityData;
import com.acc.model.CSRCustomerDetailsModel;
import com.acc.service.MDIYSubmitUserService;
import com.frs.bean.MDIRequestResponseBean;
import com.frs.bean.MDIUser;
import com.frs.submitUser.MDIApplicationSubmitUserInterface;
import com.frs.submitUser.StatusData;


/**
 * @author swarnima.gupta
 *
 */
public class MDIYApplicationSubmitUserImpl implements MDIApplicationSubmitUserInterface
{
	private MDIYSubmitUserService mdiYSubmitUserService;

	@Resource(name = "mdiYBase64ToImageConverter")
	private Converter<String, ImageQualityData> mdiYBase64ToImageConverter;

	@Resource(name = "customCsrCustomerDetailsConverter")
	private Converter<MDIUser, CSRCustomerDetailsModel> mdiYCSRCustomerConverter;

	@Resource(name = "mdiYCustomerToCSRCustomerConverter")
	private Converter<CustomerModel, CSRCustomerDetailsModel> mdiYCustomerToCSRCustomerConverter;

	private ModelService modelService;

	@Autowired
	private MediaService mediaService;

	@Autowired
	private CatalogVersionService catalogVersionService;

	@Autowired
	private SetupSyncJobService setupSyncJobService;

	private static int counter1 = 1000;

	private static final Logger LOG = Logger.getLogger(MDIYApplicationSubmitUserImpl.class);

	private static int counter = 100;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.frs.submitUser.MDIApplicationSubmitUserInterface#notifyAgentForNewUser(com.frs.bean.MDIRequestResponseBean)
	 */
	@Override
	public StatusData notifyAgentForNewUser(final MDIRequestResponseBean bean)
	{
		LOG.info("notifyAgentForNewUser");
		final MDIUser user = bean.getUser();
		final StatusData statusData = new StatusData();
		final ImageQualityData mdiYImageData = mdiYBase64ToImageConverter.convert(user.getImageInBase64());
		DataInputStream dis = null;
		final MediaModel media = modelService.create(MediaModel.class);
		try
		{
			counter1++;
			media.setCode(counter1 + "");
			final CatalogVersionModel stagedCatalogVersion = catalogVersionService.getCatalogVersion("electronicsContentCatalog",
					CatalogManager.OFFLINE_VERSION);
			media.setCatalogVersion(stagedCatalogVersion);
			modelService.save(media);
			final FileInputStream fis = new FileInputStream(mdiYImageData.getImagePath());
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
		CSRCustomerDetailsModel model = modelService.create(CSRCustomerDetailsModel.class);
		model.setImageUrl(media.getURL2());
		model = mdiYCSRCustomerConverter.convert(user, model);
		model.setCustomerId(String.valueOf(counter));
		counter++;
		mdiYSubmitUserService.addUserToQueue(model);
		statusData.setStatus("success");
		return statusData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.frs.submitUser.MDIApplicationSubmitUserInterface#notifyAgentForRegisteredUser(com.frs.bean.MDIRequestResponseBean
	 * )
	 */
	@Override
	public StatusData notifyAgentForRegisteredUser(final MDIRequestResponseBean bean)
	{
		LOG.info("notifyAgentForRegisteredUser");
		final MDIUser user = bean.getUser();
		final StatusData statusData = new StatusData();
		final CustomerModel customer = mdiYSubmitUserService.doesCustomerExist(user.getBiometricId());
		if (StringUtils.isNotEmpty(user.getBiometricId()) && null != customer)
		{
			CSRCustomerDetailsModel model = modelService.create(CSRCustomerDetailsModel.class);
			model = mdiYCSRCustomerConverter.convert(user, model);
			model = mdiYCustomerToCSRCustomerConverter.convert(customer, model);
			mdiYSubmitUserService.addUserToQueue(model);
			statusData.setStatus("success");
		}
		return statusData;
	}

	/**
	 * @param mdiYSubmitUserService
	 *           the mdiYSubmitUserService to set
	 */
	public void setMdiYSubmitUserService(final MDIYSubmitUserService mdiYSubmitUserService)
	{
		this.mdiYSubmitUserService = mdiYSubmitUserService;
	}

	/**
	 * @param modelService
	 *           the modelService to set
	 */
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

}
