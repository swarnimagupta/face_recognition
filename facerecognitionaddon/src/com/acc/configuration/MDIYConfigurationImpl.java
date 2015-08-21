/**
 *
 */
package com.acc.configuration;

import de.hybris.platform.util.Config;

import com.frs.common.MDIConfigurationInterface;



/**
 * @author swapnil.a.pandey
 *
 */
public class MDIYConfigurationImpl implements MDIConfigurationInterface
{
	private static final String FACE_RECOGNITION_IMAGE_THRESHOLD = "face.recognition.image.threshold";
	private static final String FACE_RECOGNITION_IMAGE_QUALITY_CHECK_URL = "face.recognition.image.quality.check.url";
	private static final String FACE_RECOGNITION_PERSIST_NEW_USER_URL = "face.recognition.persist.new.user.url";
	private String imageQualityCheckUrl;
	private int imageThresholdValue;
	private String persistNewUserUrl;




	@Override
	public int getImageThresholdValue()
	{
		imageThresholdValue = Config.getInt(FACE_RECOGNITION_IMAGE_THRESHOLD, 20);
		return imageThresholdValue;

	}




	/*
	 * (non-Javadoc)
	 *
	 * @see com.acc.domain.dummy.MDIConfigurationInterface#getImageQualityCheckUrl()
	 */
	@Override
	public String getImageQualityCheckUrl()
	{
		imageQualityCheckUrl = Config.getParameter(FACE_RECOGNITION_IMAGE_QUALITY_CHECK_URL);
		return imageQualityCheckUrl;
	}




	/*
	 * (non-Javadoc)
	 *
	 * @see com.acc.domain.dummy.MDIConfigurationInterface#getPersistNewUserUrl()
	 */
	@Override
	public String getPersistNewUserUrl()
	{
		persistNewUserUrl = Config.getParameter(FACE_RECOGNITION_PERSIST_NEW_USER_URL);
		return persistNewUserUrl;
	}






}
