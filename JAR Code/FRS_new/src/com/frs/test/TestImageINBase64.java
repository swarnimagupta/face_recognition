/**
 * 
 */
package com.frs.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.frs.common.MDIProfileHelper;
import com.frs.common.MDIProfileUtils;

/**
 * @author Pavan
 *
 */
public class TestImageINBase64 {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		MDIProfileUtils mdiProfileUtils = new MDIProfileUtils();
		
		File imageFile= new File("d:/abc.jpg");
		BufferedImage image = ImageIO.read(imageFile);
		
		String base64String = mdiProfileUtils.getImageInBase64(image);
		System.out.println("Base 64 String : "+base64String);
		System.out.println("Base 64 String Length : "+base64String.length());
		
	}

}
