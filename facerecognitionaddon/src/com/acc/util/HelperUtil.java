/**
 *
 */
package com.acc.util;

import java.io.File;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;


/**
 * @author swarnima.gupta
 *
 */
public class HelperUtil
{

	private static final String SEPARATOR = "\\";
	private static final String HYBRIS_DATA = "\\hybris\\data\\";
	private static final String IMAGE2 = "\\image\\";
	private static final Logger LOG = Logger.getLogger(HelperUtil.class);

	/**
	 * @param uid
	 */
	public static void createDirectory(final String uid)
	{
		String location = "C:" + HYBRIS_DATA;
		final String dateTime = new DateTime().toString("dd-MM-yy HH:mm:ss");
		File g = new File(location + IMAGE2);
		if (!g.exists())
		{
			if (g.mkdir())
			{
				LOG.debug("image Directory is created!" + dateTime.toString());
			}
			else
			{
				LOG.debug("Failed to create directory!" + dateTime.toString());
				//creating for linux or mac
				location = HYBRIS_DATA;
				g = new File(location + IMAGE2);
				if (!g.exists())
				{
					if (g.mkdir())
					{
						LOG.debug("image Directory is created!" + dateTime.toString());
					}
					else
					{
						LOG.debug("Failed to create directory!" + dateTime.toString());
					}
				}
			}
		}
		else
		{
			final String output = dateTime.toString().replace(":", "-");
			final File f = new File(g + SEPARATOR + output);
			LOG.debug("datetime Directory is created!" + output + g.toString());
			f.mkdir();
			LOG.debug("datetime Directory is created after f!" + f.toString());
			if (f.exists())
			{
				final File e = new File(f + SEPARATOR + uid);
				e.mkdir();
				LOG.debug("customerID folder is created after f!" + f.toString());
			}
		}
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
