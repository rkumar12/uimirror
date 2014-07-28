/*******************************************************************************
 * Copyright (c) 2014 Uimirror.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Uimirror license
 * which accompanies this distribution, and is available at
 * http://www.uimirror.com/legal
 *
 * Contributors:
 * Uimirror Team
 *******************************************************************************/
package com.uimirror.ws.api.security.provider.client;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.DuplicateKeyException;
import com.mongodb.WriteResult;
import com.uimirror.ws.api.security.base.SecurityFieldConstants;

/**
 * <p>Basic MONGO implementation of the client details service.</p>
 * @author Jayaram
 */
public class MongoClientDetailsService implements ClientDetailsService, ClientRegistrationService{
	
	protected static final Logger LOG = LoggerFactory.getLogger(MongoClientDetailsService.class);

	private PasswordEncoder passwordEncoder = NoOpPasswordEncoder.getInstance();
	
	private final DBCollection uimClientCollection;
	private static final DBObject PROJECT_FIELDS = new BasicDBObject(15); 

	public MongoClientDetailsService(DBCollection clientCollection){
		Assert.notNull(clientCollection, "Data Base Client Collection Can't be null");
		this.uimClientCollection = clientCollection;
		initialize();
	}
	
	/**
	 * @param passwordEncoder the password encoder to set
	 */
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	private static void initialize(){
		PROJECT_FIELDS.put(SecurityFieldConstants._ID, 1);
		PROJECT_FIELDS.put(SecurityFieldConstants._CLIENT_SECRET, 1);
		PROJECT_FIELDS.put(SecurityFieldConstants._CLIENT_RESOURCE_IDS, 1);
		PROJECT_FIELDS.put(SecurityFieldConstants._CLIENT_SCOPE, 1);
		PROJECT_FIELDS.put(SecurityFieldConstants._CLIENT_AUTHORIZED_GRANT_TYPE, 1);
		PROJECT_FIELDS.put(SecurityFieldConstants._CLIENT_WEB_REDIRECT_URI, 1);
		PROJECT_FIELDS.put(SecurityFieldConstants._CLIENT_AUTHORITIES, 1);
		PROJECT_FIELDS.put(SecurityFieldConstants._CLIENT_ACCESS_TOKEN_VALIDITY, 1);
		PROJECT_FIELDS.put(SecurityFieldConstants._CLIENT_REFRESH_TOKEN_VALIDITY, 1);
		PROJECT_FIELDS.put(SecurityFieldConstants._CLIENT_ADDITIONAL_INFORMATION, 1);
		PROJECT_FIELDS.put(SecurityFieldConstants._CLIENT_AUTO_APPROVAL_SCOPES, 1);
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.ClientDetailsService#loadClientByClientId(java.lang.String)
	 */
	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		Assert.hasText(clientId, "Client Id Can't be Empty");
		DBObject query = new BasicDBObject(SecurityFieldConstants._ID, new ObjectId(clientId));
		DBObject result = uimClientCollection.findOne(query, PROJECT_FIELDS);
		if(result == null){
			throw new NoSuchClientException("No client with requested id: " + clientId);
		}
		return buildClientDetails(result);
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.ClientRegistrationService#addClientDetails(org.springframework.security.oauth2.provider.ClientDetails)
	 */
	@Override
	public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {
		Assert.notNull(clientDetails, "Client Details Object can't be empty for creating");
		try{
			uimClientCollection.save(getFieldsToCreate(clientDetails));
		}catch(DuplicateKeyException me){
			throw new ClientAlreadyExistsException("Client already exists: " + clientDetails.getClientId(), me);
		}
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.ClientRegistrationService#updateClientDetails(org.springframework.security.oauth2.provider.ClientDetails)
	 */
	@Override
	public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {
		Assert.notNull(clientDetails, "Client Details Object can't be empty for updateing");
		DBObject query = new BasicDBObject(SecurityFieldConstants._ID, new ObjectId(clientDetails.getClientId()));
		WriteResult res = uimClientCollection.update(query, getFieldsToUpdate(clientDetails));
		if(res == null || !res.isUpdateOfExisting()){
			throw new NoSuchClientException("No client found with id = " + clientDetails.getClientId());
		}
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.ClientRegistrationService#updateClientSecret(java.lang.String, java.lang.String)
	 */
	@Override
	public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {
		DBObject query = new BasicDBObject(SecurityFieldConstants._ID, clientId);
		DBObject update = new BasicDBObject(SecurityFieldConstants._SET, new BasicDBObject(SecurityFieldConstants._CLIENT_SECRET, passwordEncoder.encode(secret)));
		
		WriteResult res = uimClientCollection.update(query, update);
		if(res == null || !res.isUpdateOfExisting()){
			throw new NoSuchClientException("No client found with id = " + clientId);
		}
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.ClientRegistrationService#removeClientDetails(java.lang.String)
	 */
	@Override
	public void removeClientDetails(String clientId) throws NoSuchClientException {
		DBObject query = new BasicDBObject(SecurityFieldConstants._ID, clientId);
		WriteResult rs = uimClientCollection.remove(query);
		if(rs == null || !rs.isUpdateOfExisting()){
			throw new NoSuchClientException("No client found with id = " + clientId);
		}
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.ClientRegistrationService#listClientDetails()
	 */
	@Override
	public List<ClientDetails> listClientDetails() {
		DBCursor results = uimClientCollection.find();
		return buildClientDetailsFromCursor(results);
	}
	/**
	 * <p>This will build the <code>{@link DBObject}</code> to create a document</p>
	 * @param clientDetails
	 * @return
	 */
	private DBObject getFieldsToCreate(ClientDetails clientDetails){
		DBObject createDoc = convertToFields(clientDetails);
		createDoc.put(SecurityFieldConstants._ID, clientDetails.getClientId());
		if(StringUtils.hasText(clientDetails.getClientSecret())){
			createDoc.put(SecurityFieldConstants._CLIENT_SECRET, passwordEncoder.encode(clientDetails.getClientSecret()));
		}
		return createDoc;
	}
	/**
	 * <p>This will build the <code>{@link DBObject}</code> to update a document</p>
	 * @param clientDetails
	 * @return
	 */
	private DBObject getFieldsToUpdate(ClientDetails clientDetails){
		return new BasicDBObject(SecurityFieldConstants._SET, convertToFields(clientDetails));
	}
	/**
	 * <p>This will build the fields to update in-terms of a map</p>
	 * @param clientDetails
	 * @return
	 */
	private DBObject convertToFields(ClientDetails clientDetails){
		DBObject updateFields = new BasicDBObject(15);
		//Resource Id Not empty then populate
		if(!CollectionUtils.isEmpty(clientDetails.getResourceIds())){
			updateFields.put(SecurityFieldConstants._CLIENT_RESOURCE_IDS, clientDetails.getResourceIds());
		}
		//Scope not empty then populate
		if(!CollectionUtils.isEmpty(clientDetails.getScope())){
			updateFields.put(SecurityFieldConstants._CLIENT_SCOPE, clientDetails.getScope());
		}
		//Grant Type Not empty then populate
		if(!CollectionUtils.isEmpty(clientDetails.getAuthorizedGrantTypes())){
			updateFields.put(SecurityFieldConstants._CLIENT_AUTHORIZED_GRANT_TYPE, clientDetails.getAuthorizedGrantTypes());
		}
		//Redirect URI of the client is not empty then populate
		if(!CollectionUtils.isEmpty(clientDetails.getRegisteredRedirectUri())){
			updateFields.put(SecurityFieldConstants._CLIENT_WEB_REDIRECT_URI, clientDetails.getRegisteredRedirectUri());
		}
		//Authorities Not empty then populate
		if(!CollectionUtils.isEmpty(clientDetails.getAuthorities())){
			updateFields.put(SecurityFieldConstants._CLIENT_AUTHORITIES, clientDetails.getAuthorities());
		}
		//Access Token Validity more than default value
		if(clientDetails.getAccessTokenValiditySeconds() > 0){
			updateFields.put(SecurityFieldConstants._CLIENT_ACCESS_TOKEN_VALIDITY, clientDetails.getAccessTokenValiditySeconds());
		}
		//Refresh Token Validity more than default value
		if(clientDetails.getRefreshTokenValiditySeconds() > 0){
			updateFields.put(SecurityFieldConstants._CLIENT_REFRESH_TOKEN_VALIDITY, clientDetails.getRefreshTokenValiditySeconds());
		}
		//Any additional Information associated with the client
		if(!CollectionUtils.isEmpty(clientDetails.getAdditionalInformation())){
			updateFields.put(SecurityFieldConstants._CLIENT_ADDITIONAL_INFORMATION, clientDetails.getAdditionalInformation());
		}
		//Any Auto Approval associated for client
		updateFields.put(SecurityFieldConstants._CLIENT_AUTO_APPROVAL_SCOPES, getAutoApproveScopes(clientDetails));
		
		return updateFields; 
	}
	
	/**
	 * <p>This will deserilze the result set received from the <code>{@link #loadClientByClientId(String)}</code></p>
	 * @param result
	 * @return instance of <code>{@link ClientDetails}</code>
	 */
	@SuppressWarnings("unchecked")
	private ClientDetails buildClientDetails(DBObject result){
		
		String clientId = (String)result.get(SecurityFieldConstants._ID);
		String resourceIds = StringUtils.collectionToCommaDelimitedString((Set<String>)result.get(SecurityFieldConstants._CLIENT_RESOURCE_IDS));
		String scopes = StringUtils.collectionToCommaDelimitedString((Set<String>)result.get(SecurityFieldConstants._CLIENT_SCOPE));
		String grantedAuthoritiesType = StringUtils.collectionToCommaDelimitedString((Set<String>)result.get(SecurityFieldConstants._CLIENT_AUTHORIZED_GRANT_TYPE));
		String authorities = StringUtils.collectionToCommaDelimitedString((List<Object>)result.get(SecurityFieldConstants._CLIENT_AUTHORITIES));
		String redirectURI = StringUtils.collectionToCommaDelimitedString((Set<String>)result.get(SecurityFieldConstants._CLIENT_WEB_REDIRECT_URI));
		
		//Build a basic client details object based on the result get from the database
		BaseClientDetails details = new BaseClientDetails(clientId, resourceIds, scopes, grantedAuthoritiesType, authorities, redirectURI);
		
		details.setClientSecret((String)result.get(SecurityFieldConstants._CLIENT_SECRET));
		
		if (result.get(SecurityFieldConstants._CLIENT_ACCESS_TOKEN_VALIDITY) != null) {
			details.setAccessTokenValiditySeconds((int)result.get(SecurityFieldConstants._CLIENT_ACCESS_TOKEN_VALIDITY));
		}
		if (result.get(SecurityFieldConstants._CLIENT_REFRESH_TOKEN_VALIDITY) != null) {
			details.setRefreshTokenValiditySeconds((int)result.get(SecurityFieldConstants._CLIENT_REFRESH_TOKEN_VALIDITY));
		}
		Object additionalInfo = result.get(SecurityFieldConstants._CLIENT_ADDITIONAL_INFORMATION);
		if (additionalInfo != null) {
			Map<String, Object> additionalInformation = ((BasicDBObject)additionalInfo).toMap();
			details.setAdditionalInformation(additionalInformation);
		}
		Object autoApproveScopes = result.get(SecurityFieldConstants._CLIENT_AUTO_APPROVAL_SCOPES);
		if(autoApproveScopes != null){
			details.setAutoApproveScopes((Set<String>)autoApproveScopes);
		}
		return details;
	}
	
	/**
	 * <p>This will build the list of client details from the cursor.</p>
	 * @param cursor
	 * @return <code>{@link List} of {@link ClientDetails}</code>
	 */
	private List<ClientDetails> buildClientDetailsFromCursor(DBCursor cursor){
		List<ClientDetails> clientDetails = new ArrayList<ClientDetails>(cursor.size());
		cursor.batchSize(20);
		cursor.forEach((clinet) -> clientDetails.add(buildClientDetails(clinet)));
		return clientDetails;
	}

	/**
	 * <p>This will do the Auto Approve for the client.</p>
	 * @param clientDetails
	 * @return
	 */
	private Set<String> getAutoApproveScopes(ClientDetails clientDetails) {
		Set<String> scopes = new HashSet<String>();
		if (clientDetails.isAutoApprove("true")) {
			scopes.add("true");
			return scopes; // all scopes autoapproved
		}
		for (String scope : clientDetails.getScope()) {
			if (clientDetails.isAutoApprove(scope)) {
				scopes.add(scope);
			}
		}
		return scopes;
	}
}
