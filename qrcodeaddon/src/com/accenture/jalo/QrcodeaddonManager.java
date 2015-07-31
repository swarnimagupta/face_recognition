package com.accenture.jalo;

import com.accenture.constants.QrcodeaddonConstants;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;
import org.apache.log4j.Logger;

@SuppressWarnings("PMD")
public class QrcodeaddonManager extends GeneratedQrcodeaddonManager
{
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger( QrcodeaddonManager.class.getName() );
	
	public static final QrcodeaddonManager getInstance()
	{
		ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (QrcodeaddonManager) em.getExtension(QrcodeaddonConstants.EXTENSIONNAME);
	}
	
}
