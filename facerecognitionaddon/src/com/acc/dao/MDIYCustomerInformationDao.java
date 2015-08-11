/**
 *
 */
package com.acc.dao;

import com.acc.model.ImageQualityModel;


/**
 * @author swarnima.gupta
 *
 */
public interface MDIYCustomerInformationDao
{
	public ImageQualityModel getCustomerImages(String customerId);
}
