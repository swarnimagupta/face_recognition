/**
 *
 */
package com.acc.facades.storecustomer;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.acc.enums.CSRStoreStatus;
import com.acc.facades.CSRCustomerDetails.data.CSRCustomerDetailsData;
import com.acc.model.CSRCustomerDetailsModel;


/**
 * @author prasun.a.kumar
 *
 */
public interface StoreCustomerFacade
{

	/**
	 * This method returns the CSR customer details list.
	 */
	public List<CSRCustomerDetailsModel> getCSRCustomerDetails();

	/**
	 * This method is used to update the customer details.
	 *
	 * @param status
	 *           YTODO
	 */
	public CSRCustomerDetailsModel updateCSRCustomerDetail(final String csrUID, final String customerUID, String status);

	/**
	 * @param CSRStoreStatus
	 * @return List<CSRCustomerDetailsModel>
	 */
	public List<CSRCustomerDetailsModel> getCSRCustomerDetailsByStatus(CSRStoreStatus status);

	public List<CSRCustomerDetailsData> getCollectOrderByCustomerName(String customerName, HttpServletRequest request);

	public List<CSRCustomerDetailsData> getCustomerDetailsByDateAndTime(final String fromDate, final String toDate,
			final String fromTime, final String toTime, HttpServletRequest request);

}
