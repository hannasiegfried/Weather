package com.oocode;

import org.junit.Test;

import java.time.DayOfWeek;
import java.util.Random;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class CachingForecasterTest {
    private final Random random = new Random();

    @Test
    public void delegatesIfForecastNotSeenBefore() {
        Forecaster forecaster = mock(Forecaster.class);
        Forecast expected = new Forecast(random.nextInt(),
                random.nextInt(),
                "description-" + random.nextInt());
        DayOfWeek dayOfWeek = DayOfWeek.of(1 + random.nextInt(7));
        String place = "place-" + random.nextInt();
        given(forecaster.forecastFor(dayOfWeek, place)).willReturn(expected);

        CachingForecaster underTest = new CachingForecaster(forecaster);

        Forecast actual = underTest.forecastFor(dayOfWeek, place);
        assertThat(actual, equalTo(expected));
    }

    @Test
    public void doesNotAskDelegateAgainIfSeenBefore() {
        Forecaster forecaster = mock(Forecaster.class);
        Forecast expected = new Forecast(random.nextInt(),
                random.nextInt(),
                "description-" + random.nextInt());
        DayOfWeek dayOfWeek = DayOfWeek.of(1 + random.nextInt(7));
        String place = "place-" + random.nextInt();
        given(forecaster.forecastFor(dayOfWeek, place)).willReturn(expected);

        CachingForecaster underTest = new CachingForecaster(forecaster);

        underTest.forecastFor(dayOfWeek, place);
        Forecast actual = underTest.forecastFor(dayOfWeek, place);
        assertThat(actual, equalTo(expected));
        verify(forecaster, times(1)).forecastFor(dayOfWeek, place);
    }
}