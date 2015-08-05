/**
 *
 */
package com.acc.dao.impl;

import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.Collection;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.acc.dao.MDIYCustomerInformationDao#getCustomerImages(java.lang.String)
	 */
	@Override
	public Collection<ImageQualityModel> getCustomerImages(final String customerId)
	{

		final FlexibleSearchQuery query = new FlexibleSearchQuery(
				"select {pk} from {ImageQuality} where {customer}=(select {pk} from {customer} where {customerId} like '"
						+ customerId + "%')");
		final SearchResult<ImageQualityModel> result = getFlexibleSearchService().search(query);
		return CollectionUtils.isNotEmpty(result.getResult()) ? result.getResult() : CollectionUtils.EMPTY_COLLECTION;
	}

}
