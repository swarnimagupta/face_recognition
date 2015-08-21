/**
 *
 */
package com.acc.form;

import org.springframework.web.multipart.MultipartFile;


/**
 * @author swarnima.gupta
 *
 */
public class UploadedFile
{

	private MultipartFile file;

	public MultipartFile getFile()
	{
		return file;
	}

	public void setFile(final MultipartFile file)
	{
		this.file = file;
	}
}
