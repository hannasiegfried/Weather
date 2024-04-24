package com.oocode;

import org.junit.jupiter.api.Test;

import static java.time.DayOfWeek.FRIDAY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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
}