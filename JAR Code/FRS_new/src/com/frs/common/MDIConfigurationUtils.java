package com.frs.common;




public class MDIConfigurationUtils
{

	private String imageQualityCheckUrl;
	private int imageThresholdValue;
	private String persistNewUserUrl;


	/**
	 * @return the imageQualityCheckUrl
	 */
	public String getImageQualityCheckUrl()
	{
		return imageQualityCheckUrl;
	}

	/**
	 * @param imageQualityCheckUrl
	 *           the imageQualityCheckUrl to set
	 */
	public void setImageQualityCheckUrl(final String imageQualityCheckUrl)
	{
		this.imageQualityCheckUrl = imageQualityCheckUrl;
	}

	/**
	 * @return the persistNewUserUrl
	 */
	public String getPersistNewUserUrl()
	{
		return persistNewUserUrl;
	}

	/**
	 * @param persistNewUserUrl
	 *           the persistNewUserUrl to set
	 */
	public void setPersistNewUserUrl(final String persistNewUserUrl)
	{
		this.persistNewUserUrl = persistNewUserUrl;
	}

	public void setImageThresholdValue(final int imageThresholdValue)
	{
		this.imageThresholdValue = imageThresholdValue;
	}

	public int getImageThresholdValue()
	{
		return imageThresholdValue;
	}

	public MDIConfigurationUtils()
	{
		System.out.println("inside MDIConfigurationUtils" + imageThresholdValue);

	}

	public MDIConfigurationUtils(final MDIConfigurationInterface interfaceObj)
	{
		imageQualityCheckUrl = interfaceObj.getImageQualityCheckUrl();
		imageThresholdValue = interfaceObj.getImageThresholdValue();
		persistNewUserUrl = interfaceObj.getPersistNewUserUrl();
		System.out.println("inside MDIConfigurationUtils constructor" + imageThresholdValue);
		System.out.println("inside MDIConfigurationUtils constructor" + imageQualityCheckUrl);
		System.out.println("inside MDIConfigurationUtils constructor" + persistNewUserUrl);

	}



}
