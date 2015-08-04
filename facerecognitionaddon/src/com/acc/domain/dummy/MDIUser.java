/**
 *
 */
package com.acc.domain.dummy;

import java.awt.image.BufferedImage;


/**
 * @author Pavan
 *
 */
public class MDIUser
{

	public String userName;
	public String hybrisId;
	public String biometricId;
	public BufferedImage image;
	public String imagePath;
	public String imageInBase64;

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(final String userName)
	{
		this.userName = userName;
	}

	public String getHybrisId()
	{
		return hybrisId;
	}

	public void setHybrisId(final String hybrisId)
	{
		this.hybrisId = hybrisId;
	}

	public String getBiometricId()
	{
		return biometricId;
	}

	public void setBiometricId(final String biometricId)
	{
		this.biometricId = biometricId;
	}

	public BufferedImage getImage()
	{
		return image;
	}

	public void setImage(final BufferedImage image)
	{
		this.image = image;
	}

	public String getImagePath()
	{
		return imagePath;
	}

	public void setImagePath(final String imagePath)
	{
		this.imagePath = imagePath;
	}

	public String getImageInBase64()
	{
		return imageInBase64;
	}

	public void setImageInBase64(final String imageInBase64)
	{
		this.imageInBase64 = imageInBase64;
	}

}
