/**
 *
 */
package com.acc.dao.impl;

import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.acc.dao.MDIYSubmitUserDao;


/**
 * @author swarnima.gupta
 *
 */
public class MDIYSubmitUserDaoImpl implements MDIYSubmitUserDao
{

	@Autowired
	private FlexibleSearchService flexibleSearchService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.acc.dao.MDIYSubmitUserDao#doesCustomerExist(java.lang.String)
	 */
	@Override
	public CustomerModel doesCustomerExist(final String identityId)
	{
		final FlexibleSearchQuery flexibleQuery = new FlexibleSearchQuery(
				"select {pk} from {Customer} where {imageQuality}=(select {pk} from {ImageQuality} " + "where {identityId}='"
						+ identityId + "')");
		final SearchResult<CustomerModel> result = flexibleSearchService.search(flexibleQuery);
		return CollectionUtils.isNotEmpty(result.getResult()) ? result.getResult().get(0) : null;
	}

}
