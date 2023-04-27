package com.oocode;

import org.junit.Test;

import java.util.Random;

import static java.time.DayOfWeek.FRIDAY;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class CachingForecasterTest {
    private final Random random = new Random();

    @Test
    public void delegatesIfForecastNotSeenBefore() {
        Forecaster forecaster = mock(Forecaster.class);
        Forecast expected = new Forecast(random.nextInt(),
                random.nextInt(),
                "description-" + random.nextInt());
        given(forecaster.forecastFor(FRIDAY, "Somewhere"))
                .willReturn(expected);

        CachingForecaster underTest = new CachingForecaster(forecaster);

        Forecast actual = underTest.forecastFor(FRIDAY, "Somewhere");
        assertThat(actual, equalTo(expected));
    }
}