package com.uimirror.location.coordinate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.uimirror.reach.core.coordinate.CardinalDirection;
import com.uimirror.reach.core.coordinate.GeographicCoordinateType;

public class CardinalDirectionTest {

    @Test
    public void shouldHaveCorrectCoordinateType() {
        assertThat(CardinalDirection.NORTH.getCoordinateType(), is(GeographicCoordinateType.LATITUDE));
        assertThat(CardinalDirection.SOUTH.getCoordinateType(), is(GeographicCoordinateType.LATITUDE));
        assertThat(CardinalDirection.EAST.getCoordinateType(), is(GeographicCoordinateType.LONGITUDE));
        assertThat(CardinalDirection.WEST.getCoordinateType(), is(GeographicCoordinateType.LONGITUDE));
    }
}
