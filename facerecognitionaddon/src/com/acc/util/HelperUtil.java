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
	private static final String IMAGE2 = "/image";
	private static final Logger LOG = Logger.getLogger(HelperUtil.class);

	/**
	 * @param uid
	 */
	public static String createDirectory(final String uid)
	{
		StringBuilder location = new StringBuilder("C:" + HYBRIS_DATA);
		final String dateTime = (new Date()).toString();
		location.append(IMAGE2);
		final String output = dateTime.toString().replace(":", "-");
		location.append(SEPARATOR).append(output);
		location.append(SEPARATOR).append(uid);
		File g = new File(location.toString());
		if (!g.exists())
		{
			boolean result = false;
			try
			{
				g.mkdirs();
				result = true;
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
				//creating for linux or mac
				location = new StringBuilder(HYBRIS_DATA);
				location.append(IMAGE2);
				location.append(SEPARATOR).append(output);
				location.append(SEPARATOR).append(uid);
				g = new File(location.toString());
				if (!g.exists())
				{
					result = false;
					try
					{
						g.mkdirs();
						result = true;
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
			}
		}
		//		else
		//		{
		//			//location.append(SEPARATOR).append(output);
		//			final File f = new File(g);
		//			LOG.info("datetime Directory is created!" + output + g.toString());
		//			f.mkdir();
		//			LOG.info("datetime Directory is created after f!" + f.toString());
		//			if (f.exists())
		//			{
		//				location.append(SEPARATOR).append(uid);
		//				final File e = new File(f + SEPARATOR + uid);
		//				e.mkdir();
		//				LOG.info("customerID folder is created after f!" + f.toString());
		//			}
		//		}
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
