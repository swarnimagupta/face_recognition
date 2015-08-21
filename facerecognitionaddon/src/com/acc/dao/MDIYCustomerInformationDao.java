/**
 *
 */
package com.acc.dao;

import java.util.List;

import com.acc.model.ImageQualityModel;


/**
 * @author swarnima.gupta
 *
 */
public interface MDIYCustomerInformationDao
{
	public ImageQualityModel getCustomerImages(String customerId);

	public List<ImageQualityModel> getImageQualities();
}
