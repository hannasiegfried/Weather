package com.oocode;

import org.junit.Test;

import java.time.DayOfWeek;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class CachingForecasterTest {
    @Test
    public void delegatesIfForecastNotSeenBefore() {
        Forecaster forecaster = mock(Forecaster.class);
        Forecast expected = new Forecast(1, 2, "Cold");
        given(forecaster.forecastFor(DayOfWeek.FRIDAY, "Somewhere"))
                .willReturn(expected);

        CachingForecaster underTest = new CachingForecaster(forecaster);

        Forecast actual = underTest.forecastFor(DayOfWeek.FRIDAY, "Somewhere");
        assertThat(actual, equalTo(expected));
    }
}