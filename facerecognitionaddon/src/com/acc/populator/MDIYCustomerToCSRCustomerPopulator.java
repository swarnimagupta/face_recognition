/**
 *
 */
package com.acc.populator;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import java.util.Date;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import com.acc.enums.CSRStoreStatus;
import com.acc.model.CSRCustomerDetailsModel;


/**
 * @author swarnima.gupta
 *
 */
public class MDIYCustomerToCSRCustomerPopulator implements Populator<CustomerModel, CSRCustomerDetailsModel>
{

	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.converters.Populator#populate(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void populate(final CustomerModel source, final CSRCustomerDetailsModel target) throws ConversionException
	{
		Assert.notNull(source);
		Assert.notNull(target);
		target.setComplexion(source.getComplexion());
		final String[] ignoreProperties =
		{ "creationtime", "loginTime", "pk" };
		BeanUtils.copyProperties(source, target, ignoreProperties);
		target.setLoginTime(new Date());
		target.setCustomerId(source.getUid());
		target.setCustomerName(source.getDisplayName());
		target.setUUID(source.getUUID());
		target.setPointOfService("Chiba");
		target.setStatus(CSRStoreStatus.LOGGEDIN);
		if (CollectionUtils.isNotEmpty(source.getImageQuality()))
		{
			target.setImageUrl(source.getImageQuality().iterator().next().getImagePath());
		}

	}

}
