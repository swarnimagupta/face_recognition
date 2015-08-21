/**
 *
 */
package com.acc.service;

import java.util.List;

import com.acc.model.ImageQualityModel;


/**
 * @author swarnima.gupta
 *
 */
public interface MDIYCustomerInformationService
{
	public ImageQualityModel getCustomerImages(String customerId);

	public List<ImageQualityModel> getImageQualities();
}
