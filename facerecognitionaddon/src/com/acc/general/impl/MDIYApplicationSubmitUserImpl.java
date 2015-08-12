/**
 *
 */
package com.acc.general.impl;

import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.model.ModelService;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

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
		CSRCustomerDetailsModel model = modelService.create(CSRCustomerDetailsModel.class);
		model.setImageUrl(mdiYImageData.getImagePath());
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
		LOG.info("notifyAgentForNewUser");
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
