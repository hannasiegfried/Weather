package com.oocode;

import org.junit.jupiter.api.Test;

import static java.time.DayOfWeek.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class CachingForecasterTest {
    @Test
    public void delegatesForecastingForNewForecast() {
        var delegate = mock(Forecaster.class);
        Forecast expectedForecast = new Forecast(1, 2, "cold");
        given(delegate.forecastFor("Oxford", FRIDAY)).willReturn(expectedForecast);

        var underTest = new CachingForecaster(delegate);

        var forecast = underTest.forecastFor("Oxford", FRIDAY);

        verify(delegate).forecastFor("Oxford", FRIDAY); // This is arguable
        assertThat(forecast, equalTo(expectedForecast));
    }

    @Test
    public void cachesForecasts() {
        var delegate = mock(Forecaster.class);
        Forecast expectedForecast = new Forecast(1, 2, "cold");
        given(delegate.forecastFor("Oxford", FRIDAY)).willReturn(expectedForecast);

        var underTest = new CachingForecaster(delegate);

        var forecast = underTest.forecastFor("Oxford", FRIDAY);
        underTest.forecastFor("Oxford", FRIDAY);

        verify(delegate, times(1)).forecastFor("Oxford", FRIDAY); // This is arguable
        assertThat(forecast, equalTo(expectedForecast));
    }

    @Test
    public void cachesForecastsMaximumSize() {
        var delegate = mock(Forecaster.class);
        Forecast expectedForecast = new Forecast(1, 2, "cold");
        given(delegate.forecastFor("Oxford", FRIDAY)).willReturn(expectedForecast);

        var underTest = new CachingForecaster(delegate);

        var forecast = underTest.forecastFor("Oxford", FRIDAY);
        underTest.forecastFor("Oxford", MONDAY);
        underTest.forecastFor("Oxford", TUESDAY);
        underTest.forecastFor("Oxford", WEDNESDAY);
        underTest.forecastFor("Oxford", FRIDAY);

        verify(delegate, times(2)).forecastFor("Oxford", FRIDAY); // This is arguable
        assertThat(forecast, equalTo(expectedForecast));
    }

}