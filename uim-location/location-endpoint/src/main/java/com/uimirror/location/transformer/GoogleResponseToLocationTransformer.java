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
package com.uimirror.location.transformer;

import java.util.Iterator;
import java.util.Set;

import org.springframework.util.StringUtils;

import com.uimirror.core.GeoLongLat;
import com.uimirror.core.service.TransformerService;
import com.uimirror.location.City;
import com.uimirror.location.Country;
import com.uimirror.location.DefaultLocation;
import com.uimirror.location.DefaultLocation.LocationBuilder;
import com.uimirror.location.Locality;
import com.uimirror.location.State;
import com.uimirror.location.core.GeocodeResponse;
import com.uimirror.location.core.components.AddressComponent;
import com.uimirror.location.core.components.AddressComponentType;
import com.uimirror.location.core.components.GeocodedAddress;

/**
 * This will transform the {@link GeocodeResponse} to {@link DefaultLocation}
 * @author Jay
 */
public class GoogleResponseToLocationTransformer implements TransformerService<GeocodeResponse, DefaultLocation>{

	/* (non-Javadoc)
	 * @see com.uimirror.core.service.TransformerService#transform(java.lang.Object)
	 */
	@Override
	public DefaultLocation transform(GeocodeResponse response) {
		Location loc = createLocation(response);
		return loc.getLocation();
	}
	
	/**
	 * Populate the inner location from the response
	 * @param response
	 * @return
	 */
	private Location createLocation(GeocodeResponse response){
		//get the first address which has every thing
		GeocodedAddress geocodedAddresses = response.getGeocodedAddresses().get(0);
		Iterator<AddressComponentType> addressTypeIterator = geocodedAddresses.addressTypeIterator();
		Location loc = new Location();
		while(addressTypeIterator.hasNext()){
			loc.type = addressTypeIterator.next();
			break;
		}
		loc.latLongAddress = geocodedAddresses.getFormattedAddress();
		String latLong = response.getQueryString();
		String[] latLongCord = latLong.split(",");
		loc.latitude = Double.parseDouble(latLongCord[0]);
		loc.longitude = Double.parseDouble(latLongCord[1]);
		Iterator<AddressComponent> addressComponentIterator = geocodedAddresses.addressComponentIterator();
		while(addressComponentIterator.hasNext()){
			AddressComponent next = addressComponentIterator.next();
			Set<AddressComponentType> addressComponentTypes = next.getAddressComponentTypes();
			if(loc.postalCode == null && addressComponentTypes.contains(AddressComponentType.POSTAL_CODE)){
				loc.postalCode = next.getLongName();
			}
			if(loc.locationAddress == null && addressComponentTypes.contains(AddressComponentType.ROUTE)){
				loc.locationAddress = next.getLongName();
			}
			if(loc.countryName == null && addressComponentTypes.contains(AddressComponentType.COUNTRY)){
				loc.countryName = next.getLongName();
				loc.countryShortName = next.getShortName();
			}
			if(loc.stateName == null && addressComponentTypes.contains(AddressComponentType.ADMINISTRATIVE_AREA_LEVEL_1)){
				loc.stateName = next.getLongName();
				loc.stateShortName = next.getShortName();
			}
			if(addressComponentTypes.contains(AddressComponentType.SUBLOCALITY)){
				if((loc.localityName != null && loc.localityName.length() > next.getLongName().length()) || loc.localityName == null){
					loc.localityName = next.getLongName();
					loc.localityShortName = next.getShortName();
				}
			}
			if(addressComponentTypes.contains(AddressComponentType.ADMINISTRATIVE_AREA_LEVEL_2) 
					|| addressComponentTypes.contains(AddressComponentType.LOCALITY)){
				if((loc.cityName != null && loc.cityName.length() > next.getLongName().length()) || loc.cityName == null){
					loc.cityName = next.getLongName();
					loc.cityShortName = next.getShortName();
				}
			}
		}
		return loc;
	}
	
	private static class Location{
		private String latLongAddress;
		private String postalCode;
		private String locationAddress;
		private String countryShortName;
		private String countryName;
		private String stateShortName;
		private String stateName;
		private String cityShortName;
		private String cityName;
		private String localityShortName;
		private String localityName;
		private double latitude;
		private double longitude;
		private AddressComponentType type;
		
		private Country getCountry(){
			if(StringUtils.hasText(countryName) || StringUtils.hasText(countryShortName)){
				return new Country.CountryBuilder(null).updateName(countryName).updateShortName(countryShortName).build();
			}
			return null;
		}
		private State getState(){
			if(StringUtils.hasText(stateName) || StringUtils.hasText(stateShortName)){
				return new State.StateBuilder(null).updateName(stateName).updateShortName(stateShortName).build();
			}
			return null;
		}
		private City getCity(){
			if(StringUtils.hasText(cityName) || StringUtils.hasText(cityShortName)){
				return new City.CityBuilder(null).updateName(cityName).updateShortName(cityShortName).build();
			}
			return null;
		}
		private Locality getLocality(){
			if(StringUtils.hasText(localityName) || StringUtils.hasText(localityShortName)){
				return new Locality.LocalityBuilder(null).updateName(localityName).updateShortName(localityShortName).build();
			}
			return null;
		}
		private GeoLongLat getGeoCord(){
			return new GeoLongLat.GeoLongLatBuilder(latLongAddress).updateLatitude(latitude).updateLongitude(longitude).build();
		}
		private String getPostalCode(){
			return postalCode;
		}
		private String getAddress(){
			return locationAddress;
		}
		
		/**
		 * Builds the {@link DefaultLocation} address componet from the given values
		 * @return
		 */
		private DefaultLocation getLocation(){
			LocationBuilder locationBuilder = new DefaultLocation.LocationBuilder(getGeoCord());
			locationBuilder.updateName(getAddress());
			Country country = getCountry();
			if(country != null)
				locationBuilder.updateCountry(country);
			State state = getState();
			if(state != null)
				locationBuilder.updateState(state);
			City city = getCity();
			if(city != null)
				locationBuilder.updateCity(city);
			Locality locality = getLocality();
			if(locality != null)
				locationBuilder.updateLocality(locality);
			if(StringUtils.hasText(getPostalCode()))
				locationBuilder.updatePin(getPostalCode());
			
			locationBuilder.updateLocationType(type);
			
			return locationBuilder.build();
		}
	}

}
