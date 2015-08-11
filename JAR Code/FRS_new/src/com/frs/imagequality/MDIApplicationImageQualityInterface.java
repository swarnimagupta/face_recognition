/**
 * 
 */
package com.frs.imagequality;

import java.awt.image.BufferedImage;

import com.frs.bean.MDIRequestResponseBean;

/**
 * @author Pavan
 *
 */
public interface MDIApplicationImageQualityInterface {

	public MDIRequestResponseBean checkQuality(String inputJson);
	
	public MDIRequestResponseBean processImage(String inputJson);
	
}
