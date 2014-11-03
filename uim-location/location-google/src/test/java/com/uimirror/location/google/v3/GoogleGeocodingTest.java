package com.uimirror.location.google.v3;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.uimirror.location.core.GeocodeResponse;
import com.uimirror.location.core.components.AddressComponent;
import com.uimirror.location.core.components.AddressComponentType;
import com.uimirror.location.core.components.GeocodeStatus;
import com.uimirror.location.core.components.GeocodedAddress;
import com.uimirror.location.core.components.GeographicLocation;

public class GoogleGeocodingTest {

    private static final String GOOGLE_MAPS_API_PREMIER_CLIENT_ID = "my-client-id";
    private static final String GOOGLE_MAPS_API_PREMIER_KEY = "my-premier-key";

    @Before
    public void setUp() throws Exception {
        Thread.sleep(500); // Sleep a bit so geocoding will not get rejected
    }

    @Test
    public void shouldGeocodeAddressUsingTheStandardGoogleGeocodingService() throws Exception {
        GoogleGeocoder subject = GoogleGeocoderFactory.createDefaultGoogleGeocoder();
        GeocodeResponse geocodeResponse = subject.geocodeAddress("1600 Amphitheatre Pkwy, Mountain View, CA 94043, USA");

        assertThat(geocodeResponse.getGeocodeStatus(), is(GeocodeStatus.OK));
    }

    @Ignore("The client ID and the premier key are invalid")
    @Test
    public void shouldGeocodeAddressUsingThePremierGoogleGeocodingService() {
        GoogleGeocoder subject = GoogleGeocoderFactory.createPremierGoogleGeocoder(GOOGLE_MAPS_API_PREMIER_CLIENT_ID, GOOGLE_MAPS_API_PREMIER_KEY);
        GeocodeResponse geocodeResponse = subject.geocodeAddress("1600 Amphitheatre Pkwy, Mountain View, CA 94043, USA");

        assertThat(geocodeResponse.getGeocodeStatus(), is(GeocodeStatus.OK));
    }

    @Test
    public void shouldLookupAddressUsingTheStandardGoogleGeocodingService() {
        GoogleGeocoder subject = GoogleGeocoderFactory.createDefaultGoogleGeocoder();
        
        GeocodeResponse geocodeResponse = subject.lookupAddress(51.5033630, -0.1276250);
        List<GeocodedAddress> geocodedAddresses = geocodeResponse.getGeocodedAddresses();
		String latLongAddress = null;
		String postalCode = null;
		String locationAddress = null;
		String countryShortName = null;
		String countryName = null;
		String stateShortName = null;
		String stateName = null;
		String cityShortName = null;
		String cityName = null;
		String localityShortName = null;
		String localityName = null;
		int start = 0;
		geocodedAddresses.get(0).getFormattedAddress();
		String latLong = geocodeResponse.getQueryString();
		String[] latLongCord = latLong.split(",");
		System.out.println(Double.parseDouble(latLongCord[0]));
		System.out.println(Double.parseDouble(latLongCord[1]));
		if(geocodedAddresses.get(0) != null && geocodedAddresses.get(0).getFormattedAddress() != null){
				latLongAddress = geocodedAddresses.get(0).getFormattedAddress();
				System.out.println("latLongAddress "+latLongAddress);
		}
		for(GeocodedAddress address : geocodedAddresses){
			Iterator<AddressComponent> addressComponentIterator = address.addressComponentIterator();
			while(addressComponentIterator.hasNext()){
				AddressComponent next = addressComponentIterator.next();
				Set<AddressComponentType> addressComponentTypes = next.getAddressComponentTypes();
				if(postalCode == null && addressComponentTypes.contains(AddressComponentType.POSTAL_CODE)){
					postalCode = next.getLongName();
					System.out.println("postalCode "+postalCode);
				}
				if(locationAddress == null &&addressComponentTypes.contains(AddressComponentType.ROUTE)){
					locationAddress = next.getLongName();
					System.out.println("locationAddress "+locationAddress);
				}
				if(countryName == null && addressComponentTypes.contains(AddressComponentType.COUNTRY)){
					countryName = next.getLongName();
					countryShortName = next.getShortName();
					System.out.println("countryName "+countryName);
				}
				if(stateName == null && addressComponentTypes.contains(AddressComponentType.ADMINISTRATIVE_AREA_LEVEL_1)){
					stateName = next.getLongName();
					stateShortName = next.getShortName();
					System.out.println("stateName "+stateName);
				}
				if(addressComponentTypes.contains(AddressComponentType.ADMINISTRATIVE_AREA_LEVEL_2) 
						|| addressComponentTypes.contains(AddressComponentType.LOCALITY)){
					if((start == 0 && cityName != null && cityName.length() > next.getLongName().length()) || cityName == null){
						cityName = next.getLongName();
						cityShortName = next.getShortName();
						System.out.println("cityName "+cityName);
					}
				}
				if(addressComponentTypes.contains(AddressComponentType.SUBLOCALITY)){
					if((start == 0 && localityName != null && localityName.length() > next.getLongName().length()) || localityName == null){
						localityName = next.getLongName();
						localityShortName = next.getShortName();
						System.out.println("localityName "+localityName);
					}
				}
			}
			start++;
		}
        assertThat(geocodeResponse.getGeocodeStatus(), is(GeocodeStatus.OK));
    }

