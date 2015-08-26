/**
 *
 */
package com.acc.util;

import java.io.File;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;


/**
 * @author swarnima.gupta
 *
 */
public class HelperUtil
{

	private static final String SEPARATOR = "/";
	private static final String HYBRIS_DATA = "/hybris/data";
	private static final Logger LOG = Logger.getLogger(HelperUtil.class);

	/**
	 * @param uid
	 */
	public static String createDirectory(final String uid)
	{
		final StringBuilder location = new StringBuilder("./../../../../data/media/sys_master/images/face_recog_images");
		final String dateTime = (new Date()).toString();
		final String output = dateTime.toString().replace(":", "-");
		location.append(SEPARATOR).append(output);
		location.append(SEPARATOR).append(uid).append(SEPARATOR);
		final File g = new File(location.toString());
		if (!g.exists())
		{
			boolean result = false;
			try
			{
				result = g.mkdirs();
			}
			catch (final SecurityException se)
			{
				LOG.error("could not create directory", se);
			}
			if (result)
			{
				LOG.info("image Directory is created!" + dateTime.toString());
			}
			else
			{
				LOG.info("Failed to create directory!" + dateTime.toString());
			}
		}
		return location.toString();
	}

	/**
	 * Encodes the byte array into base64 string
	 *
	 * @param imageByteArray
	 *           - byte array
	 * @return String a {@link java.lang.String}
	 */
	public static String encodeImage(final byte[] imageByteArray)
	{
		return Base64.encodeBase64URLSafeString(imageByteArray);
	}

	/**
	 * Decodes the base64 string into byte array
	 *
	 * @param imageDataString
	 *           - a {@link java.lang.String}
	 * @return byte array
	 */
	public static byte[] decodeImage(final String imageDataString)
	{
		return Base64.decodeBase64(imageDataString);
	}
}
