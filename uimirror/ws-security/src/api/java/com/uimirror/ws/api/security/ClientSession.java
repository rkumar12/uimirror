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
package com.uimirror.ws.api.security;

import java.io.Serializable;

/**
 * <p>This will holds the client session details</p>
 * @author Jayaram
 */
public class ClientSession implements Serializable{

	private static final long serialVersionUID = -256554046885843048L;
	private final Client client;
	private final ClientAccessInfo clientAccessInfo;

	/**
	 * @param client
	 * @param clientAccessInfo
	 */
	public ClientSession(Client client, ClientAccessInfo clientAccessInfo) {
		super();
		this.client = client;
		this.clientAccessInfo = clientAccessInfo;
	}

	/**
	 * @return the client
	 */
	public Client getClient() {
		return client;
	}

	/**
	 * @return the clientAccessInfo
	 */
	public ClientAccessInfo getClientAccessInfo() {
		return clientAccessInfo;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((client == null) ? 0 : client.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClientSession other = (ClientSession) obj;
		if (client == null) {
			if (other.client != null)
				return false;
		} else if (!client.equals(other.client))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ClientSession [client=" + client + ", clientAccessInfo="
				+ clientAccessInfo + "]";
	}
	
}
