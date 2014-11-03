package com.uimirror.location.core.components;

/**
 * {@code AddressComponentType} contains enumerated values to indicate the type of
 * a particular address component.
 */
public enum AddressComponentType {

    /**
     * Indicates a first-order civil entity below the country level. For example within
     * the United States, these administrative levels are states.
     */
    ADMINISTRATIVE_AREA_LEVEL_1("ADMINISTRATIVE_AREA_LEVEL_1"),

    /**
     * Indicates a second-order civil entity below the country level. For example within
     * the United States, these administrative levels are counties.
     */
    ADMINISTRATIVE_AREA_LEVEL_2("ADMINISTRATIVE_AREA_LEVEL_2"),

    /**
     * Indicates a third-order civil entity below the country level. This type indicates
     * a minor civil division.
     */
    ADMINISTRATIVE_AREA_LEVEL_3("ADMINISTRATIVE_AREA_LEVEL_3"),

    /**
     * Indicates an airport.
     */
    AIRPORT("AIRPORT"),

    /**
     * Indicates a bus station.
     */
    BUS_STATION("BUS_STATION"),

    /**
     * Indicates a church.
     */
    CHURCH("CHURCH"),

    /**
     * Indicates a city hall.
     */
    CITY_HALL("CITY_HALL"),

    /**
     * Indicates a commonly-used alternative name for the entity.
     */
    COLLOQUIAL_AREA("COLLOQUIAL_AREA"),

    /**
     * Indicates the national political entity, and is typically the highest order type returned
     * by a geocoder service.
     */
    COUNTRY("COUNTRY"),

    /**
     * Indicates a courthouse.
     */
    COURTHOUSE("COURTHOUSE"),

    /**
     * Indicates a named establishment.
     */
    ESTABLISHMENT("ESTABLISHMENT"),

    /**
     * Indicates the floor of a building address.
     */
    FLOOR("FLOOR"),

    /**
     * Indicates a healthcare location.
     */
    HEALTH("HEALTH"),

    /**
     * Indicates a hospital.
     */
    HOSPITAL("HOSPITAL"),

    /**
     * Indicates a major intersection, usually of two major roads.
     */
    INTERSECTION("INTERSECTION"),

    /**
     * Indicates a library.
     */
    LIBRARY("LIBRARY"),

    /**
     * Indicates an incorporated city or town political entity.
     */
    LOCALITY("LOCALITY"),

    /**
     * Indicates a local government office.
     */
    LOCAL_GOVERNMENT_OFFICE("LOCAL_GOVERNMENT_OFFICE"),

    /**
     * Indicates a museum.
     */
    MUSEUM("MUSEUM"),

    /**
     * Indicates a prominent natural feature.
     */
    NATURAL_FEATURE("NATURAL_FEATURE"),

    /**
     * Indicates a named neighborhood.
     */
    NEIGHBORHOOD("NEIGHBORHOOD"),

    /**
     * Indicates a named park.
     */
    PARK("PARK"),

    /**
     * Indicates a place of worship.
     */
    PLACE_OF_WORSHIP("PLACE_OF_WORSHIP"),

    /**
     * Indicates a named point of interest. Typically, these "POI"s are prominent local
     * entities that don't easily fit in another category such as "Empire State Building"
     * or "Statue of Liberty."
     */
    POINT_OF_INTEREST("POINT_OF_INTEREST"),

    /**
     * Indicates a political entity. Usually, this type indicates a polygon of some civil
     * administration.
     */
    POLITICAL("POLITICAL"),

    /**
     * Indicates a specific postal box.
     */
    POST_BOX("POST_BOX"),

    /**
     * Indicates a postal code.
     */
    POSTAL_CODE("POSTAL_CODE"),

    /**
     * Indicates a postal code prefix.
     */
    POSTAL_CODE_PREFIX("POSTAL_CODE_PREFIX"),

    /**
     * Indicates a postal town. Normally its main function is to distinguish between locality
     * or street names in addresses not including a postcode.
     */
    POSTAL_TOWN("POSTAL_TOWN"),

    /**
     * Indicates a post office.
     */
    POST_OFFICE("POST_OFFICE"),

    /**
     * Indicates a named location, usually a building or collection of buildings with a common name.
     */
    PREMISE("PREMISE"),

    /**
     * Indicates the room of a building address.
     */
    ROOM("ROOM"),

    /**
     * Indicates a named route (such as "US 101").
     */
    ROUTE("ROUTE"),

    /**
     * Indicates a school.
     */
    SCHOOL("SCHOOL"),

    /**
     * Indicates a precise street address.
     */
    STREET_ADDRESS("STREET_ADDRESS"),

    /**
     * Indicates the precise street number.
     */
    STREET_NUMBER("STREET_NUMBER"),

    /**
     * Indicates an first-order civil entity below a locality.
     */
    SUBLOCALITY("SUBLOCALITY"),
    /**
     * Indicates an second-order civil entity below a locality.
     */
    SUBLOCALITY_LEVEL_1("SUBLOCALITY_LEVEL_1"),
    /**
     * Indicates an third-order civil entity below a locality.
     */
    SUBLOCALITY_LEVEL_2("SUBLOCALITY_LEVEL_2"),
    /**
     * Indicates an fourth-order civil entity below a locality.
     */
    SUBLOCALITY_LEVEL_3("SUBLOCALITY_LEVEL_3"),

    /**
     * Indicates a first-order entity below a named location, usually a singular building within
     * a collection of buildings with a common name.
     */
    SUBPREMISE("SUBPREMISE"),

    /**
     * Indicates a subway station.
     */
    SUBWAY_STATION("SUBWAY_STATION"),

    /**
     * Indicates a train station.
     */
    TRAIN_STATION("TRAIN_STATION"),

    /**
     * Indicates a transit station.
     */
    TRANSIT_STATION("TRANSIT_STATION"),

    /**
     * Inidicates a university.
     */
    UNIVERSITY("UNIVERSITY");
    
    private final String type;
    
    private AddressComponentType(String type) {
		this.type = type;
	}

	public String getLocType() {
		return type;
	}

	@Override
    public String toString() {
    	return this.getLocType();
    } 
    
    public static AddressComponentType getEnum(String type) {
    	if(type == null)
    		throw new IllegalArgumentException("Location type can't be empty");
    	for(AddressComponentType v : values())
    		if(type.equalsIgnoreCase(v.getLocType())) return v;
    	return null;
    }
}
