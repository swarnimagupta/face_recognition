/**
 *
 */
package com.acc.job;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.model.ModelService;

import java.util.List;

import com.acc.model.ImageQualityModel;
import com.acc.model.cronjob.PersistCustomerImagesCronJobModel;
import com.acc.service.MDIYCustomerInformationService;
import com.frs.bean.MDIRequestResponseBean;
import com.frs.bean.MDIUser;
import com.frs.common.MDIProfileHelper;
import com.frs.common.MDIProfileUtils;


/**
 * @author swarnima.gupta
 *
 */
public class PersistCustomerImagesJob extends AbstractJobPerformable<PersistCustomerImagesCronJobModel>
{

	private MDIYCustomerInformationService mDIYCustomerInformationService;

	private ModelService modelService;

	private Converter<ImageQualityModel, String> mdiYImagesToStringConverter;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable#perform(de.hybris.platform.cronjob.model.CronJobModel
	 * )
	 */
	@Override
	public PerformResult perform(final PersistCustomerImagesCronJobModel arg0)
	{
		final List<ImageQualityModel> imageQualitiesList = mDIYCustomerInformationService.getImageQualities();
		MDIRequestResponseBean bean = null;
		MDIUser user = null;
		final MDIProfileUtils utils = new MDIProfileUtils();
		final MDIProfileHelper helper = new MDIProfileHelper();
		for (final ImageQualityModel model : imageQualitiesList)
		{
			bean = new MDIRequestResponseBean();
			user = new MDIUser();
			user.setImageInBase64(mdiYImagesToStringConverter.convert(model));
			bean.setUser(user);
			final String inputPersistUserJson = utils.getJsonForNewUser(bean);
			final MDIRequestResponseBean requestResponseBean = helper.newUser(inputPersistUserJson);
			model.setIdentityId(requestResponseBean.getUser().biometricId);
			modelService.saveAll(model);
		}
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}

	/**
	 * @return the mDIYCustomerInformationService
	 */
	public MDIYCustomerInformationService getmDIYCustomerInformationService()
	{
		return mDIYCustomerInformationService;
	}

	/**
	 * @param mDIYCustomerInformationService
	 *           the mDIYCustomerInformationService to set
	 */
	public void setmDIYCustomerInformationService(final MDIYCustomerInformationService mDIYCustomerInformationService)
	{
		this.mDIYCustomerInformationService = mDIYCustomerInformationService;
	}

	/**
	 * @return the modelService
	 */
	public ModelService getModelService()
	{
		return modelService;
	}

	/**
	 * @param modelService
	 *           the modelService to set
	 */
	@Override
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	/**
	 * @return the mdiYImagesToStringConverter
	 */
	public Converter<ImageQualityModel, String> getMdiYImagesToStringConverter()
	{
		return mdiYImagesToStringConverter;
	}

	/**
	 * @param mdiYImagesToStringConverter
	 *           the mdiYImagesToStringConverter to set
	 */
	public void setMdiYImagesToStringConverter(final Converter<ImageQualityModel, String> mdiYImagesToStringConverter)
	{
		this.mdiYImagesToStringConverter = mdiYImagesToStringConverter;
	}


}
