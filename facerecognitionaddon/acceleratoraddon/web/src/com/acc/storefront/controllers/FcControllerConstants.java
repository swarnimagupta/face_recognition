package com.acc.storefront.controllers;

/**
 * @author swapnil.a.pandey
 * 
 */
public interface FcControllerConstants
{
	/**
	 * Class with view name constants
	 */
	interface Views
	{
		interface Cms
		{
			String ComponentPrefix = "cms/";
		}

		interface Pages
		{
			interface Account
			{
				String FaceRecognitionPage = "pages/addon:/facerecognitionaddon/pages/account/faceRecognitionPage";
			}

		}
		
		interface Fragments
		{
			interface Facerecog
			{
				String ajaxuploadImage = "pages/addon:/facerecognitionaddon/fragments/facerecog/ajaxuploadImage";
			}
		}
	}
}
