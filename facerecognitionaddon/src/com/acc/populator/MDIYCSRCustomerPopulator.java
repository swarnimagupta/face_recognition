/**
 *
 */
package com.acc.populator;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import org.springframework.util.Assert;

import com.acc.domain.dummy.MDIUser;
import com.acc.model.CSRCustomerDetailsModel;


/**
 * @author swarnima.gupta
 *
 */
public class MDIYCSRCustomerPopulator implements Populator<MDIUser, CSRCustomerDetailsModel>
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hybris.platform.converters.Populator#populate(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void populate(final MDIUser source, final CSRCustomerDetailsModel target) throws ConversionException
	{
		Assert.notNull(source);
		Assert.notNull(target);
		target.setCustomerId(source.getHybrisId());
		target.setCustomerName(source.getUserName());
	}
}
