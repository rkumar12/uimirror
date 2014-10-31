package com.uimirror.location.core.components;


/**
 * {@code LocationType} is a marker interface to define the type of a location on a map.
 */
public enum LocationType {

    /**
     * Indicates that the location information is accurate down to street address precision.
     */
    ROOFTOP("ROOFTOP"),

    /**
     * Indicates that the location information reflects an approximation (usually on a road)
     * interpolated between two precise points (such as intersections). Interpolated locations are generally used
     * when rooftop geocodes are unavailable for a street address.
     */
    RANGE_INTERPOLATED("RANGE_INTERPOLATED"),

    /**
     * Indicates that the location is the geometric center of a polyline (for example, a street) or
     * a polygon (region).
     */
    GEOMETRIC_CENTER("GEOMETRIC_CENTER"),

    /**
     * Indicates that the location information is approximate.
     */
    APPROXIMATE("APPROXIMATE");
    
    private final String type;
	
	private LocationType(String type) {
		this.type = type;
	}

	public String getLocationType() {
		return type;
	}

	@Override
    public String toString() {
    	return this.getLocationType();
    } 
    
    public static LocationType getEnum(String type) {
    	if(type == null)
    		throw new IllegalArgumentException("Location type can't be empty");
    	for(LocationType v : values())
    		if(type.equalsIgnoreCase(v.getLocationType())) return v;
    	return null;
    }
}

