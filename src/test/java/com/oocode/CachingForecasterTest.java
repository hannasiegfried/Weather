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
    private final Forecaster delegate = mock(Forecaster.class);
    private final CachingForecaster underTest = new CachingForecaster(delegate);

    @Test
    public void delegatesIfForecastNotSeenBefore() {
        Forecast expected = randomForecast();
        DayOfWeek dayOfWeek = randomDayOfWeek();
        String place = randomPlace();
        given(delegate.forecastFor(dayOfWeek, place)).willReturn(expected);

        Forecast actual = underTest.forecastFor(dayOfWeek, place);

        assertThat(actual, equalTo(expected));
    }

    @Test
    public void doesNotAskDelegateAgainIfSeenBefore() {
        Forecast expected = randomForecast();
        DayOfWeek dayOfWeek = randomDayOfWeek();
        String place = randomPlace();
        given(delegate.forecastFor(dayOfWeek, place)).willReturn(expected);

        underTest.forecastFor(dayOfWeek, place);

        Forecast actual = underTest.forecastFor(dayOfWeek, place);
        assertThat(actual, equalTo(expected));
        verify(delegate, times(1)).forecastFor(dayOfWeek, place);
    }

    private DayOfWeek randomDayOfWeek() {
        return DayOfWeek.of(1 + random.nextInt(7));
    }

    private String randomPlace() {
        return "place-" + random.nextInt();
    }

    private Forecast randomForecast() {
        return new Forecast(random.nextInt(),
                random.nextInt(),
                "description-" + random.nextInt());
    }
}