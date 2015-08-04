/**
 * 
 */
package com.acc.core.facade.impl;

import javax.annotation.Resource;

import com.acc.core.facade.MDIYSaveBiometricDataFacade;
import com.acc.core.service.MDIYSaveBiometricDataService;


/**
 * @author jayasree.gogineni
 * 
 */
public class MDIYSaveBiometricDataFacadeImpl implements MDIYSaveBiometricDataFacade
{
	@Resource(name = "saveBiometricDataService")
	MDIYSaveBiometricDataService saveBiometricDataService;

	public MDIYSaveBiometricDataService mdiySaveBiometricDataService;

	/**
	 * @return the saveBiometricDataService
	 */
	public MDIYSaveBiometricDataService getSaveBiometricDataService()
	{
		return saveBiometricDataService;
	}

	/**
	 * @param saveBiometricDataService
	 *           the saveBiometricDataService to set
	 */
	public void setSaveBiometricDataService(final MDIYSaveBiometricDataService saveBiometricDataService)
	{
		this.saveBiometricDataService = saveBiometricDataService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.acc.core.facade.MDIYSaveBiometricDataFacade#saveBiometricData(java.lang.String)
	 */
	@Override
	public void saveBiometricData(final String biometricID)
	{
		// YTODO Auto-generated method stub

	}



}
