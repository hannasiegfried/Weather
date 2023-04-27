package com.oocode;

import org.junit.Test;

import java.time.DayOfWeek;
import java.util.Random;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class CachingForecasterTest {
    @Test
    public void delegatesIfForecastNotSeenBefore() {
        Forecaster forecaster = mock(Forecaster.class);
        Forecast expected = new Forecast(new Random().nextInt(),
                new Random().nextInt(),
                "description-" + new Random().nextInt());
        given(forecaster.forecastFor(DayOfWeek.FRIDAY, "Somewhere"))
                .willReturn(expected);

        CachingForecaster underTest = new CachingForecaster(forecaster);

        Forecast actual = underTest.forecastFor(DayOfWeek.FRIDAY, "Somewhere");
        assertThat(actual, equalTo(expected));
    }
}