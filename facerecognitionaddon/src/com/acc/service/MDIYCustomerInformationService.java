/**
 *
 */
package com.acc.service;

import java.util.Collection;

import com.acc.model.ImageQualityModel;


/**
 * @author swarnima.gupta
 *
 */
public interface MDIYCustomerInformationService
{
	public Collection<ImageQualityModel> getCustomerImages(String customerId);
}
