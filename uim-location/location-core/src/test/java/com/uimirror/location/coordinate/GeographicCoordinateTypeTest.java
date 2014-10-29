package com.uimirror.location.coordinate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

import com.uimirror.location.core.coordinate.CardinalDirection;
import com.uimirror.location.core.coordinate.GeographicCoordinateType;

public class GeographicCoordinateTypeTest {

    @Test
    public void shouldValidateBigDecimalLatitude() {
        assertTrue(GeographicCoordinateType.LATITUDE.isValidValue(new BigDecimal(-90)));
        assertTrue(GeographicCoordinateType.LATITUDE.isValidValue(new BigDecimal(90)));
        assertFalse(GeographicCoordinateType.LATITUDE.isValidValue(new BigDecimal(-90.1)));
        assertFalse(GeographicCoordinateType.LATITUDE.isValidValue(new BigDecimal(90.1)));
    }

    @Test
    public void shouldValidateBigDecimalLongitude() {
        assertTrue(GeographicCoordinateType.LONGITUDE.isValidValue(new BigDecimal(-180)));
        assertTrue(GeographicCoordinateType.LONGITUDE.isValidValue(new BigDecimal(180)));
        assertFalse(GeographicCoordinateType.LONGITUDE.isValidValue(new BigDecimal(-180.1)));
        assertFalse(GeographicCoordinateType.LONGITUDE.isValidValue(new BigDecimal(180.1)));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionIfBigDecimalCoordinateIsNull() {
    	GeographicCoordinateType.LATITUDE.isValidValue(null);
    }

    @Test
    public void shouldValidateDoubleLatitude() {
        assertTrue(GeographicCoordinateType.LATITUDE.isValidValue(-90.0));
        assertTrue(GeographicCoordinateType.LATITUDE.isValidValue(90.0));
        assertFalse(GeographicCoordinateType.LATITUDE.isValidValue(-90.1));
        assertFalse(GeographicCoordinateType.LATITUDE.isValidValue(90.1));
    }

    @Test
    public void shouldValidateDoubleLongitude() {
        assertTrue(GeographicCoordinateType.LONGITUDE.isValidValue(-180.0));
        assertTrue(GeographicCoordinateType.LONGITUDE.isValidValue(180.0));
        assertFalse(GeographicCoordinateType.LONGITUDE.isValidValue(-180.1));
        assertFalse(GeographicCoordinateType.LONGITUDE.isValidValue(180.1));
    }

    @Test
    public void shouldValidateAngleLatitude() {
        assertTrue(GeographicCoordinateType.LATITUDE.isValidAngle(90, 0, 0, 0, CardinalDirection.NORTH));
        assertTrue(GeographicCoordinateType.LATITUDE.isValidAngle(90, 0, 0, 0, CardinalDirection.SOUTH));
        assertTrue(GeographicCoordinateType.LATITUDE.isValidAngle(0, 0, 0, 0, CardinalDirection.ZERO_LATITUDE_DEGREE));
        assertFalse(GeographicCoordinateType.LATITUDE.isValidAngle(90, 0, 0, 1, CardinalDirection.NORTH));
        assertFalse(GeographicCoordinateType.LATITUDE.isValidAngle(90, 0, 0, 1, CardinalDirection.SOUTH));
        assertFalse(GeographicCoordinateType.LATITUDE.isValidAngle(0, 0, 0, 1, CardinalDirection.ZERO_LATITUDE_DEGREE));
        assertFalse(GeographicCoordinateType.LATITUDE.isValidAngle(-90, 0, 0, 0, CardinalDirection.NORTH));
        assertFalse(GeographicCoordinateType.LATITUDE.isValidAngle(90, 0, 0, 0, CardinalDirection.EAST));
    }

    @Test
    public void shouldValidateAngleLongitude() {
        assertTrue(GeographicCoordinateType.LONGITUDE.isValidAngle(180, 0, 0, 0, CardinalDirection.EAST));
        assertTrue(GeographicCoordinateType.LONGITUDE.isValidAngle(180, 0, 0, 0, CardinalDirection.WEST));
        assertTrue(GeographicCoordinateType.LONGITUDE.isValidAngle(0, 0, 0, 0, CardinalDirection.ZERO_LONGITUDE_DEGREE));
        assertFalse(GeographicCoordinateType.LONGITUDE.isValidAngle(180, 0, 0, 1, CardinalDirection.EAST));
        assertFalse(GeographicCoordinateType.LONGITUDE.isValidAngle(180, 0, 0, 1, CardinalDirection.WEST));
        assertFalse(GeographicCoordinateType.LONGITUDE.isValidAngle(0, 0, 0, 1, CardinalDirection.ZERO_LONGITUDE_DEGREE));
        assertFalse(GeographicCoordinateType.LONGITUDE.isValidAngle(-180, 0, 0, 0, CardinalDirection.EAST));
        assertFalse(GeographicCoordinateType.LONGITUDE.isValidAngle(180, 0, 0, 0, CardinalDirection.NORTH));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionIfCardinalDirectionIsNull() {
    	GeographicCoordinateType.LATITUDE.isValidAngle(0, 0, 0, 0, null);
    }
}
