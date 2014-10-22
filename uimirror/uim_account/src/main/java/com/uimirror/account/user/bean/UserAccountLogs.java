package com.uimirror.account.user.bean;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.uimirror.account.user.UserAccountDBFields;
import com.uimirror.core.mongo.feature.BeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;

public class UserAccountLogs extends BeanBasedDocument<UserAccountLogs> implements BeanValidatorService {
	
	private static final long serialVersionUID = -6504474875834652281L;
	private String createdOn;
	private String modifiedOn;
	private Map<String,Object> details;
	
	//Don't Use this until it has specific requirement
	public UserAccountLogs() {
		super();
	}
	
	public UserAccountLogs(Map<String, Object> details) {
		super(details);
	}

	public UserAccountLogs(String id,String createdOn, String modifiedOn, Map<String, Object> details) {
		super(id);
		this.createdOn = createdOn;
		this.modifiedOn = modifiedOn;
		this.details = details;
	}
	
	public String getProfileId(){
		return getId();
	}
	
	public String getCreatedOn() {
		return createdOn;
	}

	public String getModifiedOn() {
		return modifiedOn;
	}

	public Map<String, Object> getDetails() {
		return details;
	}
	
	@Override
	public Map<String, Object> toMap() {
		// First check if it represents a valid state then can be serialized
		if (!isValid())
			throw new IllegalStateException("Can't be serailized the state of the object");
		return serailize();
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uimirror.core.service.BeanValidatorService#isValid()
	 */
	@Override
	public boolean isValid() {
		boolean valid = Boolean.TRUE;
		if(!StringUtils.hasText(getId()))
			valid = Boolean.FALSE;
		if(!StringUtils.hasText(getCreatedOn()))
			valid = Boolean.FALSE;
		if(!StringUtils.hasText(getModifiedOn()));
			valid = Boolean.FALSE;
		
		return valid;
	}

	@Override
	public UserAccountLogs initFromMap(Map<String, Object> src) {
		// Validate the source shouldn't be empty
				validateSource(src);
				// Initialize the state
				return init(src);
	}
	
	/**
	 * converts a map that comes from DB into UserAccountLogs object.
	 * 
	 * @param raw
	 * @return {@link UserAccountLogs}
	 */
	@SuppressWarnings("unchecked")
	private UserAccountLogs init(Map<String, Object> raw) {

		String id = (String) raw.get(UserAccountDBFields.ID);
		String createdOn = (String) raw.get(UserAccountDBFields.CREATED_ON);
		String modifiedOn = (String) raw.get(UserAccountDBFields.MODIFIED_ON);
		Map<String,Object> details =  (Map<String, Object>) raw.get(UserAccountDBFields.DETAILS);

		return new UserAccountLogs(id,createdOn,modifiedOn,details);
	}
	
	/**
	 * Serialize the current state that needs to be persisted to the system.
	 * 
	 * @return
	 */
	public Map<String, Object> serailize() {
		Map<String, Object> state = new LinkedHashMap<String, Object>(16);
		state.put(UserAccountDBFields.ID, getId());
		state.put(UserAccountDBFields.CREATED_ON, getCreatedOn());
		state.put(UserAccountDBFields.MODIFIED_ON, getModifiedOn());
		if(!CollectionUtils.isEmpty(getDetails()))
			state.put(UserAccountDBFields.DETAILS, getDetails());
		return state;
	}
	
}
