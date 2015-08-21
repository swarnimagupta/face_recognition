/**
 *
 */
package com.acc.dao.impl;

import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.acc.dao.MDIYCustomerInformationDao;
import com.acc.model.ImageQualityModel;


/**
 * @author swarnima.gupta
 *
 */
public class MDIYCustomerInformationDaoImpl extends DefaultGenericDao<ImageQualityModel> implements MDIYCustomerInformationDao
{

	/**
	 * @param typecode
	 */
	public MDIYCustomerInformationDaoImpl(final String typecode)
	{
		super(typecode);
		// YTODO Auto-generated constructor stub
	}

	public CustomerModel getCustomer(final String customerId)
	{
		final FlexibleSearchQuery query = new FlexibleSearchQuery("select {pk} from {customer} where {customerID} like '"
				+ customerId + "%' OR {uid} like '" + customerId + "%'");
		final SearchResult<CustomerModel> result = getFlexibleSearchService().search(query);
		return CollectionUtils.isNotEmpty(result.getResult()) ? result.getResult().get(0) : null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.acc.dao.MDIYCustomerInformationDao#getCustomerImages(java.lang.String)
	 */
	@Override
	public ImageQualityModel getCustomerImages(final String customerId)
	{
		final CustomerModel customer = getCustomer(customerId);
		final FlexibleSearchQuery query = new FlexibleSearchQuery("select {pk} from {ImageQuality} where {customer}='"
				+ customer.getPk().getLongValueAsString() + "'");
		final SearchResult<ImageQualityModel> result = getFlexibleSearchService().search(query);
		return CollectionUtils.isNotEmpty(result.getResult()) ? result.getResult().get(0) : null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.acc.dao.MDIYCustomerInformationDao#getImageQualities()
	 */
	@Override
	public List<ImageQualityModel> getImageQualities()
	{
		final FlexibleSearchQuery query = new FlexibleSearchQuery("select {pk} from {ImageQuality} where {identityId}=''");
		final SearchResult<ImageQualityModel> result = getFlexibleSearchService().search(query);
		return CollectionUtils.isNotEmpty(result.getResult()) ? result.getResult() : Collections.EMPTY_LIST;
	}

}
