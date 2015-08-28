/**
 *
 */
package com.acc.populator;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import java.util.Date;

import org.springframework.util.Assert;

import com.acc.enums.CSRStoreStatus;
import com.acc.enums.Complexion;
import com.acc.model.CSRCustomerDetailsModel;
import com.frs.bean.MDIUser;


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
		target.setPointOfService("Chiba");
		target.setStatus(CSRStoreStatus.LOGGEDIN);
		target.setLoginTime(new Date());
		target.setAge(String.valueOf(source.getAge()));
		target.setGender(source.getGender());
		if (Complexion.BLACK.toString().equals(source.getComplexion()))
		{
			target.setComplexion(Complexion.BLACK);
		}
		else if (Complexion.DARKBROWN.toString().equals(source.getComplexion()))
		{
			target.setComplexion(Complexion.DARKBROWN);
		}
		else if (Complexion.FAIR.toString().equals(source.getComplexion()))
		{
			target.setComplexion(Complexion.FAIR);
		}
		else if (Complexion.VERYFAIR.toString().equals(source.getComplexion()))
		{
			target.setComplexion(Complexion.VERYFAIR);
		}
		else if (Complexion.WHEATISH.toString().equals(source.getComplexion()))
		{
			target.setComplexion(Complexion.WHEATISH);
		}

	}
}
