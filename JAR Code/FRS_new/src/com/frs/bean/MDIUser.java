/**
 * 
 */
package com.frs.bean;

import java.awt.image.BufferedImage;

/**
 * @author Pavan
 *
 */
public class MDIUser {

	public String userName;
	public String hybrisId;
	public String biometricId;
	public BufferedImage image;
	public String imagePath;
	public String imageInBase64;
	public int age;
	public String complexion;
	public String gender;
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getHybrisId() {
		return hybrisId;
	}
	
	public void setHybrisId(String hybrisId) {
		this.hybrisId = hybrisId;
	}
	
	public String getBiometricId() {
		return biometricId;
	}
	
	public void setBiometricId(String biometricId) {
		this.biometricId = biometricId;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	public String getImagePath() {
		return imagePath;
	}
	
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	public String getImageInBase64() {
		return imageInBase64;
	}
	
	public void setImageInBase64(String imageInBase64) {
		this.imageInBase64 = imageInBase64;
	}

	/**
	 * @return the complexion
	 */
	public String getComplexion() {
		return complexion;
	}

	/**
	 * @param complexion the complexion to set
	 */
	public void setComplexion(String complexion) {
		this.complexion = complexion;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}
	
}
