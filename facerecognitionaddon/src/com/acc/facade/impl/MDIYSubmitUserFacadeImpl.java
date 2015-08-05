/**
 *
 */
package com.acc.facade.impl;

import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.model.ModelService;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.acc.data.ImageQualityData;
import com.acc.data.StatusData;
import com.acc.domain.dummy.MDIUser;
import com.acc.facade.MDIYSubmitUserFacade;
import com.acc.model.CSRCustomerDetailsModel;
import com.acc.service.MDIYSubmitUserService;


/**
 * @author swarnima.gupta
 *
 */
public class MDIYSubmitUserFacadeImpl implements MDIYSubmitUserFacade
{

	private MDIYSubmitUserService mdiYSubmitUserService;

	@Resource(name = "mdiYBase64ToImageConverter")
	private Converter<String, ImageQualityData> mdiYBase64ToImageConverter;

	@Resource(name = "customCsrCustomerDetailsConverter")
	private Converter<MDIUser, CSRCustomerDetailsModel> mdiYCSRCustomerConverter;

	@Resource(name = "mdiYCustomerToCSRCustomerConverter")
	private Converter<CustomerModel, CSRCustomerDetailsModel> mdiYCustomerToCSRCustomerConverter;

	private ModelService modelService;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.acc.facade.MDIYSubmitUserFacade#addUserToQueue()
	 */
	@Override
	public StatusData addUserToQueue(final MDIUser user)
	{
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
		else if (StringUtils.isNotEmpty(user.getBiometricId()) && null == customer)
		{
			statusData.setStatus("Error in finding the customer with the given IdentityId. Customer Does Not Exist!");
		}
		else
		{
			final ImageQualityData mdiYImageData = mdiYBase64ToImageConverter.convert(user.getImageInBase64());
			CSRCustomerDetailsModel model = modelService.create(CSRCustomerDetailsModel.class);
			model.setImageUrl(mdiYImageData.getImagePath());
			model = mdiYCSRCustomerConverter.convert(user, model);
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
