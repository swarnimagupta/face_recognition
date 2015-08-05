/**
 *
 */
package com.acc.populator;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

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
	}

}