    @Ignore("The client ID and the premier key are invalid")
    @Test
    public void shouldLookupAddressUsingThePremierGoogleGeocodingService() {
        GoogleGeocoder subject = GoogleGeocoderFactory.createPremierGoogleGeocoder(GOOGLE_MAPS_API_PREMIER_CLIENT_ID, GOOGLE_MAPS_API_PREMIER_KEY);
        GeocodeResponse geocodeResponse = subject.lookupAddress(37.4213068, -122.08529);

        assertThat(geocodeResponse.getGeocodeStatus(), is(GeocodeStatus.OK));
    }

    @Test
    public void shouldGeocodeFullySpecifiedQueryUsingTheStandardGoogleGeocodingService() {
        GoogleGeocoder subject = GoogleGeocoderFactory.createDefaultGoogleGeocoder();
        GoogleGeocodeRequest geocodeRequest = subject.newGeocodeRequestBuilder("1600 Amphitheatre Pkwy, Mountain View, CA 94043, USA")
                .inLanguage("en")
                .withRegionBiasing("us")
                .withViewportBiasing(GeographicLocation.fromValues(36.4213068, -123.08529), GeographicLocation.fromValues(38.4213068, -121.08529))
                .build();
        GeocodeResponse geocodeResponse = subject.geocode(geocodeRequest);
        assertThat(geocodeResponse.getGeocodeStatus(), is(GeocodeStatus.OK));
    }

    @Ignore("The client ID and the premier key are invalid")
    @Test
    public void shouldGeocodeFullySpecifiedQueryUsingThePremierGoogleGeocodingService() {
        GoogleGeocoder subject = GoogleGeocoderFactory.createPremierGoogleGeocoder(GOOGLE_MAPS_API_PREMIER_CLIENT_ID, GOOGLE_MAPS_API_PREMIER_KEY);
        GoogleGeocodeRequest geocodeRequest = subject.newGeocodeRequestBuilder("1600 Amphitheatre Pkwy, Mountain View, CA 94043, USA")
                .inLanguage("en")
                .withRegionBiasing("us")
                .withViewportBiasing(GeographicLocation.fromValues(36.4213068, -123.08529), GeographicLocation.fromValues(38.4213068, -121.08529))
                .build();
        GeocodeResponse geocodeResponse = subject.geocode(geocodeRequest);

        assertThat(geocodeResponse.getGeocodeStatus(), is(GeocodeStatus.OK));
    }
}
