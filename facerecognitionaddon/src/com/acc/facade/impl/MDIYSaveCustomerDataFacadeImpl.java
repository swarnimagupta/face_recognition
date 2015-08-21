/**
 *
 */
package com.acc.facade.impl;

import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.util.Config;

import java.math.BigDecimal;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.acc.dao.StoreLoginDao;
import com.acc.data.ImageQualityData;
import com.acc.data.StatusData;
import com.acc.facade.MDIYSaveCustomerDataFacade;
import com.acc.model.ImageQualityModel;
import com.acc.service.MDIYSaveCustomerDataService;
import com.frs.bean.MDIRequestResponseBean;
import com.frs.bean.MDIUser;
import com.frs.common.MDIProfileHelper;
import com.frs.common.MDIProfileUtils;


/**
 * @author swarnima.gupta
 *
 */

public class MDIYSaveCustomerDataFacadeImpl implements MDIYSaveCustomerDataFacade
{

	private static final Logger LOG = Logger.getLogger(MDIYSaveCustomerDataFacadeImpl.class);

	private UserService userService;
	@Autowired
	private StoreLoginDao storeLoginDao;

	@Resource(name = "mdiYBase64ToImageConverter")
	private Converter<String, ImageQualityData> mdiYBase64ToImageConverter;

	private MDIYSaveCustomerDataService mdiySaveCustomerDataService;

	private ModelService modelService;

	private static final String FACE_RECOGNITION_IMAGE_THRESHOLD = "face.recognition.image.threshold";


	@Override
	public void saveCustomerData(final CustomerModel model)
	{
		// YTODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.acc.facade.MDIYSaveCustomerDataFacade#saveCustomerImage(java.lang.String, java.lang.String)
	 */
	@Override
	public StatusData saveCustomerImage(final String imageInBase64, final String customerId)
	{
		final CustomerModel model = storeLoginDao.isCustomerFound(customerId);
		//		final MDIRequestResponseBean bean = new MDIRequestResponseBean();
		//		final MDIUser user = new MDIUser();
		//		LOG.info("imageInBase64" + imageInBase64);
		//		LOG.info("customerId" + customerId);
		//		user.setImageInBase64(imageInBase64);
		//		user.setHybrisId(customerId);
		//		bean.setUser(user);
		//		final MDIProfileUtils util = new MDIProfileUtils();
		//		final String inputJson = util.getJsonForNewUser(bean);
		//		LOG.info("inputJson" + inputJson);
		//		final MDIProfileHelper helper = new MDIProfileHelper();
		//		final MDIRequestResponseBean outputBean = helper.newUser(inputJson);
		//		final MDIUser outputUser = outputBean.getUser();
		//		LOG.info("outputBean.getQualityScoreMap().get(Default.Cognitec850)"
		//				+ outputBean.getQualityScoreMap().get("Default.Cognitec850"));

		final MDIProfileUtils utils = new MDIProfileUtils();
		final MDIRequestResponseBean bean = new MDIRequestResponseBean();
		final MDIUser user = new MDIUser();
		user.setImageInBase64(imageInBase64);
		bean.setUser(user);
		final String inputJson = utils.getJsonForQuality(bean);
		final MDIProfileHelper helper = new MDIProfileHelper();
		final MDIRequestResponseBean output = helper.checkQuality(inputJson);
		final Map<String, String> scoreMap = output.getQualityScoreMap();
		final BigDecimal qualityScore = new BigDecimal(scoreMap.get("Default.Cognitec850"));
		final BigDecimal thresholdValue = new BigDecimal(Config.getString(FACE_RECOGNITION_IMAGE_THRESHOLD, "20"));
		if (qualityScore.compareTo(thresholdValue) >= 0)
		{
			final ImageQualityModel imageQuality = modelService.create(ImageQualityModel.class);
			imageQuality.setQualityScore(qualityScore.toString());
			imageQuality.setCustomer(model);
			final ImageQualityData data = mdiYBase64ToImageConverter.convert(imageInBase64);
			imageQuality.setImagePath(data.getImagePath());
			//persist in biometrics get identity id
			final String inputPersistUserJson = utils.getJsonForNewUser(bean);
			final MDIRequestResponseBean requestResponseBean = helper.newUser(inputPersistUserJson);
			imageQuality.setIdentityId(requestResponseBean.getUser().biometricId);
			modelService.saveAll(imageQuality);
		}
		/////////////////////////////////////////////
		//		final ImageQualityData data = mdiYBase64ToImageConverter.convert(imageInBase64);
		//		final ImageQualityModel qualityModel = modelService.create(ImageQualityModel.class);
		//		qualityModel.setImagePath(data.getImagePath());
		//		LOG.info("data.getImagePath()" + data.getImagePath());
		//		qualityModel.setCustomer(model);
		//		//LOG.info("outputUser.getBiometricId()" + outputUser.getBiometricId());
		//		//qualityModel.setIdentityId(Integer.valueOf(outputUser.getBiometricId()));
		//		//qualityModel.setQualityScore(outputBean.getQualityScoreMap().get("Default.Cognitec850"));
		//		modelService.save(qualityModel);
		final StatusData status = new StatusData();
		status.setStatus("success");
		return status;
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService()
	{
		return userService;
	}

	/**
	 * @param userService
	 *           the userService to set
	 */
	public void setUserService(final UserService userService)
	{
		this.userService = userService;
	}

	/**
	 * @return the mdiySaveCustomerDataService
	 */
	public MDIYSaveCustomerDataService getMdiySaveCustomerDataService()
	{
		return mdiySaveCustomerDataService;
	}

	/**
	 * @param mdiySaveCustomerDataService
	 *           the mdiySaveCustomerDataService to set
	 */
	public void setMdiySaveCustomerDataService(final MDIYSaveCustomerDataService mdiySaveCustomerDataService)
	{
		this.mdiySaveCustomerDataService = mdiySaveCustomerDataService;
	}

	/**
	 * @param modelService
	 *           the modelService to set
	 */
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}
}
