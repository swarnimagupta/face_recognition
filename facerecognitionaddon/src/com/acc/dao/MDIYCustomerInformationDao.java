/**
 *
 */
package com.acc.dao;

import java.util.Collection;

import com.acc.model.ImageQualityModel;


/**
 * @author swarnima.gupta
 *
 */
public interface MDIYCustomerInformationDao
{
	public Collection<ImageQualityModel> getCustomerImages(String customerId);
}
